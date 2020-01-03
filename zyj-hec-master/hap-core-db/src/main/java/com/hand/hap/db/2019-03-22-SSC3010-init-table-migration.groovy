package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-03-22-SSC3010-init-table-migration.groovy') {


    changeSet(author: "hui.zhao01@hand-china.com", id: "2019-03-22-SSC_TASK_POOL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'SSC_TASK_POOL_S', startValue:"10001")
        }

        createTable(tableName: "SSC_TASK_POOL", remarks: "共享任务池") {

            column(name: "TASK_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "SSC_TASK_POOL_PK")} 
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
            column(name: "WORK_CENTER_ID", type: "BIGINT", remarks: "工作中心ID")  {constraints(nullable:"false")}  
            column(name: "WORKFLOW_ID", type: "BIGINT", remarks: "工作流程ID")   
            column(name: "DOC_WFL_ASSIGN_ID", type: "BIGINT", remarks: "业务单据关联工作流程ID")   
            column(name: "CURRENT_NODE_ID", type: "BIGINT", remarks: "当前节点ID")   
            column(name: "CURRENT_WORK_TEAM_ID", type: "BIGINT", remarks: "当前工作组ID")   
            column(name: "CURRENT_EMPLOYEE_ID", type: "BIGINT", remarks: "当前工作人员ID")   
            column(name: "TASK_STATUS", type: "VARCHAR(30)", remarks: "任务状态,SYSCODE:SSC_TASK_STATUS")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "SSC_TASK_POOL", indexName: "SSC_TASK_POOL_N1") {
                    column(name: "DOC_NUMBER")
                }
                createIndex(tableName: "SSC_TASK_POOL", indexName: "SSC_TASK_POOL_N2") {
                    column(name: "COMPANY_ID")
                    column(name: "MAG_ORG_ID")
                }
                createIndex(tableName: "SSC_TASK_POOL", indexName: "SSC_TASK_POOL_N3") {
                    column(name: "SET_OF_BOOKS_ID")
                    column(name: "ACC_ENTITY_ID")
                }
                createIndex(tableName: "SSC_TASK_POOL", indexName: "SSC_TASK_POOL_N5") {
                    column(name: "CURRENT_EMPLOYEE_ID")
                }
            
        addUniqueConstraint(columnNames:"DOC_ID,DOC_CATEGORY",tableName:"SSC_TASK_POOL",constraintName: "SSC_TASK_POOL_U1")
    }


    changeSet(author: "hui.zhao01@hand-china.com", id: "2019-03-22-SSC_TASK_POOL_HIS") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'SSC_TASK_POOL_HIS_S', startValue:"10001")
        }

        createTable(tableName: "SSC_TASK_POOL_HIS", remarks: "共享任务池_历史") {

            column(name: "TASK_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "SSC_TASK_POOL_HIS_PK")} 
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
            column(name: "WORK_CENTER_ID", type: "BIGINT", remarks: "工作重心")  {constraints(nullable:"false")}  
            column(name: "WORKFLOW_ID", type: "BIGINT", remarks: "工作流程ID")   
            column(name: "DOC_WFL_ASSIGN_ID", type: "BIGINT", remarks: "业务单据关联工作流程ID")   
            column(name: "CURRENT_NODE_ID", type: "BIGINT", remarks: "当前节点ID")   
            column(name: "CURRENT_WORK_TEAM_ID", type: "BIGINT", remarks: "当前工作组ID")   
            column(name: "CURRENT_EMPLOYEE_ID", type: "BIGINT", remarks: "当前工作人员ID")   
            column(name: "TASK_STATUS", type: "VARCHAR(30)", remarks: "任务状态,SYSCODE:SSC_TASK_STATUS")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "SSC_TASK_POOL_HIS", indexName: "SSC_TASK_POOL_HIS_N1") {
                    column(name: "DOC_NUMBER")
                }
                createIndex(tableName: "SSC_TASK_POOL_HIS", indexName: "SSC_TASK_POOL_HIS_N2") {
                    column(name: "COMPANY_ID")
                    column(name: "MAG_ORG_ID")
                }
                createIndex(tableName: "SSC_TASK_POOL_HIS", indexName: "SSC_TASK_POOL_HIS_N3") {
                    column(name: "ACC_ENTITY_ID")
                    column(name: "SET_OF_BOOKS_ID")
                }
                createIndex(tableName: "SSC_TASK_POOL_HIS", indexName: "SSC_TASK_POOL_HIS_N4") {
                    column(name: "CURRENT_WORK_TEAM_ID")
                    column(name: "WORK_CENTER_ID")
                }
                createIndex(tableName: "SSC_TASK_POOL_HIS", indexName: "SSC_TASK_POOL_HIS_N5") {
                    column(name: "CURRENT_EMPLOYEE_ID")
                }
                createIndex(tableName: "SSC_TASK_POOL_HIS", indexName: "SSC_TASK_POOL_HIS_N6") {
                    column(name: "DOC_ID")
                    column(name: "DOC_CATEGORY")
                }
        
    }


    changeSet(author: "hui.zhao01@hand-china.com", id: "2019-03-22-SSC_TASK_DISPATCH_RECORD") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'SSC_TASK_DISPATCH_RECORD_S', startValue:"10001")
        }

        createTable(tableName: "SSC_TASK_DISPATCH_RECORD", remarks: "任务分派记录") {

            column(name: "DISPATCH_RECORD_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "SSC_TASK_DISPATCH_RECORD_PK")} 
            column(name: "TASK_ID", type: "BIGINT", remarks: "任务ID")  {constraints(nullable:"false")}  
            column(name: "NODE_ID", type: "BIGINT", remarks: "当前节点ID")   
            column(name: "WORK_TEAM_ID", type: "BIGINT", remarks: "当前工作组ID")   
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "当前操作人员ID")   
            column(name: "STATUS", type: "VARCHAR(30)", remarks: "当前派工处理状态：SSC_DISPATCH_STATUS")  {constraints(nullable:"false")}  
            column(name: "ENTER_TIME", type: "DATETIME", remarks: "开始时间")   
            column(name: "EXIT_TIME", type: "DATETIME", remarks: "结束时间")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "SSC_TASK_DISPATCH_RECORD", indexName: "SSC_TASK_DISPATCH_RECORD_N1") {
                    column(name: "NODE_ID")
                }
                createIndex(tableName: "SSC_TASK_DISPATCH_RECORD", indexName: "SSC_TASK_DISPATCH_RECORD_N2") {
                    column(name: "EMPLOYEE_ID")
                }
        
    }


    changeSet(author: "hui.zhao01@hand-china.com", id: "2019-03-22-SSC_TASK_DISPATCH_RECORD_HIS") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'SSC_TASK_DISPATCH_RECORD_HIS_S', startValue:"10001")
        }

        createTable(tableName: "SSC_TASK_DISPATCH_RECORD_HIS", remarks: "任务分派记录_历史表") {

            column(name: "DISPATCH_RECORD_ID", type: "BIGINT", remarks: "主键")  {constraints(nullable:"false")}  
            column(name: "TASK_ID", type: "BIGINT", remarks: "任务ID")  {constraints(nullable:"false")}  
            column(name: "NODE_ID", type: "BIGINT", remarks: "当前节点ID")   
            column(name: "WORK_TEAM_ID", type: "BIGINT", remarks: "当前工作组ID")   
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "当前操作人员ID")   
            column(name: "STATUS", type: "VARCHAR(30)", remarks: "当前派工处理状态:SSC_PROCESS_STATUS")  {constraints(nullable:"false")}  
            column(name: "ENTER_TIME", type: "DATETIME", remarks: "开始时间")   
            column(name: "EXIT_TIME", type: "DATETIME", remarks: "结束时间")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "SSC_TASK_DISPATCH_RECORD_HIS", indexName: "SSC_TASK_DISPATCH_RD_HIS_N1") {
                    column(name: "DISPATCH_RECORD_ID")
                }
    
    }

    changeSet(author: "hui.zhao01@hand-china.com", id: "2019-03-25-SSC_TASK_DISPATCH_ADVICE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'SSC_TASK_DISPATCH_ADVICE_S', startValue:"10001")
        }

        createTable(tableName: "SSC_TASK_DISPATCH_ADVICE", remarks: "任务分派建议作业人员") {

            column(name: "ADVICE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "SSC_TASK_DISPATCH_ADVICE_PK")}
            column(name: "DISPATCH_RECORD_ID", type: "BIGINT", remarks: "任务派工ID")  {constraints(nullable:"false")}
            column(name: "TASK_ID", type: "BIGINT", remarks: "任务ID")  {constraints(nullable:"false")}
            column(name: "WORK_TEAM_ID", type: "BIGINT", remarks: "工作组ID")  {constraints(nullable:"false")}
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        createIndex(tableName: "SSC_TASK_DISPATCH_ADVICE", indexName: "SSC_TASK_DISPATCH_ADVICE_N1") {
            column(name: "DISPATCH_RECORD_ID")
        }
        createIndex(tableName: "SSC_TASK_DISPATCH_ADVICE", indexName: "SSC_TASK_DISPATCH_ADVICE_N2") {
            column(name: "WORK_TEAM_ID")
        }
        createIndex(tableName: "SSC_TASK_DISPATCH_ADVICE", indexName: "SSC_TASK_DISPATCH_ADVICE_N3") {
            column(name: "EMPLOYEE_ID")
        }

    }

}