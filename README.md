# 🗄️ In-Memory Database (Java)

An advanced **In-Memory Database** implementation in **Java**, featuring multiple indexing strategies and a **Segment Tree** for efficient aggregate queries.

This project demonstrates how core data structures—**Trie**, **HashMap**, **TreeMap**, and **Segment Tree**—can be combined to build a lightweight, high-performance data management system.

---

## 📘 Overview

This project provides a lightweight, high-performance **in-memory database** designed for learning, experimentation, and small-scale applications.  
It stores `Employee` objects and supports queries across multiple dimensions:

- By **ID**
- By **Department**
- By **Salary Range**
- By **Name Prefix**
- By **Salary Sum (ID Range)** using Segment Tree

The design combines **index-based searching** and **range-based aggregation**, similar to optimizations found in modern database systems.

---

## 🧩 Components

### **1. `InMemoryDB`**
The core database engine that manages all CRUD operations and maintains multiple indexes.

**Responsibilities:**
- Store and retrieve `Employee` objects
- Maintain secondary indexes
- Manage a `SegmentTree<Double>` for range-sum queries
- Handle updates and index synchronization

| Index Type   | Data Structure                  | Purpose                         |
|---------------|----------------------------------|----------------------------------|
| Department    | `HashMap<String, List<Integer>>` | Fast department-wise lookup      |
| Salary Range  | `TreeMap<Double, List<Integer>>` | Range-based salary queries       |
| Name Prefix   | `Trie<Integer>`                 | Efficient prefix-based search    |
| Salary Sum    | `SegmentTree<Double>`           | Range-based salary aggregation   |

---

### **2. `Trie<K>`**
A generic trie (prefix tree) used for fast name-based prefix searches.

**Supported Operations:**
- `insert(String word, K key)`
- `remove(String word, K key)`
- `findAllWithPrefix(String prefix)`

Used in `InMemoryDB` to efficiently retrieve all employees whose names start with a given prefix.

---

### **3. `SegmentTree<E>`**
A generic segment tree supporting efficient **range queries** and **point updates**.

**Time Complexity:**
- Query → O(log n)
- Update → O(log n)

Example operation: sum of salaries between employee IDs `[start, end]`.

---

### **4. `Employee`**
A simple data class representing an employee record.

```java
public class Employee implements Comparable<Employee> {
    private final int id;
    private final String name;
    private final String department;
    private final double salary;
    // Constructor, getters, equals, hashCode, compareTo, toString
}
```

---

## ⚙️ Features

- ✅ Fast CRUD operations
- ✅ Multiple secondary indexes
- ✅ Range-based salary sum queries
- ✅ Prefix-based name search (Trie)
- ✅ Department and salary indexing
- ✅ Automatic index updates on modification
- ✅ Error handling for invalid IDs

---

## 🧮 Example Usage

```java
public static void main(String[] args) {
    InMemoryDB db = new InMemoryDB();

    db.put(0, new Employee(0, "Alice", "Engineering", 120000));
    db.put(1, new Employee(1, "Bob", "Marketing", 95000));
    db.put(2, new Employee(2, "Charlie", "Engineering", 90000));
    db.put(3, new Employee(3, "David", "HR", 110000));
    db.put(4, new Employee(4, "Eve", "Engineering", 150000));

    System.out.println("Salary sum for IDs 1–3: " + db.getSalarySumForIdRange(1, 3));
    System.out.println("Find by department (Engineering): " + db.findByDepartment("Engineering"));
    System.out.println("Find by salary range (90k–120k): " + db.findBySalaryRange(90000, 120000));
    System.out.println("Find by name prefix ('A'): " + db.findByNamePrefix("A"));
}
```

---

## 🧠 Design Highlights

- **Generic Segment Tree:** Supports any associative operation via `BinaryOperator<E>`
- **Dynamic Index Updates:** Automatically synchronizes indexes on insert, update, or delete
- **Trie-Based Search:** Sublinear prefix lookup performance
- **Immutable Query Results:** Prevents unintended side effects

---

## 🧾 Example Output

```
--- Initializing In-Memory Database ---

--- Step 1: Populating Data (Testing put) ---
Get Employee with ID 3: DB.Employee{id=3, name='Charlie', department='Engineering', salary=90000.0}

Updating Bob's (ID 2) salary...
Get Bob after update: DB.Employee{id=2, name='Bob', department='Marketing', salary=105000.0}

Deleting Alicia (ID 6)...
Get Alicia after delete: null

--- Step 3: Sorting ---
Sorted by Salary DESCENDING:
Eve - 150000
Alice - 120000
David - 110000
Bob - 105000
Charlie - 90000

--- Step 4: Indexing Queries ---
Find by Department 'Engineering':
Alice, Charlie, Eve

Find by Salary Range $100,000 - $130,000:
Alice, Bob, David

Find by Name Prefix 'ali':
Alice, Alicia, Allison

Get Salary Sum for ID Range [2, 4]:
Sum: 305000
```

---

## 🧰 Tech Stack

- **Language:** Java 17+
- **Data Structures:** `HashMap`, `TreeMap`, `Trie`, `SegmentTree`, `List`, `Set`
- **Programming Paradigm:** Object-Oriented with Functional Components
- **Time Complexities:**
    - CRUD → O(1) average
    - Range Query → O(log n)
    - Prefix Query → O(m + k)

---

## 🚀 Future Improvements

- [ ] Persistent storage (save/load to file or DB)
- [ ] Thread-safe (concurrent) version
- [ ] Batch updates and transactions
- [ ] Segment Tree extensions (min/max queries)
- [ ] Unit testing with JUnit

---

## 📦 Getting Started

### Prerequisites
- Java 17 or higher
- Maven or Gradle (optional)

### Clone the Repository
```bash
git clone https://github.com/abdkhaleel/no-sql-dbms-impl.git
cd no-sql-dbms-impl
```

### Compile and Run
```bash
javac *.java
java Main
```

---

## 🏷️ License

This project is licensed under the **MIT License**.  
You may use, modify, and distribute it with proper attribution.

---

## 👨‍💻 Author

**Abdul Khaleel**  
Developer passionate about database systems and algorithmic data structures.

---

## 🤝 Contributing

Contributions, issues, and feature requests are welcome!  
Feel free to check the [issues page](https://github.com/abdkhaleel/no-sql-dbms-impl/issues).

---

## ⭐ Show Your Support

If you found this project helpful, please give it a ⭐️ on [GitHub](https://github.com/abdkhaleel/no-sql-dbms-impl)!