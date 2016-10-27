### Entry level of Stack & Queue

---

Queue : First in First out.         Stack: Last in First out.

For dynamic size, it is better to implement stack or queue with linkedList.

I just try two exercise in Introduction to Algorithms.

1. Implement Queue using Stack.

   ```java
   class MyQueue {
       
       Stack<Integer> s1 = new Stack<>();
       Stack<Integer> s2 = new Stack<>();
       
       public void push(int x) {
           s1.push(x);
       }

       // Removes the element from in front of queue.
       public void pop() {
           peek();
           s2.pop();
       }

       // Get the front element.
       public int peek() {
           if(s2.isEmpty()) {
               while(!s1.isEmpty()) {
                   s2.push(s1.pop());
               }
           }
           return s2.peek();
       }

       // Return whether the queue is empty.
       public boolean empty() {
           return s1.isEmpty() && s2.isEmpty();
       }
   }
   ```

   ​

2. Implement Stack using Queue;

   ```java
   class MyStack {
       // Push element x onto stack.
       Queue<Integer> queue = new LinkedList<>();
       public void push(int x) {
           queue.offer(x);
           for(int i=1; i<queue.size(); i++) {
               queue.offer(queue.poll());
           }
       }

       // Removes the element on top of the stack.
       public void pop() {
           queue.poll();
       }

       // Get the top element.
       public int top() {
           return queue.peek();
       }

       // Return whether the stack is empty.
       public boolean empty() {
           return queue.isEmpty();
       }
   }
   ```

3. implement min stack. a min stack can return current minimum value in the stack. my solution is to using a monotonic stack. 

   ```java
   public class MinStack {
       
       Stack<Integer> s1;
       Stack<Integer> s2;

       /** initialize your data structure here. */
       public MinStack() {
           s1 = new Stack<>();
           s2 = new Stack<>();
       }
       
       public void push(int x) {
           s1.push(x);
           if(s2.isEmpty() || x <= s2.peek()) s2.push(x);
       }
       
       public void pop() {
           int k = s1.pop();
           if(k == s2.peek()) s2.pop();
       }
       
       public int top() {
           return s1.peek();
       }
       
       public int getMin() {
           return s2.peek();
       }
   }
   ```

   ​