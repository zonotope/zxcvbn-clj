(ns zxcvbn.core
  (:import [com.nulabinc.zxcvbn Zxcvbn]))

(defn- crack-time->map [crack-time]
  {:online-throttling-100-per-hour
   (.getOnlineThrottling100perHour crack-time)

   :online-no-throttling-10-per-second
   (.getOnlineNoThrottling10perSecond crack-time)

   :offline-slow-hashing-1e4-per-second
   (.getOfflineSlowHashing1e4perSecond crack-time)

   :offline-fast-hashing-1e10-per-second
   (.getOfflineFastHashing1e10PerSecond crack-time)})

(defn- sequence->map [sequence])

(defn- feedback->map [feedback]
  {:suggestions (.getSuggestions feedback)
   :warning (.getWarning feedback)})

(defn- strength->map [strength]
  (let [crack-time-seconds (.getCrackTimeSeconds strength)
        crack-times-display (.getCrackTimesDisplay strength)
        feedback (.getFeedback strength)]

    {:calc-time (.getCalcTime strength)
     :guesses (.getGuesses strength)
     :guesses-log-10 (.getGuessesLog10 strength)
     :score (.getScore strength)
     :sequence (.getSequence strength)

     :crack-time-seconds (crack-time->map crack-time-seconds)
     :crack-times-display (crack-time->map crack-times-display)
     :feedback (feedback->map feedback)}))

(let [checker (Zxcvbn.)]
  (defn check [password]
    (strength->map
     (.measure checker password))))
