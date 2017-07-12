/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.whg.websocket.server.handler;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.whg.websocket.server.framework.Dispatcher;
import com.whg.websocket.server.framework.GlobalServer;
import com.whg.websocket.server.framework.Player;
import com.whg.websocket.server.framework.request.JsonRequest;
import com.whg.websocket.server.framework.request.Request;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

@Sharable
public class WebSocketFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketFrameHandler.class);
    
    private final Dispatcher dispatcher;
    private final GlobalServer globalServer;
    
    public WebSocketFrameHandler(Dispatcher dispatcher, GlobalServer globalServer) {
		this.dispatcher = dispatcher;
		this.globalServer = globalServer;
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		globalServer.addConnect(new Player(ctx));
	}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        // ping and pong frames already handled

    	if(!(frame instanceof TextWebSocketFrame)){
    		 String message = "unsupported frame type: " + frame.getClass().getName();
             throw new UnsupportedOperationException(message);
    	}
    	
    	// Send the uppercase string back.
        String request = ((TextWebSocketFrame) frame).text();
        logger.info("{} received {}", ctx.channel(), request);
        ctx.channel().writeAndFlush(new TextWebSocketFrame(request));
        
        handle(ctx, request);
    }
    
    private void handle(ChannelHandlerContext ctx, String request){
        Player player =  ctx.channel().attr(Player.key).get();
        if(player == null){
        	//throw new BusinessException("not exist player!");
        	throw new RuntimeException("not exist player!");
        }
        
        //Request wsRequest = JSONUtil.fromJSON(request, JsonRequest.class);
        Request wsRequest = JSON.parseObject(request, JsonRequest.class);
        dispatcher.dispatch(player, wsRequest);
    }
}
