package com.example.subash.fetchdemo.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class People {

    @SerializedName("peoples")
    List<Person> personList;

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }
}
