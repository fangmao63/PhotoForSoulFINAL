package com.example.photoforsoul;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
@Entity(tableName = "favourite_photos")
public class Photo implements Serializable {
    @PrimaryKey
    @NonNull
    @SerializedName("id")
    private String id;
    @SerializedName("url")
    private String url;
    @SerializedName("author")
    private String author;
    @SerializedName("download_url")
    private String download_url;



    public Photo(String url, String author, String id, String download_url) {
        this.url = url;
        this.author = author;
        this.id = id;
        this.download_url = download_url;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "url='" + url + '\'' +
                ", author='" + author + '\'' +
                ", id='" + id + '\'' +
                ", download_url='" + download_url + '\'' +
                '}';
    }

    public String getDownload_url() {
        return download_url;
    }

}
