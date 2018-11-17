import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;

/*
 * TODO : 2명 동시 플레이!
 * TODO : 로직 에러 수정
 * TODO : 애니메이션 추가
 * TODO : 랭킹 구현
 * TODO : 디자인 구현
 * TODO : 2명 동시 플레이 구현 -> 한 명 접속시 대기, 2명 접속시 게임 시작 -> 방을 하나 더 만듬
 */

@ServerEndpoint("/WebSocket")
public class WebSocket {
	private Session session;
	private Grid grid;
	
	@OnOpen
	public void handleOpen(Session session) {
		this.session = session;
		grid = new Grid();
		System.out.println("client is now connected");
	}
	
	@OnMessage
	public String handleMessage(String message) {		
		System.out.println("receive from client : " + message);
		
		if(message.equals("connected")) {
			return toJson();
		}

		grid.merge(message);
		if(!grid.isFull()) {
			grid.addRandomlyTile();
		}

		return toJson();
	}
	
	@OnClose
	public void handleClose() {
		System.out.println("client is now disconnected");
	}
	
	@OnError
	public void handleError(Throwable t) {
		t.printStackTrace();
	}

	public String toJson() {
		Gson gson = new Gson();
		String str = gson.toJson(grid);
		return str;
	}
}
