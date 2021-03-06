(ns loopme.cache.processor
  (:require [clojure.core.cache :as cache]))

(def SOFT-CACHE (atom (cache/soft-cache-factory {})))
(def LONG-CACHE (atom (cache/ttl-cache-factory {} :ttl 900000))) ; 15 mins
(def SHORT-CACHE (atom (cache/ttl-cache-factory {} :ttl 30000))) ; 30 sec

(defn cache-db
  [cache-type]
  (case cache-type
    :soft  SOFT-CACHE
    :long  LONG-CACHE
    :short SHORT-CACHE))

(defn has?
  [cache-key cache-type]
  (let [db (cache-db cache-type)]
    (cache/has? @db (keyword cache-key))))

(defn pull
  [cache-key cache-type]
  (let [db (cache-db cache-type)]
    ((keyword cache-key) @db)))

(defn push
  [cache-key cache-value cache-type]
  (let [db (cache-db cache-type)]
    (swap! db cache/miss (keyword cache-key) cache-value))
  cache-value)

(defn delete
  [cache-key cache-type]
  (let [db (cache-db cache-type)]
    (swap! db cache/evict (keyword cache-key))))

(defn hit-or-miss
  [cache-key cache-value cache-type]
  (if (has? cache-key cache-type)
    (pull cache-key cache-type)
    (push cache-key cache-value cache-type)))

(defn hit-or-miss-function
  [cache-key cache-type f]
  (if (has? cache-key cache-type)
    (pull cache-key cache-type)
    (push cache-key (f) cache-type)))

(defn clear-cache
  [cache-type]
  (case cache-type
    :soft  (swap! SOFT-CACHE (fn[c] (cache/soft-cache-factory {})))
    :long  (swap! LONG-CACHE (fn[c] (cache/ttl-cache-factory {} :ttl 900000)))
    :short (swap! SHORT-CACHE (fn[c] (cache/ttl-cache-factory {} :ttl 30000)))))