## Programming Exercise 1

---

### Divide & Conquer

Count Inversion:

Your task is to compute the number of inversions in the file given, where the $i^{th}$ row of the file indicates the $i^{th}$ entry of an array.

So the solution to this problem is to divide the problem T(n) into 3 parts:

1. count the inversion in the left half of the array
2. count the inversion in the right half of the array
3. count the inversion between the left half and the right half.

$T(n) = 2T(\frac{n}{2})+O(n)$  , according to the master theorem，the time complexity is O(nlogn)

 

`Count` : return the inversion in the array between `start` and `end`

`CountSplit`: return the inversions in subtask 3

```python
def readInt():
	intArray = open('IntegerArray.txt','r')
	lines = intArray.readlines()
	arr = []
	for line in lines:
		line = line.strip()
		arr.append(int(line))
	return arr


def Count(arr, start, end):
	if start >= end: return 0
	mid = (start + end)/2
	x = Count(arr, start, mid)
	y = Count(arr, mid+1, end)
	z = CountSplit(arr, start, end)
	return x+y+z


def CountSplit(arr, start, end):
	mid = (start + end)/2
	arr_c = [0]*(end-start+1)
	ai = start
	bi = mid+1
	ci = 0
	cnt = 0
	while ai <= mid and bi <= end:
		if arr[ai] <= arr[bi]:
			arr_c[ci] = arr[ai]
			ai+=1
			ci+=1
		else:
			arr_c[ci] = arr[bi]
			bi+=1
			ci+=1
			cnt += mid-ai+1
	while ai <= mid:
		arr_c[ci] = arr[ai]
		ai+=1
		ci+=1
	while bi <= end:
		arr_c[ci] = arr[bi]
		bi+=1
		ci+=1
	for i in range(len(arr_c)):
		arr[i+start] = arr_c[i]

	return cnt


def main():
	arr = readInt()
	print Count(arr, 0, len(arr)-1)

if __name__ == '__main__':
	main()
```



