(ns loopme.cache.fixure
  (:require [loopme.cache.processor :as cache]))

(defn clear [f]
  (cache/clear-cache :soft)
  (f))