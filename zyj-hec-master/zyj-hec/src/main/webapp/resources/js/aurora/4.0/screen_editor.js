//不严谨，仅限此页面
Array.prototype.contains=function(str){
	var bool=false;
	for(var i=0;i<this.length;i++){
		if(this[i].toLowerCase()==str.toLowerCase()){
			bool=true;
			break;
		}
	}
	return bool;
}

function init(screen_config){
	var view=eval('('+screen_config+')');
	screenDatasets=view['a:dataSets'];//span,div这些怎么处理？要不要保存？
	layouts=['form','queryForm','box','hBox','vBox','fieldSet','tab','tabPanel','grid','formToolBar','formBody','span','div','screenTopToolbar','buttonGroup'];
	tags=['textField','numberField','comboBox','multiComboBox','checkBox','lov','textArea','datePicker','dateTimePicker','label','radio','button','gridButton','toolbarButton'];
		getDataSetDetail(view['a:dataSets']);
		initBindDs(view['a:dataSets']);
		if(view.hasOwnProperty('a:screenBody')) view=view['a:screenBody'];
		for(var attr in view){//解析view页面组件
			var type='';
			if(attr.indexOf('a:')>-1){
				type=attr.substring(2);
			}else{
				type=attr;
			}
			if(layouts.contains(type)){
				getLayout(view[attr],type);//解析layout组件
			}
			else if(tags.contains(type)){
				
			}
		}
}

//初始化绑定dataset的option数据
function initBindDs(dss){
	if(dss){
		var ds=dss['a:dataSet'];
		for(var i=0;i<ds.length;i++){
			var id=ds[i]['@id'];
			var record=$au('bpm_bindtarget_lists').create();
			record.set('datasetId',id);
		}
		datasetInit(dss);
	}
}

//layout:组件配置信息，layoutType:组件类型，parentId:父级组件ID
function getLayout(layout,layoutType,parentId){
	if(layout.length){
		for(var i=0;i<layout.length;i++){
			getLayoutDetail(layout[i],layoutType,parentId);
		}
	}
	else{
		getLayoutDetail(layout,layoutType,parentId);
	}
}

function getLayoutDetail(layout,layoutType,parentId){
	if(layoutType=='grid'){
		getGridLayout(layout,parentId,'grid');
	}
	else{
		var layout_type_desc='';
		switch(layoutType){
			case 'queryForm':layout_type_desc='queryform';break;
			case 'form':layout_type_desc='表单';break;
			case 'hBox':layout_type_desc='横向布局';break;
			case 'box':layout_type_desc='盒布局';break;
			case 'vBox':layout_type_desc='纵向布局';break;
			case 'fieldSet':layout_type_desc='fieldSet';break;
			case 'tab':layout_type_desc='标签页';break;
			case 'tabPanel':layout_type_desc='标签组';break;
			case 'grid':layout_type_desc='列表';break;
			default :layout_type_desc=layoutType;break;
		}
		var record=$au('screen_layout_basic_ds').create();
		var seq=(record.ds.indexOf(record)+1)*10;
		record.set('layout_type',layoutType);
		record.set('layout_type_desc',layout_type_desc);
		record.set('layout_sequence',seq);
		convertToRecord(layout,record);
		if(parentId){
			record.set('view_parent_layout_id',parentId);
			var pr=$au('screen_layout_basic_ds').find('id',parentId);
			if(pr && !pr.get('screen_id') && record.get('screen_id')){
				var sr=$au('screen_include_ds').find('screen_id',record.get('screen_id'));
				if(sr)
					sr.set('parent_container_id',pr.get('layout_sequence'));
			}
			
		}
		if(!record.get('id')){
			record.set('id',getNewId($au('screen_layout_basic_ds'),'layout_type',layoutType));
		}
		if(record.get('prompt')){
			record.set('title_display',getPrompt(record.get('prompt')));
		}
		var cmp=initCmp('layout',parentId,layoutType,record);
		cmp.setAttr(record);
		var layoutId=cmp.id;
		
		if(layout.length){
			for(var i=0;i<layout.length;i++){
				getTag(layoutId,layout[i],record);
			}
		}else{
			getTag(layoutId,layout,record);
		}
	}
}
function getTag(layoutId,obj,layoutRecord){
	for(var key in obj){
		if(typeof obj[key]=='object'){
			var type=key.substring(2);
			if(type=='groupButtons'){ obj=obj['a:groupButtons']; getTag(layoutId,obj,layoutRecord);}
			if(type=='floatList'){ obj=obj['a:floatList']; getGridLayout(obj,layoutId,'floatList');}
			if(type=='tabs'){ obj=obj['a:tabs']['a:tab']; getLayout(obj,'tab',layoutId);}
			else if(layouts.contains(type)){
				getLayout(obj[key],type,layoutId);
			}
			else if(tags.contains(type)){
				var tag=obj[key];
				if(tag.length){
					for(var i=0;i<tag.length;i++){
						if(key.indexOf('button')!=-1||key.indexOf('Button')!=-1){
							getButtonDetail(layoutId,tag[i],key.substring(2),false,layoutRecord);
						}
						else{
							getTagDetail(layoutId,tag[i],key.substring(2),false,layoutRecord);
						}
					}
				}
				else{
					if(key.indexOf('button')!=-1||key.indexOf('Button')!=-1){
						getButtonDetail(layoutId,tag,key.substring(2),false,layoutRecord);
					}
					else{
						getTagDetail(layoutId,tag,key.substring(2),false,layoutRecord);
					}
				}
			}
			else if(type=='placeHolder') {dynamicCmpInit(layoutId,obj['a:placeHolder'],layoutRecord)};
		}
	}
}
function getTagDetail(layoutId,tag,tagType,isGrid,layoutRecord){
	//if(tags.contains(tagType)){
		var tag_type_desc='';
		switch(tagType){
			case 'textField':tag_type_desc='文本框';break;
			case 'lov':tag_type_desc='值列表';break;
			case 'comboBox':tag_type_desc='下拉框';break;
			case 'dateTimePicker':ag_type_desc='日期时间框';break;
			case 'datePicker':tag_type_desc='日期框';break;
			case 'label':tag_type_desc='标签';break;
			case 'checkBox':tag_type_desc='多选框';break;
			case 'radio':tag_type_desc='单选框';break;
			case 'textArea':tag_type_desc='文本区';break;
			default : tag_type_desc=tagType;break;
		}
		layoutRecord.ds.locate(layoutRecord.ds.indexOf(layoutRecord)+1);
		var record=$au('screen_tags_basic_ds').create();
		var seq=(record.ds.indexOf(record)+1)*10;
		record.set('tag_type',tagType);
		record.set('tag_type_desc',tag_type_desc);
		record.set('tag_sequence',seq);
		if(!tag['prompt']){
			if(tag['@bindtarget']||tag['@bindTarget']){
				var dsr=dataSetDs.find('dataset_id',tag['@bindtarget'])||dataSetDs.find('dataset_id',tag['@bindTarget']);
				if(dsr){
					dataSetDs.locate(dataSetDs.indexOf(dsr)+1,true);
					var dsf=$au('screen_dataset_fields_ds').find('name',tag['@name']);
					if(dsf){
						record.set('prompt_desc',dsf.get('prompt_desc'));
						record.set('prompt',dsf.get('prompt'));
						record.set('required',dsf.get('required'));
						record.set('readonly',dsf.get('readonly'));
					}
				}
			}else{
				var dsr=dataSetDs.find('dataset_id',layoutRecord.get('bindtarget'));
				if(dsr){
					dataSetDs.locate(dataSetDs.indexOf(dsr)+1,true);
					var dsf=$au('screen_dataset_fields_ds').find('name',tag['@name']);
					if(dsf){
						record.set('prompt_desc',getPrompt(dsf.get('prompt')));
						record.set('prompt',dsf.get('prompt'));
						record.set('required',dsf.get('required'));
						record.set('readonly',dsf.get('readonly'));
					}
				}
			}
		}
		convertToRecord(tag,record);
		var cmp=initCmp('tag',layoutId,tagType,record,isGrid);
		if(!record.get('id')){
			record.set('id',layoutRecord.get('id')+'_'+getNewId($au('screen_tags_basic_ds'),'tag_type',tagType));
		}
		cmp.setAttr(record);
	//}
}
function getButtonDetail(layoutId,btn,btnTag,isGrid,layoutRecord){
	if(btnTag=='placeHolder'){
		layoutRecord.ds.locate(layoutRecord.ds.indexOf(layoutRecord)+1);
		var record=$au('screen_buttons_result_ds').create();
		var seq=(record.ds.indexOf(record)+1)*10;
		record.set('operation_type',operation_type);
		record.set('operation_type_desc',operation_type_desc);
		record.set('button_tag',btnTag);
		record.set('button_sequence',seq);
		record.set('type','placeHolder');
		
		convertToRecord(btn,record);
		var cmpType=btnTag.toLowerCase();
		var cmp=initCmp('button',layoutId,'placeHolder',record,isGrid);
		cmp.setAttr(record);
	}else{
		
		var operation_type='';
		var operation_type_desc='';
		var type_desc='';
		var btnType=btn['@type'];
		var pmt=btn['@text'];
		switch(btnType){
			case 'add':operation_type='OPERATION';operation_type_desc='操作';type_desc='新增';break;
			case 'delete':operation_type='OPERATION';operation_type_desc='操作';type_desc='删除';break;
			case 'save':operation_type='OPERATION';operation_type_desc='操作';type_desc='保存';break;
			case 'clear':operation_type='OPERATION';operation_type_desc='操作';type_desc='清除';break;
			case 'excel':operation_type='OPERATION';operation_type_desc='操作';type_desc='导出';break;
		}
		layoutRecord.ds.locate(layoutRecord.ds.indexOf(layoutRecord)+1);
		var record=$au('screen_buttons_result_ds').create();
		var seq=(record.ds.indexOf(record)+1)*10;
		record.set('operation_type',operation_type);
		record.set('operation_type_desc',operation_type_desc);
		record.set('button_tag',btnTag);
		record.set('button_sequence',seq);
		record.set('type',btnType);
		record.set('type_desc',type_desc);
		if(pmt){
			record.set('text_display',getPrompt(pmt));
		}
		if(btnTag=='gridButton'){
			switch(btnType){
				case 'add':record.set('text_display','新增');break;
				case 'delete':record.set('text_display','删除');break;
				case 'save':record.set('text_display','保存');break;
				case 'clear':record.set('text_display','清除');break;
				case 'excel':record.set('text_display','导出');break;
			}
		} 
		convertToRecord(btn,record);
		var cmpType=btnTag.toLowerCase();
		if(isGrid) cmpType='grid'+btnType;
		var cmp=initCmp('button',layoutId,cmpType,record,isGrid);
		if(!record.get('id')){
			if(btnType){
				record.set('id',layoutId+'_'+getNewId($au('screen_buttons_result_ds'),'type',btnType));
			}else if(record.get('click')){
				record.set('id',layoutId+'_'+record.get('click'));
			}
		}
		if(!isGrid) cmp.setAttr(record);
	}
}

function convertToRecord(obj,record){
	var fields=record.ds.fields;
	var reg=/\_/g;
	for(var key in fields){
		if(key.indexOf('tf_')==0||key.indexOf('nf_')==0||key.indexOf('cb_')==0||key.indexOf('ck_')==0||key.indexOf('dk_')==0){key=key.substring(3);}
		else if(key.indexOf('lov_')==0||key.indexOf('dtk_')==0){key=key.substring(4);}
		else if(key.indexOf('l_')==0||key.indexOf('r_')==0){key=key.substring(4);}
		var k='@'+key.replace(reg,'');
		if(key=='dataset') k='@bindtarget';
		if(key=='lock_flag') k='@lock';
		if(key=='prompt'){
			var prompt=obj['@prompt'];
			if(prompt){
				record.set('prompt_desc',getPrompt(prompt));
			}
		}
		if(obj&&obj.hasOwnProperty(k)){
			record.set(key,obj[k.toLowerCase()]);
		}
	}
	if(obj.hasOwnProperty('@screen_id')){
		record.set('screen_id',obj['@screen_id']);
	}
}

function getGridLayout(grid,parentId,layoutType){
	var record=$au('screen_layout_basic_ds').create();
	var seq=(record.ds.indexOf(record)+1)*10;
	record.set('layout_type',layoutType);
	record.set('layout_type_desc',layoutType);
	record.set('layout_sequence',seq);
	if(parentId) record.set('view_parent_layout_id',parentId);
	convertToRecord(grid,record);
	var cmp=initCmp('layout',parentId,layoutType,record);
	if(!record.get('id')){
		record.set('id',getNewId($au('screen_layout_basic_ds'),'layout_type',layoutType));
	}
	cmp.setAttr(record);
	var layoutId=cmp.id;
	
	var columns=grid['a:columns']['a:column'];//可能存在placeholder
	var editors=grid['a:editors'];
	var buttons=grid['a:toolBar'];//可能存在placeholder
	for(var i=0;i<columns.length;i++){
		var column=columns[i];
		var tagType='textField';
		if(column.hasOwnProperty('@editor')){
			var e=getColumnEditor(editors,column['@editor']);
			if(!e['editorType']){
				getTagDetail(layoutId,column,'textField',true,record);
			}else{
				getTagDetail(layoutId,column,e['editorType'],true,record);
			}
		}
		else{
			getTagDetail(layoutId,column,tagType,true,record);
		}
	}
	if(grid['a:columns'].hasOwnProperty('a:placeHolder')){
		var tagplaceholders=grid['a:columns']['a:placeHolder'];
		dynamicCmpInit(layoutId,tagplaceholders,record,true,false);
	}
	if(buttons){
		for(var attr in buttons){
			if(typeof buttons[attr] =='object' && attr!='a:placeHolder'){
				var btns=buttons[attr];
				var btnTag=attr.substring(2);
				if(btns){
					if(btns.length){
						//for(var i=0;i<btns.length;i++){
							Ext.each(btns, function(btn) {
								getButtonDetail(layoutId,btn,btnTag,true,record);
								
							});
						//}
					}
					else{
						getButtonDetail(layoutId,btns,btnTag,true,record);
					}
				}
			}
		}
	}
	if(grid['a:toolBar'] && grid['a:toolBar'].hasOwnProperty('a:placeHolder')){
		var btnplaceholders=grid['a:toolBar']['a:placeHolder'];
		dynamicCmpInit(layoutId,btnplaceholders,record,true,true);
	}
	$('.grid-drag-container').niceScroll();
}

function getColumnEditor(editors,id){
	var editor={};
	for(var key in editors){
		if(editors[key]['@id']==id){
			editor['editorType']=key.substring(2);
			editor['editorPro']=editors[key];
			break;
		}
	}
	return editor;
}

//dataset信息
function getDataSetDetail(datasets){
	if(datasets){
		for(var i=0;i<datasets['a:dataSet'].length;i++){
			var dataset=datasets['a:dataSet'][i];
			var record=$au('screen_dataset_ds').create();
			record.set('dataset_id',dataset['@id']);
			convertToRecord(dataset,record);
			var cmp=initCmp('dataset',null,'dataset',record,false);
			cmp.setAttr(record);
			if(dataset.hasOwnProperty('a:fields')){//页面上没有提供对dataset的field的操作，所以保存field信息没有必要。
				getDsFieldDetail(dataset,record)
			}
		}
	}
}
//dataset的field信息
function getDsFieldDetail(dataset,dsRecord){
	var dataset_id=dsRecord.get('dataset_id');
	dsRecord.ds.locate(dsRecord.ds.indexOf(dsRecord)+1,true);
	var fields=dataset['a:fields']['a:field'];
	for(var i=0;i<fields.length;i++){
		var record=$au('screen_dataset_fields_ds').create();
		var sequence=(record.ds.indexOf(record)+1)*10;
		record.set('field_sequence',sequence);
		record.set('dataset_id',dataset_id);
		convertToRecord(fields[i],record);
		if(fields[i].hasOwnProperty('a:mapping')){
			getFdMapperDetail(fields[i]['a:mapping'],record);
		}
	}
}
//field的mapper信息
function getFdMapperDetail(field,fdRecord){
	fdRecord.ds.locate(fdRecord.ds.indexOf(fdRecord)+1);
	var mappers=field['a:map'];
	for(var i=0;i<mappers.length;i++){
		var record=$au('screen_field_mapper_ds').create();
		var sequence=(record.ds.indexOf(record)+1)*10;
		record.set('mapper_sequence',sequence);
		convertToRecord(mappers[i],record);
	}
}

//获取从数据库获取prompt，采用同步请求，而非异步,消耗时间过长，
function getPrompt(prompt){
	var promptDs=$au('prompt_ds');
	var desc="";
	var sf=this;
/*	Aurora.request({
		lockMessage:'',
		sync:false,
		processData:false,
		url:$au('prompt_query_link').getUrl(),
        para:{'prompt_code':prompt},
        success:function(result){
        	if(result.result.record){
        		desc=result.result.record.description;
        	}
        }
      });*/
	return desc||prompt;
}

function load_val(callback){
	
}

function dynamicCmpInit(layoutId,placeholders,layoutRecord,isGrid,isBtn){
	for(var i=0;i<placeholders.length;i++){
			if(!isBtn){
				getTagDetail(layoutId,placeholders[i],'placeHolder',isGrid,layoutRecord);
			}else{
				getButtonDetail(layoutId,placeholders[i],'placeHolder',isGrid,layoutRecord)
			}
		var phId=placeholders[i]['@id'];
		var targetView=$('[targetid="'+phId+'"]',vc);
		if(targetView.length==1){
			var children=$(targetView).children();//children不一定是p:switch，也可能是p:loop
			getPlaceHolderField(layoutId,children,layoutRecord,phId,phId);
		}
	}
}

function getPlaceHolderField(layoutId,children,layoutRecord,dimensionLayoutId,targetId){
	var reg=/\/model\/((\w)+)(\/record\/)?/;
	var m='';
	if(children && children.length && children.length>0){
		for(var i=0;i<children.length;i++){
			var nodeName=children[i].nodeName;//loop,switch,不一定
			var source='';
			var switches=[];
			switch(nodeName){
				case 'p:loop':source=$(children[i]).attr('source');switches=$(children[i]).children();
				case 'p:switch':switches.push(children[i]);source=$(children[i]).attr('test');
			}
			if(source){
				source.replace(reg,function(s,value){
					m=value;
				});
			}
			var model=init_models[m];
			if(model){
				var vConfig={};
				vConfig.targetId=targetId;
				vConfig.source=source;
				vConfig.switches=switches;
				vConfig.model=model;
				getSwitches(layoutId,layoutRecord,dimensionLayoutId,vConfig);
			}else{
				//$('#'+targetId).parent().remove();
				//layoutRecord.ds.remove(layoutRecord);
			}
		}
	}
}

function getSwitches(layoutId,layoutRecord,dimensionLayoutId,vConfig){
	var switches=vConfig.switches;
	var model=vConfig.model;
		for(var i=0;i<model.length;i++){
			for(var j=0;j<switches.length;j++){
				var testValue='';
				var value='';
				var test=$(switches[j]).attr('test');
				if(test){
					test=test.substring(test.indexOf('@')+1);
					testValue=model[i]['@'+test];
				}
				var cases=$(switches[j]).children();
				if(cases && cases.length && cases.length>0){
					for(var z=0;z<cases.length;z++){
						value=$(cases[z]).attr('value');	
						if(testValue==value||value=='*'){
							var pc=$(cases[z]).children();
							if(pc.length>0){
								var components=$(pc).children();
								for(var w=0;w<components.length;w++){
									var isGrid=layoutRecord.get('layout_type')=='grid'?true:false;
									var record=null;
									if(components[w].nodeName.substring(2)=='button'){
										getButtonDetail(dimensionLayoutId,htmltoObject(components[w]),components[w].nodeName.substring(2),isGrid,layoutRecord);
										record=btnDimensionDs.create();
									}else{
										getTagDetail(dimensionLayoutId,htmltoObject(components[w]),components[w].nodeName.substring(2),isGrid,layoutRecord);
										record=dimensionDs.create();//有可能是button,不仅仅是tag
									}
									record.set('dimension_sequence',(record.ds.indexOf(record)+1)*10);
									record.set('targetid',vConfig.targetId);
									record.set('source',vConfig.source);
									record.set('test',test);
									record.set('value',value);
									record.set('doc_id',doc_id);
									record.set('doc_type',doc_type);
								}
							}
						}
					}
				}
			}
		}
		if($('.drag-container',$('#'+layoutId)).children().length==0){
//			$('#'+vConfig.targetId).parent().remove();
//			layoutRecord.ds.remove(layoutRecord);
		}
}

function htmltoObject(html){
	var obj={};
	var attrs = html.attributes;
	for(var i=0;i<attrs.length;i++){
		obj['@'+attrs[i].name]=attrs[i].value;
	}
	return obj;
}

