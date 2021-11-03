package com.etsija.jefuscores.db;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface GamelogDao {

    @Insert
    void insert(Gamelog gamelog);

    @Update
    void upoate(Gamelog gamelog);

    @Delete
    void delete(Gamelog gamelog);

    @Query("DELETE FROM gamelog_table")
    void deleteAllGamelogs();

    @Query("SELECT * from gamelog_table ORDER BY date DESC, time DESC")
    LiveData<List<Gamelog>> getAllGamelogs();
}
