package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-03-15-SSC1010swcbs-init-table-migration.groovy') {


    changeSet(author: "bo.zhang02@hand-china.com", id: "2019-03-15-SSC_WORK_CENTER_BUSI_SCOPE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'SSC_WORK_CENTER_BUSI_SCOPE_S', startValue:"10001")
        }

        createTable(tableName: "SSC_WORK_CENTER_BUSI_SCOPE", remarks: "共享作业中心业务范围") {

            column(name: "SCOPE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "SSC_WORK_CENTER_BUSI_SCOPE_PK")} 
            column(name: "SCOPE_CODE", type: "VARCHAR(30)", remarks: "业务范围代码")  {constraints(nullable:"false")}  
            column(name: "WORK_CENTER_ID", type: "BIGINT", remarks: "工作中心ID")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述ID")   
            column(name: "MAG_ORG_ID", type: "BIGINT", remarks: "管理组织ID")   
            column(name: "START_DATE", type: "DATETIME", remarks: "启用日期")  {constraints(nullable:"false")}  
            column(name: "END_DATE", type: "DATETIME", remarks: "结束日期")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"SCOPE_CODE",tableName:"SSC_WORK_CENTER_BUSI_SCOPE",constraintName: "SSC_WORK_CENTER_BUSI_SCOPE_U1")
    }

    changeSet(author:"bo.zhang02@hand-china.com", id: "2019-03-15-SSC_WORK_CENTER_BUSI_SCOPE_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'SSC_WORK_CENTER_BUSI_SCOPE_TL_S', startValue: "10001")
        }

        createTable(tableName: "SSC_WORK_CENTER_BUSI_SCOPE_TL", remarks: "共享作业中心业务范围多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "SCOPE_ID", type: "BIGINT",  remarks: "主键")  {constraints(nullable: "false", primaryKey: "true")} 
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