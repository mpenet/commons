(defproject cc.qbits/commons "1.0.0-alpha1"
  :description "Stuff"
  :url "https://github.com/mpenet/commons"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :deploy-repositories [["releases" :clojars] ["snapshots" :clojars]]
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/spec.alpha "0.2.176"]]
  :source-paths ["src/clj"]
  :global-vars {*warn-on-reflection* true})
