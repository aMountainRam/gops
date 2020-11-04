(ns gops.messages-test
  (:require [clojure.test :refer :all]
            [gops.messages :refer :all]))

;; ----------
;; test message routines
(deftest message-routines
  (testing "Response on message routines"
    (let [scores [1 0]
          bounty-card :2
          states [{:turn 1
                   :last-played-cards [:J bounty-card]
                   :last-bounty-card bounty-card}]]
      (is (= "Player 0 wins!" (win-message scores)))
      (is (= "Scores: 1 v 0" (score-message scores)))
      (is (= "Turn: 1 - Bounty: :2\n\tPlayer 0 plays: :J\n\tPlayer 1 plays: :2\n"
            (turn-message (last states)))))))
;; ----------
