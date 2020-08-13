(defproject alilu "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [aleph "0.4.6"]
                ;  [org.apache.logging.log4j/log4j "2.13.3" :extension "pom"
                ;                   :exclusions [javax.mail/mail
                ;                               javax.jms/jms
                ;                               com.sun.jdmk/jmxtools
                ;                               com.sun.jmx/jmxri]]
                                              ]
  :main ^:skip-aot alilu.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})

  ; [org.apache.logging.log4j/log4j "2.13.3" :extension "pom"]