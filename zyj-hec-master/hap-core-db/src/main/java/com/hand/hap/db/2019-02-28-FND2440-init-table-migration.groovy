package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-02-28-FND2440-init-table-migration.groovy') {


    changeSet(author: "rui.shi@hand-china.com", id: "2019-02-28-GLD_SOB_PERIOD_STATUS") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_SOB_PERIOD_STATUS_S', startValue:"10001")
        }

        createTable(tableName: "GLD_SOB_PERIOD_STATUS", remarks: "帐套级会计期间控制表") {

            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "PERIOD_STATUS_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "GLD_SOB_PERIOD_STATUS_PK")} 
            column(name: "SET_OF_BOOKS_ID", type: "BIGINT", remarks: "账套ID")  {constraints(nullable:"false")}  
            column(name: "PERIOD_SET_CODE", type: "VARCHAR(30)", remarks: "会计期代码")  {constraints(nullable:"false")}  
            column(name: "PERIOD_NAME", type: "VARCHAR(30)", remarks: "会计期间")  {constraints(nullable:"false")}  
            column(name: "INTERNAL_PERIOD_NUM", type: "BIGINT", remarks: "期间序号")  {constraints(nullable:"false")}  
            column(name: "PERIOD_STATUS_CODE", type: "VARCHAR(20)", remarks: "期间状态代码")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "GLD_SOB_PERIOD_STATUS", indexName: "GLD_SOB_PERIOD_STATUS_N1") {
                    column(name: "INTERNAL_PERIOD_NUM")
                    column(name: "PERIOD_SET_CODE")
                    column(name: "PERIOD_STATUS_CODE")
                }
            
        addUniqueConstraint(columnNames:"INTERNAL_PERIOD_NUM,PERIOD_SET_CODE,SET_OF_BOOKS_ID",tableName:"GLD_SOB_PERIOD_STATUS",constraintName: "GLD_SOB_PERIOD_STATUS_U1")
    }


}