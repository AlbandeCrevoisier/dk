if (!exists("roundseries", mode = "function")) source("roundseries.R")

lab <- function()
{
	for (i in c(5, 7, 15, 25)) {
		roundseries("sample-audio/rec1.txt",
			paste("sample-audio/rec1_", i, ".txt", sep=""),
			i)
		roundseries("sample-audio/rec2.txt",
			paste("sample-audio/rec2_", i, ".txt", sep=""),
			i)
	}
}
