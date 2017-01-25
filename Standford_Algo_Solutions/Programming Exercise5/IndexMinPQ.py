class IndexMinPQ:

	def __init__(self, maxN):
		self.maxN = maxN
		self.n = 0
		self.keys = [None] * (maxN + 1)
		self.pq = [0] * (maxN + 1)
		self.qp = [-1] * (maxN + 1)

	def isEmpty(self):
		return self.n == 0

	def contains(self, i):
		return self.qp[i] != -1

	def size(self):
		return self.n

	def insert(self, i, key):
		self.n += 1
		self.qp[i] = self.n
		self.pq[self.n] = i
		self.keys[i] = key
		self.swim(self.n)
	
	def minIndex(self):
		return self.pq[1]

	def minKey(self):
		return self.keys[self.pq[1]]

	def delMin(self):
		min_t = self.pq[1]
		self.exch(1, self.n)
		self.n -= 1
		self.sink(1)
		self.qp[min_t] = -1
		self.keys[min_t] = None
		self.pq[self.n+1] = -1
		return min_t

	def keyOf(self, i):
		return self.keys[i]

	def changeKey(self, i, key):
		self.keys[i] = key
		self.swim(self.qp[i])
		self.sink(self.qp[i])


	def decreaseKey(self, i, key):
		self.keys[i] = key
		self.swim(self.qp[i])

	def increaseKey(self, i, key):
		self,keys[i] = key
		self.sink(self.qp[i])

	def delete(self, i):
		index = self.qp[i]
		exch(index, n);
		n -= 1
		self.swim(index)
		self.sink(index)
		self.keys[i] = None
		self.qp[i] = -1

	def exch(self, i, j):
		self.pq[i], self.pq[j] = self.pq[j], self.pq[i]
		self.qp[self.pq[i]] = i
		self.qp[self.pq[j]] = j

	def swim(self, k):
		while k > 1 and self.greater(k/2, k):
			self.exch(k, k/2)
			k /= 2

	def sink(self, k):
		while 2*k <= self.n:
			j = 2 * k
			if j < self.n and self.greater(j, j+1):
				j += 1
			if not self.greater(k, j): break
			self.exch(k, j)
			k = j

	def greater(self, i, j):
		return self.keys[self.pq[i]] > self.keys[self.pq[j]]


