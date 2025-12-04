# ArrayDeque in Java

## Overview
`ArrayDeque` is a resizable array implementation of the `Deque` interface (double-ended queue). It's faster than `LinkedList` for queue/stack operations and has no capacity restrictions.

## Basic Operations

### Creation
```java
import java.util.ArrayDeque;

ArrayDeque<Integer> deque = new ArrayDeque<>();
ArrayDeque<int[]> queue = new ArrayDeque<>();  // For arrays
```

### Queue Operations (FIFO)
```java
queue.add(element);          // Add to end (throws exception if full)
queue.offer(element);        // Add to end (returns false if full)
queue.remove();              // Remove from front (throws exception if empty)
queue.poll();                // Remove from front (returns null if empty)
queue.peek();                // View front element without removing
```

### Stack Operations (LIFO)
```java
deque.push(element);         // Add to front
deque.pop();                 // Remove from front
deque.peek();                // View front element
```

### Deque-Specific Operations
```java
deque.addFirst(element);     // Add to front
deque.addLast(element);      // Add to end
deque.removeFirst();         // Remove from front
deque.removeLast();          // Remove from end
deque.pollFirst();           // Remove from front (null if empty)
deque.pollLast();            // Remove from end (null if empty)
```

## Common Use Cases

### BFS Queue
```java
ArrayDeque<int[]> queue = new ArrayDeque<>();
queue.add(new int[]{row, col});

while (!queue.isEmpty()) {
    int[] current = queue.pollFirst();
    int i = current[0], j = current[1];
    // Process and add neighbors
}
```

### Stack for DFS
```java
ArrayDeque<Integer> stack = new ArrayDeque<>();
stack.push(node);

while (!stack.isEmpty()) {
    int current = stack.pop();
    // Process and push neighbors
}
```

## Performance
- **O(1)** for add/remove operations at both ends
- **O(1)** amortized for resizing
- **Faster than LinkedList** due to better cache locality
- **Not thread-safe** (use `ConcurrentLinkedDeque` for concurrent access)

## Key Points
✅ Preferred over `Stack` class (legacy)  
✅ Preferred over `LinkedList` for queue/stack operations  
✅ No `null` elements allowed  
✅ Not synchronized  
✅ Implements both `Queue` and `Deque` interfaces
