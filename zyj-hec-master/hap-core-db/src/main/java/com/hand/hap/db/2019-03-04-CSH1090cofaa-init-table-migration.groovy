package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-03-04-CSH1090cofaa-init-table-migration.groovy') {


    changeSet(author: "bo.zhang05@hand-china.com", id: "2019-03-04-CSH_OFFER_FORMAT_ASGN_AE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_OFFER_FORMAT_ASGN_AE_S', startValue:"10001")
        }

        createTable(tableName: "CSH_OFFER_FORMAT_ASGN_AE", remarks: "报盘文件导出格式定义分配核算主体") {

            column(name: "ASSIGN_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_OFFER_FORMAT_ASGN_AE_PK")} 
            column(name: "FORMAT_HDS_ID", type: "BIGINT", remarks: "报盘文件导出格式定义头ID")  {constraints(nullable:"false")}  
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体ID")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"FORMAT_HDS_ID,ACC_ENTITY_ID",tableName:"CSH_OFFER_FORMAT_ASGN_AE",constraintName: "CSH_OFFER_FORMAT_ASGN_AE_U1")
    }


}