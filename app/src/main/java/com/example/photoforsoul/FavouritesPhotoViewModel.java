package com.example.photoforsoul;

import android.app.Application;
import android.graphics.Movie;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class FavouritesPhotoViewModel extends AndroidViewModel {
    private final PhotoDao photoDao;
    public FavouritesPhotoViewModel(@NonNull Application application) {
        super(application);
        photoDao = PhotoDatabase.getInstance(application).photoDao();
    }

    public LiveData<List<Photo>> getPhotos() {
        return photoDao.getAllFavouritePhotos();
    }
}
