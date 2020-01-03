package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-01-16-exp-init-table-migration.groovy') {


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-16-EXP_COM_EMP_GROUP_REF_EMP") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'EXP_COM_EMP_GROUP_REF_EMP_S', startValue: "10001")
        }

        createTable(tableName: "EXP_COM_EMP_GROUP_REF_EMP", remarks: "公司级员工组分配员工") {

            column(name: "REF_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "pk") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "EXP_COM_EMP_GROUP_REF_EMP_PK")
            }
            column(name: "EMP_GROUP_COM_ASSIGN_ID", type: "BIGINT", remarks: "管理组织级员工组公司分配ID") {
                constraints(nullable: "false")
            }
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "员工ID") { constraints(nullable: "false") }
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

        addUniqueConstraint(columnNames: "EMPLOYEE_ID,EMP_GROUP_COM_ASSIGN_ID", tableName: "EXP_COM_EMP_GROUP_REF_EMP", constraintName: "EXP_COM_EMP_GROUP_REF_EMPS_U1")
    }


}