$(document).ready(function(){
        
    var i = 1;
    
    function slide(){
        var images = [
            "url('images/slider/1.JPG')", 
            "url('images/slider/2.JPG')", 
            "url('images/slider/3.JPG')"
        ];    
        
        var title = [
            'Welcome To Sri Krishna Freshers !',
            'This is the second post',
            'This is the third post'
        ];
        
        var excerpt = [
            'A warm welcome to all freshers from Sri Krishna Management.',
            'Lorem ipsum dolor sit amet, consectetur adipiscing elit sed.',
            'Praesent auctor orci vitae urna feugiat tincidunt.'
        ];
        
        var classes = [
            'col-md-4 col-md-offset-8 content one',
            'col-md-4 col-md-offset-8 content two',
            'col-md-4 col-md-offset-8 content three'
        ];
        
        $("#slider").css("opacity", "0");
        $("#slider").css("background-image", images[i]);
        $("#slider .container > div").removeClass();
        $("#slider .container > div").addClass(classes[i]);
        $("#slider .title").html(title[i]);
        $("#slider .excerpt").html(excerpt[i]);
        $("#slider").css("opacity", "1");
        
        i = (i + 1) % 3
        
    }
    
    setInterval(slide, 4000);
});