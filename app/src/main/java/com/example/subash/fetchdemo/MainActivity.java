package com.example.subash.fetchdemo;

import android.app.ProgressDialog;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.subash.fetchdemo.downloadmanager.ImageDownload;
import com.example.subash.fetchdemo.model.Employee;
import com.example.subash.fetchdemo.model.Person;
import com.tonyodev.fetch.Fetch;
import com.tonyodev.fetch.listener.FetchListener;
import com.tonyodev.fetch.request.RequestInfo;

import java.util.List;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements FetchListener {

    private MainActivityPresenter presenter;

    private ProgressDialog progressDialog;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout = findViewById(R.id.linearLayout);
        setupRealm();
        setupFetch();
        setupPresenter();
    }

    private void setupRealm() {
        Realm.init(this);
    }

    public void showProgressDialog(String message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);

        }
        progressDialog.setCancelable(false);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public void setImages(List<String> images) {
        for (String image : images) {
            ImageView imageView = new ImageView(this);
            imageView.setImageBitmap(BitmapFactory.decodeFile(image));
            linearLayout.addView(imageView);
        }
    }

    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.hide();
        }
        progressDialog = null;
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void setupPresenter() {
        presenter = new MainActivityPresenter(this);
        presenter.fetchPeople();
        Fetch fetch = Fetch.newInstance(this);
        List<RequestInfo> requestInfos = fetch.get();
        for (RequestInfo requestInfo: requestInfos) {
            if (requestInfo.getStatus() == Fetch.STATUS_DONE) {
                Log.e("status done", requestInfo.getUrl());
            } else {
                Log.e("status not done", requestInfo.getUrl());
                requestInfo.getId();
                Fetch.newInstance(this).resume(requestInfo.getId());
            }
        }
    }

    private void setupFetch() {
        new Fetch.Settings(getApplicationContext())
                .setAllowedNetwork(Fetch.NETWORK_ALL)
                .setOnUpdateInterval(3 * 1000)
                .enableLogging(true)
                .setConcurrentDownloadsLimit(3)
                .apply();
//        Fetch.newInstance(this).removeAll();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.presenter.onDestroy();
        this.presenter = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Fetch fetch = Fetch.newInstance(this);
        fetch.addFetchListener(this);
    }

    @Override
    public void onUpdate(long id, int status, int progress, long downloadedBytes, long fileSize, int error) {

        if (status == Fetch.STATUS_DONE) {
//            downloadedImageCount += 1;
            Log.e("status done", id + "");
            // save the data in appropriate realm model
//            final ImageDownload imageDownload = getImageDownloadWithID(id);
//            switch (imageDownload.getType()) {
//                case "person_profile_picture": {
//                    final Person person = (Person) imageDownload.getData();
//                    Realm realm = Realm.getDefaultInstance();
//                    realm.executeTransaction(new Realm.Transaction() {
//                        @Override
//                        public void execute(Realm realm) {
//                            person.setFilePath(fileDirectory + imageDownload.getFilePath());
//                        }
//                    });
//                }
//                break;
//                case "employee_profile_picture": {
//                    final Employee employee = (Employee) imageDownload.getData();
//                    Realm realm = Realm.getDefaultInstance();
//                    realm.executeTransaction(new Realm.Transaction() {
//                        @Override
//                        public void execute(Realm realm) {
//                            employee.setFilePath(fileDirectory + imageDownload.getFilePath());
//                        }
//                    });
//                }
//                break;
            }
        }
//        // check if all images has been downloaded
//        if (downloadedImageCount == imageDownloads.size()) {
//            if (listener != null) {
//                listener.onImagesDownloadComplete();
//            }
//        }



}
