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
            type:type,
            text:$("#talk").val(),
            questionAbout:qAbout,
            questionValue:qValue,
            questionString:qString,
            answer:qAnswer
        }
    ))
    ;
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
    if (data.type == 'start') $(chatLine).addClass('start');
    if (data.type == 'leave') $(chatLine).addClass('leave');
    if (data.type == 'info') $(chatLine).addClass('info');

    if (data.type == 'ask') {
        $("#questionPanel").show();
        $("#answerPanel").hide();
    }
    if (data.type == 'answer') {
        $("#answerPanel").show();
    }
    if (data.type == 'wait') {
        $("#questionPanel").hide();
        $("#answerPanel").hide();
    }
    if (data.type == 'my-ask' || data.type == 'my-answer') {
        $(chatLine).addClass('question');
        $("#questionPanel").hide();
        $("#answerPanel").hide();
    }
    if (data.type == 'op-ask' || data.type == 'op-answer') {
        $(chatLine).addClass('question');
    }
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

var qAbout;
var qValue;
var qString;
function askQuestion() {
    var question = $("#askQuestions").val().split(",");
    qAbout = $("#questionAbout").val();
    qValue = question[0];
    qString = question[1];
    sendMessage("question");
}

var qAnswer;
function answerQuestion(answer) {
    qAnswer = answer;
    sendMessage("answer");
}


