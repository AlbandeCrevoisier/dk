# R lab #

In this R lab, I apply Lloyd's k-means on audio files I recorded, then switch
each value with its centroid one, then compress it and compare the compressed
size with the one of the original size.

Compression tool used: gzip.
```
rec1.raw.gz: 129K
rec2.raw.gz: 113K
```

__Note__

I modified the `write` C function to write the output in `argv[2]` to help
automation, which explains its different use in the `lab.sh` script.

At the time, the k-means take hours to run on my 12 years old thinkpad, so I do
not yet have the outputs. Everything has been tested on 20 lines long test
files and runs.

