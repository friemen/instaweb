(ns sample.forml
  (:require [metam.core :refer :all]))

(declare defaults)

(defmetamodel forml
  (-> (make-hierarchy)
      (derive ::button ::widget)
      (derive ::checkbox ::widget)
      (derive ::label ::widget)
      (derive ::panel ::widget)
      (derive ::textfield ::widget))
  {::button {:text []}
   ::checkbox {:label [string?]
               :value []}
   ::label {:class [string?]
            :text [string?]
            :for [string?]}
   ::panel {:elements [(coll (type-of ::widget))]}
   ::textfield {:label [string?]
                :value []}}
  #'defaults)


(defdefaults defaults forml
  {:default nil
   [::widget :text]           (:name spec)
   [::checkbox :label]        (:name spec)
   [::textfield :label]       (:name spec)})

