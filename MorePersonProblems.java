import java.util.*;
import java.util.stream.Collectors;
import java.util.function.Function;

public class MorePersonProblems {
    public static void main(String[] args) {
        List<Person> persons = Arrays.asList(                
            new Person("John", 25, 50000, "IT"),
            new Person("Jane", 30, 60000, "HR"),
            new Person("Adam", 28, 55000, "IT"),
            new Person("Eve", 35, 70000, "Finance"),
            new Person("Mike", 40, 80000, "IT"),
            new Person("Sarah", 32, 45000, "HR"),
            new Person("David", 27, 90000, "Finance"),
            new Person("John", 45, 95000, "IT"),  // Duplicate name for testing
            new Person("Anna", 29, 52000, "Finance")
        );
        
        System.out.println("===== MORE JAVA 8 STREAM PROBLEMS (41-50) =====\n");
        
        // Problem 41: Find duplicate names
        System.out.println("41. Find duplicate names:");
        Set<String> uniqueNames = new HashSet<>();
        Set<String> duplicateNames = persons.stream()
            .map(Person::getName)
            .filter(name -> !uniqueNames.add(name))
            .collect(Collectors.toSet());
        System.out.println("  Duplicate names: " + duplicateNames);
        
        // Problem 42: Get employees with salary in top 50%
        System.out.println("\n42. Employees with salary in top 50%:");
        double medianSalary = persons.stream()
            .mapToDouble(Person::getSalary)
            .sorted()
            .skip(persons.size() / 2)
            .findFirst()
            .orElse(0.0);
        System.out.println("  Median salary: " + medianSalary);
        persons.stream()
            .filter(p -> p.getSalary() >= medianSalary)
            .forEach(p -> System.out.println("  - " + p.getName() + ": " + p.getSalary()));
        
        // Problem 43: Create a frequency map of first characters of names
        System.out.println("\n43. Frequency of first characters in names:");
        Map<Character, Long> firstCharFrequency = persons.stream()
            .map(p -> p.getName().charAt(0))
            .collect(Collectors.groupingBy(
                Function.identity(),
                Collectors.counting()
            ));
        firstCharFrequency.forEach((ch, count) -> 
            System.out.println("  '" + ch + "': " + count));
        
        // Problem 44: Find the most common department
        System.out.println("\n44. Most common department:");
        persons.stream()
            .collect(Collectors.groupingBy(
                Person::getDepartment,
                Collectors.counting()
            ))
            .entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .ifPresent(entry -> 
                System.out.println("  " + entry.getKey() + " (" + entry.getValue() + " employees)"));
        
        // Problem 45: Get employees sorted by name length then by name
        System.out.println("\n45. Employees sorted by name length then alphabetically:");
        persons.stream()
            .sorted(Comparator
                .comparingInt((Person p) -> p.getName().length())
                .thenComparing(Person::getName))
            .forEach(p -> System.out.println("  " + p.getName() + " (" + p.getName().length() + " chars)"));
        
        // Problem 46: Calculate bonus (5% of salary) for each employee
        System.out.println("\n46. Calculate 5% bonus for each employee:");
        persons.stream()
            .forEach(p -> {
                double bonus = p.getSalary() * 0.05;
                System.out.println("  " + p.getName() + ": Salary=" + p.getSalary() + 
                    ", Bonus=" + String.format("%.2f", bonus) + 
                    ", Total=" + String.format("%.2f", p.getSalary() + bonus));
            });
        
        // Problem 47: Find employees with names containing 'a' (case-insensitive)
        System.out.println("\n47. Employees with names containing 'a' (case-insensitive):");
        persons.stream()
            .filter(p -> p.getName().toLowerCase().contains("a"))
            .forEach(p -> System.out.println("  - " + p.getName()));
        
        // Problem 48: Get the salary range (max - min) by department
        System.out.println("\n48. Salary range (max - min) by department:");
        Map<String, DoubleSummaryStatistics> deptSalaryStats = persons.stream()
            .collect(Collectors.groupingBy(
                Person::getDepartment,
                Collectors.summarizingDouble(Person::getSalary)
            ));
        deptSalaryStats.forEach((dept, stats) -> {
            double range = stats.getMax() - stats.getMin();
            System.out.println("  " + dept + ": " + stats.getMin() + " to " + 
                stats.getMax() + " (range: " + range + ")");
        });
        
        // Problem 49: Create a map of department to list of ages
        System.out.println("\n49. Map of department to list of ages:");
        Map<String, List<Integer>> deptAges = persons.stream()
            .collect(Collectors.groupingBy(
                Person::getDepartment,
                Collectors.mapping(Person::getAge, Collectors.toList())
            ));
        deptAges.forEach((dept, ages) -> 
            System.out.println("  " + dept + ": " + ages));
        
        // Problem 50: Find the employee closest to average age
        System.out.println("\n50. Employee closest to average age:");
        double avgAge = persons.stream()
            .mapToInt(Person::getAge)
            .average()
            .orElse(0.0);
        System.out.println("  Average age: " + String.format("%.2f", avgAge));
        
        persons.stream()
            .min(Comparator.comparingDouble(p -> Math.abs(p.getAge() - avgAge)))
            .ifPresent(p -> System.out.println("  Closest: " + p.getName() + " (" + p.getAge() + 
                ", difference: " + String.format("%.2f", Math.abs(p.getAge() - avgAge)) + ")"));
        
        System.out.println("\n===== PROBLEMS 51-60 =====\n");
        
        // Problem 51: Group employees by salary bracket (every 20000)
        System.out.println("51. Group employees by salary bracket (every 20000):");
        Map<Integer, List<Person>> bySalaryBracket = persons.stream()
            .collect(Collectors.groupingBy(p -> (int)(p.getSalary() / 20000) * 20000));
        bySalaryBracket.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(entry -> {
                System.out.println("  " + entry.getKey() + "-" + (entry.getKey() + 19999) + ":");
                entry.getValue().forEach(p -> 
                    System.out.println("    - " + p.getName() + ": " + p.getSalary()));
            });
        
        // Problem 52: Find employees with salary greater than average of their age group
        System.out.println("\n52. Employees with salary > average of their age group:");
        Map<String, Double> ageGroupAvgSalary = persons.stream()
            .collect(Collectors.groupingBy(
                p -> p.getAge() >= 40 ? "40+" : p.getAge() >= 30 ? "30-39" : "20-29",
                Collectors.averagingDouble(Person::getSalary)
            ));
        
        System.out.println("  Age group averages:");
        ageGroupAvgSalary.forEach((group, avg) -> 
            System.out.println("    " + group + ": " + String.format("%.2f", avg)));
        
        System.out.println("\n  Employees above their age group average:");
        persons.forEach(p -> {
            String group = p.getAge() >= 40 ? "40+" : p.getAge() >= 30 ? "30-39" : "20-29";
            double groupAvg = ageGroupAvgSalary.get(group);
            if (p.getSalary() > groupAvg) {
                System.out.println("    - " + p.getName() + " (" + group + "): " + 
                    p.getSalary() + " > " + String.format("%.2f", groupAvg));
            }
        });
        
        // Problem 53: Get the longest name
        System.out.println("\n53. Longest name(s):");
        Optional<Integer> maxLength = persons.stream()
            .map(p -> p.getName().length())
            .max(Integer::compare);
        
        maxLength.ifPresent(length -> {
            System.out.println("  Length: " + length);
            persons.stream()
                .filter(p -> p.getName().length() == length)
                .forEach(p -> System.out.println("  - " + p.getName()));
        });
        
        // Problem 54: Create a custom comparator that sorts by department then salary descending
        System.out.println("\n54. Sort by department then salary (descending):");
        persons.stream()
            .sorted(Comparator
                .comparing(Person::getDepartment)
                .thenComparing(Comparator.comparingDouble(Person::getSalary).reversed()))
            .forEach(p -> System.out.println("  " + p.getDepartment() + " - " + 
                p.getName() + ": " + p.getSalary()));
        
        // Problem 55: Find all unique combinations of department and age
        System.out.println("\n55. Unique department-age combinations:");
        Set<String> uniqueCombos = persons.stream()
            .map(p -> p.getDepartment() + "-" + p.getAge())
            .collect(Collectors.toSet());
        uniqueCombos.forEach(combo -> System.out.println("  - " + combo));
        
        // Problem 56: Calculate cumulative salary
        System.out.println("\n56. Cumulative salary (running total):");
        final double[] cumulative = {0};
        persons.stream()
            .sorted(Comparator.comparingDouble(Person::getSalary))
            .forEach(p -> {
                cumulative[0] += p.getSalary();
                System.out.println("  " + p.getName() + ": " + p.getSalary() + 
                    " (Cumulative: " + String.format("%.2f", cumulative[0]) + ")");
            });
        
        // Problem 57: Group by department and get min, max, avg salary
        System.out.println("\n57. Department salary statistics (min, max, avg):");
        deptSalaryStats.forEach((dept, stats) -> {
            System.out.println("  " + dept + ":");
            System.out.println("    Min: " + stats.getMin());
            System.out.println("    Max: " + stats.getMax());
            System.out.println("    Avg: " + String.format("%.2f", stats.getAverage()));
            System.out.println("    Count: " + stats.getCount());
        });
        
        // Problem 58: Find employees whose name is palindrome
        System.out.println("\n58. Employees with palindrome names:");
        persons.stream()
            .filter(p -> {
                String name = p.getName().toLowerCase();
                return new StringBuilder(name).reverse().toString().equals(name);
            })
            .forEach(p -> System.out.println("  - " + p.getName()));
        
        // Problem 59: Calculate tax for each employee (20% tax if salary > 60000, else 10%)
        System.out.println("\n59. Calculate tax for each employee:");
        persons.stream()
            .forEach(p -> {
                double taxRate = p.getSalary() > 60000 ? 0.20 : 0.10;
                double tax = p.getSalary() * taxRate;
                double netSalary = p.getSalary() - tax;
                System.out.println("  " + p.getName() + ": Gross=" + p.getSalary() + 
                    ", Tax=" + String.format("%.2f", tax) + " (" + (taxRate * 100) + "%)" +
                    ", Net=" + String.format("%.2f", netSalary));
            });
        
        // Problem 60: Find the median salary
        System.out.println("\n60. Median salary:");
        List<Double> sortedSalaries = persons.stream()
            .map(Person::getSalary)
            .sorted()
            .collect(Collectors.toList());
        
        int size = sortedSalaries.size();
        if (size % 2 == 0) {
            double median = (sortedSalaries.get(size/2 - 1) + sortedSalaries.get(size/2)) / 2;
            System.out.println("  Median: " + median);
        } else {
            System.out.println("  Median: " + sortedSalaries.get(size/2));
        }
        
        System.out.println("\n===== ALL PROBLEMS (41-60) SOLVED SUCCESSFULLY =====");
    }
}