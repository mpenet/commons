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

(defmacro compile-if
  "Evaluate `exp` and if it returns logical true and doesn't error, expand to
  `then`.  Else expand to `else`.
  (compile-if (Class/forName \"java.util.concurrent.ForkJoinTask\")
    (do-cool-stuff-with-fork-join)
    (fall-back-to-executor-services))
  Taken from https://github.com/clojure/clojure/blob/master/src/clj/clojure/core/reducers.clj#L24"
  [exp then & [else]]
  (if (try (eval exp)
           (catch Throwable _ false))
    `(do ~then)
    `(do ~else)))

(defmacro compile-if-ns-exists
  [n then & [else]]
  `(compile-if (try (require (quote ~n))
                    true
                    (catch Exception _# nil))
               ~then
               ~else))

(defmacro compile-if-class-exists
  [k then & [else]]
  `(compile-if (Class/forName ~(str k))
               ~then
               ~else))
