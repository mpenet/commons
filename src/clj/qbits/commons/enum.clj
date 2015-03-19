(ns qbits.commons.enum
  (:require [clojure.string :as string]))

(defn enum->map
  [enum]
  (reduce
   (fn [m hd]
     (assoc m (-> (.name ^Enum hd)
                  (.toLowerCase)
                  (string/replace "_" "-")
                  keyword)
            hd))
   {}
   (java.util.EnumSet/allOf enum)))
