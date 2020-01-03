package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-03-14-CSH1030-init-table-migration.groovy') {


    changeSet(author: "hui.lu@hand-china.com", id: "2019-03-14-SSC_WORKFLOW") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'SSC_WORKFLOW_S', startValue:"10001")
        }

        createTable(tableName: "SSC_WORKFLOW", remarks: "共享工作流程") {

            column(name: "WORKFLOW_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "SSC_WORKFLOW_PK")} 
            column(name: "WORKFLOW_CODE", type: "VARCHAR(30)", remarks: "工作流程代码")   
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述")
            column(name: "DOC_CATEGORY", type: "VARCHAR(30)", remarks: "单据类别")   
            column(name: "PAGE_ID", type: "BIGINT", remarks: "共享作业操作页面ID")   
            column(name: "FINISH_PROCEDURE_ID", type: "BIGINT", remarks: "结束时过程ID")   
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
        
        addUniqueConstraint(columnNames:"WORKFLOW_CODE",tableName:"SSC_WORKFLOW",constraintName: "SSC_WORKFLOW_U1")
    }

    changeSet(author:"hui.lu@hand-china.com", id: "2019-03-14-SSC_WORKFLOW_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'SSC_WORKFLOW_TL_S', startValue: "10001")
        }

        createTable(tableName: "SSC_WORKFLOW_TL", remarks: "共享工作流程多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "WORKFLOW_ID", type: "BIGINT",  remarks: "主键")  {constraints(nullable: "false", primaryKey: "true")} 
            column(name: "DESCRIPTION", type: "VARCHAR(500)",  remarks: "描述")
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
	
	changeSet(author: "hui.lu@hand-china.com", id: "2019-03-14-SSC_NODE_ASGN_WORK_CENTER") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'SSC_NODE_ASGN_WORK_CENTER_S', startValue:"10001")
        }

        createTable(tableName: "SSC_NODE_ASGN_WORK_CENTER", remarks: "工作流程节点分配工作中心") {

            column(name: "RECORD_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "SSC_NODE_ASGN_WORK_CENTER_PK")} 
            column(name: "NODE_ID", type: "BIGINT", remarks: "工作流程节点ID")  {constraints(nullable:"false")}  
            column(name: "WORK_CENTER_ID", type: "BIGINT", remarks: "工作中心ID")  {constraints(nullable:"false")}  
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
        
        addUniqueConstraint(columnNames:"NODE_ID,WORK_CENTER_ID",tableName:"SSC_NODE_ASGN_WORK_CENTER",constraintName: "SSC_NODE_ASGN_WORK_CENTERS_U1")
    }

    changeSet(author: "hui.lu@hand-china.com", id: "2019-03-14-SSC_WORKFLOW_ACTION") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'SSC_WORKFLOW_ACTION_S', startValue:"10001")
        }

        createTable(tableName: "SSC_WORKFLOW_ACTION", remarks: "共享工作流程动作") {

            column(name: "ACTION_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "SSC_WORKFLOW_ACTION_PK")}
            column(name: "WORKFLOW_ID", type: "BIGINT", remarks: "工作流程ID")  {constraints(nullable:"false")}
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述")
            column(name: "ACTION_TYPE", type: "VARCHAR(30)", remarks: "动作类型")
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
        createIndex(tableName: "SSC_WORKFLOW_ACTION", indexName: "SSC_WORKFLOW_ACTION_N1") {
            column(name: "WORKFLOW_ID")
        }

    }

    changeSet(author:"hui.lu@hand-china.com", id: "2019-03-14-SSC_WORKFLOW_ACTION_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'SSC_WORKFLOW_ACTION_TL_S', startValue: "10001")
        }

        createTable(tableName: "SSC_WORKFLOW_ACTION_TL", remarks: "共享工作流程动作多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "ACTION_ID", type: "BIGINT",  remarks: "主键")  {constraints(nullable: "false", primaryKey: "true")}
            column(name: "DESCRIPTION", type: "VARCHAR(500)",  remarks: "描述")
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

    changeSet(author: "hui.lu@hand-china.com", id: "2019-03-14-SSC_WORKFLOW_PROC") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'SSC_WORKFLOW_PROC_S', startValue:"10001")
        }

        createTable(tableName: "SSC_WORKFLOW_PROC", remarks: "共享工作流程相关过程") {

            column(name: "RECORD_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "SSC_WORKFLOW_PROC_PK")}
            column(name: "WORKFLOW_ID", type: "BIGINT", remarks: "工作流程ID")
            column(name: "PROCEDURE_ID", type: "BIGINT", remarks: "过程ID")
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
        createIndex(tableName: "SSC_WORKFLOW_PROC", indexName: "SSC_WORKFLOW_PROC_N1") {
            column(name: "WORKFLOW_ID")
        }

    }

    changeSet(author: "hui.lu@hand-china.com", id: "2019-03-14-SSC_WORKFLOW_NODE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'SSC_WORKFLOW_NODE_S', startValue:"10001")
        }

        createTable(tableName: "SSC_WORKFLOW_NODE", remarks: "共享工作流程节点") {

            column(name: "NODE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "SSC_WORKFLOW_NODE_PK")}
            column(name: "WORKFLOW_ID", type: "BIGINT", remarks: "工作流程ID")  {constraints(nullable:"false")}
            column(name: "NODE_SEQUENCE", type: "BIGINT", remarks: "节点顺序")  {constraints(nullable:"false")}
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述")
            column(name: "PROCESS_PAGE_ID", type: "BIGINT", remarks: "操作页面ID")
            column(name: "VIEW_PAGE_ID", type: "BIGINT", remarks: "查看页面ID")
            column(name: "TIME_CONTROL_FLAG", type: "VARCHAR(1)", remarks: "是否进行时间限制")
            column(name: "TIME_LIMIT", type: "BIGINT", remarks: "时间限制(分钟)")
            column(name: "AUTO_DISPATCH_FLAG", type: "VARCHAR(1)", remarks: "自动派工标志")
            column(name: "CAN_HOLD_FLAG", type: "VARCHAR(1)", remarks: "是否可以暂挂")
            column(name: "CAN_RETURN_FLAG", type: "VARCHAR(1)", remarks: "是否可以退回派工池")
            column(name: "MANUAL_DISPATCH_FLAG", type: "VARCHAR(1)", remarks: "手动取单标志")
            column(name: "FIXED_FLAG", type: "VARCHAR(1)", remarks: "固定节点标志")
            column(name: "NODE_TYPE", type: "VARCHAR(30)", remarks: "节点类型")  {constraints(nullable:"false")}
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

        addUniqueConstraint(columnNames:"NODE_SEQUENCE,WORKFLOW_ID",tableName:"SSC_WORKFLOW_NODE",constraintName: "SSC_WORKFLOW_NODES_U1")
    }

    changeSet(author:"hui.lu@hand-china.com", id: "2019-03-14-SSC_WORKFLOW_NODE_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'SSC_WORKFLOW_NODE_TL_S', startValue: "10001")
        }

        createTable(tableName: "SSC_WORKFLOW_NODE_TL", remarks: "共享工作流程节点多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "NODE_ID", type: "BIGINT",  remarks: "主键")  {constraints(nullable: "false", primaryKey: "true")}
            column(name: "DESCRIPTION", type: "VARCHAR(500)",  remarks: "描述")
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

    changeSet(author: "hui.lu@hand-china.com", id: "2019-03-14-SSC_WORKFLOW_NODE_PROC") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'SSC_WORKFLOW_NODE_PROC_S', startValue:"10001")
        }

        createTable(tableName: "SSC_WORKFLOW_NODE_PROC", remarks: "共享工作流程节点相关过程") {

            column(name: "RECORD_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "SSC_WORKFLOW_NODE_PROC_PK")}
            column(name: "NODE_ID", type: "BIGINT", remarks: "工作流程节点ID")
            column(name: "PROCEDURE_ID", type: "BIGINT", remarks: "过程ID")
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
        createIndex(tableName: "SSC_WORKFLOW_NODE_PROC", indexName: "SSC_WORKFLOW_NODE_PROC_N1") {
            column(name: "NODE_ID")
        }

    }

    changeSet(author: "hui.lu@hand-china.com", id: "2019-03-14-SSC_WORKFLOW_NODE_ACTION") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'SSC_WORKFLOW_NODE_ACTION_S', startValue:"10001")
        }

        createTable(tableName: "SSC_WORKFLOW_NODE_ACTION", remarks: "共享工作流程节点动作") {

            column(name: "RECORD_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "SSC_WORKFLOW_NODE_ACTION_PK")}
            column(name: "NODE_ID", type: "BIGINT", remarks: "节点ID")  {constraints(nullable:"false")}
            column(name: "ACTION_ID", type: "BIGINT", remarks: "动作ID")  {constraints(nullable:"false")}
            column(name: "BACK_NODE_ID", type: "BIGINT", remarks: "退回节点ID")
            column(name: "PROCEDURE_ID", type: "BIGINT", remarks: "动作触发过程")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }

        addUniqueConstraint(columnNames:"NODE_ID,ACTION_ID",tableName:"SSC_WORKFLOW_NODE_ACTION",constraintName: "SSC_WORKFLOW_NODE_ACTIONS_U1")
    }

    changeSet(author: "hui.lu@hand-china.com", id: "2019-03-14-SSC_WORKFLOW_NODE_RULE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'SSC_WORKFLOW_NODE_RULE_S', startValue:"10001")
        }

        createTable(tableName: "SSC_WORKFLOW_NODE_RULE", remarks: "共享作业流程节点_非固定节点规则") {

            column(name: "RECORD_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "SSC_WORKFLOW_NODE_RULE_PK")}
            column(name: "DOC_WFL_ASSIGN_ID", type: "BIGINT", remarks: "业务单据分配工作流程ID")  {constraints(nullable:"false")}
            column(name: "NODE_ID", type: "BIGINT", remarks: "工作流程节点ID")  {constraints(nullable:"false")}
            column(name: "WFL_BUSINESS_RULE_ID", type: "BIGINT", remarks: "工作权限规则ID")  {constraints(nullable:"false")}
            column(name: "START_DATE", type: "DATETIME", remarks: "启用日期")  {constraints(nullable:"false")}
            column(name: "END_DATE", type: "DATETIME", remarks: "失效日期")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }

        addUniqueConstraint(columnNames:"WFL_BUSINESS_RULE_ID,DOC_WFL_ASSIGN_ID,NODE_ID",tableName:"SSC_WORKFLOW_NODE_RULE",constraintName: "SSC_WORKFLOW_NODE_RULES_U1")
    }

}