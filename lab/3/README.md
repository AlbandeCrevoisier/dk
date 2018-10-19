# Apache Pig lab #

1.
Map: Project the tuple on userId and bag all fields.
Combine: Project on userId and combine the bags.
Reduce: Project on userId, combine the bags, count and filter them.

2.
Map: Project the tuple on movieId and bag all fields.
Combine: Project on movieId and count the bags.
Reduce: Project on movieId and count the bags.

3.
Map: Rearrange fields and filter 'Documentary' movies.
Reduce: Join ratings and movies.
Map: Bag all.
Combine: Compute local average.
Reduce: Compute general average.
The query execution was optimised by filtering before the join, compensating for
my lack of experience.

4.
Map: Rearrange fields and filter 'Action' movies.
Reduce: Join tags and movies.
Map: Project the tuple on movieId and bag all fields.
Combine: Project on movieId and count the bags.
Reduce: Project on movieId and count the bags.
