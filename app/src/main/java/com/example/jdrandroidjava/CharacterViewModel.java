package com.example.jdrandroidjava;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CharacterViewModel extends AndroidViewModel {

    private CharacterRepository mRepository;

    private final LiveData<List<Character>> mAllCharacters;

    public CharacterViewModel(Application application) {
        super(application);
        mRepository = new CharacterRepository(application);
        mAllCharacters = mRepository.getAllCharacters();
    }

    LiveData<List<Character>> getmAllCharacters() {
        return mAllCharacters;
    }

}

