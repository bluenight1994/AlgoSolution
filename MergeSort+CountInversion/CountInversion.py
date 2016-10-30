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


