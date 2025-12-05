# Merge Overlapping Intervals

## Problem
Given a collection of intervals, merge all overlapping intervals.

**Example:**
```
Input:  [[1,3], [2,6], [8,10], [15,18]]
Output: [[1,6], [8,10], [15,18]]

Explanation: [1,3] and [2,6] overlap → merge to [1,6]
```

## Algorithm

### Step 1: Sort by Start Time
```java
Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
// Or use TreeSet with comparator
```

### Step 2: Merge Overlapping Intervals
```java
if (prevEnd >= currentStart) {
    // Overlapping - merge
    newEnd = Math.max(prevEnd, currentEnd);
} else {
    // Non-overlapping - add to result
    result.add(currentInterval);
}
```

## Complete Implementation

```java
public int[][] merge(int[][] intervals) {
    if (intervals.length <= 1) return intervals;
    
    // Sort by start time
    Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
    
    List<int[]> merged = new ArrayList<>();
    int[] current = intervals[0];
    merged.add(current);
    
    for (int[] interval : intervals) {
        int currentEnd = current[1];
        int nextStart = interval[0];
        int nextEnd = interval[1];
        
        if (currentEnd >= nextStart) {
            // Overlapping - merge
            current[1] = Math.max(currentEnd, nextEnd);
        } else {
            // Non-overlapping - add new interval
            current = interval;
            merged.add(current);
        }
    }
    
    return merged.toArray(new int[merged.size()][]);
}
```

## Using TreeSet (Day 5 Approach)

```java
private static void mergeRanges() {
    TreeSet<long[]> mergedRanges = new TreeSet<>(RANGE_COMPARATOR);
    Iterator<long[]> itr = freshRange.iterator();
    
    if (itr.hasNext()) {
        mergedRanges.add(itr.next());  // Add first
    }
    
    while (itr.hasNext()) {
        long[] current = itr.next();
        long[] last = mergedRanges.last();
        
        if (last[1] >= current[0]) {
            // Overlapping - merge
            mergedRanges.remove(last);
            last[1] = Math.max(last[1], current[1]);
            mergedRanges.add(last);
        } else {
            // Non-overlapping - add new
            mergedRanges.add(current);
        }
    }
    
    freshRange = mergedRanges;
}
```

## Overlap Conditions

```java
// Two intervals overlap if:
prevEnd >= currentStart

// Visualizations:
[----]       // prev
   [----]    // current  → overlaps (prevEnd=4 >= currentStart=3)

[--]         // prev
      [--]   // current  → no overlap (prevEnd=2 < currentStart=5)
```

## Edge Cases

```java
// Empty input
if (intervals.length == 0) return new int[0][];

// Single interval
if (intervals.length == 1) return intervals;

// Completely contained interval
[1, 10]
  [3, 5]   → merged to [1, 10]

// Adjacent intervals (depends on problem)
[1, 3]
[4, 6]     → usually NOT merged (gap of 1)
```

## Complexity
- **Time:** O(n log n) - dominated by sorting
- **Space:** O(n) - for output array

## Variations

### Insert Interval
```java
public int[][] insert(int[][] intervals, int[] newInterval) {
    List<int[]> result = new ArrayList<>();
    int i = 0;
    
    // Add all intervals before newInterval
    while (i < intervals.length && intervals[i][1] < newInterval[0]) {
        result.add(intervals[i++]);
    }
    
    // Merge overlapping intervals
    while (i < intervals.length && intervals[i][0] <= newInterval[1]) {
        newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
        newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
        i++;
    }
    result.add(newInterval);
    
    // Add remaining intervals
    while (i < intervals.length) {
        result.add(intervals[i++]);
    }
    
    return result.toArray(new int[result.size()][]);
}
```

## Key Points
✅ Always sort by start time first  
✅ Check overlap: `prevEnd >= currentStart`  
✅ Merge: `newEnd = max(prevEnd, currentEnd)`  
✅ Use TreeSet for automatic sorting with custom types  
✅ Handle edge cases: empty, single, contained intervals
