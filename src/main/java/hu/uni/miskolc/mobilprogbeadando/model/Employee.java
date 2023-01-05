package hu.uni.miskolc.mobilprogbeadando.model;

import java.io.Serializable;

public class Employee implements Serializable {

    private String name;
    private String salary;
    private String age;

    public Employee() {
    }

    public Employee(String name, String salary, String age) {
        this.name = name;
        this.salary = salary;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

}
