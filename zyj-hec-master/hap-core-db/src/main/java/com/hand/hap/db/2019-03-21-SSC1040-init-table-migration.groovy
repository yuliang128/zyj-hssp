package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-03-21-SSC1040-init-table-migration.groovy') {


    changeSet(author: "bo.zhang05@hand-china.com", id: "2019-03-21-SSC_NODE_ASSIGN_RULE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'SSC_NODE_ASSIGN_RULE_S', startValue:"10001")
        }

        createTable(tableName: "SSC_NODE_ASSIGN_RULE", remarks: "共享工作流程节点分配工作人员权限规则") {

            column(name: "RECORD_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "SSC_NODE_ASSIGN_RULE_PK")} 
            column(name: "WORKER_ASSIGN_ID", type: "BIGINT", remarks: "作业人员分配ID")  {constraints(nullable:"false")}  
            column(name: "WFL_BUSINESS_RULE_ID", type: "BIGINT", remarks: "工作流权限规则ID")  {constraints(nullable:"false")}  
            column(name: "START_DATE", type: "DATETIME", remarks: "启用日期")  {constraints(nullable:"false")}  
            column(name: "END_DATE", type: "DATETIME", remarks: "失效日期")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"WFL_BUSINESS_RULE_ID,WORKER_ASSIGN_ID",tableName:"SSC_NODE_ASSIGN_RULE",constraintName: "SSC_NODE_ASSIGN_RULE_U1")
    }


}