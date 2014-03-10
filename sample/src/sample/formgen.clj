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
    [:p {:class "form"}
     (generate* id {} (f/label (if (:required el)
                                 (str label-text "*")
                                 label-text)
                               :for id
                               :class "field"))
     markup]
    markup))

(defn- attrs
  [el id & passthrough-keys]
  (into {:id id}
        (->> passthrough-keys
             (filter #(el %))
             (map #(vector % (el %)) ))))

(defn- as-vec
  [x]
  (cond
   (vector? x) x
   (coll? x) (vec x)
   :else (vector x)))

(defn- value
  [data el]
  (get-in data (as-vec (:bind el))))


(defmulti generate*
  (fn [parentid data el]
    (metatype el)))


(defmethod generate* :default
  [parentid data el]
  "")


(defmethod generate* ::f/button
  [parentid data el]
  (let [id (make-id parentid el)]
    [:input {:id id :type "submit" :value (:text el)}]))


(defmethod generate* ::f/checkbox
  [parentid data el]
  (let [id (make-id parentid el)]
    (with-label el id
      [:input {:id id :type "checkbox" :checked (value data el)}])))


(defmethod generate* ::f/label
  [parentid data el]
  (let [id (make-id parentid el)]
    [:label (attrs el id :for :class) (:text el)]))


(defmethod generate* ::f/panel
  [parentid data el]
  (let [id (make-id parentid el)]
    [:div
     {:id id :class "form"}
     [:p {:class "heading"} (:title el)]
     (->> el
          :elements
          (map (partial generate* id data)))]))


(defn column-header
  [col]
  (vector :th (:text col)))

(defn table-row
  [cols item]
  (vector :tr (map #(vector :td (value item %)) cols)))

(defmethod generate* ::f/table
  [parentid data el]
  (let [cols (:columns el)
        items (value data el)]
    [:div {:class "table"}
     [:p {:class "heading"} (:title el)]
     [:table
      [:tr (map column-header cols)]
      (map (partial table-row cols) items)]]))

(defmethod generate* ::f/textfield
  [parentid data el]
  (let [id (make-id parentid el)]
    (with-label el id
      [:input {:id id :type "text" :value (value data el)}])))


(defn generate
  ([el]
     (generate* "" {} el))
  ([data el]
     (generate* "" data el)))
