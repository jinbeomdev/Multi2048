import javax.servlet.http.HttpSession;
import javax.websocket.Session;

public class SessionUser {
	private Session session;
	private String id;

	private SessionUser enemySessionUser;
	private Grid grid;
	
	private int bestScore;
	
	private boolean gameStart;
	private boolean gameOver;
	
	public SessionUser(Session session) {
		this.session = session;
		id = null;
		
		this.enemySessionUser = null;
		this.grid = new Grid();
		
		this.bestScore = 0;
		
		this.gameStart = false;
		this.gameOver = false;
	}
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	public void setEnemySession(SessionUser enemySessionUser) {
		this.enemySessionUser = enemySessionUser;
	}
	
	public void setBestScore(int bestScore) {
		this.bestScore = bestScore;
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
	
	public String getId() {
		return id;
	}

	public Grid getGrid() {
		return grid;
	}

	public SessionUser getEnemySession() {
		return enemySessionUser;
	}
	
	public int getBestScore() {
		return bestScore;
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
