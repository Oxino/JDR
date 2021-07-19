package com.example.jdrandroidjava;

import android.app.Application;
import androidx.lifecycle.LiveData;

import java.util.List;


class ItemRepository {

    private ItemDao mItemDao;

    ItemRepository(Application application) {
        JdrRoomDatabase db = JdrRoomDatabase.getDatabase(application);
        mItemDao = db.itemDao();
    }

    LiveData<List<Item>> getAllItems(int characterId) {
        return mItemDao.getItems(characterId);
    }

    void delete(int id) {
        JdrRoomDatabase.databaseWriteExecutor.execute(() -> {
            mItemDao.delete(id);
        });
    }

}
