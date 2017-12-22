package com.example.subash.fetchdemo.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Employees {

    @SerializedName("employees")
    List<Employee> employeeList;

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

}
