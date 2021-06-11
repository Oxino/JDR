package com.example.jdrandroidjava;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import java.util.ArrayList;

@Entity(tableName="character_table")
public class Character {

    @NonNull
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "name")
    private final String name;

    @NonNull
    @ColumnInfo(name = "storage")
    private int storage = 100;

    public Character(@NonNull String name){
        this.name = name;
    }

    public Character(@NonNull String name, int clutter){
        this.name = name;
        this.storage = clutter;
    }

    public int getStorage(){
        return this.storage;
    }
}
