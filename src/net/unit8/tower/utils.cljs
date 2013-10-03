(ns net.unit8.tower.utils
  (:require
    [clojure.string :as str]))

(defn leaf-nodes
  "Takes a nested map and squashes it into a sequence of paths to leaf nodes.
  Based on 'flatten-tree' by James Reaves on Google Groups."
  [m]
  (if (map? m)
    (for [[k v] m
          w (leaf-nodes v)]
      (cons k w))
    (list (list m))))

(defn html-breaks [s] (str/replace s #"(\r?\n|\r)" "<br/>"))
(defn html-escape [s]
  (-> (str s)
      (str/replace "&"  "&amp;") ; First!
      (str/replace "<"  "&lt;")
      (str/replace ">"  "&gt;")
      ;;(str/replace "'"  "&#39;") ; NOT &apos;
      (str/replace "\"" "&quot;")))

(defn md-to-html-string [s] s)

(defn markdown
  [s & [{:keys [inline? auto-links?] :as opts}]]
  ;; TODO cond-> with Clojure 1.5 dep
  (let [s (str s)
        s (if-not auto-links? s (str/replace s #"https?://([\w/\.-]+)" "[$1]($0)"))
        s (if-not inline?     s (str/replace s #"(\r?\n|\r)+" " "))
        s (apply md-to-html-string s (reduce concat opts))
        s (if-not inline?     s (str/replace s #"^<p>(.*?)</p>$" "$1"))]
    s))

(defn fq-name "Like `name` but includes namespace in string when present."
  [x] (if (string? x) x
          (let [n (name x)]
            (if-let [ns (namespace x)] (str ns "/" n) n))))

(defn explode-keyword [k] (str/split (fq-name k) #"[\./]"))

(defn merge-keywords [ks & [as-ns?]]
  (let [parts (->> ks (filterv identity) (mapv explode-keyword) (reduce into []))]
    (when-not (empty? parts)
      (if as-ns? ; Don't terminate with /
        (keyword (str/join "." parts))
        (let [ppop (pop parts)]
          (keyword (when-not (empty? ppop) (str/join "." ppop))
                   (peek parts)))))))

(defn merge-deep-with ; From clojure.contrib.map-utils
  "Like `merge-with` but merges maps recursively, applying the given fn
  only when there's a non-map at a particular level.

  (merge-deep-with + {:a {:b {:c 1 :d {:x 1 :y 2}} :e 3} :f 4}
                    {:a {:b {:c 2 :d {:z 9} :z 3} :e 100}})
  => {:a {:b {:z 3, :c 3, :d {:z 9, :x 1, :y 2}}, :e 103}, :f 4}"
  [f & maps]
  (apply
   (fn m [& maps]
     (if (every? map? maps)
       (apply merge-with m maps)
       (apply f maps)))
   maps))

;; Used by: Timbre, Tower
(def merge-deep (partial merge-deep-with (fn [x y] y)))



