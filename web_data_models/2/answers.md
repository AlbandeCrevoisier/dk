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

Not every document that is valid with regard to the DTD can be generated by
the provided grammar: Patinte and Doctor should not impose any order.

A counter-example can easily be built: a doctor followed by a patient.

### Question 2 ###

Every document generated by the grammar is valid with regard to the DTD, as
the former defines a special case of the latter.

### Question 3 ###

The grammar can't be expressed as a DTD, as we cannot seperate the person case
by stating wether it has a phone number.

### Question 4 ###

It can be expressed as a XML Schema so. See grammar.xsd.
