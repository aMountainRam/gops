(ns gops.moves)

(defn play-random-strategy [playing-cards bounty]
  (rand-nth (vec playing-cards)))

(defn play-equal-strategy [playing-cards bounty]
  bounty)

(defn pick-random [cards]
  (rand-nth (vec cards)))

(defn without [arr value]
  (disj arr value))