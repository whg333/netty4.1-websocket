package com.whg.backend.bo;

import java.util.concurrent.TimeUnit;

import com.whg.util.json.JsonDataQuerier;

public class Base {
	
	public static int INIT_ENERGY = 50;
	
	public static int STEAL_MIN_LAND_LEVEL = 3; //能被偷（随机富翁）的最小岛屿等级

	public static int MAX_RECORD_USER_NUM = 3;
	public static int MAX_SHIELD_NUM = 3;
	
	public static int MAX_ENERGY_VALUE = 50;
	public static long RECOVER_ENERGY_TIME = TimeUnit.HOURS.toMillis(1);//TimeUnit.SECONDS.toMillis(10);
	public static int RECOVER_ENERGY_PER_HOUR = 6;
	
	public static int ATTACK_SUCCESS_REWARD = 300000;
	public static int WANTED_SUCCESS_REWARD = 300000;
	public static int ATTACK_DEFENDED_REWARD = 100000;
	
	public static long ONLINE_EXPIRE_TIME = TimeUnit.MINUTES.toMillis(10);
	
	public static long STEAL_END_TIME = TimeUnit.HOURS.toMillis(8);
	public static int STEAL_LIMIT = 1500000;
	
	/** 玩家被锁定为随机攻击者之后的持续时间，避免并发问题——同一时刻随机到相同的攻击者 */
	public static long ATTACK_END_TIME = TimeUnit.MILLISECONDS.toMillis(5);
	
	public static int MAX_BUILDING_NUM = 5;
	public static int MAX_BUILDING_LEVEL = 5;
	
	public static int MAX_MINE_LEVEL = 5;
	public static int COLLECT_MIN_GOLD = 100;
	public static String LAND_PREFIX = "land_";
	public static int MAX_LAND_LEVEL = 35;
	
	public static int OPEN_PET_LAND_LEVEL = 4;
	public static int ALL_PET_NUM = 4;
	
	public static int HEART_ADD_PET_EXP = 50;
	public static long FOOD_ADD_PET_SKILL_TIME = TimeUnit.HOURS.toMillis(4);
	
	public static int MAX_FRIEND_SEND_ITEM_NUM = 20;
	public static int MAX_MESSAGE_NUM = 20;
	
	public static int MAX_RANK_NUM = 100;
	public static int BE_ATTACK_EXPIRE_DAY = 15;
	public static int EVIL_RANK_EXPIRE_DAY = 7; 
	
	public static int SMALL_WANTED_ORDER_HOUR = 24;
	public static int SMALL_WANTED_ORDER_NUM = 5;
	public static int BIG_WANTED_ORDER_HOUR = 48;
	public static int BIG_WANTED_ORDER_NUM = 10;
	
	public static int MAX_WORLD_MSG_NUM = 30;
	public static int MAX_USER_MSG_NUM = 20;
	
	public static int DAILY_MISSION_NUM = 5;
	
	public static int RECEIPT_GOLD_OPENING_LEVEL = 3;
	
	public static void init(BoFactory boFactory){
		JsonDataQuerier jsonDataQuerier = boFactory.getJsonDataQuerier();
		RECOVER_ENERGY_PER_HOUR = jsonDataQuerier.getBaseIntValue("RECOVER_ENERGY_PER_HOUR");
		
		ATTACK_SUCCESS_REWARD = jsonDataQuerier.getBaseIntValue("ATTACK_SUCCESS_REWARD");
		ATTACK_DEFENDED_REWARD = jsonDataQuerier.getBaseIntValue("ATTACK_DEFENDED_REWARD");
		STEAL_LIMIT = jsonDataQuerier.getBaseIntValue("STEAL_LIMIT");
		
		RECEIPT_GOLD_OPENING_LEVEL = jsonDataQuerier.getBaseIntValue("RECEIPT_GOLD_OPENING_LEVEL");
		
		SMALL_WANTED_ORDER_HOUR = jsonDataQuerier.getBaseIntValue("SMALL_WANTED_ORDER_HOUR");
		SMALL_WANTED_ORDER_NUM = jsonDataQuerier.getBaseIntValue("SMALL_WANTED_ORDER_NUM");
		BIG_WANTED_ORDER_HOUR = jsonDataQuerier.getBaseIntValue("BIG_WANTED_ORDER_HOUR");
		BIG_WANTED_ORDER_NUM = jsonDataQuerier.getBaseIntValue("BIG_WANTED_ORDER_NUM");
	}
	
}
