package com;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*
 * TODO : compare id and pwd with db.
 * TODO : there is no id, making it
 * TODO : there is no nickname, making it
 */

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
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
    public LoginServlet() {
        super();
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
		RequestDispatcher rd = null;
		String email = (String)request.getParameter("email");
		String passwd =  (String)request.getParameter("password");
		HttpSession session = request.getSession();
		
		if(email == null || passwd == null) {
			return;
		}
		
		try {			
			Class.forName(JDBC_DRIVER);
			
			conn = DriverManager.getConnection(DB_URL, DB_ID, DB_PASSWD);
			stmt = conn.createStatement();
			String sql;
			sql = "select passwd, id from user where email='"+ email + "';";
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.first()) {
				if(passwd.equals(rs.getString("passwd"))) {
					session.setAttribute("email", email);
					session.setAttribute("passwd", passwd);
					session.setAttribute("id", rs.getString("id"));
					rd = request.getRequestDispatcher("game.jsp");
				} else {
					rd = request.getRequestDispatcher("index.html");
				}
			} else {
				session.setAttribute("email", email);
				session.setAttribute("passwd", passwd);
				rd = request.getRequestDispatcher("setId.html");
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
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
		
		rd.forward(request, response);
	}
}
