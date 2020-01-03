package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-03-20-SSC1050-init-table-migration.groovy') {


    changeSet(author: "hui.lu@hand-china.com", id: "2019-03-20-SSC_DOCUMENT_WORKFLOW") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'SSC_DOCUMENT_WORKFLOW_S', startValue:"10001")
        }

        createTable(tableName: "SSC_DOCUMENT_WORKFLOW", remarks: "单据分配工作流程") {

            column(name: "DOC_WFL_ASSIGN_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "SSC_DOCUMENT_WORKFLOW_PK")} 
            column(name: "MAG_ORG_ID", type: "BIGINT", remarks: "管理组织ID")  {constraints(nullable:"false")}  
            column(name: "DOC_CATEGORY", type: "VARCHAR(30)", remarks: "单据类别")  {constraints(nullable:"false")}  
            column(name: "DOC_TYPE_ID", type: "BIGINT", remarks: "单据类型ID")   
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司ID")   
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体ID")   
            column(name: "WORKFLOW_ID", type: "BIGINT", remarks: "工作流程ID")  {constraints(nullable:"false")}  
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
            createIndex(tableName: "SSC_DOCUMENT_WORKFLOW", indexName: "SSC_DOCUMENT_WORKFLOW_N1") {
                    column(name: "MAG_ORG_ID")
                }
                createIndex(tableName: "SSC_DOCUMENT_WORKFLOW", indexName: "SSC_DOCUMENT_WORKFLOW_N2") {
                    column(name: "DOC_CATEGORY")
                    column(name: "DOC_TYPE_ID")
                }
                createIndex(tableName: "SSC_DOCUMENT_WORKFLOW", indexName: "SSC_DOCUMENT_WORKFLOW_N3") {
                    column(name: "COMPANY_ID")
                    column(name: "DOC_CATEGORY")
                }
                createIndex(tableName: "SSC_DOCUMENT_WORKFLOW", indexName: "SSC_DOCUMENT_WORKFLOW_N4") {
                    column(name: "DOC_CATEGORY")
                    column(name: "ACC_ENTITY_ID")
                }
        
    }
	
	changeSet(author: "hui.lu@hand-china.com", id: "2019-03-20-WFL_BUSINESS_RULE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'WFL_BUSINESS_RULE_S', startValue:"10001")
        }

        createTable(tableName: "WFL_BUSINESS_RULE", remarks: "权限规则定义表") {

            column(name: "BUSINESS_RULE_ID", type: "NUMBER", remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "WFL_BUSINESS_RULE_PK")} 
            column(name: "BUSINESS_RULE_CODE", type: "VARCHAR(30)", remarks: "权限规则编码")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "权限规则名称")   
            column(name: "WORKFLOW_CATEGORY", type: "VARCHAR(30)", remarks: "工作流类型")   
            column(name: "START_DATE_ACTIVE", type: "DATETIME", remarks: "有效日期从")  {constraints(nullable:"false")}  
            column(name: "END_DATE_ACTIVE", type: "DATETIME", remarks: "有效日期到")   
            column(name: "NODE_FLAG", type: "VARCHAR(1)", remarks: "工作流节点规则")  {constraints(nullable:"false")}  
            column(name: "WORKFLOW_FLAG", type: "VARCHAR(1)", remarks: "工作流指定")  {constraints(nullable:"false")}  
            column(name: "AUTHORIZATION_FLAG", type: "VARCHAR(1)", remarks: "审批授权")  {constraints(nullable:"false")}  
            column(name: "SSC_FIXED_NODE_FLAG", type: "VARCHAR(1)", remarks: "工作台节点规则")  {constraints(nullable:"false")}  
            column(name: "SSC_AUTHORIZATION_FLAG", type: "VARCHAR(1)", remarks: "派工规则")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "NUMBER", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "NUMBER", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"BUSINESS_RULE_CODE",tableName:"WFL_BUSINESS_RULE",constraintName: "WFL_BUSINESS_RULE_U1")
    }

    changeSet(author:"hui.lu@hand-china.com", id: "2019-03-20-WFL_BUSINESS_RULE_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'WFL_BUSINESS_RULE_TL_S', startValue: "10001")
        }

        createTable(tableName: "WFL_BUSINESS_RULE_TL", remarks: "权限规则定义表多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "BUSINESS_RULE_ID", type: "NUMBER",  remarks: "主键，供其他表外键使用")  {constraints(nullable: "false", primaryKey: "true")} 
            column(name: "DESCRIPTION", type: "VARCHAR(500)",  remarks: "权限规则名称")   
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