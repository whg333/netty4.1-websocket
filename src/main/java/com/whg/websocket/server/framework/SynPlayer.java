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

public class SynPlayer {

	public static final AttributeKey<SynPlayer> Player = AttributeKey.valueOf("synPlayer");
	
	public enum NetState{
		connect, 
		idle, 
		play, 
		disconnect,
		;
	}
	
	private final ChannelHandlerContext ctx;
	
	private AtomicReference<NetState> userNet = new AtomicReference<SynPlayer.NetState>();
	private AtomicLong lastUpdateHeart = new AtomicLong();
	private AtomicBoolean repeatLogin = new AtomicBoolean(false);
	
	private UserInfo userInfo;
	
	public SynPlayer(ChannelHandlerContext ctx) {
		this.ctx = ctx;
		this.ctx.channel().attr(Player).set(this);
		this.userNet.set(NetState.connect);
		this.lastUpdateHeart.set(TimeUtil.currentTimeMillis());
	}
	
	public void write(Object obj){
		if(ctx == null || !isConnect()){
			return;
		}
		ctx.writeAndFlush(obj);
	}
	
	public boolean isConnect(){
		return userNet.get() == NetState.connect;
	}
	
	public void closeRepeatLogin(){
		close(1001, "【repeat login/重复登录】");
	}
	
	public void close(int statusCode, String reasonText){
		ctx.writeAndFlush(new CloseWebSocketFrame(statusCode, reasonText));
	}
	
	public int getPlayerId() {
		return ctx.channel().hashCode();
	}
	
	public void repeatLogin(){
		Assert.isTrue(repeatLogin.compareAndSet(false, true));
	}
	
	/** 是否已经重复登录 */
	public boolean isRepeatLogin() {
		return repeatLogin.get();
	}
	
	public void destory() {
		ctx.channel().attr(Player).set(null);
		Assert.isTrue(userNet.compareAndSet(NetState.connect, NetState.disconnect));
	}
	
	public long getUserId(){
		return userInfo.getUserId();
	}
	
}
