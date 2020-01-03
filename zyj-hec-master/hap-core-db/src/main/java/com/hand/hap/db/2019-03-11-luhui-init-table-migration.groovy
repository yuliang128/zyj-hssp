package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-03-11-luhui-init-table-migration.groovy') {


    changeSet(author: "hui.lu@hand-china.com", id: "2019-03-11-CON_CASH_TRX_PMT_SCHEDULE_LN") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CON_CASH_TRX_PMT_SCHEDULE_LN_S', startValue:"10001")
        }

        createTable(tableName: "CON_CASH_TRX_PMT_SCHEDULE_LN", remarks: "现金事务与合同资金计划行关联表") {

            column(name: "TRX_PMT_SCHEDULE_LINE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "事务计划行ID")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CON_CASH_TRX_PMT_SCHEDULE_LN_PK")} 
            column(name: "CSH_TRANSACTION_LINE_ID", type: "BIGINT", remarks: "现金事务行ID")  {constraints(nullable:"false")}  
            column(name: "WRITE_OFF_ID", type: "BIGINT", remarks: "核销ID")   
            column(name: "CONTRACT_HEADER_ID", type: "BIGINT", remarks: "合同头ID")  {constraints(nullable:"false")}  
            column(name: "PAYMENT_SCHEDULE_LINE_ID", type: "BIGINT", remarks: "支付计划行ID")   
            column(name: "AMOUNT", type: "NUMBER", remarks: "金额")   
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "CON_CASH_TRX_PMT_SCHEDULE_LN", indexName: "CON_CASH_TRX_PMT_SCHDL_LN_N1") {
                    column(name: "CSH_TRANSACTION_LINE_ID")
                    column(name: "PAYMENT_SCHEDULE_LINE_ID")
                }
        
    }


}