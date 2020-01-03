package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-02-20FND1090-init-table-migration.groovy') {


    changeSet(author: "weikun.wang@hand-china.com", id: "2019-02-20-GLD_EXCHANGE_RATE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_EXCHANGE_RATE_S', startValue:"10001")
        }

        createTable(tableName: "GLD_EXCHANGE_RATE", remarks: "汇率表") {

            column(name: "CONVERSION_RATE", type: "DECIMAL(10,4)", remarks: "兑换货币数量")
            column(name: "PERIOD_NAME", type: "VARCHAR(30)", remarks: "期间")   
            column(name: "CONVERSION_DATE", type: "DATETIME", remarks: "日期")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "EXCHANGE_RATE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "GLD_EXCHANGE_RATE_PK")} 
            column(name: "TYPE_ID", type: "BIGINT", remarks: "汇率类型ID")  {constraints(nullable:"false")}  
            column(name: "FROM_CURRENCY", type: "VARCHAR(10)", remarks: "原货币")  {constraints(nullable:"false")}  
            column(name: "CURRENCY_VOLUME", type: "DECIMAL(10,0)", remarks: "原货币数量")
            column(name: "TO_CURRENCY", type: "VARCHAR(10)", remarks: "兑换货币")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"CONVERSION_DATE,TO_CURRENCY,PERIOD_NAME,TYPE_ID,FROM_CURRENCY",tableName:"GLD_EXCHANGE_RATE",constraintName: "GLD_EXCHANGE_RATES_U1")
    }


}