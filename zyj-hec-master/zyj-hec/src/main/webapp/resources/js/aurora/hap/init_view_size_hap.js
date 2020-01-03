(function(){
	var initViewSize = function(){
		var usedWidth = 0;
		var usedHeight = 0;
//		if(window.location.href.indexOf("main_hap.screen") != -1){
////			debugger;
			var page_content = $('.page-content');
			var page_container = $('.page-container');
			usedWidth += 20;//子页面body的padding值
			usedHeight += 20;//子页面body的padding值
			usedWidth += (page_content.outerWidth(true)-page_content.width());//计算page_content的边缘宽度
			usedWidth += (page_container.outerWidth(true)-page_container.width());//计算page_container的边缘宽度
			usedHeight += (page_container.outerHeight(true)-page_container.height());//计算page_container的边缘高度
			usedWidth -= 20;//修正计算错误
			usedHeight += 10;//修正计算错误
//		}
		if(window.location.href.indexOf("role_select") != -1){
			usedHeight += 70;
		}
        var viewportWidth = $(".k-tabstrip-wrapper").width();
        var viewportHeight = $(".page-content").height();
		// var availableWidth = viewportWidth - usedWidth;
		// var availableHeight = viewportHeight - usedHeight;
        setCookie('vw',viewportWidth);
        setCookie('vh',viewportHeight-1);
		// $A.usedWidth = usedWidth;
		// $A.usedHeight = usedHeight;
		// $A.auWindow.fireEvent('resize');
	};
	$(window).bind('load',initViewSize).bind('resize',initViewSize);
})();

function setCookie(name, value) {
    // var exp = new Date();
    // exp.setTime(exp.getTime() + 60 * 60 * 1000);
    document.cookie = name + "=" + escape(value) + ";expires=" + "" + ";path=/";
}
