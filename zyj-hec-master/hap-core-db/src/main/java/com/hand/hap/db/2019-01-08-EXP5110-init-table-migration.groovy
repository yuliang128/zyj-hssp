package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-01-08-EXP5110-init-table-migration.groovy') {


    changeSet(author: "yang.duan@hand-china.com", id: "2019-01-08-EXP_MO_REPORT_TYPE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_MO_REPORT_TYPE_S', startValue:"10001")
        }

        createTable(tableName: "EXP_MO_REPORT_TYPE", remarks: "管理组织级报销单类型定义，取代EXP_SOB_REPORT_TYPES") {

            column(name: "ADJUSTMENT_FLAG", type: "VARCHAR(1)", remarks: "调整类报销单标志")  {constraints(nullable:"false")}  
            column(name: "PAYMENT_FLAG", type: "VARCHAR(1)", remarks: "是否需要支付标志")  {constraints(nullable:"false")}  
            column(name: "REPORT_NAME", type: "VARCHAR(2000)", remarks: "报表名称")   
            column(name: "AMORTIZATION_FLAG", type: "VARCHAR(1)", remarks: "摊销标志")  {constraints(nullable:"false")}  
            column(name: "TEMPLATE_FLAG", type: "VARCHAR(1)", remarks: "摊销模板标志")  {constraints(nullable:"false")}  
            column(name: "RESERVE_BUDGET", type: "VARCHAR(1)", remarks: "预算占用标志")  {constraints(nullable:"false")}  
            column(name: "BUDGET_CONTROL_ENABLED", type: "VARCHAR(1)", remarks: "预算控制标志")  {constraints(nullable:"false")}  
            column(name: "DATA_REVERSE_FLAG", type: "VARCHAR(1)", remarks: "数据反转标志")  {constraints(nullable:"false")}  
            column(name: "PAYMENT_METHOD_ID", type: "BIGINT", remarks: "付款方式")   
            column(name: "DOCUMENT_TYPE", type: "VARCHAR(100)", remarks: "单据类型")   
            column(name: "ICON", type: "VARCHAR(255)", remarks: "图标（路径/CSS类）")   
            column(name: "CAPTION_HDS_ID", type: "BIGINT", remarks: "填写说明ID")   
            column(name: "APP_PAGE_CODE", type: "VARCHAR(30)", remarks: "APP端页面类型(SYSCODE:APP_PAGE_TYPE)")   
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "MO_EXP_REPORT_TYPE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "组织架构级报销单类型ID，PK")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_MO_REPORT_TYPE_PK")} 
            column(name: "MAG_ORG_ID", type: "BIGINT", remarks: "组织架构ID")  {constraints(nullable:"false")}  
            column(name: "MO_EXP_REPORT_TYPE_CODE", type: "VARCHAR(30)", remarks: "组织架构级报销单代码")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述")  {constraints(nullable:"false")}  
            column(name: "DOCUMENT_PAGE_TYPE", type: "VARCHAR(30)", remarks: "单据页面类型")  {constraints(nullable:"false")}  
            column(name: "MO_EXP_REQ_TYPE_ID", type: "BIGINT", remarks: "组织架构级申请单类型ID")   
            column(name: "RELATION_MODE_CODE", type: "VARCHAR(30)", remarks: "关联方式（SYSCODE：EXP_MO_REPORT_TYPE_RELATION_MODE）")   
            column(name: "CURRENCY_CODE", type: "VARCHAR(30)", remarks: "币种")   
            column(name: "CODING_RULE", type: "VARCHAR(30)", remarks: "编码规则")   
            column(name: "LINE_NUMBER_BEGINNING", type: "BIGINT", remarks: "起始值")   
            column(name: "STEP_LENGTH", type: "BIGINT", remarks: "步长")   
            column(name: "AUTO_APPROVE_FLAG", type: "VARCHAR(1)", remarks: "自审批标志")  {constraints(nullable:"false")}  
            column(name: "AUTO_AUDIT_FLAG", type: "VARCHAR(1)", remarks: "自审核标志")  {constraints(nullable:"false")}  
            column(name: "PAYEE_CATEGORY", type: "VARCHAR(30)", remarks: "收款方类别")  {constraints(nullable:"false")}  
            column(name: "REQ_REQUIRED_FLAG", type: "VARCHAR(1)", remarks: "必须从申请单创建")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"MO_EXP_REPORT_TYPE_CODE,MAG_ORG_ID",tableName:"EXP_MO_REPORT_TYPE",constraintName: "EXP_MO_REPORT_TYPE_U1")
    }

    changeSet(author:"yang.duan@hand-china.com", id: "2019-01-08-EXP_MO_REPORT_TYPE_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_MO_REPORT_TYPE_TL_S', startValue: "10001")
        }

        createTable(tableName: "EXP_MO_REPORT_TYPE_TL", remarks: "管理组织级报销单类型定义，取代EXP_SOB_REPORT_TYPES多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "MO_EXP_REPORT_TYPE_ID", type: "BIGINT",  remarks: "组织架构级报销单类型ID，PK")  {constraints(nullable: "false", primaryKey: "true")} 
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

    changeSet(author: "yang.duan@hand-china.com", id: "2019-01-08-EXP_MO_REPORT_TYPE_ASGN_COM") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_MO_REPORT_TYPE_ASGN_COM_S', startValue:"10001")
        }

        createTable(tableName: "EXP_MO_REPORT_TYPE_ASGN_COM", remarks: "管理组织级报销单类型分配公司") {

            column(name: "ASSIGN_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_MO_REPORT_TYPE_ASGN_COM_PK")} 
            column(name: "MO_EXP_REPORT_TYPE_ID", type: "BIGINT", remarks: "")   
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "")   
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"COMPANY_ID,MO_EXP_REPORT_TYPE_ID",tableName:"EXP_MO_REPORT_TYPE_ASGN_COM",constraintName: "EXP_MO_REPORT_TYPE_ASGN_COM_U1")
    }


    changeSet(author: "yang.duan@hand-china.com", id: "2019-01-08-EXP_MO_REP_ELE_REF_EXP_IT") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_MO_REP_ELE_REF_EXP_IT_S', startValue:"10001")
        }

        createTable(tableName: "EXP_MO_REP_ELE_REF_EXP_IT", remarks: "管理组织级报销单类型下页面元素关联费用项目，取代EXP_SOB_TYPE_EXPENSE_ITEMS") {

            column(name: "REF_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "pk")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_MO_REP_ELE_REF_EXP_IT_PK")} 
            column(name: "EXP_TYPE_REF_ID", type: "BIGINT", remarks: "管理组织级报销单类型与报销类型关联ID")  {constraints(nullable:"false")}  
            column(name: "MO_EXPENSE_ITEM_ID", type: "BIGINT", remarks: "费用项目ID")  {constraints(nullable:"false")}  
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
        
        addUniqueConstraint(columnNames:"MO_EXPENSE_ITEM_ID,EXP_TYPE_REF_ID",tableName:"EXP_MO_REP_ELE_REF_EXP_IT",constraintName: "EXP_MO_REP_ELE_REF_EXP_IT_U1")
    }


    changeSet(author: "yang.duan@hand-china.com", id: "2019-01-08-EXP_MO_REP_ELE_REF_EXP_TP") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_MO_REP_ELE_REF_EXP_TP_S', startValue:"10001")
        }

        createTable(tableName: "EXP_MO_REP_ELE_REF_EXP_TP", remarks: "管理组织级报销单类型下页面元素关联报销类型，取代EXP_SOB_REPORT_REF_TYPES") {

            column(name: "EXP_TYPE_REF_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "报销单类型下页面元素关联报销类型ID，pk")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_MO_REP_ELE_REF_EXP_TP_PK")} 
            column(name: "REP_PAGE_ELE_REF_ID", type: "BIGINT", remarks: "管理组织级报销单类型下页面元素关联ID")  {constraints(nullable:"false")}  
            column(name: "MO_EXPENSE_TYPE_ID", type: "BIGINT", remarks: "管理组织级报销类型ID")  {constraints(nullable:"false")}  
            column(name: "DEFAULT_FLAG", type: "VARCHAR(1)", remarks: "默认标志")  {constraints(nullable:"false")}  
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "APPONIT_ITEM_FLAG", type: "VARCHAR(1)", remarks: "指定明细项目标志")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"MO_EXPENSE_TYPE_ID,REP_PAGE_ELE_REF_ID",tableName:"EXP_MO_REP_ELE_REF_EXP_TP",constraintName: "EXP_MO_REP_ELE_REF_EXP_TP_U1")
    }


    changeSet(author: "yang.duan@hand-china.com", id: "2019-01-08-EXP_MO_REP_ELE_REF_LN_DIM") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_MO_REP_ELE_REF_LN_DIM_S', startValue:"10001")
        }

        createTable(tableName: "EXP_MO_REP_ELE_REF_LN_DIM", remarks: "管理组织级报销单类型下页面元素关联行维度") {

            column(name: "REF_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "pk")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_MO_REP_ELE_REF_LN_DIM_PK")} 
            column(name: "REP_PAGE_ELE_REF_ID", type: "BIGINT", remarks: "管理组织级报销单类型下页面元素关联ID")  {constraints(nullable:"false")}  
            column(name: "DIMENSION_ID", type: "BIGINT", remarks: "维度ID")  {constraints(nullable:"false")}  
            column(name: "LAYOUT_PRIORITY", type: "BIGINT", remarks: "布局顺序")  {constraints(nullable:"false")}  
            column(name: "DEFAULT_DIM_VALUE_ID", type: "BIGINT", remarks: "默认维值ID")   
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
        
        addUniqueConstraint(columnNames:"DIMENSION_ID,REP_PAGE_ELE_REF_ID",tableName:"EXP_MO_REP_ELE_REF_LN_DIM",constraintName: "EXP_MO_REP_ELE_REF_LN_DIM_U1")
    }


    changeSet(author: "yang.duan@hand-china.com", id: "2019-01-08-EXP_MO_REP_ELE_REF_LN_OBJ") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_MO_REP_ELE_REF_LN_OBJ_S', startValue:"10001")
        }

        createTable(tableName: "EXP_MO_REP_ELE_REF_LN_OBJ", remarks: "管理组织级报销单类型下页面元素关联行费用对象") {

            column(name: "REF_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "pk")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_MO_REP_ELE_REF_LN_OBJ_PK")} 
            column(name: "REP_PAGE_ELE_REF_ID", type: "BIGINT", remarks: "管理组织级报销单类型下页面元素关联ID")  {constraints(nullable:"false")}  
            column(name: "MO_EXP_OBJ_TYPE_ID", type: "BIGINT", remarks: "管理组织级费用对象类型ID")  {constraints(nullable:"false")}  
            column(name: "REQUIRED_FLAG", type: "VARCHAR(1)", remarks: "是否必输")  {constraints(nullable:"false")}  
            column(name: "LAYOUT_PRIORITY", type: "BIGINT", remarks: "布局顺序")  {constraints(nullable:"false")}  
            column(name: "DEFAULT_MO_OBJECT_ID", type: "BIGINT", remarks: "默认管理组织级费用对象ID")   
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
        
        addUniqueConstraint(columnNames:"MO_EXP_OBJ_TYPE_ID,REP_PAGE_ELE_REF_ID",tableName:"EXP_MO_REP_ELE_REF_LN_OBJ",constraintName: "EXP_MO_REP_ELE_REF_LN_OBJ_U1")
    }


    changeSet(author: "yang.duan@hand-china.com", id: "2019-01-08-EXP_MO_REP_ELE_REF_LN_OBJ_TMP") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_MO_REP_ELE_REF_LN_OBJ_TMP_S', startValue:"10001")
        }

        createTable(tableName: "EXP_MO_REP_ELE_REF_LN_OBJ_TMP", remarks: "") {

            column(name: "REF_ID", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "REP_PAGE_ELE_REF_ID", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "MO_EXP_OBJ_TYPE_ID", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "REQUIRED_FLAG", type: "VARCHAR(1)", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAYOUT_PRIORITY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "DEFAULT_MO_OBJECT_ID", type: "BIGINT", remarks: "")
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "")  {constraints(nullable:"false")}
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }

    }


    changeSet(author: "yang.duan@hand-china.com", id: "2019-01-08-EXP_MO_REP_TYPE_REF_ELE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_MO_REP_TYPE_REF_ELE_S', startValue:"10001")
        }

        createTable(tableName: "EXP_MO_REP_TYPE_REF_ELE", remarks: "管理组织级报销单类型关联页面元素") {

            column(name: "REP_PAGE_ELE_REF_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "管理组织级报销单类型关联报销页面元素ID，pk")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_MO_REP_TYPE_REF_ELE_PK")} 
            column(name: "MO_EXP_REPORT_TYPE_ID", type: "BIGINT", remarks: "管理组织级报销单类型ID")  {constraints(nullable:"false")}  
            column(name: "REPORT_PAGE_ELEMENT_ID", type: "BIGINT", remarks: "报销单页面元素ID")  {constraints(nullable:"false")}  
            column(name: "SEQUENCE", type: "BIGINT", remarks: "页面元素排序")   
            column(name: "DOC_TYPE_CODE", type: "VARCHAR(30)", remarks: "表单类型（SYSCODE：EXP_DOC_PAGE_TYPE）")  {constraints(nullable:"false")}  
            column(name: "INVOICE_FLAG", type: "VARCHAR(1)", remarks: "发票标识")  {constraints(nullable:"false")}  
            column(name: "TAX_FLAG", type: "VARCHAR(1)", remarks: "税额标识")  {constraints(nullable:"false")}  
            column(name: "MORE_INVOICE_FLAG", type: "VARCHAR(1)", remarks: "更多发票标识")  {constraints(nullable:"false")}  
            column(name: "MORE_TAX_FLAG", type: "VARCHAR(1)", remarks: "更多税额标识")  {constraints(nullable:"false")}  
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
        
        addUniqueConstraint(columnNames:"REPORT_PAGE_ELEMENT_ID,MO_EXP_REPORT_TYPE_ID",tableName:"EXP_MO_REP_TYPE_REF_ELE",constraintName: "EXP_MO_REP_TYPE_REF_ELE_U1")
    }


    changeSet(author: "yang.duan@hand-china.com", id: "2019-01-08-EXP_MO_REP_TYPE_REF_EMP_GP") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_MO_REP_TYPE_REF_EMP_GP_S', startValue:"10001")
        }

        createTable(tableName: "EXP_MO_REP_TYPE_REF_EMP_GP", remarks: "管理组织级报销单类型关联员工组，取代EXP_SOB_REP_T_USER_GROUPS") {

            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "REF_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "pk")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_MO_REP_TYPE_REF_EMP_GP_PK")} 
            column(name: "MO_EXP_REPORT_TYPE_ID", type: "BIGINT", remarks: "管理组织级报销单类型ID")  {constraints(nullable:"false")}  
            column(name: "MO_EMP_GROUP_ID", type: "BIGINT", remarks: "管理组织级员工组ID")  {constraints(nullable:"false")}  
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"MO_EMP_GROUP_ID,MO_EXP_REPORT_TYPE_ID",tableName:"EXP_MO_REP_TYPE_REF_EMP_GP",constraintName: "EXP_MO_REP_TYPE_REF_EMP_GP_U1")
    }


    changeSet(author: "yang.duan@hand-china.com", id: "2019-01-08-EXP_MO_REP_TYPE_REF_EXP_IT") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_MO_REP_TYPE_REF_EXP_IT_S', startValue:"10001")
        }

        createTable(tableName: "EXP_MO_REP_TYPE_REF_EXP_IT", remarks: "管理组织级报销单类型下页面元素关联费用项目，取代EXP_SOB_TYPE_EXPENSE_ITEMS") {

            column(name: "REF_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "pk")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_MO_REP_TYPE_REF_EXP_IT_PK")} 
            column(name: "EXP_TYPE_REF_ID", type: "BIGINT", remarks: "管理组织级报销单类型与报销类型关联ID")  {constraints(nullable:"false")}  
            column(name: "MO_EXPENSE_ITEM_ID", type: "BIGINT", remarks: "费用项目ID")  {constraints(nullable:"false")}  
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
        
        addUniqueConstraint(columnNames:"MO_EXPENSE_ITEM_ID,EXP_TYPE_REF_ID",tableName:"EXP_MO_REP_TYPE_REF_EXP_IT",constraintName: "EXP_MO_REP_TYPE_REF_EXP_IT_U1")
    }


    changeSet(author: "yang.duan@hand-china.com", id: "2019-01-08-EXP_MO_REP_TYPE_REF_EXP_TYPE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_MO_REP_TYPE_REF_EXP_TYPE_S', startValue:"10001")
        }

        createTable(tableName: "EXP_MO_REP_TYPE_REF_EXP_TYPE", remarks: "管理组织级报销单那关联报销类型，取代EXP_SOB_REPORT_REF_TYPES") {

            column(name: "REF_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "pk")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_MO_REP_TYPE_REF_EXP_TYPE_PK")} 
            column(name: "MO_EXP_REPORT_TYPE_ID", type: "BIGINT", remarks: "管理组织级报销单类型ID")  {constraints(nullable:"false")}  
            column(name: "MO_EXPENSE_TYPE_ID", type: "BIGINT", remarks: "管理组织级报销类型ID")  {constraints(nullable:"false")}  
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
        
        addUniqueConstraint(columnNames:"MO_EXPENSE_TYPE_ID,MO_EXP_REPORT_TYPE_ID",tableName:"EXP_MO_REP_TYPE_REF_EXP_TYPE",constraintName: "EXP_MO_REP_TYPE_REF_EXP_TYP_U1")
    }


    changeSet(author: "yang.duan@hand-china.com", id: "2019-01-08-EXP_MO_REP_TYPE_REF_HD_DIM") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_MO_REP_TYPE_REF_HD_DIM_S', startValue:"10001")
        }

        createTable(tableName: "EXP_MO_REP_TYPE_REF_HD_DIM", remarks: "管理组织级报销单类型关联头维度，取代EXP_SOB_REP_REF_DIM") {

            column(name: "REF_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "pk")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_MO_REP_TYPE_REF_HD_DIM_PK")} 
            column(name: "MO_EXP_REPORT_TYPE_ID", type: "BIGINT", remarks: "管理组织级报销单类型ID")  {constraints(nullable:"false")}  
            column(name: "DIMENSION_ID", type: "BIGINT", remarks: "维度ID")  {constraints(nullable:"false")}  
            column(name: "LAYOUT_PRIORITY", type: "BIGINT", remarks: "布局顺序")  {constraints(nullable:"false")}  
            column(name: "DEFAULT_DIM_VALUE_ID", type: "BIGINT", remarks: "默认维值ID")   
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
            
        addUniqueConstraint(columnNames:"MO_EXP_REPORT_TYPE_ID,DIMENSION_ID",tableName:"EXP_MO_REP_TYPE_REF_HD_DIM",constraintName: "EXP_MO_REP_TYPE_REF_HD_DIM_U1")
        addUniqueConstraint(columnNames:"MO_EXP_REPORT_TYPE_ID,LAYOUT_PRIORITY",tableName:"EXP_MO_REP_TYPE_REF_HD_DIM",constraintName: "EXP_MO_REP_TYPE_REF_HD_DIM_U2")
    }


    changeSet(author: "yang.duan@hand-china.com", id: "2019-01-08-EXP_MO_REP_TYPE_REF_HD_OBJ") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_MO_REP_TYPE_REF_HD_OBJ_S', startValue:"10001")
        }

        createTable(tableName: "EXP_MO_REP_TYPE_REF_HD_OBJ", remarks: "管理组织级报销单类型关联头费用对象，取代EXP_SOB_REPORT_REF_OBJECT") {

            column(name: "REF_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "pk")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_MO_REP_TYPE_REF_HD_OBJ_PK")} 
            column(name: "MO_EXP_REPORT_TYPE_ID", type: "BIGINT", remarks: "管理组织级报销单类型ID")  {constraints(nullable:"false")}  
            column(name: "MO_EXP_OBJ_TYPE_ID", type: "BIGINT", remarks: "管理组织级费用对象类型ID")  {constraints(nullable:"false")}  
            column(name: "REQUIRED_FLAG", type: "VARCHAR(1)", remarks: "是否必输")  {constraints(nullable:"false")}  
            column(name: "LAYOUT_PRIORITY", type: "BIGINT", remarks: "布局顺序")  {constraints(nullable:"false")}  
            column(name: "DEFAULT_MO_OBJECT_ID", type: "BIGINT", remarks: "默认管理组织级费用对象ID")   
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
            
        addUniqueConstraint(columnNames:"MO_EXP_REPORT_TYPE_ID,MO_EXP_OBJ_TYPE_ID",tableName:"EXP_MO_REP_TYPE_REF_HD_OBJ",constraintName: "EXP_MO_REP_TYPE_REF_HD_OBJ_U1")
        addUniqueConstraint(columnNames:"MO_EXP_REPORT_TYPE_ID,LAYOUT_PRIORITY",tableName:"EXP_MO_REP_TYPE_REF_HD_OBJ",constraintName: "EXP_MO_REP_TYPE_REF_HD_OBJ_U2")
    }


    changeSet(author: "yang.duan@hand-china.com", id: "2019-01-08-EXP_MO_REP_TYPE_REF_PAY_UD") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_MO_REP_TYPE_REF_PAY_UD_S', startValue:"10001")
        }

        createTable(tableName: "EXP_MO_REP_TYPE_REF_PAY_UD", remarks: "管理组织级报销单类型关联付款用途，取代EXP_SOB_REP_REF_PAYMENT_USEDES") {

            column(name: "REF_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "pk")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_MO_REP_TYPE_REF_PAY_UD_PK")} 
            column(name: "MO_EXP_REPORT_TYPE_ID", type: "BIGINT", remarks: "管理组织级报销单类型ID")  {constraints(nullable:"false")}  
            column(name: "USEDES_ID", type: "BIGINT", remarks: "付款用途ID")  {constraints(nullable:"false")}  
            column(name: "DEFAULT_FLAG", type: "VARCHAR(1)", remarks: "默认标志")  {constraints(nullable:"false")}  
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
        
        addUniqueConstraint(columnNames:"USEDES_ID,MO_EXP_REPORT_TYPE_ID",tableName:"EXP_MO_REP_TYPE_REF_PAY_UD",constraintName: "EXP_MO_REP_TYPE_REF_PAY_UD_U1")
    }

    changeSet(author: "yang.duan@hand-china.com", id: "2019-01-08-EXP_REPORT_ACCOUNT") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_REPORT_ACCOUNT_S', startValue:"10001")
        }

        createTable(tableName: "EXP_REPORT_ACCOUNT", remarks: "报销单凭证表") {

            column(name: "EXP_REPORT_JE_LINE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "凭证行ID")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_REPORT_ACCOUNT_PK")}
            column(name: "EXP_REPORT_HEADER_ID", type: "BIGINT", remarks: "报销单头ID")  {constraints(nullable:"false")}
            column(name: "EXP_REPORT_DIST_ID", type: "BIGINT", remarks: "报销单分配行ID")
            column(name: "PAYMENT_SCHEDULE_LINE_ID", type: "BIGINT", remarks: "计划付款行ID")
            column(name: "DESCRIPTION", type: "VARCHAR(2000)", remarks: "描述")
            column(name: "JOURNAL_DATE", type: "DATETIME", remarks: "凭证日期")  {constraints(nullable:"false")}
            column(name: "PERIOD_NAME", type: "VARCHAR(30)", remarks: "期间")  {constraints(nullable:"false")}
            column(name: "SOURCE_CODE", type: "VARCHAR(30)", remarks: "来源代码")  {constraints(nullable:"false")}
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体ID")  {constraints(nullable:"false")}
            column(name: "RESP_CENTER_ID", type: "BIGINT", remarks: "责任中心ID")  {constraints(nullable:"false")}
            column(name: "ACCOUNT_ID", type: "BIGINT", remarks: "科目ID")  {constraints(nullable:"false")}
            column(name: "CURRENCY_CODE", type: "VARCHAR(30)", remarks: "币种")  {constraints(nullable:"false")}
            column(name: "EXCHANGE_RATE_TYPE", type: "VARCHAR(30)", remarks: "汇率类型")
            column(name: "EXCHANGE_RATE", type: "NUMBER", remarks: "汇率")  {constraints(nullable:"false")}
            column(name: "ENTERED_AMOUNT_DR", type: "NUMBER", remarks: "原币借方金额")
            column(name: "ENTERED_AMOUNT_CR", type: "NUMBER", remarks: "原币贷方金额")
            column(name: "FUNCTIONAL_AMOUNT_DR", type: "NUMBER", remarks: "本币借方金额")
            column(name: "FUNCTIONAL_AMOUNT_CR", type: "NUMBER", remarks: "本币贷方金额")
            column(name: "GLD_INTERFACE_FLAG", type: "VARCHAR(10)", remarks: "总账标志")  {constraints(nullable:"false")}
            column(name: "TRANSFER_FLAG", type: "VARCHAR(1)", remarks: "进项税转出标志（Y：是/N：否）")  {constraints(nullable:"false")}
            column(name: "USAGE_CODE", type: "VARCHAR(30)", remarks: "用途代码")  {constraints(nullable:"false")}
            column(name: "ACCOUNT_SEGMENT1", type: "VARCHAR(200)", remarks: "科目段1")
            column(name: "ACCOUNT_SEGMENT2", type: "VARCHAR(200)", remarks: "科目段2")
            column(name: "ACCOUNT_SEGMENT3", type: "VARCHAR(200)", remarks: "科目段3")
            column(name: "ACCOUNT_SEGMENT4", type: "VARCHAR(200)", remarks: "科目段4")
            column(name: "ACCOUNT_SEGMENT5", type: "VARCHAR(200)", remarks: "科目段5")
            column(name: "ACCOUNT_SEGMENT6", type: "VARCHAR(200)", remarks: "科目段6")
            column(name: "ACCOUNT_SEGMENT7", type: "VARCHAR(200)", remarks: "科目段7")
            column(name: "ACCOUNT_SEGMENT8", type: "VARCHAR(200)", remarks: "科目段8")
            column(name: "ACCOUNT_SEGMENT9", type: "VARCHAR(200)", remarks: "科目段9")
            column(name: "ACCOUNT_SEGMENT10", type: "VARCHAR(200)", remarks: "科目段10")
            column(name: "ACCOUNT_SEGMENT11", type: "VARCHAR(200)", remarks: "科目段11")
            column(name: "ACCOUNT_SEGMENT12", type: "VARCHAR(200)", remarks: "科目段12")
            column(name: "ACCOUNT_SEGMENT13", type: "VARCHAR(200)", remarks: "科目段13")
            column(name: "ACCOUNT_SEGMENT14", type: "VARCHAR(200)", remarks: "科目段14")
            column(name: "ACCOUNT_SEGMENT15", type: "VARCHAR(200)", remarks: "科目段15")
            column(name: "ACCOUNT_SEGMENT16", type: "VARCHAR(200)", remarks: "科目段16")
            column(name: "ACCOUNT_SEGMENT17", type: "VARCHAR(200)", remarks: "科目段17")
            column(name: "ACCOUNT_SEGMENT18", type: "VARCHAR(200)", remarks: "科目段18")
            column(name: "ACCOUNT_SEGMENT19", type: "VARCHAR(200)", remarks: "科目段19")
            column(name: "ACCOUNT_SEGMENT20", type: "VARCHAR(200)", remarks: "科目段20")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        createIndex(tableName: "EXP_REPORT_ACCOUNT", indexName: "EXP_REPORT_ACCOUNT_N1") {
            column(name: "EXP_REPORT_HEADER_ID")
        }
        createIndex(tableName: "EXP_REPORT_ACCOUNT", indexName: "EXP_REPORT_ACCOUNT_N2") {
            column(name: "EXP_REPORT_DIST_ID")
        }
        createIndex(tableName: "EXP_REPORT_ACCOUNT", indexName: "EXP_REPORT_ACCOUNT_N3") {
            column(name: "PAYMENT_SCHEDULE_LINE_ID")
        }

    }


    changeSet(author: "yang.duan@hand-china.com", id: "2019-01-08-EXP_REPORT_ACCOUNT_TMP") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_REPORT_ACCOUNT_TMP_S', startValue:"10001")
        }

        createTable(tableName: "EXP_REPORT_ACCOUNT_TMP", remarks: "报销单核算凭证临时表") {

            column(name: "BATCH_ID", type: "BIGINT", remarks: "批次ID")  {constraints(nullable:"false")}
            column(name: "EXPENSE_REPORT_HEADER_ID", type: "BIGINT", remarks: "报销单头id")  {constraints(nullable:"false")}
            column(name: "SUCCESS_FLAG", type: "VARCHAR(1)", remarks: "成功标志")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}
            column(name: "AMORTIZATION_FLAG", type: "VARCHAR(1)", remarks: "摊销标志")
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }

        addPrimaryKey(columnNames:"EXPENSE_REPORT_HEADER_ID,BATCH_ID",tableName:"EXP_REPORT_ACCOUNT_TMP",constraintName: "EXP_REPORT_ACCOUNT_TMP_PK")
    }


    changeSet(author: "yang.duan@hand-china.com", id: "2019-01-08-EXP_REPORT_COMPANY_TMP") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_REPORT_COMPANY_TMP_S', startValue:"10001")
        }

        createTable(tableName: "EXP_REPORT_COMPANY_TMP", remarks: "") {

            column(name: "SESSION_ID", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }

        addPrimaryKey(columnNames:"COMPANY_ID,SESSION_ID",tableName:"EXP_REPORT_COMPANY_TMP",constraintName: "EXP_REPORT_COMPANY_TMP_PK")
    }


    changeSet(author: "yang.duan@hand-china.com", id: "2019-01-08-EXP_REPORT_DIMENSION_VALUE_TMP") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_REPORT_DIMENSION_VALUE_TMP_S', startValue:"10001")
        }

        createTable(tableName: "EXP_REPORT_DIMENSION_VALUE_TMP", remarks: "报销单维值范围临时表") {

            column(name: "SESSION_ID", type: "BIGINT", remarks: "系统SESSION ID")  {constraints(nullable:"false")}
            column(name: "DIM_VALUE_FROM", type: "VARCHAR(30)", remarks: "维值从")
            column(name: "DIM_VALUE_TO", type: "VARCHAR(30)", remarks: "维值到")
            column(name: "DIMENSION_SEQUENCE", type: "BIGINT", remarks: "维值序号")  {constraints(nullable:"false")}
            column(name: "DIMENSION_ID", type: "BIGINT", remarks: "维度id")  {constraints(nullable:"false")}
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID")  {constraints(nullable:"false")}
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }

        addPrimaryKey(columnNames:"SESSION_ID,DIMENSION_SEQUENCE",tableName:"EXP_REPORT_DIMENSION_VALUE_TMP",constraintName: "EXP_REPORT_DIM_VALUE_TMP_PK")
        addUniqueConstraint(columnNames:"SESSION_ID,DIMENSION_ID",tableName:"EXP_REPORT_DIMENSION_VALUE_TMP",constraintName: "EXP_REPORT_DIM_VALUE_TMP_U1")
    }


    changeSet(author: "yang.duan@hand-china.com", id: "2019-01-08-EXP_REPORT_DIST") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_REPORT_DIST_S', startValue:"10001")
        }

        createTable(tableName: "EXP_REPORT_DIST", remarks: "报销单分配行") {

            column(name: "POSITION_ID", type: "BIGINT", remarks: "岗位ID")  {constraints(nullable:"false")}
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "员工ID")  {constraints(nullable:"false")}
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体ID")  {constraints(nullable:"false")}
            column(name: "RESP_CENTER_ID", type: "BIGINT", remarks: "责任中心ID")  {constraints(nullable:"false")}
            column(name: "BGT_ENTITY_ID", type: "BIGINT", remarks: "预算实体ID")  {constraints(nullable:"false")}
            column(name: "BGT_CENTER_ID", type: "BIGINT", remarks: "预算中心ID")  {constraints(nullable:"false")}
            column(name: "PAYEE_CATEGORY", type: "VARCHAR(30)", remarks: "收款方类别")
            column(name: "PAYEE_ID", type: "BIGINT", remarks: "收款方ID")
            column(name: "PAYMENT_FLAG", type: "VARCHAR(1)", remarks: "废弃_付款标志")
            column(name: "REPORT_STATUS", type: "VARCHAR(30)", remarks: "废弃_单据状态")  {constraints(nullable:"false")}
            column(name: "CLOSE_FLAG", type: "VARCHAR(1)", remarks: "关闭标志")  {constraints(nullable:"false")}
            column(name: "CLOSE_DATE", type: "DATETIME", remarks: "关闭日期")
            column(name: "CLOSE_DATE_TZ", type: "TIMESTAMP", remarks: "关闭日期_业务时区")
            column(name: "CLOSE_DATE_LTZ", type: "TIMESTAMP", remarks: "关闭日期_查询时区")
            column(name: "AUDIT_FLAG", type: "VARCHAR(30)", remarks: "废弃_审核标志")
            column(name: "AUDIT_DATE", type: "DATETIME", remarks: "废弃_审核日期")
            column(name: "AUDIT_DATE_TZ", type: "TIMESTAMP", remarks: "废弃_审核日期_业务时区")
            column(name: "AUDIT_DATE_LTZ", type: "TIMESTAMP", remarks: "废弃_审核日期_查询时区")
            column(name: "WRITE_OFF_STATUS", type: "VARCHAR(30)", remarks: "废弃_核销状态")
            column(name: "WRITE_OFF_COMLETED_DATE", type: "DATETIME", remarks: "废弃_核销日期")
            column(name: "WRITE_OFF_COMLETED_DATE_TZ", type: "TIMESTAMP", remarks: "废弃_核销日期_业务时区")
            column(name: "WRITE_OFF_COMLETED_DATE_LTZ", type: "TIMESTAMP", remarks: "废弃_核销日期_查询时区")
            column(name: "ATTACHMENT_NUM", type: "BIGINT", remarks: "附件数")
            column(name: "DIMENSION1_ID", type: "BIGINT", remarks: "维度1")
            column(name: "DIMENSION2_ID", type: "BIGINT", remarks: "维度2")
            column(name: "DIMENSION3_ID", type: "BIGINT", remarks: "维度3")
            column(name: "DIMENSION4_ID", type: "BIGINT", remarks: "维度4")
            column(name: "DIMENSION5_ID", type: "BIGINT", remarks: "维度5")
            column(name: "DIMENSION6_ID", type: "BIGINT", remarks: "维度6")
            column(name: "DIMENSION7_ID", type: "BIGINT", remarks: "维度7")
            column(name: "DIMENSION8_ID", type: "BIGINT", remarks: "维度8")
            column(name: "DIMENSION9_ID", type: "BIGINT", remarks: "维度9")
            column(name: "DIMENSION10_ID", type: "BIGINT", remarks: "维度10")
            column(name: "DIMENSION11_ID", type: "BIGINT", remarks: "维度11")
            column(name: "DIMENSION12_ID", type: "BIGINT", remarks: "维度12")
            column(name: "DIMENSION13_ID", type: "BIGINT", remarks: "维度13")
            column(name: "DIMENSION14_ID", type: "BIGINT", remarks: "维度14")
            column(name: "DIMENSION15_ID", type: "BIGINT", remarks: "维度15")
            column(name: "DIMENSION16_ID", type: "BIGINT", remarks: "维度16")
            column(name: "DIMENSION17_ID", type: "BIGINT", remarks: "维度17")
            column(name: "DIMENSION18_ID", type: "BIGINT", remarks: "维度18")
            column(name: "DIMENSION19_ID", type: "BIGINT", remarks: "维度19")
            column(name: "DIMENSION20_ID", type: "BIGINT", remarks: "维度20")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "EXP_REPORT_DIST_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "报销单分配行ID,PK")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_REPORT_DIST_PK")}
            column(name: "EXP_REPORT_LINE_ID", type: "BIGINT", remarks: "报销单行ID")  {constraints(nullable:"false")}
            column(name: "DESCRIPTION", type: "VARCHAR(4000)", remarks: "描述")
            column(name: "DATE_FROM", type: "DATETIME", remarks: "费用发生日期从")
            column(name: "DATE_TO", type: "DATETIME", remarks: "费用发生日期到")
            column(name: "PERIOD_NAME", type: "VARCHAR(30)", remarks: "期间")  {constraints(nullable:"false")}
            column(name: "BUSINESS_CURRENCY_CODE", type: "VARCHAR(30)", remarks: "业务币种")  {constraints(nullable:"false")}
            column(name: "BIZ2PAY_EXCHANGE_TYPE", type: "VARCHAR(30)", remarks: "业务币种->支付币种汇率类型")
            column(name: "BIZ2PAY_EXCHANGE_RATE", type: "NUMBER", remarks: "业务币种->支付币种汇率")  {constraints(nullable:"false")}
            column(name: "PAYMENT_CURRENCY_CODE", type: "VARCHAR(30)", remarks: "支付币种")  {constraints(nullable:"false")}
            column(name: "PAY2FUN_EXCHANGE_TYPE", type: "VARCHAR(30)", remarks: "支付币种->本位币汇率类型")
            column(name: "PAY2FUN_EXCHANGE_RATE", type: "NUMBER", remarks: "支付币种->本位率汇率")  {constraints(nullable:"false")}
            column(name: "MANAGEMENT_CURRENCY_CODE", type: "VARCHAR(30)", remarks: "管理币种")  {constraints(nullable:"false")}
            column(name: "PAY2MAG_EXCHANGE_TYPE", type: "VARCHAR(30)", remarks: "业务币种->管理币种汇率类型")
            column(name: "PAY2MAG_EXCHANGE_RATE", type: "NUMBER", remarks: "业务币种->管理币种汇率")  {constraints(nullable:"false")}
            column(name: "MO_EXPENSE_TYPE_ID", type: "BIGINT", remarks: "管理组织级报销类型ID")  {constraints(nullable:"false")}
            column(name: "MO_EXPENSE_ITEM_ID", type: "BIGINT", remarks: "管理组织级费用项目ID")  {constraints(nullable:"false")}
            column(name: "BUDGET_ITEM_ID", type: "BIGINT", remarks: "预算项目ID")
            column(name: "BUSINESS_PRICE", type: "NUMBER", remarks: "业务币种单价")  {constraints(nullable:"false")}
            column(name: "PAYMENT_PRICE", type: "NUMBER", remarks: "支付币种单价")  {constraints(nullable:"false")}
            column(name: "MANAGEMENT_PRICE", type: "NUMBER", remarks: "管理币种单价")  {constraints(nullable:"false")}
            column(name: "PRIMARY_QUANTITY", type: "NUMBER", remarks: "数量")  {constraints(nullable:"false")}
            column(name: "PRIMARY_UOM", type: "VARCHAR(30)", remarks: "数量单位")
            column(name: "SECONDARY_QUANTITY", type: "NUMBER", remarks: "废弃_次要数量")
            column(name: "SECONDARY_UOM", type: "VARCHAR(30)", remarks: "废弃_次要数量单位")
            column(name: "BUSINESS_AMOUNT", type: "NUMBER", remarks: "业务币种金额")  {constraints(nullable:"false")}
            column(name: "PAYMENT_AMOUNT", type: "NUMBER", remarks: "支付币种金额")  {constraints(nullable:"false")}
            column(name: "MANAGEMENT_AMOUNT", type: "NUMBER", remarks: "管理币种金额")  {constraints(nullable:"false")}
            column(name: "REPORT_FUNCTIONAL_AMOUNT", type: "NUMBER", remarks: "本位币金额")  {constraints(nullable:"false")}
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "管理公司ID")  {constraints(nullable:"false")}
            column(name: "OPERATION_UNIT_ID", type: "BIGINT", remarks: "废弃_经营单位ID")
            column(name: "UNIT_ID", type: "BIGINT", remarks: "部门ID")  {constraints(nullable:"false")}
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        createIndex(tableName: "EXP_REPORT_DIST", indexName: "EXP_REPORT_DIST_N1") {
            column(name: "EXP_REPORT_LINE_ID")
        }
        createIndex(tableName: "EXP_REPORT_DIST", indexName: "EXP_REPORT_DIST_N2") {
            column(name: "COMPANY_ID")
            column(name: "UNIT_ID")
            column(name: "POSITION_ID")
        }
        createIndex(tableName: "EXP_REPORT_DIST", indexName: "EXP_REPORT_DIST_N3") {
            column(name: "ACC_ENTITY_ID")
            column(name: "RESP_CENTER_ID")
        }
        createIndex(tableName: "EXP_REPORT_DIST", indexName: "EXP_REPORT_DIST_N4") {
            column(name: "BGT_CENTER_ID")
            column(name: "BGT_ENTITY_ID")
        }
        createIndex(tableName: "EXP_REPORT_DIST", indexName: "EXP_REPORT_DIST_N5") {
            column(name: "EMPLOYEE_ID")
        }

    }


    changeSet(author: "yang.duan@hand-china.com", id: "2019-01-08-EXP_REPORT_DISTS_SESSION_TMP") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_REPORT_DISTS_SESSION_TMP_S', startValue:"10001")
        }

        createTable(tableName: "EXP_REPORT_DISTS_SESSION_TMP", remarks: "费用报销单分配行临时表") {

            column(name: "SESSION_ID", type: "BIGINT", remarks: "系统SESSION ID")  {constraints(nullable:"false")}
            column(name: "EXP_REPORT_DISTS_ID", type: "BIGINT", remarks: "分配行id")  {constraints(nullable:"false")}
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID")  {constraints(nullable:"false")}
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }

        addPrimaryKey(columnNames:"EXP_REPORT_DISTS_ID,SESSION_ID",tableName:"EXP_REPORT_DISTS_SESSION_TMP",constraintName: "EXP_REP_DISTS_SESSION_TMP_PK")
    }


    changeSet(author: "yang.duan@hand-china.com", id: "2019-01-08-EXP_REPORT_EMPLOYEE_TMP") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_REPORT_EMPLOYEE_TMP_S', startValue:"10001")
        }

        createTable(tableName: "EXP_REPORT_EMPLOYEE_TMP", remarks: "") {

            column(name: "SESSION_ID", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }

        addPrimaryKey(columnNames:"EMPLOYEE_ID,SESSION_ID",tableName:"EXP_REPORT_EMPLOYEE_TMP",constraintName: "EXP_REPORT_EMPLOYEE_TMP_PK")
    }


    changeSet(author: "yang.duan@hand-china.com", id: "2019-01-08-EXP_REPORT_HEADER") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_REPORT_HEADER_S', startValue:"10001")
        }

        createTable(tableName: "EXP_REPORT_HEADER", remarks: "报销单头") {

            column(name: "ATTACHMENT_NUM", type: "BIGINT", remarks: "附件数量")
            column(name: "DESCRIPTION", type: "VARCHAR(4000)", remarks: "报销单头描述")
            column(name: "WRITE_OFF_STATUS", type: "VARCHAR(30)", remarks: "核销状态")  {constraints(nullable:"false")}
            column(name: "WRITE_OFF_COMPLETED_DATE", type: "DATETIME", remarks: "核销日期")
            column(name: "WRITE_OFF_COMPLETED_DATE_TZ", type: "TIMESTAMP", remarks: "核销日期_业务时区")
            column(name: "WRITE_OFF_COMPLETED_DATE_LTZ", type: "TIMESTAMP", remarks: "核销日期_查询时区")
            column(name: "AMORTIZATION_FLAG", type: "VARCHAR(1)", remarks: "摊销标志")  {constraints(nullable:"false")}
            column(name: "REVERSED_FLAG", type: "VARCHAR(1)", remarks: "反冲标志")  {constraints(nullable:"false")}
            column(name: "SOURCE_EXP_REP_HEADER_ID", type: "BIGINT", remarks: "来源报销单ID")
            column(name: "SOURCE_TYPE", type: "VARCHAR(30)", remarks: "来源类型(MANUAL)")
            column(name: "PAYMENT_METHOD_ID", type: "BIGINT", remarks: "付款方式")
            column(name: "RELEASE_TYPE", type: "VARCHAR(30)", remarks: "申请单关联方式，DOCUMENT/LINE，整单/按行")
            column(name: "EXP_REQUISITION_HEADER_ID", type: "BIGINT", remarks: "整单关联时的申请单头ID")
            column(name: "DIMENSION1_ID", type: "BIGINT", remarks: "维度1")
            column(name: "DIMENSION2_ID", type: "BIGINT", remarks: "维度2")
            column(name: "DIMENSION3_ID", type: "BIGINT", remarks: "维度3")
            column(name: "DIMENSION4_ID", type: "BIGINT", remarks: "维度4")
            column(name: "DIMENSION5_ID", type: "BIGINT", remarks: "维度5")
            column(name: "DIMENSION6_ID", type: "BIGINT", remarks: "维度6")
            column(name: "DIMENSION7_ID", type: "BIGINT", remarks: "维度7")
            column(name: "DIMENSION8_ID", type: "BIGINT", remarks: "维度8")
            column(name: "DIMENSION9_ID", type: "BIGINT", remarks: "维度9")
            column(name: "DIMENSION10_ID", type: "BIGINT", remarks: "维度10")
            column(name: "DIMENSION11_ID", type: "BIGINT", remarks: "维度11")
            column(name: "DIMENSION12_ID", type: "BIGINT", remarks: "维度12")
            column(name: "DIMENSION13_ID", type: "BIGINT", remarks: "维度13")
            column(name: "DIMENSION14_ID", type: "BIGINT", remarks: "维度14")
            column(name: "DIMENSION15_ID", type: "BIGINT", remarks: "维度15")
            column(name: "DIMENSION16_ID", type: "BIGINT", remarks: "维度16")
            column(name: "DIMENSION17_ID", type: "BIGINT", remarks: "维度17")
            column(name: "DIMENSION18_ID", type: "BIGINT", remarks: "维度18")
            column(name: "DIMENSION19_ID", type: "BIGINT", remarks: "维度19")
            column(name: "DIMENSION20_ID", type: "BIGINT", remarks: "维度20")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "VAT_INVOICE_FLAG", type: "VARCHAR(1)", remarks: "是否有增值税专票")
            column(name: "AUTHENTICATING_FLAG", type: "VARCHAR(30)", remarks: "增值税专票是否完全认证")
            column(name: "DOC_STATUS", type: "VARCHAR(30)", remarks: "退回状态（SYSCODE：CSH_DOC_BACK）")
            column(name: "EXP_REPORT_HEADER_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "报销单头ID，pk")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_REPORT_HEADER_PK")}
            column(name: "EXP_REPORT_NUMBER", type: "VARCHAR(30)", remarks: "报销单编号")  {constraints(nullable:"false")}
            column(name: "EXP_REPORT_BARCODE", type: "VARCHAR(30)", remarks: "废弃：报销单编码")
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司ID")  {constraints(nullable:"false")}
            column(name: "OPERATION_UNIT_ID", type: "BIGINT", remarks: "废弃：经营单位")
            column(name: "UNIT_ID", type: "BIGINT", remarks: "部门ID")  {constraints(nullable:"false")}
            column(name: "POSITION_ID", type: "BIGINT", remarks: "岗位ID")  {constraints(nullable:"false")}
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "员工ID")  {constraints(nullable:"false")}
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体ID")  {constraints(nullable:"false")}
            column(name: "RESP_CENTER_ID", type: "BIGINT", remarks: "责任中心ID")  {constraints(nullable:"false")}
            column(name: "BGT_ENTITY_ID", type: "BIGINT", remarks: "预算实体ID")  {constraints(nullable:"false")}
            column(name: "BGT_CENTER_ID", type: "BIGINT", remarks: "预算中心ID")  {constraints(nullable:"false")}
            column(name: "PAYEE_CATEGORY", type: "VARCHAR(30)", remarks: "收款方类型")  {constraints(nullable:"false")}
            column(name: "PAYEE_ID", type: "BIGINT", remarks: "收款方ID")  {constraints(nullable:"false")}
            column(name: "MO_EXP_REPORT_TYPE_ID", type: "BIGINT", remarks: "报销单类型ID")  {constraints(nullable:"false")}
            column(name: "MO_EMP_GROUP_ID", type: "BIGINT", remarks: "管理组织级员工组ID")
            column(name: "PAYMENT_CURRENCY_CODE", type: "VARCHAR(10)", remarks: "支付币种")  {constraints(nullable:"false")}
            column(name: "PAYMENT_AMOUNT", type: "NUMBER", remarks: "支付币种金额")
            column(name: "PAY2FUN_EXCHANGE_TYPE", type: "VARCHAR(30)", remarks: "支付币种->本位币汇率类型")
            column(name: "PAY2FUN_EXCHANGE_RATE", type: "NUMBER", remarks: "支付币种->本位币汇率")  {constraints(nullable:"false")}
            column(name: "REPORT_DATE", type: "DATETIME", remarks: "报销日期")  {constraints(nullable:"false")}
            column(name: "REPORT_DATE_TZ", type: "TIMESTAMP", defaultValueComputed:"CURRENT_TIMESTAMP", remarks: "报销日期_业务时区")  {constraints(nullable:"false")}
            column(name: "REPORT_DATE_LTZ", type: "TIMESTAMP", defaultValueComputed:"CURRENT_TIMESTAMP", remarks: "报销日期_查询时区")  {constraints(nullable:"false")}
            column(name: "PERIOD_NAME", type: "VARCHAR(30)", remarks: "期间")  {constraints(nullable:"false")}
            column(name: "REPORT_STATUS", type: "VARCHAR(30)", remarks: "报销单状态")  {constraints(nullable:"false")}
            column(name: "JE_CREATION_STATUS", type: "VARCHAR(30)", remarks: "凭证创建状态")
            column(name: "AUDIT_FLAG", type: "VARCHAR(1)", remarks: "审核标志")  {constraints(nullable:"false")}
            column(name: "AUDIT_DATE", type: "DATETIME", remarks: "审核日期")
            column(name: "AUDIT_DATE_TZ", type: "TIMESTAMP", remarks: "审核日期_业务时区")
            column(name: "AUDIT_DATE_LTZ", type: "TIMESTAMP", remarks: "审核日期_查询时区")
            column(name: "GLD_INTERFACE_FLAG", type: "VARCHAR(1)", remarks: "总账接口标志")
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        createIndex(tableName: "EXP_REPORT_HEADER", indexName: "EXP_REPORT_HEADER_N1") {
            column(name: "COMPANY_ID")
            column(name: "UNIT_ID")
            column(name: "POSITION_ID")
        }
        createIndex(tableName: "EXP_REPORT_HEADER", indexName: "EXP_REPORT_HEADER_N2") {
            column(name: "RESP_CENTER_ID")
            column(name: "ACC_ENTITY_ID")
        }
        createIndex(tableName: "EXP_REPORT_HEADER", indexName: "EXP_REPORT_HEADER_N3") {
            column(name: "BGT_ENTITY_ID")
            column(name: "BGT_CENTER_ID")
        }
        createIndex(tableName: "EXP_REPORT_HEADER", indexName: "EXP_REPORT_HEADER_N4") {
            column(name: "EMPLOYEE_ID")
        }
        createIndex(tableName: "EXP_REPORT_HEADER", indexName: "EXP_REPORT_HEADER_N5") {
            column(name: "MO_EXP_REPORT_TYPE_ID")
        }
        createIndex(tableName: "EXP_REPORT_HEADER", indexName: "EXP_REPORT_HEADER_N6") {
            column(name: "SOURCE_EXP_REP_HEADER_ID")
            column(name: "REVERSED_FLAG")
        }
        createIndex(tableName: "EXP_REPORT_HEADER", indexName: "EXP_REPORT_HEADER_N7") {
            column(name: "EXP_REQUISITION_HEADER_ID")
        }

        addUniqueConstraint(columnNames:"EXP_REPORT_NUMBER",tableName:"EXP_REPORT_HEADER",constraintName: "EXP_REPORT_HEADER_U1")
    }


    changeSet(author: "yang.duan@hand-china.com", id: "2019-01-08-EXP_REPORT_INTERFACE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_REPORT_INTERFACE_S', startValue:"10001")
        }

        createTable(tableName: "EXP_REPORT_INTERFACE", remarks: "") {

            column(name: "BATCH_ID", type: "BIGINT", remarks: "批次号")  {constraints(nullable:"false")}
            column(name: "BATCH_LINE_ID", type: "BIGINT", remarks: "批次行id")  {constraints(nullable:"false")}
            column(name: "IMPORT_FLAG", type: "VARCHAR(2)", remarks: "导入状态")  {constraints(nullable:"false")}
            column(name: "EXP_REPORT_BATCH_NUMBER", type: "VARCHAR(200)", remarks: "报销单唯一标识")  {constraints(nullable:"false")}
            column(name: "EXP_REPORT_TYPE_CODE", type: "VARCHAR(30)", remarks: "报销单类型代码")  {constraints(nullable:"false")}
            column(name: "HEAD_COMPANY_CODE", type: "VARCHAR(30)", remarks: "报销单头公司代码")  {constraints(nullable:"false")}
            column(name: "EXP_REPORT_BARCODE", type: "VARCHAR(30)", remarks: "条码")
            column(name: "HEAD_EMPLOYEE_CODE", type: "VARCHAR(30)", remarks: "申请人")
            column(name: "HEAD_POSITION_CODE", type: "VARCHAR(30)", remarks: "报销单头岗位")
            column(name: "PAYEE_CATEGORY", type: "VARCHAR(30)", remarks: "收款方类型")
            column(name: "PAYEE_CODE", type: "VARCHAR(30)", remarks: "收款方代码")
            column(name: "PAYMENT_METHOD", type: "VARCHAR(30)", remarks: "付款方式代码")
            column(name: "EXPENSE_USER_GROUP_CODE", type: "VARCHAR(30)", remarks: "费用用户组代码")
            column(name: "CURRENCY_CODE", type: "VARCHAR(30)", remarks: "币种")
            column(name: "EXCHANGE_RATE_TYPE", type: "VARCHAR(30)", remarks: "汇率类型")
            column(name: "EXCHANGE_RATE_QUOTATION", type: "VARCHAR(30)", remarks: "汇率标价方式")
            column(name: "EXCHANGE_RATE", type: "NUMBER", remarks: "汇率")  {constraints(nullable:"false")}
            column(name: "REPORT_DATE", type: "VARCHAR(30)", remarks: "报销日期")
            column(name: "HEAD_PERIOD_NAME", type: "VARCHAR(30)", remarks: "头期间")
            column(name: "ATTACHMENT_NUM", type: "BIGINT", remarks: "附件数")
            column(name: "HEAD_DESCRIPTION", type: "VARCHAR(2000)", remarks: "头描述")
            column(name: "LINE_NUMBER", type: "BIGINT", remarks: "行号")  {constraints(nullable:"false")}
            column(name: "CITY", type: "VARCHAR(200)", remarks: "城市")
            column(name: "PLACE_CODE", type: "VARCHAR(30)", remarks: "地点代码")
            column(name: "PLACE_TYPE_CODE", type: "VARCHAR(30)", remarks: "地点类型代码")
            column(name: "DESCRIPTION", type: "VARCHAR(200)", remarks: "行描述")
            column(name: "DATE_FROM", type: "VARCHAR(30)", remarks: "日期从")
            column(name: "DATE_TO", type: "VARCHAR(30)", remarks: "日期到")
            column(name: "PERIOD_NAME", type: "VARCHAR(30)", remarks: "期间")  {constraints(nullable:"false")}
            column(name: "EXPENSE_TYPE_CODE", type: "VARCHAR(30)", remarks: "费用类型代码")  {constraints(nullable:"false")}
            column(name: "EXPENSE_ITEM_CODE", type: "VARCHAR(30)", remarks: "费用项目代码")  {constraints(nullable:"false")}
            column(name: "BUDGET_ITEM_CODE", type: "VARCHAR(30)", remarks: "预算项目代码")
            column(name: "PRICE", type: "NUMBER", remarks: "单价")  {constraints(nullable:"false")}
            column(name: "PRIMARY_QUANTITY", type: "NUMBER", remarks: "数量")  {constraints(nullable:"false")}
            column(name: "COMPANY_CODE", type: "VARCHAR(30)", remarks: "公司")  {constraints(nullable:"false")}
            column(name: "UNIT_CODE", type: "VARCHAR(30)", remarks: "部门代码")
            column(name: "POSITION_CODE", type: "VARCHAR(30)", remarks: "岗位代码")
            column(name: "RESP_CENTER_CODE", type: "VARCHAR(30)", remarks: "责任中心代码")  {constraints(nullable:"false")}
            column(name: "EMPLOYEE_CODE", type: "VARCHAR(30)", remarks: "员工代码")
            column(name: "DIMENSION1_CODE", type: "VARCHAR(30)", remarks: "维度1代码")
            column(name: "DIMENSION2_CODE", type: "VARCHAR(30)", remarks: "维度2代码")
            column(name: "DIMENSION3_CODE", type: "VARCHAR(30)", remarks: "维度3代码")
            column(name: "DIMENSION4_CODE", type: "VARCHAR(30)", remarks: "维度4代码")
            column(name: "DIMENSION5_CODE", type: "VARCHAR(30)", remarks: "维度5代码")
            column(name: "DIMENSION6_CODE", type: "VARCHAR(30)", remarks: "维度6代码")
            column(name: "DIMENSION7_CODE", type: "VARCHAR(30)", remarks: "维度7代码")
            column(name: "DIMENSION8_CODE", type: "VARCHAR(30)", remarks: "维度8代码")
            column(name: "DIMENSION9_CODE", type: "VARCHAR(30)", remarks: "维度9代码")
            column(name: "DIMENSION10_CODE", type: "VARCHAR(30)", remarks: "维度10代码")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建人")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")
            column(name: "LAST_UPDATED_BY", type: "NUMBER", remarks: "最后修改人")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后修改日期")
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        createIndex(tableName: "EXP_REPORT_INTERFACE", indexName: "EXP_REPORT_INTERFACE_N1") {
            column(name: "BATCH_ID")
        }
        createIndex(tableName: "EXP_REPORT_INTERFACE", indexName: "EXP_REPORT_INTERFACE_N2") {
            column(name: "IMPORT_FLAG")
        }
        createIndex(tableName: "EXP_REPORT_INTERFACE", indexName: "EXP_REPORT_INTERFACE_N3") {
            column(name: "EXP_REPORT_BATCH_NUMBER")
        }

    }


    changeSet(author: "yang.duan@hand-china.com", id: "2019-01-08-EXP_REPORT_INTERFACE_LOG") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_REPORT_INTERFACE_LOG_S', startValue:"10001")
        }

        createTable(tableName: "EXP_REPORT_INTERFACE_LOG", remarks: "") {

            column(name: "BATCH_ID", type: "BIGINT", remarks: "批次号")
            column(name: "BATCH_LINE_ID", type: "BIGINT", remarks: "序号")
            column(name: "EXP_REPORT_BATCH_NUMBER", type: "VARCHAR(200)", remarks: "报销单唯一标识")
            column(name: "LINE_NUMBER", type: "BIGINT", remarks: "行号")
            column(name: "MESSAGE", type: "VARCHAR(4000)", remarks: "错误信息")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建人")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后修改人")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后修改日期")
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        createIndex(tableName: "EXP_REPORT_INTERFACE_LOG", indexName: "EXP_REPORT_INTERFACE_LOG_N1") {
            column(name: "BATCH_ID")
        }

    }


    changeSet(author: "yang.duan@hand-china.com", id: "2019-01-08-EXP_REPORT_LINE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_REPORT_LINE_S', startValue:"10001")
        }

        createTable(tableName: "EXP_REPORT_LINE", remarks: "报销单行") {

            column(name: "INVOICE_TYPE", type: "VARCHAR(30)", remarks: "发票类型")
            column(name: "EXP_REPORT_LINE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "报销单行ID,PK")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_REPORT_LINE_PK")}
            column(name: "EXP_REPORT_HEADER_ID", type: "BIGINT", remarks: "报销单头ID")  {constraints(nullable:"false")}
            column(name: "LINE_NUMBER", type: "BIGINT", remarks: "行号")  {constraints(nullable:"false")}
            column(name: "REPORT_PAGE_ELEMENT_CODE", type: "VARCHAR(30)", remarks: "报销单页面元素代码")  {constraints(nullable:"false")}
            column(name: "PLACE_TYPE_ID", type: "BIGINT", remarks: "地点类型")
            column(name: "PLACE_ID", type: "BIGINT", remarks: "地点类型")
            column(name: "CITY", type: "VARCHAR(200)", remarks: "城市，手动输入")
            column(name: "DESCRIPTION", type: "VARCHAR(4000)", remarks: "描述")
            column(name: "DATE_FROM", type: "DATETIME", remarks: "费用发生日期从")
            column(name: "DATE_TO", type: "DATETIME", remarks: "费用发生日期到")
            column(name: "PERIOD_NAME", type: "VARCHAR(30)", remarks: "期间")  {constraints(nullable:"false")}
            column(name: "BUSINESS_CURRENCY_CODE", type: "VARCHAR(30)", remarks: "业务币种")  {constraints(nullable:"false")}
            column(name: "BIZ2PAY_EXCHANGE_TYPE", type: "VARCHAR(30)", remarks: "业务币种->支付币种汇率类型")
            column(name: "BIZ2PAY_EXCHANGE_RATE", type: "NUMBER", remarks: "业务币种->支付币种汇率")  {constraints(nullable:"false")}
            column(name: "PAYMENT_CURRENCY_CODE", type: "VARCHAR(30)", remarks: "支付币种")  {constraints(nullable:"false")}
            column(name: "PAY2FUN_EXCHANGE_TYPE", type: "VARCHAR(30)", remarks: "支付币种->本位币汇率类型")
            column(name: "PAY2FUN_EXCHANGE_RATE", type: "NUMBER", remarks: "支付币种->本位率汇率")  {constraints(nullable:"false")}
            column(name: "MANAGEMENT_CURRENCY_CODE", type: "VARCHAR(30)", remarks: "管理币种")  {constraints(nullable:"false")}
            column(name: "PAY2MAG_EXCHANGE_TYPE", type: "VARCHAR(30)", remarks: "支付币种->管理币种汇率类型")
            column(name: "PAY2MAG_EXCHANGE_RATE", type: "NUMBER", remarks: "支付币种->管理币种汇率")  {constraints(nullable:"false")}
            column(name: "MO_EXPENSE_TYPE_ID", type: "BIGINT", remarks: "管理组织级报销类型ID")  {constraints(nullable:"false")}
            column(name: "MO_EXPENSE_ITEM_ID", type: "BIGINT", remarks: "管理组织级费用项目ID")  {constraints(nullable:"false")}
            column(name: "BUDGET_ITEM_ID", type: "BIGINT", remarks: "预算项目ID")
            column(name: "BUSINESS_PRICE", type: "NUMBER", remarks: "业务币种单价")  {constraints(nullable:"false")}
            column(name: "PAYMENT_PRICE", type: "NUMBER", remarks: "支付币种单价")  {constraints(nullable:"false")}
            column(name: "MANAGEMENT_PRICE", type: "NUMBER", remarks: "管理币种单价")  {constraints(nullable:"false")}
            column(name: "PRIMARY_QUANTITY", type: "NUMBER", remarks: "数量")  {constraints(nullable:"false")}
            column(name: "PRIMARY_UOM", type: "VARCHAR(30)", remarks: "数量单位")
            column(name: "SECONDARY_QUANTITY", type: "NUMBER", remarks: "废弃_次要数量")
            column(name: "SECONDARY_UOM", type: "VARCHAR(30)", remarks: "废弃_次要数量单位")
            column(name: "BUSINESS_AMOUNT", type: "NUMBER", remarks: "业务币种金额")  {constraints(nullable:"false")}
            column(name: "PAYMENT_AMOUNT", type: "NUMBER", remarks: "支付币种金额")  {constraints(nullable:"false")}
            column(name: "MANAGEMENT_AMOUNT", type: "NUMBER", remarks: "管理币种金额")  {constraints(nullable:"false")}
            column(name: "REPORT_FUNCTIONAL_AMOUNT", type: "NUMBER", remarks: "本位币金额")  {constraints(nullable:"false")}
            column(name: "DISTRIBUTION_SET_ID", type: "BIGINT", remarks: "废弃_分配集ID")
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "管理公司ID")  {constraints(nullable:"false")}
            column(name: "OPERATION_UNIT_ID", type: "BIGINT", remarks: "废弃_经营单位ID")
            column(name: "UNIT_ID", type: "BIGINT", remarks: "部门ID")  {constraints(nullable:"false")}
            column(name: "POSITION_ID", type: "BIGINT", remarks: "岗位ID")  {constraints(nullable:"false")}
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "员工ID")  {constraints(nullable:"false")}
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体ID")  {constraints(nullable:"false")}
            column(name: "RESP_CENTER_ID", type: "BIGINT", remarks: "责任中心ID")  {constraints(nullable:"false")}
            column(name: "BGT_ENTITY_ID", type: "BIGINT", remarks: "预算实体ID")  {constraints(nullable:"false")}
            column(name: "BGT_CENTER_ID", type: "BIGINT", remarks: "预算中心ID")  {constraints(nullable:"false")}
            column(name: "PAYEE_CATEGORY", type: "VARCHAR(30)", remarks: "收款方类别")
            column(name: "PAYEE_ID", type: "BIGINT", remarks: "收款方ID")
            column(name: "PAYMENT_FLAG", type: "VARCHAR(1)", remarks: "废弃_付款标志")
            column(name: "REPORT_STATUS", type: "VARCHAR(30)", remarks: "废弃_单据状态")  {constraints(nullable:"false")}
            column(name: "AUDIT_FLAG", type: "VARCHAR(30)", remarks: "废弃_审核标志")  {constraints(nullable:"false")}
            column(name: "AUDIT_DATE", type: "DATETIME", remarks: "废弃_审核日期")
            column(name: "AUDIT_DATE_TZ", type: "TIMESTAMP", remarks: "废弃_审核日期_业务时区")
            column(name: "AUDIT_DATE_LTZ", type: "TIMESTAMP", remarks: "废弃_审核日期_查询时区")
            column(name: "WRITE_OFF_STATUS", type: "VARCHAR(30)", remarks: "废弃_核销状态")  {constraints(nullable:"false")}
            column(name: "WRITE_OFF_COMLETED_DATE", type: "DATETIME", remarks: "废弃_核销日期")
            column(name: "WRITE_OFF_COMLETED_DATE_TZ", type: "TIMESTAMP", remarks: "废弃_核销日期_业务时区")
            column(name: "WRITE_OFF_COMLETED_DATE_LTZ", type: "TIMESTAMP", remarks: "废弃_核销日期_查询时区")
            column(name: "ATTACHMENT_NUM", type: "BIGINT", remarks: "附件数")
            column(name: "DIMENSION1_ID", type: "BIGINT", remarks: "维度1")
            column(name: "DIMENSION2_ID", type: "BIGINT", remarks: "维度2")
            column(name: "DIMENSION3_ID", type: "BIGINT", remarks: "维度3")
            column(name: "DIMENSION4_ID", type: "BIGINT", remarks: "维度4")
            column(name: "DIMENSION5_ID", type: "BIGINT", remarks: "维度5")
            column(name: "DIMENSION6_ID", type: "BIGINT", remarks: "维度6")
            column(name: "DIMENSION7_ID", type: "BIGINT", remarks: "维度7")
            column(name: "DIMENSION8_ID", type: "BIGINT", remarks: "维度8")
            column(name: "DIMENSION9_ID", type: "BIGINT", remarks: "维度9")
            column(name: "DIMENSION10_ID", type: "BIGINT", remarks: "维度10")
            column(name: "DIMENSION11_ID", type: "BIGINT", remarks: "维度11")
            column(name: "DIMENSION12_ID", type: "BIGINT", remarks: "维度12")
            column(name: "DIMENSION13_ID", type: "BIGINT", remarks: "维度13")
            column(name: "DIMENSION14_ID", type: "BIGINT", remarks: "维度14")
            column(name: "DIMENSION15_ID", type: "BIGINT", remarks: "维度15")
            column(name: "DIMENSION16_ID", type: "BIGINT", remarks: "维度16")
            column(name: "DIMENSION17_ID", type: "BIGINT", remarks: "维度17")
            column(name: "DIMENSION18_ID", type: "BIGINT", remarks: "维度18")
            column(name: "DIMENSION19_ID", type: "BIGINT", remarks: "维度19")
            column(name: "DIMENSION20_ID", type: "BIGINT", remarks: "维度20")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        createIndex(tableName: "EXP_REPORT_LINE", indexName: "EXP_REPORT_LINE_N1") {
            column(name: "POSITION_ID")
            column(name: "COMPANY_ID")
            column(name: "UNIT_ID")
        }
        createIndex(tableName: "EXP_REPORT_LINE", indexName: "EXP_REPORT_LINE_N2") {
            column(name: "RESP_CENTER_ID")
            column(name: "ACC_ENTITY_ID")
        }
        createIndex(tableName: "EXP_REPORT_LINE", indexName: "EXP_REPORT_LINE_N3") {
            column(name: "BGT_ENTITY_ID")
            column(name: "BGT_CENTER_ID")
        }
        createIndex(tableName: "EXP_REPORT_LINE", indexName: "EXP_REPORT_LINE_N4") {
            column(name: "EMPLOYEE_ID")
        }

        addUniqueConstraint(columnNames:"REPORT_PAGE_ELEMENT_CODE,EXP_REPORT_HEADER_ID,LINE_NUMBER",tableName:"EXP_REPORT_LINE",constraintName: "EXP_REPORT_LINE_U1")
    }


    changeSet(author: "yang.duan@hand-china.com", id: "2019-01-08-EXP_REPORT_OBJECT") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_REPORT_OBJECT_S', startValue:"10001")
        }

        createTable(tableName: "EXP_REPORT_OBJECT", remarks: "费用报销单费用对象信息") {

            column(name: "EXP_REPORT_HEADER_ID", type: "BIGINT", remarks: "费用报销单头ID")  {constraints(nullable:"false")}
            column(name: "EXP_REPORT_LINE_ID", type: "BIGINT", remarks: "费用报销单行ID")
            column(name: "MO_EXP_OBJ_TYPE_ID", type: "BIGINT", remarks: "费用对象类型ID")  {constraints(nullable:"false")}
            column(name: "MO_EXPENSE_OBJECT_ID", type: "BIGINT", remarks: "费用对象ID")
            column(name: "MO_EXPENSE_OBJECT_NAME", type: "VARCHAR(2000)", remarks: "费用对象描述")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID")  {constraints(nullable:"false")}
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")  {constraints(nullable:"false")}
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        createIndex(tableName: "EXP_REPORT_OBJECT", indexName: "EXP_REPORT_OBJECT_N1") {
            column(name: "MO_EXP_OBJ_TYPE_ID")
            column(name: "MO_EXPENSE_OBJECT_ID")
        }
        createIndex(tableName: "EXP_REPORT_OBJECT", indexName: "EXP_REPORT_OBJECT_N2") {
            column(name: "EXP_REPORT_HEADER_ID")
        }

    }


    changeSet(author: "yang.duan@hand-china.com", id: "2019-01-08-EXP_REPORT_OBJECTS_LAYOUT") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_REPORT_OBJECTS_LAYOUT_S', startValue:"10001")
        }

        createTable(tableName: "EXP_REPORT_OBJECTS_LAYOUT", remarks: "费用报销单费用对象布局") {

            column(name: "EXP_REPORT_HEADER_ID", type: "BIGINT", remarks: "费用报销单头ID")  {constraints(nullable:"false")}
            column(name: "LAYOUT_POSITION", type: "VARCHAR(30)", remarks: "布局位置")  {constraints(nullable:"false")}
            column(name: "LAYOUT_PRIORITY", type: "BIGINT", remarks: "布局顺序")  {constraints(nullable:"false")}
            column(name: "EXPENSE_OBJECT_TYPE_ID", type: "BIGINT", remarks: "费用对象类型ID")  {constraints(nullable:"false")}
            column(name: "DEFAULT_OBJECT_ID", type: "BIGINT", remarks: "缺省费用对象ID")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID")  {constraints(nullable:"false")}
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")  {constraints(nullable:"false")}
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }

        addPrimaryKey(columnNames:"EXPENSE_OBJECT_TYPE_ID,EXP_REPORT_HEADER_ID",tableName:"EXP_REPORT_OBJECTS_LAYOUT",constraintName: "EXP_REPORT_OBJECTS_LAYOUT_PK")
        addUniqueConstraint(columnNames:"LAYOUT_POSITION,LAYOUT_PRIORITY,EXP_REPORT_HEADER_ID",tableName:"EXP_REPORT_OBJECTS_LAYOUT",constraintName: "EXP_REPORT_OBJECTS_LAYOUT_U1")
    }


    changeSet(author: "yang.duan@hand-china.com", id: "2019-01-08-EXP_REPORT_PAGE_ELEMENT") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_REPORT_PAGE_ELEMENT_S', startValue:"10001")
        }

        createTable(tableName: "EXP_REPORT_PAGE_ELEMENT", remarks: "费用报销单页面元素") {

            column(name: "SERVICE3_ID", type: "BIGINT", remarks: "扩展页面3ID")
            column(name: "SERVICE4_ID", type: "BIGINT", remarks: "扩展页面4ID")
            column(name: "SERVICE5_ID", type: "BIGINT", remarks: "扩展页面5ID")
            column(name: "REPORT_TYPE_FLAG", type: "VARCHAR(1)", remarks: "报销类型标识")  {constraints(nullable:"false")}
            column(name: "EXPENSE_OBJECT_FLAG", type: "VARCHAR(1)", remarks: "费用对象标识")  {constraints(nullable:"false")}
            column(name: "DIMENSION_FLAG", type: "VARCHAR(1)", remarks: "维度标识")  {constraints(nullable:"false")}
            column(name: "INVOICE_FLAG", type: "VARCHAR(1)", remarks: "发票标识")  {constraints(nullable:"false")}
            column(name: "TAX_FLAG", type: "VARCHAR(1)", remarks: "税额标识")  {constraints(nullable:"false")}
            column(name: "MORE_INVOICE_FLAG", type: "VARCHAR(1)", remarks: "更多发票标识")  {constraints(nullable:"false")}
            column(name: "MORE_TAX_FLAG", type: "VARCHAR(1)", remarks: "更多税额标识")  {constraints(nullable:"false")}
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")  {constraints(nullable:"false")}
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "SYSTEM_FLAG", type: "VARCHAR(1)", remarks: "预置标志")  {constraints(nullable:"false")}
            column(name: "REPORT_PAGE_ELEMENT_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "报销单页面元素ID，pk")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_REPORT_PAGE_ELEMENT_PK")}
            column(name: "REPORT_PAGE_ELEMENT_CODE", type: "VARCHAR(30)", remarks: "报销单页面元素代码")  {constraints(nullable:"false")}
            column(name: "REQ_PAGE_ELEMENT_ID", type: "BIGINT", remarks: "关联申请单页面元素ID")
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述")  {constraints(nullable:"false")}
            column(name: "SERVICE_ID", type: "BIGINT", remarks: "创建页面ID")  {constraints(nullable:"false")}
            column(name: "READONLY_SERVICE_ID", type: "BIGINT", remarks: "只读页面ID")
            column(name: "SERVICE1_ID", type: "BIGINT", remarks: "扩展页面1ID")
            column(name: "SERVICE2_ID", type: "BIGINT", remarks: "扩展页面2ID")
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }

        addUniqueConstraint(columnNames:"REPORT_PAGE_ELEMENT_CODE",tableName:"EXP_REPORT_PAGE_ELEMENT",constraintName: "EXP_REPORT_PAGE_ELEMENT_U1")
        addUniqueConstraint(columnNames:"SERVICE_ID",tableName:"EXP_REPORT_PAGE_ELEMENT",constraintName: "EXP_REPORT_PAGE_ELEMENT_U2")
    }

    changeSet(author:"yang.duan@hand-china.com", id: "2019-01-08-EXP_REPORT_PAGE_ELEMENT_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_REPORT_PAGE_ELEMENT_TL_S', startValue: "10001")
        }

        createTable(tableName: "EXP_REPORT_PAGE_ELEMENT_TL", remarks: "费用报销单页面元素多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "REPORT_PAGE_ELEMENT_ID", type: "BIGINT",  remarks: "报销单页面元素ID，pk")  {constraints(nullable: "false", primaryKey: "true")}
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

    changeSet(author: "yang.duan@hand-china.com", id: "2019-01-08-EXP_REPORT_PAYMENT_TMP") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_REPORT_PAYMENT_TMP_S', startValue:"10001")
        }

        createTable(tableName: "EXP_REPORT_PAYMENT_TMP", remarks: "报销单付款金额表") {

            column(name: "SESSION_ID", type: "BIGINT", remarks: "系统SESSION ID")  {constraints(nullable:"false")}
            column(name: "PAYMENT_SCHEDULE_LINE_ID", type: "BIGINT", remarks: "计划付款行ID")  {constraints(nullable:"false")}
            column(name: "PAYMENT_AMOUNT", type: "NUMBER", remarks: "付款金额")  {constraints(nullable:"false")}
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID")  {constraints(nullable:"false")}
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        createIndex(tableName: "EXP_REPORT_PAYMENT_TMP", indexName: "EXP_REPORT_PAYMENT_TMP_N1") {
            column(name: "PAYMENT_SCHEDULE_LINE_ID")
            column(name: "SESSION_ID")
        }

    }


    changeSet(author: "yang.duan@hand-china.com", id: "2019-01-08-EXP_REPORT_PMT_SCHEDULE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_REPORT_PMT_SCHEDULE_S', startValue:"10001")
        }

        createTable(tableName: "EXP_REPORT_PMT_SCHEDULE", remarks: "报销单计划付款行") {

            column(name: "CITY_CODE", type: "VARCHAR(200)", remarks: "城市代码")
            column(name: "CITY_NAME", type: "VARCHAR(200)", remarks: "城市名称")
            column(name: "CONTRACT_HEADER_ID", type: "BIGINT", remarks: "合同申请头ID")
            column(name: "CON_PMT_SCHEDULE_LINE_ID", type: "BIGINT", remarks: "资金计划行ID")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "PAYMENT_SCHEDULE_LINE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "计划付款行ID")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_REPORT_PMT_SCHEDULE_PK")}
            column(name: "EXP_REPORT_HEADER_ID", type: "BIGINT", remarks: "报销单头ID")  {constraints(nullable:"false")}
            column(name: "SCHEDULE_LINE_NUMBER", type: "BIGINT", remarks: "计划付款行号")  {constraints(nullable:"false")}
            column(name: "DESCRIPTION", type: "VARCHAR(2000)", remarks: "描述")
            column(name: "PAYMENT_CURRENCY_CODE", type: "VARCHAR(30)", remarks: "付款币种")  {constraints(nullable:"false")}
            column(name: "PAYEE_CATEGORY", type: "VARCHAR(30)", remarks: "收款方类别")
            column(name: "PAYEE_ID", type: "BIGINT", remarks: "收款方ID")
            column(name: "SCHEDULE_START_DATE", type: "DATETIME", remarks: "计划起始付款日期")
            column(name: "SCHEDULE_DUE_DATE", type: "DATETIME", remarks: "计划付款日期")
            column(name: "DUE_AMOUNT", type: "NUMBER", remarks: "计划付款金额")  {constraints(nullable:"false")}
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司ID")  {constraints(nullable:"false")}
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算实体ID")  {constraints(nullable:"false")}
            column(name: "PAYMENT_METHOD_ID", type: "BIGINT", remarks: "付款方式")
            column(name: "PAYMENT_USEDE_ID", type: "BIGINT", remarks: "付款用途")
            column(name: "CASH_FLOW_ITEM_ID", type: "BIGINT", remarks: "现金流量项")
            column(name: "FROZEN_FLAG", type: "VARCHAR(1)", remarks: "冻结标志")
            column(name: "ACCOUNT_NAME", type: "VARCHAR(200)", remarks: "户名")
            column(name: "ACCOUNT_NUMBER", type: "VARCHAR(200)", remarks: "账号")
            column(name: "BANK_CODE", type: "VARCHAR(200)", remarks: "银行代码")
            column(name: "BANK_NAME", type: "VARCHAR(200)", remarks: "银行名称")
            column(name: "BANK_LOCATION_CODE", type: "VARCHAR(200)", remarks: "分行代码")
            column(name: "BANK_LOCATION_NAME", type: "VARCHAR(200)", remarks: "分行名称")
            column(name: "PROVINCE_CODE", type: "VARCHAR(200)", remarks: "省份代码")
            column(name: "PROVINCE_NAME", type: "VARCHAR(200)", remarks: "省份名称")
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }

        addUniqueConstraint(columnNames:"SCHEDULE_LINE_NUMBER,EXP_REPORT_HEADER_ID",tableName:"EXP_REPORT_PMT_SCHEDULE",constraintName: "EXP_REPORT_PMT_SCHEDULE_U1")
    }


    changeSet(author: "yang.duan@hand-china.com", id: "2019-01-08-EXP_REPORT_PMT_SCH_TAX_LINE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_REPORT_PMT_SCH_TAX_LINE_S', startValue:"10001")
        }

        createTable(tableName: "EXP_REPORT_PMT_SCH_TAX_LINE", remarks: "报销单计划付款行关联税金") {

            column(name: "TAX_LINE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其它表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_REPORT_PMT_SCH_TAX_LINE_PK")}
            column(name: "PAYMENT_SCHEDULE_LINE_ID", type: "BIGINT", remarks: "报销单计划付款行ID")  {constraints(nullable:"false")}
            column(name: "TAX_TYPE_ID", type: "BIGINT", remarks: "税率类型ID")
            column(name: "TAX_RATE", type: "NUMBER", remarks: "税率")
            column(name: "TAX_AMOUNT", type: "NUMBER", remarks: "税额")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        createIndex(tableName: "EXP_REPORT_PMT_SCH_TAX_LINE", indexName: "EXP_REPORT_PMT_SCH_TAX_LNS_N1") {
            column(name: "PAYMENT_SCHEDULE_LINE_ID")
        }
        createIndex(tableName: "EXP_REPORT_PMT_SCH_TAX_LINE", indexName: "EXP_REPORT_PMT_SCH_TAX_LNS_N2") {
            column(name: "TAX_RATE")
            column(name: "TAX_TYPE_ID")
        }

    }


    changeSet(author: "yang.duan@hand-china.com", id: "2019-01-08-EXP_REPORT_RECEIVE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_REPORT_RECEIVE_S', startValue:"10001")
        }

        createTable(tableName: "EXP_REPORT_RECEIVE", remarks: "费用报销单接收表") {

            column(name: "RECEIVE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_REPORT_RECEIVE_PK")}
            column(name: "SEQUENCE_NUM", type: "BIGINT", remarks: "序号")  {constraints(nullable:"false")}
            column(name: "RECEIVE_NUMBER", type: "VARCHAR(30)", remarks: "接收单号")  {constraints(nullable:"false")}
            column(name: "RECEIVE_DATE", type: "DATETIME", remarks: "接收日期")  {constraints(nullable:"false")}
            column(name: "DOCUMENT_TYPE", type: "VARCHAR(30)", remarks: "单据类型")  {constraints(nullable:"false")}
            column(name: "DOCUMENT_ID", type: "BIGINT", remarks: "单据头ID")  {constraints(nullable:"false")}
            column(name: "DOCUMENT_NUMBER", type: "VARCHAR(30)", remarks: "单据编号")  {constraints(nullable:"false")}
            column(name: "STATUS", type: "VARCHAR(30)", remarks: "状态（SYSCODE：EXP_REPORT_RECEIVE_STATUS）")  {constraints(nullable:"false")}
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        createIndex(tableName: "EXP_REPORT_RECEIVE", indexName: "EXP_REPORT_RECEIVE_N1") {
            column(name: "DOCUMENT_ID")
            column(name: "DOCUMENT_NUMBER")
            column(name: "DOCUMENT_TYPE")
        }
        createIndex(tableName: "EXP_REPORT_RECEIVE", indexName: "EXP_REPORT_RECEIVE_N2") {
            column(name: "DOCUMENT_NUMBER")
        }

    }


    changeSet(author: "yang.duan@hand-china.com", id: "2019-01-08-EXP_REPORT_TRAVEL_LINE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_REPORT_TRAVEL_LINE_S', startValue:"10001")
        }

        createTable(tableName: "EXP_REPORT_TRAVEL_LINE", remarks: "报销单-差旅关联行") {

            column(name: "DEPARTURE_PLACE", type: "VARCHAR(200)", remarks: "行程_出发地")
            column(name: "DEPARTURE_PLACE_ID", type: "BIGINT", remarks: "行程_出发地ID")
            column(name: "DEPARTURE_DATE", type: "DATETIME", remarks: "行程_出发日期")
            column(name: "ARRIVAL_PLACE", type: "VARCHAR(200)", remarks: "行程_到达地")
            column(name: "ARRIVAL_PLACE_ID", type: "BIGINT", remarks: "行程_到达地ID")
            column(name: "ARRIVAL_DATE", type: "DATETIME", remarks: "行程_到达日期")
            column(name: "ACCOMMODATION_DATE_FROM", type: "DATETIME", remarks: "住宿_入住日期")
            column(name: "ACCOMMODATION_DATE_TO", type: "DATETIME", remarks: "住宿_离店日期")
            column(name: "ACCOMMODATION_DAYS", type: "NUMBER", remarks: "住宿_住宿天数")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建人")  {constraints(nullable:"false")}
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "更新人")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "更新日期")  {constraints(nullable:"false")}
            column(name: "EXP_REPORT_LINE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "报销单行ID，也是报销单-差旅关联行的PK")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_REPORT_TRAVEL_LINE_PK")}
            column(name: "TRAVEL_PLAN_LINE_ID", type: "BIGINT", remarks: "差旅计划行ID，可为空")
            column(name: "TRAVEL_LINE_CATEGORY", type: "VARCHAR(30)", remarks: "差旅计划行类别")  {constraints(nullable:"false")}
            column(name: "TRANSPORTATION", type: "VARCHAR(30)", remarks: "行程_交通工具")
            column(name: "SEAT_CLASS", type: "VARCHAR(30)", remarks: "行程_舱位/座位等级")
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        createIndex(tableName: "EXP_REPORT_TRAVEL_LINE", indexName: "EXP_REPORT_TRAVEL_LINE_N1") {
            column(name: "TRAVEL_PLAN_LINE_ID")
        }

    }


    changeSet(author: "yang.duan@hand-china.com", id: "2019-01-08-EXP_REPORT_UNIT_TMP") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_REPORT_UNIT_TMP_S', startValue:"10001")
        }

        createTable(tableName: "EXP_REPORT_UNIT_TMP", remarks: "") {

            column(name: "SESSION_ID", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "UNIT_ID", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }

        addPrimaryKey(columnNames:"UNIT_ID,SESSION_ID",tableName:"EXP_REPORT_UNIT_TMP",constraintName: "EXP_REPORT_UNIT_TMP_PK")
    }

}