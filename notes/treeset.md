# TreeSet Cheatsheet

## Overview
`TreeSet` is a sorted set implementation based on a Red-Black tree. Elements are ordered using natural ordering or a custom `Comparator`.

## Key Features
- ✅ Sorted order (ascending by default)
- ✅ No duplicates
- ✅ O(log n) for add, remove, contains
- ❌ No null elements (unless custom comparator allows)
- ❌ Not synchronized

## Creation

```java
// Natural ordering (requires Comparable)
TreeSet<Integer> set = new TreeSet<>();

// Custom comparator (REQUIRED for arrays/non-comparable types)
TreeSet<int[]> set = new TreeSet<>((a, b) -> {
    if (a[0] != b[0]) return Integer.compare(a[0], b[0]);
    return Integer.compare(a[1], b[1]);
});

// Using Comparator.comparing
TreeSet<long[]> set = new TreeSet<>(
    Comparator.comparingLong((long[] a) -> a[0])
              .thenComparingLong(a -> a[1])
);
```

## Common Operations

```java
set.add(element);           // Add element
set.remove(element);        // Remove element
set.contains(element);      // Check existence
set.size();                 // Get size
set.isEmpty();              // Check if empty
set.clear();                // Remove all elements
```

## Navigable Operations

```java
set.first();                // Get smallest element
set.last();                 // Get largest element
set.lower(e);               // Largest element < e
set.floor(e);               // Largest element <= e
set.ceiling(e);             // Smallest element >= e
set.higher(e);              // Smallest element > e
set.pollFirst();            // Remove & return first
set.pollLast();             // Remove & return last
```

## Iteration

```java
// Forward iteration
for (int[] range : set) {
    // process range
}

// Using iterator
Iterator<int[]> itr = set.iterator();
while (itr.hasNext()) {
    int[] range = itr.next();
    // process range
}

// Descending order
for (int[] range : set.descendingSet()) {
    // process in reverse
}
```

## Common Pitfall

```java
// ❌ WRONG - will throw ClassCastException
TreeSet<int[]> set = new TreeSet<>();  // Arrays are not Comparable!

// ✅ CORRECT - provide comparator
TreeSet<int[]> set = new TreeSet<>((a, b) -> 
    Integer.compare(a[0], b[0])
);
```

## Use Cases
- Sorted unique elements
- Range queries
- Finding nearest elements
- Merge intervals problems
