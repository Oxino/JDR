package com.example.jdrandroidjava.characterWithItemsClass;


import android.app.Application;
import androidx.lifecycle.LiveData;

import com.example.jdrandroidjava.JdrRoomDatabase;
import com.example.jdrandroidjava.characterClass.CharacterDao;
import com.example.jdrandroidjava.characterWithItemsClass.CharacterWithItems;

import java.util.List;


class CharacterWithItemsRepository {

    private final CharacterDao mCharacterDao;
    private final LiveData<List<CharacterWithItems>> mAllCharactersWithItems;

    CharacterWithItemsRepository(Application application) {
        JdrRoomDatabase db = JdrRoomDatabase.getDatabase(application);
        mCharacterDao = db.characterDao();
        mAllCharactersWithItems = mCharacterDao.getCharacterWithItems();
    }

    LiveData<List<CharacterWithItems>> getAllCharactersWithItems() {
        return mAllCharactersWithItems;
    }

    LiveData<CharacterWithItems> getCharacterWithItems(int id){
        return mCharacterDao.getCharacter(id);
    }

}

