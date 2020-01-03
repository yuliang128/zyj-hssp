package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-02-27-GL3001-GL3002-init-table-migration.groovy') {


    changeSet(author: "hui.zhao01@hand-china.com", id: "2019-02-27-GLD_SAP_POSTING_KEY") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_SAP_POSTING_KEY_S', startValue:"10001")
        }

        createTable(tableName: "GLD_SAP_POSTING_KEY", remarks: "SAP记账码设置") {

            column(name: "POSTING_KEY_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "表主键，供其他表做外键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "GLD_SAP_POSTING_KEY_PK")} 
            column(name: "ACCOUNT_SET_ID", type: "BIGINT", remarks: "科目表")  {constraints(nullable:"false")}  
            column(name: "RECONCILIATION_TYPE", type: "VARCHAR(30)", remarks: "统驭类型[是/否]")   
            column(name: "SPECIAL_GL_TYPE", type: "VARCHAR(30)", remarks: "特别总账类型[是/否]")   
            column(name: "PAYEE_CATEGORY", type: "VARCHAR(30)", remarks: "收款方类型")   
            column(name: "DR_CR_TYPE", type: "VARCHAR(30)", remarks: "借贷方[DR 借方/CR 贷方]")  {constraints(nullable:"false")}  
            column(name: "POSTING_KEY", type: "VARCHAR(30)", remarks: "记账码")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "SET_OF_BOOKS_ID", type: "BIGINT", remarks: "账套")   
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算公司")   
            column(name: "RE_POSTING_KEY", type: "VARCHAR(30)", remarks: "对方记账码")   
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"RECONCILIATION_TYPE,SPECIAL_GL_TYPE,DR_CR_TYPE,SET_OF_BOOKS_ID,ACC_ENTITY_ID,ACCOUNT_SET_ID,PAYEE_CATEGORY",tableName:"GLD_SAP_POSTING_KEY",constraintName: "GLD_SAP_POSTING_KEY_U1")
    }


    changeSet(author: "hui.zhao01@hand-china.com", id: "2019-02-27-GLD_SAP_RECON_ACCOUNT") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_SAP_RECON_ACCOUNT_S', startValue:"10001")
        }

        createTable(tableName: "GLD_SAP_RECON_ACCOUNT", remarks: "SAP统驭科目定义") {

            column(name: "SC_GLD_SAP_RECON_ACCOUNTS_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "GLD_SAP_RECON_ACCOUNT_PK")} 
            column(name: "SET_OF_BOOKS_ID", type: "BIGINT", remarks: "账套")   
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算公司")   
            column(name: "ACCOUNT_ID", type: "BIGINT", remarks: "科目")   
            column(name: "COMMAND_FLAG", type: "VARCHAR(100)", remarks: "SAP统驭标识")   
            column(name: "SGL_FLAG", type: "VARCHAR(100)", remarks: "是否特别总账")   
            column(name: "SGL", type: "VARCHAR(100)", remarks: "特别总账标识")   
            column(name: "ENABLED_FLAG", type: "VARCHAR(10)", remarks: "启用")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "ACCOUNT_SET_ID", type: "BIGINT", remarks: "科目表")   
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"ACCOUNT_ID,SET_OF_BOOKS_ID,ACC_ENTITY_ID",tableName:"GLD_SAP_RECON_ACCOUNT",constraintName: "GLD_SAP_RECON_ACCOUNT_U1")
    }


}