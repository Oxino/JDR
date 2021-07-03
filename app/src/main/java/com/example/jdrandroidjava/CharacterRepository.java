package com.example.jdrandroidjava;


import android.app.Application;
import androidx.lifecycle.LiveData;

import java.util.List;


class CharacterRepository {

    private CharacterDao mCharacterDao;
    private LiveData<List<Character>> mAllCharacters;
    private LiveData<List<CharacterWithItems>> mAllCharactersWithItems;


    CharacterRepository(Application application) {
        JdrRoomDatabase db = JdrRoomDatabase.getDatabase(application);
        mCharacterDao = db.characterDao();
        mAllCharacters = mCharacterDao.getCharacters();
        mAllCharactersWithItems = mCharacterDao.getCharacterWithItems();
    }

    LiveData<List<Character>> getAllCharacters() {
        return mAllCharacters;
    }

    LiveData<List<CharacterWithItems>> getAllCharactersWithItems() {
        return mAllCharactersWithItems;
    }

    void insert(Character character) {
        JdrRoomDatabase.databaseWriteExecutor.execute(() -> {
            mCharacterDao.insert(character);
        });
    }

}

