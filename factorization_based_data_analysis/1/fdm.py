import  numpy as np
from scipy.sparse import rand
import matplotlib.pyplot as plt
import seaborn as sns
sns.set()

I = 10
J = 20
K = 3
MaxIterALS = 50
eta = 0
d = 0.1



Wtrue = rand(I, K, d).todense()
Htrue = rand(K, J, d).todense()

X = Wtrue @ Htrue + np.random.rand(I, J)

Wals = 2 * np.random.rand(I, K)
Hals = 2 * np.random.rand(K, J)
Wgd = Wals
Hgd = Hals

errals = []
errgd = []
for i in range(MaxIterALS):
    Wals = X @ Hals.transpose() @ np.linalg.inv(Hals @ Hals.transpose())
    Hals = (np.linalg.inv(Wals.transpose() @ Wals)) @ Wals.transpose() @ X
    Wgd = Wgd + eta * (X - Wgd @ Hgd) @ Hgd.transpose()
    Hgd = Hgd + eta * Wgd.transpose() @ (X - Wgd @ Hgd)
    errals.append(np.linalg.norm(X - Wals @ Hals))
    errgd.append(np.linalg.norm(X - Wgd @ Hgd))
print(errals)
print(errgd)
plt.plot(errals, label="errals")
plt.plot(errgd, label="errgd")
plt.show()
