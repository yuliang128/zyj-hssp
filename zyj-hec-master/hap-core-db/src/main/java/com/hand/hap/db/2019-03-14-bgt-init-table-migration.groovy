package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-03-14-BGT1080-init-table-migration.groovy') {

    changeSet(author: "yuting.gui@hand-china.com", id: "2019-03-14-BGT_ORG_PERIOD_STATUS") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_ORG_PERIOD_STATUS_S', startValue: "10001")
        }

        createTable(tableName: "BGT_ORG_PERIOD_STATUS", remarks: "预算组织级预算期间状态表") {

            column(name: "PERIOD_STATUS_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "BGT_ORG_PERIOD_STATUS_PK")}
            column(name: "BGT_ORG_ID", type: "BIGINT", remarks: "预算组织ID") { constraints(nullable: "false") }
            column(name: "BGT_PERIOD_SET_CODE", type: "VARCHAR(30)", remarks: "预算期间") { constraints(nullable: "false") }
            column(name: "BGT_PERIOD_YEAR", type: "BIGINT", remarks: "年度") { constraints(nullable: "false") }
            column(name: "BGT_PERIOD_NUM", type: "BIGINT", remarks: "预算期间数") { constraints(nullable: "false") }
            column(name: "PERIOD_NAME", type: "VARCHAR(30)", remarks: "期间")
            column(name: "BGT_PERIOD_STATUS_CODE", type: "VARCHAR(20)", remarks: "预算期间状态")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BGT_ORG_PERIOD_STATUS", indexName: "BGT_ORG_PERIOD_STATUS_N1") {
            column(name: "BGT_PERIOD_NUM")
            column(name: "BGT_ORG_ID")
            column(name: "BGT_PERIOD_STATUS_CODE")
        }
    }

    changeSet(author: "yuting.gui@hand-china.com", id: "2019-03-14-BGT_ENTITY_PERIOD_STATUS") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_ENTITY_PERIOD_STATUS_S', startValue: "10001")
        }

        createTable(tableName: "BGT_ENTITY_PERIOD_STATUS", remarks: "预算实体级预算期间状态表") {

            column(name: "PERIOD_STATUS_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "BGT_ENTITY_PERIOD_STATUS_PK")}
            column(name: "BGT_ENTITY_ID", type: "BIGINT", remarks: "预算实体ID") { constraints(nullable: "false") }
            column(name: "BGT_ORG_ID", type: "BIGINT", remarks: "预算组织ID") { constraints(nullable: "false") }
            column(name: "BGT_PERIOD_SET_CODE", type: "VARCHAR(30)", remarks: "预算期间") { constraints(nullable: "false") }
            column(name: "BGT_PERIOD_YEAR", type: "BIGINT", remarks: "年度") { constraints(nullable: "false") }
            column(name: "BGT_PERIOD_NUM", type: "BIGINT", remarks: "预算期间数") { constraints(nullable: "false") }
            column(name: "PERIOD_NAME", type: "VARCHAR(30)", remarks: "期间")
            column(name: "BGT_PERIOD_STATUS_CODE", type: "VARCHAR(20)", remarks: "预算期间状态")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BGT_ENTITY_PERIOD_STATUS", indexName: "BGT_ENTITY_PERIOD_STATUS_N1") {
            column(name: "BGT_PERIOD_NUM")
            column(name: "BGT_ENTITY_ID")
            column(name: "BGT_PERIOD_STATUS_CODE")
        }
    }


}