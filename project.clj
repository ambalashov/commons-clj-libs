(defproject loopme/cache "0.1.1"
  :description "Loopme cache library."
  :license {:name "MIT license"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/core.cache "0.6.3"]]
  :plugins [[s3-wagon-private "1.1.2"]]
  :repositories [["loopme" {:url           "s3p://lm-artifacts/releases/"
                            :username :env :passphrase :env
                            :sign-releases false}]])
