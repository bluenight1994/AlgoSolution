class SegmentTree {

	private int[] A;
	private int[] tree;

	public SegmentTree(int[] nums) {
		A = new int[nums.length];
		for (int i = 0; i < nums.length; i++) {
			A[i] = nums[i];
		}
		int x = (int)(Math.ceil(Math.log(nums.length)/Math.log(2)));
		int size = 2 * (int) Math.pow(2, x) - 1;
		tree = new int[size];
		build(0, 0, A.length-1);
	}	

	private void build(int node, int start, int end) {
		if (start == end) {
			tree[node] = A[start];
		}
		int mid = (start + end) / 2;
		build(2 * node + 1, start, mid);
		build(2 * node + 2, mid + 1, end);
		tree[node] = tree[2 * node + 1] + tree[2 * node + 2];
	}

	public void update(int i, int val) {
		updateHelper(0, 0, A.length-1, i, val);
	}

	private void updateHelper(int cur, int start, int end, int id, int val) {
		if (start == end) {
			A[id] = val;
			tree[cur] = val;
		} else {
			int mid = (start + end) / 2;
			if (start <= id and id <= end) {
				updateHelper(2 * cur + 1, start, mid, id, val);
			} else {
				updateHelper(2 * cur + 2, mid+1, end, id, val);
			}
			tree[cur] = tree[2*cur+1] + tree[2*cur+2];
		}
	}

	public int query(int i, int j) {
		return queryHelper(0, 0, A.length-1, i, j); 
	}

	private int queryHelper(int cur, int start, int end, int l, int r) {
		if (r < start || l > end) return 0;
		if (l <= start && r >= end) {
			return tree[cur];
		}
		int mid = (start + end) / 2;
		return query(2 * mid + 1, start, mid, l, r) + query(2 * mid + 2, mid + 1, end, l, r);
	}
}