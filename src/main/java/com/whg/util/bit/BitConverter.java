package com.whg.util.bit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class BitConverter {

    public static byte[] int2bytes(int intValue) {
        byte[] result = new byte[4];
        for (int i = 3; i >= 0; i--) {
            result[i] = (byte) (intValue & 0xff);
            intValue >>= 8;
        }
        return result;
    }
    
    public static int bytes2oneInt(byte[] bytes) {
        return bytes2int(bytes, 0);
    }

    public static int bytes2int(byte[] bytes, int pos) {
        int result = 0;
        result |= (bytes[pos] & 0xff);
        for (int i = pos; i < pos + 4; i++) {
            result <<= 8;
            result |= (bytes[i] & 0xff);
        }
        return result;
    }
    
    public static byte[][] stringList2bytesArray(List<String> stringList) {
		return stringArray2bytesArray(stringList.toArray(new String[stringList.size()]));
    }
    
    public static byte[][] stringArray2bytesArray(String... stringArray) {
		byte[][] bytesArray = new byte[stringArray.length][];
		for(int i=0;i<stringArray.length;i++){
			bytesArray[i] = stringArray[i].getBytes();
		}
		return bytesArray;
    }
    
    public static List<byte[]> longCol2bytesCol(Collection<Long> longList) {
		return longArray2bytesList(longList.toArray(new Long[longList.size()]));
    }
    
    public static byte[][] longCol2bytesArray(Collection<Long> longList) {
		return longArray2bytesArray(longList.toArray(new Long[longList.size()]));
    }
    
    public static byte[][] longArray2bytesArray(Long... longArray) {
		return longArray2bytesList(longArray).toArray(new byte[longArray.length][]);
    }
    
    public static List<byte[]> longArray2bytesList(Long... longArray) {
		List<byte[]> bytesList = new ArrayList<byte[]>(longArray.length);
		for(int i=0;i<longArray.length;i++){
			bytesList.add(long2bytes(longArray[i]));
		}
		return bytesList;
    }
    
    public static byte[][] bytesCol2bytesArray(Collection<byte[]> bytesCol){
		byte[][] bytesArray = new byte[bytesCol.size()][];
		Iterator<byte[]> it = bytesCol.iterator();
		int i=0;
		while(it.hasNext()){
			bytesArray[i++] = it.next();
		}
		return bytesArray;
    }
    
    public static List<String> bytesCol2stringList(Collection<byte[]> bytesCol){
		List<String> stringList = new ArrayList<String>(bytesCol.size());
		Iterator<byte[]> it = bytesCol.iterator();
		while(it.hasNext()){
			stringList.add(new String(it.next()));
		}
		return stringList;
    }
    
    public static List<Long> bytesCol2longList(Collection<byte[]> bytesCol){
    	List<Long> longList = new ArrayList<Long>(bytesCol.size());
    	for(byte[] bytes:bytesCol){
    		longList.add(bytes2long(bytes));
    	}
    	return longList;
    	
    }

    public static byte[] long2bytes(long input) {
        byte[] result = new byte[8];
        for (int i = 7; i >= 0; i--) {
            result[i] = (byte) (input & 0xff);
            input >>= 8;
        }
        return result;
    }
    
    /**
     * 使用long2bytes存储优于String存储，
     * 是因为long的byte长度固定为8个字节64位，
     * 而String转成byte后的长度可能会很长
     */
    public static void main(String[] args) {
		long id = Long.MAX_VALUE;
		String idStr = id+"";
		
		System.out.println(new String(idStr.getBytes()));
    	System.out.println(idStr.getBytes().length);
    	
    	System.out.println(new String(long2bytes(id)));
    	System.out.println(long2bytes(id).length);
	}
    
    public static long bytes2long(byte[] bytes) {
        return bytes2long(bytes, 0);
    }

    public static long bytes2long(byte[] bytes, int pos) {
        long result = 0;
        result |= (bytes[pos] & 0xff);
        for (int i = pos; i < pos + 8; i++) {
            result <<= 8;
            result |= (bytes[i] & 0xff);
        }
        return result;
    }

    public static int[] bytes2int(byte[] bytes) {
        int[] results = new int[bytes.length / 4];
        int pos = 0;
        for (int i = 0; i < results.length; i++) {
            results[i] = bytes2int(bytes, pos);
            pos += 4;

        }
        return results;
    }
}
