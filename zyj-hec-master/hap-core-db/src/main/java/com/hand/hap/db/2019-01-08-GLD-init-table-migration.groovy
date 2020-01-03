package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-01-08-GLD-init-table-migration.groovy') {


    changeSet(author: "yang.duan@hand-china.com", id: "2019-01-08-GLD_ACC_ENTITY_REF_ACCOUNT") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_ACC_ENTITY_REF_ACCOUNT_S', startValue:"10001")
        }

        createTable(tableName: "GLD_ACC_ENTITY_REF_ACCOUNT", remarks: "核算主体关联科目") {

            column(name: "REF_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "GLD_ACC_ENTITY_REF_ACCOUNT_PK")} 
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体ID")  {constraints(nullable:"false")}  
            column(name: "SET_OF_BOOKS_ID", type: "BIGINT", remarks: "帐套ID")  {constraints(nullable:"false")}  
            column(name: "ACCOUNT_ID", type: "BIGINT", remarks: "科目ID")  {constraints(nullable:"false")}  
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
        
        addUniqueConstraint(columnNames:"SET_OF_BOOKS_ID,ACCOUNT_ID,ACC_ENTITY_ID",tableName:"GLD_ACC_ENTITY_REF_ACCOUNT",constraintName: "GLD_ACC_ENTITY_REF_ACCOUNT_U1")
    }


    changeSet(author: "yang.duan@hand-china.com", id: "2019-01-08-GLD_ACC_ENTITY_REF_BE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_ACC_ENTITY_REF_BE_S', startValue:"10001")
        }

        createTable(tableName: "GLD_ACC_ENTITY_REF_BE", remarks: "核算主体关联预算实体表") {

            column(name: "REF_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "GLD_ACC_ENTITY_REF_BE_PK")} 
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体ID")  {constraints(nullable:"false")}  
            column(name: "BGT_ENTITY_ID", type: "BIGINT", remarks: "预算实体ID")  {constraints(nullable:"false")}  
            column(name: "DEFAULT_FLAG", type: "VARCHAR(1)", remarks: "默认标志")   
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"BGT_ENTITY_ID,ACC_ENTITY_ID",tableName:"GLD_ACC_ENTITY_REF_BE",constraintName: "GLD_ACC_ENTITY_REF_BE_U1")
    }


    changeSet(author: "yang.duan@hand-china.com", id: "2019-01-08-GLD_ACC_ENTITY_REF_SOB") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_ACC_ENTITY_REF_SOB_S', startValue:"10001")
        }

        createTable(tableName: "GLD_ACC_ENTITY_REF_SOB", remarks: "核算主体分配帐套") {

            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "REF_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "GLD_ACC_ENTITY_REF_SOB_PK")} 
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体ID")  {constraints(nullable:"false")}  
            column(name: "SET_OF_BOOKS_ID", type: "BIGINT", remarks: "帐套ID")  {constraints(nullable:"false")}  
            column(name: "DEFAULT_FLAG", type: "VARCHAR(1)", remarks: "默认标志")  {constraints(nullable:"false")}  
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"SET_OF_BOOKS_ID,ACC_ENTITY_ID",tableName:"GLD_ACC_ENTITY_REF_SOB",constraintName: "GLD_ACC_ENTITY_REF_SOB_U1")
    }

    changeSet(author: "yang.duan@hand-china.com", id: "2019-01-08-GLD_RESP_CENTER_REF_BC") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_RESP_CENTER_REF_BC_S', startValue:"10001")
        }

        createTable(tableName: "GLD_RESP_CENTER_REF_BC", remarks: "核算主体级成本中心关联预算中心表") {

            column(name: "REF_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "GLD_RESP_CENTER_REF_BC_PK")} 
            column(name: "RESP_CENTER_ID", type: "BIGINT", remarks: "成本中心ID")  {constraints(nullable:"false")}  
            column(name: "BGT_ENTITY_ID", type: "BIGINT", remarks: "预算实体ID")  {constraints(nullable:"false")}  
            column(name: "BGT_CENTER_ID", type: "BIGINT", remarks: "预算中心ID")  {constraints(nullable:"false")}  
            column(name: "DEFAULT_FLAG", type: "VARCHAR(1)", remarks: "默认标志")   
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"BGT_ENTITY_ID,BGT_CENTER_ID,RESP_CENTER_ID",tableName:"GLD_RESP_CENTER_REF_BC",constraintName: "GLD_RESP_CENTER_REF_BC_U1")
    }
}