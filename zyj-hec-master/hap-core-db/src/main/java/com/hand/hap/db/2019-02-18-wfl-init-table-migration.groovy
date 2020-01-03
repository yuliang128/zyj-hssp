package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-02-18-wfl-init-table-migration.groovy') {


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-18-WFL_PROCESS") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_PROCESS_S', startValue: "10001")
        }

        createTable(tableName: "WFL_PROCESS", remarks: "工作流程") {

            column(name: "PROCESS_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "WFL_PROCESS_PK")
            }
            column(name: "PROCESS_CODE", type: "VARCHAR(30)", remarks: "流程代码") { constraints(nullable: "false") }
            column(name: "PROCESS_NAME", type: "VARCHAR(500)", remarks: "流程名称ID")
            column(name: "DOC_CATEGORY", type: "VARCHAR(30)", remarks: "单据类别") { constraints(nullable: "false") }
            column(name: "SUB_PROCESS_FLAG", type: "VARCHAR(1)", remarks: "子流程标志") { constraints(nullable: "false") }
            column(name: "PARENT_PROCESS_ID", type: "BIGINT", remarks: "父流程ID")
            column(name: "PROCESS_PAGE_ID", type: "BIGINT", remarks: "操作页面ID")
            column(name: "VIEW_PAGE_ID", type: "BIGINT", remarks: "查看页面ID")
            column(name: "START_PROCEDURE_ID", type: "BIGINT", remarks: "启动触发过程ID")
            column(name: "END_PROCEDURE_ID", type: "BIGINT", remarks: "结束触发过程ID")
            column(name: "CONFIG_TYPE", type: "VARCHAR(30)", remarks: "配置方式:简易(SIMPLE)标准(STANDARD)") {
                constraints(nullable: "false")
            }
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "VERSION", type: "BIGINT", remarks: "工作流版本") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "PROCESS_CODE", tableName: "WFL_PROCESS", constraintName: "WFL_PROCESS_U1")
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-18-WFL_PROCESS_TL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_PROCESS_TL_S', startValue: "10001")
        }

        createTable(tableName: "WFL_PROCESS_TL", remarks: "工作流程多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "PROCESS_ID", type: "BIGINT", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "PROCESS_NAME", type: "VARCHAR(500)", remarks: "流程名称ID")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-18-WFL_EVENT") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_EVENT_S', startValue: "10001")
        }

        createTable(tableName: "WFL_EVENT", remarks: "工作流事件") {

            column(name: "EVENT_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "WFL_EVENT_PK")
            }
            column(name: "EVENT_TYPE", type: "VARCHAR(30)", remarks: "事件类型，SYSCODE:WFL_EVENT_TYPE") {
                constraints(nullable: "false")
            }
            column(name: "EVENT_CODE", type: "VARCHAR(30)", remarks: "事件代码") { constraints(nullable: "false") }
            column(name: "EVENT_NAME", type: "VARCHAR(500)", remarks: "事件描述")
            column(name: "PROCESS_ID", type: "BIGINT", remarks: "流程ID") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "EVENT_CODE,PROCESS_ID", tableName: "WFL_EVENT", constraintName: "WFL_EVENT_U1")
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-18-WFL_EVENT_TL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_EVENT_TL_S', startValue: "10001")
        }

        createTable(tableName: "WFL_EVENT_TL", remarks: "工作流事件多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "EVENT_ID", type: "BIGINT", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "EVENT_NAME", type: "VARCHAR(500)", remarks: "事件描述")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-18-WFL_FLOW") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_FLOW_S', startValue: "10001")
        }

        createTable(tableName: "WFL_FLOW", remarks: "工作流流转") {

            column(name: "FLOW_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "WFL_FLOW_PK")
            }
            column(name: "PROCESS_ID", type: "BIGINT", remarks: "流程ID") { constraints(nullable: "false") }
            column(name: "FLOW_CODE", type: "VARCHAR(255)", remarks: "流转代码") { constraints(nullable: "false") }
            column(name: "FLOW_NAME", type: "VARCHAR(500)", remarks: "流转描述")
            column(name: "FROM_ELEMENT_TYPE", type: "VARCHAR(30)", remarks: "输入元素类型") { constraints(nullable: "false") }
            column(name: "FROM_ELEMENT_ID", type: "BIGINT", remarks: "输入元素ID") { constraints(nullable: "false") }
            column(name: "TO_ELEMENT_TYPE", type: "VARCHAR(30)", remarks: "输出元素类型") { constraints(nullable: "false") }
            column(name: "TO_ELEMENT_ID", type: "BIGINT", remarks: "输出元素ID") { constraints(nullable: "false") }
            column(name: "FLOW_VALUE", type: "VARCHAR(2000)", remarks: "流转判定值")
            column(name: "SEQUENCE", type: "BIGINT", remarks: "流程顺序，用于网关后的顺序判断")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "FLOW_CODE,PROCESS_ID", tableName: "WFL_FLOW", constraintName: "WFL_FLOW_U1")
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-18-WFL_FLOW_TL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_FLOW_TL_S', startValue: "10001")
        }

        createTable(tableName: "WFL_FLOW_TL", remarks: "工作流流转多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "FLOW_ID", type: "BIGINT", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "FLOW_NAME", type: "VARCHAR(500)", remarks: "流转描述")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-18-WFL_GATEWAY") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_GATEWAY_S', startValue: "10001")
        }

        createTable(tableName: "WFL_GATEWAY", remarks: "工作流网关") {

            column(name: "GATEWAY_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "WFL_GATEWAY_PK")
            }
            column(name: "PROCESS_ID", type: "BIGINT", remarks: "流程ID") { constraints(nullable: "false") }
            column(name: "GATEWAY_TYPE", type: "VARCHAR(30)", remarks: "网关类型,SYSCODE:WFL_GATEWAY_TYPE") {
                constraints(nullable: "false")
            }
            column(name: "GATEWAY_CODE", type: "VARCHAR(30)", remarks: "网关代码") { constraints(nullable: "false") }
            column(name: "GATEWAY_NAME", type: "VARCHAR(500)", remarks: "网关描述")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "GATEWAY_CODE,PROCESS_ID", tableName: "WFL_GATEWAY", constraintName: "WFL_GATEWAY_U1")
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-18-WFL_GATEWAY_TL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_GATEWAY_TL_S', startValue: "10001")
        }

        createTable(tableName: "WFL_GATEWAY_TL", remarks: "工作流网关多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "GATEWAY_ID", type: "BIGINT", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "GATEWAY_NAME", type: "VARCHAR(500)", remarks: "网关描述")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-18-WFL_TASK") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_TASK_S', startValue: "10001")
        }

        createTable(tableName: "WFL_TASK", remarks: "工作流任务") {

            column(name: "TASK_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "WFL_TASK_PK")
            }
            column(name: "PROCESS_ID", type: "BIGINT", remarks: "流程ID") { constraints(nullable: "false") }
            column(name: "TASK_CODE", type: "VARCHAR(30)", remarks: "任务代码") { constraints(nullable: "false") }
            column(name: "TASK_NAME", type: "VARCHAR(500)", remarks: "任务名ID")
            column(name: "TASK_TYPE", type: "VARCHAR(30)", remarks: "任务类型") { constraints(nullable: "false") }
            column(name: "PROCESS_PAGE_ID", type: "BIGINT", remarks: "操作页面ID")
            column(name: "VIEW_PAGE_ID", type: "BIGINT", remarks: "查看页面ID")
            column(name: "ARRIVE_PROCEDURE_ID", type: "BIGINT", remarks: "抵达任务处理过程ID")
            column(name: "DEPART_PROCEDURE_ID", type: "BIGINT", remarks: "离开任务处理过程ID")
            column(name: "RECEIVE_TYPE", type: "VARCHAR(30)", remarks: "接收类型，并行接受、串行接收") {
                constraints(nullable: "false")
            }
            column(name: "SEQUENCE", type: "BIGINT", remarks: "节点顺序，用于快速配置")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "TASK_CODE,PROCESS_ID", tableName: "WFL_TASK", constraintName: "WFL_TASK_U1")
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-18-WFL_TASK_TL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_TASK_TL_S', startValue: "10001")
        }

        createTable(tableName: "WFL_TASK_TL", remarks: "工作流任务多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "TASK_ID", type: "BIGINT", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "TASK_NAME", type: "VARCHAR(500)", remarks: "任务名ID")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-18-WFL_USER_TASK") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_USER_TASK_S', startValue: "10001")
        }

        createTable(tableName: "WFL_USER_TASK", remarks: "工作流任务_用户任务") {

            column(name: "USER_TASK_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "WFL_USER_TASK_PK")
            }
            column(name: "TASK_ID", type: "BIGINT", remarks: "任务ID") {
                constraints(nullable: "false")
            }
            column(name: "APPROVE_TYPE", type: "VARCHAR(30)", remarks: "审批类型，SYSCODE:WFL_APPROVE_TYPE") {
                constraints(nullable: "false")
            }
            column(name: "APPROVE_TYPE_PARAM", type: "VARCHAR(30)", remarks: "审批类型参数")
            column(name: "CAN_SUBMITTER_RECHECK", type: "VARCHAR(1)", remarks: "提交人是否需要审批") {
                constraints(nullable: "false")
            }
            column(name: "CAN_REPEATED_APPROVE", type: "VARCHAR(1)", remarks: "是否需要重复审批") {
                constraints(nullable: "false")
            }
            column(name: "CAN_NO_APPROVER", type: "VARCHAR(1)", remarks: "是否可以无审批人") { constraints(nullable: "false") }
            column(name: "CAN_ADD_NOTIFICATION", type: "VARCHAR(1)", remarks: "是否可以添加抄送人") {
                constraints(nullable: "false")
            }
            column(name: "CAN_TAKE_BACK", type: "VARCHAR(1)", remarks: "是否可以收回") { constraints(nullable: "false") }
            column(name: "TIME_LIMITED_FLAG", type: "VARCHAR(1)", remarks: "是否有时间限制") { constraints(nullable: "false") }
            column(name: "LIMITED_HOURS", type: "NUMBER", remarks: "时间限制（小时）")
            column(name: "OVERTIME_NOTIFY_FLAG", type: "VARCHAR(1)", remarks: "是否需要超时发送提醒") {
                constraints(nullable: "false")
            }
            column(name: "OVERTIME_NOTIFY_SCHEDULER_ID", type: "BIGINT", remarks: "超时提醒计划ID")
            column(name: "OVERTIME_NOTIFY_MSG_TPLT_ID", type: "BIGINT", remarks: "超时提醒消息模板ID")
            column(name: "ARRIVE_NOTIFY_FLAG", type: "VARCHAR(1)", remarks: "抵达任务是否需要提醒") {
                constraints(nullable: "false")
            }
            column(name: "ARRIVE_MSG_TPLT_ID", type: "BIGINT", remarks: "抵达任务提醒消息模板ID")
            column(name: "POST_NOTIFY_FLAG", type: "VARCHAR(1)", remarks: "通过任务是否需要提醒") {
                constraints(nullable: "false")
            }
            column(name: "POST_MSG_TPLT_ID", type: "BIGINT", remarks: "通过任务提醒消息ID")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "", defaultValueComputed: "CURRENT_TIMESTAMP") {
                constraints(nullable: "false")
            }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "", defaultValue: "-1") {
                constraints(nullable: "false")
            }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        addUniqueConstraint(columnNames: "TASK_ID", tableName: "WFL_USER_TASK", constraintName: "WFL_USER_TASK_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-18-WFL_SUB_PROCESS_TASK") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_SUB_PROCESS_TASK_S', startValue: "10001")
        }

        createTable(tableName: "WFL_SUB_PROCESS_TASK", remarks: "工作流任务_子流程") {

            column(name: "SUB_PROCESS_TASK_ID", autoIncrement: true, startWith: "10001", type: "BIGINT", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "WFL_SUB_PROCESS_TASK_PK")
            }
            column(name: "TASK_ID", type: "BIGINT", remarks: "任务ID") { constraints(nullable: "false") }
            column(name: "PROCESS_ID", type: "BIGINT", remarks: "流程ID") { constraints(nullable: "false") }
            column(name: "TASK_AUTO_PASS_FLAG", type: "VARCHAR(1)", remarks: "节点自动通过标志") {
                constraints(nullable: "false")
            }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "TASK_ID", tableName: "WFL_SUB_PROCESS_TASK", constraintName: "WFL_SUB_PROCESS_TASK_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-18-WFL_GATEWAY_BIZ_RULE_GROUP") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_GATEWAY_BIZ_RULE_GROUP_S', startValue: "10001")
        }

        createTable(tableName: "WFL_GATEWAY_BIZ_RULE_GROUP", remarks: "工作流网关权限规则组") {

            column(name: "GROUP_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "WFL_GATEWAY_BIZ_RULE_GROUP_PK")
            }
            column(name: "GATEWAY_ID", type: "BIGINT", remarks: "网关ID") { constraints(nullable: "false") }
            column(name: "SEQUENCE", type: "BIGINT", remarks: "顺序") { constraints(nullable: "false") }
            column(name: "GROUP_CODE", type: "VARCHAR(30)", remarks: "权限组代码") { constraints(nullable: "false") }
            column(name: "GROUP_NAME", type: "VARCHAR(500)", remarks: "权限组名称ID")
            column(name: "FLOW_ID", type: "BIGINT", remarks: "流ID") { constraints(nullable: "false") }
            column(name: "ADJUST_TYPE", type: "VARCHAR(30)", remarks: "权限明细判断类型") { constraints(nullable: "false") }
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "GROUP_CODE,GATEWAY_ID", tableName: "WFL_GATEWAY_BIZ_RULE_GROUP", constraintName: "WFL_GATEWAY_BIZ_RULE_GROUP_U1")
        addUniqueConstraint(columnNames: "SEQUECNE,GATEWAY_ID", tableName: "WFL_GATEWAY_BIZ_RULE_GROUP", constraintName: "WFL_GATEWAY_BIZ_RULE_GROUP_U2")
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-18-WFL_GATEWAY_BIZ_RULE_GROUP_TL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_GATEWAY_BIZ_RULE_GROUP_TL_S', startValue: "10001")
        }

        createTable(tableName: "WFL_GATEWAY_BIZ_RULE_GROUP_TL", remarks: "工作流网关权限规则组多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "GROUP_ID", type: "BIGINT", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "GROUP_NAME", type: "VARCHAR(500)", remarks: "权限组名称ID")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-18-WFL_GATEWAY_BIZ_RULE_DETAIL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_GATEWAY_BIZ_RULE_DETAIL_S', startValue: "10001")
        }

        createTable(tableName: "WFL_GATEWAY_BIZ_RULE_DETAIL", remarks: "工作流网关权限规则分配明细") {

            column(name: "DETAIL_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "WFL_GATEWAY_BIZ_RULE_DETAIL_PK")
            }
            column(name: "GROUP_ID", type: "BIGINT", remarks: "权限组ID") { constraints(nullable: "false") }
            column(name: "BUSINESS_RULE_ID", type: "BIGINT", remarks: "权限规则ID") { constraints(nullable: "false") }
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "WFL_GATEWAY_BIZ_RULE_DETAIL", indexName: "WFL_GATEWAY_BIZ_RULE_DETAIL_N1") {
            column(name: "GROUP_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-18-WFL_TASK_RECEIVER") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_TASK_RECEIVER_S', startValue: "10001")
        }

        createTable(tableName: "WFL_TASK_RECEIVER", remarks: "工作流任务接收者") {

            column(name: "RECEIVER_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "WFL_TASK_RECEIVER_PK")
            }
            column(name: "TASK_ID", type: "BIGINT", remarks: "任务ID") { constraints(nullable: "false") }
            column(name: "RECEIVER_CATEGORY", type: "VARCHAR(200)", remarks: "接收者类别") { constraints(nullable: "false") }
            column(name: "RECEIVER_TYPE", type: "VARCHAR(30)", remarks: "接收者类型")
            column(name: "SEQUENCE", type: "BIGINT", remarks: "接收者序号") { constraints(nullable: "false") }
            column(name: "RECEIVER_PARAM1_ID", type: "BIGINT", remarks: "接收者参数1ID")
            column(name: "RECEIVER_PARAM1_CODE", type: "VARCHAR(200)", remarks: "接收者参数1代码")
            column(name: "RECEIVER_PARAM2_ID", type: "BIGINT", remarks: "接收者参数2ID")
            column(name: "RECEIVER_PARAM2_CODE", type: "VARCHAR(200)", remarks: "接收者参数2代码")
            column(name: "RECEIVER_PARAM3_ID", type: "BIGINT", remarks: "接收者参数3ID")
            column(name: "RECEIVER_PARAM3_CODE", type: "VARCHAR(200)", remarks: "接收者参数3代码")
            column(name: "RECEIVER_PARAM4_ID", type: "BIGINT", remarks: "接收者参数4ID")
            column(name: "RECEIVER_PARAM4_CODE", type: "VARCHAR(200)", remarks: "接收者参数4代码")
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-18-WFL_TASK_RCV_BIZ_DETAIL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_TASK_RCV_BIZ_DETAIL_S', startValue: "10001")
        }

        createTable(tableName: "WFL_TASK_RCV_BIZ_DETAIL", remarks: "工作流任务接收者权限规则分配明细") {

            column(name: "DETAIL_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "WFL_TASK_RCV_BIZ_DETAIL_PK")
            }
            column(name: "RECEIVER_ID", type: "BIGINT", remarks: "接受者ID") { constraints(nullable: "false") }
            column(name: "BUSINESS_RULE_ID", type: "BIGINT", remarks: "权限规则ID") { constraints(nullable: "false") }
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "WFL_TASK_RCV_BIZ_DETAIL", indexName: "WFL_TASK_RCV_BIZ_DETAIL_N1") {
            column(name: "RECEIVER_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-19-WFL_VERSION_EVENT") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_VERSION_EVENT_S', startValue: "10001")
        }

        createTable(tableName: "WFL_VERSION_EVENT", remarks: "已发布工作流事件") {

            column(name: "VERSION_EVENT_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "WFL_VERSION_EVENT_PK")
            }
            column(name: "EVENT_ID", type: "BIGINT", remarks: "事件ID") { constraints(nullable: "false") }
            column(name: "VERSION", type: "BIGINT", remarks: "工作流版本") { constraints(nullable: "false") }
            column(name: "EVENT_TYPE", type: "VARCHAR(30)", remarks: "事件类型，SYSCODE:WFL_EVENT_TYPE") {
                constraints(nullable: "false")
            }
            column(name: "EVENT_CODE", type: "VARCHAR(30)", remarks: "事件代码") { constraints(nullable: "false") }
            column(name: "EVENT_NAME", type: "VARCHAR(500)", remarks: "事件描述")
            column(name: "PROCESS_ID", type: "BIGINT", remarks: "流程ID") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "EVENT_ID,VERSION", tableName: "WFL_VERSION_EVENT", constraintName: "WFL_VERSION_EVENT_U1")
        addUniqueConstraint(columnNames: "PROCESS_ID,EVENT_CODE,VERSION", tableName: "WFL_VERSION_EVENT", constraintName: "WFL_VERSION_EVENT_U2")
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-19-WFL_VERSION_EVENT_TL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_VERSION_EVENT_TL_S', startValue: "10001")
        }

        createTable(tableName: "WFL_VERSION_EVENT_TL", remarks: "已发布工作流事件多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "VERSION_EVENT_ID", type: "BIGINT", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "EVENT_NAME", type: "VARCHAR(500)", remarks: "事件描述")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-19-WFL_VERSION_FLOW") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_VERSION_FLOW_S', startValue: "10001")
        }

        createTable(tableName: "WFL_VERSION_FLOW", remarks: "已发布工作流流转") {

            column(name: "VERSION_FLOW_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "WFL_VERSION_FLOW_PK")
            }
            column(name: "FLOW_ID", type: "BIGINT", remarks: "流转ID") { constraints(nullable: "false") }
            column(name: "VERSION", type: "BIGINT", remarks: "工作流版本") { constraints(nullable: "false") }
            column(name: "PROCESS_ID", type: "BIGINT", remarks: "流程ID") { constraints(nullable: "false") }
            column(name: "FLOW_CODE", type: "VARCHAR(255)", remarks: "流转代码") { constraints(nullable: "false") }
            column(name: "FLOW_NAME", type: "VARCHAR(500)", remarks: "流转描述")
            column(name: "FROM_ELEMENT_TYPE", type: "VARCHAR(30)", remarks: "输入元素类型") { constraints(nullable: "false") }
            column(name: "FROM_ELEMENT_ID", type: "BIGINT", remarks: "输入元素ID") { constraints(nullable: "false") }
            column(name: "TO_ELEMENT_TYPE", type: "VARCHAR(30)", remarks: "输出元素类型") { constraints(nullable: "false") }
            column(name: "TO_ELEMENT_ID", type: "BIGINT", remarks: "输出元素ID") { constraints(nullable: "false") }
            column(name: "FLOW_VALUE", type: "VARCHAR(2000)", remarks: "流转判定值")
            column(name: "SEQUENCE", type: "BIGINT", remarks: "流程顺序，用于网关后的顺序判断")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "FLOW_ID,VERSION", tableName: "WFL_VERSION_FLOW", constraintName: "WFL_VERSION_FLOW_U1")
        addUniqueConstraint(columnNames: "PROCESS_ID,FLOW_CODE,VERSION", tableName: "WFL_VERSION_FLOW", constraintName: "WFL_VERSION_FLOW_U2")
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-19-WFL_VERSION_FLOW_TL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_VERSION_FLOW_TL_S', startValue: "10001")
        }

        createTable(tableName: "WFL_VERSION_FLOW_TL", remarks: "已发布工作流流转多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "VERSION_FLOW_ID", type: "BIGINT", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "FLOW_NAME", type: "VARCHAR(500)", remarks: "流转描述")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-19-WFL_VERSION_GATEWAY") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_VERSION_GATEWAY_S', startValue: "10001")
        }

        createTable(tableName: "WFL_VERSION_GATEWAY", remarks: "已发布工作流网关") {

            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "VERSION_GATEWAY_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "WFL_VERSION_GATEWAY_PK")
            }
            column(name: "GATEWAY_ID", type: "BIGINT", remarks: "网关ID") { constraints(nullable: "false") }
            column(name: "VERSION", type: "BIGINT", remarks: "工作流版本") { constraints(nullable: "false") }
            column(name: "PROCESS_ID", type: "BIGINT", remarks: "流程ID") { constraints(nullable: "false") }
            column(name: "GATEWAY_TYPE", type: "VARCHAR(30)", remarks: "网关类型,SYSCODE:WFL_GATEWAY_TYPE") {
                constraints(nullable: "false")
            }
            column(name: "GATEWAY_CODE", type: "VARCHAR(30)", remarks: "网关代码") { constraints(nullable: "false") }
            column(name: "GATEWAY_NAME", type: "VARCHAR(500)", remarks: "网关描述")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "GATEWAY_ID,VERSION", tableName: "WFL_VERSION_GATEWAY", constraintName: "WFL_VERSION_GATEWAY_U1")
        addUniqueConstraint(columnNames: "PROCESS_ID,GATEWAY_CODE,VERSION", tableName: "WFL_VERSION_GATEWAY", constraintName: "WFL_VERSION_GATEWAY_U2")
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-19-WFL_VERSION_GATEWAY_TL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_VERSION_GATEWAY_TL_S', startValue: "10001")
        }

        createTable(tableName: "WFL_VERSION_GATEWAY_TL", remarks: "已发布工作流网关多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "VERSION_GATEWAY_ID", type: "BIGINT", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "GATEWAY_NAME", type: "VARCHAR(500)", remarks: "网关描述")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-19-WFL_VERSION_PROCESS") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_VERSION_PROCESS_S', startValue: "10001")
        }

        createTable(tableName: "WFL_VERSION_PROCESS", remarks: "已发布工作流程") {

            column(name: "VERSION_PROCESS_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "WFL_VERSION_PROCESS_PK")
            }
            column(name: "PROCESS_ID", type: "BIGINT", remarks: "工作流ID") { constraints(nullable: "false") }
            column(name: "VERSION", type: "BIGINT", remarks: "工作流版本") { constraints(nullable: "false") }
            column(name: "PROCESS_CODE", type: "VARCHAR(30)", remarks: "流程代码") { constraints(nullable: "false") }
            column(name: "PROCESS_NAME", type: "VARCHAR(500)", remarks: "流程名称ID")
            column(name: "DOC_CATEGORY", type: "VARCHAR(30)", remarks: "单据类别") { constraints(nullable: "false") }
            column(name: "SUB_PROCESS_FLAG", type: "VARCHAR(1)", remarks: "子流程标志") { constraints(nullable: "false") }
            column(name: "PARENT_PROCESS_ID", type: "BIGINT", remarks: "父流程ID")
            column(name: "PROCESS_PAGE_ID", type: "BIGINT", remarks: "操作页面ID")
            column(name: "VIEW_PAGE_ID", type: "BIGINT", remarks: "查看页面ID")
            column(name: "START_PROCEDURE_ID", type: "BIGINT", remarks: "启动触发过程ID")
            column(name: "END_PROCEDURE_ID", type: "BIGINT", remarks: "结束触发过程ID")
            column(name: "CONFIG_TYPE", type: "VARCHAR(30)", remarks: "配置方式:简易(SIMPLE)标准(STANDARD)") {
                constraints(nullable: "false")
            }
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "PROCESS_ID,VERSION", tableName: "WFL_VERSION_PROCESS", constraintName: "WFL_VERSION_PROCESS_U1")
        addUniqueConstraint(columnNames: "PROCESS_CODE,VERSION", tableName: "WFL_VERSION_PROCESS", constraintName: "WFL_VERSION_PROCESS_U2")
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-19-WFL_VERSION_PROCESS_TL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_VERSION_PROCESS_TL_S', startValue: "10001")
        }

        createTable(tableName: "WFL_VERSION_PROCESS_TL", remarks: "已发布工作流程多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "VERSION_PROCESS_ID", type: "BIGINT", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "PROCESS_NAME", type: "VARCHAR(500)", remarks: "流程名称ID")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-19-WFL_VERSION_TASK") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_VERSION_TASK_S', startValue: "10001")
        }

        createTable(tableName: "WFL_VERSION_TASK", remarks: "已发布工作流任务") {

            column(name: "VERSION_TASK_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "WFL_VERSION_TASK_PK")
            }
            column(name: "TASK_ID", type: "BIGINT", remarks: "任务ID") { constraints(nullable: "false") }
            column(name: "VERSION", type: "BIGINT", remarks: "工作流版本") { constraints(nullable: "false") }
            column(name: "PROCESS_ID", type: "BIGINT", remarks: "流程ID") { constraints(nullable: "false") }
            column(name: "TASK_CODE", type: "VARCHAR(30)", remarks: "任务代码") { constraints(nullable: "false") }
            column(name: "TASK_NAME", type: "VARCHAR(500)", remarks: "任务名ID")
            column(name: "TASK_TYPE", type: "VARCHAR(30)", remarks: "任务类型") { constraints(nullable: "false") }
            column(name: "PROCESS_PAGE_ID", type: "BIGINT", remarks: "操作页面ID")
            column(name: "VIEW_PAGE_ID", type: "BIGINT", remarks: "查看页面ID")
            column(name: "ARRIVE_PROCEDURE_ID", type: "BIGINT", remarks: "抵达任务处理过程ID")
            column(name: "DEPART_PROCEDURE_ID", type: "BIGINT", remarks: "离开任务处理过程ID")
            column(name: "RECEIVE_TYPE", type: "VARCHAR(30)", remarks: "接收类型，并行接受、串行接收") {
                constraints(nullable: "false")
            }
            column(name: "SEQUENCE", type: "BIGINT", remarks: "节点顺序，用于快速配置")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "TASK_ID,VERSION", tableName: "WFL_VERSION_TASK", constraintName: "WFL_VERSION_TASK_U1")
        addUniqueConstraint(columnNames: "PROCESS_ID,TASK_CODE,VERSION", tableName: "WFL_VERSION_TASK", constraintName: "WFL_VERSION_TASK_U2")
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-19-WFL_VERSION_TASK_TL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_VERSION_TASK_TL_S', startValue: "10001")
        }

        createTable(tableName: "WFL_VERSION_TASK_TL", remarks: "已发布工作流任务多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "VERSION_TASK_ID", type: "BIGINT", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "TASK_NAME", type: "VARCHAR(500)", remarks: "任务名ID")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-19-WFL_VERSION_USER_TASK") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_VERSION_USER_TASK_S', startValue: "10001")
        }

        createTable(tableName: "WFL_VERSION_USER_TASK", remarks: "已发布工作流任务_用户任务") {

            column(name: "VERSION_TASK_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "WFL_VERSION_USER_TASK_PK")
            }
            column(name: "USER_TASK_ID", type: "BIGINT", remarks: "任务ID") { constraints(nullable: "false") }
            column(name: "TASK_ID", type: "BIGINT", remarks: "任务ID") { constraints(nullable: "false") }
            column(name: "VERSION", type: "BIGINT", remarks: "工作流版本") { constraints(nullable: "false") }
            column(name: "APPROVE_TYPE", type: "VARCHAR(30)", remarks: "审批类型，SYSCODE:WFL_APPROVE_TYPE") {
                constraints(nullable: "false")
            }
            column(name: "APPROVE_TYPE_PARAM", type: "VARCHAR(30)", remarks: "审批类型参数")
            column(name: "CAN_SUBMITTER_RECHECK", type: "VARCHAR(1)", remarks: "提交人是否需要审批") {
                constraints(nullable: "false")
            }
            column(name: "CAN_REPEATED_APPROVE", type: "VARCHAR(1)", remarks: "是否需要重复审批") {
                constraints(nullable: "false")
            }
            column(name: "CAN_NO_APPROVER", type: "VARCHAR(1)", remarks: "是否可以无审批人") { constraints(nullable: "false") }
            column(name: "CAN_ADD_APPROVER", type: "VARCHAR(1)", remarks: "是否可以添加审批人") {
                constraints(nullable: "false")
            }
            column(name: "CAN_DELIVER", type: "VARCHAR(1)", remarks: "是否可以转交") { constraints(nullable: "false") }
            column(name: "CAN_ADD_NOTIFICATION", type: "VARCHAR(1)", remarks: "是否可以添加抄送人") {
                constraints(nullable: "false")
            }
            column(name: "CAN_TAKE_BACK", type: "VARCHAR(1)", remarks: "是否可以收回") { constraints(nullable: "false") }
            column(name: "CAN_GO_BACK", type: "VARCHAR(1)", remarks: "是否可以退回上一节点") { constraints(nullable: "false") }
            column(name: "TIME_LIMITED_FLAG", type: "VARCHAR(1)", remarks: "是否有时间限制") { constraints(nullable: "false") }
            column(name: "LIMITED_HOURS", type: "NUMBER", remarks: "时间限制（小时）")
            column(name: "OVERTIME_NOTIFY_FLAG", type: "VARCHAR(1)", remarks: "是否需要超时发送提醒") {
                constraints(nullable: "false")
            }
            column(name: "OVERTIME_NOTIFY_SCHEDULER_ID", type: "BIGINT", remarks: "超时提醒计划ID")
            column(name: "OVERTIME_NOTIFY_MSG_TPLT_ID", type: "BIGINT", remarks: "超时提醒消息模板ID")
            column(name: "ARRIVE_NOTIFY_FLAG", type: "VARCHAR(1)", remarks: "抵达任务是否需要提醒") {
                constraints(nullable: "false")
            }
            column(name: "ARRIVE_MSG_TPLT_ID", type: "BIGINT", remarks: "抵达任务提醒消息模板ID")
            column(name: "POST_NOTIFY_FLAG", type: "VARCHAR(1)", remarks: "通过任务是否需要提醒") {
                constraints(nullable: "false")
            }
            column(name: "POST_MSG_TPLT_ID", type: "BIGINT", remarks: "通过任务提醒消息ID")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "VERSION,TASK_ID", tableName: "WFL_VERSION_USER_TASK", constraintName: "WFL_VERSION_USER_TASK_U1")
        addUniqueConstraint(columnNames: "VERSION,USER_TASK_ID", tableName: "WFL_VERSION_USER_TASK", constraintName: "WFL_VERSION_USER_TASK_U2")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-19-WFL_VER_GTW_BIZ_RULE_DETAIL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_VER_GTW_BIZ_RULE_DETAIL_S', startValue: "10001")
        }

        createTable(tableName: "WFL_VER_GTW_BIZ_RULE_DETAIL", remarks: "已发布工作流网关权限规则分配明细") {

            column(name: "VERSION_DETAIL_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "WFL_VER_GTW_BIZ_RULE_DETAIL_PK")
            }
            column(name: "DETAIL_ID", type: "BIGINT", remarks: "网关规则权限明细ID") { constraints(nullable: "false") }
            column(name: "VERSION", type: "BIGINT", remarks: "工作流版本") { constraints(nullable: "false") }
            column(name: "GROUP_ID", type: "BIGINT", remarks: "权限组ID") { constraints(nullable: "false") }
            column(name: "BUSINESS_RULE_ID", type: "BIGINT", remarks: "权限规则ID") { constraints(nullable: "false") }
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "WFL_VER_GTW_BIZ_RULE_DETAIL", indexName: "WFL_VER_GTW_BIZ_RULE_DETAIL_N1") {
            column(name: "VERSION_DETAIL_ID")
        }
        createIndex(tableName: "WFL_VER_GTW_BIZ_RULE_DETAIL", indexName: "WFL_VER_GTW_BIZ_RULE_DETAIL_N2") {
            column(name: "VERSION")
            column(name: "GROUP_ID")
        }

        addUniqueConstraint(columnNames: "VERSION,DETAIL_ID", tableName: "WFL_VER_GTW_BIZ_RULE_DETAIL", constraintName: "WFL_VER_GTW_BIZ_RULE_DETAIL_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-19-WFL_VER_GTW_BIZ_RULE_GROUP") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_VER_GTW_BIZ_RULE_GROUP_S', startValue: "10001")
        }

        createTable(tableName: "WFL_VER_GTW_BIZ_RULE_GROUP", remarks: "已发布工作流网关权限规则组") {

            column(name: "VERSION_GROUP_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "WFL_VER_GTW_BIZ_RULE_GROUP_PK")
            }
            column(name: "GROUP_ID", type: "BIGINT", remarks: "网关权限规则组ID") { constraints(nullable: "false") }
            column(name: "VERSION", type: "BIGINT", remarks: "工作流版本") { constraints(nullable: "false") }
            column(name: "GATEWAY_ID", type: "BIGINT", remarks: "网关ID") { constraints(nullable: "false") }
            column(name: "SEQUECNE", type: "BIGINT", remarks: "顺序") { constraints(nullable: "false") }
            column(name: "GROUP_CODE", type: "VARCHAR(30)", remarks: "权限组代码") { constraints(nullable: "false") }
            column(name: "GROUP_NAME", type: "VARCHAR(500)", remarks: "权限组名称ID")
            column(name: "FLOW_ID", type: "BIGINT", remarks: "流ID") { constraints(nullable: "false") }
            column(name: "ADJUST_TYPE", type: "VARCHAR(30)", remarks: "权限明细判断类型") { constraints(nullable: "false") }
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "GROUP_ID,VERSION", tableName: "WFL_VER_GTW_BIZ_RULE_GROUP", constraintName: "WFL_VER_GTW_BIZ_RULE_GROUP_U1")
        addUniqueConstraint(columnNames: "GATEWAY_ID,GROUP_CODE,VERSION", tableName: "WFL_VER_GTW_BIZ_RULE_GROUP", constraintName: "WFL_VER_GTW_BIZ_RULE_GROUP_U2")
        addUniqueConstraint(columnNames: "GATEWAY_ID,SEQUECNE,VERSION", tableName: "WFL_VER_GTW_BIZ_RULE_GROUP", constraintName: "WFL_VER_GTW_BIZ_RULE_GROUP_U3")
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-19-WFL_VER_GTW_BIZ_RULE_GROUP_TL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_VER_GTW_BIZ_RULE_GROUP_TL_S', startValue: "10001")
        }

        createTable(tableName: "WFL_VER_GTW_BIZ_RULE_GROUP_TL", remarks: "已发布工作流网关权限规则组多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "VERSION_GROUP_ID", type: "BIGINT", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "GROUP_NAME", type: "VARCHAR(500)", remarks: "权限组名称ID")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-19-WFL_VER_SUB_PROCESS_TASK") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_VER_SUB_PROCESS_TASK_S', startValue: "10001")
        }

        createTable(tableName: "WFL_VER_SUB_PROCESS_TASK", remarks: "已发布工作流任务_子流程") {

            column(name: "VERSION_TASK_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "WFL_VER_SUB_PROCESS_TASK_PK")
            }
            column(name: "SUB_PROCESS_TASK_ID", type: "BIGINT", remarks: "子流程任务ID") { constraints(nullable: "false") }
            column(name: "TASK_ID", type: "BIGINT", remarks: "任务ID") { constraints(nullable: "false") }
            column(name: "VERSION", type: "BIGINT", remarks: "工作流版本") { constraints(nullable: "false") }
            column(name: "PROCESS_ID", type: "BIGINT", remarks: "流程ID") { constraints(nullable: "false") }
            column(name: "TASK_AUTO_PASS_FLAG", type: "VARCHAR(1)", remarks: "节点自动通过标志") {
                constraints(nullable: "false")
            }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "VERSION,TASK_ID", tableName: "WFL_VER_SUB_PROCESS_TASK", constraintName: "WFL_VER_SUB_PROCESS_TASK_U1")
        addUniqueConstraint(columnNames: "VERSION,SUB_PROCESS_TASK_ID", tableName: "WFL_VER_SUB_PROCESS_TASK", constraintName: "WFL_VER_SUB_PROCESS_TASK_U2")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-19-WFL_VER_TASK_RCV_BIZ_DETAIL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_VER_TASK_RCV_BIZ_DETAIL_S', startValue: "10001")
        }

        createTable(tableName: "WFL_VER_TASK_RCV_BIZ_DETAIL", remarks: "已发布工作流任务接收者权限规则分配明细") {

            column(name: "VERSION_DETAIL_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "WFL_VER_TASK_RCV_BIZ_DETAIL_PK")
            }
            column(name: "DETAIL_ID", type: "BIGINT", remarks: "接受者权限规则分配明细ID") { constraints(nullable: "false") }
            column(name: "VERSION", type: "BIGINT", remarks: "工作流版本") { constraints(nullable: "false") }
            column(name: "RECEIVER_ID", type: "BIGINT", remarks: "接受者ID") { constraints(nullable: "false") }
            column(name: "BUSINESS_RULE_ID", type: "BIGINT", remarks: "权限规则ID") { constraints(nullable: "false") }
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "WFL_VER_TASK_RCV_BIZ_DETAIL", indexName: "WFL_VER_TASK_RCV_BIZ_DETAIL_N1") {
            column(name: "RECEIVER_ID")
            column(name: "VERSION")
        }

        addUniqueConstraint(columnNames: "DETAIL_ID,VERSION", tableName: "WFL_VER_TASK_RCV_BIZ_DETAIL", constraintName: "WFL_VER_TASK_RCV_BIZ_DETAIL_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-19-WFL_VER_TASK_RECEIVER") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_VER_TASK_RECEIVER_S', startValue: "10001")
        }

        createTable(tableName: "WFL_VER_TASK_RECEIVER", remarks: "已发布工作流任务接收者") {

            column(name: "VERSION_RECEIVER_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "WFL_VER_TASK_RECEIVER_PK")
            }
            column(name: "RECEIVER_ID", type: "BIGINT", remarks: "接受者ID") { constraints(nullable: "false") }
            column(name: "VERSION", type: "BIGINT", remarks: "工作流版本") { constraints(nullable: "false") }
            column(name: "TASK_ID", type: "BIGINT", remarks: "任务ID") { constraints(nullable: "false") }
            column(name: "RECEIVER_CATEGORY", type: "VARCHAR(200)", remarks: "接收者类别") { constraints(nullable: "false") }
            column(name: "RECEIVER_TYPE", type: "VARCHAR(30)", remarks: "接收者类型")
            column(name: "SEQUENCE", type: "BIGINT", remarks: "接收者序号") { constraints(nullable: "false") }
            column(name: "RECEIVER_PARAM1_ID", type: "BIGINT", remarks: "接收者参数1ID")
            column(name: "RECEIVER_PARAM1_CODE", type: "VARCHAR(200)", remarks: "接收者参数1代码")
            column(name: "RECEIVER_PARAM2_ID", type: "BIGINT", remarks: "接收者参数2ID")
            column(name: "RECEIVER_PARAM2_CODE", type: "VARCHAR(200)", remarks: "接收者参数2代码")
            column(name: "RECEIVER_PARAM3_ID", type: "BIGINT", remarks: "接收者参数3ID")
            column(name: "RECEIVER_PARAM3_CODE", type: "VARCHAR(200)", remarks: "接收者参数3代码")
            column(name: "RECEIVER_PARAM4_ID", type: "BIGINT", remarks: "接收者参数4ID")
            column(name: "RECEIVER_PARAM4_CODE", type: "VARCHAR(200)", remarks: "接收者参数4代码")
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "VERSION,RECEIVER_ID", tableName: "WFL_VER_TASK_RECEIVER", constraintName: "WFL_VER_TASK_RECEIVER_U1")
    }



    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-18-WFL_INSTANCE") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_INSTANCE_S', startValue: "10001")
        }

        createTable(tableName: "WFL_INSTANCE", remarks: "工作流实例") {

            column(name: "INSTANCE_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "WFL_INSTANCE_PK")
            }
            column(name: "PROCESS_ID", type: "BIGINT", remarks: "流程ID") { constraints(nullable: "false") }
            column(name: "VERSION", type: "BIGINT", remarks: "版本") { constraints(nullable: "false") }
            column(name: "STATUS", type: "VARCHAR(30)", remarks: "状态") { constraints(nullable: "false") }
            column(name: "DOC_CATEGORY", type: "VARCHAR(30)", remarks: "单据类别") { constraints(nullable: "false") }
            column(name: "DOC_TYPE_ID", type: "BIGINT", remarks: "单据类型") { constraints(nullable: "false") }
            column(name: "DOC_ID", type: "VARCHAR(200)", remarks: "单据ID") { constraints(nullable: "false") }
            column(name: "START_DATE", type: "DATETIME", remarks: "开始日期")
            column(name: "END_DATE", type: "DATETIME", remarks: "结束日期")
            column(name: "INSTANCE_DESC", type: "VARCHAR(2000)", remarks: "工作流实例描述")
            column(name: "DOC_AMOUNT", type: "NUMBER", remarks: "单据金额")
            column(name: "DOC_NUMBER", type: "VARCHAR(30)", remarks: "单据编号")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "WFL_INSTANCE", indexName: "WFL_INSTANCE_N1") {
            column(name: "PROCESS_ID")
        }
        createIndex(tableName: "WFL_INSTANCE", indexName: "WFL_INSTANCE_N2") {
            column(name: "DOC_CATEGORY")
            column(name: "DOC_ID")
        }
        createIndex(tableName: "WFL_INSTANCE", indexName: "WFL_INSTANCE_N3") {
            column(name: "DOC_NUMBER")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-18-WFL_INS_EVENT") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_INS_EVENT_S', startValue: "10001")
        }

        createTable(tableName: "WFL_INS_EVENT", remarks: "工作流事件实例") {

            column(name: "INS_EVENT_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "WFL_INS_EVENT_PK")
            }
            column(name: "INSTANCE_ID", type: "BIGINT", remarks: "工作流实例ID") { constraints(nullable: "false") }
            column(name: "EVENT_ID", type: "BIGINT", remarks: "事件ID") { constraints(nullable: "false") }
            column(name: "RESULT", type: "VARCHAR(200)", remarks: "执行结果")
            column(name: "ARRIVAL_STATUS", type: "VARCHAR(30)", remarks: "抵达状态") { constraints(nullable: "false") }
            column(name: "ARRIVAL_DATE", type: "DATETIME", remarks: "抵达日期")
            column(name: "DEPARTURE_DATE", type: "DATETIME", remarks: "结束日期")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "WFL_INS_EVENT", indexName: "WFL_INS_EVENT_N1") {
            column(name: "INSTANCE_ID")
        }
        createIndex(tableName: "WFL_INS_EVENT", indexName: "WFL_INS_EVENT_N2") {
            column(name: "EVENT_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-18-WFL_INS_FLOW") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_INS_FLOW_S', startValue: "10001")
        }

        createTable(tableName: "WFL_INS_FLOW", remarks: "工作流流向实例") {

            column(name: "INS_FLOW_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "WFL_INS_FLOW_PK")
            }
            column(name: "INSTANCE_ID", type: "BIGINT", remarks: "工作流实例ID")
            column(name: "FLOW_ID", type: "BIGINT", remarks: "流向ID")
            column(name: "FROM_ELEMENT_TYPE", type: "VARCHAR(30)", remarks: "来源元素类别")
            column(name: "FROM_ELEMENT_ID", type: "BIGINT", remarks: "来源元素ID")
            column(name: "TO_ELEMENT_TYPE", type: "VARCHAR(30)", remarks: "目标元素类别")
            column(name: "TO_ELEMENT_ID", type: "BIGINT", remarks: "目标元素ID")
            column(name: "RESULT", type: "VARCHAR(200)", remarks: "执行结果")
            column(name: "ARRIVAL_STATUS", type: "VARCHAR(30)", remarks: "抵达状态")
            column(name: "ARRIVAL_DATE", type: "DATETIME", remarks: "抵达日期")
            column(name: "DEPARTURE_DATE", type: "DATETIME", remarks: "离开日期")
            column(name: "SEQUENCE", type: "BIGINT", remarks: "流程顺序，用于网关后的顺序判断")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "WFL_INS_FLOW", indexName: "WFL_INS_FLOW_N1") {
            column(name: "INSTANCE_ID")
        }
        createIndex(tableName: "WFL_INS_FLOW", indexName: "WFL_INS_FLOW_N2") {
            column(name: "FLOW_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-18-WFL_INS_GATEWAY") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_INS_GATEWAY_S', startValue: "10001")
        }

        createTable(tableName: "WFL_INS_GATEWAY", remarks: "工作流网关实例") {

            column(name: "INS_GATEWAY_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "WFL_INS_GATEWAY_PK")
            }
            column(name: "INSTANCE_ID", type: "BIGINT", remarks: "工作流实例ID") { constraints(nullable: "false") }
            column(name: "GATEWAY_ID", type: "BIGINT", remarks: "网关ID") { constraints(nullable: "false") }
            column(name: "RESULT", type: "VARCHAR(200)", remarks: "结果")
            column(name: "ARRIVAL_STATUS", type: "VARCHAR(30)", remarks: "抵达状态") { constraints(nullable: "false") }
            column(name: "ARRIVAL_DATE", type: "DATETIME", remarks: "抵达日期")
            column(name: "DEPARTURE_DATE", type: "DATETIME", remarks: "离开日期")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "WFL_INS_GATEWAY", indexName: "WFL_INS_GATEWAY_N1") {
            column(name: "INSTANCE_ID")
        }
        createIndex(tableName: "WFL_INS_GATEWAY", indexName: "WFL_INS_GATEWAY_N2") {
            column(name: "GATEWAY_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-18-WFL_INS_TASK") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_INS_TASK_S', startValue: "10001")
        }

        createTable(tableName: "WFL_INS_TASK", remarks: "工作流任务实例") {

            column(name: "INS_TASK_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "WFL_INS_TASK_PK")
            }
            column(name: "INSTANCE_ID", type: "BIGINT", remarks: "实例ID") { constraints(nullable: "false") }
            column(name: "TASK_ID", type: "BIGINT", remarks: "任务ID") { constraints(nullable: "false") }
            column(name: "RESULT", type: "VARCHAR(200)", remarks: "结果")
            column(name: "ARRIVAL_STATUS", type: "VARCHAR(30)", remarks: "抵达状态") { constraints(nullable: "false") }
            column(name: "ARRIVAL_DATE", type: "DATETIME", remarks: "抵达日期")
            column(name: "DEPARTURE_DATE", type: "DATETIME", remarks: "离开日期")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "WFL_INS_TASK", indexName: "WFL_INS_TASK_N1") {
            column(name: "INSTANCE_ID")
        }
        createIndex(tableName: "WFL_INS_TASK", indexName: "WFL_INS_TASK_N2") {
            column(name: "TASK_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-18-WFL_INS_TASK_RECEIVER") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_INS_TASK_RECEIVER_S', startValue: "10001")
        }

        createTable(tableName: "WFL_INS_TASK_RECEIVER", remarks: "工作流实例任务接收者") {

            column(name: "INS_RECEIVER_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "WFL_INS_TASK_RECEIVER_PK")
            }
            column(name: "INSTANCE_ID", type: "BIGINT", remarks: "工作流实例ID") { constraints(nullable: "false") }
            column(name: "INS_TASK_ID", type: "BIGINT", remarks: "任务ID") { constraints(nullable: "false") }
            column(name: "RECEIVER_ID", type: "BIGINT", remarks: "接收者ID") { constraints(nullable: "false") }
            column(name: "RECEIVER_CATEGORY", type: "VARCHAR(200)", remarks: "接受者类别") { constraints(nullable: "false") }
            column(name: "RECEIVER_TYPE", type: "VARCHAR(30)", remarks: "接收者类型")
            column(name: "SEQUENCE", type: "BIGINT", remarks: "接收者序号") { constraints(nullable: "false") }
            column(name: "RECEIVER_PARAM1_ID", type: "BIGINT", remarks: "接收者参数1ID")
            column(name: "RECEIVER_PARAM1_CODE", type: "VARCHAR(200)", remarks: "接收者参数1代码")
            column(name: "RECEIVER_PARAM2_ID", type: "BIGINT", remarks: "接收者参数2ID")
            column(name: "RECEIVER_PARAM2_CODE", type: "VARCHAR(200)", remarks: "接收者参数2代码")
            column(name: "RECEIVER_PARAM3_ID", type: "BIGINT", remarks: "接收者参数3ID")
            column(name: "RECEIVER_PARAM3_CODE", type: "VARCHAR(200)", remarks: "接收者参数3代码")
            column(name: "RECEIVER_PARAM4_ID", type: "BIGINT", remarks: "接收者参数4ID")
            column(name: "RECEIVER_PARAM4_CODE", type: "VARCHAR(200)", remarks: "接收者参数4代码")
            column(name: "POST_VALIDATION_FLAG", type: "VARCHAR(1)", remarks: "通过校验标志") {
                constraints(nullable: "false")
            }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "WFL_INS_TASK_RECEIVER", indexName: "WFL_INS_TASK_RECEIVER_N1") {
            column(name: "INSTANCE_ID")
        }
        createIndex(tableName: "WFL_INS_TASK_RECEIVER", indexName: "WFL_INS_TASK_RECEIVER_N2") {
            column(name: "INS_TASK_ID")
        }
        createIndex(tableName: "WFL_INS_TASK_RECEIVER", indexName: "WFL_INS_TASK_RECEIVER_N3") {
            column(name: "RECEIVER_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-18-WFL_INS_TASK_RECEIVER_HT") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_INS_TASK_RECEIVER_HT_S', startValue: "10001")
        }

        createTable(tableName: "WFL_INS_TASK_RECEIVER_HT", remarks: "") {

            column(name: "INS_RECEIVER_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "WFL_INS_TASK_RECEIVER_HT_PK")
            }
            column(name: "INSTANCE_ID", type: "BIGINT", remarks: "工作流实例ID") { constraints(nullable: "false") }
            column(name: "INS_TASK_ID", type: "BIGINT", remarks: "任务ID") { constraints(nullable: "false") }
            column(name: "RECEIVER_ID", type: "BIGINT", remarks: "接收者ID") { constraints(nullable: "false") }
            column(name: "RECEIVER_CATEGORY", type: "VARCHAR(200)", remarks: "接受者类别") { constraints(nullable: "false") }
            column(name: "RECEIVER_TYPE", type: "VARCHAR(30)", remarks: "接收者类型")
            column(name: "SEQUENCE", type: "BIGINT", remarks: "接收者序号") { constraints(nullable: "false") }
            column(name: "RECEIVER_PARAM1_ID", type: "BIGINT", remarks: "接收者参数1ID")
            column(name: "RECEIVER_PARAM1_CODE", type: "VARCHAR(200)", remarks: "接收者参数1代码")
            column(name: "RECEIVER_PARAM2_ID", type: "BIGINT", remarks: "接收者参数2ID")
            column(name: "RECEIVER_PARAM2_CODE", type: "VARCHAR(200)", remarks: "接收者参数2代码")
            column(name: "RECEIVER_PARAM3_ID", type: "BIGINT", remarks: "接收者参数3ID")
            column(name: "RECEIVER_PARAM3_CODE", type: "VARCHAR(200)", remarks: "接收者参数3代码")
            column(name: "RECEIVER_PARAM4_ID", type: "BIGINT", remarks: "接收者参数4ID")
            column(name: "RECEIVER_PARAM4_CODE", type: "VARCHAR(200)", remarks: "接收者参数4代码")
            column(name: "POST_VALIDATION_FLAG", type: "VARCHAR(1)", remarks: "通过校验标志") {
                constraints(nullable: "false")
            }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "WFL_INS_TASK_RECEIVER_HT", indexName: "WFL_INS_TASK_RECEIVER_HT_N1") {
            column(name: "INSTANCE_ID")
        }
        createIndex(tableName: "WFL_INS_TASK_RECEIVER_HT", indexName: "WFL_INS_TASK_RECEIVER_HT_N2") {
            column(name: "INS_TASK_ID")
        }
        createIndex(tableName: "WFL_INS_TASK_RECEIVER_HT", indexName: "WFL_INS_TASK_RECEIVER_HT_N3") {
            column(name: "RECEIVER_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-18-WFL_INS_TASK_HIERARCHY") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_INS_TASK_HIERARCHY_S', startValue: "10001")
        }

        createTable(tableName: "WFL_INS_TASK_HIERARCHY", remarks: "工作流实例任务接收者层次") {

            column(name: "INS_HIERARCHY_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "WFL_INS_TASK_HIERARCHY_PK")
            }
            column(name: "INSTANCE_ID", type: "BIGINT", remarks: "工作流实例ID") { constraints(nullable: "false") }
            column(name: "INS_TASK_ID", type: "BIGINT", remarks: "工作流实例任务ID") { constraints(nullable: "false") }
            column(name: "INS_RECEIVER_ID", type: "BIGINT", remarks: "工作流实例接收者ID")
            column(name: "SEQUENCE", type: "BIGINT", remarks: "序号") { constraints(nullable: "false") }
            column(name: "APPROVER_ID", type: "BIGINT", remarks: "审批者ID") { constraints(nullable: "false") }
            column(name: "POSTED_FLAG", type: "VARCHAR(1)", remarks: "已审批标志") { constraints(nullable: "false") }
            column(name: "DISABLED_FLAG", type: "VARCHAR(1)", remarks: "失效标志") { constraints(nullable: "false") }
            column(name: "PARENT_INS_HIERARCHY_ID", type: "BIGINT", remarks: "父接受者层次ID")
            column(name: "NOTE", type: "VARCHAR(2000)", remarks: "备注")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "WFL_INS_TASK_HIERARCHY", indexName: "WFL_INS_TASK_HIERARCHY_N1") {
            column(name: "INSTANCE_ID")
        }
        createIndex(tableName: "WFL_INS_TASK_HIERARCHY", indexName: "WFL_INS_TASK_HIERARCHY_N2") {
            column(name: "INS_TASK_ID")
        }
        createIndex(tableName: "WFL_INS_TASK_HIERARCHY", indexName: "WFL_INS_TASK_HIERARCHY_N3") {
            column(name: "INS_RECEIVER_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-18-WFL_INS_TASK_HIERARCHY_HT") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_INS_TASK_HIERARCHY_HT_S', startValue: "10001")
        }

        createTable(tableName: "WFL_INS_TASK_HIERARCHY_HT", remarks: "") {

            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "INS_HIERARCHY_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "WFL_INS_TASK_HIERARCHY_HT_PK")
            }
            column(name: "INSTANCE_ID", type: "BIGINT", remarks: "工作流实例ID") { constraints(nullable: "false") }
            column(name: "INS_TASK_ID", type: "BIGINT", remarks: "工作流实例任务ID") { constraints(nullable: "false") }
            column(name: "INS_RECEIVER_ID", type: "BIGINT", remarks: "工作流实例接收者ID")
            column(name: "SEQUENCE", type: "BIGINT", remarks: "序号") { constraints(nullable: "false") }
            column(name: "APPROVER_ID", type: "BIGINT", remarks: "审批者ID") { constraints(nullable: "false") }
            column(name: "POSTED_FLAG", type: "VARCHAR(1)", remarks: "已审批标志") { constraints(nullable: "false") }
            column(name: "DISABLED_FLAG", type: "VARCHAR(1)", remarks: "失效标志") { constraints(nullable: "false") }
            column(name: "PARENT_INS_HIERARCHY_ID", type: "BIGINT", remarks: "父接受者层次ID")
            column(name: "NOTE", type: "VARCHAR(2000)", remarks: "备注")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "WFL_INS_TASK_HIERARCHY_HT", indexName: "WFL_INS_TASK_HIERARCHY_HT_N1") {
            column(name: "INSTANCE_ID")
        }
        createIndex(tableName: "WFL_INS_TASK_HIERARCHY_HT", indexName: "WFL_INS_TASK_HIERARCHY_HT_N2") {
            column(name: "INS_TASK_ID")
        }
        createIndex(tableName: "WFL_INS_TASK_HIERARCHY_HT", indexName: "WFL_INS_TASK_HIERARCHY_HT_N3") {
            column(name: "INS_RECEIVER_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-18-WFL_INS_TASK_RECIPIENT") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_INS_TASK_RECIPIENT_S', startValue: "10001")
        }

        createTable(tableName: "WFL_INS_TASK_RECIPIENT", remarks: "工作流实例当前审批人") {

            column(name: "INS_RECIPIENT_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "WFL_INS_TASK_RECIPIENT_PK")
            }
            column(name: "INSTANCE_ID", type: "BIGINT", remarks: "工作流实例ID") { constraints(nullable: "false") }
            column(name: "INS_TASK_ID", type: "BIGINT", remarks: "工作流实例任务ID") { constraints(nullable: "false") }
            column(name: "INS_HIERARCHY_ID", type: "BIGINT", remarks: "工作流实例审批层次ID") { constraints(nullable: "false") }
            column(name: "SEQUENCE", type: "BIGINT", remarks: "顺序") { constraints(nullable: "false") }
            column(name: "APPROVER_ID", type: "BIGINT", remarks: "审批者ID") { constraints(nullable: "false") }
            column(name: "COMMISSION_BY", type: "BIGINT", remarks: "转交来源")
            column(name: "COMMISSION_DATE", type: "DATETIME", remarks: "转交日期")
            column(name: "LIMITED_DATE", type: "DATETIME", remarks: "时限日期")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "WFL_INS_TASK_RECIPIENT", indexName: "WFL_INS_TASK_RECIPIENT_N1") {
            column(name: "INSTANCE_ID")
        }
        createIndex(tableName: "WFL_INS_TASK_RECIPIENT", indexName: "WFL_INS_TASK_RECIPIENT_N2") {
            column(name: "INS_TASK_ID")
        }
        createIndex(tableName: "WFL_INS_TASK_RECIPIENT", indexName: "WFL_INS_TASK_RECIPIENT_N3") {
            column(name: "INS_HIERARCHY_ID")
        }
        createIndex(tableName: "WFL_INS_TASK_RECIPIENT", indexName: "WFL_INS_TASK_RECIPIENT_N4") {
            column(name: "APPROVER_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-18-WFL_INS_TASK_RECIPIENT_HT") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_INS_TASK_RECIPIENT_HT_S', startValue: "10001")
        }

        createTable(tableName: "WFL_INS_TASK_RECIPIENT_HT", remarks: "") {

            column(name: "INS_RECIPIENT_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "WFL_INS_TASK_RECIPIENT_HT_PK")
            }
            column(name: "INSTANCE_ID", type: "BIGINT", remarks: "工作流实例ID") { constraints(nullable: "false") }
            column(name: "INS_TASK_ID", type: "BIGINT", remarks: "工作流实例任务ID") { constraints(nullable: "false") }
            column(name: "INS_HIERARCHY_ID", type: "BIGINT", remarks: "工作流实例审批层次ID") { constraints(nullable: "false") }
            column(name: "SEQUENCE", type: "BIGINT", remarks: "顺序") { constraints(nullable: "false") }
            column(name: "APPROVER_ID", type: "BIGINT", remarks: "审批者ID") { constraints(nullable: "false") }
            column(name: "COMMISSION_BY", type: "BIGINT", remarks: "转交来源")
            column(name: "COMMISSION_DATE", type: "DATETIME", remarks: "转交日期")
            column(name: "LIMITED_DATE", type: "DATETIME", remarks: "时限日期")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "WFL_INS_TASK_RECIPIENT_HT", indexName: "WFL_INS_TASK_RECIPIENT_HT_N1") {
            column(name: "INSTANCE_ID")
        }
        createIndex(tableName: "WFL_INS_TASK_RECIPIENT_HT", indexName: "WFL_INS_TASK_RECIPIENT_HT_N2") {
            column(name: "INS_TASK_ID")
        }
        createIndex(tableName: "WFL_INS_TASK_RECIPIENT_HT", indexName: "WFL_INS_TASK_RECIPIENT_HT_N3") {
            column(name: "INS_HIERARCHY_ID")
        }
        createIndex(tableName: "WFL_INS_TASK_RECIPIENT_HT", indexName: "WFL_INS_TASK_RECIPIENT_HT_N4") {
            column(name: "APPROVER_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-18-WFL_APPROVE_RECORD") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_APPROVE_RECORD_S', startValue: "10001")
        }

        createTable(tableName: "WFL_APPROVE_RECORD", remarks: "工作流审批记录") {

            column(name: "RECORD_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "WFL_APPROVE_RECORD_PK")
            }
            column(name: "INSTANCE_ID", type: "BIGINT", remarks: "工作流实例ID") { constraints(nullable: "false") }
            column(name: "INS_TASK_ID", type: "BIGINT", remarks: "任务ID") { constraints(nullable: "false") }
            column(name: "ACTION_ID", type: "BIGINT", remarks: "动作ID")
            column(name: "ACTION_TYPE", type: "VARCHAR(30)", remarks: "动作类型") { constraints(nullable: "false") }
            column(name: "COMMENT_TEXT", type: "VARCHAR(2000)", remarks: "审批意见")
            column(name: "INS_RECIPIENT_ID", type: "BIGINT", remarks: "工作流待办事项ID")
            column(name: "INS_HIERARCHY_ID", type: "BIGINT", remarks: "工作流接受者层次ID")
            column(name: "APPROVER_ID", type: "BIGINT", remarks: "审批者ID") { constraints(nullable: "false") }
            column(name: "APPROVE_DATE", type: "DATETIME", remarks: "审批日期") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "WFL_APPROVE_RECORD", indexName: "WFL_APPROVE_RECORD_N1") {
            column(name: "APPROVE_DATE")
            column(name: "INSTANCE_ID")
        }
        createIndex(tableName: "WFL_APPROVE_RECORD", indexName: "WFL_APPROVE_RECORD_N2") {
            column(name: "INS_RECIPIENT_ID")
        }
        createIndex(tableName: "WFL_APPROVE_RECORD", indexName: "WFL_APPROVE_RECORD_N3") {
            column(name: "INS_HIERARCHY_ID")
        }
        createIndex(tableName: "WFL_APPROVE_RECORD", indexName: "WFL_APPROVE_RECORD_N4") {
            column(name: "APPROVER_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-18-WFL_LOG") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_LOG_S', startValue: "10001")
        }

        createTable(tableName: "WFL_LOG", remarks: "工作流日志") {

            column(name: "LOG_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "WFL_LOG_PK")
            }
            column(name: "INSTANCE_ID", type: "BIGINT", remarks: "工作流实例ID")
            column(name: "ELEMENT_CATEGORY", type: "VARCHAR(30)", remarks: "元素类别")
            column(name: "ELEMENT_ID", type: "BIGINT", remarks: "元素ID")
            column(name: "LOG_SOURCE", type: "VARCHAR(200)", remarks: "日志来源")
            column(name: "LOG_TEXT", type: "VARCHAR(4000)", remarks: "日志内容")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "WFL_LOG", indexName: "WFL_LOG_N1") {
            column(name: "INSTANCE_ID")
        }
        createIndex(tableName: "WFL_LOG", indexName: "WFL_LOG_N2") {
            column(name: "ELEMENT_ID")
            column(name: "ELEMENT_CATEGORY")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-18-WFL_PROCESS_ACTION") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_PROCESS_ACTION_S', startValue: "10001")
        }

        createTable(tableName: "WFL_PROCESS_ACTION", remarks: "流程动作") {

            column(name: "PROCESS_ID", type: "BIGINT", remarks: "")
            column(name: "ACTION_TYPE", type: "VARCHAR(30)", remarks: "动作类型") { constraints(nullable: "false") }
            column(name: "ACTION_NAME", type: "VARCHAR(500)", remarks: "动作名称ID") { constraints(nullable: "false") }
            column(name: "PROCEDURE_ID", type: "BIGINT", remarks: "调研过程ID")
            column(name: "SEND_TARGET_TASK_ID", type: "BIGINT", remarks: "跳转目标任务ID")
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "ACTION_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "WFL_PROCESS_ACTION_PK")
            }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "WFL_PROCESS_ACTION", indexName: "WFL_PROCESS_ACTION_N1") {
            column(name: "PROCESS_ID")
        }

    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-18-WFL_PROCESS_ACTION_TL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_PROCESS_ACTION_TL_S', startValue: "10001")
        }

        createTable(tableName: "WFL_PROCESS_ACTION_TL", remarks: "流程动作多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "ACTION_NAME", type: "VARCHAR(500)", remarks: "动作名称ID")
            column(name: "ACTION_ID", type: "BIGINT", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-18-WFL_TASK_ACTION") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_TASK_ACTION_S', startValue: "10001")
        }

        createTable(tableName: "WFL_TASK_ACTION", remarks: "任务动作") {

            column(name: "TASK_ACTION_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "WFL_TASK_ACTION_PK")
            }
            column(name: "TASK_ID", type: "BIGINT", remarks: "任务ID") { constraints(nullable: "false") }
            column(name: "ACTION_ID", type: "BIGINT", remarks: "动作ID") { constraints(nullable: "false") }
            column(name: "SEQUENCE", type: "BIGINT", remarks: "顺序") { constraints(nullable: "false") }
            column(name: "PROCEDURE_ID", type: "BIGINT", remarks: "过程ID")
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "ACTION_ID,TASK_ID", tableName: "WFL_TASK_ACTION", constraintName: "WFL_TASK_ACTION_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-18-WFL_DELIVER") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_DELIVER_S', startValue: "10001")
        }

        createTable(tableName: "WFL_DELIVER", remarks: "工作流转交") {

            column(name: "DELIVER_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "WFL_DELIVER_PK")
            }
            column(name: "DOC_CATEGORY", type: "VARCHAR(30)", remarks: "单据类别") { constraints(nullable: "false") }
            column(name: "PROCESS_ID", type: "BIGINT", remarks: "流程ID")
            column(name: "FROM_USER_ID", type: "BIGINT", remarks: "原审批人") { constraints(nullable: "false") }
            column(name: "TO_USER_ID", type: "BIGINT", remarks: "转交人") { constraints(nullable: "false") }
            column(name: "DATE_FROM", type: "DATETIME", remarks: "日期从") { constraints(nullable: "false") }
            column(name: "DATE_TO", type: "DATETIME", remarks: "日期到")
            column(name: "DESCRIPTION", type: "VARCHAR(2000)", remarks: "描述")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "WFL_DELIVER", indexName: "WFL_DELIVER_N1") {
            column(name: "FROM_USER_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-18-WFL_DELIVER_BIZ_RULE_ASSIGN") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_DELIVER_BIZ_RULE_ASSIGN_S', startValue: "10001")
        }

        createTable(tableName: "WFL_DELIVER_BIZ_RULE_ASSIGN", remarks: "工作流转交分配权限规则") {

            column(name: "ASSIGN_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "WFL_DELIVER_BIZ_RULE_ASSIGN_PK")
            }
            column(name: "DELIVER_ID", type: "BIGINT", remarks: "转交ID") { constraints(nullable: "false") }
            column(name: "BUSINESS_RULE_ID", type: "BIGINT", remarks: "业务权限规则ID") { constraints(nullable: "false") }
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "WFL_DELIVER_BIZ_RULE_ASSIGN", indexName: "WFL_DELIVER_BIZ_RULE_ASSIGN_N1") {
            column(name: "DELIVER_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-18-WFL_DOC_PROCESS_ASSIGN") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_DOC_PROCESS_ASSIGN_S', startValue: "10001")
        }

        createTable(tableName: "WFL_DOC_PROCESS_ASSIGN", remarks: "单据工作流分配") {

            column(name: "ASSIGN_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "WFL_DOC_PROCESS_ASSIGN_PK")
            }
            column(name: "PRIORITY", type: "BIGINT", remarks: "优先级") { constraints(nullable: "false") }
            column(name: "DOC_CATEGORY", type: "VARCHAR(30)", remarks: "单据类别") { constraints(nullable: "false") }
            column(name: "DOC_TYPE_ID", type: "BIGINT", remarks: "单据类型ID")
            column(name: "PROCESS_ID", type: "BIGINT", remarks: "流程ID")
            column(name: "START_DATE", type: "DATETIME", remarks: "开始日期") { constraints(nullable: "false") }
            column(name: "END_DATE", type: "DATETIME", remarks: "结束日期")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "WFL_DOC_PROCESS_ASSIGN", indexName: "WFL_DOC_PROCESS_ASSIGN_N1") {
            column(name: "DOC_CATEGORY")
            column(name: "DOC_TYPE_ID")
        }
        createIndex(tableName: "WFL_DOC_PROCESS_ASSIGN", indexName: "WFL_DOC_PROCESS_ASSIGN_N2") {
            column(name: "PROCESS_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-18-WFL_DOC_BIZ_RULE_ASSIGN") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_DOC_BIZ_RULE_ASSIGN_S', startValue: "10001")
        }

        createTable(tableName: "WFL_DOC_BIZ_RULE_ASSIGN", remarks: "单据工作流分配权限分配") {

            column(name: "ASSIGN_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "WFL_DOC_BIZ_RULE_ASSIGN_PK")
            }
            column(name: "DOC_PROCESS_ASSIGN_ID", type: "BIGINT", remarks: "单据流程分配ID")
            column(name: "BUSINESS_RULE_ID", type: "BIGINT", remarks: "权限规则ID")
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "WFL_DOC_BIZ_RULE_ASSIGN", indexName: "WFL_DOC_BIZ_RULE_ASSIGN_N1") {
            column(name: "DOC_PROCESS_ASSIGN_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-18-WFL_PAGE") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_PAGE_S', startValue: "10001")
        }

        createTable(tableName: "WFL_PAGE", remarks: "工作流页面") {

            column(name: "PAGE_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "WFL_PAGE_PK")
            }
            column(name: "PAGE_CODE", type: "VARCHAR(30)", remarks: "页面代码") { constraints(nullable: "false") }
            column(name: "PAGE_NAME", type: "VARCHAR(500)", remarks: "页面名称ID")
            column(name: "RESOURCE_ID", type: "BIGINT", remarks: "资源ID") { constraints(nullable: "false") }
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-18-WFL_PAGE_TL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_PAGE_TL_S', startValue: "10001")
        }

        createTable(tableName: "WFL_PAGE_TL", remarks: "工作流页面多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "PAGE_ID", type: "BIGINT", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "PAGE_NAME", type: "VARCHAR(500)", remarks: "页面名称ID")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-18-WFL_PROCEDURE") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_PROCEDURE_S', startValue: "10001")
        }

        createTable(tableName: "WFL_PROCEDURE", remarks: "工作流过程") {

            column(name: "PROCEDURE_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "WFL_PROCEDURE_PK")
            }
            column(name: "PROCEDURE_CODE", type: "VARCHAR(30)", remarks: "过程代码") { constraints(nullable: "false") }
            column(name: "PROCEDURE_NAME", type: "VARCHAR(500)", remarks: "过程名称ID")
            column(name: "PROCEDURE_CONTENT", type: "VARCHAR(200)", remarks: "过程内容") { constraints(nullable: "false") }
            column(name: "PROCEDURE_TYPE", type: "VARCHAR(30)", remarks: "过程类型") { constraints(nullable: "false") }
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-18-WFL_PROCEDURE_TL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'WFL_PROCEDURE_TL_S', startValue: "10001")
        }

        createTable(tableName: "WFL_PROCEDURE_TL", remarks: "工作流过程多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "PROCEDURE_ID", type: "BIGINT", remarks: "主键") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "PROCEDURE_NAME", type: "VARCHAR(500)", remarks: "过程名称ID")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
    }

}