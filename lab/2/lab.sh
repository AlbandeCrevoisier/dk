#!/bin/bash
Rscript lab.R
cd sample-audio
for i in 1 2; do
for j in 5 7 15 25; do
../read-write-files-in-C-language/write rec${i}_${j}.out rec${i}_${j}.raw
gzip rec${i}_${j}.raw
done
done
