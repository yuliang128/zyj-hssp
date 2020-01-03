package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-01-28-CSH1015-init-table-migration.groovy') {


    changeSet(author: "yuting.gui@hand-china.com", id: "2019-01-28-CSH_BANK") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_BANK_S', startValue:"10001")
        }

        createTable(tableName: "CSH_BANK", remarks: "银行定义表") {

            column(name: "BANK_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_BANKS_PK")} 
            column(name: "BANK_CODE", type: "VARCHAR(30)", remarks: "银行代码")  {constraints(nullable:"false")}  
            column(name: "BANK_NAME", type: "VARCHAR(500)", remarks: "银行名称")
            column(name: "BANK_NAME_ALT", type: "VARCHAR(500)", remarks: "银行简称")
            column(name: "COUNTRY_CODE", type: "VARCHAR(30)", remarks: "国家")   
            column(name: "BANK_TYPE", type: "VARCHAR(30)", remarks: "银行类型（CASH：现金银行、CLEARING：清算银行、INTERNAL：内部银行、NORMAL：一般银行）")  {constraints(nullable:"false")}  
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")  {constraints(nullable:"false")}  
            column(name: "ZERO_AMOUNTS_ALLOWED", type: "VARCHAR(1)", remarks: "允许零付款")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "CSH_BANK", indexName: "CSH_BANK_N1") {
                    column(name: "BANK_NAME")
                }
                createIndex(tableName: "CSH_BANK", indexName: "CSH_BANK_N2") {
                    column(name: "BANK_NAME_ALT")
                }
        
    }

    changeSet(author:"yuting.gui@hand-china.com", id: "2019-01-28-CSH_BANK_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_BANK_TL_S', startValue: "10001")
        }

        createTable(tableName: "CSH_BANK_TL", remarks: "银行定义表多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "BANK_ID", type: "BIGINT",  remarks: "主键，供其他表外键使用")  {constraints(nullable: "false", primaryKey: "true")} 
            column(name: "BANK_NAME", type: "VARCHAR(500)",  remarks: "银行名称")
            column(name: "BANK_NAME_ALT", type: "VARCHAR(500)",  remarks: "银行简称")
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