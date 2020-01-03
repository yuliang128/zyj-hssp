package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-03-05-CSH1090cofl-init-table-migration.groovy') {


    changeSet(author: "bo.zhang05@hand-china.com", id: "2019-03-05-CSH_OFFER_FORMAT_LNS") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_OFFER_FORMAT_LNS_S', startValue:"10001")
        }

        createTable(tableName: "CSH_OFFER_FORMAT_LNS", remarks: "报盘文件导出格式定义行表") {

            column(name: "FORMAT_LNS_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其它表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_OFFER_FORMAT_LNS_PK")} 
            column(name: "FORMAT_HDS_ID", type: "BIGINT", remarks: "报盘文件导出格式定义头ID")  {constraints(nullable:"false")}  
            column(name: "LINE_NUMBER", type: "BIGINT", remarks: "行号")  {constraints(nullable:"false")}  
            column(name: "COLUMN_DESC", type: "VARCHAR(1000)", remarks: "字段名称")   
            column(name: "COLUMN_NAME", type: "VARCHAR(200)", remarks: "字段代码")  {constraints(nullable:"false")}  
            column(name: "COLUMN_SQL_TEXT", type: "VARCHAR(4000)", remarks: "字段格式值")   
            column(name: "COLUMN_FORMAT", type: "VARCHAR(100)", remarks: "格式（SYSCODE：CSH_OFFER_COLUMN_FORMAT）")   
            column(name: "GROUP_FLAG", type: "VARCHAR(1)", remarks: "分组标志")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OFFER_COLUMN_NAME", type: "VARCHAR(300)", remarks: "报盘列名")   
            column(name: "OFFER_VALUE_TYPE", type: "VARCHAR(30)", remarks: "取值类型（SYSCODE：CSH_OFFER_VALUE_TYPE）")   
            column(name: "SPLICE_TYPE", type: "VARCHAR(30)", remarks: "拼接位置(PREFIX 附加前缀/SUFFIX 附加后缀 SYSCODE：CSH_OFFER_SPLICE_TYPE)")   
            column(name: "SPLICE_VALUE", type: "VARCHAR(300)", remarks: "拼接值")   
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"COLUMN_NAME,FORMAT_HDS_ID",tableName:"CSH_OFFER_FORMAT_LNS",constraintName: "CSH_OFFER_FORMAT_LNS_U1")
    }


}