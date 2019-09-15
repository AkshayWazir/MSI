package org.lol.wazirbuild.msilib.Database.Daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import org.lol.wazirbuild.msilib.Database.model.notes_category_model;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface notes_category_dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(notes_category_model note);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertAll(List<notes_category_model> notes);

    @Delete
    void deleteNote(notes_category_model notes);

    @Query("SELECT * FROM Notes WHERE id=:id")
    notes_category_model getNote(int id);

    @Query("SELECT * FROM Notes ORDER BY date DESC")
    LiveData<List<notes_category_model>> getAllNotes();

    @Query("DELETE FROM Notes")
    int deleteAllNotes();

    @Query("SELECT COUNT(*) FROM Notes")
    int getCount();
}
