(ns net.unit8.tower-test
  (:require-macros [cemerick.cljs.test :refer (is deftest with-test runtests testing)])
  (:require
    [cemerick.cljs.test :as t]
    [net.unit8.tower :as tower]))

(def my-tconfig
  { :dev-mode? true
    :fallback-locale :en
    :dictionary
    { :en
      { :example
        { :foo ":en :example/foo text"
          :foo_comment "Hello translator, please do x"
          :bar { :baz ":en :example.bar/baz text"}
          :greeting "Hello %s, how are you?"}
        :missing "<Missing translation: [%1$s %2$s %3$s]>"}
      :en-US
      { :example { :foo ":en-US :example/foo text" }}}
    })

(deftest test-t
  (testing "en-US"
    (is (= (tower/t :en-US my-tconfig :example/foo) ":en-US :example/foo text")))
  (testing "fallback"
    (is (= (tower/t :en-US my-tconfig :example.bar/baz) ":en :example.bar/baz text")))
  (testing "Nest"
    (is (= (tower/t :en my-tconfig :example.bar/baz) ":en :example.bar/baz text"))))

(def remote-tconfig
  { :dev-mode? true
    :fallback-locale :en
    :dictionary "dict.json"
    })

(deftest test-remote-dictonary
  (testing ""
    (with-redefs [js/XMLHttpRequest
                   (fn [] (this-as me
                            (set! (.-responseText me) (.stringify js/JSON (clj->js {:en {:example {:foo "fooo!"}}})))
                            (set! (.-open me) (fn [method url async?] (println url)))
                            (set! (.-send me) (fn [query] nil))
                            me))]
      (is (= (tower/t :en remote-tconfig :example/foo))))))
