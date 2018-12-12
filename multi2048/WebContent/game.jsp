<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
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
    <link href="style/game.css" rel="stylesheet">
  </head>

  <body class="text-center">
      <div class="cover-container d-flex h-100 p-3 mx-auto flex-column">
          <header class="masthead mb-auto">
            <div id="status"></div>
            <div id="enemyOut" class="alert alert-warning alert-dismissible fade show" role="alert" style="display:none;">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                  <span aria-hidden="true">&times;</span>
                </button>
                <strong>Enemy!</strong> went out!
            </div>
          </header>
    
          <main role="main" class="inner cover">
              <div class="my-container">
                <div id="preloader">
                  <div id="loader"></div>
                  Waiting for player
                </div>
                <div>My Score : </div><div id="best-score"></div>
                <div class="row">
                  <div class="cell"></div>
                  <div class="cell"></div>
                  <div class="cell"></div>
                  <div class="cell"></div>
                </div>
                <div class="row">
                 <div class="cell"></div>
                 <div class="cell"></div>
                 <div class="cell"></div>
                 <div class="cell"></div>
                </div>
                <div class="row">
                  <div class="cell"></div>
                  <div class="cell"></div>
                  <div class="cell"></div>
                  <div class="cell"></div>
                </div>
                <div class="row">
                 <div class="cell"></div>
                 <div class="cell"></div>
                 <div class="cell"></div>
                 <div class="cell"></div>
               </div>
              </div>
          </main>
    
          <footer class="mastfoot mt-auto">
              <div class="enemy-container">
                  <div class="row">
                    <div class="cell"></div>
                    <div class="cell"></div>
                    <div class="cell"></div>
                    <div class="cell"></div>
                  </div>
                  <div class="row">
                   <div class="cell"></div>
                   <div class="cell"></div>
                   <div class="cell"></div>
                   <div class="cell"></div>
                  </div>
                  <div class="row">
                    <div class="cell"></div>
                    <div class="cell"></div>
                    <div class="cell"></div>
                    <div class="cell"></div>
                  </div>
                  <div class="row">
                   <div class="cell"></div>
                   <div class="cell"></div>
                   <div class="cell"></div>
                   <div class="cell"></div>
                 </div>
              </div>

              <div>
                <textarea id="messageTextArea" readonly="readonly" rows="5" cols="45"></textarea>
              </div>
              <input type="text" id="messageText" size="35">
              <input type="button" value="Send" onclick="sendMessage()">
          </footer>
      </div>
        
     <% String id = (String)session.getAttribute("id"); %>
     <script>
       var id = "<%= id %>";

       function sendMessage() {
         var messageText = document.getElementById("messageText");
         webSocket.send(messageText.value);
         messageText.value = "";
       }
     </script>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
    <script src="js/websocketManager.js"></script>
    <script src="js/keyboardManager.js"></script>
  </body>
</html>