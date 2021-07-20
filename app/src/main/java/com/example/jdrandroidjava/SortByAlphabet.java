package com.example.jdrandroidjava;

import java.util.Comparator;

class SortByAlphabet implements Comparator<Item>
{

    public int compare(Item a, Item b)
    {
        return a.getName().compareTo(b.getName());
    }

}
