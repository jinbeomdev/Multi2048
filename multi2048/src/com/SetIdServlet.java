package com;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * Servlet implementation class SetIdServlet
 */
@WebServlet("/SetIdServlet")
public class SetIdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/multi2048?serverTimezone=UTC";
	
	private static final String DB_ID = "root";
	private static final String DB_PASSWD = "1q2w3e4r!!";
	
	private Connection conn = null;
	private Statement stmt = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetIdServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String email = (String)session.getAttribute("email");
		String passwd =  (String)session.getAttribute("passwd");
		String id = (String)request.getParameter("id");
		
		response.getWriter().append(email).append(passwd).append(id);
		
		if(email == null || passwd == null || id == null) {
			return;
		}
		
		try {
			Class.forName(JDBC_DRIVER);
			
			conn = DriverManager.getConnection(DB_URL, DB_ID, DB_PASSWD);
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			String sql;
			sql = "select * from user";
			ResultSet rs = stmt.executeQuery(sql);
			rs.moveToInsertRow();
			rs.updateString("email", email);
			rs.updateString("passwd", passwd);
			rs.updateString("id", id);
			rs.insertRow();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		if(stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			stmt = null;
		}
		
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			conn = null;
		}
		
		session.setAttribute("id", id);
		response.sendRedirect("game.jsp");
	}
}
