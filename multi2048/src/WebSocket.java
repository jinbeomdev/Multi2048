import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
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

	public WebSocket() {
		sessionUser = null;
	}
	
	@OnOpen
	public void handleOpen(Session session, EndpointConfig config) throws IOException {
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
			
			lastUser.getSession().getBasicRemote().sendText("{\"dataType\":\"gameStart\"}");
			lastUser.getSession().getBasicRemote().sendText(parseGrid(lastUser, sessionUser));
			session.getBasicRemote().sendText("{\"dataType\":\"gameStart\"}"); 
			session.getBasicRemote().sendText(parseGrid(sessionUser, lastUser)); 
		}
		
		userList.add(sessionUser);
	}
	
	@OnMessage
	public void handleMessage(String message, Session session) throws IOException {
		if(message.length() > 2 &&  message.substring(0, 2).equals("id")) {
			sessionUser.setId(message.substring(3));
			return;
		}
		
		if(!sessionUser.getGameStart()) {
			return;
		}
		
		SessionUser enemySessionUser = sessionUser.getEnemySession();

		if(!message.equals("ArrowDown") &&
		   !message.equals("ArrowUp") &&
		   !message.equals("ArrowLeft") &&
		   !message.equals("ArrowRight")) {
			//message
			sessionUser.getSession().getBasicRemote().sendText("{\"dataType\":\"message\", \"message\":\"" + sessionUser.getId() + ":" + message + "\"}");
			enemySessionUser.getSession().getBasicRemote().sendText("{\"dataType\":\"message\", \"message\":\"" + sessionUser.getId() + ":" + message + "\"}");
			return;
		}
		
		sessionUser.getGrid().merge(message);
		sessionUser.getGrid().addRandomlyTile();
		
		
		if(enemySessionUser == null) {
			return;
		}
		
		enemySessionUser.getSession().getBasicRemote().sendText(parseGrid(enemySessionUser, sessionUser));
		session.getBasicRemote().sendText(parseGrid(sessionUser, enemySessionUser));
	}
	
	@OnClose
	public void handleClose(Session session) throws IOException, SQLException, ClassNotFoundException {
		System.out.println("handleClose() is called, Session" + session.toString() + "");
		SessionUser enemySession = sessionUser.getEnemySession();
		
		DBHelper dbHelper = new DBHelper();
		dbHelper.updateBestScore(sessionUser.getId(), sessionUser.getGrid().getBestScore());
		
		if(enemySession != null) {
			enemySession.getSession().getBasicRemote().sendText("{\"dataType\":\"enemyOut\"}");
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
		String ret = "{\"dataType\":\"grid\"" +
				 	 ", \"myGrid\":";
		ret += gson.toJson(mySessionUser.getGrid());
		ret += ", \"enemyGrid\":";
		ret += gson.toJson(enemySessionUser.getGrid());
		ret += "}";
		return ret;
	}
}
