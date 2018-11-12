package multi2048;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/WebSocket")
public class WebSocket {
	@OnOpen
	public void handleOpen() {
		System.out.println("client is now connected");
	}
	
	@OnMessage
	public String handleMessage(String message) {
		System.out.println("receive from client : " + message);
		return message;
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
