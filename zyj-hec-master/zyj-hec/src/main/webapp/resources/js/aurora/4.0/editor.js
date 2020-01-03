//自动生成组件ID
function getNewId(ds,property,value){
	var num=0;
	var str='';
	if(bpm_type!='page'){
		var records=ds.getAll();
		if(property){
			for(var i=0;i<records.length;i++){
				if(records[i].get(property)==value){
					num++;
				}
			}
			str=value.toLowerCase()+'_'+(num<10?('0'+num):num)+'_id';
		}else{
			//页面级按钮ID生成
			num=records.length>0?records.length:1;
			str='btn_'+(num<10?('0'+num):num)+'_id'
		}
		if(bpm_type=='tplt' && template_code){
			str=template_code+'_'+str;
		}
	}
	return str;
}

//生成新添加组件的序号
function getCmpSequence(ds,sequence_type){
	var records=ds.getAll();
	var s=10;
	if(records && records.length>1){
		s=s+records[records.length-2].get(sequence_type);
	}
	return s;
}

//拖拽调整组件顺序后，sequence重置
function dragSortableHandle(sf,ui){
	jsPlumbRedraw();
	//组件顺序调整
	var arr = $(sf).sortable('toArray');
	//setSequence(arr,$AE.get(cmpId));
	setSequence(arr);
	//窗口大小修改后，scroll重置
	$("#editor-container").getNiceScroll().resize();
	$("#dataset-container-shell").getNiceScroll().resize();
	$(".grid-drag-container").getNiceScroll().resize();
	
	FirstOpenDetail(ui);
}

function FirstOpenDetail(ui){
	var cmpId='';
	if(ui.item && $('.element-title',ui.item).attr('id'))
		cmpId=$('.element-title',ui.item).attr('id').replace('-element-title','');
	//拉入组件后，直接跳出组件明细配置页面
	//顺序有问题，先执行打开明细，在执行sequence重置
	var component=$AE.get(cmpId);
	if(component && component.getDataRecord().isNew){
		var url=componentScreen[$('#'+component.id+'-element-title').attr('elementType')] + '?componentId=' + component.id +'&bpm_type='+bpm_type;
		if(component && component.layoutId && $AE.get(component.layoutId)){
			url=url+'&layoutType='+$AE.get(component.layoutId).getCmpType().toLowerCase();//获取layout布局组件类型，用于view-config
		}
		setTimeout(function(){
			new Aurora.Window({
				url: url,
				id: $('#'+component.id+'-element-title').attr('elementType') + '_window',
				width: 800,
				side: 'right'
			}).on('close',function(){
				$('.ui-sortable').removeClass('drag-container-focus');
			});
		},200); 
	}
}
function hoverEvtInit(sf){
	var id=$('.element-title:first',$(sf)).attr('id').replace('-element-title','');
	$('#'+id).hover(function(){
		$('#'+id+'-element-title').removeClass('un-show-element');
		$('#'+id+'-delete-button').removeClass('un-show-element');
	},function(){
		$('#'+id+'-element-title').addClass('un-show-element');
		$('#'+id+'-delete-button').addClass('un-show-element');
	});
}
function jsPlumbRedraw() {
    //重绘连接线
    $AE.jsPlumbInstance.setSuspendDrawing(false, true);
}
function jsPlumbDeleteAll(target,source){
	$('.dataset-shell').removeClass('dataset-shell-focus');
	$('.drag-container-focus').removeClass('drag-container-focus');
	//删掉所有的连接线
    $AE.jsPlumbInstance.getConnections({target:target,source:source}).forEach(function (connection) {
        $AE.jsPlumbInstance.deleteConnection(connection);
    });
}

function saveSequence(record,url){
	if(url){
		Aurora.request({
			url: url,
			para: record.data,
			success: function(result) {
			},
			scope: this
		});
	}
}
//组件顺序调整 两种组件混合时，sequence怎么给？
function setSequence(arr){
	var url='';
	if(arr.length>1){
		var layoutArr=[],tagArr=[],btnArr=[],layoutLength=layoutDs.getAll().length;
		for(var i=0;i<arr.length;i++){
			if(arr[i]){
				var cmpid=$('.element-title:first',$('#'+arr[i])).attr('id').replace('-element-title','');
				var cmp=$AE.get(cmpid);
				if(cmp){
					var record=cmp.getDataRecord();
					if(record.ds.id==layoutDs.id){layoutArr.push(cmp);}
					else if(record.ds.id==tagDs.id){tagArr.push(cmp);}
					else if(record.ds.id==btnDs.id){btnArr.push(cmp);}
				}
			}
		}
		if(btnArr.length>0){
			if(bpm_type == 'page'){
                    url=$au('engineButtonPageUpdateLink').getUrl();
            }
			for(var i=0;i<btnArr.length;i++){
				btnArr[i].setSequence((i+1)*10);
				btnArr[i].getDataRecord().set('buttonSequence',(i+1)*10);
				if(btnArr[i].getDataRecord().get('buttonId')){
					saveSequence(btnArr[i].getDataRecord(),url);
				}
			}
			
		}
		else if(tagArr.length>0){
			if(bpm_type == 'page'){
				url=$au('engineTagPageUpdateLink').getUrl();
			}
			for(var i=0;i<tagArr.length;i++){
				tagArr[i].setSequence((i+1)*10);
				tagArr[i].getDataRecord().set('tagSequence',(i+1)*10);
				if(tagArr[i].getDataRecord().get('tagId')){
					saveSequence(tagArr[i].getDataRecord(),url);
				}
			}
		}else if(layoutArr.length){
			if(bpm_type == 'page'){
                url=$au('engineLayoutPageUpdatelink').getUrl();
            }
			if(layoutLength==layoutArr.length){//没有layout放在parant_layout下
				for(var i=0;i<layoutArr.length;i++){
					layoutArr[i].setSequence((i+1)*10);
					layoutArr[i].getDataRecord().set('layoutSequence',(i+1)*10);
					if(layoutArr[i].getDataRecord().get('layoutId')){
						saveSequence(layoutArr[i].getDataRecord(),url);
					}
				}
			}else{
				var layoutseq=[];
				for(var i=0;i<layoutArr.length;i++){//有layout放在parant_layout下
					var dataRecord=layoutArr[i].getDataRecord();
					if(dataRecord && dataRecord.get('layoutSequence')){
						layoutseq.push(dataRecord.get('layoutSequence'));
					}
				}
				layoutseq.sort(function(a,b){
					return a-b;
				});
				for(var i=0;i<layoutArr.length;i++){//有layout放在parant_layout下
					var dataRecord=layoutArr[i].getDataRecord();
					if(dataRecord){
						dataRecord.set('layoutSequence',layoutseq[i]);
						layoutArr[i].setAttr(dataRecord);
					}
					if(layoutArr[i].getDataRecord().get('layoutId')){
						saveSequence(layoutArr[i].getDataRecord(),url);
					}
				}
				
			}
		}
	}
	
}
//拖拽样式初始化
function dragSortableInit(event, ui) {
		ui.placeholder.height(20);
		ui.placeholder.css('margin-top', '10px');
		var layoutColumn = 1;
		var colClass = '';
		var layouttype='';
		var layoutComponent = $(this).parents('.layout-component')[0];
		if (layoutComponent && $(layoutComponent).attr('id')) {
			layoutColumn = $AE.get($(layoutComponent).attr('id')).column;
			layouttype=$AE.get($(layoutComponent).attr('id')).layoutType;
		}
		if(layouttype!='grid'){
			
			if (layoutColumn == 1) {
				colClass = 'col-md-12';
			} else if (layoutColumn == 2) {
				colClass = 'col-md-6';
			} else if (layoutColumn == 3) {
				colClass = 'col-md-4';
			} else if (layoutColumn == 4) {
				colClass = 'col-md-3';
			} else {
				colClass = 'col-md-12';
			}
		}
		//页面级按钮默认col-md-2
		if(ui.helper.attr('aetype')=='Button'){
			colClass = 'col-md-2';
		}
		else if(ui.helper.attr('aetype')=='placeHolder'){
			colClass = 'col-md-12';
		}
		ui.placeholder.addClass(colClass);
		
		ui.helper.removeClass('col-md-12 col-md-6 col-md-4 col-md-3');
		ui.helper.addClass(colClass);
}

function datasetContainerSortableInit(event, ui) {
    ui.placeholder.height(25);
    ui.placeholder.addClass('dataset-shell');
}

function hiddenContainerSortableInit(event,ui){
	ui.placeholder.height(25);
    ui.placeholder.addClass('hidden-shell');
}

function gridSortableInit(event, ui) {
    ui.helper.removeClass('col-md-12 col-md-6 col-md-4 col-md-3');

    ui.placeholder.height(74);
    ui.placeholder.width(120);
}

function gridSelectSortableInit(event, ui) {
    ui.helper.removeClass('col-md-12 col-md-6 col-md-4 col-md-3');

    ui.placeholder.height(74);
    ui.placeholder.width(37);
}

function gridNavBarSortableInit(event, ui) {
    ui.helper.removeClass('col-md-12 col-md-6 col-md-4 col-md-3');

    ui.placeholder.height(37);
    ui.placeholder.width('100%');
}
function gridButtonSortableInit(event,ui){
    ui.helper.removeClass('col-md-12 col-md-6 col-md-4 col-md-3');

    ui.placeholder.height(37);
    ui.placeholder.width('100%');
}

//拖拽初始化
function draggableInit(event, ui, sf) {
	var target=$(ui.helper).parent();
	var cmp=null;
	if($(target).hasClass('drag-container')||$(target).hasClass('grid-drag-container')||$(target).hasClass('grid-button-container')||$(target).hasClass('grid-navbar-container')||$(target).hasClass('grid-select-container')){
		$(ui.helper).removeClass().css('width', '').css('height', '');
		var gridFlag = false;
		var layoutId=$(ui.helper).parent().attr('id');//父级组件页面对应的ID layout_view_id
		if(layoutId){
			layoutId=layoutId.replace('-drag-container', '');
		}
		else{
			layoutId='';
		}
		if ($(ui.helper).parent().hasClass('drag-container')) {
			var layoutColumn = 1;
			var colClass = '';
			var layoutComponent = $(ui.helper).parents('.layout-component')[0];
			if (layoutComponent && $(layoutComponent).attr('id')) {
				layoutColumn = $AE.get($(layoutComponent).attr('id')).column;
			}
			
			if (layoutColumn == 1) {
				colClass = 'col-md-12';
			} else if (layoutColumn == 2) {
				colClass = 'col-md-6';
			} else if (layoutColumn == 3) {
				colClass = 'col-md-4';
			} else if (layoutColumn == 4) {
				colClass = 'col-md-3';
			} else {
				colClass = 'col-md-12';
			}
			
			$(ui.helper).addClass(colClass);
		} else if ($(ui.helper).parent().hasClass('grid-drag-container')) {
			gridFlag = true;
			layoutId = layoutId.replace('-drag-container', '');
			layoutId = layoutId.replace('-select-container', '');
			layoutId = layoutId.replace('-button-container', '');
			layoutId = layoutId.replace('-navbar-container', '');
		}
		var index=0;
		var r=null;
		if($AE.get(layoutId)){
			r=$AE.get(layoutId).getDataRecord();
			index=r.ds.indexOf(r);
			layoutDs.locate(index+1,true);
			layout_id=r.get('layoutId');//父级组件数据库对应的ID layout_id
		}
		var html = '';
		if(bpm_type=='page' && r && 'COM_HEADER_FORM'==r.get('layoutCode')){
			Aurora.showWarningMessage('','通用单据头From不能添加子元素！',function(){
				$(ui.helper).html('');
			});
		}
		else if(bpm_type=='page' && $(sf).attr('cmptype')=='layout'){
			Aurora.showWarningMessage('','动态页面配置无法添加layout布局！',function(){
				$(ui.helper).html('');
			});
		}
		else{
			switch ($(sf).attr('aetype')) {
			case 'Box':
				var id=getNewId(layoutDs,'layoutType','box');
				var record=layoutDs.create();
				cmp = (new $AE.Box({
					id:id,
					column: 1,
					dataRecord:record,
					layoutSequence:getCmpSequence(layoutDs,'layoutSequence'),
					parentLayout:layoutId
				}));
				html=cmp.getDom();
				cmp.initRecord(record);
				cmp.setAttr(record);
				break;
			case 'HBox':
				var id=getNewId(layoutDs,'layoutType','hBox');
				var record=layoutDs.create();
				cmp = (new $AE.HBox({
					id:id,
					column: 1,
					dataRecord:record,
					layoutSequence:getCmpSequence(layoutDs,'layoutSequence'),
					parentLayout:layoutId
				}));
				html=cmp.getDom();
				cmp.initRecord(record);
				cmp.setAttr(record);
				break;
			case 'VBox':
				var id=getNewId(layoutDs,'layoutType','vBox');
				var record=layoutDs.create();
				cmp = (new $AE.VBox({
					id:id,
					column: 1,
					dataRecord:record,
					layoutSequence:getCmpSequence(layoutDs,'layoutSequence'),
					parentLayout:layoutId
				}));
				html=cmp.getDom();
				cmp.initRecord(record);
				cmp.setAttr(record);
				break;
			case 'Form':
				var id=getNewId(layoutDs,'layoutType','form');
				var record=layoutDs.create();
				cmp = (new $AE.Form({
					id:id,
					column: 1,
					dataRecord:record,
					layoutSequence:getCmpSequence(layoutDs,'layoutSequence'),
					parentLayout:layoutId
				}));
				html=cmp.getDom();
				cmp.initRecord(record);
				cmp.setAttr(record);
				break;
			case 'FieldSet':
				var id=getNewId(layoutDs,'layoutType','fieldSet');
				var record=layoutDs.create();
				cmp = (new $AE.FieldSet({
					id:id,
					column: 1,
					dataRecord:record,
					layoutSequence:getCmpSequence(layoutDs,'layoutSequence'),
					parentLayout:layoutId
				}));
				html=cmp.getDom();
				cmp.initRecord(record);
				cmp.setAttr(record);
				break;
			case 'QueryForm':
				var id=getNewId(layoutDs,'layoutType','queryForm');
				var record=layoutDs.create();
				cmp = (new $AE.QueryForm({
					id:id,
					column: 1,
					dataRecord:record,
					layoutSequence:getCmpSequence(layoutDs,'layoutSequence'),
					parentLayout:layoutId
				}));
				html=cmp.getDom();
				cmp.initRecord(record);
				cmp.setAttr(record);
				break;
			case 'TabPanel':
				var id=getNewId(layoutDs,'layoutType','tabPanel');
				var record=layoutDs.create();
				cmp = (new $AE.TabPanel({
					id:id,
					dataRecord:record,
					layoutSequence:getCmpSequence(layoutDs,'layoutSequence'),
					parentLayout:layoutId
				}));
				html=cmp.getDom();
				cmp.initRecord(record);
				cmp.setAttr(record);
				break;
			case 'TextField':
				if(layoutId!='editor-container'){
					var id=getNewId(tagDs,'tagType','textField');
					var record=tagDs.create();
					var sequence=getCmpSequence(tagDs,'tagSequence');
					cmp = (new $AE.TextField({
						id:id,
						/*tagCode:id,*/
						layoutId:layoutId,
						layout_id:layout_id,
						dataRecord:record,
						tagSequence:sequence
					}));
					html=cmp.getDom(gridFlag);
					cmp.initRecord(record);
					//拖拽初始化record
					cmp.setAttr(record);
				}
				break;
			case 'NumberField':
				if(layoutId!='editor-container'){
					var id=getNewId(tagDs,'tagType','numberField');
					var record=tagDs.create();
					cmp = (new $AE.NumberField({
						id:id,
						/*tagCode:id,*/
						layoutId:layoutId,
						layout_id:layout_id,
						dataRecord:record,
						tagSequence:getCmpSequence(tagDs,'tagSequence')
					}));
					html=cmp.getDom(gridFlag);
					cmp.initRecord(record);
					cmp.setAttr(record);
				}
				break;
			case 'ComboBox':
				if(layoutId!='editor-container'){
					var id=getNewId(tagDs,'tagType','comboBox');
					var record=tagDs.create();
					cmp = (new $AE.ComboBox({
						id:id,
						/*tagCode:id,*/
						layoutId:layoutId,
						layout_id:layout_id,
						dataRecord:record,
						tagSequence:getCmpSequence(tagDs,'tagSequence')
					}));
					html=cmp.getDom(gridFlag);
					cmp.initRecord(record);
					cmp.setAttr(record);
				}
				break;
			case 'CheckBox':
				if(layoutId!='editor-container'){
					var id=getNewId(tagDs,'tagType','checkBox');
					var record=tagDs.create();
					cmp = (new $AE.CheckBox({
						id:id,
						/*tagCode:id,*/
						layoutId:layoutId,
						layout_id:layout_id,
						dataRecord:record,
						tagSequence:getCmpSequence(tagDs,'tagSequence')
					}));
					html=cmp.getDom(gridFlag);
					cmp.initRecord(record);
					cmp.setAttr(record);
				}
				break;
			case 'Radio':
				if(layoutId!='editor-container'){
					var id=getNewId(tagDs,'tagType','radio');
					var record=tagDs.create();
					cmp = (new $AE.Radio({
						id:id,
						/*tagCode:id,*/
						layoutId:layoutId,
						layout_id:layout_id,
						dataRecord:record,
						tagSequence:getCmpSequence(tagDs,'tagSequence')
					}));
					html=cmp.getDom(gridFlag);
					cmp.initRecord(record);
					cmp.setAttr(record);
				}
				break;
			case 'Lov':
				if(layoutId!='editor-container'){
					var id=getNewId(tagDs,'tagType','lov');
					var record=tagDs.create();
					cmp = (new $AE.Lov({
						id:id,
						/*tagCode:id,*/
						layoutId:layoutId,
						layout_id:layout_id,
						dataRecord:record,
						tagSequence:getCmpSequence(tagDs,'tagSequence')
					}));
					html=cmp.getDom(gridFlag);
					cmp.initRecord(record);
					cmp.setAttr(record);
				}
				break;
			case 'TextArea':
				if(layoutId!='editor-container'){
					var id=getNewId(tagDs,'tagType','textArea');
					var record=tagDs.create();
					cmp = (new $AE.TextArea({
						id:id,
						/*tagCode:id,*/
						layoutId:layoutId,
						layout_id:layout_id,
						dataRecord:record,
						tagSequence:getCmpSequence(tagDs,'tagSequence')
					}));
					html=cmp.getDom(gridFlag);
					cmp.initRecord(record);
					cmp.setAttr(record);
				}
				break;
			case 'DatePicker':
				if(layoutId!='editor-container'){
					var id=getNewId(tagDs,'tagType','datePicker');
					var record=tagDs.create();
					cmp = (new $AE.DatePicker({
						id:id,
						/*tagCode:id,*/
						layoutId:layoutId,
						layout_id:layout_id,
						dataRecord:record,
						tagSequence:getCmpSequence(tagDs,'tagSequence')
					}));
					html=cmp.getDom(gridFlag);
					cmp.initRecord(record);
					cmp.setAttr(record);
				}
				break;
			case 'DateTimePicker':
				if(layoutId!='editor-container'){
					var id=getNewId(tagDs,'tagType','dateTimePicker');
					var record=tagDs.create();
					cmp = (new $AE.DateTimePicker({
						id:id,
						/*tagCode:id,*/
						layoutId:layoutId,
						layout_id:layout_id,
						dataRecord:record,
						tagSequence:getCmpSequence(tagDs,'tagSequence')
					}));
					html=cmp.getDom(gridFlag);
					cmp.initRecord(record);
					cmp.setAttr(record);
				}
				break;
			case 'Label':
				if(layoutId!='editor-container'){
					var id=getNewId(tagDs,'tagType','label');
					var record=tagDs.create();
					cmp = (new $AE.Label({
						id:id,
						/*tagCode:id,*/
						layoutId:layoutId,
						layout_id:layout_id,
						dataRecord:record,
						tagSequence:getCmpSequence(tagDs,'tagSequence')
					}));
					html=cmp.getDom(gridFlag);
					cmp.initRecord(record);
					cmp.setAttr(record);
				}
				break;
			case 'Grid':
				var id=getNewId(layoutDs,'layoutType','grid');
				var record=layoutDs.create();
				cmp = (new $AE.Grid({
					id:id,
					dataRecord:record,
					layoutSequence:getCmpSequence(tagDs,'layoutSequence'),
					parentLayout:layoutId
				}));
				html=cmp.getDom();
				cmp.initRecord(record);
				cmp.setAttr(record);
				break;
			case 'Button':
				if(!layoutId){
					Aurora.showWarningMessage('','页面按钮需放在hBox组件内！',function(){
						$(ui.helper).html('');
						return;
					});
				}else{
					//根据layoutid找到layout，判断layout是否hbox
					var layoutCmp=$AE.get(layoutId);
					var layoutType='';
					if(layoutCmp){layoutType=layoutCmp.getCmpType();}
					if(bpm_type=='tplt'&&layoutType=='hBox'){
						var record=btnDs.create();
						cmp = (new $AE.Button({
							id:getNewId(btnDs),
							dataRecord:record,
							buttonSequence:getCmpSequence(btnDs,'buttonSequence')
						}));
						html=cmp.getDom(gridFlag);
						cmp.initRecord(record);
						cmp.setAttr(record);
						ui.helper.removeClass('col-md-12 col-md-6 col-md-4 col-md-3');
						$(ui.helper).addClass('col-md-2');
					}
					else if(bpm_type=='page'){
						Aurora.showWarningMessage('','OA页面无法添加按钮！',function(){
							$(ui.helper).html('');
							return;
						});
					}
					else if(bpm_type=='tplt'&&layoutType!='hBox'){
						Aurora.showWarningMessage('','模板页面按钮需放在hBox组件内！',function(){
							$(ui.helper).html('');
							return;
						});
					}else{
						var record=btnDs.create();
						cmp = (new $AE.Button({
							id:getNewId(btnDs),
							dataRecord:record,
							buttonSequence:getCmpSequence(btnDs,'buttonSequence')
						}));
						html=cmp.getDom(gridFlag);
						cmp.initRecord(record);
						cmp.setAttr(record);
						ui.helper.removeClass('col-md-12 col-md-6 col-md-4 col-md-3');
						$(ui.helper).addClass('col-md-2');
					}
				}
				break;
			}
			if (html) {
				$(ui.helper).attr('id',Ext.id());
				$(ui.helper).html(html);
				if ($(ui.helper).attr('cmptype') == 'input') {
					if (gridFlag) {
						$(ui.helper).addClass('grid-input-shell');
						if($(sf).attr('aetype')=='TextArea')//textarea增大高度
							$(ui.helper).css('height','90px');
					} else {
						$(ui.helper).addClass('input-shell');
					}
				}
				hoverEvtInit($(ui.helper));
				sf.cmp=cmp;
			}else{
				$(ui.helper).html('');
			}
		}
	}
    
    
}

function containerClickInit(e) {
	jsPlumbDeleteAll();
    $('.drag-container-focus').removeClass('drag-container-focus');
    if ($(e.target).hasClass('drag-container') || $(e.target).hasClass('grid-drag-container')) {
        target = $(e.target);
    } else {
        if ($(e.target).hasClass('layout-component')) {
            parentTarget = e.target;
        } else {
            parentTarget = $(e.target).parents('.layout-component')[0];
        }
        target = $(parentTarget).find('.drag-container')[0] || $(parentTarget).find('.grid-drag-container')[0];
    }
    e.stopPropagation();
    e.preventDefault();
    if (target) {
        $(target).addClass('drag-container-focus')
    }
}


function elementTitleClick(e) {
    var cmpId = $(e.target).attr('id').replace('-element-title', '');
    var cmp=$AE.get(cmpId);
    var url=componentScreen[$(e.target).attr('elementType')] + '?componentId=' + cmpId +'&bpm_type='+bpm_type;
    //var url=$au("engineBpmPageTag"+$(e.target).attr('elementType')+"Link").getUrl() + '?componentId=' + cmpId +'&bpm_type='+bpm_type;
    if(cmp && cmp.layoutId && $AE.get(cmp.layoutId)){
    	var rd=layoutDs.find('layoutId',cmp.getDataRecord().get('layoutId'));
    	if(rd){
    		layoutDs.locate(layoutDs.indexOf(rd)+1,true);
    	}
    	
    	url=url+'&layoutType='+$AE.get(cmp.layoutId).getCmpType().toLowerCase();//获取layout布局组件类型，用于view-config
    }
    new Aurora.Window({
        url: url,
        id: $(e.target).attr('elementType') + '_window',
        width: 800,
        side: 'right'
    }).on('close',function(){
    	$('.ui-sortable').removeClass('drag-container-focus');
    });
}

//加入回收站，数据并没有从dataset中remove
function deleteButtonClick(e){
	var cmpId = $(e.target).attr('id').replace('-delete-button', '');
	Aurora.showConfirm('系统提示', '确认将'+cmpId+'加入回收站?', function(resp) {
		var cmp = $AE.get(cmpId);
		var type=cmp.getCmpType();
		if(cmp && cmp.layoutId && $AE.get(cmp.layoutId)){
	    	var rd=layoutDs.find('layoutId',cmp.getDataRecord().get('layoutId'));
	    	if(rd){
	    		layoutDs.locate(layoutDs.indexOf(rd)+1,true);
	    	}
		}
		if(type=='tab'){
			deleteComponent=$('#'+cmpId+'-tab');
			var children=$('.nav-tabs',$('#'+cmp.layoutId+'-tab-drag-container')).children();
			if(children.length>1){
				$('#'+cmpId+'-tab').remove();
				$('#'+cmpId+'-tab-title').remove();
			}
			else{
				$('#'+cmp.layoutId+'-tab-drag-container').empty();
			}
		}
		else{
			if($('#'+cmpId+'-prompt').length>0){
				deleteComponent=$('#'+cmpId+'-prompt').parent();
			}else{
				deleteComponent=$('#'+cmpId+'-element-title').parent();
			}
		}
		//$AE.CmpManager.remove(cmpId);
		var record = cmp.getDataRecord();
		if(record){
			if(screen_edit){
				//如果是解析screen来的数据，record的enableFlag变成N
				record.set('enableFlag','N');
			}else{
				//将该条record数据删除
				if(record.get('tagId'))
					record.isNew=false;
				record.ds.remove(record);
			}
		}
		//$AE.CmpManager.remove(cmpId);
		var recycle=cmp.getRecycleDom(type,cmpId);
		$AE.recycleComponent.put(cmpId,deleteComponent);
		$(deleteComponent).remove();
		$('#recycle-container').append(recycle);
		
		//删掉与该组件相连的连线

	    jsPlumbDeleteAll(null,cmpId);
	    jsPlumbDeleteAll(cmpId,null);
		
		//初始化所有的recycle-delete-button的点击事件
	    $('.recycle-delete-button').unbind('click').click(recycleDeleteButtonClick);
	    
	    //初始化所有的recycle-undo-button的点击事件
	    $('.recycle-undo-button').unbind('click').click(recycleUndoButtonClick);
	    
	    recycleIconClickInit();
       
        resp.close();
    });
	
    
}

//grid上的组件 删除按钮点击初始化
function gridDeleteButtonClick(e){
	console.log($(e.target));
}

//从回收站撤销
function recycleUndoButtonClick(e){
	var cmpId = $(e.target).attr('id').replace('-recycle-undo-button', '');
	var cmp=$AE.CmpManager.get(cmpId);
	var recycleIndex=0;
	if(cmp && cmp.layoutId && $AE.get(cmp.layoutId)){
    	var rd=layoutDs.find('layoutId',cmp.getDataRecord().get('layoutId'));
    	if(rd){
    		recycleIndex=layoutDs.indexOf(rd);
    		layoutDs.locate(recycleIndex+1,true);
    	}
	}
	//获取组件所属的布局Id
	var layoutId=cmp.config["layoutId"]||'';
	var cmpTemplate = $AE.recycleComponent.get(cmpId);
	$('.drag-container',cmpTemplate).removeClass('drag-container-focus');
	var aetype=cmp.getCmpType();
	$('#'+cmpId).remove();
	$AE.recycleComponent.remove(cmpId);
	
	if(aetype=='DataSet'){
		$(cmpTemplate).removeClass('dataset-shell-focus');
		$('#dataset-container').append(cmpTemplate);
		datasetClickInit();
		datasetIconClickInit();
	}
	else if(aetype=='Hidden'){
		$(cmpTemplate).removeClass('hidden-shell-focus');
		$('#hidden-container').append(cmpTemplate);
		 hiddenClickInit();
         hiddenIconClickInit();
	}
	else if(aetype=='tab'){
		var title=cmp.getTitle();
		$('.nav-tabs',$('#'+layoutId)).append('<li class="" id="'+cmpId+'-tab-title" ><div class="element-title" elementType="Tab" style="display:none" id="'+cmpId+'-element-title">Tab</div>'+
                '<div class="element-delete-button" style="display:none" id="'+cmpId+'-delete-button"></div>'+
                '<a href="#'+cmpId+'-tab">'+title+'</a></li>');
		$(cmpTemplate).css('display','none');
		$('.tab-content',$('#'+layoutId)).append(cmpTemplate);
		
		if(screen_edit){
			record.set('enableFlag','Y');
		}
		
		
		$('a').each(function(){
    		$(this).unbind('click').click(function(e){
    			$('.active').each(function(){
    				$(this).removeClass("active");
    				$('.element-title',this).css('display','none');
            		$('.element-delete-button',this).css('display','none');
    				var i=$($(this).children()[2]).attr('href').replace('#','');
    				$('#'+i).css('display','none');
    			});
        		$(e.target).parent().addClass("active");
        		$('.element-title',$(e.target).parent()).css('display','block');
        		$('.element-delete-button',$(e.target).parent()).css('display','block');
        		var Id=$(e.target).attr('href').replace('#', '');
        		$('#'+Id).css('display','block');
        	});
    	});
	}
	else {
		var record=cmp.getDataRecord();
		//获取cmptype
		var cmptype=$(cmpTemplate).attr('cmptype');
		var allrecords=null;
		var sequence_type,nextId='';
		if(cmptype=='layout'){
			allrecords=layoutDs.getAll();
			sequence_type='layoutSequence';
		}
		else if(cmptype=='input'){
			allrecords=tagDs.getAll();
			sequence_type='tagSequence';
		}
		else if(cmptype=='btn'){
			allrecords=btnDs.getAll();
			sequence_type='buttonSequence';
		}
		for(var i=0;i<allrecords.length;i++){
			if(allrecords[i].get(sequence_type)>record.get(sequence_type)){
				nextId=allrecords[i].get('id');
				break;
			}
		}
		if(nextId){
			if($('#'+nextId+'-prompt').length==0){
				$(cmpTemplate).insertBefore($('#'+nextId).parent());
			}
			$(cmpTemplate).insertBefore($('#'+nextId+'-prompt').parent());
		}else{
			switch(layoutId){
				case '':$('#editor-container').append(cmpTemplate);break;
				case 'editor-container':$('#editor-container').append(cmpTemplate);break;
				default:$('#'+layoutId+'-drag-container').append(cmpTemplate);break;
			}
		}
		if(screen_edit){
			record.set('enable_flag','Y');
		}else{
			record.ds.add(record);//如果是layout,对应的tag也将被删除
			if($('#'+cmpId+'-drag-container',cmpTemplate).length>0){
				var recycChildren=$('#'+cmpId+'-drag-container',cmpTemplate).children();
				for(var i=0;i<recycChildren.length;i++){
					var recycChildId=$('.element-title',recycChildren[i]).attr('id').replace('-element-title','');
					var recycChildCmp=$AE.get(recycChildId);
					recycChildCmp.getDataRecord().ds.add(recycChildCmp.getDataRecord());
					recycChildCmp.onHover();
				}
			}
			record.ds.submit();
		}
		cmp.onHover();
	}
	
	//初始化所有的element-title的点击事件
    $('.element-title').unbind('click').click(elementTitleClick);
    
    //初始化所有的element-delete-button的点击事件
    $('.element-delete-button').unbind('click').click(deleteButtonClick);
}
//从回收站删除
function recycleDeleteButtonClick(e){
	var cmpId = $(e.target).attr('id').replace('-recycle-delete-button', '');
	Aurora.showConfirm('系统提示', '将彻底删除'+cmpId+'?', function(resp) {
		var cmp = $AE.CmpManager.get(cmpId);
		$('#'+cmpId).remove();
		$AE.CmpManager.remove(cmpId);
		$AE.recycleComponent.remove(cmpId);
		
		if(screen_edit && cmp){
			if(cmp.getDataRecord().ds['bindtarget']){
				$au(cmp.getDataRecord().ds['bindtarget']).submit();
			}else{
				cmp.getDataRecord().ds.submit();
			}
		}
       
        resp.close();
        toastr.remove();
        toastr.success("删除成功");
    });
}

function datasetClickInit() {
    $('.dataset-shell').unbind('click').click(function (e) {
        $('.dataset-shell').removeClass('dataset-shell-focus');
        var datasetShell = $($(e.target).parents('.dataset-shell')[0] || $(e.target));
        datasetShell.addClass('dataset-shell-focus');
        window.currentDataSet = $AE.get($(datasetShell.find('.dataset-prompt')[0]).attr('id'));

        //删掉所有的连接线
        jsPlumbDeleteAll();
        
        //生成所有与当前DataSet关联的元素的连接线
        for (var cmpId in $AE.CmpManager.getAll()) {
            if ($AE.get(cmpId).bindTarget == window.currentDataSet.id) {
                $AE.jsPlumbInstance.connect({
                    source: cmpId,
                    target: window.currentDataSet.id
                });
            }
        }
        //阻止事件冒泡
        return false;
    });
}

//dataset图标点击初始化
function datasetIconClickInit() {
	//先解除click事件绑定，防止多次触发
    $('.dataset-icon').unbind('click').click(function (e) {
        var cmpId = $(e.target).attr('id').replace('-icon', '');
        new Aurora.Window({
            url: componentScreen[$($(e.target).parents('.dataset-shell')[0]).attr('aetype')] + '?componentId=' + cmpId,
            id: $($(e.target).parents('.dataset-shell')[0]).attr('aetype') + '_window',
            width: 800,
            side: 'right'
        });
    });
}

function hiddenClickInit() {
    $('.hidden-shell').unbind('click').click(function (e) {
        $('.hidden-shell').removeClass('hidden-shell-focus');
        var hiddenShell = $($(e.target).parents('.hidden-shell')[0] || $(e.target));
        hiddenShell.addClass('hidden-shell-focus');
        window.currentHidden = $AE.get($(hiddenShell.find('.hidden-prompt')[0]).attr('id'));
        var fieldFrom= $AE.get($(hiddenShell.find('.hidden-prompt')[0]).attr('id')).fieldFrom;
        //删掉所有的连接线
        jsPlumbDeleteAll();
        //生成所有与当前DataSet关联的元素的连接线
        for (var cmpId in $AE.CmpManager.getAll()) {
        	if ($AE.get(cmpId).name && fieldFrom&& ($AE.get(cmpId).name == fieldFrom)) {
                $AE.jsPlumbInstance.connect({
                    source: window.currentHidden.id,
                    target: cmpId
                });
            }
        }
    });
}

//隐藏组件图标点击事件初始化
function hiddenIconClickInit() {
    $('.hidden-icon').unbind('click').click(function (e) {
        var cmpId = $(e.target).attr('id').replace('-icon', '');
        new Aurora.Window({
            url: componentScreen[$($(e.target).parents('.hidden-shell')[0]).attr('aetype')] + '?componentId=' + cmpId,
            id: $($(e.target).parents('.hidden-shell')[0]).attr('aetype') + '_window',
            width: 800,
            side: 'right'
        });
    });
}

//回收组件图标点击事件初始化
function recycleIconClickInit() {
    $('.recycle-icon').unbind('click').click(function (e) {
        var cmpId = $(e.target).attr('id').replace('-recycle-icon', '');
        var screen=componentScreen[$($(e.target).parents('.recycle-component')[0]).attr('aetype')||$AE.get(cmpId).getCmpType()];
        new Aurora.Window({
            url:  screen+ '?componentId=' + cmpId,
            id: $($(e.target).parents('.recycle-component')[0]).attr('aetype') + '_window',
            width: 800,
            side: 'right'
        });
    });
}

$(document).ready(function () {
	$("#editor-container").niceScroll();
	$("#dataset-container-shell").niceScroll();
	$(".grid-drag-container").niceScroll();
	
	$(document).click(function () {  
		jsPlumbDeleteAll();
	  });
	
	$('.expand-down').click( function(e){
		var id=$(e.target).attr('id').replace('a-', '');
		var dis=$('#'+id).css('display');
		if(dis=='none'){
			$('#'+id).css('display','block');
			$(e.target).removeClass('expand-left');
			$(e.target).addClass('expand-down');
		}
		else{
			$('#'+id).css('display','none');
			$(e.target).removeClass('expand-down');
			$(e.target).addClass('expand-left');
		}
	});
	
    //页面加载完成执行初始化
    //拖拽容器sortable初始化
	initDraggable();
    
});

//jsPlumb初始化
jsPlumb.bind("ready", function () {
	//默认锚点
    var defaultAnchors = ["Top", "Right", "Bottom", "Left", [0.25, 0, 0, -1], [0.75, 0, 0, -1], [0.25, 1, 0, 1], [0.75, 1, 0, 1]
        , [0, 0.25, 0, -1], [0, 0.75, 0, -1], [1, 0.25, 0, 1], [1, 0.75, 0, 1]];
    $AE.jsPlumbInstance = jsPlumb.getInstance({
        PaintStyle: {strokeWidth: 1, stroke: '#89bcde', dashstyle: "3 3",},
        Container: $("body"),
        Connector: ["StateMachine", {curviness: 30}],
        // Connector: ["StateMachine"],连线，Bezier，StateMachine，Flowchart，Straight
        // Connector: ["Flowchart", { stub: [40, 60], gap: 10, cornerRadius: 5, alwaysRespectStubs: true }],
        //Connector: ["Straight", {stub: [20, 50], gap: 0}],

        Endpoint: ["Dot", {radius: 2, cssClass: "displaynone"}],/// 端点 通过样式来隐藏锚点
        //Endpoint: ["Image ", { src: "http://rmadyq.sintoon.cn/Content/16Icons/arrow_right.png" }],/// 通过样式来隐藏锚点
        // Endpoint: ["Blank", "Blank"], 失败报错了，
        EndpointStyle: {fillStyle: "#567567"},
        Anchor: [defaultAnchors],
        // Anchor: ["Perimeter", { shape: "Triangle" }],
        Container: $('body'),
        DragOptions: {cursor: 'pointer', zIndex: 2000},
        ConnectionOverlays: [
            ["Arrow", {fill: "#09098e", width: 10, length: 10, location: 1}]
        ]
    });

});

//editor_update.screen页面调用。根据后台数据加载
function loadData(ds,type){
	var records=ds.getAll();
	for(var i=0;i<records.length;i++){
		var record=records[i];
		if(type){
			cmp=initCmp('dataset',null,type,record);
			cmp.setAttr(record);
		}
		else{
			//考虑layout存在parent_layout的情况
			var parent_layout_id=record.get('parentLayoutId');
			layoutType=record.get('layoutType');
			if(!parent_layout_id || parent_layout_id==-1){
				cmp=initCmp('layout',null,layoutType,record);
				cmp.setAttr(record);
			}else{
				//根据parent_layout_id怎么找到父级layout
				var parentRecord=record.ds.find('layoutId',parent_layout_id);
				if(parentRecord){
					cmp=initCmp('layout',parentRecord.get('id'),layoutType,record);
					cmp.setAttr(record);
				}
			}
			//ds.locate(record.ds.indexOf(ds)+1,true);
		}
	}
}
function loadTags(records,layoutRecord){
		var layoutId=layoutRecord.get('id');
		var layout_id=layoutRecord.get('layoutId');
		var layoutType=layoutRecord.get('layoutType');
		var isGrid=(layoutType=='grid')?true:false;
		getTagData(0,records,isGrid,layoutId,layout_id);
		/*for(var j=0;j<records.length;j++){
			var tagType=records[j].get('tag_type');
			records[j].set('layout_view_id',layoutId);
			var cmp=initCmp(layoutId,tagType,records[j],isGrid);
			cmp.setAttr(records[j]);
			if(tagType=='placeHolder'){
				//$au('sys_screen_dimension_ds').setQueryParameter('layout_id',records[j].get('layout_id'));
				//records[j].ds.locate(records[j].ds.indexOf(records[j])+1,true);
			}
		}*/
}

function getTagData(index,records,isGrid,layoutId,layout_id){
    if(index<records.length && records.length>0 && !records[index].get('renderedFlag')){
    	var tagType=records[index].get('tagType');
		records[index].set('layoutViewId',layoutId);
		var cmp=initCmp('tag',layoutId,tagType,records[index],isGrid);
		cmp.setAttr(records[index]);
		records[index].set('renderedFlag',true);
		if(tagType=='placeHolder'){
			Aurora.request({
				url: $au('tag_dimension_query_link').getUrl(),
				para: {layout_id:layout_id,id:records[index].get('id')},
				success: function(result) {
					if(result.result){
						var recs=[].concat(result.result.record);
						var datas = [];
			            if(recs.length > 0 && recs[0]){
			                for(var i=0,l=recs.length;i<l;i++){
			                	recs[i].dimension_tag='Y';
			                    var item = {
			                        data:recs[i]             
			                    }
			                    datas.push(item);
			                }
			                $au('screen_tags_basic_ds').keepData=true;
			                $au('screen_tags_basic_ds').loadData(datas);
			                getTagData(0,$au('screen_tags_basic_ds').find('dimension_tag','Y',null,true),false,records[index].get('id'),layout_id);
			                getTagData(index+1,records,isGrid,layoutId,layout_id);
			            }else{
			            	getTagData(index+1,records,isGrid,layoutId,layout_id);
			            }
					}
				},
				scope: this
			});
		}else{
			getTagData(index+1,records,isGrid,layoutId,layout_id);
		}
    }else{
        return;
    }
} 

function getButtonData(index,records,isGrid,layoutId,layout_id){
	if(index<records.length && records.length>0 && !records[index].get('renderedFlag')){
		var btnType=records[index].get('buttonType');
		if(isGrid && btnType!='placeHolder'){
			btnType='grid'+records[index].get('buttonType');
		}
		records[index].set('layoutViewId',layoutId);
		var cmp=initCmp('button',layoutId,btnType,records[index],isGrid);
		records[index].set('renderedFlag',true);
		if(!isGrid || btnType.toLowerCase()=='placeholder')
			cmp.setAttr(records[index]);
		if(btnType=='placeHolder'){
			Aurora.request({
				url: $au('button_dimension_query_link').getUrl(),
				para: {layout_id:layout_id,id:records[index].get('id')},
				success: function(result) {
					if(result.result){
						var recs=[].concat(result.result.record);
						var datas = [];
			            if(recs.length > 0 && recs[0]){
			                for(var i=0,l=recs.length;i<l;i++){
			                	recs[i].dimension_button='Y';
			                    var item = {
			                        data:recs[i]             
			                    }
			                    datas.push(item);
			                }
			                $au('screen_buttons_result_ds').keepData=true;
			                $au('screen_buttons_result_ds').loadData(datas);//用loaddata有问题，再次保存时，dataset数据记录改变
			                getButtonData(0,$au('screen_buttons_result_ds').find('dimension_button','Y',null,true),isGrid,records[index].get('id'),layout_id);
			                getButtonData(index+1,records,isGrid,layoutId,layout_id);
			            }else{
			            	getButtonData(index+1,records,isGrid,layoutId,layout_id);
			            }
					}
				},
				scope: this
			});
		}else{
			getButtonData(index+1,records,isGrid,layoutId,layout_id);
		}
    }else{
        return;
    }
}
function loadLayoutButtons(records,layoutRecord){
		var layoutType=layoutRecord.get('layoutType');
		if(layoutType=='hBox' && records.length==0){//把页面上的按钮放在页面的hbox组件里
			loadButtons(btnDs.getAll(),layoutRecord);
		}
		else{
			if(records.length>0 && layoutRecord.get("layoutId")==records[0].get("layoutId")){
                loadButtons(records,layoutRecord);
			}
		}
	
}

function loadButtons(records,layoutRecord){
	var layoutId=layoutRecord.get('id');
	var isGrid=layoutRecord.get('layoutType')=="grid"?true:false;
	var layout_id=layoutRecord.get('layoutId');
	getButtonData(0,records,isGrid,layoutId,layout_id);
}

function initButtonCmp(layoutId,type,record,cmp,isGrid){
	switch(type){
		case 'button':cmp=new $AE.Button({dataRecord:record});
					  html=cmp.getDom(false);
					  $('#'+layoutId+'-drag-container').append('<div id='+Ext.id()+' cmptype="btn" aetype="Button" class="col-md-2" style="position: relative; opacity: 1; left: 0px; top: 0px;">'+html+'</div>');
					  break;
		case 'gridbutton':cmp=new $AE.GridButton({
										dataRecord:record,
										type:record.get('type')
						  });
						  html=cmp.getDom(false);
						  $('#'+layoutId+'-drag-container').append('<div id='+Ext.id()+' cmptype="btn" aetype="GridButton" class="col-md-2" style="position: relative; opacity: 1; left: 0px; top: 0px;">'+html+'</div>');
						  break;
		case 'gridadd':var rlayoutId='';
						if($AE.get(layoutId)){
							if(!$AE.get(layoutId).layoutType){
								rlayoutId=$AE.get(layoutId).layoutId;
							}else{
								rlayoutId=layoutId;
							}
							var gridButtons=$AE.get(rlayoutId).getButtons();
								gridButtons.push(record);
								cmp=(new $AE.GridAddButton({
													dataRecord:record
								}));
								html = cmp.getDom();
								$('#'+layoutId+'-button-container').append('<div aetype="GridAddButton" style="opacity: 1;">'+html+'</div>');
							}
						break;
		case 'griddelete':var rlayoutId='';
							if($AE.get(layoutId)){
								if(!$AE.get(layoutId).layoutType){
									rlayoutId=$AE.get(layoutId).layoutId;
								}else{
									rlayoutId=layoutId;
								}
								var gridButtons=$AE.get(rlayoutId).getButtons();
				   				  	gridButtons.push(record);
				   				  	cmp=(new $AE.GridDeleteButton({
				   					  					dataRecord:record
				   				  	}));
				   				  	html = cmp.getDom();
				   				  $('#'+layoutId+'-button-container').append('<div aetype="GridDeleteButton" style="opacity: 1;">'+html+'</div>');
							}
				   			break;
		case 'gridclear':var rlayoutId='';
							if($AE.get(layoutId)){
								if(!$AE.get(layoutId).layoutType){
									rlayoutId=$AE.get(layoutId).layoutId;
								}else{
									rlayoutId=layoutId;
								}
								var gridButtons=$AE.get(rlayoutId).getButtons();
					  			 	gridButtons.push(record);
					  			 	cmp=(new $AE.GridClearButton({
					  				 					dataRecord:record
					  			 	}));
					  			 	html = cmp.getDom();
					  			 	$('#'+layoutId+'-button-container').append('<div aetype="GridClearButton" style="opacity: 1;">'+html+'</div>');
							} 
					  		break;
		case 'gridsave':var rlayoutId='';
						if($AE.get(layoutId)){
							if(!$AE.get(layoutId).layoutType){
								rlayoutId=$AE.get(layoutId).layoutId;
							}else{
								rlayoutId=layoutId;
							}
							var gridButtons=$AE.get(rlayoutId).getButtons();
								gridButtons.push(record);
								cmp=(new $AE.GridSaveButton({
				 									dataRecord:record
								}));
								html = cmp.getDom();
								$('#'+layoutId+'-button-container').append('<div aetype="GridSaveButton" style="opacity: 1;">'+html+'</div>');
						}	
						break;
        case 'gridexcel':
				var rlayoutId='';
				if($AE.get(layoutId)){
					if(!$AE.get(layoutId).layoutType){
						rlayoutId=$AE.get(layoutId).layoutId;
					}else{
						rlayoutId=layoutId;
					}
					var gridButtons=$AE.get(rlayoutId).getButtons();
					gridButtons.push(record);
					cmp=(new $AE.GridExcelButton({
						dataRecord:record
					}));
					html = cmp.getDom();
					$('#'+layoutId+'-button-container').append('<div aetype="GridExcelButton" style="opacity: 1;">'+html+'</div>');
				}
            break;
		case 'placeHolder':cmp=(new $AE.PlaceHolder({
										layoutId:layoutId,
										dataRecord:record
							}));
							html=cmp.getDom(isGrid,true);
							if(isGrid){
								$('#'+layoutId+'-button-container').append('<div id='+Ext.id()+' cmptype="input" aetype="placeHolder" style="opacity: 1;">'+html+'</div>');
							}else{
								$('#'+layoutId+'-drag-container').append('<div id='+Ext.id()+' cmptype="input" aetype="placeHolder" class="col-md-12" style="position: relative; opacity: 1; left: 0px; top: 0px;">'+html+'</div>');
							}
							break;
		default:cmp=new $AE.Button({dataRecord:record});
		  		html=cmp.getDom(false);
		  		$('#'+layoutId+'-drag-container').append('<div id='+Ext.id()+' cmptype="btn" aetype="Button" class="col-md-2" style="position: relative; opacity: 1; left: 0px; top: 0px;">'+html+'</div>');
		  		break;
	}
	return cmp;
}

function initLayoutCmp(layoutId,type,record,cmp){
	switch(type){
		case 'grid':cmp=new $AE.Grid({
							dataRecord:record,
							layoutSequence:(record.ds.indexOf(record)+1)*10
						});
					html = cmp.getDom();
					if(!layoutId){
						$('#editor-container').append('<div id='+Ext.id()+' cmptype="layout" aetype="Grid" class="col-md-12" style="position: relative; opacity: 1; left: 0px; top: 0px;">'+html+'</div>');
					}
					else{
						$('#'+layoutId+'-drag-container').append('<div id='+Ext.id()+' cmptype="layout" aetype="Grid" class="col-md-12" style="position: relative; opacity: 1; left: 0px; top: 0px;">'+html+'</div>');
					}
					break;
//flostlist怎么处理？
		case 'floatList':cmp=new $AE.Grid({
									dataRecord:record,
									layoutSequence:(record.ds.indexOf(record)+1)*10
							});
							html = cmp.getDom();
							if(!layoutId){
								$('#editor-container').append('<div id='+Ext.id()+' cmptype="layout" aetype="floatList" class="col-md-12" style="position: relative; opacity: 1; left: 0px; top: 0px;">'+html+'</div>');
							}
							else{
								$('#'+layoutId+'-drag-container').append('<div id='+Ext.id()+' cmptype="layout" aetype="floatList" class="col-md-12" style="position: relative; opacity: 1; left: 0px; top: 0px;">'+html+'</div>');
							}
							break;
		case 'buttonGroup':cmp=new $AE.Basic({
								dataRecord:record
									});
						   html = cmp.getDom();
						  if(!layoutId){
							  $('#editor-container').append('<div id='+Ext.id()+' cmptype="layout" aetype="buttonGroup" class="col-md-9" style="position: relative; opacity: 1; left: 0px; top: 0px;">'+html+'</div>');
						  }else{
							  $('#'+layoutId+'-drag-container').append('<div id='+Ext.id()+' cmptype="layout" aetype="buttonGroup" class="col-md-9" style="position: relative; opacity: 1; left: 0px; top: 0px;">'+html+'</div>');
						  }
						  break;
		case 'box':cmp=new $AE.Box({
								column: record.get('columnNum'),
								dataRecord:record
						});
					html = cmp.getDom();
					if(!layoutId){
						$('#editor-container').append('<div id='+Ext.id()+' cmptype="layout" aetype="Box" class="col-md-12" style="position: relative; opacity: 1; left: 0px; top: 0px;">'+html+'</div>');
					}else{
						$('#'+layoutId+'-drag-container').append('<div id='+Ext.id()+' cmptype="layout" aetype="Box" class="col-md-12" style="position: relative; opacity: 1; left: 0px; top: 0px;">'+html+'</div>');
					}
					break;
		case 'hBox':cmp=new $AE.HBox({
									column: record.get('columnNum'),
									dataRecord:record
						});
					html = cmp.getDom();
					if(!layoutId){
					   $('#editor-container').append('<div id='+Ext.id()+' cmptype="layout" aetype="HBox" class="col-md-12" style="position: relative; opacity: 1; left: 0px; top: 0px;">'+html+'</div>');
					}else{
					   $('#'+layoutId+'-drag-container').append('<div id='+Ext.id()+' cmptype="layout" aetype="HBox" class="col-md-12" style="position: relative; opacity: 1; left: 0px; top: 0px;">'+html+'</div>');
					}
					break;
		case 'vBox':cmp=new $AE.VBox({
									column: record.get('columnNum'),
									dataRecord:record
					});
					html = cmp.getDom();
					if(!layoutId){
						$('#editor-container').append('<div id='+Ext.id()+' cmptype="layout" aetype="VBox" class="col-md-12" style="position: relative; opacity: 1; left: 0px; top: 0px;">'+html+'</div>');
					}else{
						$('#'+layoutId+'-drag-container').append('<div id='+Ext.id()+' cmptype="layout" aetype="VBox" class="col-md-12" style="position: relative; opacity: 1; left: 0px; top: 0px;">'+html+'</div>');
					}
					break;
		case 'form':cmp=new $AE.Form({
									column: record.get('columnNum'),
									dataRecord:record,
								});
					html = cmp.getDom();
					if(!layoutId){
						$('#editor-container').append('<div id='+Ext.id()+' cmptype="layout" aetype="Form" class="col-md-12" style="position: relative; opacity: 1; left: 0px; top: 0px;">'+html+'</div>');
					}else{
						$('#'+layoutId+'-drag-container').append('<div id='+Ext.id()+' cmptype="layout" aetype="Form" class="col-md-12" style="position: relative; opacity: 1; left: 0px; top: 0px;">'+html+'</div>');
					}
					break;
		case 'queryForm':cmp=new $AE.QueryForm({
										column: record.get('columnNum'),
										dataRecord:record,
								});
						html = cmp.getDom();
						if(!layoutId){
							$('#editor-container').append('<div id='+Ext.id()+' cmptype="layout" aetype="QueryForm" class="col-md-12" style="position: relative; opacity: 1; left: 0px; top: 0px;">'+html+'</div>');
						}else{
							$('#'+layoutId+'-drag-container').append('<div id='+Ext.id()+' cmptype="layout" aetype="QueryForm" class="col-md-12" style="position: relative; opacity: 1; left: 0px; top: 0px;">'+html+'</div>');
						}
						break;
		case 'fieldSet':cmp=new $AE.FieldSet({
										column: record.get('columnNum'),
										dataRecord:record,
								});
						html = cmp.getDom();
						if(!layoutId){
							$('#editor-container').append('<div id='+Ext.id()+' cmptype="layout" aetype="FieldSet" class="col-md-12" style="position: relative; opacity: 1; left: 0px; top: 0px;">'+html+'</div>');
						}else{
							$('#'+layoutId+'-drag-container').append('<div id='+Ext.id()+' cmptype="layout" aetype="FieldSet" class="col-md-12" style="position: relative; opacity: 1; left: 0px; top: 0px;">'+html+'</div>');
						}
						break;
		case 'tabPanel':cmp=new $AE.TabPanel({
										dataRecord:record,
									});
									html = cmp.getDom();
									if(!layoutId){
										$('#editor-container').append('<div id='+Ext.id()+' cmptype="layout" aetype="TabPanel" class="col-md-12" style="position: relative; opacity: 1; left: 0px; top: 0px;">'+html+'</div>');
									}else{
										$('#'+layoutId+'-drag-container').append('<div id='+Ext.id()+' cmptype="layout" aetype="TabPanel" class="col-md-12" style="position: relative; opacity: 1; left: 0px; top: 0px;">'+html+'</div>');
									}
									break;
		case 'tab': 
					var tabGroupNumber=record.get('tabGroupNumber');
					var tabpanelRecord=null;
					if(tabGroupNumber){
						tabpanelRecord=record.ds.find('layoutSequence',record.get('tabGroupNumber'));
					}else if(record.get('parentLayoutId')){
						tabpanelRecord=record.ds.find('layoutId',record.get('parentLayoutId'));
					}
					else{
						tabpanelRecord=record.ds.find('id',record.get('viewParentLayoutId'));
					}
					if(tabpanelRecord){
						var tabPanelId=tabpanelRecord.get('id');
						cmp=new $AE.Tab({
							dataRecord:record,
							layoutId:tabPanelId,
							tagGroupNumber:tabGroupNumber,
							column: record.get('columnNum'),
							id:record.get('id')
						});
						html = cmp.getDom();
						var children=$('#'+tabPanelId+'-tab-drag-container').children();
						var cmpId=$('li',html).attr('id').replace('-tab-title','');
						if(children.length>=1){
							var tabTitle=$('li',html);
							var tabBody=$('.tab-content',html).children();
							$(tabBody).css('display','none');
							$('ul',children[0]).append(tabTitle);
							$('.tab-content',children[0]).append(tabBody);
						}
						else{
							$('#'+tabPanelId+'-tab-drag-container').append('<div id='+Ext.id()+' cmptype="layout" aetype="TabPanel" class="col-md-12" style="position: relative; opacity: 1; left: 0px; top: 0px;">'+html+'</div>');
							$('#'+cmpId+'-element-title').show();
							$('#'+cmpId+'element-delete-button').css('display','block');
							$('a',$('#'+tabPanelId)).parent().addClass("active");
						}
					}
					break;
			case 'placeHolder':
						cmp=(new $AE.PlaceHolder({
							layoutId:layoutId,
							dataRecord:record
						}));
						html=cmp.getDom(false,false);
						if(!layoutId){
							$('#editor-container').append('<div id='+Ext.id()+' cmptype="layout" aetype="placeHolder" class="col-md-12" style="position: relative; opacity: 1; left: 0px; top: 0px;">'+html+'</div>');
						}else{
							$('#'+layoutId+'-drag-container').append('<div id='+Ext.id()+' cmptype="layout" aetype="placeHolder" class="col-md-12" style="position: relative; opacity: 1; left: 0px; top: 0px;">'+html+'</div>');
						}
				break;
	     default:
	    	 	cmp=new $AE.Basic({
	    	 			dataRecord:record,
	    	 			column: record.get('columnNum'),
	    	 	});
	     		html = cmp.getDom();
	     		if(!layoutId){
	     			$('#editor-container').append('<div id='+Ext.id()+' cmptype="layout" aetype="OTHER" class="col-md-12" style="position: relative; opacity: 1; left: 0px; top: 0px;">'+html+'</div>');
	     		}else{
	     			$('#'+layoutId+'-drag-container').append('<div id='+Ext.id()+' cmptype="layout" aetype="OTHER" class="col-md-12" style="position: relative; opacity: 1; left: 0px; top: 0px;">'+html+'</div>');
	     		}
	     		break;
	}
	return cmp;
}

function initTagCmp(layoutId,type,record,isGrid,colClass,cmp){
	switch(type){
		case 'textField':cmp=(new $AE.TextField({
										layoutId:layoutId,
										dataRecord:record
						}));
						html = cmp.getDom(isGrid);
						if(isGrid){
							$('#'+layoutId+'-drag-container').append('<div id='+Ext.id()+' cmptype="input" aetype="TextField" class="grid-input-shell" style="opacity: 1;">'+html+'</div>');
						}else{
							$('#'+layoutId+'-drag-container').append('<div id='+Ext.id()+' cmptype="input" aetype="TextField" class="'+colClass+' input-shell" style="position: relative; opacity: 1; left: 0px; top: 0px;">'+html+'</div>');
						}
						break;
		case 'textArea':cmp=(new $AE.TextArea({
									layoutId:layoutId,
									dataRecord:record
							}));
						html = cmp.getDom(isGrid);
						if(isGrid){
							$('#'+layoutId+'-drag-container').append('<div id='+Ext.id()+' cmptype="input" aetype="TextArea" class="grid-input-shell" style="opacity: 1;height:90px;">'+html+'</div>');
						}else{
							$('#'+layoutId+'-drag-container').append('<div id='+Ext.id()+' cmptype="input" aetype="TextArea" class="'+colClass+' input-shell" style="position: relative; opacity: 1; left: 0px; top: 0px;">'+html+'</div>');
						}
						break;
		case 'comboBox':cmp=(new $AE.ComboBox({
										layoutId:layoutId,
										dataRecord:record
						}));
						html = cmp.getDom(isGrid);
						if(isGrid){
							$('#'+layoutId+'-drag-container').append('<div id='+Ext.id()+' cmptype="input" aetype="ComboBox" class="grid-input-shell" style="opacity: 1;">'+html+'</div>');
						}else{
							$('#'+layoutId+'-drag-container').append('<div id='+Ext.id()+' cmptype="input" aetype="ComboBox" class="'+colClass+' input-shell" style="position: relative; opacity: 1; left: 0px; top: 0px;">'+html+'</div>');
						}
						break;
		case 'multiComboBox':cmp=(new $AE.ComboBox({
											layoutId:layoutId,
											dataRecord:record
							}));
							html = cmp.getDom(isGrid);
							if(isGrid){
								$('#'+layoutId+'-drag-container').append('<div id='+Ext.id()+' cmptype="input" aetype="multiComboBox" class="grid-input-shell" style="opacity: 1;">'+html+'</div>');
							}else{
								$('#'+layoutId+'-drag-container').append('<div id='+Ext.id()+' cmptype="input" aetype="multiComboBox" class="'+colClass+' input-shell" style="position: relative; opacity: 1; left: 0px; top: 0px;">'+html+'</div>');
							}
							break;
		case 'lov':cmp=(new $AE.Lov({
									layoutId:layoutId,
									dataRecord:record
					}));
					html = cmp.getDom(isGrid);
					if(isGrid){
						$('#'+layoutId+'-drag-container').append('<div id='+Ext.id()+' cmptype="input" aetype="Lov" class="grid-input-shell" style="opacity: 1;">'+html+'</div>');
					}else{
						$('#'+layoutId+'-drag-container').append('<div id='+Ext.id()+' cmptype="input" aetype="Lov" class="'+colClass+' input-shell" style="position: relative; opacity: 1; left: 0px; top: 0px;">'+html+'</div>');
					}
					break;	
		case 'numberField':cmp=(new $AE.NumberField({
											layoutId:layoutId,
											dataRecord:record
							}));
							html = cmp.getDom(isGrid);
							if(isGrid){
								$('#'+layoutId+'-drag-container').append('<div id='+Ext.id()+' cmptype="input" aetype="NumberField" class="grid-input-shell" style="opacity: 1;">'+html+'</div>');
							}else{
								$('#'+layoutId+'-drag-container').append('<div id='+Ext.id()+' cmptype="input" aetype="NumberField" class="'+colClass+' input-shell" style="position: relative; opacity: 1; left: 0px; top: 0px;">'+html+'</div>');
							}
							break;
		case 'datePicker':cmp=(new $AE.DatePicker({
											layoutId:layoutId,
											dataRecord:record
		 					}));
							html = cmp.getDom(isGrid);
							if(isGrid){
								$('#'+layoutId+'-drag-container').append('<div id='+Ext.id()+' cmptype="input" aetype="DatePicker" class="grid-input-shell" style="opacity: 1;">'+html+'</div>');
							}else{
								$('#'+layoutId+'-drag-container').append('<div id='+Ext.id()+' cmptype="input" aetype="DatePicker" class="'+colClass+' input-shell" style="position: relative; opacity: 1; left: 0px; top: 0px;">'+html+'</div>');
							}
							break;	
		case 'dateTimePicker':cmp=(new $AE.DateTimePicker({
												layoutId:layoutId,
												dataRecord:record
								}));
							  html = cmp.getDom(isGrid);
							  if(isGrid){
								  $('#'+layoutId+'-drag-container').append('<div id='+Ext.id()+' cmptype="input" aetype="DateTimePicker" class="grid-input-shell" style="opacity: 1;">'+html+'</div>');
							  }else{
								  $('#'+layoutId+'-drag-container').append('<div id='+Ext.id()+' cmptype="input" aetype="DateTimePicker" class="'+colClass+' input-shell" style="position: relative; opacity: 1; left: 0px; top: 0px;">'+html+'</div>');
							  }
							  break;
		case 'checkBox':cmp=(new $AE.CheckBox({
										layoutId:layoutId,
										dataRecord:record
						}));
						html = cmp.getDom(isGrid);
						if(isGrid){
							$('#'+layoutId+'-drag-container').append('<div id='+Ext.id()+' cmptype="input" aetype="CheckBox" class="grid-input-shell" style="opacity: 1;">'+html+'</div>');
						}else{
							$('#'+layoutId+'-drag-container').append('<div id='+Ext.id()+' cmptype="input" aetype="CheckBox" class="'+colClass+' input-shell" style="position: relative; opacity: 1; left: 0px; top: 0px;">'+html+'</div>');
						}
						break;
		case 'radio':cmp=(new $AE.Radio({
									layoutId:layoutId,
									dataRecord:record
					}));
					html = cmp.getDom(isGrid);
					if(isGrid){
						$('#'+layoutId+'-drag-container').append('<div id='+Ext.id()+' cmptype="input" aetype="Radio" class="grid-input-shell" style="opacity: 1;">'+html+'</div>');
					}else{
						$('#'+layoutId+'-drag-container').append('<div id='+Ext.id()+' cmptype="input" aetype="Radio" class="'+colClass+' input-shell" style="position: relative; opacity: 1; left: 0px; top: 0px;">'+html+'</div>');
					}
					break;
		case 'label':cmp=(new $AE.Label({
									layoutId:layoutId,
									dataRecord:record
					}));
					html = cmp.getDom(isGrid);
					if(isGrid){
						$('#'+layoutId+'-drag-container').append('<div id='+Ext.id()+' cmptype="input" aetype="Label" class="grid-input-shell" style="opacity: 1;">'+html+'</div>');
					}else{
						$('#'+layoutId+'-drag-container').append('<div id='+Ext.id()+' cmptype="input" aetype="Label" class="'+colClass+' input-shell" style="position: relative; opacity: 1; left: 0px; top: 0px;">'+html+'</div>');
					}
					break;
		case 'placeHolder':cmp=(new $AE.PlaceHolder({
											layoutId:layoutId,
											dataRecord:record
							}));
							html=cmp.getDom(isGrid,false);
							if(isGrid){
								$('#'+layoutId+'-drag-container').append('<div id='+Ext.id()+' cmptype="input" aetype="placeHolder" class="grid-input-shell" style="opacity: 1;">'+html+'</div>');
							}else{
								$('#'+layoutId+'-drag-container').append('<div id='+Ext.id()+' cmptype="input" aetype="placeHolder" class="col-md-12" style="position: relative; opacity: 1; left: 0px; top: 0px;">'+html+'</div>');
							}
							break;
		case 'hidden':cmp=(new $AE.HiddenField({
										dataRecord:record
						}));
						html = cmp.getDom();
						$('#hidden-container').append('<div id='+Ext.id()+' cmptype="hidden" aetype="Hidden" class="hidden-shell" style="position: relative; opacity: 1; left: 0px; top: 0px;">'+html+'</div>');
						hiddenClickInit();
						hiddenIconClickInit();
						break;
		default:cmp=(new $AE.TextField({
							layoutId:layoutId,
							dataRecord:record
				}));
				html = cmp.getDom(isGrid);
				if(isGrid){
					$('#'+layoutId+'-drag-container').append('<div id='+Ext.id()+' cmptype="input" aetype="TextField" class="grid-input-shell" style="opacity: 1;">'+html+'</div>');
				}else{
					$('#'+layoutId+'-drag-container').append('<div id='+Ext.id()+' cmptype="input" aetype="TextField" class="'+colClass+' input-shell" style="position: relative; opacity: 1; left: 0px; top: 0px;">'+html+'</div>');
				}
				break;
	}
	return cmp;
	
}

function initCmp(inittype,layoutId,type,record,isGrid){
	var cmp;
	var layoutCmp;
	var layoutColumn=1;
	var colClass='';
	if(layoutId){
		layoutCmp=$AE.get(layoutId);
		if(layoutCmp && layoutCmp.column)
			layoutColumn=layoutCmp.getColumn();
	}
	if (layoutColumn == 1) {
        colClass = 'col-md-12';
    } else if (layoutColumn == 2) {
        colClass = 'col-md-6';
    } else if (layoutColumn == 3) {
        colClass = 'col-md-4';
    } else if (layoutColumn == 4) {
        colClass = 'col-md-3';
    } else {
        colClass = 'col-md-12';
    }
	switch(inittype){
		case 'button':cmp=initButtonCmp(layoutId,type,record,cmp,isGrid);break;
		case 'dataset':cmp=new $AE.DataSet({
								id:record.get('id')||'',
         						dataRecord:record
        					});
					   html = cmp.getDom();
					   $('#dataset-container').append('<div id='+Ext.id()+' cmptype="dataset" aetype="DataSet" class="dataset-shell" style="position: relative; opacity: 1; left: 0px; top: 0px;">'+html+'</div>');
					   cmp.setAttr(record);
					   datasetClickInit();
					   datasetIconClickInit();
					   break;
		case 'tag':cmp=initTagCmp(layoutId,type,record,isGrid,colClass,cmp);break;
		case 'layout':cmp=initLayoutCmp(layoutId,type,record,cmp);break;
		}
	initDraggable();
    $('.drag-container,.grid-drag-container').unbind('click').click(containerClickInit);
    $('.element-title').unbind('click').click(elementTitleClick);
    $('.element-delete-button').unbind('click').click(deleteButtonClick);
    $('.recycle-delete-button').unbind('click').click(recycleDeleteButtonClick);
    $('.recycle-undo-button').unbind('click').click(recycleUndoButtonClick);
    $('a[hrefType="tab"]').each(function(){
		$(this).unbind('click').click(function(e){
			$('.active').each(function(){
				$(this).removeClass("active");
				$('.element-title',this).css('display','none');
        		$('.element-delete-button',this).css('display','none');
				var i=$($(this).children()[2]).attr('href').replace('#','');
				$('#'+i).css('display','none');
			});
    		$(e.target).parent().addClass("active");
    		$('.element-title',$(e.target).parent()).css('display','block');
    		$('.element-delete-button',$(e.target).parent()).css('display','block');
    		var Id=$(e.target).attr('href').replace('#', '');
    		$('#'+Id).css('display','block');
    	});
	});
    //窗口大小修改后，scroll重置
    $("#editor-container").getNiceScroll().resize();
    $("#dataset-container-shell").getNiceScroll().resize();
    $(".grid-drag-container").getNiceScroll().resize();
	return cmp;
}

//根据模板的screen页面解析出来的dataset,添加到画布上
function datasetInit(datasets){
	for(var i=0;i<datasets['a:dataSet'].length;i++){
		var dataset=datasets['a:dataSet'][i];
		if(!$AE.get(dataset['@id'])){
			var record=dataSetDs.create();
			record.set('datasetId',dataset['@id']);
			convertToRecord(dataset,record);
			var cmp=initCmp('dataset',null,'dataset',record);
			cmp.setAttr(record);
		}
	}
}
function convertToRecord(obj,record){
	var fields=record.ds.fields;
	var reg=/\_/g;
	for(var key in fields){
		var k='@'+key.replace(reg,'');
		if(obj&&obj.hasOwnProperty(k)){
			record.set(key,obj[k.toLowerCase()]);
		}
	}
}


function initDraggable(){
	$('.drag-container').sortable({
        delay: 0,
        connectToSortable: ".drag-container",
        tolerance: "pointer",
        placeholder: "ui-state-highlight",
        helper:"original",
        item:"> *",
        revert: true,
        over: dragSortableInit,
        stop: function(event,ui){
        	dragSortableHandle(this,ui);
        }
    });
	
	//$('.drag-container').disableSelection();
	
	$('.grid-drag-container').sortable({
        delay: 0,
        connectToSortable: ".grid-drag-container",
        tolerance: "pointer",
        placeholder: "ui-state-highlight",
        revert: true,
        over: dragSortableInit,
        stop: function(event,ui){
        	dragSortableHandle(this,ui);
        }
    });
	
	$('.grid-button-container').sortable({
        delay: 0,
        connectToSortable: ".grid-button-container",
        tolerance: "pointer",
        placeholder: "ui-state-highlight",
        revert: true,
        over: dragSortableInit,
        stop: function(event,ui){
        	dragSortableHandle(this,ui);
        }
    });
	
	$('.grid-navbar-container').sortable({
        delay: 0,
        connectToSortable: ".grid-navbar-container",
        tolerance: "pointer",
        placeholder: "ui-state-highlight",
        revert: true,
        over: dragSortableInit,
        stop: function(event,ui){
        	dragSortableHandle(this,ui);
        }
    });
	
	$('.grid-select-container').sortable({
        delay: 0,
        connectToSortable: ".grid-select-container",
        tolerance: "pointer",
        placeholder: "ui-state-highlight",
        revert: true,
        over: dragSortableInit,
        stop: function(event,ui){
        	dragSortableHandle(this,ui);
        }
    });
    
    //拖拽容器sortable初始化
    $('.tab-drag-container').sortable({
        delay: 0,
        connectToSortable: ".tab-drag-container",
        tolerance: "pointer",
        placeholder: "ui-state-highlight",
        revert: true,
        over: dragSortableInit,
        stop: function(event,ui){
        	dragSortableHandle(this,ui);
        }
    });

    //dataset拖拽容器sortable初始化
    $('.dataset-drag-container').sortable({
        delay: 0,
        connectToSortable: ".dataset-drag-container",
        tolerance: "pointer",
        placeholder: "ui-state-highlight",
        revert: true,
        over: datasetContainerSortableInit,
        stop: function(event,ui){
        	dragSortableHandle(this,ui);
        }
    });
    
  //隐藏域拖拽容器sortable初始化
    $('.hidden-drag-container').sortable({
        delay: 0,
        connectToSortable: ".hidden-drag-container",
        tolerance: "pointer",
        placeholder: "ui-state-highlight",
        revert: true,
        over: hiddenContainerSortableInit,
        stop: function(event,ui){
        	dragSortableHandle(this,ui);
        }
    });

    //grid的多选框draggable初始化
    $('[aetype="GridSelectBox"]').each(function () {
        $(this).draggable({
            connectToSortable: ".grid-select-container",
            helper: 'clone',
            revert: "invalid",
            opacity: 0.5,
            start: function (event, ui) {
            },
            stop: function (event, ui) {
                $(ui.helper).removeClass().css('width', '').css('height', '');
                html = (new $AE.GridSelectBox({})).getDom();
                $(ui.helper).html(html);
                //var gridId=$(ui.helper).parent().attr('id').replace('-select-container','');
               // var cmp=$AE.CmpManager.get(gridId);
                //cmp.setSelectable(true);
                
                /*$('.element-delete-button').click(function(e){
                	cmp.setSelectable(false);
                });*/
            }
        });
    });

    //grid的单选框draggable初始化
    $('[aetype="GridRadioBox"]').each(function () {
        $(this).draggable({
            connectToSortable: ".grid-select-container",
            helper: 'clone',
            revert: "invalid",
            opacity: 0.5,
            start: function (event, ui) {
            },
            stop: function (event, ui) {
                $(ui.helper).removeClass().css('width', '').css('height', '');
                html = (new $AE.GridRadioBox({})).getDom();
                $(ui.helper).html(html);
            }
        });
    });

    //grid的导航条draggable初始化
    $('[aetype="GridNavBar"]').each(function () {
        $(this).draggable({
            connectToSortable: ".grid-navbar-container",
            helper: 'clone',
            revert: "invalid",
            opacity: 0.5,
            start: function (event, ui) {
            },
            stop: function (event, ui) {
                $(ui.helper).removeClass().css('width', '').css('height', '');
                html = (new $AE.GridNavBar({})).getDom();
                $(ui.helper).html(html);
            }
        });
    });
    //grid的新增按钮draggable初始化
    $('[aetype="GridAddButton"]').each(function () {
        $(this).draggable({
            connectToSortable: ".grid-button-container",
            helper: 'clone',
            revert: "invalid",
            opacity: 0.5,
            start: function (event, ui) {
            },
            stop: function (event, ui) {
                $(ui.helper).removeClass().css('width', '').css('height', '');
                var layoutId=$(ui.helper).parent().attr('id');
                if(layoutId){
                	layoutId=layoutId.replace('-button-container', '');
                }
                var index=0;
                if($AE.get(layoutId)){
                	var r=$AE.get(layoutId).getDataRecord();
                	var gridButtons=$AE.get(layoutId).getButtons();
                	index=r.ds.indexOf(r);
                	layoutDs.locate(index+1,true);
                	var record=layoutBtnDs.create();
                	record.set('buttonType','add');
                	record.set('buttonTypeDesc','新增');
                	gridButtons.push(record);
                	html = (new $AE.GridAddButton({
                		dataRecord:record
                	})).getDom();
                	$(ui.helper).html(html);
                }
            }
        });
    });


    //grid的删除按钮draggable初始化
    $('[aetype="GridDeleteButton"]').each(function () {
        $(this).draggable({
            connectToSortable: ".grid-button-container",
            helper: 'clone',
            revert: "invalid",
            opacity: 0.5,
            start: function (event, ui) {
            },
            stop: function (event, ui) {
                $(ui.helper).removeClass().css('width', '').css('height', '');
                var layoutId=$(ui.helper).parent().attr('id');
                if(layoutId){
                	layoutId=layoutId.replace('-button-container', '');
                }
                var index=0;
                if($AE.get(layoutId)){
                	var r=$AE.get(layoutId).getDataRecord();
                	var gridButtons=$AE.get(layoutId).getButtons();
                	index=r.ds.indexOf(r);
                	layoutDs.locate(index+1,true);
                	var record=layoutBtnDs.create();
                	record.set('buttonType','delete');
                	record.set('buttonTypeDesc','删除');
                	gridButtons.push(record);
                	html = (new $AE.GridDeleteButton({
                		dataRecord:record
                	})).getDom();
                	$(ui.helper).html(html);
                }
            }
        });
    });

    //grid的删除按钮draggable初始化
    $('[aetype="GridSaveButton"]').each(function () {
        $(this).draggable({
            connectToSortable: ".grid-button-container",
            helper: 'clone',
            revert: "invalid",
            opacity: 0.5,
            start: function (event, ui) {
            },
            stop: function (event, ui) {
                $(ui.helper).removeClass().css('width', '').css('height', '');
                var layoutId=$(ui.helper).parent().attr('id');
                if(layoutId){
                	layoutId=layoutId.replace('-button-container', '');
                }
                var index=0;
                if($AE.get(layoutId)){
                	var r=$AE.get(layoutId).getDataRecord();
                	var gridButtons=$AE.get(layoutId).getButtons();
                	index=r.ds.indexOf(r);
                	layoutDs.locate(index+1,true);
                	var record=layoutBtnDs.create();
                	record.set('buttonType','save');
                	record.set('buttonTypeDesc','保存');
                	gridButtons.push(record);
                	html = (new $AE.GridSaveButton({
                		dataRecord:record
                	})).getDom();
                	$(ui.helper).html(html);
                }
            }
        });
    });

    //grid的删除按钮draggable初始化
    $('[aetype="GridClearButton"]').each(function () {
        $(this).draggable({
            connectToSortable: ".grid-button-container",
            helper: 'clone',
            revert: "invalid",
            opacity: 0.5,
            start: function (event, ui) {
            },
            stop: function (event, ui) {
                $(ui.helper).removeClass().css('width', '').css('height', '');
                var layoutId=$(ui.helper).parent().attr('id');
                if(layoutId){
                	layoutId=layoutId.replace('-button-container', '');
                }
                var index=0;
                if($AE.get(layoutId)){
                	var r=$AE.get(layoutId).getDataRecord();
                	var gridButtons=$AE.get(layoutId).getButtons();
                	index=r.ds.indexOf(r);
                	layoutDs.locate(index+1,true);
                	var record=layoutBtnDs.create();
                	record.set('buttonType','clear');
                	record.set('buttonTypeDesc','清除');
                	gridButtons.push(record);
                	html = (new $AE.GridClearButton({
                		dataRecord:record
                	})).getDom();
                	$(ui.helper).html(html);
                }
            }
        });
    });

    $('[aetype="GridExcelButton"]').each(function () {
        $(this).draggable({
            connectToSortable: ".grid-button-container",
            helper: 'clone',
            revert: "invalid",
            opacity: 0.5,
            start: function (event, ui) {
            },
            stop: function (event, ui) {
                $(ui.helper).removeClass().css('width', '').css('height', '');
                var layoutId=$(ui.helper).parent().attr('id');
                if(layoutId){
                    layoutId=layoutId.replace('-button-container', '');
                }
                var index=0;
                if($AE.get(layoutId)){
                    var r=$AE.get(layoutId).getDataRecord();
                    var gridButtons=$AE.get(layoutId).getButtons();
                    index=r.ds.indexOf(r);
                    layoutDs.locate(index+1,true);
                    var record=layoutBtnDs.create();
                    record.set('buttonType','excel');
                    record.set('buttonTypeDesc','导出');
                    gridButtons.push(record);
                    html = (new $AE.GridExcelButton({
                        dataRecord:record
                    })).getDom();
                    $(ui.helper).html(html);
                }
            }
        });
    });

    //dataset draggable初始化
    $('[aetype="DataSet"]').each(function () {
        $(this).draggable({
            connectToSortable: ".dataset-drag-container",
            helper: 'clone',
            revert: "invalid",
            opacity: 0.5,
            start: function (event, ui) {
            },
            stop: function (event, ui) {
                $(ui.helper).removeClass().css('width', '').css('height', '');
                var record=dataSetDs.create();
                html = (new $AE.DataSet({
                	dataRecord:record
                })).getDom();
                $(ui.helper).html(html);
                $(ui.helper).addClass('dataset-shell');

                //所有ds组件的click初始化
                datasetClickInit();

                //所有ds组件的icon的click初始化
                datasetIconClickInit();
                
              //初始化所有的element-delete-button的点击事件
                $('.element-delete-button').unbind('click').click(deleteButtonClick);
            }
        });
    });

    //grid的导航条draggable初始化
    $('[aetype="Hidden"]').each(function () {
        $(this).draggable({
            connectToSortable: ".hidden-drag-container",
            helper: 'clone',
            revert: "invalid",
            opacity: 0.5,
            start: function (event, ui) {
            },
            stop: function (event, ui) {
                $(ui.helper).removeClass().css('width', '').css('height', '');
                html = (new $AE.HiddenField({})).getDom();
                $(ui.helper).html(html);
                $(ui.helper).addClass('hidden-shell');

                //所有ds组件的click初始化
                hiddenClickInit();

                //所有ds组件的icon的click初始化
                hiddenIconClickInit();
              
                //初始化所有的element-delete-button的点击事件
                $('.element-delete-button').unbind('click').click(deleteButtonClick);
            }
        });
    });
    
    $('[aetype="Tab"]').each(function () {
        $(this).draggable({
            connectToSortable: ".tab-drag-container",
            helper: 'clone',
            revert: "invalid",
            opacity: 0.5,
            start: function (event, ui) {
            },
            stop: function (event, ui) {
            	var children=$(ui.helper).parent().children();
            	var parentCmpId=$(ui.helper).parent().attr('id').replace('-tab-drag-container','');
            	tagGroupNumber=$AE.get(parentCmpId).layoutSequence;
            	var record=layoutDs.create();
            	html = (new $AE.Tab({
            		dataRecord:record,
            		layoutId:parentCmpId,
                	layoutSequence:(record.ds.indexOf(record)+1)*10,
                	tagGroupNumber:tagGroupNumber
            	})).getDom();
            	var cmpId=$('li',html).attr('id').replace('-tab-title','');
            	if(children.length>1){
            		$('.ui-draggable-dragging',$(ui.helper).parent()).remove();
            		children=$(ui.helper).parent().children();
            		var tabTitle=$('li',html);
            		var tabBody=$('.tab-content',html).children();
            		$(tabBody).css('display','none');
            		//$(tabBody).css('height','130px');
            		$('ul',children[0]).append(tabTitle);
            		$('.tab-content',children[0]).append(tabBody);
            		
            	}
            	else{
            		$(ui.helper).removeClass().css('width', '').css('height', '');
            		$(ui.helper).html(html);
            		$('.element-title',ui.helper).show();
            		$('.element-delete-button',ui.helper).css('display','block');
            		$('a',ui.helper).parent().addClass("active");
            		// $(ui.helper).addClass('hidden-shell');
            		
            	}
            	
            	//初始化所有的element-title的点击事件
                $('.element-title').unbind('click').click(elementTitleClick);
                
                //初始化所有的element-delete-button的点击事件
                $('#'+cmpId+'-delete-button').unbind('click').click(deleteButtonClick);
                
            	$('a').each(function(){
            		$(this).unbind('click').click(function(e){
            			$('.active').each(function(){
            				$(this).removeClass("active");
            				$('.element-title',this).css('display','none');
                    		$('.element-delete-button',this).css('display','none');
            				var i=$($(this).children()[2]).attr('href').replace('#','');
            				$('#'+i).css('display','none');
            			});
                		$(e.target).parent().addClass("active");
                		$('.element-title',$(e.target).parent()).css('display','block');
                		$('.element-delete-button',$(e.target).parent()).css('display','block');
                		var Id=$(e.target).attr('href').replace('#', '');
                		$('#'+Id).css('display','block');
                	});
            	});
            	
            }
        });
    });
    
    
    //所有组件的draggable初始化
    $('.component-shell').each(function () {
        $(this).draggable({
            connectToSortable: $(this).attr('cmptype') == 'layout' ? ".drag-container" : ".drag-container,.grid-drag-container",
            helper: 'clone',
            revert: "invalid",
            opacity: 0.5,
            start: function (event, ui) {
            },
            stop: function (event, ui) {
                draggableInit(event, ui, this);

                //控件拖拽完成后重新计算页面上的sortable组件
                setTimeout(function () {
                        //初始化所有的拖拽容器的点击边框突出动作
                        $('.drag-container,.grid-drag-container').unbind('click').click(containerClickInit);

                        //初始化所有的element-title的点击事件
                        $('.element-title').unbind('click').click(elementTitleClick);
                        
                        //初始化所有的element-delete-button的点击事件
                        $('.element-delete-button').unbind('click').click(deleteButtonClick);
                        
                      //初始化所有的recycle-delete-button的点击事件
                        $('.recycle-delete-button').unbind('click').click(recycleDeleteButtonClick);
                        
                      //初始化所有的recycle-undo-button的点击事件
                        $('.recycle-undo-button').unbind('click').click(recycleUndoButtonClick);
                    },
                    500);
                
            }

        });
    });
}

