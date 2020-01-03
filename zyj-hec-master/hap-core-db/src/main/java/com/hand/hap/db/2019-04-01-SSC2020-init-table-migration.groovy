package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-04-01-SSC2020-init-table-migration.groovy') {


    changeSet(author: "hui.zhao01@hand-china.com", id: "2019-04-01-SSC_WORK_RECORD") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'SSC_WORK_RECORD_S', startValue:"10001")
        }

        createTable(tableName: "SSC_WORK_RECORD", remarks: "员工工作记录") {

            column(name: "RECORD_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "SSC_WORK_RECORD_PK")} 
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "员工")  {constraints(nullable:"false")}  
            column(name: "WORK_STATUS", type: "VARCHAR(30)", remarks: "工作状态")  {constraints(nullable:"false")}  
            column(name: "PROCESS_DATE", type: "DATETIME", remarks: "操作日期")  {constraints(nullable:"false")}  
            column(name: "PROCESS_TIME", type: "DATETIME", remarks: "操作时间")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "SSC_WORK_RECORD", indexName: "SSC_WORK_RECORD_N1") {
                    column(name: "EMPLOYEE_ID")
                }
                createIndex(tableName: "SSC_WORK_RECORD", indexName: "SSC_WORK_RECORD_N2") {
                    column(name: "PROCESS_DATE")
                }
        
    }


}