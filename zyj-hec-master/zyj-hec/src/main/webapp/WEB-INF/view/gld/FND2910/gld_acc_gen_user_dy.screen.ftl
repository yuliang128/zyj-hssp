<?xml version="1.0" encoding="UTF-8"?>

    <#--$Author: ouyangzhaochang2663 $-->
    <#--$Date: 2010/12/10 07:57:18 $-->
    <#--$Revision: 1.4 $-->
    <#--$Purpose: 用途代码定义 - 配置匹配值(动态)-->

<a:screen xmlns:c="aurora.application.action" xmlns:p="uncertain.proc" xmlns:a="http://www.aurora-framework.org/application" trace="true">
    <a:init-procedure>
        <p:echo></p:echo>
    </a:init-procedure>
    <a:view>
        <#--<a:link id="gld_acc_gen_value_lov_link" url="$[/request/@context_path]/gld/FND2910/gld_acc_gen_value_lov.screen"/>-->
        <script><![CDATA[
            function FND2910_closeAccUserWindow() {
                $au('gld_acc_gen_window').close();
            }

            function toLine(name) {
                return name.replace(/([A-Z])/g,"_$1").toLowerCase();
            }


        function onQueryUpdate(ds, record, name, value, oldValue) {
                var whereClause = '';
                var records = $au('FND2910_grps_ln_ds').getAll();
                if (name != 'whereClause' && name != 'setOfBooksName') {
                    for (var k = 0;k < records.length;k++) {
                        var grpsRecord = records[k];
                        var tempName = grpsRecord.get('mappingConditionCode');
                        if (record.get(tempName) && record.get(tempName) != 'undefined') {
                            if (grpsRecord.get('refIdField')) {
                                whereClause = whereClause + ' and t.' + toLine(tempName) + '_c like \'%' + record.get(tempName) + '%\'';
                            } else {
                                whereClause = whereClause + ' and t.' + toLine(tempName) + ' like \'%' + record.get(tempName) + '%\'';
                            }
                        }
                    }
                    record.set('whereClause', whereClause);
                }

                if (name == 'setOfBooksId' || name == 'magOrgId') {
                    if (record.get('setOfBooksId') && record.get('magOrgId')) {
                        $au('FND2910_add_grid_button').enable();
                        $au('FND2910_save_grid_button').enable();
                        $au('FND2910_delete_grid_button').enable();
                    } else {
                        $au('FND2910_add_grid_button').disable();
                        $au('FND2910_save_grid_button').disable();
                        $au('FND2910_delete_grid_button').disable();
                    }
                    if (record.get('setOfBooksId') && record.get('magOrgId')) {
                        $au('FND2910_gld_acc_gen_value_modify_ds').query();
                    }
                }

                if (name == 'magOrgId') {
                    var sobDs = $au('FND2910_set_of_book_ds');
                    sobDs.setQueryParameter('magOrgId', value);
                    sobDs.query();

                }


                if (name == 'setOfBooksId') {
                    var records2 = $au('FND2910_gld_acc_gen_value_modify_ds').getAll();
                    for (var j = 0;j < records2.length;j++) {
                        for (var i = 0;i < records.length;i++) {
                            var lovfield = records2[j].getField(records[i].get('mappingConditionCode'));
                            // var url = $au('gld_acc_gen_value_lov_link').getUrl() + '?mapping_condition_code=' + records[i].get('mappingConditioCode') + '&set_of_books_id=' + record.get('setOfBooksId') + '&mag_org_id=' + record.get('magOrgId');
                            // lovfield.setLovUrl(url);
                            lovfield.setLovPara('setOfBooksId',records[i].get('setOfBooksId'));
                            lovfield.setLovPara('magOrgId',records[i].get('magOrgId'));
                        }
                    }
                }
            }

            function FND2910_gridCellClick(grid, row, name, record) {
                var records1 = $au('FND2910_gld_acc_gen_user_dy_query_ds').getAt(0);
                var setOfBooksId = records1.get('setOfBooksId');
                if (name != 'accountCode' && name != 'accountDescription') {
                    // var url = record.getField(name).get('lovurl');
                    // var p = Ext.urlDecode(url.substring(url.indexOf('?') + 1, url.length));
                    // var code = p['mappingConditionCode'];
                    var code = record.get("mappingConditionCode");
                    var records = $au('FND2910_grps_ln_ds').getAll();
                    for (var i = 0;i < records.length;i++) {
                        var re = records[i];
                        if (re.get('mappingConditionCode') == code) {
                            var mc_code_value;
                            if (name == 'bankBranchC') {
                                mc_code_value = record.get('bankCodeValue');
                            } else if (name == 'bankAccountC') {
                                mc_code_value = record.get('bankBranchCodeValue');
                            } else if (name == 'partnerC') {
                                mc_code_value = record.get('partnerCategoryCodeValue');
                            } else if (name == 'partnerTypeC') {
                                mc_code_value = record.get('partnerCategoryCodeValue');
                            } else if (name == 'employeeExpense_itemC') {
                                mc_code_value = record.get('managingOrgCodeValue');
                                /*  if (!mc_code_value) {
                                 $A.showMessage('$[l:PROMPT]', '$[l:FND_COMPANY.COMPANY_ORG_CODE]');
                                 return;
                                 } */
                            } else if (name == 'expenseReportTypeC') {
                                mc_code_value = record.get('managingOrgCodeValue');
                                /* if (!mc_code_value) {
                                 $A.showMessage('$[l:PROMPT], '$[l:FND_COMPANY.COMPANY_ORG_CODE]');
                                 return;
                                 } */
                            } else if (name == 'fndCompanyC') {
                                mc_code_value = record.get('managingOrgCodeValue');
                                /*  if (!mc_code_value) {
                                 $A.showMessage('$[l:PROMPT]', '$[l:FND_COMPANY.COMPANY_ORG_CODE]');
                                 return;
                                 } */
                            } else if (name == 'employeeExpenseTypeC') {
                                mc_code_value = record.get('managingOrgCodeValue');
                                /*  if (!mc_code_value) {
                                 $A.showMessage('$[l:PROMPT]', '$[l:FND_COMPANY.COMPANY_ORG_CODE]');
                                 return;
                                 } */
                            } else if (name == 'orgUnitTypeC') {
                                mc_code_value = record.get('managingOrgCodeValue');
                                /*  if (!mc_code_value) {
                                 $A.showMessage('$[l:PROMPT]', '$[l:FND_COMPANY.COMPANY_ORG_CODE]');
                                 return;
                                 } */
                            } else if (name == 'org_unit_c') {
                                mc_code_value = record.get('fndCompanyCodeValue');
                                if (!mc_code_value) {
                                    $A.showMessage('$[l:PROMPT]', '$[l:GLD_ACCOUNTING_ENTITIES.EXP_POSITION]');
                                    return;
                                }
                            } else if (name == 'employee_c') {
                                mc_code_value = record.get('fndCompanyCodeValue');
                                if (!mc_code_value) {
                                    $A.showMessage('$[l:PROMPT]', '$[l:GLD_ACCOUNTING_ENTITIES.EXP_POSITION]');
                                    return;
                                }
                            } else if (name == 'employeeTypeC') {
                                mc_code_value = record.get('fndCompanyCodeValue');
                                if (!mc_code_value) {
                                    $A.showMessage('$[l:PROMPT]', '$[l:GLD_ACCOUNTING_ENTITIES.EXP_POSITION]');
                                    return;
                                }
                            } else if (name == 'expPositionC') {
                                mc_code_value = record.get('fndCompanyCodeValue');
                                if (!mc_code_value) {
                                    $A.showMessage('$[l:PROMPT]', '$[l:GLD_ACCOUNTING_ENTITIES.EXP_POSITION]');
                                    return;
                                }
                            } else if (name == 'respCenterC') {
                                mc_code_value = record.get('accEntityCodeValue');
                                if (!mc_code_value) {
                                    $A.showMessage('$[l:PROMPT]', '$[l:GLD_ACCOUNTING_ENTITIES.ACCT_ENTITY]');
                                    return;
                                }

                            } else {
                                mc_code_value = '';
                            }

                            var recordField = record.getField(name);
                            recordField.setLovPara('setOfBooksId', $au('FND2910_gld_acc_gen_user_dy_query_ds').getAt(0).get('setOfBooksId'));
                            recordField.setLovPara('magOrgId', $au('FND2910_gld_acc_gen_user_dy_query_ds').getAt(0).get('magOrgId'));
                            recordField.setLovPara('companyId', record.get('fndCompany'));
                            recordField.setLovPara('bankCode', record.get('bank'));
                            recordField.setLovPara('bankBranchId', record.get('bankBranch'));
                            recordField.setLovPara('partnerCategory', record.get('partnerCategory'));
                            recordField.setLovPara('accEntityId', record.get('accEntity'));
                        }
                    }

                }
                if (name == 'accountCode') {
                    var accountSetId = records1.get('accountSetId');
                    var accEntityId = record.get('accEntity');
                    record.getField('accountCode').setLovPara('accountSetId', accountSetId);
                    record.getField('accountCode').setLovPara('accEntityId', accEntityId);

                }

                if (name != 'accountDescription') {
                    var cid = $au('FND2910_gld_acc_gen_user_dy_query_ds').getCurrentRecord().get('setOfBooksId');
                    record.getMeta().getField(name).setLovPara('setOfBooksId', cid);
                    if (name == 'partnerC') {
                        record.getMeta().getField(name).setLovPara('partnerCategory', record.get('partnerCategory') || '');
                    }
                }

                var recordField = record.getField(name);
                recordField.setLovPara('setOfBooksId', $au('FND2910_gld_acc_gen_user_dy_query_ds').getAt(0).get('setOfBooksId'));
                recordField.setLovPara('magOrgId', $au('FND2910_gld_acc_gen_user_dy_query_ds').getAt(0).get('magOrgId'));

            }

            function FND2910_onAddDataFun(ds, record, index) {
                var setOfBooksId = $au('FND2910_gld_acc_gen_user_dy_query_ds').getCurrentRecord().get('setOfBooksId');
                var magOrgId = $au('FND2910_gld_acc_gen_user_dy_query_ds').getCurrentRecord().get('magOrgId');
                record.set('setOfBooksId', setOfBooksId);
                record.set('magOrgId', magOrgId);
                var records = $au('FND2910_grps_ln_ds').getAll();
                var records2 = $au('FND2910_gld_acc_gen_value_modify_ds').getAll();
                for (var j = 0;j < records2.length;j++) {
                    for (var i = 0;i < records.length;i++) {
                        var lovfield = records2[j].getField(records[i].get('mappingConditionCode'));
                        lovfield.setLovPara("setOfBooksId",setOfBooksId);
                        lovfield.setLovPara("magOrgId",magOrgId);
                    }
                }
            }

            function FND2910_onSubmitFun(ds) {
                var fieldsRecords = $au('FND2910_grps_ln_ds').getAll();
                var records = $au('FND2910_gld_acc_gen_value_modify_ds').getAll();
                for (var i = 0;i < records.length;i++) {
                    var codes = toLine(fieldsRecords[0].get('mappingConditionCode'));
                    var values = records[i].get(fieldsRecords[0].get('mappingConditionCode'));
                    if ( !! (fieldsRecords[0].get('refIdField'))) {
                        var c = fieldsRecords[0].get('mappingConditionCode') + 'C';
                        codes = toLine(fieldsRecords[0].get('mappingConditionCode')) + '*' + toLine(c);
                        values = values + '*' + records[i].get(c);
                    }
                    for (var j = 1;j < fieldsRecords.length;j++) {
                        var code = toLine(fieldsRecords[j].get('mappingConditionCode'));
                        codes = codes + '*' + code;
                        values = values + '*' + records[i].get(fieldsRecords[j].get('mappingConditionCode'));
                        if ( !! (fieldsRecords[j].get('refIdField'))) {
                            var c = code + 'C';
                            codes = codes + '*' + toLine(c);
                            values = values + '*' + records[i].get(c);
                        }
                    }
                    records[i].set('mappingCondition', codes);
                    records[i].set('mappingConditionValue', values);
                }
            }

            function FND2910_morequery() {
                $au('FND2910_gld_acc_gen_value_modify_ds').query();
            }

            function FND2910_submitSuccessFun() {
                $au('FND2910_gld_acc_gen_value_modify_ds').query();
            }

            function FND2910_onloadFun(ds) {
                var records = $au('FND2910_grps_ln_ds').getAll();
                var records2 = $au('FND2910_gld_acc_gen_value_modify_ds').getAll();
                var set_of_books_id = $au('FND2910_gld_acc_gen_user_dy_query_ds').getCurrentRecord();
                for (var j = 0;j < records2.length;j++) {
                    for (var i = 0;i < records.length;i++) {
                        var lovfield = records2[j].getField(records[i].get('mappingConditionCode'));
                        var mappingConditionCodeVlaue = lovfield.name + 'CodeValue';
                        records2[j].set(mappingConditionCodeVlaue, records2[j].get(lovfield.name));
                        // var url = $au('gld_acc_gen_value_lov_link').getUrl() + '?mapping_condition_code=' + records[i].get('mappingConditionCode') + '&set_of_books_id=' + set_of_books_id.get('setOfBooksId');
                        // lovfield.setLovUrl(url);
                        lovfield.setLovPara('setOfBooksId',set_of_books_id.get('setOfBooksId'));
                    }
                    records2[j].dirty = false;
                }
            }

            function FND2910_onUpdateDataFun(dataset, record, name, value, oldvalue) {
                if (name == 'managingOrgC') {
                    record.set('employeeExpenseItemC', '');
                    record.set('expenseReportTypeC', '');
                    record.set('fndCompanyC', '');
                    record.set('employeeExpenseTypeC', '');
                    record.set('orgUnitTypeC', '');
                }
                if (name == 'fndCompanyC') {
                    record.set('orgUnitC', '');
                    record.set('employeeTypeC', '');
                    record.set('expPositionC', '');
                }
                if (name == 'acctEntityC') {
                    record.set('respCenterC', '');
                }
            }

            function FND2910_onSobDsLoad(ds, res) {
                var rd = $au('FND2910_gld_acc_gen_user_dy_query_ds').getAt(0);
                var rds = ds.getAll();
                for (var i = 0;i < rds.length;i++) {
                    if (rds[i].get('defaultFlag') == 'Y') {
                        rd.set('setOfBooksName', rds[i].get('setOfBooksName'));
                        rd.set('setOfBooksId', rds[i].get('setOfBooksId'));
                        break;
                    }
                }
            }
        ]]></script>
        <a:dataSets>
            <a:dataSet id="FND2910_grps_ln_ds" autoQuery="true" fetchAll="true" queryUrl="$[/request/@context_path]/gld/mapping-cond-grp-ln/model-query?groupName=$[/parameter/@groupName]">
            </a:dataSet>
            <a:dataSet id="FND2910_gld_mapping_conds_grps_hd_info_ds" autoQuery="true" baseUrl="/gld/mapping-cond-grp-hd" queryUrl="$[/request/@context_path]/gld/mapping-cond-grp-hd/query?groupName=$[/parameter/@groupName]&amp;usageCode=$[/parameter/@usage_code_hd]">
                <a:fields>
                    <a:field name="description" readOnly="true"/>
                    <a:field name="groupName" readOnly="true"/>
                    <a:field name="priority" readOnly="true"/>
                </a:fields>
            </a:dataSet>
            <a:dataSet id="FND2910_mag_org_ds" autoQuery="true" fetchAll="true" queryUrl="$[/request/@context_path]/fnd/fnd-managing-organizations/query" loadData="true"/>
            <a:dataSet id="FND2910_set_of_book_ds" autoQuery="true" fetchAll="true" queryUrl="$[/request/@context_path]/gld-set-of-book/query" loadData="true">
                <a:events>
                    <a:event name="load" handler="FND2910_onSobDsLoad"/>
                </a:events>
            </a:dataSet>
            <a:dataSet id="FND2910_gld_acc_gen_user_dy_query_ds" autoCreate="true">
                <a:fields>
                    <a:field name="accountCode"/>
                    <a:field name="tableName" defaultValue="$[/parameter/@table_name]"/>
                    <a:field name="magOrgName" displayField="description" options="FND2910_mag_org_ds" prompt="fnd_managing_organizations.mag_org_name" required="true" returnField="magOrgId" valueField="magOrgId"/>
                    <a:field name="magOrgId"/>
                    <a:field name="setOfBooksName" displayField="setOfBooksName" options="FND2910_set_of_book_ds" prompt="gld_set_of_book.set_of_books_name" required="true" returnField="setOfBooksId" valueField="setOfBooksId"/>
                    <a:field name="setOfBooksId"/>
                    <a:field name="accountSetId"/>
                    <a:field name="whereClause"/>
                    <#list GrpLns as lines>
                        <#if lines.refIdField == null>
                            <a:field name="${lines.mappingConditionCode}"/>
                        </#if>
                        <#if lines.refIdField??>
                            <a:field name="${lines.mappingConditionCode}C"/>
                        </#if>
                    </#list>
                </a:fields>
                <a:events>
                    <a:event name="update" handler="onQueryUpdate"/>
                </a:events>
            </a:dataSet>
            <a:dataSet id="FND2910_gld_acc_gen_value_modify_ds" autoPageSize="true" queryUrl="$[/request/@context_path]/gld/mapping-cond-grp-hd/query-user-dy" queryDataSet="FND2910_gld_acc_gen_user_dy_query_ds" selectable="true" submitUrl="$[/request/@context_path]/gld/mapping-cond-grp-hd/query-user-dy-submit">
                <a:fields>
                    <a:field name="tableName" defaultValue="$[/parameter/@table_name]"/>
                    <a:field name="whereClause"/>
                    <a:field name="accountCode" autoComplete="true" autoCompleteField="code_name" lovCode = "GLD_ACCOUNT_LOV" lovHeight="450" lovWidth="600" prompt="gld_accounts.account_code" required="true" title="choose.accounting_choose">
                        <a:mapping>
                            <a:map from="accountId" to="accountId"/>
                            <a:map from="accountCode" to="accountCode"/>
                            <a:map from="description" to="accountDescription"/>
                        </a:mapping>
                    </a:field>
                    <a:field name="accountDescription" prompt="gld_accounts.account_description"/>
                    <#list GrpLns as lines>
                        <#if lines.refIdField == null>
                            <a:field name="${lines.mappingConditionCode}" autoComplete="true" editable="false" lovHeight="450" lovWidth="600"  lovCode="${lines.lovStatement}"  required="true" title="hap_select_match_value">
                                <a:mapping>
                                    <a:map from="id" to="${lines.mappingConditionCode}"/>
                                    <a:map from="code" to="${lines.mappingConditionCode}"/>
                                    <a:map from="id" to="${lines.mappingConditionCode}CodeValue"/>
                                </a:mapping>
                            </a:field>
                        </#if>
                        <#if lines.refIdField??>
                            <a:field name="${lines.mappingConditionCode}C" autoComplete="true" editable="false"  lovHeight="450" lovWidth="600"  lovCode="${lines.lovStatement}" required="true" title="hap_select_match_value">
                                <a:mapping>
                                    <a:map from="code" to="${lines.mappingConditionCode}C"/>
                                    <a:map from="id" to="${lines.mappingConditionCode}"/>
                                    <a:map from="id" to="${lines.mappingConditionCode}CodeValue"/>
                                </a:mapping>
                            </a:field>
                        </#if>
                    </#list>
                </a:fields>
                <a:events>
                    <a:event name="add" handler="FND2910_onAddDataFun"/>
                    <a:event name="update" handler="FND2910_onUpdateDataFun"/>
                    <a:event name="load" handler="FND2910_onloadFun"/>
                    <a:event name="beforesubmit" handler="FND2910_onSubmitFun"/>
                    <a:event name="submitSuccess" handler="FND2910_submitSuccessFun"/>
                </a:events>
            </a:dataSet>
        </a:dataSets>
        <a:screenBody>
            <a:form id="FND2910_gld_acc_gen_user_dy_fs1" column="3" shrinkable="false" title="hap_query_title">
                <a:formToolbar>
                    <a:label name="separator"/>
                    <a:toolbarButton click="FND2910_morequery" text="hap.query" width="80"/>
                    <a:gridButton id="FND2910_add_grid_button" bind="FND2910_gld_acc_gen_user_dy_grid" disabled="true" type="add" width="80"/>
                    <a:gridButton id="FND2910_save_grid_button" bind="FND2910_gld_acc_gen_user_dy_grid" disabled="true" type="save" width="80"/>
                    <a:gridButton id="FND2910_delete_grid_button" bind="FND2910_gld_acc_gen_user_dy_grid" disabled="true" type="delete" width="80"/>
                    <a:toolbarButton click="FND2910_closeAccUserWindow" text="hap_back" width="80"/>
                </a:formToolbar>
                <a:textField name="usageCode" bindTarget="usage_code_info_ds" prompt="gld_usage_codes.usage_code"/>
                <a:textField name="description" bindTarget="usage_code_info_ds" prompt="gld_usage_codes.usage_codes_description"/>
                <a:textField name="groupName" bindTarget="FND2910_gld_mapping_conds_grps_hd_info_ds" prompt="gld_mapping_conds_grps_hd.group_name"/>
                <a:textField name="description" bindTarget="FND2910_gld_mapping_conds_grps_hd_info_ds" prompt="gld_mapping_conds_grps_hd.mapping_conds_grps_description"/>
                <a:textField name="priority" bindTarget="FND2910_gld_mapping_conds_grps_hd_info_ds" prompt="gld_mapping_conds_grps_hd.priority"/>
                <a:textField name="accountCode" bindTarget="FND2910_gld_acc_gen_user_dy_query_ds" prompt="gld_accounts.account_code"/>
                <a:comboBox name="magOrgName" bindTarget="FND2910_gld_acc_gen_user_dy_query_ds" prompt="fnd_managing_organizations.mag_org_name"/>
                <a:comboBox name="setOfBooksName" bindTarget="FND2910_gld_acc_gen_user_dy_query_ds" prompt="gld_set_of_books_vl.set_of_books_code"/>
            </a:form>
            <a:form id="FND2910_gld_acc_gen_user_dy_fs2" column="4" labelWidth="120" shrinkable="false" title="hap_query_more">
                <#list GrpLns as lines>
                    <a:textField name="${lines.mappingConditionCode}" bindTarget="FND2910_gld_acc_gen_user_dy_query_ds" prompt="${lines.mappingConditionSqlDesc}"/>
                </#list>
            </a:form>
            <a:grid id="FND2910_gld_acc_gen_user_dy_grid" autoAdjustHeight="true" bindTarget="FND2910_gld_acc_gen_value_modify_ds" marginHeight="350" marginWidth="0" navBar="true">
                <a:columns>
                    <#list GrpLns as lines>
                        <#if lines.refIdField  == null>
                            <a:column name="${lines.mappingConditionCode}" align="center" editor="FND2910_dynamic_column_lov" prompt="${lines.mappingConditionSqlDesc}" width="80"/>
                        </#if>
                        <#if lines.refIdField??>
                            <a:column name="${lines.mappingConditionCode}C" align="center" editor="FND2910_dynamic_column_lov" prompt="${lines.mappingConditionSqlDesc}" width="80"/>
                        </#if>
                    </#list>
                    <a:column name="accountCode" align="center" editor="FND2910_dynamic_column_lov" prompt="gld_accounts.account_code" width="80"/>
                    <a:column name="accountDescription" align="left" prompt="gld_accounts.account_description" width="200"/>
                </a:columns>
                <a:editors>
                    <a:lov id="FND2910_dynamic_column_lov"/>
                </a:editors>
                <a:events>
                    <a:event name="cellclick" handler="FND2910_gridCellClick"/>
                </a:events>
            </a:grid>
        </a:screenBody>
    </a:view>
</a:screen>
