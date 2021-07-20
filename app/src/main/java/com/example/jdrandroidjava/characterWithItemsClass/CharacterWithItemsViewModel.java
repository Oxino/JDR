package com.example.jdrandroidjava.characterWithItemsClass;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.jdrandroidjava.characterWithItemsClass.CharacterWithItems;
import com.example.jdrandroidjava.characterWithItemsClass.CharacterWithItemsRepository;

import java.util.List;

public class CharacterWithItemsViewModel extends AndroidViewModel {

    private final CharacterWithItemsRepository mRepository;

    private final LiveData<List<CharacterWithItems>> mAllCharactersWithItems;


    public CharacterWithItemsViewModel(@NonNull Application application) {
        super(application);
        mRepository = new CharacterWithItemsRepository(application);
        mAllCharactersWithItems = mRepository.getAllCharactersWithItems();
    }

    public LiveData<List<CharacterWithItems>> getmAllCharactersWithItems() {
        return mAllCharactersWithItems;
    }

    public LiveData<CharacterWithItems> getCharacterWithItems(int id){
        return mRepository.getCharacterWithItems(id);
    }

}
