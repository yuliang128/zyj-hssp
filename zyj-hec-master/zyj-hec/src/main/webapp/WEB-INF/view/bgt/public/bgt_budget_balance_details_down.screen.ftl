<?xml version="1.0" encoding="UTF-8"?>
<!--
    $Author: lixi  
    $Date: 2011-7-28 下午03:40:26  
    $Revision: 1.0  
    $Purpose: 
-->
<a:screen xmlns:a="http://www.aurora-framework.org/application" xmlns:p="uncertain.proc">
    <a:init-procedure>
        <p:echo/>
    </a:init-procedure>
    <a:view>
        <a:link id="pur_purchase_order_readonly_link_3" url="$[/request/@context_path]/pur/pur_purchase_order_readonly_wfl.screen"/>
        <a:link id="pur_purchase_requisition_readonly_link_2" url="$[/request/@context_path]/wfl/WFL1001/pur_purchase_requisition_readonly_wfl.screen"/>
        <a:link id="exp_requisition_readonly_link_1" url="$[/request/@context_path]/expm/EXP4010/exp_requisition_view_main.screen"/>
        <a:link id="exp_report_maintain_read_only_link_6" url="$[/request/@context_path]/expm/EXP5110/exp_report_view_main.screen"/>
        <script><![CDATA[
            function doc_number_editor(value, record, name) {
                var type = record.get('docType');
                if (type == 'EXP_REPORT') {
                    url = $au('exp_report_maintain_read_only_link_6').getUrl() + '?expReportHeaderId=' + record.get('docId') + '&canUploadFlag=false';
                } else if (type == 'EXP_REQUISITION') {
                    url = $au('exp_requisition_readonly_link_1').getUrl() + '?expRequisitionHeaderId=' + record.get('docId') + '&canUploadFlag=false';
                } else if (type == 'PUR_REQUISITION') {
                    url = $au('pur_purchase_requisition_readonly_link_2').getUrl() + '?headId=' + record.get('docId') + '&canUploadFlag=false';
                } else if (type == 'PUR_ORDER') {
                    url = $au('pur_purchase_order_readonly_link_3').getUrl() + '?headId=' + record.get('docId');
                }
                return '<a href="javascript:jump(\'' + url + '\')">' + value + '</a>';
            }
            
            function jump(url) {
                new Aurora.Window({
                    id: 'document_details_win',
                    url: url,
                    title: '$[l:pur_requisition_allocate.order_details]',
                    fullScreen: true
                });
            }
            
            function back() {
                $au('bgt_budget_balance_details_down_window').close();
            }
        ]]></script>
        <a:dataSets>
            <a:dataSet id="result_ds" autoPageSize="true" autoQuery="true" baseUrl="/bgt/balance-detail-init" queryUrl="$[/request/@context_path]/bgt/balance-detail-init/query?reserveFlag=$[/parameter/@reserveFlag]">
                <a:fields>
                    <a:field name="companyDesc" prompt="fnd_centralized_managing.company_id"/>
                    <a:field name="unitDesc" prompt="exp_org_position.unit"/>
                    <a:field name="docNumber" prompt="exp_report_header.exp_report_number"/>
                    <a:field name="lineNumber" prompt="hap_line_number"/>
                    <a:field name="status" prompt="acp_acp_requisition_hd.status"/>
                    <a:field name="description" prompt="exp_accrued_accounts.description"/>
                    <a:field name="docType"/>
                    <a:field name="docId"/>
                    <a:field name="typeDesc" prompt="acp_requisition.req_types"/>
                    <#if reserveFlag == "R">
                        <a:field name="employeeDesc" prompt="csh_payment_requisition_hd.employee_name"/>
                        <a:field name="docDate" prompt="csh_payment_requisition_hd.requisition_date"/>
                        <a:field name="budgetItemDesc" prompt="exp_company_req_item.req_item_id"/>
                        <a:field name="docAmount" prompt="acp_requisition.amount"/>
                        <a:field name="closeStatus" prompt="exp_requisition_header.close_status_name"/>
                    <#elseif reserveFlag == "U">
                        <a:field name="employeeDesc" prompt="exp_report_header.employee_id"/>
                        <a:field name="docDate" prompt="exp_report_header.report_date"/>
                        <a:field name="budgetItemDesc" prompt="exp_req_items.rep_item_code"/>
                        <a:field name="docAmount" prompt="exp_report_lines.report_amount"/>
                        <a:field name="closeStatus" prompt="prompt.reverse"/>
                    </#if>
                </a:fields>
            </a:dataSet>
        </a:dataSets>
        <a:screenBody>
            <a:form padding="0" showmargin="false" shrinkable="false" title="budget_balance_query">
                <a:formToolbar>
                    <a:label name="separator"/>
                    <a:gridButton bind="bgt_balance_detail_down_grid" type="excel" width="80"/>
                    <a:toolbarButton click="back" text="hap_back" width="80"/>
                </a:formToolbar>
                <a:grid id="bgt_balance_detail_down_grid" bindTarget="result_ds" marginHeight="132" marginWidth="3" navBar="true">
                    <a:columns>
                        <a:column name="docNumber" align="center" renderer="doc_number_editor" width="120"/>
                        <a:column name="typeDesc" align="center" width="120"/>
                        <a:column name="lineNumber" align="center" width="60"/>
                        <a:column name="companyDesc" align="center" width="120"/>
                        <a:column name="unitDesc" align="center" width="120"/>
                        <a:column name="employeeDesc" align="center" width="80"/>
                        <a:column name="docDate" align="center" width="80"/>
                        <a:column name="budgetItemDesc" align="center" width="120"/>
                        <a:column name="docAmount" align="right" renderer="Aurora.formatMoney" width="80"/>
                        <a:column name="description" align="center" width="150"/>
                        <a:column name="status" align="center" width="80"/>
                        <a:column name="closeStatus" align="center" width="80"/>
                    </a:columns>
                </a:grid>
            </a:form>
        </a:screenBody>
    </a:view>
</a:screen>
