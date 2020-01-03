<?xml version="1.0" encoding="UTF-8"?>
<a:screen xmlns:c="aurora.application.action" xmlns:p="uncertain.proc"
          xmlns:a="http://www.aurora-framework.org/application" xmlns:s="aurora.plugin.script" trace="true">
    <a:view>
        <a:link id="BGT5120_set_bgt_journal_status_link" url="$[/request/@context_path]/bgt/journal-header/setStatus/"/>
        <a:link id="BGT5120_uploadFile_link" url="$[/request/@context_path]/app/APP2030/app_uploadFile.screen"/>
        <a:link id="BGT5120_bgt_journal_main_link"
                url="$[/request/@context_path]/bgt/BGT5120/bgt_journal_main.screen"/>
        <!--added by zealot at 20181121 工作流提交前预览-->
        <a:link id="BGT5120_workflow_preview_before_submit_link"
                url="$[/request/@context_path]/wfl/WFL1001/wfl_workflow_preview_before_submit.screen"/>
        <style>
            /*.layout-td-con {
                padding: 0 !important;
            }*/
        </style>
        <script><![CDATA[
        var submitFlag = false;

        function BGT5120_closeJournal() {
            $au('BGT5120_bgt_journal_maintain_window').close();
        }

        function BGT5120_onBgtCenterFocusFun() {
            var headerRecord = $au('BGT5120_bgtJournalHeaderDs').getAt(0);
            headerRecord.getField('bgtCenterName').setLovPara('bgtEntityId', headerRecord.get('bgtEntityId'));
        }

        function BGT5120_journalLineAdd() {
            var selectedRecords = $au('BGT5120_bgtJournalLineDs').getSelected();
            var lineDs = $au('BGT5120_bgtJournalLineDs');
            if (selectedRecords && selectedRecords.length && selectedRecords.length != 0) {
                Ext.each(selectedRecords, function (selectedRecord) {
                    var data = {};
                    for (var field in $au('BGT5120_bgtJournalLineDs').fields) {
                        data[field] = selectedRecord.get(field);
                    }
                    data['bgtJournalLineId'] = null;
                    $au('BGT5120_bgtJournalLineDs').create(data);
                });
            } else {
                var headerRecord = $au('BGT5120_bgtJournalHeaderDs').getAt(0);
                var data = {};
                data['structureId'] = headerRecord.get('structureId');
                $au('BGT5120_bgtJournalLineDs').create(data);
            }
        }

        function BGT5120_initFun() {
            var header_id = $au('BGT5120_bgtJournalHeaderDs').getAt(0).get('journalHeaderId');
            var bgtDiv = Ext.get('saveBgtDiv');
            if (Ext.isEmpty(header_id)) {
                bgtDiv.parent().setStyle('display', 'none');
            } else {
                bgtDiv.parent().setStyle('display', 'table-cell');
            }
            if ('$[/model/header/records/record/@periodStrategy]' == 'MONTH') {
                $au('BGT5120_bgtJournalLineDs').fields.linePeriodName.pro.required = true;
                $au('BGT5120_bgtJournalLineDs').fields.lineBgtPeriodNum.pro.required = true;
                $au('BGT5120_bgtJournalLineDs').fields.linePeriodYear.pro.required = true;
                $au('BGT5120_bgtJournalLineDs').fields.linePeriodQuarter.pro.required = true;
                $au('BGT5120_journalLineGrid').hideColumn('linePeriodQuarter');
                $au('BGT5120_journalLineGrid').hideColumn('linePeriodYear');
            } else if ('$[/model/header/records/record/@periodStrategy]' == 'QUARTER') {
                $au('BGT5120_bgtJournalLineDs').fields.linePeriodYear.pro.required = true;
                $au('BGT5120_bgtJournalLineDs').fields.linePeriodQuarter.pro.required = true;
                $au('BGT5120_journalLineGrid').hideColumn('linePeriodName');
            } else if ('$[/model/header/records/record/@periodStrategy]' == 'YEAR') {
                $au('BGT5120_bgtJournalLineDs').fields.linePeriodYear.pro.required = true;
                $au('BGT5120_journalLineGrid').hideColumn('linePeriodName');
                $au('BGT5120_journalLineGrid').hideColumn('linePeriodQuarter');
            }
            var headerRecord = $au('BGT5120_bgtJournalHeaderDs').getAt(0);
            if (headerRecord.get('journalHeaderId')) {
                $au('BGT5120_uploadBtn').enable();
                headerRecord.getField('bgtEntityName').setReadOnly(true);
                headerRecord.getField('bgtCenterName').setReadOnly(true);
            }

            /* add by liliang 2018/11/19 预算表定义中，预算中心和预算实体未勾选时，隐藏列 */
            //begin
            if ('$[/model/header/records/record/@entityFlag]' == 'N') {
                $au('BGT5120_journalLineGrid').hideColumn('bgtEntityName');
                $au('BGT5120_bgtJournalLineDs').fields.bgtEntityName.pro.required = false;
                $au('BGT5120_bgtJournalLineDs').fields.bgtEntityId.pro.required = false;
            }
            if ('$[/model/header/records/record/@centerFlag]' == 'N') {
                $au('BGT5120_journalLineGrid').hideColumn('bgtCenterName');
                $au('BGT5120_bgtJournalLineDs').fields.bgtCenterName.pro.required = false;
                $au('BGT5120_bgtJournalLineDs').fields.bgtCenterId.pro.required = false;
            }
            //end
        }

        function BGT5120_onLineCellClickFun(grid, row, name, record) {
            if (name == 'bgtEntityName') {
                if ('$[/model/header/records/record/@authorityType]' != 'ALL') {
                    lovCode = 'LOV_BGT_ENTITY_IN_JOURNAL';
                } else {
                    lovCode = 'LOV_BGT_ENTITY_ALL';
                }
                record.getField('bgtEntityName').setLovPara('bgtOrgId', '$[/model/header/records/record/@bgtOrgId]');
                record.getField('bgtEntityName').setLovPara('bgtJournalTypeId', '$[/model/header/records/record/@bgtJournalTypeId]');
                record.getField('bgtEntityName').setLovPara('authorityType', '$[/model/header/records/record/@authorityType]');
                record.getField('bgtEntityName').setLovCode(lovCode);
            } else if (name == 'bgtCenterName') {
                if (record.get('bgtEntityId') == null || record.get('bgtEntityId') == '' || record.get('bgtEntityId') == undefined) {
                    Aurora.showMessage('$[l:prompt]', '$[l:please_choice_bgt_entity_first]');
                    return false;
                } else {
                    if ('$[/model/header/records/record/@authorityType]' != 'ALL') {
                        lovCode = 'LOV_BGT_CENTER_IN_JOURNAL';
                    } else {
                        lovCode = 'LOV_BGT_CENTER_IN_ENTITY';
                    }
                    record.getField('bgtCenterName').setLovPara('bgtOrgId', '$[/model/header/records/record/@bgtOrgId]');
                    record.getField('bgtCenterName').setLovPara('bgtEntityId', record.get('bgtEntityId'));
                    record.getField('bgtCenterName').setLovPara('bgtJournalTypeId', '$[/model/header/records/record/@bgtJournalTypeId]');
                    record.getField('bgtCenterName').setLovPara('authorityType', '$[/model/header/records/record/@authorityType]');
                    record.getField('bgtCenterName').setLovCode(lovCode);
                }
            } else if (name == 'companyName') {
                //行上只能选择当前公司
                record.getField('companyName').setLovPara('companyId', '$[/session/@companyId]');
            } else if (name == 'unitName') {
                record.getField('unitName').setLovPara('companyId', record.get('companyId'));
            } else if (name == 'positionName') {
                record.getField('positionName').setLovPara('companyId', record.get('companyId'));
                record.getField('positionName').setLovPara('unitId', record.get('unitId'));
            } else if (name == 'employeeName') {
                record.getField('employeeName').setLovPara('companyId', record.get('companyId'));
                record.getField('employeeName').setLovPara('unitId', record.get('unitId'));
                record.getField('employeeName').setLovPara('positionId', record.get('positionId'));
            } else if (name == 'budgetItemName') {
                record.getField(name).setLovPara('bgtJournalTypeId', '$[/model/header/records/record/@bgtJournalTypeId]');
            }
            if (/dimension/.test(name)) {
                // Modify Tagin/2018.03.27 增加维度设置查询条件
                var id = '';
                var fieldName = name.replace('name', 'level');
                var level = record.get(fieldName);
                if (level == 'BUDGET') {
                    id = '$[/parameter/@bgtOrgId]';
                    record.getField(name).setLovPara('companyId', id);
                    record.getField(name).setLovPara('companyLevel', level);
                } else if (level == 'ACCOUNTING') {
                    record.getField(name).setLovPara('companyLevel', level);
                } else if (level == 'MANAGEMENT') {
                    id = '$[/session/@companyId]';
                    record.getField(name).setLovPara('companyId', id);
                    record.getField(name).setLovPara('companyLevel', level);
                }
            }
        }

        function BGT5120_onLineUpdateFun(ds, record, name, value, oldValue) {
            if (name == 'bgtEntityId') {
                record.set('bgtCenterName', '');
                record.set('bgtCenterId', '');
            } else if (name == 'companyId') {
                record.set('unitName', '');
                record.set('unitId', '');
            } else if (name == 'unitId') {
                record.set('positionName', '');
                record.set('positionId', '');
            } else if (name == 'positionId') {
                record.set('employeeName', '');
                record.set('employeeId', '');
            } else if (name == 'amount') {
                record.set('functionalAmount', value);
            }
        }

        function BGT5120_onHeaderUpdate(ds, record, name, value, oldValue) {
            if (name == 'dimension21_id') {
                record.set('dimension22_id', '');
                record.set('dimension22_name', '');
            } else if (name == 'dimension22_id') {
                record.set('dimension23_id', '');
                record.set('dimension23_name', '');
            } else if (name == 'dimension23_id') {
                record.set('dimension24_id', '');
                record.set('dimension24_name', '');
            }
        }

        function BGT5120_save() {
            var headerDs = $au('BGT5120_bgtJournalHeaderDs');
            var headerRecord = headerDs.getAll()[0];
            if (!headerDs.validate()) {
                return false;
            } else {
                var record = headerDs.getAll()[0];
                record.dirty = true;
                var expObjDatas = [];
                for (field in headerDs.fields) {
                    if (/^#/.test(field)) {
                        //id值
                        var expObjTypeId = field.replace(/^#/, '');
                        var idValue = record.get(field);
                        var nameValue = record.get('^' + field);
                        var expObjData = {
                            expense_object_type_id: expObjTypeId,
                            expense_object_id: idValue,
                            expense_object_name: nameValue
                        };
                        expObjDatas.push(expObjData);
                    }
                }
                record['expenseObjectHeaders'] = expObjDatas;
            }
            var lineDs = $au('BGT5120_bgtJournalLineDs');
            var lineRecords = lineDs.getAll();
            if (!lineRecords || lineRecords.length == 0) {
                Aurora.showErrorMessage('$[l:hap.error]', '$[l:prompt.add_line_date]');
                return;
            }
            for (var i = 0; i < lineRecords.length; i++) {
                var expObjDatas = [];
                for (field in lineDs.fields) {
                    if (/^#/.test(field)) {
                        //id字段
                        var expObjTypeId = lineRecords[i].get('expenseObjectTypeId');
                        var idValue = lineRecords[i].get(field);
                        var nameValue = lineRecords[i].get('^' + field);
                        var expObjData = {
                            expenseObjectTypeId: expObjTypeId,
                            expenseObjectId: idValue,
                            expenseObjectName: nameValue
                        };
                        expObjDatas.push(expObjData);
                    }
                }
                lineRecords[i]['expenseObjectLines'] = expObjDatas;
            }
            $au('BGT5120_bgtJournalHeaderDs').submit();
        }


        function BGT5120_onHeaderSubmitSuccess() {
            debugger;
            var lineDs = $au('BGT5120_bgtJournalLineDs');
            var lineRecords = lineDs.getAll();
            if (lineRecords.length != 0) {
                Ext.get('saveBgtDiv').parent().setStyle('display', 'table-cell');
            }


            var headerRecord = $au('BGT5120_bgtJournalHeaderDs').getAt(0);
            if (headerRecord.get('journalHeaderId')) {
                $au('BGT5120_uploadBtn').enable();
                headerRecord.getField('bgtEntityName').setReadOnly(true);
                headerRecord.getField('bgtCenterName').setReadOnly(true);
            }

            lineDs.setQueryUrl('$[/request/@context_path]/bgt/journal-line/query?journalHeaderId=' + headerRecord.get('journalHeaderId'));
            lineDs.query();

            if (submitFlag) {
                BGT5120_submit();
                submitFlag = false;
            }

        }

        function BGT5120_onLineLoadSuccess() {
            //
        }

        function BGT5120_submit() {
            debugger;
            //预算日记账未保存首先执行保存
            if (!submitFlag) {
                BGT5120_save();
                 submitFlag = true;
                 return;
             }
             submitFlag = false;
            var params = [];
            params[0] = {};
            params[0]['journalHeaderId'] = $au('BGT5120_bgtJournalHeaderDs').getAt(0).get('journalHeaderId');
            Aurora.request({
                lockMessage: '$[l:hap.handle_waiting]',
                url: $au('BGT5120_set_bgt_journal_status_link').getUrl()+'/SUBMIT/',
                para:  params,
                success: function () {
                    //BGT5120_closeJournal();
                    //注释&add 提交之后，应该跳转到我的预算日记账主页面，并刷新 by chao.dai 2018-09-27
                    window.location.href = $au('BGT5120_bgt_journal_main_link').getUrl();
                }
            });
        }

        function BGT5120_upload() {
            new Aurora.Window({
                url: $au('BGT5120_uploadFile_link').getUrl() + '?tableName=BGT_JOURNAL_HEADER&tablePkValue=' + $au('BGT5120_bgtJournalHeaderDs').getAt(0).get('journalHeaderId'),
                title: '$[l:hap_attachment_documentation]',
                width: 600,
                height: 400
            });
        }

        function BGT5120_headerDimensionFunc() {
            // Modify Y.duan/2018-7-26 15:34:21 增加维度设置查询条件 this.para.companyLevel
            //Modify Y.duan/2018-8-2 11:28:10 增加预置维度维值选择逻辑
            var record = $au('BGT5120_bgtJournalHeaderDs').getAt(0);
            var field_name = this.binder.name;
            if (field_name == 'dimension21_name') {
                //只能选择当前公司
                record.getField(this.binder.name).setLovPara('companyId', '$[/session/@companyId]');
            } else if (field_name == 'dimension22_name') {
                //部门Lov设置公司限制
                record.getField(this.binder.name).setLovPara('companyId', record.get('dimension21_id'));
            } else if (field_name == 'dimension23_name') {
                //岗位Lov设置公司、部门限制
                record.getField(this.binder.name).setLovPara('companyId', record.get('dimension21_id'));
                record.getField(this.binder.name).setLovPara('unitId', record.get('dimension22_id'));
            } else if (field_name == 'dimension24_name') {
                //员工Lov设置公司、部门、岗位限制
                record.getField(this.binder.name).setLovPara('companyId', record.get('dimension21_id'));
                record.getField(this.binder.name).setLovPara('unitId', record.get('dimension22_id'));
                record.getField(this.binder.name).setLovPara('positionId', record.get('dimension23_id'));
            } else {
                var id = '';
                var level = this.para.companyLevel;
                if (level == 'BUDGET') {
                    id = '$[/parameter/@bgtOrgId]';
                    record.getField(this.binder.name).setLovPara('companyId', id);
                    record.getField(this.binder.name).setLovPara('companyLevel', level);
                } else if (level == 'ACCOUNTING') {
                    record.getField(this.binder.name).setLovPara('companyLevel', level);
                } else if (level == 'MANAGEMENT') {
                    id = '$[/session/@companyId]';
                    record.getField(this.binder.name).setLovPara('companyId', id);
                    record.getField(this.binder.name).setLovPara('companyLevel', level);
                }
            }
        }

        //added by zealot at 20181121 单据提交前工作流预览
        function BGT5120_wflPreview() {
            var headerRecord = $au('BGT5120_bgtJournalHeaderDs').getAt(0);
            var transaction_type_id = headerRecord.get('bgtJournalTypeId');
            var companyId = headerRecord.get('companyId');
            var instance_param = headerRecord.get('journalHeaderId');
            if (instance_param) {
                new Aurora.Window({
                    url: $au('BGT5120_workflow_preview_before_submit_link').getUrl() + '?transactionCategory=BUDGET_JOURNAL&transactionTypeId=' + transaction_type_id + '&companyId=' + companyId + '&instance_param=' + instance_param,
                    id: 'BGT5120_workflow_preview_before_submit_window',
                    title: '$[l:wfl.preview]',
                    fullScreen: true
                });
            }
        }

        Aurora.onReady(BGT5120_initFun);
        ]]></script>
        <a:dataSets>
            <a:dataSet id="BGT5120_bgtVersionDs" autoQuery="true" fetchAll="true"
                       queryUrl="$[/request/@context_path]/bgt/version/query?bgtOrgId=$[/model/header/records/record/@bgtOrgId]&amp;bgtJournalTypeId=$[/model/header/records/record/@bgtJournalTypeId]"/>
            <a:dataSet id="BGT5120_bgtScenarioDs" autoQuery="true" fetchAll="true"
                       queryUrl="$[/request/@context_path]/bgt/scenario/query?bgtOrgId=$[/model/header/records/record/@bgtOrgId]&amp;bgtJournalTypeId=$[/model/header/records/record/@bgtJournalTypeId]"/>
            <a:dataSet id="BGT5120_bgtPeriodDs" autoQuery="true" fetchAll="true"
                       queryUrl="$[/request/@context_path]/bgt/period/query?bgtOrgId=$[/model/header/records/record/@bgtOrgId]"/>
            <a:dataSet id="BGT5120_currencyDs" autoQuery="true"
                       queryUrl="$[/request/@context_path]/gld-currency/query"/>
            <a:dataSet id="BGT5120_exchangeRateTypeDs" autoQuery="true"
                       queryUrl="$[/request/@context_path]/gld/exchangerate-type/query"/>
            <a:dataSet id="BGT5120_bgtJournalHeaderDs"
                       baseUrl="/bgt/journal-header">
                <a:datas dataSource="header/records"/>
                <a:fields>
                    <a:field name="journalHeaderId"/>
                    <a:field name="budgetJournalNumber" prompt="bgt_journal_header.budget_journal_number"
                             readOnly="true"/>
                    <a:field name="bgtJournalTypeName" prompt="bgt_journal_header.bgt_journal_type" readOnly="true"/>
                    <a:field name="structureName" prompt="bgt_journal_header.bgt_structure" readOnly="true"/>
                    <a:field name="periodStrategyName" prompt="bgt_journal_header.period_strategy"
                             readOnly="true"/>
                    <a:field name="bgtEntityName"
                             lovService="hec_util.bgt_entities_vl_lov?bgtOrgId=$[/model/header/records/record/@bgtOrgId]"
                             prompt="bgt_journal_header.bgt_entity">
                        <a:mapping>
                            <a:map from="bgtEntityName" to="bgtEntityName"/>
                            <a:map from="bgtEntityId" to="bgtEntityId"/>
                        </a:mapping>
                    </a:field>
                    <a:field name="bgtCenterName"
                             lovService="hec_util.bgt_centers_entities_vl_lov?bgtOrgId=$[/model/header/records/record/@bgtOrgId]"
                             prompt="bgt_journal_header.bgt_center">
                        <a:mapping>
                            <a:map from="bgtCenterName" to="bgtCenterName"/>
                            <a:map from="bgtCenterId" to="bgtCenterId"/>
                        </a:mapping>
                    </a:field>
                    <a:field name="scenarioName" displayField="description" options="BGT5120_bgtScenarioDs"
                             prompt="bgt_journal_header.bgt_scenario" required="true" returnField="scenarioId"
                             valueField="scenarioId"/>
                    <a:field name="scenarioId"/>
                    <a:field name="versionName" displayField="description" options="BGT5120_bgtVersionDs"
                             prompt="bgt_journal_header.bgt_version" required="true" returnField="versionId"
                             valueField="versionId"/>
                    <a:field name="versionId"/>
                    <a:field name="statusName" prompt="bgt_journal_header.status" readOnly="true"/>
                    <a:field name="journalHeaderNotes" prompt="bgt_journal_header.journal_header_notes"/>
                    <a:field name="structureId"/>
                    <#list headerDimList as headerDim>
                        <a:field name="${headerDim.display_field}" defaultValue="${headerDim.default_dim_value_name}"
                                 lovCode="${headerDim.lovCode}"
                                 prompt="${headerDim.prompt}"
                                 required="true" title="${headerDim.title}">
                            <a:mapping>
                                <a:map from="${headerDim.fromName}" to="${headerDim.display_field}"/>
                                <a:map from="${headerDim.fromId}" to="${headerDim.return_field}"/>
                            </a:mapping>
                        </a:field>
                        <a:field name="${headerDim.return_field}" defaultValue="${headerDim.default_dim_value_id}"/>
                    </#list>
                    <#list headerObjList as headerObj>
                        <a:field name="${headerObj.display_field}"
                                 defaultValue="${headerObj.dft_mo_expense_object_name}"
                                 lovCode="LOV_EXP_MO_EXPENSE_OBJECT_VALUE?moExpObjTypeId=${headerObj.mo_exp_obj_type_id}"
                                 prompt="${headerObj.mo_exp_obj_type_name}" required="${headerObj.required_flag}"
                                 title="${headerObj.mo_exp_obj_type_name}">
                            <a:mapping>
                                <a:map from="name" to="${headerObj.display_field}"/>
                                <a:map from="id" to="${headerObj.return_field}"/>
                            </a:mapping>
                        </a:field>
                        <a:field name="${headerObj.return_field}" defaultValue="${headerObj.dft_mo_expense_object_id}"/>
                    </#list>
                </a:fields>
                <a:events>
                    <a:event name="submitsuccess" handler="BGT5120_onHeaderSubmitSuccess"/>
                    <a:event name="update" handler="BGT5120_onHeaderUpdate"/>
                    <#--<a:event name="loadsuccess" handler="BGT5120_onHeaderLoadSuccess"/>-->
                </a:events>
            </a:dataSet>
            <a:dataSet id="BGT5120_bgtJournalLineDs" autoPageSize="true" autoQuery="true" bindName="journalLines"
                       bindTarget="BGT5120_bgtJournalHeaderDs" baseUrl="/bgt/journal-line"
                       queryUrl="$[/request/@context_path]/bgt/journal-line/query?journalHeaderId=$[/model/header/records/record/@journalHeaderId]"
                       selectable="true">
                <a:fields>
                    <a:field name="bgtJournalLineId"/>
                    <a:field name="bgtEntityName" prompt="bgt_journal_line.bgt_entity" required="true"
                             title="exp_org_unit.choice_bgt_entity_display">
                        <a:mapping>
                            <a:map from="bgtEntityName" to="bgtEntityName"/>
                            <a:map from="bgtEntityId" to="bgtEntityId"/>
                        </a:mapping>
                    </a:field>
                    <a:field name="bgtEntityId"/>
                    <a:field name="bgtCenterName" prompt="bgt_journal_line.bgt_center" required="true"
                             title="exp_org_unit.choice_bgt_center_display">
                        <a:mapping>
                            <a:map from="bgtCenterName" to="bgtCenterName"/>
                            <a:map from="bgtCenterId" to="bgtCenterId"/>
                        </a:mapping>
                    </a:field>
                    <a:field name="bgtCenterId"/>
                    <a:field name="budgetItemName"
                             lovCode="LOV_BUDGET_ITEM_IN_BGT_ORG?bgtOrgId=$[/model/header/records/record/@bgtOrgId]&amp;summary_flag=N"
                             prompt="bgt_journal_line.budget_item" required="true"
                             title="bgt_budget_item.title">
                        <a:mapping>
                            <a:map from="budgetItemName" to="budgetItemName"/>
                            <a:map from="budgetItemId" to="budgetItemId"/>
                        </a:mapping>
                    </a:field>
                    <a:field name="budgetItemId"/>
                    <a:field name="linePeriodName"
                             lovCode="LOV_BGT_PERIOD_IN_JOURNAL?bgtOrgId=$[/model/header/records/record/@bgtOrgId]&amp;structureId=$[/model/header/records/record/@structureId]"
                             prompt="bgt_journal_line.period_name" title="select_business_period">
                        <a:mapping>
                            <a:map from="periodName" to="linePeriodName"/>
                            <a:map from="periodYear" to="linePeriodYear"/>
                            <a:map from="quarterNum" to="linePeriodQuarter"/>
                            <a:map from="periodNum" to="lineBgtPeriodNum"/>
                        </a:mapping>
                    </a:field>
                    <a:field name="linePeriodQuarter" prompt="bgt_journal_line.period_quarter"/>
                    <a:field name="linePeriodYear" prompt="bgt_journal_line.period_year"/>
                    <a:field name="lineBgtPeriodNum" prompt="bgt_journal_line.bgt_period_num"/>
                    <a:field name="currencyName" displayField="currencyName" options="BGT5120_currencyDs"
                             prompt="bgt_journal_line.currency" required="true" returnField="currency"
                             valueField="currencyCode"/>
                    <a:field name="currency"/>
                    <a:field name="exchangeRate" prompt="bgt_journal_line.exchange_rate"/>
                    <a:field name="amount" prompt="bgt_journal_line.amount" required="true"/>
                    <a:field name="functionalAmount" prompt="bgt_journal_line.functional_amount"/>
                    <a:field name="quantity" prompt="bgt_journal_line.quantity" required="true"/>
                    <a:field name="journalLinesNotes" prompt="bgt_journal_line.journal_line_notes"/>
                    <#list lineDimList as lineDim>
                        <a:field name="${lineDim.display_field}" defaultValue="${lineDim.default_dim_value_name}"
                                 lovCode="${lineDim.lovCode}"
                                 prompt="${lineDim.prompt}"
                                 required="true" title="${lineDim.title}">
                            <a:mapping>
                                <a:map from="${lineDim.fromName}" to="${lineDim.display_field}"/>
                                <a:map from="${lineDim.fromId}" to="${lineDim.return_field}"/>
                            </a:mapping>
                        </a:field>
                        <a:field name="${lineDim.return_field}" defaultValue="${lineDim.default_dim_value_id}"/>
                    </#list>
                    <#list lineObjList as lineObj>
                        <a:field name="${lineObj.display_field}"
                                 defaultValue="${lineObj.dft_mo_expense_object_name}"
                                 lovCode="LOV_EXP_MO_EXPENSE_OBJECT_VALUE?moExpObjTypeId=${lineObj.mo_exp_obj_type_id}"
                                 prompt="${lineObj.mo_exp_obj_type_name}" required="${lineObj.required_flag}"
                                 title="${lineObj.mo_exp_obj_type_name}">
                            <a:mapping>
                                <a:map from="name" to="${lineObj.display_field}"/>
                                <a:map from="id" to="${lineObj.return_field}"/>
                            </a:mapping>
                        </a:field>
                        <a:field name="${lineObj.return_field}" defaultValue="${lineObj.dft_mo_expense_object_id}"/>
                    </#list>
                </a:fields>
                <a:events>
                    <a:event name="update" handler="BGT5120_onLineUpdateFun"/>
                    <a:event name="loadsuccess" handler="BGT5120_onLineLoadSuccess"/>
                </a:events>
            </a:dataSet>
        </a:dataSets>
        <a:screenBody>
            <a:form column="1" title="$[/model/header/records/record/@bgtJournalTypeName]" style="width:100%;">
                <a:formToolbar>
                    <a:label name="separator"/>
                    <a:button click="BGT5120_save" text="hap_save_draft" width="80"/>
                    <a:hBox id="saveBgtDiv" width="160">
                        <a:button click="BGT5120_submit" text="hap_submit_approval" width="80"/>
                        <a:button id="BGT5120_wflPreviewBtn" click="BGT5120_wflPreview" text="wfl.preview" width="80"/>
                        <a:button id="BGT5120_uploadBtn" click="BGT5120_upload" disabled="true"
                                  text="hap_attachment_documentation" width="80"/>
                    </a:hBox>
                    <a:button click="BGT5120_closeJournal" text="hap_back" width="80"/>
                </a:formToolbar>
                <a:box column="4" labelWidth="110" style="width:100%;">
                    <a:textField name="budgetJournalNumber" bindTarget="BGT5120_bgtJournalHeaderDs"/>
                    <a:textField name="bgtJournalTypeName" bindTarget="BGT5120_bgtJournalHeaderDs"/>
                    <a:textField name="structureName" bindTarget="BGT5120_bgtJournalHeaderDs"/>
                    <a:textField name="periodStrategyName" bindTarget="BGT5120_bgtJournalHeaderDs"/>
                    <a:comboBox name="scenarioName" bindTarget="BGT5120_bgtJournalHeaderDs"/>
                    <a:comboBox name="versionName" bindTarget="BGT5120_bgtJournalHeaderDs"/>
                    <a:textField name="statusName" bindTarget="BGT5120_bgtJournalHeaderDs"/>
                    <#list headerDimList as headerDim>
                        <a:lov name="${headerDim.display_field}" bindTarget="BGT5120_bgtJournalHeaderDs">
                            <a:events>
                                <a:event name="focus" handler="BGT5120_headerDimensionFunc"/>
                            </a:events>
                        </a:lov>
                    </#list>
                    <#list headerObjList as headerObj>
                        <#if headerObj.expense_object_method == 'DESC_TEXT'>
                            <a:textField name="${headerObj.display_field}" bindTarget="BGT5120_bgtJournalHeaderDs"/>
                        <#else>
                            <a:lov name="${headerObj.display_field}" bindTarget="BGT5120_bgtJournalHeaderDs"/>
                        </#if>
                    </#list>
                </a:box>
                <a:box labelWidth="110" style="width:100%;margin-top:0px;">
                    <a:textArea name="journalHeaderNotes" bindTarget="BGT5120_bgtJournalHeaderDs"
                                calcWidth="calc((100%  + 90px) / 4 * 3 + 150px)"/>
                </a:box>
            </a:form>
            <a:grid id="BGT5120_journalLineGrid" autoAdjustHeight="true" bindTarget="BGT5120_bgtJournalLineDs"
                    marginHeight="191" marginWidth="16" navBar="true">
                <a:toolBar>
                    <a:button click="BGT5120_journalLineAdd" type="add"/>
                    <a:button type="delete"/>
                </a:toolBar>
                <a:columns>
                    <a:column name="journalLinesNotes" align="left" editor="BGT5120_journalTa" width="150"/>
                    <a:column name="bgtEntityName" align="center" editor="BGT5120_journalLov" width="120"/>
                    <a:column name="bgtCenterName" align="center" editor="BGT5120_journalLov" width="120"/>
                    <a:column name="budgetItemName" align="center" editor="BGT5120_journalLov" width="120"/>
                    <a:column name="linePeriodName" align="center" editor="BGT5120_journalLov" width="80"/>
                    <a:column name="linePeriodQuarter" align="right" editor="BGT5120_journalNf" width="80"/>
                    <a:column name="linePeriodYear" align="right" editor="BGT5120_journalNf" width="80"/>
                    <a:column name="currencyName" align="center" editor="BGT5120_journalCb"/>
                    <a:column name="amount" align="right" editor="BGT5120_journalNf" renderer="Aurora.formatMoney"/>
                    <a:column name="quantity" align="right" editor="BGT5120_journalNf" renderer="Aurora.formatNumber"/>
                    <#list lineDimList as lineDim>
                        <a:column name="${lineDim.display_field}" editor="BGT5120_journalLov"/>
                    </#list>
                    <#list lineObjList as lineObj>
                        <#if lineObj.expense_object_method == 'DESC_TEXT'>
                            <a:column name="${lineObj.display_field}" align="center" editor="BGT5120_journalTf"/>
                        <#else>
                            <a:column name="${lineObj.display_field}" align="center" editor="BGT5120_journalLov"/>
                        </#if>
                    </#list>
                </a:columns>
                <a:editors>
                    <a:textArea id="BGT5120_journalTa"/>
                    <a:textField id="BGT5120_journalTf"/>
                    <a:comboBox id="BGT5120_journalCb"/>
                    <a:numberField id="BGT5120_journalNf"/>
                    <a:lov id="BGT5120_journalLov"/>
                </a:editors>
                <a:events>
                    <a:event name="cellclick" handler="BGT5120_onLineCellClickFun"/>
                </a:events>
            </a:grid>
        </a:screenBody>
    </a:view>
</a:screen>
