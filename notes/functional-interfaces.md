# Functional Interfaces in Java

## Overview
A **Functional Interface** is an interface with **exactly one abstract method** (SAM - Single Abstract Method). They enable lambda expressions and method references in Java 8+.

## Definition

```java
@FunctionalInterface  // Optional but recommended
interface Calculator {
    int calculate(int x, int y);  // Single abstract method
}
```

The `@FunctionalInterface` annotation:
- ✅ Optional but recommended
- ✅ Compiler checks for exactly one abstract method
- ✅ Generates error if more methods added

---

## Lambda Syntax

### Traditional vs Lambda

```java
// Traditional anonymous class (verbose)
Calculator add = new Calculator() {
    @Override
    public int calculate(int x, int y) {
        return x + y;
    }
};

// Lambda expression (concise)
Calculator add = (x, y) -> x + y;
```

### Lambda Variations

```java
// No parameters
() -> "Hello"

// One parameter (parentheses optional)
x -> x * 2
(x) -> x * 2

// Multiple parameters
(x, y) -> x + y

// Multiple statements (requires braces and return)
(x, y) -> {
    int sum = x + y;
    return sum * 2;
}
```

---

## Built-in Functional Interfaces

Located in `java.util.function` package.

### Function<T, R>
Transforms input to output.

```java
// Method signature: R apply(T t)

Function<String, Integer> length = s -> s.length();
int len = length.apply("Hello");  // 5

Function<Integer, Integer> square = x -> x * x;
int result = square.apply(5);  // 25

// Chaining
Function<String, String> trim = String::trim;
Function<String, Integer> parse = Integer::parseInt;
Function<String, Integer> trimAndParse = trim.andThen(parse);
```

**Common uses:**
- Stream `map()` operation
- Data transformation
- Type conversion

### Consumer<T>
Accepts input, performs action, no return value.

```java
// Method signature: void accept(T t)

Consumer<String> print = s -> System.out.println(s);
print.accept("Hello");

Consumer<Integer> increment = x -> counter.add(x);

// Chaining
Consumer<String> log = s -> logger.info(s);
Consumer<String> printAndLog = print.andThen(log);
```

**Common uses:**
- Stream `forEach()` operation
- Side effects (printing, logging)
- Updating state

### Supplier<T>
Provides output with no input (generator/factory).

```java
// Method signature: T get()

Supplier<String> greeting = () -> "Hello World";
String msg = greeting.get();

Supplier<Double> random = () -> Math.random();
Supplier<ArrayList<String>> listFactory = ArrayList::new;

// Lazy initialization
Supplier<ExpensiveObject> lazy = () -> new ExpensiveObject();
ExpensiveObject obj = lazy.get();  // Created only when needed
```

**Common uses:**
- Lazy initialization
- Factory methods
- Default values
- Random generation

### Predicate<T>
Tests a condition, returns boolean.

```java
// Method signature: boolean test(T t)

Predicate<Integer> isEven = x -> x % 2 == 0;
boolean result = isEven.test(4);  // true

Predicate<String> isEmpty = String::isEmpty;
Predicate<String> isLong = s -> s.length() > 10;

// Combining predicates
Predicate<Integer> isPositive = x -> x > 0;
Predicate<Integer> isPositiveEven = isEven.and(isPositive);
Predicate<Integer> notEven = isEven.negate();
```

**Common uses:**
- Stream `filter()` operation
- Conditional logic
- Validation

### BiFunction<T, U, R>
Takes two inputs, returns output.

```java
// Method signature: R apply(T t, U u)

BiFunction<Integer, Integer, Integer> add = (x, y) -> x + y;
int sum = add.apply(5, 3);  // 8

BiFunction<String, Integer, String> repeat = (s, n) -> s.repeat(n);
String result = repeat.apply("Hi", 3);  // "HiHiHi"
```

### BiConsumer<T, U>
Takes two inputs, no return value.

```java
// Method signature: void accept(T t, U u)

BiConsumer<String, Integer> printWithCount = (s, n) -> 
    System.out.println(s + " x " + n);
printWithCount.accept("Hello", 3);  // "Hello x 3"
```

### BiPredicate<T, U>
Tests two inputs, returns boolean.

```java
// Method signature: boolean test(T t, U u)

BiPredicate<Integer, Integer> isGreater = (x, y) -> x > y;
boolean result = isGreater.test(5, 3);  // true
```

---

## Specialized Functional Interfaces

For primitive types (avoid boxing overhead).

### IntFunction, LongFunction, DoubleFunction
```java
IntFunction<String> intToString = i -> String.valueOf(i);
```

### ToIntFunction, ToLongFunction, ToDoubleFunction
```java
ToIntFunction<String> stringLength = s -> s.length();
```

### IntConsumer, LongConsumer, DoubleConsumer
```java
IntConsumer print = i -> System.out.println(i);
```

### IntPredicate, LongPredicate, DoublePredicate
```java
IntPredicate isPositive = i -> i > 0;
```

### IntSupplier, LongSupplier, DoubleSupplier
```java
IntSupplier random = () -> (int)(Math.random() * 100);
```

### IntBinaryOperator, LongBinaryOperator, DoubleBinaryOperator
```java
IntBinaryOperator add = (a, b) -> a + b;
```

---

## Method References

Shorthand syntax for lambdas that just call a method.

### Types of Method References

```java
// 1. Static method reference
Function<String, Integer> parse = Integer::parseInt;
// Equivalent: s -> Integer.parseInt(s)

// 2. Instance method reference (on specific object)
String prefix = "Hello ";
Function<String, String> greet = prefix::concat;
// Equivalent: s -> prefix.concat(s)

// 3. Instance method reference (on parameter)
Function<String, Integer> length = String::length;
// Equivalent: s -> s.length()

// 4. Constructor reference
Supplier<ArrayList<String>> listFactory = ArrayList::new;
// Equivalent: () -> new ArrayList<>()

Function<Integer, int[]> arrayFactory = int[]::new;
// Equivalent: n -> new int[n]
```

### Common Examples

```java
// forEach with method reference
list.forEach(System.out::println);
// Instead of: list.forEach(s -> System.out.println(s))

// map with method reference
stream.map(String::trim);
// Instead of: stream.map(s -> s.trim())

// filter with method reference
stream.filter(Objects::nonNull);
// Instead of: stream.filter(obj -> obj != null)

// sorted with method reference
list.sort(String::compareTo);
// Instead of: list.sort((a, b) -> a.compareTo(b))
```

---

## Practical Usage Examples

### Stream API Operations

```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie");

// map - Function<T, R>
List<Integer> lengths = names.stream()
    .map(String::length)  // or s -> s.length()
    .collect(Collectors.toList());

// filter - Predicate<T>
List<String> longNames = names.stream()
    .filter(s -> s.length() > 4)
    .collect(Collectors.toList());

// forEach - Consumer<T>
names.forEach(System.out::println);

// reduce - BinaryOperator<T>
Optional<String> combined = names.stream()
    .reduce((a, b) -> a + ", " + b);
```

### Custom Utilities

```java
// Generic input processor
public static <T> T processInput(Function<ArrayList<String>, T> processor) 
    throws IOException {
    ArrayList<String> lines = getInputLines();
    return processor.apply(lines);
}

// Usage
long result = processInput(lines -> {
    // Process lines and return result
    return lines.stream()
                .mapToLong(Long::parseLong)
                .sum();
});

// With method reference
long result = processInput(MyClass::processLines);
```

### Callbacks and Event Handlers

```java
public class Button {
    private Consumer<String> onClick;
    
    public void setOnClick(Consumer<String> handler) {
        this.onClick = handler;
    }
    
    public void click() {
        onClick.accept("Button clicked!");
    }
}

// Usage
Button btn = new Button();
btn.setOnClick(msg -> System.out.println(msg));
btn.click();  // "Button clicked!"
```

---

## Composing Functions

### andThen
Execute functions in sequence.

```java
Function<Integer, Integer> multiplyBy2 = x -> x * 2;
Function<Integer, Integer> add3 = x -> x + 3;

// First multiply, then add
Function<Integer, Integer> combined = multiplyBy2.andThen(add3);
int result = combined.apply(5);  // (5 * 2) + 3 = 13
```

### compose
Execute functions in reverse order.

```java
// First add, then multiply
Function<Integer, Integer> combined = multiplyBy2.compose(add3);
int result = combined.apply(5);  // (5 + 3) * 2 = 16
```

### Predicate Composition

```java
Predicate<Integer> isEven = x -> x % 2 == 0;
Predicate<Integer> isPositive = x -> x > 0;

// AND
Predicate<Integer> isPositiveEven = isEven.and(isPositive);

// OR
Predicate<Integer> isEvenOrPositive = isEven.or(isPositive);

// NOT
Predicate<Integer> isOdd = isEven.negate();
```

---

## Best Practices

### ✅ Do

```java
// Use method references when possible
list.forEach(System.out::println);

// Keep lambdas short and readable
Predicate<String> isEmpty = String::isEmpty;

// Use appropriate functional interface
Function<String, Integer> parse = Integer::parseInt;  // Not Supplier

// Avoid side effects in pure functions
Function<Integer, Integer> square = x -> x * x;  // Good
```

### ❌ Don't

```java
// Don't use lambdas for complex logic
Function<String, Integer> bad = s -> {
    // 20 lines of code...
};  // Extract to method instead

// Don't modify external state in lambdas
int[] counter = {0};
list.forEach(x -> counter[0]++);  // Use stream operations instead

// Don't catch exceptions inside lambdas unnecessarily
Function<String, Integer> risky = s -> {
    try {
        return Integer.parseInt(s);
    } catch(Exception e) {
        return 0;  // Hidden errors
    }
};
```

---

## Common Patterns

### Optional with Functional Interfaces

```java
Optional<String> opt = Optional.of("Hello");

// map - Function
opt.map(String::toUpperCase);

// filter - Predicate
opt.filter(s -> s.length() > 3);

// ifPresent - Consumer
opt.ifPresent(System.out::println);

// orElseGet - Supplier
String value = opt.orElseGet(() -> "Default");
```

### CompletableFuture

```java
CompletableFuture.supplyAsync(() -> fetchData())  // Supplier
    .thenApply(data -> transform(data))           // Function
    .thenAccept(result -> save(result))           // Consumer
    .exceptionally(ex -> handleError(ex));        // Function
```

---

## Performance Considerations

- **Primitive specializations** avoid boxing overhead
- **Method references** may be optimized better than lambdas
- **Stateless lambdas** enable better parallelization
- **Avoid capturing** large objects in closures

```java
// Prefer primitive specializations
IntStream.range(0, 1000)
    .filter(i -> i % 2 == 0)  // IntPredicate
    .sum();

// Instead of
Stream.iterate(0, i -> i + 1)
    .limit(1000)
    .filter(i -> i % 2 == 0)  // Predicate<Integer> - boxing overhead
    .mapToInt(i -> i)
    .sum();
```

---

## Summary

| Interface | Parameters | Return | Method | Use Case |
|-----------|-----------|--------|--------|----------|
| `Function<T,R>` | 1 | Yes | `apply()` | Transform data |
| `Consumer<T>` | 1 | No | `accept()` | Side effects |
| `Supplier<T>` | 0 | Yes | `get()` | Generate/provide |
| `Predicate<T>` | 1 | boolean | `test()` | Test condition |
| `BiFunction<T,U,R>` | 2 | Yes | `apply()` | Transform 2 inputs |
| `BiConsumer<T,U>` | 2 | No | `accept()` | Action on 2 inputs |
| `BiPredicate<T,U>` | 2 | boolean | `test()` | Test 2 inputs |
| `UnaryOperator<T>` | 1 | Same type | `apply()` | Transform same type |
| `BinaryOperator<T>` | 2 | Same type | `apply()` | Combine 2 of same type |

**Key Takeaways:**
- Functional interfaces enable passing behavior as parameters
- Lambda expressions provide concise implementation
- Method references offer even shorter syntax
- Built-in interfaces cover most common patterns
- Essential for Stream API and modern Java code
