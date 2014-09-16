(ns loopme.cache.long-processor-test
  (:require [clojure.test :refer :all]
            [clojure.core.cache :as cache]
            [loopme.cache.processor :refer :all]
            [loopme.cache.fixure :as fixure]))

(use-fixtures :each fixure/clear)

(deftest long-cache
  (testing "default - data not exists"
    (is (not (has? "long-test-key" :long))))

  (testing "default read = nil"
    (is (nil? (pull "long-test-key" :long))))

  (testing "basic insert = value"
    (is (= "test-value" (push "long-test-key" "test-value" :long)))
    (is (= "test-value" (pull "long-test-key" :long))))

  (testing "basic insert to correct cache type"
    (push "long-test-key" "test-value" :long)
    (is (cache/has? @LONG-CACHE (symbol "long-test-key"))))

  (testing "basic re-insert = new value"
    (is (= "test-value2" (push "long-test-key" "test-value2" :long)))
    (is (= "test-value2" (pull "long-test-key" :long))))

  (testing "basic remove = nil"
    (push "long-test-key" "test-value" :long)
    (is (has? "long-test-key" :long))
    (delete "long-test-key" :long)
    (is (not (has? "long-test-key" :long))))

  (testing "hit-or-miss logic"
    (is (= "test-value" (hit-or-miss "long-test-key" "test-value" :long)))
    (is (= "test-value" (pull "long-test-key" :long)))
    (is (= "test-value" (hit-or-miss "long-test-key" "test-value2" :long)))
    (is (= "test-value" (pull "long-test-key" :long)))))
