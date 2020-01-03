package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-02-14-EXP1030-init-table-migration.groovy') {


    changeSet(author: "zhongyu.wang@hand-china.com", id: "2019-02-14-EXP_EMPLOYEE_LEVEL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_EMPLOYEE_LEVEL_S', startValue:"10001")
        }

        createTable(tableName: "EXP_EMPLOYEE_LEVEL", remarks: "员工级别") {

            column(name: "EMPLOYEE_LEVELS_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "员工级别ID")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_EMPLOYEE_LEVEL_PK")} 
            column(name: "LEVEL_SERIES_ID", type: "BIGINT", remarks: "级别系列ID")  {constraints(nullable:"false")}  
            column(name: "EMPLOYEE_LEVELS_CODE", type: "VARCHAR(30)", remarks: "级别代码")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述")  {constraints(nullable:"false")}  
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"LEVEL_SERIES_ID,EMPLOYEE_LEVELS_CODE",tableName:"EXP_EMPLOYEE_LEVEL",constraintName: "EXP_EMPLOYEE_LEVELS_U1")
    }

    changeSet(author:"zhongyu.wang@hand-china.com", id: "2019-02-14-EXP_EMPLOYEE_LEVEL_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_EMPLOYEE_LEVEL_TL_S', startValue: "10001")
        }

        createTable(tableName: "EXP_EMPLOYEE_LEVEL_TL", remarks: "员工级别多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "EMPLOYEE_LEVELS_ID", type: "BIGINT",  remarks: "员工级别ID")  {constraints(nullable: "false", primaryKey: "true")} 
            column(name: "DESCRIPTION", type: "VARCHAR(500)",  remarks: "描述")   
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