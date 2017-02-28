public class Codec {

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        if (root == null) return "[]";
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        sb.append(root.val);
        sb.append(",");
        while(!queue.isEmpty()) {
            int size = queue.size();
            StringBuilder tmp = new StringBuilder();
            int cnt = 0;
            for (int i = 0; i < size; i++) {
                TreeNode cur = queue.poll();
                if (cur.left != null) {
                    cnt ++;
                    queue.offer(cur.left);
                    tmp.append(cur.left.val+",");
                } else {
                    tmp.append("null,");
                }
                if (cur.right != null) {
                    cnt ++;
                    queue.offer(cur.right);
                    tmp.append(cur.right.val+",");
                } else {
                    tmp.append("null,");
                }
            }
            if (cnt != 0)
                sb.append(tmp.toString());
        }
        String res = sb.toString();
        res = res.substring(0, res.length()-1);
        return res + "]";
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if (data == null || data.equals("[]")) return null;
        data = data.substring(1, data.length()-1);
        String[] uu = data.split(",");
        Queue<String> pipe = new LinkedList<>();
        for (String s : uu) pipe.offer(s);
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode root = new TreeNode(Integer.valueOf(pipe.poll()));
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            TreeNode[] curr = new TreeNode[size];
            for (int i = 0; i < size; i++) {
                curr[i] = queue.poll();
            }
            for (int i = 0; i < size; i++) {
                if (pipe.isEmpty()) break;
                String next = pipe.poll();
                if (!next.equals("null")) {
                    TreeNode a = new TreeNode(Integer.valueOf(next));
                    curr[i].left = a;
                    queue.offer(a);
                }
                String nnext = pipe.poll();
                if (!nnext.equals("null")) {
                    TreeNode b = new TreeNode(Integer.valueOf(nnext));
                    curr[i].right = b;
                    queue.offer(b);
                }
            }
        }
        return root;
    }
}