(ns my-exercise.search-test
  (:require [clojure.test :refer :all]
            [my-exercise.search :refer :all]))

; defining variables
(def sample-place
  (format-string "San Francisco"))

(def parsed-sample-edn
  (parser "({:a 1})"))

(def parsed-blank-edn
  (parser (str "()")))

; tests
(deftest format-string-test
  (testing "Removes spaces from string and returns lowercase version"
      (is (= "sanfrancisco" sample-place))))

(deftest parser-test
  (testing "Takes EDN, removes outer parentheses and parses to a Clojure array-map"
      (is (= (type (array-map :a 1)) (type parsed-sample-edn)))))

(deftest parser-blank-test
  (testing "Takes empty EDN, parses to a Clojure array-map with an 'election not found' message to user"
      (is (= (str "Uh-oh! We couldn't find an election for that address -- please try again.")
              (:message parsed-blank-edn)))))
      


