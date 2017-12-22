package com.example.subash.fetchdemo.rest;

import com.example.subash.fetchdemo.model.Employees;
import com.example.subash.fetchdemo.model.People;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("/bins/v9c8v")
    Observable<People> getPeople();

    @GET("/bins/17ybmv")
    Observable<Employees> getEmployees();

}
