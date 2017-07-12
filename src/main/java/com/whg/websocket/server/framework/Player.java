package com.whg.websocket.server.framework;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.util.Assert;

import com.whg.util.time.TimeUtil;
import com.whg.websocket.bo.user.UserInfo;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.util.AttributeKey;

public class Player {

	public static final AttributeKey<Player> key = AttributeKey.valueOf("key");
	
	public enum NetState{
		connect, 
		idle, 
		play, 
		disconnect,
		;
	}
	
	private final Channel channel;
	
	private AtomicReference<NetState> userNet = new AtomicReference<Player.NetState>();
	private AtomicLong lastUpdateHeart = new AtomicLong();
	private AtomicBoolean repeatLogin = new AtomicBoolean(false);
	
	private UserInfo userInfo;
	
	public Player(ChannelHandlerContext ctx) {
		this.channel = ctx.channel();
		this.channel.attr(key).set(this);
		this.userNet.set(NetState.connect);
		this.lastUpdateHeart.set(TimeUtil.currentTimeMillis());
	}
	
	public void write(Object obj){
		if(channel == null || !isConnect()){
			return;
		}
		channel.writeAndFlush(obj);
	}
	
	public boolean isConnect(){
		return userNet.get() == NetState.connect;
	}
	
	public void closeRepeatLogin(){
		close(1001, "【repeat login/重复登录】");
	}
	
	public void close(int statusCode, String reasonText){
		channel.writeAndFlush(new CloseWebSocketFrame(statusCode, reasonText));
	}
	
	public int getPlayerId() {
		return channel.hashCode();
	}
	
	public void repeatLogin(){
		Assert.isTrue(repeatLogin.compareAndSet(false, true));
	}
	
	/** 是否已经重复登录 */
	public boolean isRepeatLogin() {
		return repeatLogin.get();
	}
	
	public void destory() {
		channel.attr(key).set(null);
		Assert.isTrue(userNet.compareAndSet(NetState.connect, NetState.disconnect));
	}
	
	public long getUserId(){
		return userInfo.getUserId();
	}
	
}
