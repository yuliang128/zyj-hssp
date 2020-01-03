package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-02-19-fnd-init-table-migration.groovy') {


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-19-FND_DOC_INFO") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'FND_DOC_INFO_S', startValue:"10001")
        }

        createTable(tableName: "FND_DOC_INFO", remarks: "单据信息表") {

            column(name: "DOC_INFO_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "FND_DOC_INFO_PK")} 
            column(name: "DOC_CATEGORY", type: "VARCHAR(30)", remarks: "单据类别")  {constraints(nullable:"false")}  
            column(name: "PARENT_DOC_INFO_ID", type: "BIGINT", remarks: "父单据信息ID")   
            column(name: "TABLE_LEVEL_CODE", type: "VARCHAR(30)", remarks: "表级别")   
            column(name: "TABLE_NAME", type: "VARCHAR(30)", remarks: "表名")  {constraints(nullable:"false")}  
            column(name: "TABLE_PARAM_NAME", type: "VARCHAR(200)", remarks: "参数名称")  {constraints(nullable:"false")}  
            column(name: "ID_FIELD_NAME", type: "VARCHAR(30)", remarks: "ID字段名称")  {constraints(nullable:"false")}  
            column(name: "NUMBER_FIELD_NAME", type: "VARCHAR(30)", remarks: "编码字段名称")   
            column(name: "TYPE_FIELD_NAME", type: "VARCHAR(30)", remarks: "类型字段名称")   
            column(name: "AMOUNT_FIELD_NAME", type: "VARCHAR(30)", remarks: "金额字段名称")   
            column(name: "SQL_CONTENT", type: "VARCHAR(4000)", remarks: "SQL内容")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")   
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")   
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "FND_DOC_INFO", indexName: "FND_DOC_INFO_N1") {
                    column(name: "DOC_CATEGORY")
                }
                createIndex(tableName: "FND_DOC_INFO", indexName: "FND_DOC_INFO_N2") {
                    column(name: "PARENT_DOC_INFO_ID")
                }
        
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-19-FND_BUSINESS_RULE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'FND_BUSINESS_RULE_S', startValue:"10001")
        }

        createTable(tableName: "FND_BUSINESS_RULE", remarks: "权限规则表") {

            column(name: "BUSINESS_RULE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "FND_BUSINESS_RULE_PK")} 
            column(name: "BUSINESS_RULE_CODE", type: "VARCHAR(30)", remarks: "权限规则代码")  {constraints(nullable:"false")}  
            column(name: "BUSINESS_RULE_NAME", type: "VARCHAR(500)", remarks: "权限规则名称ID")
            column(name: "DOC_CATEGORY", type: "VARCHAR(30)", remarks: "单据类别")  {constraints(nullable:"false")}  
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")   
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")   
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"BUSINESS_RULE_CODE",tableName:"FND_BUSINESS_RULE",constraintName: "FND_BUSINESS_RULE_U1")
    }

    changeSet(author:"hao.zhou@hand-china.com", id: "2019-02-19-FND_BUSINESS_RULE_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'FND_BUSINESS_RULE_TL_S', startValue: "10001")
        }

        createTable(tableName: "FND_BUSINESS_RULE_TL", remarks: "权限规则表多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "BUSINESS_RULE_ID", type: "BIGINT",  remarks: "主键")  {constraints(nullable: "false", primaryKey: "true")} 
            column(name: "BUSINESS_RULE_NAME", type: "VARCHAR(500)",  remarks: "权限规则名称ID")
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

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-19-FND_BUSINESS_RULE_DETAIL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'FND_BUSINESS_RULE_DETAIL_S', startValue:"10001")
        }

        createTable(tableName: "FND_BUSINESS_RULE_DETAIL", remarks: "权限规则明细表") {

            column(name: "DETAIL_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "FND_BUSINESS_RULE_DETAIL_PK")} 
            column(name: "BUSINESS_RULE_ID", type: "BIGINT", remarks: "权限规则ID")  {constraints(nullable:"false")}  
            column(name: "SEQUENCE", type: "BIGINT", remarks: "序号")  {constraints(nullable:"false")}  
            column(name: "PARAMETER_ID", type: "BIGINT", remarks: "参数ID")  {constraints(nullable:"false")}  
            column(name: "AND_OR", type: "VARCHAR(30)", remarks: "AND/OR")   
            column(name: "LEFT_PARENTHESIS", type: "VARCHAR(30)", remarks: "左括号")   
            column(name: "RIGHT_PARENTHESIS", type: "VARCHAR(30)", remarks: "右括号")   
            column(name: "CONDITION_TYPE", type: "VARCHAR(30)", remarks: "条件类型")  {constraints(nullable:"false")}  
            column(name: "CONDITION_VALUE", type: "VARCHAR(2000)", remarks: "条件值")  {constraints(nullable:"false")}  
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")   
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")   
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"SEQUENCE,BUSINESS_RULE_ID",tableName:"FND_BUSINESS_RULE_DETAIL",constraintName: "FND_BUSINESS_RULE_DETAIL_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-19-FND_BUSINESS_RULE_PARAMETER") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'FND_BUSINESS_RULE_PARAMETER_S', startValue:"10001")
        }

        createTable(tableName: "FND_BUSINESS_RULE_PARAMETER", remarks: "权限规则参数表") {

            column(name: "PARAMETER_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "FND_BUSINESS_RULE_PARAMETER_PK")} 
            column(name: "PARAMETER_CODE", type: "VARCHAR(30)", remarks: "参数代码")  {constraints(nullable:"false")}  
            column(name: "PARAMETER_NAME", type: "VARCHAR(500)", remarks: "参数名称ID")   
            column(name: "DOC_CATEGORY", type: "VARCHAR(30)", remarks: "单据类别")  {constraints(nullable:"false")}  
            column(name: "SQL_CONTENT", type: "VARCHAR(2000)", remarks: "SQL内容")  {constraints(nullable:"false")}  
            column(name: "DATA_TYPE", type: "VARCHAR(30)", remarks: "数据类型")  {constraints(nullable:"false")}  
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")   
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")   
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"PARAMETER_CODE",tableName:"FND_BUSINESS_RULE_PARAMETER",constraintName: "FND_BUSINESS_RULE_PARAMETER_U1")
    }

    changeSet(author:"hao.zhou@hand-china.com", id: "2019-02-19-FND_BUSINESS_RULE_PARAMETER_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'FND_BUSINESS_RULE_PARAMETER_TL_S', startValue: "10001")
        }

        createTable(tableName: "FND_BUSINESS_RULE_PARAMETER_TL", remarks: "权限规则参数表多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "PARAMETER_ID", type: "BIGINT",  remarks: "主键")  {constraints(nullable: "false", primaryKey: "true")} 
            column(name: "PARAMETER_NAME", type: "VARCHAR(500)",  remarks: "参数名称ID")   
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