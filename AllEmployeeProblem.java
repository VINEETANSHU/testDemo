import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Employee{
    private String name;
    private int age;
    private double salary;
    private String department;
    public Employee(String name,int age,double salary,String department){
        this.name=name;
        this.age=age;
        this.salary=salary;
        this.department=department;
    }
    public String getName(){ return name;}
    public int getAge(){return age;}
    public double getSalary(){return salary;   }
    public String getDepartment(){return department;    }
    public void setName(String name){this.name=name;}
    public void setAge(int age){this.age=age;}
    public void setSalary(double salary){this.salary=salary;}
    public void setDepartment(String department){this.department=department;}
    @Override
    public String toString(){
        return name +  "( "+age+ " ," +salary + " , "+department+ ")";
    }

}
public class AllEmployeeProblem {
    public static void main(String[] args) {
        List<Employee> empList=Arrays.asList(
            new Employee("vineet", 34, 500000, "Devoloper"),
             new Employee("John", 25, 50000, "IT"),
            new Employee("Jane", 30, 60000, "HR"),
            new Employee("Adam", 28, 55000, "IT"),
            new Employee("Eve", 35, 70000, "Finance"),
            new Employee("Mike", 40, 80000, "IT"),
            new Employee("Sarah", 32, 45000, "HR"),
            new Employee("David", 27, 90000, "Finance")

        );
         System.out.println("===== JAVA 8 STREAM PROBLEMS SOLUTIONS =====\n");
        //  System.out.println(empList);
        
        // Problem 1: Filter IT Department Employees
        // List<Employee> empItList=empList.stream()
        // .filter(x->"IT".equals(x.getDepartment())).collect(Collectors.toList());
        // empItList.forEach(p->System.out.println(" - "+p.getName()+" => "+p.getDepartment()));
        // //.............OR....................Same reult....................
        // System.out.println(".............Same Result................");
        // empItList.stream().filter(x->"IT".equals(x.getDepartment())).forEach(p->
        //     System.out.println(" - "+p.getName()+" => "+p.getDepartment())
        // );
        // //.............OR....................Same reult using Predicate ....................
        // System.out.println(".............Same Result using Predicate................");
        // Predicate<Employee> isDept=t->"IT".equals(t.getDepartment());
        // empItList.stream().filter(isDept)
        // .map(m->"- "+m.getName()+" => "+m.getDepartment()).forEach(System.out::println);

        //  //.............OR....................Same reult using partitioningBy ....................
        // System.out.println(".............Same Result using partitioningBy................");
        // Map<Boolean,List<Employee>> resultMap=empItList.stream()
        // .collect(Collectors.partitioningBy(
        //     p->"IT".equals(p.getDepartment())
        // ));
        // resultMap.get(true).forEach(
        //     p->System.out.println("-"+p.getName()+" => "+p.getDepartment())
        // );

        //       //.............OR....................Same reult using groupingBy ....................
        // System.out.println(".............Same Result using groupingBy................");
        // Map<String,List<Employee>> groupResuList=empList.stream()
        // .collect(Collectors.groupingBy(Employee::getDepartment));
        // groupResuList.get("IT").forEach(
        //     g->System.out.println(" - "+g.getName()+" => "+g.getDepartment())
        // );
        
         // Problem 2: Get names of all employees
          // Problem 2: Get names of all employees

        //  List<String> employeesList=empList.stream().map(Employee::getName)
        //  .collect(Collectors.toList());
        //  employeesList.forEach(name->System.out.println("name : "+name));
        // Problem 3: Get average salary of all employees
        // System.out.println("\n3. Average Salary:");
        // double averageSalary=empList.stream()
        // .mapToDouble(Employee::getSalary)
        // .average()
        // .orElse(0.0);
        // System.out.println("Average :"+String.format("%.2f", averageSalary));
          // Problem 4: Find employee with maximum salary
        // System.out.println("\n4. Employee with Maximum Salary:");
        // empList.stream().max(Comparator.comparingDouble(Employee::getSalary))
        // .ifPresent(p->System.out.println(". "+p.getName()+" => "+p.getSalary()));

        // Problem 5: Group employees by department
        // System.out.println("\n5. Employees Grouped by Department:");
        // Map<String,List<Employee>> deptEmpMap=empList.stream()
        // .collect(Collectors.groupingBy(Employee::getDepartment));
        // deptEmpMap.forEach((dept,empMapList)->{
        //     System.out.println(" "+dept+" : ");
        //     empMapList.forEach(name->System.out.println("     - "+name.getName()));
        // });

        // Problem 6: Get total salary by department
    //     System.out.println("\n6. Total Salary by Department:");
    //     Map<String,Double> totalSalByDept=empList.stream()
    //     .collect(Collectors.groupingBy(Employee::getDepartment,
    //              Collectors.summingDouble(Employee::getSalary))
    // );
    //    totalSalByDept.forEach((dept,sal)->{
    //     System.out.println("  "+dept +" => "+sal);
    //    });


    
        // // Problem 7: Find employees older than 30 and age sorted in descending order
        // System.out.println("\n7. Employees Older than 30:");
        // List<Employee> empOlderThen30=empList.stream()
        // .filter(o->o.getAge()>30)
        // .sorted(Comparator.comparing(Employee::getAge).reversed())
        // .collect(Collectors.toList());
        // empOlderThen30.forEach(p->System.out.println(" "+p.getName()+" => "+p.getAge()));
        // Problem 9: Check if all employees earn more than 40000
        // System.out.println("\n9. All employees earn > 40000?");
        // boolean allEarnMoreThan40k = empList.stream()
        //     .allMatch(p -> p.getSalary() > 40000);
        // System.out.println("  Result: " + allEarnMoreThan40k);
        
        // Problem 10: Get distinct departments
        // System.out.println("\n10. Distinct Departments:");
        // List<String> distictDepartmenList=empList.stream()
        // .map(Employee::getDepartment)
        // .distinct()
        // .collect(Collectors.toList());
        // distictDepartmenList.forEach(p->System.out.println(p));

          // Problem 11: Get average age by department
        // System.out.println("\n11. Average Age by Department:");
        // Map<String,Double> avgAgeByDeptMap=empList.stream()
        // .collect(Collectors.groupingBy(Employee::getDepartment,
        //     Collectors.averagingDouble(Employee::getAge)
        // ));
        // avgAgeByDeptMap.forEach((dept,age)->System.out.println(" "+dept+" =>"+age));

          // Problem 12: Find first employee in IT department
        // System.out.println("\n12. First IT Employee:");
        // empList.stream().filter(p->"IT".equals(p.getDepartment()))
        // .findFirst()
        // .ifPresent(dept->System.out.println(dept));

        // Problem 13: Count employees in each department
     

    }
    
}
