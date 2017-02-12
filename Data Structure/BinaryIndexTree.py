class BinaryIndexTree(object):

	def __init__(self, nums):
		self.nums = nums
		self.n = len(nums)
		self.tree = [0] * (self.n+1)
		for i in range(self.n):
			self.build(i, nums[i])

	def build(self, i, val):
		i += 1
		while i <= self.n:
			self.tree[i] += val
			i += (i & -i)

	def update(self, i, val):
		diff = val - self.nums[i]
		self.nums[i] = val
		self.build(i, diff)

	def query(self, i):
		res = 0
		i += 1
		while i > 0:
			res += self.tree[i]
			i -= (i & -i)
		return res

	def range(self, i, j):
		return self.query(j) - self.query(i-1)


a = BinaryIndexTree([1,1,2,4,1,4,0])
print a.tree


