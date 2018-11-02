import numpy as np
import pandas as pd
from sklearn.ensemble import RandomForestClassifier
import seaborn as sns

train = pd.read_csv('train-set.csv')
train_X = train.drop(['Id', 'Cover_Type'], axis = 1).values
train_Y = train.Cover_Type.values
test = pd.read_csv('test-set.csv')
test_X = test.drop(['Id'], axis = 1).values

rf = RandomForestClassifier(n_estimators=100)
rf.fit(train_X, train_Y)

pred = rf.predict(test_X)

pd.DataFrame({'Id': test.Id.values, 'Cover_Type': pred}).to_csv('pred.csv', index=False)
