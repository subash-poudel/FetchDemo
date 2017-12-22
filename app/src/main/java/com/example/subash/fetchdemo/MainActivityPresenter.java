package com.example.subash.fetchdemo;

import android.app.DownloadManager;
import android.util.Log;

import com.example.subash.fetchdemo.dataservice.DataService;
import com.example.subash.fetchdemo.downloadmanager.ImageDownload;
import com.example.subash.fetchdemo.downloadmanager.ImageDownloadManager;
import com.example.subash.fetchdemo.model.People;
import com.example.subash.fetchdemo.model.Person;
import com.example.subash.fetchdemo.rest.ApiInterface;
import com.example.subash.fetchdemo.rest.RestHelper;
import com.tonyodev.fetch.Fetch;
import com.tonyodev.fetch.listener.FetchListener;
import com.tonyodev.fetch.request.Request;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivityPresenter {

    private MainActivity activity;
    private CompositeDisposable compositeDisposable;
    private DataService dataService;

    public MainActivityPresenter(MainActivity activity) {
        this.activity = activity;
        compositeDisposable = new CompositeDisposable();
        this.dataService = new DataService();
    }

    public void fetchPeople() {
        addSubscription(RestHelper.getApi().getPeople()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableObserver<People>() {
                    @Override
                    public void onNext(People people) {
                        dataService.insertPeople(people);
                        downloadProfilePictures();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                })
        );
    }

    private void downloadProfilePictures() {
        List<ImageDownload> imageDownloadRequest = new ArrayList<>();
        for(Person person: dataService.getPersons()) {
            String fileName = person.getFirstName() + "_" + person.getLastName() + ".jpg";
            ImageDownload imageDownload = new ImageDownload(person.getProfilePicture(),
                    "profile_picture",
                    person,
                    fileName);
            imageDownloadRequest.add(imageDownload);
        }

        ImageDownloadManager imageDownloadManager = new ImageDownloadManager(imageDownloadRequest);
        imageDownloadManager.startDownload(activity);

    }

    public void onDestroy() {
        compositeDisposable.dispose();
    }

    private void addSubscription(Disposable disposable) {
        compositeDisposable.add(disposable);
    }


}
