"""
	TASK:

	implement the "Median Maintenance" algorithm
	(covered in the week 5 lecture heap applications)

	INPUT:

	median.txt contains a list of integers from 1 to 10000 (unsorted)
	treat it as a stream of numbers, arriving one by one.
	the kth median mk is defined as the median of the numbers x1,...,xk

	OUTPUT:

	output the sum of these 10000 medians modulo 10000

	SOLUTION:

	use maxPQ & minPQ

	maxPQ store left half of the data
	minPQ store right half of the data

	when a new item come in :
	if item is smaller than the root of maxPQ, then insert it to the maxPQ

	make sure the size of maxPQ & minPQ are balance
"""
from heapq import *

class Median:

	def __init__(self):
		self.maxPQ = []
		self.minPQ = []
		self.size = 0

	def add(self, item):
		# add item
		if self.size == 0:
			self.maxPQ.append(-item)
		elif self.size == 1:
			a = -self.maxPQ.pop()
			b = item
			heappush(self.maxPQ, -min(a,b))
			heappush(self.minPQ, max(a,b))
		else:
			root = -heappop(self.maxPQ)
			if item < root:
				heappush(self.maxPQ, -item)
			else:
				heappush(self.minPQ, item)
			heappush(self.maxPQ, -root)
		self.size += 1

		# balance the minPQ and maxPQ
		while abs(len(self.maxPQ)-len(self.minPQ)) > 1:
			if len(self.maxPQ) > len(self.minPQ):
				p = -heappop(self.maxPQ)
				heappush(self.minPQ, p)
			else:
				p = heappop(self.minPQ)
				heappush(self.maxPQ, -p)

		# get median
		if len(self.maxPQ) == len(self.minPQ):
			return -self.maxPQ[0]
			# return (-self.maxPQ[0] + self.minPQ[0]) / 2.0
		else:
			if len(self.maxPQ) > len(self.minPQ):
				return -self.maxPQ[0]
			else:
				return self.minPQ[0]

def main():
	f = open('median.txt', 'r')
	lines = f.readlines()
	arr = [int(line.strip()) for line in lines]
	median = Median()
	print len(arr)
	ret = 0
	for num in arr:
		ret += median.add(num)
		ret %= 10000
	print ret


main()

####     answer: 1213