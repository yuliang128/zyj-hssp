package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-01-23-FND1130-init-table-migration.groovy') {


    changeSet(author: "hongliu.ye@hand-china.com", id: "2019-01-23-EXP_MO_EXPENSE_ITEM") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'EXP_MO_EXPENSE_ITEM_S', startValue: "10001")
        }

        createTable(tableName: "EXP_MO_EXPENSE_ITEM", remarks: "费用项目") {

            column(name: "MO_EXPENSE_ITEM_ID", type: "BIGINT", autoIncrement: "true", startWith: "10001", remarks: "费用项目ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "EXP_MO_EXPENSE_ITEM_PK")
            }
            column(name: "MAG_ORG_ID", type: "BIGINT", remarks: "管理组织ID") { constraints(nullable: "false") }
            column(name: "MO_EXPENSE_ITEM_CODE", type: "VARCHAR(30)", remarks: "费用项目代码") {
                constraints(nullable: "false")
            }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述") { constraints(nullable: "false") }
            column(name: "CURRENCY_CODE", type: "VARCHAR(30)", remarks: "币种")
            column(name: "STANDARD_PRICE", type: "BIGINT", remarks: "标准费率")
            column(name: "MO_REQ_ITEM_ID", type: "BIGINT", remarks: "申请项目ID")
            column(name: "BUDGET_ITEM_ID", type: "BIGINT", remarks: "预算项目ID")
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "EXP_MO_EXPENSE_ITEM", indexName: "EXP_MO_EXPENSE_ITEM_N1") {
            column(name: "MO_REQ_ITEM_ID")
        }
        createIndex(tableName: "EXP_MO_EXPENSE_ITEM", indexName: "EXP_MO_EXPENSE_ITEM_N2") {
            column(name: "BUDGET_ITEM_ID")
        }

        addUniqueConstraint(columnNames: "MAG_ORG_ID,MO_EXPENSE_ITEM_CODE", tableName: "EXP_MO_EXPENSE_ITEM", constraintName: "EXP_MO_EXPENSE_ITEM_U1")
    }

    changeSet(author: "hongliu.ye@hand-china.com", id: "2019-01-23-EXP_MO_EXPENSE_ITEM_TL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'EXP_MO_EXPENSE_ITEM_TL_S', startValue: "10001")
        }

        createTable(tableName: "EXP_MO_EXPENSE_ITEM_TL", remarks: "费用项目多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "MO_EXPENSE_ITEM_ID", type: "BIGINT", remarks: "费用项目ID") {
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

}