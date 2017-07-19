/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.csbeng.nice2mit.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Carmine Ingaldi
 */
public class LRUMap <K , V> extends LinkedHashMap
{
    private int capacity;

    public LRUMap(int capacity)
    {
        super(capacity + 1, 1.1f, true);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry entry)
    {
        return size () > capacity;
    }


    
    
    

}
