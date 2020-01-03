package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-01-29-EXP4020-init-table-migration.groovy') {


    changeSet(author: "jiangxz", id: "2019-01-29-EXP_MO_REQ_TYPE") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'EXP_MO_REQ_TYPE_S', startValue: "10001")
        }

        createTable(tableName: "EXP_MO_REQ_TYPE", remarks: "管理组织级申请单类型定义") {

            column(name: "MO_EXP_REQ_TYPE_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "组织架构级申请单类型ID，PK") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "EXP_MO_REQ_TYPE_PK")
            }
            column(name: "MAG_ORG_ID", type: "BIGINT", remarks: "组织架构ID") { constraints(nullable: "false") }
            column(name: "MO_EXP_REQ_TYPE_CODE", type: "VARCHAR(30)", remarks: "组织架构级申请单类型代码") {
                constraints(nullable: "false")
            }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述ID") { constraints(nullable: "false") }
            column(name: "DOCUMENT_PAGE_TYPE", type: "VARCHAR(30)", remarks: "单据页面类型") {
                constraints(nullable: "false")
            }
            column(name: "CURRENCY_CODE", type: "VARCHAR(30)", remarks: "币种")
            column(name: "ACCRUED_FLAG", type: "VARCHAR(1)", remarks: "弃用，增长标志")
            column(name: "LINE_NUMBER_BEGINNING", type: "BIGINT", remarks: "开始行号")
            column(name: "STEP_LENGTH", type: "BIGINT", remarks: "步长")
            column(name: "AUTO_APPROVE_FLAG", type: "VARCHAR(1)", remarks: "自审批标志") { constraints(nullable: "false") }
            column(name: "AUTO_AUDIT_FLAG", type: "VARCHAR(1)", remarks: "自审核标志") { constraints(nullable: "false") }
            column(name: "ONE_OFF_FLAG", type: "VARCHAR(1)", remarks: "一次性标志") { constraints(nullable: "false") }
            column(name: "TOLERANCE_FLAG", type: "VARCHAR(1)", remarks: "容限控制标志") { constraints(nullable: "false") }
            column(name: "TOLERANCE_RANGE", type: "VARCHAR(30)", remarks: "容限控制范围")
            column(name: "TOLERANCE_RATIO", type: "BIGINT", remarks: "容限控制比例")
            column(name: "PAYEE_CATEGORY", type: "VARCHAR(30)", remarks: "收款方类别")
            column(name: "REPORT_NAME", type: "VARCHAR(2000)", remarks: "报表名称")
            column(name: "RESERVE_BUDGET", type: "VARCHAR(1)", remarks: "预算占用标志") { constraints(nullable: "false") }
            column(name: "BUDGET_CONTROL_ENABLED", type: "VARCHAR(1)", remarks: "预算控制标志") {
                constraints(nullable: "false")
            }
            column(name: "ICON", type: "VARCHAR(255)", remarks: "图标（路径/CSS类）")
            column(name: "CAPTION_HDS_ID", type: "BIGINT", remarks: "填写说明ID")
            column(name: "APP_PAGE_CODE", type: "VARCHAR(30)", remarks: "APP端页面类型(SYSCODE:APP_PAGE_TYPE)")
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

        addUniqueConstraint(columnNames: "MO_EXP_REQ_TYPE_CODE,MAG_ORG_ID", tableName: "EXP_MO_REQ_TYPE", constraintName: "EXP_MO_REQ_TYPE_U1")
    }

    changeSet(author: "jiangxz", id: "2019-01-29-EXP_MO_REQ_TYPE_TL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'EXP_MO_REQ_TYPE_TL_S', startValue: "10001")
        }

        createTable(tableName: "EXP_MO_REQ_TYPE_TL", remarks: "管理组织级申请单类型定义") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "MO_EXP_REQ_TYPE_ID", type: "BIGINT", remarks: "组织架构级申请单类型ID，PK") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述ID")
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
    changeSet(author: "jiangxz", id: "2019-01-29-EXP_MO_REQ_TYPE_ASGN_COM") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'EXP_MO_REQ_TYPE_ASGN_COM_S', startValue: "10001")
        }

        createTable(tableName: "EXP_MO_REQ_TYPE_ASGN_COM", remarks: "") {

            column(name: "ASSIGN_ID", type: "BIGINT", remarks: "pk") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "EXP_MO_REQ_TYPE_ASGN_COM_PK")
            }
            column(name: "MO_EXP_REQ_TYPE_ID", type: "BIGINT", remarks: "管理组织级申请单类型ID") {
                constraints(nullable: "false")
            }
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司ID") { constraints(nullable: "false") }
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

    }
    changeSet(author: "jiangxz", id: "2019-01-29-EXP_MO_REQ_TYPE_REF_EMP_GP") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'EXP_MO_REQ_TYPE_REF_EMP_GP_S', startValue: "10001")
        }

        createTable(tableName: "EXP_MO_REQ_TYPE_REF_EMP_GP", remarks: "管理组织级申请单类型关联员工组") {

            column(name: "REF_ID", type: "BIGINT", remarks: "pk") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "EXP_MO_REQ_TYPE_REF_EMP_GP_PK")
            }
            column(name: "MO_EXP_REQ_TYPE_ID", type: "BIGINT", remarks: "管理组织级申请单类型ID") {
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

        addUniqueConstraint(columnNames: "MO_EMP_GROUP_ID,MO_EXP_REQ_TYPE_ID", tableName: "EXP_MO_REQ_TYPE_REF_EMP_GP", constraintName: "EXP_MO_REQ_TYPE_REF_EMP_GP_U1")
    }
    changeSet(author: "jiangxz", id: "2019-01-29-EXP_MO_REQ_ELE_REF_EXP_TP") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'EXP_MO_REQ_ELE_REF_EXP_TP_S', startValue: "10001")
        }

        createTable(tableName: "EXP_MO_REQ_ELE_REF_EXP_TP", remarks: "管理组织级申请单类型下页面元素关联报销类型") {

            column(name: "EXP_TYPE_REF_ID", type: "BIGINT", remarks: "pk") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "EXP_MO_REQ_ELE_REF_EXP_TP_PK")
            }
            column(name: "REQ_PAGE_ELE_REF_ID", type: "BIGINT", remarks: "管理组织级申请单类型与报销类型关联ID") {
                constraints(nullable: "false")
            }
            column(name: "MO_EXPENSE_TYPE_ID", type: "BIGINT", remarks: "管理组织级报销类型ID") {
                constraints(nullable: "false")
            }
            column(name: "DEFAULT_FLAG", type: "VARCHAR(1)", remarks: "默认标志") { constraints(nullable: "false") }
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "APPONIT_ITEM_FLAG", type: "VARCHAR(1)", remarks: "指定明细项目标志") {
                constraints(nullable: "false")
            }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "REQ_PAGE_ELE_REF_ID,MO_EXPENSE_TYPE_ID", tableName: "EXP_MO_REQ_ELE_REF_EXP_TP", constraintName: "EXP_MO_REQ_ELE_REF_EXP_TP_U1")
    }
    changeSet(author: "jiangxz", id: "2019-01-29-EXP_MO_REQ_ELE_REF_LN_DIM") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'EXP_MO_REQ_ELE_REF_LN_DIM_S', startValue: "10001")
        }

        createTable(tableName: "EXP_MO_REQ_ELE_REF_LN_DIM", remarks: "管理组织级申请单类型下页面元素关联行维度") {

            column(name: "REF_ID", type: "BIGINT", remarks: "pk") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "EXP_MO_REQ_ELE_REF_LN_DIM_PK")
            }
            column(name: "REQ_PAGE_ELE_REF_ID", type: "BIGINT", remarks: "管理组织级报销单类型下页面元素关联ID") {
                constraints(nullable: "false")
            }
            column(name: "DIMENSION_ID", type: "BIGINT", remarks: "维度ID") { constraints(nullable: "false") }
            column(name: "LAYOUT_PRIORITY", type: "BIGINT", remarks: "布局顺序") { constraints(nullable: "false") }
            column(name: "DEFAULT_DIM_VALUE_ID", type: "BIGINT", remarks: "默认维值ID")
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

        addUniqueConstraint(columnNames: "DIMENSION_ID,REQ_PAGE_ELE_REF_ID", tableName: "EXP_MO_REQ_ELE_REF_LN_DIM", constraintName: "EXP_MO_REQ_ELE_REF_LN_DIM_U1")
    }
    changeSet(author: "jiangxz", id: "2019-01-29-EXP_MO_REQ_ELE_REF_LN_OBJ") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'EXP_MO_REQ_ELE_REF_LN_OBJ_S', startValue: "10001")
        }

        createTable(tableName: "EXP_MO_REQ_ELE_REF_LN_OBJ", remarks: "管理组织级申请单类型下页面元素关联行费用对象") {

            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "REF_ID", type: "BIGINT", remarks: "pk") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "EXP_MO_REQ_ELE_REF_LN_OBJ_PK")
            }
            column(name: "REQ_PAGE_ELE_REF_ID", type: "BIGINT", remarks: "管理组织级报销单类型下页面元素关联ID") {
                constraints(nullable: "false")
            }
            column(name: "MO_EXP_OBJ_TYPE_ID", type: "BIGINT", remarks: "管理组织级费用对象类型ID") {
                constraints(nullable: "false")
            }
            column(name: "REQUIRED_FLAG", type: "VARCHAR(1)", remarks: "是否必输") { constraints(nullable: "false") }
            column(name: "LAYOUT_PRIORITY", type: "BIGINT", remarks: "布局顺序") { constraints(nullable: "false") }
            column(name: "DEFAULT_MO_OBJECT_ID", type: "BIGINT", remarks: "默认管理组织级费用对象ID")
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "MO_EXP_OBJ_TYPE_ID,REQ_PAGE_ELE_REF_ID", tableName: "EXP_MO_REQ_ELE_REF_LN_OBJ", constraintName: "EXP_MO_REQ_ELE_REF_LN_OBJ_U1")
    }
    changeSet(author: "jiangxz", id: "2019-01-29-EXP_MO_REQ_ELE_REF_REQ_IT") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'EXP_MO_REQ_ELE_REF_REQ_IT_S', startValue: "10001")
        }

        createTable(tableName: "EXP_MO_REQ_ELE_REF_REQ_IT", remarks: "管理组织级申请单类型下页面元素关联费用申请项目") {

            column(name: "REF_ID", type: "BIGINT", remarks: "pk") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "EXP_MO_REQ_ELE_REF_REQ_IT_PK")
            }
            column(name: "EXP_TYPE_REF_ID", type: "BIGINT", remarks: "管理组织级申请单类型与报销类型关联ID") {
                constraints(nullable: "false")
            }
            column(name: "MO_REQ_ITEM_ID", type: "BIGINT", remarks: "申请项目ID") { constraints(nullable: "false") }
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

        addUniqueConstraint(columnNames: "MO_REQ_ITEM_ID,EXP_TYPE_REF_ID", tableName: "EXP_MO_REQ_ELE_REF_REQ_IT", constraintName: "EXP_MO_REQ_ELE_REF_REQ_IT_U1")
    }
}