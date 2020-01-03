package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-03-20-SSC1040snaw-init-table-migration.groovy') {


    changeSet(author: "bo.zhang05@hand-china.com", id: "2019-03-20-SSC_NODE_ASSIGN_WORKER") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'SSC_NODE_ASSIGN_WORKER_S', startValue:"10001")
        }

        createTable(tableName: "SSC_NODE_ASSIGN_WORKER", remarks: "共享工作流程节点分配工作人员") {

            column(name: "WORKER_ASSIGN_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "SSC_NODE_ASSIGN_WORKER_PK")} 
            column(name: "NODE_ID", type: "BIGINT", remarks: "工作流程节点ID")  {constraints(nullable:"false")}  
            column(name: "WORK_TEAM_ID", type: "BIGINT", remarks: "工作组ID")  {constraints(nullable:"false")}  
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "作业人员员工ID")   
            column(name: "DISPATCH_BASIS_TYPE", type: "VARCHAR(30)", remarks: "派工依据类型：SSC_DISPATCH_BASIS")  {constraints(nullable:"false")}  
            column(name: "BASIS_VALUE", type: "BIGINT", remarks: "依据值")  {constraints(nullable:"false")}  
            column(name: "MAX_DISPATCH_COUNT", type: "NUMBER", remarks: "单次最大取单量")  {constraints(nullable:"false")}  
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
            createIndex(tableName: "SSC_NODE_ASSIGN_WORKER", indexName: "SSC_NODE_ASSIGN_WORKER_N1") {
                    column(name: "NODE_ID")
                }
                createIndex(tableName: "SSC_NODE_ASSIGN_WORKER", indexName: "SSC_NODE_ASSIGN_WORKER_N2") {
                    column(name: "WORK_TEAM_ID")
                    column(name: "EMPLOYEE_ID")
                }
        
    }


}