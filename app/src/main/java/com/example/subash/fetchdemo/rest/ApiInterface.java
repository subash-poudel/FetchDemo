package com.example.subash.fetchdemo.rest;

import com.example.subash.fetchdemo.model.People;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("/bins/v9c8v")
    Observable<People> getPeople();

}
