(ns neovim-client.parser
  "Turn Neovim's api-info schema into Clojure functions."
  (:require
    [clojure.java.io :as io]
    [clojure.string :as string]
    [msgpack.clojure-extensions]
    [msgpack.core :as msgpack]))

(defn namespace*
  "As far as we know, there will only be three namespace suffixes possible:
  buffer, tabpage, window. However, at api_level 0 the operation named
  'buffer_get_line_count' was changed to 'nvim_buf_get_line_count' in
  api_level 1."
  [namespace]
  (condp string/starts-with? namespace
    "buffer" "buffer"
    "window" "window"
    "tabpage" "tab"))

(defn- method?
  [api-op-spec]
  (get api-op-spec "method"))

(defn- name-parts*
  [api-op-spec]
  (let [parts (-> api-op-spec
                  (get "name")
                  (string/replace-first "nvim_" "")
                  (string/replace-first "vim_" "")
                  (string/split #"_"))]
    (if (method? api-op-spec)
      [(first parts) (rest parts)]
      [nil parts])))

(defn- name-parts
  [api-op-spec level]
  (let [[namespace name-parts] (name-parts* api-op-spec)
        prefix (str "neovim-client." level ".api")]
    {:ns (symbol (if namespace
                   (str prefix "." (namespace* namespace))
                   prefix))
     :name (symbol (string/join "-" name-parts))}))

(defn- params
  [api-op-spec]
  (mapv (comp symbol second) (get api-op-spec "parameters")))

(defn api-op-spec->fn-spec
  [level api-op-spec]
  (merge
    (name-parts api-op-spec level)
    {:op (get api-op-spec "name")
     :params (params api-op-spec)
     :method (method? api-op-spec)}))

(defn fn-spec->defn*
  [{:keys [params op name]}]
  `(~'defn ~name [~'component ~@params]
     (neovim-client.nvim/exec ~'component ~op ~@params)))

(defn fn-spec->defn-async*
  [{:keys [params op name]}]
  `(~'defn ~(symbol (str name "-async")) [~'component ~@params ~'f]
     (neovim-client.nvim/exec-async ~'component ~op ~@params ~'f)))

(defn fn-spec->defns
  [{:keys [name] :as fn-spec}]
  (list
    (fn-spec->defn* fn-spec)
    (fn-spec->defn-async* fn-spec)))

(defn- include?
  "Should we include this operation?"
  [version {:strs [since deprecated_since] :as op-spec}]
  (and (<= since version)
       (or (nil? deprecated_since)
           (< version deprecated_since))))

(defn functions-by-ns
  "Return a map of namespace => sequence of `defn`."
  [api-info version]
  (->> (get api-info "functions")
       (filter (partial include? version))
       (map (partial api-op-spec->fn-spec version))
       (reduce (fn [m fn-spec]
                 (update m (:ns fn-spec) concat (fn-spec->defns fn-spec)))
               {})))

(defn max-level
  [{:strs [version]} prerelease-ok?]
  (let [{:strs [api_level api_prerelease]} version]
    (if api_prerelease
      (if prerelease-ok? api_level (dec api_level))
      api_level)))

(defn min-level
  [{:strs [version]}]
  (get version "api_compatible"))

(defn api-info-resource
  []
  (msgpack/unpack (-> "api-info.mp"
                      clojure.java.io/resource
                      clojure.java.io/input-stream)))

(defn require-expr
  [ns]
  `(~'ns ~ns (:require [neovim-client.nvim])))

(defn write-files
  [api-info level]
  (let [f-map (functions-by-ns api-info level)]
    (doseq [[ns fs] f-map]
      (let [filename (str (string/join "/"
                                       (concat
                                         ["src/neovim_client"]
                                         (rest (string/split (str ns) #"\."))))
                          ".clj")]
        (io/make-parents filename)
        (spit filename (string/join "\n\n" (concat [(require-expr ns)] fs)))))))

(defn generate
  "Intern functions for all api operations."
  []
  (let [api-info (api-info-resource)]
    (doseq [level (range (min-level api-info)
                         (inc (max-level api-info true)))]
      (write-files api-info level))))
