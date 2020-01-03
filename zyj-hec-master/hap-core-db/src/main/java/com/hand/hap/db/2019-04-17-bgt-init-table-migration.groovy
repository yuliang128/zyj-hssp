package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-04-17-bgt-init-table-migration.groovy') {


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-04-17-BGT_JOURNAL_BALANCE_INIT") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'BGT_JOURNAL_BALANCE_INIT_S', startValue:"10001")
        }

        createTable(tableName: "BGT_JOURNAL_BALANCE_INIT", remarks: "预算余额临时表") {

            column(name: "SESSION_ID", type: "BIGINT", remarks: "")   
            column(name: "BALANCE_ID", type: "BIGINT", remarks: "预算表balance")   
            column(name: "SET_OF_BOOKS_ID", type: "BIGINT", remarks: "帐套id")   
            column(name: "BGT_ORG_ID", type: "BIGINT", remarks: "预算组织id")   
            column(name: "BGT_ENTITY_ID", type: "BIGINT", remarks: "")   
            column(name: "BGT_CENTER_ID", type: "BIGINT", remarks: "")   
            column(name: "BALANCE_COMPANY_ID", type: "BIGINT", remarks: "预算公司ID")   
            column(name: "BALANCE_OPERATION_UNIT_ID", type: "BIGINT", remarks: "经营单位id")   
            column(name: "STRUCTURE_ID", type: "BIGINT", remarks: "预算表id")   
            column(name: "SCENARIO_ID", type: "BIGINT", remarks: "预算场景id")   
            column(name: "VERSION_ID", type: "BIGINT", remarks: "版本ID")   
            column(name: "RESPONSIBILITY_CENTER_ID", type: "BIGINT", remarks: "责任中心id")   
            column(name: "BUDGET_ITEM_ID", type: "BIGINT", remarks: "预算项目id")   
            column(name: "PERIOD_YEAR", type: "BIGINT", remarks: "预算年度")   
            column(name: "PERIOD_QUARTER", type: "BIGINT", remarks: "季度")   
            column(name: "PERIOD_NAME", type: "VARCHAR(30)", remarks: "期间")   
            column(name: "BGT_PERIOD_NUM", type: "BIGINT", remarks: "预算期间")   
            column(name: "CURRENCY", type: "VARCHAR(30)", remarks: "币种")   
            column(name: "PERIOD_AMOUNT", type: "NUMBER", remarks: "期间总额")   
            column(name: "PERIOD_FUNCTIONAL_AMOUNT", type: "NUMBER", remarks: "本位币期间金额")   
            column(name: "PERIOD_QUANTITY", type: "BIGINT", remarks: "期间数量")   
            column(name: "QUARTER_AMOUNT", type: "NUMBER", remarks: "季度总额")   
            column(name: "QUARTER_FUNCTIONAL_AMOUNT", type: "NUMBER", remarks: "本位币季度金额")   
            column(name: "QUARTER_QUANTITY", type: "BIGINT", remarks: "季度数量")   
            column(name: "UOM", type: "VARCHAR(30)", remarks: "经营单位id")   
            column(name: "YEAR_AMOUNT", type: "NUMBER", remarks: "年度总额")   
            column(name: "YEAR_FUNCTIONAL_AMOUNT", type: "NUMBER", remarks: "本位币年度金额")   
            column(name: "YEAR_QUANTITY", type: "NUMBER", remarks: "年度数量")   
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司ID")   
            column(name: "OPERATION_UNIT_ID", type: "BIGINT", remarks: "经营单位id")   
            column(name: "UNIT_ID", type: "BIGINT", remarks: "部门id")   
            column(name: "UNIT_GROUP_ID", type: "BIGINT", remarks: "部门组ID")   
            column(name: "POSITION_ID", type: "BIGINT", remarks: "岗位id")   
            column(name: "POSITION_GROUP_ID", type: "BIGINT", remarks: "岗位组ID")   
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "员工id")   
            column(name: "EMPLOYEE_GROUP_ID", type: "BIGINT", remarks: "员工组ID")   
            column(name: "DIMENSION1_ID", type: "BIGINT", remarks: "维值1id")   
            column(name: "DIMENSION2_ID", type: "BIGINT", remarks: "维值2id")   
            column(name: "DIMENSION3_ID", type: "BIGINT", remarks: "维值3id")   
            column(name: "DIMENSION4_ID", type: "BIGINT", remarks: "维值4id")   
            column(name: "DIMENSION5_ID", type: "BIGINT", remarks: "维值5id")   
            column(name: "DIMENSION6_ID", type: "BIGINT", remarks: "维值6id")   
            column(name: "DIMENSION7_ID", type: "BIGINT", remarks: "维值7id")   
            column(name: "DIMENSION8_ID", type: "BIGINT", remarks: "维值8id")   
            column(name: "DIMENSION9_ID", type: "BIGINT", remarks: "维值9id")   
            column(name: "DIMENSION10_ID", type: "BIGINT", remarks: "维值10id")   
            column(name: "DIMENSION11_ID", type: "BIGINT", remarks: "")   
            column(name: "DIMENSION12_ID", type: "BIGINT", remarks: "")   
            column(name: "DIMENSION13_ID", type: "BIGINT", remarks: "")   
            column(name: "DIMENSION14_ID", type: "BIGINT", remarks: "")   
            column(name: "DIMENSION15_ID", type: "BIGINT", remarks: "")   
            column(name: "DIMENSION16_ID", type: "BIGINT", remarks: "")   
            column(name: "DIMENSION17_ID", type: "BIGINT", remarks: "")   
            column(name: "DIMENSION18_ID", type: "BIGINT", remarks: "")   
            column(name: "DIMENSION19_ID", type: "BIGINT", remarks: "")   
            column(name: "DIMENSION20_ID", type: "BIGINT", remarks: "")   
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "BGT_JOURNAL_BALANCE_INIT", indexName: "BGT_JOURNAL_BALANCE_INIT_N1") {
                    column(name: "SESSION_ID")
                }
    
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-04-17-BGT_BUDGET_RESERVE_INIT") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'BGT_BUDGET_RESERVE_INIT_S', startValue:"10001")
        }

        createTable(tableName: "BGT_BUDGET_RESERVE_INIT", remarks: "预算保留临时表") {

            column(name: "SESSION_ID", type: "BIGINT", remarks: "")   
            column(name: "BUDGET_RESERVE_LINE_ID", type: "BIGINT", remarks: "预算保留表行ID")   
            column(name: "RESERVE_COMPANY_ID", type: "BIGINT", remarks: "占用公司id")   
            column(name: "RESERVE_OPERATION_UNIT_ID", type: "BIGINT", remarks: "经营单位id")   
            column(name: "BGT_ORG_ID", type: "BIGINT", remarks: "预算组织id")   
            column(name: "BGT_ENTITY_ID", type: "BIGINT", remarks: "")   
            column(name: "BGT_CENTER_ID", type: "BIGINT", remarks: "")   
            column(name: "PERIOD_YEAR", type: "BIGINT", remarks: "预算年度")   
            column(name: "PERIOD_QUARTER", type: "BIGINT", remarks: "季度")   
            column(name: "PERIOD_NAME", type: "VARCHAR(30)", remarks: "期间")   
            column(name: "BGT_PERIOD_NUM", type: "BIGINT", remarks: "预算期间")   
            column(name: "RELEASE_ID", type: "BIGINT", remarks: "释放标志")   
            column(name: "BUSINESS_TYPE", type: "VARCHAR(30)", remarks: "业务类型")   
            column(name: "RESERVE_FLAG", type: "VARCHAR(1)", remarks: "释放标志")   
            column(name: "STATUS", type: "VARCHAR(30)", remarks: "状态")   
            column(name: "MANUAL_FLAG", type: "VARCHAR(1)", remarks: "预算状态")   
            column(name: "DOCUMENT_ID", type: "BIGINT", remarks: "单据头id")   
            column(name: "DOCUMENT_LINE_ID", type: "BIGINT", remarks: "表行id")   
            column(name: "CURRENCY", type: "VARCHAR(30)", remarks: "币种")   
            column(name: "BUDGET_ITEM_ID", type: "BIGINT", remarks: "预算项目id")   
            column(name: "RESPONSIBILITY_CENTER_ID", type: "BIGINT", remarks: "责任中心id")   
            column(name: "EXCHANGE_RATE_TYPE", type: "VARCHAR(30)", remarks: "汇率类型")   
            column(name: "EXCHANGE_RATE_QUOTATION", type: "VARCHAR(30)", remarks: "标价方法")   
            column(name: "EXCHANGE_RATE", type: "NUMBER", remarks: "汇率")   
            column(name: "AMOUNT", type: "NUMBER", remarks: "金额")   
            column(name: "FUNCTIONAL_AMOUNT", type: "NUMBER", remarks: "本位币金额")   
            column(name: "QUANTITY", type: "NUMBER", remarks: "数量")   
            column(name: "UOM", type: "VARCHAR(30)", remarks: "经营单位id")   
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司ID")   
            column(name: "OPERATION_UNIT_ID", type: "BIGINT", remarks: "经营单位id")   
            column(name: "UNIT_ID", type: "BIGINT", remarks: "部门id")   
            column(name: "POSITION_ID", type: "BIGINT", remarks: "岗位id")
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "员工id")
            column(name: "DIMENSION1_ID", type: "BIGINT", remarks: "维值1id")
            column(name: "DIMENSION2_ID", type: "BIGINT", remarks: "维值2id")
            column(name: "DIMENSION3_ID", type: "BIGINT", remarks: "维值3id")
            column(name: "DIMENSION4_ID", type: "BIGINT", remarks: "维值4id")
            column(name: "DIMENSION5_ID", type: "BIGINT", remarks: "维值5id")
            column(name: "DIMENSION6_ID", type: "BIGINT", remarks: "维值6id")
            column(name: "DIMENSION7_ID", type: "BIGINT", remarks: "维值7id")
            column(name: "DIMENSION8_ID", type: "BIGINT", remarks: "维值8id")
            column(name: "DIMENSION9_ID", type: "BIGINT", remarks: "维值9id")
            column(name: "DIMENSION10_ID", type: "BIGINT", remarks: "维值10id")
            column(name: "DIMENSION11_ID", type: "BIGINT", remarks: "维值11id")
            column(name: "DIMENSION12_ID", type: "BIGINT", remarks: "维值12id")
            column(name: "DIMENSION13_ID", type: "BIGINT", remarks: "维值13id")
            column(name: "DIMENSION14_ID", type: "BIGINT", remarks: "维值14id")
            column(name: "DIMENSION15_ID", type: "BIGINT", remarks: "维值15id")
            column(name: "DIMENSION16_ID", type: "BIGINT", remarks: "维值16id")
            column(name: "DIMENSION17_ID", type: "BIGINT", remarks: "维值17id")
            column(name: "DIMENSION18_ID", type: "BIGINT", remarks: "维值18id")
            column(name: "DIMENSION19_ID", type: "BIGINT", remarks: "维值19id")
            column(name: "DIMENSION20_ID", type: "BIGINT", remarks: "维值20id")
            column(name: "SOURCE_TYPE", type: "VARCHAR(30)", remarks: "来源类型")
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "BGT_BUDGET_RESERVE_INIT", indexName: "BGT_BUDGET_RESERVE_INIT_N1") {
                    column(name: "SESSION_ID")
                }
    
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-04-17-BGT_BALANCE_QUERY_ID_TEMP") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'BGT_BALANCE_QUERY_ID_TEMP_S', startValue:"10001")
        }

        createTable(tableName: "BGT_BALANCE_QUERY_ID_TEMP", remarks: "") {

            column(name: "SESSION_ID", type: "BIGINT", remarks: "")   
            column(name: "TYPE", type: "VARCHAR(30)", remarks: "")   
            column(name: "ID", type: "BIGINT", remarks: "")   
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-04-17-BGT_BALANCE_QUERY_DETAIL_TEMP") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'BGT_BALANCE_QUERY_DETAIL_TEMP_S', startValue:"10001")
        }

        createTable(tableName: "BGT_BALANCE_QUERY_DETAIL_TEMP", remarks: "预算余额查询结果临时表") {

            column(name: "SESSION_ID", type: "BIGINT", remarks: "系统SESSION ID")  {constraints(nullable:"false")}  
            column(name: "BGT_ORG_ID", type: "BIGINT", remarks: "")   
            column(name: "BGT_ENTITY_ID", type: "BIGINT", remarks: "")   
            column(name: "BGT_CENTER_ID", type: "BIGINT", remarks: "")   
            column(name: "STRUCTURE_ID", type: "BIGINT", remarks: "预算表id")   
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司ID")   
            column(name: "VERSION_ID", type: "BIGINT", remarks: "预算版本id")   
            column(name: "OPERATION_UNIT_ID", type: "BIGINT", remarks: "经营单位id")   
            column(name: "SCENARIO_ID", type: "BIGINT", remarks: "预算场景id")   
            column(name: "RESPONSIBILITY_CENTER_ID", type: "BIGINT", remarks: "责任中心id")   
            column(name: "BUDGET_ITEM_TYPE_ID", type: "BIGINT", remarks: "预算项目类型id")   
            column(name: "BUDGET_ITEM_ID", type: "BIGINT", remarks: "预算项目id")   
            column(name: "PERIOD_YEAR", type: "BIGINT", remarks: "年度")   
            column(name: "PERIOD_QUARTER", type: "BIGINT", remarks: "季度")   
            column(name: "PERIOD_NAME", type: "VARCHAR(30)", remarks: "期间")   
            column(name: "BGT_PERIOD_NUM", type: "BIGINT", remarks: "预算期间")   
            column(name: "CURRENCY", type: "VARCHAR(30)", remarks: "币种")   
            column(name: "BGT_AMOUNT", type: "NUMBER", remarks: "预算金额")   
            column(name: "BGT_FUN_AMOUNT", type: "NUMBER", remarks: "预算本笔金额")   
            column(name: "BGT_QUANTITY", type: "NUMBER", remarks: "预算数量")   
            column(name: "EXP_RESERVE_AMOUNT", type: "NUMBER", remarks: "预算占用金额")   
            column(name: "EXP_RESERVE_FUN_AMOUNT", type: "NUMBER", remarks: "预算占用金额")   
            column(name: "EXP_RESERVE_QUANTITY", type: "NUMBER", remarks: "预算使用数量")   
            column(name: "EXP_USED_AMOUNT", type: "NUMBER", remarks: "预算使用金额")   
            column(name: "EXP_USED_FUN_AMOUNT", type: "NUMBER", remarks: "预算使用数量")   
            column(name: "EXP_USED_QUANTITY", type: "NUMBER", remarks: "报销使用数量")   
            column(name: "EXP_AVAILABLE_AMOUNT", type: "NUMBER", remarks: "预算可用金额")   
            column(name: "EXP_AVAILABLE_FUN_AMOUNT", type: "NUMBER", remarks: "预算可用本币金额")   
            column(name: "EXP_AVAILABLE_QUANTITY", type: "NUMBER", remarks: "预算可用数量")   
            column(name: "UNIT_ID", type: "BIGINT", remarks: "部门id")   
            column(name: "UNIT_GROUP_ID", type: "BIGINT", remarks: "部门组ID")   
            column(name: "UNIT_LEVEL_ID", type: "BIGINT", remarks: "部门级别ID")   
            column(name: "POSITION_ID", type: "BIGINT", remarks: "岗位id")   
            column(name: "POSITION_GROUP_ID", type: "BIGINT", remarks: "岗位组ID")   
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "员工id")   
            column(name: "EMPLOYEE_GROUP_ID", type: "BIGINT", remarks: "员工组id")   
            column(name: "EMPLOYEE_LEVEL_ID", type: "BIGINT", remarks: "员工级别id")   
            column(name: "EMPLOYEE_JOB_ID", type: "BIGINT", remarks: "员工职务ID")   
            column(name: "DIMENSION1_ID", type: "BIGINT", remarks: "维值1id")   
            column(name: "DIMENSION2_ID", type: "BIGINT", remarks: "维值2id")   
            column(name: "DIMENSION3_ID", type: "BIGINT", remarks: "维值3id")   
            column(name: "DIMENSION4_ID", type: "BIGINT", remarks: "维值4id")   
            column(name: "DIMENSION5_ID", type: "BIGINT", remarks: "维值5id")   
            column(name: "DIMENSION6_ID", type: "BIGINT", remarks: "维值6id")   
            column(name: "DIMENSION7_ID", type: "BIGINT", remarks: "维值7id")   
            column(name: "DIMENSION8_ID", type: "BIGINT", remarks: "维值8id")   
            column(name: "DIMENSION9_ID", type: "BIGINT", remarks: "维值9id")   
            column(name: "DIMENSION10_ID", type: "BIGINT", remarks: "维值10id")   
            column(name: "DIMENSION11_ID", type: "BIGINT", remarks: "")   
            column(name: "DIMENSION12_ID", type: "BIGINT", remarks: "")   
            column(name: "DIMENSION13_ID", type: "BIGINT", remarks: "")   
            column(name: "DIMENSION14_ID", type: "BIGINT", remarks: "")   
            column(name: "DIMENSION15_ID", type: "BIGINT", remarks: "")   
            column(name: "DIMENSION16_ID", type: "BIGINT", remarks: "")   
            column(name: "DIMENSION17_ID", type: "BIGINT", remarks: "")   
            column(name: "DIMENSION18_ID", type: "BIGINT", remarks: "")   
            column(name: "DIMENSION19_ID", type: "BIGINT", remarks: "")   
            column(name: "DIMENSION20_ID", type: "BIGINT", remarks: "")   
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "BGT_BALANCE_QUERY_DETAIL_TEMP", indexName: "BGT_BALANCE_QUERY_DTL_TEMP_N1") {
                    column(name: "SESSION_ID")
                }
    
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-04-17-BGT_BALANCE_QUERY_SUMMARY_TEMP") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'BGT_BALANCE_QUERY_SUMMARY_TEMP_S', startValue:"10001")
        }

        createTable(tableName: "BGT_BALANCE_QUERY_SUMMARY_TEMP", remarks: "预算余额查询临时表") {


            column(name: "SESSION_ID", type: "BIGINT", remarks: "系统SESSION ID")  {constraints(nullable:"false")}  
            column(name: "STRUCTURE_ID", type: "BIGINT", remarks: "预算表id")   
            column(name: "BGT_ORG_ID", type: "BIGINT", remarks: "")   
            column(name: "BGT_ENTITY_ID", type: "BIGINT", remarks: "")   
            column(name: "BGT_CENTER_ID", type: "BIGINT", remarks: "")   
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司ID")   
            column(name: "VERSION_ID", type: "BIGINT", remarks: "版本ID")   
            column(name: "OPERATION_UNIT_ID", type: "BIGINT", remarks: "经营单位id")   
            column(name: "SCENARIO_ID", type: "BIGINT", remarks: "预算场景id")   
            column(name: "RESPONSIBILITY_CENTER_ID", type: "BIGINT", remarks: "责任中心id")   
            column(name: "BUDGET_ITEM_TYPE_ID", type: "BIGINT", remarks: "预算项目类型id")   
            column(name: "BUDGET_ITEM_ID", type: "BIGINT", remarks: "预算项目id")   
            column(name: "PERIOD_YEAR", type: "BIGINT", remarks: "预算年度")   
            column(name: "PERIOD_QUARTER", type: "BIGINT", remarks: "季度")   
            column(name: "PERIOD_NAME", type: "VARCHAR(30)", remarks: "期间")   
            column(name: "BGT_PERIOD_NUM", type: "BIGINT", remarks: "预算期间")   
            column(name: "CURRENCY", type: "VARCHAR(30)", remarks: "币种")   
            column(name: "BGT_AMOUNT", type: "NUMBER", remarks: "预算金额")   
            column(name: "BGT_FUN_AMOUNT", type: "NUMBER", remarks: "预算本币金额")   
            column(name: "BGT_QUANTITY", type: "NUMBER", remarks: "预算数量")   
            column(name: "EXP_RESERVE_AMOUNT", type: "NUMBER", remarks: "预算保留金额")   
            column(name: "EXP_RESERVE_FUN_AMOUNT", type: "NUMBER", remarks: "预算保留金额")   
            column(name: "EXP_RESERVE_QUANTITY", type: "NUMBER", remarks: "预算保留数量")   
            column(name: "EXP_USED_AMOUNT", type: "NUMBER", remarks: "预算占用金额")   
            column(name: "EXP_USED_FUN_AMOUNT", type: "NUMBER", remarks: "预算占用本币金额")   
            column(name: "EXP_USED_QUANTITY", type: "NUMBER", remarks: "报销使用数量")   
            column(name: "EXP_AVAILABLE_AMOUNT", type: "NUMBER", remarks: "预算可用金额")   
            column(name: "EXP_AVAILABLE_FUN_AMOUNT", type: "NUMBER", remarks: "预算可用金额")   
            column(name: "EXP_AVAILABLE_QUANTITY", type: "NUMBER", remarks: "保留可用金额")   
            column(name: "UNIT_ID", type: "BIGINT", remarks: "部门id")   
            column(name: "UNIT_GROUP_ID", type: "BIGINT", remarks: "部门组ID")   
            column(name: "UNIT_LEVEL_ID", type: "BIGINT", remarks: "部门级别ID")   
            column(name: "POSITION_ID", type: "BIGINT", remarks: "岗位id")   
            column(name: "POSITION_GROUP_ID", type: "BIGINT", remarks: "岗位组ID")   
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "员工id")   
            column(name: "EMPLOYEE_GROUP_ID", type: "BIGINT", remarks: "员工组id")   
            column(name: "EMPLOYEE_LEVEL_ID", type: "BIGINT", remarks: "员工等级id")   
            column(name: "EMPLOYEE_JOB_ID", type: "BIGINT", remarks: "员工职务ID")   
            column(name: "DIMENSION1_ID", type: "BIGINT", remarks: "维值1id")   
            column(name: "DIMENSION2_ID", type: "BIGINT", remarks: "维值2id")   
            column(name: "DIMENSION3_ID", type: "BIGINT", remarks: "维值3id")   
            column(name: "DIMENSION4_ID", type: "BIGINT", remarks: "维值4id")   
            column(name: "DIMENSION5_ID", type: "BIGINT", remarks: "维值5id")   
            column(name: "DIMENSION6_ID", type: "BIGINT", remarks: "维值6id")   
            column(name: "DIMENSION7_ID", type: "BIGINT", remarks: "维值7id")   
            column(name: "DIMENSION8_ID", type: "BIGINT", remarks: "维值8id")   
            column(name: "DIMENSION9_ID", type: "BIGINT", remarks: "维值9id")   
            column(name: "DIMENSION10_ID", type: "BIGINT", remarks: "维值10id")   
            column(name: "DIMENSION11_ID", type: "BIGINT", remarks: "")
            column(name: "DIMENSION12_ID", type: "BIGINT", remarks: "")
            column(name: "DIMENSION13_ID", type: "BIGINT", remarks: "")
            column(name: "DIMENSION14_ID", type: "BIGINT", remarks: "")
            column(name: "DIMENSION15_ID", type: "BIGINT", remarks: "")
            column(name: "DIMENSION16_ID", type: "BIGINT", remarks: "")
            column(name: "DIMENSION17_ID", type: "BIGINT", remarks: "")
            column(name: "DIMENSION18_ID", type: "BIGINT", remarks: "")
            column(name: "DIMENSION19_ID", type: "BIGINT", remarks: "")
            column(name: "DIMENSION20_ID", type: "BIGINT", remarks: "")
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "BGT_BALANCE_QUERY_SUMMARY_TEMP", indexName: "BGT_BALANCE_QUERY_SUM_TEMP_N1") {
                    column(name: "SESSION_ID")
                }
    
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-04-17-BGT_BALANCE_QUERY_RESULT") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'BGT_BALANCE_QUERY_RESULT_S', startValue:"10001")
        }

        createTable(tableName: "BGT_BALANCE_QUERY_RESULT", remarks: "预算余额查询结果临时表") {

            column(name: "SESSION_ID", type: "BIGINT", remarks: "系统SESSION ID")  {constraints(nullable:"false")}  
            column(name: "BGT_ORG_ID", type: "BIGINT", remarks: "")   
            column(name: "BGT_ENTITY_ID", type: "BIGINT", remarks: "")   
            column(name: "BGT_CENTER_ID", type: "BIGINT", remarks: "")   
            column(name: "STRUCTURE_ID", type: "BIGINT", remarks: "预算表id")   
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司ID")   
            column(name: "VERSION_ID", type: "BIGINT", remarks: "预算版本id")   
            column(name: "OPERATION_UNIT_ID", type: "BIGINT", remarks: "经营单位id")   
            column(name: "SCENARIO_ID", type: "BIGINT", remarks: "预算场景id")   
            column(name: "RESPONSIBILITY_CENTER_ID", type: "BIGINT", remarks: "责任中心id")   
            column(name: "BUDGET_ITEM_TYPE_ID", type: "BIGINT", remarks: "预算项目类型id")   
            column(name: "BUDGET_ITEM_ID", type: "BIGINT", remarks: "预算项目id")   
            column(name: "PERIOD_YEAR", type: "BIGINT", remarks: "年度")   
            column(name: "PERIOD_QUARTER", type: "BIGINT", remarks: "季度")   
            column(name: "PERIOD_NAME", type: "VARCHAR(30)", remarks: "期间")   
            column(name: "BGT_PERIOD_NUM", type: "BIGINT", remarks: "预算期间")   
            column(name: "CURRENCY", type: "VARCHAR(30)", remarks: "币种")   
            column(name: "BGT_AMOUNT", type: "NUMBER", remarks: "预算金额")   
            column(name: "BGT_FUN_AMOUNT", type: "NUMBER", remarks: "预算本笔金额")   
            column(name: "BGT_QUANTITY", type: "NUMBER", remarks: "预算数量")   
            column(name: "EXP_RESERVE_AMOUNT", type: "NUMBER", remarks: "预算占用金额")   
            column(name: "EXP_RESERVE_FUN_AMOUNT", type: "NUMBER", remarks: "预算占用金额")   
            column(name: "EXP_RESERVE_QUANTITY", type: "NUMBER", remarks: "预算使用数量")   
            column(name: "EXP_USED_AMOUNT", type: "NUMBER", remarks: "预算使用金额")   
            column(name: "EXP_USED_FUN_AMOUNT", type: "NUMBER", remarks: "预算使用数量")   
            column(name: "EXP_USED_QUANTITY", type: "NUMBER", remarks: "报销使用数量")   
            column(name: "EXP_AVAILABLE_AMOUNT", type: "NUMBER", remarks: "预算可用金额")   
            column(name: "EXP_AVAILABLE_FUN_AMOUNT", type: "NUMBER", remarks: "预算可用本币金额")   
            column(name: "EXP_AVAILABLE_QUANTITY", type: "NUMBER", remarks: "预算可用数量")   
            column(name: "UNIT_ID", type: "BIGINT", remarks: "部门id")   
            column(name: "UNIT_GROUP_ID", type: "BIGINT", remarks: "部门组ID")   
            column(name: "UNIT_LEVEL_ID", type: "BIGINT", remarks: "部门级别ID")   
            column(name: "POSITION_ID", type: "BIGINT", remarks: "岗位id")   
            column(name: "POSITION_GROUP_ID", type: "BIGINT", remarks: "岗位组ID")   
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "员工id")   
            column(name: "EMPLOYEE_GROUP_ID", type: "BIGINT", remarks: "员工组id")   
            column(name: "EMPLOYEE_LEVEL_ID", type: "BIGINT", remarks: "员工级别id")   
            column(name: "EMPLOYEE_JOB_ID", type: "BIGINT", remarks: "员工职务ID")   
            column(name: "DIMENSION1_ID", type: "BIGINT", remarks: "维值1id")   
            column(name: "DIMENSION2_ID", type: "BIGINT", remarks: "维值2id")   
            column(name: "DIMENSION3_ID", type: "BIGINT", remarks: "维值3id")   
            column(name: "DIMENSION4_ID", type: "BIGINT", remarks: "维值4id")   
            column(name: "DIMENSION5_ID", type: "BIGINT", remarks: "维值5id")   
            column(name: "DIMENSION6_ID", type: "BIGINT", remarks: "维值6id")   
            column(name: "DIMENSION7_ID", type: "BIGINT", remarks: "维值7id")   
            column(name: "DIMENSION8_ID", type: "BIGINT", remarks: "维值8id")   
            column(name: "DIMENSION9_ID", type: "BIGINT", remarks: "维值9id")   
            column(name: "DIMENSION10_ID", type: "BIGINT", remarks: "维值10id")   
            column(name: "DIMENSION11_ID", type: "BIGINT", remarks: "")   
            column(name: "DIMENSION12_ID", type: "BIGINT", remarks: "")   
            column(name: "DIMENSION13_ID", type: "BIGINT", remarks: "")   
            column(name: "DIMENSION14_ID", type: "BIGINT", remarks: "")   
            column(name: "DIMENSION15_ID", type: "BIGINT", remarks: "")   
            column(name: "DIMENSION16_ID", type: "BIGINT", remarks: "")   
            column(name: "DIMENSION17_ID", type: "BIGINT", remarks: "")   
            column(name: "DIMENSION18_ID", type: "BIGINT", remarks: "")   
            column(name: "DIMENSION19_ID", type: "BIGINT", remarks: "")   
            column(name: "DIMENSION20_ID", type: "BIGINT", remarks: "")   
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "BGT_BALANCE_QUERY_RESULT", indexName: "BGT_BALANCE_QUERY_RESULT_N1") {
                    column(name: "SESSION_ID")
                }
    
    }


}