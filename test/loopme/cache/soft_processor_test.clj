(ns loopme.cache.soft-processor-test
  (:require [clojure.test :refer :all]
            [clojure.core.cache :as cache]
            [loopme.cache.processor :refer :all]))

(deftest soft-cache
  (testing "default - data not exists"
    (is (not (has? "soft-test-key" :soft))))

  (testing "default read = nil"
    (is (nil? (pull "soft-test-key" :soft))))

  (testing "basic insert = value"
    (is (= "test-value" (push "soft-test-key" "test-value" :soft)))
    (is (= "test-value" (pull "soft-test-key" :soft))))

  (testing "basic insert to correct cache type"
    (push "soft-test-key" "test-value" :soft)
    (is (cache/has? @SOFT-CACHE (keyword "soft-test-key"))))

  (testing "basic re-insert = new value"
    (is (= "test-value2" (push "soft-test-key" "test-value2" :soft)))
    (is (= "test-value2" (pull "soft-test-key" :soft))))

  (testing "basic remove = nil"
    (push "soft-test-key" "test-value" :soft)
    (is (has? "soft-test-key" :soft))
    (delete "soft-test-key" :soft)
    (is (not (has? "soft-test-key" :soft))))

  (testing "hit-or-miss logic"
    (is (= "test-value" (hit-or-miss "soft-test-key" "test-value" :soft)))
    (is (= "test-value" (pull "soft-test-key" :soft)))
    (is (= "test-value" (hit-or-miss "soft-test-key" "test-value2" :soft)))
    (is (= "test-value" (pull "soft-test-key" :soft)))))
