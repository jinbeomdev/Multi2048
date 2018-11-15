import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;

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
		if(grid.isFull()) {
			grid.addRandomlyTile();
		}
		
		System.out.println("receive from client : " + message);
		
		Gson gson = new Gson();
		String str = gson.toJson(grid);
		return str;
		
	}
	
	@OnClose
	public void handleClose() {
		System.out.println("client is now disconnected");
	}
	
	@OnError
	public void handleError(Throwable t) {
		t.printStackTrace();
	}
}
