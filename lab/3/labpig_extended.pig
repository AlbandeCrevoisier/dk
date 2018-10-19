A = LOAD '/home/alban/telecom/dk/architectures/lab/3/ml-20m/ratings.csv'
	USING PigStorage(',')
	AS (userId:int, movieId:int, rating:float, timestamp:long);
B = GROUP A BY userId;
C = FOREACH B GENERATE group AS userId, COUNT(A) AS cnt;
D = FILTER C BY cnt > 100;
EXPLAIN D;

E = GROUP A BY movieId;
F = FOREACH E GENERATE COUNT(A);
EXPLAIN F;

Y = LOAD '/home/alban/telecom/dk/architectures/lab/3/ml-20m/movies.csv'
	USING PigStorage(',')
	AS (movieID:int, title:chararray, genres:chararray);
Z = LOAD '/home/alban/telecom/dk/architectures/lab/3/ml-20m/tags.csv'
	USING PigStorage(',')
	AS (userId:int, movieId:int, tag:chararray, timestamp:long);
