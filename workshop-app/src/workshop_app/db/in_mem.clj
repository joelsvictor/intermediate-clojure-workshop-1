(ns workshop-app.db.in-mem
  (:refer-clojure :rename {update cc-update
                           read   cc-read}))


(def conn (atom {}))

(defn create
  "Create an entry for k in our given in memory datastore."
  [c k v]
  (swap! c assoc k v))


(defn update
  "Update the entry for k to value v or add it if it doesn't exist in our in memory datastore."
  [c k v]
  (swap! c assoc k v))


(defn delete
  "Delete the entry for k in our in memory datastore."
  [c k]
  (swap! c dissoc k))


(defn read
  "Read the entry for k in our in memory datastore."
  [c k]
  (get @c k))