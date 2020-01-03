package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-01-03-FND1080-init-table-migration.groovy') {


    changeSet(author: "jialin.xing@hand-china.com", id: "2019-01-08-GLD_EXCHANGERATE_TYPE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_EXCHANGERATE_TYPE_S', startValue:"10001")
        }

        createTable(tableName: "GLD_EXCHANGERATE_TYPE", remarks: "汇率类型表") {

            column(name: "TYPE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "GLD_EXCHANGERATE_TYPE_PK")}
            column(name: "SET_OF_BOOKS_ID", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "TYPE_CODE", type: "VARCHAR(30)", remarks: "汇率类型代码")  {constraints(nullable:"false")}
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "汇率类型名称")
            column(name: "METHOD_CODE", type: "VARCHAR(30)", remarks: "方式")
            column(name: "PERIOD_TYPE", type: "VARCHAR(30)", remarks: "期间类型")
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
        createIndex(tableName: "GLD_EXCHANGERATE_TYPE", indexName: "GLD_EXCHANGERATE_TYPE_N1") {
            column(name: "DESCRIPTION")
        }

        addUniqueConstraint(columnNames:"TYPE_CODE",tableName:"GLD_EXCHANGERATE_TYPE",constraintName: "GLD_EXCHANGERATE_TYPE_U1")
    }

    changeSet(author:"jialin.xing@hand-china.com", id: "2019-01-08-GLD_EXCHANGERATE_TYPE_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_EXCHANGERATE_TYPE_TL_S', startValue: "10001")
        }

        createTable(tableName: "GLD_EXCHANGERATE_TYPE_TL", remarks: "汇率类型表多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "TYPE_ID", type: "BIGINT",  remarks: "主键，供其他表外键使用")  {constraints(nullable: "false", primaryKey: "true")}
            column(name: "DESCRIPTION", type: "VARCHAR(500)",  remarks: "汇率类型名称")
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

    changeSet(author: "jialin.xing@hand-china.com", id: "2019-01-04-GLD_EXCHANGERATE_TP_ASGN_AE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_EXCHANGERATE_TP_ASGN_AE_S', startValue:"10001")
        }

        createTable(tableName: "GLD_EXCHANGERATE_TP_ASGN_AE", remarks: "汇率类型分配核算主体表") {

            column(name: "ASSIGN_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "GLD_EXCHANGERATE_TP_ASGN_AE_PK")} 
            column(name: "TYPE_ID", type: "BIGINT", remarks: "汇率类型ID")  {constraints(nullable:"false")}  
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体ID")  {constraints(nullable:"false")}  
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
        
        addUniqueConstraint(columnNames:"ACC_ENTITY_ID,TYPE_ID",tableName:"GLD_EXCHANGERATE_TP_ASGN_AE",constraintName: "GLD_EXCHANGERATE_TP_ASGN_AE_U1")
    }

}