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
H = FILTER G BY (genres matches '.*Documentary.*');
I = JOIN A BY movieId, H BY movieId;
J = GROUP I ALL;
K = FOREACH J GENERATE AVG(I.A::rating);
EXPLAIN K;

L = LOAD '/home/alban/telecom/dk/architectures/lab/3/ml-20m/tags.csv'
	USING PigStorage(',')
	AS (userId:int, movieId:int, tag:chararray, timestamp:long);
M = FILTER G BY (genres matches '.*Action.*');
N = JOIN L BY movieId, M BY movieId;
O = GROUP N BY movieId;
P = FOREACH O GENERATE COUNT(N);
EXPLAIN P;
