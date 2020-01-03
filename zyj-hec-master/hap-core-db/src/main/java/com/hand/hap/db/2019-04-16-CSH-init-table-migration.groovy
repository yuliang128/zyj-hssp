package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-04-16-CSH-init-table-migration.groovy') {


    changeSet(author: "dingwei.ma@hand-china,com", id: "2019-04-16-CON_CONTRACT_PARTNER_LINE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CON_CONTRACT_PARTNER_LINE_S', startValue:"10001")
        }

        createTable(tableName: "CON_CONTRACT_PARTNER_LINE", remarks: "合同对象表") {

            column(name: "CONTRACT_HEADER_ID", type: "BIGINT", remarks: "合同头ID")  {constraints(nullable:"false")}  
            column(name: "CONTRACT_PARTNER_LINE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "合同对象行ID")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CON_CONTRACT_PARTNER_LINE_PK")} 
            column(name: "PARTNER_CATEGORY", type: "VARCHAR(30)", remarks: "对象类型")   
            column(name: "PARTNER_ID", type: "BIGINT", remarks: "对象ID")   
            column(name: "BANK_BRANCH_CODE", type: "VARCHAR(100)", remarks: "银行")   
            column(name: "BANK_ACCOUNT_CODE", type: "VARCHAR(100)", remarks: "银行账号")   
            column(name: "TAX_ID_NUMBER", type: "VARCHAR(100)", remarks: "税号")   
            column(name: "MEMO", type: "VARCHAR(2000)", remarks: "备注")   
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "CON_CONTRACT_PARTNER_LINE", indexName: "CON_CONTRACT_PARTNER_LINE_N1") {
                    column(name: "CONTRACT_HEADER_ID")
                    column(name: "CONTRACT_PARTNER_LINE_ID")
                }
        
    }

    changeSet(author: "dingwei.ma@hand-china,com", id: "2019-04-16-CON_CONTRACT_REF_AUTHORITY") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CON_CONTRACT_REF_AUTHORITY_S', startValue:"10001")
        }

        createTable(tableName: "CON_CONTRACT_REF_AUTHORITY", remarks: "合同引用权限表") {

            column(name: "REF_AUTHORITY_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "合同引用ID")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CON_CONTRACT_REF_AUTHORITY_PK")}
            column(name: "CONTRACT_HEADER_ID", type: "BIGINT", remarks: "合同头ID")  {constraints(nullable:"false")}
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司ID")  {constraints(nullable:"false")}
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "员工ID")
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")  {constraints(nullable:"false")}
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID")  {constraints(nullable:"false")}
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")  {constraints(nullable:"false")}
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        createIndex(tableName: "CON_CONTRACT_REF_AUTHORITY", indexName: "CON_CONTRACT_REF_AUTHORITY_N1") {
            column(name: "CONTRACT_HEADER_ID")
        }

        addUniqueConstraint(columnNames:"COMPANY_ID,EMPLOYEE_ID,CONTRACT_HEADER_ID",tableName:"CON_CONTRACT_REF_AUTHORITY",constraintName: "CON_CONTRACT_REF_AUTHORITY_U1")
    }


}