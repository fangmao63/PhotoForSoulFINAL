package com.example.photoforsoul;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;


import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private MainViewModel viewModel;
    private RecyclerView recyclerViewPhotos;
    private ProgressBar progressBarLoading;
    private PhotosAdapter photosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBarLoading = findViewById(R.id.progressBarLoading);
        recyclerViewPhotos = findViewById(R.id.recyclerViewPhotos);
        photosAdapter = new PhotosAdapter();
        recyclerViewPhotos.setAdapter(photosAdapter);
        recyclerViewPhotos.setLayoutManager(new LinearLayoutManager(this));
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.getPhotos().observe(this, new Observer<List<Photo>>() {
            @Override
            public void onChanged(List<Photo> photoList) {
                photosAdapter.setPhotos(photoList);
            }
        });
        viewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading) {
                    progressBarLoading.setVisibility(View.VISIBLE);
                } else {
                    progressBarLoading.setVisibility(View.GONE);
                }
            }
        });


        photosAdapter.setOnReachEndListener(new PhotosAdapter.OnReachEndListener() {
            @Override
            public void onReachEnd() {
                viewModel.loadPhotos();
            }
        });
        photosAdapter.setOnPhotoClickListener(new PhotosAdapter.OnPhotoClickListener() {
            @Override
            public void onPhotoClick(Photo photo) {
                Intent intent = PhotoDetailActivity.newIntent(MainActivity.this, photo);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.itemFavouritePhotos) {
            Intent intent = FavouritesPhotoActivity.newIntent(this);
            startActivity(intent);
        }
      return super.onOptionsItemSelected(item);
    }
}