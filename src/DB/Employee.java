package DB;

import java.util.Objects;

public class Employee implements Comparable<Employee> {
    private final int id;
    private final String name;
    private final String department;
    private final double salary;

    public Employee(int id, String name, String department, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return getId() == employee.getId() && Double.compare(getSalary(), employee.getSalary()) == 0 && Objects.equals(getName(), employee.getName()) && Objects.equals(getDepartment(), employee.getDepartment());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDepartment(), getSalary());
    }

    @Override
    public String toString() {
        return "DB.Employee{" +
                "\n\tid=" + id +
                ",\n\tname='" + name + '\'' +
                ",\n\tdepartment='" + department + '\'' +
                ",\n\tsalary=" + salary +
                "\n}";
    }

    public int compareTo(Employee other){
        return Integer.compare(this.id, other.id);
    }
}
