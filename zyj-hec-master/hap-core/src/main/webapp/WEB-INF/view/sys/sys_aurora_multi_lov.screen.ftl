<?xml version="1.0" encoding="UTF-8"?>
<a:screen xmlns:a="http://www.aurora-framework.org/application" trace="true" xmlns:p="uncertain.proc">
    <a:init-procedure>
        <p:echo/>
    </a:init-procedure>
    <a:parameter>
        <a:param DataType="java.lang.String" Name="ORDER_FIELD"/>
        <a:param DataType="java.lang.String" Name="ORDER_TYPE"/>
    </a:parameter>
    <a:view>
        <div/>
        <script language="JavaScript"><![CDATA[
        var showCode = '$[/parameter/@showCode]';
        var showId = '$[/parameter/@showId]';
        function _onLovRecordsCommit() {
            var record = $au('SYS_LOV_RETURN_DS').getAll();
            $au('$[/parameter/@lovid]').commit(record);
        }

        function _lovquery() {
            $au('SYS_LOV_RESULT_DS').query();
        }

        function removeRecord(ds, record, isSelectAll) {
            var recId = record.get(showId);
            var reRecords = $au('SYS_LOV_RETURN_DS').getAll();
            for (var i = 0;i < reRecords.length;i++) {
                var reFunId = reRecords[i].get(showId);
                if (recId.toString() === reFunId.toString()) {
                    $au('SYS_LOV_RETURN_DS').data.remove(reRecords[i]);
                    var grid3 = document.getElementById("_superlov_autogrid3");
                    var arr = grid3.getElementsByTagName('span');
                    for (var j = 0;j < arr.length;j++) {
                        if (arr[j].getAttribute("recordid").toString() === reFunId.toString()) {
                            var navigatorName = "Microsoft Internet Explorer";
                            if(navigator.appName == navigatorName){
                                arr[j].removeNode(true);
                            }else{
                                arr[j].remove();
                            }
                        }
                    }
                }
            }
        }

        function compareDsRecord(recId) {
            var returnRecord = $au('SYS_LOV_RETURN_DS').getAll();
            for (var i = 0;i < returnRecord.length;i++) {
                var returnId = returnRecord[i].get(showId);
                if (recId.toString() === returnId.toString()) {
                    return false;
                }
            }
            return true;
        }

        function addRecord(ds, record, isSelectAll) {
            var returnDs = $au('SYS_LOV_RETURN_DS');
            var returnRecords = $au('SYS_LOV_RETURN_DS').getAll();
            if (returnRecords.length === 0) {
                returnDs.add(record);
                record.isNew = false;
            } else {
                var recId = record.get(showId);
                if (this.compareDsRecord(recId)) {
                    returnDs.add(record);
                    record.isNew = false;
                }
            }
            for (var i = 0;i < returnRecords.length;i++) {
                var returnId = returnRecords[i].get(showId);
                var returnName = returnRecords[i].get(showCode);
                if (this.compareRecord(returnId, record)) {
                    if(returnName!==""){
                        var nameAll = '';
                        nameAll += "<span recordId='"+returnId+ "' style='background:#fff;border:#ccc 1px solid;float:left;padding:2px;padding-right:16px;margin-left:10px;margin-top:5px;margin-bottom:5px;position:relative;font-size:12px;border-radius:3px;'>" + returnName + "<div id='close' style='width:9px;height:9px;color:#eee;position:absolute;top:6px;right:1px;cursor:pointer;' onclick='_removeRecords(this)'></div></span>";
                        $('#_superlov_autogrid3').append(nameAll);
                    }
                }
            }
        }

        function _removeRecords(obj) {
            var par = obj.parentNode;
            var recId = par.getAttribute("recordid");
            var ds = $au('SYS_LOV_RETURN_DS');
            var navigatorName = "Microsoft Internet Explorer";
            if(navigator.appName == navigatorName){
                par.removeNode(true);
            }else{
                par.remove();
            }
            var returnRecords = $au('SYS_LOV_RETURN_DS').getAll();
            for (var i = 0;i < returnRecords.length;i++) {
                var returnId = returnRecords[i].get(showId);
                if (recId.toString() === returnId.toString()) {
                    $au('SYS_LOV_RETURN_DS').data.remove(returnRecords[i]);
                }
            }
            var records = $au('SYS_LOV_RESULT_DS').getAll();
            for (var j = 0;j < records.length;j++) {
                var funId = records[j].get(showId);
                if (recId.toString() === funId.toString()) {
                    var resultRecord = records[j];
                }
                $au('SYS_LOV_RESULT_DS').unSelect(resultRecord, false);
            }
        }


        function onLoad() {
            var returnRecords = $au('SYS_LOV_RETURN_DS').getAll();
            if (returnRecords.length > 0) {
                var records = $au("SYS_LOV_RESULT_DS").getAll();
                for (var i = 0;i < records.length;i++) {
                    var funId = records[i].get(showId);
                    for (var j = 0;j < returnRecords.length;j++) {
                        var recId = returnRecords[j].get(showId);
                        if (recId.toString() === funId.toString()) {
                            var selectRecord = records[i];
                            $au('SYS_LOV_RESULT_DS').select(selectRecord);
                        }
                    }
                }
            }
            if (returnRecords.length > 0) {
                var records1 = $au("SYS_LOV_RESULT_DS").getAll();
                for (var m = 0;m < records1.length;m++) {
                    var funId1 = records1[m].get(showId);
                    for (var n = 0;n < returnRecords.length;n++) {
                        var recId1 = returnRecords[n].get(showId);
                        if (recId1.toString() === funId1.toString()) {
                            var selectRecord1 = records1[m];
                            $au('SYS_LOV_RESULT_DS').select(selectRecord1);
                        }
                    }
                }
            }
        }

        function compareRecord(recId) {
            var grid3 = document.getElementById("_superlov_autogrid3");
            var arr = grid3.getElementsByTagName('span');
            var oldId = recId;
            var temp = [];
            for (var i = 0;i < arr.length;i++) {
                temp.push(arr[i].getAttribute("recordid"));
            }
            for (var j = 0;j < temp.length;j++) {
                var funId = temp[j];
                if (oldId.toString() === funId.toString()) {
                    return false;
                }
            }
            return true;
        }
        Aurora.onReady(function() {
            var gridWrap = $("#_superlov_autogrid_wrap");
            var grid = $("#_superlov_autogrid");
            var grid3 = $("#_superlov_autogrid3");
            var fieldRecord = JSON.parse(Aurora.unescapeHtml('$[/parameter/@fieldRecord]'));
            var arr=[];
            for(var i in fieldRecord){
                arr.push(fieldRecord[i]);
            }
            if(arr.length>0){
                var arrId=arr[0].toString().split(";");
                var arrName = arr[1].toString().split(";");
                var returnDs = $au('SYS_LOV_RETURN_DS');
                for(var n=0;n<arrId.length;n++){
                    var data={};
                    data[showId]=arrId[n];
                    data[showCode] = arrName[n];
                    returnDs.create(data);

                }
            }
            var returnRecords = $au('SYS_LOV_RETURN_DS').getAll();
            if(returnRecords.length>0){
                for (var j = 0;j < returnRecords.length;j++) {
                    var recName = returnRecords[j].get(showCode);
                    var recId = returnRecords[j].get(showId);
                    if (compareRecord(recId)) {
                        if(recName!==""){
                            var nameAll = '';
                            nameAll += "<span recordId='"+recId+ "' style='background:#fff;border:#ccc 1px solid;float:left;padding:2px;padding-right:16px;margin-left:10px;margin-top:5px;margin-bottom:5px;position:relative;font-size:12px;border-radius:3px;'>" + recName + "<div id='close' style='width:9px;height:9px;color:#eee;position:absolute;top:6px;right:1px;cursor:pointer;' onclick='_removeRecords(this)'></div></span>";
                            $('#_superlov_autogrid3').append(nameAll);
                        }
                    }
                }
            }
        });
        ]]></script>
        <a:dataSets>
            <a:dataSet id="SYS_LOV_QUERY_DS" autoCreate="true"/>
            <a:dataSet id="SYS_LOV_RESULT_DS" autoQuery="$[/parameter/@lovautoquery]" queryDataSet="SYS_LOV_QUERY_DS"
                       <#if lov.customUrl != null>
                       queryUrl="$[/request/@context_path]${lov.customUrl}?$[/model/@lov_param]&amp;$[/parameter/@lov_param]"
                       <#else>
                       queryUrl="$[/request/@context_path]/common/lov/$[/model/@lov_code]?$[/model/@lov_param]&amp;$[/parameter/@lov_param]"
                       </#if>
                        selectable="true">
                <a:events>
                    <a:event name="load" handler="onLoad"/>
                </a:events>
            </a:dataSet>
            <a:dataSet id="SYS_LOV_RETURN_DS" selectable="true"/>
        </a:dataSets>
        <div id="lov_div">
            <a:hBox>
                <a:box id="sys_lov_auto_form"
                       className="lovForm" column="1" style="margin:7px;"
                       title="HAP_QUERY_TITLE">
                        <#list lov.lovItems as lovItem>
                        <#if lovItem.conditionField == "Y">
                        <a:box className="lovForm" labelWidth="${lovItem.conditionFieldLabelWidth}">
                            <a:textField name="${lovItem.gridFieldName}" prompt="${lovItem.display}"
                                         bindTarget="SYS_LOV_QUERY_DS" width="${lovItem.conditionFieldWidth}"/>
                        </a:box>
                        </#if>
                        </#list>
                </a:box>
                <a:button click="_lovquery" style="margin-left:7px;margin-top:7px;" text="hap.query" width="80"/>
                <#--<a:autoForm bindTarget="SYS_LOV_QUERY_DS" column="1" labelWidth="$[/parameter/@lovlabelwidth]" style="margin:7px;" title="QUERY"/>-->
                <#--<a:hBox>-->
                    <#--<a:button click="_lovquery" style="margin-left:7px;margin-top:7px;" text="HAP_QUERY" width="80"/>-->
                    <a:button click="_onLovRecordsCommit" style="margin-left:7px;margin-top:7px;" text="HAP_CONFIRM"/>
                <#--</a:hBox>-->
            </a:hBox>
            <span style="float:left;padding-top:6px;padding-left:10px;font-size:14px;color:#333;font-weight:bold;"><![CDATA[$[l:HAP.SELECTED_ITEMS]：]]></span>
            <div id="_superlov_autogrid3" style="overflow-x:auto;max-height:60px;"/>
            <a:hBox>
                <#--<a:autoGrid id="_superlov_autogrid" bindTarget="SYS_LOV_RESULT_DS" enablepagesize="false" height="$[/parameter/@gridheight]" navBar="true" style="margin-left:7px;" width="$[/parameter/@innerwidth]">-->
                    <#--<a:events>-->
                        <#--<a:event name="dblclick" handler="_onLovRowSelect"/>-->
                    <#--</a:events>-->
                <#--</a:autoGrid>-->
                <a:grid id="_superlov_autogrid" bindTarget="SYS_LOV_RESULT_DS" height="$[/parameter/@gridheight]"
                        navBar="true"
                        style="margin:7px;" width="$[/parameter/@innerwidth]">
                    <a:columns>
                    <#list lov.lovItems as lovItem>
                        <#if lovItem.gridField == "Y">
                            <a:column name="${lovItem.gridFieldName}" prompt="${lovItem.display}"/>
                        </#if>
                    </#list>
                    </a:columns>
                </a:grid>
            </a:hBox>
            <div class="win-toolbar" style="width:100%;height:35px;"/>
        </div>
    </a:view>
</a:screen>
