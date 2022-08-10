package com.example.photoforsoul;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class PhotoDetailActivity extends AppCompatActivity {
    private static final String ExtraPhoto = "PHOTO";

    private PhotoDetailViewModel photoDetailViewModel;

    private ImageView imageViewPhoto;
    private TextView textViewAuthorName;
    private ImageView imageViewStar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        photoDetailViewModel = new ViewModelProvider(this).get(PhotoDetailViewModel.class);
        imageViewPhoto = findViewById(R.id.imageViewPhoto);
        textViewAuthorName = findViewById(R.id.textViewAuthorName);
        imageViewStar = findViewById(R.id.imageViewStar);
        Photo photo = (Photo)(getIntent().getSerializableExtra(ExtraPhoto));
        Glide.with(this)
                .load(photo.getDownload_url())
                .into(imageViewPhoto);
        textViewAuthorName.setText("Фотограф: "+photo.getAuthor());
        Drawable starOff = ContextCompat.getDrawable(PhotoDetailActivity.this, android.R.drawable.star_big_off);
        Drawable starOn = ContextCompat.getDrawable(PhotoDetailActivity.this, android.R.drawable.star_big_on);
        photoDetailViewModel.getFavouritePhoto(Integer.parseInt(photo.getId())).observe(this, new Observer<Photo>() {
            @Override
            public void onChanged(Photo photoFromDb) {
                if(photoFromDb == null) {
                    imageViewStar.setImageDrawable(starOff);
                    imageViewStar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            photoDetailViewModel.insertPhoto(photo);
                        }
                    });
                }
                else {
                    imageViewStar.setImageDrawable(starOn);
                    imageViewStar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            photoDetailViewModel.removePhoto(Integer.parseInt(photo.getId()));
                        }
                    });
                }
            }
        });
    }

    public static Intent newIntent(Context context, Photo photo) {
        Intent intent = new Intent(context, PhotoDetailActivity.class);
        intent.putExtra(ExtraPhoto, photo);
        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.photo_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.itemAll) {
            Intent intent = MainActivity.newIntent(this);
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.itemFavouritePhotos) {
            Intent intent = FavouritesPhotoActivity.newIntent(this);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}