<?xml version="1.0" encoding="UTF-8"?>
<a:screen xmlns:c="aurora.application.action" xmlns:p="uncertain.proc"
          xmlns:a="http://www.aurora-framework.org/application" xmlns:s="aurora.plugin.script" trace="true">
    <a:view>
        <a:link id="EXP5110_moreInvoiceTaxLink"
                url="$[/request/@context_path]/expm/EXP5110/exp_report_maintain_vat_invoice_more_tax.screen"/>
        <script><![CDATA[
            function EXP5110_onstandardEditorFocus(cmp) {
                var name = cmp.binder.name;
                var record = cmp.binder.ds.getCurrentRecord();
                var positionId = record.get('positionId');
                if (name == 'unitName') {
                    var employeeId = record.get('employeeId');
                    if (!Ext.isEmpty(employeeId)) {
                        record.getField('unitName').setLovPara('employeeId', record.get('employeeId'));
                        record.getField('unitName').setLovPara('companyId', record.get('companyId'));
                        record.getField('unitName').setLovCode('EXP_REP_EM_UNIT_LOV');
                    } else {
                        record.getField('unitName').setLovPara('companyId', record.get('companyId'));
                        // 新增核算主体Id查询条件 caoke 2018-1-25 14:38:04
                        record.getField('unitName').setLovPara('accEntityId', record.get('accEntityId'));
                        record.getField('unitName').setLovCode('EXP_ORG_UNITS_VL_LOV');
                    }
                } else if (name == 'positionName') {
                    record.getField('positionName').setLovPara('companyId', record.get('companyId'));
                    record.getField('positionName').setLovPara('employeeId', record.get('employeeId'));
                    record.getField('positionName').setLovPara('unitId', record.get('unitId'));
                } else if (name == 'employeeName') {
                    record.getField('employeeName').setLovPara('companyId', record.get('companyId'));
                    if (!Ext.isEmpty(positionId)) {
                        record.getField('employeeName').setLovPara('positionId', record.get('positionId'));
                        record.getField('employeeName').setLovPara('primaryPositionFlag', null);
                    } else {
                        record.getField('employeeName').setLovPara('positionId', null);
                        record.getField('employeeName').setLovPara('primaryPositionFlag', 'Y');
                    }
                } else if (name == 'accEntityName') {
                    record.getField('accEntityName').setLovPara('companyId', record.get('companyId'));
                } else if (name == 'respCenterName') {
                    record.getField('respCenterName').setLovPara('accEntityId', record.get('accEntityId'));
                } else if (name == 'placeName') {
                    record.getField('placeName').setLovPara('companyId', record.get('companyId'));
                } else if (name == 'moExpenseTypeName') {
                    $au('EXP5110_standard_expense_type_ds').setQueryParameter('companyId', record.get('companyId'));
                    $au('EXP5110_standard_expense_type_ds').query();
                } else if (name == 'moExpenseItemName') {
                    record.getField('moExpenseItemName').setLovPara('moExpenseTypeId', record.get('moExpenseTypeId'));
                    record.getField('moExpenseItemName').setLovPara('companyId', record.get('companyId'));
                    record.getField('moExpenseItemName').setLovPara('moExpReportTypeId', '$[/parameter/@moExpReportTypeId]');
                    record.getField('moExpenseItemName').setLovPara('pageElementCode', 'STANDARD');
                } else if (name == 'moExpenseTypeName') {
                    $au('EXP5110_standard_expense_type_ds').setQueryParameter('companyId', record.get('companyId'));
                } else if (name == 'invoiceItemName' || name == 'invoiceUsedeName') {
                    record.getField(name).setLovPara('accEntityId', $au('EXP5110_exp_report_header_ds').getAt(0).get('accEntityId'));
                }
                if (/dimension/.test(name)) { // 设置维度查询条件【Tips：若涉及多维度则需要判断维度对应的级别，不同级别对应的组织架构不同】
                    var id = '';
                    var fieldName = name.replace('name', 'Level');
                    var level = record.get(fieldName);
                    if (level == 'BUDGET') {
                        id = record.get('bgtEntityId');
                    } else if (level == 'ACCOUNTING') {
                        id = record.get('accEntityId');
                    } else if (level == 'MANAGEMENT') {
                        id = '$[/session/@companyId]';
                    }
                    record.getField(name).setLovPara('companyId', id);
                }
            }

            function EXP5110_standardPreRecord() {
                $au('EXP5110_exp_report_standard_line_ds').pre();
            }

            function EXP5110_standardNextRecord() {
                $au('EXP5110_exp_report_standard_line_ds').next();
            }

            function EXP5110_standardNewRecord() {
                EXP5110_standardLineAdd();
            }

            function EXP5110_standardExtendClose() {
                $au(this.__host.id).close();
            }

            function EXP5110_standardSaveReport() {
                EXP5110_saveReport();
            }

            Aurora.onReady(function () {
                var headerRecord = $au('EXP5110_exp_report_header_ds').getAt(0);
                var dimFlag = false;
                var objFlag = false;
                for (cmpName in $A.CmpManager.cache) {
                    if (cmpName.indexOf('EXP5110_editor_') != -1) {
                        $au(cmpName)['on']('focus', EXP5110_onstandardEditorFocus);
                    }
                    if (cmpName.indexOf('EXP5110_editor_dim') != -1) {
                        $au(cmpName)['on']('focus', EXP5110_onstandardEditorFocus);
                        dimFlag = true;
                    }
                    if (cmpName.indexOf('EXP5110_editor_obj') != -1) {
                        objFlag = true;
                    }
                }
                if (!dimFlag) {
                    Ext.get('EXP5110_standardDimForm').setStyle('display', 'none');
                    Ext.get('EXP5110_standardDimForm').parent().setStyle('padding', '0px');
                }
                if (!objFlag) {
                    Ext.get('EXP5110_standardObjForm').setStyle('display', 'none');
                    Ext.get('EXP5110_standardObjForm').parent().setStyle('padding', '0px');
                }

                // Modify Tagin/2018.05.14 设置更多发票Form
                EXP5110_standardMoreInvoiceInitFunc($au('EXP5110_stardardMoreInvoiceGrid'));
            });

            function EXP5110_onItemNameFocus() {
                var record = $au('EXP5110_exp_report_standard_line_ds').getCurrentRecord();
                $au('EXP5110_standard_items_ds').setQueryParameter('moExpenseTypeId', record.get('moExpenseTypeId'));
                $au('EXP5110_standard_items_ds').setQueryParameter('companyId', record.get('companyId'));
                $au('EXP5110_standard_items_ds').setQueryParameter('moExpReportTypeId', $au('EXP5110_exp_report_header_ds').getAt(0).get('moExpReportTypeId'));
                $au('EXP5110_standard_items_ds').setQueryParameter('pageElementCode', 'STANDARD');
                $au('EXP5110_standard_items_ds').query();
            }

            function EXP5110_onMoreInvoiceCellClickFun(grid, row, name, record) {
                if (name == 'invoiceCode') {
                    record.getField(name).setLovPara('sourceDataset', 'EXP5110_moreInvoiceResultDs');
                    record.getField(name).setLovPara('sourcePage', 'moreInvoice');
                    record.getField(name).setLovPara('relationId', record.get('relationId'));
                    record.getField(name).setLovPara('invoiceId', record.get('invoiceId'));
                    record.getField(name).setLovPara('invoiceCategoryId', record.get('invoiceCategoryId'));
                    record.getField(name).setLovPara('expReportLineId', record.get('expReportLineId'));
                    record.getField(name).setLovWidth(parseInt(Aurora.getCookie('vw')) + 25);
                    record.getField(name).setLovHeight(parseInt(Aurora.getCookie('vh')));
                    record.getField(name).setTitle('$[l:invoice_select]');
                }
            }

            function EXP5110_standardMoreInvoiceInitFunc(grid) {
                var hRecord = $au('EXP5110_exp_report_header_ds').getAt(0);
                if (('$[/parameter/@invoiceFlag]' == 'N' && '$[/parameter/@taxFlag]' == 'N') || hRecord.get('vatInvoiceFlag') == 'N') {
                    Ext.get('EXP5110_standardMoreInvoice_form').setStyle('display', 'none');
                }
                if ('$[/parameter/@invoiceFlag]' == 'N') {
                    grid.hideColumn('invoiceCategoryName');
                    grid.hideColumn('invoiceCode');
                    grid.hideColumn('invoiceNumber');
                    grid.hideColumn('invoiceDate');
                    grid.hideColumn('invoiceAmount');
                    grid.hideColumn('invoiceLineNumber');
                    grid.hideColumn('invoiceLineAmount');
                } else {
                    grid.showColumn('invoiceCategoryName');
                    grid.showColumn('invoiceCode');
                    grid.showColumn('invoiceNumber');
                    grid.showColumn('invoiceDate');
                    grid.showColumn('invoiceAmount');
                    grid.showColumn('invoiceLineNumber');
                    grid.showColumn('invoiceLineAmount');
                }
                if ('$[/parameter/@taxFlag]' == 'N') {
                    grid.hideColumn('taxTypeName');
                    grid.hideColumn('taxAmount');
                } else {
                    grid.showColumn('taxTypeName');
                    grid.showColumn('taxAmount');
                }
            }

            function EXP5110_moreInvoiceEditorFun(record, name) {
                var invoiceSource = record.get('invoiceSource');
                var invoiceType = record.get('invoiceType');
                var relationId = record.get('relationId');
                if (name == 'invoiceCategoryName') {
                    return 'EXP5110_standard_dist_cb';
                } else if (name == 'invoiceCode') {
                    if (record.get('invoiceCategoryId')) {
                        return 'EXP5110_standard_dist_lov';
                    }
                } else if (name == 'invoiceNumber') {
                    if (invoiceSource == 'MANUAL' && !relationId) {
                        return 'EXP5110_standard_dist_tf';
                    }
                } else if (name == 'invoiceDate') {
                    if (invoiceSource == 'MANUAL' && !relationId) {
                        return 'EXP5110_standard_dist_dp';
                    }
                } else if (name == 'invoiceAmount') {
                    if (invoiceSource == 'MANUAL' && !relationId) {
                        return 'EXP5110_standard_dist_nf_2';
                    }
                } else if (name == 'taxTypeName') {
                    if (invoiceSource == 'MANUAL' && !relationId) {
                        return 'EXP5110_standard_dist_cb';
                    }
                } else if (name == 'taxAmount') {
                    if (invoiceSource == 'MANUAL' && !relationId) {
                        return 'EXP5110_standard_dist_nf_2';
                    }
                } else if (name == 'businessAmount') {
                    return 'EXP5110_standard_dist_nf_2';
                }
                return '';
            }

            function EXP5110_moreInvoiceTypeLoadFunc(dataSet) {
                // Modify Tagin/2018.04.10 增加根据发票代码获取发票类型加载时动态设置单据行的发票类型字段
                var record = dataSet.getAt(0);
                var reportRd = $au('EXP5110_moreInvoiceResultDs').getCurrentRecord();
                var invoiceType = record.get('invoiceType');
                if (reportRd.get('invoiceCode')) {
                    reportRd.set('invoiceSource', !reportRd.get('invoiceSource') ? 'MANUAL' : reportRd.get('invoiceSource'));
                    reportRd.set('invoiceType', invoiceType);
                } else {
                    reportRd.set('invoiceType', '');
                }
            }

            // Modify Tagin/2018.05.11 若单据行变更，则切换更多发票的信息
            $au('EXP5110_exp_report_standard_line_ds')['on']('indexchange', function (dataSet, record) {
                var id = record.get('expReportLineId');
                if (id && $A.CmpManager.get('EXP5110_moreInvoiceResultDs')) {
                    var moreDs = $au('EXP5110_moreInvoiceResultDs');
                    moreDs.setQueryParameter('expReportLineId', id);
                    moreDs.query();
                }
            });

            function EXP5110_onMoreInvoiceUpdateFun(ds, record, name, value, oldValue) {
                var hecUtil = new HecUtil();
                if (name == 'invoiceCategoryName') {
                    // 清空
                    record.set('invoiceType', '');
                    record.set('invoiceCode', '');
                    record.set('invoiceNumber', '');
                    record.set('invoiceDate', '');
                    record.set('invoiceAmount', '');
                    record.set('taxTypeName', '');
                    record.set('taxTypeId', '');
                    record.set('taxTypeRate', '');
                    record.set('taxAmount', '');
                    record.set('invoiceSource', '');
                } else if (name == 'taxTypeRate') {
                    if (record.get('invoiceAmount') && value) {
                        if (record.get('autoCalculationFlag') == 'Y') { // Tagin/2018.07.11 自动计算税额标识为Y则自动计算
                            record.set('taxAmount', hecUtil.calTaxAmount(record.get('invoiceAmount'), value, record.get('taxCategory'), record.get('businessCurrencyPrecision')));
                        } else {
                            record.set('taxAmount', '');
                        }
                    }
                } else if (name == 'invoiceAmount') {
                    if (record.get('taxTypeRate') && value) {
                        if (record.get('autoCalculationFlag') == 'Y') { // Tagin/2018.07.11 自动计算税额标识为Y则自动计算
                            record.set('taxAmount', hecUtil.calTaxAmount(value, record.get('taxTypeRate'), record.get('taxCategory'), record.get('businessCurrencyPrecision')));
                        } else {
                            record.set('taxAmount', '');
                        }
                    }
                } else if (name == 'businessAmount') {
                    if (record.get('biz2payExchangeRate') && value) {
                        record.set('paymentAmount', hecUtil.calExchangeAmount(value, record.get('biz2payExchangeRate'), record.get('paymentCurrencyPrecision')));
                    }
                } else if (name == 'paymentAmount') {
                    if (record.get('pay2funExchangeRate') && value) {
                        record.set('functionalAmount', hecUtil.calExchangeAmount(value, record.get('pay2funExchangeRate'), record.get('functionalCurrencyPrecision')));
                    }
                } else if (name == 'invoiceCode') {
                    // Modify Tagin/2018.04.10 根据发票代码获取发票类型
                    var dataSet = $au('EXP5110_moreInvoiceTypeDs');
                    dataSet.setQueryParameter('invoiceCode', value);
                    dataSet.query();
                } else if (name == 'invoiceType') {
                    // 设置字段必输
                    EXP5110_moreInvoiceRequired(record);
                }
            }

            function EXP5110_moreInvoiceRequired(record) {
                var value = record.get('invoiceType');
                if (value == 'VAT_SPECIAL') {
                    record.getField('invoiceCode').setRequired(true);
                    record.getField('invoiceNumber').setRequired(true);
                    record.getField('invoiceDate').setRequired(true);
                    record.getField('invoiceAmount').setRequired(true);
                    record.getField('taxTypeName').setRequired(true);
                    record.getField('taxAmount').setRequired(true); //发票金额
                } else if (value == 'VAT_NORMAL' || value == 'VAT_ELECTRONIC_NORMAL') {
                    record.getField('invoiceCode').setRequired(true);
                    record.getField('invoiceNumber').setRequired(true);
                    record.getField('invoiceDate').setRequired(true);
                    record.getField('invoiceAmount').setRequired(true);
                } else if (value == 'OTHER' || value == undefined || value == '') {
                    record.getField('invoiceCode').setRequired(false);
                    record.getField('invoiceNumber').setRequired(false);
                    record.getField('invoiceDate').setRequired(false);
                    record.getField('invoiceAmount').setRequired(false);
                    record.getField('taxTypeName').setRequired(false);
                    record.getField('taxAmount').setRequired(false); //发票金额
                }
            }

            function EXP5110_onMoreInvoiceSubmitFun(dataSet) {
                dataSet.query();
            }

            function EXP5110_moreInvoiceTaxRenderer(value, record, name) {
                var index = $au('EXP5110_moreInvoiceResultDs').indexOf(record);
                return '<a href="javascript:EXP5110_moreInvoiceTaxFunc(' + index + ')">$[l:vat_invoice_lines.tax_rate]</a>';
            }

            function EXP5110_moreInvoiceTaxFunc(index) {
                var record = $au('EXP5110_moreInvoiceResultDs').getAt(index);
                var invoiceLineId = record.get('invoiceLineId') || '';
                var invoiceId = record.get('invoiceId') || '';
                var relationId = record.get('relationId') || '';
                var lineId = record.get('expReportLineId') || '';
                new Aurora.Window({
                    id: 'EXP5110_moreInvoiceTaxWindow',
                    url: $au('EXP5110_moreInvoiceTaxLink').getUrl() + '?invoiceLineId=' + invoiceLineId + '&invoiceId=' + invoiceId + '&relationId=' + relationId + '&expReportLineId=' + lineId,
                    side: 'right',
                    width: 1020
                }).on('close', function () {
                    $au('EXP5110_moreInvoiceResultDs').query();
                });
            }

            function EXP5110_onMoreInvoiceAddFun(dataSet, record, index) {
                var lRecord = $au('EXP5110_exp_report_standard_line_ds').getCurrentRecord();
                record.set('biz2payExchangeRate', lRecord.get('biz2payExchangeRate'));
                record.set('pay2funExchangeRate', lRecord.get('pay2funExchangeRate'));
                record.set('businessCurrencyPrecision', lRecord.get('businessCurrencyPrecision'));
                record.set('paymentCurrencyPrecision', lRecord.get('paymentCurrencyPrecision'));
                record.set('functionalCurrencyPrecision', lRecord.get('functionalCurrencyPrecision'));
            }

            ]]></script>
        <a:dataSets>
            <a:dataSet id="EXP5110_moreInvoiceTypeDs" model="hec_util.get_invoice_type">
                <a:events>
                    <a:event name="load" handler="EXP5110_moreInvoiceTypeLoadFunc"/>
                </a:events>
            </a:dataSet>
            <a:dataSet id="EXP5110_moreInvoiceCategoryDs" fetchAll="false" loadData="true"/>
            <a:dataSet id="EXP5110_moreInvoiceTaxRateTypeDs" autoQuery="false" queryUrl="$[/request/@context_path]/exp/report-line/queryTaxTypeCode?withholdingFlag=N"/>
            <a:dataSet id="EXP5110_moreInvoiceResultDs" autoPageSize="true" autoQuery="false"  selectable="true">
                <a:fields>
                    <!-- Modify Tagin/2018.04.12 将发票类型调整为从发票代码自动获取 / 增加发票种类字段 -->
                    <a:field name="relationId"/>
                    <a:field name="expReportHeaderId" defaultValue="$[/parameter/@expReportHeaderId]"/>
                    <a:field name="expReportLineId" defaultValue="$[/parameter/@expReportLineId]"/>
                    <a:field name="invoiceCategoryName" displayField="invoiceCategoryName"
                             options="EXP5110_moreInvoiceCategoryDs" prompt="vat_invoice_items.invoice_category"
                             required="true" returnField="invoiceCategoryId" valueField="invoiceCategoryId"/>
                    <a:field name="invoiceCategoryId"/>
                    <a:field name="invoiceType"/>
                    <a:field name="invoiceCode" fetchRemote="false"
                             lovUrl="$[/request/@context_path]/modules/expm/EXP5110/exp_report_maintain_select_invoice.screen"
                             prompt="exp_report.invoice_code"/>
                    <a:field name="invoiceNumber" prompt="acp_invoice_header.invoice_number"/>
                    <a:field name="invoiceDate" prompt="vat_invoices.invoice_date"/>
                    <a:field name="invoiceAmount" prompt="vat_invoices.total_invoice_amount"/>
                    <a:field name="taxTypeName" displayField="taxTypeName" options="EXP5110_moreInvoiceTaxRateTypeDs"
                             prompt="exp_report_lines.tax_rate_type">
                        <a:mapping>
                            <a:map from="taxTypeId" to="taxTypeId"/>
                            <a:map from="taxCategory" to="taxCategory"/>
                            <a:map from="autoCalculationFlag" to="autoCalculationFlag"/>
                            <a:map from="taxTypeRate" to="taxTypeRate"/>
                        </a:mapping>
                    </a:field>
                    <a:field name="taxTypeId"/>
                    <a:field name="taxTypeRate"/>
                    <a:field name="taxAmount" prompt="vat_invoices.invoice_line_tax_amount"/>
                    <a:field name="invoiceSource" defaultValue="MANUAL"/>
                    <a:field name="businessAmount" prompt="exp_report.actual_reimbursement_amount" required="true"/>
                    <a:field name="biz2payExchangeRate"/>
                    <a:field name="paymentAmount"/>
                    <a:field name="pay2funExchangeRate"/>
                    <a:field name="functionalAmount"/>
                    <a:field name="businessCurrencyPrecision"/>
                    <a:field name="paymentCurrencyPrecision"/>
                    <a:field name="functionalCurrencyPrecision"/>
                    <a:field name="autoCalculationFlag"/>
                    <a:field name="taxCategory"/>
                    <a:field name="moreTax"/>
                </a:fields>
                <a:events>
                    <a:event name="add" handler="EXP5110_onMoreInvoiceAddFun"/>
                    <a:event name="update" handler="EXP5110_onMoreInvoiceUpdateFun"/>
                    <a:event name="submitsuccess" handler="EXP5110_onMoreInvoiceSubmitFun"/>
                </a:events>
            </a:dataSet>
        </a:dataSets>
        <a:screenBody>
            <a:form id="EXP5110_standard_line_extend_form" column="3" labelWidth="120" padding="6" showmargin="0"
                    title="expenses_news">
                <a:formToolbar>
                    <a:label name="separator"/>
                    <a:button click="EXP5110_standardPreRecord" text="hap_previous" width="80"/>
                    <a:button click="EXP5110_standardNextRecord" text="hap_next" width="80"/>
                    <a:button click="EXP5110_standardSaveReport" text="hap_save" width="80"/>
                    <a:button click="EXP5110_standardExtendClose" text="hap_back" width="80"/>
                </a:formToolbar>
                <a:numberField name="lineNumber" id="EXP5110_editor_001" allowDecimals="false" allowNegative="false"
                               bindTarget="EXP5110_exp_report_standard_line_ds" prompt="bpm_tplt.line_num"/>
                <a:lov name="companyName" id="EXP5110_editor_003" bindTarget="EXP5110_exp_report_standard_line_ds"
                       prompt="acp_invoice_lines.company_id"/>
                <a:lov name="unitName" id="EXP5110_editor_004" bindTarget="EXP5110_exp_report_standard_line_ds"
                       prompt="bgt_budget_reserves.unit_id"/>
                <a:lov name="positionName" id="EXP5110_editor_005" bindTarget="EXP5110_exp_report_standard_line_ds"
                       prompt="acp_requisition.position"/>
                <a:lov name="employeeName" id="EXP5110_editor_006" bindTarget="EXP5110_exp_report_standard_line_ds"
                       prompt="bgt_budget_item_mapping.employee_name"/>
                <a:lov name="accEntityName" id="EXP5110_editor_007" bindTarget="EXP5110_exp_report_standard_line_ds"
                       prompt="csh_offer_format.acc_entity"/>
                <a:lov name="respCenterName" id="EXP5110_editor_008" bindTarget="EXP5110_exp_report_standard_line_ds"
                       prompt="acp_invoice_line.responsibility_center_id"/>
                <a:comboBox name="moExpenseTypeName" id="EXP5110_editor_009"
                            bindTarget="EXP5110_exp_report_standard_line_ds"
                            prompt="con_payment_schedules.expense_type_id"/>
                <a:comboBox name="moExpenseItemName" id="EXP5110_editor_010"
                            bindTarget="EXP5110_exp_report_standard_line_ds" prompt="acp_invoice_line.expense_item_id">
                    <a:events>
                        <a:event name="focus" handler="EXP5110_onItemNameFocus"/>
                    </a:events>
                </a:comboBox>
                <a:lov name="placeName" id="EXP5110_editor_012" bindTarget="EXP5110_exp_report_standard_line_ds"
                       prompt="exp_amortization_tp_lns.city"/>
                <a:comboBox name="placeTypeName" id="EXP5110_editor_011"
                            bindTarget="EXP5110_exp_report_standard_line_ds" prompt="exp_report_lines.place_type_id"/>
                <a:datePicker name="dateFrom" id="EXP5110_editor_013" bindTarget="EXP5110_exp_report_standard_line_ds"
                              prompt="bgt_period.start_date" renderer="Aurora.formatDate"/>
                <a:datePicker name="dateTo" id="EXP5110_editor_014" bindTarget="EXP5110_exp_report_standard_line_ds"
                              prompt="bgt_period.end_date" renderer="Aurora.formatDate"/>
                <a:comboBox name="businessCurrencyName" id="EXP5110_editor_015"
                            bindTarget="EXP5110_exp_report_standard_line_ds" prompt="business_currency_name"/>
                <a:numberField name="businessPrice" id="EXP5110_editor_016"
                               bindTarget="EXP5110_exp_report_standard_line_ds"
                               editorFunction="EXP5110_standardBizPriceEditorFun" prompt="business_price"
                               renderer="Aurora.formatMoney"/>
                <a:numberField name="primaryQuantity" id="EXP5110_editor_017"
                               bindTarget="EXP5110_exp_report_standard_line_ds" prompt="acp_invoice_line.quantity"/>
                <a:numberField name="businessAmount" id="EXP5110_editor_028"
                               bindTarget="EXP5110_exp_report_standard_line_ds" prompt="business_amount" readOnly="true"
                               renderer="Aurora.formatMoney"/>
                <a:lov name="periodName" id="EXP5110_editor_032" bindTarget="EXP5110_exp_report_standard_line_ds"
                       prompt="bgt_budget_reserves.period_name"/>
                <a:textArea name="description" id="EXP5110_editor_002" bindTarget="EXP5110_exp_report_standard_line_ds"
                            calcWidth="calc((80%  - 120px) / 3 * 3 + 30px)" colspan="3"
                            prompt="acp_invoice_header.description"/>
            </a:form>
            <a:form id="EXP5110_standardDimForm" column="3" padding="6" showmargin="0" title="dimension_information">
                <#list standard_line_dimensions as line_dimension>
                    <#if line_dimension ??>
                        <a:lov name="${line_dimension.displayField}"
                               id="EXP5110_editor_dim_${line_dimension.dimensionId}"
                               bindTarget="EXP5110_exp_report_standard_line_ds"
                               prompt="${line_dimension.dimensionName}"/>
                    </#if>
                </#list>
            </a:form>
            <a:form id="EXP5110_standardObjForm" column="3" padding="6" showmargin="0"
                    title="expense_object_information">
                <#list standard_line_objects as line_object>
                    <#if line_object ??>
                        <#if line_object.expenseObjectMethod == 'DESC_TEXT'>
                            <a:textField name="${line_object.displayField}"
                                         id="EXP5110_editor_obj_${line_object.moExpObjTypeId}"
                                         bindTarget="EXP5110_exp_report_standard_line_ds"
                                         prompt="${line_object.moExpObjTypeName}"/>
                        <#else>
                            <a:lov name="${line_object.displayField}"
                                   id="EXP5110_editor_obj_${line_object.moExpObjTypeId}"
                                   bindTarget="EXP5110_exp_report_standard_line_ds"
                                   prompt="${line_object.moExpObjTypeName}"/>
                        </#if>
                    </#if>
                </#list>
            </a:form>
            <a:form id="EXP5110_standardMoreInvoice_form" padding="0" showmargin="0" title="more_invoice_news">
                <a:formToolbar>
                    <a:label name="separator"/>
                    <a:gridButton bind="EXP5110_stardardMoreInvoiceGrid" type="add" width="80"/>
                    <a:gridButton bind="EXP5110_stardardMoreInvoiceGrid" type="save" width="80"/>
                    <a:gridButton bind="EXP5110_stardardMoreInvoiceGrid" type="delete" width="80"/>
                </a:formToolbar>
                <a:grid id="EXP5110_stardardMoreInvoiceGrid" autoAdjustHeight="true"
                        bindTarget="EXP5110_moreInvoiceResultDs" marginHeight="151" navBar="true" selectable="true"
                        width=".8">
                    <a:columns>
                        <a:column name="invoiceCategoryName" align="center" editor="EXP5110_standard_dist_cb"
                                  width="90"/>
                        <a:column name="invoiceCode" align="center" editorFunction="EXP5110_moreInvoiceEditorFun"
                                  width="90"/>
                        <a:column name="invoiceNumber" align="center" editorFunction="EXP5110_moreInvoiceEditorFun"
                                  width="90"/>
                        <a:column name="invoiceDate" align="center" editorFunction="EXP5110_moreInvoiceEditorFun"
                                  renderer="Aurora.formatDate" width="90"/>
                        <a:column name="invoiceAmount" align="right" editorFunction="EXP5110_moreInvoiceEditorFun"
                                  renderer="Aurora.formatMoney" width="90"/>
                        <a:column name="taxTypeName" align="center" editorFunction="EXP5110_moreInvoiceEditorFun"
                                  width="90"/>
                        <a:column name="taxAmount" align="right" editorFunction="EXP5110_moreInvoiceEditorFun"
                                  renderer="Aurora.formatMoney" width="90"/>
                        <a:column name="businessAmount" align="right" editor="EXP5110_standard_dist_nf_2"
                                  renderer="Aurora.formatMoney" width="90"/>
                        <a:column name="moreTax" align="center" renderer="EXP5110_moreInvoiceTaxRenderer" width="90"/>
                    </a:columns>
                    <a:editors>
                        <a:textField id="EXP5110_standard_dist_tf"/>
                        <a:datePicker id="EXP5110_standard_dist_dp"/>
                        <a:lov id="EXP5110_standard_dist_lov"/>
                        <a:comboBox id="EXP5110_standard_dist_cb"/>
                        <a:numberField id="EXP5110_standard_dist_nf_2" allowDecimals="true" allowNegative="false"
                                       decimalPrecision="2"/>
                        <a:numberField id="EXP5110_standard_dist_nf_0" allowDecimals="false"/>
                    </a:editors>
                    <a:events>
                        <a:event name="cellclick" handler="EXP5110_onMoreInvoiceCellClickFun"/>
                    </a:events>
                </a:grid>
            </a:form>
        </a:screenBody>
    </a:view>
</a:screen>
