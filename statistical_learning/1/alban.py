import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
sns.set()

data  = pd.read_csv("data")

plt.plot(data)
plt.show()

c = np.polyfit(data['sample size'], data['shattering coef'], 10)

def p(c, x):
    ret = 0
    for i in range(10):
        ret += c[i] * (x ** 10-i)
    return ret

plt.plot([p(c, i) for i in range(100)])
plt.show()
