$(document).ready(function ($) {
    console.log("Position"));
    $('#main-visitante').on('load', function ($) {        
        var h = $("#header-visitante").outerHeight();
        console.log("Altura:" + h);
        $('#main-visitante').css('padding-top', h)
    });
});