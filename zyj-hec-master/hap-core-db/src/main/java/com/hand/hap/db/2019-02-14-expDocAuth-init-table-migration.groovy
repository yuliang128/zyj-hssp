package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-02-14-expDocAuth-init-table-migration.groovy') {


    changeSet(author: "yang.duan@hand-china.com", id: "2019-02-14-EXP_DOCUMENT_AUTHORITY") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_DOCUMENT_AUTHORITY_S', startValue:"10001")
        }

        createTable(tableName: "EXP_DOCUMENT_AUTHORITY", remarks: "单据授权表") {

            column(name: "AUTHORITY_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_DOCUMENT_AUTHORITY_PK")} 
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司ID")  {constraints(nullable:"false")}  
            column(name: "UNIT_ID", type: "BIGINT", remarks: "委托部门ID")   
            column(name: "POSITION_ID", type: "BIGINT", remarks: "委托岗位ID")   
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "委托人ID")   
            column(name: "GRANTED_POSITION_ID", type: "BIGINT", remarks: "受托岗位ID")   
            column(name: "GRANTED_EMPLOYEE_ID", type: "BIGINT", remarks: "受托人ID")   
            column(name: "DOC_CATEGORY", type: "VARCHAR(30)", remarks: "业务类型（SYSCODE：WFL_WORKFLOW_CATEGORY）")  {constraints(nullable:"false")}  
            column(name: "DOC_TYPE_ID", type: "BIGINT", remarks: "单据类型ID")   
            column(name: "START_DATE_ACTIVE", type: "DATETIME", remarks: "有效期从")  {constraints(nullable:"false")}  
            column(name: "END_DATE_ACTIVE", type: "DATETIME", remarks: "有效期至")   
            column(name: "INQUIRE_FLAG", type: "VARCHAR(1)", remarks: "查询标志")  {constraints(nullable:"false")}  
            column(name: "MAINTAIN_FLAG", type: "VARCHAR(1)", remarks: "维护标志")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "EXP_DOCUMENT_AUTHORITY", indexName: "EXP_DOCUMENT_AUTHORITY_N1") {
                    column(name: "GRANTED_EMPLOYEE_ID")
                    column(name: "DOC_CATEGORY")
                }
                createIndex(tableName: "EXP_DOCUMENT_AUTHORITY", indexName: "EXP_DOCUMENT_AUTHORITY_N2") {
                    column(name: "DOC_CATEGORY")
                    column(name: "END_DATE_ACTIVE")
                    column(name: "GRANTED_POSITION_ID")
                    column(name: "GRANTED_EMPLOYEE_ID")
                    column(name: "START_DATE_ACTIVE")
                }
        
    }


}