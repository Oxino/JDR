package com.example.jdrandroidjava;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.io.Serializable;
import java.util.List;

public class CharacterWithItems implements Serializable {

    @Embedded
    public Character character;
    @Relation(
            parentColumn = "id",
            entityColumn = "fk_character_id",
            entity = Item.class
    )
    public List<Item> items;

    public String getActualStorageToString(){
        int size = 0;
        for (Item item: items) {
            size += item.size;
        }
        return size + "/" + character.storage;
    }

    public int getActualStorage(){
        int size = 0;
        for (Item item: items) {
            size += item.size;
        }
        return size;
    }


}