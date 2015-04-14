(ns qbits.commons.exception
  "Borrowed from flatland/useful")

(defn cause-trace
  "Return an Exception's cause trace as an array of lines"
  [exception]
  (map trim (split-lines (with-out-str (print-cause-trace exception)))))

(defn ex-map
  "Return a map with the keys: :name, :message, and :trace. :trace is the cause trace as an array of lines "
  [exception]
  {:name (-> exception class .getName)
   :message (.getMessage exception)
   :trace (cause-trace exception)})
