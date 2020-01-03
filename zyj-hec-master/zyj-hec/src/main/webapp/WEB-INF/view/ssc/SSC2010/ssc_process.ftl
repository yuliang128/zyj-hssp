<?xml version="1.0" encoding="UTF-8"?>
<a:screen xmlns:a="http://www.aurora-framework.org/application" trace="true" xmlns:p="uncertain.proc">
    <a:init-procedure>
        <p:echo/>
    </a:init-procedure>
    <a:view>
        <a:link id="SSC2010_upload_file_link" url="$[/request/@context_path]/uploadFile.screen"/>
        <a:link id="SSC2010_do_process_link" url="$[/request/@context_path]/ssc/process/do-process"/>
        <!--add by caoke/2018-05-28 拒绝页面-->
        <a:link id="SSC2010_document_dispatch_reject_link" url="$[/request/@context_path]/ssc/SSC2010/ssc_document_dispatch_reject.screen"/>
        <style>
            #approve_div{
			    position:fixed;
			    left:1px;
			    width:100%;
			    z-index:0;
			    background-color:#c9e0e6;
			}
			
			#approve_area{
			    
			}
			
			#approve_area table{
			    border:none;
			}
			
			#approve_area td{
			    border:none;
			    background:none;
			}
			
			#approve_placeholder{
			    height:50px;
			}
			
			#toggle_comment_btn{
			    display:block;
			    min-width:35px;
			    height:26px;
			    padding-left:10px;
			    padding-right:10px;
			    line-height:26px;
			    text-align:center;
			    border:1px solid #C7C7C7;
			    background-color:#FFFFFF;
			    font-size:14px;
			    font-weight:bold;
			}
			
			#toggle_comment_btn:hover{
			    color:#005A78;
			    background-color:#F7F7F7;
			}
			
			.deal_btn{
			    display:block;
			    min-width:35px;
			    padding-left:10px;
			    padding-right:10px;
			    height:26px;
			    line-height:26px;
			    text-align:center;
			    border:1px solid #C7C7C7;
			    background-color:#FFFFFF;
			    font-size:14px;
			    font-weight:bold;
			    margin-left:5px;
			}
			
			.deal_btn:hover{
			    color:#005A78;
			    background-color:#F7F7F7;
			}
			
			#comments_placeholder{
			    width:340px;
			}
        </style>
        <script><![CDATA[
            function SSC2010_processAction(e) {
                var actionId = Ext.get(e.el).parent('table.item-btn').id.replace('process-btn-', '').split('-')[1];
                var actionType = Ext.get(e.el).parent('table.item-btn').id.replace('process-btn-', '').split('-')[0];
                Aurora.Masker.mask(Ext.getBody(), '$[l:hap_handle_waiting]');
                // if (actionType == 'APPROVE') {
                    if ($au('EXP5110_exp_report_standard_line_ds').isModified()) {
                        Aurora.showMessage('$[l:prompt]', '$[l:please_save_invoice_usage]');
                    } else {
                        Aurora.request({
                            lockMesage: '处理中，请稍候...',
                            url: $au('SSC2010_do_process_link').getUrl(),
                            para: {
                                dispatchRecordId: '$[/parameter/@dispatchRecordId]',
                                actionId: actionId,
                                opinion: $au('SSC2010_opinion_ds').getAt(0).get('opinion')
                            },
                            success: function() {
                                $au('SSC2010_my_task_result_ds').query();
                                $au('SSC2010_my_task_hold_ds').query();
                                $au('SSC2010_my_task_return_back_ds').query();
                                $au('SSC2010_ssc_process_window').close();
                            }
                        });
                    }
                // } else {
                //     var opinion = $au('SSC2010_opinion_ds').getAt(0).get('opinion');
                //
                //     if (!Ext.isEmpty(opinion)) {
                //         //拒绝
                //         if (actionType == 'REJECT') {
                //             var dispatchRecordId = Number('$[/parameter/@dispatchRecordId]');
                //             var url = $au('SSC2010_document_dispatch_reject_link').getUrl() + '?dispatchRecordId=' + dispatchRecordId + '&actionId=' + actionId;
                //             new Aurora.Window({
                //                 url: url,
                //                 title: '$[l:PROMPT.AUDIT_REJECT]',
                //                 id: 'SSC2010_document_dispatch_reject_window',
                //                 width: 680,
                //                 height: 420
                //             });
                //         } else {
                //             Aurora.request({
                //                 lockMesage: '处理中，请稍候...',
                //                 url: $au('SSC2010_do_process_link').getUrl(),
                //                 para: {
                //                     dispatchRecordId: '$[/parameter/@dispatchRecordId]',
                //                     action_id: actionId,
                //                     opinion: $au('SSC2010_opinion_ds').getAt(0).get('opinion')
                //                 },
                //                 success: function() {
                //                     $au('SSC2010_my_task_result_ds').query();
                //                     $au('SSC2010_my_task_hold_ds').query();
                //                     $au('SSC2010_my_task_return_back_ds').query();
                //                     $au('SSC2010_ssc_process_window').close();
                //                 }
                //             });
                //         }
            
            
                    } else {
                        new Aurora.showMessage('$[l:prompt]', '$[l:enter_operational_opinion]');
                    }
                }
                Aurora.Masker.unmask(Ext.getBody());
            }
            
            function SSC2010_processReturn() {
                $au('SSC2010_ssc_process_window').close();
            }
            
            function SSC2010_viewAttachment() {
                var url = $au('SSC2010_upload_file_link').getUrl() + '?tableName=' + '$[/model/docInfo/records/record/@tableName]' + '&headerId=' + '$[/model/docInfo/records/record/@docId]';
                new Aurora.Window({
                    url: url,
                    title: '$[l:atm.upload_attachment]',
                    id: 'check_upload_file_screen',
                    width: 600,
                    height: 400
                });
            }
        ]]></script>
        <a:dataSets>
            <a:dataSet id="SSC2010_opinion_ds" autoCreate="true"/>
            <a:dataSet id="SSC2010_process_ds" fetchAll="true" loadData="true" autoQuery="true" queryUrl="$[/request/@context_path]/ssc/process/process-query?dispatchRecordId=$[/parameter/@dispatchRecordId]"/>
        </a:dataSets>
        <a:screenTopToolbar style="position:fixed;top:26px;left:5px;right:25px;height:45px;z-index:9999; border-bottom:1px solid #C7C7C7; background-color:#FFFFFF">
            <div class="clearfix" style="width:100%;height:100%;">
                <a:box padding="0" style="margin-top:5px;float:left;">
                    <a:textArea name="opinion" id="opinion" bindTarget="SSC2010_opinion_ds" height="30" width="300"/>
                </a:box>
    		    <#list actions as action >
                    <td>
                        <a:button id="process-btn-${action.actionType}-${action.actionId}" actiontype="${action.actionType}" click="SSC2010_processAction" text="${action.actionName}" style="margin-top:8px;margin-left:10px;float:left;"/>
                    </td>
                </#list>
    	<a:button click="SSC2010_viewAttachment" text="prompt.add_attachment" style="margin-top:8px;margin-left:10px;float:left;"/>
    	<a:button click="SSC2010_processReturn" text="hap_back" style="margin-top:8px;margin-left:10px;float:left;"/>
        ]]></a:freeMarker>
            </div>
        </a:screenTopToolbar>
        <div id="approve_placeholder"/>
        <a:screen-include screen="$[/model/@processService]=$[/model/docInfo/records/record/@doc_id]&amp;audit_option=Y"/>
        <a:form marginWidth="15" padding="0" showmargin="false" style="margin-top:10px;" title="wfl_workflow_approve.comment">
            <a:grid bindTarget="SSC2010_process_ds" marginWidth="15" style="border:none">
                <a:columns>n
                    <a:column name="processType" align="center" prompt="ssc_process.process_type" width="80"/>
                    <a:column name="processTime" Width="120" align="center" prompt="ssc_process.process_time" renderer="Aurora.formatDateTime"/>
                    <a:column name="nodeName" Width="120" align="center" prompt="ssc_process.node_name"/>
                    <a:column name="actionName" align="center" prompt="ssc_process.action_name" width="120"/>
                    <a:column name="employeeName" Width="120" align="center" prompt="ssc_process.employee_name"/>
                    <a:column name="workTeamName" align="center" prompt="ssc_process.work_team_name" width="120"/>
                    <a:column name="processOpinion" prompt="ssc_process.process_opinion" width="310"/>
                </a:columns>
            </a:grid>
        </a:form>
    </a:view>
</a:screen>
