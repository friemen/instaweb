(ns instaweb.viewer
  "Interactively create web pages"
  (:require [hiccup.core :as hc]
            [hiccup.page :as hp]
            [compojure.core :refer [defroutes GET]]
            [compojure.route :as route]
            [org.httpkit.server :as httpkit]
            [ring.middleware.resource :refer [wrap-resource]]
            [garden.core :refer [css]]
            [garden.color :refer [rgb]]
            [clojure.data.json :as json]))



(defn default-content
  []
  (hc/html [:div
            [:p
             "Welcome to interactive web development!" [:br]
             "To replace the default content/style with your input enter in the REPL:"]
            [:pre "(reset! v/content #'your-content-fn)"]
            [:pre "(reset! v/style #'your-style-fn)"]
            [:p "Whenever you reevaluate your functions, the browser will show the new content."]]))

(def content "Points to a var pointing to a content fn" (atom #'default-content))

(defn default-style []
  (css [:body
        {:background-color (rgb 200 130 80)}
        {:font-family "helvetica"}
        {:font-size "large"}]))

(def style "Points to a var pointing to a style fn" (atom #'default-style))



(defn js
  [script-url]
  [:script {:src script-url
            :charset "utf-8"
            :type "text/javascript"}])


(defn interactive-page
  []
  {:status 200
   :body (hp/html5 [:head
                    [:title "Interactive mode"]
                    (js "/script/jquery-1.8.3.min.js") ;; downloaded from http://code.jquery.com/jquery-1.8.3.min.js
                    (js "/script/reloader.js")
                    [:style {:id "mainStyle"} ((var-get @style))]]
                   [:body [:div {:id "content"} ((var-get @content))]])})


(defn production-page
  []
  {:status 200
   :body (hp/html5 [:head
                    [:title "Production mode"]
                    [:style {:id "mainStyle"} ((var-get @style))]]
                   [:body ((var-get @content))])})



(defroutes app
  ; load page without reloading
  (GET "/" [] (production-page))
  ; load page with embedded Javascript reloading
  (GET "/viewer" [] (interactive-page))
  ; support AJAX calls
  (GET "/refresh" [] {:status 200
                      :content-type "application/json"
                      :body (json/write-str {:content ((var-get @content))
                                             :style ((var-get @style))})})
  (route/resources "/")
  (route/not-found "Unknown resource."))


(def port (atom 3000))

(defn run!
  []
  (println (str "Open in your browser http://localhost:" @port "/viewer"))
  (httpkit/run-server #'app {:port @port}))

(def shutdown! (run!))

(defn reset-port!
  [n]
  (shutdown!)
  (reset! port n)
  (alter-var-root #'shutdown! run!))

