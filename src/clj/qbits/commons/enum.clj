(ns qbits.commons.enum
  (:require
   [clojure.string :as str]
   [clojure.spec.alpha :as spec]
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

(defn enum->set [enum]
  (into #{}
        (map #(format-key (.name ^Enum %)))
        (java.util.EnumSet/allOf enum)))

(defmacro defspec
  [k enum]
  `(spec/def ~k ~(enum->set (eval enum))))

(defn fields->map
  [^Object cls]
  (reduce
   (fn [m ^java.lang.reflect.Field f]
     (assoc m (format-key (.getName f)) (.get f cls)))
   {}
   (->> cls bean :declaredFields)))

(defmacro case-enum
  "Like `case`, but explicitly dispatch on Java enum ordinals."
  [e & clauses]
  (letfn [(enum-ordinal [e] `(let [^Enum e# ~e] (.ordinal e#)))]
    `(case ~(enum-ordinal e)
       ~@(concat
          (mapcat (fn [[test result]]
                    [(eval (enum-ordinal test)) result])
                  (partition 2 clauses))
          (when (odd? (count clauses))
            (list (last clauses)))))))

(defmacro enum->fn
  "Like enum->map but >6x faster and throws on invalid enum values
  instead of just returning nil"
  [enum-symbol]
  (let [enum (eval enum-symbol)
        enum-map (enum->map enum)]
    `(fn [x#]
       (case
           x#
         ~@(mapcat (fn [[k ^Enum e]]
                     [k (->> (.name e)
                             (str enum-symbol "/")
                             symbol)])
                   enum-map)
         (throw (ex-info ~(format "Invalid Enum key - possible keys are -> %s"
                                  (str/join ", " (map key enum-map)))
                         {:type  ::invalid-enum-value}))))))
