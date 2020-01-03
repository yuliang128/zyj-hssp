package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-01-08-SYS1120-init-table-migration.groovy') {


    changeSet(author: "dingwei.ma@hand-china.com", id: "2019-01-08-SYS_PARAMETER_VALUE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'SYS_PARAMETER_VALUE_S', startValue:"10001")
        }

        createTable(tableName: "SYS_PARAMETER_VALUE", remarks: "系统参数值表") {

            column(name: "PARAMETER_ID", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LEVEL_ID", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LEVEL_VALUE", type: "BIGINT", remarks: "值层次")  {constraints(nullable:"false")}  
            column(name: "PARAMETER_VALUE", type: "VARCHAR(240)", remarks: "参数值")   
            column(name: "VALUE_CODE", type: "VARCHAR(1000)", remarks: "值代码")   
            column(name: "VALUE_NAME", type: "VARCHAR(1000)", remarks: "值名称")   
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addPrimaryKey(columnNames:"PARAMETER_ID,LEVEL_ID,LEVEL_VALUE",tableName:"SYS_PARAMETER_VALUE",constraintName: "SYS_PARAMETER_VALUE_PK")
        addUniqueConstraint(columnNames:"PARAMETER_ID,LEVEL_ID,LEVEL_VALUE",tableName:"SYS_PARAMETER_VALUE",constraintName: "SYS_PARAMETER_VALUE_U1")
    }


}