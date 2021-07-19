package com.example.jdrandroidjava;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName="character_table")
public class Character implements Serializable {

    @NonNull
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    protected int id;

    @NonNull
    @ColumnInfo(name = "name")
    protected final String name;

    @NonNull
    @ColumnInfo(name = "storage")
    protected int storage;

    public Character(@NonNull String name, @NonNull int storage){
        this.name = name;
        this.storage = storage;
    }

    @Ignore
    public Character(@NonNull String name, @NonNull int storage, @NonNull int id){
        this.name = name;
        this.storage = storage;
        this.id = id;
    }

    public int getStorage(){
        return this.storage;
    }

    public String getName() { return this.name;}

    public int getId() { return this.id;}
}
