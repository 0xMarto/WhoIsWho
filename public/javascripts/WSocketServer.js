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
    var question = $("#askQuestions").val().split(",");
    chatSocket.send(JSON.stringify(
        {
            type:type,
            text:$("#talk").val(),
            questionString:question[1],
            questionAbout:$("#questionAbout").val(),
            questionValue:question[0]
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
    if (data.type == 'mistake') $(chatLine).addClass('mistake');
    if (data.type == 'my-ask' || data.type == 'op-ask') $(chatLine).addClass('question');
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

function askQuestion() {
    sendMessage("question");
}
