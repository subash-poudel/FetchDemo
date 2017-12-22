package com.example.subash.fetchdemo;

import android.app.ProgressDialog;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tonyodev.fetch.Fetch;

import java.util.List;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

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
        for (String image: images) {
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
    }

    private void setupFetch() {
        new Fetch.Settings(getApplicationContext())
                .setAllowedNetwork(Fetch.NETWORK_ALL)
                .setOnUpdateInterval(3 * 1000)
                .enableLogging(true)
                .setConcurrentDownloadsLimit(3)
                .apply();
        Fetch.newInstance(this).removeAll();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.presenter.onDestroy();
        this.presenter = null;
    }

}
