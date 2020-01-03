package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-01-08-FND2130-init-table-migration.groovy') {


    changeSet(author: "hongliu.ye@hand-china.com", id: "2019-01-08-GLD_COA_STRUCTURE") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'GLD_COA_STRUCTURE_S', startValue: "10001")
        }

        createTable(tableName: "GLD_COA_STRUCTURE", remarks: "科目结构") {

            column(name: "COA_STRUCTURE_ID", type: "BIGINT", autoIncrement: "true", startWith: "10001", remarks: "科目结构ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "GLD_COA_STRUCTURE_PK")
            }
            column(name: "COA_STRUCTURE_CODE", type: "VARCHAR(30)", remarks: "科目结构代码") {
                constraints(nullable: "false")
            }
            column(name: "STRUCTURE_FORMAT", type: "VARCHAR(100)", remarks: "科目结构") { constraints(nullable: "false") }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述")
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "COA_STRUCTURE_CODE", tableName: "GLD_COA_STRUCTURE", constraintName: "GLD_COA_STRUCTURE_U1")
        addUniqueConstraint(columnNames: "DESCRIPTION", tableName: "GLD_COA_STRUCTURE", constraintName: "GLD_COA_STRUCTURE_U2")
    }

    changeSet(author: "hongliu.ye@hand-china.com", id: "2019-01-08-GLD_COA_STRUCTURE_TL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'GLD_COA_STRUCTURE_TL_S', startValue: "10001")
        }

        createTable(tableName: "GLD_COA_STRUCTURE_TL", remarks: "科目结构多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "COA_STRUCTURE_ID", type: "BIGINT", remarks: "科目结构ID") {
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