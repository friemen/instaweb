(ns sample.page
  "Interactively create web pages"
  (:require [hiccup.core :as hc]
            [hiccup.page :as hp]
            [garden.core :refer [css]]
            [garden.color :refer [rgb]]))


;; Load this ns into a REPL
;; then try
#_(require '[instaweb.viewer :as v])

;; Switch to your browser and load http://localhost:3000/viewer

;; Replace content and style
#_(reset! v/content #'make-content)
#_(reset! v/style #'make-style)


;; sample style and content

(defn make-style
  []
  (css [:body
        {:background-color (rgb 140 200 250)}
        {:font-family "helvetica"}
        {:font-size "normal"}]))

(defn button
  [text]
  [:input {:type "submit" :value text}])

(defn make-content
  []
  (hc/html [:table
            [:tr [:th {:colspan "2"} "A form"]]
            [:tr [:td "Foo"] [:td [:input {:type "text"}]]]
            [:tr [:td "Bar" [:td [:input {:type "checkbox"}]]]]
            [:tr [:td {:colspan "2"} (button "OK") (button "Cancel")]]]))

