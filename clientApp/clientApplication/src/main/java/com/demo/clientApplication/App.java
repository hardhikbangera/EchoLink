package com.demo.clientApplication;


import java.io.IOException;
import java.net.URI;
import java.util.Random;

import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.DeploymentException;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.WebSocketContainer;

@ClientEndpoint
public class App {	

	private static Session session;
	@OnOpen
	public void onOpen(Session session) {
		App.session=session;
		IO.println("Client connected "+session.getId());		
		
	}
	@OnMessage
	public void textMessage(String message) {
		IO.println(message);
	}
	
	@OnClose
	public void onClose(Session session) {
		IO.println("Client disconnected");
		
	
    }
	public static void main( String[] args ) {
		try {
			//JOIN:username:roomId  format
			Random random=new Random();
			String roomId;
			IO.println("Enter you username");
			String username=IO.readln();
			
			String answer=IO.readln("Do you want me to create roomId(yes/no): ");
			if(answer.equals("yes")) {
				IO.print("Enter your roomId : ");
				roomId=IO.readln();
			}else {
				int roomIdInteger=random.nextInt(1000,9999);
				roomId=String.valueOf(roomIdInteger);
				IO.println("Your roomId is:"+roomId);
			}
			
			
			WebSocketContainer container=ContainerProvider.getWebSocketContainer();
			container.connectToServer(App.class, URI.create("ws://localhost:8080/chat"));
			session.getBasicRemote().sendText("JOIN:"+username+":"+roomId);
			
			//MSG:message  format
			IO.println("Send any message:");
			while(true) {
				String msg=IO.readln();
				if("exit".equals(msg)) {break;}
				session.getBasicRemote().sendText("MSG:"+msg);
			}
			session.close();
		} catch (DeploymentException |IOException e) {
			e.printStackTrace();
		} 
}
}


