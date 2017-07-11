package com.whg.util.collection.list;

public interface LongList {

    void ensureCapacity(int minCapacity);

    int size();

    boolean isEmpty();

    boolean contains(long o);

    int indexOf(long o);

    int lastIndexOf(long o);

    long[] toArray();

    long get(int index);

    long set(int index, long element);

    boolean add(long e);

    void add(int index, long element);

    long removeByIndex(int index);

    boolean remove(long o);

    void clear();
    
    void sort();
    
    void trimToSize();

}