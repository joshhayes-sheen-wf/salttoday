(ns salttoday.pages.home
  (:require [ajax.core :refer [GET PUT]]
            [reagent.core :as r]
            [salttoday.common :refer [display-comment]]
            [salttoday.pages.common :refer [content make-layout jumbotron]]
            ))

(def state
  (r/atom {}))

(defn top-comments-handler [response]
  (js/console.log response)
  (reset! state response))

(GET "/top-comments"
     {:headers {"Accept" "application/transit"}
      :handler top-comments-handler})

(defn home-page []
  (make-layout :home
               [:div

                [:div
                 [:div [:div.general-heading "Today"] [:div.general-line-break]
                  [:div.comments-type-header.container
                   [:div
                    [:div.liked-heading "Liked"][:div.line-break-positive]
                    [:div (display-comment (get @state "daily-positive") "positive")]]]

                  [:div.comments-type-header.container
                   [:div
                    [:div.disliked-heading "Disliked"][:div.line-break-negative]
                    [:div (display-comment (get @state "daily-negative") "negative")]]]
                  ]
                 ]

                [:div
                 [:div
                  [:div.general-heading "All Time"] [:div.general-line-break]
                  [:div.panel-body
                   [:div.comments-type-header.container
                    [:div.liked-heading "Liked"][:div.line-break-positive]
                    (for [comment (get @state "all-time-positives")]
                      (display-comment comment "positive"))]
                   [:div.comments-type-header.container
                    [:div.disliked-heading "Disliked"][:div.line-break-negative]
                    (for [comment (get @state "all-time-negatives")]
                      (display-comment comment "negative"))]]]]]))