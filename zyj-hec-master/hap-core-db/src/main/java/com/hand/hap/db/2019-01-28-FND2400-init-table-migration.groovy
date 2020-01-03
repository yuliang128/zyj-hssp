package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-01-28-FND2400-init-table-migration.groovy') {


    changeSet(author: "hongliu.ye@hand-china.com", id: "2019-01-28-PUR_VENDER_TYPE") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'PUR_VENDER_TYPE_S', startValue: "10001")
        }

        createTable(tableName: "PUR_VENDER_TYPE", remarks: "供应商类型表") {

            column(name: "VENDER_TYPE_ID", type: "BIGINT", autoIncrement: "true", startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "PUR_VENDER_TYPE_PK")
            }
            column(name: "VENDER_TYPE_CODE", type: "VARCHAR(30)", remarks: "供应商类型代码") { constraints(nullable: "false") }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "供应商类型名称")
            column(name: "ONE_OFF_FLAG", type: "VARCHAR(1)", remarks: "一次性标志") { constraints(nullable: "false") }
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "VENDER_TYPE_CODE", tableName: "PUR_VENDER_TYPE", constraintName: "PUR_VENDER_TYPE_U1")
    }

    changeSet(author: "hongliu.ye@hand-china.com", id: "2019-01-28-PUR_VENDER_TYPE_TL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'PUR_VENDER_TYPE_TL_S', startValue: "10001")
        }

        createTable(tableName: "PUR_VENDER_TYPE_TL", remarks: "供应商类型表多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "VENDER_TYPE_ID", type: "BIGINT", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "供应商类型名称")
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

    changeSet(author: "hongliu.ye@hand-china.com", id: "2019-01-29-PUR_VENDER_TYPE_REF_AE") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'PUR_VENDER_TYPE_REF_AE_S', startValue: "10001")
        }

        createTable(tableName: "PUR_VENDER_TYPE_REF_AE", remarks: "供应商类型分配核算主体表") {

            column(name: "REF_ID", type: "BIGINT", autoIncrement: "true", startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "PUR_VENDER_TYPE_REF_AE_PK")
            }
            column(name: "VENDER_TYPE_ID", type: "BIGINT", remarks: "供应商类型ID") { constraints(nullable: "false") }
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体ID") { constraints(nullable: "false") }
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "ACC_ENTITY_ID,VENDER_TYPE_ID", tableName: "PUR_VENDER_TYPE_REF_AE", constraintName: "PUR_VENDER_TYPE_REF_AE_U1")
    }

}