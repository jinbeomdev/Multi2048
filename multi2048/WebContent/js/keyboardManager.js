window.addEventListener("keydown", function (event) {
    if (event.defaultPrevented) {
        return; // Do nothing if the event was already processed
    }
  
    switch (event.key) {
        case "Down": // IE specific value
        case "ArrowDown":
            webSocket.send("ArrowDown");
            break;
        case "Up": // IE specific value
        case "ArrowUp":
            webSocket.send("ArrowUp");
            break;
        case "Left": // IE specific value
        case "ArrowLeft":
            webSocket.send("ArrowLeft");
            break;
        case "Right": // IE specific value
        case "ArrowRight":
            webSocket.send("ArrowRight");
            break;
        default:
            return; // Quit when this doesn't handle the key event.
    }
  
    // Cancel the default action to avoid it being handled twice
    event.preventDefault();
  }, true);