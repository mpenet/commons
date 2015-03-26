(ns qbits.commons.enum
  (:require
   [clojure.string :as str]
   [qbits.commons.string :as s]))

(defn format-key
  [k]
  (-> k
      s/camel->dashed
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
