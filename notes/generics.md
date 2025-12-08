# Generics in Java

## Overview
Generics enable types (classes, interfaces, methods) to be **parameters** when defining classes, interfaces, and methods. They provide **compile-time type safety** and eliminate the need for casting.

## Why Generics?

### Without Generics (Old Way)
```java
List list = new ArrayList();
list.add("Hello");
list.add(123);  // No error, but dangerous

String s = (String) list.get(0);  // Manual casting required
String s2 = (String) list.get(1); // Runtime error! ClassCastException
```

### With Generics (Modern Way)
```java
List<String> list = new ArrayList<>();
list.add("Hello");
list.add(123);  // Compile-time error! Type safety

String s = list.get(0);  // No casting needed
```

---

## Basic Syntax

### Generic Classes

```java
// Single type parameter
public class Box<T> {
    private T value;
    
    public void set(T value) {
        this.value = value;
    }
    
    public T get() {
        return value;
    }
}

// Usage
Box<String> stringBox = new Box<>();
stringBox.set("Hello");
String value = stringBox.get();  // No casting

Box<Integer> intBox = new Box<>();
intBox.set(42);
int num = intBox.get();
```

### Multiple Type Parameters

```java
public class Pair<K, V> {
    private K key;
    private V value;
    
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }
    
    public K getKey() { return key; }
    public V getValue() { return value; }
}

// Usage
Pair<String, Integer> pair = new Pair<>("Age", 25);
String key = pair.getKey();    // "Age"
Integer value = pair.getValue(); // 25
```

### Generic Interfaces

```java
public interface Comparable<T> {
    int compareTo(T other);
}

public class Person implements Comparable<Person> {
    private String name;
    
    @Override
    public int compareTo(Person other) {
        return this.name.compareTo(other.name);
    }
}
```

---

## Generic Methods

Methods can have their own type parameters, independent of the class.

### Syntax

```java
public class Utils {
    // Generic static method
    public static <T> T getFirst(List<T> list) {
        return list.isEmpty() ? null : list.get(0);
    }
    
    // Generic method with multiple parameters
    public static <K, V> Map<K, V> createMap(K key, V value) {
        Map<K, V> map = new HashMap<>();
        map.put(key, value);
        return map;
    }
    
    // Generic method with return type
    public static <T> List<T> arrayToList(T[] array) {
        return Arrays.asList(array);
    }
}

// Usage
List<String> names = Arrays.asList("Alice", "Bob");
String first = Utils.getFirst(names);  // Type inference

Map<String, Integer> map = Utils.createMap("Count", 42);
```

### Real-World Example

```java
public class utils {
    // Generic input processing
    public static <T> T readInputAndSolve(Function<ArrayList<String>, T> solver) 
        throws IOException {
        long startTime = getCurrentTime();
        ArrayList<String> lines = getInputLines();
        
        long processStartTime = getCurrentTime();
        T result = solver.apply(lines);  // T can be any type
        
        long endTime = getCurrentTime();
        printTimeStats(out, processStartTime, startTime, endTime);
        
        return result;
    }
}

// Usage - T is inferred as Long
long ans = utils.readInputAndSolve(lines -> {
    return processLines(lines);  // Returns Long
});

// Usage - T is inferred as String
String result = utils.readInputAndSolve(lines -> {
    return String.join(",", lines);  // Returns String
});
```

---

## Type Parameter Naming Conventions

| Letter | Meaning | Example |
|--------|---------|---------|
| `T` | Type | `Box<T>` |
| `E` | Element | `List<E>` |
| `K` | Key | `Map<K, V>` |
| `V` | Value | `Map<K, V>` |
| `N` | Number | `Box<N extends Number>` |
| `R` | Result/Return | `Function<T, R>` |

---

## Bounded Type Parameters

Restrict the types that can be used as type arguments.

### Upper Bounds (extends)

```java
// Only types that extend Number
public class NumberBox<T extends Number> {
    private T value;
    
    public double getDoubleValue() {
        return value.doubleValue();  // Can call Number methods
    }
}

// Usage
NumberBox<Integer> intBox = new NumberBox<>();  // OK
NumberBox<Double> doubleBox = new NumberBox<>();  // OK
NumberBox<String> stringBox = new NumberBox<>();  // Compile error!
```

### Multiple Bounds

```java
// T must extend both Number AND implement Comparable
public class SortableNumber<T extends Number & Comparable<T>> {
    private T value;
    
    public boolean isGreaterThan(T other) {
        return value.compareTo(other) > 0;
    }
}
```

### Bounded Generic Methods

```java
// Only accepts types that extend Comparable
public static <T extends Comparable<T>> T findMax(List<T> list) {
    if (list.isEmpty()) return null;
    
    T max = list.get(0);
    for (T item : list) {
        if (item.compareTo(max) > 0) {
            max = item;
        }
    }
    return max;
}

// Usage
List<Integer> numbers = Arrays.asList(1, 5, 3, 9, 2);
Integer max = findMax(numbers);  // 9
```

---

## Wildcards

Used when you don't care about the specific type.

### Unbounded Wildcard (?)

```java
// Accept any type
public static void printList(List<?> list) {
    for (Object item : list) {
        System.out.println(item);
    }
}

// Usage
printList(Arrays.asList(1, 2, 3));        // List<Integer>
printList(Arrays.asList("a", "b", "c"));  // List<String>
```

### Upper Bounded Wildcard (? extends T)

```java
// Accept T or any subtype of T (read-only)
public static double sumNumbers(List<? extends Number> list) {
    double sum = 0;
    for (Number num : list) {
        sum += num.doubleValue();
    }
    return sum;
}

// Usage
sumNumbers(Arrays.asList(1, 2, 3));           // List<Integer>
sumNumbers(Arrays.asList(1.5, 2.5, 3.5));     // List<Double>
sumNumbers(Arrays.asList(1L, 2L, 3L));        // List<Long>
```

### Lower Bounded Wildcard (? super T)

```java
// Accept T or any supertype of T (write-only)
public static void addIntegers(List<? super Integer> list) {
    list.add(1);
    list.add(2);
    list.add(3);
}

// Usage
List<Integer> intList = new ArrayList<>();
addIntegers(intList);  // OK

List<Number> numList = new ArrayList<>();
addIntegers(numList);  // OK - Number is supertype of Integer

List<Object> objList = new ArrayList<>();
addIntegers(objList);  // OK - Object is supertype of Integer
```

### PECS Principle
**Producer Extends, Consumer Super**

```java
// Producer (reading from) - use extends
public static <T> void copy(List<? extends T> source, List<? super T> dest) {
    for (T item : source) {  // Reading from source
        dest.add(item);      // Writing to dest
    }
}

// Usage
List<Integer> ints = Arrays.asList(1, 2, 3);
List<Number> nums = new ArrayList<>();
copy(ints, nums);  // OK
```

---

## Type Erasure

Generics are **compile-time only**. At runtime, generic type information is erased.

### What Happens

```java
// Source code
List<String> strings = new ArrayList<>();
List<Integer> integers = new ArrayList<>();

// After type erasure (runtime)
List strings = new ArrayList();  // Raw type
List integers = new ArrayList(); // Same raw type

// This is why you can't do:
if (strings instanceof List<String>) { }  // Compile error
```

### Implications

```java
// Cannot create generic array
T[] array = new T[10];  // Compile error

// Cannot use primitive types
List<int> list;  // Error - must use List<Integer>

// Cannot create instance of type parameter
T obj = new T();  // Compile error

// Cannot use in static context
class Box<T> {
    static T value;  // Compile error
}
```

### Workarounds

```java
// Creating generic array - use ArrayList instead
List<String>[] array = new ArrayList[10];  // Warning but works
// Better: List<List<String>> list = new ArrayList<>();

// Creating instance - pass Class object
public class Factory<T> {
    private Class<T> type;
    
    public Factory(Class<T> type) {
        this.type = type;
    }
    
    public T createInstance() throws Exception {
        return type.getDeclaredConstructor().newInstance();
    }
}

// Usage
Factory<String> factory = new Factory<>(String.class);
String s = factory.createInstance();
```

---

## Common Generic Patterns

### Builder Pattern

```java
public class Query<T> {
    private Class<T> type;
    private String filter;
    
    private Query(Class<T> type) {
        this.type = type;
    }
    
    public static <T> Query<T> from(Class<T> type) {
        return new Query<>(type);
    }
    
    public Query<T> where(String filter) {
        this.filter = filter;
        return this;
    }
    
    public List<T> execute() {
        // Execute query and return results
        return new ArrayList<>();
    }
}

// Usage
List<Person> people = Query.from(Person.class)
                           .where("age > 18")
                           .execute();
```

### Factory Pattern

```java
public interface Factory<T> {
    T create();
}

public class PersonFactory implements Factory<Person> {
    @Override
    public Person create() {
        return new Person();
    }
}
```

### Repository Pattern

```java
public interface Repository<T, ID> {
    T findById(ID id);
    List<T> findAll();
    void save(T entity);
    void delete(ID id);
}

public class UserRepository implements Repository<User, Long> {
    @Override
    public User findById(Long id) {
        // Implementation
        return null;
    }
    
    @Override
    public List<User> findAll() {
        return new ArrayList<>();
    }
    
    @Override
    public void save(User entity) {
        // Implementation
    }
    
    @Override
    public void delete(Long id) {
        // Implementation
    }
}
```

---

## Generic Collections

### Common Generic Collections

```java
// List
List<String> list = new ArrayList<>();
List<Integer> linkedList = new LinkedList<>();

// Set
Set<String> hashSet = new HashSet<>();
Set<String> treeSet = new TreeSet<>();

// Map
Map<String, Integer> map = new HashMap<>();
Map<Integer, String> treeMap = new TreeMap<>();

// Queue
Queue<Integer> queue = new ArrayDeque<>();
Queue<String> priorityQueue = new PriorityQueue<>();
```

### Custom Comparator with Generics

```java
Comparator<Person> byAge = (p1, p2) -> Integer.compare(p1.age, p2.age);

// Or using Comparator methods
Comparator<Person> byName = Comparator.comparing(Person::getName);
Comparator<Person> byAgeDesc = Comparator.comparingInt(Person::getAge).reversed();
```

---

## Best Practices

### ✅ Do

```java
// Use diamond operator (type inference)
List<String> list = new ArrayList<>();  // Not new ArrayList<String>()

// Use bounded wildcards for flexibility
public void process(List<? extends Number> numbers) { }

// Prefer generics over raw types
List<String> list = new ArrayList<>();  // Not List list = new ArrayList();

// Use meaningful type parameter names
public class Response<T> { }  // Good
public class Response<X> { }  // Less clear
```

### ❌ Don't

```java
// Don't use raw types
List list = new ArrayList();  // Warning: raw type

// Don't use wildcard in return type
public List<?> getData() { }  // Caller can't add anything

// Don't overuse generics
public <T> T doNothing(T value) { return value; }  // Unnecessary

// Don't mix raw and generic types
List<String> strings = new ArrayList();  // Inconsistent
```

---

## Common Use Cases

### 1. Collections Framework

```java
List<String> names = new ArrayList<>();
Set<Integer> uniqueNumbers = new HashSet<>();
Map<String, Person> personMap = new HashMap<>();
```

### 2. Utility Methods

```java
public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
    return list.stream()
               .filter(predicate)
               .collect(Collectors.toList());
}
```

### 3. Data Transfer Objects

```java
public class Response<T> {
    private T data;
    private String message;
    private int status;
    
    public Response(T data, String message, int status) {
        this.data = data;
        this.message = message;
        this.status = status;
    }
}

// Usage
Response<User> userResponse = new Response<>(user, "Success", 200);
Response<List<Post>> postsResponse = new Response<>(posts, "Found", 200);
```

### 4. Optional Values

```java
public <T> Optional<T> findFirst(List<T> list, Predicate<T> predicate) {
    return list.stream()
               .filter(predicate)
               .findFirst();
}
```

---

## Advanced Topics

### Recursive Type Bounds

```java
// Enum pattern
public class Enum<E extends Enum<E>> { }

// Comparable pattern
public class Person implements Comparable<Person> {
    public int compareTo(Person other) { }
}
```

### Type Tokens

```java
public class TypeReference<T> {
    private final Type type;
    
    protected TypeReference() {
        Type superclass = getClass().getGenericSuperclass();
        this.type = ((ParameterizedType) superclass).getActualTypeArguments()[0];
    }
    
    public Type getType() {
        return type;
    }
}

// Usage
TypeReference<List<String>> typeRef = new TypeReference<List<String>>() {};
```

---

## Summary

| Feature | Syntax | Example |
|---------|--------|---------|
| Generic Class | `class Box<T>` | `Box<String>` |
| Generic Method | `<T> T method()` | `<String> String get()` |
| Multiple Params | `<K, V>` | `Map<String, Integer>` |
| Upper Bound | `<T extends Type>` | `<T extends Number>` |
| Wildcard | `<?>` | `List<?>` |
| Upper Wildcard | `<? extends T>` | `List<? extends Number>` |
| Lower Wildcard | `<? super T>` | `List<? super Integer>` |

**Key Benefits:**
- ✅ Compile-time type safety
- ✅ No casting needed
- ✅ Code reusability
- ✅ Cleaner, more readable code
- ✅ Catch errors at compile-time, not runtime
