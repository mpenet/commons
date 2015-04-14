(ns qbits.commons
  (:require [qbits.commons.ns :as qns]))

(doseq [module '(enum exception ns string)]
  (qns/alias-ns (symbol (str "qbits.commons." module))))
