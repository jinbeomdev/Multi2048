import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/*
 * TODO : 두 명이 동시에 플레이 가능하게 하기
 * TODO : 여러 명이 두 명씩 매칭되게 하기
 * TODO : 일정 시간 동안 랜더링 되도록 하기(멀티플레이를 위함)
 * TODO : 애니메이션 효과 추가하기
 * TODO : 랭킹 추가 (DB 연동)
 * TODO : 웹 디자인 하기
 */

@ServerEndpoint("/WebSocket")
public class WebSocket {
	static final int sizeUsers = 2;
	static private SessionUser[] sessionUsers = new SessionUser[2];
	
	@OnOpen
	public void handleOpen(Session session) {
		System.out.println("client is now connected");
		
		for(int user = 0; user < sizeUsers; user++) {
			if(sessionUsers[user] == null) {
				sessionUsers[user] = new SessionUser(session, new Grid());
				session.getUserProperties().put("numUser", user);
				return;
			}
		}
		
		System.out.println("server is full");
	}
	
	@OnMessage
	public String handleMessage(String message, Session session) {
		int numUser = (int)session.getUserProperties().get("numUser");
		
		System.out.println("receive from client : " + numUser + "," + message);
		
		if(message.equals("connected")) {
			return toJson(numUser);
		}

		sessionUsers[numUser].getGrid().merge(message);
		if(!sessionUsers[numUser].getGrid().isFull()) {
			sessionUsers[numUser].getGrid().addRandomlyTile();
		}

		return toJson(numUser);
	}
	
	@OnClose
	public void handleClose(Session session) {
		System.out.println("client is now disconnected");
		int numUser = (int)session.getUserProperties().get("numUser");
		sessionUsers[numUser] = null;
	}
	
	@OnError
	public void handleError(Throwable t) {
		t.printStackTrace();
	}

	public String toJson(int numUser) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Grid[] players = new Grid[2];
		players[0] = sessionUsers[numUser].getGrid();
		if(sessionUsers[Math.abs(numUser - 1)] != null) {
			players[1] = sessionUsers[Math.abs(numUser - 1)].getGrid();
		}
		String ret = gson.toJson(players);
		//System.out.println(ret);
		return ret;
	}
}
