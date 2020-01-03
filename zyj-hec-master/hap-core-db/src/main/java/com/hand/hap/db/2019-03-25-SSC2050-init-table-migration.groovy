package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-03-25-SSC2050-init-table-migration.groovy') {


    changeSet(author: "bo.zhang05@hand-china.com", id: "2019-03-25-SSC_TASK_POOL_PREPARE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'SSC_TASK_POOL_PREPARE_S', startValue:"10001")
        }

        createTable(tableName: "SSC_TASK_POOL_PREPARE", remarks: "任务池_等待入池") {

            column(name: "TASK_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "SSC_TASK_POOL_PREPARE_PK")} 
            column(name: "DOC_CATEGORY", type: "VARCHAR(30)", remarks: "单据类别")  {constraints(nullable:"false")}  
            column(name: "DOC_TYPE_ID", type: "BIGINT", remarks: "单据类型ID")   
            column(name: "DOC_ID", type: "BIGINT", remarks: "单据ID")  {constraints(nullable:"false")}  
            column(name: "DOC_NUMBER", type: "VARCHAR(200)", remarks: "单据编号")   
            column(name: "MAG_ORG_ID", type: "BIGINT", remarks: "管理组织ID")   
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司ID")   
            column(name: "UNIT_ID", type: "BIGINT", remarks: "部门ID")   
            column(name: "POSITION_ID", type: "BIGINT", remarks: "岗位ID")   
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "员工ID")   
            column(name: "SET_OF_BOOKS_ID", type: "BIGINT", remarks: "账套ID")   
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体ID")   
            column(name: "ENTER_TIME", type: "DATETIME", remarks: "进入本池时间")  {constraints(nullable:"false")}  
            column(name: "EXIT_TIME", type: "DATETIME", remarks: "退出本池时间")   
            column(name: "PROCESS_STATUS", type: "VARCHAR(30)", remarks: "处理状态，N/Y/E/I，未处理，已处理，处理异常，处理中")   
            column(name: "ERROR_MESSAGE", type: "VARCHAR(2000)", remarks: "错误消息，由预处理池进入派工池时的异常消息")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"DOC_ID,DOC_CATEGORY",tableName:"SSC_TASK_POOL_PREPARE",constraintName: "SSC_TASK_POOL_PREPARE_U1")
    }


}