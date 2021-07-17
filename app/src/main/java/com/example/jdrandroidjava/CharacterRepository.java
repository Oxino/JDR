package com.example.jdrandroidjava;


import android.app.Application;
import androidx.lifecycle.LiveData;

import java.util.List;


class CharacterRepository {

    private CharacterDao mCharacterDao;
    private LiveData<List<Character>> mAllCharacters;

    CharacterRepository(Application application) {
        JdrRoomDatabase db = JdrRoomDatabase.getDatabase(application);
        mCharacterDao = db.characterDao();
        mAllCharacters = mCharacterDao.getCharacters();
    }

    LiveData<List<Character>> getAllCharacters() {
        return mAllCharacters;
    }


    void insert(Character character) {
        JdrRoomDatabase.databaseWriteExecutor.execute(() -> {
            mCharacterDao.insert(character);
        });
    }

}

