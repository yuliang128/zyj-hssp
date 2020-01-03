importClass(Packages.uncertain.spring.core.SpringAppContextHolder)
importPackage(Packages.com.hand.hec.panda.bpm.dto)
importClass(Packages.aurora.application.action.ModelCopy)
importClass(Packages.java.util.HashMap)

var DynamicEngine = function () {
    var configObj = $config();
    var config = new CompositeMap(configObj);
    var viewObj = CompositeUtil.findChild(configObj, 'view');
    var view = new CompositeMap(viewObj);
    var viewType = $ctx.parameter.viewType;
    var approveRecord = $ctx.parameter.approveRecord;
    var viewRadioDsBmArray = [];
    var appContext = SpringAppContextHolder.getSpringAppContext();
    var jsEngine = appContext.getBean('jsEngineService');
    var codeService = appContext.getBean('codeServiceImpl');
    var requestCtx = $ctx.model.requestContext;
    var pageIdStr = $ctx.getObject('parameter').pageId;
    var bpmCache = appContext.getBean('bpmCache');
    var compositeLoader = $instance('uncertain.composite.CompositeLoader');

    if (approveRecord == 'Y') {
        viewType = 'VIEW';
    }
    var scriptLastObj = CompositeUtil.findChild(viewObj, 'script', 'positionType', 'last');
    var scriptLastNode = null;
    if (!scriptLastObj) {
        scriptLastNode = view.createChildNS('script');
        scriptLastNode.positionType = 'last';
    } else {
        scriptLastNode = new CompositeMap(scriptLastObj);
    }

    var scriptFirstObj = CompositeUtil.findChild(viewObj, 'script', 'positionType', 'first');
    var scriptFirstNode = null;
    if (!scriptFirstObj) {
        var viewChildList = viewObj.getChilds();
        scriptFirstNode = new CompositeMap('script');
        viewChildList.add(0, scriptFirstNode.getData());
        scriptFirstNode.positionType = 'first';
    } else {
        scriptFirstNode = new CompositeMap(scriptFirstObj);
    }

    // 输出友好的错误信息至页面
    var writeErrorMsg = function (errorMsg) {
        var node = CompositeUtil.findChild(configObj, 'view');
        node.getChildsNotNull().clear();
        div = node.createChild('div');
        div.put('style', 'font-size:16px;font-weight:bold;')
        div.setText(errorMsg);
    }

    // 处理布局组件的基础属性
    var dealLayoutBasic = function (layoutNode, layoutBasic) {
        // 设置宽度
        if (layoutBasic.getWidth()) {
            layoutNode.width = layoutBasic.getWidth();
        }
        // 设置高度
        if (layoutBasic.getHeight()) {
            layoutNode.height = layoutBasic.getHeight();
        }
        // 设置自适应宽度
        if (layoutBasic.getMarginwidth()) {
            layoutNode.marginwidth = layoutBasic.getMarginwidth();
        }
        // 设置自适应高度
        if (layoutBasic.getMarginheight()) {
            layoutNode.marginheight = layoutBasic.getMarginheight();
        }
        // 设置样式
        if (layoutBasic.getStyle()) {
            layoutNode.style = layoutBasic.getStyle();
        }
    }

    // 处理Form类型的布局组件特殊属性
    var dealLayoutForm = function (layoutNode, layoutBasic) {
        $ctx.getObject('parameter').layoutId = layoutBasic.getLayoutId();
        var formAttArr = jsEngine.queryPageLayoutForm(requestCtx, $ctx.getObject('parameter').layoutId);

        if (formAttArr.size() && (formAttArr.size() == 1)) {
            var formAtt = formAttArr.get(0);
            // 设置form的描述
            if (formAtt.getPrompt()) {
                layoutNode.prompt = formAtt.getPrompt();
            }
            // 设置form的title
            if (formAtt.getTitle()) {
                layoutNode.title = formAtt.getTitle();
            }
            // 设置form的列数
            if (formAtt.getColumnNum()) {
                layoutNode.column = formAtt.getColumnNum();
            }
            // 设置form的行数
            if (formAtt.getRowNum()) {
                layoutNode.row = formAtt.getRowNum();
            }
            // 设置form的标签宽度
            if (formAtt.getLabelwidth()) {
                layoutNode.labelwidth = formAtt.getLabelwidth();
            }
            // 设置form的标签分割符
            if (formAtt.getLabelseparator()) {
                layoutNode.labelseparator = formAtt.getLabelseparator();
            }
        } else {
            throw new java.lang.Exception('布局组件代码为:' + layoutBasic.getLayoutCode() + '的' + layoutBasic.getLayoutType()
                + '类型的布局组件Form属性记录不存在！');
        }
    }

    // 处理Grid类型的布局组件特殊属性
    var dealLayoutGrid = function (layoutNode, layoutBasic) {
        $ctx.getObject('parameter').layoutId = layoutBasic.getLayoutId();
        var gridAttArr = jsEngine.queryPageLayoutGrid(requestCtx, $ctx.getObject('parameter').layoutId);
        if (gridAttArr.size() && (gridAttArr.size() == 1)) {
            var gridAtt = gridAttArr.get(0);
            // 设置grid的是否有导航
            if (gridAtt.getNavbar()) {
                layoutNode.navbar = gridAtt.getNavbar();
            }

            // 设置Grid的tab的prompt
            if (layoutBasic.getLayoutDesc() && CompositeUtil.findParentWithName(layoutNode.getData(), 'tab')) {
                CompositeUtil.findParentWithName(layoutNode.getData(), 'tab').put('prompt', (layoutBasic.getLayoutDesc()));
            }
        } else {
            throw new java.lang.Exception('布局组件代码为:' + layoutBasic.getLayoutCode() + '的' + layoutBasic.getLayoutType()
                + '类型的布局组件Grid属性记录不存在！');
        }
    }

    // 处理Tab类型的布局组件特殊属性
    var dealLayoutTab = function (layoutNode, layoutBasic) {
        $ctx.getObject('parameter').layoutId = layoutBasic.getLayoutId();
        var tabAttArr = jsEngine.queryPageLayoutTab(requestCtx, $ctx.getObject('parameter').layoutId);
        if (tabAttArr && (tabAttArr.size() == 1)) {
            var tabAtt = tabAttArr[0];
            // 设置Tab的默认选中
            if (tabAtt.getSelected()) {
                layoutNode.selected = tabAtt.geSelected();
            }
            // 设置Tab的可关闭
            if (tabAtt.getCloseable()) {
                layoutNode.closeable = tabAtt.getCloseable();
            }
            // 设置Tab的是否可用
            if (tabAtt.getDisabled()) {
                layoutNode.disabled = tabAtt.getDisabled();
            }
            // 设置Tab的引用页面
            if (tabAtt.getRef()) {
                layoutNode.ref = tabAtt.getRef();
            }
            // 设置Tab的标签样式
            if (tabAtt.getTabstyle()) {
                layoutNode.tabstyle = tabAtt.getTabstyle();
            }
            // 设置Tab的内部样式
            if (tabAtt.getBodystyle()) {
                layoutNode.bodystyle = tabAtt.getBodystyle();
            }
        } else {
            throw new java.lang.Exception('布局组件代码为:' + layoutBasic.getLayoutCode() + '的' + layoutBasic.getLayoutType()
                + '类型的布局组件Tab属性记录不存在！');
        }
    }

    // 处理布局组件设置
    var dealLayout = function (layoutBasic) {
        if (layoutBasic.getId()) {
            // 布局组件属性设置
            var layoutNode = new CompositeMap(CompositeUtil.findChild($config(), layoutBasic.getLayoutType(), 'id',
                layoutBasic.getId()));
            if (layoutNode) {
                dealLayoutBasic(layoutNode, layoutBasic);
                if (layoutBasic.getLayoutType() == 'form' || layoutBasic.getLayoutType() == 'fieldSet'
                    || layoutBasic.getLayoutType() == 'box' || layoutBasic.getLayoutType() == 'vBox'
                    || layoutBasic.getLayoutType() == 'hBox') {
                    dealLayoutForm(layoutNode, layoutBasic);
                } else if (layoutBasic.getLayoutType() == 'grid' || layoutBasic.getLayoutType() == 'table') {
                    dealLayoutGrid(layoutNode, layoutBasic);
                } else if (layoutBasic.getLayoutType() == 'tab') {
                    dealLayoutTab(layoutNode, layoutBasic);
                } else {
                    throw new java.lang.Exception('布局组件代码为:' + layoutBasic.getLayoutCode() + '的' + layoutBasic.getLayoutType()
                        + '类型的布局组件类型不在处理范围当中！');
                }
                if (!layoutBasic.getHidden() || layoutBasic.getHidden() != 'true') {
                    // 处理布局组件下的所有标签
                    dealTags(layoutBasic, layoutNode);
                    // 处理布局组件下的所有按钮
                    dealLayoutButtons(layoutBasic, layoutNode);
                    // 处理布局组件下的所有事件
                    dealLayoutEvents(layoutBasic, layoutNode);
                }
            } else {
                throw new java.lang.Exception('布局组件代码为:' + layoutBasic.getLayoutCode() + '的' + layoutBasic.getLayoutType()
                    + '类型的布局组件不存在!');
            }
        } else {
            throw new java.lang.Exception('布局代码为:' + layoutBasic.getLayoutCode() + ',布局描述为:' + layoutBasic.getLayoutDesc()
                + '的布局组件ID未设置！');
        }
    }

    var dealLayoutsHidden = function () {
        // 查询当前page下的所有布局组件
        var layoutArr = jsEngine.queryPageLayoutBasic(requestCtx, $ctx.getObject('parameter').pageId);
        if (!layoutArr || layoutArr.size() == 0) {
            throw new java.lang.Exception('当前页面不存在布局组件！');
        }
        // 处理布局组件配置\布局组件下的标签配置\布局组件下的按钮配置\布局组件的事件配置\布局组件下标签的事件配置
        for (var layoutIdx = 0; layoutIdx < layoutArr.size(); layoutIdx++) {
            dealLayoutHidden(layoutArr.get(layoutIdx));
        }
    }

    var dealLayoutHidden = function (layoutBasic) {
        var layoutNode = new CompositeMap(CompositeUtil.findChild($config(), layoutBasic.getLayoutType(), 'id',
            layoutBasic.getId()));
        // 设置隐藏
        if (layoutBasic.getHidden()) {
            if (layoutBasic.getHidden() == 'true' || layoutBasic.getHidden() == 'Y') {
                layoutNode.hidden = 'true';
                layoutNode.getParent().removeChild(layoutNode);
                return;
            } else if (layoutBasic.getHidden() == 'false' || layoutBasic.getHidden() == 'N') {
                layoutNode.hidden = 'false';
            }
        }
    }

    // 处理所有的布局组件
    var dealLayouts = function () {
        // 查询当前page下的所有布局组件
        var layoutArr = jsEngine.queryPageLayoutBasic(requestCtx, $ctx.getObject('parameter').pageId);

        if (!layoutArr || layoutArr.size() == 0) {
            throw new java.lang.Exception('当前页面不存在布局组件！');
        }

        // 处理布局组件配置\布局组件下的标签配置\布局组件下的按钮配置\布局组件的事件配置\布局组件下标签的事件配置
        for (var layoutIdx = 0; layoutIdx < layoutArr.size(); layoutIdx++) {
            dealLayout(layoutArr.get(layoutIdx));
        }
    }

    // 标签的数据源属性设置
    var dealTagDsAtt = function (tagBasic, ds) {
        var fieldsObj = CompositeUtil.findChild(ds, 'fields');
        var fields = null;
        // 如果当前dataSet下不存在Fields标签，则新建
        if (!fieldsObj) {
            fields = new CompositeMap(ds).createChildNS('fields');
        } else {
            fields = new CompositeMap(fieldsObj);
        }
        var fieldObj = CompositeUtil.findChild(fieldsObj, 'field', 'name', tagBasic.getName());
        var field = null;
        // 如果当前fields下不存在对应name的field，则新建
        if (!fieldObj) {
            field = fields.createChildNS('field');
        } else {
            field = new CompositeMap(fieldObj);
        }
        // 设置标签名称
        if (tagBasic.getName()) {
            field.name = tagBasic.getName();
        }
        // 设置默认值
        if (tagBasic.getDefaultvalue()) {
            var defaultvalue = tagBasic.getDefaultvalue();
            if(defaultvalue.contains('#LOV')){
                field.defaultvalue = jsEngine.getLovDefaultvalue(requestCtx,$ctx.getData(),tagBasic.getDefaultvalue());
            }else if(defaultvalue.contains('#JAVA')){
                field.defaultvalue = jsEngine.getJavaDefaultvalue(requestCtx,$ctx.getData(),tagBasic.getDefaultvalue());
            }else{
                field.defaultvalue = tagBasic.getDefaultvalue();
            }
        }
        // 设置是否只读
        if (tagBasic.getReadonly()) {
            field.readonly = tagBasic.getReadonly();
        }
        // 设置是否必输
        if (tagBasic.getRequired()) {
            field.required = tagBasic.getRequired();
        }
        // 设置描述
        if (tagBasic.getPrompt()) {
            field.prompt = tagBasic.getPrompt();
        }
        // 设置校验代码
        if (tagBasic.getValidator()) {
            field.validator = tagBasic.getValidator();
        }
        // 设置必输提示信息
        if (tagBasic.getRequiredmessage()) {
            field.requiredmessage = tagBasic.getRequiredmessage();
        }
        return field;
    }

    // 非模板标签处理
    var dealTagNoTplt = function (tagBasic, layoutBasic, layoutNode, ds, field) {
        // 如果当前标签类型为隐藏，则不创建相关标签
        if (tagBasic.getTagType() == 'hidden') {
            return;
        } else {
            var tagObj = null;
            var tag = null;
            var tagId = layoutBasic.getId() + '_' + tagBasic.getName() + '_' + tagBasic.getTagType();
            tagBasic.setId(tagId);
            if (layoutBasic.getLayoutType() == 'grid' || layoutBasic.getLayoutType() == 'table') {
                // Grid以及Table的属性设置
                colObj = CompositeUtil.findChild(layoutNode.getData(), 'column', 'name', tagBasic.getName());
                if (colObj) {
                    throw new java.lang.Exception('布局组件:' + layoutBasic.getLayoutDesc() + '下已经存在名称为:' + tagBasic.getName()
                        + '的列');
                } else {
                    columnsObj = CompositeUtil.findChild(layoutNode.getData(), 'columns');
                    var columnNode = null;
                    if (!columnsObj) {
                        columnsNode = layoutNode.createChildNS('columns');
                    } else {
                        columnsNode = new CompositeMap(columnsObj);
                    }
                    editorsObj = CompositeUtil.findChild(layoutNode.getData(), 'editors');
                    if (!editorsObj) {
                        editorsNode = layoutNode.createChildNS('editors');
                    } else {
                        editorsNode = new CompositeMap(editorsObj);
                    }
                    // 如果当前标签存在上级标签,说明是合并列,需要将当前标签创建到上级标签下,否则直接创建columns下
                    if (tagBasic.getParentName()) {
                        parentColumnObj = CompositeUtil.findChild(columnsNode.getData(), 'column', 'name',
                            tagBasic.getParentName());
                        if (!parentColumnObj) {
                            throw new java.lang.Exception('布局组件:' + layoutBasic.getLayoutDesc() + '下名称为:' + tagBasic.getName()
                                + '的标签的父标签还未被创建，请检查标签顺序');
                        } else {
                            var parentColumnNode = new CompositeMap(parentColumnObj);
                            columnNode = parentColumnNode.createChildNS('column');
                        }
                    } else {
                        columnNode = columnsNode.createChildNS('column');
                    }

                    // 设置column的name属性
                    if (tagBasic.getName()) {
                        columnNode.name = tagBasic.getName();
                    }
                    // 设置column的width属性
                    if (tagBasic.getWidth()) {
                        columnNode.width = tagBasic.getWidth();
                    }
                    // 设置column的align属性
                    if (tagBasic.getAlign()) {
                        columnNode.align = tagBasic.getAlign();
                    }
                    // 如果当前列可以维护，则设置column的编辑器
                    if (tagBasic.getReadonly() != 'true' && tagBasic.getReadonly() != 'Y') {
                        // 合并列不存在编辑器
                        if (tagBasic.getTagType() != 'merge_column') {
                            columnNode.editor = tagId;
                        }
                    }
                    // 设置column的editorfunction属性
                    if (tagBasic.getEditorfunction() && (tagBasic.getReadonly() != 'true' && tagBasic.getReadonly() != 'Y')) {
                        columnNode.editorfunction = tagBasic.getEditorfunction();
                    }
                    // 设置column的footerrenderer属性
                    if (tagBasic.getFooterrenderer() && tagBasic.getFooterrenderer() == 'Y' && tagBasic.getFooterrendererJs()) {
                        var footerRendererFunName = layoutBasic.getId() + '_' + tagBasic.getName() + '_footerrenderer';
                        var originalText = scriptFirstNode.getText();
                        originalText = originalText ? originalText : '';
                        scriptFirstNode.setText(originalText + '\n' + 'function ' + footerRendererFunName
                            + '(_data,_name){' + tagBasic.getFooterrendererJs() + '};\n');
                        columnNode.footerrenderer = footerRendererFunName;
                    }
                    // 设置column的lock属性
                    if (tagBasic.getLockFlag()) {
                        columnNode.lock = tagBasic.getLockFlag();
                    }
                    // 设置column的renderer属性
                    if (tagBasic.getRenderer()) {
                        columnNode.renderer = tagBasic.getRenderer();
                    }
                    // 设置column的resizable属性
                    if (tagBasic.getResizable()) {
                        columnNode.resizable = tagBasic.getResizable();
                    }
                    // 设置column的sortable属性
                    if (tagBasic.getSortable()) {
                        columnNode.sortable = tagBasic.getSortable();
                    }
                    // 设置column的printable属性
                    if (tagBasic.getPrintable()) {
                        columnNode.printable = tagBasic.getPrintable();
                    }
                    // 设置column的hidden属性
                    // if (tagBasic.getHidden()) {
                    // columnNode.hidden = tagBasic.getHidden();
                    // }
                    // 如果当前列类型为merge_column，则不生成相应的编辑器，直接返回
                    if (tagBasic.getTagType() == 'merge_column') {
                        columnNode.prompt = tagBasic.getPrompt();
                        return;
                    }
                    tag = editorsNode.createChildNS(tagBasic.getTagType());
                    // 所有的标签都不进行全角半角转换
                    tag.transformCharacter = 'false';
                    // 设置tag的ID属性
                    tag.id = tagId;
                }
            } else {
                // Form类的属性设置
                tagObj = CompositeUtil.findChild(layoutNode.getData(), tagBasic.getTagType(), 'name', tagBasic.getName());
                if (tagObj) {
                    throw new java.lang.Exception('布局组件:' + layoutBasic.getLayoutDesc() + '下已经存在名称为:' + tagBasic.getName()
                        + '的标签');
                } else {
                    tag = layoutNode.createChildNS(tagBasic.getTagType());
                }
                // 设置tag的ID属性
                tag.id = tagId;

                // 设置标签的name属性
                if (tagBasic.getName()) {
                    tag.name = tagBasic.getName();
                }
                // 设置标签的bindTarget属性
                if (tagBasic.getBindtarget()) {
                    tag.bindtarget = tagBasic.getBindtarget();
                }
                // 设置标签的width属性
                if (tagBasic.getWidth()) {
                    tag.width = tagBasic.getWidth();
                }
                // 设置标签的height属性
                if (tagBasic.getHeight()) {
                    tag.height = tagBasic.getHeight();
                }
                // 设置标签的style属性
                if (tagBasic.getStyle()) {
                    tag.style = tagBasic.getStyle();
                }
                // 设置标签的colspan属性
                if (tagBasic.getColspan()) {
                    tag.colspan = tagBasic.getColspan();
                }
                // 设置标签的rowspan属性
                if (tagBasic.getRowspan()) {
                    tag.rowspan = tagBasic.getRowspan();
                }
                // 设置标签的hidden属性
                if (tagBasic.getHidden()) {
                    tag.hidden = tagBasic.getHidden();
                }
                // 所有的标签都不进行全角半角转换
                tag.transformCharacter = 'false';
            }

            // 设置查询参数
            $ctx.getObject('parameter').tagId = tagBasic.getTagId();
            if (tagBasic.getTagType() == 'checkBox') {
                var checkBoxAttArr = jsEngine.queryPageTagCheckbox(requestCtx, $ctx.getObject('parameter').tagId);
                if (!checkBoxAttArr || checkBoxAttArr.size() == 0) {
                    throw new java.lang.Exception('布局组件:' + layoutBasic.getLayoutDesc() + '下名称为:' + tagBasic.getName() + '的'
                        + tagBasic.getTagType() + '类型的标签不存在相应的' + tagBasic.getTagType() + '属性');
                } else {
                    var checkBoxAtt = checkBoxAttArr.get(0);
                    // 设置checkBox的checkedvalue属性
                    if (checkBoxAtt.getCheckedvalue()) {
                        field.checkedvalue = checkBoxAtt.getCheckedvalue();
                    }
                    // 设置checkBox的checkedvalue属性
                    if (checkBoxAtt.getUncheckedvalue()) {
                        field.uncheckedvalue = checkBoxAtt.getUncheckedvalue();
                    }
                    // 设置checkBox的label属性
                    if (checkBoxAtt.getLabel()) {
                        tag.label = checkBoxAtt.getLabel();
                    }
                }
            } else if (tagBasic.getTagType() == 'comboBox') {
                var comboBoxAttArr = jsEngine.queryPageTagCombobox(requestCtx, $ctx.getObject('parameter').tagId);
                if (!comboBoxAttArr || comboBoxAttArr.size() == 0) {
                    throw new java.lang.Exception('布局组件:' + layoutBasic.getLayoutDesc() + '下名称为:' + tagBasic.getName() + '的'
                        + tagBasic.getTagType() + '类型的标签不存在相应的' + tagBasic.getTagType() + '属性');
                } else {
                    var comboBoxAtt = comboBoxAttArr.get(0);
                    // 设置comboBox的options属性
                    if (comboBoxAtt.getOptions()) {
                        field.options = comboBoxAtt.getOptions();
                    }
                    // 设置comboBox的displayfield属性
                    if (comboBoxAtt.getDisplayfield()) {
                        field.displayfield = comboBoxAtt.getDisplayfield();
                    }
                    // 设置comboBox的valuefield属性
                    if (comboBoxAtt.getValuefield()) {
                        field.valuefield = comboBoxAtt.getValuefield();
                    }
                    // 设置comboBox的returnfield属性
                    if (comboBoxAtt.getReturnfield()) {
                        field.returnfield = comboBoxAtt.getReturnfield();
                    }
                    // 设置comboBox的options属性|syscode属性|sql属性，只能用一种
                    var dss = CompositeUtil.findChild($config(), 'dataSets');
                    if (comboBoxAtt.getDatasource() == 'OPTIONS') {
                        //取消直接设置Options的支持，修改为使用SYSCODE
                    } else if (comboBoxAtt.getDatasource() == 'SYSCODE' && comboBoxAtt.getSyscode()) {
                        var optionsDsId = layoutBasic.getId() + '_' + tagBasic.getName() + '_syscode_' + comboBoxAtt.getSyscode()
                            + '_ds';
                        var childList = dss.getChilds();
                        var optionsDs = new CompositeMap('dataSet');
                        optionsDs.getData().setPrefix(dss.getPrefix());
                        optionsDs.getData().setNameSpaceURI(dss.getNamespaceURI());
                        childList.add(0, optionsDs.getData());
                        optionsDs.id = optionsDsId;
                        optionsDs.queryurl = $ctx.getObject('request').context_path + '/common/auroraCode/' + comboBoxAtt.getSyscode() + '/';
                        optionsDs.autoquery = 'true';
                        optionsDs.fetchall = 'true';
                        field.displayfield = 'meaning';
                        field.valuefield = 'value';
                        field.options = optionsDsId;
                    } else if (comboBoxAtt.getDatasource() == 'SQLTEXT' && comboBoxAtt.getSqltext()) {
                        //取消直接设置SQL的支持，修改为使用LOV
                    } else if (comboBoxAtt.getDatasource() == 'LOVCODE' && comboBoxAtt.getLovcode()) {
                        var optionsDsId = layoutBasic.getId() + '_' + tagBasic.getName() + '_lovcode_' + comboBoxAtt.getLovcode()
                            + '_ds';
                        var childList = dss.getChilds();
                        var optionsDs = new CompositeMap('dataSet');
                        optionsDs.getData().setPrefix(dss.getPrefix());
                        optionsDs.getData().setNameSpaceURI(dss.getNamespaceURI());
                        childList.add(0, optionsDs.getData());
                        optionsDs.id = optionsDsId;
                        optionsDs.queryurl = $ctx.getObject('request').context_path + '/common/lov/' + comboBoxAtt.getLovcode() + '/';
                        optionsDs.autoquery = 'true';
                        optionsDs.fetchall = 'true';
                        field.options = optionsDsId;
                    }
                    // 设置comboBox的mapping属性，lovcode类型设置mapping
                    if (comboBoxAtt.getDatasource() == 'LOVCODE' || comboBoxAtt.getDatasource() == 'SYSCODE' ) {
                        $ctx.parameter.tagId = tagBasic.getTagId();
                        var mapperArr = jsEngine.queryPageTagComboboxMap(requestCtx, $ctx.parameter.tagId);
                        if (mapperArr && mapperArr.size() > 0 && (comboBoxAtt.getReturnfield() || comboBoxAtt.getValuefield())) {
                            throw new java.lang.Exception('布局组件:' + layoutBasic.getLayoutDesc() + '下名称为:' + tagBasic.getName()
                                + '的' + tagBasic.getTagType() + '类型的标签既存在returnField、valueField又存在mapper映射，存在冲突！');
                        } else if (mapperArr.size() > 0) {
                            var mappingField = field.createChildNS('mapping');
                            for (var mapperIdx = 0; mapperIdx < mapperArr.size(); mapperIdx++) {
                                var mapField = mappingField.createChildNS('map');
                                mapField.from = mapperArr.get(mapperIdx).getFromField();
                                mapField.to = mapperArr.get(mapperIdx).getToField();
                            }
                        }
                    }
                }
            } else if (tagBasic.getTagType() == 'datePicker') {
                var datePickerAttArr = jsEngine.queryPageTagDatepicker(requestCtx, $ctx.getObject('parameter').tagId);
                if (!datePickerAttArr || datePickerAttArr.size() == 0) {
                    throw new java.lang.Exception('布局组件:' + layoutBasic.getLayoutDesc() + '下名称为:' + tagBasic.getName() + '的'
                        + tagBasic.getTagType() + '类型的标签不存在相应的' + tagBasic.getTagType() + '属性');
                } else {
                    var datePickerAtt = datePickerAttArr.get(0);
                    // 设置datePicker的dayrenderer属性
                    if (datePickerAtt.getDayrenderer()) {
                        field.dayrenderer = datePickerAtt.getDayrenderer();
                    }
                    // 设置datePicker的enablebesidedays属性
                    if (datePickerAtt.getEnablebesidedays()) {
                        field.enablebesidedays = datePickerAtt.getEnablebesidedays();
                    }
                    // 设置datePicker的enablemonthbtn属性
                    /*
                     * if (datePickerAtt.enablemonthbtn) { tag.enablemonthbtn =
                     * datePickerAtt.enablemonthbtn; }
                     */
                    // 设置datePicker的viewsize属性
                    if (datePickerAtt.getViewsize()) {
                        tag.viewsize = datePickerAtt.getViewsize();
                    }
                    // 设置datePicker的renderer属性
                    if (datePickerAtt.getRenderer()) {
                        tag.renderer = datePickerAtt.getRenderer();
                    } else {
                        tag.renderer = 'Aurora.formatDate';
                        if (columnNode) {
                            columnNode.renderer = 'Aurora.formatDate'
                        }
                    }
                }
            } else if (tagBasic.getTagType() == 'dateTimePicker') {
                var datetimePickerAttArr = jsEngine.queryPageTagDatetimepicker(requestCtx, $ctx.getObject('parameter').tagId);
                if (!datetimePickerAttArr || datetimePickerAttArr.size() == 0) {
                    throw new java.lang.Exception('布局组件:' + layoutBasic.getLayoutDesc() + '下名称为:' + tagBasic.getName() + '的'
                        + tagBasic.getTagType() + '类型的标签不存在相应的' + tagBasic.getTagType() + '属性');
                } else {
                    var datetimePickerAtt = datetimePickerAttArr.get(0);
                    // 设置datetimePicker的dayrenderer属性
                    if (datetimePickerAtt.getDayrenderer()) {
                        field.dayrenderer = datetimePickerAtt.getDayrenderer();
                    }
                    // 设置datetimePicker的enablebesidedays属性
                    if (datetimePickerAtt.getEnablebesidedays()) {
                        field.enablebesidedays = datetimePickerAtt.getEnablebesidedays();
                    }
                    // 设置datetimePicker的enablemonthbtn属性
                    /*
                     * if (datetimePickerAtt.enablemonthbtn) {
                     * tag.enablemonthbtn = datetimePickerAtt.enablemonthbtn; }
                     */
                    // 设置datetimePicker的viewsize属性
                    if (datetimePickerAtt.getViewsize()) {
                        tag.viewsize = datetimePickerAtt.getViewsize();
                    }
                    // 设置datetimePicker的renderer属性
                    if (datetimePickerAtt.getRenderer()) {
                        tag.renderer = datetimePickerAtt.getRenderer();
                    } else {
                        tag.renderer = 'Aurora.formatDateTime';
                        if (columnNode) {
                            columnNode.renderer = 'Aurora.formatDateTime'
                        }
                    }
                    // 设置datetimePicker的hour属性
                    if (datetimePickerAtt.getHour()) {
                        tag.hour = datetimePickerAtt.getHour();
                    }
                    // 设置datetimePicker的minute属性
                    if (datetimePickerAtt.getMinute()) {
                        tag.minute = datetimePickerAtt.getMinute();
                    }
                    // 设置datetimePicker的second属性
                    if (datetimePickerAtt.getSecond()) {
                        tag.second = datetimePickerAtt.getSecond();
                    }
                }
            } else if (tagBasic.getTagType() == 'label') {
                var labelAttArr = jsEngine.queryPageTagLabel(requestCtx, $ctx.getObject('parameter').tagId);
                if (!labelAttArr || labelAttArr.size() == 0) {
                    throw new java.lang.Exception('布局组件:' + layoutBasic.getLayoutDesc() + '下名称为:' + tagBasic.getName() + '的'
                        + tagBasic.getTagType() + '类型的标签不存在相应的' + tagBasic.getTagType() + '属性');
                } else {
                    var labelAtt = labelAttArr.get(0);
                    // 设置label的renderer属性
                    if (labelAtt.getRenderer()) {
                        tag.renderer = labelAtt.getRenderer();
                    }
                }
            } else if (tagBasic.getTagType() == 'lov') {
                var lovAttArr = jsEngine.queryPageTagLov(requestCtx, $ctx.getObject('parameter').tagId);
                if (!lovAttArr || lovAttArr.size() == 0) {
                    throw new java.lang.Exception('布局组件:' + layoutBasic.getLayoutDesc() + '下名称为:' + tagBasic.getName() + '的'
                        + tagBasic.getTagType() + '类型的标签不存在相应的' + tagBasic.getTagType() + '属性');
                } else {
                    var lovAtt = lovAttArr.get(0);
                    // 设置lov的lovautoquery属性
                    if (lovAtt.getLovautoquery()) {
                        field.lovautoquery = lovAtt.getLovautoquery();
                    }
                    // 设置lov的lovgridheight属性
                    if (lovAtt.getLovgridheight()) {
                        field.lovgridheight = lovAtt.getLovgridheight();
                    } else {
                        field.lovgridheight = 320;
                    }
                    // 设置lov的lovheight属性
                    if (lovAtt.getLovheight()) {
                        field.lovheight = lovAtt.getLovheight();
                    } else {
                        field.lovheight = 450;
                    }
                    // 设置lov的lovwidth属性
                    if (lovAtt.getLovwidth()) {
                        field.lovwidth = lovAtt.getLovwidth();
                    } else {
                        field.lovwidth = 500;
                    }
                    // 设置lov的lovlabelwidth属性
                    if (lovAtt.getLovlabelwidth()) {
                        field.lovlabelwidth = lovAtt.getLovlabelwidth();
                    }
                    // 设置lov的autocomplete属性
                    if (lovAtt.getAutocomplete()) {
                        field.autocomplete = lovAtt.getAutocomplete();
                    }
                    // 设置lov的autocompletefield属性
                    if (lovAtt.getAutocompletefield()) {
                        field.autocompletefield = lovAtt.getAutocompletefield();
                    }
                    // 设置lov的title属性
                    if (lovAtt.getTitle()) {
                        field.title = lovAtt.getTitle();
                    }
                    // 设置lov的fetchRemote属性
                    // if (lovAtt.getFetchremote()) {
                    //     field.fetchremote = lovAtt.getFetchremote();
                    // } else {
                    //     field.fetchremote = 'true';
                    // }
                    // 设置lov的lovservice属性|lovurl属性|syscode属性|sqltext属性
                    //目前只支持LOVCODE
                    if (lovAtt.getLovcode()) {
                        field.lovcode = lovAtt.getLovcode();
                    }
                    // else if (lovAtt.lovurl) {
                    //     field.lovurl = lovAtt.lovurl;
                    // } else if (lovAtt.datasource == 'SYSCODE' && datalovAtt.syscode) {
                    //     field.lovservice = 'bpm.ENGINE.bpm_engine_lov_syscode_query?syscode=' + lovAtt.syscode;
                    // } else if (lovAtt.datasource == 'SQLTEXT' && lovAtt.sqltext) {
                    //     field.lovurl = $ctx.getObject('request').context_path
                    //         + '/modules/bpm/ENGINE/bpm_lov_service.screen?tagId=' + tagBasic.getTagId()
                    //         + '&lov_width=' + field.lovwidth + '&lov_height=' + field.lovheight
                    //         + '&lov_grid_height=' + field.lovgridheight;
                    //     field.lovservice = 'bpm.ENGINE.bpm_engine_lov_sqltext_query?tagId=' + tagBasic.getTagId();
                    // }
                    // 设置comboBox的mapping属性
                    $ctx.parameter.tagId = tagBasic.getTagId();
                    var mapperArr = jsEngine.queryPageTagLovMap(requestCtx, $ctx.parameter.tagId);
                    if (!mapperArr || mapperArr.size() == 0) {
                        throw new java.lang.Exception('布局组件:' + layoutBasic.getLayoutDesc() + '下名称为:' + tagBasic.getName() + '的'
                            + tagBasic.getTagType() + '类型的标签不存在mapping映射,请设置相应的mapping映射！');
                    } else {
                        var mappingField = field.createChildNS('mapping');
                        for (var mapperIdx = 0; mapperIdx < mapperArr.size(); mapperIdx++) {
                            var mapField = mappingField.createChildNS('map');
                            mapField.from = mapperArr.get(mapperIdx).getFromField();
                            mapField.to = mapperArr.get(mapperIdx).getToField();
                        }
                    }
                }
            } else if (tagBasic.getTagType() == 'numberField') {
                var numberFieldAttArr = jsEngine.queryPageTagNumberfield(requestCtx, $ctx.parameter.tagId);
                if (!numberFieldAttArr || numberFieldAttArr.size() == 0) {
                    throw new java.lang.Exception('布局组件:' + layoutBasic.getLayoutDesc() + '下名称为:' + tagBasic.getName() + '的'
                        + tagBasic.getTagType() + '类型的标签不存在相应的' + tagBasic.getTagType() + '属性');
                } else {
                    var numberFieldAtt = numberFieldAttArr.get(0);
                    // 设置numberField的allowdecimals属性
                    if (numberFieldAtt.getAllowdecimals()) {
                        tag.allowdecimals = numberFieldAtt.getAllowdecimals();
                    }
                    // 设置numberField的decimalprecision属性
                    if (numberFieldAtt.getDecimalprecision()) {
                        tag.decimalprecision = numberFieldAtt.getDecimalprecision();
                    }
                    // 设置numberField的allownegative属性
                    if (numberFieldAtt.getAllownegative()) {
                        tag.allownegative = numberFieldAtt.getAllownegative();
                    }
                    // 设置numberField的allowformat属性
                    if (numberFieldAtt.getAllowformat()) {
                        tag.allowformat = numberFieldAtt.getAllowformat();
                        if (numberFieldAtt.getAllowformat() == 'true'
                            && (layoutBasic.getLayoutType() == 'grid' || layoutBasic.getLayoutType() == 'table')) {
                            columnNode.renderer = 'Aurora.formatMoney';
                        }
                    }
                }
            } else if (tagBasic.getTagType() == 'radio') {
                var radioAttArr = jsEngine.queryPageTagRadio(requestCtx, $ctx.parameter.tagId);
                if (!radioAttArr || radioAttArr.size() == 0) {
                    throw new java.lang.Exception('布局组件:' + layoutBasic.getLayoutDesc() + '下名称为:' + tagBasic.getName() + '的'
                        + tagBasic.getTagType() + '类型的标签不存在相应的' + tagBasic.getTagType() + '属性');
                } else {
                    var radioAtt = radioAttArr.get(0);

                    // 设置radio的valuefield属性
                    if (radioAtt.getValuefield()) {
                        field.valuefield = radioAtt.getValuefield();
                    }
                    // 设置radio的labelexpression属性
                    if (radioAtt.getLabelexpression()) {
                        tag.labelexpression = radioAtt.getLabelexpression();
                    }
                    // 设置radio的labelfield属性
                    if (radioAtt.getLabelfield()) {
                        tag.labelfield = radioAtt.getLabelfield();
                    }
                    // 设置radio的layout属性
                    if (radioAtt.getLayout()) {
                        tag.layout = radioAtt.getLayout();
                    } else {
                        tag.layout = 'horizontal';
                    }
                    // 设置radio的options属性|syscode属性|sqltext属性
                    if (radioAtt.datasource == 'OPTIONS') {
                        //radio目前仅支持syscode
                    } else if (radioAtt.datasource == 'SYSCODE' && radioAtt.getSyscode()) {
                        $ctx.parameter.syscode = radioAtt.getSyscode();
                        var rootPath = field.name + '_' + radioAtt.getSyscode();

                        codeValues = codeService.selectCodeValuesByCodeName(requestCtx, $ctx.parameter.syscode);
                        var tmpMap = new HashMap();
                        tmpMap.put(rootPath, codeValues);
                        ModelCopy.copy($ctx.getData(), tmpMap);

                        tag.options = '/model/' + rootPath + '/records';
                        tag.labelexpression = '$[@description]';
                        tag.valuefield = 'value';
                    } else if (radioAtt.getDatasource() == 'SQLTEXT' && radioAtt.getSqltext()) {
                        throw new java.lang.Exception('布局组件:' + layoutBasic.getLayoutDesc() + '下名称为:' + tagBasic.getName() + '的'
                            + tagBasic.getTagType() + '类型的标签存在SQLTEXT属性,目前该属性不被支持');
                    }
                }
            } else if (tagBasic.getTagType() == 'textField') {
                var textFieldAttArr = jsEngine.queryPageTagTextfield(requestCtx, $ctx.parameter.tagId);
                if (!textFieldAttArr || textFieldAttArr.size() == 0) {
                    throw new java.lang.Exception('布局组件:' + layoutBasic.getLayoutDesc() + '下名称为:' + tagBasic.getName() + '的'
                        + tagBasic.getTagType() + '类型的标签不存在相应的' + tagBasic.getTagType() + '属性');
                } else {
                    var textFieldAtt = textFieldAttArr.get(0);
                    // 设置textField的typecase属性
                    if (textFieldAtt.getTypecase()) {
                        field.typecase = textFieldAtt.getTypecase();
                    }
                }
            } else if (tagBasic.getTagType() == 'tree') {
                //暂时不支持Tree
            } else if (tagBasic.getTagType() == 'treeGrid') {
                //暂时不支持TreeGrid
            }
        }
    }

    // 表单类型的模板标签处理
    var dealGridTagTplt = function (tagBasic, layoutBasic, layoutNode, ds, field) {
        if (tagBasic.getReadonly() && (tagBasic.getReadonly() == 'Y' || tagBasic.getReadonly() == 'true')) {
            // 修改模板标签为只读类型时的editor和editorFun
            var layoutObj = CompositeUtil.findChild($config(), layoutBasic.getLayoutType(), 'id', layoutBasic.getId());
            var columnsObj = CompositeUtil.findChild(layoutObj, 'columns');
            if (!columnsObj) {
                return;
            } else {
                var column = CompositeUtil.findChild(columnsObj, 'column', 'name', tagBasic.getName());
                if (column) {
                    if (tagBasic.getReadonly() && (tagBasic.getReadonly() == 'Y' || tagBasic.getReadonly() == 'true')) {
                        column.remove('editor');
                        column.remove('editorfunction');
                    } else if (tagBasic.getReadonly() && (tagBasic.getReadonly() == 'Y' || tagBasic.getReadonly() == 'true')) {
                        return;
                    }
                } else {
                    return;
                }
            }
        }
    }

    // 处理标签设置
    var dealTag = function (tagBasic, layoutBasic, layoutNode) {
        var ds = CompositeUtil.findChild($config(), 'dataSet', 'id', tagBasic.getBindtarget());
        if (!ds) {
            // 如果标签上设置的数据源不存在，抛出异常进行提示
            throw new java.lang.Exception('布局组件：' + layoutBasic.getLayoutDesc() + '下的标签：' + tagBasic.getTagDesc()
                + '对应的DataSet：' + tagBasic.getBindtarget() + '不存在');
        } else {
            var field = dealTagDsAtt(tagBasic, ds);
            if (tagBasic.templateFlag == 'Y') {
                // 处理模板带出的标签
                if (!tagBasic.getId() && layoutBasic.getLayoutType() != 'grid' && layoutBasic.getLayoutType() != 'table') {
                    // 如果当前标签的ID未设置，抛出异常进行提示
                    throw new java.lang.Exception('布局组件：' + layoutBasic.getLayoutDesc() + '下的标签：' + tagBasic.getTagDesc()
                        + '对应的Id属性为空');
                } else if (layoutBasic.getLayoutType() != 'grid' && layoutBasic.getLayoutType() != 'table') {
                    var tag = CompositeUtil.findChild($config(), tagBasic.getTagType(), 'id', tagBasic.getId());
                    if (!tag) {
                        // 如果ID对应的标签不存在，抛出异常进行提示
                        throw new java.lang.Exception('布局组件：' + layoutBasic.getLayoutDesc() + '下的标签：' + tagBasic.getTagDesc()
                            + '的Id:' + tagBasic.getId() + '标签不存在');
                    }

                    tag.put('hidden', (tagBasic.getHidden()).toString());
                } else {
                    dealGridTagTplt(tagBasic, layoutBasic, layoutNode, ds, field)
                }
            } else if (tagBasic.templateFlag == 'N') {
                // 处理配置进来的标签
                dealTagNoTplt(tagBasic, layoutBasic, layoutNode, ds, field);
            }
            dealTagEvents(tagBasic, layoutBasic);
        }
    }

    // 处理标签下的所有事件
    var dealTagEvents = function (tagBasic, layoutBasic) {
        $ctx.parameter.tagId = tagBasic.getTagId();
        var tagEventsArr = jsEngine.queryPageTagEvent(requestCtx, $ctx.parameter.tagId);
        if (tagEventsArr && tagEventsArr.size() != 0) {
            for (var eventIdx = 0; eventIdx < tagEventsArr.size(); eventIdx++) {
                var tagEventAtt = tagEventsArr.get(eventIdx);
                if (tagEventAtt.eventTarget == 'DATASET') {
                    // 设置数据源上的事件
                    var dsObj = CompositeUtil.findChild(viewObj, 'dataSet', 'id', tagBasic.getBindtarget());
                    if (!dsObj) {
                        throw new java.lang.Exception('标签:' + tagBasic.getTagDesc() + '设置的bindtarget:'
                            + tagBasic.getBindtarget() + '不存在');
                    } else {
                        var originalText = scriptLastNode.getText();
                        originalText = originalText ? originalText : '';
                        scriptLastNode.setText(originalText + '\n' + '$au(\'' + tagBasic.getBindtarget() + '\').on(\''
                            + tagEventsArr.get(eventIdx).getEventType() + '\',' + tagEventsArr.get(eventIdx).getEventHandler()
                            + ');\n');
                    }
                } else if (tagEventAtt.eventTarget == 'TAG'
                    && (layoutBasic.getLayoutType() != 'grid' || layoutBasic.getLayoutType() != 'table')) {
                    // 如果当前布局组件不为grid和table的时候，tag属性可以设置，grid和table上的tag的
                    var originalText = scriptLastNode.getText();
                    originalText = originalText ? originalText : '';
                    scriptLastNode
                        .setText(originalText + '\n' + '$au(\'' + tagBasic.getId() + '\').on(\''
                            + tagEventsArr.get(eventIdx).getEventType() + '\',' + tagEventsArr.get(eventIdx).getEventHandler()
                            + ');\n');
                }
            }
        }
    }

    // 处理布局组件下的所有标签
    var dealTags = function (layoutBasic, layoutNode) {
        // 处理布局组件下的标签属性
        $ctx.getObject('parameter').layoutId = layoutBasic.getLayoutId();
        var tagArr = jsEngine.queryPageTagBasic(requestCtx, $ctx.getObject('parameter').layoutId);
        for (var tagIdx = 0; tagIdx < tagArr.size(); tagIdx++) {
            dealTag(tagArr.get(tagIdx), layoutBasic, layoutNode);
        }
    }

    var dealLayoutBtn = function (layoutBtnAtt, layoutBasic, layoutNode) {
        if (layoutBasic.getLayoutType() == 'grid' || layoutBasic.getLayoutType() == 'table') {
            if (layoutBtnAtt.getTemplateFlag() == 'Y') {
                var buttonObj = CompositeUtil.findChild(layoutNode.getData(), 'button', 'id', layoutBtnAtt.getId());

                if (!buttonObj) {
                    throw new java.lang.Exception('模板类型的布局组件按钮,ID为:' + layoutBtnAtt.getId() + '在模板页面中不存在');
                } else {
                    var button = new CompositeMap(buttonObj);
                    if (layoutBtnAtt.getLayoutId() == 'OPERATION' && viewType == 'VIEW') {
                        layoutBtnAtt.setDisabled('true');
                        layoutBtnAtt.setHidden('true');
                    }
                    // 设置按钮的text属性
                    if (layoutBtnAtt.getText()) {
                        button.text = layoutBtnAtt.getText();
                    }
                    // 设置按钮的click属性
                    if (layoutBtnAtt.getClick()) {
                        button.click = layoutBtnAtt.getClick();
                    }
                    // 设置按钮的type属性
                    if (layoutBtnAtt.getButtonType()) {
                        button.type = layoutBtnAtt.getButtonType();
                    }
                    // 设置按钮的width属性
                    if (layoutBtnAtt.getWidth()) {
                        button.width = layoutBtnAtt.getWidth();
                    }
                    // 设置按钮的height属性
                    if (layoutBtnAtt.getHeight()) {
                        button.height = layoutBtnAtt.getHeight();
                    }
                    // 设置按钮的icon属性
                    if (layoutBtnAtt.getIcon()) {
                        button.icon = layoutBtnAtt.getIcon();
                    }
                    // 设置按钮的disabled属性
                    if (layoutBtnAtt.getDisabled()) {
                        button.disabled = layoutBtnAtt.getDisabled();
                    }
                    // 设置按钮的hidden属性
                    if (layoutBtnAtt.getHidden()) {
                        button.hidden = layoutBtnAtt.getHidden();
                    }
                }
            } else if (layoutBtnAtt.getTemplateFlag() == 'N') {
                // 页面上的非模板类型的按钮，支持新建
                var buttonObj = CompositeUtil.findChild(layoutNode.getData(), 'button', 'id', layoutBtnAtt.getId());
                if (buttonObj) {
                    throw new java.lang.Exception('模板类型的布局组件按钮,ID为:' + layoutBtnAtt.getId() + '在模板页面中已经存在');
                } else {
                    var toolBarObj = CompositeUtil.findChild(layoutNode.getData(), 'toolBar');
                    var toolBar = null;
                    if (toolBarObj) {
                        toolBar = new CompositeMap(toolBarObj);
                    } else {
                        toolBar = layoutNode.createChildNS('toolBar');
                    }
                    if (layoutBtnAtt.getLayoutId() == 'OPERATION' && viewType == 'VIEW') {
                        layoutBtnAtt.setDisabled('true');
                        layoutBtnAtt.setHidden('true');
                    }
                    var button = toolBar.createChildNS('button');
                    // 设置按钮的text属性
                    if (layoutBtnAtt.getText()) {
                        button.text = layoutBtnAtt.getText();
                    }
                    // 设置按钮的click属性
                    if (layoutBtnAtt.getClick()) {
                        button.click = layoutBtnAtt.getClick();
                    }
                    // 设置按钮的type属性
                    if (layoutBtnAtt.getButtonType()) {
                        button.type = layoutBtnAtt.getButtonType();
                    }
                    // 设置按钮的width属性
                    if (layoutBtnAtt.getWidth()) {
                        button.width = layoutBtnAtt.getWidth();
                    }
                    // 设置按钮的height属性
                    if (layoutBtnAtt.getHeight()) {
                        button.height = layoutBtnAtt.getHeight();
                    }
                    // 设置按钮的icon属性
                    if (layoutBtnAtt.getIcon()) {
                        button.icon = layoutBtnAtt.getIcon();
                    }
                    // 设置按钮的disabled属性
                    if (layoutBtnAtt.getDisabled()) {
                        button.disabled = layoutBtnAtt.getDisabled();
                    }
                    // 设置按钮的hidden属性
                    if (layoutBtnAtt.getHidden()) {
                        button.hidden = layoutBtnAtt.getHidden();
                    }
                }
            }
        } else {
            throw new java.lang.Exception('目前只支持Grid和Table类型布局组件下的按钮配置，布局组件:' + layoutBasic.getLayoutDesc() + '的类型为:'
                + layoutBasic.getLayoutType());
        }
    }

    // 处理布局组件下的所有按钮
    var dealLayoutButtons = function (layoutBasic, layoutNode) {
        var layoutBtnArr = jsEngine.queryPageLayoutButton(requestCtx, layoutBasic.getLayoutId());
        for (var btnIdx = 0; btnIdx < layoutBtnArr.size(); btnIdx++) {
            dealLayoutBtn(layoutBtnArr.get(btnIdx), layoutBasic, layoutNode);
        }
    }

    // 处理布局组件下的所有事件
    var dealLayoutEvents = function (layoutBasic, layoutNode) {
        var layoutEventsArr = jsEngine.queryPageLayoutEvent(requestCtx, layoutBasic.getLayoutId());
        if (layoutEventsArr && layoutEventsArr.size() != 0) {
            for (var eventIdx = 0; eventIdx < layoutEventsArr.size(); eventIdx++) {
                var layoutEventAtt = layoutEventsArr.get(eventIdx);
                if (layoutEventAtt.getEventTarget() == 'DATASET') {
                    var dsObj = CompositeUtil.findChild(viewObj, 'dataSet', 'id', layoutBasic.getDataset());
                    if (!dsObj) {
                        throw new java.lang.Exception('布局组件:' + layoutBasic.getLayoutDesc() + '设置的dataset:'
                            + layoutBasic.getDataset() + '不存在');
                    } else {
                        var originalText = scriptLastNode.getText();
                        originalText = originalText ? originalText : '';
                        scriptLastNode.setText(originalText + '\n' + '$au(\'' + layoutBasic.getDataset() + '\').on(\''
                            + layoutEventAtt.getEventType() + '\',' + layoutEventAtt.getEventHandler() + ')\n');
                    }
                } else if (layoutEventAtt.getEventTarget() == 'LAYOUT') {
                    var originalText = scriptLastNode.getText();
                    originalText = originalText ? originalText : '';
                    scriptLastNode.setText(originalText + '\n' + '$au(\'' + layoutNode.id + '\').on(\''
                        + layoutEventAtt.getEventType() + '\',' + layoutEventAtt.getEventHandler() + ');\n');
                }
            }
        }
    }

    // 处理页面的所有按钮
    var dealPageButtons = function () {
        // 查询当前page下的所有按钮
        var pageBtnArr = jsEngine.queryPageButton(requestCtx, pageId);
        if (pageBtnArr && pageBtnArr.size() > 0) {
            for (var pageBtnIdx = 0; pageBtnIdx < pageBtnArr.size(); pageBtnIdx++) {
                dealPageButton(pageBtnArr.get(pageBtnIdx));
            }
        }
    }

    // 处理页面按钮
    var dealPageButton = function (pageBtnAtt) {
        if (pageBtnAtt.getTemplateFlag() == 'Y') {
            var buttonObj = CompositeUtil.findChild($config(), 'button', 'id', pageBtnAtt.getId());
            if (!buttonObj) {
                throw new java.lang.Exception('模板类型的页面按钮,ID为:' + pageBtnAtt.getId() + '在模板页面中不存在');
            } else {
                var button = new CompositeMap(buttonObj);
                // 设置按钮的text属性
                if (pageBtnAtt.getText()) {
                    button.text = pageBtnAtt.getText();
                }
                // 设置按钮的click属性
                if (pageBtnAtt.getClick()) {
                    button.click = pageBtnAtt.getClick();
                }
                // 设置按钮的type属性
                if (pageBtnAtt.getButtonType()) {
                    button.type = pageBtnAtt.getButtonType();
                }
                // 设置按钮的width属性
                if (pageBtnAtt.getWidth()) {
                    button.width = pageBtnAtt.getWidth();
                }
                // 设置按钮的height属性
                if (pageBtnAtt.getHeight()) {
                    button.height = pageBtnAtt.getHeight();
                }
                // 设置按钮的icon属性
                if (pageBtnAtt.getIcon()) {
                    button.icon = pageBtnAtt.getIcon();
                }
                // 设置按钮的disabled属性
                if (pageBtnAtt.getDisabled()) {
                    button.disabled = pageBtnAtt.getDisabled();
                }
                // 设置按钮的hidden属性
                if (pageBtnAtt.getHidden()) {
                    button.hidden = pageBtnAtt.getHidden();
                }
            }
        } else if (pageBtnAtt.getTemplateFlag() == 'N') {
            // 页面上的非模板类型的按钮，目前不支持新建
            return;
        }
    }

    // 处理页面的所有事件
    var dealPageEvents = function () {
        // 页面事件
        //暂不支持页面事件
    }

    return {
        rendererPage: function () {
            try {
                // 刷新缓存标志
                var refreshCacheFlag = $ctx.parameter.refreshCache;

                if (!pageIdStr) {
                    throw new java.lang.Exception('当前页面传入的PageId为空！');
                }else{
                    pageId = java.lang.Long.parseLong(pageIdStr);
                }
                // 缓存键，采用dynamic_page_页面ID格式组成
                var viewCacheKey = 'dynamic_page_' + pageId;
                // var dynamicCache = $cache('DynamicPageCache');
                // 如果当前刷新缓存标志位Y，则刷新缓存
                if (refreshCacheFlag && refreshCacheFlag == 'Y') {
                    // 设置查询条件pageId
                    $ctx.getObject('parameter').pageId = pageId;
                    // 处理所有的布局组建
                    dealLayouts();
                    // 处理所有的页面按钮
                    dealPageButtons();
                    // 处理所有的页面事件
                    dealPageEvents();
                    // 处理布局组件的隐藏
                    dealLayoutsHidden();
                    viewObj = CompositeUtil.findChild($config(), 'view');
                    if (viewObj) {
                        bpmCache.setValue(viewCacheKey,viewObj.toXML());
                    }
                } else {
                    // 根据缓存键获取缓存的view
                    // var viewCacheValue = dynamicCache.getValue(viewCacheKey);

                    println(bpmCache.getValue(viewCacheKey));

                    var viewCacheValue = compositeLoader.loadFromString(bpmCache.getValue(viewCacheKey));
                    // 如果当前缓存内容为空，则重新生成
                    if (viewCacheValue==null) {
                        // 设置查询条件pageId
                        $ctx.getObject('parameter').pageId = pageId;
                        // 处理所有的布局组建
                        dealLayouts();
                        // 处理所有的页面按钮
                        dealPageButtons();
                        // 处理所有的页面事件
                        dealPageEvents();
                        // 处理布局组件的隐藏
                        dealLayoutsHidden();
                        // 如果view节点不为null，则缓存当前view至缓存中
                        viewObj = CompositeUtil.findChild($config(), 'view');
                        if (viewObj) {
                            bpmCache.setValue(viewCacheKey,viewObj.toXML());
                        }
                    } else {
                        // 如果缓存内容不为空，则取缓存的view节点
                        config.removeChild(view);
                        var viewObj = new CompositeMap(viewCacheValue.clone());
                        config.addChild(viewObj);
                    }
                }
                println(config.toXML());
            } catch (e) {
                writeErrorMsg(e.message);
            }
        }
    }
}();
// 处理逻辑调用
DynamicEngine.rendererPage();
