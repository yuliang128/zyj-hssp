package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-04-24-ACP1135-init-table-migration.groovy') {


    changeSet(author: "yuting.gui@hand-china.com", id: "2019-04-24-ACP_MO_PAY_REQ_TP_ASGN_COM") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'ACP_MO_PAY_REQ_TP_ASGN_COM_S', startValue:"10001")
        }

        createTable(tableName: "ACP_MO_PAY_REQ_TP_ASGN_COM", remarks: "管理组织级付款申请单类型分配管理公司") {

            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "ASSIGN_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "ACP_MO_PAY_REQ_TP_ASGN_COM_PK")} 
            column(name: "MO_PAY_REQ_TYPE_ID", type: "BIGINT", remarks: "管理组织级付款申请单类型ID")  {constraints(nullable:"false")}  
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "管理公司ID")  {constraints(nullable:"false")}  
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"COMPANY_ID,MO_PAY_REQ_TYPE_ID",tableName:"ACP_MO_PAY_REQ_TP_ASGN_COM",constraintName: "ACP_MO_PAY_REQ_TP_ASGN_COM_U1")
    }


    changeSet(author: "yuting.gui@hand-china.com", id: "2019-04-24-ACP_MO_PAY_REQ_TP_REF_DOC") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'ACP_MO_PAY_REQ_TP_REF_DOC_S', startValue:"10001")
        }

        createTable(tableName: "ACP_MO_PAY_REQ_TP_REF_DOC", remarks: "管理组织级付款申请单类型关联单据类型") {

            column(name: "REF_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "ACP_MO_PAY_REQ_TP_REF_DOC_PK")} 
            column(name: "MO_PAY_REQ_TYPE_ID", type: "BIGINT", remarks: "管理组织级付款申请单类型ID")  {constraints(nullable:"false")}  
            column(name: "DOCUMENT_TYPE", type: "VARCHAR(30)", remarks: "单据类型（REPORT报销单/CONTRACT合同）")  {constraints(nullable:"false")}  
            column(name: "DOCUMENT_TYPE_ID", type: "BIGINT", remarks: "单据类型ID（报销单类型ID/合同类型ID）")  {constraints(nullable:"false")}  
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"DOCUMENT_TYPE,DOCUMENT_TYPE_ID,MO_PAY_REQ_TYPE_ID",tableName:"ACP_MO_PAY_REQ_TP_REF_DOC",constraintName: "ACP_MO_PAY_REQ_TP_REF_DOC_U1")
    }


    changeSet(author: "yuting.gui@hand-china.com", id: "2019-04-24-ACP_MO_PAY_REQ_TP_REF_EMP_GP") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'ACP_MO_PAY_REQ_TP_REF_EMP_GP_S', startValue:"10001")
        }

        createTable(tableName: "ACP_MO_PAY_REQ_TP_REF_EMP_GP", remarks: "管理组织级付款申请单类型关联员工组") {

            column(name: "REF_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "ACP_MO_PAY_REQ_TP_REF_EMP_GP_PK")} 
            column(name: "MO_PAY_REQ_TYPE_ID", type: "BIGINT", remarks: "管理组织级付款申请单类型ID")  {constraints(nullable:"false")}  
            column(name: "MO_EMP_GROUP_ID", type: "BIGINT", remarks: "管理组织级员工组ID")  {constraints(nullable:"false")}  
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"MO_EMP_GROUP_ID,MO_PAY_REQ_TYPE_ID",tableName:"ACP_MO_PAY_REQ_TP_REF_EMP_GP",constraintName: "ACP_MO_PAY_REQ_TP_REF_EMPG_U1")
    }


    changeSet(author: "yuting.gui@hand-china.com", id: "2019-04-24-ACP_MO_PAY_REQ_TP_REF_TRX") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'ACP_MO_PAY_REQ_TP_REF_TRX_S', startValue:"10001")
        }

        createTable(tableName: "ACP_MO_PAY_REQ_TP_REF_TRX", remarks: "管理组织级付款申请单类型分配现金事物类型") {

            column(name: "REF_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "ACP_MO_PAY_REQ_TP_REF_TRX_PK")} 
            column(name: "MO_PAY_REQ_TYPE_ID", type: "BIGINT", remarks: "管理组织级付款申请单类型ID")  {constraints(nullable:"false")}  
            column(name: "MO_CSH_TRX_CLASS_ID", type: "BIGINT", remarks: "管理组织级现金事物分类ID")  {constraints(nullable:"false")}  
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"MO_CSH_TRX_CLASS_ID,MO_PAY_REQ_TYPE_ID",tableName:"ACP_MO_PAY_REQ_TP_REF_TRX",constraintName: "ACP_MO_PAY_REQ_TP_REF_TRX_U1")
    }


    changeSet(author: "yuting.gui@hand-china.com", id: "2019-04-24-ACP_MO_PAY_REQ_TP_REF_USED") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'ACP_MO_PAY_REQ_TP_REF_USED_S', startValue:"10001")
        }

        createTable(tableName: "ACP_MO_PAY_REQ_TP_REF_USED", remarks: "管理组织级付款申请单类型关联付款用途") {

            column(name: "REF_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "ACP_MO_PAY_REQ_TP_REF_USED_PK")} 
            column(name: "MO_PAY_REQ_TYPE_ID", type: "BIGINT", remarks: "管理组织级付款申请单类型ID")  {constraints(nullable:"false")}  
            column(name: "PAYMENT_USEDE_ID", type: "BIGINT", remarks: "管理组织级付款用途ID")  {constraints(nullable:"false")}  
            column(name: "DEFAULT_FLAG", type: "VARCHAR(1)", remarks: "默认标志")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"MO_PAY_REQ_TYPE_ID,PAYMENT_USEDE_ID",tableName:"ACP_MO_PAY_REQ_TP_REF_USED",constraintName: "ACP_MO_PAY_REQ_TP_REF_USED_U1")
    }

    changeSet(author: "yuting.gui@hand-china.com", id: "2019-04-25-CON_MO_CONTRACT_TYPE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CON_MO_CONTRACT_TYPE_S', startValue:"10001")
        }

        createTable(tableName: "CON_MO_CONTRACT_TYPE", remarks: "管理组织级合同类型定义") {

            column(name: "MO_CONTRACT_TYPE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "合同类型ID")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CON_MO_CONTRACT_TYPE_PK")}
            column(name: "MAG_ORG_ID", type: "BIGINT", remarks: "管理组织ID")  {constraints(nullable:"false")}
            column(name: "MO_CONTRACT_TYPE_CODE", type: "VARCHAR(30)", remarks: "合同类型代码")  {constraints(nullable:"false")}
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述")
            column(name: "AUTO_APPROVAL_FLAG", type: "VARCHAR(1)", remarks: "自审批标志")  {constraints(nullable:"false")}
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")  {constraints(nullable:"false")}
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "CONTRACT_ATTRIBUTE", type: "VARCHAR(30)", remarks: "合同属性")
            column(name: "CONTRACT_CATEGORY", type: "VARCHAR(30)", remarks: "合同分类")
            column(name: "CON_CONTRACT_PAGE_TYPE", type: "VARCHAR(30)", remarks: "合同页面模板")
            column(name: "IS_REQ_FLAG", type: "VARCHAR(1)", remarks: "是否申请")
            column(name: "BUDGET_RESERVATION_FLAG", type: "VARCHAR(1)", remarks: "预算占用")
            column(name: "BUDGET_CONTROL_ENABLED_FLAG", type: "VARCHAR(1)", remarks: "预算控制")
            column(name: "INVESTMENT_PLAN_CONTROL_FLAG", type: "VARCHAR(1)", remarks: "投资计划控制")
            column(name: "CURRENCY_CODE", type: "VARCHAR(30)", remarks: "币种")
            column(name: "REQUIREMENT_SOURCE_TYPE", type: "VARCHAR(30)", remarks: "需求来源")
            column(name: "RECEIVE_FLAG", type: "VARCHAR(1)", remarks: "是否接收标志")
            column(name: "ICON", type: "VARCHAR(255)", remarks: "图标（路径/css类）")
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }

        addUniqueConstraint(columnNames:"MO_CONTRACT_TYPE_CODE,MAG_ORG_ID",tableName:"CON_MO_CONTRACT_TYPE",constraintName: "CON_MO_CONTRACT_TYPES_U1")
    }

    changeSet(author:"yuting.gui@hand-china.com", id: "2019-04-25-CON_MO_CONTRACT_TYPE_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CON_MO_CONTRACT_TYPE_TL_S', startValue: "10001")
        }

        createTable(tableName: "CON_MO_CONTRACT_TYPE_TL", remarks: "管理组织级合同类型定义多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "MO_CONTRACT_TYPE_ID", type: "BIGINT",  remarks: "合同类型ID")  {constraints(nullable: "false", primaryKey: "true")}
            column(name: "DESCRIPTION", type: "VARCHAR(500)",  remarks: "描述")
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue : "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
    }


}