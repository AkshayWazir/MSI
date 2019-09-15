package org.lol.wazirbuild.msilib.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import org.lol.wazirbuild.msilib.Database.Daos.notes_category_dao;
import org.lol.wazirbuild.msilib.Database.model.notes_category_model;

@Database(entities = {notes_category_model.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class notes_category_database extends RoomDatabase {

    public static final String DATABASE_NAME = "notesdatabase.db";
    public static volatile notes_category_database instance;
    public static final Object LOCK = new Object();

    public abstract notes_category_dao notesdao();

    public static notes_category_database getInstance(Context context) {

        if (instance == null)
            synchronized (LOCK) {
                if (instance == null) {

                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            notes_category_database.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }

        return instance;
    }
}
