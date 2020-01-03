package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-03-15-SSC1010swcdt-init-table-migration.groovy') {


    changeSet(author: "bo.zhang05@hand-china.com", id: "2019-03-15-SSC_WORK_CENTER_DOC_TYPE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'SSC_WORK_CENTER_DOC_TYPE_S', startValue:"10001")
        }

        createTable(tableName: "SSC_WORK_CENTER_DOC_TYPE", remarks: "共享作业中心业务范围_单据类型范围") {

            column(name: "RECORD_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "SSC_WORK_CENTER_DOC_TYPE_PK")} 
            column(name: "SCOPE_ID", type: "BIGINT", remarks: "业务范围ID")  {constraints(nullable:"false")}  
            column(name: "DOC_CATEGORY", type: "VARCHAR(30)", remarks: "单据类别")  {constraints(nullable:"false")}  
            column(name: "DOC_TYPE_ID", type: "BIGINT", remarks: "单据类型ID")   
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
        
        addUniqueConstraint(columnNames:"DOC_CATEGORY,DOC_TYPE_ID,SCOPE_ID",tableName:"SSC_WORK_CENTER_DOC_TYPE",constraintName: "SSC_WORK_CENTER_DOC_TYPE_U1")
    }


}