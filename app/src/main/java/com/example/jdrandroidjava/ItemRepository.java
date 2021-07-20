package com.example.jdrandroidjava;

import android.app.Application;
import androidx.lifecycle.LiveData;

import java.util.List;


class ItemRepository {

    public ItemDao mItemDao;

    ItemRepository(Application application) {
        JdrRoomDatabase db = JdrRoomDatabase.getDatabase(application);
        mItemDao = db.itemDao();
    }

    void insert(Item item) {
        JdrRoomDatabase.databaseWriteExecutor.execute(() -> {
            mItemDao.insert(item);
        });
    }
    void update(Item item){
        JdrRoomDatabase.databaseWriteExecutor.execute(() -> {
            mItemDao.updateItem(item.getName(), item.getSize(), item.getDescription(), item.characterId);;
        });
    }

}

