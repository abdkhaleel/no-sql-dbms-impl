# In-Memory Database (Java)

An advanced in-memory database implementation in **Java**, featuring multiple indexing strategies and a **Segment Tree** for efficient aggregate queries.  
This project demonstrates the integration of data structures like **Trie**, **HashMap**, **TreeMap**, and **Segment Tree** to support fast, complex lookups and analytics.

---

## 📘 Overview

This project provides a lightweight, high-performance **in-memory database** designed for learning, experimentation, and small-scale applications.  
It stores `Employee` objects and supports queries across multiple dimensions:

- **By ID**
- **By Department**
- **By Salary Range**
- **By Name Prefix**
- **By Salary Sum over ID Range** (via Segment Tree)

The design combines **index-based searching** and **range-based aggregation**, similar to concepts in modern databases (e.g., secondary indexing and range query optimization).

---

## 🧩 Components

### 1. `InMemoryDB`
The core database engine that manages all CRUD operations and maintains secondary indexes.

**Key Responsibilities:**
- Store and retrieve `Employee` objects.
- Maintain **department**, **salary**, and **name** indexes.
- Manage a **Segment Tree** for O(log n) range-sum queries on salaries.
- Handle updates gracefully with index synchronization.

**Indexes:**
| Index Type | Data Structure | Purpose |
|-------------|----------------|----------|
| Department  | `HashMap<String, List<Integer>>` | Fast department-wise lookup |
| Salary Range| `TreeMap<Double, List<Integer>>` | Range-based salary queries |
| Name Prefix | `Trie<Integer>` | Efficient prefix search |
| Salary Sum  | `SegmentTree<Double>` | Range-based salary aggregation |

---

### 2. `Trie<K>`
A generic trie (prefix tree) implementation used for fast name-based prefix searches.

**Operations Supported:**
- `insert(String word, K key)`
- `remove(String word, K key)`
- `findAllWithPrefix(String prefix)`

Used in `InMemoryDB` to retrieve all employees whose names start with a given prefix.

---

### 3. `SegmentTree<E>`
A generic segment tree supporting:
- **Range queries** (e.g., salary sum for employee ID range)
- **Point updates** (e.g., update salary for a specific employee)

**Time Complexity:**
- Query: O(log n)
- Update: O(log n)

This enables analytics-style queries over the in-memory dataset.

---

### 4. `Employee`
A simple data class representing a single employee record.

```java
public class Employee {
    private int id;
    private String name;
    private String department;
    private double salary;
    // Getters, setters, constructor, toString()
}
