package com.example.photoforsoul;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

@Dao
public interface PhotoDao {
    @Query("SELECT * FROM favourite_photos")
    LiveData<List<Photo>> getAllFavouritePhotos();

    @Query("SELECT * FROM favourite_photos WHERE id = :photoId")
    LiveData<Photo> getFavouritePhoto(int photoId);

    @Insert
    Completable insertPhoto(Photo photo);

    @Query("DELETE FROM favourite_photos WHERE id = :photoId")
    Completable removePhoto(int photoId);
}
