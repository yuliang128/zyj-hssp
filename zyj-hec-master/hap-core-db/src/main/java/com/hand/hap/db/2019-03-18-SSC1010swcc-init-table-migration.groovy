package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-03-18-SSC1010swcc-init-table-migration.groovy') {


    changeSet(author: "bo.zhang05@hand-china.com", id: "2019-03-18-SSC_WORK_CENTER_COMPANY") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'SSC_WORK_CENTER_COMPANY_S', startValue:"10001")
        }

        createTable(tableName: "SSC_WORK_CENTER_COMPANY", remarks: "共享作业中心业务范围_公司范围") {

            column(name: "RECORD_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "SSC_WORK_CENTER_COMPANY_PK")} 
            column(name: "SCOPE_ID", type: "BIGINT", remarks: "业务范围ID")  {constraints(nullable:"false")}  
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司ID")  {constraints(nullable:"false")}  
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用状态")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"COMPANY_ID,SCOPE_ID",tableName:"SSC_WORK_CENTER_COMPANY",constraintName: "SSC_WORK_CENTER_COMPANY_U1")
    }


}