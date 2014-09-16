(ns loopme.cache.short-processor-test
  (:require [clojure.test :refer :all]
            [clojure.core.cache :as cache]
            [loopme.cache.processor :refer :all]))

(deftest short-cache
  (testing "default - data not exists"
    (is (not (has? "short-test-key" :short))))

  (testing "default read = nil"
    (is (nil? (pull "short-test-key" :short))))

  (testing "basic insert = value"
    (is (= "test-value" (push "short-test-key" "test-value" :short)))
    (is (= "test-value" (pull "short-test-key" :short))))

  (testing "basic insert to correct cache type"
    (push "short-test-key" "test-value" :short)
    (is (cache/has? @SHORT-CACHE (symbol "short-test-key"))))

  (testing "ttl correct check"
    (push "short-test-key" "test-value" :short)
    (Thread/sleep 30000)
    (is (not (has? "short-test-key" :short))))

  (testing "basic re-insert = new value"
    (is (= "test-value2" (push "short-test-key" "test-value2" :short)))
    (is (= "test-value2" (pull "short-test-key" :short))))

  (testing "basic remove = nil"
    (push "short-test-key" "test-value" :short)
    (is (has? "short-test-key" :short))
    (delete "short-test-key" :short)
    (is (not (has? "short-test-key" :short))))

  (testing "hit-or-miss logic"
    (is (= "test-value" (hit-or-miss "short-test-key" "test-value" :short)))
    (is (= "test-value" (pull "short-test-key" :short)))
    (is (= "test-value" (hit-or-miss "short-test-key" "test-value2" :short)))
    (is (= "test-value" (pull "short-test-key" :short)))))
