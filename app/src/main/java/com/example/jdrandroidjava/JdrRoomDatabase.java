package com.example.jdrandroidjava;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.jdrandroidjava.characterClass.Character;
import com.example.jdrandroidjava.characterClass.CharacterDao;
import com.example.jdrandroidjava.itemClass.Item;
import com.example.jdrandroidjava.itemClass.ItemDao;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Character.class, Item.class}, version = 1, exportSchema = false)
public abstract class JdrRoomDatabase extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile JdrRoomDatabase INSTANCE;

    // --- DAO ---
    public abstract ItemDao itemDao();
    public abstract CharacterDao characterDao();
    public static final ExecutorService databaseWriteExecutor =
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

    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {

                CharacterDao characterDao = INSTANCE.characterDao();
                characterDao.deleteAll();

                Character character = new Character("Perso1", 100);
                characterDao.insert(character);
                character = new Character("Perso2", 150);
                characterDao.insert(character);
                character = new Character("Perso3", 350);
                characterDao.insert(character);
                character = new Character("Perso4", 100);
                characterDao.insert(character);
                character = new Character("Perso5", 100);
                characterDao.insert(character);

                ItemDao itemDao = INSTANCE.itemDao();

                Item item = new Item("Ep??e", "Une super ??p??e trop cool", 10, 1);
                itemDao.insert(item);
                item = new Item("Manche", "Un bout de bois", 10, 1);
                itemDao.insert(item);
                item = new Item("Bouclier", "Un tr??s vieux bouclier", 15, 1);
                itemDao.insert(item);
                item = new Item("Armure", "De l'acier rafistol??", 22, 1);
                itemDao.insert(item);
                item = new Item("Poulet", "Il faut bien manger", 22, 1);
                itemDao.insert(item);
            });
        }
    };


}
