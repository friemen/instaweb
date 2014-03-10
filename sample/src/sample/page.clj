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
        {:background-color (rgb 220 200 220)}
        {:font-family "helvetica"}
        {:font-size "normal"}]
       [:label.field
        {:width "100px"
         :float "left"}]
       [:p.heading {:font-weight "bold"
                    :text-align "center"
                    :margin "0px 0px 10px 2px"}]
       [:div.form {:background-color "#eee"
                   :margin "1em"
                   :padding "8px"
                   :border "1px solid grey"
                   :float "left"}]
       [:p.form {:margin "2px"}]))


(defn layout
  [markup]
  [:form markup])

(defn make-content
  []
  (->> (panel "Address" :elements
             [(textfield "Name" :required true)
              (textfield "Street")
              (textfield "Zipcode")
              (textfield "City")
              (checkbox "Faraway" :label "Is it far away?")
              (button "OK") (button "Back")])
       (generate {:city "Foo" :zipcode "43112" :faraway false})
       layout
       hc/html))



