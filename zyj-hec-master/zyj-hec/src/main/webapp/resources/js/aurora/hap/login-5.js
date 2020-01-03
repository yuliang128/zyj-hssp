var Login = function() {
	var handleAlerts = function() {
        $('body').on('click', '[data-close="alert"]', function(e) {
            $(this).parent('.alert').hide();
            $(this).closest('.note').hide();
            e.preventDefault();
        });

        $('body').on('click', '[data-close="note"]', function(e) {
            $(this).closest('.note').hide();
            e.preventDefault();
        });

        $('body').on('click', '[data-remove="note"]', function(e) {
            $(this).closest('.note').remove();
            e.preventDefault();
        });
    };
    return {
        //main function to initiate the module
        init: function() {
            // init background slide images
            $('.login-bg').backstretch([
                _baseContext +　"/resources/images/aurora/hap/bg1.jpg",
                _baseContext +　"/resources/images/aurora/hap/bg2.jpg",
                _baseContext +　"/resources/images/aurora/hap/bg3.jpg"
                ], {
                  fade: 1000,
                  duration: 4000
                }
            );
            
            handleAlerts();
        }

    };

}();

jQuery(document).ready(function() {
    Login.init();
});



function player(){
	var browser=navigator.appName;
	var b_version=navigator.appVersion;
	var version=b_version.split(";"); 
	var trim_Version=version[1].replace(/[ ]/g,""); 
	var viewHeight=document.documentElement.clientHeight;
	var viewWidth=document.documentElement.clientWidth;
	var minHeight=viewHeight/2 + "px";
	if(browser=="Microsoft Internet Explorer" && trim_Version=="MSIE8.0"){
		$(".user-login-5 .login-bg").css("min-height",viewHeight);
		$(".user-login-5").css("min-height",viewHeight);
		$(".user-login-5 .login-container").css("min-height",viewHeight);
		if(viewWidth&&viewWidth<1023){
			$(".user-login-5 .login-bg").css("min-height",minHeight);
			$(".user-login-5").css("min-height",minHeight);
			$(".user-login-5 .login-container").css("min-height",minHeight);
		}
	}
}