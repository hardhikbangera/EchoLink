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
	
	private final Set<WebSocketSession> set=ConcurrentHashMap.newKeySet();
	
	private final Map<WebSocketSession,String> map=new ConcurrentHashMap<>();
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		set.add(session);
		IO.println("Connection is successfull for userID: "+session.getId());
	}
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
			try {
				String newMessage=message.getPayload();
		if(!map.containsKey(session)) {
			map.put(session,newMessage);
				broadcast(newMessage+" joined the chat");
		}else {
			String username=map.get(session);
			broadcast(username+" : "+newMessage);
		}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		String user=map.remove(session);
		set.remove(session);
		if(user!=null) {
			IO.println("user left the chat");
		}
	}
	private void broadcast(String string) throws IOException {
		for(WebSocketSession eachSession:set) {
			if(eachSession.isOpen()) {
				eachSession.sendMessage(new TextMessage(string));
			}
		}
	}
	

}
