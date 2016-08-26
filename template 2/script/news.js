$(document).ready(function(){
        
    var i = 1;
    
    function news(){
        var images = [
            "url('images/events/1.JPG')", 
            "url('images/events/2.JPG')", 
            "url('images/events/3.JPG')",
            "url('images/events/4.JPG')",
            "url('images/events/5.JPG')"
        ];    
        
        var title = [
            "<p>Cybernova Starts off !</p><div class = 'excerpt'>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non dolor maximus, feugiat ante nec, tempor sapien. Quisque et mi a metus mollis scelerisque.</div>",
            "<p>This is the second post</p><div class = 'excerpt'>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non dolor maximus, feugiat ante nec, tempor sapien. Quisque et mi a metus mollis scelerisque.</div>",
            "<p>This is the third post</p><div class = 'excerpt'>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non dolor maximus, feugiat ante nec, tempor sapien. Quisque et mi a metus mollis scelerisque.</div>",
            "<p>This is the fourth post</p><div class = 'excerpt'>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non dolor maximus, feugiat ante nec, tempor sapien. Quisque et mi a metus mollis scelerisque.</div>",
            "<p>This is the fifth post</p><div class = 'excerpt'>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non dolor maximus, feugiat ante nec, tempor sapien. Quisque et mi a metus mollis scelerisque.</div>"
        ];
        
        $("#events").css("background-image", images[i]);
        $("#events .title").html(title[i]);
        
        i = (i + 1) % 5
        
    }
    
    setInterval(news, 4000);
});