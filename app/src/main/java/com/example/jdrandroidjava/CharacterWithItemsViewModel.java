package com.example.jdrandroidjava;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CharacterWithItemsViewModel extends AndroidViewModel {

    private CharacterWithItemsRepository mRepository;

    private LiveData<List<CharacterWithItems>> mAllCharactersWithItems;


    public CharacterWithItemsViewModel(@NonNull Application application) {
        super(application);
        mRepository = new CharacterWithItemsRepository(application);
        mAllCharactersWithItems = mRepository.getAllCharactersWithItems();
    }

    LiveData<List<CharacterWithItems>> getmAllCharactersWithItems() {
        return mAllCharactersWithItems;
    }

    LiveData<CharacterWithItems> getCharacterWithItems(int id){
        return mRepository.getCharacterWithItems(id);
    }

}
