package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-0305-CSH1230-init-table-migration.groovy') {


    changeSet(author: "weikun.wang@hand-china.com", id: "2019-03-05-CSH_PAYMENT_RULE_DETAIL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_PAYMENT_RULE_DETAILS_S', startValue:"10001")
        }

        createTable(tableName: "CSH_PAYMENT_RULE_DETAIL", remarks: "支付权限规则定义明细表") {

            column(name: "RULE_DETAIL_ID", type: "BIGINT", remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_PAYMENT_RULE_DETAIL_PK")}
            column(name: "RULE_ID", type: "BIGINT", remarks: "支付规则ID")  {constraints(nullable:"false")}
            column(name: "SEQUENCE", type: "BIGINT", remarks: "序号")  {constraints(nullable:"false")}
            column(name: "AND_OR", type: "VARCHAR(30)", remarks: "AND/OR")  {constraints(nullable:"false")}  
            column(name: "LEFT_PARENTHESIS", type: "VARCHAR(30)", remarks: "左括号")   
            column(name: "RIGHT_PARENTHESIS", type: "VARCHAR(30)", remarks: "右括号")   
            column(name: "RULE_PARAMETER_ID", type: "BIGINT", remarks: "权限规则参数ID")  {constraints(nullable:"false")}
            column(name: "CONDITION_TYPE", type: "VARCHAR(30)", remarks: "条件类型")  {constraints(nullable:"false")}  
            column(name: "CONDITION_VALUE", type: "VARCHAR(100)", remarks: "条件值")  {constraints(nullable:"false")}  
            column(name: "SQL_PARAM_1", type: "VARCHAR(100)", remarks: "参数1")   
            column(name: "SQL_PARAM_2", type: "VARCHAR(100)", remarks: "参数2")   
            column(name: "SQL_PARAM_3", type: "VARCHAR(100)", remarks: "参数3")   
            column(name: "SQL_PARAM_4", type: "VARCHAR(100)", remarks: "参数4")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "CSH_PAYMENT_RULE_DETAIL", indexName: "CSH_PAYMENT_RULE_DETAIL_N1") {
                    column(name: "RULE_ID")
                }
        
    }


}