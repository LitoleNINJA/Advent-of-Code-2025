# Java Streams Quick Reference

## Stream Pipeline

```
Source → Intermediate Operations → Terminal Operation
```

1. **Source** — Collection, array, generator, I/O channel
2. **Intermediate Operations** (lazy) — transform stream, return new stream
3. **Terminal Operations** (eager) — produce result, trigger execution

---

## Key Intermediate Operations

| Operation | Description |
|-----------|-------------|
| `filter(Predicate)` | Select elements matching condition |
| `map(Function)` | Transform elements 1:1 |
| `flatMap(Function)` | Transform elements 1:many, flatten result |
| `distinct()` | Remove duplicates |
| `sorted()` / `sorted(Comparator)` | Order elements |
| `limit(n)` / `skip(n)` | Truncate/skip elements |
| `peek(Consumer)` | Debug/inspect elements (side-effect) |

---

## Key Terminal Operations

| Operation | Description |
|-----------|-------------|
| `forEach(Consumer)` | Perform action on each element |
| `collect(Collector)` | Gather into collection/map |
| `reduce(BinaryOperator)` | Combine elements into single result |
| `count()` | Count elements |
| `anyMatch/allMatch/noneMatch(Predicate)` | Boolean tests |
| `findFirst()` / `findAny()` | Retrieve element(s) |
| `min/max(Comparator)` | Find extremes |

---

## Lambda Syntax

```java
(param) -> expression
```

```java
(param) -> { 
    statements; 
}
```

---

## Important Notes

⚠️ **Streams are:**
- **Lazy** — computed only when terminal operation runs
- **Single-use** — cannot be reused after consumption
- **Non-interfering** — must not modify the source
- **Stateless** — result should not depend on mutable state