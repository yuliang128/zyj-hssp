package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-01-12-GLD1050-init-table-migration.groovy') {


    changeSet(author: "hui.zhao01@hand-china.com", id: "2019-02-12-GLD_TRANSIT_ACCOUNT") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_TRANSIT_ACCOUNT_S', startValue:"10001")
        }

        createTable(tableName: "GLD_TRANSIT_ACCOUNT", remarks: "中转科目定义表") {

            column(name: "TRANSIT_ACCOUNT_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "GLD_TRANSIT_ACCOUNT_PK")} 
            column(name: "MAG_ORG_ID", type: "BIGINT", remarks: "管理组织")  {constraints(nullable:"false")}  
            column(name: "SET_OF_BOOKS_ID", type: "BIGINT", remarks: "账套")  {constraints(nullable:"false")}  
            column(name: "MO_EXPENSE_ITEM_ID", type: "BIGINT", remarks: "费用项目")  {constraints(nullable:"false")}  
            column(name: "MO_EXP_REPORT_TYPE_ID", type: "BIGINT", remarks: "报销单类型")   
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体")   
            column(name: "DR_ACCOUNT_ID", type: "BIGINT", remarks: "借方科目")  {constraints(nullable:"false")}  
            column(name: "CR_ACCOUNT_ID", type: "BIGINT", remarks: "贷方科目")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "GLD_TRANSIT_ACCOUNT", indexName: "GLD_TRANSIT_ACCOUNT_N1") {
                    column(name: "MO_EXP_REPORT_TYPE_ID")
                    column(name: "MO_EXPENSE_ITEM_ID")
                    column(name: "ACC_ENTITY_ID")
                }
            
        addUniqueConstraint(columnNames:"MO_EXPENSE_ITEM_ID,SET_OF_BOOKS_ID,MAG_ORG_ID",tableName:"GLD_TRANSIT_ACCOUNT",constraintName: "GLD_TRANSIT_ACCOUNT_U1")
    }


}