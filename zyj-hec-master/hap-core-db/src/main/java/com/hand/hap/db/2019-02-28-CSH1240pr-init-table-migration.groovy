package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-02-28-CSH1240pr-init-table-migration.groovy') {


    changeSet(author: "bo.zhang05@hand-china.com", id: "2019-02-28-CSH_PAYMENT_RULE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_PAYMENT_RULE_S', startValue:"10001")
        }

        createTable(tableName: "CSH_PAYMENT_RULE", remarks: "支付权限规则定义表") {

            column(name: "RULE_ID", type: "BIGINT", remarks: "主键")  {constraints(nullable:"false")}  
            column(name: "MAG_ORG_ID", type: "BIGINT", remarks: "管理组织ID")  {constraints(nullable:"false")}  
            column(name: "RULE_CODE", type: "VARCHAR(30)", remarks: "权限规则代码")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "权限规则代码名称ID")   
            column(name: "START_DATE", type: "DATETIME", remarks: "有效日期从")  {constraints(nullable:"false")}  
            column(name: "END_DATE", type: "DATETIME", remarks: "有效日期到")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "BUSINESS_TYPE", type: "VARCHAR(30)", remarks: "业务类别(SYSCODE:CSH_PAYMENT_DOCUMENT_CATEGORY)")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }

        addUniqueConstraint(columnNames:"MAG_ORG_ID,RULE_CODE",tableName:"CSH_PAYMENT_RULE",constraintName: "CSH_PAYMENT_RULE_U1")

    }

    changeSet(author:"bo.zhang05@hand-china.com", id: "2019-02-28-CSH_PAYMENT_RULE_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_PAYMENT_RULE_TL_S', startValue: "10001")
        }

        createTable(tableName: "CSH_PAYMENT_RULE_TL", remarks: "支付权限规则定义表多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "DESCRIPTION", type: "VARCHAR(500)",  remarks: "权限规则代码名称ID")   
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