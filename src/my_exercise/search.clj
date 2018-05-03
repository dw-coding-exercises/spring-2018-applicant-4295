(ns my-exercise.search
  (:require [clj-http.client :as client]
            [clj-time.core :as t]
            [my-exercise.home :as home]
            [clojure.string :as str]
            [clojure.edn :as edn]))

(defn format-string [input]
  (str/replace 
    (str/lower-case input) #"[^a-z]" ""))

(defn parser [response] 
  (def length (- (count response) 1))
  (def snipped-response
    (subs response 1 length)) ;removes parentheses from initial EDN response so it can be parsed
  (if (str/blank? snipped-response)
    (edn/read-string "{:message \"Uh-oh! We couldn't find an election for that address -- please try again.\"}") ;adds message to user if response is blank
    (edn/read-string snipped-response)))

(defn create-api-request [place state]
  (def query-string-1 "https://api.turbovote.org/elections/upcoming?district-divisions=ocd-division/country:us/state:")
  (def query-string-2 ",ocd-division/country:us/state:")
  (def query-string-3 "/place:")
  (str/join [query-string-1 state query-string-2 state query-string-3 place]))

(defn create-user-address-string [address parsed-response] ;creates string to show user which address was submitted
  (def address-string {:address-string 
    (str/join " " [ (address "street")
                    (address "street-2")
                    (address "city")
                    (address "state")
                    (address "zip")])})
  (merge-with +
      address-string
      parsed-response))

(defn election-search [request]
  (def address (:form-params request))
  (def place (format-string (address "city")))
  (def state (str/lower-case (address "state")))
  (def api-request
    (create-api-request place state))
  (def response
    (:body (client/get api-request {:insecure? true})))
  (def parsed-response
    (parser response))
  (def final-response
    (create-user-address-string address parsed-response))
  (home/page final-response))