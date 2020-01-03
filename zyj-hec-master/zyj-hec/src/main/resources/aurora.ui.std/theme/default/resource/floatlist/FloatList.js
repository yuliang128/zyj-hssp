(function ($A) {
	var rowClass = 'item-float-list-row',
	cellClass = 'item-list-cell',
	cellColClass = 'item-float-list-row-col-',
	clickableClass = 'item-list-row-clickable',
	NOT_FOUND = '未找到',
	METHOD = '方法!',
	ATT_RECORD_ID = '_row',
	EVT_CLICK = 'click',
	EVT_BEFORE_CLOSE_ROW = 'beforecloserow',
	EVT_CLOSE_ROW = 'closerow',
	EVT_BEFORE_LOAD_MORE_DATA = 'beforeloadmoredata',
	EVT_LOAD_MORE_DATA = 'loadmoredata',
	EVT_ROW_CLICK = 'rowclick',
	EVT_LOAD = 'load';
	$A.FloatList = Ext.extend($A.Component, {
		constructor: function (config) {
			var sf = this;
			sf.bindTarget = config.bindtarget;
			sf.ds = $au(sf.bindTarget);
			sf.columns = config.columns;
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
			sf.moreBtn = wrap.child('div.item-float-list-more-btn');
			sf.initTemplate();
			sf.processDataSetListener('on');
			sf.onLoad();
		},
		processListener: function (ou) {
			var sf = this;
			$A.FloatList.superclass.processListener.call(sf, ou);
			if(sf.loadMore){
				sf.moreBtn['on']('click', sf.onLoadMoreData, sf);
			}
		},
		processRowListener: function (ou, rowElement, record) {
			var sf = this;
			if(sf.closeable){
				var closeBtn = Ext.get(sf.id + '-' + record.id + '-closebtn');
				closeBtn[ou](EVT_CLICK, sf.onRowClose, sf);
			}
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
				'<div class="floatList-ckb item-ckb{readonly}{ck}" id="rowcheck-{rowId}">',
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
				'<div class="floatList-radio item-radio-img{readonly}{ck}" id="rowradio-{rowId}">',
				'</div>',
				'</center>',
				'</td>']);
		},
		onLoad: function () {
			var sf = this;
			var records = sf.ds.getAll();
			sf.rowArr = [];
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
			sf.fireEvent(EVT_LOAD, sf, sf.ds);
			sf.doUnMask();
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
		/**
		 * name:徐昭
		 * time:2017/08/14
		 * purpose:设置Radio状态
		 */
		setRadioStatus : function (el, checked){
			if(!checked){
				el.removeClass('item-radio-img-c').addClass('item-radio-img-u');
			}else{
				el.addClass('item-radio-img-c').removeClass('item-radio-img-u');
			}
		},
		/**
		 * name:徐昭
		 * time:2017/08/14
		 * purpose:设置checkBox状态
		 */
		setCheckBoxStatus : function(el, checked){
			if(!checked){
	            el.removeClass('item-ckb-c').addClass('item-ckb-u');
	        }else{
	            el.addClass('item-ckb-c').removeClass('item-ckb-u');
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
				el.removeClass(['item-ckb-c','item-ckb-u'])
				.addClass(flag?'item-ckb-readonly-u':'item-ckb-readonly-c');
			}else{
				el.removeClass(['item-radio-img-c','item-radio-img-u','item-radio-img-readonly-c','item-radio-img-readonly-u'])
                .addClass(flag?'item-radio-img-readonly-u':'item-radio-img-readonly-c');
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
				el.removeClass(['item-ckb-readonly-u','item-ckb-readonly-c'])
				.addClass(flag?'item-ckb--u':'item-ckb-c');
			}else{
				el.removeClass(['item-radio-img-u','item-radio-img-c','item-radio-img-readonly-u','item-radio-img-readonly-c'])
                .addClass(flag?'item-radio-img-u':'item-radio-img-c');
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
				cb.parent('.floatList-rowbox').addClass('item-ckb-self');
				if(sf.selectionmodel == 'multiple'){
					sf.setCheckBoxStatus(cb, true);
				}else{
					sf.setRadioStatus(cb, true);
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
				cb.parent('.floatList-rowbox').addClass('item-ckb-self');
				if(sf.selectionmodel == 'multiple'){
					sf.setCheckBoxStatus(cb, false);
				}else{
					sf.setRadioStatus(cb, false);
				}
				sf.setSelectStatus(record);
			}
		},
		onRowClick: function (e) {
			var sf = this;
			/**
			 * name:徐昭
			 * time:2017/08/14
			 * purpose:当点击复选框或者单选框时触发相关事件
			 */
			if(rowElement = (Ext.get(e.target).parent('td[atype=floatList.rowcheck]') || Ext.get(e.target).parent('td[atype=floatList.rowradio]'))){
				var atype = rowElement.getAttribute('atype'),
				rid = rowElement.getAttribute('recordid'),
				ds = sf.ds;
				if(atype == 'floatList.rowcheck'){
					var cb = Ext.get('rowcheck-'+rid);
					if(cb.hasClass('item-ckb-readonly-u')||cb.hasClass('item-ckb-readonly-c')) return;
					cb.hasClass('item-ckb-c') ? ds.unSelect(rid) : ds.select(rid);
				}else if(atype == 'floatList.rowradio'){
					var cb = Ext.get('rowradio-'+rid);
					if(cb.hasClass('item-radio-img-readonly-u')||cb.hasClass('item-radio-img-readonly-c')) return;
					ds.select(rid);
				}
			}else if (rowElement = Ext.get(e.target).parent('div.item-float-list-row')) {
				sf.fireEvent(EVT_ROW_CLICK, sf, rowElement, sf.ds.findById(rowElement.getAttribute('_row')));
			}
		},
		doMask : function(){
			var sf = this;
			Aurora.Masker.mask(sf.wrap);
		},
		doUnMask : function(){
			var sf = this;
			Aurora.Masker.unmask(sf.wrap);
		}
	});
})($A);
