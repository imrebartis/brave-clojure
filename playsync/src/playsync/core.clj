(ns playsync.core
  (:require [clojure.core.async
             :as a
             :refer [>! <! >!! <!! go chan buffer close! thread
                     alts! alts!! timeout]]))

(def echo-chan (chan))
(go (println (<! echo-chan)))
(>!! echo-chan "ketchup")
; => true
; => ketchup

(def echo-buffer (chan 2))
(>!! echo-buffer "ketchup")
; => true
(>!! echo-buffer "ketchup")
; => true
(>!! echo-buffer "ketchup")
; This blocks because the channel buffer is full

; THREAD: use it instead of a go block when you’re performing a long-running task
; Unlike future, instead of returning an object that you can dereference,
; thread returns a channel
(thread (println (<!! echo-chan)))
(>!! echo-chan "mustard")
; => true
; => mustard

(let [t (thread "chili")]
  (<!! t))
; => "chili"
