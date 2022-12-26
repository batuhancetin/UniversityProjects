; batuhan cetin
; 2019400117
; compiling: yes
; complete: yes

#lang racket

(provide (all-defined-out))

; 10 points
(define := (lambda (var value) (list var value)))
; 10 points
(define -- (lambda args (list 'let args)))
; 10 points
(define @ (lambda (bindings expr) (append bindings expr)))
; 20 points
(define split_at_delim (lambda (delim args)
                         (remove-elem (foldr (lambda (elem mylist)
                                  (if (eqv? elem delim)
                                      (cons empty mylist)
                                      (cons (cons elem (car mylist)) (cdr mylist))))
                                (list empty) args) '())))
; 30 points
(define parse_expr (lambda (expr)
                     (if (member '+ expr)
                         (cons '+ (map parse_expr (split_at_delim '+ expr)))
                         (if (member '* expr)
                             (cons '* (map parse_expr (split_at_delim '* expr)))
                             (if (member '@ expr)
                                 (@ (car (map parse_expr (split_at_delim '@ expr))) (list (cadr (map parse_expr (split_at_delim '@ expr)))))
                                 (if (member '-- expr)
                                     (apply -- (map parse_expr (split_at_delim '-- expr)))
                                     (if (member ':= expr)
                                         (apply := (map parse_expr (split_at_delim ':= expr)))
                                         (if (pair? (car expr))
                                             (parse_expr (car expr))
                                             (if (equal? (car expr) 'quote)
                                                 (cadr expr)
                                                 (car expr))))))))))
                     
;                     
; 20 points
(define eval_expr (lambda (expr) (eval (parse_expr expr))))


(define remove-elem
  (lambda (mylist elem)
    (cond
      ((null? mylist) '())
      ((eq? (car mylist) elem) (cdr mylist))
       (else 
        (cons (car mylist) (remove-elem (cdr mylist) elem))))
    ))