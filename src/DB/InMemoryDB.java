package DB;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryDB {
    private final Map<Integer, Employee> storage = new HashMap<>();

    private final Map<String, List<Integer>> departmentIndex = new HashMap<>();

    private final NavigableMap<Double, List<Integer>> salaryIndex = new TreeMap<>();

    private final Trie<Integer> nameIndex = new Trie<>();

    private static final int MAX_EMPLOYEES = 100;
    private final Double[] salaries = new Double[MAX_EMPLOYEES];
    private SegmentTree<Double> salarySumSegmentTree;

    public InMemoryDB(){
        Arrays.fill(salaries, 0.0);
        this.salarySumSegmentTree = new SegmentTree<>(salaries, (a, b) -> a + b);
    }

    public void put(Integer key, Employee value){
        if(key >= MAX_EMPLOYEES || key < 0){
            System.out.println("Error: Employee ID out of bounds.");
            return;
        }
        if(storage.containsKey(key)){
            Employee oldValue = storage.get(key);
            removeDepartmentIndex(key, oldValue);
            removeSalaryIndex(key, oldValue);
            nameIndex.remove(oldValue.getName().toLowerCase(), key);
        }
        storage.put(key, value);

        updateDepartmentIndex(key, value);
        updateSalaryIndex(key, value);
        nameIndex.insert(value.getName().toLowerCase(), key);

        salarySumSegmentTree.update(key, value.getSalary());
    }

    private void updateSalaryIndex(Integer key, Employee value) {
        salaryIndex.computeIfAbsent(value.getSalary(), k -> new ArrayList<>()).add(key);
    }

    private void updateDepartmentIndex(Integer key, Employee value) {
        departmentIndex.computeIfAbsent(value.getDepartment(), k -> new ArrayList<>()).add(key);
    }

    public Optional<Employee> get(Integer key){
        return Optional.ofNullable(storage.get(key));
    }

    public void delete(Integer key){
        Employee valueToRemove = storage.get(key);
        if(valueToRemove != null){
            removeDepartmentIndex(key, valueToRemove);
            removeSalaryIndex(key, valueToRemove);
            storage.remove(key);
            nameIndex.remove(valueToRemove.getName().toLowerCase(), key);
            salarySumSegmentTree.update(key, 0.0);
        }
    }

    private void removeSalaryIndex(Integer key, Employee valueToRemove) {
        List<Integer> ids = salaryIndex.get(valueToRemove.getSalary());
        if(ids != null){
            ids.remove(key);
            if(ids.isEmpty()){
                salaryIndex.remove(valueToRemove.getSalary());
            }
        }
    }

    private void removeDepartmentIndex(Integer key, Employee value){
        String department = value.getDepartment();
        List<Integer> idsInDept = departmentIndex.get(department);
        if(idsInDept != null){
            idsInDept.remove(key);
            if(idsInDept.isEmpty()){
                departmentIndex.remove(department);
            }
        }
    }

    public List<Employee> getAll(){
        return new ArrayList<>(storage.values());
    }

    public List<Employee> findByDepartment(String department){
        List<Integer> keys = departmentIndex.get(department);
        if(keys == null || keys.isEmpty()){
            return Collections.emptyList();
        }
        return keys.stream()
                .map(storage::get)
                .collect(Collectors.toList());
    }

    public List<Employee> findBySalaryRange(double minSalary, double maxSalary){
        Map<Double, List<Integer>> rangeView = salaryIndex.subMap(minSalary, true, maxSalary, true);
        return rangeView.values().stream()
                .flatMap(List::stream)
                .map(storage::get)
                .collect(Collectors.toList());
    }

    public List<Employee> findByNamePrefix(String prefix){
        Set<Integer> keys = nameIndex.findAllWithPrefix(prefix);

        return keys.stream()
                .map(storage::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public Double getSalarySumForIdRange(int startId, int endId){
        if(startId < 0 || endId >= MAX_EMPLOYEES || startId > endId){
            return 0.0;
        }
        return salarySumSegmentTree.query(startId, endId);
    }
}