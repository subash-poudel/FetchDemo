package com.example.subash.fetchdemo.downloadmanager;

public class ImageDownload {

    private String imageURL;
    private Object data;
    private String type;
    private String filePath;
    private long fetchDownloadID;

    public ImageDownload(String imageURL, String type, Object data, String filePath) {
        this.imageURL = imageURL;
        this.type = type;
        this.data = data;
        this.filePath = filePath;
    }

    public long getFetchDownloadID() {
        return fetchDownloadID;
    }

    public void setFetchDownloadID(long fetchDownloadID) {
        this.fetchDownloadID = fetchDownloadID;
    }

    public String getImageURL() {
        return imageURL;
    }

    public Object getData() {
        return data;
    }

    public String getType() {
        return type;
    }

    public String getFilePath() {
        return filePath;
    }
}
