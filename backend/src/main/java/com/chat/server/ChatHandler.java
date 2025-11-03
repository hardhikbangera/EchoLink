package com.chat.server;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class ChatHandler  extends TextWebSocketHandler{
	private final Map<String,Set<WebSocketSession>> rooms=new ConcurrentHashMap<>();
	private final Map<WebSocketSession,UserInfo> users=new ConcurrentHashMap<>();
	
	private static class UserInfo{
		String username;
		String roomId;
		UserInfo(String username,String roomId){
			this.username=username;
			this.roomId=roomId;
		}
	}
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
			IO.println("Connection Established");
	}
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
	String currentMessage=message.getPayload();
	
	//JOIN:username:roomId
	if(currentMessage.startsWith("JOIN")) {
		String[] result=currentMessage.split(":");
		
		String username=result[1];
		String roomId=result[2];
		
		
		users.put(session, new UserInfo(username,roomId));
		rooms.putIfAbsent(roomId, ConcurrentHashMap.newKeySet());
		rooms.get(roomId).add(session);
		broadcast(username+" joined the chat ",roomId,session);
		}
	//MSG:message
	else if(currentMessage.startsWith("MSG")) {
		String userMessage=currentMessage.substring(4);
		UserInfo userInfo=users.get(session);
		if(userInfo!=null) {
			broadcast(userInfo.username+":"+userMessage,userInfo.roomId,session);
			
		}
	}
	}
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		UserInfo userInfo=users.remove(session);
		if(userInfo!=null) {
			rooms.get(userInfo.roomId).remove(userInfo);
			broadcast(userInfo.username+" left the room ", userInfo.roomId, session);
		}
	}
	private void broadcast(String message,String roomId,WebSocketSession session) {
		Set<WebSocketSession> resultSet=rooms.get(roomId);
		for(WebSocketSession eachSet:resultSet) {
			if(eachSet.isOpen() && !eachSet.getId().equals(session.getId())) {
				try {
					eachSet.sendMessage(new TextMessage(message));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
