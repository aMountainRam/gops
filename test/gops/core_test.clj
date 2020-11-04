(ns gops.core-test
  (:require [clojure.test :refer :all]
            [gops.core :as core]))

;; ----------
;; test core/play routines
(deftest new-score-test
  (testing "Test for increasing the score"
    (let [init-score [1 2]]
      (is (= [1 13] (core/new-score init-score :4 :J))))))

(def init-state [{:turn 0
                  :bounty-cards #{:A :1 :2 :3 :4 :5 :6 :7 :8 :9 :10 :J :Q :K}
                  :player-cards [#{:A :1 :2 :3 :4 :5 :6 :7 :8 :9 :10 :J :Q :K}
                                 #{:A :1 :2 :3 :4 :5 :6 :7 :8 :9 :10 :J :Q :K}]
                  :scores [0 0]}])
(deftest change-init-state
  (testing "Attempt to modify the initial state"
    (is (= 1 (-> init-state core/next-and-get :turn)))
    (is (= (-> init-state last :bounty-cards count)
           (-> init-state core/next-and-get :bounty-cards count inc)))
    (is (= (-> init-state last :player-cards first count)
           (-> init-state core/next-and-get :player-cards first count inc)))))

(deftest run-a-game
  (testing "Run the full game providing init-state"
    (let [game (core/play-turn init-state core/next-state core/are-there-bounty?)]
      (is (empty? (-> game last :bounty-cards)))
      (is (empty? (-> game last :player-cards first)))
      (is (empty? (-> game last :player-cards rest first))))))

(deftest run-the-game
  (testing "Run the full game"
    (let [game (core/run-game)]
      (is (empty? (-> game last :bounty-cards)))
      (is (empty? (-> game last :player-cards first)))
      (is (empty? (-> game last :player-cards rest first))))))
;; ----------