import java.util.*;
import java.util.stream.*;
import java.util.function.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

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

public class CompleteJava8Problems {
    public static void main(String[] args) {
        List<Person> persons = Arrays.asList(                
            new Person("John", 25, 50000, "IT"),
            new Person("Jane", 30, 60000, "HR"),
            new Person("Adam", 28, 55000, "IT"),
            new Person("Eve", 35, 70000, "Finance"),
            new Person("Mike", 40, 80000, "IT"),
            new Person("Sarah", 32, 45000, "HR"),
            new Person("David", 27, 90000, "Finance"),
            new Person("Anna", 29, 52000, "Finance"),
            new Person("Robert", 45, 95000, "IT"),
            new Person("Lisa", 38, 75000, "HR"),
            new Person("Tom", 31, 65000, "IT"),
            new Person("Emily", 26, 48000, "Finance")
        );
        
        System.out.println("===== COMPLETE JAVA 8 STREAM API MASTER CLASS =====");
        System.out.println("Total Employees: " + persons.size() + "\n");
        
        // ========== SECTION 1: FILTERING OPERATIONS ==========
        System.out.println("\n=== SECTION 1: FILTERING OPERATIONS ===");
        
        // Problem 61: Multiple filter conditions
        System.out.println("\n61. IT employees aged 25-35 with salary > 50000:");
        List<Person> filtered = persons.stream()
            .filter(p -> "IT".equals(p.getDepartment()))
            .filter(p -> p.getAge() >= 25 && p.getAge() <= 35)
            .filter(p -> p.getSalary() > 50000)
            .collect(Collectors.toList());
        filtered.forEach(p -> System.out.println("  - " + p));
        
        // Problem 62: Filter using Predicate
        System.out.println("\n62. Using Predicate for filtering:");
        Predicate<Person> highEarner = p -> p.getSalary() > 70000;
        Predicate<Person> experienced = p -> p.getAge() > 35;
        
        List<Person> seniorHighEarners = persons.stream()
            .filter(highEarner.and(experienced))
            .collect(Collectors.toList());
        seniorHighEarners.forEach(p -> System.out.println("  - " + p.getName() + 
            " (Age: " + p.getAge() + ", Salary: " + p.getSalary() + ")"));
        
        // ========== SECTION 2: MAPPING OPERATIONS ==========
        System.out.println("\n\n=== SECTION 2: MAPPING OPERATIONS ===");
        
        // Problem 63: Transform to DTO
        System.out.println("\n63. Transform Person to EmployeeDTO:");
        List<EmployeeDTO> dtos = persons.stream()
            .map(p -> new EmployeeDTO(p.getName(), p.getDepartment(), p.getSalary()))
            .collect(Collectors.toList());
        dtos.forEach(dto -> System.out.println("  " + dto));
        
        // Problem 64: FlatMap - Get all characters from names
        System.out.println("\n64. All unique characters from employee names:");
        List<Character> uniqueChars = persons.stream()
            .flatMap(p -> p.getName().chars().mapToObj(c -> (char)c))
            .distinct()
            .sorted()
            .collect(Collectors.toList());
        System.out.println("  Unique characters: " + uniqueChars);
        
        // ========== SECTION 3: COLLECTORS ADVANCED ==========
        System.out.println("\n\n=== SECTION 3: ADVANCED COLLECTORS ===");
        
        // Problem 65: Grouping with downstream collector
        System.out.println("\n65. Department with employee count and total salary:");
        Map<String, Map<String, Object>> deptStats = persons.stream()
            .collect(Collectors.groupingBy(
                Person::getDepartment,
                Collectors.collectingAndThen(
                    Collectors.toList(),
                    list -> {
                        Map<String, Object> stats = new HashMap<>();
                        stats.put("count", list.size());
                        stats.put("totalSalary", list.stream().mapToDouble(Person::getSalary).sum());
                        stats.put("avgAge", list.stream().mapToInt(Person::getAge).average().orElse(0));
                        return stats;
                    }
                )
            ));
        deptStats.forEach((dept, stats) -> 
            System.out.println("  " + dept + ": " + stats));
        
        // Problem 66: Partitioning with multiple conditions
        System.out.println("\n66. Complex partitioning:");
        Map<Boolean, Map<String, List<Person>>> complexPartition = persons.stream()
            .collect(Collectors.partitioningBy(
                p -> p.getSalary() > 60000,
                Collectors.groupingBy(Person::getDepartment)
            ));
        System.out.println("  High earners (>60000):");
        complexPartition.get(true).forEach((dept, list) -> 
            System.out.println("    " + dept + ": " + list.stream().map(Person::getName).collect(Collectors.toList())));
        
        // ========== SECTION 4: REDUCTION OPERATIONS ==========
        System.out.println("\n\n=== SECTION 4: REDUCTION OPERATIONS ===");
        
        // Problem 67: Custom reduction
        System.out.println("\n67. Custom reduction - concatenate all names:");
        String allNames = persons.stream()
            .map(Person::getName)
            .reduce("", (partial, name) -> partial + ", " + name)
            .substring(2); // Remove initial ", "
        System.out.println("  All names: " + allNames);
        
        // Problem 68: Parallel stream reduction
        System.out.println("\n68. Parallel stream - sum of salaries:");
        double parallelSum = persons.parallelStream()
            .mapToDouble(Person::getSalary)
            .reduce(0, Double::sum);
        System.out.println("  Parallel sum: " + parallelSum);
        
        // ========== SECTION 5: OPTIONAL OPERATIONS ==========
        System.out.println("\n\n=== SECTION 5: OPTIONAL OPERATIONS ===");
        
        // Problem 69: Chaining Optional operations
        System.out.println("\n69. Optional chaining - find HR manager:");
        Optional<Person> hrManager = persons.stream()
            .filter(p -> "HR".equals(p.getDepartment()))
            .max(Comparator.comparingDouble(Person::getSalary))
            .map(manager -> {
                System.out.println("  Found HR manager: " + manager.getName());
                return manager;
            });
        
        // Problem 70: Optional with orElse
        System.out.println("\n70. Find youngest IT employee or default:");
        Person defaultPerson = new Person("Default", 0, 0, "None");
        Person youngestIT = persons.stream()
            .filter(p -> "IT".equals(p.getDepartment()))
            .min(Comparator.comparingInt(Person::getAge))
            .orElse(defaultPerson);
        System.out.println("  Youngest IT: " + youngestIT.getName());
        
        // ========== SECTION 6: COMPARATOR OPERATIONS ==========
        System.out.println("\n\n=== SECTION 6: COMPARATOR OPERATIONS ===");
        
        // Problem 71: Complex comparator
        System.out.println("\n71. Sort by multiple criteria:");
        List<Person> multiSorted = persons.stream()
            .sorted(Comparator
                .comparing(Person::getDepartment)
                .thenComparing(Person::getAge, Comparator.reverseOrder())
                .thenComparing(Person::getSalary))
            .collect(Collectors.toList());
        multiSorted.forEach(p -> System.out.println("  " + p.getDepartment() + " | " + 
            p.getAge() + " | " + p.getSalary() + " | " + p.getName()));
        
        // ========== SECTION 7: PRIMITIVE STREAMS ==========
        System.out.println("\n\n=== SECTION 7: PRIMITIVE STREAMS ===");
        
        // Problem 72: IntStream operations
        System.out.println("\n72. IntStream statistics:");
        IntSummaryStatistics ageStats = persons.stream()
            .mapToInt(Person::getAge)
            .summaryStatistics();
        System.out.println("  Age Stats: " + ageStats);
        
        // Problem 73: DoubleStream operations
        System.out.println("\n73. Salary range analysis:");
        DoubleStream salaryStream = persons.stream()
            .mapToDouble(Person::getSalary);
        System.out.println("  Min: " + salaryStream.min().orElse(0));
        System.out.println("  Max: " + persons.stream().mapToDouble(Person::getSalary).max().orElse(0));
        
        // ========== SECTION 8: PARALLEL STREAMS ==========
        System.out.println("\n\n=== SECTION 8: PARALLEL STREAMS ===");
        
        // Problem 74: Parallel stream with thread-safe collection
        System.out.println("\n74. Parallel stream processing:");
        List<String> parallelProcessed = persons.parallelStream()
            .filter(p -> p.getSalary() > 50000)
            .map(p -> {
                // Simulate some processing
                return Thread.currentThread().getName() + " processed " + p.getName();
            })
            .collect(Collectors.toList());
        parallelProcessed.forEach(msg -> System.out.println("  " + msg));
        
        // ========== SECTION 9: STREAM GENERATION ==========
        System.out.println("\n\n=== SECTION 9: STREAM GENERATION ===");
        
        // Problem 75: Generate streams
        System.out.println("\n75. Stream generation examples:");
        System.out.println("  a) Stream.iterate:");
        Stream.iterate(1, n -> n + 1)
            .limit(5)
            .forEach(n -> System.out.print(n + " "));
        
        System.out.println("\n  b) Stream.generate:");
        Stream.generate(Math::random)
            .limit(3)
            .forEach(n -> System.out.print(String.format("%.2f ", n)));
        
        // ========== SECTION 10: FUNCTIONAL INTERFACES ==========
        System.out.println("\n\n\n=== SECTION 10: FUNCTIONAL INTERFACES ===");
        
        // Problem 76: Function composition
        System.out.println("\n76. Function composition:");
        Function<Person, String> getName = Person::getName;
        Function<String, Integer> getLength = String::length;
        Function<Person, Integer> getNameLength = getName.andThen(getLength);
        
        persons.stream()
            .map(getNameLength)
            .forEach(len -> System.out.print(len + " "));
        
        // Problem 77: Supplier and Consumer
        System.out.println("\n\n77. Supplier and Consumer:");
        Supplier<List<String>> departmentSupplier = () -> 
            persons.stream()
                .map(Person::getDepartment)
                .distinct()
                .collect(Collectors.toList());
        
        Consumer<String> printDept = dept -> System.out.println("  - " + dept);
        departmentSupplier.get().forEach(printDept);
        
        // ========== SECTION 11: CUSTOM COLLECTORS ==========
        System.out.println("\n\n=== SECTION 11: CUSTOM COLLECTORS ===");
        
        // Problem 78: Custom collector
        System.out.println("\n78. Custom collector - collect to custom object:");
        EmployeeSummary summary = persons.stream()
            .collect(Collector.of(
                EmployeeSummary::new,
                EmployeeSummary::accept,
                EmployeeSummary::combine,
                Collector.Characteristics.CONCURRENT
            ));
        System.out.println("  Summary: " + summary);
        
        // ========== SECTION 12: STREAM PIPELINES ==========
        System.out.println("\n\n=== SECTION 12: STREAM PIPELINES ===");
        
        // Problem 79: Complex pipeline
        System.out.println("\n79. Complex stream pipeline:");
        Map<String, Double> result = persons.stream()
            .filter(p -> p.getAge() > 25)
            .collect(Collectors.groupingBy(
                Person::getDepartment,
                Collectors.mapping(
                    Person::getSalary,
                    Collectors.collectingAndThen(
                        Collectors.toList(),
                        salaries -> salaries.stream()
                            .mapToDouble(s -> s)
                            .average()
                            .orElse(0.0)
                    )
                )
            ));
        System.out.println("  Average salary by dept for age > 25: " + result);
        
        // ========== SECTION 13: STREAM ITERATION ==========
        System.out.println("\n\n=== SECTION 13: STREAM ITERATION ===");
        
        // Problem 80: Stream iteration with peek
        System.out.println("\n80. Stream iteration with peek:");
        List<Person> processed = persons.stream()
            .peek(p -> System.out.println("  Processing: " + p.getName()))
            .filter(p -> p.getSalary() > 60000)
            .peek(p -> System.out.println("  Filtered: " + p.getName()))
            .collect(Collectors.toList());
        
        // ========== SECTION 14: INFINITE STREAMS ==========
        System.out.println("\n\n=== SECTION 14: INFINITE STREAMS ===");
        
        // Problem 81: Infinite stream with limit
        System.out.println("\n81. Infinite stream example:");
        System.out.print("  Random salaries: ");
        Stream.generate(() -> Math.random() * 100000)
            .limit(5)
            .forEach(s -> System.out.print(String.format("%.0f ", s)));
        
        // ========== SECTION 15: STREAM CONCATENATION ==========
        System.out.println("\n\n\n=== SECTION 15: STREAM CONCATENATION ===");
        
        // Problem 82: Concatenate streams
        System.out.println("\n82. Stream concatenation:");
        Stream<Person> itStream = persons.stream().filter(p -> "IT".equals(p.getDepartment()));
        Stream<Person> hrStream = persons.stream().filter(p -> "HR".equals(p.getDepartment()));
        
        List<Person> combined = Stream.concat(itStream, hrStream)
            .sorted(Comparator.comparing(Person::getSalary))
            .collect(Collectors.toList());
        combined.forEach(p -> System.out.println("  " + p.getName() + " - " + p.getDepartment() + " - " + p.getSalary()));
        
        // ========== SECTION 16: COLLECTORS.JOINING ==========
        System.out.println("\n\n=== SECTION 16: COLLECTORS.JOINING ===");
        
        // Problem 83: Advanced joining
        System.out.println("\n83. Advanced string joining:");
        String delimited = persons.stream()
            .map(Person::getName)
            .collect(Collectors.joining(" | ", "[ ", " ]"));
        System.out.println("  " + delimited);
        
        // ========== SECTION 17: TO_MAP COLLECTOR ==========
        System.out.println("\n\n=== SECTION 17: TO_MAP COLLECTOR ===");
        
        // Problem 84: ToMap with merge function
        System.out.println("\n84. ToMap with duplicate key handling:");
        Map<String, Double> nameToSalary = persons.stream()
            .collect(Collectors.toMap(
                Person::getName,
                Person::getSalary,
                (oldValue, newValue) -> Math.max(oldValue, newValue) // Handle duplicates
            ));
        System.out.println("  Name to Salary map: " + nameToSalary);
        
        // ========== SECTION 18: GROUPING BY MULTI-LEVEL ==========
        System.out.println("\n\n=== SECTION 18: MULTI-LEVEL GROUPING ===");
        
        // Problem 85: Multi-level grouping
        System.out.println("\n85. Multi-level grouping:");
        Map<String, Map<String, List<Person>>> multiGroup = persons.stream()
            .collect(Collectors.groupingBy(
                Person::getDepartment,
                Collectors.groupingBy(p -> p.getAge() > 30 ? "Senior" : "Junior")
            ));
        multiGroup.forEach((dept, ageGroups) -> {
            System.out.println("  " + dept + ":");
            ageGroups.forEach((category, list) -> 
                System.out.println("    " + category + ": " + list.stream()
                    .map(Person::getName)
                    .collect(Collectors.joining(", "))));
        });
        
        // ========== SECTION 19: PARTITIONING BY MULTI-LEVEL ==========
        System.out.println("\n\n=== SECTION 19: MULTI-LEVEL PARTITIONING ===");
        
        // Problem 86: Complex partitioning
        System.out.println("\n86. Nested partitioning:");
        Map<Boolean, Map<Boolean, List<Person>>> nestedPartition = persons.stream()
            .collect(Collectors.partitioningBy(
                p -> p.getSalary() > 60000,
                Collectors.partitioningBy(p -> p.getAge() > 30)
            ));
        System.out.println("  High earners > 60000:");
        System.out.println("    Age > 30: " + nestedPartition.get(true).get(true).stream()
            .map(Person::getName).collect(Collectors.toList()));
        System.out.println("    Age <= 30: " + nestedPartition.get(true).get(false).stream()
            .map(Person::getName).collect(Collectors.toList()));
        
        // ========== SECTION 20: STREAM ITERATION METHODS ==========
        System.out.println("\n\n=== SECTION 20: STREAM ITERATION METHODS ===");
        
        // Problem 87: forEachOrdered
        System.out.println("\n87. Parallel stream with forEachOrdered:");
        System.out.print("  Names in order: ");
        persons.parallelStream()
            .map(Person::getName)
            .forEachOrdered(name -> System.out.print(name + " "));
        
        // Problem 88: Skip and Limit
        System.out.println("\n\n88. Pagination with skip and limit:");
        int page = 2;
        int pageSize = 3;
        List<Person> pageResults = persons.stream()
            .sorted(Comparator.comparing(Person::getName))
            .skip((page - 1) * pageSize)
            .limit(pageSize)
            .collect(Collectors.toList());
        System.out.println("  Page " + page + " results:");
        pageResults.forEach(p -> System.out.println("    - " + p.getName()));
        
        // ========== SECTION 21: STREAM REDUCE METHODS ==========
        System.out.println("\n\n=== SECTION 21: STREAM REDUCE METHODS ===");
        
        // Problem 89: Reduce with identity, accumulator, combiner
        System.out.println("\n89. Reduce with combiner (parallel safe):");
        Integer totalNameLength = persons.parallelStream()
            .reduce(0,
                (sum, p) -> sum + p.getName().length(),  // Accumulator
                Integer::sum);                           // Combiner
        System.out.println("  Total name characters: " + totalNameLength);
        
        // ========== SECTION 22: OPTIONAL METHODS ==========
        System.out.println("\n\n=== SECTION 22: OPTIONAL METHODS ===");
        
        // Problem 90: Optional filter and map
        System.out.println("\n90. Optional chaining methods:");
        Optional<Double> highSalary = persons.stream()
            .filter(p -> p.getName().equals("David"))
            .findFirst()
            .map(Person::getSalary)
            .filter(s -> s > 80000)
            .map(s -> s * 1.1); // 10% bonus
        highSalary.ifPresent(s -> System.out.println("  David's salary with bonus: " + s));
        
        // ========== SECTION 23: PRIMITIVE STREAM METHODS ==========
        System.out.println("\n\n=== SECTION 23: PRIMITIVE STREAM METHODS ===");
        
        // Problem 91: IntStream range and boxed
        System.out.println("\n91. IntStream operations:");
        System.out.print("  Ages as list: ");
        List<Integer> ages = persons.stream()
            .mapToInt(Person::getAge)
            .boxed()
            .collect(Collectors.toList());
        System.out.println(ages);
        
        // ========== SECTION 24: COLLECTORS SUMMARIZING ==========
        System.out.println("\n\n=== SECTION 24: COLLECTORS SUMMARIZING ===");
        
        // Problem 92: Summarizing collectors
        System.out.println("\n92. Salary summarizing by department:");
        Map<String, DoubleSummaryStatistics> salarySummaries = persons.stream()
            .collect(Collectors.groupingBy(
                Person::getDepartment,
                Collectors.summarizingDouble(Person::getSalary)
            ));
        salarySummaries.forEach((dept, stats) -> 
            System.out.println("  " + dept + ": " + stats));
        
        // ========== SECTION 25: STREAM OF OPTIONALS ==========
        System.out.println("\n\n=== SECTION 25: STREAM OF OPTIONALS ===");
        
        // Problem 93: FlatMap with Optional
        System.out.println("\n93. FlatMap with Optional:");
        List<String> namesStartingWithJ = persons.stream()
            .map(p -> Optional.of(p)
                .filter(person -> person.getName().startsWith("J"))
                .map(Person::getName))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
        System.out.println("  Names starting with J: " + namesStartingWithJ);
        
        // ========== SECTION 26: STREAM ITERATION CONTROL ==========
        System.out.println("\n\n=== SECTION 26: STREAM ITERATION CONTROL ===");
        
        // Problem 94: TakeWhile (Java 9+ but simulated)
        System.out.println("\n94. Simulating takeWhile (stop when condition met):");
        List<Person> earlyList = persons.stream()
            .sorted(Comparator.comparing(Person::getSalary))
            .collect(Collectors.toList());
        
        // Simulating takeWhile
        List<Person> lowEarners = new ArrayList<>();
        for (Person p : earlyList) {
            if (p.getSalary() < 60000) {
                lowEarners.add(p);
            } else {
                break;
            }
        }
        System.out.println("  Employees with salary < 60000: " + 
            lowEarners.stream().map(Person::getName).collect(Collectors.toList()));
        
        // ========== SECTION 27: CUSTOM TERMINAL OPERATION ==========
        System.out.println("\n\n=== SECTION 27: CUSTOM TERMINAL OPERATION ===");
        
        // Problem 95: Custom terminal operation
        System.out.println("\n95. Custom forEach with index:");
        AtomicInteger index = new AtomicInteger(1);
        persons.stream()
            .sorted(Comparator.comparing(Person::getSalary).reversed())
            .forEach(p -> System.out.println("  " + index.getAndIncrement() + 
                ". " + p.getName() + ": " + p.getSalary()));
        
        // ========== SECTION 28: STREAM TO ARRAY ==========
        System.out.println("\n\n=== SECTION 28: STREAM TO ARRAY ===");
        
        // Problem 96: Convert stream to array
        System.out.println("\n96. Convert stream to array:");
        Person[] personArray = persons.stream()
            .filter(p -> p.getAge() > 30)
            .toArray(Person[]::new);
        System.out.println("  Employees over 30: " + Arrays.toString(personArray));
        
        // ========== SECTION 29: STREAM ITERATE ==========
        System.out.println("\n\n=== SECTION 29: STREAM ITERATE ===");
        
        // Problem 97: Stream.iterate with predicate
        System.out.println("\n97. Generate salary increments:");
        System.out.print("  Starting from 50000 with 10% increments: ");
        Stream.iterate(50000.0, salary -> salary * 1.10)
            .limit(5)
            .forEach(s -> System.out.print(String.format("%.0f ", s)));
        
        // ========== SECTION 30: COMPREHENSIVE FINAL PROBLEM ==========
        System.out.println("\n\n\n=== SECTION 30: COMPREHENSIVE FINAL PROBLEM ===");
        
        // Problem 98: Complete business logic pipeline
        System.out.println("\n98. Complete business analysis:");
        
        Map<String, Object> businessReport = persons.stream()
            .collect(Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    Map<String, Object> report = new HashMap<>();
                    
                    // Basic stats
                    report.put("totalEmployees", list.size());
                    report.put("totalSalary", list.stream().mapToDouble(Person::getSalary).sum());
                    report.put("averageAge", list.stream().mapToInt(Person::getAge).average().orElse(0));
                    
                    // Department analysis
                    Map<String, Long> deptCount = list.stream()
                        .collect(Collectors.groupingBy(Person::getDepartment, Collectors.counting()));
                    report.put("departmentDistribution", deptCount);
                    
                    // Salary analysis
                    Map<String, Double> deptAvgSalary = list.stream()
                        .collect(Collectors.groupingBy(
                            Person::getDepartment,
                            Collectors.averagingDouble(Person::getSalary)
                        ));
                    report.put("departmentAvgSalary", deptAvgSalary);
                    
                    // Age groups
                    Map<String, Long> ageGroups = list.stream()
                        .collect(Collectors.groupingBy(
                            p -> p.getAge() < 30 ? "Young" : p.getAge() < 40 ? "Middle" : "Senior",
                            Collectors.counting()
                        ));
                    report.put("ageGroups", ageGroups);
                    
                    // High earners
                    List<String> highEarners = list.stream()
                        .filter(p -> p.getSalary() > 70000)
                        .map(Person::getName)
                        .collect(Collectors.toList());
                    report.put("highEarners", highEarners);
                    
                    return report;
                }
            ));
        
        System.out.println("  Business Report:");
        businessReport.forEach((key, value) -> System.out.println("    " + key + ": " + value));
        
        // Problem 99: Performance comparison
        System.out.println("\n99. Sequential vs Parallel performance:");
        
        long seqStart = System.currentTimeMillis();
        double seqSum = persons.stream()
            .mapToDouble(Person::getSalary)
            .sum();
        long seqEnd = System.currentTimeMillis();
        
        long parStart = System.currentTimeMillis();
        double parSum = persons.parallelStream()
            .mapToDouble(Person::getSalary)
            .sum();
        long parEnd = System.currentTimeMillis();
        
        System.out.println("  Sequential time: " + (seqEnd - seqStart) + "ms");
        System.out.println("  Parallel time: " + (parEnd - parStart) + "ms");
        System.out.println("  Sum is equal: " + (seqSum == parSum));
        
        // Problem 100: Master Challenge
        System.out.println("\n100. MASTER CHALLENGE: Complete Employee Analytics");
        System.out.println("  Creating comprehensive employee dashboard...");
        
        EmployeeDashboard dashboard = new EmployeeDashboard(persons);
        dashboard.displayDashboard();
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALL 100+ JAVA 8 STREAM PROBLEMS SOLVED SUCCESSFULLY!");
        System.out.println("=".repeat(70));
    }
    
    // Supporting classes
    
    static class EmployeeDTO {
        private String name;
        private String department;
        private double salary;
        
        public EmployeeDTO(String name, String department, double salary) {
            this.name = name;
            this.department = department;
            this.salary = salary;
        }
        
        @Override
        public String toString() {
            return name + " works in " + department + " earning " + salary;
        }
    }
    
    static class EmployeeSummary {
        private int count;
        private double totalSalary;
        private int totalAge;
        
        public void accept(Person p) {
            count++;
            totalSalary += p.getSalary();
            totalAge += p.getAge();
        }
        
        public EmployeeSummary combine(EmployeeSummary other) {
            this.count += other.count;
            this.totalSalary += other.totalSalary;
            this.totalAge += other.totalAge;
            return this;
        }
        
        public double getAverageSalary() {
            return count > 0 ? totalSalary / count : 0;
        }
        
        public double getAverageAge() {
            return count > 0 ? (double) totalAge / count : 0;
        }
        
        @Override
        public String toString() {
            return String.format("Employees: %d, Avg Salary: %.2f, Avg Age: %.1f", 
                count, getAverageSalary(), getAverageAge());
        }
    }
    
    static class EmployeeDashboard {
        private List<Person> employees;
        
        public EmployeeDashboard(List<Person> employees) {
            this.employees = employees;
        }
        
        public void displayDashboard() {
            System.out.println("\n  === EMPLOYEE DASHBOARD ===");
            
            // Top section
            System.out.println("  Total Employees: " + employees.size());
            System.out.println("  Total Salary Cost: " + 
                String.format("%.2f", employees.stream().mapToDouble(Person::getSalary).sum()));
            
            // Department breakdown
            System.out.println("\n  Department Breakdown:");
            employees.stream()
                .collect(Collectors.groupingBy(Person::getDepartment, Collectors.counting()))
                .forEach((dept, count) -> 
                    System.out.println("    " + dept + ": " + count + " employees"));
            
            // Salary analysis
            System.out.println("\n  Salary Analysis:");
            DoubleSummaryStatistics stats = employees.stream()
                .mapToDouble(Person::getSalary)
                .summaryStatistics();
            System.out.println("    Min: " + stats.getMin());
            System.out.println("    Max: " + stats.getMax());
            System.out.println("    Avg: " + String.format("%.2f", stats.getAverage()));
            
            // Top performers
            System.out.println("\n  Top 3 Earners:");
            employees.stream()
                .sorted(Comparator.comparingDouble(Person::getSalary).reversed())
                .limit(3)
                .forEach(p -> System.out.println("    " + p.getName() + ": " + p.getSalary()));
        }
    }
}