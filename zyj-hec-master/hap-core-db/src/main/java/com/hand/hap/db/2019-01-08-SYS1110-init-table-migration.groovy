package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-01-08-SYS1110-init-table-migration.groovy') {


    changeSet(author: "dingwei.ma@hand-china.com", id: "2019-01-08-SYS_PARAMETER") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'SYS_PARAMETER_S', startValue:"10001")
        }

        createTable(tableName: "SYS_PARAMETER", remarks: "系统参数表") {

            column(name: "PARAMETER_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "SYS_PARAMETER_PK")} 
            column(name: "PARAMETER_CODE", type: "VARCHAR(100)", remarks: "参数代码")  {constraints(nullable:"false")}  
            column(name: "VALIDATION_TYPE", type: "VARCHAR(100)", remarks: "验证类型")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "VARCHAR(2000)", remarks: "参数名称")  {constraints(nullable:"false")}  
            column(name: "USER_CHANGEABLE_FLAG", type: "VARCHAR(1)", remarks: "用户可更新")  {constraints(nullable:"false")}  
            column(name: "USER_VISIBLE_FLAG", type: "VARCHAR(1)", remarks: "用户可查看")  {constraints(nullable:"false")}  
            column(name: "SYSTEM_ENABLED_FLAG", type: "VARCHAR(1)", remarks: "参数级别：全局")  {constraints(nullable:"false")}  
            column(name: "ROLE_ENABLED_FLAG", type: "VARCHAR(1)", remarks: "参数级别：角色")  {constraints(nullable:"false")}  
            column(name: "USER_ENABLED_FLAG", type: "VARCHAR(1)", remarks: "参数级别：用户")  {constraints(nullable:"false")}  
            column(name: "COMPANY_ENABLED_FLAG", type: "VARCHAR(1)", remarks: "参数级别：公司")  {constraints(nullable:"false")}  
            column(name: "SQL_VALIDATION", type: "VARCHAR(2000)", remarks: "SQL验证")   
            column(name: "START_DATE_ACTIVE", type: "DATETIME", remarks: "有效日期从")   
            column(name: "END_DATE_ACTIVE", type: "DATETIME", remarks: "有效日期至")   
            column(name: "PARAMETER_NAME", type: "VARCHAR(500)", remarks: "")   
            column(name: "ENCRYPT_VALUE_FLAG", type: "VARCHAR(1)", remarks: "加密数据")   
            column(name: "APP_ENABLED_FLAG", type: "VARCHAR(1)", remarks: "APP 启用")   
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID")  {constraints(nullable:"false")}  
            column(name: "ACC_ENTITY_ENABLED_FLAG", type: "VARCHAR(1)", remarks: "参数级别：核算主体")  {constraints(nullable:"false")}  
            column(name: "MAG_ORG_ENABLED_FLAG", type: "VARCHAR(1)", remarks: "参数级别：管理组织")  {constraints(nullable:"false")}  
            column(name: "SET_OF_BOOKS_ENABLED_FLAG", type: "VARCHAR(1)", remarks: "参数级别：账套")  {constraints(nullable:"false")}  
            column(name: "BGT_ORG_ENABLED_FLAG", type: "VARCHAR(1)", remarks: "参数级别：预算组织")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"PARAMETER_CODE",tableName:"SYS_PARAMETER",constraintName: "SYS_PARAMETER_U1")
    }

    changeSet(author:"dingwei.ma@hand-china.com", id: "2019-01-08-SYS_PARAMETER_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'SYS_PARAMETER_TL_S', startValue: "10001")
        }

        createTable(tableName: "SYS_PARAMETER_TL", remarks: "系统参数表多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "PARAMETER_ID", type: "BIGINT",  remarks: "")  {constraints(nullable: "false", primaryKey: "true")} 
            column(name: "PARAMETER_NAME", type: "VARCHAR(500)",  remarks: "")
            column(name: "DESCRIPTION", type: "VARCHAR(2000)",  remarks: "")
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