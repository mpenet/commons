(ns qbits.commons.jvm)

(defn add-shutdown-hook!
  [f]
  (.addShutdownHook (Runtime/getRuntime)
                    (Thread. f)))
