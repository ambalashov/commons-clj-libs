(ns loopme-cache.specific-test
  (:require [clojure.test :refer :all]
            [clojure.core.cache :as cache]
            [loopme-cache.processor :refer :all]))

(deftest specific-cache
  (testing "push if blank, read if present"
    (is (not (has? "spec-test" :soft)))
    (is (= "test-value" (hit-or-miss "spec-test" "test-value" :soft)))
    (is (has? "spec-test" :soft))
    (is (= "test-value" (hit-or-miss "spec-test" "test-value-new" :soft)))
    (is (has? "spec-test" :soft))
    (is (= "test-value" (pull "spec-test" :soft))))
  (testing "hit-or-miss-function"
    (is (not (has? "spec-test-function" :soft)))
    (is (= 3 (hit-or-miss-function "spec-test-function" :soft #(+ 2 1))))
    (is (has? "spec-test-function" :soft))
    (is (= 3 (hit-or-miss-function "spec-test-function" :soft #(+ 2 2))))
    (is (has? "spec-test-function" :soft))
    (is (= 3 (pull "spec-test-function" :soft)))))
