<link rel = "stylesheet" href = "css/footer.css">

<script>
    function change(){
        document.getElementById("ugc").setAttribute("src", "images/footer/UGC1.png");
    }
    function chageback(){
        document.getElementById("ugc").setAttribute("src", "images/footer/UGC.png");
    }
</script>

<footer>
    <div class = "flex">
        
        <div>
            
        </div>
        
        <div>
            
        </div>
        
        <div class = "image">
            <img src = "images/footer/SKI.png" width = "50%">
            <div>
                <img src = "images/footer/AU.png" width = "15%">
                <img src = "images/footer/AICTE.png" width = "15%">
                <img id = "ugc" src = "images/footer/UGC.png" width = "15%" onmouseover = "change()" onmouseleave = "chageback()">
                <img src = "images/footer/FB.png" width = "15%" style = "border-radius: 50%;">
            </div>
        </div>
        
        <div>
            
        </div>
        
        <div>
            
        </div>
        
    </div>
</footer>