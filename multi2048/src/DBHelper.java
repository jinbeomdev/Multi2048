import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBHelper {
	private static final long serialVersionUID = 1L;
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/multi2048?serverTimezone=UTC";
	
	private static final String DB_ID = "root";
	private static final String DB_PASSWD = "1q2w3e4r!!";
	
	private Connection conn = null;
	private Statement stmt = null;
	
	DBHelper() throws ClassNotFoundException {
		Class.forName(JDBC_DRIVER);
		
		try {
			conn = DriverManager.getConnection(DB_URL, DB_ID, DB_PASSWD);
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateBestScore(String id, int bestScore) {
		System.out.println(id+bestScore);
		int rsBestScore;
		try {
			ResultSet rs = stmt.executeQuery("select i, id, score from best_score where id='" + id + "';");
			if(!rs.next()) {
				rs.moveToInsertRow();
				rs.updateString("id", id);
				rs.updateInt("score", bestScore);
				rs.insertRow();
				return;
			}

			rsBestScore = rs.getInt("score");
			if(rsBestScore < bestScore) {
				System.out.println("update best_score set score="+bestScore+" where id='"+id+"';");
				stmt.executeUpdate("update best_score set score="+bestScore+" where id='"+id+"';");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

	}
}
