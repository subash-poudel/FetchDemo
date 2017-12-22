package com.example.subash.fetchdemo.dataservice;

import com.example.subash.fetchdemo.model.Employee;
import com.example.subash.fetchdemo.model.Employees;
import com.example.subash.fetchdemo.model.People;
import com.example.subash.fetchdemo.model.Person;

import java.util.List;

import io.realm.Realm;

public class DataService {

    public void insertPeople(final People people) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (Person person: people.getPersonList()) {
                    realm.insertOrUpdate(person);
                }
            }
        });
    }

    public void insertEmployees(final Employees employees) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for(Employee employe: employees.getEmployeeList()) {
                    realm.insertOrUpdate(employe);
                }
            }
        });
    }


    public List<Person> getPersons() {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Person.class).findAll();
    }

    public List<Employee> getEmployees() {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Employee.class).findAll();
    }


}
