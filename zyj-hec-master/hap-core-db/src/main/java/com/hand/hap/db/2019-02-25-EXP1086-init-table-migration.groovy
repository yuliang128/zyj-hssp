package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-02-25-EXP1086-init-table-migration.groovy') {


    changeSet(author: "jiangxz", id: "2019-02-25-EXP_MO_EXP_OBJ_TP_ASGN_COM") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_MO_EXP_OBJ_TP_ASGN_COM_S', startValue:"10001")
        }

        createTable(tableName: "EXP_MO_EXP_OBJ_TP_ASGN_COM", remarks: "管理组织级费用对象类型分配公司") {

            column(name: "ASSIGN_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "pk")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_MO_EXP_OBJ_TP_ASGN_COM_PK")} 
            column(name: "MO_EXP_OBJ_TYPE_ID", type: "BIGINT", remarks: "管理组织级费用对象类型ID")  {constraints(nullable:"false")}  
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司ID")  {constraints(nullable:"false")}  
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
        
        addUniqueConstraint(columnNames:"COMPANY_ID,MO_EXP_OBJ_TYPE_ID",tableName:"EXP_MO_EXP_OBJ_TP_ASGN_COM",constraintName: "EXP_MO_EXP_OBJ_TP_ASGN_COM_U1")
    }


    changeSet(author: "jiangxz", id: "2019-02-25-EXP_MO_EXPENSE_OBJECT_VALUE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_MO_EXPENSE_OBJECT_VALUE_S', startValue:"10001")
        }

        createTable(tableName: "EXP_MO_EXPENSE_OBJECT_VALUE", remarks: "管理组织级费用对象值定义，取代EXP_SOB_EXPENSE_OBJECT_VALUE") {

            column(name: "MO_EXPENSE_OBJECT_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "管理组织级费用对象ID，PK")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_MO_EXPENSE_OBJECT_VALUE_PK")}
            column(name: "MO_EXP_OBJ_TYPE_ID", type: "BIGINT", remarks: "管理组织级费用对象类型ID")  {constraints(nullable:"false")}  
            column(name: "MO_EXPENSE_OBJECT_CODE", type: "VARCHAR(30)", remarks: "管理组织级费用对象代码")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述ID")  {constraints(nullable:"false")}  
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
        
        addUniqueConstraint(columnNames:"MO_EXP_OBJ_TYPE_ID,MO_EXPENSE_OBJECT_CODE",tableName:"EXP_MO_EXPENSE_OBJECT_VALUE",constraintName: "EXP_MO_EXPENSE_OBJECT_VALUE_U1")
    }

    changeSet(author:"jiangxz", id: "2019-02-25-EXP_MO_EXPENSE_OBJECT_VALUE_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_MO_EXPENSE_OBJECT_VALUE_TL_S', startValue: "10001")
        }

        createTable(tableName: "EXP_MO_EXPENSE_OBJECT_VALUE_TL", remarks: "管理组织级费用对象值定义") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "MO_EXPENSE_OBJECT_ID", type: "BIGINT",  remarks: "管理组织级费用对象ID，PK")  {constraints(nullable: "false", primaryKey: "true")} 
            column(name: "DESCRIPTION", type: "VARCHAR(500)",  remarks: "描述ID")   
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

    changeSet(author: "jiangxz", id: "2019-02-25-EXP_MO_EXPENSE_OBJECT_TYPE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_MO_EXPENSE_OBJECT_TYPE_S', startValue:"10001")
        }

        createTable(tableName: "EXP_MO_EXPENSE_OBJECT_TYPE", remarks: "管理组织级费用对象类型定义") {

            column(name: "MO_EXP_OBJ_TYPE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "管理组织级费用对象类型ID")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_MO_EXPENSE_OBJECT_TYPE_PK")}
            column(name: "MAG_ORG_ID", type: "BIGINT", remarks: "管理组织ID")  {constraints(nullable:"false")}  
            column(name: "MO_EXP_OBJ_TYPE_CODE", type: "VARCHAR(30)", remarks: "管理组织级费用对象类型代码")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述ID")  {constraints(nullable:"false")}  
            column(name: "SYSTEM_FLAG", type: "VARCHAR(1)", remarks: "系统级标志")  {constraints(nullable:"false")}  
            column(name: "EXPENSE_OBJECT_METHOD", type: "VARCHAR(30)", remarks: "费用对象取值类型")  {constraints(nullable:"false")}  
            column(name: "SQL_QUERY", type: "VARCHAR(2000)", remarks: "查询SQL")   
            column(name: "SQL_VALIDATE", type: "VARCHAR(2000)", remarks: "验证SQL")   
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
        
        addUniqueConstraint(columnNames:"MO_EXP_OBJ_TYPE_CODE,MAG_ORG_ID",tableName:"EXP_MO_EXPENSE_OBJECT_TYPE",constraintName: "EXP_MO_EXPENSE_OBJECT_TYPE_U1")
    }

    changeSet(author:"jiangxz", id: "2019-02-25-EXP_MO_EXPENSE_OBJECT_TYPE_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_MO_EXPENSE_OBJECT_TYPES_TL_S', startValue: "10001")
        }

        createTable(tableName: "EXP_MO_EXPENSE_OBJECT_TYPE_TL", remarks: "管理组织级费用对象类型定义") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "MO_EXP_OBJ_TYPE_ID", type: "BIGINT",  remarks: "管理组织级费用对象类型ID")  {constraints(nullable: "false", primaryKey: "true")} 
            column(name: "DESCRIPTION", type: "VARCHAR(500)",  remarks: "描述ID")   
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