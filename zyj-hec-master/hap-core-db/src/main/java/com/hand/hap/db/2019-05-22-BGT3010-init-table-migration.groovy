package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-05-22-BGT3010-init-table-migration.groovy') {


    changeSet(author: "yuting.gui@hand-china.com", id: "2019-05-22-BGT_BALANCE_DETAIL_INIT") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'BGT_BALANCE_DETAIL_INIT_S', startValue:"10001")
        }

        createTable(tableName: "BGT_BALANCE_DETAIL_INIT", remarks: "预算保留临时表") {

            column(name: "SESSION_ID", type: "BIGINT", remarks: "系统SESSION ID")  {constraints(nullable:"false")}  
            column(name: "BUDGET_RESERVE_LINE_ID", type: "BIGINT", remarks: "预算保留表行ID")   
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "BGT_BALANCE_DETAIL_INIT", indexName: "BGT_BALANCE_DETAILS_TMP_N1") {
                    column(name: "SESSION_ID")
                }
    
    }


}