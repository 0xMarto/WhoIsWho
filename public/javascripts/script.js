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
        $(this).rotate3Di('toggle', 1000, {direction: 'clockwise', sideChange: mySideChange});

    });
});



