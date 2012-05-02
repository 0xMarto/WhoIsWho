/**
 * User: Mart0
 * Date: 4/30/12
 */
var WS = window['MozWebSocket'] ? MozWebSocket : WebSocket;
    var WSPath = $("#WSocketPath").val();
    var chatSocket = new WS(WSPath);
    chatSocket.onmessage = receiveEvent;
    $("#talk").keypress(handleReturnKey);

    function sendMessage(type) {
        chatSocket.send(JSON.stringify(
                {
                    type: type,
                    text: $("#talk").val(),
                    x:  $("#xArea").val(),
                    y: $("#yArea").val()
                }
        ));
        $("#talk").val('');
    }

    function receiveEvent(event) {
        var data = JSON.parse(event.data);

        // Handle errors
        if (data.error) {
            chatSocket.close();
            $("#onError span").text(data.error);
            $("#onError").show();
            return
        } else {
            $("#onChat").show();
        }

        // Create the message element
        var chatLine = $('<div class="message"><span></span><user></user><p></p></div>');
        if (data.type == 'chat') {
            $(chatLine).addClass('chat');
            $("user", chatLine).text(data.name + ":");
        }
        if (data.type == 'start') $(chatLine).addClass('start');
        if (data.type == 'leave') $(chatLine).addClass('leave');
        if (data.type == 'info') $(chatLine).addClass('info');

        $("span", chatLine).text(data.type);
        $("p", chatLine).text(data.message);
        $('#messages').append(chatLine)
    }

    function handleReturnKey(e) {
        if (e.charCode == 13 || e.keyCode == 13) {
            e.preventDefault();
            sendMessage("chat");
        }
    }

    function getServerInfo() {
        sendMessage("serverInfo");
    }

    var letters = 'ABCDEFGHIJ'.split('');
    addGrid(document.getElementById("myBoard"), 'water');
    addGrid(document.getElementById("opBoard"), 'water');

    function addGrid(div, cls) {
        for (var j = 0; j < letters.length + 1; j++) {
            for (var i = 0; i < letters.length + 1; i++) {
                // create a new button
                var b = document.createElement("BUTTON");
                b.type = 'button';
                b.setAttribute("class", 'anno');

                if (j == 0 && i == 0) {
                    // add blank button at origin
                    b.innerHTML = '&nbsp;';
                }
                else if (j == 0 && i >= 1) {
                    // add horizontal A-J
                    b.innerHTML = letters[i - 1];
                }
                else if (i == 0) {
                    // add vertical 1-10
                    b.innerHTML = j;
                }
                else {
                    // create grid and fill grid-hash
                    b.innerHTML = '&nbsp;';
                    b.value = i + '-' + j;
                    b.setAttribute("class", cls);
//                    game[div.id]['grid'][i + '-' + j] = b;
                }

//                 add button
                div.appendChild(b);
            }
            div.appendChild(document.createElement("BR"));
        }
    }

    function fire() {
        sendMessage("shoot");
        $("#xArea").val('');
        $("#yArea").val('');
    }

