import javax.websocket.Session;

public class SessionUser {
	private Session session;
	private Grid grid;
	
	public SessionUser() {
		session = null;
		grid = null;
	}
	
	public SessionUser(Session session, Grid grid) {
		this.session = session;
		this.grid = grid;
	}
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	public Session getSession() {
		return session;
	}
	
	public void setGrid(Grid grid) {
		this.grid = grid;
	}
	
	public Grid getGrid() {
		return grid;
	}
}
