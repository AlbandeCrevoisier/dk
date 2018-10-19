# Apache Pig lab #

1.
Map: Project the tuple on userId and bag all fields.
Combine: Project on userId and combine the bags.
Reduce: Project on userId, combine the bags, count and filter them.

2.
Map: Project the tuple on movieId and bag allfields.
Combine: Project on movieId and combine the bags.
Reduce: Project on movieId and combine the bags.

3.
Map: rearrange fields and filter 'Documentary' movies.
Reduce: Join ratings and movies.
Map: Bag all.
Combine: Compute local average.
Reduce: Compute general average.
