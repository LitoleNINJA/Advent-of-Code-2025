# Kahn's Algorithm - Topological Sort

## Overview
Kahn's Algorithm performs topological sorting on a Directed Acyclic Graph (DAG) using BFS. It processes nodes with no incoming edges first, gradually removing edges and processing newly available nodes.

## Algorithm Steps

1. **Calculate in-degrees** - Count incoming edges for each node
2. **Initialize queue** - Add all nodes with in-degree 0
3. **Process queue**:
   - Remove node from queue
   - Add to result
   - Decrease in-degree of all neighbors
   - Add neighbors with in-degree 0 to queue
4. **Check cycle** - If processed nodes < total nodes, graph has a cycle

## Implementation

```java
public static List<Integer> topologicalSort(int n, List<List<Integer>> adjList) {
    // Step 1: Calculate in-degrees
    int[] inDegree = new int[n];
    for (int u = 0; u < n; u++) {
        for (int v : adjList.get(u)) {
            inDegree[v]++;
        }
    }
    
    // Step 2: Initialize queue with nodes having in-degree 0
    ArrayDeque<Integer> queue = new ArrayDeque<>();
    for (int i = 0; i < n; i++) {
        if (inDegree[i] == 0) {
            queue.add(i);
        }
    }
    
    // Step 3: Process queue
    List<Integer> result = new ArrayList<>();
    while (!queue.isEmpty()) {
        int node = queue.pollFirst();
        result.add(node);
        
        // Reduce in-degree of neighbors
        for (int neighbor : adjList.get(node)) {
            inDegree[neighbor]--;
            if (inDegree[neighbor] == 0) {
                queue.add(neighbor);
            }
        }
    }
    
    // Step 4: Check for cycle
    if (result.size() != n) {
        return null;  // Graph has cycle, topological sort not possible
    }
    
    return result;
}
```

## Example

```
Graph:    5 → 0 ← 4
          ↓   ↓   ↓
          2 → 3 → 1

In-degrees: [2, 1, 1, 1, 0, 0]
Queue:      [4, 5]

Process: 4 → 0, 1
         5 → 0, 2
         0 → 1, 3
         2 → 3
         1
         3

Result: [4, 5, 0, 2, 1, 3] or [5, 4, 0, 2, 3, 1]
```

## Complexity
- **Time:** O(V + E) where V = vertices, E = edges
- **Space:** O(V) for in-degree array and queue

## Use Cases
✅ Task scheduling with dependencies  
✅ Build systems (compile order)  
✅ Course prerequisites  
✅ Dependency resolution  
✅ Detecting cycles in directed graphs

## Comparison: Kahn's vs DFS

| Feature | Kahn's Algorithm | DFS-based |
|---------|------------------|-----------|
| Approach | BFS (iterative) | DFS (recursive) |
| Cycle Detection | Built-in | Requires extra logic |
| Order | Any valid order | Reverse finish time |
| Implementation | Queue-based | Stack/recursion |
| Intuition | Remove leaves iteratively | Post-order traversal |
