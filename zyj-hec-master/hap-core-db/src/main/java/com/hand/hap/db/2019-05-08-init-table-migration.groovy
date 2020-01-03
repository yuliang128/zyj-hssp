package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-05-08-init-table-migration.groovy') {


    changeSet(author: "yuting.gui@hand-china.com", id: "2019-05-08-ACP_REQ_AUDIT_ERROR_LOG") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'ACP_REQ_AUDIT_ERROR_LOG_S', startValue:"10001")
        }

        createTable(tableName: "ACP_REQ_AUDIT_ERROR_LOG", remarks: "付款申请单审核错误日志表") {

            column(name: "SESSION_ID", type: "BIGINT", remarks: "系统SESSION ID")  {constraints(nullable:"false")}  
            column(name: "REQUISITION_NUMBER", type: "VARCHAR(30)", remarks: "申请单编码")   
            column(name: "REQUISITION_LNS_ID", type: "BIGINT", remarks: "付款申请行id")   
            column(name: "MESSAGE", type: "VARCHAR(1000)", remarks: "日志信息")   
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "ACP_REQ_AUDIT_ERROR_LOG", indexName: "ACP_REQ_AUDIT_ERROR_LOGS_N1") {
                    column(name: "SESSION_ID")
                }
    
    }


}