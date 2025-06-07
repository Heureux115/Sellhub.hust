package com.example.manager.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int age;
    private String gender;
    private String donvicongtac;
    private String chucvu;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Salary> salaries = new ArrayList<>();

    public Employee() {
    }

    public Employee(String name, int age, String gender, String donvicongtac, String chucvu ) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.donvicongtac = donvicongtac;
        this.chucvu = chucvu;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getDonvicongtac() {
        return donvicongtac;
    }
    public void setDonvicongtac(String donvicongtac) {
        this.donvicongtac = donvicongtac;
    }
    public String getChucvu() {
        return chucvu;
    }
    public void setChucvu(String chucvu) {
        this.chucvu = chucvu;
    }
    public List<Salary> getSalaries() {
        return salaries;
    }
    public void setSalaries(List<Salary> salaries) {
        this.salaries = salaries;
    }
    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}
}

