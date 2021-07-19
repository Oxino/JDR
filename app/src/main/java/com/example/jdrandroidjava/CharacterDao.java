package com.example.jdrandroidjava;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Character character);

    @Query("DELETE FROM character_table")
    void deleteAll();

    @Query("SELECT * FROM character_table ORDER BY id ASC")
    LiveData<List<Character>> getCharacters();

    @Transaction
    @Query("SELECT * FROM character_table WHERE id = :id")
    LiveData<CharacterWithItems> getCharacter(int id);

    @Query("UPDATE character_table SET name = :name , storage = :storage WHERE id = :id")
    void updateCharacter(String name, int storage, int id);

    @Query("DELETE FROM character_table WHERE id = :id")
    void delete(int id);

    @Transaction
    @Query("SELECT * FROM character_table")
    LiveData<List<CharacterWithItems>> getCharacterWithItems();
}
