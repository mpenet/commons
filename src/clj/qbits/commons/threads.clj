(ns qbits.commons.threads)

(defn set-thread-name!
  [n]
  (-> (Thread/currentThread)
      (.setName  (str n))))
