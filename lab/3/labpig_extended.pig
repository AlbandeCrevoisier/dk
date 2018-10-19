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

G = LOAD '/home/alban/telecom/dk/architectures/lab/3/ml-20m/movies.csv'
	USING PigStorage(',')
	AS (movieId:int, title:chararray, genres:chararray);
H = JOIN A BY movieId, G BY movieId;
I = FILTER H BY (genres matches '.*Documentary.*');
J = GROUP I ALL;
K = FOREACH J GENERATE AVG(J.A::rating);
ILLUSTRATE K;
EXPLAIN K;

L = LOAD '/home/alban/telecom/dk/architectures/lab/3/ml-20m/tags.csv'
	USING PigStorage(',')
	AS (userId:int, movieId:int, tag:chararray, timestamp:long);
