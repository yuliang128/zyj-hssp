package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-04-23-FND1060-init-table-migration.groovy') {


    changeSet(author: "yuting.gui@hand-china.com", id: "2019-04-23-FND_UOM_TYPE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'FND_UOM_TYPE_S', startValue:"10001")
        }

        createTable(tableName: "FND_UOM_TYPE", remarks: "管理组织级计量单位类型") {

            column(name: "UOM_TYPE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "FND_UOM_TYPE_PK")} 
            column(name: "UOM_TYPE_CODE", type: "VARCHAR(30)", remarks: "计量单位类型代码")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "计量单位类型名称ID")   
            column(name: "META_UOM_ID", type: "BIGINT", remarks: "元单位")   
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
        
        addUniqueConstraint(columnNames:"UOM_TYPE_CODE",tableName:"FND_UOM_TYPE",constraintName: "FND_UOM_TYPE_U1")
    }

    changeSet(author:"yuting.gui@hand-china.com", id: "2019-04-23-FND_UOM_TYPE_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'FND_UOM_TYPE_TL_S', startValue: "10001")
        }

        createTable(tableName: "FND_UOM_TYPE_TL", remarks: "管理组织级计量单位类型多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "UOM_TYPE_ID", type: "BIGINT",  remarks: "主键，供其他表外键使用")  {constraints(nullable: "false", primaryKey: "true")} 
            column(name: "DESCRIPTION", type: "VARCHAR(500)",  remarks: "计量单位类型名称ID")   
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