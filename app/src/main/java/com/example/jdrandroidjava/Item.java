package com.example.jdrandroidjava;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName="item_table",
        foreignKeys = @ForeignKey(entity = Character.class, parentColumns = "id", childColumns = "characterId")
)
public class Item {

    @NonNull
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    protected int id;

    @NonNull
    @ColumnInfo(name = "characterId")
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
}
