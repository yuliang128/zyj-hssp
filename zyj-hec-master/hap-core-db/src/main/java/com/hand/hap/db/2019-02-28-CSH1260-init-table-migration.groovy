package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-02-28-CSH1260-init-table-migration.groovy') {


    changeSet(author: "hui.lu@hand-china.com", id: "2019-02-28-CSH_PAYMENT_BATCH_TYPE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_PAYMENT_BATCH_TYPE_S', startValue:"10001")
        }

        createTable(tableName: "CSH_PAYMENT_BATCH_TYPE", remarks: "付款批类型定义") {

            column(name: "TYPE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_PAYMENT_BATCH_TYPE_PK")} 
            column(name: "MAG_ORG_ID", type: "BIGINT", remarks: "管理组织ID")  {constraints(nullable:"false")}  
            column(name: "TYPE_CODE", type: "VARCHAR(30)", remarks: "付款批类型代码")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "付款批类型描述")
            column(name: "AUDIT_METHOD", type: "VARCHAR(30)", remarks: "审核方式（SYSCODE：CSH_PAY_BATCH_TYPE_AUDIT_METHOD)")   
            column(name: "POSTING_METHOD", type: "VARCHAR(30)", remarks: "过账方式（SYSCODE：CSH_PAY_BATCH_TYPE_POST_METHOD）")   
            column(name: "ACCOUNT_METHOD", type: "VARCHAR(30)", remarks: "凭证方式（SYSCODE：CSH_PAY_BATCH_TYPE_ACCOUNT_METHOD）")   
            column(name: "ACCOUNT_FLAG", type: "VARCHAR(1)", remarks: "合并凭证")  {constraints(nullable:"false")}  
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"MAG_ORG_ID,TYPE_CODE",tableName:"CSH_PAYMENT_BATCH_TYPE",constraintName: "CSH_PAYMENT_BATCH_TYPE_U1")
    }

    changeSet(author:"hui.lu@hand-china.com", id: "2019-02-28-CSH_PAYMENT_BATCH_TYPE_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_PAYMENT_BATCH_TYPE_TL_S', startValue: "10001")
        }

        createTable(tableName: "CSH_PAYMENT_BATCH_TYPE_TL", remarks: "付款批类型定义多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "TYPE_ID", type: "BIGINT",  remarks: "主键，供其他表外键使用")  {constraints(nullable: "false", primaryKey: "true")} 
            column(name: "DESCRIPTION", type: "VARCHAR(500)",  remarks: "付款批类型描述")
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