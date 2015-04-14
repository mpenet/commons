(ns qbits.commons.exception
  "Borrowed from flatland/useful"
  (:require
   [clojure.string :as str]
   [clojure.stacktrace :as st]))

(defn cause-trace
  "Return an Exception's cause trace as an array of lines"
  [exception]
  (map str/trim (str/split-lines (with-out-str (st/print-cause-trace exception)))))

(defn ex-map
  "Return a map with the keys: :name, :message, and :trace. :trace is the cause trace as an array of lines "
  [^Throwable exception]
  {:name (-> exception class .getName)
   :message (.getMessage exception)
   :trace (cause-trace exception)})
