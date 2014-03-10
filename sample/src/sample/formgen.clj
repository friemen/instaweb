(ns sample.formgen
  (require [metam.core :refer [metatype metatype?]]
           [sample.forml :as f]))

(declare generate*)

(defn- make-id
  [parentid el]
  (if (empty? parentid)
    (:name el)
    (str parentid ":" (:name el))))

(defn- with-label
  [el id markup]
  (if-let [label-text (:label el)]
    [:p
     (generate* id (f/label label-text :for id :class "field"))
     markup]
    markup))

(defn- attrs
  [el id & passthrough-keys]
  (into {:id id}
        (->> passthrough-keys
             (filter #(el %))
             (map #(vector % (el %)) ))))


(defmulti generate*
  (fn [parentid el]
    (metatype el)))

(defmethod generate* :default
  [parentid el]
  "")

(defmethod generate* ::f/button
  [parentid el]
  (let [id (make-id parentid el)]
    [:input {:id id :type "submit" :value (:text el)}]))

(defmethod generate* ::f/checkbox
  [parentid el]
  (let [id (make-id parentid el)]
    (with-label el id
      [:input {:id id :type "checkbox" :value (:value el)}])))

(defmethod generate* ::f/label
  [parentid el]
  (let [id (make-id parentid el)]
    [:label (attrs el id :for :class) (:text el)]))


(defmethod generate* ::f/panel
  [parentid el]
  (let [id (make-id parentid el)]
    [:group {:id id} (->> el
                          :elements
                          (map (partial generate* id)))]))

(defmethod generate* ::f/textfield
  [parentid el]
  (let [id (make-id parentid el)]
    (with-label el id
      [:input {:id id :type "text"}])))


(defn generate
  [el]
  (generate* "" el))
