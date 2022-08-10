package com.example.photoforsoul;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder> {

    private List<Photo> photos = new ArrayList<>();
    private OnReachEndListener onReachEndListener;
    private OnPhotoClickListener onPhotoClickListener;

    public void setOnPhotoClickListener(OnPhotoClickListener onPhotoClickListener) {
        this.onPhotoClickListener = onPhotoClickListener;
    }

    public void setOnReachEndListener(OnReachEndListener onReachEndListener) {
        this.onReachEndListener = onReachEndListener;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.photo_item,
                parent,
                false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Photo photo = photos.get(position);
        Glide.with(holder.itemView)
                .load(photo.getDownload_url())
                .into(holder.imageViewPhoto);

        if(position >= photos.size()-6 && onReachEndListener != null) {
            onReachEndListener.onReachEnd();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onPhotoClickListener!=null) {
                    onPhotoClickListener.onPhotoClick(photo);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    interface OnReachEndListener {
        void onReachEnd();
    }

    interface OnPhotoClickListener {
        void onPhotoClick(Photo photo);
    }

    static class PhotoViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageViewPhoto;

        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPhoto = itemView.findViewById(R.id.imageViewPhoto);
        }
    }

}
