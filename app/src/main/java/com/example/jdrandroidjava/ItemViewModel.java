package com.example.jdrandroidjava;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {

    private ItemRepository mRepository;

    public ItemViewModel(Application application) {
        super(application);
        mRepository = new ItemRepository(application);
    }

    public void insert(Item item){
        mRepository.insert(item);
    }

}

