package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: 'CSH5070-2019-03-04-init-table-migration.groovy') {


    changeSet(author: "dingwei.ma@hand-china.com", id: "2019-03-04-CSH_PAYMENT_REQ_ACCOUNT_TMP") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_PAYMENT_REQ_ACCOUNT_TMP_S', startValue:"10001")
        }

        createTable(tableName: "CSH_PAYMENT_REQ_ACCOUNT_TMP", remarks: "借款申请凭证临时表") {

            column(name: "SESSION_ID", type: "BIGINT", remarks: "系统SESSION ID")  {constraints(nullable:"false")}  
            column(name: "PAYMENT_REQ_HEADER_ID", type: "BIGINT", remarks: "借款申请单头id")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
    
        addUniqueConstraint(columnNames:"PAYMENT_REQ_HEADER_ID,SESSION_ID",tableName:"CSH_PAYMENT_REQ_ACCOUNT_TMP",constraintName: "CSH_PMT_REQ_ACCOUNTS_TMP_U1")
    }
	
	
	changeSet(author: "dingwei.ma@hand-china.com", id: "2019-03-04-CSH_PMT_REQ_CURRENCY_TMP") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_PMT_REQ_CURRENCY_TMP_S', startValue:"10001")
        }

        createTable(tableName: "CSH_PMT_REQ_CURRENCY_TMP", remarks: "汇率定义表") {

            column(name: "SESSION_ID", type: "BIGINT", remarks: "系统SESSION ID")  {constraints(nullable:"false")}  
            column(name: "CURRENCY_CODE", type: "VARCHAR(30)", remarks: "币种")  {constraints(nullable:"false")}  
            column(name: "EXCHANGE_RATE_TYPE", type: "VARCHAR(30)", remarks: "汇率类型")   
            column(name: "EXCHANGE_RATE", type: "BIGINT", remarks: "汇率")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
    
        addUniqueConstraint(columnNames:"CURRENCY_CODE,SESSION_ID",tableName:"CSH_PMT_REQ_CURRENCY_TMP",constraintName: "CSH_PMT_REQ_CURRENCY_TMP_U1")
    }


    changeSet(author: "dingwei.ma@hand-china.com", id: "2019-03-04-CSH_PMT_REQ_AUDIT_ERROR_LOG") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_PMT_REQ_AUDIT_ERROR_LOG_S', startValue:"10001")
        }

        createTable(tableName: "CSH_PMT_REQ_AUDIT_ERROR_LOG", remarks: "借款单审核错误日志表") {

            column(name: "SESSION_ID", type: "BIGINT", remarks: "系统SESSION ID")  {constraints(nullable:"false")}  
            column(name: "REQUISITION_NUMBER", type: "VARCHAR(30)", remarks: "申请单编码")   
            column(name: "PAYMENT_REQ_LINE_ID", type: "BIGINT", remarks: "借款申请行id")   
            column(name: "MESSAGE", type: "VARCHAR(1000)", remarks: "日志信息")   
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "CSH_PMT_REQ_AUDIT_ERROR_LOG", indexName: "CSH_PMT_REQ_AUDIT_ERR_LOGS_N1") {
                    column(name: "SESSION_ID")
                }
    
    }


}