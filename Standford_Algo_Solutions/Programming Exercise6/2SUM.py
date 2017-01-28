"""
	TASK:

	implement a variant of the 2-SUM algorithm 
	( Week 6 lecture on hash table applications )

	compute the number of target values t in the interval [-10000, 10000]
	such that there are distinct numbers x,y in the input file that satisfy x + y = t.
	

	INPUT:

	2sum.txt
	contains 1 million integers, both positive and negative

	OUTPUT:

	numbers of target values t
"""

"""
	NAIVE SOLUTION is O(n*n)
	Current SOLUTION is O(n*k) k = interval size  here k = 20000
	50 times smaller.


"""

def twoSum(ht, arr, target):
	for x in arr:
		y = target - x
		if y in ht and x != y:
			return (x, y)
	return None


def main():
	# read data
	arr = []
	lines = open('2sum.txt', 'r').readlines()
	for line in lines:
		arr.append(int(line.strip()))
	hashtable = dict()
	for x in arr:
		hashtable[x] = True
	cnt = 0
	for t in range(-10000, 10000+1):
		if(twoSum(hashtable, arr, t)):
			cnt += 1
	print cnt

main()
