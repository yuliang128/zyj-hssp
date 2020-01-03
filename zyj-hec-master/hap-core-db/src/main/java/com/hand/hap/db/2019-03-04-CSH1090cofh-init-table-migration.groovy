package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-03-04-CSH1090cofh-init-table-migration.groovy') {


    changeSet(author: "bo.zhang05@hand-china.com", id: "2019-03-04-CSH_OFFER_FORMAT_HDS") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_OFFER_FORMAT_HDS_S', startValue:"10001")
        }

        createTable(tableName: "CSH_OFFER_FORMAT_HDS", remarks: "报盘文件导出格式定义头表") {

            column(name: "FORMAT_HDS_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_OFFER_FORMAT_HDS_PK")} 
            column(name: "FORMAT_CODE", type: "VARCHAR(30)", remarks: "报盘格式代码")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "报盘格式名称ID")   
            column(name: "EXPORT_TYPE_CODE", type: "VARCHAR(30)", remarks: "导出格式（SYSCODE：CSH_OFFER_EXPORT_TYPE）")  {constraints(nullable:"false")}  
            column(name: "EXPORT_SEPARATOR_CODE", type: "VARCHAR(30)", remarks: "分隔符（SYSCODE：CSH_OFFER_EXPORT_SEPARATOR）")   
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
            createIndex(tableName: "CSH_OFFER_FORMAT_HDS", indexName: "CSH_OFFER_FORMAT_HDS_N1") {
                    column(name: "DESCRIPTION")
                }
            
        addUniqueConstraint(columnNames:"FORMAT_CODE",tableName:"CSH_OFFER_FORMAT_HDS",constraintName: "CSH_OFFER_FORMAT_HDS_U1")
    }

    changeSet(author:"bo.zhang05@hand-china.com", id: "2019-03-04-CSH_OFFER_FORMAT_HDS_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_OFFER_FORMAT_HDS_TL_S', startValue: "10001")
        }

        createTable(tableName: "CSH_OFFER_FORMAT_HDS_TL", remarks: "报盘文件导出格式定义头表多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "FORMAT_HDS_ID", type: "BIGINT",  remarks: "主键，供其他表外键使用")  {constraints(nullable: "false", primaryKey: "true")} 
            column(name: "DESCRIPTION", type: "VARCHAR(500)",  remarks: "报盘格式名称ID")   
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