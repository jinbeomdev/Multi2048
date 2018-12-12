<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Multi2048</title>

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">

    <!-- Custom styles for this template -->
    <link href="style/index.css" rel="stylesheet">
  </head>

  <body class="text-center">
    <div class="cover-container d-flex h-100 p-3 mx-auto flex-column">
      <header class="masthead mb-auto">
        <div class="inner">
          <h3 class="masthead-brand">Multi2048</h3>
          <nav class="nav nav-masthead justify-content-center">
            <a class="nav-link" href="index.html">Home</a>
            <a class="nav-link active" href="#">Ranking</a>
          </nav>
        </div>
      </header>

      <main role="main" class="inner cover">
            <h2>Ranking</h2>
            <div class="table-responsive">
              <table class="table table-striped table-sm">
                <thead>
                  <tr>
                    <th>NO.</th>
                    <th>ID</th>
                    <th>SCORE</th>
                  </tr>
                </thead>
                <tbody>
                  <%
                  	Connection conn = null;
    				Statement stmt = null;
    				ResultSet rs = null;
    				
                  	Class.forName("com.mysql.cj.jdbc.Driver");
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/multi2048?serverTimezone=UTC", "root", "1q2w3e4r!!");	
    				stmt = conn.createStatement();
    				rs = stmt.executeQuery("select * from best_score order by score desc");
    				
    				while(rs.next()) {
                  %>
					<tr>
					  <td><%= rs.getRow() %></td>
					  <td><%= rs.getString("id") %></td>
					  <td><%= rs.getInt("score") %></td>
					</tr>
				  <%
    				}
				  %>
                </tbody>
              </table>
            </div>
      </main>

      <footer class="mastfoot mt-auto">
        <div class="inner">
          <p>Dept. of Computer Science and Enginnering, Dongguk Univ., Seoul, Korea, Jin-Beom Kim</p>
        </div>
      </footer>
    </div>


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
  </body>
</html>