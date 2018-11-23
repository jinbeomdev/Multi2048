import java.io.IOException;
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

@ServerEndpoint("/WebSocket")
public class WebSocket {
	static private Gson gson = new GsonBuilder().setPrettyPrinting().create();
	static private List<SessionUser> userList = Collections.synchronizedList(new ArrayList<>());

	private SessionUser sessionUser;

	public void WebSocket() {
		sessionUser = null;
	}
	
	@OnOpen
	public void handleOpen(Session session) throws IOException {
		System.out.println("handleOpen() is called, Session = " + session.toString());
		if(sessionUser == null) {
			sessionUser = new SessionUser(session);
		} else {
			System.out.println("when calling handleOpen(), SessionUser is not null");
		}

		if(userList.isEmpty()) {
			userList.add(sessionUser);
			return;
		}

		SessionUser lastUser = userList.get(userList.size() - 1);

		if(lastUser.getGameStart() == false) {
			System.out.println(lastUser.getSession().toString() + "," + sessionUser.getSession().toString() + " are matched");
			lastUser.matchPlay(sessionUser);
			sessionUser.matchPlay(lastUser);
			
			lastUser.getSession().getBasicRemote().sendText(parseGrid(lastUser, sessionUser));
			session.getBasicRemote().sendText(parseGrid(sessionUser, lastUser)); 
		}
		
		userList.add(sessionUser);
	}
	
	@OnMessage
	public void handleMessage(String message, Session session) throws IOException {
		sessionUser.getGrid().merge(message);
		sessionUser.getGrid().addRandomlyTile();
		
		SessionUser enemySessionUser = sessionUser.getEnemySession();
		
		if(enemySessionUser == null) {
			return;
		}
		
		enemySessionUser.getSession().getBasicRemote().sendText(parseGrid(enemySessionUser, sessionUser));
		session.getBasicRemote().sendText(parseGrid(sessionUser, enemySessionUser));
	}
	
	@OnClose
	public void handleClose(Session session) {
		System.out.println("handleClose() is called, Session" + session.toString() + "");
		SessionUser enemySession = sessionUser.getEnemySession();
		
		if(enemySession != null) {
			enemySession.setEnemySession(null);	
		}
		
		sessionUser.setEnemySession(null);
		userList.remove(sessionUser);
		sessionUser = null;
	}
	
	@OnError
	public void handleError(Throwable t) {
		t.printStackTrace();
	}
	
	public String parseGrid(SessionUser mySessionUser, SessionUser enemySessionUser) {
		System.out.println("parseGrid() is called");
		String ret = "{\"myGrid\":";
		ret += gson.toJson(mySessionUser.getGrid());
		ret += ", \"enemyGrid\":";
		ret += gson.toJson(enemySessionUser.getGrid());
		ret += "}";
		return ret;
	}
}
