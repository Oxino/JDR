package com.example.jdrandroidjava;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName="item_table",
        foreignKeys = @ForeignKey(entity = Character.class, parentColumns = "id", childColumns = "fk_character_id"), indices = @Index("fk_character_id"))
public class Item {

    @NonNull
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    protected int id;

    @NonNull
    @ColumnInfo(name = "fk_character_id")
    protected int characterId;

    @NonNull
    @ColumnInfo(name = "name")
    protected final String name;

    @NonNull
    @ColumnInfo(name = "size")
    protected int size;

    @ColumnInfo(name = "description")
    protected String description;

    public Item(@NonNull String name, String description, @NonNull int size, @NonNull int characterId){
        this.name = name;
        this.description = description;
        this.size = size;
        this.characterId = characterId;
    }


    public int getId(){return this.id;}

    public int getCharacterId(){return this.characterId;}

    public String getName(){return this.name;}

    public int getSize(){return this.size;}

    public String getDescription(){return this.description;}
}
