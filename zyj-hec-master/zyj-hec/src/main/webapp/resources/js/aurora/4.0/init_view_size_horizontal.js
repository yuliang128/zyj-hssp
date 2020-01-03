(function(){
	var initViewSize = function(){
		
		var pageTopbar = $('#page-topbar');
		var pageSidebar = $('#page-sidebar');
		
		var usedHeight = 0;
		var usedWidth = 0;
		
		//tab页的高度
		usedHeight += 80;
		
		
		var viewportWidth = Aurora.getViewportWidth();
		var viewportHeight = Aurora.getViewportHeight();
		
		var availableWidth = viewportWidth - usedWidth;
		var availableHeight = viewportHeight - usedHeight;
		
		$A.setCookie('vw',availableWidth);
		$A.setCookie('vh',availableHeight);
		
		$A.usedWidth = usedWidth;
		$A.usedHeight = usedHeight;
	};
	
	$(window).bind('load',initViewSize).bind('resize',initViewSize);
	
	//由于加载该段JS时vue尚未渲染完成，所以在sidebar的vue的updated中调用$(window).resize()重新计算宽度
})();

