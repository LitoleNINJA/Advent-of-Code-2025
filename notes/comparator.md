# Comparator Cheatsheet

## Overview
`Comparator` is a functional interface for custom comparison logic. Used with sorted collections like `TreeSet`, `TreeMap`, `PriorityQueue`.

## Return Values
- **Negative** (< 0): first argument comes before second
- **Zero** (0): arguments are equal
- **Positive** (> 0): first argument comes after second

## Creation Methods

### Lambda (Modern)
```java
Comparator<int[]> comp = (a, b) -> Integer.compare(a[0], b[0]);

// Multi-level comparison
Comparator<int[]> comp = (a, b) -> {
    if (a[0] != b[0]) return Integer.compare(a[0], b[0]);
    return Integer.compare(a[1], b[1]);
};
```

### Comparator.comparing (Most Readable)
```java
// Single key
Comparator<int[]> comp = Comparator.comparingInt(a -> a[0]);

// Multiple keys
Comparator<long[]> comp = Comparator
    .comparingLong((long[] a) -> a[0])
    .thenComparingLong(a -> a[1]);
```

### Anonymous Class (Old Style)
```java
Comparator<int[]> comp = new Comparator<int[]>() {
    @Override
    public int compare(int[] a, int[] b) {
        return Integer.compare(a[0], b[0]);
    }
};
```

## Built-in Comparators

```java
// Natural order
Comparator<String> natural = Comparator.naturalOrder();

// Reverse order
Comparator<String> reverse = Comparator.reverseOrder();

// Null-friendly
Comparator<String> nullFirst = Comparator.nullsFirst(natural);
Comparator<String> nullLast = Comparator.nullsLast(natural);

// Reversed
Comparator<String> reversed = natural.reversed();
```

## Chaining Comparators

```java
// Compare by length, then alphabetically
Comparator<String> comp = Comparator
    .comparingInt(String::length)
    .thenComparing(String::toLowerCase);

// With custom secondary comparator
Comparator<String> comp = Comparator
    .comparing(String::length)
    .thenComparing(String.CASE_INSENSITIVE_ORDER);
```

## Specialized Comparators

```java
// For int
Comparator.comparingInt(ToIntFunction)

// For long
Comparator.comparingLong(ToLongFunction)

// For double
Comparator.comparingDouble(ToDoubleFunction)
```

## Common Use Cases

### Sort Arrays by First Element
```java
Comparator<int[]> byFirst = Comparator.comparingInt(a -> a[0]);
TreeSet<int[]> set = new TreeSet<>(byFirst);
```

### Sort by Multiple Fields
```java
Comparator<Person> comp = Comparator
    .comparing(Person::getLastName)
    .thenComparing(Person::getFirstName)
    .thenComparingInt(Person::getAge);
```

### Reverse Sorting
```java
Comparator<Integer> desc = Comparator.reverseOrder();
TreeSet<Integer> set = new TreeSet<>(desc);
```

## Extract Comparator as Constant

```java
public class MyClass {
    private static final Comparator<long[]> RANGE_COMPARATOR = (a, b) -> {
        if (a[0] != b[0]) return Long.compare(a[0], b[0]);
        return Long.compare(a[1], b[1]);
    };
    
    private static TreeSet<long[]> ranges = new TreeSet<>(RANGE_COMPARATOR);
}
```

## Key Points
✅ Functional interface - use lambdas  
✅ Reusable across collections  
✅ Chainable with `thenComparing`  
✅ Extract as constant to avoid duplication  
❌ Must be consistent for correct behavior
