package com.example.jdrandroidjava;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Character.class, Item.class}, version = 1, exportSchema = false)
public abstract class JdrRoomDatabase extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile JdrRoomDatabase INSTANCE;

    // --- DAO ---
    public abstract ItemDao itemDao();
    public abstract CharacterDao characterDao();
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(1);

    // --- INSTANCE ---
    public static JdrRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (JdrRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            JdrRoomDatabase.class, "MyDatabase.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
