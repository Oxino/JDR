package com.example.jdrandroidjava;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CharacterViewModel extends AndroidViewModel {

    private CharacterRepository mRepository;

    private LiveData<List<Character>> mAllCharacters;

    private LiveData<List<CharacterWithItems>> mAllCharactersWithItems;

    public CharacterViewModel(@NonNull Application application) {
        super(application);
        mRepository = new CharacterRepository(application);
        mAllCharacters = mRepository.getAllCharacters();
        mAllCharactersWithItems = mRepository.getAllCharactersWithItems();
    }


    LiveData<List<Character>> getmAllCharacters() {
        return mAllCharacters;
    }

    LiveData<List<CharacterWithItems>> getmAllCharactersWithItems() {
        return mAllCharactersWithItems;
    }

}

