package com.example.subash.fetchdemo;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.tonyodev.fetch.Fetch;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    private MainActivityPresenter presenter;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
