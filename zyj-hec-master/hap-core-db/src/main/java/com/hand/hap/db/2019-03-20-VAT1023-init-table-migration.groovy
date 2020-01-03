package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-03-20-VAT1023-init-table-migration.groovy') {


    changeSet(author: "hui.zhao01@hand-china.com", id: "2019-03-20-VAT_TAX_TRANSFER_RATIO") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'VAT_TAX_TRANSFER_RATIO_S', startValue:"10001")
        }

        createTable(tableName: "VAT_TAX_TRANSFER_RATIO", remarks: "进项税转出比例定义") {

            column(name: "RATIO_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "VAT_TAX_TRANSFER_RATIO_PK")} 
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体")  {constraints(nullable:"false")}  
            column(name: "PERIOD_NAME", type: "VARCHAR(30)", remarks: "期间")  {constraints(nullable:"false")}  
            column(name: "VAT_TRANSFER_TYPE", type: "VARCHAR(30)", remarks: "转出方式")  {constraints(nullable:"false")}  
            column(name: "TRANSFER_RATIO", type: "NUMBER", remarks: "转出比例")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"ACC_ENTITY_ID,PERIOD_NAME",tableName:"VAT_TAX_TRANSFER_RATIO",constraintName: "VAT_TAX_TRANSFER_RATIO_U1")
    }


}