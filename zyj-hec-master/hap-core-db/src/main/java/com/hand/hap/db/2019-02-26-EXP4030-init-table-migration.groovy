package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-02-26-EXP4030-init-table-migration.groovy') {

    changeSet(author: "xiuxian.wu@hand-china.com", id: "2019-02-26-EXP_MO_EXPENSE_TYPE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_MO_EXPENSE_TYPE_S', startValue:"10001")
        }

        createTable(tableName: "EXP_MO_EXPENSE_TYPE", remarks: "管理组织级报销类型定义，取代EXP_SOB_EXPENSE_TYPES") {

            column(name: "MO_EXPENSE_TYPE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001", remarks: "pk，管理组织级报销类型ID")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_MO_EXPENSE_TYPE_PK")}
            column(name: "MAG_ORG_ID", type: "BIGINT", remarks: "管理组织ID")  {constraints(nullable:"false")}
            column(name: "MO_EXPENSE_TYPE_CODE", type: "VARCHAR(30)", remarks: "管理组织级报销类型代码")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述ID")  {constraints(nullable:"false")}  
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
        addUniqueConstraint(columnNames:"MO_EXPENSE_TYPE_CODE",tableName:"EXP_MO_EXPENSE_TYPE",constraintName: "EXP_MO_EXPENSE_TYPE_U1")
    }

    changeSet(author:"xiuxian.wu@hand-china.com", id: "2019-02-26-EXP_MO_EXPENSE_TYPE_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_MO_EXPENSE_TYPE_TL_S', startValue: "10001")
        }

        createTable(tableName: "EXP_MO_EXPENSE_TYPE_TL", remarks: "管理组织级报销类型定义，取代EXP_SOB_EXPENSE_TYPES多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "MO_EXPENSE_TYPE_ID", type: "BIGINT",  remarks: "pk，管理组织级报销类型ID")  {constraints(nullable: "false", primaryKey: "true")}
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
    changeSet(author: "xiuxian.wu@hand-china.com", id: "2019-02-26-EXP_MO_EXP_TYPE_ASGN_COM") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_MO_EXP_TYPE_ASGN_COM_S', startValue:"10001")
        }

        createTable(tableName: "EXP_MO_EXP_TYPE_ASGN_COM", remarks: "管理组织级报销类型分配公司") {

            column(name: "ASSIGN_ID", type: "BIGINT",autoIncrement: true, startWith: "10001", remarks: "pk")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_MO_EXP_TYPE_ASGN_COM_PK")}
            column(name: "MO_EXPENSE_TYPE_ID", type: "BIGINT", remarks: "管理组织级报销类型ID")
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司ID")
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "EXP_MO_EXP_TYPE_ASGN_COM", indexName: "EXP_MO_EXP_TYPE_ASGN_COM_N1") {
                    column(name: "COMPANY_ID")
                }
        
    }


    changeSet(author: "xiuxian.wu@hand-china.com", id: "2019-02-26-EXP_MO_EXP_TYPE_REF_EXP_IT") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_MO_EXP_TYPE_REF_EXP_IT_S', startValue:"10001")
        }

        createTable(tableName: "EXP_MO_EXP_TYPE_REF_EXP_IT", remarks: "管理组织级报销类型关联费用项目，取代EXP_SOB_TYPE_EXPENSE_ITEMS") {

            column(name: "REF_ID", type: "BIGINT",autoIncrement: true, startWith: "10001", remarks: "pk")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_MO_EXP_TYPE_REF_EXP_IT_PK")}
            column(name: "MAG_ORG_ID", type: "BIGINT", remarks: "管理组织ID")  {constraints(nullable:"false")}
            column(name: "MO_EXPENSE_TYPE_ID", type: "BIGINT", remarks: "管理组织级报销类型ID")  {constraints(nullable:"false")}
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
        
        addUniqueConstraint(columnNames:"MO_EXPENSE_TYPE_ID,MO_EXPENSE_ITEM_ID",tableName:"EXP_MO_EXP_TYPE_REF_EXP_IT",constraintName: "EXP_MO_EXP_TYPE_REF_EXP_ITS_U1")
    }


    changeSet(author: "xiuxian.wu@hand-china.com", id: "2019-02-26-EXP_MO_EXP_TYPE_REF_REQ_IT") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_MO_EXP_TYPE_REF_REQ_IT_S', startValue:"10001")
        }

        createTable(tableName: "EXP_MO_EXP_TYPE_REF_REQ_IT", remarks: "管理组织级报销类型关联申请项目，取代EXP_SOB_TYPE_REQ_ITEMS") {

            column(name: "MO_EXPENSE_TYPE_ID", type: "BIGINT", remarks: "管理组织级报销类型ID")  {constraints(nullable:"false")}  
            column(name: "MO_REQ_ITEM_ID", type: "BIGINT", remarks: "费用项目ID")  {constraints(nullable:"false")}  
            column(name: "ENABLED_FLAG", type: "VARCHAR(38)", remarks: "启用标志")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "REF_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "pk")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_MO_EXP_TYPE_REF_REQ_IT_PK")} 
            column(name: "MAG_ORG_ID", type: "BIGINT", remarks: "管理组织ID")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"MO_REQ_ITEM_ID,MO_EXPENSE_TYPE_ID",tableName:"EXP_MO_EXP_TYPE_REF_REQ_IT",constraintName: "EXP_MO_EXP_TYPE_REF_REQ_ITS_U1")
    }


}