package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-04-24-CSH-init-table-migration.groovy') {


    changeSet(author: "jialin.xing@hand-china.com", id: "2019-04-24-CSH_MO_REPAY_REG_REF_EMP_GP") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'CSH_MO_REPAY_REG_REF_EMP_GP_S', startValue: "10001")
        }

        createTable(tableName: "CSH_MO_REPAY_REG_REF_EMP_GP", remarks: "管理组织级还款申请单类型分配员工组") {

            column(name: "REF_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CSH_MO_REPAY_REG_REF_EMP_GP_PK")
            }
            column(name: "MO_REPAYMENT_REG_TYPE_ID", type: "BIGINT", remarks: "管理组织级还款申请单类型ID") {
                constraints(nullable: "false")
            }
            column(name: "MO_EMP_GROUP_ID", type: "BIGINT", remarks: "管理组织级员工组ID") { constraints(nullable: "false") }
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

        addUniqueConstraint(columnNames: "MO_EMP_GROUP_ID,MO_REPAYMENT_REG_TYPE_ID", tableName: "CSH_MO_REPAY_REG_REF_EMP_GP", constraintName: "CSH_MO_REPAY_REG_REF_GP_U1")
    }


    changeSet(author: "jialin.xing@hand-china.com", id: "2019-04-24-CSH_MO_REPAY_REG_ASGN_COM") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'CSH_MO_REPAY_REG_ASGN_COM_S', startValue: "10001")
        }

        createTable(tableName: "CSH_MO_REPAY_REG_ASGN_COM", remarks: "管理组织级还款申请单类型分配公司") {

            column(name: "ASSIGN_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键，共其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "CSH_MO_REPAY_REG_ASGN_COM_PK")
            }
            column(name: "MO_REPAYMENT_REG_TYPE_ID", type: "BIGINT", remarks: "管理组织级还款申请单类型ID") {
                constraints(nullable: "false")
            }
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "管理公司ID") { constraints(nullable: "false") }
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

        addUniqueConstraint(columnNames: "COMPANY_ID,MO_REPAYMENT_REG_TYPE_ID", tableName: "CSH_MO_REPAY_REG_ASGN_COM", constraintName: "CSH_MO_REPAY_REG_ASGN_COM_U1")
    }


}