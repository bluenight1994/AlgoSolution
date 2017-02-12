def countingSort(A, low, high):
	p = [0] * (high - low + 1)
	for k in A:
		p[k-low] += 1
	for i in range(1, len(p)):
		p[i] += p[i-1]
	ret = [0] * len(A)
	for num in A:
		print p[num-low]
		ret[p[num-low]-1] = num
		p[num-low] -= 1
	return ret

print countingSort([1,4,1,2,7,5,2], 0, 9)

# time complexity O(2n+k)
# k denote the range of number

