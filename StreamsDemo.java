import org.omg.Messaging.SyncScopeHelper;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

class Emp{

    String name;
    int age;

    int salary;
    String desig;
    String dept;

    public Emp(String n,int a,int s,String design,String depts){
        name = n;
        age = a;
        salary = s;
        desig = design;
        dept = depts;
    }
    public String toString(){
        return "Name : "+name+" Age: "+age+" Salary : "+salary+" Designation : "+desig+" Department : "+dept;
    }
}

public class StreamsDemo {

    public static void main(String args[]){

        List<Emp> empList = new ArrayList<Emp>();

        empList.add(new Emp("Akshay",25,23000,"Programmer","IT"));
        empList.add(new Emp("Ganesh",35,43000,"Tester","IT"));
        empList.add(new Emp("Akil",55,33000,"Sales Lead","Sales"));
        empList.add(new Emp("Ramesh",25,73000,"Manager","IT"));
        empList.add(new Emp("Manoj",35,85000,"Sales","Sales"));
        empList.add(new Emp("Sooraj",73,93000,"Manager","Sales"));
        empList.add(new Emp("Mathew",25,47000,"Executive","HR"));
        empList.add(new Emp("John",35,83000,"Manager","HR"));
        empList.add(new Emp("Raju",78,98000,"Manager","IT"));
        empList.add(new Emp("Tim",25,55000,"Sales","Sales"));
        empList.add(new Emp("Lord",35,20000,"Tester","IT"));
        empList.add(new Emp("Dileep",58,23000,"Tester","HR"));
        empList.add(new Emp("Arjun",25,63000,"Sales","Sales"));
        empList.add(new Emp("Aravind",35,53000,"Executive","HR"));
        empList.add(new Emp("Sonu",25,73000,"Manager","HR"));

        /*  1st Question */

        Map<String,Long> res1 = empList.stream().collect(Collectors.groupingBy(e->e.dept,Collectors.counting()));
        System.out.println("1. Total number of employess");
        System.out.println(res1);

  
        System.out.println("----------------");
        System.out.println("2. Top 3 Senior most employees ");
        List<Emp> res3 = empList.stream().sorted((e1,e2) -> e2.age-e1.age).limit(3).collect(Collectors.toList());
        System.out.println(res3);

        Function<Emp,String> filterFunc  = e -> e.age>40 ? "SENIOR": e.age >30 ? "MID_AGE":"YOUTH";

        //Map<String,Long> res8 = empList.stream().collect(Collectors.groupingBy(filterFunc,Collectors.counting()));
        IntSummaryStatistics res8 = empList.stream().collect(Collectors.summarizingInt((e)->e.age));
        System.out.println("----------------");
        System.out.println("3. Company type young or senior ");
        String ageType = (res8.getAverage()<=30) ? "YOUTH": res8.getAverage()>30 && res8.getAverage()<40  ? "MID_AGE" : "SENIOR";
        //System.out.println(res8.getAverage()+","+result);
        //String ageType = res8.entrySet().stream().sorted((e1,e2)-> (int) (e2.getValue() - e1.getValue())).limit(1).collect(Collectors.toList()).get(0).getKey();
        System.out.println(res8+","+ageType);

        System.out.println("----------------");
        System.out.println("4. 5th highest earning employees ");
        List<Emp> res4 = empList.stream().sorted((e1,e2) -> e2.salary-e1.salary).limit(5).skip(4).collect(Collectors.toList());
        System.out.println(res4);

        Map<String, Integer> res5 = empList.stream().collect(Collectors.groupingBy(e->e.dept,
                Collectors.summingInt(e->e.salary)));
        System.out.println("----------------");
        System.out.println("5. Most expensive department");

        String value = Collections.max(res5.entrySet(), (entry1, entry2) -> entry1.getValue() - entry2.getValue()).getKey();
        System.out.println("Most expensive department "+value+" value "+res5.get(value));
    }


}
