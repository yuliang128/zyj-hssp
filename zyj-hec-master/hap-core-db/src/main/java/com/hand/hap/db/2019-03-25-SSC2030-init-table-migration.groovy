package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-03-25-SSC2030-init-table-migration.groovy') {


    changeSet(author: "hui.lu@hand-china.com", id: "2019-03-25-SSC_PROCESS") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'SSC_PROCESS_S', startValue:"10001")
        }

        createTable(tableName: "SSC_PROCESS", remarks: "操作记录") {

            column(name: "PROCESS_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "SSC_PROCESS_PK")} 
            column(name: "DISPATCH_RECORD_ID", type: "BIGINT", remarks: "派工记录ID")  {constraints(nullable:"false")}  
            column(name: "TASK_ID", type: "BIGINT", remarks: "任务ID")  {constraints(nullable:"false")}  
            column(name: "PROCESS_TYPE_CODE", type: "VARCHAR(30)", remarks: "操作类型代码")  {constraints(nullable:"false")}  
            column(name: "ACTION_ID", type: "BIGINT", remarks: "动作ID")   
            column(name: "PROCESS_TIME", type: "DATETIME", remarks: "操作时间")  {constraints(nullable:"false")}  
            column(name: "PROCESS_OPINION", type: "VARCHAR(2000)", remarks: "操作意见")   
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "员工ID")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "SSC_PROCESS", indexName: "SSC_PROCESS_N1") {
                    column(name: "DISPATCH_RECORD_ID")
                }
                createIndex(tableName: "SSC_PROCESS", indexName: "SSC_PROCESS_N2") {
                    column(name: "TASK_ID")
                }
                createIndex(tableName: "SSC_PROCESS", indexName: "SSC_PROCESS_N3") {
                    column(name: "EMPLOYEE_ID")
                }
        
    }


}