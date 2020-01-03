(function(){
	var initViewSize = function(){
		
		var pageTopbar = $('#page-topbar');
		var pageSidebar = $('#page-sidebar');
		
		var usedHeight = 0;
		var usedWidth = 0;
		
		//tab页的高度
		usedHeight += 30;
		
		if(pageTopbar.length){
			usedHeight += 50;
		}
		//如果当前页面存在pagesidebar，说明是home.screen,宽度按照50，180计算
		if(pageSidebar.length){
			//如果当前页面存在page-sidebar，默认是打开状态
			if(pageSidebar.hasClass('page-side-toggle-off') || Ext.isIE8){
				usedWidth += 50;
			}else{
				usedWidth += 180;
			}
		}else{
			//如果当前不存在pagesidebar，说明是内部页面，使用宽度按照 -14计算
			usedWidth += 50;
		}
		
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

