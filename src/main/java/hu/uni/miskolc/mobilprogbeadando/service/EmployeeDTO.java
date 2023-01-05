package hu.uni.miskolc.mobilprogbeadando.service;

import java.io.Serializable;

public class EmployeeDTO implements Serializable {

    private int id;
    private String name;
    private String salary;
    private String age;

    public EmployeeDTO() {
    }

    public EmployeeDTO(int id, String name, String salary, String age) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.age = age;
    }
    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
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
