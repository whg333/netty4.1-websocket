package com.whg.util.exception;

import org.springframework.util.Assert;

import com.whg.util.collection.map.IntHashMap;

public enum ErrorCode {

	//LOGIN（登录操作）
	LOGIN_USER_ID_ERROR(10094, "userId不合法, userId=0"),
	LOGIN_USER_IS_BLACK(10102, "用户在黑名单中"),
	LOGIN_USER_IS_FROZEN(10095, "用户已被冻结"),
	LOGIN_USER_IS_NULL(10096, "用户不存在"),
	
	//COMMON(全局通用——操作)
	COMMON_CAN_NOT_OPERATING_MUTLI_THINGS(88, "不能同时进行操作"),
	COMMON_MAY_BE_USE_GAME_PLUGIN(10103, "不是合法的数据, 请联系客服咨询技术人员"),
	//COMMON(全局通用——数据库)
	COMMON_SYSTEM_ERROR(4000, "系统错误, 请刷新游戏"),
	COMMON_STORAGE_ERROR(4001,"系统存储错误"),
	COMMON_ADD_KEY_ERROR(4002, "首次添加key失败"),
	COMMON_EXIST_KEY_ERROR(4003, "此key已经存在不可再添加"),
	COMMON_NO_OPEN(4004,"该功能尚未开放"),
	//COMMON(全局通用——其他)
	COMMON_HAD_CLOSED_SERVER_ANNOUNCE(80001, "已经关闭过停服公告，不可频繁调用关闭停服公告接口"), 
	
	//USER(用户资源)
	USER_NOT_ENOUGH_GOLD(81001, "金钱不够"),
	USER_NOT_ENOUGH_GEM(81002, "钻石不足，请补充后再来"),
	USER_NOT_ENOUGH_POWER(81003, "体力不够"),
	USER_NOT_ENOUGH_SHIELD(81004, "护盾不足"),
	USER_NOT_ENOUGH_RESOURCE(81005, "用户资源不足"),
	USER_NOT_ENOUGH_ITEM(81006, "道具不足"),
	//USER(用户操作/状态)
	USER_NOT_IN_ATTACK_STATE(82001, "玩家未在攻击状态，不可以攻击/复仇"),
	USER_ATTACK_ERROR_TARGET(82002, "玩家攻击目标错误，不可以攻击"),
	USER_CAN_NOT_GACHA(82003, "玩家不在正常的转盘状态，不可以转转盘"),
	USER_CAN_NOT_ATTACK_SELF(82004, "不可以攻击自己"),
	USER_ATTACK_ERROR_REVENGE(82005, "玩家攻击复仇目标错误，不可以攻击"),
	USER_ATTACK_ERROR_WANTED(82006, "玩家攻击好友通缉目标错误，不可以攻击"),
	USER_CAN_NOT_ATTACK_EMPTY_LAND(82007, "不可以攻击空岛屿"),
	USER_NOT_IN_STEAL_STATE(82008, "玩家未在偷取状态，不可频繁点击偷取按钮"),
	USER_NOT_RANDOM_USER_TO_ATTACK(82009, "没有随机到可攻打的玩家"),
	USER_HAD_ALREADY_WANTED(82101, "该玩家已经被通缉了，不可再次通缉"),
	USER_FRIEND_FLYING_NOT_EXIST(82102, "好友家没有生成过飞行宠物，不可能抓到"),
	USER_FRIEND_FLYING_HAD_GONE(82103, "你手慢了没抓到！好友家的飞行宠物已经逃跑了"),
	USER_HAD_ALREADY_CATCH_FLYING(82104, "已经抓到过好友家的飞行宠物，不能再次抓到"),
	
	//BUILD（岛屿/建筑/金矿）
	BUILD_BUILDING_LEVEL_IS_MAX(83001, "此建筑已达到最大等级，不可以升级"),
	BUILD_CURRENT_MINE_NOT_OPEN(83002, "此金矿尚未开启"),
	BUILD_MINE_LEVEL_IS_MAX(83003, "此金矿已达到最大等级"),
	BUILD_CAN_NOT_COLLECT_MINE(83004, "收集矿山功能未开启"),
	BUILD_TOO_FEW_GOLD_TO_COLLECT(83005, "存钱罐金额太少不能收钱"),
	BUILD_BUILDING_CAN_NOT_REPAIR(83006, "此建筑不是破损待修状态，不可以进行修理"),
	BUILD_BUILDING_CAN_NOT_LEVEL_UP_IN_IMPAIRED(83007, "此建筑处于破损待修状态，不可以升级"),
	BUILD_CAN_NOT_LIKE_LAND_BEFORE_FINISH_BUILD(83008, "只有在升级完岛屿所有的建筑后才能点赞"),
	BUILD_BUILDING_IS_DESTORY(83009, "此建筑已经完全摧毁，不可再次攻击了"),
	BUILD_BUILDING_HAD_SHIELD(83010, "此建筑物上已经有护盾了"),
	
	//FRIEND（好友）
	FRIEND_IS_NOT_FRIEND(84000, "不是平台好友也不是游戏好友"),
	FRIEND_IS_NOT_GAME_FRIEND(84001, "不是游戏好友"),
	FRIEND_ALREADY_GAME_FRIEND(84002, "和对方已经是游戏好友"),
	FRIEND_CAN_NOT_SEND_FRIEND_AGAIN(84003, "已经赠送过给好友不能再次赠送"),
	FRIEND_NOT_SEND(84004, "好友尚未赠送不可领取"),
	FRIEND_CAN_NOT_RECEIVE_FRIEND_SEND_AGAIN(84005, "已经接收过好友赠送过不能再次接收"),
	FRIEND_CAN_NOT_GET_FRIEND_SEND_AGAIN(84006, "已经领取过好友赠送不能再次领取"),
	FRIEND_HAD_RECEIVED_APPLY(84007, "对方已经收到申请，请耐心等待对方的处理"),
	FRIEND_HAD_NOT_RECEIVED_APPLY(84008, "没有收到此玩家的申请游戏好友"),
	FRIEND_HAD_ALREADY_APPLY(84009, "您已经申请过，不可频繁重复申请，请耐心等待对方的处理"),
	FRIEND_RECEIVED_APPLY_IS_EMPTY(84010, "没有新申请的游戏好友，不必处理"),
	FRIEND_EXCEED_TODAY_HAD_GOT(84011, "超过每日领取上限"),
	
	//MINIGAME_MONSTER（迷你小游戏——打召唤兽）
	MINIGAME_MONSTER_NOT_BEEN_CALLED(85001, "怪兽尚未被唤醒，不可操作"),
	MINIGAME_MONSTER_HAD_BEEN_CALLED(85002, "怪兽已经被唤醒，无需再次召唤"),
	MINIGAME_MONSTER_HAD_GONE(85003, "怪兽已经逃走了，请重新召唤"),
	MINIGAME_MONSTER_NOT_DEAD(85004, "怪兽还未被打死，没有奖励"),
	MINIGAME_MONSTER_HAD_DEAD(85005, "怪兽已经被打死，不可再次攻击，请重新召唤"),
	MINIGAME_HAS_MONSTER_DEAD_AWARDS(85006, "上次打死怪兽的奖励尚未领取，请领取后才再次召唤怪兽"),
	MINIGAME_HAD_RECEIVED_MONSTER_AWARDS(85007, "怪兽奖励已领取过，不可再次领取"),
	MINIGAME_NOT_FOUND_HELP_MONSTER_AWARDS(85008, "未找到协助好友打死怪兽的奖励"),
	MINIGAME_NOT_FOUND_FRIEND_ASK_FOR_HELP_MONSTER(85009, "未找到好友请求协助打死怪兽"),
	
	//MINIGAME_TREE（迷你小游戏——魔法树）
	TREE_IS_NOT_EMPTY(85101, "魔法树上还有东西，不能使用魔法瓶"),
	TREE_IS_EMPTY_CAN_NOT_SHOOT(85102, "魔法树上没有东西，不能射击了"),
	
	//MINIGAME_PUZZLE（迷你小游戏——拼图）
	PUZZLE_HAS_FRAGMENT(85201, "拼图上已经存在该碎片，不必再次使用"),
	PUZZLE_NOT_HAS_FRAGMENT(85202, "拼图尚未存在该碎片，不能卸下"),
	PUZZLE_HAS_PUZZLE_AWARDS(85203, "上次集齐拼图的奖励尚未领取，请领取后才再次收集拼图"),
	PUZZLE_HAD_RECEIVED_PUZZLE_AWARDS(85204, "拼图奖励已领取过，不可再次领取"),
	PUZZLE_NOT_FULL(85205, "拼图尚未集齐，不可以领取拼图奖励"),
	
	//PET（宠物系统）
	PET_LAND_LEVEL_CAN_NOT_OPERATE_PET(86001, "岛屿等级尚未达到孵化宠物的条件"),
	PET_HAS_ALL_PET(86002, "已经拥有全部宠物，不能再孵化了"),
	PET_NO_TIME_TO_HATCH_PET(86003, "尚未到孵化时间，不可以孵化"),
	PET_IS_MAX_LEVEL(86004, "宠物已到达最大等级，无需升级"),
	PET_CAN_NOT_LEVEL_UP(86005, "宠物经验不足不可以升级"),
	PET_NOT_EXIST_PET(86006, "不存在的宠物"),
	PET_PET_HAD_FOLLOWED(86007, "当前宠物已经跟随了，不必再次跟随"),
	PET_ONLY_FOLLOW_PET_CAN_FEED(86008, "只有跟随的宠物才能喂食"),
	
	//VIP（vip系统）
	VIP_USER_IS_NOT_VIP(87101, "用户不是VIP，不可领取VIP奖励"),
	VIP_CAN_NOT_RECEIVE_AWARD(87102, "今天已经领取过VIP奖励，不可再次领取"),
	VIP_RECEIVE_MESSAGE_AWARDS_ERROR(87103, "领取消息奖励类型错误"),
	VIP_CAN_NOT_FOUND_MESSAGE_AWARDS(87104, "没有找到对应时间戳的消息奖励"),
	
	//SIGN_IN（签到领奖）
	SIGN_IN_CAN_NOT_RECEIVE_AWARD(87105, "今天已经领取过签到奖励，不可再次领取"),
	
	//MISSION（任务系统——每日任务）
	MISSION_IS_NULL(88001, "任务不存在"),
	MISSION_NOT_COMPLETED(88002, "任务未完成，不可领取任务奖励"),
	MISSION_HAD_RECEIVED_AWARD(88003, "已经领取过任务奖励"),
	;
	
	public static final int STATUS_ERROR = -1;
    public static final int STATUS_SUCCESS = 200;
    
    public static final int LOGIN_IS_EXPIRED = 888;
    public static final int LOGIN_EMPTY_OPENID = 901;
    public static final int LOGIN_TOKEN_ERROR = 902;
    public static final int LOGIN_CAN_NOT_REPEAT = 8888;
	
	public final int code;
	public final String msg;
	
	private ErrorCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	private static final IntHashMap<ErrorCode> map = new IntHashMap<ErrorCode>();
	
    public static void init() {
    	ErrorCode[] values = values();
    	for(ErrorCode errorCode:values){
    		Assert.isNull(map.put(errorCode.code, errorCode), "存在重复的错误码code id="+errorCode.code); 			
    	}
    }
    
    public static ErrorCode get(int code){
    	return map.get(code);
    }
	
}
