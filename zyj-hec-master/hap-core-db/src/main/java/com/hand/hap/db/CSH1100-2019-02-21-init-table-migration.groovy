package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: 'CSH1100-2019-02-21-init-table-migration.groovy') {


    changeSet(author: "dingwei.ma@hand-china.com", id: "2019-02-21-CSH_CASH_FLOW_FORMULA_HD") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_CASH_FLOW_FORMULA_HD_S', startValue:"10001")
        }

        createTable(tableName: "CSH_CASH_FLOW_FORMULA_HD", remarks: "现金流量公式头表") {

            column(name: "CASH_FLOW_ITEM_ID", type: "BIGINT", remarks: "现金流量项ID")  {constraints(nullable:"false")}  
            column(name: "FORMULA_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "公式ID")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_CASH_FLOW_FORMULA_HD_PK")} 
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
    
    }


    changeSet(author: "dingwei.ma@hand-china.com", id: "2019-02-21-CSH_CASH_FLOW_FORMULA_LN") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_CASH_FLOW_FORMULA_LN_S', startValue:"10001")
        }

        createTable(tableName: "CSH_CASH_FLOW_FORMULA_LN", remarks: "现金流量公式行表") {

            column(name: "FORMULA_ID", type: "BIGINT", remarks: "公式ID")  {constraints(nullable:"false")}  
            column(name: "FORMULA_LINE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "公式行ID")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_CASH_FLOW_FORMULA_LN_PK")} 
            column(name: "OPERATION", type: "VARCHAR(1)", remarks: "运算符")  {constraints(nullable:"false")}  
            column(name: "CASH_FLOW_ITEM_ID_FROM", type: "BIGINT", remarks: "现金流入项ID")  {constraints(nullable:"false")}  
            column(name: "CASH_FLOW_ITEM_ID_TO", type: "BIGINT", remarks: "现金流出项ID")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
    
    }
	
	changeSet(author: "dingwei.ma@hand-china.com", id: "2019-02-21-CSH_CASH_ACCOUNT") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_CASH_ACCOUNT_S', startValue:"10001")
        }

        createTable(tableName: "CSH_CASH_ACCOUNT", remarks: "现金及等价物科目表") {
            column(name: "ASSIGN_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键Id")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_CASH_ACCOUNT_PK")}
            column(name: "ACCOUNT_ID", type: "BIGINT", remarks: "科目ID")  {constraints(nullable:"false")}  
            column(name: "SET_OF_BOOKS_ID", type: "BIGINT", remarks: "帐套ID")  {constraints(nullable:"false")}
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        addUniqueConstraint(columnNames:"SET_OF_BOOKS_ID,ACCOUNT_ID",tableName:"CSH_CASH_ACCOUNT",constraintName: "CSH_CASH_ACCOUNTS_U1")
    }
	
	changeSet(author: "dingwei.ma@hand-china.com", id: "2019-02-21-CSH_DEFAULT_CASH_FLOW_ITEM") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_DEFAULT_CASH_FLOW_ITEM_S', startValue:"10001")
        }

        createTable(tableName: "CSH_DEFAULT_CASH_FLOW_ITEM", remarks: "默认现金流量项表") {
            column(name: "ASSIGN_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键Id")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_DEFAULT_CASH_FLOW_ITEM_PK")}
            column(name: "CASH_FLOW_ITEM_ID", type: "BIGINT", remarks: "现金流量项ID")  {constraints(nullable:"false")}  
            column(name: "SET_OF_BOOKS_ID", type: "BIGINT", remarks: "帐套ID")  {constraints(nullable:"false")}
            column(name: "ACCOUNT_ID", type: "BIGINT", remarks: "科目ID")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }

        addUniqueConstraint(columnNames:"ACCOUNT_ID,CASH_FLOW_ITEM_ID",tableName:"CSH_DEFAULT_CASH_FLOW_ITEM",constraintName: "CSH_DEFAULT_CASH_FLOW_ITEM_U1")
    }


}