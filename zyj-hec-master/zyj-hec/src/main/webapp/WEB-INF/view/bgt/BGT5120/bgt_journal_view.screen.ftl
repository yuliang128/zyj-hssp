<?xml version="1.0" encoding="UTF-8"?>
<a:screen xmlns:c="aurora.application.action" xmlns:p="uncertain.proc"
          xmlns:a="http://www.aurora-framework.org/application" xmlns:s="aurora.plugin.script" trace="true">
    <a:view>
        <a:link id="BGT5120_uploadFile_link" url="$[/request/@context_path]/app/APP2030/app_uploadFile.screen"/>
        <a:link id="BGT5120_bgt_journal_main_link" url="$[/request/@context_path]/modules/bgt/BGT5120/bgt_journal_main.screen"/>
        <a:link id="BGT5120_exp_document_history_view_link" url="$[/request/@context_path]/expm/public/exp_document_history_query.screen"/>
        <script><![CDATA[
        var submitFlag = false;

        function BGT5120_closeJournal() {
            $au('BGT5120_bgt_journal_view_window').close();
        }

        function BGT5120_initFun() {
            debugger;
            var header_id = $au('BGT5120_bgtJournalHeaderDs').getAt(0).get('journalHeaderId');
            if ('$[/model/header/records/record/@periodStrategy]' == 'MONTH') {
                $au('BGT5120_bgtJournalLineDs').fields.linePeriodName.pro.required = true;
                $au('BGT5120_bgtJournalLineDs').fields.lineBgtPeriodNum.pro.required = true;
                $au('BGT5120_bgtJournalLineDs').fields.linePeriodYear.pro.required = true;
                $au('BGT5120_bgtJournalLineDs').fields.linePeriodQuarter.pro.required = true;
                $au('BGT5120_journalLineGrid').hideColumn('linePeriodQuarter');
                $au('BGT5120_journalLineGrid').hideColumn('linePeriodYear');
            } else if ('$[/model/header/records/record/@periodStrategy]' == 'QUARTER') {
                $au('BGT5120_bgtJournalLineDs').fields.linePeriodYear.pro.required = true;
                $au('BGT5120_bgtJournalLineDs').fields.linePeriodQuarter.pro.required = true;
                $au('BGT5120_journalLineGrid').hideColumn('linePeriodName');
            } else if ('$[/model/header/records/record/@periodStrategy]' == 'YEAR') {
                $au('BGT5120_bgtJournalLineDs').fields.linePeriodYear.pro.required = true;
                $au('BGT5120_journalLineGrid').hideColumn('linePeriodName');
                $au('BGT5120_journalLineGrid').hideColumn('linePeriodQuarter');
            }
            var headerRecord = $au('BGT5120_bgtJournalHeaderDs').getAt(0);
            if (headerRecord.get('journalHeaderId')) {
                $au('BGT5120_uploadBtn').enable();
                headerRecord.getField('bgtEntityName').setReadOnly(true);
                headerRecord.getField('bgtCenterName').setReadOnly(true);
            }

            /* add by liliang 2018/11/19 预算表定义中，预算中心和预算实体未勾选时，隐藏列 */
            //begin
            if ('$[/model/header/records/record/@entityFlag]' == 'N') {
                $au('BGT5120_journalLineGrid').hideColumn('bgtEntityName');
                $au('BGT5120_bgtJournalLineDs').fields.bgtEntityName.pro.required = false;
                $au('BGT5120_bgtJournalLineDs').fields.bgtEntityId.pro.required = false;
            }
            if ('$[/model/header/records/record/@centerFlag]' == 'N') {
                $au('BGT5120_journalLineGrid').hideColumn('bgtCenterName');
                $au('BGT5120_bgtJournalLineDs').fields.bgtCenterName.pro.required = false;
                $au('BGT5120_bgtJournalLineDs').fields.bgtCenterId.pro.required = false;
            }
            //end
        }


        function BGT5120_upload() {
            new Aurora.Window({
                url: $au('BGT5120_uploadFile_link').getUrl() + '?tableName=BGT_JOURNAL_HEADER&tablePkValue=' + $au('BGT5120_bgtJournalHeaderDs').getAt(0).get('journalHeaderId'),
                title: '$[l:atm.upload_attachment]',
                width: 600,
                height: 400
            });
        }

        function BGT5120_checkBgtJournalHistory() {
            var journalHeaderId = $au('BGT5120_bgtJournalHeaderDs').getAt(0).get('journalHeaderId');
            new Aurora.Window({
                url: $au('BGT5120_exp_document_history_view_link').getUrl() + '?documentId=' + journalHeaderId + '&documentType=BUDGET_JOURNAL',
                id: 'BGT5120_exp_document_history_view_window',
                fullScreen: false,
                width: 1020,
                side: 'right'
            });
        }

        Aurora.onReady(BGT5120_initFun);
        ]]></script>
        <a:dataSets>
            <a:dataSet id="BGT5120_bgtVersionDs" autoQuery="true" fetchAll="true"
                       queryUrl="$[/request/@context_path]/bgt/version/query?bgtOrgId=$[/model/header/records/record/@bgtOrgId]&amp;bgtJournalTypeId=$[/model/header/records/record/@bgtJournalTypeId]"/>
            <a:dataSet id="BGT5120_bgtScenarioDs" autoQuery="true" fetchAll="true"
                       queryUrl="$[/request/@context_path]/bgt/scenario/query?bgtOrgId=$[/model/header/records/record/@bgtOrgId]&amp;bgtJournalTypeId=$[/model/header/records/record/@bgtJournalTypeId]"/>
            <a:dataSet id="BGT5120_bgtPeriodDs" autoQuery="true" fetchAll="true"
                       queryUrl="$[/request/@context_path]/bgt/period/query?bgtOrgId=$[/model/header/records/record/@bgtOrgId]"/>
            <a:dataSet id="BGT5120_currencyDs" autoQuery="true"
                       queryUrl="$[/request/@context_path]/gld-currency/query"/>
            <a:dataSet id="BGT5120_exchangeRateTypeDs" autoQuery="true"
                       queryUrl="$[/request/@context_path]/gld/exchangerate-type/query"/>
            <a:dataSet id="BGT5120_bgtJournalHeaderDs"
                       baseUrl="/bgt/journal-header">
                <a:datas dataSource="header/records"/>
                <a:fields>
                    <a:field name="journalHeaderId" readOnly="true"/>
                    <a:field name="budgetJournalNumber"
                             prompt="bgt_journal_header.budget_journal_number"
                             readOnly="true"/>
                    <a:field name="bgtJournalTypeName" prompt="bgt_journal_header.bgt_journal_type"
                             readOnly="true"/>
                    <a:field name="structureName" prompt="bgt_journal_header.bgt_structure"
                             readOnly="true"/>
                    <a:field name="periodStrategyName" prompt="bgt_journal_header.period_strategy"
                             readOnly="true"/>
                    <a:field name="bgtEntityName" readOnly="true"
                             lovService="hec_util.bgt_entities_vl_lov?bgtOrgId=$[/model/header/records/record/@bgtOrgId]"
                             prompt="bgt_journal_header.bgt_entity">
                        <a:mapping>
                            <a:map from="bgtEntityName" to="bgtEntityName"/>
                            <a:map from="bgtEntityId" to="bgtEntityId"/>
                        </a:mapping>
                    </a:field>
                    <a:field name="bgtCenterName" readOnly="true"
                             lovService="hec_util.bgt_centers_entities_vl_lov?bgtOrgId=$[/model/header/records/record/@bgtOrgId]"
                             prompt="bgt_journal_header.bgt_center">
                        <a:mapping>
                            <a:map from="bgtCenterName" to="bgtCenterName"/>
                            <a:map from="bgtCenterId" to="bgtCenterId"/>
                        </a:mapping>
                    </a:field>
                    <a:field name="scenarioName" readOnly="true" displayField="description"
                             options="BGT5120_bgtScenarioDs"
                             prompt="bgt_journal_header.bgt_scenario" required="true" returnField="scenarioId"
                             valueField="scenarioId"/>
                    <a:field name="scenarioId" readOnly="true"/>
                    <a:field name="versionName" readOnly="true" displayField="description"
                             options="BGT5120_bgtVersionDs"
                             prompt="bgt_journal_header.bgt_version" required="true" returnField="versionId"
                             valueField="versionId"/>
                    <a:field name="versionId" readOnly="true"/>
                    <a:field name="statusName" readOnly="true" prompt="bgt_journal_header.status"/>
                    <a:field name="journalHeaderNotes" readOnly="true"
                             prompt="bgt_journal_header.journal_header_notes"/>
                    <a:field name="structureId" readOnly="true"/>
                    <#list headerDimList as headerDim>
                        <a:field name="${headerDim.display_field}" readOnly="true"
                                 defaultValue="${headerDim.default_dim_value_name}"
                                 lovCode="${headerDim.lovCode}"
                                 prompt="${headerDim.prompt}"
                                 required="true" title="${headerDim.title}">
                            <a:mapping>
                                <a:map from="${headerDim.fromName}" to="${headerDim.display_field}"/>
                                <a:map from="${headerDim.fromId}" to="${headerDim.return_field}"/>
                            </a:mapping>
                        </a:field>
                        <a:field name="${headerDim.return_field}" readOnly="true"
                                 defaultValue="${headerDim.default_dim_value_id}"/>
                    </#list>
                    <#list headerObjList as headerObj>
                        <a:field name="${headerObj.display_field}" readOnly="true"
                                 defaultValue="${headerObj.dft_mo_expense_object_name}"
                                 lovCode="LOV_EXP_MO_EXPENSE_OBJECT_VALUE?moExpObjTypeId=${headerObj.mo_exp_obj_type_id}"
                                 prompt="${headerObj.mo_exp_obj_type_name}" required="${headerObj.required_flag}"
                                 title="${headerObj.mo_exp_obj_type_name}">
                            <a:mapping>
                                <a:map from="name" to="${headerObj.display_field}"/>
                                <a:map from="id" to="${headerObj.return_field}"/>
                            </a:mapping>
                        </a:field>
                        <a:field name="${headerObj.return_field}" readOnly="true"
                                 defaultValue="${headerObj.dft_mo_expense_object_id}"/>
                    </#list>
                </a:fields>
            </a:dataSet>
            <a:dataSet id="BGT5120_bgtJournalLineDs" autoPageSize="true" autoQuery="true" bindName="journalLines"
                       bindTarget="BGT5120_bgtJournalHeaderDs" baseUrl="/bgt/journal-line"
                       queryUrl="$[/request/@context_path]/bgt/journal-line/query?journalHeaderId=$[/model/header/records/record/@journalHeaderId]"
                       selectable="true">
                <a:fields>
                    <a:field name="bgtJournalLineId"/>
                    <a:field name="bgtEntityName" prompt="bgt_journal_line.bgt_entity" required="true"
                             title="gld_responsibility_centers.bgt_entitychoice">
                        <a:mapping>
                            <a:map from="bgtEntityName" to="bgtEntityName"/>
                            <a:map from="bgtEntityId" to="bgtEntityId"/>
                        </a:mapping>
                    </a:field>
                    <a:field name="bgtEntityId"/>
                    <a:field name="bgtCenterName" prompt="bgt_journal_line.bgt_center" required="true"
                             title="exp_org_units.choice_bgt_center_display">
                        <a:mapping>
                            <a:map from="bgtCenterName" to="bgtCenterName"/>
                            <a:map from="bgtCenterId" to="bgtCenterId"/>
                        </a:mapping>
                    </a:field>
                    <a:field name="bgtCenterId"/>
                    <a:field name="budgetItemName"
                             lovCode="LOV_BUDGET_ITEM_IN_BGT_ORG?bgtOrgId=$[/model/header/records/record/@bgtOrgId]&amp;summaryFlag=N"
                             prompt="bgt_journal_line.budget_item" required="true"
                             title="bgt_budget_items.bgt_budget_items_choose">
                        <a:mapping>
                            <a:map from="budgetItemName" to="budgetItemName"/>
                            <a:map from="budgetItemId" to="budgetItemId"/>
                        </a:mapping>
                    </a:field>
                    <a:field name="budgetItemId"/>
                    <a:field name="linePeriodName"
                             lovCode="LOV_BGT_PERIOD_IN_JOURNAL?bgtOrgId=$[/model/header/records/record/@bgtOrgId]&amp;structureId=$[/model/header/records/record/@structureId]"
                             prompt="bgt_journal_line.period_name" title="select_business_period">
                        <a:mapping>
                            <a:map from="periodName" to="linePeriodName"/>
                            <a:map from="periodYear" to="linePeriodYear"/>
                            <a:map from="quarterNum" to="linePeriodQuarter"/>
                            <a:map from="periodNum" to="lineBgtPeriodNum"/>
                        </a:mapping>
                    </a:field>
                    <a:field name="linePeriodQuarter" prompt="bgt_journal_line.period_quarter"/>
                    <a:field name="linePeriodYear" prompt="bgt_journal_line.period_year"/>
                    <a:field name="lineBgtPeriodNum" prompt="bgt_journal_line.bgt_period_num"/>
                    <a:field name="currencyName" displayField="currencyName" options="BGT5120_currencyDs"
                             prompt="bgt_journal_line.currency" required="true" returnField="currency"
                             valueField="currencyCode"/>
                    <a:field name="currency"/>
                    <a:field name="exchangeRate" prompt="bgt_journal_line.exchange_rate"/>
                    <a:field name="amount" prompt="bgt_journal_line.amount" required="true"/>
                    <a:field name="functionalAmount" prompt="bgt_journal_line.functional_amount"/>
                    <a:field name="quantity" prompt="bgt_journal_line.quantity" required="true"/>
                    <a:field name="journalLinesNotes" prompt="bgt_journal_line.journal_line_notes"/>
                    <#list lineDimList as lineDim>
                        <a:field name="${lineDim.display_field}" defaultValue="${lineDim.default_dim_value_name}"
                                 lovCode="${lineDim.lovCode}"
                                 prompt="${lineDim.prompt}"
                                 required="true" title="${lineDim.title}">
                            <a:mapping>
                                <a:map from="${lineDim.fromName}" to="${lineDim.display_field}"/>
                                <a:map from="${lineDim.fromId}" to="${lineDim.return_field}"/>
                            </a:mapping>
                        </a:field>
                        <a:field name="${lineDim.return_field}" defaultValue="${lineDim.default_dim_value_id}"/>
                    </#list>
                    <#list lineObjList as lineObj>
                        <a:field name="${lineObj.display_field}"
                                 defaultValue="${lineObj.dft_mo_expense_object_name}"
                                 lovCode="LOV_EXP_MO_EXPENSE_OBJECT_VALUE?moExpObjTypeId=${lineObj.mo_exp_obj_type_id}"
                                 prompt="${lineObj.mo_exp_obj_type_name}" required="${lineObj.required_flag}"
                                 title="${lineObj.mo_exp_obj_type_name}">
                            <a:mapping>
                                <a:map from="name" to="${lineObj.display_field}"/>
                                <a:map from="id" to="${lineObj.return_field}"/>
                            </a:mapping>
                        </a:field>
                        <a:field name="${lineObj.return_field}" defaultValue="${lineObj.dft_mo_expense_object_id}"/>
                    </#list>
                </a:fields>
            </a:dataSet>
        </a:dataSets>
        <a:screenBody>
            <a:form column="1" title="$[/model/header/records/record/@bgtJournalTypeName]" style="width:100%;">
                <a:formToolbar>
                    <a:label name="separator"/>
                    <a:button id="BGT5120_uploadBtn" click="BGT5120_upload" disabled="true"
                              text="hap_attachment_documentation" width="80"/>
                    <a:button id="BGT5120_docHistoryBtn" click="BGT5120_checkBgtJournalHistory" text="prompt.history" width="80"/>
                    <a:button click="BGT5120_closeJournal" text="hap_back" width="80"/>
                </a:formToolbar>
                <a:box column="4" labelWidth="110" style="width:100%;">
                    <a:textField name="budgetJournalNumber" bindTarget="BGT5120_bgtJournalHeaderDs"/>
                    <a:textField name="bgtJournalTypeName" bindTarget="BGT5120_bgtJournalHeaderDs"/>
                    <a:textField name="structureName" bindTarget="BGT5120_bgtJournalHeaderDs"/>
                    <a:textField name="periodStrategyName" bindTarget="BGT5120_bgtJournalHeaderDs"/>
                    <a:comboBox name="scenarioName" bindTarget="BGT5120_bgtJournalHeaderDs"/>
                    <a:comboBox name="versionName" bindTarget="BGT5120_bgtJournalHeaderDs"/>
                    <a:textField name="statusName" bindTarget="BGT5120_bgtJournalHeaderDs"/>
                    <#list headerDimList as headerDim>
                        <a:lov name="${headerDim.display_field}" bindTarget="BGT5120_bgtJournalHeaderDs">
                            <a:events>
                                <a:event name="focus" handler="BGT5120_headerDimensionFunc"/>
                            </a:events>
                        </a:lov>
                    </#list>
                    <#list headerObjList as headerObj>
                        <#if headerObj.expense_object_method == 'DESC_TEXT'>
                            <a:textField name="${headerObj.display_field}" bindTarget="BGT5120_bgtJournalHeaderDs"/>
                        <#else>
                            <a:lov name="${headerObj.display_field}" bindTarget="BGT5120_bgtJournalHeaderDs"/>
                        </#if>
                    </#list>
                </a:box>
                <a:box labelWidth="110" style="width:100%;margin-top:0px;">
                    <a:textArea name="journalHeaderNotes" bindTarget="BGT5120_bgtJournalHeaderDs"
                                calcWidth="calc((100%  + 90px) / 4 * 3 + 150px)"/>
                </a:box>
            </a:form>
            <a:grid id="BGT5120_journalLineGrid" autoAdjustHeight="true" bindTarget="BGT5120_bgtJournalLineDs"
                    marginHeight="191" marginWidth="16" navBar="true">
                <a:columns>
                    <a:column name="journalLinesNotes" align="left" width="150"/>
                    <a:column name="bgtEntityName" align="center" width="120"/>
                    <a:column name="bgtCenterName" align="center" width="120"/>
                    <a:column name="budgetItemName" align="center" width="120"/>
                    <a:column name="linePeriodName" align="center" width="80"/>
                    <a:column name="linePeriodQuarter" align="right" width="80"/>
                    <a:column name="linePeriodYear" align="right" width="80"/>
                    <a:column name="currencyName" align="center"/>
                    <a:column name="amount" align="right" renderer="Aurora.formatMoney"/>
                    <a:column name="quantity" align="right" renderer="Aurora.formatNumber"/>
                    <#list lineDimList as lineDim>
                        <a:column name="${lineDim.display_field}"/>
                    </#list>
                    <#list lineObjList as lineObj>
                        <a:column name="${lineObj.display_field}" align="center"/>
                    </#list>
                </a:columns>
            </a:grid>
        </a:screenBody>
    </a:view>
</a:screen>
