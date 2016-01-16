(ns qbits.commons.jvm)

(defn add-shutdown-hook!
  [f]
  (.addShutdownHook (Runtime/getRuntime)
                    (Thread. ^Runnable f)))

(defn set-uncaught-ex-handler!
  [f]
  (Thread/setDefaultUncaughtExceptionHandler
   (reify Thread$UncaughtExceptionHandler
     (uncaughtException [_ thread ex]
       (f thread ex)))))
