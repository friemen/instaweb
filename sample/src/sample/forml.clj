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
   ::column {:bind [binding?]
             :text [string?]}
   ::label {:class [string?]
            :text [string?]
            :for [string?]}
   ::panel {:elements [(coll (type-of ::widget))]
            :title [string?]}
   ::table {:bind [binding?]
            :columns [(coll (type-of ::column))]
            :title [string?]}
   ::textfield {:bind [binding?]
                :label [string?]
                :required [boolean?]
                :value []}}
  #'defaults)

(defn keyword-from-name
  [spec]
  (-> spec :name lower-case keyword))


(defdefaults defaults forml
  {:default nil
   [::checkbox :bind]         (keyword-from-name spec)
   [::checkbox :label]        (:name spec)
   [::column :bind ]          (keyword-from-name spec)
   [::column :text]           (:name spec)
   [::panel :title]           (:name spec)
   [::table :bind]            (keyword-from-name spec)
   [::table :title]           (:name spec)
   [::textfield :bind]        (keyword-from-name spec)
   [::textfield :label]       (:name spec)
   [::textfield :required]    false
   [::widget :text]           (:name spec)})

