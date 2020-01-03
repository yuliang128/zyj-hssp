package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-03-11-SCC1010swc-init-table-migration.groovy') {


    changeSet(author: "bo.zhang05@hand-china.com", id: "2019-03-11-SSC_WORK_CENTER") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'SSC_WORK_CENTER_S', startValue:"10001")
        }

        createTable(tableName: "SSC_WORK_CENTER", remarks: "共享作业中心") {

            column(name: "WORK_CENTER_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "SSC_WORK_CENTER_PK")} 
            column(name: "WORK_CENTER_CODE", type: "VARCHAR(30)", remarks: "工作中心代码")   
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述ID")   
            column(name: "MANAGER_TYPE", type: "VARCHAR(30)", remarks: "负责人类型")   
            column(name: "MANAGER_ID", type: "BIGINT", remarks: "负责人ID")   
            column(name: "PROCESS_MODEL", type: "VARCHAR(30)", remarks: "处理模式：SSC_PROCESS_MODEL")   
            column(name: "AUDIT_TYPE", type: "VARCHAR(30)", remarks: "审核")   
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
        
        addUniqueConstraint(columnNames:"WORK_CENTER_CODE",tableName:"SSC_WORK_CENTER",constraintName: "SSC_WORK_CENTER_U1")
    }

    changeSet(author:"bo.zhang05@hand-china.com", id: "2019-03-11-SSC_WORK_CENTER_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'SSC_WORK_CENTER_TL_S', startValue: "10001")
        }

        createTable(tableName: "SSC_WORK_CENTER_TL", remarks: "共享作业中心多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "WORK_CENTER_ID", type: "BIGINT",  remarks: "主键")  {constraints(nullable: "false", primaryKey: "true")} 
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