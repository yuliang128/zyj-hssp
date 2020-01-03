package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-2-27-EXP2170-init-table-migration.groovy') {


    changeSet(author: "yang.cai@hand-china.com", id: "2019-02-27-EXP_MO_REQ_ITEM_DESC") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_MO_REQ_ITEM_DESC_S', startValue:"10001")
        }

        createTable(tableName: "EXP_MO_REQ_ITEM_DESC", remarks: "管理组织级申请项目说明表") {

            column(name: "ITEM_DESC", type: "BIGINT", remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_MO_REQ_ITEM_DESC_PK")}
            column(name: "MAG_ORG_ID", type: "BIGINT", remarks: "管理组织ID")  {constraints(nullable:"false")}  
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "管理公司ID")   
            column(name: "MO_EXP_REQ_TYPE_ID", type: "BIGINT", remarks: "申请单类型ID")   
            column(name: "MO_EXPENSE_TYPE_ID", type: "BIGINT", remarks: "报销类型ID")   
            column(name: "MO_REQ_ITEM_ID", type: "BIGINT", remarks: "申请项目ID")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "VARCHAR(4000)", remarks: "说明")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "EXP_MO_REQ_ITEM_DESC", indexName: "EXP_MO_REQ_ITEM_DESC_N1") {
                    column(name: "COMPANY_ID")
                    column(name: "MAG_ORG_ID")
                    column(name: "MO_REQ_ITEM_ID")
                    column(name: "MO_EXP_REQ_TYPE_ID")
                    column(name: "MO_EXPENSE_TYPE_ID")
                }
        
    }

    changeSet(author:"yang.cai@hand-china.com", id: "2019-02-27-EXP_MO_REQ_ITEM_DESC_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_MO_REQ_ITEM_DESC_TL_S', startValue: "10001")
        }

        createTable(tableName: "EXP_MO_REQ_ITEM_DESC_TL", remarks: "管理组织级申请项目说明表多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "ITEM_DESC", type: "BIGINT",  remarks: "主键，供其他表外键使用")  {constraints(nullable: "false", primaryKey: "true")}
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