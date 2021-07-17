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
    protected int id;

    @NonNull
    @ColumnInfo(name = "name")
    protected final String name;

    @NonNull
    @ColumnInfo(name = "storage")
    protected int storage = 100;

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

    public String getName() { return this.name;}

    public int getId() { return this.id;}
}
