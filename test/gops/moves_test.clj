(ns gops.moves-test
  (:require [clojure.test :refer :all]
            [gops.moves :refer :all]))

;; ----------
;; test moves
(deftest moves-1
  (testing "Remove from array"
    (let [arr #{:J :A :5 :6}]
      (is (= (count (rest arr)) (count (without arr :J))))
      (is (= (set (rest arr)) (without arr :A)))
      (is (= (count arr) (count (without arr :K)))))))

(deftest moves-2
  (testing "Pick a card/Play random"
    (let [arr #{:J :A :5 :6}
          bounty :A]
      (is (contains? arr (play-random-strategy arr bounty)))
      (is (contains? arr (pick-random arr))))))

(deftest moves-3
  (testing "Play equals"
    (let [arr #{:J :5 :K}
          bounty :K]
      (is (= bounty (play-equal-strategy arr bounty))))))
;; ----------