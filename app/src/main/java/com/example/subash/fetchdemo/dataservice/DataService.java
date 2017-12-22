package com.example.subash.fetchdemo.dataservice;

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

    public List<Person> getPersons() {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Person.class).findAll();
    }

}
