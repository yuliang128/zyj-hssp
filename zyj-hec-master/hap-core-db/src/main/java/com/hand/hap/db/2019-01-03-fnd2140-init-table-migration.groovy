package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-01-03-fnd2140-init-table-migration.groovy') {


    changeSet(author: "gyt", id: "2019-01-03-GLD_ACCOUNT") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_ACCOUNT_S', startValue:"10001")
        }

        createTable(tableName: "GLD_ACCOUNT", remarks: "科目表") {

            column(name: "ACCOUNT_SET_ID", type: "BIGINT", remarks: "科目表ID")  {constraints(nullable:"false")}  
            column(name: "ACCOUNT_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "科目ID")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "GLD_ACCOUNT_PK")} 
            column(name: "ACCOUNT_CODE", type: "VARCHAR(100)", remarks: "科目代码")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "VARCHAR(1000)", remarks: "描述")
            column(name: "ACCOUNT_TYPE", type: "VARCHAR(1)", remarks: "类型")  {constraints(nullable:"false")}  
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")   
            column(name: "SUMMARY_FLAG", type: "VARCHAR(1)", remarks: "汇总")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
                    
        addUniqueConstraint(columnNames:"ACCOUNT_SET_ID,ACCOUNT_CODE",tableName:"GLD_ACCOUNT",constraintName: "GLD_ACCOUNT_U1")
    }

    changeSet(author:"gyt", id: "2019-01-03-GLD_ACCOUNT_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_ACCOUNT_TL_S', startValue: "10001")
        }

        createTable(tableName: "GLD_ACCOUNT_TL", remarks: "科目表多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "ACCOUNT_ID", type: "BIGINT",  remarks: "科目ID")  {constraints(nullable: "false", primaryKey: "true", primaryKeyName: "GLD_ACCOUNT_TL_PK")} 
            column(name: "DESCRIPTION", type: "VARCHAR(2000)",  remarks: "描述")
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

    changeSet(author: "gyt", id: "2019-01-03-GLD_ACCOUNT_HIERARCHY") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_ACCOUNT_HIERARCHY_S', startValue:"10001")
        }

        createTable(tableName: "GLD_ACCOUNT_HIERARCHY", remarks: "会计科目层次") {

            column(name: "SUB_ACCOUNT_CODE", type: "VARCHAR(100)", remarks: "子科目")   
            column(name: "FROM_ACCOUNT_CODE", type: "VARCHAR(100)", remarks: "科目从")   
            column(name: "TO_ACCOUNT_CODE", type: "VARCHAR(100)", remarks: "科目到")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID")  {constraints(nullable:"false")}  
            column(name: "ACCOUNT_HIERARCHY_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "科目层次ID")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "GLD_ACCOUNT_HIERARCHY_PK")} 
            column(name: "ACCOUNT_SET_ID", type: "BIGINT", remarks: "科目表ID")  {constraints(nullable:"false")}  
            column(name: "PARENT_ACCOUNT_ID", type: "BIGINT", remarks: "父科目ID")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "GLD_ACCOUNT_HIERARCHY", indexName: "GLD_ACCOUNT_HIERARCHY_N1") {
                    column(name: "ACCOUNT_SET_ID")
                    column(name: "PARENT_ACCOUNT_ID")
                }
        
    }


    changeSet(author: "gyt", id: "2019-01-03-GLD_ACCOUNT_HIERARCHY_DETAIL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_ACCOUNT_HIERARCHY_DETAIL_S', startValue:"10001")
        }

        createTable(tableName: "GLD_ACCOUNT_HIERARCHY_DETAIL", remarks: "会计科目层次明细") {

            column(name: "HIERARCHY_DETAIL_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "会计科目层次明细ID")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "GLD_ACCOUNT_HIERARCHY_DETAIL_PK")}
            column(name: "ACCOUNT_SET_ID", type: "BIGINT", remarks: "科目表ID")  {constraints(nullable:"false")}  
            column(name: "PARENT_ACCOUNT_ID", type: "BIGINT", remarks: "父科目ID")   
            column(name: "ACCOUNT_ID", type: "BIGINT", remarks: "科目ID")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "GLD_ACCOUNT_HIERARCHY_DETAIL", indexName: "GLD_ACCOUNT_HIERARCHY_DETAIL_N1") {
                    column(name: "ACCOUNT_ID")
                    column(name: "PARENT_ACCOUNT_ID")
                }
                createIndex(tableName: "GLD_ACCOUNT_HIERARCHY_DETAIL", indexName: "GLD_ACCOUNT_HIERARCHY_DETAIL_N2") {
                    column(name: "PARENT_ACCOUNT_ID")
                }
        
        addUniqueConstraint(columnNames:"PARENT_ACCOUNT_ID,ACCOUNT_SET_ID,ACCOUNT_ID",tableName:"GLD_ACCOUNT_HIERARCHY_DETAIL",constraintName: "GLD_ACCOUNT_HIERARCHY_DETAIL_U1")
    }

    changeSet(author: "gyt", id: "2019-01-03-GLD_ACCOUNT_SET") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_ACCOUNT_SET_S', startValue:"10001")
        }

        createTable(tableName: "GLD_ACCOUNT_SET", remarks: "科目表头定义") {

            column(name: "ACCOUNT_SET_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "科目表ID")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "GLD_ACCOUNT_SET_PK")}
            column(name: "ACCOUNT_SET_CODE", type: "VARCHAR(30)", remarks: "科目表代码")  {constraints(nullable:"false")}
            column(name: "ACCOUNT_SET_NAME", type: "VARCHAR(500)", remarks: "科目表名称")
            column(name: "COA_STRUCTURE_ID", type: "BIGINT", remarks: "科目结构ID")  {constraints(nullable:"false")}
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID")  {constraints(nullable:"false")}
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }

        addUniqueConstraint(columnNames:"ACCOUNT_SET_CODE",tableName:"GLD_ACCOUNT_SET",constraintName: "GLD_ACCOUNT_SET_U1")
        addUniqueConstraint(columnNames:"ACCOUNT_SET_NAME",tableName:"GLD_ACCOUNT_SET",constraintName: "GLD_ACCOUNT_SET_U2")
    }

    changeSet(author:"gyt", id: "2019-01-03-GLD_ACCOUNT_SET_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'GLD_ACCOUNT_SET_TL_S', startValue: "10001")
        }

        createTable(tableName: "GLD_ACCOUNT_SET_TL", remarks: "科目表头定义多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "ACCOUNT_SET_ID", type: "BIGINT",  remarks: "科目表ID")  {constraints(nullable: "false", primaryKey: "true")}
            column(name: "ACCOUNT_SET_NAME", type: "VARCHAR(500)",  remarks: "科目表名称")
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