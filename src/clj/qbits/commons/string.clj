(ns qbits.commons.string
  (:require [clojure.string :as str]))

(defn camel->dashed
  [s]
  (-> s
      (str/replace #"^[A-Z]+" str/lower-case)
      (str/replace #"_?([A-Z]+)"
                 (comp (partial str "-")
                       str/lower-case second))
      (str/replace #"-|_" "-")))
