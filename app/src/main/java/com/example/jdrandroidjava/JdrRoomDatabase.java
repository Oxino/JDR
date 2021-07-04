package com.example.jdrandroidjava;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;
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
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                CharacterDao characterDao = INSTANCE.characterDao();
                characterDao.deleteAll();

                Character character = new Character("Lucas");
                characterDao.insert(character);
                character = new Character("Lucas2");
                characterDao.insert(character);
                character = new Character("Lucas3");
                characterDao.insert(character);
                character = new Character("Lucas4");
                characterDao.insert(character);
                character = new Character("Lucas5");
                characterDao.insert(character);

                ItemDao itemDao = INSTANCE.itemDao();

                Item item = new Item("obet", "10", 10, 1);
                itemDao.insert(item);
            });
        }
    };


}
