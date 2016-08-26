$(document).ready(function(){
    
    $(".bar").hide();
    
    function hideall(){
        $("#about").hide();
        $("#admission").hide();
        $("#department").hide();
        $("#research").hide();
        $("#exams").hide();
        $("#life").hide();
    }
    
    function reset(){
        $(".about").css("background-color", "rgba(56 ,66 ,77, 0.7)");
        $(".admission").css("background-color", "rgba(56 ,66 ,77, 0.7)");
        $(".department").css("background-color", "rgba(56 ,66 ,77, 0.7)");
        $(".research").css("background-color", "rgba(56 ,66 ,77, 0.7)");
        $(".exams").css("background-color", "rgba(56 ,66 ,77, 0.7)");
        $(".life").css("background-color", "rgba(56 ,66 ,77, 0.7)");
    }
    
    $(".about").on('click', function(){
        hideall();
        reset();
        $("#about").show();
        $(".icons").css("right", "30%");
        $(".about").css("background-color", "rgba(56 ,66 ,77, 1)");
        $(".bar").fadeIn();
    });
    
    $(".admission").on('click', function(){
        hideall();
        reset();
        $("#admission").show();
        $(".icons").css("right", "30%");
        $(".admission").css("background-color", "rgba(56 ,66 ,77, 1)");
        $(".bar").fadeIn();
    });
    
    $(".department").on('click', function(){
        hideall();
        reset();
        $("#department").show();
        $(".icons").css("right", "30%");
        $(".department").css("background-color", "rgba(56 ,66 ,77, 1)");
        $(".bar").fadeIn();
    });
    
    $(".research").on('click', function(){
        hideall();
        reset();
        $("#research").show();
        $(".icons").css("right", "30%");
        $(".research").css("background-color", "rgba(56 ,66 ,77, 1)");
        $(".bar").fadeIn();
    });
    
    $(".exams").on('click', function(){
        hideall();
        reset();
        $("#exams").show();
        $(".icons").css("right", "30%");
        $(".exams").css("background-color", "rgba(56 ,66 ,77, 1)");
        $(".bar").fadeIn();
    });
    
    $(".life").on('click', function(){
        hideall();
        reset();
        $("#life").show();
        $(".icons").css("right", "30%");
        $(".life").css("background-color", "rgba(56 ,66 ,77, 1)");
        $(".bar").fadeIn();
    });
    
    $(".fa-times-circle").on('click', function(){
        reset();
        $(".bar").fadeOut('fast');
        $(".icons").css("right", "0%");
    });
    
});