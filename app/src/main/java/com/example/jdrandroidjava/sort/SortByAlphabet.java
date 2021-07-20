package com.example.jdrandroidjava.sort;

import com.example.jdrandroidjava.itemClass.Item;

import java.util.Comparator;

public class SortByAlphabet implements Comparator<Item>
{

    public int compare(Item a, Item b)
    {
        return a.getName().compareTo(b.getName());
    }

}
