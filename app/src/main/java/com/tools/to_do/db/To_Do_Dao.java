package com.tools.to_do.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface To_Do_Dao {
    @Query("select * from to_do")
    List<To_Do> getToDos();

    @Query("select * from to_do")
    LiveData<List<To_Do>> getLiveToDos();

    @Query("select * from to_do where _id=:id")
    To_Do getToDo(long id);

    @Delete
    void delete(To_Do to_do);

    @Insert
    long add(To_Do to_do);

    @Update
    void update(To_Do todo);
}
