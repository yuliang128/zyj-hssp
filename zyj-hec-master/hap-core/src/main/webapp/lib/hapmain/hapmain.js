window.onload = function() {
    var moreFun = document.getElementById("more-fun"),
        moreMask = document.getElementById("more-mask"),
        moreWrap = document.getElementById("more-wrap");
    var onOff = true;
    moreFun.onclick = function() {
        if (onOff) {
            $("#more-mask").css("display", "block");
            $("#more-fun").animate({
                right: "240px"
            });
            $("#more-wrap").animate({
                width: "240px"
            });
            if (!$("#more-fun").hasClass("more-active")) {
                $("#more-fun").addClass("more-active");
            }
            onOff = false;
        } else {
            $("#more-mask").css("display", "none");
            $("#more-fun").animate({
                right: "5px"
            });
            $("#more-wrap").animate({
                width: "0px"
            });
            $("#more-fun").removeClass("more-active");
            onOff = true;
        }
    };
    moreFun.onblur = function() {
        $("#more-mask").css("display", "none");
        $("#more-fun").animate({
            right: "5px"
        });
        $("#more-wrap").animate({
            width: "0px"
        });
        $("#more-fun").removeClass("more-active");
        onOff = true;
    };
    $("#toDoBody .infoTab li").click(function(){
        tabClickHandle(this)
    });

    $("#applicationBody .infoTab li").click(function(){
        tabClickHandle(this)
    });
}