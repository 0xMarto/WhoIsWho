/**
 * User: Mart0
 * Date: 4/30/12
 */
var WSPath = $("#WSocketPath").val();

if (WSPath) {
    console.log("Websocket started in path: " + WSPath);
    var WS = window['MozWebSocket'] ? MozWebSocket : WebSocket;
    var chatSocket = new WS(WSPath);
    chatSocket.onmessage = receiveEvent;
    $("#talk").keypress(handleReturnKey);
} else {
    console.log("System Error, cannot start websocket in path: " + WSPath);
}

function sendMessage(type) {
    chatSocket.send(JSON.stringify(
        {
            type: type,
            text: $("#talk").val(),
            questionAbout: qAbout,
            questionValue: qValue,
            questionString: qString,
            answer: qAnswer,
            guessCard: guessCardName
        }
    ));
    if (type == 'chat'){
        $("#talk").val('');
    }
}

function receiveEvent(event) {
    var data = JSON.parse(event.data);
    console.log("Receive event: " + data.type);

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

//    var chatLine = $('<div class="message"><span></span><user></user><p></p></div>');
    var chatLine = $('<div class="message"><user></user><p></p></div>');

    if (data.type == 'chat') {
        $(chatLine).addClass('chat');
        $("user", chatLine).text(data.name + ":");
    }
    if (data.type == 'mistake') $(chatLine).addClass('mistake');
    if (data.type == 'start') $(chatLine).addClass('start');
    if (data.type == 'leave') $(chatLine).addClass('leave');
    if (data.type == 'info') $(chatLine).addClass('info');

    if (data.type == 'yourCard') {
        $(chatLine).addClass('yourCard');
        var cardName = data.message.split(":")[1];
        showPlayerCard(cardName);
    }

    if (data.type == 'ask') {
        $("#questionPanel").show();
        $("#answerPanel").hide();
    }
    if (data.type == 'answer') {
        $("#answerPanel").show();
    }
    if (data.type == 'wait') {
        //$("#questionPanel").hide();
        //$("#answerPanel").hide();
    }
    if (data.type == 'my-ask' || data.type == 'my-answer') {
        $(chatLine).addClass('question');
        $("#questionPanel").hide();
        $("#answerPanel").hide();
    }
    if (data.type == 'op-ask' || data.type == 'op-answer') {
        $(chatLine).addClass('question');
    }
    if (data.type == "lie") {
        $("#lies").html(data.message);
        $(chatLine).addClass('lie');
        data.message = "Lier !!!!";
    }
    if (data.type == "end") {
        $("#questionPanel").hide();
        $("#answerPanel").hide();
        $(chatLine).addClass('end');
        updateRanking(data);
    }
    if (data.type == 'op-guess' || data.type == 'my-guess') {
        $(chatLine).addClass('end');
    }


    //$("span", chatLine).text(data.type);
    $("p", chatLine).text(data.message);
    $('#messages').append(chatLine);
    $("#messages").scrollTop($("#messages")[0].scrollHeight);
}

function updateRanking(data) {
    if (data.message.search("Lose") != -1 || data.message.search("LOSE") != -1 || data.message.search("lose") != -1){
        updatePlayerByName(globalPlayer, "lose");
    } else if (data.message.search("Win") != -1 || data.message.search("WIN") != -1 || data.message.search("win") != -1){
        updatePlayerByName(globalPlayer, "win");
    }
}

function showPlayerCard(cardName) {
    var cardList = document.getElementsByClassName("card");
    for (var i = 0; i < cardList.length; i++) {
        var name = (cardList[i].getElementsByTagName("h2")[0].innerHTML).toUpperCase();
        if (name == cardName) {
            console.log(name);
            cardList[i].className += " " + "playerCard";
            var cardInfoElement = cardList[i].getElementsByClassName("cardInfo")[0];
            var id = cardInfoElement.getAttribute("id");
            $("#" + id).attr("style", "box-shadow: 7px 7px 5px 2px rgba(15, 56, 218, 0.82)");
        }
    }
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

var guessCardName;
function guessCard() {
    smoke.prompt('What\'s the card?', function (e) {
        if (e) {
            guessCardName = e;
            sendMessage("guess");
        }
    });
}