<?xml version="1.0" encoding="UTF-8"?>
<a:screen xmlns:a="http://www.aurora-framework.org/application" trace="true">
    <a:view>
        <a:link id="WFL2010_approveLink" url="$[/request/@context_path]/wfl/todo/approve"/>
        <a:link id="WFL2010_sendBackLink" url="$[/request/@context_path]/wfl/todo/send-back"/>
        <a:link id="WFL2010_sendToLink" url="$[/request/@context_path]/wfl/todo/send-to"/>
        <a:link id="WFL2010_addTaskLink" url="$[/request/@context_path]/wfl/todo/add-task"/>
        <a:link id="WFL2010_deliverLink" url="$[/request/@context_path]/wfl/public/wfl_deliver.screen"/>
        <a:link id="WFL2010_addApproverLink" url="$[/request/@context_path]/wfl/public/wfl_add_approver.screen"/>
        <script><![CDATA[
        function WFL2010_doAction(evt) {
            var strArray = evt.id.split('-');
            var actionType = strArray[1]
            var actionId = strArray[2];

            if (actionType == 'AGREE' || actionType == 'REJECT') {
                // 如果当前动作类型为 同意 或者 拒绝
                var record = $au('approveDs').getAt(0);
                record.set('actionId', actionId);
                $au('approveDs').setSubmitUrl($au('WFL2010_approveLink').getUrl());
                $au('approveDs').submit();
            } else if (actionType == 'SEND_BACK') {
                // 如果当前动作类型为 退回
                var record = $au('approveDs').getAt(0);
                record.set('actionId', actionId);
                $au('approveDs').setSubmitUrl($au('WFL2010_sendBackLink').getUrl());
                $au('approveDs').submit();
            } else if (actionType == 'SEND_TO') {
                // 如果当前动作类型为 跳转节点
                var record = $au('approveDs').getAt(0);
                record.set('actionId', actionId);
                $au('approveDs').setSubmitUrl($au('WFL2010_sendToLink').getUrl());
                $au('approveDs').submit();
            } else if (actionType == 'ADD_TASK') {
                // 如果当前动作类型为 添加任务
                var record = $au('approveDs').getAt(0);
                record.set('actionId', actionId);
                $au('approveDs').setSubmitUrl($au('WFL2010_addTaskLink').getUrl());
                $au('approveDs').submit();
            } else if (actionType == 'DELIVER') {
                // 如果当前动作类型为 转交
                new Aurora.Window({
                    id: 'WFL2010_deliverWindow',
                    url: $au('WFL2010_deliverLink').getUrl() + '?instanceId=$[/parameter/@instanceId]&insRecipientId=$[/parameter/@insRecipientId]&actionId=' + actionId,
                    width: 500,
                    height: 300
                });
            } else if (actionType == 'ADD_APPROVER') {
                // 如果当前动作类型为 添加审批人
                new Aurora.Window({
                    id: 'WFL2010_addApproverWindow',
                    url: $au('WFL2010_addApproverLink').getUrl() + '?instanceId=$[/parameter/@instanceId]&insRecipientId=$[/parameter/@insRecipientId]&actionId=' + actionId,
                    width: 500,
                    height: 520
                });
            }
        }

        ]]></script>
        <a:dataSets>
            <a:dataSet id="approveDs" autoCreate="true">
                <a:fields>
                    <a:field name="insTaskRecipientId"/>
                    <a:field name="actionId"/>
                    <a:field name="commentText" prompt="wfl_approve.comment_text"/>
                </a:fields>
            </a:dataSet>
        </a:dataSets>
        <a:screenBody>
            <a:form title="wfl_approve.comment_text">
                <a:textArea bindTarget="approveDs" name="commentText" calcWidth="calc(100% - 150px)" width="800"
                            height="80"/>
            </a:form>
            <a:hBox>
            <#list actionList as action>
                <a:button text="${action.actionName}" click="WFL2010_doAction"
                          id="action-${action.actionType}-${action.actionId}"/>
            </#list>
            </a:hBox>
            <a:screen-include screen="/WEB-INF/view/wfl/public/wfl_view.screen"/>
        </a:screenBody>
    </a:view>
</a:screen>