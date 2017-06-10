$(window).scroll(function(){
    console.log($(window).scrollTop() + " " + $("#content").css("margin-top"));
    if($(window).scrollTop() > $("#first").height()){
        $("#first #bg > div:first-child").hide();
    }
    else{
        $("#first #bg > div:first-child").show();
    }
});

$(document).ready(function(){
    $("#content").css("margin-top", $("#first").height() + $("header").height() + 10);
    
    
    $("#services").css("margin-top", $("header").height());
    if($(window).width() > 750)
        $("#services").css("height", $("#services").height() - $("header").height());
    
    
    $("#services #left > div > div").css("height", $("#services #left > div").height() + 10);
    $("#second > div").css("height", $("#second > div").width());
    
    $(function() {
      $('a[href*="#"]:not([href="#"])').click(function() {
          
          if($(this).attr("id") == "gototop"){
                $('html, body').animate({
                  scrollTop: 0
                }, 1000);
                return false;
          }
          
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
    
    $("#services #left > div").on('click', function(){
        $("#services #right > div").fadeOut();
        $("." + $(this).attr("id")).fadeIn();
    });
    
});

