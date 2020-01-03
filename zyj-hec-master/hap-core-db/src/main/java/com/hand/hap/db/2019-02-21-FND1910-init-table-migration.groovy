package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-02-21-FND1910-init-table-migration.groovy') {


    changeSet(author: "zhongyu.wang@hand-china.com", id: "2019-02-21-FND_CODING_RULE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'FND_CODING_RULE_S', startValue:"10001")
        }

        createTable(tableName: "FND_CODING_RULE", remarks: "编码规则") {

            column(name: "CODING_RULE_OBJECT_ID", type: "BIGINT", remarks: "编码规则对象ID")  {constraints(nullable:"false")}  
            column(name: "CODING_RULE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "编码规则ID")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "FND_CODING_RULE_PK")} 
            column(name: "CODING_RULE_CODE", type: "VARCHAR(30)", remarks: "编码规则代码")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述")  {constraints(nullable:"false")}  
            column(name: "NOTE", type: "VARCHAR(2000)", remarks: "备注")   
            column(name: "RESET_FREQUENCE", type: "VARCHAR(30)", remarks: "重置频率")  {constraints(nullable:"false")}  
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
            createIndex(tableName: "FND_CODING_RULE", indexName: "FND_CODING_RULES_N1") {
                    column(name: "CODING_RULE_OBJECT_ID")
                    column(name: "CODING_RULE_ID")
                }
            
        addUniqueConstraint(columnNames:"CODING_RULE_CODE",tableName:"FND_CODING_RULE",constraintName: "FND_CODING_RULES_U1")
    }

    changeSet(author:"zhongyu.wang@hand-china.com", id: "2019-02-21-FND_CODING_RULE_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'FND_CODING_RULE_TL_S', startValue: "10001")
        }

        createTable(tableName: "FND_CODING_RULE_TL", remarks: "编码规则多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "CODING_RULE_ID", type: "BIGINT",  remarks: "编码规则ID")  {constraints(nullable: "false", primaryKey: "true")} 
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
	
	 changeSet(author: "zhongyu.wang@hand-china.com", id: "2019-02-21-FND_CODING_RULE_OBJECT") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'FND_CODING_RULE_OBJECT_S', startValue:"10001")
        }

        createTable(tableName: "FND_CODING_RULE_OBJECT", remarks: "编码规则对象") {

            column(name: "CODING_RULE_OBJECT_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "编码规则对象ID")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "FND_CODING_RULE_OBJECT_PK")} 
            column(name: "DOCUMENT_CATEGORY", type: "VARCHAR(30)", remarks: "单据类别")  {constraints(nullable:"false")}  
            column(name: "PRIORITY", type: "BIGINT", remarks: "优先级")  {constraints(nullable:"false")}  
            column(name: "MAG_ORG_ID", type: "BIGINT", remarks: "管理组织ID")   
            column(name: "DOCUMENT_TYPE", type: "VARCHAR(30)", remarks: "单据类型")   
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "管理公司ID")   
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体ID")
            column(name: "CODE_RULE_HEADER_ID", type: "BIGINT", remarks: "HAP编码规则头ID")
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
            
        addUniqueConstraint(columnNames:"MAG_ORG_ID,ACC_ENTITY_ID,COMPANY_ID,DOCUMENT_CATEGORY,DOCUMENT_TYPE",tableName:"FND_CODING_RULE_OBJECT",constraintName: "FND_CODING_RULE_OBJECTS_U1")
        addUniqueConstraint(columnNames:"MAG_ORG_ID,PRIORITY,DOCUMENT_CATEGORY",tableName:"FND_CODING_RULE_OBJECT",constraintName: "FND_CODING_RULE_OBJECTS_U2")
    }
	
	changeSet(author: "zhongyu.wang@hand-china.com", id: "2019-02-21-FND_CODING_RULE_DETAIL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'FND_CODING_RULE_DETAIL_S', startValue:"10001")
        }

        createTable(tableName: "FND_CODING_RULE_DETAIL", remarks: "编码规则明细") {

            column(name: "CODING_RULE_LINE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "编码规则行ID")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "FND_CODING_RULE_DETAIL_PK")} 
            column(name: "CODING_RULE_ID", type: "BIGINT", remarks: "编码规则ID")  {constraints(nullable:"false")}  
            column(name: "SEQUENCE", type: "BIGINT", remarks: "顺序号")  {constraints(nullable:"false")}  
            column(name: "SEGMENT_TYPE", type: "VARCHAR(30)", remarks: "段")  {constraints(nullable:"false")}  
            column(name: "SEGMENT_VALUE", type: "VARCHAR(30)", remarks: "段值")   
            column(name: "DATE_FORMAT", type: "VARCHAR(30)", remarks: "日期格式")   
            column(name: "LENGTH", type: "BIGINT", remarks: "位数")   
            column(name: "INCREMENTAL", type: "BIGINT", remarks: "步长")   
            column(name: "START_VALUE", type: "BIGINT", remarks: "开始值")   
            column(name: "DISPLAY_FLAG", type: "VARCHAR(1)", remarks: "")   
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"SEQUENCE,CODING_RULE_ID",tableName:"FND_CODING_RULE_DETAIL",constraintName: "FND_CODING_RULE_DETAILS_U1")
    }
	
	
    changeSet(author: "zhongyu.wang@hand-china.com", id: "2019-02-21-FND_OPERATION_UNIT") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'FND_OPERATION_UNIT_S', startValue:"10001")
        }

        createTable(tableName: "FND_OPERATION_UNIT", remarks: "经营单位") {

            column(name: "OPERATION_UNIT_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "经营单位ID")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "FND_OPERATION_UNIT_PK")} 
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司ID")  {constraints(nullable:"false")}  
            column(name: "OPERATION_UNIT_CODE", type: "VARCHAR(30)", remarks: "经营单位代码")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述")  {constraints(nullable:"false")}  
            column(name: "CHIEF_POSITION_ID", type: "BIGINT", remarks: "主岗位id")   
            column(name: "SUMMARY_FLAG", type: "VARCHAR(1)", remarks: "汇总")  {constraints(nullable:"false")}  
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"COMPANY_ID,OPERATION_UNIT_CODE",tableName:"FND_OPERATION_UNIT",constraintName: "FND_OPERATION_UNITS_U1")
    }

    changeSet(author:"zhongyu.wang@hand-china.com", id: "2019-02-21-FND_OPERATION_UNIT_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'FND_OPERATION_UNIT_TL_S', startValue: "10001")
        }

        createTable(tableName: "FND_OPERATION_UNIT_TL", remarks: "经营单位多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "OPERATION_UNIT_ID", type: "BIGINT",  remarks: "经营单位ID")  {constraints(nullable: "false", primaryKey: "true")} 
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