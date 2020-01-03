function getContextPath(){
	var pathName = document.location.pathname;   
    var index = pathName.substr(1).indexOf("/");   
    var result = pathName.substr(0,index+1);   
    return result; 
};

$A.auForm=Ext.extend($A.Component,{
	constructor:function(config){
		var sf = this;
		sf.editors=[];
		$A.auForm.superclass.constructor.call(sf, config);
	},
	initComponent:function(config){
		var sf=this;
		$A.auForm.superclass.initComponent.call(sf, config);
		sf.width = 1*(sf.width||300);
        sf.height= 1*(sf.height||200);
        
        var wrap = sf.wrap = new Ext.Template(sf.getTemplate()).append(document.body, {
        	id:sf.id,
        	title:sf.title,
        	width:sf.width,
        	height:sf.height,
        	},true);
       sf.body=wrap.child('TBODY');
		
	},
	getTemplate : function() {
            return [
                //'<DIV>',
    	            '<TABLE class="" style="width:{width}px;height:{height}px;">',
    	            	'<TBODY class="form_body">',
    	            	'</TBODY>',
    	            '</TABLE>'
           // '</DIV>'
            ];
    },
    
	addEditor:function(editor,label){
		var sf=this;
		var tr=document.createElement('tr');
		var th=document.createElement('th');
		//th.className="layout-th";
		th.width="30px";
		th.height="30px";
		var tag=document.createElement('label');
		tag.style="text-align:right";
		tag.innerHTML=label;
		th.appendChild(tag);
		var td=document.createElement('td');
		td.className="layout-td-cell";
		td.height="30px";
		td.appendChild(editor.wrap.dom);
		tr.appendChild(th);
		tr.appendChild(td);
		sf.body.dom.appendChild(tr);
		sf.editors.push(editor);
	},
	getEditor:function(name){
		//同一个form有name相同的话就会出现问题
		var edi=null;
		Ext.each(this.editors,function(editor){
    		var ename = editor.name;
            if(name==ename){
            	edi=editor;
            }
    	});
		return edi;
	},
	getIndex:function(editor){
		var id = editor.id;
		var index=-1;
		for(var i=0;i<this.editors.length;i++){
			var editorId=this.editors[i].id;
			if(id==editorId){
				index=i;
				break;
			}
		}
		return index;
	},
	removeEditor:function(editor){
		var sf=this;
		var id=editor.id;
		var index=sf.getIndex(editor);
		sf.body.dom.deleteRow(index);
			Ext.each(sf.editors,function(edi){
				var eid = edi.id;
				if(id==eid){
					sf.editors.remove(editor);
				}
			});
	}
	
});


$A.auText = Ext.extend($A.Component,{
	autoselect : true,
	transformcharacter : true,
	readOnlyCss:'item-readOnly',
	requiredCss:'item-notBlank',
	constructor: function(config) {
		var sf=this;
		sf.type='auText';
		sf.name=config.name;
		sf.placeHolder=config.placeHolder;
		sf.bindTarget=config.bindTarget;
		sf.value=config.value;
		sf.id=config.id;
		sf.readonly=config.readonly;
		sf.required=config.required;
        this.context = config.context||'';
		$A.auText.superclass.constructor.call(sf, config);
    },
    initComponent : function(config){
    	var sf = this;
    	$A.auText.superclass.initComponent.call(sf, config);
    	sf.wrap=new Ext.Template(sf.getTemplate()).append(Ext.getBody(), {
        	id:sf.id,
        	placeHolder:sf.placeHolder,
        	width:sf.width,
        	height:sf.height,
        	value:sf.value,
        	name:sf.name
        	},true);
    	this.el = this.wrap.child('input[atype=auForm.auText]'); 
    	this.inputWrap = this.wrap.child('.item-input-wrap');
    	sf.initStatus();
    },
    initStatus : function(){
    	var sf = this;
    	//sf.clearInvalid();
    	sf.initRequired(sf.required);
    	sf.initReadOnly(sf.readonly);
    	//sf.initEditable(sf.editable);
    },
    initReadOnly : function(readonly){
    	var sf = this;
    	if(sf.currentReadonly == readonly)return;
    	sf.currentReadonly = sf.readonly = readonly;
    	sf.el.dom.readOnly = readonly;
    	sf.el[readonly?'addClass':'removeClass'](sf.readOnlyCss);
    },
    initRequired : function(required){
    	var sf = this;
    	if(sf.currentRequired == required)return;
		//sf.clearInvalid();    	
    	sf.currentRequired = sf.required = required;
    	sf.inputWrap[required?'addClass':'removeClass'](sf.requiredCss);
    },
    processListener: function(ou){
    	var sf = this;
    	$A.auText.superclass.processListener.call(sf, ou);
    	sf.el[ou]('change', sf.onChange, sf);
    	sf.el[ou]('focus', sf.onFocus,  sf);
		sf.el[ou]('blur', sf.onBlur,  sf);
    },
    getTemplate:function(){
    	 return [
    	        // '<table class="layout-table">',
    	         	'<tr>',
    	         		/*'<th class="layout-th" width="90">',
    	         			'<label style="text-align:right">{placeHolder}</label>',
    	         		'</th>',*/
    	         		'<td class="layout-td-cell">',
    	         			'<DIV class="item-tf item-wrap" style="width:{width}px;height:{height}px;{style}" id="{id}" >',
    	         				'<DIV class="item-input-wrap">',
    	         					'<INPUT  name="{name}" placeholder="{placeHolder}" tabindex="2" style="height:{height}px;width:{width}px;border-width:0px;text-indent:10px;" type="text"  atype="auForm.auText"  value="{value}" />',
    	         				'</DIV>',
    	         			'</DIV>',
    	         		'</td>',
    	         	'</tr>'
    	        // '</table>'
             ];
    },
    processMouseOverOut : function(ou){
    	var sf = this;
        sf.el[ou]("mouseover", sf.onMouseOver, sf)
        	[ou]("mouseout", sf.onMouseOut, sf);
    },
    initEvents : function(){
    	$A.auText.superclass.initEvents.call(this);
        this.addEvents(
        'change');
    },
	setWidth: function(w){
		this.el.setStyle("width",w+"px");
	},
	setHeight: function(h){
		this.el.setStyle("height",h+"px");
	},
	setId:function(id){
		this.el.id=id;
	},
    onChange : function(e){
    	var value=this.getRawValue();
    	//var field = this.bindTarget.getMeta().getField(name);
    	this.bindTarget.set(this.name,value);
    	
    	this.fireEvent('change', this);
    	},
    
    processValue : function(v){
    	return v;
    },
    onFocus : function(e){
    	var sf = this;
    	sf.autoselect && sf.select.defer(1,sf);
        if(!sf.hasFocus){
            sf.hasFocus = true;
            sf.startValue = sf.getValue();
            if(sf.emptytext && !sf.readonly){
	            sf.el.dom.value == sf.emptytext && sf.setRawValue('');
	            sf.el.removeClass(sf.emptyTextCss);
	        }
	        sf.el.addClass(sf.focusCss);
            sf.fireEvent("focus", sf);
        }
    },
    onBlur : function(e){
    	var sf = this;
    	if(sf.hasFocus){
	        sf.hasFocus = false;
	        !sf.readonly && sf.setValue(sf.processValue(sf.getRawValue()));
	        sf.el.removeClass(sf.focusCss);
	        sf.fireEvent("blur", sf);
    	}
    },
    getBindTarget:function(){
    	if(this.bindTarget){
    		return this.bindTarget;
    	}
    	else{
    		return '';
    	}
    },
    getEL:function(){
    	return this.el||'';
    },
/*  onMouseOver : function(e){
  $A.ToolTip.show(this.id, "测试");
  },
  onMouseOut : function(e){
  $A.ToolTip.hide();
  },*/

    setValue : function(v, silent){
    	var sf = this;
    	if(sf.emptytext && sf.el && !Ext.isEmpty(v)){
            sf.wrap.removeClass(sf.emptyTextCss);
        }
        sf.setRawValue(Ext.isEmpty(v)? '' : v);
    	$A.auText.superclass.setValue.call(sf,v, silent);
    },
    getRawValue : function(){
        var sf = this,v = sf.el.getValue();
        v = v === sf.emptytext || v === undefined?'':v;
        //判断是不是全角
       if(sf.isDbc(v)){
            v = sf.dbc2sbc(v);
        }
        return v;
    },
    
    select : function(start, end){
    	if(!this.hasFocus)return;
    	var v = this.getRawValue();
        if(v.length > 0){
            start = start === undefined ? 0 : start;
            end = end === undefined ? v.length : end;
            var d = this.el.dom;
            if(start === 0 && end === v.length && d.select){
            	d.select();
            }else{
	            if(d.setSelectionRange){  
	                d.setSelectionRange(start, end);
	            }else if(d.createTextRange){
	                var range = d.createTextRange();
	                range.moveStart("character", start);
	                range.moveEnd("character", end-v.length);
	                range.select();
	            }
            }
        }
// this.isSelect = true;
    },
    setRawValue : function(v){
    	var dom = this.el.dom;
        if(dom.value === (v = Ext.isEmpty(v)?'':v)) return;
        return dom.value = v;
    },
    reset : function(){
    	var sf = this;
    	sf.setValue(sf.originalValue);
        sf.clearInvalid();
        sf.applyEmptyText();
    },
    /**
	 * 组件获得焦点
	 */
    focus : function(){
    	this.el.dom.focus();
    	this.fireEvent('focus', this);
    },
    /**
	 * 组件失去焦点
	 */
    blur : function(){
    	this.el.blur();
    	this.fireEvent('blur', this);
    },
    isDbc : function(s){
        if(!this.transformcharacter) return false;
        var dbc = false;
        for(var i=0;i<s.length;i++){
            var c = s.charCodeAt(i);
            if((c>65248)||(c==12288)) {
                dbc = true
                break;
            }
        }
        return dbc;
    },
    dbc2sbc : function(str){
        var result = [];
        for(var i=0;i<str.length;i++){
            var code = str.charCodeAt(i);// 获取当前字符的unicode编码
            if (code >= 65281 && code <= 65373) {// 在这个unicode编码范围中的是所有的英文字母已及各种字符
                result.push(String.fromCharCode(code - 65248));// 把全角字符的unicode编码转换为对应半角字符的unicode码
            } else if (code == 12288){// 空格
                result.push(' ');
            } else {
                result.push(str.charAt(i));
            }
        }
        return result.join('');
    }
});

$A.auCombobox=Ext.extend($A.TriggerField,{
	maxHeight:202,
	blankOption:true,
	rendered:false,
	selectedClass:'item-comboBox-selected',	
	constructor: function(config) {
		var sf=this;
		sf.type='auCombobox';
		sf.name=config.name;
		sf.placeHolder=config.placeHolder;
        sf.options=config.options;
        sf.valueField=config.valueField;
        sf.displayField=config.displayField;
        sf.returnField=config.returnField;
        sf.width=config.width;
        sf.height=config.height;
        sf.ds=$au(sf.options);
        sf.mapping=config.mapping;
        sf.bindTarget=config.bindTarget;
		$A.auCombobox.superclass.constructor.call(sf, config);
    },
    initComponent : function(config){
    	var sf = this;
    	var wrap=sf.wrap=new Ext.Template(sf.getTemplate()).append(Ext.getBody(), {
        	id:sf.id,
        	placeHolder:sf.placeHolder,
        	width:sf.width,
        	height:sf.height,
        	value:sf.value,
        	name:sf.name
        	},true);
    	//sf.processListener('on');
    	$A.auCombobox.superclass.initComponent.call(sf, config);
    	if(sf.options){
    		var records=sf.ds.getAll();
    		sf.setOptions(sf.options);
    	}
    	sf.el = wrap.child('input[atype=field.input]'); 
    },
	initEvents:function(){
		this.addEvents(
		'select',
		'change');
		$A.auCombobox.superclass.initEvents.call(this);
	},
    getTemplate:function(){
    	return [
    	     //   '<table>',
    	         	'<tr>',
//    	         		'<th class="layout-th" width="90">',
//    	         			'<DIV style="text-align:right">{placeHolder}</DIV>',
//    	         		'</th>',
    	         		'<td class="layout-td-cell" style="padding:3px">',
    	        '<DIV class="item-tf item-wrap" style="width:{width}px;height:{height}px;{style}" id="{id}">',
    	        	'<DIV class="item-input-wrap item-trigger-wrap">',
    	        		'<DIV style="height:100%">',
    	        			'<INPUT name="{name}" placeholder="{placeHolder}" tabindex="2" style="text-indent:10px;width:{width}px;height:{height}px;line-height:{height}px;" class="item-textField" atype="field.input" value="{value}"/>',
    	        		'</DIV>',
    	        		'<DIV class="item-trigger item-comboButton" atype="triggerfield.trigger" hideFocus></DIV>',
    	        	'</DIV>',
    	        	'<DIV class="item-popup" atype="triggerfield.popup" style="width:{width}px;">',
    	        		'<DIV class="item-popup-content item-comboBox-view"></DIV>',
    	        	'</DIV>',
    	        '</DIV>',
    	        	'</td>',
    	        	'</tr>'
       	         //'</table>'
    	       ];
    },
    setOptions : function(options){
		var ds = $au(options);
		if(this.optionDataSet != ds){
			this.processDataSetListener('un');
			this.optionDataSet = ds;
			this.processDataSetListener('on');
			this.rendered = false;
			if(!Ext.isEmpty(this.value)) this.setValue(this.value, true)
		}
	},
	setValue: function(v, silent,vr){
        $A.auCombobox.superclass.setValue.call(this, v, silent);
        var r = this.bindTarget;
        var sf=this;
        if(r && !silent){
			var field = r.getMeta().getField(sf.name);
			if(field){
				var raw = this.getRawValue(),
					record = vr||sf.getRecordByDisplay(raw);
				Ext.each(sf.mapping,function(map){
					var vl = record ? record.get(map.from) : (this.fetchrecord===false?raw:'');
//    					var vl = record ? (record.get(map.from)||'') : '';
//    					if(vl!=''){
    					if(!Ext.isEmpty(vl,true)){
    						//避免render的时候发生update事件
//    						if(silent){
//                                r.data[map.to] = vl;
//    						}else{
    						    r.set(map.to,vl);						
//    						}
    					}else{
    						delete r.data[map.to];
    					}					
				},this);
			}
		}
	},
	 getRecordByDisplay: function(name){
	    	if(!this.optionDataSet)return null;
			var record = null;
			Ext.each(this.optionDataSet.getAll(),function(r){
				if(this.getRenderText(r) == name){
					record = r;
					return false;
				}
			},this);
			return record;
	    },
	getBindTarget:function(){
    	if(this.bindTarget){
    		return this.bindTarget;
    	}
    	else{
    		return '';
    	}
    },
	clearOptions : function(){
		   this.processDataSetListener('un');
		   this.optionDataSet = null;
	},
	processDataSetListener: function (ou) {
		var sf = this;
		var ds=sf.optionDataSet;
		if(ds){
            ds[ou]('load', sf.onLoad, this);
            ds[ou]('query', sf.onLoad, this);
           	ds[ou]('add', sf.onLoad, this);
            ds[ou]('update', sf.onLoad, this);
            ds[ou]('remove', sf.onLoad, this);
            ds[ou]('clear', sf.onLoad, this);
            ds[ou]('reject',sf.onLoad, this);
		}
	},
	processListener:function(ou){
		var sf=this;
		$A.auCombobox.superclass.processListener.call(sf, ou);
		sf.el[ou]('change',sf.onChange,this);
	},
	onChange:function(){
		sf.fireEvent('change',sf);
	},
	onLoad:function(){
		this.rendered=false;
		if(this.isExpanded()){
			this.expand();
		}
	},
	expand:function(){
		if(!this.optionDataSet)return;
		if(this.rendered===false) this.doQuery();
        $A.auCombobox.superclass.expand.call(this);
		var v = this.getValue();
		this.currentIndex = this.getIndex(v);
		if (!Ext.isEmpty(v)) {				
			this.selectItem(this.currentIndex,true);
		}		
	},
	doQuery : function(q) {		
		var ds = this.optionDataSet;
        if(ds)
		if(Ext.isEmpty(q)){
			ds.clearFilter();
		}else{
			var reg = new RegExp(q.replace(/[+?*.^$\[\](){}\\|]/g,function(v){
					return '\\'+v;
				}),'i'),
				field = this.displayField;
	        ds.filter(function(r){
	        	return reg.test(r.get(field));
	        },this);
		}
		//值过滤先不添加
		this.onRender();	
	},
	onRender:function(){	
        if(!this.view){
			this.view=this.popupContent.update('<ul></ul>').child('ul')
				.on('click', this.onViewClick,this)
				.on('mousemove',this.onViewMove,this);
        }
        if(this.optionDataSet){
			this.initList();
			this.rendered = true;
		}       
		this.correctViewSize();
	},
	initList: function(){
		this.currentIndex = this.selectedIndex = null;
		var ds = this.optionDataSet,v = this.view;
		if(ds.loading == true){
			v.update('<li tabIndex="-1">'+_lang['combobox.loading']+'</li>');
		}else{
			var sb = [],n=0;
			if(this.blankoption){
				sb.push('<li tabIndex="0">&nbsp;</li>');
				n=1;
			}
			Ext.each(ds.getAll(),function(d,i){
                sb.push('<li');
                if(this.tipfield&&d.get(this.tipfield)) {
                    sb.push(' title="',d.get(this.tipfield),'"')
                }
				sb.push(' tabIndex="',i+n,'">',this.getRenderText(d),'</li>');
			},this);
				v.update(sb.join(''));			
		}
	},
	getRenderText : function(record){
        var rder = $A.getRenderer(this.displayrenderer);
        if(rder){
            return rder(this,record);
        }else{
            return record.get(this.displayField);
        }
	},
	correctViewSize: function(){
		var widthArray = [],
			mw = this.wrap.getWidth();
		Ext.each(this.view.dom.childNodes,function(li){
			mw = Math.max(mw,$A.TextMetrics.measure(li,li.innerHTML).width)||mw;
		});
		var lh = Math.max(20,Math.min(this.popupContent.child('ul').getHeight()+2,this.maxHeight)); 
		this.popup.setWidth(mw).setHeight(lh);
	},
	getIndex:function(v){
		var df=this.displayField;
		return Ext.each(this.optionDataSet.getAll(),function(d){
			if(d.data[df]==v){				
				return false;
			}
		});
	},
	onViewClick:function(e,t){
		if(t.tagName!='LI'){
		    return;
		}		
		this.onSelect(t);
		this.collapse();
	},	
	onSelect:function(target){
		var index = target.tabIndex;
		if(index==-1)return;
		var sf = this,value=null,display='',record=null;
		if(sf.blankoption){
			index--;	
		}
		if(index!=-1){
			record = sf.optionDataSet.getAt(index);
			value = record.get(sf.valueField);
			display = sf.getRenderText(record);//record.get(this.displayfield);
		}
		sf.setValue(display,null,record);
		
		sf.bindTarget.set(sf.returnField,value);
		sf.bindTarget.set(sf.name,display);
		sf.fireEvent('select',sf, value, display, record);
        
	},
	collapse:function(){
		$A.auCombobox.superclass.collapse.call(this);
		if(!Ext.isEmpty(this.currentIndex))
			Ext.fly(this.getNode(this.currentIndex)).removeClass(this.selectedClass);
	},
	selectItem:function(index,focus){
		if(Ext.isEmpty(index)||index==-1){
			return;
		}	
		var node = this.getNode(index),
			sindex = this.selectedIndex,
			cls = this.selectedClass;			
		if(node && node.tabIndex!=sindex){
			if(!Ext.isEmpty(sindex)){							
				Ext.fly(this.getNode(sindex)).removeClass(cls);
			}
			this.selectedIndex=node.tabIndex;	
			if(focus)this.focusRow(this.selectedIndex);
			Ext.fly(node).addClass(cls);					
		}			
	},
	getNode:function(index){		
		return this.view.query('li[tabindex!=-1]')[index];
	},
	focusRow : function(row){
        var r = 20,
            ub = this.popupContent,
            stop = ub.getScroll().top,
            h = ub.getHeight(),
            sh = ub.dom.scrollWidth > ub.dom.clientWidth? 16 : 0;
        if(row*r<stop){
            ub.scrollTo('top',row*r-1)
        }else if((row+1)*r>(stop+h-sh)){//this.ub.dom.scrollHeight
            ub.scrollTo('top', (row+1)*r-h + sh);
        }
    },
    onViewMove:function(e,t){
        this.selectItem(t.tabIndex);
	}
});
$A.auLov=Ext.extend($A.TextField,{
	WIDTH : 'width',
	PX : 'px',
	SELECTED_CLS : 'autocomplete-selected',
	readOnlyCss:'item-readOnly',
	constructor:function(config){
		var sf = this;
		sf.type='auLov';
        sf.isWinOpen = false;
        sf.fetching = false;
        sf.fetchremote = true;
        sf.maxHeight = 240;
        sf.lovService=config.lovService;
        sf.mapping=config.mapping;
        sf.width=config.width;
        sf.height=config.height;
        sf.placeHolder=config.placeHolder;
        sf.id=config.id;
        sf.value=config.value;
        sf.name=config.name;
        var binder=new Object();
        binder.ds=config.ds;
        binder.name=config.name;
        sf.binder=binder;
        sf.record=config.record;
        $A.auLov.superclass.constructor.call(sf, config); 
	},
	initComponent:function(config){
		var sf = this;
    	var wrap=sf.wrap=new Ext.Template(sf.getTemplate()).append(Ext.getBody(), {
        	id:sf.id,
        	placeHolder:sf.placeHolder,
        	width:sf.width,
        	height:sf.height,
        	value:sf.value,
        	name:sf.name
        	},true);
    	$A.auLov.superclass.initComponent.call(sf, config);
    	sf.trigger = sf.wrap.child('div[atype=triggerfield.trigger]');
    	this.inputWrap = this.wrap.child('.item-input-wrap');
    	this.el=this.wrap.child('input[atype=field.input]'); 
	},
	
	 initEvents : function(){
	        this.addEvents("beforetriggerclick",
	        				'beforecommit',
	        				'commit',
	        				'fetching',
	        				'fetched');
	        $A.auLov.superclass.initEvents.call(this);
	 },
	getTemplate:function(){
		return[
		       '<DIV class="item-wrap item-tf"  style="width:{width}px;height:{height}px;{style}" id="{id}">',
		       		'<DIV class="item-input-wrap item-trigger-wrap">',
		       			'<DIV style="height:100%">',
		       				'<INPUT name="{name}" placeholder="{placeHolder}" value="{value}" tabindex="2" style="text-indent:10px;height:{height}px;width:{width}px;line-height:{height}px;${fontstyle}" class="item-textField" atype="field.input" autocomplete="off"/>',
		       			'</DIV>',
		       			'<DIV class="item-trigger item-lovButton" atype="triggerfield.trigger" hideFocus></DIV>',
		       		'</DIV>',
		       	'</DIV>'
		       ];
	},
	processListener: function(ou){
    	var sf = this,view = sf.autocompleteview;
        $A.auLov.superclass.processListener.call(sf,ou);
        sf.trigger[ou]('mousedown',sf.onWrapFocus,sf, {preventDefault:true})
        	[ou]('click',sf.onTriggerClick, sf, {preventDefault:true});
    },
    onWrapFocus : function(e,t){
    	var sf = this;
    	e.stopEvent();//阻止事件冒泡
		sf.focus.defer(Ext.isIE?1:0,sf);
    },
    getBindTarget:function(){
    	if(this.record){
    		return this.record;
    	}
    	else{
    		return '';
    	}
    },
    setReadOnly:function(bool){
    	this.readOnly=bool;
    	this.initReadOnly(bool);
    },
    initReadOnly : function(readonly){
    	var sf = this;
    	if(sf.currentReadonly == readonly)return;
    	sf.currentReadonly = sf.readonly = readonly;
    	sf.el.dom.readOnly = readonly;
    	sf.el[readonly?'addClass':'removeClass'](sf.readOnlyCss);
    },
    onTriggerClick : function(e){
    	e.stopEvent();
    	var sf = this,view = sf.autocompleteview;
    	if(sf.fireEvent("beforetriggerclick",sf)){
    		sf.showLovWindow();
    	}
    },
    showLovWindow : function(){
    	var sf = this;
        if(sf.fetching||sf.isWinOpen||sf.readonly) return;
        
        var v = sf.getRawValue(),
        	lovurl = sf.lovurl,
    		svc = sf.lovService,ctx = getContextPath()+'/',
    		w = sf.lovwidth||700,
			url;
        sf.blur();
        if(!Ext.isEmpty(lovurl)){
        	//urlAppend添加字符串到查询url,urlEncode将js对象序列化成查询字符串
            url = Ext.urlAppend(lovurl,Ext.urlEncode(sf.getFieldPara()));
        }else if(!Ext.isEmpty(svc)){
            url = ctx + 'sys_lov.screen?url='+encodeURIComponent(Ext.urlAppend(ctx + 'autocrud/'+svc+'/query',Ext.urlEncode(sf.getLovPara())))+'&service='+svc;
    	}
        if(url) {
	        sf.isWinOpen = true;
            sf.win = new Aurora.Window({
            	title:sf.title||'Lov', 
            	url:Ext.urlAppend(url,"lovid="+sf.id+"&key="+encodeURIComponent(v)+"&gridheight="+(sf.lovgridheight||350)+"&innerwidth="+(w-30)+"&lovautoquery="+(Ext.isEmpty(sf.lovautoquery) ? 'true' : sf.lovautoquery) +"&lovlabelwidth="+(sf.lovlabelwidth||75)+"&lovpagesize="+(sf.lovpagesize||'')), 
            	height:sf.lovheight||400,
            	width:w});
            sf.win.on('close',sf.onWinClose,sf);
        }
    },
    onWinClose: function(){
    	var sf = this;
        sf.isWinOpen = false;
        sf.win = null;
        if(!Ext.isIE6 && !Ext.isIE7){
            sf.focus();
        }else{
        	(function(){sf.focus()}).defer(10);
        }
    },
    getLovPara : function(){
    	return this.getPara();
    },
    isEventFromComponent:function(el){
    	var popup = this.autocompleteview;
    	return $A.auLov.superclass.isEventFromComponent.call(this,el) || (popup && popup.wrap.contains(el));
    },
    onViewSelect : function(r){
		var sf = this;
		if(!r){
			if(sf.autocompleteview.isLoaded)
				sf.fetchRecord();
		}else{
			sf.setValue('');
			//sf.commit(this.record);
		}
		sf.focus();
	},
    fetchRecord : function(){
    	var sf = this;
        
    },
    onViewMove:function(e,t){
        this.selectItem((Ext.fly(t).findParent('tr[tabindex]')||t).tabIndex);        
	},
	selectItem:function(index){
		if(Ext.isEmpty(index)||index < -1){
			return;
		}	
		var sf = this,node = sf.getNode(index),selectedIndex = sf.selectedIndex;	
		if(node && node.tabIndex!=selectedIndex){
			if(!Ext.isEmpty(selectedIndex)){							
				Ext.fly(sf.getNode(selectedIndex)).removeClass(SELECTED_CLS);
			}
			sf.selectedIndex=node.tabIndex;			
			Ext.fly(node).addClass(SELECTED_CLS);					
		}			
	},
	getNode:function(index){
		var nodes = this.fetchSingleWindow.body.query('tr[tabindex!=-2]'),l = nodes.length;
		if(index >= l) index =  index % l;
		else if (index < 0) index = l + index % l;
		return nodes[index];
	},
    onFetchFailed: function(res){
    	var sf = this;
        sf.fetching = false;
        $A.SideBar.enable = $A.slideBarEnable;
        sf.fireEvent('fetched',sf);
    },    
    createListView : function(datas,binder,isRecord){
    	var sb = ['<table class="autocomplete" cellspacing="0" cellpadding="2">'],
    		displayFields = binder.ds.getField(binder.name).getPropertity('displayFields');
        if(displayFields && displayFields.length){
        	sb.push('<tr tabIndex="-2" class="autocomplete-head">');
        	Ext.each(displayFields,function(field){
        		sb.push('<td>',field.prompt,'</td>');
        	});
			sb.push('</tr>');
        }
		for(var i=0,l=datas.length;i<l;i++){
			var d = datas[i];
			sb.push('<tr tabIndex="',i,'"',i%2==1?' class="autocomplete-row-alt"':_N,'>',this.getRenderText(isRecord?d:new $A.Record(d),displayFields),'</tr>');	//sf.litp.applyTemplate(d)等数据源明确以后再修改		
		}
		sb.push('</table>');
		return sb;
    },
    getRenderText : function(record,displayFields){
        var sf = this,
        	rder = A.getRenderer(sf.autocompleterenderer),
        	text = [],
        	fn = function(t){
        		var v = record.get(t);
        		text.push('<td>',Ext.isEmpty(v)?'&#160;':v,'</td>');
        	};
        if(rder){
            text.push(rder(sf,record));
        }else if(displayFields){
        	Ext.each(displayFields,function(field){
        		fn(field.name);
        	});
        }else{
        	fn(sf.autocompletefield)
        }
		return text.join(_N);
	},
    commit:function(r,lr,mapping){
        var sf = this,record = lr || sf.record;
        if(sf.fireEvent('beforecommit', sf, record, r)!==false){
	        if(sf.win) sf.win.close();
	        if(record && r){
	        	Ext.each(mapping || sf.mapping,function(map){
	        		var from = r.get(map.from);
	                record.set(map.to,Ext.isEmpty(from)?'':from);
	                var v=record.get(sf.name);
	                if(v){
	                	//sf.record.set(sf.name,v);
	                	$A.auLov.superclass.setValue.call(sf,v);
	                }
	        	});
	        }
	        sf.fireEvent('commit', sf, record, r);
        }
    },
    onBlur : function(){
    	var sf = this,view = sf.autocompleteview;
    	if(!view || !view.isShow){
    		$A.auLov.superclass.onBlur.call(sf);
    	}
    },
    setLovService:function(lovService){
    	this.lovService=lovService;
    	//var record=this.record;
    	//console.log(this);
    	//console.log(record.getMeta().getField(name));
    	//record.getMeta().getField(name).setLovService(lovService);
    	//$A.auLov.superclass.setLovService.call(lovService);
    },
    onKeyDown : function(e){
        if(this.isWinOpen)return;       
        var sf = this,keyCode = e.keyCode,
        	view = sf.autocompleteview;
        if(!view || !view.isShow){
        	if(!e.ctrlKey && keyCode == 40){
        		e.stopEvent();
        		sf.showLovWindow();
        	}
            $A.auLov.superclass.onKeyDown.call(sf,e);
        }
    },
    onChange : function(e){
    	var sf = this,view = sf.autocompleteview;
    	$A.auLov.superclass.onChange.call(sf);
    	if(!view || !view.isShow)
			sf.fetchRecord();
    }
});

$A.auCheckBox=Ext.extend($A.Component,{
	checkedCss:'item-ckb-c',
	uncheckedCss:'item-ckb-u',
	readonyCheckedCss:'item-ckb-readonly-c',
	readonlyUncheckedCss:'item-ckb-readonly-u',
	constructor: function(config){
		var sf=this;
		sf.type='auCheckBox';
		sf.checked = config.checked || false;
		sf.readonly = config.readonly || false;
		sf.label=config.label;
		sf.bindTarget=config.bindTarget;
		sf.name=config.name;
		sf.value=config.value;
		$A.auCheckBox.superclass.constructor.call(this,config);
	},
	initComponent:function(config){
		this.checkedvalue = 'Y';
		this.uncheckedvalue = 'N';
		var sf = this;
    	var wrap=sf.wrap=new Ext.Template(sf.getTemplate()).append(Ext.getBody(), {
        	id:sf.id,
        	width:sf.width,
        	height:sf.height,
        	value:sf.value,
        	label:sf.label,
        	name:sf.name
        	},true);
		$A.auCheckBox.superclass.initComponent.call(this, config);
		this.el=this.wrap.child('div[atype=checkbox]');
		this.setValue(this.checked);
		this.bindTarget.set(this.name,this.checked);
	},
	getTemplate:function(){
		return[
		       '<div style="height:{height}px;width:{width}px;{style}" id="{id}">',
		       		'<DIV name="{name}" tabIndex="{tabindex}" value="{value}" hideFocus class="item-ckb" style="margin-top:{margin-top}px" atype="checkbox">',
		       			/*'<div class="item-outline"></div>',*/
		       		'</DIV>',
		       		//'<label style="margin-left:3px;line-height:{height}px">{label}</label>',
		       	'</div>'
		       ];
	},
	processListener: function(ou){
    	this.wrap
    		[ou]('mousedown',this.onMouseDown,this)
    		[ou]('click',this.onClick,this);
    	this.el[ou]('keydown',this.onKeyDown,this);
    	this.el[ou]('focus',this.onFocus,this);
    	this.el[ou]('blur',this.onBlur,this);
    	this.el[ou]('change',this.onBlur,this);
    },
    onMouseDown : function(e){
    	var sf = this;
    	sf.hasFocus && e.stopEvent();
    	sf.focus.defer(Ext.isIE?1:0,sf);
    },
    getBindTarget:function(){
    	if(this.bindTarget){
    		return this.bindTarget;
    	}
    	else{
    		return '';
    	}
    },
    onClick: function(event){
		if(!this.readonly){
			this.checked = this.checked ? false : true;	
			this.setValue(this.checked);
			this.bindTarget.set(this.name,this.checked);
			this.fireEvent('click', this, this.checked);
			this.focus();
		}
	},
	onKeyDown : function(e){
    	var keyCode = e.keyCode;
    	if(keyCode == 32){
    		this.onClick.call(this,e);
    		e.stopEvent();
    	}
    },
    focus : function(){
		this.el.focus();
	},
	blur : function(){
		this.el.blur();		
	},
	onFocus : function(){
		var sf = this;
		if(!sf.hasFocus){
	        sf.hasFocus = true;
			sf.el.addClass(sf.focusCss);
			sf.fireEvent('focus',sf);
		}
	},
	onBlur : function(){
		var sf = this;
		if(sf.hasFocus){
	        sf.hasFocus = false;
			sf.el.removeClass(sf.focusCss);
			sf.fireEvent('blur',sf);
		}
	},
	onChange:function(){
		var sf=this;
		sf.bindTarget.set(sf.name,this.getValue());
		sf.fireEvent('change',sf);
	},
	initEvents:function(){
		$A.auCheckBox.superclass.initEvents.call(this);  	
		this.addEvents(
		'click');    
	},
	initStatus:function(){
		this.el.removeClass(this.checkedCss);
		this.el.removeClass(this.uncheckedCss);
		this.el.removeClass(this.readonyCheckedCss);
		this.el.removeClass(this.readonlyUncheckedCss);
		if (this.readonly) {				
			this.el.addClass(this.checked ? this.readonyCheckedCss : this.readonlyUncheckedCss);			
		}else{
			this.el.addClass(this.checked ? this.checkedCss : this.uncheckedCss);
		}		
	},
	setValue:function(v, silent){
		if(typeof(v)==='boolean'){
			this.checked=v?true:false;			
		}else{
			this.checked = (''+v == ''+this.checkedvalue)
		}
		this.initStatus();
		var value = this.checked==true ? this.checkedvalue : this.uncheckedvalue;		
		$A.auCheckBox.superclass.setValue.call(this,value, silent);
	},
	getValue : function(){
    	var v= this.value;
		v=(v === null || v === undefined ? '' : v);
		return v;
    }
});

$A.auTledit = Ext.extend($A.Tledit,{
    constructor: function(config) {
        var sf=this;
        sf.type='auTledit';
        sf.name=config.name;
        sf.placeHolder=config.placeHolder;
        sf.bindTarget=config.bindTarget;
        sf.record=config.record;
        sf.value=config.value;
        sf.id=config.id;
        sf.width=config.width;
        sf.height=config.height;
        sf.readonly=config.readonly;
        sf.required=config.required;
        sf.descidfield = config.descIdField;
        var binder=new Object();
        binder.ds=config.record.ds;
        binder.name=config.name;
        sf.binder=binder;
        this.context = config.context= getContextPath()+'/';
        $A.auTledit.superclass.constructor.call(sf, config);
    },
    initComponent : function(config){
        var sf = this;
        sf.wrap=new Ext.Template(sf.getTemplate()).append(Ext.getBody(), {
            id:sf.id,
            placeHolder:sf.placeHolder,
            width:sf.width,
            height:sf.height,
            value:sf.value,
            name:sf.name
        },true);
        $A.auTledit.superclass.initComponent.call(sf, config);
        this.el = this.wrap.child('input[atype=field.input]');
        this.inputWrap = this.wrap.child('.item-input-wrap');
        sf.initStatus();
    },
    getTemplate:function(){
        return [
            '<tr>',
            '<td class="layout-td-cell">',
            '<DIV class="item-wrap item-tf" isgrid="false" style="width:{width}px;height:{height}px;{style}" id="{id}">',
            '<DIV class="item-input-wrap item-trigger-wrap">',
            '<DIV style="height:100%">',
            '<INPUT placeholder="{placeholder}" tabindex="2" style="text-indent:4px;height:{height}px;line-height:{height}px;" class="item-textField" atype="field.input" value="{value}"/>',
            '</DIV>',
            '<DIV class="item-trigger item-tleditButton" atype="triggerfield.trigger"></DIV>',
            '</DIV>',
            '</DIV>',
            '</td>',
            '</tr>'
        ];
    }
});
