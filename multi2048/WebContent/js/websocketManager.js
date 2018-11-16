var webSocket;

function connect() {
    webSocket = new WebSocket("ws://localhost:8090/multi2048/WebSocket");
    
    webSocket.onmessage = function(event) {
        var cells = document.getElementsByClassName("grid-cell");
        var data = JSON.parse(event.data);
        
        document.getElementById("status").innerText = event.data;

        cells[0].innerText = data.cells[0][0];
        cells[1].innerText = data.cells[0][1];
        cells[2].innerText = data.cells[0][2];
        cells[3].innerText = data.cells[0][3];
        cells[4].innerText = data.cells[1][0];
        cells[5].innerText = data.cells[1][1];
        cells[6].innerText = data.cells[1][2];
        cells[7].innerText = data.cells[1][3];
        cells[8].innerText = data.cells[2][0];
        cells[9].innerText = data.cells[2][1];
        cells[10].innerText = data.cells[2][2];
        cells[11].innerText = data.cells[2][3];
        cells[12].innerText = data.cells[3][0];
        cells[13].innerText = data.cells[3][1];
        cells[14].innerText = data.cells[3][2];
        cells[15].innerText = data.cells[3][3];
    }

    webSocket.onopen = function(event) {
        document.getElementById("status").innerText = event;
    }
    
    webSocket.onclose = function(event) {
        document.getElementById("status").innerText = event;
    }

    webSocket.onerror = function(event) {
        document.getElementById("status").innerText = event;
    }
};

function disconnect() {
    webSocket.close();
};