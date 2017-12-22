package com.example.subash.fetchdemo.downloadmanager;

import android.content.Context;
import android.util.Log;

import com.example.subash.fetchdemo.model.Employee;
import com.example.subash.fetchdemo.model.Person;
import com.tonyodev.fetch.Fetch;
import com.tonyodev.fetch.listener.FetchListener;
import com.tonyodev.fetch.request.Request;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import io.realm.Realm;

public class ImageDownloadManager {

    private List<ImageDownload> imageDownloads;
    private ImagesDownloadListener listener;

    public ImageDownloadManager(List<ImageDownload> imageDownloads) {
        this.imageDownloads = imageDownloads;
    }
    int downloadedImageCount = 0;
    String fileDirectory;

    public void setListener(ImagesDownloadListener listener) {
        this.listener = listener;
    }

    public void startDownload(final Context context) {
        Fetch fetch = Fetch.newInstance(context);
        fileDirectory = context.getFilesDir().getAbsolutePath() + File.separator;
        final String imageRootDirectory = context.getFilesDir().getAbsolutePath();

        for (ImageDownload imageDownload : imageDownloads) {
            Request request = new Request(imageDownload.getImageURL(),
                    imageRootDirectory,
                    imageDownload.getFilePath()
            );
            long downloadId = fetch.enqueue(request);
            Log.e("Status enqueue", downloadId + "");
            if (downloadId != Fetch.ENQUEUE_ERROR_ID) {
                //case not handled code copied from github
                imageDownload.setFetchDownloadID(downloadId);
            }

        }

        fetch.addFetchListener(new FetchListener() {
            @Override
            public void onUpdate(long id, int status, int progress, long downloadedBytes, long fileSize, int error) {
                if (status == Fetch.STATUS_DONE) {
                    downloadedImageCount += 1;
                    Log.e("status done", id + "");
                    // save the data in appropriate realm model
                    final ImageDownload imageDownload = getImageDownloadWithID(id);
                    switch (imageDownload.getType()) {
                        case "person_profile_picture": {
                            final Person person = (Person) imageDownload.getData();
                            Realm realm = Realm.getDefaultInstance();
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    person.setFilePath(fileDirectory + imageDownload.getFilePath());
                                }
                            });
                        }
                        break;
                        case "employee_profile_picture": {
                            final Employee employee = (Employee) imageDownload.getData();
                            Realm realm = Realm.getDefaultInstance();
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    employee.setFilePath(fileDirectory + imageDownload.getFilePath());
                                }
                            });
                        }
                        break;
                    }
                }
                // check if all images has been downloaded
                if (downloadedImageCount == imageDownloads.size()) {
                    if (listener != null) {
                        listener.onImagesDownloadComplete();
                    }
                }
            }
        });
    }

    private ImageDownload getImageDownloadWithID(long id) {
        for (ImageDownload imageDownload : imageDownloads) {
            if (imageDownload.getFetchDownloadID() == id) {
                return imageDownload;
            }
        }
        return null;
    }

    public interface ImagesDownloadListener {
        void onImagesDownloadComplete();
    }

}
