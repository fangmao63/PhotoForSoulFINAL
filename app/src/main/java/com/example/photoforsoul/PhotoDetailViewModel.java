package com.example.photoforsoul;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PhotoDetailViewModel extends AndroidViewModel {

    private final PhotoDao photoDao;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    public PhotoDetailViewModel(@NonNull Application application) {
        super(application);
        photoDao = PhotoDatabase.getInstance(application).photoDao();
    }

    public LiveData<Photo> getFavouritePhoto(int photoId) {
        return photoDao.getFavouritePhoto(photoId);
    }

    public void insertPhoto(Photo photo) {
        Disposable disposable = photoDao.insertPhoto(photo)
                .subscribeOn(Schedulers.io())
                .subscribe();
        compositeDisposable.add(disposable);
    }

    public void removePhoto(int photoId) {
        Disposable disposable = photoDao.removePhoto(photoId)
                .subscribeOn(Schedulers.io())
                .subscribe();
        compositeDisposable.add(disposable);
    }
}
