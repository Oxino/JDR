package com.example.jdrandroidjava;

import android.app.Application;
import androidx.lifecycle.LiveData;

import java.util.List;



public class CharacterWithItemsRepository {

    private CharacterDao mCharacterDao;
    private LiveData<List<CharacterWithItems>> mAllCharactersWithItems;

    CharacterWithItemsRepository(Application application) {
        JdrRoomDatabase db = JdrRoomDatabase.getDatabase(application);
        mCharacterDao = db.characterDao();
        mAllCharactersWithItems = mCharacterDao.getCharacterWithItems();
    }

    LiveData<List<CharacterWithItems>> getAllCharactersWithItems() {
        return mAllCharactersWithItems;
    }
}
