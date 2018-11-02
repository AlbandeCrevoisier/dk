import numpy as np
import pandas as pd
from sklearn.ensemble import RandomForestClassifier
import seaborn as sns

train = pd.read_csv("train-set.csv")
test = pd.read_csv("test-set.csv")

