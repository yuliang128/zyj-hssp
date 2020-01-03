package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-01-09-fnd-init-table-migration.groovy') {


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-09-FND_DIMENSION") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'FND_DIMENSION_S', startValue:"10001")
        }

        createTable(tableName: "FND_DIMENSION", remarks: "维度定义") {

            column(name: "DIMENSION_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "FND_DIMENSION_PK")} 
            column(name: "DIMENSION_SEQUENCE", type: "BIGINT", remarks: "序号")  {constraints(nullable:"false")}  
            column(name: "DIMENSION_CODE", type: "VARCHAR(30)", remarks: "维度代码")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "维度描述ID")   
            column(name: "SYSTEM_FLAG", type: "VARCHAR(1)", remarks: "系统标志")  {constraints(nullable:"false")}  
            column(name: "SYSTEM_LEVEL", type: "VARCHAR(30)", remarks: "系统级类型（SYSCODE：SYS_ORGANIZATION_TYPE）")   
            column(name: "COMPANY_LEVEL", type: "VARCHAR(30)", remarks: "公司级类型（SYSCODE：SYS_ORGANIZATION_TYPE）")   
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用不标志")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            
        addUniqueConstraint(columnNames:"DIMENSION_SEQUENCE",tableName:"FND_DIMENSION",constraintName: "FND_DIMENSIONS_U1")
        addUniqueConstraint(columnNames:"DIMENSION_CODE",tableName:"FND_DIMENSION",constraintName: "FND_DIMENSIONS_U2")
    }

    changeSet(author:"hao.zhou@hand-china.com", id: "2019-01-09-FND_DIMENSION_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'FND_DIMENSION_TL_S', startValue: "10001")
        }

        createTable(tableName: "FND_DIMENSION_TL", remarks: "维度定义多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "DIMENSION_ID", type: "BIGINT",  remarks: "主键，供其他表外键使用")  {constraints(nullable: "false", primaryKey: "true")} 
            column(name: "DESCRIPTION", type: "VARCHAR(500)",  remarks: "维度描述ID")   
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

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-09-FND_DIMENSION_VALUE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'FND_DIMENSION_VALUE_S', startValue:"10001")
        }

        createTable(tableName: "FND_DIMENSION_VALUE", remarks: "系统级维值定义") {

            column(name: "DIMENSION_VALUE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "FND_DIMENSION_VALUE_PK")} 
            column(name: "DIMENSION_ID", type: "BIGINT", remarks: "维度定义ID")  {constraints(nullable:"false")}  
            column(name: "DIMENSION_VALUE_CODE", type: "VARCHAR(30)", remarks: "维值代码")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "维值描述ID")   
            column(name: "SUMMARY_FLAG", type: "VARCHAR(1)", remarks: "汇总标志")  {constraints(nullable:"false")}  
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
            
        addUniqueConstraint(columnNames:"DIMENSION_VALUE_ID,DIMENSION_ID",tableName:"FND_DIMENSION_VALUE",constraintName: "FND_DIMENSION_VALUES_U1")
        addUniqueConstraint(columnNames:"DIMENSION_VALUE_CODE,DIMENSION_ID",tableName:"FND_DIMENSION_VALUE",constraintName: "FND_DIMENSION_VALUES_U2")
    }

    changeSet(author:"hao.zhou@hand-china.com", id: "2019-01-09-FND_DIMENSION_VALUE_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'FND_DIMENSION_VALUE_TL_S', startValue: "10001")
        }

        createTable(tableName: "FND_DIMENSION_VALUE_TL", remarks: "系统级维值定义多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "DIMENSION_VALUE_ID", type: "BIGINT",  remarks: "主键，供其他表外键使用")  {constraints(nullable: "false", primaryKey: "true")} 
            column(name: "DESCRIPTION", type: "VARCHAR(500)",  remarks: "维值描述ID")   
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

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-09-FND_DIM_VALUE_HIERARCHY") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'FND_DIM_VALUE_HIERARCHY_S', startValue:"10001")
        }

        createTable(tableName: "FND_DIM_VALUE_HIERARCHY", remarks: "维值层次定义") {

            column(name: "HIERARCHY_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "FND_DIM_VALUE_HIERARCHY_PK")} 
            column(name: "PARENT_DIM_VALUE_ID", type: "BIGINT", remarks: "父维值定义ID")  {constraints(nullable:"false")}  
            column(name: "DIMENSION_VALUE_ID", type: "BIGINT", remarks: "子维值定义ID")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"PARENT_DIM_VALUE_ID,DIMENSION_VALUE_ID",tableName:"FND_DIM_VALUE_HIERARCHY",constraintName: "FND_DIM_VALUE_HIERARCHY_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-09-FND_COMPANY_DIMENSION_VALUE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'FND_COMPANY_DIMENSION_VALUE_S', startValue:"10001")
        }

        createTable(tableName: "FND_COMPANY_DIMENSION_VALUE", remarks: "公司级维值定义") {

            column(name: "COMPANY_DIM_VALUE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "FND_COMPANY_DIMENSION_VALUE_PK")} 
            column(name: "COMPANY_LEVEL", type: "VARCHAR(30)", remarks: "公司级类型（SYSCODE：SYS_ORGANIZATION_TYPE）")  {constraints(nullable:"false")}  
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "值ID（管理公司ID/预算实体ID/核算主体ID）")  {constraints(nullable:"false")}  
            column(name: "DIMENSION_VALUE_ID", type: "BIGINT", remarks: "系统级维值定义ID")  {constraints(nullable:"false")}  
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
        
        addUniqueConstraint(columnNames:"DIMENSION_VALUE_ID,COMPANY_LEVEL,COMPANY_ID",tableName:"FND_COMPANY_DIMENSION_VALUE",constraintName: "FND_COMPANY_DIM_VALUES_U1")
    }


}