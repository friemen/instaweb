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

(def content-div {:background-color "#eee"
                  :margin "4px"
                  :padding "8px"
                  :border "1px solid grey"
                  :float "left"
                  :box-shadow "4px 4px 4px #888888"})

(defn make-style
  []
  (css [:body
        {:background-color (rgb 220 200 220)}
        {:font-family "helvetica"}
        {:font-size "normal"}]
       [:table {:background-color "#eee"
                :border-collapse "collapse"}]
       [:th {:background-color "#ccc"
             :border "thin solid black"}]
       [:td {:border "thin solid black"
             :padding-left "1em"
             :padding-right "1em"}]
       [:div.table content-div]
       [:div.stacked {:clear "both"}]
       [:label.field
        {:width "100px"
         :float "left"}]
       [:p.heading {:font-weight "bold"
                    :margin "0px 0px 10px 2px"}]
       [:div.form content-div]
       [:p.form {:margin "2px"}]))


;; sample data

(def data {:selected {:name "Donald Duck"
                      :street "Upperstreet 42"
                      :city "Duckberg"
                      :zipcode "43112"
                      :faraway false}
           :addresses [{:name "Mickey Mouse"
                        :street "Upperstreet 42"
                        :city "Duckberg"
                        :zipcode "43112"
                        :faraway false}
                       {:name "Donald Duck"
                        :street "Upperstreet 42"
                        :city "Duckberg"
                        :zipcode "43112"
                        :faraway false}]})

;; sample form and table
  
(defn layout
  [& parts]
  [:form (map #(vector :div {:class "stacked"} %) parts)])


(defn address-panel
  []
  (panel "Address" :elements
         [(textfield "Name" :required true)
          (textfield "Street")
          (textfield "Zipcode")
          (textfield "City")
          (checkbox "Faraway" :label "Is it far away?")
          (button "OK") (button "Back")]))

(defn addresses-table
  []
  (table "Addresses" :columns
         [(column "Name")
          (column "Street")
          (column "Zipcode")
          (column "City")]))

(defn make-content
  []
  (hc/html
   (layout (generate (:selected data) (address-panel))
           (generate data (addresses-table)))))








