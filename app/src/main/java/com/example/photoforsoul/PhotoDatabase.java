package com.example.photoforsoul;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = {Photo.class}, version = 1, exportSchema = false)
public abstract class PhotoDatabase extends RoomDatabase {
    private static final String DB_NAME = "photo.db";
    private static PhotoDatabase instance = null;

    public static PhotoDatabase getInstance(Application application) {
        if(instance == null) {
            instance = Room.databaseBuilder(application,
                    PhotoDatabase.class,
                    DB_NAME
                    ).build();
        }
        return instance;
    }

    abstract PhotoDao photoDao();

}
