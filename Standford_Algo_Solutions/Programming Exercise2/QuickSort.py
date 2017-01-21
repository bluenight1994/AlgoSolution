# compute the total number of comparisons used to sort by quick sort

def readInt():
	file = open('QuickSort.txt', 'r')
	lines = file.readlines()
	arr = []
	for line in lines:
		line = line.strip()
		arr.append(int(line))
	return arr

# my implementation of quick sort

def quickSort(A, start, end):
	if end - start < 1: return
	q = partition(A, start, end)
	quickSort(A, start, q-1)
	quickSort(A, q+1, end)

"""
	choose the last element of array as pivot
	# of comparisons is 164123
"""

def partition(A, start, end):
	global cnt
	cnt += end - start
	pivot = A[end]
	j = start
	for i in range(start, end):
		if A[i] < pivot:
			A[i], A[j] = A[j], A[i]
			j += 1
	A[j], A[end] = A[end], A[j]
	return j

"""
	choose the first element of the array as pivot
	# of comparasions is 162085
"""

def adjustPivot(A):
	mid = (start + end)/2 
	pivot = sorted([A[start],A[end],A[mid]])[1]
	if A[start] == pivot: pass
	elif A[mid] == pivot:
		A[mid], A[start] = A[start], A[mid]
	else:
		A[end], A[start] = A[start], A[end]

def partition(A, start, end):
	global cnt 
	cnt += end - start
	pivot = A[start]
	j = start+1
	for i in range(start+1, end+1):
		if A[i] < pivot:
			A[i], A[j] = A[j], A[i]
			j += 1
	A[start], A[j-1] = A[j-1], A[start]
	return j-1

# part 1 choose the 1st element as pivot
cnt = 0
A = readInt()
quickSort(A, 0, len(A)-1)
print A
print cnt

