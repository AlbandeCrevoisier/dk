# Lloyd's algorithm for k-means clustering.
lloydkmeans <- function(X, k = 5, max_iter = 10)
{
	m <- as.integer(nrow(X))
	cl <- array(-1, dim = m)
	cen <- X[sample.int(m, k), , drop = FALSE]
	for (i in 1:max_iter) {
		for (j in 1:m) {
			best <- .Machine$integer.max
			for (l in 1:k) {
				s <- sum((X[j,] - cen[l,]) ** 2)
				if (s < best) {
					cl[j] <- l
					best <- s
				}
			}
			for (l in 1:k) {
				c <- X[cl == l, , drop = FALSE]
				if (nrow(c) == 0) next
				cen[l,] <- (colSums(c) / nrow(c))
			}
		}
	}
	structure(list(cen = cen, cl =  cl))
}
