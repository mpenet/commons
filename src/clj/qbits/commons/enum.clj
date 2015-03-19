(ns qbits.commons.enum
  (:require [clojure.string :as str]))

(defn format-key
  [k]
  (-> k
      str/lower-case
      (str/replace "_" "-")
      keyword))

(defn enum->map
  [enum]
  (reduce
   (fn [m hd]
     (assoc m (format-key (.name ^Enum hd)) hd))
   {}
   (java.util.EnumSet/allOf enum)))

(defn fields->map
  [^Object cls]
  (reduce
   (fn [m ^java.lang.reflect.Field f]
     (assoc m (format-key (.getName f)) (.get f cls)))
   {}
   (->> cls bean :declaredFields)))
