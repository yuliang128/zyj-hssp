(function ($A) {
    var rowClass = 'item-hlist-row',
        cellClass = 'item-list-cell',
        hoverInfoClass = 'item-hlist-row-hover',
        clickableClass = 'item-list-row-clickable',
        NOT_FOUND = '未找到',
        METHOD = '方法!',
        ATT_RECORD_ID = '_row',
        EVT_CLICK = 'click',
        EVT_BEFORE_CLOSE_ROW = 'beforecloserow',
        EVT_CLOSE_ROW = 'closerow',
        EVT_BEFORE_LOAD_MORE_DATA = 'beforeloadmoredata',
        EVT_LOAD_MORE_DATA = 'loadmoredata',
        EVT_ROW_CLICK = 'rowclick'
    ;

    $A.HList = Ext.extend($A.Component, {
        constructor: function (config) {
            var sf = this;
            sf.bindTarget = config.bindtarget;
            sf.ds = $au(sf.bindTarget);
            sf.columns = config.columns;
            sf.columnNumber = config.columnnumber;
            sf.rowStyle = config.rowstyle;
            sf.closeable = config.closeable;
            sf.rowWidth = config.rowwidth;
            sf.rowArr = [];
            sf.cellArr = [];
            sf.bodyWidth = 0;
            sf.events = config.events;
            sf.clickable = config.clickable;
            $A.HList.superclass.constructor.call(sf, config);
        },
        initComponent: function (config) {
            $A.HList.superclass.initComponent.call(this, config);
            sf = this;
            wrap = sf.wrap = Ext.get(sf.id);
            sf.hlh = wrap.child('thead.item-list-head');
            sf.hlc = wrap.child('tbody.item-list-content');
            sf.hlcl = wrap.child('td.item-list-content-left');
            sf.hlcc = wrap.child('td.item-list-content-center');
            sf.hl = wrap.child('div.item-hlist');
            sf.hlb = wrap.child('div.item-hlist-body');
            sf.hlcr = wrap.child('td.item-list-content-right');
            sf.hlf = wrap.child('tfoot.item-list-foot');
            sf.leftBtn = wrap.child('div.item-hlist-left-button');
            sf.rightBtn = wrap.child('div.item-hlist-right-button');
            sf.initTemplate();
            sf.processDataSetListener('on');
            sf.onLoad();
        },
        processListener: function (ou) {
            var sf = this;
            $A.HList.superclass.processListener.call(sf, ou);
            sf.leftBtn[ou](EVT_CLICK, sf.onMoveToLeft, sf);
            sf.rightBtn[ou](EVT_CLICK, sf.onMoveToRight, sf);
        },
        processRowListener: function (ou, rowElement, record) {
            var sf = this;
            if (sf.closeable) {
                var closeBtn = Ext.get(sf.id + '-' + record.id + '-closebtn');
                closeBtn[ou](EVT_CLICK, sf.onRowClose, sf);
            }

            Ext.get(rowElement)[ou](EVT_CLICK, sf.onRowClick, sf);

        },
        processAllRowListener: function (ou) {
            var sf = this;
            Ext.each(sf.hlb.query('div.item-hlist-row'), function (rowElement) {
                sf.processRowListener(ou, rowElement, sf.ds.findById(rowElement.getAttribute(ATT_RECORD_ID)));
            });
        },
        processDataSetListener: function (ou) {
            if (sf.ds) {
                sf.ds[ou]('load', sf.onLoad, sf);
                sf.ds[ou]('update', sf.onLoad, sf);
                sf.ds[ou]('add', sf.onLoad, sf);
                sf.ds[ou]('remove', sf.onRemove, sf);
            }
        },
        initEvents: function () {
            $A.HList.superclass.initEvents.call(this);
            this.addEvents(
                /**
                 * @event beforecloserow
                 * 行上的关闭按钮点击时触发该事件
                 * @param {Aurora.HList} HList 当前HList组件.
                 * @param {Aurora.Record} record 点击关闭按钮所在行的Record对象.
                 * @param {Dom} rowElement 当前行的dom元素
                 * @return {Boolean} result 是否可以关闭
                 */
                EVT_BEFORE_CLOSE_ROW,
                /**
                 * @event closerow
                 * 行上的关闭按钮点击后触发该事件
                 * @param {Aurora.HList} HList 当前HList组件.
                 * @param {Aurora.Record} record 点击关闭按钮所在行的Record对象.
                 * @param {Dom} rowElement 当前行的dom元素
                 */
                EVT_CLOSE_ROW,
                /**
                 * @event beforeloadmoredata
                 * 加载更多之前触发的事件
                 * @param {Aurora.HList} HList 当前HList组件.
                 * @param {Aurora.DataSet} dataSet 当前HList所绑定的DataSet
                 * @return {Boolean} result 是否可以加载更多数据
                 */
                EVT_BEFORE_LOAD_MORE_DATA,
                /**
                 * @event loadmoredata
                 * 加载更多触发的事件
                 * @param {Aurora.HList} HList 当前HList组件.
                 * @param {Aurora.DataSet} dataSet 当前HList所绑定的DataSet
                 */
                EVT_LOAD_MORE_DATA,
                /**
                 * @event loadmoredata
                 * 加载更多触发的事件
                 * @param {Aurora.HList} HList 当前HList组件.
                 * @param {Aurora.Record} record 点击关闭按钮所在行的Record对象.
                 * @param {Dom} rowElement 当前行的dom元素
                 */
                EVT_ROW_CLICK
            );
        },
        initTemplate: function () {
            var sf = this;
            sf.rowTplt = new Ext.Template([
                '<div id="{rowId}" ' + ATT_RECORD_ID + '="{_rowId}" class="' + rowClass + '{columnNumber} {clickableClass}" style="{rowStyle}">',
                '<table>',
                '<tr>',
                '{cellStr}',
                '</tr>',
                '</table>',
                '{hoverInfoStr}',
                '</div>']);
            sf.cellTplt = new Ext.Template(['<td class="' + cellClass + '" width="{cellWidth}" cellpadding="0">',
                '{cellContent}',
                '</td>']);

            sf.closeBtnTplt = new Ext.Template([
                '<div class="item-hlist-close" id="{closeBtnId}"/>'
            ]);

            sf.hoverInfoTplt = new Ext.Template([
                '<div class="' + hoverInfoClass + '">',
                '{cellContent}',
                '</div>'
            ]);
        },
        onLoad: function () {
            var sf = this;
            var records = sf.ds.getAll();
            sf.bodyWidth = records.length * sf.rowWidth;
            sf.rowArr = [];
            Ext.each(records, function (record) {
                sf.cellArr = [];
                var hoverInfoStr = '';
                Ext.each(sf.columns, function (column) {
                    var value = record.get(column.name);
                    var cellContent;
                    if (column.renderer) {
                        cellContent = sf.renderText(record, column, value);
                    } else {
                        cellContent = $A.escapeHtml(value);
                    }
                    if (column.type && column.type === 'hoverinfo') {
                        hoverInfoStr = sf.hoverInfoTplt.applyTemplate({
                            cellContent: cellContent
                        });
                    } else {
                        sf.cellArr.push(sf.cellTplt.applyTemplate({
                            cellWidth: column.width,
                            cellContent: cellContent
                        }));
                    }
                });

                if (sf.closeable) {
                    sf.cellArr.push(sf.closeBtnTplt.applyTemplate({
                        closeBtnId: sf.id + '-' + record.id + '-closebtn'
                    }));
                }

                sf.rowArr.push(sf.rowTplt.applyTemplate({
                    rowId: sf.id + '-' + record.id,
                    _rowId: record.id,
                    columnNumber: sf.columnNumber,
                    clickableClass : sf.clickable ? clickableClass : '',
                    rowStyle: sf.rowStyle + ";width:" + sf.rowWidth + "px;",
                    cellStr: (new Ext.Template(sf.cellArr)).applyTemplate(),
                    hoverInfoStr: hoverInfoStr
                }));
            });
            if(sf.bodyWidth > 0){
                sf.hlb.setWidth(sf.bodyWidth + "px");
            }
            sf.hlb.update(new Ext.Template(sf.rowArr).applyTemplate());
            sf.processAllRowListener('on');
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
            var renderer = col.renderer,
                value = $A.escapeHtml(value);
            if (renderer) {
                var rder = $A.getRenderer(renderer);
                if (rder == null) {
                    alert(NOT_FOUND + renderer + METHOD)
                    return value;
                }
                value = rder(value, record, col.name);
            }
            return value == null ? '' : value;
        },
        onMoveToLeft: function () {
            var sf = this;
            var left = sf.hl.getScroll()['left'];
            sf.hl.scrollTo('left', left - 0.5 * sf.hlc.getWidth());
        },
        onMoveToRight: function () {
            var sf = this;
            var left = sf.hl.getScroll()['left'];
            sf.hl.scrollTo('left', left + 0.5 * sf.hlc.getWidth());
        },
        onRowClick: function (e) {
            var sf = this;
            if (rowElement = Ext.get(e.target).parent('div.item-hlist-row')) {
                sf.fireEvent(EVT_ROW_CLICK, sf, rowElement, sf.ds.findById(rowElement.getAttribute('_row')));
            }
        }
    });
})($A);
