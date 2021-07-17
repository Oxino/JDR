package com.example.jdrandroidjava;

import android.app.Application;
import androidx.lifecycle.LiveData;

import java.util.List;


class ItemRepository {

    public ItemDao mItemDao;
    private LiveData<List<Item>> mAllItems;
    private Character character;

    ItemRepository(Application application) {
        JdrRoomDatabase db = JdrRoomDatabase.getDatabase(application);
        mItemDao = db.itemDao();
        mAllItems = mItemDao.getItems(character.getId());
    }

    LiveData<List<Item>> getAllItems() {
        return mAllItems;
    }

    void insert(Item item) {
        JdrRoomDatabase.databaseWriteExecutor.execute(() -> {
            mItemDao.insert(item);
        });
    }

}

