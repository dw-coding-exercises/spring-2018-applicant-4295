# Find My Next Election App

This app makes the following changes to the provided Democracy Works server-side web application:

* Creates the missing /search route
* Ingests the incoming form parameters
* Derives OCD-IDs from the user-submitted data
* Requests matching elections from the DW election API
* Displays them to the user

As well as fulfilling the basic requirements, it adds:

* Basic HTML form validation - form must be filled out in order to submit
* Displays the user's address along with election results
* Formats the returned date so is correct in user's timezone
* Tells the user if no elections are found for that address
* `search_test.clj`, new unit test file with 3 assertions

### Tools used

The newly-added file `search.clj` uses the [clj-http](https://github.com/dakrone/clj-http) library to make the http request to DW's election API. [clojure/edn](https://clojure.github.io/clojure/clojure.edn-api.html) is used to parse the returned EDN data into Clojure-usable code.

The returned EDN date is formatted with [clj-time](https://github.com/clj-time/clj-time)'s `format` and `coerce` tools.

### Next steps/To-dos

* Testing needs to be built out and API calls mocked with `clj-http-fake`.
* As mentioned in challenge description, could dig into user's address to return more personalized data.
* Implementation issue with `ring.util.anti-forgery` - if page is left idle, then refreshed, throws an error.
* More substantial validation of form fields before making API call.