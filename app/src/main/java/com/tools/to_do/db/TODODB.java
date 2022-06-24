package com.tools.to_do.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {To_Do.class},version = 1)
public abstract class TODODB extends RoomDatabase {
    public abstract To_Do_Dao  getToDoDao();
}
