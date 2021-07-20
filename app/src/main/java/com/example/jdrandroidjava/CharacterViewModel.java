package com.example.jdrandroidjava;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CharacterViewModel extends AndroidViewModel {

    private final CharacterRepository mRepository;

    private final LiveData<List<Character>> mAllCharacters;

    public CharacterViewModel(@NonNull Application application) {
        super(application);
        mRepository = new CharacterRepository(application);
        mAllCharacters = mRepository.getAllCharacters();
    }

    public LiveData<List<Character>> getmAllCharacters() {
        return mAllCharacters;
    }

    public void insert(Character character){
        mRepository.insert(character);
    }

    public void update(Character character){
        mRepository.update(character);
    }


    public void delete(int id){
        mRepository.delete(id);
    }


}

