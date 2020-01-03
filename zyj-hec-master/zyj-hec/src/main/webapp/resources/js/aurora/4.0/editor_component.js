$AE = AuroraEditor = {
    version: '1.0',
    revision: '$Rev:$'
};

$AE.jsPlumbInstance = null;

function Dictionary (){
	this.cache = {};
	this.put = function (id, cmp) {
        			if (!this.cache)
        				this.cache = {};
        			if (this.cache[id] != null) {
        				alert("错误: ID为' " + id + " '的组件已经存在!");
        				return;
        			}
        			this.cache[id] = cmp;
    			};
    this.getAll = function () {
        			return this.cache;
    			};
    this.remove = function (id) {
        			var cmp = this.cache[id];
        			if (cmp) {
        				delete this.cache[id];
        			}
    			};
    this.get = function (id) {
        		if (!this.cache)
        			return null;
        		return this.cache[id];
    		};
    			
}
$AE.CmpManager = new Dictionary();

$AE.recycleComponent = new Dictionary();

$AE.get = function (id) {
    return $AE.CmpManager.get(id);
};

$AE.lang=document.cookie.match(new RegExp("(^| )" + "LANG" + "=([^;]*)(;|$)"))?document.cookie.match(new RegExp("(^| )" + "LANG" + "=([^;]*)(;|$)"))[2]:'zh_CN';

$AE.Component = Ext.extend(Ext.util.Observable, {
    constructor: function (config) {
        $AE.Component.superclass.constructor.call(this);
        config.title = config.title ? config.title : 'Title';
        config.id = config.id ? config.id : ('ae-' + Ext.id());
        this.config = config;
        this.id = config.id;
        this.dataRecord = config.dataRecord;
        this.domEl = null;
        $AE.CmpManager.put(this.id, this);
        this.initComponent(config);
        this.initEvents();
    },
    initComponent: function (config) {
        config = config || {};
        Ext.apply(this, config);
        this.wrap = Ext.get(this.id);
        if(this.wrap)
        	this.el=this.wrap.child('input[atype=field.input]');
        if (this.listeners) {
            this.on(this.listeners);
        }
    },
    initEvents: function () {
        this.addEvents(
            /**
             * @event focus 获取焦点事件
             * @param {Component}
             *            this 当前组件.
             */
            'focus',
            /**
             * @event blur 失去焦点事件
             * @param {Component}
             *            this 当前组件.
             */
            'blur',
            /**
             * @event change 组件值改变事件.
             * @param {Component}
             *            this 当前组件.
             * @param {Object}
             *            value 新的值.
             * @param {Object}
             *            oldValue 旧的值.
             */
            'change',
            /**
             * @event valid 组件验证事件.
             * @param {Component}
             *            this 当前组件.
             * @param {Aurora.Record}
             *            record record对象.
             * @param {String}
             *            name 对象绑定的Name.
             * @param {Boolean}
             *            isValid 验证是否通过.
             */
            'valid',
            /**
             * @event mouseover 鼠标经过组件事件.
             * @param {Component}
             *            this 当前组件.
             * @param {EventObject}
             *            e 鼠标事件对象.
             */
            'mouseover',
            /**
             * @event mouseout 鼠标离开组件事件.
             * @param {Component}
             *            this 当前组件.
             * @param {EventObject}
             *            e 鼠标事件对象.
             */
            'mouseout',
            'hover');
        this.processListener('on');
    },
    processListener: function (ou) {
        var sf = this;
        if (sf.el) {
            sf.el[ou]("focus", sf.onFocus, sf)[ou]("blur", sf.onBlur, sf)[ou]("change", sf.onChange, sf)[ou]("keyup",
                sf.onKeyUp, sf)[ou]("keydown", sf.onKeyDown, sf)[ou]("keypress", sf.onKeyPress, sf)[ou]("hover",sf.onHover,sf);
        }
    },
    onFocus: function (e) {
        this.fireEvent('focus', this, e);
    },
    onBlur: function (e) {
        this.fireEvent('blur', this, e);
    },
    onChange: function (e) {
        this.fireEvent('change', this, e);
    },
    onKeyUp: function (e) {
        this.fireEvent('keyup', this, e);
    },
    onKeyDown: function (e) {
        this.fireEvent('keydown', this, e);
    },
    onKeyPress: function (e) {
        this.fireEvent('keypress', this, e);
    },
    onHover :function(e){//组件添加鼠标经过事件
    	var sf=this;
    	
    		$('#'+this.id).hover(function(){
    			$('#'+sf.id+'-element-title').removeClass('un-show-element');
    			if(sf.template_flag=='N' || bpm_type!='page'){//扩展性？
    				$('#'+sf.id+'-delete-button').removeClass('un-show-element');
    			}
    		},function(){
    			$('#'+sf.id+'-element-title').addClass('un-show-element');
    			if(sf.template_flag=='N' || bpm_type!='page'){
    				$('#'+sf.id+'-delete-button').addClass('un-show-element');
    			}
    		});
    },
    getDom: function (config) {
        /***********************************************************************
         * 默认DOM结构: 空
         */
        var domTemplate = [
            '', '', ''];
        var tplt = new Ext.XTemplate(domTemplate);
        return tplt.applyTemplate(config);
    },
    getDomEl: function () {
        if (!this.domEl || this.domEl.length == 0) {
            this.domEl = $('#' + this.id);
        }
        return this.domEl;
    },
    getRecycleDom:function(type){//回收站组件样式
    	var sf = this;
    	var cmptype='';
    	if(sf.layoutType){cmptype='layout';}
    	else if(sf.tagType){cmptype='input';}
    	else{cmptype='btn';}
    	
        var domTemplate = [
            '<div class="recycle-component" id="{id}" cmptype="'+cmptype+'"'+'aetype="'+type+'">',
            	'<img id="{id}-recycle-icon" class="recycle-icon" src="../../resources/images/aurora/4.0/screen_editor/_'+type.toLowerCase()+'.png" >',
            	'    <div class="recycle-delete-button" id="{id}-recycle-delete-button"></div>',
            	'    <div class="recycle-undo-button" id="{id}-recycle-undo-button"></div>',
            	'<div class="recycle-prompt">',
                this.id,
                '</div>',
            '</div>'
        ];
        var tplt = new Ext.XTemplate(domTemplate);
        return tplt.applyTemplate(sf);
    	
    },
    setDataRecord:function(record){
    	var fields=this.dataRecord.ds.fields;
    	for(var key in fields){
    		//if(record.get(key)){
    			this.dataRecord.set(key,record.get(key));
    		//}
    	}
    },
    getDataRecord:function(){
    	return this.dataRecord;
    },
    getCmpType:function(){
    	return this.tagType?this.tagType:this.layoutType;
    }
});

$AE.Box = Ext.extend($AE.Component, {
    constructor: function (config) {
        config.column = config.column || 1;
        this.column = config.column;
        this.title = config.title;
        this.layoutSequence=config.layoutSequence;
        this.bindTarget = config.bindTarget;
        this.layoutCode = config.layoutCode;
        this.layoutType= config.layoutType||'box';
        this.layoutTypeDesc= config.layoutTypeDesc||'盒装布局';
        this.parentLayout = config.parentLayout;
        $AE.Box.superclass.constructor.call(this, config);
    },
    getDom: function (config) {
        var sf = this;
        var domTemplate = [
            '<table border="0" class="layout-table layout-component" id="{id}" bindTarget="{bindTarget}" cellpadding="0" cellspacing="0">',
            '    <tr><td><div class="element-title un-show-element" id="{id}-element-title" elementType="Box">{[$AE.lang=="ZHS" ? \'盒布局\' :\'Box\']}</div>',
            '    <div class="element-delete-button un-show-element" id="{id}-delete-button"></div>',
            '    </td></tr>',
            '    <tbody>',
            '    <tr>',
            '        <td class="layout-td-cell drag-shell" style="padding:3px">',
            '			<div class="drag-container row" id="{id}-drag-container"></div>',
            '        </td>',
            '    </tr>',
            '    </tbody>',
            '</table>'];
        var tplt = new Ext.XTemplate(domTemplate);
        return tplt.applyTemplate(sf);
    },
    setTitle: function (record) {
    	var titleDisplay=record.get('titleDisplay')||'';
    	var title=record.get('title')||'';
    	var pmt=titleDisplay?titleDisplay:title;
        $('#' + this.id + '-title').text(pmt);
        this.title=title;
        this.titleDisplay=titleDisplay;
    },
    initRecord: function (record) {
    	record.set('id',this.id);
    	if(this.bindTarget){
    		record.set('dataset', this.bindTarget);
    	}
    	record.set('layoutDesc', this.layout_desc);
    	record.set('columnNum', this.column);
    	record.set('layoutCode', this.layoutCode);
    	record.set('layoutSequence',this.layoutSequence);
    	record.set('layoutType',this.layoutType);
    	record.set('layoutTypeDesc',this.layoutTypeDesc);
    	record.set('viewParentLayoutId',this.parentLayout);
    	record.set('style',this.style);
    	record.set('width',this.width);
    	record.set('height',this.height);
    	if(this.template_flag){
    		record.set('templateFlag',this.template_flag);
    	}
    	if(this.parent_layout_id){
    		record.set('parentLayoutId',this.parent_layout_id);
    	}
    	//if(this.hidden){
    		record.set('hidden',this.hidden);
    	//}
    	record.set('title',this.title);
    	record.set('titleDisplay',this.titleDisplay);
    	if(this.layout_id){
    		record.set('layoutId',this.layout_id);
    	}
    	if(this.template_id){
    		record.set('templateId',this.template_id);
    	}
    	if(this.page_id){
    		record.set('pageId',this.page_id);
    	}
    	if(this.screen_id)
    		record.set('screenId',this.screen_id);
    	record.set('labelwidth',this.labelwidth);
    	if(this.enable_flag)
    		record.set('enableFlag',this.enable_flag);
    	
    },
    setAttr: function (record) {
    	if(record.get('id')){
    		this.setId(record.get('id'));
    	}
    	this.setBindTarget(record.get('dataset'));
    	this.setTitle(record);
        //this.column = record.get('column');
    	this.layoutCode=record.get('layoutCode');
    	this.setSequence(record.get('layoutSequence'));
    	this.layoutType=record.get('layoutType');
    	this.layoutTypeDesc=record.get('layoutTypeDesc');
    	this.setParentLayout(record,record.get('viewParentLayoutId'));
    	this.parent_layout_id=record.get('parentLayoutId');
    	this.style=record.get('style');
    	this.setDataRecord(record);
    	
    	this.setWidth(record.get('width'));
    	this.setHeight(record);
    	
    	this.template_flag=record.get('templateFlag');
    	this.setHide(record.get('hidden'));
    	this.layout_desc=record.get('layoutDesc');
    	
    	this.layout_id=record.get('layoutId');
    	this.template_id=record.get('templateId');
    	this.page_id=record.get('pageId');
    	this.labelwidth=record.get('labelwidth');
    	
    	this.enable_flag=record.get('enableFlag');
    	
    	this.setColumn(record.get('columnNum'));
    	
    	this.screen_id=record.get('screenId');
    	
    	this.onHover();
    },
    setColumn:function(column){
    	if(column>0){
    		var oldCol=parseInt(12/this.column);
    		var newCol=parseInt(12/column);
    		if(this.layoutType=='box'||this.layoutType=='form'||this.layoutType=='fieldSet'){
    			var children=$('#'+this.id+'-drag-container').children();
    			for(var i=0;i<children.length;i++){
    				if($(children[i]).attr('cmptype')!='layout'){
						$(children[i]).removeClass('col-md-'+oldCol);//1
						$(children[i]).addClass('col-md-'+newCol);//1
					}
    			}
    		}
    		this.column=column;
    	}
    },
    setHide:function(hidden){
    	if(hidden=='true'||hidden==true){
    		$('#'+this.id).addClass('layout-hide');
    		$('<div class="layout-hide-img"></div>').insertAfter($('#'+this.id+'-delete-button'),this.domEl);
    	}
    	else{
    		$('.input-field-hide',this.domEl).remove();
    		$('#'+this.id).removeClass('layout-hide');
    		$('.layout-hide-img',this.domEl).remove();
    	}
    	//$(hiddenWrap).addClass('item-input-wrap');
    	this.hidden=hidden;
    },
    setParentLayout:function(record,parentLayoutId){
    	var parentCmp=$AE.get(parentLayoutId);
    	if(parentCmp){
    		var parentLayout=parentCmp.getDataRecord().get('layoutId');
    		if(parentLayout){
    			this.parent_layout_id=parentLayout;
    			record.set('parentLayoutId',parentLayout);
    		}
    	}
    	if(parentLayoutId=='editor-container'){
    		this.parentLayout='';
    	}else{
    		this.parentLayout=parentLayoutId;
    	}
    	//移动组件
    },
    setSequence:function(sequence){
    	this.layoutSequence=sequence;
    },
    setBindTarget: function (bindTarget) {
        this.bindTarget = bindTarget;
        this.bindDataset(bindTarget);
    },
    bindDataset: function (datasetId) {
        //如果绑定DataSet不是当前选中的Dataset的时候不显示连接线，等dataset被选中的时候显示连接线
        //如果绑定DataSet是当前选中的DataSet则显示连接线
        if (window.currentDataSet && window.currentDataSet.id == datasetId) {
            $AE.jsPlumbInstance.getConnections({source: this.id}).forEach(function (connection) {
                $AE.jsPlumbInstance.deleteConnection(connection);
            });

            $AE.jsPlumbInstance.connect({
                source: this.id,
                target: datasetId
            });
        }
    },
    getColumn:function(){
    	return this.column;
    },
    setId:function(id){
    	var cmp=$AE.get(this.id);
    	$AE.CmpManager.remove(this.id);
    	$AE.CmpManager.put(id,cmp);
    	
    	if($AE.recycleComponent.get(this.id)){
    		$AE.recycleComponent.remove(this.id);
        	$AE.recycleComponent.put(id,cmp);
    	}
    	//修改对应dataset dom结构的ID
    	$('#'+this.id+'-element-title').attr('id',id+'-element-title');
    	$('#'+this.id+'-delete-button').attr('id',id+'-delete-button');
    	$('#'+this.id+'-drag-container').attr('id',id+'-drag-container');
    	$('#'+this.id+'-tab-drag-container').attr('id',id+'-tab-drag-container');
    	$('#'+this.id+'-title').attr('id',id+'-title');
    	$('#'+this.id+'-tab').attr('id',id+'-tab');
    	$('#'+this.id+'-tab-title').attr('id',id+'-tab-title');
    	$('#'+this.id+'-button-container').attr('id',id+'-button-container');
    	$(this.getDomEl()).attr('id',id);
    	this.id=id;
    },
    setWidth:function(width){
    	if(width){
    		var cmpParent=$(this.getDomEl()).css('width',width+'px');
    		this.width=width;
    		$("#editor-container").getNiceScroll().resize();
    	}
    },
    setHeight:function(record){
    	var height=record.get('height');
    	var autoAdjustHeight=record.get('autoadjustheight');
    	if(!autoAdjustHeight || autoAdjustHeight=='false'){
    		if(height){
    			var cmpParent=$(this.getDomEl()).css('height',height+'px');
    			this.height=height;
    			$("#editor-container").getNiceScroll().resize();
    		}
    	}
    }
});

$AE.HBox = Ext.extend($AE.Box, {
    constructor: function (config) {
    	config.layoutType=config.layoutType||'hBox';
    	config.layoutTypeDesc=config.layoutTypeDesc||'横向布局';
        $AE.HBox.superclass.constructor.call(this, config);
    },
    getDom: function (config) {
        var sf = this;
        var domTemplate = [
            '<table border="0" class="layout-table layout-component" id="{id}" cellpadding="0" cellspacing="0">',
            '    <tr><td><div class="element-title un-show-element" id="{id}-element-title" elementType="HBox">{[$AE.lang=="ZHS" ? \'横向布局\' :\'HBox\']}</div>',
            '    <div class="element-delete-button un-show-element" id="{id}-delete-button"></div>',
            '    </td></tr>',
            '    <tbody>',
            '    <tr>',
            '        <td class="layout-td-cell drag-shell" style="padding:3px">',
            '           <div class="drag-container row" id="{id}-drag-container"></div>',
            '        </td>',
            '    </tr>',
            '    </tbody>',
            '</table>'];
        var tplt = new Ext.XTemplate(domTemplate);
        return tplt.applyTemplate(sf);
    }
});

$AE.VBox = Ext.extend($AE.Box, {
    constructor: function (config) {
    	config.layoutType=config.layoutType||'vBox';
    	config.layoutTypeDesc=config.layoutTypeDesc||'纵向布局';
        $AE.VBox.superclass.constructor.call(this, config);
    },
    getDom: function () {
        var sf = this;
        var domTemplate = [
            '<table border="0" class="layout-table layout-component" id="{id}" cellpadding="0" cellspacing="0">',
            '    <tr><td><div class="element-title un-show-element" id="{id}-element-title" elementType="VBox">{[$AE.lang=="ZHS" ? \'纵向布局\' :\'VBox\']}</div>',
            '    <div class="element-delete-button un-show-element" id="{id}-delete-button"></div>',
            '    </td></tr>',
            '    <tbody>',
            '    <tr>',
            '        <td class="layout-td-cell drag-shell" style="padding:3px">',
            '           <div class="drag-container row" id="{id}-drag-container"></div>',
            '        </td>',
            '    </tr>',
            '    </tbody>',
            '</table>'];
        var tplt = new Ext.XTemplate(domTemplate);
        return tplt.applyTemplate(sf);
    }
});

$AE.Form = Ext
.extend(
    $AE.Box,
    {
        constructor: function (config) {
        	config.layoutType=config.layoutType||'form';
        	config.layoutTypeDesc=config.layoutTypeDesc||'表单';
            $AE.Form.superclass.constructor.call(this, config);
        },
        getDom: function () {
            var sf = this;
            var domTemplate = [
                '<table border="0" class="layout-table layout-form layout-title layout-component" id="{id}" cellpadding="0" cellspacing="0">',
                '    <tr><td><div class="element-title un-show-element" id="{id}-element-title" elementType="Form">{[$AE.lang=="ZHS" ? \'表单布局\' :\'Form\']}</div>',
                '    <div class="element-delete-button un-show-element" id="{id}-delete-button"></div>',
                '    </td></tr>',
                '    <thead>',
                '    <tr>',
                '        <th auskin="white" class="form_head_hap" colspan="4">',
                '            <table style="width:100%;table-layout:fixed;">',
                '                <tbody>',
                '                <tr>',
                '                    <td auskin="white" class="vertical-line">',
                '                        <div class="form_body_out"></div>',
                '                        <span></span></td>',
                '                    <td style="width:66px;" class="form-title" id="{id}-title">{title}</td>',
                '                    <td></td>',
                '                </tr>',
                '                </tbody>',
                '            </table>',
                '        </th>',
                '    </tr>',
                '    </thead>',
                '    <tbody class="form_body">',
                '    <tr height="5">',
                '        <td colspan="4"></td>',
                '    </tr>',
                '    <tr><td colspan="4" class="drag-shell">',
                '    <div class="drag-container row" id="{id}-drag-container"></div>',
                '</td></tr>',
                '    <tr height="5">',
                '        <td colspan="4"></td>',
                '    </tr>',
                '    </tbody>',
                '</table>'];
            var tplt = new Ext.XTemplate(domTemplate);
            return tplt.applyTemplate(sf);
        }
    });

$AE.Basic = Ext
.extend(
    $AE.Box,
    {
        constructor: function (config) {
        	config.layoutType=config.layoutType||'basic';
        	config.layoutTypeDesc=config.layoutTypeDesc||'其他';
        	config.column=config.colum||4;
            $AE.Form.superclass.constructor.call(this, config);
        },
        getDom: function () {
            var sf = this;
            var domTemplate = ['<table border="0" class="layout-table layout-component" id="{id}" bindTarget="{bindTarget}" cellpadding="0" cellspacing="0">',
            '    <tr><td><div class="element-title un-show-element" id="{id}-element-title" elementType="Basic">{[$AE.lang=="ZHS" ? \'其他\' :\'Basic\']}</div>',
            '    </td></tr>',
            '    <tbody>',
            '    <tr>',
            '        <td class="layout-td-cell drag-shell" style="padding:3px">',
            '			<div class="drag-container row" id="{id}-drag-container"></div>',
            '        </td>',
            '    </tr>',
            '    </tbody>',
            '</table>'];
            var tplt = new Ext.XTemplate(domTemplate);
            return tplt.applyTemplate(sf);
        }
    });


$AE.FieldSet = Ext
.extend(
    $AE.Box,
    {
        constructor: function (config) {
        	config.layoutType=config.layoutType||'fieldSet';
        	config.layoutTypeDesc=config.layoutTypeDesc||'框状布局';
            $AE.FieldSet.superclass.constructor.call(this, config);
        },
        getDom: function () {
            var sf = this;
            var domTemplate = [
                '<fieldset class="item-fieldset layout-table layout-component" id="{id}">',
                '    <div class="element-title un-show-element" id="{id}-element-title" elementType="FieldSet">{[$AE.lang=="ZHS" ? \'框状布局\' :\'FieldSet\']}</div>',
                '	 <div class="element-delete-button un-show-element" id="{id}-delete-button"></div>',
                '    <legend class="field_head" unselectable="on">',
                '<span id="{id}-title">{title}</span>',
                '</legend>',
                '    <table width="100%" border="0" cellpadding="0" cellspacing="0">',
                '        <tbody class="form_body">',
                '        <tr>',
                '            <th class="layout-th"></th>',
                '            <td class="layout-td-cell" style="padding:3px">',
                '            </td>',
                '        </tr>',
                '        <tr height="5">',
                '            <td colspan="2"  class="drag-shell">',
                '               <div class="drag-container row" id="{id}-drag-container"></div>',
                '            </td>',
                '        </tr>',
                '        </tbody>',
                '    </table>',
                '</fieldset>'];
            var tplt = new Ext.XTemplate(domTemplate);
            return tplt.applyTemplate(sf);
        }
    });

$AE.QueryForm = Ext
.extend(
    $AE.Box,
    {
        constructor: function (config) {
        	config.layoutType=config.layoutType||'queryForm';
        	config.layoutTypeDesc=config.layoutTypeDesc||'查询表单';
            $AE.QueryForm.superclass.constructor.call(this, config);
        },
        getDom: function () {
            var sf = this;
            var domTemplate = [
                '<table width="100%"  cellpadding="0" cellspacing="0" class="layout-component"  id={id}>',
                '    <tr>',
                '		<th class="layout-th"></th>',
                '		<td>',
                '			<div class="element-title un-show-element" id="{id}-element-title" elementType="QueryForm">{[$AE.lang=="ZHS" ? \'查询表单\' :\'QueryForm\']}</div>',
                '			<div class="element-delete-button un-show-element" id="{id}-delete-button"></div>',
                '		</td>',
                '	 </tr>',
                '	 <tbody>',
                '        <tr>',
                '            <th class="layout-th"></th>',
                '            <td class="layout-td-cell" style="padding:3px">',
                '            </td>',
                '        </tr>',
                '		 <tr>',
                '			<td class="drag-shell" style="width:85%;"><div class="drag-container row" id="{id}-drag-container"></div></td>',
                '			<td >',
                '				<div class="layout_queryform" style="right: 0px;background-position: -50px -24px;">',
                '				</div>',
                '				<div class="layout_queryform" style="right: 30px;background-position: -255px -24px;">',
                '				</div>',
                '				<div class="layout_queryform" style="right: 60px;background-position: -455px -24px;">',
                '				</div>',
                '			</td>',
                '	</tr>',
                '	</tbody>',
                '</table>'];
            var tplt = new Ext.XTemplate(domTemplate);
            return tplt.applyTemplate(sf.config);
        }
    });

$AE.TabPanel = Ext
.extend(
    $AE.Box,
    {
        constructor: function (config) {
        	config.layoutType=config.layoutType||'tabPanel';
        	config.layoutTypeDesc=config.layoutTypeDesc||'标签组';
            $AE.TabPanel.superclass.constructor.call(this, config);
        },
        getDom: function () {
            var sf = this;
            var domTemplate = [
            	'<div class="row layout-component" id={id}>',
            	'			<div class="element-title un-show-element" id="{id}-element-title" elementType="TabPanel">{[$AE.lang=="ZHS" ? \'标签组\' :\'TabPanel\']}</div>',
                '			<div class="element-delete-button un-show-element" id="{id}-delete-button"></div>',
                '<div class="tab-drag-container row ui-sortable" id="{id}-tab-drag-container"></div>',
                '</div>'
            ];
            var tplt = new Ext.XTemplate(domTemplate);
            return tplt.applyTemplate(sf.config);
        }
        
    });


$AE.Tab = Ext
.extend(
    $AE.Box,
    {
        constructor: function (config) {
        	config.layoutType=config.layoutType||'tab';
        	config.layoutTypeDesc=config.layoutTypeDesc||'标签页';
        	this.layoutId=config.layoutId;
        	this.tagGroupNumber=config.tagGroupNumber;
        	this.ref=config.ref;
        	this.disabled=config.disabled;
        	this.selected=config.selected;
        	this.closeable=config.closeable;
            $AE.Tab.superclass.constructor.call(this, config);
        },
        getDom: function () {
            var sf = this;
            var domTemplate = [
            	'<div class="row layout-component">',
                '<ul class="nav nav-tabs">',
                    '<li class="" id="{id}-tab-title" >',
                    '<div class="element-title un-show-element" style="display:none" id="{id}-element-title" elementType="Tab">{[$AE.lang=="ZHS" ? \'标签页\' :\'Tab\']}</div>',
                    '<div class="element-delete-button un-show-element" style="display:none" id="{id}-delete-button"></div>',
                    '<a href="#{id}-tab" hrefType="tab">{title}</a></li>',
                '</ul>',
                '<div class="tab-content">',
                    '<div id="{id}-tab">',
                        '<div class="drag-container row ui-sortable" id="{id}-drag-container"></div>',
                    '</div>',
                 '</div>',
                '</div>',
            '</div>'
            ];
            var tplt = new Ext.XTemplate(domTemplate);
            return tplt.applyTemplate(sf.config);
        },
        initRecord: function (record) {
        	record.set('tabGroupNumber', this.tagGroupNumber);
        	record.set('ref', this.ref);
        	record.set('disabled', this.disabled);
        	record.set('selected', this.selected);
        	record.set('closeable',this.closeable);
        	$AE.Tab.superclass.initRecord.call(this, record);
        },
        setAttr: function (record) {
        	this.tagGroupNumber=record.get('tabGroupNumber');
        	this.ref=record.get('ref');
        	this.disabled=record.get('disabled');
        	this.selected=record.get('selected');
        	this.closeable=record.get('closeable');
        	this.setTitle(record.get('title_display'));
        	$AE.Tab.superclass.setAttr.call(this, record);
        	
        },
        setTitle:function(title){
        	if(typeof title=='string'){
        		$('a:first',$('#'+this.id+'-tab-title')).text(title);
        		this.title=title;
        	}
        },
        getTitle:function(){
        	return this.title;
        }
    });

$AE.Field = Ext
.extend(
    $AE.Component,
    {
        constructor: function (config) {
        	config.prompt = config.prompt || 'Prompt';
            this.bindTarget = config.bindTarget;
            this.defaultValue = config.defaultValue;
            this.required = config.required;
            this.readonly = config.readonly;
            this.layoutId = config.layoutId;
            this.tagSequence = config.tagSequence;
            this.tagCode = config.tagCode;
            this.tagType = config.tagType;
            this.tagTypeDesc = config.tagTypeDesc;
            this.tagDesc = config.tagDesc;
            this.fieldName = config.fieldName;
            this.fieldId = config.fieldId;
            $AE.Field.superclass.constructor.call(this, config);
        },
        initRecord: function (record) {
            record.set('bindtarget', this.bindTarget);
            record.set('bindTarget', this.bindTarget);
            record.set('layoutViewId',this.layoutId);
            record.set('name', this.name);
            record.set('prompt', this.prompt);
            record.set('promptDesc', this.promptDesc);
            record.set('placeholder', this.placeholder);
            record.set('defaultvalue', this.defaultValue);
            record.set('required', this.required);
            record.set('readonly', this.readonly);
            record.set('tagSequence',this.tagSequence);
            record.set('tagCode',this.tagCode);
            record.set('tagType',this.tagType);
            record.set('tagTypeDesc',this.tagTypeDesc);
            record.set('tagDesc',this.tagDesc);
            record.set('fieldName',this.fieldName);
            if(this.field_id){
            	record.set('fieldId',this.field_id);
            }
            if(this.layout_id){
            	record.set('layoutId',this.layout_id);
            }
            if(this.tag_id){
            	record.set('tagId',this.tag_id);
            }
            
            record.set('id',this.id);
            record.set('colspan',this.colspan);
            record.set('rowspan',this.rowspan);
            record.set('lock',this.lock);
            record.set('sortable',this.sortable);
            record.set('footerrenderer',this.footerrenderer);
            record.set('hidden',this.hidden);
            record.set('align',this.align);
            record.get('alignDesc',this.align_desc);
            record.get('footerrenderer',this.footerrenderer);
            record.get('lock',this.lock);
            record.get('sortable',this.sortable);
            record.get('parentTagDesc',this.parent_tag_desc);
            record.get('parentTagId',this.parent_tag_id);
            record.set('printable',this.printable);
            record.set('width',this.width);
            record.set('tfTypecaseDesc',this.tf_typecase_desc);
            record.set('lovLovautoquery',this.lov_lovautoquery);
            if(this.enable_flag)
            	record.set('enableFlag',this.enable_flag);
            if(this.template_flag){
            	record.set('templateFlag',this.template_flag);
            }
            record.set('appHideFlag',this.app_hide_flag);
        },
        setAttr: function (record) {
            this.setBindTarget(record.get('bindtarget')||record.get('bindTarget'));
            this.setLayoutId(record.get('layoutViewId'));
            this.setName(record.get('name'));
            this.promptDesc=record.get('promptDesc')||'';
            this.prompt=record.get('prompt')||'';
            this.setPlaceholder(record.get('placeholder'));
            this.setDefaultValue(record.get('defaultvalue'));
            this.setRequired(record.get('required'));
            this.setReadOnly(record.get('readonly'));
            this.setSequence(record.get('tagSequence'));
            this.tagCode=record.get('tagCode');
            this.tagType=record.get('tagType');
            this.tagTypeDesc=record.get('tagTypeDesc');
            this.tagDesc=record.get('tagDesc');
            this.fieldName=record.get('fieldName');
            this.field_id=record.get('fieldId');
            this.layout_id=record.get('layoutId');
            this.tag_id=record.get('tagId');
            this.setColSpan(record.get('colspan'));
            this.rowspan=record.get('rowspan');
            this.lock=record.get('lock');
            this.sortable=record.get('sortable');
            this.footerrenderer=record.get('footerrenderer');
            this.template_flag=record.get('templateFlag');
            this.setHide(record.get('hidden'));
            this.align=record.get('align');
            this.align_desc=record.get('alignDesc');
            this.footerrenderer=record.get('footerrenderer');
            this.lock=record.get('lock');
            this.sortable=record.get('sortable');
            this.parent_tag_desc=record.get('parentTagDesc');
            this.parent_tag_id=record.get('parentTagId');
            this.printable=record.get('printable');
            this.width=record.get('width');
            this.tf_typecase_desc=record.get('tfTypecaseDesc');
            this.lov_lovautoquery=record.get('lovLovautoquery');
            this.enable_flag=record.get('enableFlag');
            
            this.setDataRecord(record);
            if(record.get('id')){
            	this.setId(record.get('id'));
            }
            this.setPrompt(record);
            this.app_hide_flag=record.get('appHideFlag');
            
            this.onHover();
        },
        setHide:function(hidden){
        	var hiddenWrap=$('#'+this.id+'-input',this.domEl);
        	//$(hiddenWrap).removeClass('item-input-wrap');
        	if(this.hidden!='true' && hidden=='true'){
        		if(this.tagType!='textArea'){
        			$('#'+this.id+'-radio',this.domEl).addClass('item-input-radio-hidden');
        			$('#'+this.id+'-ckb',this.domEl).addClass('item-input-ckb-hidden');
        			
        			$('<div class="input-field-hide"><div class="input-hide-img"></div></div>').insertBefore(hiddenWrap);
        			$('.item-input-wrap',this.domEl).css('border','none');
        		}
        		else{
        			$('<div class="input-hide-img"></div>').insertBefore(hiddenWrap);
        			$(hiddenWrap).addClass('input-field-hide');
        		}
        	}
        	else if(this.hidden=='true' && hidden!='true'){
        		if(this.tagType!='textArea'){
        			$('#'+this.id+'-radio',this.domEl).removeClass('item-input-radio-hidden');
        			$('#'+this.id+'-ckb',this.domEl).removeClass('item-input-ckb-hidden');
        			
        			$('.input-field-hide',this.domEl).remove();
        			$('.item-input-wrap',this.domEl).css('border','#ccc 1px solid');
        		}else{
        			$('.input-hide-img',this.domEl).remove();
        			$(hiddenWrap).removeClass('input-field-hide');
        		}
        	}
        	//$(hiddenWrap).addClass('item-input-wrap');
        	this.hidden=hidden;
        },
        setColSpan:function(colspan){
        	if(colspan && colspan>0){
        		var col=colspan*3;
        		var colClass='col-md-'+col;
        		var h=$(this.domEl).parent();
        		if($(h).hasClass("input-shell")){
        			$(h).removeClass('col-md-12 col-md-6 col-md-4 col-md-3');
        			$(h).addClass(colClass);
        			this.colspan=colspan;
        		}
        	}
        },
        setSequence:function(sequence){
        	this.tagSequence=sequence;
        },
        setDefaultValue: function (defaultValue) {
        	if(defaultValue){
        		this.defaultValue = defaultValue;
        		if($('#'+this.id+'-input')[0] && $('#'+this.id+'-input')[0].nodeName=='DIV'){
        			$('#'+this.id+'-input')[0].innerHTML=defaultValue;
        		}else{
        			$('#'+this.id+'-input').val(defaultValue);
        		}
        	}
        },
        setRequired: function (required) {
        	if(required=='Y'||required=='true'||required==true){
        		required=true;
        	}else{
        		required=false;
        	}
            this.required = required;
            if (this.required) {
            	$('<div class="input-required">*</div>').insertAfter($('#'+this.id+'-delete-button'));
                //this.getDomEl().addClass('input-required');
            } else {
                $('.input-required',this.getDomEl()).remove();
            }
        },
        setReadOnly: function (readonly) {
        	if(readonly=='Y'||readonly=='true'||readonly==true){
        		readonly=true;
        	}else{
        		readonly=false;
        	}
            this.readonly = readonly;
            if (this.readonly) {
                this.getDomEl().addClass('input-readonly');
                //20180108 添加设置input为readonly
                $('input', this.getDomEl()).attr("readonly", true);
            } else {
                this.getDomEl().removeClass('input-readonly');
                //20180108 删除input的readonly属性
                $('input', this.getDomEl()).attr("readonly", false);
            }
        },
        setBindTarget: function (bindTarget) {
            this.bindTarget = bindTarget;
            this.bindDataset(bindTarget);
        },
        setName: function (name) {
            this.name = name;
        },
        setPrompt: function (record) {
        	var promptDesc=record.get('promptDesc');
        	var prompt=record.get('prompt');
        	var tagDesc=record.get('tagDesc');
        	var pmt='';
        	if(promptDesc){
        		pmt=promptDesc;
        	}
        	else{
        		if(tagDesc){pmt=tagDesc;}
        		else{pmt=prompt;}
        	}
            $('#' + this.id + '-prompt').text(tagDesc);
        },
        setPlaceholder: function (placeholder) {
            this.placeholder = placeholder;
            $('#' + this.id + '-input').attr('placeholder', placeholder);
        },
        setLayoutId:function(layoutId){
        	this.layoutId=layoutId;
        },
        bindDataset: function (datasetId) {
            //如果绑定DataSet不是当前选中的Dataset的时候不显示连接线，等dataset被选中的时候显示连接线
            //如果绑定DataSet是当前选中的DataSet则显示连接线
            if (window.currentDataSet && window.currentDataSet.id == datasetId) {
                $AE.jsPlumbInstance.getConnections({source: this.id}).forEach(function (connection) {
                    $AE.jsPlumbInstance.deleteConnection(connection);
                });

                $AE.jsPlumbInstance.connect({
                    source: this.id,
                    target: datasetId
                });
            }
        },
        setId:function(id){
        	var cmp=$AE.get(this.id);
        	$AE.CmpManager.remove(this.id);
        	$AE.CmpManager.put(id,cmp);
        	
        	if($AE.recycleComponent.get(this.id)){
        		$AE.recycleComponent.remove(this.id);
            	$AE.recycleComponent.put(id,cmp);
        	}
        	//修改对应dataset dom结构的ID
        	$('#'+this.id+'-element-title').attr('id',id+'-element-title');
        	$('#'+this.id+'-delete-button').attr('id',id+'-delete-button');
        	$('#'+this.id+'-prompt').attr('id',id+'-prompt');
        	$('#'+this.id+'-input').attr('id',id+'-input');
        	$('#'+this.id+'-ckb').attr('id',id+'-ckb');
        	$('#'+this.id+'-radio').attr('id',id+'-radio');
        	$(this.getDomEl()).attr('id',id);
        	this.id=id;
        	
        	this.el=this.getDomEl()
        }
    });

$AE.TextField = Ext
.extend(
    $AE.Field,
    {
        constructor: function (config) {
        	config.prompt = config.prompt || '文本域';
        	config.tagType = config.tagType ||'textField';
        	config.tagTypeDesc = config.tagTypeDesc ||'文本域';
            $AE.TextField.superclass.constructor.call(this, config);
        },
        getDom: function (gridFlag) {
            var sf = this;
            if (!gridFlag) {
                var domTemplate = [
                    '<div class="input-prompt" id="{id}-prompt">{prompt}</div>',
                    '<div class="item-tf item-wrap input-component {[this.required ? \'input-required\' : \'\']} {[this.readonly ? \'input-readonly\' : \'\']}" isgrid="false" style="width:60%;height:28px;" id="{id}">',
                    '    <div class="element-title un-show-element" id="{id}-element-title" elementType="TextField">{[$AE.lang=="ZHS" ? \'文本域\' :\'TextField\']}</div>',
                    '    <div class="element-delete-button un-show-element" id="{id}-delete-button"></div>',
                    '    <div class="item-input-wrap" >',
                    '        <input id="{id}-input"  placeholder="{placeholder}" style="height: 28px; text-indent: 4px; text-transform: uppercase;" type="input" class="item-textField" atype="field.input" autocomplete="off" value="" >',
                    '    </div>',
                    '</div>'];
            } else {
                var domTemplate = [
                    '<div class="grid-input-prompt" id="{id}-prompt">{prompt}</div>',
                    '<div class="grid-input">',
                    '    <div class="item-tf item-wrap grid-input-component {[this.required ? \'grid-input-required\' : \'\']} {[this.readonly ? \'grid-input-readonly\' : \'\']}" isgrid="false" style="width:90px;height:28px;" id="{id}">',
                    '        <div class="element-title un-show-element" id="{id}-element-title" elementType="TextField">{[$AE.lang=="ZHS" ? \'文本域\' :\'TextField\']}</div>',
                    '        <div class="element-delete-button un-show-element" id="{id}-delete-button"></div>',
                    '        <div class="item-input-wrap" >',
                    '            <input id="{id}-input"  placeholder="{placeholder}" style="height: 28px; text-indent: 4px; text-transform: uppercase;" type="input" class="item-textField" atype="field.input" autocomplete="off" value="" >',
                    '        </div>',
                    '    </div>',
                    '</div>'];
            }
            var tplt = new Ext.XTemplate(domTemplate, {
                compiled: true
            });
            return tplt.applyTemplate(sf);
        },
        setAttr:function(record){
        	$AE.TextField.superclass.setAttr.call(this, record);
        }
    });


$AE.NumberField = Ext
.extend(
    $AE.Field,
    {
        constructor: function (config) {
        	config.prompt = config.prompt || '数字框';
        	config.tagType = config.tagType ||'numberField';
        	config.tagTypeDesc = config.tagTypeDesc ||'数字域';
            this.allowdecimals=config.allowdecimals;
            this.decimalprecision=config.decimalprecision;
            this.allownegative=config.allownegative;
            this.allowformat=config.allowformat;
            $AE.NumberField.superclass.constructor.call(this, config);
        },
        getDom: function (gridFlag) {
            var sf = this;
            if (!gridFlag) {
                var domTemplate = [
                    '<div class="input-prompt" id="{id}-prompt">{prompt}</div>',
                    '<div class="item-wrap item-tf input-component {[this.required ? \'input-required\' : \'\']} {[this.readonly ? \'input-readonly\' : \'\']}" isgrid="false" style="width:60%;height:28px;" id="{id}">',
                    '    <div class="element-title un-show-element" id="{id}-element-title" elementType="NumberField">{[$AE.lang=="ZHS" ? \'数字域\' :\'NumberField\']}</div>',
                    '    <div class="element-delete-button un-show-element" id="{id}-delete-button"></div>',
                    '    <div class="item-input-wrap">',
                    '        <input  id="{id}-input" placeholder="{placeholder}" name="{name}" bindTarget="{bindTarget}" tabindex="0" style="height:28px;line-height:28px;ime-mode:;" type="input" class="item-textField item-numberField" atype="field.input" autocomplete="off" value="" >',
                    '    </div>',
                    '</div>'];
            } else {
                var domTemplate = [
                    '<div class="grid-input-prompt" id="{id}-prompt">{prompt}</div>',
                    '    <div class="grid-input">',
                    '        <div class="item-wrap item-tf grid-input-component {[this.required ? \'grid-input-required\' : \'\']} {[this.readonly ? \'grid-input-readonly\' : \'\']}" isgrid="false" style="width:90px;height:28px;" id="{id}">',
                    '            <div class="element-title un-show-element" id="{id}-element-title" elementType="NumberField">{[$AE.lang=="ZHS" ? \'数字域\' :\'NumberField\']}</div>',
                    '            <div class="element-delete-button un-show-element" id="{id}-delete-button"></div>',
                    '            <div class="item-input-wrap" style="padding-right:3px">',
                    '                <input id="{id}-input" placeholder="{placeholder}" name="{name}" bindTarget="{bindTarget}" tabindex="0" style="height:28px;line-height:28px;ime-mode:;" type="input" class="item-textField item-numberField" atype="field.input" autocomplete="off" value="" >',
                    '            </div>',
                    '        </div>',
                    '    </div>',
                    '</div>'];

            }
            var tplt = new Ext.XTemplate(domTemplate);
            return tplt.applyTemplate(sf);
        },
        initRecord:function(record){
        	record.set('nfAllowdecimals',this.allowdecimals);
        	record.set('nfDecimalprecision',this.decimalprecision);
        	record.set('nfAllownegative',this.allownegative);
        	record.set('nfAllowformat',this.allowformat);
        	$AE.NumberField.superclass.initRecord.call(this, record);
        },
        setAttr:function(record){
        	this.allowdecimals=record.get('nfAllowdecimals');
        	this.decimalprecision=record.get('nfDecimalprecision');
        	this.allownegative=record.get('nfAllownegative');
        	this.allowformat=record.get('nfAllowformat');
        	$AE.NumberField.superclass.setAttr.call(this, record);
        }
    });

$AE.ComboBox = Ext
.extend(
    $AE.Field,
    {
        constructor: function (config) {
            config.prompt = config.prompt || '下拉框';
            config.tagType = config.tagType ||'comboBox';
        	config.tagTypeDesc = config.tagTypeDesc ||'下拉框';
            $AE.ComboBox.superclass.constructor.call(this, config);
        },
        getDom: function (gridFlag) {
            var sf = this;
            if (!gridFlag) {
                var domTemplate = [
                    '<div class="input-prompt" id="{id}-prompt">{prompt}</div>',
                    '<div class="item-wrap item-tf input-component {[this.required ? \'input-required\' : \'\']} {[this.readonly ? \'input-readonly\' : \'\']}" isgrid="false" style="width:60%;height:28px;" id="{id}">',
                    '    <div class="element-title un-show-element" id="{id}-element-title" elementType="ComboBox">{[$AE.lang=="ZHS" ? \'下拉框\' :\'ComboBox\']}</div>',
                    '    <div class="element-delete-button un-show-element" id="{id}-delete-button"></div>',
                    '    <div class="item-input-wrap">',
                    '        <div style="height:100%">',
                    '            <input  id="{id}-input" placeholder="{placeholder}" tabindex="0" name="{name}" bindTarget="{bindTarget}"  style="text-indent:4px;height:28px;line-height:28px;" class="item-textField" atype="field.input" autocomplete="off" value="" >',
                    '        </div>',
                    '        <div class="item-trigger item-comboButton" atype="triggerfield.trigger" hidefocus="" ></div>',
                    '    </div>',
                    '</div>'];
            } else {
                var domTemplate = ['<div class="grid-input-prompt" id="{id}-prompt">{prompt}</div>',
                    '    <div class="grid-input">',
                    '        <div class="item-wrap item-tf grid-input-component {[this.required ? \'grid-input-required\' : \'\']} {[this.readonly ? \'grid-input-readonly\' : \'\']}" isgrid="false" style="width:90px;height:28px;" id="{id}">',
                    '            <div class="element-title un-show-element" id="{id}-element-title" elementType="ComboBox">{[$AE.lang=="ZHS" ? \'下拉框\' :\'ComboBox\']}</div>',
                    '            <div class="element-delete-button un-show-element" id="{id}-delete-button"></div>',
                    '            <div class="item-input-wrap">',
                    '                <div style="height:100%">',
                    '                    <input  id="{id}-input" placeholder="{placeholder}" tabindex="0" name="{name}" bindTarget="{bindTarget}"  style="text-indent:4px;height:28px;line-height:28px;" class="item-textField" atype="field.input" autocomplete="off" value="" >',
                    '                </div>',
                    '                <div class="item-trigger item-comboButton" atype="triggerfield.trigger" hidefocus="" ></div>',
                    '            </div>',
                    '        </div>',
                    '    </div>',
                    '</div>'];
            }
            ;
            var tplt = new Ext.XTemplate(domTemplate);
            return tplt.applyTemplate(sf);
        },
        initRecord:function(record){
        	record.set('cbDatasourceDesc',this.cb_datasource_desc);
        	record.set('cbDatasource',this.cb_datasource);
        	record.set('cbOptions',this.cb_options);
        	record.set('cbValuefield',this.cb_valuefield);
        	record.set('cbReturnfieldDesc',this.cb_returnfield_desc);
        	record.set('cbReturnfield',this.cb_returnfield);
        	record.set('cbDisplayfield',this.cb_displayfield);
        	record.set('cbSyscode',this.cb_syscode);
        	record.set('cbSqltext',this.cb_sqltext);
        	record.set('cbParseSql',this.cb_parse_sql);
        	record.set('cbOptionsLink',this.cb_options_link);
            record.set('cbLovcode',this.cb_lovcode);
        	$AE.ComboBox.superclass.initRecord.call(this, record);
        },
        setAttr:function(record){
        	this.cb_datasource_desc=record.get('cbDatasourceDesc');
        	this.cb_datasource=record.get('cbDatasource');
        	this.cb_options=record.get('cbOptions');
        	this.cb_valuefield=record.get('cbValuefield');
        	this.cb_returnfield_desc=record.get('cbReturnfieldDesc');
        	this.cb_returnfield=record.get('cbReturnfield');
        	this.cb_displayfield=record.get('cbDisplayfield');
        	this.cb_syscode=record.get('cbSyscode');
        	this.cb_sqltext=record.get('cbSqltext');
        	this.cb_parse_sql=record.get('cbParseSql');
        	this.cb_options_link=record.get('cbOptionsLink');
            this.cb_lovcode=record.get('cbLovcode');
        	$AE.ComboBox.superclass.setAttr.call(this, record);
        }
    });

$AE.Lov = Ext
.extend(
    $AE.Field,
    {
        constructor: function (config) {
            config.prompt = config.prompt || 'Lov';
            config.tagType = config.tagType ||'lov';
        	config.tagTypeDesc = config.tagTypeDesc ||'值列表';
            $AE.Lov.superclass.constructor.call(this, config);
        },
        getDom: function (gridFlag) {
            var sf = this;
            if (!gridFlag) {
                var domTemplate = [
                    '<div class="input-prompt" id="{id}-prompt">{prompt}</div>',
                    '<div class="item-wrap item-tf input-component {[this.required ? \'input-required\' : \'\']} {[this.readonly ? \'input-readonly\' : \'\']}" isgrid="false" style="width:60%;height:28px;" id="{id}">',
                    '    <div class="element-title un-show-element" id="{id}-element-title" elementType="Lov">{[$AE.lang=="ZHS" ? \'值列表\' :\'Lov\']}</div>',
                    '    <div class="element-delete-button un-show-element" id="{id}-delete-button"></div>',
                    '    <div class="item-input-wrap">',
                    '        <div style="height:100%">',
                    '            <input id="{id}-input" placeholder="{placeholder}" name="{name}" bindTarget="{bindTarget}" tabindex="0" style="text-indent:4px;height:28px;line-height:28px;" class="item-textField" atype="field.input" autocomplete="off" >',
                    '        </div>',
                    '        <div class="item-trigger item-lovButton" atype="triggerfield.trigger" hidefocus="" ></div>',
                    '    </div>',
                    '</div>'];
            } else {
                var domTemplate = ['<div class="grid-input-prompt" id="{id}-prompt">{prompt}</div>',
                    '    <div class="grid-input">',
                    '        <div class="item-wrap item-tf grid-input-component {[this.required ? \'grid-input-required\' : \'\']} {[this.readonly ? \'grid-input-readonly\' : \'\']}" isgrid="false" style="width:90px;height:28px;" id="{id}">',
                    '            <div class="element-title un-show-element" id="{id}-element-title" elementType="Lov">{[$AE.lang=="ZHS" ? \'值列表\' :\'Lov\']}</div>',
                    '            <div class="element-delete-button un-show-element" id="{id}-delete-button"></div>',
                    '            <div class="item-input-wrap">',
                    '                <div style="height:100%">',
                    '                    <input id="{id}-input" placeholder="{placeholder}" name="{name}" bindTarget="{bindTarget}" tabindex="0" style="text-indent:4px;height:28px;line-height:28px;" class="item-textField" atype="field.input" autocomplete="off" >',
                    '                </div>',
                    '                <div class="item-trigger item-lovButton" atype="triggerfield.trigger" hidefocus="" ></div>',
                    '            </div>',
                    '        </div>',
                    '    </div>',
                    '</div>'];
            }
            var tplt = new Ext.XTemplate(domTemplate);
            return tplt.applyTemplate(sf);
        },
        initRecord:function(record){
        	record.set('lovDatasourceDesc',this.lov_datasource_desc);
        	record.set('lovDatasource',this.lov_datasource);
        	record.set('lovLovservice',this.lov_lovservice);
        	record.set('lovLovurl',this.lov_lovurl);
        	record.set('lovLovautoquery',this.lov_lovautoquery);
        	record.set('lovLovgridheight',this.lov_lovgridheight);
        	record.set('lovLovheight',this.lov_lovheight);
        	record.set('lovLovwidth',this.lov_lovwidth);
        	record.set('lovLovlabelwidth',this.lov_lovlabelwidth);
        	record.set('lovAutocomplete',this.lov_autocomplete);
        	record.set('lovAutocompletefield',this.lov_autocompletefield);
        	record.set('lovTitle',this.lov_title);
        	record.set('lovSqltext',this.lov_sqltext);
        	record.set('lovSyscode',this.lov_syscode);
            record.set('lovLovcode',this.lov_lovcode);
        	$AE.Lov.superclass.initRecord.call(this, record);
        },
        setAttr:function(record){
        	this.lov_datasource_desc=record.get('lovDatasourceDesc');
        	this.lov_datasource=record.get('lovDatasource');
        	this.lov_lovservice=record.get('lovLovservice');
        	this.lov_lovurl=record.get('lovLovurl');
        	this.lov_lovautoquery=record.get('lovLovautoquery');
        	this.lov_lovgridheight=record.get('lovLovgridheight');
        	this.lov_lovheight=record.get('lovLovheight');
        	this.lov_lovwidth=record.get('lovLovwidth');
        	this.lov_lovlabelwidth=record.get('lovLovlabelwidth');
        	this.lov_autocomplete=record.get('lovAutocomplete');
        	this.lov_autocompletefield=record.get('lovAutocompletefield');
        	this.lov_title=record.get('lovTitle');
        	this.lov_sqltext=record.get('lovSqltext');
        	this.lov_syscode=record.get('lovSyscode');
            this.lov_lovcode=record.get('lovLovcode');
        	$AE.Lov.superclass.setAttr.call(this, record);
        }
    });


$AE.TextArea = Ext
.extend(
    $AE.Field,
    {
        constructor: function (config) {
            config.prompt = config.prompt || '文本区';
            config.tagType = config.tagType ||'textArea';
        	config.tagTypeDesc = config.tagTypeDesc ||'文本区';
            $AE.TextArea.superclass.constructor.call(this, config);
        },
        getDom: function (gridFlag) {
            var sf = this;
            if (!gridFlag) {
                var domTemplate = [
                    '<div class="input-prompt" id="{id}-prompt">{prompt}</div>',
                    '<div class="input-component {[this.required ? \'input-required\' : \'\']} {[this.readonly ? \'input-readonly\' : \'\']}" id="{id}" >',
                    '    <div class="element-title un-show-element" id="{id}-element-title" elementType="TextArea">{[$AE.lang=="ZHS" ? \'多行文本\' :\'TextArea\']}</div>',
                    '    <div class="element-delete-button un-show-element" id="{id}-delete-button"></div>',
                    '		<div class="item-input-wrap">',
                    '    		<textarea id="{id}-input" placeholder="{placeholder}" tabindex="0" isgrid="false" class="item-textarea item-tf" autocomplete="off" atype="input" style="width:140px;height:40px;" name="{name}" bindTarget="{bindTarget}"></textarea>',
                    '		</div>',
                    '</div>'];
            } else {
                var domTemplate = ['<div class="grid-input-prompt" id="{id}-prompt">{prompt}</div>',
                    '    <div class="grid-input" style="height:51px;">',
                    '        <div class="grid-input-component {[this.required ? \'grid-input-required\' : \'\']} {[this.readonly ? \'grid-input-readonly\' : \'\']}" id="{id}" >',
                    '            <div class="element-title un-show-element" id="{id}-element-title" elementType="TextArea">{[$AE.lang=="ZHS" ? \'多行文本\' :\'TextArea\']}</div>',
                    '            <div class="element-delete-button un-show-element" id="{id}-delete-button"></div>',
                    '				<div class="item-input-wrap">',
                    '           		<textarea id="{id}-input" placeholder="{placeholder}" tabindex="0" isgrid="false" class="item-textarea item-tf" autocomplete="off" atype="input" style="width:90px;height:40px;" name="{name}" bindTarget="{bindTarget}"></textarea>',
                    '        		</div>',
                    '		</div>',
                    '    </div>',
                    '</div>'];
            }
            var tplt = new Ext.XTemplate(domTemplate);
            return tplt.applyTemplate(sf);
        }
    });


$AE.CheckBox = Ext
.extend(
    $AE.Field,
    {
        constructor: function (config) {
            config.prompt = config.prompt || '复选框';
            config.tagType = config.tagType ||'checkBox';
        	config.tagTypeDesc = config.tagTypeDesc ||'复选框';
            $AE.CheckBox.superclass.constructor.call(this, config);
        },
        getDom: function (gridFlag) {
            var sf = this;
            if (!gridFlag) {
                var domTemplate = [
                    '<div class="input-prompt" id="{id}-prompt">{prompt}</div>',
                    '<div class="input-component {[this.required ? \'input-required\' : \'\']} {[this.readonly ? \'input-readonly\' : \'\']}" id="{id}">',
                    '    <div class="element-title un-show-element" id="{id}-element-title" elementType="CheckBox">{[$AE.lang=="ZHS" ? \'复选框\' :\'CheckBox\']}</div>',
                    '    <div class="element-delete-button un-show-element" id="{id}-delete-button"></div>',
                    '    <div class="" style="height:28px;width:-1px;" >',
                    '        <div id="{id}-ckb" tabindex="0" hidefocus="" class="item-input-ckb" atype="checkbox">',
                    '        </div>',
                    '    </div>',
                    '</div>'];
            } else {
                var domTemplate = ['<div class="grid-input-prompt" id="{id}-prompt">{prompt}</div>',
                    '    <div class="grid-input">',
                    '        <div class="grid-input-component {[this.required ? \'grid-input-required\' : \'\']} {[this.readonly ? \'grid-input-readonly\' : \'\']}" id="{id}">',
                    '            <div class="element-title un-show-element" id="{id}-element-title" elementType="CheckBox">{[$AE.lang=="ZHS" ? \'复选框\' :\'CheckBox\']}</div>',
                    '            <div class="element-delete-button un-show-element" id="{id}-delete-button"></div>',
                    '            <div class="" style="height:28px;width:-1px;margin-left:35px;" >',
                    '                <div tabindex="0" hidefocus="" class="item-input-ckb" atype="checkbox">',
                    '                </div>',
                    '            </div>',
                    '        </div>',
                    '    </div>',
                    '</div>'];
            }
            var tplt = new Ext.XTemplate(domTemplate);
            return tplt.applyTemplate(sf);
        },
        initRecord:function(record){
        	record.set('ckCheckedvalue',this.ck_checkedvalue);
        	record.set('ckUncheckedvalue',this.ck_uncheckedvalue);
        	record.set('ckLabel',this.ck_label);
        	$AE.CheckBox.superclass.initRecord.call(this, record);
        },
        setAttr:function(record){
        	this.ck_checkedvalue=record.get('ckCheckedvalue');
        	this.ck_uncheckedvalue=record.get('ckUncheckedvalue');
        	this.ck_label=record.get('ckLabel');
        	this.setReadOnly(record.get('readonly'));
        	this.setHide(record.get('hidden'));
        	$AE.CheckBox.superclass.setAttr.call(this, record);
        },
        setReadOnly: function (readonly) {
        	if(readonly=='Y'||readonly=='true'||readonly==true){
        		readonly=true;
        	}else{
        		readonly=false;
        	}
            this.readonly = readonly;
            if (this.readonly) {
            	$('#'+this.id+'-ckb').addClass('item-input-ckb-readonly');
                //20180108 添加设置input为readonly
                $('#'+this.id+'-ckb').attr("readonly", true);
            } else {
            	$('#'+this.id+'-ckb').removeClass('item-input-ckb-readonly');
                //20180108 删除input的readonly属性
            	$('#'+this.id+'-ckb').attr("readonly", false);
            }
        },
        setHide:function(hidden){
        	var hiddenWrap=$('#'+this.id+'-input',this.domEl);
        	if(this.hidden!='true' && hidden=='true'){
        		$('<div class="little-hide-img"></div>').insertBefore($('#'+this.id+'-ckb',this.domEl));
        		$('#'+this.id+'-ckb',this.domEl).addClass('item-input-ckb-hidden');

        	}
        	else if(this.hidden=='true' && hidden!='true'){
        		$('.little-hide-img',this.domEl).remove();
        		$('#'+this.id+'-ckb',this.domEl).removeClass('item-input-ckb-hidden');
        	}
        	this.hidden=hidden;
        },
    });

$AE.DatePicker = Ext
.extend(
    $AE.Field,
    {
        constructor: function (config) {
            config.prompt = config.prompt || '日期域';
            config.tagType = config.tagType ||'datePicker';
        	config.tagTypeDesc = config.tagTypeDesc ||'日期域';
            $AE.DatePicker.superclass.constructor.call(this, config);
        },
        getDom: function (gridFlag) {
            var sf = this;
            if (!gridFlag) {
                var domTemplate = [
                    '<div class="input-prompt" id="{id}-prompt">{prompt}</div>',
                    '<div class="item-wrap item-tf input-component {[this.required ? \'input-required\' : \'\']} {[this.readonly ? \'input-readonly\' : \'\']}" style="width:60%;height:28px;" id="{id}">',
                    '    <div class="element-title un-show-element" id="{id}-element-title" elementType="DatePicker">{[$AE.lang=="ZHS" ? \'日期框\' :\'DatePicker\']}</div>',
                    '    <div class="element-delete-button un-show-element" id="{id}-delete-button"></div>',
                    '    <div class="item-input-wrap" >',
                    '        <div style="height:100%">',
                    '            <input id="{id}-input" placeholder="{placeholder}" name="{name}" bindTarget="{bindTarget}" tabindex="0" style="text-indent:4px;height:28px;line-height:28px;" class="item-textField" atype="field.input" autocomplete="off" value="" >',
                    '        </div>',
                    '        <div class="item-trigger item-dateButton" atype="triggerfield.trigger" hidefocus="" ></div>',
                    '    </div>',
                    '</div>'];
            } else {
                var domTemplate = ['<div class="grid-input-prompt" id="{id}-prompt">{prompt}</div>',
                    '    <div class="grid-input">',
                    '        <div class="item-wrap item-tf grid-input-component {[this.required ? \'grid-input-required\' : \'\']} {[this.readonly ? \'grid-input-readonly\' : \'\']}" style="width:90px;height:28px;" id="{id}">',
                    '            <div class="element-title un-show-element" id="{id}-element-title" elementType="DatePicker">{[$AE.lang=="ZHS" ? \'日期框\' :\'DatePicker\']}</div>',
                    '            <div class="element-delete-button un-show-element" id="{id}-delete-button"></div>',
                    '            <div class="item-input-wrap" >',
                    '                <div style="height:100%">',
                    '                    <input id="{id}-input" placeholder="{placeholder}" name="{name}" bindTarget="{bindTarget}" tabindex="0" style="text-indent:4px;height:28px;line-height:28px;" class="item-textField" atype="field.input" autocomplete="off" value="" >',
                    '                </div>',
                    '                <div class="item-trigger item-dateButton" atype="triggerfield.trigger" hidefocus="" ></div>',
                    '            </div>',
                    '        </div>',
                    '    </div>',
                    '</div>'];
            }
            var tplt = new Ext.XTemplate(domTemplate);
            return tplt.applyTemplate(sf);
        },
        initRecord:function(record){
        	record.set('dkEnablebesidedays',this.dk_enablebesidedays);
        	record.set('dkEnablemonthbtn',this.dk_enablemonthbtn);
        	record.set('dkViewsize',this.dk_viewsize);
        	record.set('dkRenderer',this.dk_renderer);
        	$AE.DatePicker.superclass.initRecord.call(this, record);
        },
        setAttr:function(record){
        	this.dk_enablebesidedays=record.get('dkEnablebesidedays');
        	this.dk_enablemonthbtn=record.get('dkEnablemonthbtn');
        	this.dk_viewsize=record.get('dkViewsize');
        	this.dk_renderer=record.get('dkRenderer');
        	$AE.DatePicker.superclass.setAttr.call(this, record);
        }
    });

$AE.DateTimePicker = Ext
.extend(
    $AE.DatePicker,
    {
        constructor: function (config) {
            config.prompt = config.prompt || '时间框';
            config.tagType = config.tagType ||'dateTimePicker';
        	config.tagTypeDesc = config.tagTypeDesc ||'日期时间域';
            $AE.DateTimePicker.superclass.constructor.call(this, config);
        },
        getDom: function (gridFlag) {
            var sf = this;
            if (!gridFlag) {
                var domTemplate = [
                    '<div class="input-prompt" id="{id}-prompt">{prompt}</div>',
                    '<div class="item-wrap item-tf input-component {[this.required ? \'input-required\' : \'\']} {[this.readonly ? \'input-readonly\' : \'\']}" style="width:60%;height:28px;" id="{id}">',
                    '    <div class="element-title un-show-element" id="{id}-element-title" elementType="DateTimePicker">{[$AE.lang=="ZHS" ? \'日期时间框\' :\'DateTimePicker\']}</div>',
                    '    <div class="element-delete-button un-show-element" id="{id}-delete-button"></div>',
                    '    <div class="item-input-wrap" >',
                    '        <div style="height:100%">',
                    '            <input id="{id}-input" placeholder="{placeholder}" name="{name}" bindTarget="{bindTarget}" tabindex="0" style="text-indent:4px;height:28px;line-height:28px;" class="item-textField" atype="field.input" autocomplete="off" value="" >',
                    '        </div>',
                    '        <div class="item-trigger item-dateButton" atype="triggerfield.trigger" hidefocus="" ></div>',
                    '    </div>',
                    '</div>'];
            } else {
                var domTemplate = ['<div class="grid-input-prompt" id="{id}-prompt">{prompt}</div>',
                    '    <div class="grid-input">',
                    '        <div class="item-wrap item-tf grid-input-component {[this.required ? \'grid-input-required\' : \'\']} {[this.readonly ? \'grid-input-readonly\' : \'\']}" style="width:90px;height:28px;" id="{id}">',
                    '            <div class="element-title un-show-element" id="{id}-element-title" elementType="DateTimePicker">{[$AE.lang=="ZHS" ? \'日期时间框\' :\'DateTimePicker\']}</div>',
                    '            <div class="element-delete-button un-show-element" id="{id}-delete-button"></div>',
                    '            <div class="item-input-wrap" >',
                    '                <div style="height:100%">',
                    '                    <input id="{id}-input" placeholder="{placeholder}" name="{name}" bindTarget="{bindTarget}" tabindex="0" style="text-indent:4px;height:28px;line-height:28px;" class="item-textField" atype="field.input" autocomplete="off" value="" >',
                    '                </div>',
                    '                <div class="item-trigger item-dateButton" atype="triggerfield.trigger" hidefocus="" ></div>',
                    '            </div>',
                    '        </div>',
                    '    </div>',
                    '</div>'];
            }
            var tplt = new Ext.XTemplate(domTemplate);
            return tplt.applyTemplate(sf);
        },
        initRecord:function(record){
        	record.set('dtkDayrenderer',this.dtk_dayrenderer);
        	record.set('dtkEnablebesidedays',this.dtk_enablebesidedays);
        	record.set('dtkEnablemonthbtn',this.dtk_enablemonthbtn);
        	record.set('dtkViewsize',this.dtk_viewsize);
        	record.set('dtkRenderer',this.dtk_renderer);
        	record.set('dtkHour',this.dtk_hour);
        	record.set('dtkMinute',this.dtk_minute);
        	record.set('dtkSecond',this.dtk_second);
        	$AE.DateTimePicker.superclass.initRecord.call(this, record);
        },
        setAttr:function(record){
        	this.dtk_dayrenderer=record.get('dtkDayrenderer');
        	this.dtk_enablebesidedays=record.get('dtkEnablebesidedays');
        	this.dtk_enablemonthbtn=record.get('dtkEnablemonthbtn');
        	this.dtk_viewsize=record.get('dtkViewsize');
        	this.dtk_dayrenderer=record.get('dtkRenderer');
        	this.dtk_enablebesidedays=record.get('dtkHour');
        	this.dtk_enablemonthbtn=record.get('dtkMinute');
        	this.dtk_viewsize=record.get('dtkSecond');
        	$AE.DateTimePicker.superclass.setAttr.call(this, record);
        }
    });


$AE.Label = Ext
.extend(
    $AE.Field,
    {
        constructor: function (config) {
            config.prompt = config.prompt || '标签';
            config.tagType = config.tagType ||'label';
        	config.tagTypeDesc = config.tagTypeDesc ||'下划线文本域';
            $AE.Label.superclass.constructor.call(this, config);
        },
        getDom: function (gridFlag) {
            var sf = this;
            if (!gridFlag) {
                var domTemplate = [
                    '<div class="input-prompt" id="{id}-prompt">{prompt}</div>',
                    '<div class="input-component {[this.required ? \'input-required\' : \'\']} {[this.readonly ? \'input-readonly\' : \'\']}" style="width:60%;height:28px;line-height:28px;font-weight:normal;border-bottom:1px solid #ccc;" id="{id}">',
                    '    <div class="element-title un-show-element" id="{id}-element-title" elementType="Label">{[$AE.lang=="ZHS" ? \'下划线文本域\' :\'Label\']}</div>',
                    '    <div class="element-delete-button un-show-element" id="{id}-delete-button"></div>',
                    '    <div id="{id}-input"  bindtarget="{bindTarget}" name="{name}" style="height:100%">',
                    '文本内容',
                    '    </div>',
                    '</div>'];
            } else {
                var domTemplate = [
                    '<div class="grid-input-prompt" id="{id}-prompt">{prompt}</div>',
                    '    <div class="grid-input">',
                    '        <div class="grid-input-component {[this.required ? \'grid-input-required\' : \'\']} {[this.readonly ? \'grid-input-readonly\' : \'\']}" style="width:90px;height:28px;" id="{id}">',
                    '            <div class="element-title un-show-element" elementType="Label">{[$AE.lang=="ZHS" ? \'下划线文本域\' :\'Label\']}</div>',
                    '            <div class="element-delete-button un-show-element" id="{id}-delete-button"></div>',
                    '            <div  id="{id}-input" bindtarget="{bindTarget}" name="{name}" style="height:100%">',
                    '文本内容',
                    '            </div>',
                    '        </div>',
                    '    </div>',
                    '</div>'];
            }


            var tplt = new Ext.XTemplate(domTemplate);
            return tplt.applyTemplate(sf);
        },
        initRecord:function(record){
        	record.set('lRenderer',this.l_renderer);
        	$AE.Label.superclass.initRecord.call(this, record);
        },
        setAttr:function(record){
        	this.l_renderer=record.get('lRenderer');
        	$AE.Label.superclass.setAttr.call(this, record);
        }
    });

$AE.Radio = Ext.extend($AE.Field, {
    constructor: function (config) {
    	config.prompt = config.prompt || '单选框';
    	config.tagType = config.tagType ||'radio';
    	config.tagTypeDesc = config.tagTypeDesc ||'单选框';
        $AE.Radio.superclass.constructor.call(this, config);
    },
    getDom: function () {
        var sf = this;
        	var domTemplate = [
                '<div class="input-prompt" id="{id}-prompt">{prompt}</div>',
                '<div class="input-component {[this.required ? \'input-required\' : \'\']} {[this.readonly ? \'input-readonly\' : \'\']}" id="{id}">',
                '    <div class="element-title un-show-element" id="{id}-element-title" elementType="Radio" >{[$AE.lang=="ZHS" ? \'单选框\' :\'Radio\']}</div>',
                '    <div class="element-delete-button un-show-element" id="{id}-delete-button"></div>',
                '    <div class="" style="height:28px;width:-1px;" >',
                '        <div id="{id}-radio" class="item-input-radio"  atype="radio">',
                '        </div>',
                '    </div>',
                '</div>'];
        var tplt = new Ext.XTemplate(domTemplate);
        return tplt.applyTemplate(sf);
    },
    initRecord:function(record){
    	record.set('rLabelexpression',this.r_labelexpression);
    	record.set('rLayoutDesc',this.r_layout_desc);
    	record.set('rLayout',this.r_layout);
    	record.set('rDatasourceDesc',this.r_datasource_desc);
    	record.set('rDatasource',this.r_datasource);
    	record.set('rSyscode',this.r_syscode);
    	record.set('rOptionsLink',this.r_options_link);
    	$AE.Radio.superclass.initRecord.call(this, record);
    },
    setAttr:function(record){
    	this.r_layout_desc=record.get('rLayoutDesc');
    	this.r_layout=record.get('rLayout');
    	this.r_datasource_desc=record.get('rDatasourceDesc');
    	this.r_datasource=record.get('rDatasource');
    	this.r_syscode=record.get('rSyscode');
    	this.r_options_link=record.get('rOptionsLink');
    	this.r_labelexpression=record.get('rLabelexpression');
    	this.setReadOnly(record.get('readonly'));
    	this.setHide(record.get('hidden'));
    	$AE.Radio.superclass.setAttr.call(this, record);
    },
    setReadOnly: function (readonly) {
    	if(readonly=='Y'||readonly=='true'||readonly==true){
    		readonly=true;
    	}else{
    		readonly=false;
    	}
        this.readonly = readonly;
        if (this.readonly) {
        	$('#'+this.id+'-radio').addClass('item-input-radio-readonly');
            //20180108 添加设置input为readonly
            $('#'+this.id+'-radio').attr("readonly", true);
        } else {
        	$('#'+this.id+'-radio').removeClass('item-input-radio-readonly');
            //20180108 删除input的readonly属性
        	$('#'+this.id+'-radio').attr("readonly", false);
        }
    },
    setHide:function(hidden){
    	var hiddenWrap=$('#'+this.id+'-input',this.domEl);
    	if(this.hidden!='true' && hidden=='true'){
    			$('<div class="little-hide-img"></div>').insertBefore($('#'+this.id+'-radio',this.domEl));
    			$('#'+this.id+'-radio',this.domEl).addClass('item-input-radio-hidden');
    	}
    	else if(this.hidden=='true' && hidden!='true'){
    			$('.little-hide-img',this.domEl).remove();
    			$('#'+this.id+'-radio',this.domEl).removeClass('item-input-radio-hidden');
    	}
    	this.hidden=hidden;
    }
});
$AE.Button = Ext.extend($AE.Component,
    {
        constructor: function (config) {
            config.text = config.text || '按钮';
            config.disabled = config.disabled || false;
            this.buttonCode = config.buttonCode;
            this.text = config.text;
            this.operationType = config.operationType;
            this.operationTypeDesc = config.operationTypeDesc;
            this.buttonDesc = config.buttonDesc;
            this.disabled = config.disabled;
            this.hidden = config.hidden;
            this.buttonSequence=config.buttonSequence;
            $AE.Button.superclass.constructor.call(this, config);
        },
        getDom: function (gridFlag) {
            var sf = this;
            if (!gridFlag) {
                var domTemplate = [
                	'<div id="{id}" style="position:relative;width: 80px;margin-top:10px;margin-left: 8px;">',
                	'    <div class="element-title un-show-element" id="{id}-element-title" elementType="Button">{[$AE.lang=="ZHS" ? \'按钮\' :\'Button\']}</div>',
                    '    <div class="element-delete-button un-show-element" id="{id}-delete-button"></div>',
                	'	 <button class="item-button" style="height:30px;width:80px;" id="{id}-button">{text}</button>',
                	'</div>'
                    ];
            } 
            var tplt = new Ext.XTemplate(domTemplate);
            return tplt.applyTemplate(sf);
        },
        initRecord: function (record) {
        	record.set('buttonCode',this.buttonCode);
        	record.set('id',this.id);
        	record.set('text',this.text);
        	record.set('textDisplay',this.text_display);
        	record.set('operationType',this.operationType);
        	record.set('operationTypeDesc',this.operationTypeDesc);
        	record.set('buttonDesc',this.buttonDesc);
        	record.set('disabled',this.disabled);
        	record.set('hidden',this.hidden);
        	record.set('buttonSequence',this.buttonSequence);
        	record.set('buttonType',this.type);
        	record.set('buttonTypeDesc',this.typeDesc);
        	record.set('click',this.click);
        	record.set('width',this.width);
        	record.set('height',this.height);
        	record.set('style',this.style);
        	record.set('templateFlag',this.template_flag);
        	if(this.enable_flag)
        		record.set('enableFlag',this.enable_flag);
        	if(this.btnTag)
        		record.set('buttonTag',this.btnTag);
        	if(this.template_id)
        		record.set('templateId',this.template_id);
        	if(this.button_id)
        		record.set('buttonId',this.button_id);
            if(this.page_id)
                record.set('pageId',this.page_id);
        },
        setAttr: function (record) {
        	this.buttonCode = record.get('buttonCode');
        	if(record.get('id')){
        		this.setId(record.get('id'));
        	}
        	this.text=record.get('text');
        	this.text_display=record.get('textDisplay');
        	this.setText(record.get('textDisplay')||record.get('text'));
        	this.operationType = record.get('operationType');
        	this.operationTypeDesc = record.get('operationTypeDesc');
        	this.buttonDesc = record.get('buttonDesc');
        	this.setDisabled(record.get('disabled'));
        	this.setHide(record.get('hidden'));
        	this.setSequence(record.get('buttonSequence'));
        	this.type=record.get('buttonType');
        	this.typeDesc=record.get('buttonTypeDesc');
        	this.template_flag=record.get('templateFlag');
        	this.template_id=record.get('templateId');
        	this.button_id=record.get('buttonId');
        	this.enable_flag=record.get('enableFlag');
        	this.click=record.get('click');
        	this.width=record.get('width');
        	this.height=record.get('height');
        	this.style=record.get('style');
        	this.btnTag=record.get('buttonTag');
        	this.page_id = record.get('pageId');
        	this.setDataRecord(record);
        	
        	this.onHover();
        },
        setHide:function(hidden){
        	if(hidden=='true'){
        		$('<div class="btn-hide-img"></div>').insertBefore($('button',this.domEl));
        		$('button',this.domEl).addClass('item-button-hide');
        	}else{
        		$('button',this.domEl).removeClass('item-button-hide');
        		$('.btn-hide-img',this.domEl).remove();
        	}
        	this.hidden=hidden;
        },
        setId:function(id){
        	var cmp=$AE.get(this.id);
        	$AE.CmpManager.remove(this.id);
        	$AE.CmpManager.put(id,cmp);
        	
        	if($AE.recycleComponent.get(this.id)){
        		$AE.recycleComponent.remove(this.id);
            	$AE.recycleComponent.put(id,cmp);
        	}
        	//修改对应dataset dom结构的ID
        	$('#'+this.id+'-element-title').attr('id',id+'-element-title');
        	$('#'+this.id+'-delete-button').attr('id',id+'-delete-button');
        	$('#'+this.id+'-button').attr('id',id+'-button');
        	$(this.getDomEl()).attr('id',id);
        	this.id=id;
        },
        setText: function (text){
        	$('#'+this.id+'-button').html(text);
        },
        setDisabled: function (disabled) {
        	
        	this.disabled = disabled;
        	if(disabled=='Y'){
        		$('#'+this.id+'-button').attr('disabled',true);
        		$('#'+this.id+'-button').addClass('item-button-disabled');
        	}
        	else{
        		$('#'+this.id+'-button').removeAttr('disabled');
        		$('#'+this.id+'-button').removeClass('item-button-disabled');
        	}
        },
        getCmpType:function(){
        	return 'Button';
        },
        setSequence:function(sequence){
        	this.buttonSequence=sequence;
        },
        onHover :function(e){
        	var sf=this;
	        	$('#'+this.id).hover(function(){
	        		$('#'+sf.id+'-element-title').removeClass('un-show-element');
	        		if(sf.template_flag=='N' || bpm_type!='page'){
	        			$('#'+sf.id+'-delete-button').removeClass('un-show-element');
	        		}
	        	},function(){
	        		$('#'+sf.id+'-element-title').addClass('un-show-element');
	        		if(sf.template_flag=='N' || bpm_type!='page'){
	        			$('#'+sf.id+'-delete-button').addClass('un-show-element');
	        		}
	        	});
        }
    });

$AE.GridButton = Ext.extend($AE.Button,
	    {
	        constructor: function (config) {
	            config.text = config.text || '按钮';
	            config.disabled = config.disabled || false;
	            this.type=config.type;
	            $AE.GridButton.superclass.constructor.call(this, config);
	        },
	        getDom: function () {
	            var sf = this;
	                var domTemplate = [
	                	'<div id="{id}" style="position:relative;width: 80px;margin-top:10px;margin-left: 8px;">',
	                	'    <div class="element-title un-show-element" id="{id}-element-title" elementType="Button">{[$AE.lang=="ZHS" ? \'按钮\' :\'Button\']}</div>',
	                    '    <div class="element-delete-button un-show-element" id="{id}-delete-button"></div>',
	                	'	 <button class="item-button gridbutton-{type}" auskin={type} style="height:30px;width:80px;" id="{id}-button">{text}</button>',
	                	'</div>'
	                    ];
	            var tplt = new Ext.XTemplate(domTemplate);
	            return tplt.applyTemplate(sf);
	        }
});

$AE.HiddenField = Ext.extend($AE.Field,{
	constructor:function (config) {
		this.name=config.name||'hidden_field';
		config.tagType = config.tagType ||'hidden';
    	config.tagTypeDesc = config.tagTypeDesc ||'隐藏域';
		this.fieldFrom=config.fieldFrom;
		//this.fieldFromType=config.fieldFromType;
		$AE.HiddenField.superclass.constructor.call(this,config);
	},
	getDom: function () {
		var sf=this;
		var domTemplate = [
            '<div class="hidden-icon" id="{id}-icon"></div>',
            '    <div class="element-delete-button" id="{id}-delete-button"></div>',
            '<div class="hidden-prompt" id="{id}">',
            '{name}',
            '</div>'
        ];
		var tplt = new Ext.XTemplate(domTemplate);
        return tplt.applyTemplate(sf);
	},
	setAttr: function (record) {
		var fieldSource=record.get('fieldSource');
		for (var cmpId in $AE.CmpManager.getAll()) {
            if ($AE.get(cmpId).name && fieldSource&& ($AE.get(cmpId).name == fieldSource)) {
            	var cmpType=$AE.get(cmpId).getCmpType();
            	var layoutId=$AE.get(cmpId).layoutId;
            	this.containerId=layoutId;
            	this.fieldFromType=cmpType;
            }
    	}
		if(record.get('id')){
			this.setId(record.get('id'));
		}
        this.setName(record.get('name'));
        if(record.get('field_source')){
        	this.setFieldFrom(record.get('fieldSource'));
        }
       // this.fieldFromType=record.get('field_from_type');
        this.tagType=record.get('tagType');
        this.tagTypeDesc=record.get('tagTypeDesc');
        this.tagCode=record.get('tagCode');
        //$AE.HiddenField.superclass.setAttr.call(this, record);
        if(this.dataRecord){
        	this.setDataRecord(record);
        }
        this.tagSequence=record.get('tagSequence');
        
    },
    initRecord: function (record) {
    	record.set('id', this.id);
        record.set('name', this.name);
        record.set('fieldSource', this.fieldFrom);
        record.set('fieldFrom_type',this.fieldFromType);
        record.set('tagType',this.tagType);
        record.set('tagTypeDesc',this.tagTypeDesc);
        record.set('tagCode',this.tagCode);
        record.set('tagSequence',this.tagSequence);
        //$AE.HiddenField.superclass.initRecord.call(this, record);
    },
    setName:function (name){
    	$('#'+this.id).html(name);
    	this.name = name;
    },
    setId:function(id){
    	var cmp=$AE.get(this.id);
    	$AE.CmpManager.remove(this.id);
    	$AE.CmpManager.put(id,cmp);
    	
    	if($AE.recycleComponent.get(this.id)){
    		$AE.recycleComponent.remove(this.id);
        	$AE.recycleComponent.put(id,cmp);
    	}
    	//修改对应dataset dom结构的ID
    	$('#'+this.id+'-icon').attr('id',id+'-icon');
    	$('#'+this.id+'-delete-button').attr('id',id+'-delete-button');
    	$(this.getDomEl()).html(id);
    	$(this.getDomEl()).attr('id',id);
    	this.id = id;
    },
    setFieldFrom:function (fieldFrom){
    	var sf=this;
    	this.fieldFrom = fieldFrom;
    	//删掉所有的连接线
        $AE.jsPlumbInstance.getConnections({source: this.id}).forEach(function (connection) {
            $AE.jsPlumbInstance.deleteConnection(connection);
        });
        //生成所有与当前DataSet关联的元素的连接线
        for (var cmpId in $AE.CmpManager.getAll()) {
            if ($AE.get(cmpId).name && fieldFrom&& ($AE.get(cmpId).name == fieldFrom)) {
            	var cmpType=$AE.get(cmpId).getCmpType();
            	var layoutId=$AE.get(cmpId).layoutId;
            	if(sf.containerId && sf.containerId!=layoutId){
            		if(sf.dataRecord){
            			sf.dataRecord.ds.remove(sf.dataRecord);
            			sf.dataRecord=null;
            		}
            	}
            	if(!sf.dataRecord){
            		var r=$AE.get(layoutId).getDataRecord();
            		index=r.ds.indexOf(r);
            		layoutDs.locate(index+1,true);
            		var record=tagDs.create();
            		var tagSequence=(record.ds.indexOf(record)+1)*10;
            		sf.containerId=layoutId;
            		sf.fieldFromType=cmpType;
            		sf.dataRecord=record,
            		sf.tagSequence=tagSequence;
            	}
            	if(window.currentHidden){
            		$AE.jsPlumbInstance.connect({
            			source: window.currentHidden.id,
            			target: cmpId
            		});
            	}
            }
        }
    	
    }
});

$AE.PlaceHolder = Ext.extend($AE.Box, {
    constructor: function (config) {
    	config.layoutType=config.layoutType||'PlaceHolder';
    	config.layoutTypeDesc=config.layoutTypeDesc||'动态布局';
        $AE.PlaceHolder.superclass.constructor.call(this, config);
    },
    getDom: function (isGrid,isbtn) {
        var sf = this;
        var domTemplate = [
    		'<table border="0" style="background: #dcd9d9;" class="layout-table layout-component" id="{id}" cellpadding="0" cellspacing="0">',
    		'    <tr><td><div class="element-title un-show-element" id="{id}-element-title" elementType="PlaceHolder">{[$AE.lang=="ZHS" ? \'动态占用\' :\'PlaceHolder\']}</div>',
    		'    </td></tr>',
    		'    <tbody>',
    		'    <tr>',
    		'        <td class="layout-td-cell drag-shell" style="padding:3px">',
    		'           <div class="drag-container row" id="{id}-drag-container"></div>',
    		'        </td>',
    		'    </tr>',
    		'    </tbody>',
    		'</table>'];
        if(isGrid){
        	 if(isbtn){
             	var domTemplate = [
             		'<table border="0" style="background: #dcd9d9;float:left;margin-left:2px;width: 40px;" class="" id="{id}" cellpadding="0" cellspacing="0">',
             		'    <tr><td><div class="element-title un-show-element" id="{id}-element-title" elementType="PlaceHolder">{[$AE.lang=="ZHS" ? \'动态占用\' :\'PlaceHolder\']}</div>',
             		'    </td></tr>',
             		'    <tbody>',
             		'    <tr>',
             		'        <td class="layout-td-cell drag-shell" style="">',
             		'           <div class="" style="min-height:37px;" id="{id}-button-container"></div>',
             		'        </td>',
             		'    </tr>',
             		'    </tbody>',
             		'</table>'];

             }else{
            	 domTemplate = [
            		 '<table border="0" style="background: #dcd9d9;position: relative;width:100%;" id="{id}" cellpadding="0" cellspacing="0">',
            		 '    <tr><td><div class="element-title un-show-element" style="top: 0px;" id="{id}-element-title" elementType="PlaceHolder">{[$AE.lang=="ZHS" ? \'动态占用\' :\'PlaceHolder\']}</div>',
            		 '    </td></tr>',
            		 '    <tbody>',
            		 '    <tr>',
            		 '        <td class="layout-td-cell drag-shell" style="padding:3px">',
            		 '           <div class="drag-container row" id="{id}-drag-container" style="height:69px;"></div>',
            		 '        </td>',
            		 '    </tr>',
            		 '    </tbody>',
            		 '</table>'];
             }
        }
       
        var tplt = new Ext.XTemplate(domTemplate);
        return tplt.applyTemplate(sf);
    }
});

$AE.Grid = Ext.extend($AE.Box, {
    constructor: function (config) {
    	config.layoutType=config.layoutType||'grid';
    	config.layoutTypeDesc=config.layoutTypeDesc||'列表';
    	this.navbar=config.navbar;
    	this.selectable=config.selectable;
    	this.selectionModel=config.selectionModel;
    	this.selectionModelDesc=config.selectionModelDesc;
    	this.gridButtons=[];
        $AE.Grid.superclass.constructor.call(this, config);
    },
    getDom: function () {
        var sf = this;
        var domTemplate = [
            '<div class="grid-content layout-table layout-component" id="{id}">',
            '    <div class="element-title un-show-element" id="{id}-element-title" elementType="Grid">{[$AE.lang=="ZHS" ? \'列表\' :\'Grid\']}</div>',
            '    <div class="element-delete-button un-show-element" id="{id}-delete-button"></div>',
            '    <div class="grid-header-border"></div>',
            '    <div class="grid-select-container" id="{id}-select-container"></div>',
            '    <div class="grid-button-container" id="{id}-button-container"></div>',
            '    <div class="grid-navbar-container" id="{id}-navbar-container"></div>',
            '    <div class="grid-drag-container" id="{id}-drag-container"></div>',
            '</div>'
        ];
        var tplt = new Ext.XTemplate(domTemplate);
        return tplt.applyTemplate(sf);
    },
    setNavbar:function(navbar){
    	var el=this.getDomEl();
    	var navbarContainer=$('.grid-navbar-container',el);
    	this.navbar=navbar;
    	if(navbar=='Y'||navbar=='true'){
    		if(navbarContainer.children().length==0){
    			var html = (new $AE.GridNavBar({})).getDom();
    			navbarContainer.append('<div aetype="GridNavBar" style="opacity: 1;">'+html+'</div>');
    		}
    	}
    	else{
    		$(navbarContainer).empty();
    	}
    },
    setSelectable:function(selectable){
    	var el=this.getDomEl();
    	var selectContainer=$('.grid-select-container',el);
    	this.selectable=selectable;
    	if(selectable=='Y'){
    		this.selectionModel='multiple';
    		this.selectionModelDesc='多选';
    		if(selectContainer.children().length==0){
    			var html = (new $AE.GridSelectBox({})).getDom();
    			selectContainer.append('<div aetype="GridSelectBox" style="opacity: 1;">'+html+'</div>');
    		}
    	}
    	else{
    		$(selectContainer).empty();
    	}
    },
    setSelectionModel:function(selectionModel){
    	var el=this.getDomEl();
    	var selectContainer=$('.grid-select-container',el);
    	this.selectionModel=selectionModel;
    	if(this.selectable=='Y'){
    		if(selectionModel=='single'){
    			$(selectContainer).empty();
    			var html = (new $AE.GridRadioBox({})).getDom();
    			selectContainer.append('<div aetype="GridRadioBox" style="opacity: 1;">'+html+'</div>');
    		}
    		else{
    			$(selectContainer).empty();
    			var html = (new $AE.GridSelectBox({})).getDom();
    			selectContainer.append('<div aetype="GridSelectBox" style="opacity: 1;">'+html+'</div>');
    		}
    	}
    },
    addButton:function(record){
    	var type=record.get('buttonType');
    	var html='';
    	var el=this.getDomEl();
    	var buttonContainer=$('.grid-button-container',el);
    	var layoutId=$(buttonContainer).attr('id').replace('-button-container', '');
    	var index=0;
    	//var record=null;
        if($AE.get(layoutId)){
        	var r=$AE.get(layoutId).getDataRecord();
        	index=r.ds.indexOf(r);
        	layoutDs.locate(index+1,true);
        	//有问题
        	if(record.isNew){
        		record=layoutBtnDs.create();
        	}
        }
    	var buttonChildren='';
    	var aetype='';
    	if(record){
    		switch (type){
    			case 'add':
    					record.set('buttonType','add');
    					record.set('buttonTypeDesc','新增');
    					html=(new $AE.GridAddButton({
    						dataRecord:record
    					})).getDom();
    					aetype='GridAddButton';
    					buttonChildren=$('[aetype="GridAddButton"]',buttonContainer);break;
    			case 'delete':
    						record.set('buttonType','delete');
    						record.set('buttonTypeDesc','删除');
    						html=(new $AE.GridDeleteButton({
    							dataRecord:record
    						})).getDom();
    						aetype='GridDeleteButton';
    						buttonChildren=$('[aetype="GridDeleteButton"]',buttonContainer);break;
    			case 'save':
    					record.set('buttonType','save');
    					record.set('buttonTypeDesc','保存');
    					html=(new $AE.GridSaveButton({
    						dataRecord:record
    					})).getDom();
    					aetype='GridSaveButton';
    					buttonChildren=$('[aetype="GridSaveButton"]',buttonContainer);break;
    			case 'clear':
    					record.set('buttonType','clear');
    					record.set('buttonTypeDesc','清除');
    					html=(new $AE.GridClearButton({
    						dataRecord:record
    					})).getDom();
    					aetype='GridClearButton';
    					buttonChildren=$('[aetype="GridClearButton"]',buttonContainer);break;
                case 'excel':
                    record.set('buttonType','excel');
                    record.set('buttonTypeDesc','导出');
                    html=(new $AE.GridExcelButton({
                        dataRecord:record
                    })).getDom();
                    aetype='GridExcelButton';
                    buttonChildren=$('[aetype="GridExcelButton"]',buttonContainer);break;
    		}
    		if(buttonChildren.length==0){
    			buttonContainer.append('<div aetype="'+aetype+'" style="opacity: 1;">'+html+'</div>');
    		}
    		this.gridButtons.push(record);
    	}
    },
    removeButton:function(record){
    	var type=record.get('buttonType');
    	var el=this.getDomEl();
    	var buttonContainer=$('.grid-button-container',el);
    	var aetype='';
    	switch (type){
			case 'add':aetype='GridAddButton';break;
			case 'delete':aetype='GridDeleteButton';break;
			case 'save':aetype='GridSaveButton';break;
			case 'clear':aetype='GridClearButton';break;
            case 'excel':aetype='GridExcelButton';break;
    	}
    	$('[aetype="'+aetype+'"]',buttonContainer).remove();//删除grid上的按钮
    },
    setAttr: function (record) {
		this.setNavbar(record.get('navbar'));
		var gridDataSet=record.get('dataset');
		if(gridDataSet){
			var dsCmp=$AE.get(gridDataSet);
			if(dsCmp){
				var selectable=dsCmp.getSelectable();
				var selectionmodel=dsCmp.getSelectionModel();
				this.setSelectable(selectable);
				if(selectable){
					this.setSelectionModel(selectionmodel);
			        //this.selectionModelDesc=record.get('selectionModelDesc');
				}
			}
		}
        this.setBindTarget(record.get('dataset'));
        this.column = record.get('columnNum');
    	this.layoutCode=record.get('layoutCode');
    	this.layoutSequence=record.get('layoutSequence');
    	this.layoutType=record.get('layoutType');
    	this.layoutTypeDesc=record.get('layoutTypeDesc');
    	if(record.get('id')){
    		this.setId(record.get('id'));
    	}
        
        this.setDataRecord(record);
        
        $AE.Grid.superclass.setAttr.call(this, record);
    },
    initRecord: function (record) {
    	record.set('navbar', this.navbar);
        record.set('selectable', this.selectable);
        record.set('selectionModel', this.selectionModel);
        record.set('selectionModelDesc',this.selectionModelDesc);
        
        $AE.Grid.superclass.initRecord.call(this, record);
    },
    setButtons:function(buttons,isScreenEditor){
    	for(var i=0;i<this.gridButtons.length;i++){
    		if(jQuery.inArray(this.gridButtons[i], buttons)==-1){
    			if(isScreenEditor){
    				this.gridButtons[i].set('enableFlag','N');
    				this.removeButton(this.gridButtons[i]);
    			}else{
    				this.removeButton(this.gridButtons[i]);
    			}
    		}
    	}
    	for(var i=0;i<buttons.length;i++){
    		if(jQuery.inArray(buttons[i],this.gridButtons)==-1){
    			this.addButton(buttons[i]);
    		}
    	}
    	this.gridButtons=buttons;
    },
    getButtons:function(){
    	return this.gridButtons;
    },
    setDataRecord:function(record){
    	var fields=this.dataRecord.ds.fields;
    	for(var key in fields){
    		var field = fields[key];
            if(field.type == 'dataset'&&field.pro['name']=='buttons'){                
                var ds = field.pro['dataset'];
                var buttonRecords=ds.getAll();
                if(buttonRecords.length<=this.gridButtons.length){
                	for(var i=0;i<buttonRecords.length;i++){
                		for(var k in ds.fields){
                			if(this.gridButtons.length>0 && this.gridButtons[i].get(k)){
                				buttonRecords[i].set(k,this.gridButtons[i].get(k));
                			}
                		}
                	}
                }else{
                	for(var i=0;i<this.gridButtons.length;i++){
                		for(var k in ds.fields){
                			if(this.gridButtons.length>0 && this.gridButtons[i].get(k)){
                				buttonRecords[i].set(k,this.gridButtons[i].get(k));
                			}
                		}
                	}
                }
            }else{
            	if(record.get(key)){
            		this.dataRecord.set(key,record.get(key));
            	}
            }
    	}
    },
    setId:function(id){
    	var cmp=$AE.get(this.id);
    	$AE.CmpManager.remove(this.id);
    	$AE.CmpManager.put(id,cmp);
    	
    	if($AE.recycleComponent.get(this.id)){
    		$AE.recycleComponent.remove(this.id);
        	$AE.recycleComponent.put(id,cmp);
    	}
    	//修改对应dataset dom结构的ID
    	$('#'+this.id+'-element-title').attr('id',id+'-element-title');
    	$('#'+this.id+'-delete-button').attr('id',id+'-delete-button');
    	$('#'+this.id+'-select-container').attr('id',id+'-select-container');
    	$('#'+this.id+'-button-container').attr('id',id+'-button-container');
    	$('#'+this.id+'-navbar-container').attr('id',id+'-navbar-container');
    	$('#'+this.id+'-drag-container').attr('id',id+'-drag-container');
    	$(this.getDomEl()).attr('id',id);
    	
    	this.id=id;
    }
});

$AE.GridSelectBox = Ext.extend($AE.Component, {
    constructor: function (config) {
        $AE.GridSelectBox.superclass.constructor.call(this, config);
    },
    getDom: function () {
        var sf = this;
        var domTemplate = [
            '<div class="grid-select-content">',
            '    <div class="grid-select-header-content"><div class="grid-select-header-select"></div></div>',
            '    <div class="grid-select-line-content"><div class="grid-select-line-select"></div></div>',
            '</div>'
        ];
        var tplt = new Ext.XTemplate(domTemplate);
        return tplt.applyTemplate(sf);
    }
});

$AE.GridRadioBox = Ext.extend($AE.Component, {
    constructor: function (config) {
        $AE.GridSelectBox.superclass.constructor.call(this, config);
    },
    getDom: function () {
        var sf = this;
        var domTemplate = [
            '<div class="grid-radio-content">',
            '    <div class="grid-radio-header-content"><div class="grid-radio-header-radio"></div></div>',
            '    <div class="grid-radio-line-content"><div class="grid-radio-line-radio"></div></div>',
            '</div>'
        ];
        var tplt = new Ext.XTemplate(domTemplate);
        return tplt.applyTemplate(sf);
    }
});

$AE.GridNavBar = Ext.extend($AE.Component, {
    constructor: function (config) {
        $AE.GridNavBar.superclass.constructor.call(this, config);
    },
    getDom: function () {
        var sf = this;
        var domTemplate = [
            '<div class="item-toolbar grid-navbar"  style="width:-1px;">',
            '    <div>',
            '        <table class="item-btn item-btn-icon infor-btn" text="" auskin="" style="float:right;margin-left:5px;margin-right:1px;margin-top:8px;;width:16px;"  cellspacing="0">',
            '            <tbody>',
            '            <tr>',
            '                <td class="item-btn-ml"><i></i></td>',
            '                <td class="item-btn-mc">',
            '                    <button tabindex="0" type="button" hidefocus="true" atype="btn" title="刷新" style="height:24px;" class="nav-refresh"><span class="item-icon"></span>',
            '                        <div class="item-icon-text" style="line-height:24px;height:24px;background-position:0px -125px;">&nbsp;</div>',
            '                    </button>',
            '                </td>',
            '                <td class="item-btn-mr"><i></i></td>',
            '            </tr>',
            '            </tbody>',
            '        </table>',
            '        <div style="float:left;margin-right:1px;margin-top:9px;float:right;" class="item-label">显示条目 1 - 5 共 99</div>',
            '        <div  style="float:left;margin-right:1px;margin-top:9px;float:right;margin-left:1px;margin-right:5px;" class="item-label">每页</div>',
            '        <div class="item-wrap item-tf" isgr style="width:60px;height:25px;float:right;margin-right:1px;margin-top:7px;margin-left:5px;" >',
            '            <div class="item-input-wrap" >',
            '                <div style="height:100%">',
            '                    <input placeholder="" tabindex="0"  style="text-indent:4px;height:25px;line-height:25px;" class="item-textField" atype="field.input" autocomplete="off" value="">',
            '                </div>',
            '                <div class="item-trigger item-comboButton" hidefocus=""></div>',
            '            </div>',
            '',
            '        </div>',
            '        <div style="float:left;margin-right:1px;margin-top:9px;margin-left:5px;margin-right:5px" class="item-label"></div>',
            '        <table class="item-btn item-btn-icon infor-btn " text="&nbsp;" auskin="" style="float:right;margin-left:5px;margin-right:1px;margin-top:8px;;width:16px;"  cellspacing="0">',
            '            <tbody>',
            '            <tr>',
            '                <td class="item-btn-ml"><i></i></td>',
            '                <td class="item-btn-mc">',
            '                    <button tabindex="0" type="button" hidefocus="true" atype="btn" title="最后页" style="height:24px;" class="nav-lastpage"><span class="item-icon"></span>',
            '                        <div class="item-icon-text" style="line-height:24px;height:24px;background-position:0px -93px">&nbsp;</div>',
            '                    </button>',
            '                </td>',
            '                <td class="item-btn-mr"><i></i></td>',
            '            </tr>',
            '            </tbody>',
            '        </table>',
            '        <table class="item-btn item-btn-icon infor-btn" text="&nbsp;" auskin="" style="float:right;margin-left:5px;margin-right:1px;margin-top:8px;;width:16px;"  cellspacing="0">',
            '            <tbody>',
            '            <tr>',
            '                <td class="item-btn-ml"><i></i></td>',
            '                <td class="item-btn-mc">',
            '                    <button tabindex="0" type="button" hidefocus="true" atype="btn" title="下一页" style="height:24px;" class="nav-nextpage"><span class="item-icon"></span>',
            '                        <div class="item-icon-text" style="line-height:24px;height:24px;background-position:0px -61px;">&nbsp;</div>',
            '                    </button>',
            '                </td>',
            '                <td class="item-btn-mr"><i></i></td>',
            '            </tr>',
            '            </tbody>',
            '        </table>',
            '        <div class="sep" style="float:right;margin-right:5px;margin-top:10px;margin-left:5px;"></div>',
            '        <div style="float:left;margin-right:1px;margin-top:9px;float:right;margin-left:5px" class="item-label">共1页</div>',
            '        <div class="item-wrap item-tf" isgr style="width:30px;height:25px;float:right;margin-right:1px;margin-top:7px;margin-left:5px;" >',
            '            <div class="item-input-wrap" style="">',
            '                <input tabindex="0" style="height:25px;line-height:25px;ime-mode:disabled;" type="input" class="item-textField item-numberField" autocomplete="off">',
            '            </div>',
            '        </div>',
            '        <div style="float:left;margin-right:1px;margin-top:9px;float:right;margin-left:2px;margin-right:2px" class="item-label">页数</div>',
            '        <div class="sep" style="float:right;margin-right:5px;margin-top:10px;margin-left:5px;"></div>',
            '        <table class="item-btn item-btn-icon infor-btn " text="&nbsp;" auskin="" style="float:right;margin-left:5px;margin-right:1px;margin-top:8px;;width:16px;"  cellspacing="0">',
            '            <tbody>',
            '            <tr>',
            '                <td class="item-btn-ml"><i></i></td>',
            '                <td class="item-btn-mc">',
            '                    <button tabindex="0" type="button" hidefocus="true"  title="上一页" style="height:24px;" class="nav-prepage"><span class="item-icon"></span>',
            '                        <div class="item-icon-text" style="line-height:24px;height:24px;background-position:0px -29px;">&nbsp;</div>',
            '                    </button>',
            '                </td>',
            '                <td class="item-btn-mr"><i></i></td>',
            '            </tr>',
            '            </tbody>',
            '        </table>',
            '        <table class="item-btn item-btn-icon infor-btn " text="&nbsp;" style="float:right;margin-left:5px;margin-right:1px;margin-top:8px;;width:16px;"  cellspacing="0">',
            '            <tbody>',
            '            <tr>',
            '                <td class="item-btn-ml"><i></i></td>',
            '                <td class="item-btn-mc">',
            '                    <button tabindex="0" type="button" hidefocus="true" title="第一页" style="height:24px;" class="nav-firstpage"><span class="item-icon"></span>',
            '                        <div class="item-icon-text" style="line-height:24px;height:24px;background-position:0px 3px;">&nbsp;</div>',
            '                    </button>',
            '                </td>',
            '                <td class="item-btn-mr"><i></i></td>',
            '            </tr>',
            '            </tbody>',
            '        </table>',
            '    </div>',
            '</div>'
        ];
        var tplt = new Ext.XTemplate(domTemplate);
        return tplt.applyTemplate(sf);
    }
});

$AE.GridAddButton = Ext.extend($AE.Component, {
    constructor: function (config) {
        $AE.GridAddButton.superclass.constructor.call(this, config);
    },
    getDom: function () {
        var sf = this;
        var domTemplate = [
            '<div>',
            '	<table auskin="icon" cellspacing="0" class="item-btn btn-add item-btn-icon-text infor-btn" style="float:left;margin-right:1px;margin-top:3px;;width:80px;" text="新增">',
            '		<tbody>',
            '			<tr>',
            '				<td class="item-btn-ml">',
            '					<i/>',
            '				</td>',
            '				<td class="item-btn-mc">',
            '					<button atype="btn" class="grid-add3" hidefocus="true" style="height:24px;" tabindex="0" title="" type="button">',
            '						<span class="item-icon"/>',
            '						<div class="item-icon-text" style="line-height:24px;height:24px;">新增</div>',
            '					</button>',
            '				</td>',
            '				<td class="item-btn-mr">',
            '					<i/>',
            '				</td>',
            '			</tr>',
            '		</tbody>',
            '	</table>',
            '</div>'
        ];
        var tplt = new Ext.XTemplate(domTemplate);
        return tplt.applyTemplate(sf);
    }
});

$AE.GridDeleteButton = Ext.extend($AE.Component, {
    constructor: function (config) {
        $AE.GridDeleteButton.superclass.constructor.call(this, config);
    },
    getDom: function () {
        var sf = this;
        var domTemplate = [
            '<div>',
            '<table  auskin="icon" cellspacing="0" class="item-btn btn-delete item-btn-icon-text infor-btn" style="float:left;margin-right:1px;margin-top:3px;;width:80px;" text="删除">',
            '<tbody>',
            '<tr>',
            '<td class="item-btn-ml">',
            '<i/>',
            '</td>',
            '<td class="item-btn-mc">',
            '<button atype="btn" class="grid-delete3" hidefocus="true" style="height:24px;" tabindex="0" title="" type="button">',
            '<span class="item-icon"/>',
            '<div class="item-icon-text" style="line-height:24px;height:24px;">删除</div>',
            '</button>',
            '</td>',
            '<td class="item-btn-mr">',
            '<i/>',
            '</td>',
            '</tr>',
            '</tbody>',
            '</table>',
            '</div>'
        ];
        var tplt = new Ext.XTemplate(domTemplate);
        return tplt.applyTemplate(sf);
    }
});

$AE.GridSaveButton = Ext.extend($AE.Component, {
    constructor: function (config) {
        $AE.GridSaveButton.superclass.constructor.call(this, config);
    },
    getDom: function () {
        var sf = this;
        var domTemplate = [
            '<div>',
            '<table  auskin="icon" cellspacing="0" class="item-btn btn-save item-btn-icon-text infor-btn" style="float:left;margin-right:1px;margin-top:3px;;width:80px;" text="保存">',
            '<tbody>',
            '<tr>',
            '<td class="item-btn-ml">',
            '<i/>',
            '</td>',
            '<td class="item-btn-mc">',
            '<button atype="btn" class="grid-save3" hidefocus="true" style="height:24px;" tabindex="0" title="" type="button">',
            '<span class="item-icon"/>',
            '<div class="item-icon-text" style="line-height:24px;height:24px;">保存</div>',
            '</button>',
            '</td>',
            '<td class="item-btn-mr">',
            '<i/>',
            '</td>',
            '</tr>',
            '</tbody>',
            '</table>',
            '</div>'
        ];
        var tplt = new Ext.XTemplate(domTemplate);
        return tplt.applyTemplate(sf);
    }
});

$AE.GridExcelButton = Ext.extend($AE.Component, {
    constructor: function (config) {
        $AE.GridExcelButton.superclass.constructor.call(this, config);
    },
    getDom: function () {
        var sf = this;
        var domTemplate = [
            '<div>',
            '<table  auskin="icon" cellspacing="0" class="item-btn btn-save item-btn-icon-text infor-btn" style="float:left;margin-right:1px;margin-top:3px;;width:80px;" text="保存">',
            '<tbody>',
            '<tr>',
            '<td class="item-btn-ml">',
            '<i/>',
            '</td>',
            '<td class="item-btn-mc">',
            '<button atype="btn" class="grid-excel3" hidefocus="true" style="height:24px;" tabindex="0" title="" type="button">',
            '<span class="item-icon"/>',
            '<div class="item-icon-text" style="line-height:24px;height:24px;">导出</div>',
            '</button>',
            '</td>',
            '<td class="item-btn-mr">',
            '<i/>',
            '</td>',
            '</tr>',
            '</tbody>',
            '</table>',
            '</div>'
        ];
        var tplt = new Ext.XTemplate(domTemplate);
        return tplt.applyTemplate(sf);
    }
});

$AE.GridClearButton = Ext.extend($AE.Component, {
    constructor: function (config) {
        $AE.GridClearButton.superclass.constructor.call(this, config);
    },
    getDom: function () {
        var sf = this;
        var domTemplate = [
            '<div>',
            '<table  auskin="icon" cellspacing="0" class="item-btn btn-clear item-btn-icon-text infor-btn" style="float:left;margin-right:1px;margin-top:3px;;width:80px;" text="保存">',
            '<tbody>',
            '<tr>',
            '<td class="item-btn-ml">',
            '<i/>',
            '</td>',
            '<td class="item-btn-mc">',
            '<button atype="btn" class="grid-clear3" hidefocus="true" style="height:24px;" tabindex="0" title="" type="button">',
            '<span class="item-icon"/>',
            '<div class="item-icon-text" style="line-height:24px;height:24px;">保存</div>',
            '</button>',
            '</td>',
            '<td class="item-btn-mr">',
            '<i/>',
            '</td>',
            '</tr>',
            '</tbody>',
            '</table>',
            '</div>'
        ];
        var tplt = new Ext.XTemplate(domTemplate);
        return tplt.applyTemplate(sf);
    }
});


$AE.DataSet = Ext.extend($AE.Component, {
    constructor: function (config) {
        this.datasetUsage = config.datasetUsage;
        this.datasetUsageName = config.datasetUsageName;
        this.dataSourceTypeName = config.dataSourceTypeName;
        this.dataSourceType = config.dataSourceType;
        this.dataSource = config.dataSource;
        this.model = config.model;
        this.lookUpCode = config.lookUpCode;
        this.queryUrl = config.queryUrl;
        this.submitUrl = config.submitUrl;
        this.bindTarget = config.bindTarget;
        this.bindName = config.bindName;
        this.queryDataset = config.queryDataset;
        this.selectable = config.selectable;
        this.selectionModel = config.selectionModel;
        this.selectionModelName = config.selectionModelName;
        this.autoCreate = config.autoCreate;
        this.autoQuery = config.autoQuery;
        this.loadData = config.loadData;
        $AE.DataSet.superclass.constructor.call(this, config);
    },
    getDom: function () {
        var sf = this;
        var domTemplate = [
            '<div class="dataset-icon" id="{id}-icon"></div>',
            '    <div class="element-delete-button" id="{id}-delete-button"></div>',
            '<div class="dataset-prompt" id="{id}">',
            '{id}',
            '</div>'
        ];
        var tplt = new Ext.XTemplate(domTemplate);
        return tplt.applyTemplate(sf);
    },
    setAttr: function (record) {
    	if(record.get('datasetId')){
    		this.setId(record.get('datasetId'));
    	}
        this.datasetUsage = record.get('datasetUsage');
        this.datasetUsageName = record.get('datasetUsageName');
        this.dataSourceTypeName = record.get('datasourceTypeName');
        this.dataSourceType = record.get('datasourceType');
        this.dataSource = record.get('datasource');
        this.model = record.get('model');
        this.lookUpCode = record.get('lookupcode');
        this.queryUrl = record.get('queryurl');
        this.submitUrl = record.get('submiturl');
        this.bindTarget = record.get('bindtarget');
        this.bindName = record.get('bindname');
        this.queryDataset = record.get('querydataset');
        this.setSelectable(record.get('selectable'));
        this.setSelectionModel(record.get('selectionmodel'));
        this.selectionModelName = record.get('selectionmodelName');
        this.autoCreate = record.get('autocreate');
        this.autoQuery = record.get('autoquery');
        this.loadData = record.get('loaddata');
        this.autoCount = record.get('autocount');
        this.screen_id=record.get('screenId');
        this.enable_flag=record.get('enableFlag');
        this.setDataRecord(record);
    },
    initRecord: function (record) {
    	record.set('datasetId', this.id);
        record.set('datasetUsage', this.datasetUsage);
        record.set('datasetUsageName', this.datasetUsageName);
        record.set('datasourceTypeName', this.dataSourceTypeName);
        record.set('datasourceType', this.dataSourceType);
        record.set('datasource', this.dataSource);
        record.set('model', this.model);
        record.set('lookupcode', this.lookUpCode);
        record.set('queryurl', this.queryUrl);
        record.set('submiturl', this.submitUrl);
        record.set('bindtarget', this.bindTarget);
        record.set('bindname', this.bindName);
        record.set('querydataset', this.queryDataset);
        record.set('selectable', this.selectable);
        record.set('selectionmodel', this.selectionModel);
        record.set('selectionmodelName', this.selectionModelName);
        record.set('autocreate', this.autoCreate);
        record.set('autoquery', this.autoQuery);
        record.set('loaddata', this.loadData);
        if(this.screen_id)
        	record.set('screenId',this.screen_id);
        record.set('enableFlag',this.enable_flag);
    },
    setId:function(id){
    	var cmp=$AE.get(this.id);
    	$AE.CmpManager.remove(this.id);
    	$AE.CmpManager.put(id,cmp);
    	
    	if($AE.recycleComponent.get(this.id)){
    		$AE.recycleComponent.remove(this.id);
        	$AE.recycleComponent.put(id,cmp);
    	}
    	//修改对应dataset dom结构的ID
    	$('#'+this.id+'-icon').attr('id',id+'-icon');
    	$('#'+this.id+'-delete-button').attr('id',id+'-delete-button');
    	$(this.getDomEl()).html(id);
    	$(this.getDomEl()).attr('id',id);
    	
    	if (window.currentDataSet && window.currentDataSet.id == this.id) {
            $AE.jsPlumbInstance.getConnections({target:this.id}).forEach(function (connection) {
                $AE.jsPlumbInstance.deleteConnection(connection);
            });
          //生成所有与当前DataSet关联的元素的连接线
            for (var cmpId in $AE.CmpManager.getAll()) {
                if ($AE.get(cmpId).bindTarget == id) {
                    $AE.jsPlumbInstance.connect({
                        source: cmpId,
                        target: id
                    });
                }
            }
        }
    	this.id = id;
    },
    setSelectable:function(selectable){
    	if (window.currentDataSet && window.currentDataSet.id == this.id) {
            $AE.jsPlumbInstance.getConnections({target:this.id}).forEach(function (connection) {
            	var cmpId=connection.sourceId;
            	var cmp=$AE.get(cmpId);
            	var aetype=$('#'+cmpId).parent().attr('aetype');
            	if(aetype&&aetype=='Grid'){
            		cmp.setSelectable(selectable);
            	}
            });
    	}
    	this.selectable = selectable;
    },
    setSelectionModel:function(selectionModel){
    	if(this.selectable=='Y'){
    		if (window.currentDataSet && window.currentDataSet.id == this.id) {
                $AE.jsPlumbInstance.getConnections({target:this.id}).forEach(function (connection) {
                	var cmpId=connection.sourceId;
                	var cmp=$AE.get(cmpId);
                	var aetype=$('#'+cmpId).parent().attr('aetype');
                	if(aetype&&aetype=='Grid'){
                		cmp.setSelectionModel(selectionModel);
                	}
                });
        	}
    	}
    	this.selectionModel=selectionModel;
    },
    getCmpType:function(){
    	return 'DataSet';
    },
    getSelectable:function(){
    	return this.selectable;
    },
    getSelectionModel:function(){
    	return this.selectionModel;
    }
});
