# tower-cljs

Clojurescript i18n & L10n library, compatible with https://github.com/ptaoussanis/tower

## Getting sarted

### Dependencies

Add the necesssary dependency to your [Leiningen](http://leiningen.org/) `project.clj` and `require` the library in your ns:

```clojure
[net.unit8/tower-cljs "0.1.0"] ;project.clj
(ns my-app (:use [net.unit8.tower :only [t]]))
```

### Translation

The `t` fn handles translations. You give it a config map witch includes your dictionary, and you're ready to go:

```clojure
(def my-tconfig
  {:dev-mode? true
   :fallback-locale :en
   :dictionary
   {:en         {:example {:foo         ":en :example/foo text"
                           :foo_comment "Hello translator, please do x"
                           :bar {:baz ":en :example.bar/baz text"}
                           :greeting "Hello %s, how are you?"
                           :inline-markdown "<tag>**strong**</tag>"
                           :block-markdown* "<tag>**strong**</tag>"
                           :with-exclaim!   "<tag>**strong**</tag>"
                           :greeting-alias :example/greeting
                           :baz-alias      :example.bar/baz}
                 :missing  "<Missing translation: [%1$s %2$s %3$s]>"}
    :en-US      {:example {:foo ":en-US :example/foo text"}}
    :en-US-var1 {:example {:foo ":en-US-var1 :example/foo text"}}}})

(t :en-US my-tconfig :example/foo) => ":en-US :example/foo text"
(t :en    my-tconfig :example/foo) => ":en :example/foo text"
(t :en    my-tconfig :example/greeting "Steve") => "Hello Steve, how are you?"
```

## License

Copyright (C) 2013 kawasima

Distributed under the Eclipse Public License, the same as Clojure.


