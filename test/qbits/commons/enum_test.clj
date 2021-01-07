(ns qbits.commons.enum-test
  (:require
   [clojure.test :as t :refer [deftest testing is]]
   [qbits.commons.enum :as sut]
   [clojure.string :as str])
  (:import
   [java.math RoundingMode]))

(deftest enum->fn-test
  (testing "rounding-mode retrieval"
    (let [efn (sut/enum->fn RoundingMode)
          v (efn :up)]
      (is (instance? RoundingMode v))
      (is (= "UP" (str v)))))

  (testing "invalid key is reported in ex-data and exception message"
    (let [efn (sut/enum->fn RoundingMode)
          v (try
              (efn :foo)
              (catch Exception err err))
          {exd-t :type
           exd-k :key
           :as exd} (ex-data v)
          ex-msg (when (instance? Exception v) (.getMessage ^Exception v))]
      (is (some? exd))
      (is (= ::sut/invalid-enum-value exd-t))
      (is (= :foo exd-k))
      (is (str/starts-with? ex-msg "Invalid Enum key - :foo" )))))
