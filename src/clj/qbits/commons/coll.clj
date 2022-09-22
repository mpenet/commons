(ns qbits.commons.coll
  (:refer-clojure :exclude [last]))

(defn last
  [xs]
  (assert (and (indexed? xs) (counted? xs)))
  (let [ctn (count xs)]
    (when (pos? ctn)
      (nth xs (dec ctn)))))
