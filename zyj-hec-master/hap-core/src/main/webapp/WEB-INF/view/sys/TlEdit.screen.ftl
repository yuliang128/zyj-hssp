<?xml version="1.0" encoding="UTF-8"?>
<a:screen xmlns:a="http://www.aurora-framework.org/application" trace="true">
    <a:view>
        <script language="JavaScript"><![CDATA[

        function submitTledit() {
            var record = $au('tledit_ds').getCurrentRecord();
            $au('$[/parameter/@tleditid]').commit(record, '$[/session/@locale]');
        }

        function exitTledit() {
            $au('$[/parameter/@tleditid]').commit();
        }

        Aurora.onReady(function () {
            var ds =$au('$[/parameter/@tleditid]');
            var record = $au('tledit_ds').getCurrentRecord();
            if (ds.record.get('__tls')){
                var obj = ds.record.get('__tls')[ds.binder.name];
                for(var lang in obj) {
                    record.set(lang,obj[lang]);
                }
            }
        });

        ]]></script>
        <a:dataSets>
            <a:dataSet id="tledit_ds" autoCreate="true">
                <a:fields>
                    <#list list as item>
                        <a:field name="${item.langCode}" defaultValue="${item.value}" required="true"/>
                    </#list>
                </a:fields>
            </a:dataSet>
        </a:dataSets>
        <div id="tledit_div">
            <div style="margin:30px;">
                <a:box>
                    <#list list as item>
                        <a:textField name="${item.langCode}" bindTarget="tledit_ds" prompt="${item.description}" width="200"/>
                    </#list>
                </a:box>
            </div>
            <div style="padding-right:30px;border-top:1px solid #ccc;">
                <a:hBox style="float:right;margin-top:10px;">
                    <a:button click="submitTledit" text="确定"/>
                    <a:button click="exitTledit" skin="white" text="取消"/>
                </a:hBox>
            </div>
        </div>
    </a:view>
</a:screen>
