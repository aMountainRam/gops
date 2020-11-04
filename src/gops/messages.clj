(ns gops.messages)

(defn win-message [scores]
  (let [p1 (first scores)
        p2 (last scores)
        diff (- p1 p2)]
    (cond
      (> diff 0) "Player 0 wins!"
      (< diff 0) "Player 1 wins!"
      :else "Players tie.")))

(defn score-message [scores]
  (let [p1 (first scores)
        p2 (last scores)]
    (str "Scores: " p1 " v " p2)))

(defn end-message [scores]
  (str (score-message scores) "\n" (win-message scores)))

(defn turn-message [state]
  (let [turn (:turn state)
        card0 (-> state :last-played-cards first)
        card1 (-> state :last-played-cards rest first)]
    (str "Turn: " turn " - Bounty: " (:last-bounty-card state)
      "\n\tPlayer 0 plays: " card0
      "\n\tPlayer 1 plays: " card1 "\n")))