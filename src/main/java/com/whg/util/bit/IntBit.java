package com.whg.util.bit;

import java.util.Arrays;

import org.springframework.util.Assert;

import com.google.protobuf.InvalidProtocolBufferException;
import com.whg.protobuf.ToolProtobuf.IntBitProto;
import com.whg.websocket.server.framework.protobuf.ProtobufSerializable;

/**
 * <p>描述：int值代表可用二进制（0和1）表示32个布尔boolean标记位,1为真0为假;index从0开始一直到31</p>
 * @author whg
 * @date 2016-11-19 上午03:35:51
 */
public class IntBit implements ProtobufSerializable<IntBitProto>{

	private int bit;
	
	public static IntBit defaultIntBit(){
		return new IntBit();
	}
	
	/** 重置所有位都为假（即0） */
	public static IntBit allTrueIntBit(int usableLength){
		IntBit intBit =  new IntBit();
		intBit.allTrue(usableLength);
		return intBit;
	}
	
	private IntBit(){
		bit = 0;
	}
	
	public IntBit(IntBitProto proto){
		copyFrom(proto);
	}
	
	/**
	 * 位置index是否为真（即1）
	 * @param index 0到31
	 * @return 真为1，假为0
	 */
	public boolean isTrue(int index){
		int temp = 1 << index;
		return (bit & temp) == temp;
	}
	
	/**
	 * 记录位置index为真（即1）
	 * @param index 0到31
	 */
	public void recordTrue(int index){
		record(index, true);
	}
	
	/**
	 * 记录位置index为假（即0）
	 * @param index 0到31
	 */
	public void recordFalse(int index){
		record(index, false);
	}
	
	public void toggle(int index){
		record(index, !isTrue(index));
	}
	
	public void record(int index, boolean isTrue){
		int temp = 1 << index;
		bit = (isTrue ? bit | temp : bit & ~temp);
	}
	
	/** 重置usableLength个位都为真（即1） */
	public void allTrue(int usableLength){
		for(int i=0;i<usableLength;i++){
			recordTrue(i);
		}
	}
	
	/** 重置所有位都为假（即0） */
	public void allFalse(){
		bit = 0;
	}
	
	public boolean[] getBooleanFlags(int size){
		Assert.isTrue(size <= Integer.SIZE);
		boolean[] booleanFlag = new boolean[size];
		for(int i=0;i<size;i++){
			booleanFlag[i] = isTrue(i);
		}
		return booleanFlag;
	}

	@Override
	public String toString() {
		return "IntBitFlag [bit=" + bit + ", binary=" + Integer.toBinaryString(bit) + "]";
	}
	
	@Override
	public void parseFrom(byte[] bytes){
		try {
            copyFrom(IntBitProto.parseFrom(bytes));
        } catch (InvalidProtocolBufferException ex) {
        	throw new IllegalArgumentException(ex);
        }
	}
	
	@Override
	public void copyFrom(IntBitProto proto) {
		bit = proto.getBit();
	}
	
	@Override
	public byte[] toByteArray() {
		return copyTo().toByteArray();
	}
	
	@Override
	public IntBitProto copyTo() {
		IntBitProto.Builder builder = IntBitProto.newBuilder();
		builder.setBit(bit);
		return builder.build();
	}
	
	public static void main(String[] args) {
		IntBit intBit = new IntBit();
		System.out.println(intBit);
		intBit.recordTrue(0);
		System.out.println(intBit);
		intBit.recordTrue(1);
		System.out.println(intBit);
		intBit.recordTrue(2);
		System.out.println(intBit);
		System.out.println();
		
		intBit.recordFalse(1);
		System.out.println(intBit);
		System.out.println(Arrays.toString(intBit.getBooleanFlags(3)));
		System.out.println(Arrays.toString(intBit.getBooleanFlags(5)));
		System.out.println();
		
		intBit.allFalse();
		System.out.println(intBit);
		
		intBit = IntBit.allTrueIntBit(3);
		System.out.println(intBit);
		
		System.out.println();
		intBit.toggle(1);
		System.out.println(intBit);
		intBit.toggle(1);
		System.out.println(intBit);
		intBit.toggle(1);
		System.out.println(intBit);
	}
	
}
