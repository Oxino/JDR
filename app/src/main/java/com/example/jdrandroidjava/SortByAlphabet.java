package com.example.jdrandroidjava;

import java.util.Comparator;
import java.util.List;

class SortByAlphabet implements Comparator<Item>
{

    public int compare(Item a, Item b)
    {
        return a.getName().compareTo(b.getName());
    }

}
