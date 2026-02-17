import java.util.*;
import java.util.stream.Collectors;

class Person {
    private String name;
    private int age;
    private double salary;
    private String department;
    
    public Person(String name, int age, double salary, String department) {
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.department = department;
    }
    
    public String getName() { return name; }
    public int getAge() { return age; }
    public double getSalary() { return salary; }
    public String getDepartment() { return department; }
    
    @Override
    public String toString() {
        return name + " (" + age + ", " + salary + ", " + department + ")";
    }
}

public class AllPersonProblems {
    public static void main(String[] args) {
        List<Person> persons = Arrays.asList(                
            new Person("John", 25, 50000, "IT"),
            new Person("Jane", 30, 60000, "HR"),
            new Person("Adam", 28, 55000, "IT"),
            new Person("Eve", 35, 70000, "Finance"),
            new Person("Mike", 40, 80000, "IT"),
            new Person("Sarah", 32, 45000, "HR"),
            new Person("David", 27, 90000, "Finance")
        );
        
        System.out.println("===== JAVA 8 STREAM PROBLEMS SOLUTIONS =====\n");
        
        // Problem 1: Filter IT Department Employees
        System.out.println("1. IT Department Employees:");
        List<Person> itEmployees = persons.stream()
            .filter(p -> "IT".equals(p.getDepartment()))
            .collect(Collectors.toList());
        itEmployees.forEach(p -> System.out.println("  - " + p.getName()));
        
        // Problem 2: Get names of all employees
      
        List<String> employeeNames = persons.stream()
            .map(Person::getName)
            .collect(Collectors.toList());
        employeeNames.forEach(name -> System.out.println("  - " + name));
        
        // Problem 3: Get average salary of all employees
        System.out.println("\n3. Average Salary:");
        double averageSalary = persons.stream()
            .mapToDouble(Person::getSalary)
            .average()
            .orElse(0.0);
        System.out.println("  Average: " + String.format("%.2f", averageSalary));
        
        // Problem 4: Find employee with maximum salary
        System.out.println("\n4. Employee with Maximum Salary:");
        persons.stream()
            .max(Comparator.comparingDouble(Person::getSalary))
            .ifPresent(p -> System.out.println("  " + p.getName() + " - " + p.getSalary()));
        
        // Problem 5: Group employees by department
        System.out.println("\n5. Employees Grouped by Department:");
        Map<String, List<Person>> employeesByDept = persons.stream()
            .collect(Collectors.groupingBy(Person::getDepartment));
        employeesByDept.forEach((dept, empList) -> {
            System.out.println("  " + dept + ":");
            empList.forEach(p -> System.out.println("    - " + p.getName()));
        });
        
        // Problem 6: Get total salary by department
        System.out.println("\n6. Total Salary by Department:");
        Map<String, Double> totalSalaryByDept = persons.stream()
            .collect(Collectors.groupingBy(
                Person::getDepartment,
                Collectors.summingDouble(Person::getSalary)
            ));
        totalSalaryByDept.forEach((dept, total) -> 
            System.out.println("  " + dept + ": " + total));
        
        // Problem 7: Find employees older than 30
        System.out.println("\n7. Employees Older than 30:");
        List<Person> olderThan30 = persons.stream()
            .filter(p -> p.getAge() > 30)
            .collect(Collectors.toList());
        olderThan30.forEach(p -> System.out.println("  - " + p.getName() + " (" + p.getAge() + ")"));
        
        // Problem 8: Sort employees by age
        System.out.println("\n8. Employees Sorted by Age:");
        List<Person> sortedByAge = persons.stream()
            .sorted(Comparator.comparingInt(Person::getAge))
            .collect(Collectors.toList());
        sortedByAge.forEach(p -> System.out.println("  " + p.getName() + ": " + p.getAge()));
        
        // Problem 9: Check if all employees earn more than 40000
        System.out.println("\n9. All employees earn > 40000?");
        boolean allEarnMoreThan40k = persons.stream()
            .allMatch(p -> p.getSalary() > 40000);
        System.out.println("  Result: " + allEarnMoreThan40k);
        
        // Problem 10: Get distinct departments
        System.out.println("\n10. Distinct Departments:");
        List<String> distinctDepts = persons.stream()
            .map(Person::getDepartment)
            .distinct()
            .collect(Collectors.toList());
        distinctDepts.forEach(dept -> System.out.println("  - " + dept));
        
        // Problem 11: Get average age by department
        System.out.println("\n11. Average Age by Department:");
        Map<String, Double> avgAgeByDept = persons.stream()
            .collect(Collectors.groupingBy(
                Person::getDepartment,
                Collectors.averagingInt(Person::getAge)
            ));
        avgAgeByDept.forEach((dept, avgAge) -> 
            System.out.println("  " + dept + ": " + String.format("%.1f", avgAge)));
        
        // Problem 12: Find first employee in IT department
        System.out.println("\n12. First IT Employee:");
        persons.stream()
            .filter(p -> "IT".equals(p.getDepartment()))
            .findFirst()
            .ifPresent(p -> System.out.println("  " + p.getName()));
        
        // Problem 13: Count employees in each department
        System.out.println("\n13. Employee Count by Department:");
        Map<String, Long> employeeCountByDept = persons.stream()
            .collect(Collectors.groupingBy(
                Person::getDepartment,
                Collectors.counting()
            ));
        employeeCountByDept.forEach((dept, count) -> 
            System.out.println("  " + dept + ": " + count + " employees"));
        
        // Problem 14: Get names sorted alphabetically
        System.out.println("\n14. Names Sorted Alphabetically:");
        List<String> sortedNames = persons.stream()
            .map(Person::getName)
            .sorted()
            .collect(Collectors.toList());
        sortedNames.forEach(name -> System.out.println("  - " + name));
        
        // Problem 15: Get second highest salary
        System.out.println("\n15. Second Highest Salary:");
        persons.stream()
            .map(Person::getSalary)
            .distinct()
            .sorted(Comparator.reverseOrder())
            .skip(1)
            .findFirst()
            .ifPresent(salary -> System.out.println("  " + salary));
        
        // Problem 16: Partition employees by age (above/below 30)
        System.out.println("\n16. Partition employees by age (above/below 30):");
        Map<Boolean, List<Person>> partitionedByAge = persons.stream()
            .collect(Collectors.partitioningBy(p -> p.getAge() >= 30));
        System.out.println("  Employees 30+:");
        partitionedByAge.get(true).forEach(p -> System.out.println("    - " + p.getName()));
        System.out.println("  Employees below 30:");
        partitionedByAge.get(false).forEach(p -> System.out.println("    - " + p.getName()));
        
        // Problem 17: Get concatenated names of all employees
        System.out.println("\n17. Concatenated names of all employees:");
        String concatenatedNames = persons.stream()
            .map(Person::getName)
            .collect(Collectors.joining(", "));
        System.out.println("  " + concatenatedNames);
        
        // Problem 18: Find the youngest employee
        System.out.println("\n18. Youngest employee:");
        persons.stream()
            .min(Comparator.comparingInt(Person::getAge))
            .ifPresent(p -> System.out.println("  " + p.getName() + " (" + p.getAge() + ")"));
        
        // Problem 19: Get salary statistics using summary statistics
        System.out.println("\n19. Salary Statistics:");
        DoubleSummaryStatistics salaryStats = persons.stream()
            .mapToDouble(Person::getSalary)
            .summaryStatistics();
        System.out.println("  Count: " + salaryStats.getCount());
        System.out.println("  Min: " + salaryStats.getMin());
        System.out.println("  Max: " + salaryStats.getMax());
        System.out.println("  Average: " + String.format("%.2f", salaryStats.getAverage()));
        System.out.println("  Sum: " + salaryStats.getSum());
        
        // Problem 20: Get employees with salary between 50000 and 70000
        System.out.println("\n20. Employees with salary between 50000 and 70000:");
        persons.stream()
            .filter(p -> p.getSalary() >= 50000 && p.getSalary() <= 70000)
            .forEach(p -> System.out.println("  - " + p.getName() + ": " + p.getSalary()));
        
        // Problem 21: Group employee names by department
        System.out.println("\n21. Group employee names by department:");
        Map<String, List<String>> namesByDept = persons.stream()
            .collect(Collectors.groupingBy(
                Person::getDepartment,
                Collectors.mapping(Person::getName, Collectors.toList())
            ));
        namesByDept.forEach((dept, names) -> 
            System.out.println("  " + dept + ": " + String.join(", ", names)));
        
        // Problem 22: Get total salary expenditure
        System.out.println("\n22. Total salary expenditure:");
        double totalSalary = persons.stream()
            .mapToDouble(Person::getSalary)
            .sum();
        System.out.println("  Total: " + totalSalary);
        
        // Problem 23: Find if any employee earns more than 80000
        System.out.println("\n23. Check if any employee earns more than 80000:");
        boolean anyEarnsMoreThan80k = persons.stream()
            .anyMatch(p -> p.getSalary() > 80000);
        System.out.println("  Result: " + anyEarnsMoreThan80k);
        if (anyEarnsMoreThan80k) {
            System.out.println("  Employees earning > 80000:");
            persons.stream()
                .filter(p -> p.getSalary() > 80000)
                .forEach(p -> System.out.println("    - " + p.getName() + ": " + p.getSalary()));
        }
        
        // Problem 24: Get employees sorted by salary in descending order
        System.out.println("\n24. Employees sorted by salary (descending):");
        persons.stream()
            .sorted(Comparator.comparingDouble(Person::getSalary).reversed())
            .forEach(p -> System.out.println("  " + p.getName() + ": " + p.getSalary()));
        
        // Problem 25: Get department with highest total salary
        System.out.println("\n25. Department with highest total salary:");
        Map<String, Double> deptTotalSalary = persons.stream()
            .collect(Collectors.groupingBy(
                Person::getDepartment,
                Collectors.summingDouble(Person::getSalary)
            ));
        deptTotalSalary.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .ifPresent(entry -> 
                System.out.println("  " + entry.getKey() + ": " + entry.getValue()));
        
        // Problem 26: Create a map of name to age
        System.out.println("\n26. Map of Name to Age:");
        Map<String, Integer> nameToAge = persons.stream()
            .collect(Collectors.toMap(
                Person::getName,
                Person::getAge
            ));
        nameToAge.forEach((name, age) -> 
            System.out.println("  " + name + ": " + age));
        
        // Problem 27: Get employees with salary greater than department average
        System.out.println("\n27. Employees earning more than their department average:");
        Map<String, Double> deptAvgSalary = persons.stream()
            .collect(Collectors.groupingBy(
                Person::getDepartment,
                Collectors.averagingDouble(Person::getSalary)
            ));
        persons.stream()
            .filter(p -> p.getSalary() > deptAvgSalary.get(p.getDepartment()))
            .forEach(p -> System.out.println("  " + p.getName() + " (" + p.getDepartment() + 
                "): " + p.getSalary() + " > " + String.format("%.2f", deptAvgSalary.get(p.getDepartment()))));
        
        // Problem 28: Find the oldest employee in each department
        System.out.println("\n28. Oldest employee in each department:");
        Map<String, Optional<Person>> oldestByDept = persons.stream()
            .collect(Collectors.groupingBy(
                Person::getDepartment,
                Collectors.maxBy(Comparator.comparingInt(Person::getAge))
            ));
        oldestByDept.forEach((dept, oldest) -> 
            oldest.ifPresent(p -> 
                System.out.println("  " + dept + ": " + p.getName() + " (" + p.getAge() + ")")));
        
        // Problem 29: Get comma separated department names
        System.out.println("\n29. Comma separated department names:");
        String deptNames = persons.stream()
            .map(Person::getDepartment)
            .distinct()
            .collect(Collectors.joining(", "));
        System.out.println("  Departments: " + deptNames);
        
        // Problem 30: Calculate salary increment by 10% for all
        System.out.println("\n30. Calculate 10% salary increment:");
        System.out.println("  Salaries after 10% increment:");
        persons.forEach(p -> {
            double newSalary = p.getSalary() * 1.10;
            System.out.println("  " + p.getName() + ": " + p.getSalary() + " → " + 
                String.format("%.2f", newSalary));
        });
        double totalAfterIncrement = persons.stream()
            .mapToDouble(p -> p.getSalary() * 1.10)
            .sum();
        System.out.println("  Total after increment: " + String.format("%.2f", totalAfterIncrement));
        
        // Problem 31: Find employees whose name starts with 'J'
        System.out.println("\n31. Employees whose name starts with 'J':");
        persons.stream()
            .filter(p -> p.getName().startsWith("J"))
            .forEach(p -> System.out.println("  - " + p.getName()));
        
        // Problem 32: Get count of employees in age groups
        System.out.println("\n32. Employees count by age groups:");
        Map<String, Long> ageGroupCount = persons.stream()
            .collect(Collectors.groupingBy(p -> {
                if (p.getAge() >= 20 && p.getAge() <= 29) return "20-29";
                else if (p.getAge() >= 30 && p.getAge() <= 39) return "30-39";
                else return "40+";
            }, Collectors.counting()));
        ageGroupCount.forEach((group, count) -> 
            System.out.println("  " + group + ": " + count + " employees"));
        
        // Problem 33: Get top 3 highest paid employees
        System.out.println("\n33. Top 3 highest paid employees:");
        List<Person> top3Earners = persons.stream()
            .sorted(Comparator.comparingDouble(Person::getSalary).reversed())
            .limit(3)
            .collect(Collectors.toList());
        for (int i = 0; i < top3Earners.size(); i++) {
            Person p = top3Earners.get(i);
            System.out.println("  " + (i + 1) + ". " + p.getName() + " - " + p.getSalary());
        }
        
        // Problem 34: Calculate average salary of employees aged above 30
        System.out.println("\n34. Average salary of employees aged above 30:");
        double avgSalaryAbove30 = persons.stream()
            .filter(p -> p.getAge() > 30)
            .mapToDouble(Person::getSalary)
            .average()
            .orElse(0.0);
        System.out.println("  Average: " + String.format("%.2f", avgSalaryAbove30));
        
        // Problem 35: Check if all department names are uppercase
        System.out.println("\n35. Check if all department names are uppercase:");
        boolean allDeptUppercase = persons.stream()
            .map(Person::getDepartment)
            .allMatch(dept -> dept.equals(dept.toUpperCase()));
        System.out.println("  Result: " + allDeptUppercase);
        
        // Problem 36: Find employee with shortest name
        System.out.println("\n36. Employee with shortest name:");
        persons.stream()
            .min(Comparator.comparingInt(p -> p.getName().length()))
            .ifPresent(p -> System.out.println("  " + p.getName() + " (" + p.getName().length() + " characters)"));
        
        // Problem 37: Group employees by salary range
        System.out.println("\n37. Group employees by salary range:");
        Map<String, List<Person>> bySalaryRange = persons.stream()
            .collect(Collectors.groupingBy(p -> {
                if (p.getSalary() < 50000) return "Below 50k";
                else if (p.getSalary() <= 70000) return "50k-70k";
                else return "Above 70k";
            }));
        bySalaryRange.forEach((range, empList) -> {
            System.out.println("  " + range + ":");
            empList.forEach(p -> System.out.println("    - " + p.getName() + ": " + p.getSalary()));
        });
        
        // Problem 38: Get names in uppercase
        System.out.println("\n38. Employee names in uppercase:");
        List<String> uppercaseNames = persons.stream()
            .map(Person::getName)
            .map(String::toUpperCase)
            .collect(Collectors.toList());
        uppercaseNames.forEach(name -> System.out.println("  - " + name));
        
        // Problem 39: Find if there's a duplicate department
        System.out.println("\n39. Check for duplicate departments:");
        long distinctDeptCount = persons.stream()
            .map(Person::getDepartment)
            .distinct()
            .count();
        long totalDeptCount = persons.stream()
            .map(Person::getDepartment)
            .count();
        boolean hasDuplicates = totalDeptCount > distinctDeptCount;
        System.out.println("  Has duplicate departments: " + hasDuplicates);
        
        // Problem 40: Create custom string representation
        System.out.println("\n40. Custom string representation:");
        String customRep = persons.stream()
            .map(p -> p.getName() + "[" + p.getDepartment().charAt(0) + "]")
            .collect(Collectors.joining("; "));
        System.out.println("  " + customRep);
        
        System.out.println("\n===== ALL 40 PROBLEMS SOLVED SUCCESSFULLY =====");
    }
}