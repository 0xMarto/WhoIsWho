/* Author: Team w&w */
function validateUsername() {
    var username = document.getElementById("username").value();
    if (username.contains("%")) {
        document.getElementById("username").value("");
    }
}

$(document).ready(function () {
    $('#boardGame div.cardBack').hide().css('left', 0);

    function mySideChange(front) {
        if (front) {
            $(this).parent().find('div.cardFront').show();
            $(this).parent().find('div.cardBack').hide();

        } else {
            $(this).parent().find('div.cardFront').hide();
            $(this).parent().find('div.cardBack').show();
        }
    }
    $('.cardInfo').click(function () {
        $(this).rotate3Di('toggle', 750, {direction: 'clockwise', sideChange: mySideChange});

    });

    $("#askQuestions").chained("#questionAbout");

//    Load ranking table when document is loaded
    listJson();
});


function loadRanking() {
    var room = $("#rankingRoom")

    room.slideToggle('slow');
}

var req;

function loadXML (method, url, params, callback) {
    var baseUrl = "http://dpoi2012api.appspot.com/api/1.0";
    if (window.XMLHttpRequest) {
        req = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        /* For IE/Windows ActiveX */
        req = new ActiveXObject("Microsoft.XMLHTTP");
    }
    req.onreadystatechange = function() {
        /*readyState 4 = Complete */
        if (req.readyState == 4) {
            /*Status = 200, everything OK*/
            if (req.status == 200) {
                var jsonData = JSON.parse (req.responseText);
                /*Check code to make sure the status is OK*/
                if(jsonData.status.code == 1) {
                    callback(jsonData);
                } else if (jsonData.status.code == 6) {
			callback(jsonData);
		} else if (jsonData.status.code == 5) {
			callback(jsonData);
		} else {
                    var loading = document.getElementById('loading');
                    suicide (loading);
                    showWarning(req);
                }
            } else {
                var loading = document.getElementById('loading');
                suicide (loading);
                showWarning(req);
            }
        }
    }
;
    req.open(method , baseUrl+url+params , true);
    req.send();
}

function listJson (){
    var method = "GET";
    var url = "/list"
    var params = "?credential=ranking";
    loadXML (method, url, params, parseJSON);
}

function updateJson (id) {
    var first = document.getElementById("editFirst").value;
    var last = document.getElementById("editLast").value;
    var mail = document.getElementById("editMail").value;
    var phone = document.getElementById("editPhone").value;

    var method = "POST";
    var url = "/update?credential=nconstanzo";
    var params = "&id="+id+"&first="+first+"&last="+last+"&mail="+mail+"&phone="+phone ;
    loadXML(method,url,params, updateOneJson);
    hidePopUp();
}

function updateOneJson (json) {
    var mail = json.payload.mail;
    var phone = json.payload.phone;
    var last = json.payload.last;
    var first = json.payload.first;
    var id = json.payload.id;
    var row = document.getElementById(id);
    suicide(row);
    createTableRow(id,first,last,mail,phone);
}

function viewJson (id){
    var method = "GET";
    var url = "/view";
    var params = "?credential=nconstanzo&id="+id;
    loadXML(method, url, params, popUp);
}

function viewEditJson (id) {
    var method = "GET";
    var url = "/view";
    var params = "?credential=nconstanzo&id="+id;
    loadXML(method, url, params, editPopUp);
}

function createJson () {
    var first = document.getElementById("editFirst").value;
    var last = document.getElementById("editLast").value;
    var mail = document.getElementById("editMail").value;
    var phone = document.getElementById("editPhone").value;

    var method = "POST";
    var url = "/create?credential=nconstanzo";
    var params = "&first="+first+"&last="+last+"&mail="+mail+"&phone="+phone;
    loadXML(method, url, params, parseOneJson);
    hidePopUp();
}

function parseOneJson (json) {

	var id = json.payload.id;
	var mail = json.payload.mail;
	var phone = json.payload.phone;
        var last = json.payload.last;
        var first = json.payload.first;
	createTableRow (id, first, last, mail, phone);
}

function deleteJson (id){
    var method = "POST";
    var url = "/delete";
    var params = "?credential=nconstanzo&id="+id;
    loadXML(method, url, params, deleteRow);
    hidePopUp();
}

function deleteRow (json) {

	var id = json.payload.id;
	var row = document.getElementById(id);
    	suicide(row);

}

function parseJSON(json) {
        for (i = 0; i < json.payload.count; i++) {
            var id = json.payload.items[i].id;
            var credential = json.payload.items[i].credential;
            var first = json.payload.items[i].first;
            var last = json.payload.items[i].last;
            var mail = json.payload.items[i].mail;
            var phone = json.payload.items[i].phone;
            var created = json.payload.items[i].created;
            createTableRow(id, first, last, mail, phone);
        }
        document.getElementById('table1').deleteRow(json.payload.count+1);
        var loading = document.getElementById('loading');
        suicide (loading);
}

function createTableRow (id, first, last, mail, phone) {
    var table = document.getElementById('rankingTable').insertRow(1);
    table.setAttribute("class", "row");

    var cero = table.insertCell(0);
    var one = table.insertCell(1);
    var two = table.insertCell(2);
    var three = table.insertCell(3);


    table.setAttribute("id", id);

    cero.innerHTML = first;
    one.innerHTML = last;
    two.innerHTML = mail;
    three.innerHTML = phone;

}

function showWarning (req) {
    var index = document.getElementById("index");
    var element = document.createElement("span");

    element.setAttribute ("id", "error");
    element.setAttribute ("class", "message");
    element.innerHTML = "Oops... An error ocurred: \n"+req.statusText;

    setTimeout(fade(element), 1000);

    index.appendChild(element);
}

function fade(element) {
    var op = 1;  // initial opacity
    var timer = setInterval(function () {
                            if (op <= 0.1){
                            clearInterval(timer);

                            element.style.display = 'none';
                            }
                            element.style.opacity = op;
                            element.style.filter = 'alpha(opacity=' + op * 100 + ")";
                            op -= op * 0.1;
                            }, 50);
}

function popUp (json){
    var window = document.getElementById("viewPopUp");
    var blanket = document.getElementById("popUpBlanket");
    show(window);
    show(blanket);

    var table = document.getElementById("userTable")

    var id = json.payload.id;
    var mail = json.payload.mail;
    var phone = json.payload.phone;
    var last = json.payload.last;
    var first = json.payload.first;
    var created = json.payload.created;
    table.deleteRow(1);
    var row = table.insertRow(1);

    var zero = row.insertCell(0);
    var one = row.insertCell(1);
    var two = row.insertCell(2);
    var three = row.insertCell(3);
    var four = row.insertCell(4);
    var five = row.insertCell(5);

    zero.innerHTML = id;
    one.innerHTML = first;
    two.innerHTML = last;
    three.innerHTML = mail;
    four.innerHTML = phone;
    five.innerHTML = created;
}

function editPopUp (json) {
    var window = document.getElementById("editPopUp");
    var blanket = document.getElementById("popUpBlanket");
    show(window);
    show(blanket);

    var title = document.getElementById("editTitle");
    title.innerHTML = "Edit User Information";


    var id = json.payload.id;

    var first = document.getElementById("editFirst");
    var last = document.getElementById("editLast");
    var mail = document.getElementById("editMail");
    var phone = document.getElementById("editPhone");

    first.value = json.payload.first;
    last.value = json.payload.last;
    mail.value = json.payload.mail;
    phone.value = json.payload.phone;

    var saveEdit = document.getElementById("saveEdit");
     saveEdit.setAttribute("onclick", "updateJson('"+id+"')");

}

function hidePopUp () {
    var window = document.getElementById("viewPopUp");
    var editWindow = document.getElementById("editPopUp");
    var cancelWindow = document.getElementById("cancelPopUp");
    var blanket = document.getElementById("popUpBlanket");
    hide(window);
    hide (editWindow);
    hide(cancelWindow);
    hide(blanket);

}

function createUserPopUp (){
    var window = document.getElementById("editPopUp");
    var blanket = document.getElementById("popUpBlanket");
    show(window);
    show(blanket);

    var title = document.getElementById("editTitle");
    title.innerHTML = "Add new user";

    var first = document.getElementById("editFirst");
    var last = document.getElementById("editLast");
    var mail = document.getElementById("editMail");
    var phone = document.getElementById("editPhone");

    first.value ="";
    last.value ="";
    mail.value ="";
    phone.value ="";


    var saveUser = document.getElementById("saveEdit");
    saveUser.setAttribute("onclick", "createJson()")
}

function cancelPopUp (id) {
    var window = document.getElementById("cancelPopUp");
    var blanket = document.getElementById("popUpBlanket");
    show(blanket);
    show(window);

    var ok = document.getElementById("confirmDelete");
    ok.setAttribute("onclick","deleteJson('"+id+"')" );

}



function suicide (element) {
    element.parentNode.removeChild(element);
}

function show (element) {
    element.style.display="block";
}


