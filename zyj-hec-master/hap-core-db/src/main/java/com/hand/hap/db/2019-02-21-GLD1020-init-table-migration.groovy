package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-02-21-GLD1020-init-table-migration.groovy') {


    changeSet(author: "rui.shi@hand-china.com", id: "2019-02-21-GLD_SOB_RULE_GROUP") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_SOB_RULE_GROUP_S', startValue:"10001")
        }

        createTable(tableName: "GLD_SOB_RULE_GROUP", remarks: "帐套级会计规则组") {

            column(name: "RULE_GROUP_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "GLD_SOB_RULE_GROUP_PK")} 
            column(name: "SET_OF_BOOK_ID", type: "BIGINT", remarks: "账套ID")  {constraints(nullable:"false")}  
            column(name: "RULE_GROUP_CODE", type: "VARCHAR(30)", remarks: "账套级会计规则组代码")  {constraints(nullable:"false")}  
            column(name: "RULE_GROUP_NAME", type: "VARCHAR(500)", remarks: "账套级会计规则组名称")
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
                createIndex(tableName: "GLD_SOB_RULE_GROUP", indexName: "GLD_SOB_RULE_GROUP_N1") {
                    column(name: "RULE_GROUP_NAME")
                }
        
        addUniqueConstraint(columnNames:"RULE_GROUP_CODE",tableName:"GLD_SOB_RULE_GROUP",constraintName: "GLD_SOB_RULE_GROUP_U1")
    }

    changeSet(author:"rui.shi@hand-china.com", id: "2019-02-21-GLD_SOB_RULE_GROUP_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_SOB_RULE_GROUP_TL_S', startValue: "10001")
        }

        createTable(tableName: "GLD_SOB_RULE_GROUP_TL", remarks: "帐套级会计规则组多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "RULE_GROUP_ID", type: "BIGINT",  remarks: "主键，供其他表外键使用")  {constraints(nullable: "false", primaryKey: "true")} 
            column(name: "RULE_GROUP_NAME", type: "VARCHAR(500)",  remarks: "账套级会计规则组名称")
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