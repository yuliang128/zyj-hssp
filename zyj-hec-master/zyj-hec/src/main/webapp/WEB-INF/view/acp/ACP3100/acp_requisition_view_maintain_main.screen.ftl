<?xml version="1.0" encoding="UTF-8"?>
<!--
    $Author: Tagin
    $Date: 2017-09-01 上午11:27:22  
    $Revision: 1.0  
    $Purpose: 付款申请单创建/维护页面
-->
<a:screen xmlns:c="aurora.application.action" xmlns:p="uncertain.proc" xmlns:a="http://www.aurora-framework.org/application" xmlns:s="aurora.plugin.script" trace="true">
    <a:view>
        <a:link id="ACP3100_submit_exp_requisition_link" url="$[/request/@context_path]/acp/requisition-hd/submitAcpRequisition"/>
        <a:link id="ACP3100_acp_requisition_main_link" url="$[/request/@context_path]/acp/ACP3100/acp_requisition_main.screen"/>
        <a:link id="ACP3100_acp_requisition_deleteAll_link" url="$[/request/@context_path]/acp/requisition-hd/delete"/>
        <a:link id="ACP3100_exp_report_schedule_link" url="$[/request/@context_path]/acp/ACP3100/acp_requisition_from_report_lov.screen"/>
        <a:link id="ACP3100_con_contract_schedule_link" url="$[/request/@context_path]/acp/ACP3100/acp_requisition_from_contract_lov.screen"/>
        <a:link id="ACP3100_document_history_view_link" url="$[/request/@context_path]/expm/public/exp_document_history_query.screen"/>
        <a:link id="ACP3100_uploadFile_link" url="$[/request/@context_path]/app/APP2030/app_uploadFile.screen"/>
        <!--added by zealot at 20181121 工作流提交前预览-->
        <a:link id="EXP5110_workflow_preview_before_submit_link" url="$[/request/@context_path]/modules/wfl/WFL1001/wfl_workflow_preview_before_submit.screen"/>
        <style>
        .reqnumberlov{
            color: #005a78;
            text-decoration: none;
            cursor:pointer;
        }

        .reqnumberlov:hover{
            color: #ff9900;
            text-decoration: none;
        }


        .reqnumberlov input{
            cursor:pointer;
        }
        .layout-td-con{
            padding:0!important;
        }
        </style>
        <script><![CDATA[
            var headerRecord;
            var submitFlag = false;
            
            function ACP3100_getNextLineNumber(dataSet) {
                var nextLineNumber = 10;
                var allPmtRecords = dataSet.getAll();
                Ext.each(allPmtRecords, function(allPmtRecord) {
                    if (allPmtRecord.get('lineNumber') >= nextLineNumber) {
                        nextLineNumber = allPmtRecord.get('lineNumber') + 10;
                    }
                }, this);
                return nextLineNumber;
            }
            
            function ACP3100_acpRequisitionFromRportReady() {
                var header_id = $au('ACP3100_acpRequisitionHdsDs').getAt(0).get('requisitionHdsId');
                var acpReqDiv = Ext.get('saveAcpReqDiv');
                if (header_id == undefined) {
                    acpReqDiv.parent().setStyle('display', 'none');
                } else {
                    acpReqDiv.parent().setStyle('display', 'table-cell');
                }
            }
            
            function ACP3100_submitRequisition() {
                //存在未保存的申请单数据，先执行保存逻辑
                if (!submitFlag) {
                    ACP3100_saveRequisition();
                    submitFlag = true;
                    return;
                }
                submitFlag = false;
                Aurora.request({
                    lockMessage: '付款单正在提交中，请稍候!',
                    url: $au('ACP3100_submit_exp_requisition_link').getUrl(),
                    para: {
                        requisitionHdsId: $au('ACP3100_acpRequisitionHdsDs').getAt(0).get('requisitionHdsId')
                    },
                    success: function(resp) {
                        window.location.href = $au('ACP3100_acp_requisition_main_link').getUrl();
                    }
                });
            }
            
            function ACP3100_deleteRequisition() {
                Aurora.showConfirm('$[l:exit_system]', '确认 [ <font color="red">删除</font> ] 当前付款申请单？', function(resp) {
                    Aurora.request({
                        url: $au('ACP3100_acp_requisition_deleteAll_link').getUrl(),
                        para: {
                            'requisitionHdsId': $au('ACP3100_acpRequisitionHdsDs').getAt(0).get('requisitionHdsId'),
                        },
                        success: function() {
                            $au('ACP3100_acp_requisition_maintain_main_window').close();
                        },
                        scope: this
                    });
                    resp.close();
                });
            }
            
            function ACP3100_submitSuccessFunc() {
                if (submitFlag) {
                    ACP3100_submitRequisition();
                    submitFlag = false;
                }
                var headerDs = $au('ACP3100_acpRequisitionHdsDs');
                var lineDs = $au('ACP3100_acpRequisitionLnsDs');
                var headerRecord = headerDs.getAt(0);
                var headerNumber = headerRecord.get('requisitionNumber');
                if (headerNumber != undefined) {
                    Ext.get('saveAcpReqDiv').parent().setStyle('display', 'table-cell');
                }

                lineDs.setQueryUrl('$[/request/@context_path]/acp/requisition-ln/query?requisitionHdsId='+headerRecord.get('requisitionHdsId')+'&amp;payeeId=$[/model/headerInfo/records/record/@payeeId]');
                lineDs.query();
            }
            
            function ACP3100_mainPayeeFunc() {
                var record = $au('ACP3100_acpRequisitionHdsDs').getAt(0);
                record.getField('payeeName').setLovPara('payeeCategory', record.get('payeeCategory'));
            }
            
            function ACP3100_acpRequisitionPositionFunc() {
                var record = $au('ACP3100_acpRequisitionHdsDs').getAt(0);
                var dataSet = $au('ACP3100_acpRequisitionPositionDs');
                dataSet.setQueryParameter('employeeId', record.get('employeeId'));
                dataSet.query();
            }
            
            function calcAmount() {
                var records = $au('ACP3100_acpRequisitionLnsDs').getAll();
                var amount = 0;
                for (var i = 0;i < records.length;i++) {
                    if (records[i].get('amount')) {
                        amount = plus(records[i].get('amount'), amount);
                    }
                }
                return amount;
            }
            
            function ACP3100_hdsUpdateFunc(dataSet, record, name, value, oldValue) {
                if (name == 'payeeCategoryName') {
                    record.set('payeeName', '');
                    record.set('payeeId', '');
                }
            }
            
            function ACP3100_cellClickFunc(grid, row, name, record) {
                if (name == 'payeeName') {
                    record.getField('payeeName').setLovPara('payeeCategory', record.get('payeeCategory'));
                    record.getField('payeeName').setLovPara('accEntityId', record.get('accEntityId'));
                } else if (name == 'accountNumber') {
                    record.getField('accountNumber').setLovPara('payeeId', record.get('payeeId'));
                    record.getField('accountNumber').setLovPara('payeeCategory', record.get('payeeCategory'));
                    record.getField('accountNumber').setLovPara('accEntityId', record.get('accEntityId'));
                } else if (name == 'moCshTrxClassName') {
                    $au('ACP3100_transactionClassDs').setQueryParameter('moPayReqTypeId', '$[/model/headerInfo/records/record/@moPayReqTypeId]');
                    $au('ACP3100_transactionClassDs').query();
                }
            }
            
            function ACP3100_lnsUpdateFunc(dataSet, record, name, value, oldValue) {
                if (name == 'payeeCategoryName') {
                    record.set('payeeName', '');
                    record.set('payeeId', '');
                    record.set('accountNumber', '');
                    record.set('accountName', '');
                }
                if (name == 'accountNumber' || name == 'payeeName') {
                    if (name == 'payeeName') {
                        record.set('accountNumber', '');
                    }
                    record.set('accountName', '');
                    record.set('bankCode', '');
                    record.set('bankName', '');
                    record.set('bankLocationCode', '');
                    record.set('bankLocationName', '');
                    record.set('provinceCode', '');
                    record.set('provinceName', '');
                    record.set('cityCode', '');
                    record.set('cityName', '');
                }
                if (name == 'amount') {
                    $au('ACP3100_acpRequisitionHdsDs').getAt(0).set('amount', calcAmount());
                }
            }
            
            function ACP3100_lnsRemoveFunc(dataSet, record, idx) {
                $au('ACP3100_acpRequisitionHdsDs').getAt(0).set('amount', calcAmount());
            }
            
            function ACP3100_lnsGridAddFunc() {
                var dataSet = $au('ACP3100_acpRequisitionHdsDs');
                var headerRecord = dataSet.getAt(0);
                if (dataSet.validate()) {
                    var selectRecords = $au('ACP3100_acpRequisitionLnsDs').getJsonData(true);
                    if (selectRecords && selectRecords.length) {
                        Ext.each(selectRecords, function(selectRecord) {
                            var data = {};
                            for (var field in $au('ACP3100_acpRequisitionLnsDs').fields) {
                                data[field] = selectRecord[field];
                            }
                            data['requisitionLnsId'] = null;
                            data['lineNumber'] = ACP3100_getNextLineNumber($au('ACP3100_acpRequisitionLnsDs'));
                            $au('ACP3100_acpRequisitionLnsDs').create(data);
                            headerRecord.set('amount', calcAmount());
                        }, this);
                    } else {
                        var data = {};
                        data['lineNumber'] = ACP3100_getNextLineNumber($au('ACP3100_acpRequisitionLnsDs'));
                        data['accEntityId'] = headerRecord.get('accEntityId');
                        data['payeeCategoryName'] = headerRecord.get('payeeCategoryName');
                        data['payeeCategory'] = headerRecord.get('payeeCategory');
                        data['payeeName'] = headerRecord.get('payeeName');
                        data['payeeId'] = headerRecord.get('payeeId');
                        data['paymentMethodName'] = headerRecord.get('paymentMethodName');
                        data['paymentMethodId'] = headerRecord.get('paymentMethodId');
                        data['paymentUsedeName'] = headerRecord.get('paymentUsedeName');
                        data['paymentUsedeId'] = headerRecord.get('paymentUsedeId');
                        data['accountNumber'] = headerRecord.get('accountNumber');
                        data['accountName'] = headerRecord.get('accountName');
                        data['bankCode'] = headerRecord.get('bankCode');
                        data['bankName'] = headerRecord.get('bankName');
                        data['bankLocationCode'] = headerRecord.get('bankLocationCode');
                        data['bankLocationName'] = headerRecord.get('bankLocationName');
                        data['provinceCode'] = headerRecord.get('provinceCode');
                        data['provinceName'] = headerRecord.get('provinceName');
                        data['cityCode'] = headerRecord.get('cityCode');
                        data['cityName'] = headerRecord.get('cityName');
                        $au('ACP3100_acpRequisitionLnsDs').create(data);
                    }
                }
            }

            
            function ACP3100_contractScheduleAddWindowFunc() {
                new Aurora.Window({
                    url: $au('ACP3100_con_contract_schedule_link').getUrl() + '?employeeId=$[/model/headerInfo/records/record/@employeeId]&moPayReqTypeId=$[/model/headerInfo/records/record/@moPayReqTypeId]&currencyCode=$[/model/headerInfo/records/record/@currencyCode]',
                    id: 'ACP3100_acp_requisition_from_contract_window',
                    title: '$[l:con_contract.contract_settlement]',
                    width: 1020,
                    side: 'right'
                });
            }
            
            function ACP3100_reportScheduleAddDataFunc(records) {
                if (records) {
                    // 关联报销单
                    Ext.each(records, function(record) {
                        var data = {};
                        for (var fieldName in records['fields']) {
                            data[fieldName] = record[fieldName];
                        }
                        //data['requisition_dtl_id'] = null;
                        data['lineNumber'] = ACP3100_getNextLineNumber($au('ACP3100_acpReportRequisitionDtlDs'));
                        data['accEntityId'] = $au('ACP3100_acpRequisitionHdsDs').getAt(0).get('accEntityId');
                        $au('ACP3100_acpReportRequisitionDtlDs').create(data);
                    }, this);
                }
            }
            
            
            function ACP3100_contractScheduleAddDataFunc(records) {
                if (records) {
                    // 关联合同
                    Ext.each(records, function(record) {
                        var data = {};
                        for (var fieldName in records['fields']) {
                            data[fieldName] = record[fieldName];
                        }
                        //data['requisition_dtl_id'] = null;
                        data['lineNumber'] = ACP3100_getNextLineNumber($au('ACP3100_acpContractRequisitionDtlDs'));
                        data['accEntityId'] = $au('ACP3100_acpRequisitionHdsDs').getAt(0).get('accEntityId');
                        $au('ACP3100_acpContractRequisitionDtlDs').create(data);
                    }, this);
                }
            }
            
            
            function ACP3100_saveRequisition() {
                var headerDs = $au('ACP3100_acpRequisitionHdsDs');
                if (!headerDs.isModified()) {
                    headerDs.data[0].dirty = true;
                }
                $au('ACP3100_acpRequisitionHdsDs').submit();
            
            }
            
            function ACP3100_historyRequisition() {
                var record = $au('ACP3100_acpRequisitionHdsDs').getAt(0);
                new Aurora.Window({
                    url: $au('ACP3100_document_history_view_link').getUrl() + '?documentType=ACP_REQUISITION&documentId=' + record.get('requisitionHdsId'),
                    id: 'CSH5010_document_history_view_window',
                    side: 'right',
                    width: 1020
                });
            }
            
            function ACP3100_fileRequisition() {
                var record = $au('ACP3100_acpRequisitionHdsDs').getAt(0);
                new Aurora.Window({
                    url: $au('ACP3100_uploadFile_link').getUrl() + '?tableName=ACP_REQUISITION_HD&tablePkValue=' + record.get('requisitionHdsId'),
                    title: '$[l:atm.upload_attachment]',
                    id: 'check_upload_file_screen',
                    width: 600,
                    height: 400
                });
            }
            
            function ACP3100_backRequisition() {
                if (Aurora.CmpManager.get('ACP3100_acp_requisition_view_main_window')) {
                    $au('ACP3100_acp_requisition_view_main_window').close();
                }
            }
            
            //added by zealot at 20181121 单据提交前工作流预览
            
            function ACP3100_wflPreview() {
                var headerRecord = $au('ACP3100_acpRequisitionHdsDs').getAt(0);
                var transaction_type_id = headerRecord.get('moPayReqTypeId');
                var company_id = headerRecord.get('companyId');
                var instance_param = headerRecord.get('requisitionHdsId');
                var auto_approve_flag = headerRecord.get('autoApproveFlag');
                if (auto_approve_flag == 'Y') {
                    Aurora.showWarningMessage('$[l:prompt]', '$[l:the_acp_requisition_is_auto_approve]', null, 200, 100);
                    return;
                }
                if (instance_param) {
                    new Aurora.Window({
                        url: $au('EXP5110_workflow_preview_before_submit_link').getUrl() + '?transactionCategory=ACP_REQUISITION&transactionTypeId=' + transactionTypeId + '&companyId=' + companyId + '&instanceParam=' + instanceParam,
                        id: 'EXP5110_workflow_preview_before_submit_window',
                        title: '$[l:wfl.preview]',
                        fullScreen: true
                    });
                } else {}
            }

        function ACP3100_lnsIndexChangeFunc(ds,record){
            var requisitionLnsId = record.get('requisitionLnsId');
            if(requisitionLnsId){
                $au('ACP3100_acpReportRequisitionDtlDs').setQueryParameter('requisitionLnsId',requisitionLnsId);
                $au('ACP3100_acpReportRequisitionDtlDs').query();
            }
        }
        ]]></script>
        <a:dataSets>
            <a:dataSet id="EXP4010_currency_ds" autoQuery="true" queryUrl="$[/request/@context_path]/gld-currency/query?enabledFlag=Y"/>
            <a:dataSet id="ACP3100_payeeCategoryDs" autoQuery="true" queryUrl="$[/request/@context_path]/common/auroraCode/PAYMENT_OBJECT/"/>
            <a:dataSet id="ACP3100_payeeMethodsDs" autoQuery="true" queryUrl="$[/request/@context_path]/csh/payment-method/queryPaymentMethod?companyId=$[/session/@companyId]&amp;magOrgId=$[/session/@magOrgId]"/>
            <a:dataSet id="ACP3100_paymentUsedesDs" autoQuery="true" queryUrl="$[/request/@context_path]/acp/mo-pay-req-tp-ref-used/query?moPayReqTypeId=$[/model/headerInfo/records/record/@moPayReqTypeId]"/>
            <a:dataSet id="ACP3100_cashFlowItemDs" autoQuery="true" queryUrl="$[/request/@context_path]/csh/cash-flow-item/getCashFlowItem"/>
            <a:dataSet id="ACP3100_transactionClassDs" queryUrl="$[/request/@context_path]/acp/mo-pay-req-tp-ref-trx/queryByTypeId"/>
            <a:dataSet id="ACP3100_acpRequisitionPositionDs" queryUrl="$[/request/@context_path]/exp/org-position/queryPositionAndUnit"/>
            <a:dataSet id="ACP3100_acpRequisitionAccDs" autoQuery="true" queryUrl="$[/request/@context_path]/gld/account-entity/queryAccEntityAndCurrencyCode"/>
            <a:dataSet id="ACP3100_acpRequisitionHdsDs" baseUrl="/acp/requisition-hd" submitUrl="$[/request/@context_path]/acp/requisition-hd/submit">
                <a:datas dataSource="headerInfo/records"/>
                <a:fields>
                    <a:field name="requisitionHdsId"/>
                    <a:field name="requisitionNumber" prompt="exp_requisition_header.requisition_number" readOnly="true"/>
                    <a:field name="moPayReqTypeName" prompt="wfl_workflow_approve.process_type" readOnly="true"/>
                    <a:field name="accEntityId"/>
                    <a:field name="accEntityName" readOnly="true" displayField="accEntityName" options="ACP3100_acpRequisitionAccDs" prompt="gld_accounting_entity.acc_entity_name" required="true" returnField="accEntityId" valueField="accEntityId"/>
                    <a:field name="phone" prompt="gld_accounting_entity.phone" readOnly="true"/>
                    <a:field name="requisitionDate" prompt="exp_requisition_header.requisition_date" readOnly="true" datatype="date"/>
                    <a:field name="amount" prompt="csh_repayment_register_hd.amount" readOnly="true"/>
                    <a:field name="employeeId"/>
                    <a:field name="employeeName" prompt="acp_req_det_reado.employee_id" readOnly="true" required="true"/>
                    <a:field name="positionId"/>
                    <a:field name="positionName" readOnly="true" displayField="positionName" options="ACP3100_acpRequisitionPositionDs" prompt="acp_acp_requisition_hds.position_id" required="true" returnField="positionId" valueField="positionId">
                        <a:mapping>
                            <a:map from="unitId" to="unitId"/>
                            <a:map from="unitName" to="unitName"/>
                        </a:mapping>
                    </a:field>
                    <a:field name="unitName" prompt="acp_acp_requisition_hds.unit_id" readOnly="true"/>
                    <a:field name="unitId"/>
                    <a:field name="createdByName" prompt="exp_requisition_header.created_by" readOnly="true" required="true"/>
                    <a:field name="currencyCode"/>
                    <a:field name="currencyName" prompt="exp_requisition_header.payment_currency_name" readOnly="true" required="true"/>
                    <a:field name="status"/>
                    <a:field name="statusName" prompt="exp_requisition_header.requisition_status" readOnly="true"/>
                    <a:field name="paymentMethodId"/>
                    <a:field name="paymentMethodName" readOnly="true" displayField="description" options="ACP3100_payeeMethodsDs" prompt="csh_mo_payment_req_type.payment_method_desc" required="true" returnField="paymentMethodId" valueField="paymentMethodId"/>
                    <a:field name="paymentUsedeId"/>
                    <a:field name="paymentUsedeName" readOnly="true" displayField="paymentUsedeName" options="ACP3100_paymentUsedesDs" prompt="acp_mo_pay_req_type.define_usage" required="true" returnField="paymentUsedeId" valueField="paymentUsedeId"/>
                    <a:field name="payeeCategory"/>
                    <a:field name="payeeCategoryName"  readOnly="true" displayField="meaning" options="ACP3100_payeeCategoryDs" prompt="csh_payment_req_types.partner_category" required="true" returnField="payeeCategory" valueField="value"/>
                    <a:field name="payeeId"/>
                    <a:field name="payeeName" readOnly="true" lovCode="GLD_PAYEE_V_LOV" prompt="csh_payment_requisition_hd.partner_id" required="true" title="csh_payment_requisition_hd.partner_id">
                        <a:mapping>
                            <a:map from="payeeId" to="payeeId"/>
                            <a:map from="payeeName" to="payeeName"/>
                        </a:mapping>
                    </a:field>
                    <a:field name="description" readOnly="true" prompt="exp_requisition_header.description"/>
                    <a:field name="accountName" prompt="exp_employee_account.bank_account_name"/>
                    <a:field name="bankCode"/>
                    <a:field name="bankName"/>
                    <a:field name="bankLocationCode"/>
                    <a:field name="bankLocationName"/>
                    <a:field name="provinceCode"/>
                    <a:field name="provinceName"/>
                    <a:field name="cityCode"/>
                    <a:field name="cityName"/>
                    <a:field name="accountNumber"/>
                    <!-- modify lcy 2018/3/20 15:06:22 新增audit_flag,reversed_flag两字段 -->
                    <a:field name="auditFlag" checkedValue="Y" defaultValue="N" uncheckedValue="N"/>
                    <a:field name="reversedFlag" checkedValue="Y" defaultValue="N" uncheckedValue="N"/>
                </a:fields>
            </a:dataSet>
            <a:dataSet id="ACP3100_acpRequisitionLnsDs" autoPageSize="true" autoQuery="true" bindName="lines" bindTarget="ACP3100_acpRequisitionHdsDs" baseUrl="/acp/requisition-ln" queryUrl="$[/request/@context_path]/acp/requisition-ln/query?requisitionHdsId=$[/parameter/@requisitionHdsId]&amp;payeeId=$[/model/headerInfo/records/record/@payeeId]" selectable="true">
                <a:fields>
                    <a:field name="requisitionLnsId"/>
                    <a:field name="requisitionHdsId"/>
                    <a:field name="accEntityId"/>
                    <a:field name="lineNumber" prompt="hap_line_number"/>
                    <a:field name="payeeCategory"/>
                    <a:field name="payeeCategoryName" displayField="meaning" options="ACP3100_payeeCategoryDs" prompt="csh_payment_req_types.partner_category" required="true" returnField="payeeCategory" valueField="value"/>
                    <a:field name="payeeId"/>
                    <a:field name="payeeName" lovcCode="GLD_PAYEE_V_LOV" prompt="csh_payment_requisition_hd.partner_id" required="true" title="csh_payment_requisition_hd.partner_id">
                        <a:mapping>
                            <a:map from="payeeId" to="payeeId"/>
                            <a:map from="payeeName" to="payeeName"/>
                            <a:map from="accountNumber" to="accountNumber"/>
                            <a:map from="accountName" to="accountName"/>
                        </a:mapping>
                    </a:field>
                    <a:field name="amount" prompt="csh_payment.transaction_amount" required="true"/>
                    <a:field name="description" prompt="acp_requisition.description"/>
                    <a:field name="paymentMethodId"/>
                    <a:field name="paymentMethodName" displayField="description" options="ACP3100_payeeMethodsDs" prompt="csh_mo_payment_req_type.payment_method_desc" required="true" returnField="paymentMethodId" valueField="paymentMethodId"/>
                    <a:field name="paymentUsedeId"/>
                    <a:field name="paymentUsedeName" displayField="paymentUsedeName" options="ACP3100_paymentUsedesDs" prompt="csh_mo_payment_usede.payment_usede_code" readOnly="true" returnField="paymentUsedeId" valueField="paymentUsedeId"/>
                    <a:field name="cashFlowItemId"/>
                    <a:field name="cashFlowItemName" displayField="cashFlowItemName" options="ACP3100_cashFlowItemDs" prompt="csh_flow_item_desc" readOnly="true" returnField="cashFlowItemId" valueField="cashFlowItemId"/>
                    <a:field name="moCshTrxClassId"/>
                    <a:field name="moCshTrxClassName" displayField="moCshTrxClassName" options="ACP3100_transactionClassDs" prompt="csh_mo_transaction_classes.mo_csh_trx_class_code" required="true" returnField="moCshTrxClassId" valueField="moCshTrxClassId"/>
                    <a:field name="accountName" prompt="exp_employee_account.bank_account_name"/>
                    <a:field name="bankCode"/>
                    <a:field name="bankName"/>
                    <a:field name="bankLocationCode"/>
                    <a:field name="bankLocationName"/>
                    <a:field name="provinceCode"/>
                    <a:field name="provinceName"/>
                    <a:field name="cityCode"/>
                    <a:field name="cityName"/>
                    <a:field name="accountNumber" lovCode="GLD_PAYEE_ACCOUNT_VL_LOV" prompt="csh_bank_account_auth_detail_hct.bank_account_num" required="true">
                        <a:mapping>
                            <a:map from="accountNumber" to="accountNumber"/>
                            <a:map from="accountName" to="accountName"/>
                            <a:map from="bankCode" to="bankCode"/>
                            <a:map from="bankName" to="bankName"/>
                            <a:map from="bankLocationCode" to="bankLocationCode"/>
                            <a:map from="bankLocationName" to="bankLocationName"/>
                            <a:map from="provinceCode" to="provinceCode"/>
                            <a:map from="provinceName" to="provinceName"/>
                            <a:map from="cityCode" to="cityCode"/>
                            <a:map from="cityName" to="cityName"/>
                        </a:mapping>
                    </a:field>
                    <a:field name="requisitionPaymentDate" prompt="exp_report_pmt_schedule.schedule_due_date"/>
                </a:fields>
                <a:events>
                    <a:event name="indexChange" handler="ACP3100_lnsIndexChangeFunc"/>
                </a:events>
            </a:dataSet>
            <a:dataSet id="ACP3100_acpReportRequisitionDtlDs" bindName="report" bindTarget="ACP3100_acpRequisitionLnsDs" baseUrl="/acp/requisition-dtl" queryUrl="$[/request/@context_path]/acp/requisition-dtl/queryByLineId" queryDataSet="ACP3100_acpRequisitionLnsDs" selectable="true" submitUrl="$[/request/@context_path]/autocrud/db.acp_requisition_pkg.delete_acp_requisition_dtl/batch_update">
                <a:fields>
                    <a:field name="lineNumber" prompt="hap_line_number"/>
                    <a:field name="documentNumber" prompt="exp7010.report_number"/>
                    <a:field name="documentLineNumber" prompt="exp_report_pmt_schedules.schedule_line_number"/>
                    <a:field name="totalAmount" prompt="exp_report_pmt_schedules.requisition_amount"/>
                    <a:field name="scheduleDueDate" prompt="exp_report_pmt_schedules.schedule_due_date"/>
                    <a:field name="payedAmount" prompt="csh_payment_requisition_hd.paymented_amount"/>
                    <a:field name="amount" prompt="exp_report_query.req_amount"/>
                </a:fields>
            </a:dataSet>
            <a:dataSet id="ACP3100_acpContractRequisitionDtlDs" bindName="contract" bindTarget="ACP3100_acpRequisitionLnsDs" baseUrl="/acp/requisition-dtl" queryUrl="$[/request/@context_path]/acp/requisition-dtl/queryByLineId" queryDataSet="ACP3100_acpRequisitionLnsDs" selectable="true" submitUrl="$[/request/@context_path]/autocrud/db.acp_requisition_pkg.delete_acp_requisition_dtl/batch_update">
                <a:fields>
                    <a:field name="lineNumber" prompt="hap_line_number"/>
                    <a:field name="documentNumber" prompt="con_contract_headers.contract_number"/>
                    <a:field name="landmarkPhase" prompt="con_contract.landmark_phase"/>
                    <a:field name="totalAmount" prompt="exp_report_pmt_schedules.amount"/>
                    <a:field name="payedAmount" prompt="acp_requisition.already_paying"/>
                    <a:field name="amount" prompt="exp_report_pmt_schedules.requisition_amount"/>
                </a:fields>
            </a:dataSet>
        </a:dataSets>
        <a:screenBody>
            <a:form column="1" title="$[/model/headerInfo/records/record/@moPayReqTypeName]">
                <a:formToolbar>
                    <a:label name="separator"/>
                    <a:button click="ACP3100_fileRequisition" text="hap_attachment_documentation" width="80"/>
                    <a:button click="ACP3100_historyRequisition" text="hap_tracking_documents" width="80"/>
                    <a:button click="ACP3100_backRequisition" text="hap_back" width="80"/>
                </a:formToolbar>
                <a:box column="4" style="width:100%;">
                    <a:textField name="requisitionNumber" bindTarget="ACP3100_acpRequisitionHdsDs"/>
                    <a:comboBox name="moPayReqTypeName" bindTarget="ACP3100_acpRequisitionHdsDs"/>
                    <a:datePicker name="requisitionDate" renderer="Aurora.formatDate" bindTarget="ACP3100_acpRequisitionHdsDs"/>
                    <a:comboBox name="employeeName" bindTarget="ACP3100_acpRequisitionHdsDs"/>
                    <a:comboBox name="positionName" bindTarget="ACP3100_acpRequisitionHdsDs">
                        <a:events>
                            <a:event name="focus" handler="ACP3100_acpRequisitionPositionFunc"/>
                        </a:events>
                    </a:comboBox>
                    <a:comboBox name="unitName" bindTarget="ACP3100_acpRequisitionHdsDs"/>
                    <a:comboBox name="accEntityName" bindTarget="ACP3100_acpRequisitionHdsDs"/>
                    <a:textField name="phone" bindTarget="ACP3100_acpRequisitionHdsDs"/>
                    <a:textField name="createdByName" bindTarget="ACP3100_acpRequisitionHdsDs"/>
                    <a:comboBox name="currencyName" bindTarget="ACP3100_acpRequisitionHdsDs"/>
                    <a:comboBox name="payeeCategoryName" bindTarget="ACP3100_acpRequisitionHdsDs"/>
                    <a:lov name="payeeName" bindTarget="ACP3100_acpRequisitionHdsDs">
                        <a:events>
                            <a:event name="focus" handler="ACP3100_mainPayeeFunc"/>
                        </a:events>
                    </a:lov>
                    <a:comboBox name="statusName" bindTarget="ACP3100_acpRequisitionHdsDs"/>
                    <a:comboBox name="paymentMethodName" bindTarget="ACP3100_acpRequisitionHdsDs"/>
                    <a:comboBox name="paymentUsedeName" bindTarget="ACP3100_acpRequisitionHdsDs"/>
                    <a:numberField name="amount" bindTarget="ACP3100_acpRequisitionHdsDs" renderer="Aurora.formatMoney"/>
                </a:box>
                <a:box style="width:100%;margin-top:5px;">
                    <a:textArea name="description" bindTarget="ACP3100_acpRequisitionHdsDs" calcWidth="calc((100%  + 90px) / 4 * 3 + 150px)" width="800"/>
                </a:box>
            </a:form>
            <a:form padding="0" showmargin="false" title="acp_requisition_hds.requisition_lns">
                <a:grid id="ACP3100_requisition_lns_grid" autoAdjustHeight="true" bindTarget="ACP3100_acpRequisitionLnsDs" marginHeight="1" marginWidth="12" navBar="true" showRowNumber="false">
                    <a:columns>
                        <a:column name="lineNumber" align="center" width="80"/>
                        <a:column name="moCshTrxClassName" align="center"  width="100"/>
                        <a:column name="payeeCategoryName" align="center"  width="100"/>
                        <a:column name="payeeName" align="center"  width="100"/>
                        <a:column name="amount" align="right"  renderer="Aurora.formatMoney" width="100"/>
                        <a:column name="paymentMethodName" align="center"  width="100"/>
                        <a:column name="requisitionPaymentDate" align="center"  renderer="Aurora.formatDate" width="100"/>
                        <a:column name="accountNumber" align="left"  width="150"/>
                        <a:column name="accountName" align="left" width="150"/>
                    </a:columns>
                </a:grid>
            </a:form>
            <#if headerInfo.paymentType=='REPORT'>
                <a:form marginWidth="10" padding="0" showmargin="false" title="exp_report.reimbursement_bill">
                    <a:grid id="ACP3100_reportRequisition_dtl_grid" autoAdjustHeight="true" bindTarget="ACP3100_acpReportRequisitionDtlDs" height="600" marginWidth="12" navBar="true" showRowNumber="false">
                        <a:columns>
                            <a:column name="lineNumber" align="center" width="50"/>
                            <a:column name="documentNumber" align="center" width="100"/>
                            <a:column name="documentLineNumber" align="center" width="50"/>
                            <a:column name="totalAmount" align="right" renderer="Aurora.formatMoney" width="100"/>
                            <a:column name="scheduleDueDate" align="center" width="100"/>
                            <a:column name="payedAmount" align="right" renderer="Aurora.formatMoney" width="100"/>
                            <a:column name="amount" align="right" editor="ACP3100_scheduleNumberField" renderer="Aurora.formatMoney" width="100"/>
                        </a:columns>
                    </a:grid>
                </a:form>
            <#elseif headerInfo.paymentType=='CONTRACT'>
                <a:form marginWidth="10" padding="0" showmargin="false" title="con_contract.contract_settlement">
                    <a:grid id="ACP3100_contractRequisition_dtl_grid" autoAdjustHeight="true" bindTarget="ACP3100_acpContractRequisitionDtlDs" height="600" marginWidth="12" navBar="true" showRowNumber="false">
                        <a:columns>
                            <a:column name="lineNumber" align="center" width="50"/>
                            <a:column name="documentNumber" align="center" width="100"/>
                            <a:column name="landmarkPhase" align="center" width="200"/>
                            <a:column name="totalAmount" align="right" renderer="Aurora.formatMoney" width="100"/>
                            <a:column name="payedAmount" align="right" renderer="Aurora.formatMoney" width="100"/>
                            <a:column name="amount" align="right" editor="ACP3100_scheduleNumberField" renderer="Aurora.formatMoney" width="100"/>
                        </a:columns>
                    </a:grid>
                </a:form>
            </#if>
        </a:screenBody>
    </a:view>
</a:screen>
