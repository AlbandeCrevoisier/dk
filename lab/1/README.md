# Recommendation system #

This lab is an implementation of a recommandation system in java using Hadoop MapReduce.

The test data is in the in/ directory and the result is output in out/.

The data consists in text files in which a line represents a cart, i.e. a set of items bought together;
and the output lists how many times each item was bought with a given item:

Output for the given example in in/ :
```
item1	{item4=1, item2=1, item3=1}
item2	{item4=2, item5=1, item3=1, item1=1}
item3	{item2=1, item1=1}
item4	{item5=1, item2=2, item1=1}
item5	{item4=1, item2=1}
```

Make commands provided:
* `make` : compile and compress
* `make run` : run code on example in/
* `make clean` : clean everything

Note: `WordCount.java` is an example of how to use Apache Hadoop in java, the file for the lab is `RecoSys.java`.
