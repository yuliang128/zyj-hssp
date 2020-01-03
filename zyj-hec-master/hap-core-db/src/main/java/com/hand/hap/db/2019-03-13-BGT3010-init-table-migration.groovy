package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-03-13-BGT3010-init-table-migration.groovy') {


    changeSet(author: "hongliu.ye@hand-china.com", id: "2019-03-13-BGT_BALANCE_QUERY_COND_H") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_BALANCE_QUERY_COND_H_S', startValue: "10001")
        }

        createTable(tableName: "BGT_BALANCE_QUERY_COND_H", remarks: "预算余额查询方案头表") {

            column(name: "BALANCE_QUERY_COND_H_ID", type: "BIGINT", autoIncrement: "true", startWith: "10001", remarks: "预算余额查询方案头ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_BALANCE_QUERY_COND_H_PK")
            }
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司ID") { constraints(nullable: "false") }
            column(name: "BALANCE_QUERY_CONDITION_CODE", type: "VARCHAR(30)", remarks: "方案代码") {
                constraints(nullable: "false")
            }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述") { constraints(nullable: "false") }
            column(name: "BUDGET_STRC_CODE", type: "VARCHAR(30)", remarks: "预算表代码")
            column(name: "SCENARIO_CODE", type: "VARCHAR(30)", remarks: "预算场景代码")
            column(name: "VERSION_CODE", type: "VARCHAR(30)", remarks: "预算版本代码")
            column(name: "AMOUNT_FLAG", type: "VARCHAR(1)", remarks: "金额标志")
            column(name: "QUANTITY_FLAG", type: "VARCHAR(1)", remarks: "数量标志")
            column(name: "PERIOD_SUMMARY_FLAG", type: "VARCHAR(1)", remarks: "期间汇总标志")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "COMPANY_ID,BALANCE_QUERY_CONDITION_CODE", tableName: "BGT_BALANCE_QUERY_COND_H", constraintName: "BGT_BALANCE_QUERY_COND_H_U1")
    }

    changeSet(author: "hongliu.ye@hand-china.com", id: "2019-03-13-BGT_BALANCE_QUERY_COND_H_TL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_BALANCE_QUERY_COND_H_TL_S', startValue: "10001")
        }

        createTable(tableName: "BGT_BALANCE_QUERY_COND_H_TL", remarks: "预算余额查询方案头表多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "BALANCE_QUERY_COND_H_ID", type: "BIGINT", remarks: "预算余额查询方案头ID") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
    }

    changeSet(author: "hongliu.ye@hand-china.com", id: "2019-03-13-BGT_BALANCE_QUERY_COND_L") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_BALANCE_QUERY_COND_L_S', startValue: "10001")
        }

        createTable(tableName: "BGT_BALANCE_QUERY_COND_L", remarks: "预算余额查询方案行表") {

            column(name: "BALANCE_QUERY_COND_H_ID", type: "BIGINT", remarks: "预算余额查询方案头ID") {
                constraints(nullable: "false")
            }
            column(name: "BALANCE_QUERY_COND_L_ID", type: "BIGINT", autoIncrement: "true", startWith: "10001", remarks: "预算余额查询方案行ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_BALANCE_QUERY_COND_L_PK")
            }
            column(name: "SESSION_ID", type: "VARCHAR(100)", remarks: "系统SESSION ID") { constraints(nullable: "false") }
            column(name: "PARAMETER", type: "VARCHAR(100)", remarks: "参数类型") { constraints(nullable: "false") }
            column(name: "PARAMETER_ID", type: "BIGINT", remarks: "参数ID")
            column(name: "PARAMETER_CODE", type: "VARCHAR(100)", remarks: "参数代码")
            column(name: "PARAMETER_UPPER_LIMIT", type: "VARCHAR(100)", remarks: "参数代码止值")
            column(name: "PARAMETER_LOWER_LIMIT", type: "VARCHAR(100)", remarks: "参数代码始值")
            column(name: "CONTROL_RULE_RANGE", type: "VARCHAR(100)", remarks: "取值范围")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BGT_BALANCE_QUERY_COND_L", indexName: "BGT_BALANCE_QUERY_COND_L_N1") {
            column(name: "BALANCE_QUERY_COND_H_ID")
            column(name: "BALANCE_QUERY_COND_L_ID")
        }

        addUniqueConstraint(columnNames: "PARAMETER,BALANCE_QUERY_COND_H_ID", tableName: "BGT_BALANCE_QUERY_COND_L", constraintName: "BGT_BALANCE_QUERY_COND_L_U1")
    }


    changeSet(author: "hongliu.ye@hand-china.com", id: "2019-03-13-BGT_BALANCE_QUERY_RESULT_TMP") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_BALANCE_QUERY_RESULT_TMP_S', startValue: "10001")
        }

        createTable(tableName: "BGT_BALANCE_QUERY_RESULT_TMP", remarks: "预算余额查询结果临时表") {

            column(name: "SESSION_ID", type: "VARCHAR(100)", remarks: "系统SESSION ID") { constraints(nullable: "false") }
            column(name: "BGT_ORG_ID", type: "BIGINT", remarks: "预算组织ID")
            column(name: "BGT_ENTITY_ID", type: "BIGINT", remarks: "预算实体ID")
            column(name: "BGT_CENTER_ID", type: "BIGINT", remarks: "预算中心ID")
            column(name: "STRUCTURE_ID", type: "BIGINT", remarks: "预算表ID")
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司ID")
            column(name: "VERSION_ID", type: "BIGINT", remarks: "预算版本ID")
            column(name: "OPERATION_UNIT_ID", type: "BIGINT", remarks: "经营单位ID")
            column(name: "SCENARIO_ID", type: "BIGINT", remarks: "预算场景ID")
            column(name: "RESPONSIBILITY_CENTER_ID", type: "BIGINT", remarks: "责任中心ID")
            column(name: "BUDGET_ITEM_TYPE_ID", type: "BIGINT", remarks: "预算项目类型ID")
            column(name: "BUDGET_ITEM_ID", type: "BIGINT", remarks: "预算项目ID")
            column(name: "PERIOD_YEAR", type: "BIGINT", remarks: "年度")
            column(name: "PERIOD_QUARTER", type: "BIGINT", remarks: "季度")
            column(name: "PERIOD_NAME", type: "VARCHAR(30)", remarks: "期间")
            column(name: "BGT_PERIOD_NUM", type: "BIGINT", remarks: "预算期间")
            column(name: "CURRENCY", type: "VARCHAR(30)", remarks: "币种")
            column(name: "BGT_AMOUNT", type: "NUMBER", remarks: "预算金额")
            column(name: "BGT_FUN_AMOUNT", type: "NUMBER", remarks: "预算本位币金额")
            column(name: "BGT_QUANTITY", type: "BIGINT", remarks: "预算数量")
            column(name: "EXP_RESERVE_AMOUNT", type: "NUMBER", remarks: "预算保留金额")
            column(name: "EXP_RESERVE_FUN_AMOUNT", type: "NUMBER", remarks: "预算保留本位币金额")
            column(name: "EXP_RESERVE_QUANTITY", type: "BIGINT", remarks: "预算保留数量")
            column(name: "EXP_USED_AMOUNT", type: "NUMBER", remarks: "预算发生金额")
            column(name: "EXP_USED_FUN_AMOUNT", type: "NUMBER", remarks: "预算发生本位币金额")
            column(name: "EXP_USED_QUANTITY", type: "BIGINT", remarks: "预算发生数量")
            column(name: "EXP_AVAILABLE_AMOUNT", type: "NUMBER", remarks: "预算可用金额")
            column(name: "EXP_AVAILABLE_FUN_AMOUNT", type: "NUMBER", remarks: "预算可用本位币金额")
            column(name: "EXP_AVAILABLE_QUANTITY", type: "BIGINT", remarks: "预算可用数量")
            column(name: "UNIT_ID", type: "BIGINT", remarks: "部门ID")
            column(name: "UNIT_GROUP_ID", type: "BIGINT", remarks: "部门组ID")
            column(name: "UNIT_LEVEL_ID", type: "BIGINT", remarks: "部门级别ID")
            column(name: "POSITION_ID", type: "BIGINT", remarks: "岗位ID")
            column(name: "POSITION_GROUP_ID", type: "BIGINT", remarks: "岗位组ID")
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "员工ID")
            column(name: "EMPLOYEE_GROUP_ID", type: "BIGINT", remarks: "员工组ID")
            column(name: "EMPLOYEE_LEVEL_ID", type: "BIGINT", remarks: "员工级别ID")
            column(name: "EMPLOYEE_JOB_ID", type: "BIGINT", remarks: "员工职务ID")
            column(name: "DIMENSION1_ID", type: "BIGINT", remarks: "维值1ID")
            column(name: "DIMENSION2_ID", type: "BIGINT", remarks: "维值2ID")
            column(name: "DIMENSION3_ID", type: "BIGINT", remarks: "维值3ID")
            column(name: "DIMENSION4_ID", type: "BIGINT", remarks: "维值4ID")
            column(name: "DIMENSION5_ID", type: "BIGINT", remarks: "维值5ID")
            column(name: "DIMENSION6_ID", type: "BIGINT", remarks: "维值6ID")
            column(name: "DIMENSION7_ID", type: "BIGINT", remarks: "维值7ID")
            column(name: "DIMENSION8_ID", type: "BIGINT", remarks: "维值8ID")
            column(name: "DIMENSION9_ID", type: "BIGINT", remarks: "维值9ID")
            column(name: "DIMENSION10_ID", type: "BIGINT", remarks: "维值10ID")
            column(name: "DIMENSION11_ID", type: "BIGINT", remarks: "维值11ID")
            column(name: "DIMENSION12_ID", type: "BIGINT", remarks: "维值12ID")
            column(name: "DIMENSION13_ID", type: "BIGINT", remarks: "维值13ID")
            column(name: "DIMENSION14_ID", type: "BIGINT", remarks: "维值14ID")
            column(name: "DIMENSION15_ID", type: "BIGINT", remarks: "维值15ID")
            column(name: "DIMENSION16_ID", type: "BIGINT", remarks: "维值16ID")
            column(name: "DIMENSION17_ID", type: "BIGINT", remarks: "维值17ID")
            column(name: "DIMENSION18_ID", type: "BIGINT", remarks: "维值18ID")
            column(name: "DIMENSION19_ID", type: "BIGINT", remarks: "维值19ID")
            column(name: "DIMENSION20_ID", type: "BIGINT", remarks: "维值20ID")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BGT_BALANCE_QUERY_RESULT_TMP", indexName: "BGT_BALANCE_QUERY_RES_TMP_N1") {
            column(name: "SESSION_ID")
        }

    }


}