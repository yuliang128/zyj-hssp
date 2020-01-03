package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-04-08-evt-init-table-migration.groovy') {


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-04-08-EVT_EVENT") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EVT_EVENT_S', startValue:"10001")
        }

        createTable(tableName: "EVT_EVENT", remarks: "事件表") {

            column(name: "EVENT_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "事件ID")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EVT_EVENT_PK")} 
            column(name: "EVENT_CODE", type: "VARCHAR(200)", remarks: "事件名称")  {constraints(nullable:"false")}  
            column(name: "EVENT_NAME", type: "VARCHAR(2000)", remarks: "事件名称")   
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
    
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-04-08-EVT_EVENT_HANDLE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EVT_EVENT_HANDLE_S', startValue:"10001")
        }

        createTable(tableName: "EVT_EVENT_HANDLE", remarks: "事件过程表") {

            column(name: "HANDLE_RECORD_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "处理过程ID")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EVT_EVENT_HANDLE_PK")} 
            column(name: "EVENT_ID", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "ORDER_NUM", type: "NUMBER", remarks: "处理顺序")  {constraints(nullable:"false")}  
            column(name: "HANDLE_TITLE", type: "VARCHAR(200)", remarks: "处理名称")   
            column(name: "HANDLE_DESC", type: "VARCHAR(4000)", remarks: "说明")   
            column(name: "BEAN_NAME", type: "VARCHAR(2000)", remarks: "JavaBean名称")   
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"EVENT_ID,ORDER_NUM",tableName:"EVT_EVENT_HANDLE",constraintName: "EVT_EVENT_HANDLE_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-04-08-EVT_EVENT_HANDLE_RECORD") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EVT_EVENT_HANDLE_RECORD_S', startValue:"10001")
        }

        createTable(tableName: "EVT_EVENT_HANDLE_RECORD", remarks: "事件过程日志表") {

            column(name: "RECORD_ID", type: "NUMBER", remarks: "记录id")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EVT_EVENT_HANDLE_RECORD_PK")} 
            column(name: "EVENT_RECORD_ID", type: "NUMBER", remarks: "事件记录id")  {constraints(nullable:"false")}  
            column(name: "HANDLE_ID", type: "NUMBER", remarks: "过程id")   
            column(name: "RETURN_CODE", type: "VARCHAR(200)", remarks: "返回编码")   
            column(name: "SUCCESS_FLAG", type: "VARCHAR(1)", remarks: "成功标识")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")   
            column(name: "CREATED_BY", type: "NUMBER", remarks: "")   
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")   
            column(name: "LAST_UPDATED_BY", type: "NUMBER", remarks: "")   
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "EVT_EVENT_HANDLE_RECORD", indexName: "EVT_EVENT_HANDLE_RECORD_N1") {
                    column(name: "EVENT_RECORD_ID")
                }
                createIndex(tableName: "EVT_EVENT_HANDLE_RECORD", indexName: "EVT_EVENT_HANDLE_RECORD_N2") {
                    column(name: "HANDLE_ID")
                }
        
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-04-08-EVT_EVENT_LOG") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EVT_EVENT_LOG_S', startValue:"10001")
        }

        createTable(tableName: "EVT_EVENT_LOG", remarks: "事件日志表") {

            column(name: "HANDLE_RECORD_ID", type: "BIGINT", remarks: "处理程序ID")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")   
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")   
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")   
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")   
            column(name: "LOG_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "日志ID")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EVT_EVENT_LOG_PK")} 
            column(name: "LOG_DATE", type: "DATETIME", remarks: "日志日期")  {constraints(nullable:"false")}  
            column(name: "LOG_TEXT", type: "VARCHAR(4000)", remarks: "日志内容")   
            column(name: "EVENT_ID", type: "BIGINT", remarks: "事件ID")  {constraints(nullable:"false")}  
            column(name: "EVENT_SOURCE", type: "VARCHAR(200)", remarks: "事件来源")   
            column(name: "EVENT_PARAM", type: "BIGINT", remarks: "事件参数")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "EVT_EVENT_LOG", indexName: "EVT_EVENT_LOG_N1") {
                    column(name: "EVENT_ID")
                }
                createIndex(tableName: "EVT_EVENT_LOG", indexName: "EVT_EVENT_LOG_N2") {
                    column(name: "EVENT_SOURCE")
                    column(name: "EVENT_PARAM")
                }
                createIndex(tableName: "EVT_EVENT_LOG", indexName: "EVT_EVENT_LOG_N3") {
                    column(name: "HANDLE_RECORD_ID")
                }
        
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-04-08-EVT_EVENT_RECORD") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EVT_EVENT_RECORD_S', startValue:"10001")
        }

        createTable(tableName: "EVT_EVENT_RECORD", remarks: "事件记录表") {

            column(name: "RECORD_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "记录ID")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EVT_EVENT_RECORD_PK")} 
            column(name: "EVENT_ID", type: "BIGINT", remarks: "事件名称")   
            column(name: "EVENT_TYPE", type: "VARCHAR(200)", remarks: "事件类型")   
            column(name: "EVENT_PARAM", type: "BIGINT", remarks: "事件参数")   
            column(name: "EVENT_SOURCE", type: "VARCHAR(200)", remarks: "事件来源")   
            column(name: "EVENT_DATA", type: "CLOB", remarks: "事件DATA")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "EVT_EVENT_RECORD", indexName: "EVT_EVENT_RECORD_N1") {
                    column(name: "EVENT_ID")
                }
                createIndex(tableName: "EVT_EVENT_RECORD", indexName: "EVT_EVENT_RECORD_N2") {
                    column(name: "EVENT_TYPE")
                }
                createIndex(tableName: "EVT_EVENT_RECORD", indexName: "EVT_EVENT_RECORD_N4") {
                    column(name: "EVENT_SOURCE")
                    column(name: "EVENT_PARAM")
                }
        
    }


}