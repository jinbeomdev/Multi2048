var webSocket;

function connect() {
    webSocket = new WebSocket("ws://localhost:8081/multi2048/WebSocket");
    
    webSocket.onmessage = function(event) {
        document.getElementById("status").innerText = event.data;
    };
    
    document.getElementById("status").innerText = "webSocket is connected";
};

function disconnect() {
    webSocket.close();
    document.getElementById("status").innerText = "webSocket is disconnected";
};