package com.example.photoforsoul;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class FavouritesPhotoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites_photo);
        RecyclerView recyclerViewPhotos = findViewById(R.id.recyclerViewPhotos);
        PhotosAdapter photosAdapter = new PhotosAdapter();
        recyclerViewPhotos.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewPhotos.setAdapter(photosAdapter);
        photosAdapter.setOnPhotoClickListener(new PhotosAdapter.OnPhotoClickListener() {
            @Override
            public void onPhotoClick(Photo photo) {
                Intent intent = PhotoDetailActivity.newIntent(FavouritesPhotoActivity.this, photo);
                startActivity(intent);
            }
        });
        FavouritesPhotoViewModel favouritesPhotoViewModel = new ViewModelProvider(this).get(FavouritesPhotoViewModel.class);
        favouritesPhotoViewModel.getPhotos().observe(this, new Observer<List<Photo>>() {
            @Override
            public void onChanged(List<Photo> photoList) {
                photosAdapter.setPhotos(photoList);
            }
        });
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, FavouritesPhotoActivity.class);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favourite_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.itemAll) {
            Intent intent = MainActivity.newIntent(this);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}