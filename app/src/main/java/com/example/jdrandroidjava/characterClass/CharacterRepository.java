package com.example.jdrandroidjava.characterClass;


import android.app.Application;
import androidx.lifecycle.LiveData;

import com.example.jdrandroidjava.JdrRoomDatabase;
import com.example.jdrandroidjava.characterClass.Character;
import com.example.jdrandroidjava.characterClass.CharacterDao;

import java.util.List;


class CharacterRepository {

    private final CharacterDao mCharacterDao;
    private final LiveData<List<Character>> mAllCharacters;


    CharacterRepository(Application application) {
        JdrRoomDatabase db = JdrRoomDatabase.getDatabase(application);
        mCharacterDao = db.characterDao();
        mAllCharacters = mCharacterDao.getCharacters();
    }

    LiveData<List<Character>> getAllCharacters() {
        return mAllCharacters;
    }


    void insert(Character character) {
        JdrRoomDatabase.databaseWriteExecutor.execute(() -> mCharacterDao.insert(character));
    }

    void update(Character character) {
        JdrRoomDatabase.databaseWriteExecutor.execute(() -> mCharacterDao.updateCharacter(character.getName(), character.getStorage(), character.getId()));
    }

    void delete(int id) {
        JdrRoomDatabase.databaseWriteExecutor.execute(() -> mCharacterDao.delete(id));
    }

}

