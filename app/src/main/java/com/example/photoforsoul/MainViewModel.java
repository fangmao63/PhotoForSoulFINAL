package com.example.photoforsoul;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {
    private final String TAG = "MainViewModel";
    private int page = (int)(1 + Math.random()*101);

    private final PhotoDao photoDao;
    private final MutableLiveData<List<Photo>> photos = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    public  MainViewModel(@NonNull Application application) {
        super(application);
        loadPhotos();
        photoDao = PhotoDatabase.getInstance(application).photoDao();

    }

    public LiveData<List<Photo>> getPhotos() {
        return photos;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void loadPhotos() {
        Boolean loading = isLoading.getValue();
        if(loading != null && loading) {
            return;
        }
        Disposable disposable = ApiFactory.apiService.loadPhotos(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Throwable {
                        isLoading.setValue(true);
                    }
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Throwable {
                        isLoading.setValue(false);
                    }
                })
                .subscribe(new Consumer<List<Photo>>() {
                    @Override
                    public void accept(List<Photo> photoList) throws Throwable {
                        List<Photo> loadedPhotos = photos.getValue();
                        if (loadedPhotos != null) {
                            loadedPhotos.addAll(photoList);
                            photos.setValue(loadedPhotos);

                        }
                        else {
                            photos.setValue(photoList);
                        }
                        page = (int)(1 + Math.random()*101);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d(TAG, throwable.toString());
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
