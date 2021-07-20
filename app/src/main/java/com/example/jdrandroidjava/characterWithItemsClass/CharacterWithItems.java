package com.example.jdrandroidjava.characterWithItemsClass;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.jdrandroidjava.itemClass.Item;
import com.example.jdrandroidjava.characterClass.Character;

import java.io.Serializable;
import java.util.List;

public class CharacterWithItems  implements Serializable {

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
            size += item.getSize();
        }
        return size + "/" + character.getStorage();
    }

    public int getActualStorage(){
        int size = 0;
        for (Item item: items) {
            size += item.getSize();
        }
        return size;
    }

    public boolean canHaveThisItem(int size){
        return this.character.getStorage() - (this.getActualStorage() + size) >= 0;
    }


}

