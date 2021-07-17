package com.example.jdrandroidjava;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;



public class CharacterWithItems {
    @Embedded
    public Character character;
    @Relation(
            parentColumn = "id",
            entityColumn = "fk_character_id",
            entity = Item.class
    )
    public List<Item> items;
}
