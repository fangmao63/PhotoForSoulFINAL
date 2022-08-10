package com.example.photoforsoul;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("list?limit=10")
    Single<List<Photo>> loadPhotos(@Query("page") int page);

}
