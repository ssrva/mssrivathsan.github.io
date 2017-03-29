$(window).scroll(function(){
    if($(window).scrollTop() == 0){
        $("header").css("background-color", "rgba(48,56,65,0.6)");
    }
    else{
        $("header").css("background-color", "rgba(48,56,65,1)");
    }
});

$(document).ready(function(){
    var selected = 1;
    
    $("header #nav ul").css("height", $("header #nav").height() + 20);
    
    
    //typed
    $(function(){
      $("#inner_master #text").typed({
        strings: ["Your source for All Things Mocked Up !"],
        typeSpeed: -5
      });
    });
    
    //smoothscroll
    $(function() {
      $('a[href*="#"]:not([href="#"])').click(function() {
        if (location.pathname.replace(/^\//,'') == this.pathname.replace(/^\//,'') && location.hostname == this.hostname){
          var target = $(this.hash);
          target = target.length ? target : $('[name=' + this.hash.slice(1) +']');
          if (target.length) {
            $('html, body').animate({
              scrollTop: target.offset().top - $("header").height()
            }, 1000);
            return false;
          }
        }
      });
    });
    
    function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            
            reader.onload = function (e) {
                $('#preview').attr('src', e.target.result);
            }
            
            reader.readAsDataURL(input.files[0]);
        }
    }
    
    $("#input").change(function(){
        readURL(this);
    });
    
    
    $("#btn").on('click', function(){
        $(".canvas_container").css("display", "none");
        $("#input").click();
    });
    
    $(".layout img").on('click', function(){
        selected = $(this).attr("class");
        $(".layout img").css("border", "5px solid #fff");
        $(this).css("border", "5px solid #00aeef");
    });
    
    function one(){
        var canvas = document.getElementById("myCanvas");
        var ctx = canvas.getContext("2d");
        var img = document.getElementById("preview");
        
        var bg = new Image();
        bg.onload = function () {
            ctx.drawImage(bg, 0, 0);
            ctx.drawImage(img, 275, 305, 440, 270);
            $("#loading").fadeOut();
            $(".canvas_container").css("display", "block");
        }
        bg.src = "images/1.png";
        
    }
    
    function two(){
        var canvas = document.getElementById("myCanvas");
        var ctx = canvas.getContext("2d");
        var img = document.getElementById("preview");
        
        var bg = new Image();
        bg.onload = function () {
            ctx.drawImage(bg, 0, 0);
            ctx.rotate(4.8*3.14/180);
            ctx.drawImage(img, 140, 145, 665, 425);
            $("#loading").fadeOut();
            $(".canvas_container").css("display", "block");
        }
        bg.src = "images/2.png";
        
    }
    
    $(".process").on('click', function(){
        $("#loading").fadeIn();
        $("#loading").css("display", "flex");
        if(selected == 1){
            one();
        }
        
        if(selected == 2){
            two();
        }
    });
    
    $(".download_button").on('click', function(){
        var canvas = document.getElementById("myCanvas");
        var link = document.createElement('a');
        link.innerHTML = 'download image';
        link.addEventListener('click', function(ev) {
            link.href = canvas.toDataURL();
            link.download = "MockupMakr.png";
        });
        link.click();
    });
});