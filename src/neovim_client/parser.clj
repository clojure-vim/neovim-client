(ns neovim-client.parser
  "Turn Neovim's api-info schema into Clojure functions."
  (:require [clojure.string :as string]))

(defn- name-parts
  [api-op-spec]
  (-> api-op-spec
      (get "name")
      (string/replace-first "nvim_" "")
      (string/replace-first "vim_" "")
      (string/split #"_")))

(defn- fn-name
  [api-op-spec]
  (->> api-op-spec
       name-parts
       rest
       (string/join "-")
       symbol))

;; TODO - factor?
(defn- fn-ns
  [api-op-spec]
  (->> api-op-spec
       name-parts
       first
       (str "neovim-client.nvim.")
       symbol))

(defn- params
  [api-op-spec]
  (mapv (comp symbol second) (get api-op-spec "parameters")))

(defn api-op-spec->fn-spec
  [api-op-spec]
  {:ns (fn-ns api-op-spec)
   :op (get api-op-spec "name")
   :params (params api-op-spec)
   :name (fn-name api-op-spec)})

(defn fn-spec->fn
  [{:keys [params op]}]
  `(fn [~@params]
     (neovim-client.nvim/exec ~op ~@params)))

;; TODO - will f# cloud docstring?
(defn fn-spec->fn-async
  [{:keys [params op]}]
  `(fn [~@params f#]
     (neovim-client.nvim/exec-async ~op ~@params f#)))

;; TODO - is quoting pattern here a smell?
(defn fn-spec->intern
  [{:keys [name ns params] :as fn-spec}]
  `(do (intern '~ns '~name ~(fn-spec->fn fn-spec))
       (intern '~ns '~(symbol (str name "-async"))
               ~(fn-spec->fn-async fn-spec))))

(def test-op-spec1 '{"since" 1,
                     "method" true,
                     "name" "nvim_win_get_number",
                     "return_type" "Integer",
                     "parameters"  (("Window" "window"))})

#_(eval (ns neovim-client.nvim.win))
#_(eval (fn-spec->intern (api-op-spec->fn-spec test-op-spec1)))
