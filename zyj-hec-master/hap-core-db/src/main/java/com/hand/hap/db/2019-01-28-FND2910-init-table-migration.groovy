package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-01-28-FND2910-init-table-migration.groovy') {


    changeSet(author: "hui.zhao01@hand-china.com", id: "2019-01-28-GLD_ACC_GEN_SYS_GENERAL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_ACC_GEN_SYS_GENERAL_S', startValue:"10001")
        }

        createTable(tableName: "GLD_ACC_GEN_SYS_GENERAL", remarks: "用途代码配置表") {

            column(name: "GENERAL_ID", type: "BIGINT", remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "GLD_ACC_GEN_SYS_GENERAL_PK")}
            column(name: "USAGE_CODE", type: "VARCHAR(30)", remarks: "用途代码")  {constraints(nullable:"false")}  
            column(name: "ACCOUNT_ID", type: "BIGINT", remarks: "科目ID")  {constraints(nullable:"false")}
            column(name: "MAG_ORG_ID", type: "BIGINT", remarks: "管理组织ID")  {constraints(nullable:"false")}
            column(name: "SET_OF_BOOKS_ID", type: "BIGINT", remarks: "账套ID")  {constraints(nullable:"false")}
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"SET_OF_BOOKS_ID,MAG_ORG_ID,USAGE_CODE",tableName:"GLD_ACC_GEN_SYS_GENERAL",constraintName: "GLD_ACC_GEN_SYS_GENERAL_U1")
    }


}