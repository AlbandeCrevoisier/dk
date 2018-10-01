# Practical lab 2 #

## 1 DTD Validation ##

### Question 1 ###

See tree.xml & tree.dtd.

### Question 2 ###

Regular grammar associated with the tree:
```
A -> a [B, E]
B -> b [[C, F]+]
C -> c [D?]
D -> d []
E -> e [D, C, C]
F -> f []
```

### Question 3 ###

Let's build the associated bottom-up tree automaton:
```
delta = {
a(q_B, q_E) -> q_A
b((q_C, q_F)+) -> q_B
c(q_D?) -> q_C
d() -> q_D
e(q_D, q_C, q_C) -> q_E
f() -> q_F
}
sigma = {a, b, c, d, e, f}
Q = {q_A, q_B, q_C, q_E, q_F}
F={q_A}
```
### Question 4 ###

see nontrivial.xml

## 2 DTD and RTG ##

### Question1 ###

