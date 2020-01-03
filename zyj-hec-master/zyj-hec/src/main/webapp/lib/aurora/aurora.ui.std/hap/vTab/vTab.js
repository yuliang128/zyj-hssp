(function(A){
var _N = '',
    sd='scroll-disabled',
    vtsto='vTab-scroll-top-over',
    vtsbo='vTab-scroll-bottom-over',
    vtst='vTab-scroll-top',
    vtsb='vTab-scroll-bottom',
    vtc='vTab-close',
    vtbo='vTab-btn-over',
    vtbd='vTab-btn-down',
    vts='vTab-scroll',
    TOP = 'top',
    BOTTOM = 'bottom',
    ACTIVE = 'active',
    UNACTIVE = 'unactive',
    NONE = 'none',
    BLOCK = 'block',
    EVT_SELECT = 'select',
    EVT_BEFORE_OPEN = 'beforeopen',
    EVT_BEFORE_SELECT = 'beforeselect',
    PADDING_TOP = 'padding-left',
    STRIP = 'strip',
    $STRIP = '.'+STRIP,
    ERROR = '销毁Tab出错: ',
    STRIP_TPL=['<div class="strip unactive"  unselectable="on" onselectstart="return false;">',
                '<div class="strip-left"></div>',
                '<div style="width:{stripwidth}px;" class="strip-center"><div class="vTab-close"></div>{prompt}</div>',
                '<div class="strip-right"></div>',
            '</div>'],
    //BODY_TPL='<div  hideFocus style="height:{bodyheight}px" class="tab"></div>';
    BODY_TPL='<div  hideFocus style="/*height:{bodyheight}px*/" class="vTab"></div>';
    //IFRAME_TPL='<iframe frameborder="0" style="border:none;width:{bodywidth}px;height:{bodyheight}px;left:-10000px;top:-10000px;" class="tab"></iframe>';
	IFRAME_TPL='<iframe frameborder="0" style="border:none;width:{bodywidth}px;" class="vTab"></iframe>';

/**
 * @class AurorA.VTab
 * @extends Aurora.Component
 * Tab组件.
 * @author njq.niu@hand-china.com
 * @constructor
 * @param {Object} config 配置对象. 
 */
A.VTab = Ext.extend(A.Component,{
    constructor: function(config){
//      this.intervalIds=[];
        A.VTab.superclass.constructor.call(this,config);
    },
    initComponent:function(config){
        A.VTab.superclass.initComponent.call(this, config);
        var sf = this,
            w = sf.wrap,
            h = sf.head = w.child('div[atype=vTab.strips]'), 
            s = sf.stripwrap = h.parent();
        sf.stripwidth = config.stripwidth||60;
        sf.stripheight = config.stripheight||37;
        sf.body = w.child('div.item-vTab-body');
        sf.scrollTop = w.child('div[atype=scroll-top]');
        sf.scrollBottom = w.child('div[atype=scroll-bottom]');
        sf.sp = s.parent();
        sf.selectTab(config.selected||0);
    },
    processListener: function(ou){
        var sf = this;
        A.VTab.superclass.processListener.call(sf,ou);
        sf.sp[ou]('mousedown',sf.onMouseDown, sf)
            [ou]('mouseup',sf.onMouseUp, sf);
        sf.stripwrap[ou]('click',sf.onClick, sf)
            [ou]('mousewheel',sf.onMouseWheel, sf);
    },
    initEvents:function(){
        A.VTab.superclass.initEvents.call(this);   
        this.addEvents(
        /**
         * @event select
         * 选择事件.
         * @param {AurorA.VTabPanel} tabPanel TabPanel对象.
         * @param {Number} index 序号.
         * @param {AurorA.VTab} tab Tab对象.
         */
        EVT_SELECT,
        /**
         * @event beforeopen
         * 选择事件.
         * @param {AurorA.VTabPanel} tabPanel TabPanel对象.
         * @param {Number} index 序号. 
         */
        EVT_BEFORE_OPEN,
        /**
         * @event beforeselect
         * 选择之前事件.
         * @param {AurorA.VTabPanel} tabPanel TabPanel对象.
         * @param {Number} index 序号.
         * @param {AurorA.VTab} tab Tab对象.
         */
        EVT_BEFORE_SELECT
        );
        
    },
    /**
     * 改变某个Tab页的url。当是当前tab页时，会立即重新加载，否则会在选中之后加载。
     * @param {Number} index TabItem序号。当index<0时，TabItem序号等于TabItem的个数加上index。
     * @param {String} url 地址。
     */
    setURL:function(index,url){
        var sf = this,
            tab = sf.getTab(index);
        if(tab){
            index = tab.index;
            if(index == sf.selectedIndex){
                sf.reloadTab(index,url);
            }else{
                tab.body.loaded = false;
                sf.items[index].ref = url;
            }
        }
    },
    /**
     * 选中某个Tab页
     * @param {Number} index TabItem序号。当index<0时，TabItem序号等于TabItem的个数加上index。
     */
    selectTab:function(index,needRefresh){ 
        var sf = this,tab=sf.getTab(index);
        if(!tab)return;
        var index=tab.index,
            activeStrip = tab.strip,
            activeBody = tab.body;      
        if(activeStrip.hasClass(sd)){
            sf.selectTab(index+1);
            return;
        }
        if(sf.fireEvent(EVT_BEFORE_SELECT,sf,index,tab)){
            sf.selectedIndex=index;         
            if(sf.activeTab)sf.activeTab.replaceClass(ACTIVE,UNACTIVE);
            sf.activeTab = activeStrip;
            activeStrip.replaceClass(UNACTIVE,ACTIVE);
//            debugger;
            var stripwrap = sf.stripwrap,
                scrollTop = sf.scrollTop,
                scrollBottom = sf.scrollBottom,
                t=activeStrip.dom.offsetTop,//div.strip.active上边框与其父级元素上边框的距离
                h=activeStrip.getHeight(),//div.strip.active的高度
                st=stripwrap.getScroll().top,//滚动条当前滚动高度
                sh=stripwrap.getHeight(),//div.item-vTab-strip的高度
                hh=sf.head.getHeight(),//div.clearfix的高度
                tb=t+h-st-sh,
                tt=st-t;
            if(tb>0){
            	scrollBottom.removeClass(sd);
            	scrollTop.removeClass(sd);
                stripwrap.scrollTo(TOP,st+tb);
            }else if(tt>0){
            	scrollTop.removeClass(sd);
                stripwrap.scrollTo(TOP,st-tt);
                scrollBottom.removeClass(sd);
            }
            if(sh+stripwrap.getScroll().top>=hh){
                stripwrap.scrollTo(TOP,hh-sh);
                scrollBottom.addClass(sd);
            }else if(index==0){
                stripwrap.scrollTo(TOP,0);
                scrollTop.addClass(sd);
            }
            if(activeBody){
                if(sf.activeBody){
                    sf.activeBody.setStyle({left:'-10000px',top:'-10000px'});
//                	sf.activeBody.setStyle({display:'none'});
                }
                sf.activeBody = activeBody.setStyle({left:0,top:0});
//                sf.activeBody = activeBody.setStyle({display:'block'});
            }
            if(sf.items[index].ref && !activeBody.loading && (activeBody.loaded!= true||needRefresh)){
                activeBody.loading = true;
                sf.load(sf.items[index].ref,activeBody,index);
            }else{
                sf.fireEvent(EVT_SELECT, sf, index,tab);
            }
        }
    },  
    /**
     * 打开一个指定引用地址的Tab页，如果该指定的引用地址的页面已经被打开，则选中该Tab页
     * @param {String} ref Tab页面的引用地址
     * @param {String} prompt Tab的标题
     */
//    openTab : function(ref,prompt){
//    	debugger;
//        var sf = this,i=0,
//            items = sf.items,l = items.length;
//        for(;i<l;i++){
//            var item = items[i];
//            if(item.ref&&item.ref==ref){
//                sf.selectTab(i);return;
//            }
//        }
//        if(sf.fireEvent(EVT_BEFORE_OPEN,sf,l)!==false){
//            items.push({'ref':ref,prompt:prompt});
//            var stripwidth=Math.max(A.TextMetrics.measure(document.body,prompt).width+30,sf.stripwidth),
//                head = sf.head,
//                body = sf.body,
//                stripwrap = sf.stripwrap,
//                width = head.getWidth()+stripwidth+20;
//            head.setWidth(width);
//            if(width>stripwrap.getWidth()){
//                sf.scrollTop.setStyle({display:BLOCK});
//                sf.scrollBottom.setStyle({display:BLOCK});
//                stripwrap.setStyle(PADDING_TOP,'1px');
//            }
//            new Ext.Template(STRIP_TPL).append(head.dom,{'prompt':prompt,'stripwidth':stripwidth});
////          new Ext.Template(BODY_TPL).append(body.dom,{'bodyheight':body.getHeight()});
////          new Ext.Template(sf.loadtype == 'iframe' ? IFRAME_TPL : BODY_TPL).append(body.dom,{id:sf.id,'bodywidth':body.getWidth()-body.getBorderWidth('lr'),'bodyheight':body.getHeight()-body.getBorderWidth('tb')});
//            new Ext.Template(sf.loadtype == 'iframe' ? IFRAME_TPL : BODY_TPL).append(body.dom,{id:sf.id,'bodywidth':body.getWidth()-body.getBorderWidth('lr')});
//
//            sf.selectTab(l);
//        }
//    },
    /**
     * 关闭某个Tab页
     * @param {Integer} index TabItem序号。当index<0时，TabItem序号等于TabItem的个数加上index。
     */
    closeTab : function(o){
//    	debugger;
        var sf = this,tab=sf.getTab(o);
        if(!tab)return;
        var strip=tab.strip,body=tab.body,index=tab.index;
        if(!strip.child('div.'+vtc)){
            A.showWarningMessage('警告','该Tab页无法被关闭!')
            return;
        }
        if(sf.activeBody == tab.body){
            sf.activeBody=null;
            sf.activeTab=null;
        }
        sf.items.splice(index,1);
        var head = sf.head,
            stripwrap = sf.stripwrap,
            height= head.getHeight()-strip.getHeight();
//      head.setWidth(width);//IE8慢
        if(height <= stripwrap.getHeight()){
            sf.scrollTop.setStyle({display:NONE});
            sf.scrollBottom.setStyle({display:NONE});
            stripwrap.setStyle(PADDING_TOP,'0');
        }
        strip.remove();
        body.remove();
        
        delete body.loaded;
        (function(){
            Ext.iterate(body.cmps,function(key,cmp){
                if(cmp.destroy){
                    try{
                        cmp.destroy();
                    }catch(e){
                        alert(ERROR + e)
                    }
                }
            });
            head.setHeight(height);
        }).defer(10);
//        debugger;
        if(index==sf.selectedIndex)sf.selectTab(index-1);
        else if(index<sf.selectedIndex)sf.selectedIndex--;
    },
    destroy : function(){
//      var bodys = Ext.DomQuery.select('div.tab',this.body.dom);
        Ext.each(this.body.dom.children,function(body){
            Ext.iterate(Ext.get(body).cmps,function(key,cmp){
                if(cmp.destroy){
                    try{
                        cmp.destroy();
                    }catch(e){
                        alert(ERROR + e)
                    }
                }
            });
        });
        A.VTab.superclass.destroy.call(this); 
    },
    /**
     * 将某个Tab页设为不可用。当TabItem有且仅有1个时，该方法无效果。
     * @param {Integer} index TabItem序号。当index<0时，TabItem序号等于TabItem的个数加上index。
     */
    setDisabled : function(index){
        var sf = this,tab = sf.getTab(index);
        if(!tab)return;
        if(sf.items.length > 1){
            var strip = tab.strip,index = tab.index;
            if(sf.activeTab==strip){
                sf.selectTab(index+(sf.getTab(index+1)?1:-1))
            }
            strip.addClass(sd);
        }
    },
    /**
     * 将某个Tab页设为可用
     * @param {Integer} index TabItem序号。当index<0时，TabItem序号等于TabItem的个数加上index。
     */
    setEnabled : function(index){
        var tab = this.getTab(index);
        if(!tab)return;
        tab.strip.removeClass(sd);
    },
    getTab : function(o){
//    	debugger;
        var sf = this,
            bodys = sf.body.query('>.vTab'),//Ext.DomQuery.select('div.tab',this.body.dom),
            strips = sf.head.query('div.strip'),//Ext.DomQuery.select('div.strip',this.head.dom),
            strip,body;
        if(Ext.isNumber(o)){
            if(o<0)o+=strips.length;
            o=Math.round(o);
            if(strips[o]){
                strip=Ext.get(strips[o]);
                body=Ext.get(bodys[o]);
            }
        }else {
            o=Ext.get(o);
            o=Ext.each(strips,function(s,i){
                if(s == o.dom){
                    strip=o;
                    body=Ext.get(bodys[i]);
                    return false;
                }
            });
        }
        if(strip){
            body.loaded = !(sf.items[o].ref && body.loaded!=true);
            return {'strip':strip,'body':body,'index':o};
        }else{
            return null;
        }
    },
    scrollTo : function(lr){
//    	debugger;
        var sf = this,
            stripwrap = sf.stripwrap,
            scrollBottom = sf.scrollBottom,
            scrollTop = sf.scrollTop,
            st = stripwrap.getScroll().top,//滚动条当前滚动高度
            sh = sf.stripheight;//tab页高度
        if(lr==TOP){//向上滚动
            stripwrap.scrollTo(TOP,st-sh);//滚动条向上滚动sh
            scrollBottom.removeClass(sd);//向上滚动按钮可用
            if(stripwrap.getScroll().top<=0){//滚动条滚到顶部时，停止滚动
                scrollTop.addClass(sd).replaceClass(vtsto,vtst);
                sf.stopScroll();
            }
        }else if(lr==BOTTOM){//向下滚动
            stripwrap.scrollTo(TOP,st+sh);//滚动条向下滚动sh
            scrollTop.removeClass(sd);//向下滚动按钮可用
            if(stripwrap.getScroll().top+stripwrap.getHeight()>=sf.head.getHeight()){//滚动条滚到底部时，停止滚动
	            scrollBottom.addClass(sd).replaceClass(vtsbo,vtsb);
	        	sf.stopScroll();
            }
        }
    },
    stopScroll : function(){
        if(this.scrollInterval){
            clearInterval(this.scrollInterval);
            delete this.scrollInterval;
        }
    },
    onClick : function(e,t){
        var el=Ext.fly(t),strip=el.parent($STRIP);
        /*
         * 单击鼠标，移除tab-btn-over
         */
        if(strip){
        	if(strip.hasClass(vtbo)){
        		strip.removeClass(vtbo);
        	}
        }
        if(el.hasClass(vtc))this.closeTab(el.parent($STRIP));
    },
    onMouseWheel : function(e){
//    	debugger
        var delta = e.getWheelDelta();
        if(delta > 0){
            this.scrollTo(TOP);
            e.stopEvent();
        }else if (delta < 0){
            this.scrollTo(BOTTOM);
            e.stopEvent();
        }
    },
    onMouseDown : function(e,t){
//    	debugger;
        var el=Ext.fly(t),strip = el.parent($STRIP),sf=this;
        if(el.hasClass(vtc)){
            el.removeClass(vtbo).addClass(vtbd);
        }else if(el.hasClass(vts) && !el.hasClass(sd)){
            if(el.hasClass(vtsto))sf.scrollTo(TOP);
            else sf.scrollTo(BOTTOM);
            sf.scrollInterval=setInterval(function(){
                if(el.hasClass(vts)&&!el.hasClass(sd)){
                    if(el.hasClass(vtsto))sf.scrollTo();
                    else sf.scrollTo(BOTTOM);
                    if(el.hasClass(sd))clearInterval(sf.scrollInterval);
                }
            },100);
        }else if(strip && strip.hasClass(STRIP) && !strip.hasClass(ACTIVE) && !strip.hasClass(sd)){
            sf.selectTab(strip);
        }
    },
    onMouseUp : function(e){
        this.stopScroll();
    },
    onMouseOver : function(e,t){
        var el=Ext.fly(t),strip = el.parent($STRIP);
        if(el.hasClass(vts)&&!el.hasClass(sd)){
            if(el.hasClass(vtst))el.replaceClass(vtst,vtsto);
            else if(el.hasClass(vtsb))el.replaceClass(vtsb,vtsbo);
        } else if(el.hasClass(vtc)){
            el.addClass(vtbo);
        }
        if(strip){
        	/*
        	 * 鼠标划过时
        	 * tab添加tab-btn-over属性
        	 * 
        	 */
        	if(!strip.hasClass(ACTIVE)){
        		strip.addClass(vtbo);
        	}
            el = strip.child('div.'+vtc);
            if(el){
                var b = this.currentBtn;
                /*
                 * table-close始终显示
                 */
//                if(b)b.hide();
                if(b)b.show();
                this.currentBtn=el;
                el.show();
            }            
        }
        A.VTab.superclass.onMouseOver.call(this,e);
    },
    onMouseOut : function(e,t){
        var el=Ext.fly(t),strip = el.parent($STRIP);
        if(el.hasClass(vts)&&!el.hasClass(sd)){
            this.stopScroll();
            if(el.hasClass(vtsto))el.replaceClass(vtsto,vtst);
            else if((el.hasClass(vtsbo)))el.replaceClass(vtsbo,vtsb);
        }else if(el.hasClass(vtc)){
            el.removeClass([vtbo,vtbd]);
        }
        if(strip){
        	/*
        	 * 鼠标移开时
        	 * tab添加tab-btn-over属性
        	 * 
        	 */
        	if(strip.hasClass(vtbo)){
        		strip.removeClass(vtbo);
        	}
            el = strip.child('div.'+vtc);
            /*
             * table-close始终显示
             */
            if(el){
//            	el.hide();
                el.show();
            }  
        }
         A.VTab.superclass.onMouseOut.call(this,e);
    },
    showLoading : function(dom){
        A.Masker.mask(dom,_lang['tab.loading']);
//      dom.setStyle({'text-align':'center','line-height':5}).update(_lang['tab.loading']);
    },
    clearLoading : function(dom){
        A.Masker.unmask(dom);
//      dom.setStyle({'text-align':_N,'line-height':_N}).update(_N);
    },
    reloadTab : function(index,url){
        index = Ext.isEmpty(index) ? this.selectedIndex:index;
        var sf = this,tab=sf.getTab(index);
        if(!tab)return;
        if(url) sf.items[index].ref = url;
        sf.selectTab(index,true);
    },
    load : function(url,dom,index){
        var sf = this,body = Ext.get(dom);
        Ext.iterate(body.cmps,function(key,cmp){
            if(cmp.destroy){
                try{
                    cmp.destroy();
                }catch(e){
                    alert(ERROR + e)
                }
            }
        });
        body.update(_N);
        body.cmps={};
        if(body.dom.tagName.toLowerCase() == 'iframe'){
            var url = Ext.urlAppend(url,Ext.urlEncode(Ext.apply({},{_sph:body.getHeight(),_spw:body.getWidth()})));
            body.dom.src = url;
        }else {
            sf.showLoading(body);
            //TODO:错误信息
            Ext.Ajax.request({
    //          url: Ext.urlAppend(url,'_vw='+sf.width+'&_vh='+(sf.height-sf.head.getHeight())),
                url: url,
                failure: function(response, opts){
                    sf.clearLoading(body);
                    var msg=['<div style="text-align:center;line-height:30px">'];
                    switch(response.status){
                        case 404:
                            msg.push('<H2>',response.status , _lang['ajax.error'],'</H2>', _lang['ajax.error.404']+'"'+ response.statusText+'"');
                            break;
                        case 500:
                            msg.push('<H2>',response.status , _lang['ajax.error'],'</H2>',_lang['tab.internet.error'],'<a href="javascript:$au(\''+sf.id+'\').selectTab('+index+')">',_lang['tab.internet.refresh'],'</a>');
                            break;
                        case 0:
                            break;
                        default:
                            msg.push(_lang['ajax.error'], response.statusText);
                            break;
                    } 
                    msg.push('</div>');
                    body.update(msg.join(''));
                    body.loading = false;
                },
                success: function(response, options){
                    var res;
                    try {
                        res = Ext.decode(response.responseText);
                    }catch(e){}            
                    if(res && res.success == false){
                        if(res.error){
                            if(res.error.code  && res.error.code == 'session_expired'){
                                A.showErrorMessage(_lang['ajax.error'],  _lang['session.expired']);
                            }else{
                                A.manager.fireEvent('ajaxfailed', A.manager, options.url,options.para,res);
                                var st = res.error.stackTrace,
                                    em = res.error.message;
                                st = st ? st.replaceAll('\r\n','</br>') : _N;
                                A.showErrorMessage(_lang['window.error'], em?em+'</br>'+st:st,null,400,em && st==_N ? 150 : 250);
                            }
                        }
                        body.loading = false;
                        return;
                    }
                    var html = response.responseText;
    //              sf.intervalIds[index]=setInterval(function(){
    //                  if(!A.focusTab){
    //                      clearInterval(sf.intervalIds[index]);
    //                      A.focusTab=body;
    //                      try{
                                body.set({url:url});
                                body.update(html,true,function(){
                                    sf.clearLoading(body);
    //                              A.focusTab=null;
                                    body.loaded = true;
                                    body.loading = false;
                                    sf.fireEvent(EVT_SELECT, sf, index)
                                },body);
    //                      }catch(e){
    //                          A.focusTab=null;
    //                      }
    //                  }
    //              },10)
                }
            }); 
        }
    },
//    setWidth : function(w){
////    	debugger;
//        w = Math.max(w,2);
//        var sf = this;
//        if(sf.width==w)return;
//        A.VTab.superclass.setWidth.call(sf, w);
//        var body = sf.body,head = sf.head,stripwrap = sf.stripwrap,
//            scrollTop = sf.scrollTop,scrollBottom= sf.scrollBottom;
//        stripwrap.setWidth(w-38);
//        if(w-38<head.getWidth()){
//            scrollTop.setStyle({display:_N});
//            scrollBottom.setStyle({display:_N});
//            stripwrap.setStyle(PADDING_TOP,'1px');
//            var sl=stripwrap.getScroll().left,sw=stripwrap.getWidth(),hw=head.getWidth();
//            if(sl<=0)scrollTop.addClass(sd);
//            else scrollTop.removeClass(sd);
//            if(sl+sw>=hw){
//                if(!scrollBottom.hasClass(sd))scrollBottom.addClass(sd);
//                else stripwrap.scrollTo(TOP,hw-sw);
//            }else scrollBottom.removeClass(sd);
//        }else{
//            scrollTop.setStyle({display:NONE});
//            scrollBottom.setStyle({display:NONE});
//            stripwrap.setStyle(PADDING_TOP,'0').scrollTo(TOP,0);
//        }
//    },
//    setHeight : function(h){
////    	debugger;
//        h = Math.max(h,25);
//        if(this.height==h)return;
//        A.VTab.superclass.setHeight.call(this, h);
//        this.body.setHeight(h-26);
//    }
});
})($A);