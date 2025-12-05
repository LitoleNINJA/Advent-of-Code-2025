# Iterator Cheatsheet

## Overview
`Iterator` provides sequential access to elements in a collection. Safer than index-based iteration for modification during traversal.

## Basic Methods

```java
Iterator<T> itr = collection.iterator();

boolean hasNext()    // Returns true if more elements exist
T next()            // Returns next element, throws NoSuchElementException if none
void remove()       // Removes last element returned by next()
```

## Usage Pattern

```java
Iterator<String> itr = list.iterator();
while (itr.hasNext()) {
    String element = itr.next();
    // process element
}
```

## Safe Removal During Iteration

```java
// ✅ CORRECT - using Iterator.remove()
Iterator<Integer> itr = list.iterator();
while (itr.hasNext()) {
    int num = itr.next();
    if (num % 2 == 0) {
        itr.remove();  // Safe removal
    }
}

// ❌ WRONG - ConcurrentModificationException
for (Integer num : list) {
    if (num % 2 == 0) {
        list.remove(num);  // Throws exception!
    }
}
```

## TreeSet Iterator Example

```java
TreeSet<long[]> set = new TreeSet<>(comparator);
Iterator<long[]> itr = set.iterator();

while (itr.hasNext()) {
    long[] range = itr.next();
    // process range
    
    if (shouldRemove(range)) {
        itr.remove();  // Safe removal from TreeSet
    }
}
```

## Advanced: forEachRemaining (Java 8+)

```java
Iterator<String> itr = list.iterator();
itr.forEachRemaining(element -> System.out.println(element));

// Equivalent to:
while (itr.hasNext()) {
    System.out.println(itr.next());
}
```

## Descending Iterator (NavigableSet)

```java
TreeSet<Integer> set = new TreeSet<>();
Iterator<Integer> descItr = set.descendingIterator();
while (descItr.hasNext()) {
    System.out.println(descItr.next());  // Reverse order
}
```

## Common Patterns

### Process and Remove
```java
Iterator<Task> itr = tasks.iterator();
while (itr.hasNext()) {
    Task task = itr.next();
    task.process();
    itr.remove();  // Remove after processing
}
```

### Skip Elements
```java
Iterator<Integer> itr = list.iterator();
if (itr.hasNext()) itr.next();  // Skip first
while (itr.hasNext()) {
    int value = itr.next();
    // process remaining elements
}
```

### Peek at Next Without Consuming
```java
// Use PeekingIterator from Guava, or manual approach:
if (itr.hasNext()) {
    T next = itr.next();
    // process next
}
```

## Key Points
✅ Only safe way to remove during iteration  
✅ Fail-fast - throws exception if collection modified externally  
✅ Works with all Collection types  
❌ Can only move forward (use ListIterator for bidirectional)  
❌ remove() can only be called once per next()

## ListIterator (Bonus)

```java
ListIterator<String> listItr = list.listIterator();

listItr.hasNext()        // Check forward
listItr.hasPrevious()    // Check backward
listItr.next()           // Move forward
listItr.previous()       // Move backward
listItr.nextIndex()      // Get next index
listItr.previousIndex()  // Get previous index
listItr.add(element)     // Insert element
listItr.set(element)     // Replace element
listItr.remove()         // Remove element
```
