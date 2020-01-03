<?xml version="1.0" encoding="UTF-8"?>
<a:screen xmlns:c="aurora.application.action" xmlns:p="uncertain.proc" xmlns:a="http://www.aurora-framework.org/application" xmlns:s="aurora.plugin.script" trace="true">
    <a:view>
        <a:link id="EXP5110_back_submit_exp_report_link" model="db.exp_report_pkg.submit_cancel_return_doc" modelaction="execute"/>
        <a:link id="EXP5110_exp_report_main_link" url="$[/request/@context_path]/expm/EXP5110/exp_report_main.screen"/>
        <a:link id="EXP5110_seeFile_link" url="$[/request/@context_path]/app/APP2030/app_uploadFile.screen"/>
        <a:link id="EXP5110_document_history_view_link" url="$[/request/@context_path]/expm/public/exp_document_history_query.screen"/>
        <a:link id="EXP5110_exp_report_maintain_create_from_req_link" url="$[/request/@context_path]/modules/expm/EXP5110/exp_report_maintain_create_from_req.screen"/>
        <a:link id="EXP5110_audit_report_submit_link" url="$[/request/@context_path]/modules/expm/EXP5110/exp_update_invoice_usede_save.svc"/>
        <style><![CDATA[
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

        	#reportview_placeholder{
			    height:45px;
			}
        ]]></style>
        <script><![CDATA[
            var headerRecord;
            var submitFlag = false;
            var close_id = this.__host.id;

            function EXP5110_onMaintainReady() {
                headerRecord = $au('EXP5110_exp_report_header_ds').getAt(0);

                //对头上的申请单编号进行处理,申请单编号的输入框点击时，打开申请单的只读页面
                if ($A.CmpManager.get('EXP5110_expReqNumberLov')) {
                    //如果存在申请单编号的lov，说明当前单据是头关联申请的，将对应的编辑器设置css，并设置点击事件
                    EXP5110_setExpReqNumberLink();
                }
            }

            function EXP5110_setExpReqNumberLink() {
                var lovDom = Ext.get('EXP5110_expReqNumberLov');
                lovDom['on']('click', function(evt) {
                    var headerRecord = $au('EXP5110_exp_report_header_ds').getAt(0);
                    var exp_requisition_header_id = headerRecord.get('expRequisitionHeaderId');
                    //Modify lcy 2018/5/18 13:24:12    直接打开申请单只读页面查看
                    var req_readonly_service_name = '$[/model/header_info/records/record/@reqReadonlyServiceName]';
                    new Aurora.Window({
                        title: '',
                        id: 'EXP5110_exp_report_create_from_req_renderer_window',
                        url: '$[/request/@context_path]/' + req_readonly_service_name + '?expRequisitionHeaderId=' + exp_requisition_header_id,
                        fullScreen: true
                    });

                });
            }

            function EXP5110_jumpMain() {
                window.location.href = $au('EXP5110_exp_report_main_link').getUrl();
            }

            function EXP5110_return() {
                //添加判断，解决单据支付，返回按钮无效问题     Y.duan  2017-8-28 15:23:32
                // if (Aurora.CmpManager.get('exp_report_view_main_screen')) {
                // $au('exp_report_view_main_screen').close();
                // } else if (Aurora.CmpManager.get('EXP5110_exp_report_view_main_window')) {
                // $au('EXP5110_exp_report_view_main_window').close();
                // } else if (Aurora.CmpManager.get('document_details_win')) {
                // $au('document_details_win').close();
                // } else if (Aurora.CmpManager.get('exp_rep_readonly_win')) {
                // $au('exp_rep_readonly_win').close();
                // } else if (Aurora.CmpManager.get('exp_report_maintain_screen')) {
                // $au('exp_report_maintain_screen').close();
                // } else if (Aurora.CmpManager.get('return_window')) {
                // $au('return_window').close();
                // } else if (Aurora.CmpManager.get('to_do_list_window')) {
                // $au('to_do_list_window').close();
                // } else if (Aurora.CmpManager.get('notification_window')) {
                // $au('notification_window').close();
                // } else if (Aurora.CmpManager.get('apprecord')) {
                // $au('apprecord').close();
                // } else if (Aurora.CmpManager.get('work_flow_involved_document_win')) {
                // $au('work_flow_involved_document_win').close();
                // } else if (Aurora.CmpManager.get('exp_report_view_main_window')) {
                // $au('exp_report_view_main_window').close();
                // } else if (Aurora.CmpManager.get('exp_report_view_main_window')) {
                // $au('exp_report_view_main_window').close();
                // }else if (Aurora.CmpManager.get('CSH1290_exp_report_view_main_screen')){
                // $au('CSH1290_exp_report_view_main_screen').close();
                // }else if (Aurora.CmpManager.get('CSH1280_exp_report_view_main_screen')){
                // $au('CSH1280_exp_report_view_main_screen').close();
                // }
                $au(this.__host.id).close();
            }

            function EXP5110_attachment() {
                var record = $au('EXP5110_exp_report_header_ds').getAt(0);
                new Aurora.Window({
                    url: $au('EXP5110_seeFile_link').getUrl() + '?tableName=EXP_REPORT_HEADERS&tablePkValue=' + record.get('expReportHeaderId'),
                    title: '$[l:atm.upload_attachment]',
                    id: 'check_upload_file_screen',
                    width: 600,
                    height: 400
                });
            }

            function EXP5110_trackReport() {
                var record = $au('EXP5110_exp_report_header_ds').getAt(0);
                new Aurora.Window({
                    url: $au('EXP5110_document_history_view_link').getUrl() + '?documentType=EXP_REPORT&documentId=' + record.get('expReportHeaderId'),
                    id: 'CSH5010_document_history_view_window',
                    side: 'right',
                    width: 1020
                });
            }
            //add lcy 2018/3/20 15:28:12 新增保存逻辑

            function EXP5110_pay_back_saveReport() {
                var headerDs = $au('EXP5110_exp_report_header_ds');
                if (!headerDs.isModified()) {
                    headerDs.data[0].dirty = true;
                }
                $au('EXP5110_exp_report_header_ds').submit();
            }
            // //add lcy 2018/3/20 15:28:12 新增保存成功

            function EXP5110_pay_back_onHeaderSubmitSuccess() {
                if (submitFlag) {
                    EXP5110_submitReport();
                    submitFlag = false;
                }
            }
            //add lcy 2018/3/20 15:28:12 新增提交逻辑

            function EXP5110_submitReport() {
                //报销单未保存首先执行保存
                if (!submitFlag) {
                    EXP5110_pay_back_saveReport();
                    submitFlag = true;
                    return;
                }
                submitFlag = false;
                Aurora.request({
                    lockMessage: '$[l:exp_report.submitting]',
                    url: $au('EXP5110_back_submit_exp_report_link').getUrl(),
                    para: {
                        exp_report_header_id: $au('EXP5110_exp_report_header_ds').getAt(0).get('expReportHeaderId')

                    },
                    success: function(resp) {
                        // modify lcy 2018/3/27 16:56:21  $au(this.__host.id).close();的方法在这里无效，改成下面的方法
                        $au(close_id).close();
                    }
                });
            }
            //【报销单审核】功能-更新发票用途  Y.duan 2018-8-22 14:14:26

            function EXP5110_audit_saveReport() {
                if ($au('EXP5110_exp_report_standard_line_ds').validate()) {
                    var data = $au('EXP5110_exp_report_header_ds').getJsonData(false);
                    Aurora.request({
                        lockMessage: '$[l:exp_report.submitting]',
                        url: $au('EXP5110_audit_report_submit_link').getUrl(),
                        para: data,
                        success: function(resp) {
                            $au(close_id).close();
                        }
                    });
                }
            }

            function EXP5110_viewonReqNumberFocusFun() {
                $au('EXP5110_exp_report_header_ds').getAt(0).getField('expRequisitionNumber').setLovPara('paymentCurrencyCode', $au('EXP5110_exp_report_header_ds').getAt(0).get('paymentCurrencyCode'));
            }
            Aurora.onReady(EXP5110_onMaintainReady);
        ]]></script>
        <a:dataSets>
            <a:dataSet id="EXP5110_paymentObjectDs" autoQuery="true" loadData="true" queryUrl="$[/request/@context_path]/common/auroraCode/PAYMENT_OBJECT"/>
            <a:dataSet id="EXP5110_currencyDs" autoQuery="true" loadData="true" queryUrl="$[/request/@context_path]/gld-currency/getGldCurrencyOption"/>
            <a:dataSet id="EXP5110_reportPageElementDs">
                <a:datas dataSource="/model/elements/records"/>
            </a:dataSet>
            <a:dataSet id="EXP5110_exp_report_header_ds" autoCreate="true"
                       submitUrl="$[/request/@context_path]/exp/report-header/saveHeader">
                <a:datas dataSource="/model/header_info/records"/>
                <a:fields>
                    <a:field name="expReportHeaderId" readOnly="true"/>
                    <a:field name="employeeName" prompt="exp_report_header.employee_name" readOnly="true"
                             required="true"/>
                    <a:field name="employeeId" readOnly="true"/>
                    <a:field name="payeeId" readOnly="true"/>
                    <a:field name="payeeName" autoComplete="true" lovCode="LOV_GLD_PAYEE" lovAutoQuery="true"
                             prompt="exp_report_header.payee_id" required="true" title="exp_report_header.payee_id" readOnly="true">
                        <a:mapping>
                            <a:map from="payeeName" to="payeeName"/>
                            <a:map from="payeeId" to="payeeId"/>
                        </a:mapping>
                    </a:field>
                    <a:field name="payeeCategory" readOnly="true"/>
                    <a:field name="payeeCategoryName" displayField="code_value_name" options="EXP5110_paymentObjectDs"
                             prompt="exp_report_header.partner_category" returnField="payeeCategory"
                             valueField="code_value" readOnly="true"/>
                    <a:field name="expReportNumber" prompt="exp_report_header.exp_report_number" readOnly="true"/>
                    <a:field name="moExpReportTypeName" prompt="exp_report_header.exp_report_type" readOnly="true"
                             required="true"/>
                    <a:field name="moExpReportTypeId" readOnly="true"/>
                    <a:field name="reportDate" prompt="exp_report_header.requisition_date" required="true" readOnly="true" datatype="date"/>
                    <a:field name="createdByName" prompt="exp_report_header.created_by_name" readOnly="true"
                             required="true"/>
                    <a:field name="paymentCurrencyName" displayField="currencyName"
                             prompt="exp_report_header.payment_currency_name" readOnly="true" required="true"
                             returnField="paymentCurrencyCode" valueField="currencyCode"/>
                    <a:field name="paymentCurrencyCode" readOnly="true"/>
                    <a:field name="repTotalAmount" prompt="exp_report_header.sum_amount" readOnly="true" />
                    <a:field name="unitId" readOnly="true"/>
                    <a:field name="unitName"
                             lovCode="EXP_UNIT_AND_POSITION_LOV?employeeId=$[/model/header_info/records/record/@employeeId]&amp;accEntityId=$[/model/header_info/records/record/@accEntityId]"
                             prompt="exp_report_header.unit_id" required="true" title="exp_report_header.unit_id" readOnly="true">
                        <a:mapping>
                            <a:map from="unitId" to="unitId"/>
                            <a:map from="unitName" to="unitName"/>
                            <a:map from="positionId" to="positionId"/>
                            <a:map from="defaultBgtEntityId" to="bgtEntityId"/>
                            <a:map from="defaultBgtCenterId" to="bgtCenterId"/>
                            <a:map from="defaultRespCenterId" to="respCenterId"/>
                            <a:map from="defaultAccEntityId" to="accEntityId"/>
                            <a:map from="defaultAccEntityName" to="accEntityName"/>
                        </a:mapping>
                    </a:field>
                    <a:field name="positionName" prompt="exp_report_header.position_name" readOnly="true"/>
                    <a:field name="pay2funExchangeTypeName" prompt="exp_report_header.pay2fun_exch_rate_name" readOnly="true"/>
                    <a:field name="pay2funExchangeType" readOnly="true"/>
                    <a:field name="pay2funExchangeRate" prompt="exp_report_header.pay2fun_exch_rate"
                             validator="EXP5110_pay2funExchangeRateValidator" readOnly="true"/>
                    <a:field name="pay2magExchangeTypeName" prompt="exp_report_header.pay2mag_exch_rate_name" readOnly="true"/>
                    <a:field name="pay2magExchangeType" readOnly="true"/>
                    <a:field name="pay2magExchangeRate" prompt="exp_report_header.pay2mag_exch_rate"
                             validator="EXP5110_pay2magExchangeRateValidator" readOnly="true"/>
                    <a:field name="reportStatusName" prompt="exp_report_header.report_status_name"
                             required="true" readOnly="true"/>
                    <a:field name="attachmentNum" prompt="exp_report_header.attachment_num" readOnly="true"/>
                    <a:field name="description" prompt="exp_report_header.description" readOnly="true"/>
                    <a:field name="accEntityId" readOnly="true"/>
                    <a:field name="accEntityName" prompt="exp_report_header.acc_entity_name" readOnly="true"/>
                    <a:field name="accCenterId" readOnly="true"/>
                    <a:field name="accCenterName" readOnly="true"/>
                    <a:field name="bgtEntityId" readOnly="true"/>
                    <a:field name="bgtEntityName" readOnly="true"/>
                    <a:field name="bgtCenterId" readOnly="true"/>
                    <a:field name="bgtCenterName" readOnly="true"/>
                    <a:field name="periodName" readOnly="true"/>
                    <a:field name="paymentMethodId" readOnly="true"/>
                    <a:field name="paymentMethodName" readOnly="true"/>
                    <a:field name="vatInvoiceFlag" checkedValue="Y" defaultValue="Y"
                             prompt="exp_report_header.vat_invoice_flag" uncheckedValue="N" readOnly="true"/>
                    <#list header_info as header>
                        <#if header.relationMethodCode == 'HEAD'>
                            <a:field name="expRequisitionNumber" lovUrl="$[/request/@context_path]/expm/EXP5110/exp_report_from_req_header_lov.screen?moExpReportTypeId=${header.moExpReportTypeId}" lovWidth="880" prompt="PUR_PURCHASE_ORDER_TYPES.REQUISITION_FLAG" required="${header.reqRequired}" title="REQUISITION_ENQUIRY" readOnly="true">
                                <a:mapping>
                                    <a:map from="expRequisitionNumber" to="expRequisitionNumber"/>
                                    <a:map from="expRequisitionHeaderId" to="expRequisitionHeaderId"/>
                                </a:mapping>
                            </a:field>
                            <a:field name="expRequisitionHeaderId" readOnly="true"/>
                        </#if>
                    </#list>
                    <#list headerDimensionFields as DimensionField>
                        <#if DimensionField != null>
                            <a:field name="${DimensionField.returnField}"
                                     defaultValue="${DimensionField.defaultDimValueId}" readOnly="true"/>
                            <a:field name="${DimensionField.displayField}"
                                     defaultValue="${DimensionField.defaultDimValueName}"
                                     prompt="${DimensionField.dimensionName}" title="${DimensionField.dimensionName}"
                                     required="true" lovCode="EXP_DIMENSION_VALUE_LOV?companyLevel=${DimensionField.companyLevel}&amp;dimensionId=${DimensionField.dimensionId}" readOnly="true">
                                <a:mapping>
                                    <a:map from="dimensionValueId" to="${DimensionField.returnField}"/>
                                    <a:map from="dimensionValueName" to="${DimensionField.displayField}"/>
                                </a:mapping>
                            </a:field>
                            <a:field name="companyLevel" defaultValue="${DimensionField.companyLevel}" readOnly="true"/>
                        </#if>
                    </#list>
                    <#list defaultObjectFields as ObjectField>
                        <a:field name="${ObjectField.returnField}" defaultValue="${ObjectField.defaultMoObjectId}" readOnly="true"/>
                        <#if ObjectField.expenseObjectMethod == "VALUE_LIST">
                            <a:field name="${ObjectField.displayField}"
                                     lovCode="LOV_OBJECT_VALUE?moExpObjTypeId=${ObjectField.moExpObjTypeId}"
                                     defaultValue="${ObjectField.defaultMoObjectName}"
                                     prompt="${ObjectField.moExpObjTypeName}" title="${ObjectField.moExpObjTypeName}"
                                     required="${ObjectField.requiredFlag}" readOnly="true">
                                <a:mapping>
                                    <a:map from="id" to="${ObjectField.returnField}"/>
                                    <a:map from="name" to="${ObjectField.displayField}"/>
                                </a:mapping>
                            </a:field>
                        </#if>
                        <#if ObjectField.expenseObjectMethod == "SQL_TEXT">
                            <a:field name="${ObjectField.displayField}"
                                     lovCode="LOV_OBJECT_SQL_VALUE?sqlQuery=${ObjectField.sqlQuery}"
                                     defaultValue="${ObjectField.defaultMoObjectName}"
                                     prompt="${ObjectField.moExpObjTypeName}" title="${ObjectField.moExpObjTypeName}"
                                     required="${ObjectField.requiredFlag}" readOnly="true">
                                <a:mapping>
                                    <a:map from="id" to="${ObjectField.returnField}"/>
                                    <a:map from="name" to="${ObjectField.displayField}"/>
                                </a:mapping>
                            </a:field>
                        </#if>
                    </#list>
                </a:fields>
                <a:events>
                    <a:event name="submitsuccess" handler="EXP5110_pay_back_onHeaderSubmitSuccess"/>
                </a:events>
            </a:dataSet>
        </a:dataSets>
        <!--add by chao.dai 2019-01-17 将报销单的按钮上移，防止滑动滚动条时按钮跟随滑动-->
        <a:screenTopToolbar style="position:fixed;left:1px;right:1px;height:34px;z-index:100;border-bottom:1px solid #C7C7C7;background-color:#FFFFFF;padding-left: 10px;">
            <div class="clearfix" style="width:100%;height:100%;">
                <div id="returnDiv">
                    <a:button id="EXP5110_returnBtn" click="EXP5110_return" style="margin-top:8px;margin-right:10px;float:right;" text="HAP_BACK"/>
                </div>
                <a:button id="EXP5110_trackBtn" click="EXP5110_trackReport" style="margin-top:8px;margin-right:10px;float:right;" text="HAP_TRACKING_DOCUMENTS"/>
                <a:button id="EXP5110_attachmentBtn" click="EXP5110_attachment" style="margin-top:8px;margin-right:10px;float:right;" text="HAP_ATTACHMENT_DOCUMENTATION"/>
<#--                <div id="auditSaveDiv">-->
<#--                    <a:button id="EXP5110_auditSaveBtn" click="EXP5110_audit_saveReport" style="margin-top:8px;margin-right:10px;float:right;" text="HAP_SAVE_DRAFT"/>-->
<#--                </div>-->
                <div id="saveExpDiv">
                    <a:button id="EXP5110_submitBtn" click="EXP5110_submitReport" style="margin-top:8px;margin-right:10px;float:right;" text="HAP_SUBMIT_APPROVAL"/>
                    <a:button id="EXP5110_saveBtn" click="EXP5110_pay_back_saveReport" style="margin-top:8px;margin-right:10px;float:right;" text="HAP_SAVE_DRAFT"/>
                </div>
                <span style="margin-top:15px;margin-left：5px;font-size:1.05em;display:block;font-weight: 600;"><![CDATA[$[/model/header_info/records/record/@moExpReportTypeName] ]]></span>
            </div>
        </a:screenTopToolbar>
        <div id="reportview_placeholder"/>
        <a:screenBody>
            <a:form column="1" marginWidth="35" title=" ">
                <a:box column="4" style="width:100%;">
                    <a:textField name="employeeName" bindTarget="EXP5110_exp_report_header_ds"/>
                    <a:textField name="expReportNumber" bindTarget="EXP5110_exp_report_header_ds"/>
                    <a:textField name="moExpReportTypeName" bindTarget="EXP5110_exp_report_header_ds"/>
                    <a:textField name="reportDate" bindTarget="EXP5110_exp_report_header_ds" renderer="Aurora.formatDate"/>
                    <a:textField name="createdByName" bindTarget="EXP5110_exp_report_header_ds"/>
                    <a:textField name="paymentCurrencyName" bindTarget="EXP5110_exp_report_header_ds"/>
                    <a:textField name="reportStatusName" bindTarget="EXP5110_exp_report_header_ds"/>
                    <#list header_info as header>
                        <#if header.taxpayerType == "GENERAL_TAXPAYER">
                            <a:checkBox name="vatInvoiceFlag" bindTarget="EXP5110_exp_report_header_ds"
                                        prompt="exp_report_header.vat_invoice_flag"/>
                        </#if>
                        <#if header.relationMethodCode == 'HEAD'>
                            <a:lov name="expRequisitionNumber" id="EXP5110_expReqNumberLov" bindTarget="EXP5110_exp_report_header_ds" className="reqnumberlov">
                                <a:events>
                                    <a:event name="focus" handler="EXP5110_onReqNumberFocusFun"/>
                                </a:events>
                            </a:lov>
                        </#if>
                    </#list>
                    <#list defaultObjectFields as ObjectField>
                        <#if ObjectField.expenseObjectMethod == "DESC_TEXT">
                            <a:textField name="${ObjectField.displayField}" bindTarget="EXP5110_exp_report_header_ds"/>
                        <#else>
                            <a:lov name="${ObjectField.displayField}" bindTarget="EXP5110_exp_report_header_ds"/>
                        </#if>
                    </#list>
                    <#list headerDimensionFields as DimensionField>
                        <#if DimensionField != null>
                            <a:lov name="${DimensionField.displayField}" bindTarget="EXP5110_exp_report_header_ds">
                                <a:events>
                                    <a:event name="focus" handler="EXP5110_headerDimensionFunc"/>
                                </a:events>
                            </a:lov>
                        </#if>
                    </#list>
                </a:box>
                <a:box style="width:100%;margin-top:-5px;">
                    <a:textArea name="description" bindTarget="EXP5110_exp_report_header_ds" calcWidth="calc((100%  + 90px) / 4 * 3 + 150px)" width="800"/>
                </a:box>
            </a:form>
            <a:vBox id="EXP5110_view_place" padding="0" showMargin="false" style="">
                <#list elements as element>
                    <#if element!=null && element.readonlyServiceName??>
                        <a:screen-include screen="/WEB-INF/view/${element.readonlyServiceName}"/>
                    </#if>
                </#list>
            </a:vBox>
        </a:screenBody>
        <script><![CDATA[
            //add lcy 2018/3/20 15:28:12 初始化判断财务退回则可以修改描述字段

            function init() {
                var ds = $au('EXP5110_exp_report_header_ds').getAt(0);
                var doc_status = ds.get('docStatus');

                if (doc_status == 'RETURN_BACK') {
                    ds.getField('description').setReadOnly(false);
                }
                // add lcy 2018/3/20 15:28:12 增加按钮隐藏逻辑
                //var saveDiv = Ext.get('saveExpDiv');
                if (doc_status == 'RETURN_BACK' || doc_status == 'PAY_BACK') {
                    //saveDiv.parent().setStyle('display', 'table-cell');
                    document.getElementById("saveExpDiv").style.display = "block";
                } else {
                    //saveDiv.parent().setStyle('display', 'none');
                    document.getElementById("saveExpDiv").style.display = "none";
                }
                // // 【报销单审核】增加保存按钮隐藏逻辑  Y.duan 2018-8-24 09:08:36
                // var audit_option = '$[/parameter/@auditOption]';
                // //var auditSaveDiv = Ext.get('auditSaveDiv');
                // if (audit_option == 'Y') {
                //     //auditSaveDiv.parent().setStyle('display', 'table-cell');
                //     document.getElementById("auditSaveDiv").style.display = "block";
                // } else {
                //     //auditSaveDiv.parent().setStyle('display', 'none');
                //     document.getElementById("auditSaveDiv").style.display = "none";
                // }

                // 【报销单审批】增加保存按钮隐藏逻辑  LiLiang 2018-10-8
                // 在审批时加载单据头的时候隐藏返回按钮
                //var EXP5110_returnBtn = Ext.get('EXP5110_returnBtn');
                if (this.__host.id != 'to_do_list_window') {
                    //EXP5110_returnBtn.parent().setStyle('display', 'table-cell');
                    document.getElementById("returnDiv").style.display = "block";
                } else {
                    //EXP5110_returnBtn.parent().setStyle('display', 'none');
                    document.getElementById("returnDiv").style.display = "none";
                }
            }
            init();
        ]]></script>
    </a:view>
</a:screen>
