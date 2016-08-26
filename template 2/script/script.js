$(document).ready(function(){
    var x = $("#menu").height();
    var y = x + $("#menucontainer").height();

    function hideall(){
        $("#about").hide();
        $("#admission").hide();
        $("#department").hide();
        $("#research").hide();
        $("#exam").hide();
        $("#life").hide();
    }
    
    function resetcolor(){
        $(".about").css("background-color", "#303841");
        $(".admission").css("background-color", "#303841");
        $(".department").css("background-color", "#303841");
        $(".research").css("background-color", "#303841");
        $(".exam").css("background-color", "#303841");
        $(".life").css("background-color", "#303841");
    }
    
    $("#header").on('mouseover', function(){
        resetcolor();
        $("#menucontainer").fadeOut(100);
    });
    
    //about
    
    $(".about").on('mouseover', function(){
        hideall();
        resetcolor();
        $("#about").show();
        $(".about").css("background-color", "#282E36");
        $("#menucontainer").fadeIn(100);
    });
    
    $("#about").on('mouseover', function(){
        $(".about").css("background-color", "#282E36");
    });

    $("#about").on('mouseleave', function(){
        $(".about").css("background-color", "#303841");
        $("#menucontainer").fadeOut(100);
    });
    
    //admission
    
    $(".admission").on('mouseover', function(){
        hideall();
        resetcolor();
        $("#admission").show();
        $(".admission").css("background-color", "#282E36");
        $("#menucontainer").fadeIn(100);
    });
    
    $("#admission").on('mouseover', function(){
        $(".admission").css("background-color", "#282E36");
    });

    $("#admission").on('mouseleave', function(){
        $(".admission").css("background-color", "#303841");
        $("#menucontainer").fadeOut(100);
    });
    
    //department
    
    $(".department").on('mouseover', function(){
        hideall();
        resetcolor();
        $("#department").show();
        $(".department").css("background-color", "#282E36");
        $("#menucontainer").fadeIn(100);
    });
    
    $("#department").on('mouseover', function(){
        $(".department").css("background-color", "#282E36");
    });

    $("#department").on('mouseleave', function(){
        $(".department").css("background-color", "#303841");
        $("#menucontainer").fadeOut(100);
    });
    
    //research
    
    $(".research").on('mouseover', function(){
        hideall();
        resetcolor();
        $("#research").show();
        $(".research").css("background-color", "#282E36");
        $("#menucontainer").fadeIn(100);
    });
    
    $("#research").on('mouseover', function(){
        $(".research").css("background-color", "#282E36");
    });

    $("#research").on('mouseleave', function(){
        $(".research").css("background-color", "#303841");
        $("#menucontainer").fadeOut(100);
    });
    
    //exam
    
    $(".exam").on('mouseover', function(){
        hideall();
        resetcolor();
        $("#exam").show();
        $(".exam").css("background-color", "#282E36");
        $("#menucontainer").fadeIn(100);
    });
    
    $("#exam").on('mouseover', function(){
        $(".exam").css("background-color", "#282E36");
    });

    $("#exam").on('mouseleave', function(){
        $(".exam").css("background-color", "#303841");
        $("#menucontainer").fadeOut(100);
    });
    
    //life
    
    $(".life").on('mouseover', function(){
        hideall();
        resetcolor();
        $("#life").show();
        $(".life").css("background-color", "#282E36");
        $("#menucontainer").fadeIn(100);
    });
    
    $("#life").on('mouseover', function(){
        $(".life").css("background-color", "#282E36");
    });

    $("#life").on('mouseleave', function(){
        $(".life").css("background-color", "#303841");
        $("#menucontainer").fadeOut(100);
    });
    
    
    //scroll
    
    $(window).scroll(function(){
        $("#menucontainer").fadeOut(100);
    });

});