<?xml version="1.0" encoding="UTF-8"?>
<!--
 * Description:
 * Version: 1.0
 * Author:jialin.xing@hand-china.com 2019/6/12
 * Copyright Hand China Co.,Ltd.
-->

<a:screen xmlns:a="http://www.aurora-framework.org/application" trace="true">

    <a:view>

        <style><![CDATA[
            table.gridtable {
                font-family: verdana, arial, sans-serif;
                font-size: 11px;
                color: #333333;
                border-width: 1px;
                border-color: #e7ecf1;
                border-collapse: collapse;
            }

            table.gridtable th {
                border-width: 1px;
                padding: 8px;
                border-style: solid;
                border-color: #e7ecf1;
                font-weight: bold;
            }

            table.gridtable td {
                border-width: 1px;
                padding: 8px;
                border-style: solid;
                border-color: #e7ecf1;
                height: 20px;
            }
            table.headTable{
                border-bottom: 0px;
            }
            table.headTable th {
                text-align: center;
            }

            table.gridtable tr:nth-child(even){
                background-color: #f9f9f9;
            }
            ]]></style>

        <script><![CDATA[
            function exportExcel() {
            let datas = [];
                let size = '${size}';
                if (size > 0) {
                    for (let i= 0; i<size;i++){
                        let checkBox = document.getElementById("checkbox_"+i);
                        if (checkBox.checked){
                            datas.push({
                                column:checkBox.value,
                            })
                        }
                    }
                    if (datas.length === 0) {
                        return
                    }

                    let $inputImg = $('<input>').attr({name:"_HAP_EXCEL_EXPORT_COLUMNS",value:JSON.stringify(datas)});
                    let $way = $('<input>').attr({name:"way",value:'hssp',type:"hidden"});
                    let $inputToken = $('<input>').attr({name:"_csrf",value:'$[/model/@_csrf/@token]',type:"hidden"});
                    let $page=$('<input>').attr({name:"page",value:1,type:"hidden"});
                    let $pagesize=$('<input>').attr({name:"pagesize",value:0,type:"hidden"});
                    let $form = $("<form>");
                    $form.attr({
                        target: '_self',
                        method: 'post',
                        action: '${url}'
                    }).append($inputImg);
                    $form.append($way);
                    $form.append($inputToken);
                    $form.append($page);
                    $form.append($pagesize);
                    $form.id='smbForm';
                    $("#batchDiv").empty().append($form);
                    $($form).submit();
                    $("#batchDiv").empty();
                }
            }
        ]]></script>

        <div id="template_div">
            <a:box>
                <a:button click="exportExcel" style="margin-left:30px;margin-top:10px;" text="prompt.export_excel"
                          width="80"/>
            </a:box>
            <div>
                <table class="gridtable headTable" style="margin-left: 30px;margin-top: 10px">
                    <tr>
                        <th width="300px">$[l:flexrulefield.columnname]</th>
                        <th width="200px">$[l:exportexcel.choosecolumn]</th>
                    </tr>
                </table>
                <table class="gridtable" style="margin-left: 30px; height: 260px;">
                        <#list list as column>
                            <tr>
                                <td width="300px">${column.description}</td>
                                <td width="200px" align="center"><input type='checkbox' id='checkbox_${column_index}' value="${column.value}"/></td>
                            </tr>
                        </#list>
                </table>
            </div>
            <div id="batchDiv" style="display: none"></div>
        </div>
    </a:view>
</a:screen>