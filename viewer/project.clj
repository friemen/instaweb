(defproject instaweb/viewer "1.0.1"
  :description "Interactive hacking web pages"
  :url "https://github.com/friemen/instaweb"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [ring/ring-core "1.2.1"]
                 [hiccup "1.0.5"]
                 [garden "1.1.5"]
                 [compojure "1.1.6"]
                 [http-kit "2.1.14"]]
  :scm {:name "git"
        :url "https://github.com/friemen/instaweb/viewer"}
  :repositories [["clojars" {:url "https://clojars.org/repo"
                             :creds :gpg}]])
