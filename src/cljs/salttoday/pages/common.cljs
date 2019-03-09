(ns salttoday.pages.common)

(defn jumbotron
  "Big name at the top?"
  [title]
  [:div {:class "jumbotron text-center"}
   [:h1 title]])

(defn content [x]
  [:div {:class "container"}
   x])

(defn nav-link [uri title current-page page icon]
  [:div.navigation-link {:class (when (= page current-page) "active")} [:a {:href uri} title]  [:span {:class icon}]])

(defn make-navbar
  [current-page]
  [:div.column {:style {:flex 15}}
   [:div.row.navigation
    [nav-link "#/" "Home " current-page :home "icon-home3"]]
   [:div.row.navigation
    [nav-link "#/users" "Users " current-page :users "icon-group"]]])

(defn make-content
  [current-page content]
  [:div.column {:style {:flex 70}}
   [:div.column.justify-center.main-content {:style {:flex-grow 1}}
    content]
   [:div.row.justify-center
    [:img.footer-pile {:src "/img/footer-pile.png"}]]])

(defn make-right-offset []
  [:div.column {:style {:flex 15}}])
