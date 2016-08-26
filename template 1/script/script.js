$(window).load(function(){
    $("#preloader").fadeOut();
});

$(document).ready(function(){
    
    var y = $("#hero").height();
    
    $("#slide").css("margin-top", y + "px");

    function hideall(){
        var x = $("#menu").height();
        $("#menucontainer").css("margin-top", x + "px");
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
    
    $("#header").on('click', function(){
        resetcolor();
        $("#menucontainer").fadeOut(100);
    });
    
    //about
    
    $(".about").on('click', function(){
        hideall();
        resetcolor();
        $("#about").show();
        $(".about").css("background-color", "#282E36");
        $("#menucontainer").fadeIn(100);
    });
    
    $("#about").on('click', function(){
        $(".about").css("background-color", "#282E36");
    });

    $("#about").on('mouseleave', function(){
        $(".about").css("background-color", "#303841");
        $("#menucontainer").fadeOut(100);
    });
    
    //admission
    
    $(".admission").on('click', function(){
        hideall();
        resetcolor();
        $("#admission").show();
        $(".admission").css("background-color", "#282E36");
        $("#menucontainer").fadeIn(100);
    });
    
    $("#admission").on('click', function(){
        $(".admission").css("background-color", "#282E36");
    });

    $("#admission").on('mouseleave', function(){
        $(".admission").css("background-color", "#303841");
        $("#menucontainer").fadeOut(100);
    });
    
    //department
    
    $(".department").on('click', function(){
        hideall();
        resetcolor();
        $("#department").show();
        $(".department").css("background-color", "#282E36");
        $("#menucontainer").fadeIn(100);
    });
    
    $("#department").on('click', function(){
        $(".department").css("background-color", "#282E36");
    });

    $("#department").on('mouseleave', function(){
        $(".department").css("background-color", "#303841");
        $("#menucontainer").fadeOut(100);
    });
    
    //research
    
    $(".research").on('click', function(){
        hideall();
        resetcolor();
        $("#research").show();
        $(".research").css("background-color", "#282E36");
        $("#menucontainer").fadeIn(100);
    });
    
    $("#research").on('click', function(){
        $(".research").css("background-color", "#282E36");
    });

    $("#research").on('mouseleave', function(){
        $(".research").css("background-color", "#303841");
        $("#menucontainer").fadeOut(100);
    });
    
    //exam
    
    $(".exam").on('click', function(){
        hideall();
        resetcolor();
        $("#exam").show();
        $(".exam").css("background-color", "#282E36");
        $("#menucontainer").fadeIn(100);
    });
    
    $("#exam").on('click', function(){
        $(".exam").css("background-color", "#282E36");
    });

    $("#exam").on('mouseleave', function(){
        $(".exam").css("background-color", "#303841");
        $("#menucontainer").fadeOut(100);
    });
    
    //life
    
    $(".life").on('click', function(){
        hideall();
        resetcolor();
        $("#life").show();
        $(".life").css("background-color", "#282E36");
        $("#menucontainer").fadeIn(100);
    });
    
    $("#life").on('click', function(){
        $(".life").css("background-color", "#282E36");
    });

    $("#life").on('mouseleave', function(){
        $(".life").css("background-color", "#303841");
        $("#menucontainer").fadeOut(100);
    });
    
    $("#border").on('mouseover', function(){
        $("#menu").slideDown();
    });
    
    $(window).scroll(function(){
        $("#hero #logo").css("margin-right", $(window).scrollTop() / 2 + "px");
        $("#hero .name").css("margin-left", $(window).scrollTop() / 2 + "px");
        $(".icons ul li").css("left", $(window).scrollTop() + "px");
    });
    
    var images = [
        "url('images/hero1.jpg')",
        "url('images/hero2.jpg')",
        "url('images/hero3.jpg')",
        "url('images/hero4.jpg')"
    ];
    
    i = 1;
    
    function heroslide(){
        $("#hero").css("background-image", images[i]);
        i = (i + 1) % 4;
    }
    
    setInterval(heroslide, 4000);

});