import  numpy as np
import matplotlib.pyplot as plt
import seaborn as sns
sns.set()


I, J, K = 10, 20, 3
niterALS, niterGD, niterMUR = 20, 50, 100
eta = 0.01

Wtrue = np.random.rand(I, K)
Htrue = np.random.rand(K, J)
X = Wtrue @ Htrue + np.random.rand(I, J)


def als(X):
    W = 2 * np.random.rand(I, K)
    H = 2 * np.random.rand(K, J)
    errals = []
    for _ in range(niterALS):
        W = X @ H.T @ np.linalg.inv(H @ H.T)
        H = (np.linalg.inv(W.T @ W)) @ W.T @ X
        errals.append(0.5 * (np.linalg.norm(X - W @ H) ** 2))
    plt.plot(errals, label="Error Alternating Least Square")
    plt.show()


def gd(X):
    W = 2 * np.random.rand(I, K)
    H = 2 * np.random.rand(K, J)
    errgd = []
    for _ in range(niterGD):
        W = W + eta * (X - W @ H) @ H.T
        H = H + eta * W.T @ (X - W @ H)
        errgd.append(0.5 * (np.linalg.norm(X - W @ H) ** 2))
    plt.plot(errgd, label="Error Gradient Descent")
    plt.show()


def mur(X):
    W = 2 * np.random.rand(I, K)
    H = 2 * np.random.rand(K, J)
    errmur = []
    for _ in range(niterMUR):
        a = X / (W @ H)
        a = a @ H.T
        b = np.ones((I, J)) @ H.T
        a = a / b
        a = W * a
        W = W * ((X / (W @ H)) @ H.T / (np.ones((I, J)) @ H.T))
        H = H * (W.T @ (X / (W @ H)) / (W.T @ np.ones((I, J))))
        errmur.append(np.sum(X * np.log(X / (W @ H)) - X + W@H))
    plt.plot(errmur, label="Error Multiplicative Update Rule")
    plt.show()
