package com.whg.util.bit;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.whg.protobuf.ToolProtobuf.BytesListProto;
import com.whg.websocket.server.framework.protobuf.ProtobufSerializable;

public class BytesList implements List<byte[]>, ProtobufSerializable<BytesListProto> {

    private LinkedList<byte[]> elements;

    public BytesList() {
        this.elements = new LinkedList<byte[]>();
    }

    public BytesList(byte[] bytes) {
        this();
        parseFrom(bytes);
    }

    public LinkedList<byte[]> getElements() {
        return elements;
    }

    public void setElements(LinkedList<byte[]> elements) {
        this.elements = elements;
    }

    @Override
    public void copyFrom(BytesListProto proto) {
        int size = proto.getElementsCount();
        for (int i = 0; i < size; i++) {
            this.elements.add(proto.getElements(i).toByteArray());
        }
    }

    @Override
    public BytesListProto copyTo() {
    	BytesListProto.Builder builder = BytesListProto.newBuilder();
        for (byte[] bytes : elements) {
            builder.addElements(ByteString.copyFrom(bytes));
        }
        return builder.build();
    }

    @Override
    public void parseFrom(byte[] bytes) {
        try {
        	BytesListProto simpleList = BytesListProto.parseFrom(bytes);
            copyFrom(simpleList);
        } catch (InvalidProtocolBufferException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public byte[] toByteArray() {
    	return copyTo().toByteArray();
    }

    @Override
    public boolean add(byte[] e) {
        return this.elements.add(e);
    }

    @Override
    public void add(int index, byte[] element) {
        this.elements.add(index, element);
    }

    @Override
    public boolean addAll(Collection<? extends byte[]> c) {
        return this.elements.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends byte[]> c) {
        return this.elements.addAll(index, c);
    }

    @Override
    public void clear() {
        this.elements.clear();
    }

    @Override
    public boolean contains(Object o) {
        return this.elements.contains(o);
    }
    
    public boolean deepContains(byte[] b) {
    	for (byte[] c : elements) {
			if(Arrays.equals(b, c)){
				return true;
			}
		}
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return this.elements.containsAll(c);
    }

    @Override
    public byte[] get(int index) {
        return this.elements.get(index);
    }

    @Override
    public int indexOf(Object o) {
        return this.elements.indexOf(o);
    }
    
    public int deepIndexOf(byte[] b) {
    	int index=0;
    	for (byte[] c : elements) {
			if(Arrays.equals(b, c)){
				return index;
			}
			index++;
		}
    	return -1;
    }

    @Override
    public boolean isEmpty() {
        return this.elements.isEmpty();
    }

    @Override
    public Iterator<byte[]> iterator() {
        return this.elements.iterator();
    }

    @Override
    public int lastIndexOf(Object o) {
        return this.elements.lastIndexOf(o);
    }

    @Override
    public ListIterator<byte[]> listIterator() {
        return this.elements.listIterator();
    }

    @Override
    public ListIterator<byte[]> listIterator(int index) {
        return this.elements.listIterator(index);
    }

    @Override
    public boolean remove(Object o) {
        return this.elements.remove(o);
    }
    
    public byte[] deepRemove(byte[] b) {
    	int index=this.deepIndexOf(b);
    	if(index==-1){
    		return null;
    	}
        return this.elements.remove(index);
    }

    @Override
    public byte[] remove(int index) {
        return this.elements.remove(index);
    }

    public byte[] removeFirst() {
        return this.elements.removeFirst();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return this.elements.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return this.elements.retainAll(c);
    }

    @Override
    public byte[] set(int index, byte[] element) {
        return this.elements.set(index, element);
    }

    @Override
    public int size() {
        return this.elements.size();
    }

    @Override
    public List<byte[]> subList(int fromIndex, int toIndex) {
        return this.elements.subList(fromIndex, toIndex);
    }

    @Override
    public Object[] toArray() {
        return this.elements.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return this.elements.toArray(a);
    }

    public void add(byte[] bytes, int limit) {
        while (this.size() > limit) {
            this.elements.removeFirst();
        }

        this.elements.add(bytes);
    }
    
    public void swap(int srcIndex, int destIndex){
    	byte[] destBytes = get(destIndex);
    	set(destIndex, set(srcIndex, destBytes));
    }

}
