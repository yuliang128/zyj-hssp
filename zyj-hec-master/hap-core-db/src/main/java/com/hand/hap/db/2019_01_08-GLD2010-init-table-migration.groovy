package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019_01_08-GLD2010-init-table-migration.groovy') {


    changeSet(author: "xiuxian.wu@hand-china.com", id: "2019-01-08-GLD_SET_OF_BOOK") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_SET_OF_BOOK_S', startValue:"10001")
        }

        createTable(tableName: "GLD_SET_OF_BOOK", remarks: "帐套") {

            column(name: "SET_OF_BOOKS_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "帐套ID")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "GLD_SET_OF_BOOK_PK")} 
            column(name: "SET_OF_BOOKS_NAME", type: "VARCHAR(500)", remarks: "帐套名称")   
            column(name: "SET_OF_BOOKS_CODE", type: "VARCHAR(30)", remarks: "帐套代码")  {constraints(nullable:"false")}  
            column(name: "PERIOD_SET_CODE", type: "VARCHAR(30)", remarks: "期间代码")  {constraints(nullable:"false")}  
            column(name: "FUNCTIONAL_CURRENCY_CODE", type: "VARCHAR(10)", remarks: "本位币")  {constraints(nullable:"false")}  
            column(name: "ACCOUNT_SET_ID", type: "BIGINT", remarks: "科目表ID")  {constraints(nullable:"false")}  
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            
        addUniqueConstraint(columnNames:"SET_OF_BOOKS_CODE",tableName:"GLD_SET_OF_BOOK",constraintName: "GLD_SET_OF_BOOKS_U1")
        addUniqueConstraint(columnNames:"SET_OF_BOOKS_NAME",tableName:"GLD_SET_OF_BOOK",constraintName: "GLD_SET_OF_BOOKS_U2")
    }

    changeSet(author:"xiuxian.wu@hand-china.com", id: "2019-01-08-GLD_SET_OF_BOOK_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_SET_OF_BOOK_TL_S', startValue: "10001")
        }

        createTable(tableName: "GLD_SET_OF_BOOK_TL", remarks: "帐套多语言表") {
            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "SET_OF_BOOKS_ID", type: "BIGINT",  remarks: "主键，供其他表外键使用")  {constraints(nullable: "false", primaryKey: "true")}
            column(name: "SET_OF_BOOKS_NAME", type: "VARCHAR(500)",  remarks: "帐套名称")   
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