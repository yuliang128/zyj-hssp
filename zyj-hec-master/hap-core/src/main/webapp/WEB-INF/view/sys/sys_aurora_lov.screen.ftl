<?xml version="1.0" encoding="UTF-8"?>
<a:screen xmlns:a="http://www.aurora-framework.org/application" trace="true">
    <a:parameter>
        <a:param DataType="java.lang.String" Name="ORDER_FIELD"/>
        <a:param DataType="java.lang.String" Name="ORDER_TYPE"/>
    </a:parameter>
    <a:view>
        <style>
            .lovForm .layout-th {
                font-size: 11px;
            }
        </style>
        <div/>
        <script language="JavaScript"><![CDATA[
        function _onLovRowselect(grid, record, row) {
            $au('$[/parameter/@lovid]').commit(record);
        }

        function _onLovKeyDown(grid, e) {
            if (e.getKey() == 13) {
                _lovok();
            }
            if (e.getKey() == 27) {
                $au('$[/parameter/@lovid]').commit();
            }
        }

        function _lovok() {
            var record = $au('SYS_LOV_RESULT_DS').getCurrentRecord();
            $au('$[/parameter/@lovid]').commit(record);
        }

        function _lovquery() {
            $au('SYS_LOV_RESULT_DS').query()
        }

        var axx = "${_csrf.token}";
        var bxx = "$[/model/@_csrf/@token]";
        debugger;
        ]]></script>
        <a:dataSets>
            <a:dataSet id="SYS_LOV_QUERY_DS" autoCreate="true"/>
            <a:dataSet id="SYS_LOV_RESULT_DS" autoQuery="$[/parameter/@lovautoquery]"
                       queryDataSet="SYS_LOV_QUERY_DS"
                       <#if lov.customUrl != null>
                       queryUrl="$[/request/@context_path]${lov.customUrl}?$[/model/@lov_param]&amp;$[/parameter/@lov_param]"
                       <#else>
                       queryUrl="$[/request/@context_path]/common/lov/$[/model/@lov_code]?$[/model/@lov_param]&amp;$[/parameter/@lov_param]"
                       </#if>
            />
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
            </a:hBox>
            <a:grid bindTarget="SYS_LOV_RESULT_DS" height="$[/parameter/@gridheight]"
                    navBar="true"
                    style="margin:7px;" width="$[/parameter/@innerwidth]">
                <a:columns>
                    <#list lov.lovItems as lovItem>
                        <#if lovItem.gridField == "Y">
                            <a:column name="${lovItem.gridFieldName}" prompt="${lovItem.display}"/>
                        </#if>
                    </#list>
                </a:columns>
                <a:events>
                    <a:event name="dblclick" handler="_onLovRowselect"/>
                    <a:event name="keydown" handler="_onLovKeyDown"/>
                </a:events>
            </a:grid>
        </div>
        <script><![CDATA[
        Aurora.onReady(function () {
            var tfs = Ext.fly('lov_div').select('input.item-textField');
            if (tfs && tfs.elements && tfs.elements[0]) {
                tfs.elements[0].focus();
            }
        });

        Ext.get('sys_lov_auto_form').on('keydown', function (e) {
            if (e.keyCode == 13) {
                _lovquery();
            }
        });
        ]]></script>
    </a:view>
</a:screen>
