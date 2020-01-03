package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-01-21-FND1130-init-table-migration.groovy') {


    changeSet(author: "hongliu.ye@hand-china.com", id: "2019-01-21-FND_CENTRALIZED_MANAGING") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'FND_CENTRALIZED_MANAGING_S', startValue: "10001")
        }

        createTable(tableName: "FND_CENTRALIZED_MANAGING", remarks: "归口管理表") {

            column(name: "CENTRALIZED_MAG_ID", type: "BIGINT", autoIncrement: "true", startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "FND_CENTRALIZED_MANAGING_PK")
            }
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "管理公司ID") { constraints(nullable: "false") }
            column(name: "APPROVE_TYPE", type: "VARCHAR(30)", remarks: "审批规则") { constraints(nullable: "false") }
            column(name: "CONDITION_TYPE", type: "VARCHAR(30)", remarks: "归口条件类型") { constraints(nullable: "false") }
            column(name: "CONDITION_VALUE_ID", type: "BIGINT", remarks: "归口条件值ID") { constraints(nullable: "false") }
            column(name: "CENTRALIZED_MAG_TYPE", type: "VARCHAR(30)", remarks: "归口管理类型") {
                constraints(nullable: "false")
            }
            column(name: "CENTRALIZED_MAG_VALUE_ID", type: "BIGINT", remarks: "归口管理明细值ID") {
                constraints(nullable: "false")
            }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "FND_CENTRALIZED_MANAGING", indexName: "FND_CENTRALIZED_MANAGING_N1") {
            column(name: "APPROVE_TYPE")
        }

        addUniqueConstraint(columnNames: "CONDITION_VALUE_ID,APPROVE_TYPE,COMPANY_ID,CONDITION_TYPE", tableName: "FND_CENTRALIZED_MANAGING", constraintName: "FND_CENTRALIZED_MANAGING_U1")
    }


}