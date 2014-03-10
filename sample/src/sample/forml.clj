(ns sample.forml
  (:require [metam.core :refer :all]
            [clojure.string :refer [lower-case]]))

(declare defaults)

(defn binding?
  [x]
  (or (nil? x) (keyword? x) (vector? x)))

(defmetamodel forml
  (-> (make-hierarchy)
      (derive ::button ::widget)
      (derive ::checkbox ::widget)
      (derive ::label ::widget)
      (derive ::panel ::widget)
      (derive ::textfield ::widget))
  {::button {:text []}
   ::checkbox {:bind [binding?]
               :label [string?]}
   ::label {:class [string?]
            :text [string?]
            :for [string?]}
   ::panel {:elements [(coll (type-of ::widget))]
            :title [string?]}
   ::textfield {:bind [binding?]
                :label [string?]
                :required [boolean?]
                :value []}}
  #'defaults)


(defdefaults defaults forml
  {:default nil
   [::checkbox :bind]         (-> spec :name lower-case keyword)
   [::checkbox :label]        (:name spec)
   [::panel :title]           (:name spec)
   [::textfield :bind]        (-> spec :name lower-case keyword)
   [::textfield :label]       (:name spec)
   [::textfield :required]    false
   [::widget :text]           (:name spec)})

