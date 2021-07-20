package com.example.jdrandroidjava.itemClass;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;

public class ItemViewModel extends AndroidViewModel {

    private final ItemRepository mRepository;

    public ItemViewModel(Application application) {
        super(application);
        mRepository = new ItemRepository(application);
    }

    public void insert(Item item){
        mRepository.insert(item);
    }

    public void update(Item item){
        mRepository.update(item);
    }

    public void delete(int id){
        mRepository.delete(id);
    }


}

