var webSocket;

function connect() {
    webSocket = new WebSocket("ws://localhost:8090/multi2048/WebSocket");
    
    webSocket.onmessage = function(event) {
        document.getElementById("status").innerText = event.data;
    };

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