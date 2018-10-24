# Execute Lloyd's k-means on a serie and replace each value by its prototype.
# The serie is read from in and the result is written in out.

if (!exists("lloydkmenas", mode = "function")) source("lloydkmeans.R")

roundseries <- function(input, output, k)
{
	X <- read.table(input)
	km <- lloydkmeans(X, k)
	for (i in 1:nrow(km$cen))
		X[km$cl == i,] <- km$cen[i, , drop = FALSE]
	# Cannot write directly since the data is read as list, not numeric type.
	# Instead, write an empty line to clear file, then append each row.
	write(x='', output)
	lapply(X, write, output, append=TRUE, ncolumns=ncol(X))
}
