## play with (Pre|In|Post) Traversal

recursive way to deal with these problem is trivial, will not cover this part in this post

first let's start with preorder traversal

1. visit current node
2. move to its left node
3. then its right node

the key idea is to use stack:

```java
//pre-order traversal root-left-right
List<Integer> preorderTraversal(TreeNode root) {
  List<Integer> ret = new ArrayList<>();
  Stack<TreeNode> stack = new Stack<>();
  if(root == null) return ret;
  // root is not null
  stack.push(root);
  while(!stack.isEmpty()){
    TreeNode node = stack.pop();
    ret.add(node.val);
    // stack is FIFO
   	if(node.right != null) {
      stack.push(node.right);
   	}
    if(node.left != null) {
      stack.push(node.left);
    }
  }
  return ret;
}

// 67/67 AC
```



the postorder traversal have a trick, postorder is left-right-root

preorder is root-left-right, code can be modified to reach sequence of root-right-left

then reverse the modified preorder sequence can be left-right-root

```java
//post-order traversal left-right-root
List<Integer> postorderTraversal(TreenNode root) {
  List<Integer> ret = new ArrayList<>();
  Stack<TreeNode> stack = new Stack<>();
  if(root == null) return ret;
  // root is not null
  stack.push(root);
  while(!stack.isEmpty()){
    TreeNode node = stack.pop();
    ret.add(node.val);
    // sequence of pop is right first, left second
    if(node.left != null) {
      stack.push(node.left);
    }
   	if(node.right != null) {
      stack.push(node.right);
   	}
  }
  //reverse the result
  Collections.reverse(ret);
  return ret;
}
```



