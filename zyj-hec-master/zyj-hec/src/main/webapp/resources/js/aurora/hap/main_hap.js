//var nav = 'Y';
//var isInitScroller = false;
//var isChangeWidth = false;
//var viewHeight = $(window).height(), contentHeight = viewHeight - 159;
$(document).ready(function(){
	$(".fun-list-lv1 li").click(function(){
		if($(this).hasClass("start"))
			{
				$(this).addClass("fun-item-lv1-hover").siblings().removeClass("fun-item-lv1-hover");
			}
		else{
			$(".start").removeClass("fun-item-lv1-hover");
		}
	});
});

/**
 * Created by HASEE on 2017/3/15.
 */

window.currentLv1FunCode = '';
window.currentLv2FunCode = '';
window.currentLv3FunCode = '';
window.currentLv1FunItem = null;
window.currentLv2FunItem = null;
window.currentLv3FunItem = null;
window.currentLv2FunArea = null;
window.currentLv3FunArea = null;
window.maskDiv = null;

window.hideLv2FunArea = function () {
    if (window.currentLv2FunArea) {
        window.currentLv2FunArea.removeClass('fun-area-show');
    }
    if(window.maskDiv){
    	window.maskDiv.removeClass('mask-div-show');
    }
}

Ext.onReady(function () {
    //Lv1功能增加hover事件监听
//	$(".fun-list-lv1").niceScroll();
	if(!Ext.isIE8){
		$(".fun-list-lv1").niceScroll({horizrailenabled:false});
		$(".sidebar-fun-area-lv2").niceScroll();
		$(".sidebar-fun-area-lv3").niceScroll();
	}
    Ext.each(Ext.query('li.fun-item-lv1'), function (funLv1Item) {
        Ext.get(funLv1Item).on('click', function (e1) {
            e1.stopEvent();
            e1.stopPropagation();
            funLv1Item.hoverTask = new Ext.util.DelayedTask(function () {
                //上一次lv2的区域隐藏
               if (window.currentLv2FunArea && window.currentLv2FunArea.hasClass('fun-area-show')) {
                    window.currentLv2FunArea.toggleClass('fun-area-show');
                }
                if(window.maskDiv && window.maskDiv.hasClass('mask-div-show')){
                	window.maskDiv.toggleClass('mask-div-show');
                }
                //上一次lv1的菜单状态清除
                if (window.currentLv1FunItem && window.currentLv1FunItem.hasClass('fun-item-lv1-hover')) {
                    window.currentLv1FunItem.toggleClass('fun-item-lv1-hover');
                }
                window.currentLv1FunCode = Ext.get(funLv1Item).getAttribute('fun-code');
                window.currentLv1FunItem = Ext.get('fun-item-lv1_' + window.currentLv1FunCode);
                window.currentLv2FunArea = Ext.get('sidebar-fun-float-area_' + window.currentLv1FunCode);
                window.maskDiv = Ext.get('mask-div');
                //本次LV1菜单状态的变化和Lv2区域的显示
                window.currentLv1FunItem.toggleClass('fun-item-lv1-hover');
                window.currentLv2FunArea.toggleClass('fun-area-show');
                window.maskDiv.toggleClass('mask-div-show');
                if(!Ext.isIE8){
                	$(".sidebar-fun-area-lv2").getNiceScroll().resize();
                }
                //Lv2功能增加hover事件监听
                Ext.each(window.currentLv2FunArea.query('li.fun-item-lv2'), function (funLv2Item) {
                    Ext.get(funLv2Item).on('mouseover', function (e2) {
                        e2.stopEvent();
                        e2.stopPropagation();
                        if (!funLv2Item.hoverTask) {
                            funLv2Item.hoverTask = new Ext.util.DelayedTask(function () {
                                //上一次lv3的区域隐藏
                                if (window.currentLv3FunArea && window.currentLv3FunArea.hasClass('fun-area-show')) {
                                    window.currentLv3FunArea.toggleClass('fun-area-show');
                                }
                                //上一次lv2的菜单状态清除
                                if (window.currentLv2FunItem && window.currentLv2FunItem.hasClass('fun-item-lv2-hover')) {
                                    window.currentLv2FunItem.toggleClass('fun-item-lv2-hover');
                                }

                                window.currentLv2FunCode = Ext.get(funLv2Item).getAttribute('fun-code');
                                window.currentLv2FunItem = Ext.get('fun-item-lv2_' + window.currentLv2FunCode);
                                window.currentLv3FunArea = Ext.get('sidebar-fun-area-lv3_' + window.currentLv2FunCode);

                                //本次LV2菜单状态的变化和Lv3区域的显示
                                window.currentLv2FunItem.toggleClass('fun-item-lv2-hover');
                                window.currentLv3FunArea.toggleClass('fun-area-show');
                                if(!Ext.isIE8){
                                	$(".sidebar-fun-area-lv3").getNiceScroll().resize();
                                }
                                //Lv3功能增加hover和click事件监听
                                Ext.each(window.currentLv3FunArea.query('li.fun-item-lv3'), function (funLv3Item) {
                                    Ext.get(funLv3Item).on('mouseover', function (e3) {
                                        e3.stopEvent();
                                        e3.stopPropagation();

                                        if (!funLv3Item.hoverTask) {
                                            funLv3Item.hoverTask = new Ext.util.DelayedTask(function () {
                                                //上一次lv3的菜单状态清除
                                                if (window.currentLv3FunItem && window.currentLv3FunItem.hasClass('fun-item-lv3-hover')) {
                                                    window.currentLv3FunItem.toggleClass('fun-item-lv3-hover');
                                                }

                                                window.currentLv3FunCode = Ext.get(funLv3Item).getAttribute('fun-code');
                                                window.currentLv3FunItem = Ext.get('fun-item-lv3_' + window.currentLv3FunCode);

                                                //本次LV3菜单状态的变化
                                                window.currentLv3FunItem.toggleClass('fun-item-lv3-hover');
                                            });
                                            funLv3Item.hoverTask.delay(100);
                                        }
                                    });

                                    Ext.get(funLv3Item).on('mouseout', function (e) {
                                        if (funLv3Item.hoverTask) {
                                            funLv3Item.hoverTask.cancel();
                                            funLv3Item.hoverTask = null;
                                        }
                                    });

                                    Ext.get(funLv3Item).on('click', function (e4) {
                                        window.hideLv2FunArea();
                                        e4.stopEvent();
                                        e4.stopPropagation();
                                        var id = Ext.get(funLv3Item).getAttribute('id');
                                        var url = Ext.get(funLv3Item).getAttribute('fun-url');
                                        var prompt = Ext.get(funLv3Item).getAttribute('fun-name');
                                        window.openTab(url, prompt);
                                    });
                                }, this);
                            });
                            funLv2Item.hoverTask.delay(150);
                        }
                    });

                    Ext.get(funLv2Item).on('mouseout', function (e) {
                        if (funLv2Item.hoverTask) {
                            funLv2Item.hoverTask.cancel();
                            funLv2Item.hoverTask = null;
                        }
                    });
                }, this);
            }, this);
            funLv1Item.hoverTask.delay(0);
        });

        Ext.get(funLv1Item).on('mouseout', function (e) {
            if (funLv1Item.hoverTask) {
                funLv1Item.hoverTask.cancel();
                funLv1Item.hoverTask = null;
            }
        });
    }, this);

/*    $('.sidebar-toggler').on('click', function (e) {
        e.stopEvent();
        e.stopPropagation();
    	
        $('#page-sidebar').toggleClass('page-side-toggle-on');
        $('#page-sidebar').toggleClass('page-side-toggle-off');

        if ($('#page-sidebar').hasClass('page-side-toggle-off')) {
            $('[data-toggle="tooltip"]').tooltip();
        } else {
            $('[data-toggle="tooltip"]').tooltip('destroy');
        }

        $(window).resize();
        $A.auWindow.fireEvent('resize');
    }, this);*/

    //一级菜单和二级菜单点击，不会隐藏掉二级菜单块，页面上其他部分的点击都隐藏整个二级菜单块
    Ext.getBody().on('click', window.hideLv2FunArea);
    Ext.each(Ext.query('.sidebar-fun-float-area'),function(lv2FunArea){
    	Ext.get(lv2FunArea).on('click', function(e){
    		window.hideLv2FunArea();
        	e.stopEvent();
        	e.stopPropagation();
        });
    });
    Ext.each(Ext.query('.fun-item-lv2'), function (lv2FunArea) {
        Ext.get(lv2FunArea).on('click', function (e) {
            e.stopEvent();
            e.stopPropagation();
        });
    }, this);
    (function(){
//        if ($jq('#page-sidebar').hasClass('page-side-toggle-off')) {
//            $jq('[data-toggle="tooltip"]').tooltip();
//        } else {
//            $jq('[data-toggle="tooltip"]').tooltip('destroy');
//        }
        $(window).resize();
        $A.auWindow.fireEvent('resize');
    })();
});