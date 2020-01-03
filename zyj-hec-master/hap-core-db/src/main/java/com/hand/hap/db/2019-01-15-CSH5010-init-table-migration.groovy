package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-01-15-CSH5010-init-table-migration.groovy') {


    changeSet(author: "dingwei.ma@hand-china.com", id: "2019-01-15-CSH_CASH_FLOW_ITEM") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_CASH_FLOW_ITEM_S', startValue:"10001")
        }

        createTable(tableName: "CSH_CASH_FLOW_ITEM", remarks: "现金流量项表") {

            column(name: "CASH_FLOW_ITEM_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "现金流量项ID")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_CASH_FLOW_ITEM_PK")} 
            column(name: "SET_OF_BOOK_ID", type: "BIGINT", remarks: "帐套ID")  {constraints(nullable:"false")}  
            column(name: "CASH_FLOW_LINE_NUMBER", type: "BIGINT", remarks: "流量项编号")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述ID")  {constraints(nullable:"false")}  
            column(name: "CASH_FLOW_ITEM_TYPE", type: "VARCHAR(30)", remarks: "流量项类型（ACCOUNT科目，CHAR字符，FORMULA公式）")  {constraints(nullable:"false")}  
            column(name: "INDENT", type: "BIGINT", remarks: "描述缩进")  {constraints(nullable:"false")}  
            column(name: "ORIENTATION", type: "VARCHAR(30)", remarks: "流向（IN流入项，OUT流出项）")   
            column(name: "VISIBLE_FLAG", type: "VARCHAR(1)", remarks: "显示（Y显示，N不显示）")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        createIndex(tableName: "CSH_CASH_FLOW_ITEM", indexName: "CSH_CASH_FLOW_ITEMS_N1") {
            column(name: "CASH_FLOW_LINE_NUMBER")
        }
    }

    changeSet(author:"dingwei.ma@hand-china.com", id: "2019-01-15-CSH_CASH_FLOW_ITEM_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_CASH_FLOW_ITEM_TL_S', startValue: "10001")
        }

        createTable(tableName: "CSH_CASH_FLOW_ITEM_TL", remarks: "现金流量项表多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "CASH_FLOW_ITEM_ID", type: "BIGINT",  remarks: "现金流量项ID")  {constraints(nullable: "false", primaryKey: "true")} 
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