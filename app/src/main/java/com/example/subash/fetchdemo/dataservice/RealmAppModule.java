package com.example.subash.fetchdemo.dataservice;

import com.example.subash.fetchdemo.model.Employee;
import com.example.subash.fetchdemo.model.Person;

import io.realm.annotations.RealmModule;

@io.realm.annotations.RealmModule(classes = {
        Person.class,
        Employee.class
})
public class RealmAppModule {

}
