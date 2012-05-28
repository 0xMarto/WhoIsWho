/* Author: Team w&w */

function hideLoginForm() {
    var element = document.getElementById("loginPage");
    element.style.display = "none";
}

function validateUsername() {
    var username = document.getElementById("username").value();
    if (username.contains("%")) {
        document.getElementById("username").value("");
    }
}

//Card dropping effect
$('.card').click(function() {
    $('.cardInfo').toggle(
        function () {
            $(this).css({"width":"100px", "height":"140px", "margin-left": "0"});
        },
        function () {
            $(this).css({"width":"120px", "height":"5px", "margin-left":"-10px"});
        }
    );
});



