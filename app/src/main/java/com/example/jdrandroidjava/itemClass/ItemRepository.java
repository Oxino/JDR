package com.example.jdrandroidjava.itemClass;

import android.app.Application;

import com.example.jdrandroidjava.JdrRoomDatabase;
import com.example.jdrandroidjava.itemClass.Item;
import com.example.jdrandroidjava.itemClass.ItemDao;

class ItemRepository {

    public ItemDao mItemDao;

    ItemRepository(Application application) {
        JdrRoomDatabase db = JdrRoomDatabase.getDatabase(application);
        mItemDao = db.itemDao();
    }

    void insert(Item item) {
        JdrRoomDatabase.databaseWriteExecutor.execute(() -> mItemDao.insert(item));
    }

    void update(Item item){
        JdrRoomDatabase.databaseWriteExecutor.execute(() -> mItemDao.updateItem(item.getName(), item.getSize(), item.getDescription(), item.getId()));
    }

    void delete(int id) {
        JdrRoomDatabase.databaseWriteExecutor.execute(() -> mItemDao.delete(id));
    }

}

