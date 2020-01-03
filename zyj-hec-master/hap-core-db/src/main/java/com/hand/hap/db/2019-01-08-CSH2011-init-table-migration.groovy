package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-01-08-CSH2011-init-table-migration.groovy') {


    changeSet(author: "dingwei.ma@hand-china.com", id: "2019-01-08-CSH_MO_PAYMENT_REQ_TYPE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_MO_PAYMENT_REQ_TYPE_S', startValue:"10001")
        }

        createTable(tableName: "CSH_MO_PAYMENT_REQ_TYPE", remarks: "管理组织级付款申请单类型，取代CSH_SOB_PAY_REQ_TYPES") {

            column(name: "MO_PAYMENT_REQ_TYPE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "管理组织级借款单类型ID,PK")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_MO_PAYMENT_REQ_TYPE_PK")} 
            column(name: "MAG_ORG_ID", type: "BIGINT", remarks: "管理组织ID")  {constraints(nullable:"false")}  
            column(name: "MO_PAYMENT_REQ_TYPE_CODE", type: "VARCHAR(30)", remarks: "管理组织级借款单类型代码")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述ID")  {constraints(nullable:"false")}  
            column(name: "CURRENCY_CODE", type: "VARCHAR(30)", remarks: "币种")   
            column(name: "AUTO_APPROVE_FLAG", type: "VARCHAR(1)", remarks: "自审批标志")  {constraints(nullable:"false")}  
            column(name: "AUTO_AUDIT_FLAG", type: "VARCHAR(1)", remarks: "自审核标志")  {constraints(nullable:"false")}  
            column(name: "REPORT_NAME", type: "VARCHAR(2000)", remarks: "报表名称")   
            column(name: "PAYMENT_METHOD_ID", type: "BIGINT", remarks: "付款方式ID")   
            column(name: "ICON", type: "VARCHAR(255)", remarks: "图标（路径/CSS类）")   
            column(name: "CAPTION_HDS_ID", type: "BIGINT", remarks: "填写说明ID")   
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
        
        addUniqueConstraint(columnNames:"MAG_ORG_ID,MO_PAYMENT_REQ_TYPE_CODE",tableName:"CSH_MO_PAYMENT_REQ_TYPE",constraintName: "CSH_MO_PAYMENT_REQ_TYPE_U1")
    }

    changeSet(author:"dingwei.ma@hand-china.com", id: "2019-01-08-CSH_MO_PAYMENT_REQ_TYPE_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_MO_PAYMENT_REQ_TYPE_TL_S', startValue: "10001")
        }

        createTable(tableName: "CSH_MO_PAYMENT_REQ_TYPE_TL", remarks: "管理组织级付款申请单类型，取代CSH_SOB_PAY_REQ_TYPES多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "MO_PAYMENT_REQ_TYPE_ID", type: "BIGINT",  remarks: "管理组织级借款单类型ID,PK")  {constraints(nullable: "false", primaryKey: "true")} 
            column(name: "DESCRIPTION", type: "VARCHAR(500)",  remarks: "描述ID")   
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

    changeSet(author: "dingwei.ma@hand-china.com", id: "2019-01-08-CSH_MO_PAY_REQ_ASGN_COM") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_MO_PAY_REQ_ASGN_COM_S', startValue:"10001")
        }

        createTable(tableName: "CSH_MO_PAY_REQ_ASGN_COM", remarks: "管理组织级借款申请单类型分配公司") {

            column(name: "ASSIGN_ID", type: "BIGINT",autoIncrement: true, startWith: "10001", remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_MO_PAY_REQ_ASGN_COM_PK")}
            column(name: "MO_PAYMENT_REQ_TYPE_ID", type: "BIGINT", remarks: "管理组织借款申请单类型ID")  {constraints(nullable:"false")}
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "管理公司ID")  {constraints(nullable:"false")}
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
        
        addUniqueConstraint(columnNames:"MO_PAYMENT_REQ_TYPE_ID,COMPANY_ID",tableName:"CSH_MO_PAY_REQ_ASGN_COM",constraintName: "CSH_MO_PAY_REQ_ASGN_COM_U1")
    }


    changeSet(author: "dingwei.ma@hand-china.com", id: "2019-01-08-CSH_MO_PAY_REQ_REF_EMP_GP") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_MO_PAY_REQ_REF_EMP_GP_S', startValue:"10001")
        }

        createTable(tableName: "CSH_MO_PAY_REQ_REF_EMP_GP", remarks: "管理组织级付款申请单类型关联员工组，取代CSH_SOB_PAY_REF_USER_GROUPS") {

            column(name: "REF_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "pk")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_MO_PAY_REQ_REF_EMP_GP_PK")} 
            column(name: "MO_PAYMENT_REQ_TYPE_ID", type: "BIGINT", remarks: "管理组织级借款申请单类型ID")  {constraints(nullable:"false")}  
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
        
        addUniqueConstraint(columnNames:"MO_EMP_GROUP_ID,MO_PAYMENT_REQ_TYPE_ID",tableName:"CSH_MO_PAY_REQ_REF_EMP_GP",constraintName: "CSH_MO_PAY_REQ_REF_EMP_GP_U1")
    }


    changeSet(author: "dingwei.ma@hand-china.com", id: "2019-01-08-CSH_MO_PAY_REQ_REF_TRX_CLS") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_MO_PAY_REQ_REF_TRX_CLS_S', startValue:"10001")
        }

        createTable(tableName: "CSH_MO_PAY_REQ_REF_TRX_CLS", remarks: "管理组织级申请单类型关联现金事物分类，取代CSH_SOB_PAY_REQ_TYPE_CLASSES") {

            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "REF_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_MO_PAY_REQ_REF_TRX_CLS_PK")} 
            column(name: "MO_PAYMENT_REQ_TYPE_ID", type: "BIGINT", remarks: "管理组织级借款申请单类型ID")  {constraints(nullable:"false")}  
            column(name: "MO_CSH_TRX_CLASS_ID", type: "BIGINT", remarks: "现金事物分类ID")  {constraints(nullable:"false")}  
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"MO_CSH_TRX_CLASS_ID,MO_PAYMENT_REQ_TYPE_ID",tableName:"CSH_MO_PAY_REQ_REF_TRX_CLS",constraintName: "CSH_MO_PAY_REQ_REF_TRX_CLS_U1")
    }


}