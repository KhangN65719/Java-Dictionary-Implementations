# Java Dictionary Implementations

Two generic dictionary implementations behind a shared Java interface: a binary search tree and a hash table. Developed for CS 342 at the University of Illinois Chicago.

## What this demonstrates

- Generic types and a shared dictionary contract for insertion, lookup, deletion, size, and iteration.
- A binary search tree with recursive operations, successor-based deletion, and breadth-first key iteration.
- An open-addressed hash table with linear probing, tombstones for deletion, and capacity growth at a 0.5 load threshold.
- JUnit tests shared across both implementations.

## Run the tests

Requires a JDK compatible with Java 11 and Maven.

```sh
mvn test
```

The existing suite runs **30 tests** across `BSTTest` and `HashMapTest`; all passed during portfolio preparation. The project is a data-structure library, so there is no application entry point.

## Structure

- `src/main/java/ProjOneDictionary.java` — common API
- `src/main/java/BinarySearchTreeDict.java` — tree implementation
- `src/main/java/HashMapDict.java` — hash-table implementation
- `src/test/java/DictionaryTest.java` — shared behavioral tests
- `src/test/java/BSTTest.java` and `HashMapTest.java` — concrete test suites

## Design tradeoffs and limitations

The tree is not self-balancing, so ordered insertions can produce linear-height trees. Hash-map iteration follows bucket order, while tree iteration is breadth-first. The existing tests cover basic dictionary operations; they do not establish correctness for every edge case. In particular, minimum-integer hash codes and repeated insert/delete cycles need further coverage, and exhausted tree iteration currently returns null.

## Background

Prepared from the original CS 342 Project 1 source. The implementation and existing tests are preserved; this portfolio version adds documentation and excludes generated files.
