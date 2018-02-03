(ns wuffer.core
  (:use [overtone.live]))

(defn wsine
  []
  (demo 10 (* (sin-osc 0.5) (saw 440))))

(defn wmixer
  []
  (demo 7 (lpf (mix (saw [50 (line 100 1600 5) 101 100.5]))
               (lin-lin (lf-tri (line 2 20 5)) -1 1 400 4000))))


(defsynth red-frik-333317729073381377
  []
  (out 0
       (/ (mix ;; docs suggest this is the same as 'mean'
           (for [i (range 50)]
             (ringz:ar (* (blip:ar (+ (* (> (lf-saw:ar (/ (+ i 1) [3 4]))
                                            (+ (lf-saw:ar (/ (+ i 1) 8)) 1))
                                         25)
                                      50)
                                   (+ i [2 3]))
                          (lf-saw:ar (/ (+ i 1) 50)
                                     (/ i 25)))
                       (* (+ i 1) 99)
                       0.1)))
          5)))

(defsynth red-frik-329680702205468674
  []
  (for [k (range 2)]
    (out k
         (/ (mix
             (for [i (range 9)]
               (let [j (sin-osc:ar (/ (+ i 1) 99))]
                 (leak-dc:ar
                  (pan2:ar (* (sin-osc:ar (+ (/ 1 9) i) 0)
                              j)
                           (* (sin-osc:ar (* (+ i 1 k) (+ (* (ceil j) 39) 39))
                                              (sin-osc:ar (+ k 2)))
                              j))))))
            3))))
(comment
  (red-frik-329680702205468674)
  (kill red-frik-329680702205468674))

(defsynth red-frik-329311535723839489
  []
  (out 0
       (rlpf:ar
        (distort
         (leak-dc:ar
          (lf-tri:ar
           (* (leak-dc:ar
               (sum
                (for [x (range 1 9)]
                  (pan2:ar
                   (> (lf-tri:ar (/ 1 x) (/ x 3)) 0.3333)
                   (lf-tri:ar (/ 666 x))))))
              999))))
        3e3)))

(comment
  (red-frik-329311535723839489)
  (kill red-frik-329311535723839489))


(defsynth red-frik-356151997256306691
  []
  (out 0
       (let [b 600
             l (local-buf b 9)
             _ (clear-buf l)
             _ (buf-wr:ar
                (lf-tri:ar
                 (for [c (* (range 3 11) 3.5)] c))
                l
                (* (lf-tri:ar
                    (for [c (* (range 3 11) 3.5)] (/ 9 c))
                    (for [c (* (range 3 11) 3.5)] (/ c 99)))
                   b))]
         (* 3 (splay:ar (play-buf 9 l :loop 1))))))

(comment
  (red-frik-356151997256306691)
  (kill red-frik-356151997256306691))

(defsynth red-frik-361960894818811905
  []
  (out 0
       (g-verb:ar
        (splay:ar
         (for [b (range 1 16 2.075)]
           (/ (tanh
               (sin-osc:ar
                (/ b)
                (* 3 (sin-osc:ar
                      (* b (lag (duty:ar
                                 b
                                 0
                                 (midicps (dseq (+ b 23) INF)))
                                2))))))
              5)))
        90)))

(comment
  (red-frik-361960894818811905)
  (kill red-frik-361960894818811905))

(comment
  (demo 10 (example dbrown :rand-walk)))
