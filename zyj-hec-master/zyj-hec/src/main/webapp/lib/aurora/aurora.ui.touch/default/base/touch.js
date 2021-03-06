var Touch = {};
(function(T,undefined){
    var cmpCache = {},
        hasTouch = 'ontouchstart' in window,
        START_EV = hasTouch ? 'touchstart' : 'mousedown',
        MOVE_EV = hasTouch ? 'touchmove' : 'mousemove',
        END_EV = hasTouch ? 'touchend' : 'mouseup',
        CANCEL_EV = hasTouch ? 'touchcancel' : 'mouseup',
        PX = 'px',_ = '#',
        funpro = Function.prototype,
        re = /\{([\w-]+)\}/g,
        slice = Array.prototype.slice;
if(!funpro.bind){
    funpro.bind = function(context){
        if (arguments.length < 2 && arguments[0] === undefined) return this;
        var __method = this, args = slice.call(arguments, 1);
        return function() {
            var a = args.concat(slice.call(arguments,0));
            return __method.apply(context, a);
        }
    }
}
$.applyTemplate = function(html,values){
	if($.isArray(html)){
		html = html.join('');
	}
	return html.replace(re, function(m, name){
    	return values[name] !== undefined ? values[name] : "";
    });
}
$.isEmpty = function(v, allowBlank){
    return v === null || v === undefined || (($.isArray(v) && !v.length)) || (!allowBlank ? v === '' : false);
}
T.get = function(id){
    return cmpCache[id]||$(_+id);
}
T.showMessage = function(){
	var el,body,list=[],showing=false;
	function show(msg){
		if(showing){
			list.push(msg);
			return;
		}
		if(!body){
			body = $(document.body);
			el = $("<div class='message'></div>").appendTo(body);
		}
		showing = true;
		el.html(msg)
			.show()
			.css({
				'left':(body.width() - el.width())/2 + PX,
				'top':(document.documentElement.clientHeight- el.height())/2 + PX
			})
			.animate({
				opacity:1
			},500,'ease-out',function(){
				setTimeout(function(){
					el.animate({
						opacity:0					
					},500,'ease-in',function(){
						el.hide();
						showing = false;
						if(msg = list.shift()){
							show(msg);
						}
					});
				},3000)
			});
	}
	return show;
}();
T.Masker = function(){
    var container = {};
    return {
        mask : function(el,msg){
            var el = $(el),
                w = el.width(),
                h = el.height(),
                spanHtml = msg?'<span>'+msg+'</span>':'',
                masker = container[el.selector],sp;
            if(masker){
                sp = masker.children('span');
                if(msg){
                    if(!sp || !sp.length){
                        sp = $(spanHtml).appendTo(masker);
                    }else
                        sp.html(msg)
                }else if(sp){
                    sp.remove();
                }
            }else {
                var isBody = !el.parent('body').length,
                    wrap = isBody?el:el.parent(),
                    offset = el.offset(),
                    opt = {'z-index': el.css('z-index') == 'auto' ? 0 : el.css('z-index') + 1},
                    p = ['<div class="touch-mask"  style="position: absolute;-webkit-transform:translate3d(0,0,0)"><div unselectable="on" style="-webkit-transform:translate3d(0,0,0)"></div>'];
                if(msg)p.push(spanHtml);
                p.push('</div>');
                $.extend(opt,isBody?{
                    'top':0,'bottom':0,'left':0,'right':0
                }:{
                    'top':offset.top + PX,
                    'left':offset.left + PX,
                    'width':w + PX,
                    'height':h + PX
                })
                masker = $(p.join('')).appendTo(wrap)
                    .css(opt);
                sp = masker.children('span');
                container[el.selector] = masker;
            }
            sp.css({'left':(w - sp.width())/2 + PX,'top':masker.height()*2/3 - 11 + PX});
        },
        unmask : function(el){
            var el = $(el),
                masker = container[el.selector];
            if(masker) {
                masker.remove();
                container[el.selector] = null;
                delete container[el.selector];
            }
        }
    }
}()
T.Ajax = function(config){
    cmpCache[config.id] = this;
    delete config.id;
    this.data = null;
	this.options = {
		type: 'POST',
		dataType: 'json',
		timeout: T.Ajax.timeout,
		parameters : {}
    }
    $.extend(this.options,config);
}
T.Ajax.timeout = 0;
$.extend(T.Ajax.prototype,{
	setData : function(data,method){
		this.data = data;
		if(method)
		for(var i=0,length = data.length;i<length;i++){
			data[i]['_status'] = method;
		}
		return this;
	},
    addParameter : function(key,value){
        if($.isObject(key)){
            for(var c in key){
                this.addParameter(c,key[c]);
            }
        }else
            this.options.parameters[key]={'value':value};
        return this;
    },
    removeParameter : function(key){
        delete this.options.parameters[key];
        return this;
    },
    setUrl : function(url){
        this.options.url = url;
        return this;
    },
    request : function(successCall,errorCall,scope){
        var data = {},p = this.options.parameters;
        if(successCall)this.options.success = successCall.bind(scope||window);
        if(errorCall)this.options.error = errorCall.bind(scope||window);
        for(var key in p){
            var v = p[key],bind = v.bind,type= v.datatype;
            data[key] = bind?$(_+bind).val():v.value;
            switch(v.datatype){
            	case 'int':
            	case 'float':
            	case 'java.lang.Long':data[key] = Number(data[key]);break;
            	case 'boolean':data[key] =  data[key]=="true";
            }
            if($.isEmpty(data[key])){
            	delete data[key];
            }
        }
        data = this.data?$.extend({'parameter':this.data},data):{
            'parameter': data
        }
        this.options.data = {
            _request_data: JSON.stringify(data)
        }
        this.xhr = $.ajax(this.options);
        return this;
    }
})

T.DateField = function(config){
	this.canPage = true;
	this.viewSize = 6;
    cmpCache[config.id] = this;
    $.extend(this,config);
    var now = new Date(),
        year = config.year || now.getFullYear(),
        month = config.month || now.getMonth() + 1;
    this.defaultDate = new Date(year,month - 1);
    this.wrap = $(_ + config.id);
    this.initComponent();
    this.processListener('on');
    this.buildViews();
    this.onClick = function(e){
    	var el = $(e.target),
        d = el.attr('_date')||((el = el.parents('[_date]')).length && el.attr('_date'));
        if(d)this.wrap.trigger('itemclick',[new Date(Number(d)),el[0]]);
    }.bind(this)
}
$.extend(T.DateField.prototype,{
    initComponent : function(){
        this.head = this.wrap.children('.datefield-head');
        this.content = this.wrap.children('.datefield-scroller')
            .width(this.wrap.width() * 6);
        this.title = this.head.children('.datefield-date').children('div');
        this.preBtn = this.head.children('button.pre');
        this.nextBtn = this.head.children('button.next');
        var sf = this,isUnbind = false,isClick = false,time;
        this.iscroll = new iScroll(this.id, {
            snap: true,
            momentum: false,
            hScrollbar: false,
            onScrollStart : function(){
                sf.wrap.trigger('scrollstart'); 
            },
            onBeforeScrollStart : function(e){
                isClick = true;
            },
            onBeforeScrollEnd : function(e){
                if(this.moved){
                    isUnbind = true;
                    this._unbind(START_EV);
                }else if(isClick){
                    sf.onClick(e);
                }
                isClick = false;
            },
            onScrollEnd: function(){
                if(isUnbind){
                    this._bind(START_EV);
                    isUnbind = false;
                }
                sf.reViews();
                sf.reflashHead();
            }
         });
    },
    processListener : function(ou){
        if(this.listeners)this.wrap[ou](this.listeners);
        $(window).on('resize',this.resize.bind(this));
    },
    resize : function(){
    	var width = this.wrap.width(),
    		views = this.views,sf=this;
    	this.content.width(this.viewSize * width);
    	for(var date in views){
    		views[date].el.width(width);
    	}
    	setTimeout(function(){sf.iscroll.scrollToPage(sf.iscroll.currPageX,null,0)},10);
    },
    reViews : function(){
        var iscroll = this.iscroll,
            currPageX = iscroll.currPageX,
            date = this.date = new Date(this.months[currPageX]),
            month = date.getMonth() + 1,
            year = date.getFullYear(),
            views = this.views,
            pdate = new Date(year,month-3);
        if(!views[pdate]){
            views[pdate] = new T.DateField.View(pdate,this,true);
            views[new Date(year,month+this.viewSize-3)].destroy();
            iscroll.scrollToPage(currPageX + 1,null,0)
        }else{
            var ndate = new Date(year,month+1);
            if(!views[ndate]){
                views[ndate] = new T.DateField.View(ndate,this);
                views[new Date(year,month-this.viewSize+1)].destroy();
                iscroll.scrollToPage(currPageX - 1,null,0)
            }
        }
    },
    clearViews : function(){
        if(this.views){
            for(var c in this.views){
                this.views[c].destroy() 
            }
        }
        this.views = {};
        this.months = [];
    },
    buildViews : function(d){
        this.clearViews();
        d = d || this.defaultDate;
        for(var now = d.getMonth(),half = this.viewSize / 2,m = now - half,max = now + half;m < max;m++){
            var date = new Date(d.getFullYear(),m);
            this.views[date] = new T.DateField.View(date,this);
        }
        this.goToDate(d);
    },
    reflashHead : function(){
        this.wrap.trigger('refresh',this.date);
    },
    preMonth : function(){
        this.goToDate(new Date(this.date.getFullYear(),this.date.getMonth() - 1),500);
    },
    nextMonth : function(){
        this.goToDate(new Date(this.date.getFullYear(),this.date.getMonth() + 1),500);
    },
    goToDate : function(date,duration){
    	if(!this.canPage)return;
        date = new Date(date.getFullYear(),date.getMonth());
        if(date.getTime() == (this.date && this.date.getTime()))return;
        if(this.views[date]){
            this.iscroll.scrollToPage(this.months.indexOf(date.getTime()),null,duration||0);
        }else this.buildViews(date);
    },
    getCurrentViewDatas : function(){
		return this.views[this.date].data;
	},
    redraw : function(data){
        var view = this.views[this.date];
        view.data = data;
        view.isAjax = !!data;
        view.draw();
    },
    setCanPage : function(canPage){
    	var sf = this;
    	this.canPage = canPage;
    	this.iscroll[canPage?'_bind':'_unbind'](START_EV);
    	this.wrap[canPage?'unbind':'bind'](END_EV,this.onClick);
    },
    isAjax : function(date){
        var view = this.views[date];
        return !!view && view.isAjax;
    }
})

T.DateField.View = function(date,options,insertFirst){
    options.months[insertFirst?'unshift':'push'](date.getTime());
    this.date = date;
    this.options = options;
    this.el = $(this.tpl.join('')).width(options.wrap.width())[insertFirst?'prependTo':'appendTo'](options.content);
    this.body = this.el.children('tbody')
    this.draw();
}
$.extend(T.DateField.View.prototype,{
    tpl : ['<table class="datefield-view" cellspacing="0" cellpadding="0"><thead><tr height="20',PX,'"><th>日</th><th>一</th><th>二</th><th>三</th><th>四</th><th>五</th><th>六</th></tr></thead><tbody></tbody></table>'],
    offset : function(){
        return this.el[0].offsetLeft;
    },
    draw : function(){
        //用来保存日期列表
        var arr = [],date = this.date,year=date.getFullYear(),month=date.getMonth()+1,that = this.options
//      ,hour=date.getHours(),minute=date.getMinutes(),second=date.getSeconds();
        //用当月第一天在一周中的日期值作为当月离第一天的天数,用上个月的最后天数补齐
        for(var i = 1, firstDay = new Date(year, month - 1, 1).getDay(),lastDay = new Date(year, month - 1, 0).getDate(); i <= firstDay; i++){ 
            arr.push(
//          (this.enablebesidedays=="both"||this.enablebesidedays=="pre")?new Date(year, month - 2, lastDay-firstDay+i,hour,minute,second):
            null);
        }
        //用当月最后一天在一个月中的日期值作为当月的天数
        for(var i = 1, monthDay = new Date(year, month, 0).getDate(); i <= monthDay; i++){ 
            arr.push(new Date(year, month - 1, i
//          ,hour,minute,second
            )); 
        }
        //用下个月的前几天补齐6行
        for(var i=1, monthDay = new Date(year, month, 0).getDay(),besideDays=43-arr.length;i<besideDays;i++){
            arr.push(
//          (this.enablebesidedays=="both"||this.enablebesidedays=="next")?new Date(year, month, i,hour,minute,second):
            null);
        }
        //先清空内容再插入
        this.body.text('');
        //插入日期
        var k=0;
        while(arr.length){
            //每个星期插入一个tr
            var row = $(this.body[0].insertRow(-1));
            row.attr({'r_index':k,'vAlign':this.options.valign});
            if(this.options.valign)row.attr({'vAlign':this.options.valign});
			//if(k%2==0)row.addClass('week-alt');
            k++;
            //每个星期有7天
            for(var i = 1; i <= 7; i++){
                var d = arr.shift();
                if(d !== undefined){
                    var cell = $(row[0].insertCell(-1)); 
                    if(d){
                        cell.attr({'c_index':i-1});
//                      cell.addClass(date.getMonth()==d.getMonth()?"item-day":"item-day item-day-besides");
                        var weekday = d.getDay(),
                            thisdate = d.getDate(),
                            renderer = that.dayrenderer;
                        if(weekday == 0 || weekday == 6)thisdate = '<span style="color:red">'+thisdate+'</span>';
                        cell.html(renderer && renderer.call(that,cell,d,thisdate,this.data)|| thisdate);
//                      cell.update(this.renderCell(cell,d,d.getDate(),month)||d.getDate());
//                      if(cell.disabled){
//                          cell.attr({'_date':'0'});
//                          cell.addClass("item-day-disabled");
//                      }else {
                            cell.attr({'_date':(''+d.getTime())});
//                          if(this.format)cell.set({'title':d.format(this.format)})
//                      }
                        //判断是否今日
//                      if(this.isSame(d, new Date())) cell.addClass("onToday");
                        //判断是否选择日期
//                      if(this.selectDay && this.isSame(d, this.selectDay))this.onSelectDay(cell);
                    }else cell.html('&#160;');
                }
            }
        }
    },
    destroy : function(){
        this.el.remove();
        this.options.months.splice(this.options.months.indexOf(this.date.getTime()),1);
        delete this.options.views[this.date];
    }
});
T.Uploader = function(config){
	var sf = this;
	sf.errors={};
	sf.success=[];
	cmpCache[config.id] = sf;
	$.extend(sf,config);
	sf.wrap = $(_ + sf.id);
	sf.initComponent();
	sf.processListener();
}
$.extend(T.Uploader.prototype,{
	html : ['<li id="{id}" class="upload-item" style="width:{size}px;height:{size}px" title="{title}">',
		'<div class="upload-item-content" style="line-height:{size}px;font-size:{fontsize}px">',
			'<span class="upload-item-progress"></span>',
			'<div class="upload-item-mask">',
				'<div class="upload-item-mask-content" style="height:{size}px;">',
					'<span class="upload-item-progress"></span>',
				'</div>',
			'</div>',
			'<a class="upload-item-btn"></a>',
		'</div>',
		'<div class="text-overflow">{title}</div>',
	'</li>'],
	initComponent:function(){
		var sf = this,
			wrap = sf.wrap,
			list = sf.list = wrap.children('.upload-list');
		sf.el = wrap.children('input[type=file]');
		sf.submitBtn = list.find('button.btn');
		sf.content = list.children('ul');
	},
	processListener : function(){
		var sf = this;
		sf.wrap.on('change',sf.getFiles.bind(sf));
		sf.submitBtn.on('click',sf.upload.bind(sf));
	},
	getFiles : function(e){
		var sf = this;
		sf.files = sf.filterFiles($.map(sf.totalFiles = (e.target.files || e.dataTransfer.files),function(file){
			return file;
		}));
		if(sf.files.length){
			sf.showList();
		}
	},
	filterFiles : function(files){
		var filter = this.filter;
		if(filter){
			files = filter(files);
		}
		return files;
	},
	showList : function(){
		var sf = this,
			size = 100;
		sf.list.show().animate({opacity:1},300,'ease-in',function(){
			$.each(sf.files,function(index,file){
				var name = file.name,
					id = sf.id + '_item' + index;
				file.index = index;
				sf.content.append($.applyTemplate(sf.html,{
					id : id,
					title : name,
					size : size,
					fontsize : size/5
				}));
				
				$('#'+id).find('a.upload-item-btn').on('click',sf.close.bind(sf));
				var reader = new FileReader();
	            reader.onloadend = function () {
	            	var imgSrc = new Image(),
	            		src;
	            	if(file.type.match(/image/)){
	            		src = reader.result;
	            	}else{
	            		src = $('#'+id).find('.upload-item-content').css('background-image').match(/url\(([^\)]*)\)/)[1];
	            	}
	            	imgSrc.src = src;
	            	imgSrc.onload = function(){
		            	var canvas=document.createElement('canvas'),
			            	w = imgSrc.width,
							h = imgSrc.height,
							context = canvas.getContext('2d'),
							wsize = w,hsize = h;
						if(w>size || h >size){
							//缩小尺寸
							if(w>h){
								wsize = size;
								hsize = size * h / w;
							}else{
								hsize = size;
								wsize = size * w / h;
							}
						}
						canvas.width=wsize;
						canvas.height=hsize;
						context.drawImage(imgSrc,0,0,w,h,0,0,wsize,hsize);
						$('#'+id+' .upload-item-content').css({
		            		'background-image':'url('+canvas.toDataURL()+')'
		            	}).animate({opacity:1},300,'ease-in');
						var imgd = context.getImageData(0,0,wsize,hsize),
							pix = imgd.data;
						//反色处理
						for(var i=0,n=pix.length;i<n;i+=4){
							pix[i] = 255 - pix[i];//红
							pix[i+1] = 255 - pix[i+1];//绿
							pix[i+2] = 255 - pix[i+2];//蓝
							//pix[i+3] = pix[i+3];//alpha
						}
						context.putImageData(imgd,0,0);
						$('#'+id+' .upload-item-mask-content').css({
		            		'background-image':'url('+canvas.toDataURL()+')'
		            	});
	            	}
	       	 	}
        		reader.readAsDataURL(file, "UTF-8");
			});
		});
	},
	close : function(e){
		var btn = $(e.target);
		if(!btn.hasClass('success')){
			var	sf = this,
				li = btn.parents('li'),
				index = li.attr('id').match(/item(\d+)/)[1],
				file = sf.totalFiles[index],
				files = sf.files;
			files.splice(files.indexOf(file),1);
			li.remove();
			if(!files.length){
				sf.totalFiles=[];
				sf.list.animate({opacity:0},300,'ease-out',function(){
					sf.list.hide();
					sf.el.val('');
				});
			}
		}
	},
	uploadComplete : function(file,e){
		var sf = this,
			xhr = e.target,
			files = sf.files;
		if(xhr.status == 200) {
			if (xhr.readyState == 4) {
				var response = eval("(" + xhr.responseText+ ')');
				if (xhr.status == 200) {
					sf.onSuccess(file, response);
					files.splice(files.indexOf(file),1);
					if (!files.length) {
						//全部完毕
						sf.onComplete();	
					}
				} else {
					sf.onFailure(file, response);		
				}
			}
		}
	},
	uploadError : function(file,e){
		alert('error')
	},
	uploadAbort : function(file,e){
		alert('abort')
	},
	upload : function(){
		var sf = this,files = sf.files;
		if(sf.uploading)return;
		sf.uploading = true;
		$.each(files,function(index,file){
			var xhr = new XMLHttpRequest();
			if (xhr.upload) {
				// 上传中
					xhr.timeout = -1;
					xhr.open("POST", sf.uploadurl, true);
					xhr.setRequestHeader("X_FILENAME", encodeURIComponent(file.name));
					xhr.upload.addEventListener("progress", function(e) {
						sf.onProgress(file, e.loaded, e.total);
					}, false);
					// 文件上传成功或是失败
					xhr.addEventListener('load',sf.uploadComplete.bind(sf,file));
					xhr.addEventListener('error',sf.uploadError.bind(sf,file));
					xhr.addEventListener('abort',sf.uploadAbort.bind(sf,file));
					// 开始上传
					var fd = new FormData(); 
		            fd.append('fileToUpload', file); 
		            fd.append("pkvalue", sf.pkvalue); 
		            fd.append("source_type", sf.sourcetype); 
					xhr.send(fd);
			}
		});
	},
	onProgress : function(file,loaded, total){
		var sf = this,
			percent = Math.floor((loaded-1)/total*100)+'%',
			wrap = $('#'+sf.id+'_item' + file.index);
		wrap.find('.upload-item-progress').text(percent);
		wrap.find('.upload-item-mask').show().css({bottom:percent});
	},
	onSuccess : function(file,response){
		var sf = this,
			wrap = $('#'+sf.id+'_item' + file.index);
		wrap.find('.upload-item-mask').hide().css({bottom:0});
		if(response.success == false){
			wrap.find('.upload-item-progress').text('');
			sf.errors[file.name]=[file,response.error];
		}else{
			wrap.find('.upload-item-progress').text('100%');
			wrap.find('.upload-item-btn').addClass('success');
			sf.success.push({file:file,id:response});
		}
	},
	onFailure : function(file,response){
		var sf = this,
			wrap = $('#'+sf.id+'_item' + file.index);
		wrap.find('.upload-item-progress').text('');
		wrap.find('.upload-item-mask').hide().css({bottom:0});
		sf.errors[file.name]=[file,response.error];
	},
	onComplete : function(){
		var sf = this,
			message =[];
		sf.uploading = false;
		for(var name in sf.errors){
			message.push('<p>File:',name,' [',sf.errors[name][1].message,']</p>');
			sf.files.push(sf.errors[name][0]);
		}
		if(message.length){
			T.showMessage(message.join(''));
			sf.errors={};
		}else{
			sf.el.val('');
			T.showMessage('上传成功');
			sf.list.animate({opacity:0},300,'ease-out',function(){
				sf.list.hide();
			});
			sf.content.html('');
			sf.wrap.trigger('uploadsuccess',sf.success);
		}
	}
});
T.SwitchButton = function(config){
    cmpCache[config.id] = this;
    this.options = {
        on: '开',
        off: '关',
        onvalue: 'Y',
        offvalue: 'N',
        defaultstatus:'off'
    }
    var opt = this.options;
    $.extend(opt , config);
    this.wrap = $(_ + config.id);
    opt.defaultvalue = opt.defaultstatus == 'off'? opt.offvalue : opt.onvalue;
    this.initComponent();
    this.val(config.value || opt.defaultvalue)
    this.processListener();
}
$.extend(T.SwitchButton.prototype,{
    initComponent : function(){
        this.btn = this.wrap.children('.switch-btn');
        this.rightside = this.wrap.width() - this.btn.width() - 2;
    },
    processListener : function(){
        var touch = {},
            _start = function(e){
                touch.x = (hasTouch?e.touches[0]:e).pageX;
                touch.startX = this.x;
                $(document).on(MOVE_EV,_move);
                $(document).on(END_EV,_end);
                e.preventDefault();
                e.stopPropagation();
            }.bind(this),
            _move = function(e){
                touch.moved = true;
                var pageX = (hasTouch?e.touches[0]:e).pageX,
                    newX = this.x + pageX - touch.x,
                    r = this.rightside;
                if(newX < 0) newX = 0;  
                else if(newX > r) newX = r;
                else touch.x = pageX;
                this._pos(newX)
            }.bind(this),
            _end = function(e){
                $(document).off(MOVE_EV,_move);
                $(document).off(END_EV,_end);
                if(this.x != touch.startX || !touch.moved)
                    this.trigger();
                touch = {};
            }.bind(this);
        this.wrap.on(START_EV,_start)
    },
    _pos : function(x,time){
        this.x = $.isEmpty(x)?this.x||0:x;
        this.btn.animate({'translate3d':[this.x+PX,0,0].join(',')},time||0);
    },
    on : function(){
        this.val(this.options.onvalue,100);
    },
    off : function(){
        this.val(this.options.offvalue,100);
    },
    trigger : function(){
        this[this.wrap.val() == this.options.onvalue?'off':'on']();
    },
    val : function(v,time){
    	if(v === undefined)return this.wrap.val();
    	var wrap = this.wrap,
    		ov = wrap.val();
    	if(ov != v){
        	wrap.val(v).trigger('change',[v,ov]);
        	this._pos(v == this.options.onvalue ? this.rightside : 0,time);
    	}
    }
});
/** Touch.Lov **/
T.Lov = function(config){
	var	id = config.id,
		sf = cmpCache[id] = this,
		opt = sf.options= {
    	},
    	ax = sf.ajax = T.get(config.bind),
    	url = ax.options.url,
    	prefix = url.indexOf('?') == -1 ? '?' : '&',
    	mapping = config.mapping,
    	lovFields = config.lovFields,
    	parameters = ax.options.parameters;
	sf.currentPage = 1;
    sf.total = 0;
    sf.pageSize = config.size||10;
    
    ax.options.url = url + prefix + '_fetchall=false&_autocount=true&pagenum=1&pagesize='+sf.pageSize;
    $.extend(opt , config);
    if(!lovFields && mapping){
    	config.lovFields = lovFields = [];
	    $.each(mapping,function(map){
	    	lovFields.push({
	    		name:map.from,
	    		forquery:true,
	    		fordisplay:true
    		});
	    });
    }
    var	layout = sf.layout = $(['<div class="lov-list">',
			'<div class="touch-screen-body touch-shadow">',
				'<div class="touch-tabpanel" id="lov-tabpanel-',id,'">',
					'<div class="touch-box-vertical">',
						'<div class="touch-tabpanel-body">',
							'<div class="touch-tabpanel-card-container">',
								'<div class="touch-tabpanel-card-item">',
									'<div id="lov-search-scroll-wraper-',id,'" class="touch-scroll-panel">',
										'<div class="touch-scroll-container" style="padding:1em">',
											'<div class="touch-fieldset">',
												'<div class="touch-box-vertical">',
													'<div class="touch-fieldset-title">查询条件</div>',
													'<div class="touch-fieldset-body">',
														'<div class="touch-fieldset-body-inner">',
															$.map(lovFields,function(field){
																var name = field.name,
																	_id = id + '-lovfield-' +name;
																parameters[name] = {bind:_id}
																return ['<div class="touch-fieldset-field">',
																	'<div class="touch-fieldset-field-label" style="width:40%"><span>',field.prompt,'</span></div>',
																	'<div class="touch-fieldset-field-input-outer">',
																		'<div class="touch-fieldset-field-input">',
																			'<input type="text" id="',_id,'" class="input"  autocapitalize="off">',
																		'</div>',
																	'</div>',
																'</div>'].join('');
															}).join(''),
										    			'</div>',
									    			'</div>',
								    			'</div>',
							    			'</div>',
						    			'</div>',
									'</div>',
								'</div>',
								'<div class="touch-tabpanel-card-item touch-tabpanel-card-item-hide">',
									'<div id="lov-scroll-wraper-',id,'" class="touch-scroll-panel">',
						    			'<div class="touch-scroll-container">',
						    			'</div>',
									'</div>',
								'</div>',
							'</div>',
						'</div>',
						'<div class="touch-tabpanel-tabbar touch-tabpanel-tabbar-bottom">',
							'<div class="touch-box-horizontal touch-tabpanel-strip-container">',
								'<div class="touch-tabpanel-strip touch-tabpanel-strip-active" unselectable="on" onselectstart="return false;">查询</div>',
								'<div class="touch-tabpanel-strip" unselectable="on" onselectstart="return false;">结果</div>',
							'</div>',
						'</div>',
					'</div>',
				'</div>',
			'</div>',
		'</div>'].join('')).insertBefore($(document.body).children()[0]).click(function(e){
			if(e.target == this){
				$(this).hide();
			}
		});
	sf.container = layout.find('#lov-scroll-wraper-'+id+' .touch-scroll-container');
	sf.search_container = layout.find('#lov-search-scroll-wraper-'+id+' .touch-scroll-container');
	sf.scroller = new T.ScrollPanel({id:'lov-scroll-wraper-'+id});
	sf.search_scroller = new T.ScrollPanel({id:'lov-search-scroll-wraper-'+id});
	tabpanel = sf.tabpanel = new T.Tab({id:'lov-tabpanel-'+id,
		listeners:{
			select : function(e,index){
				if(index == 1){
					sf.query();
				}
			}
		}
	});
    sf.wrap = $(_ + config.id);
    sf.initComponent();
    sf.val(config.value || '');
    sf.processListener();
}
$.extend(T.Lov.prototype,{
    initComponent : function(){
    	this.el = this.wrap.find('input');
    },
    processListener : function(){
        this.wrap.on('click',this.showList.bind(this));
    },
    processSelectEvent : function(){
		var sf = this,
			moved = false,
            _start = function(e){
                $(document).on(MOVE_EV,_move);
                $(document).on(END_EV,_end);
            },
            _move = function(e){
                moved = true;
            },
            _end = function(e){
                $(document).off(MOVE_EV,_move);
                $(document).off(END_EV,_end);
                if(!moved){
                	sf.onClick(e)
                }
                moved = false;
            };
        sf.listWrap.on(START_EV,_start)
	},
	query : function(){
		var sf = this;
    	sf.container.html('正在查询...');
    	sf.data = [];
    	sf.ajax.request(function(data, status,xhr){
            if(data && data.success){
                sf.total = data.result.totalCount || 0;
                sf.totalPage = Math.ceil(sf.total/sf.pageSize) || 0;
                if(data.result.totalCount > 0 ){
                    var ls = ['<table cellspacing="0" cellpadding="0" border="0">'],
                    	lovFields = sf.options.lovFields;
                    	records = sf.data = [].concat(data.result.record),
                    	rc = window[sf.renderer];
                    for(var i=0,len=records.length;i<len;i++){
                        var record = records[i];
                        ls.push('<tr class="lov-list-item" dataindex="'+i+'">');
                        if(rc){
                            ls[ls.length] = rc(record); 
                        }else{
                       		for(var j=0,len2=lovFields.length;j<len2;j++){
					    		var field = lovFields[j];
					    		if(field.fordisplay){
	                            	ls.push('<td class="lov-list-item-field">'+record[field.name]+'</td>');
					    		}
					    	}
                        }
                        ls.push('</tr>');
                    }
                    ls.push('</table>');
                    
                    sf.container.html(ls.join(''));
                    sf.scroller.refresh();
                    sf.listWrap = sf.container.find('table');
                    sf.processSelectEvent();
                }else {
                    sf.container.html('未找到任何数据!');
                }
                if(sf.options.callback) window[sf.options.callback]();
        	}
    	});
	},
    showList : function(){
    	var sf = this;
    	sf.layout.show();
    	if(sf.options.autoquery){
    		sf.tabpanel.selectTab(1);
    	}
    },
    val :function(v){
    	if(v === undefined)return this.wrap.val();
    	var wrap = this.wrap,
    		ov = wrap.val();
    	if(ov != v){
        	wrap.val(v).trigger('change',[v,ov]);
        	this.el.val(v);
    	}
    },
    onClick : function(e){
    	var li =$(e.target);
		if(li.is('tr[dataindex]')||(li = li.parent('tr[dataindex]'))&&li.length){
			this.commit(this.data[li.attr('dataindex')]);
		}
    },
    commit : function(record){
    	var mapping = this.options.mapping;
    	for(var i=0,len=mapping.length;i<len;i++){
    		var map = mapping[i];
    		T.get(map.to).val(record[map.from]);
    	}
    	this.layout.hide();
    }
});
/** Touch.Tab**/
T.Tab = function(config){
	var id = this.id = config.id,
    	sf = cmpCache[id] = this,
		opt = sf.options= {
		};
	sf.wrap = $(_+id);
	$.extend(opt , config);
	sf.initComponent();
    sf.processListener();
}
$.extend(T.Tab.prototype,{
	initComponent:function(){
		var sf = this,
			wrap = sf.wrap;
			tabbar = sf.tabbar = wrap.find('.touch-tabpanel-tabbar');
		sf.bodys = wrap.find('.touch-tabpanel-card-item');
		sf.strips = tabbar.find('.touch-tabpanel-strip');
	},
	processListener:function(){
		var sf = this;
		sf.tabbar.on('click',sf.onStripClick.bind(sf));
		if(sf.options.listeners){
			sf.wrap.on(sf.options.listeners);
		}
	},
	onStripClick:function(e){
		var t = $(e.target);
		if(t.is('.touch-tabpanel-strip')&&!t.hasClass('touch-tabpanel-strip-active')){
			this.selectTab(this.strips.indexOf(e.target));
		}
	},
	selectTab:function(i){
		this.bodys.each(function(index,body){
			(body = $(body)).addClass('touch-tabpanel-card-item-hide');
			if(index == i){
				body.removeClass('touch-tabpanel-card-item-hide');
			}
		});
		this.strips.each(function(index,strip){
			(strip = $(strip)).removeClass('touch-tabpanel-strip-active');
			if(index == i){
				strip.addClass('touch-tabpanel-strip-active');
			}
		});
		this.wrap.trigger('select',[i]);
	}
});
/** Touch.List **/
T.List = function(config){
    var bid  = config.bind,
    	id = this.id = config.id,
    	sf = cmpCache[id] = this;
    sf.wrap = $(_+id);
    sf.renderer = config.renderer;
    sf.total = 0;
    sf.pageSize = config.size||10;
    sf.currentPage = 1;
    sf.selected = [];
    sf.selectable = config.selectable || false;
    var ax = T.get(bid);
    sf.ajax = ax;
    $(document).on('ajaxSuccess', function(e, xhr, options){
        if(xhr == ax.xhr){
            var data = JSON.parse(xhr.responseText);
            if(data && data.success){
                sf.total = data.result.totalCount || 0;
                sf.totalPage = Math.ceil(sf.total/sf.pageSize) || 0;
                if(data.result.totalCount > 0 ){
                    var ls = ['<ul>'],
                    	records = sf.data = [].concat(data.result.record),
                    	rc = window[sf.renderer];
                    for(var i=0;i<records.length;i++){
                        var record = records[i];
                        ls[ls.length] = '<li dataindex="'+i+'">';
                        if(rc){
                            ls[ls.length] = rc(record); 
                        }else{
                            ls[ls.length] = record;
                        }
                        ls[ls.length] = '</li>'
                    }
                    ls[ls.length] = '</ul>'
                    if(config.showpagebar){
                        var bar = ['<table width="100%" border="0" cellspacing="3">',
                                '<tr>',
                                    '<td width="40%">',
                                        '<button type="button" id="'+id+'_pre" class="btn gray" style="float:right;font-size:16px;height:30px;">上一页</button>',
                                    '</td>',
                                    '<td width="20%" id="'+id+'_info" style="text-align:center;font-size:16px;">'+sf.currentPage+'/'+sf.totalPage+'</td>',
                                    '<td width="40%">',                                                
                                        '<button type="button" id="'+id+'_next" class="btn gray" style="float:right;font-size:16px;height:30px;">下一页</button>',
                                    '</td>',
                                '</tr>',
                            '</table>'];
                            ls[ls.length] = bar.join('');
                    }
                    
                    sf.wrap.html(ls.join(''));
                    $('#'+id+'_pre').on("click",function(){Touch.get(id).pre()});
                    $('#'+id+'_next').on("click",function(){Touch.get(id).next()});
                    sf.processSelectEvent();
                }else {
                    sf.wrap.html('未找到任何数据!');
                }
                if(config.callback) window[config.callback]();
                
            }
        }
    })
    
    sf.url = sf.ajax.options.url;
    sf.prefix = sf.url.indexOf('?') == -1 ? '?' : '&' 
    sf.ajax.options.url = sf.url + sf.prefix + 'pagenum=' + sf.currentPage + '&pagesize='+sf.pageSize;
    if(config.autoquery == true){
        sf.ajax.request(); 
    }
}
$.extend(T.List.prototype,{
	processSelectEvent : function(){
		var sf = this,
			moved = false,
            _start = function(e){
                $(document).on(MOVE_EV,_move);
                $(document).on(END_EV,_end);
            },
            _move = function(e){
                moved = true;
            },
            _end = function(e){
                $(document).off(MOVE_EV,_move);
                $(document).off(END_EV,_end);
                if(!moved){
                	sf.onClick(e)
                }
                moved = false;
            };
        sf.wrap.on(START_EV,_start)
	},
	onClick: function(e,t){
		var sf = this;
        if(!sf.selectable)return;
		var li =$(e.target).parents('li[dataindex]');
		if(li){
			var data = sf.data[li.attr('dataindex')];
			if(sf.selected.indexOf(data) != -1){
				sf.unselect(data,li);
			}else{
				sf.select(data,li);
			}
		}
	},
	selectAll : function(){
		var sf = this;
		if(!sf.selectable)return;
		sf.selected = [].concat(sf.data);
		sf.wrap.find('li').addClass('selected');
	},
	unSelectAll : function(){
		var sf = this;
		if(!sf.selectable)return;
		sf.selected = [];
		sf.wrap.find('li').removeClass('selected');
	},
	select : function(data,li){
		if(!this.selectable)return;
		this.selected.push(data);
		li.addClass('selected')
	},
	unselect : function(data,li){
		var sf = this;
		if(!sf.selectable)return;
		sf.selected.splice(sf.selected.indexOf(data),1);
		li.removeClass('selected')
	},
	getSelected : function(){
		return this.selected;
	},
    loading: function(){
        $('#'+this.id).html('正在查询...');
    },
    query:function(page){
    	var sf = this,
    		ax = sf.ajax;
        sf.loading();
        page = sf.currentPage = page||1;
        ax.options.url = sf.url + sf.prefix + 'pagenum=' + page + '&pagesize='+sf.pageSize;
        ax.request();    
    },
    pre:function(){
    	var sf = this;
        if(sf.currentPage -1 <=0)return;
        sf.query(sf.currentPage-1);
    },
    next:function(){
    	var sf = this;
        if(sf.currentPage+1> sf.totalPage)return;
        sf.query(sf.currentPage+1);
    }
})

Date.prototype.format = function(format)
{
    var o =
    {
        "M+" : this.getMonth()+1, //month
        "d+" : this.getDate(),    //day
        "h+" : this.getHours(),   //hour
        "m+" : this.getMinutes(), //minute
        "s+" : this.getSeconds(), //second
        "q+" : Math.floor((this.getMonth()+3)/3),  //quarter
        "S" : this.getMilliseconds() //millisecond
    }
    if(/(y+)/.test(format))
    format=format.replace(RegExp.$1,(this.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)
    if(new RegExp("("+ k +")").test(format))
    format = format.replace(RegExp.$1,RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
    return format;
}
/**Touch.ScrollPanel**/
T.ScrollPanel = function(config){
	var id = config.id;
	cmpCache[id] = this;
	return new iScroll(id,{
		onBeforeScrollStart : function(){}
	});
}
})(Touch);