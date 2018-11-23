import javax.websocket.Session;

public class SessionUser {
	private Session session;
	private SessionUser enemySessionUser;
	private Grid grid;

	private String userNickName;
	
	private int bestScore;
	
	private boolean gameStart;
	private boolean gameOver;
	
	public SessionUser(Session session) {
		this.session = session;
		this.grid = new Grid();
		this.enemySessionUser = null;

		this.userNickName = null;
		
		this.bestScore = 0;
		
		this.gameStart = false;
		this.gameOver = false;
	}
	
	public void setSession(Session session) {
		this.session = session;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	public void setEnemySession(SessionUser enemySessionUser) {
		this.enemySessionUser = enemySessionUser;
	}

	public void setGameStart() {
		gameStart = true;
	}

	public void setGameOver() {
		gameOver = true;
	}

	public Session getSession() {
		return session;
	}

	public Grid getGrid() {
		return grid;
	}

	public SessionUser getEnemySession() {
		return enemySessionUser;
	}

	public boolean getGameStart() {
		return gameStart;
	}

	public boolean getGameOver() {
		return gameOver;
	}

	public void matchPlay(SessionUser enemySession) {
		setGameStart();
		setEnemySession(enemySession);
	}
}
