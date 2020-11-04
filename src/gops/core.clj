(ns gops.core
  (:require [gops.moves :refer :all])
  (:require [gops.messages :refer :all])
  (:gen-class))

(defn card-values [card]
  (condp = card
    :A 1
    :2 2
    :3 3
    :4 4
    :5 5
    :6 6
    :7 7
    :8 8
    :9 9
    :10 10
    :J 11
    :Q 12
    :K 13
    0))

(defn new-score [scores card0 card1]
  (let [score0 (first scores)
        score1 (->> scores rest first)
        value0 (card-values card0)
        value1 (card-values card1)]
    (cond
      (> value0 value1) [(+ score0 value0) score1]
      (< value0 value1) [score0 (+ score1 value1)]
      :else [score0 score1])))

(defn next-state [states]
  (let [state (last states)
        bounty-card (pick-random (:bounty-cards state))
        card0 (play-random-strategy (first (:player-cards state)) bounty-card)
        card1 (play-equal-strategy (first (rest (:player-cards state))) bounty-card)]
    (conj states {:turn (+ (:turn state) 1)
                  :last-bounty-card bounty-card
                  :last-played-cards [card0 card1]
                  :bounty-cards (without (:bounty-cards state) bounty-card)
                  :player-cards [(without (->> state :player-cards first) card0)
                                 (without (->> state :player-cards rest first) card1)]
                  :scores (new-score (:scores state) card0 card1)})))

(defn next-and-get [states]
  (-> states next-state last))

(defn play-turn [states state-change end-condition]
  (if (end-condition (last states))
    states
    (play-turn (next-state states) state-change end-condition)))

(defn are-there-bounty? [state]
  (= 0 (-> state :bounty-cards count)))

(defn run-game []
  (let [states [{:turn 0
                 :bounty-cards #{:A :1 :2 :3 :4 :5 :6 :7 :8 :9 :10 :J :Q :K}
                 :player-cards [#{:A :1 :2 :3 :4 :5 :6 :7 :8 :9 :10 :J :Q :K}
                               #{:A :1 :2 :3 :4 :5 :6 :7 :8 :9 :10 :J :Q :K}]
                 :scores [0 0]}]]
    (play-turn states next-state are-there-bounty?)))

(defn report [states on-turn on-end]
  (str (clojure.string/join "" (map on-turn (rest states))) "\n" (on-end (-> (rest states) last :scores))))

(defn -main [& args]
  (println (report (run-game) turn-message end-message)))
