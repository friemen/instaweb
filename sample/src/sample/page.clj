(ns sample.page
  "Interactively create web pages"
  (:require [hiccup.core :as hc]
            [hiccup.page :as hp]
            [garden.core :refer [css]]
            [garden.color :refer [rgb]]
            [sample.forml :refer :all]
            [sample.formgen :refer [generate]]))


;; Load this ns into a REPL
;; then try
#_(ns sample.page)
#_(require '[instaweb.viewer :as v])

;; Switch to your browser and load http://localhost:3000/viewer

;; Replace content and style
#_(reset! v/content #'make-content)
#_(reset! v/style #'make-style)


;; Change make-content and/or make-style and reevaluate the
;; changed function


;; sample style and content

(defn make-style
  []
  (css [:body
        {:background-color (rgb 140 200 250)}
        {:font-family "helvetica"}
        {:font-size "normal"}]
       [:label.field
        {:width "100px"
         :float "left"}]
       [:group :p {:clear "both"
                   :height "1em"}]))


(defn make-content
  []
  (-> (panel "Data" :elements
             [(textfield "Name" :required true)
              (textfield "Street")
              (textfield "City")
              (checkbox "Adult" :label "Adult?")
              (button "OK") (button "Back")])
      generate
      hc/html))


