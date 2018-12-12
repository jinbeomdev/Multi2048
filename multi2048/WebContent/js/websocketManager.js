var webSocket;

webSocket = new WebSocket("ws://localhost:8090/multi2048/WebSocket");

webSocket.onopen = function(event) {
    console.log("onopen()");
    document.getElementById("status").innerText = event;
    webSocket.send("id:" + id);
}

webSocket.onclose = function(event) {
    console.log("onclose()");
    document.getElementById("status").innerText = event;
}

webSocket.onmessage = function(event) {
    console.log("onmessage()");

    var data = JSON.parse(event.data);
    var dataType = data.dataType;
    
    document.getElementById("status").innerText = event;

    if(dataType == "grid") {
        updateGrid(data);
    } else if (dataType == "gameStart") {
        $("#preloader").hide();
    } else if(dataType == "enemyOut") {
        $("#enemyOut").show();
        $('#enemyOut').slideDown();
    } else if(dataType == "message") {
        document.getElementById("messageTextArea").value += data.message + "\n";
    }
}

webSocket.onerror = function(event) {
    document.getElementById("status").innerText = event;
}

function updateGrid(data) {
    //document.getElementById("status").innerText = data;
    var myCells = document.getElementsByClassName("cell");
    myCells[0].innerText = data.myGrid.cells[0][0];
    myCells[1].innerText = data.myGrid.cells[0][1];
    myCells[2].innerText = data.myGrid.cells[0][2];
    myCells[3].innerText = data.myGrid.cells[0][3];
    myCells[4].innerText = data.myGrid.cells[1][0];
    myCells[5].innerText = data.myGrid.cells[1][1];
    myCells[6].innerText = data.myGrid.cells[1][2];
    myCells[7].innerText = data.myGrid.cells[1][3];
    myCells[8].innerText = data.myGrid.cells[2][0];
    myCells[9].innerText = data.myGrid.cells[2][1];
    myCells[10].innerText = data.myGrid.cells[2][2];
    myCells[11].innerText = data.myGrid.cells[2][3];
    myCells[12].innerText = data.myGrid.cells[3][0];
    myCells[13].innerText = data.myGrid.cells[3][1];
    myCells[14].innerText = data.myGrid.cells[3][2];
    myCells[15].innerText = data.myGrid.cells[3][3];
    myCells[16].innerText = data.enemyGrid.cells[0][0];
    myCells[17].innerText = data.enemyGrid.cells[0][1];
    myCells[18].innerText = data.enemyGrid.cells[0][2];
    myCells[19].innerText = data.enemyGrid.cells[0][3];
    myCells[20].innerText = data.enemyGrid.cells[1][0];
    myCells[21].innerText = data.enemyGrid.cells[1][1];
    myCells[22].innerText = data.enemyGrid.cells[1][2];
    myCells[23].innerText = data.enemyGrid.cells[1][3];
    myCells[24].innerText = data.enemyGrid.cells[2][0];
    myCells[25].innerText = data.enemyGrid.cells[2][1];
    myCells[26].innerText = data.enemyGrid.cells[2][2];
    myCells[27].innerText = data.enemyGrid.cells[2][3];
    myCells[28].innerText = data.enemyGrid.cells[3][0];
    myCells[29].innerText = data.enemyGrid.cells[3][1];
    myCells[30].innerText = data.enemyGrid.cells[3][2];
    myCells[31].innerText = data.enemyGrid.cells[3][3];

    document.getElementById("best-score").innerText = data.myGrid.bestScore;
    document.getElementById("enemy-best-score").innerText = data.enemyGrid.bestScore;
}