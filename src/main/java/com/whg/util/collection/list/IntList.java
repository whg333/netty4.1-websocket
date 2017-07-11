package com.whg.util.collection.list;


public interface IntList {

    void ensureCapacity(int minCapacity);

    int size();

    boolean isEmpty();

    boolean contains(int o);

    int indexOf(int o);

    int lastIndexOf(int o);

    int[] toArray();

    int get(int index);

    int set(int index, int element);

    boolean add(int e);

    void add(int index, int element);

    int removeByIndex(int index);

    boolean remove(int o);

    void clear();
    
    void sort();
    
    void trimToSize();

}