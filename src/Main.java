import java.util.*;
import DB.*;
public class Main {
    public static void main(String[] args) {
        System.out.println("--- Initializing In-Memory Database ---");
        InMemoryDB db = new InMemoryDB();

        // --- Data Population ---
        System.out.println("\n--- Step 1: Populating Data (Testing put) ---");
        db.put(5, new Employee(5, "Eve", "Engineering", 150000));
        db.put(2, new Employee(2, "Bob", "Marketing", 95000));
        db.put(3, new Employee(3, "Charlie", "Engineering", 90000));
        db.put(1, new Employee(1, "Alice", "Engineering", 120000));
        db.put(4, new Employee(4, "David", "HR", 110000));
        db.put(6, new Employee(6, "Alicia", "Sales", 85000));

        // --- Session 1 & 2: Core Operations (get, update, delete) ---
        System.out.println("\n--- Step 2: Core Operations ---");
        System.out.println("Get Employee with ID 3: " + db.get(3).orElse(null));
        System.out.println("Updating Bob's (ID 2) salary...");
        db.put(2, new Employee(2, "Bob", "Marketing", 105000)); // Update
        System.out.println("Get Bob after update: " + db.get(2).orElse(null));
        System.out.println("Deleting Alicia (ID 6)...");
        db.delete(6);
        System.out.println("Get Alicia after delete: " + db.get(6).orElse(null));

        // --- Session 3 & 4: Sorting (Comparable & Comparator) ---
        System.out.println("\n--- Step 3: Sorting ---");
        List<Employee> allEmployees = db.getAll();
        System.out.println("Original Order (from HashMap):");
        allEmployees.forEach(System.out::println);

        System.out.println("\nSorted by ID (Natural Order via Comparable):");
        Collections.sort(allEmployees);
        allEmployees.forEach(System.out::println);

        System.out.println("\nSorted by Name (Custom Comparator):");
        allEmployees.sort(Comparator.comparing(Employee::getName));
        allEmployees.forEach(System.out::println);

        System.out.println("\nSorted by Salary DESCENDING (Custom Comparator):");
        allEmployees.sort(Comparator.comparing(Employee::getSalary).reversed());
        allEmployees.forEach(System.out::println);

        // --- Session 5: Secondary Index Query ---
        System.out.println("\n--- Step 4: Indexing Queries ---");
        System.out.println("\nFind by Department 'Engineering' (Hash Index):");
        db.findByDepartment("Engineering").forEach(System.out::println);

        // --- Session 6: Ordered Index Query ---
        System.out.println("\nFind by Salary Range $100,000 - $130,000 (Tree Index):");
        db.findBySalaryRange(100000, 130000).forEach(System.out::println);

        // --- Session 7: Advanced Trie Query ---
        System.out.println("\n--- Step 5: Advanced Data Structure Queries ---");
        System.out.println("\nFind by Name Prefix 'ali' (Trie Index):");
        db.put(7, new Employee(7, "Allison", "Marketing", 115000)); // Add one more to test
        db.findByNamePrefix("ali").forEach(System.out::println);

        // --- Session 8: Advanced Segment Tree Query ---
        System.out.println("\nGet Salary Sum for ID Range [2, 4] (Segment Tree Index):");
        // Bob(105k) + Charlie(90k) + David(110k) = 305k
        double sum = db.getSalarySumForIdRange(2, 4);
        System.out.printf("Sum: %.0f\n", sum);

        System.out.println("\n--- All Tests Complete ---");
    }
}