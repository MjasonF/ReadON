package com.example.readon;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NotesQuery {
    @Query("SELECT * FROM notes where days = :days AND months = :months AND years = :years")
    List<Notes> getAll(int days, int months, int years);

    @Insert
    void insertAll(Notes... notes);

    @Delete
    void delete(Notes notes);
}

