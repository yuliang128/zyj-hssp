package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-01-29-EXP4010-init-table-migration.groovy') {


    changeSet(author: "jiangxz", id: "2019-01-29-EXP_REQ_PAGE_ELEMENT") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_REQ_PAGE_ELEMENT_S', startValue:"10001")
        }

        createTable(tableName: "EXP_REQ_PAGE_ELEMENT", remarks: "费用申请单页面元素") {

            column(name: "REQ_PAGE_ELEMENT_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "页面元素ID，pk")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_REQ_PAGE_ELEMENT_PK")}
            column(name: "REQ_PAGE_ELEMENT_CODE", type: "VARCHAR(30)", remarks: "页面元素代码")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述ID")  {constraints(nullable:"false")}  
            column(name: "SERVICE_ID", type: "BIGINT", remarks: "创建页面ID")  {constraints(nullable:"false")}  
            column(name: "READONLY_SERVICE_ID", type: "BIGINT", remarks: "只读页面ID")   
            column(name: "SERVICE1_ID", type: "BIGINT", remarks: "扩展页面1ID")   
            column(name: "SERVICE2_ID", type: "BIGINT", remarks: "扩展页面2ID")   
            column(name: "SERVICE3_ID", type: "BIGINT", remarks: "扩展页面3ID")   
            column(name: "SERVICE4_ID", type: "BIGINT", remarks: "扩展页面4ID")   
            column(name: "SERVICE5_ID", type: "BIGINT", remarks: "扩展页面5ID")   
            column(name: "REPORT_TYPE_FLAG", type: "VARCHAR(1)", remarks: "报销类型标识")  {constraints(nullable:"false")}  
            column(name: "EXPENSE_OBJECT_FLAG", type: "VARCHAR(1)", remarks: "费用对象标识")  {constraints(nullable:"false")}  
            column(name: "DIMENSION_FLAG", type: "VARCHAR(1)", remarks: "维度标识")  {constraints(nullable:"false")}  
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "SYSTEM_FLAG", type: "VARCHAR(1)", remarks: "预置标志")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            
        addUniqueConstraint(columnNames:"REQ_PAGE_ELEMENT_CODE",tableName:"EXP_REQ_PAGE_ELEMENT",constraintName: "EXP_REQ_PAGE_ELEMENT_U1")
        addUniqueConstraint(columnNames:"SERVICE_ID",tableName:"EXP_REQ_PAGE_ELEMENT",constraintName: "EXP_REQ_PAGE_ELEMENT_U2")
    }

    changeSet(author:"jiangxz", id: "2019-01-29-EXP_REQ_PAGE_ELEMENT_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_REQ_PAGE_ELEMENT_TL_S', startValue: "10001")
        }

        createTable(tableName: "EXP_REQ_PAGE_ELEMENT_TL", remarks: "费用申请单页面元素多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "REQ_PAGE_ELEMENT_ID", type: "BIGINT",  remarks: "页面元素ID，pk")  {constraints(nullable: "false", primaryKey: "true")} 
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

}