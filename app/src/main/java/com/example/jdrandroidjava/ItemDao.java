package com.example.jdrandroidjava;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Item item);

    @Query("DELETE FROM item_table")
    void deleteAll();

    @Query("DELETE FROM item_table WHERE id = :id")
    void delete(int id);

    @Query("SELECT * FROM item_table WHERE fk_character_id = :characterId ORDER BY id ASC")
    LiveData<List<Item>> getItems(int characterId);

    @Query("UPDATE item_table SET name = :name , size = :size, description = :description WHERE id = :id")
    void updateItem(String name, int size, String description, int id);
}
