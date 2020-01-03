(function ($A) {
	var rowClass = 'item-float-list-row',
	cellClass = 'item-list-cell',
	cellColClass = 'item-float-list-row-col-',
	clickableClass = 'item-list-row-clickable',
	NOT_FOUND = '未找到',
	METHOD = '方法!',
	TOP = 'top',
	ATT_RECORD_ID = '_row',
	EVT_CLICK = 'click',
	EVT_BEFORE_CLOSE_ROW = 'beforecloserow',
	EVT_CLOSE_ROW = 'closerow',
	EVT_BEFORE_LOAD_MORE_DATA = 'beforeloadmoredata',
	EVT_LOAD_MORE_DATA = 'loadmoredata',
	EVT_ROW_CLICK = 'rowclick',
	EVT_DBLCLICK = 'dblclick',
	FLOATLIST_ROW_SELECT = 'floatlist-row-selected',
	_ = '_',
    __ = '__',
    _N = '',
	C = '-c',
    U = '-u',
    ATYPE = 'atype',
	GRID_CKB = 'grid-ckb ',
	ITEM_CKB = 'item-ckb',
	ITEM_CKB_U = ITEM_CKB + U,//'item-ckb-u'
    ITEM_CKB_C = ITEM_CKB + C,//'item-ckb-c'
    GRID_CELL = 'grid-cell',
    ROW_CHECK = 'rowcheck',
    ROW_RADIO = 'rowradio',
    ROW_NUMBER = 'rownumber',
    ROW_ALT = 'row-alt',
    DIV = 'div',
    GRID_ROWBOX = 'grid-rowbox',
    RECORD_ID = 'recordid',
    TRUE = true,
    FALSE = false,
    NULL = null,
    EACH = Ext.each,
	EVT_LOAD = 'load';
	$A.FloatList = Ext.extend($A.Component, {
		constructor: function (config) {
//			debugger;
			var sf = this;
			sf.bindTarget = config.bindtarget;
			sf.ds = $au(sf.bindTarget);
			sf.columns = config.columns;
			sf.exportColumns = config.exportColumns;
			sf.columnNumber = config.columnnumber;
			sf.rowStyle = config.rowstyle;
			sf.closeable = config.closeable;
			sf.loadMore = config.loadmore;
			sf.rowArr = [];
			sf.cellArr = [];
			$A.FloatList.superclass.constructor.call(sf, config);
		},
		initComponent: function (config) {
			$A.FloatList.superclass.initComponent.call(this, config);
			sf = this;
			wrap = sf.wrap = Ext.get(sf.id);
			sf.flh = wrap.child('thead.item-list-head');
			sf.flc = wrap.child('tbody.item-list-content');
			sf.flcl = wrap.child('td.item-list-content-left');
			sf.flcc = wrap.child('td.item-list-content-center');
			sf.fll = wrap.child('div.item-float-list');
			sf.flcr = wrap.child('td.item-list-content-right');
			sf.flf = wrap.child('tfoot.item-list-foot');
//			sf.timer = null;
			sf.moreBtn = wrap.child('div.item-float-list-more-btn');
			sf.initTemplate();
			sf.processDataSetListener('on');
			sf.onLoad();
			// if(!Ext.isIE8){
			// 	$("div.item-float-list").niceScroll({enablemousewheel:false,horizrailenabled:false});
			// }
		},
		processListener: function (ou) {
			var sf = this;
			$A.FloatList.superclass.processListener.call(sf, ou);
			if(sf.loadMore){
				sf.moreBtn['on']('click', sf.onLoadMoreData, sf);
			}
			if(wrap){
				wrap[ou]('mousewheel',sf.onMouseWheel,sf);
			}
		},
		processRowListener: function (ou, rowElement, record) {
			var sf = this;
			if(sf.closeable){
				var closeBtn = Ext.get(sf.id + '-' + record.id + '-closebtn');
				closeBtn[ou](EVT_CLICK, sf.onRowClose, sf);
			}
			/*
			 * 徐昭  2017/11/08 19:16
			 * floatList行增加双击事件
			 */
			Ext.get(rowElement)[ou](EVT_DBLCLICK, sf.onRowDblClick, sf);
			Ext.get(rowElement)[ou](EVT_CLICK, sf.onRowClick, sf);
		},
		processAllRowListener: function (ou) {
			var sf = this;
			Ext.each(sf.fll.query('div.item-float-list-row'), function (rowElement) {
				sf.processRowListener(ou, rowElement, sf.ds.findById(rowElement.getAttribute(ATT_RECORD_ID)));
			});
		},
		processDataSetListener: function (ou) {
			var sf = this;
			if (sf.ds) {
				sf.ds[ou]('load', sf.onLoad, sf);
				sf.ds[ou]('update', sf.onLoad, sf);
				sf.ds[ou]('add', sf.onLoad, sf);
				sf.ds[ou]('remove', sf.onRemove, sf);
				sf.ds[ou]('query',sf.doMask, sf);
				sf.ds[ou]('loadfailed', sf.doUnMask, sf);
				/**
				 * author:徐昭
				 * time:2017/8/14
				 * purpose:增加floatList的"select"与"unselect"事件的监听
				 */
				sf.ds[ou]('select', sf.onFloatListSelect, sf);
				sf.ds[ou]('unselect', sf.onFloatListUnSelect, sf);
				sf.ds[ou]('indexchange', sf.onIndexChange, sf);
			}
		},
		initEvents: function () {
			$A.FloatList.superclass.initEvents.call(this);
			this.addEvents(
					/**
					 * @event beforecloserow
					 * 行上的关闭按钮点击时触发该事件
					 * @param {Aurora.FloatList} floatList 当前FloatList组件.
					 * @param {Aurora.Record} record 点击关闭按钮所在行的Record对象.
					 * @param {Dom} rowElement 当前行的dom元素
					 * @return {Boolean} result 是否可以关闭
					 */
					EVT_BEFORE_CLOSE_ROW,
					/**
					 * @event closerow
					 * 行上的关闭按钮点击后触发该事件
					 * @param {Aurora.FloatList} floatList 当前FloatList组件.
					 * @param {Aurora.Record} record 点击关闭按钮所在行的Record对象.
					 * @param {Dom} rowElement 当前行的dom元素
					 */
					EVT_CLOSE_ROW,
					/**
					 * @event beforeloadmoredata
					 * 加载更多之前触发的事件
					 * @param {Aurora.FloatList} floatList 当前FloatList组件.
					 * @param {Aurora.DataSet} dataSet 当前FloatList所绑定的DataSet
					 * @return {Boolean} result 是否可以加载更多数据
					 */
					EVT_BEFORE_LOAD_MORE_DATA,
					/**
					 * @event loadmoredata
					 * 加载更多触发的事件
					 * @param {Aurora.FloatList} floatList 当前FloatList组件.
					 * @param {Aurora.DataSet} dataSet 当前FloatList所绑定的DataSet
					 */
					EVT_LOAD_MORE_DATA,
					/**
					 * @event rowclick
					 * 加载更多触发的事件
					 * @param {Aurora.HList} HList 当前HList组件.
					 * @param {Aurora.Record} record 点击关闭按钮所在行的Record对象.
					 * @param {Dom} rowElement 当前行的dom元素
					 */
					EVT_ROW_CLICK,
					/**
					 * @event onload
					 * 加载更多触发的事件
					 * @param {Aurora.HList} HList 当前HList组件.
					 * @param {Aurora.DataSet} dataSet 当前FloatList所绑定的DataSet
					 */
					EVT_LOAD
			);
		},
		initTemplate: function () {
			var sf = this;
			sf.rowTplt = new Ext.Template([
				'<div id="{rowId}" ' + ATT_RECORD_ID + '="{_rowId}" class="' + rowClass + ' ' + cellColClass + '{columnNumber} {clickableClass}" style="{rowStyle}">',
				'<table>',
				'<tr>',
				'{cellStr}',
				'</tr>',
				'</table>',
				'</div>']);
			sf.cellTplt = new Ext.Template(['<td class="' + cellClass + '" width="{cellWidth}" cellpadding="0">',
				'{cellContent}',
				'</td>']);
			sf.closeBtnTplt = new Ext.Template([
				'<div class="item-float-list-close" id="{closeBtnId}"/>'
				]);
			/**
			 * author:徐昭
			 * time:2017/8/14
			 * purpose:增加floatList的复选框模板
			 */
			sf.checkTplt = new Ext.Template([
				'<td width="{cellWidth}" atype="floatList.rowcheck" class="floatList-rowbox " recordid="{rowId}">',
				'<center>',
				'<div class="floatList-ckb floatList-ckb{readonly}{ck}" id="rowcheck-{rowId}">',
				'</div>',
				'</center>',
				'</td>']);
			/**
			 * author:徐昭
			 * time:2017/8/14
			 * purpose:增加floatList的单选框模板
			 */
			sf.radioTplt = new Ext.Template([
				'<td width="{cellWidth}" atype="floatList.rowradio" class="floatList-rowbox " recordid="{rowId}">',
				'<center>',
				'<div class="floatList-radio floatList-radio-img{readonly}{ck}" id="rowradio-{rowId}">',
				'</div>',
				'</center>',
				'</td>']);
		},
		onLoad: function () {
			var sf = this,
				records = sf.ds.getAll(),
				cr = sf.ds.getCurrentRecord();
			sf.rowArr = [];
			sf.selectedRow = NULL;
			Ext.each(records, function (record) {
				sf.cellArr = [];
				Ext.each(sf.columns, function (column) {
					var value = record.get(column.name);
					var cellContent;
					var type = column.type;
					if (column.renderer) {
						cellContent = sf.renderText(record, column, value);
					} else {
						cellContent = $A.escapeHtml(value);
					}
					/**
					 * author:徐昭
					 * time:2017/8/14
					 * purpose:根据dataset的属性生成rowcheck或者rowradio
					 */
					if(type == 'rowcheck' || type == 'rowradio'){
						var ds = sf.ds,
						ck = ds.getSelected().indexOf(record) == -1?'-u':'-c',
								readonly = ds.execSelectFunction(record)?'':'-readonly';

						if(type == 'rowcheck'){
							sf.cellArr.push(sf.checkTplt.applyTemplate({
								cellWidth:column.width,
								rowId:record.id,
								readonly:readonly,
								ck:ck
							}));
						}else if(type == 'rowradio'){
							sf.cellArr.push(sf.radioTplt.applyTemplate({
								cellWidth:column.width,
								rowId:record.id,
								readonly:readonly,
								ck:ck
							}));
						}
					}else{
						sf.cellArr.push(sf.cellTplt.applyTemplate({
							cellWidth: column.width,
							cellContent: cellContent
						}));
					}
				});

				if(sf.closeable){
					sf.cellArr.push(sf.closeBtnTplt.applyTemplate({
						closeBtnId: sf.id + '-' + record.id + '-closebtn'
					}));
				}

				sf.rowArr.push(sf.rowTplt.applyTemplate({
					rowId: sf.id + '-' + record.id,
					_rowId: record.id,
					columnNumber: sf.columnNumber,
					clickableClass: sf.clickable ? clickableClass : '',
							rowStyle: sf.rowStyle,
							cellStr: (new Ext.Template(sf.cellArr)).applyTemplate()
				}));
			});

			sf.fll.update(new Ext.Template(sf.rowArr).applyTemplate());
			sf.processAllRowListener('on');
			cr && sf.onIndexChange(sf.ds,cr);
			sf.fireEvent(EVT_LOAD, sf, sf.ds);
			sf.doUnMask();
		},
		getDataIndex : function(rid){
//			debugger;
	        for(var i=0,data = this.ds.data,l=data.length;i<l;i++){
	            if(data[i].id == rid){
	                return i;
	            }
	        }
	        return -1;
	    },
	    focusRow : function(rowId, row){
	    	var sf = this,
	    		r = 87,
	    		stop = sf.fll.getScroll().top,
	    		h = sf.fll.getHeight();
	    	sh = sf.fll.dom.scrollWidth > sf.fll.dom.clientWidth? 16 : 0;
//	    	sh = 0;
	    	if(row*r<stop){
	    		if(sf.fll['scrollToQueue']){
	    			sf.fll['scrollToQueue'].push(row*r-1);
        		}else{
        			sf.fll['scrollToQueue'] = [];
        			sf.fll['scrollToQueue'].push(row*r-1);
        		}
	    	}else if((row+1)*r>(stop+h-sh)){//this.ub.dom.scrollHeight
        		if(sf.fll['scrollToQueue']){
        			sf.fll['scrollToQueue'].push((row+1)*r-h + sh);
        		}else{
        			sf.fll['scrollToQueue'] = [];
        			sf.fll['scrollToQueue'].push((row+1)*r-h + sh);
        		}
	        }
	    	if(!sf.fll['srollTask']){
	    		sf.fll['scrollTaskInfo'] = {
            		run:function(){
            			if(sf.fll['scrollToQueue']){
                			var scrollDrift = sf.fll['scrollToQueue'].pop();
                			if(scrollDrift){
                				sf.fll.scrollTo(TOP,scrollDrift);
                			}
                			sf.fll['scrollToQueue'] = [];
            			}else{
            				sf.fll['scrollToQueue'] = [];
            			}
            		},
            		interval:200,
            		scope:this
            	};
            	
	    		sf.fll['srollTask'] = Ext.TaskMgr.start(sf.fll['scrollTaskInfo']);
            }
	    },
		onIndexChange : function(ds,r){
			var index = this.getDataIndex(r.id);
	        if(index == -1)return;
	        this.selectRow(index, FALSE);
		},
		selectRow : function(row,locate){
			var sf = this,
            ds = sf.ds,record = ds.getAt(row),
            r = (ds.currentPage-1)*ds.pagesize + row+1;
			if(sf.selectedId && sf.selectedId === record.id) return;
	        sf.selectedId = record.id;
	        var rowId = sf.id + '-' + sf.selectedId;
	        if(sf.selectedRow) sf.selectedRow.removeClass(FLOATLIST_ROW_SELECT);
	        /*
	         * 徐昭 2017/11/21
	         * 当wrap.child('div[id=' + rowId +']')不存在时会报错
	         * 加上if(wrap.child('div[id=' + rowId +']'))判断
	         */
	        if(wrap.child('div[id=' + rowId +']')){
	        	wrap.child('div[id=' + rowId +']').addClass(FLOATLIST_ROW_SELECT);
	        	sf.selectedRow = wrap.child('div[id=' + rowId +']');
	        }
	        sf.focusRow(rowId, row);
	        if(locate!==FALSE && r != NULL) {
	            ds.locate.defer(5, ds,[r,FALSE]);
	        }
	        //徐昭 2017/12/07 21:25
	        //floatList新增选中后事件
	        sf.fireEvent("afterrowselect",sf,ds);
		},
		onRemove: function (ds, record, index) {
			var sf = this;
			var rowElement = Ext.get(sf.id + '-' + record.id);
			sf.destroyRow(rowElement, record);
			sf.closeRow(rowElement, record);
		},
		onRowClose: function (e) {
			//点击行关闭，实际执行的是DataSet的removeLocal操作
			var sf = this;
			var rowElement = Ext.get(e.target.id.replace('-closebtn', ''));
			var record = sf.ds.findById(rowElement.getAttribute(ATT_RECORD_ID));
			if (sf.fireEvent(EVT_BEFORE_CLOSE_ROW, sf, record, rowElement)) {
				sf.ds.removeLocal(record);
			}
		},
		closeRow: function (rowElement, record) {
			var sf = this;
			rowElement.dom.innerHTML = '';
			rowElement.setStyle('min-height', '0px');
			rowElement.setStyle('height', '0px');
			rowElement.setStyle('border', 'none');
			rowElement.setStyle('margin-top', '0px');

			var delayTask = new Ext.util.DelayedTask(function () {
				var rowElement = this;
				rowElement.remove();
			}, rowElement);

			delayTask.delay(500);
		},
		destroyRow: function (rowElement, record) {
			var sf = this;
			sf.processRowListener('un', rowElement, record);
		},
		renderText: function (record, col, value) {
			var sf = this;
			var renderer = col.renderer,
			value = $A.escapeHtml(value);
			if (renderer) {
				var rder = $A.getRenderer(renderer);
				if (rder == null) {
					alert(NOT_FOUND + renderer + METHOD)
					return value;
				}
				value = rder(value, record, col.name, sf);
			}
			return value == null ? '' : value;
		},
		onLoadMoreData: function () {
			var sf = this;
			if (sf.ds) {
				if (sf.fireEvent(EVT_BEFORE_LOAD_MORE_DATA, sf, sf.ds)) {
					if (sf.ds.totalPage == sf.ds.currentPage) {
						$A.SideBar.enable = true;
						$A.SideBar.show({
							msg: '没有更多数据'
						});
					} else {
						sf.ds.loadNextPage();
						sf.fireEvent(EVT_LOAD_MORE_DATA, sf, sf.ds);
					}
				}
			}
		},
		isFunctionCol: function(t){
	        return t == ROW_CHECK || t == ROW_RADIO || t == ROW_NUMBER
	    },
	    _export : function(type,filename,separator,exportParam){
	    	this.exportOptions = Ext.apply ({
	    		type:'xls',
	    		filename:'excel'
	    	},arguments.length==1?type:{
				type:type||'xls',
	            filename:filename||'excel',
	            separator:separator,
	            param:exportParam
	    	});
	        this.showExportConfirm();
	    },
	    showExportConfirm :function(){
	        var sf = this,n=0,id = sf.id + '_export',
	            msg = ['<div class="item-export-wrap" style="margin:10px;width:270px" id="',id,'">',
	                    '<div class="grid-uh" atype="grid.uh" style="width: 270px; -moz-user-select: none; text-align: left; height: 25px; cursor: default;" onselectstart="return false;" unselectable="on">',
	                    '<table cellSpacing="0" cellPadding="0" border="0"><tbody><tr height="25px">',
	                    '<td class="export-hc" style="width:22px;" atype="export.rowcheck"><center><div title="',_lang['grid.export.selectinfo'],'" atype="export.headcheck" class="',GRID_CKB,ITEM_CKB_U,'"></div></center></td>',
	                    '<td class="export-hc" style="width:222px;" atype="grid-type">',_lang['grid.export.column'],'</td>',
	                    '</tr></tbody></table></div>',
	                    '<div style="overflow:auto;height:200px;"><table cellSpacing="0" cellPadding="0" border="0"><tbody>'],
	                    exportall = TRUE,height=370,
	                    exportOptions = sf.exportOptions||(sf.exportOptions={}),
	                    type = exportOptions && exportOptions.type;
	            EACH(sf.exportColumns,function(c,i){
	                if(!sf.isFunctionCol(c.type)){
	                    if(exportall)exportall = c.forexport !==FALSE;
	                    msg.push('<tr',(n+i)%2==0?_N:' class="',ROW_ALT,'"',
	                    '><td class="',GRID_ROWBOX,'" style="width:22px;" ',
	                    RECORD_ID,'="',i,'" atype="export.rowcheck"><center><div id="',
	                    sf.id,__,i,'" class="',GRID_CKB,c.forexport === FALSE?ITEM_CKB_U:ITEM_CKB_C,
	                    '"></div></center></td><td style="width:222px"><div class="',GRID_CELL,'">',
	                    c.prompt,c.hidden?['<div style="float:right;color:red">&lt;',_lang['grid.export.hidecolumn'],'&gt;</div>'].join(''):_N,'</div></td></tr>');    
	                }else n++;
	            });
	            if(exportall)msg[9]=ITEM_CKB_C;
	            msg.push('</tbody></table></div></div>');
	            if(type == 'xls' || type== 'xlsx'){
	            	height+=30;
	            	msg.push('<div class="item-radio" class="item-radio" style="margin:15px;width:270px;height:30px">',
	            				'<div class="item-radio-option" style="width:128px;float:left" itemvalue="xls">',
	            					'<div class="item-radio-img  item-radio-img-',type=='xls'?'c':'u','"></div>',
	            					'<label class="item-radio-lb">excel2003</label>',
	            				'</div>',
	            				'<div class="item-radio-option" style="width:128px;float:left" itemvalue="xlsx">',
	            					'<div class="item-radio-img  item-radio-img-',type=='xlsx'?'c':'u','"></div>',
	            					'<label class="item-radio-lb">excel2007</label>',
	            				'</div>',
	        				'</div>')
	            }
	            msg.push('<div style="margin:15px;width:270px;color:red">',_lang['grid.export.confirmMsg'],'</div>');
	        sf.exportwindow = $A.showOkCancelWindow(_lang['grid.export.config'],msg.join(_N),function(win2){
	            //A.showConfirm(_lang['grid.export.confirm'],_lang['grid.export.confirmMsg'],function(win){
	                sf.doExport();
	                //win.close();
	                win2.body.un(EVT_CLICK,sf.onExportClick,sf);
	                //win2.close();
	            //});
	            //return FALSE;
	        },NULL,380,height);
	        sf.exportwindow.body.on(EVT_CLICK,sf.onExportClick,sf);
	    },
	    onExportClick : function(e,t){
//	    	debugger;
	    	t = Ext.fly(t);
	        var sf = this,rowbox =t.parent('td.grid-rowbox')||t.parent('td.export-hc'),
	        	radio = t.hasClass('item-radio-option')?t:t.parent('div.item-radio-option');
	        if(rowbox){
	            var atype = rowbox.getAttributeNS(_N,ATYPE);
	            if(atype=='export.rowcheck'){               
	                var rid =rowbox.getAttributeNS(_N,RECORD_ID),
	                    cb = rowbox.child(DIV),
	                    checked = cb.hasClass(ITEM_CKB_C),
	                    _atype = cb.getAttributeNS(_N,ATYPE),
	                    exportCols = sf.exportColumns;
	                sf.setCheckBoxStatus(cb, !checked);
	                if(_atype=='export.headcheck'){
	                    var che = (sf.isFunctionCol(exportCols[0].type) ? 1 : 0)
	                        + (sf.isFunctionCol(exportCols[1].type) ? 1 : 0),
	                        ctrl = e.ctrlKey;
	                    sf.exportwindow.body.select('td[atype=export.rowcheck] div[atype!=export.headcheck]')
	                        .each(function(cbs,o,i){
	                        	o = exportCols[i+che];
	                        	if(!ctrl ||!o.hidden){
		                            sf.setCheckBoxStatus(cbs, !checked);
		                            o.forexport = !checked;
	                        	}
	                        });
	                }else
	                	exportCols[rid].forexport = !checked;
	            }
	        }else if(radio){
	        	sf.setRadioStatus(radio.child('div'),TRUE);
	        	sf.setRadioStatus((radio.prev()||radio.next()).child('div'),FALSE);
	        	sf.exportOptions.type = radio.getAttributeNS(_N,'itemvalue')
	        }
	    },
	    doExport : function(){
//	    	debugger;
	        var sf = this,opt = sf.exportOptions||{};
	        $A.doExport(sf.ds,sf.exportColumns,NULL,opt.type,opt.separator,opt.filename,opt.param);
	        delete sf.exportOptions;
	    },
		/**
		 * name:徐昭
		 * time:2017/08/14
		 * purpose:设置Radio状态
		 */
		setRadioStatus : function (el, checked){
			if(!checked){
				el.removeClass('floatList-radio-img-c').addClass('floatList-radio-img-u');
			}else{
				el.addClass('floatList-radio-img-c').removeClass('floatList-radio-img-u');
			}
		},
		/**
		 * name:徐昭
		 * time:2017/08/14
		 * purpose:设置checkBox状态
		 */
		setCheckBoxStatus : function(el, checked){
			if(!checked){
	            el.removeClass('floatList-ckb-c').addClass('floatList-ckb-u');
	        }else{
	            el.addClass('floatList-ckb-c').removeClass('floatList-ckb-u');
	        }
		},
		/**
		 * name:徐昭
		 * time:2017/08/14
		 * purpose:选框不可用
		 */
		setSelectDisable : function(el,record){
			var sf = this,
				flag = sf.ds.selected.indexOf(record) == -1;
			if(sf.selectionmodel == 'multiple'){
				el.removeClass(['floatList-ckb-c','floatList-ckb-u'])
				.addClass(flag?'floatList-ckb-readonly-u':'floatList-ckb-readonly-c');
			}else{
				el.removeClass(['floatList-radio-img-c','floatList-radio-img-u','floatList-radio-img-readonly-c','floatList-radio-img-readonly-u'])
                .addClass(flag?'floatList-radio-img-readonly-u':'floatList-radio-img-readonly-c');
			}
		},
		/**
		 * name:徐昭
		 * time:2017/08/14
		 * purpose:选框可用
		 */
		setSelectEnable : function(el,record){
			var sf = this,
				flag = sf.ds.selected.indexOf(record) == -1;
			if(sf.selectionmodel == 'multiple'){
				el.removeClass(['floatList-ckb-readonly-u','floatList-ckb-readonly-c'])
				.addClass(flag?'floatList-ckb--u':'floatList-ckb-c');
			}else{
				el.removeClass(['floatList-radio-img-u','floatList-radio-img-c','floatList-radio-img-readonly-u','floatList-radio-img-readonly-c'])
                .addClass(flag?'floatList-radio-img-u':'floatList-radio-img-c');
			}
		},
		/**
		 * name:徐昭
		 * time:2017/08/14
		 * purpose:设置选框状态
		 */
		setSelectStatus : function(record){
			var sf = this,
			ds = sf.ds;
			if(ds.selectfunction){
				var cb;
				if(Ext.get('rowcheck-'+record.id)){
					cb = Ext.get('rowcheck-'+record.id);
				}else if(Ext.get('rowradio-'+record.id)){
					cb = Ext.get('rowradio-'+record.id);
				}
				if(cb){
					if(!ds.execSelectFunction(record)){
						sf.setSelectDisable(cb,record)
					}else{
						sf.setSelectEnable(cb,record);
					}
				}
			}
		},
		/**
		 * name:徐昭
		 * time:2017/08/14
		 * purpose:FloatList选中事件
		 */
		onFloatListSelect : function(ds,record){
			if(!record) return;
			var sf = this, cb;
			if(Ext.get('rowcheck-'+record.id)){
				cb = Ext.get('rowcheck-'+record.id);
			}else if(Ext.get('rowradio-'+record.id)){
				cb = Ext.get('rowradio-'+record.id);
			}
			if(cb){
				cb.parent('.item-float-list-row').addClass('item-list-row-selected');
				cb.parent('.floatList-rowbox').addClass('floatList-ckb-self');
				if(sf.selectionmodel == 'multiple'){
					sf.setCheckBoxStatus(cb, true);
				}else{
					sf.setRadioStatus(cb, true);
					ds.locate((ds.currentPage-1)*ds.pagesize + ds.indexOf(record) + 1);
				}
				sf.setSelectStatus(record);
			}
		},
		/**
		 * name:徐昭
		 * time:2017/08/14
		 * purpose:FloatList取消选中事件
		 */
		onFloatListUnSelect : function(ds, record){
			if(!record) return;
			var sf = this,cb;
			if(Ext.get('rowcheck-'+record.id)){
				cb = Ext.get('rowcheck-'+record.id);
			}else if(Ext.get('rowradio-'+record.id)){
				cb = Ext.get('rowradio-'+record.id);
			}
			if(cb){
				cb.parent('.item-float-list-row').removeClass('item-list-row-selected');
				cb.parent('.floatList-rowbox').addClass('floatList-ckb-self');
				if(sf.selectionmodel == 'multiple'){
					sf.setCheckBoxStatus(cb, false);
				}else{
					sf.setRadioStatus(cb, false);
				}
				sf.setSelectStatus(record);
			}
		},
		onRowDblClick: function (e){
//			sf.timer && clearTimeout(sf.timer);
			if (rowElement = Ext.get(e.target).parent('div.item-float-list-row')){
				sf.fireEvent(EVT_DBLCLICK, sf, rowElement, sf.ds.findById(rowElement.getAttribute('_row')));
			}
		},
		onMouseWheel : function(e){
			var sf = this,
				delta = e.getWheelDelta();
			if(!sf.ds.getAll().length) return;
			 e.stopEvent();
		        if(delta > 0){
		            sf.ds.pre();
		        } else if(delta < 0){
		            sf.ds.next();
		        }
		},
		onRowClick: function (e) {
			var sf = this;
//			if(rowElement = (Ext.get(e.target).parent('td[atype=floatList.rowcheck]') || Ext.get(e.target).parent('td[atype=floatList.rowradio]'))){
			/*
			 * 徐昭 2017/11/09 11:01
			 * 勾选事件的触发范围扩展至整个td
			 */
			if((rowElement = (Ext.get(e.target).parent('td[atype=floatList.rowcheck]') || Ext.get(e.target).parent('td[atype=floatList.rowradio]'))) || Ext.get(e.target).child('div.floatList-ckb') || Ext.get(e.target).child('div.floatList-radio')){
				if(!rowElement){
					rowElement = Ext.get(e.target);
				}	
				var atype = rowElement.getAttribute('atype'),
				rid = rowElement.getAttribute('recordid'),
				ds = sf.ds;
				if(atype == 'floatList.rowcheck'){
					var cb = Ext.get('rowcheck-'+rid);
					if(cb.hasClass('floatList-ckb-readonly-u')||cb.hasClass('floatList-ckb-readonly-c')) return;
					cb.hasClass('floatList-ckb-c') ? ds.unSelect(rid) : ds.select(rid);
				}else if(atype == 'floatList.rowradio'){
					var cb = Ext.get('rowradio-'+rid);
					if(cb.hasClass('floatList-radio-img-readonly-u')||cb.hasClass('floatList-radio-img-readonly-c')) return;
					ds.select(rid);
				}
			}
			/**
			 * name:徐昭
			 * time:2017/08/14
			 * purpose:当点击复选框或者单选框时触发相关事件
			 */
			if (rowElement = Ext.get(e.target).parent('div.item-float-list-row')) {
//				sf.timer && clearTimeout(sf.timer);
//				sf.timer = setTimeout(function(){
					/*
					 * 徐昭 2017/11/14 18:23
					 * floatList单击定位当前行
					 */
					var rid = rowElement.getAttribute('_row'),
						row = 0,
						records = sf.ds.getAll();
					if(rid == sf.selectedId) return;
					for(var i = 0; i < records.length; i++){
						if(records[i].id == rid){
							row = i;
							break;
						}
					}
					sf.selectRow(row);
//					sf.fireEvent(EVT_ROW_CLICK, sf, rowElement, sf.ds.findById(rowElement.getAttribute('_row')));
//				},300);
			}
		},
		doMask : function(){
			var sf = this;
			Aurora.Masker.mask(sf.wrap);
		},
		doUnMask : function(){
			var sf = this;
			Aurora.Masker.unmask(sf.wrap);
		},
		/*
		 * 徐昭 2017/11/14 13:40
		 * 增加setHeight方法，修复floatList在窗口重绘时出现的高度自适应问题
		 */
		setHeight : function(h){
			var sf = this;
			if(h === sf.fll.dom.style.maxHeight) return;
			sf.fll.setStyle("max-height", h+"px");
		}
	});
})($A);
