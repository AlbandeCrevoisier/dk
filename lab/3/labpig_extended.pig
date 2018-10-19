A = LOAD '/home/alban/telecom/dk/architectures/lab/3/ml-20m/ratings.csv'
	USING PigStorage(',')
	AS (userId:int, movieId:int, rating:float, timestamp:long);
ILLUSTRATE A;
B = FILTER A BY rating>0.5 AND rating<5.0;
ILLUSTRATE B;
C = GROUP B BY userId;
ILLUSTRATE C;
D = FOREACH C GENERATE group AS userId, AVG(B.rating) AS avgRating;
ILLUSTRATE D;
E = ORDER D BY avgRating DESC;
DUMP E;
EXPLAIN E;
