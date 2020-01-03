package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-01-08-CSH5010-init-table-migration.groovy') {


    changeSet(author: "dingwei.ma@hand-china.com", id: "2019-01-08-CSH_PAYMENT_REQUISITION_HD") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_PAYMENT_REQUISITION_HD_S', startValue:"10001")
        }

        createTable(tableName: "CSH_PAYMENT_REQUISITION_HD", remarks: "借款申请单头表") {

            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "DOC_STATUS", type: "VARCHAR(30)", remarks: "退回状态（SYSCODE：CSH_DOC_BACK）")   
            column(name: "PAYMENT_REQUISITION_HEADER_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "借款申请单头ID")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_PAYMENT_REQUISITION_HD_PK")} 
            column(name: "REQUISITION_NUMBER", type: "VARCHAR(30)", remarks: "申请编号")  {constraints(nullable:"false")}  
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "管理公司ID")  {constraints(nullable:"false")}  
            column(name: "OPERATION_UNIT_ID", type: "BIGINT", remarks: "废弃_经营单位ID")   
            column(name: "UNIT_ID", type: "BIGINT", remarks: "部门ID")  {constraints(nullable:"false")}  
            column(name: "POSITION_ID", type: "BIGINT", remarks: "岗位ID")  {constraints(nullable:"false")}  
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "员工ID")  {constraints(nullable:"false")}  
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体ID")  {constraints(nullable:"false")}  
            column(name: "REQUISITION_DATE", type: "DATETIME", remarks: "申请日期")  {constraints(nullable:"false")}  
            column(name: "REQUISITION_DATE_TZ", type: "TIMESTAMP", defaultValueComputed : "CURRENT_TIMESTAMP", remarks: "申请日期_业务时区")  {constraints(nullable:"false")}
            column(name: "REQUISITION_DATE_LTZ", type: "TIMESTAMP", defaultValueComputed : "CURRENT_TIMESTAMP", remarks: "申请日期_查询时区")  {constraints(nullable:"false")}
            column(name: "PAYMENT_REQ_TYPE_ID", type: "BIGINT", remarks: "借款申请类型ID")   
            column(name: "TRANSACTION_CATEGORY", type: "VARCHAR(30)", remarks: "事务类型（BUSINESS经营类，MISCELLANEOUS杂项类）")  {constraints(nullable:"false")}  
            column(name: "DISTRIBUTION_SET_ID", type: "BIGINT", remarks: "废弃_分配集")   
            column(name: "PAYMENT_METHOD_ID", type: "BIGINT", remarks: "支付方式ID")   
            column(name: "PAYEE_CATEGORY", type: "VARCHAR(30)", remarks: "对象类型（EMPLOYEE员工，VENDER供应商）")  {constraints(nullable:"false")}  
            column(name: "PAYEE_ID", type: "BIGINT", remarks: "收款对象ID")  {constraints(nullable:"false")}  
            column(name: "AMOUNT", type: "DECIMAL(20,8)", remarks: "金额")  {constraints(nullable:"false")}
            column(name: "CURRENCY_CODE", type: "VARCHAR(30)", remarks: "币种")  {constraints(nullable:"false")}  
            column(name: "REQUISITION_PAYMENT_DATE", type: "DATETIME", remarks: "付款日期")   
            column(name: "REQUISITION_PAYMENT_DATE_TZ", type: "DATETIME", remarks: "付款日期_业务时区")   
            column(name: "REQUISITION_PAYMENT_DATE_LTZ", type: "DATETIME", remarks: "付款日期_查询时区")   
            column(name: "DESCRIPTION", type: "VARCHAR(4000)", remarks: "描述")   
            column(name: "STATUS", type: "VARCHAR(30)", remarks: "状态（COMPLETELY_APPROVED审批通过，CLOSED关闭，GENERATE新建，REJECTED拒绝，SUBMITTED提交，TAKEN_BACK收回）")  {constraints(nullable:"false")}  
            column(name: "APPROVAL_DATE", type: "DATETIME", remarks: "审批日期")   
            column(name: "APPROVED_BY", type: "BIGINT", remarks: "审批人")   
            column(name: "CLOSED_DATE", type: "DATETIME", remarks: "关闭日期")   
            column(name: "CLOSED_DATE_TZ", type: "TIMESTAMP", defaultValueComputed : "CURRENT_TIMESTAMP", remarks: "关闭日期_业务时区")
            column(name: "CLOSED_DATE_LTZ", type: "TIMESTAMP", defaultValueComputed : "CURRENT_TIMESTAMP", remarks: "关闭日期_查询时区")
            column(name: "SOURCE_TYPE", type: "VARCHAR(30)", remarks: "来源类型")   
            column(name: "AUDIT_FLAG", type: "VARCHAR(1)", remarks: "审核标志")  {constraints(nullable:"false")}  
            column(name: "AUDIT_DATE", type: "DATETIME", remarks: "审核日期")   
            column(name: "REVERSED_FLAG", type: "VARCHAR(1)", remarks: "反冲标志")  {constraints(nullable:"false")}  
            column(name: "SOURCE_PMT_REQ_HEADER_ID", type: "BIGINT", remarks: "申请单头id")   
            column(name: "JE_CREATION_STATUS", type: "VARCHAR(30)", remarks: "凭证标识")   
            column(name: "EXPENSE_REQUISITION_HEADER_ID", type: "BIGINT", remarks: "所属费用申请单头ID")   
            column(name: "ATTACHMENT_NUM", type: "BIGINT", remarks: "附件数")   
            column(name: "SCHEDULE_REPAYMENT_DATE", type: "DATETIME", remarks: "计划还款日")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "CSH_PAYMENT_REQUISITION_HD", indexName: "CSH_PAYMENT_REQUISITION_HD_N1") {
                    column(name: "POSITION_ID")
                    column(name: "UNIT_ID")
                    column(name: "COMPANY_ID")
                }
                createIndex(tableName: "CSH_PAYMENT_REQUISITION_HD", indexName: "CSH_PAYMENT_REQUISITION_HD_N2") {
                    column(name: "ACC_ENTITY_ID")
                }
                createIndex(tableName: "CSH_PAYMENT_REQUISITION_HD", indexName: "CSH_PAYMENT_REQUISITION_HD_N3") {
                    column(name: "EMPLOYEE_ID")
                }
                createIndex(tableName: "CSH_PAYMENT_REQUISITION_HD", indexName: "CSH_PAYMENT_REQUISITION_HD_N4") {
                    column(name: "PAYMENT_REQ_TYPE_ID")
                }
                createIndex(tableName: "CSH_PAYMENT_REQUISITION_HD", indexName: "CSH_PAYMENT_REQUISITION_HD_N5") {
                    column(name: "SOURCE_PMT_REQ_HEADER_ID")
                    column(name: "REVERSED_FLAG")
                }
            
        addUniqueConstraint(columnNames:"REQUISITION_NUMBER",tableName:"CSH_PAYMENT_REQUISITION_HD",constraintName: "CSH_PAYMENT_REQUISITION_HD_U1")
    }


    changeSet(author: "dingwei.ma@hand-china.com", id: "2019-01-08-CSH_PAYMENT_REQUISITION_LN") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_PAYMENT_REQUISITION_LN_S', startValue:"10001")
        }

        createTable(tableName: "CSH_PAYMENT_REQUISITION_LN", remarks: "借款申请单行表") {

            column(name: "PAYMENT_REQUISITION_LINE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_PAYMENT_REQUISITION_LN_PK")} 
            column(name: "PAYMENT_REQUISITION_HEADER_ID", type: "BIGINT", remarks: "借款申请单头表ID")  {constraints(nullable:"false")}  
            column(name: "LINE_NUMBER", type: "BIGINT", remarks: "行号")  {constraints(nullable:"false")}  
            column(name: "PAYMENT_REQUISITION_LINE_TYPE", type: "VARCHAR(30)", remarks: "行类型（OTHERS其他，EXP_REQUISITION费用申请单）")  {constraints(nullable:"false")}  
            column(name: "REF_DOCUMENT_ID", type: "BIGINT", remarks: "关联单据ID")   
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司ID")  {constraints(nullable:"false")}  
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体")  {constraints(nullable:"false")}  
            column(name: "PAYEE_CATEGORY", type: "VARCHAR(30)", remarks: "收款方类型")  {constraints(nullable:"false")}  
            column(name: "PAYEE_ID", type: "BIGINT", remarks: "收款方ID")  {constraints(nullable:"false")}  
            column(name: "ACCOUNT_NAME", type: "VARCHAR(2000)", remarks: "会计科目描述")   
            column(name: "PAYMENT_METHOD_ID", type: "BIGINT", remarks: "支付方式")   
            column(name: "ACCOUNT_NUMBER", type: "VARCHAR(30)", remarks: "会计科目编码")   
            column(name: "MO_CSH_TRX_CLASS_ID", type: "BIGINT", remarks: "现金事务分类ID")  {constraints(nullable:"false")}  
            column(name: "AMOUNT", type: "DECIMAL(20,8)", remarks: "金额")  {constraints(nullable:"false")}
            column(name: "DESCRIPTION", type: "VARCHAR(2000)", remarks: "描述")   
            column(name: "PAYMENT_STATUS", type: "VARCHAR(10)", remarks: "付款状态（COMPLETELY全部付款，NEVER从未付款，PARTIALLY部分付款）")   
            column(name: "PAYMENT_COMPLETED_DATE", type: "DATETIME", remarks: "付款完成日期")   
            column(name: "CASH_FLOW_ITEM_ID", type: "BIGINT", remarks: "现金流量项ID（现金事务分类定义中设置的现金流量项）")   
            column(name: "BANK_CODE", type: "VARCHAR(30)", remarks: "银行代码")   
            column(name: "BANK_NAME", type: "VARCHAR(200)", remarks: "银行名称")   
            column(name: "BANK_LOCATION_CODE", type: "VARCHAR(30)", remarks: "分行代码")   
            column(name: "BANK_LOCATION_NAME", type: "VARCHAR(200)", remarks: "分行名称")   
            column(name: "PROVINCE_CODE", type: "VARCHAR(30)", remarks: "分行所在省")   
            column(name: "PROVINCE_NAME", type: "VARCHAR(200)", remarks: "省名称")   
            column(name: "CITY_CODE", type: "VARCHAR(30)", remarks: "分行所在市")   
            column(name: "CITY_NAME", type: "VARCHAR(200)", remarks: "市名称")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "CSH_PAYMENT_REQUISITION_LN", indexName: "CSH_PAYMENT_REQUISITION_LN_N1") {
                    column(name: "PAYMENT_REQUISITION_LINE_TYPE")
                    column(name: "REF_DOCUMENT_ID")
                }
            
        addUniqueConstraint(columnNames:"LINE_NUMBER,PAYMENT_REQUISITION_HEADER_ID,PAYMENT_REQUISITION_LINE_TYPE",tableName:"CSH_PAYMENT_REQUISITION_LN",constraintName: "CSH_PAYMENT_REQUISITION_LN_U1")
    }


    changeSet(author: "dingwei.ma@hand-china.com", id: "2019-01-08-CSH_PAYMENT_REQUISITION_REF") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_PAYMENT_REQUISITION_REF_S', startValue:"10001")
        }

        createTable(tableName: "CSH_PAYMENT_REQUISITION_REF", remarks: "借款申请关联单据表") {

            column(name: "PAYMENT_REQUISITION_REF_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "借款申请关联ID")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_PAYMENT_REQUISITION_REF_PK")} 
            column(name: "PAYMENT_REQUISITION_LINE_ID", type: "BIGINT", remarks: "借款申请单行ID")  {constraints(nullable:"false")}  
            column(name: "CSH_TRANSACTION_LINE_ID", type: "BIGINT", remarks: "现金事务行ID")  {constraints(nullable:"false")}  
            column(name: "WRITE_OFF_FLAG", type: "VARCHAR(1)", remarks: "核销标志")   
            column(name: "WRITE_OFF_ID", type: "BIGINT", remarks: "核销ID")   
            column(name: "AMOUNT", type: "NUMBER", remarks: "金额")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "VARCHAR(2000)", remarks: "描述")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "CSH_PAYMENT_REQUISITION_REF", indexName: "CSH_PAYMENT_REQ_REF_N1") {
                    column(name: "CSH_TRANSACTION_LINE_ID")
                }
                createIndex(tableName: "CSH_PAYMENT_REQUISITION_REF", indexName: "CSH_PAYMENT_REQ_REF_N2") {
                    column(name: "PAYMENT_REQUISITION_LINE_ID")
                }
            
        addUniqueConstraint(columnNames:"WRITE_OFF_ID",tableName:"CSH_PAYMENT_REQUISITION_REF",constraintName: "CSH_PAYMENT_REQ_REF_U1")
    }
	
	changeSet(author: "dingwei.ma@hand-china.com", id: "2019-01-15-CSH_PAYMENT_ENTITY_RULE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_PAYMENT_ENTITY_RULE_S', startValue:"10001")
        }

        createTable(tableName: "CSH_PAYMENT_ENTITY_RULE", remarks: "支付主体决定规则表") {

            column(name: "ENTITY_RULE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_PAYMENT_ENTITY_RULE_PK")} 
            column(name: "MAG_ORG_ID", type: "BIGINT", remarks: "管理组织ID")  {constraints(nullable:"false")}  
            column(name: "DOCUMENT_CATEGORY", type: "VARCHAR(30)", remarks: "业务类型（SYSCODE：CSH_PAYMENT_DOCUMENT_CATEGORY）")  {constraints(nullable:"false")}  
            column(name: "PRIORITY", type: "BIGINT", remarks: "优先级")  {constraints(nullable:"false")}  
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体ID")  {constraints(nullable:"false")}  
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "管理公司ID")   
            column(name: "DOCUMENT_TYPE_ID", type: "BIGINT", remarks: "单据类型ID（费用报销单、借款申请单、付款申请单）")   
            column(name: "PAYMENT_METHOD_ID", type: "BIGINT", remarks: "付款方式ID")   
            column(name: "PAYEE_CATEGORY", type: "VARCHAR(30)", remarks: "收款方类别")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "CSH_PAYMENT_ENTITY_RULE", indexName: "CSH_PAYMENT_ENTITY_RULES_N1") {
                    column(name: "DOCUMENT_TYPE_ID")
                    column(name: "DOCUMENT_CATEGORY")
                }
                createIndex(tableName: "CSH_PAYMENT_ENTITY_RULE", indexName: "CSH_PAYMENT_ENTITY_RULES_N2") {
                    column(name: "MAG_ORG_ID")
                    column(name: "COMPANY_ID")
                    column(name: "DOCUMENT_CATEGORY")
                    column(name: "ACC_ENTITY_ID")
                }
        
    }
	
	changeSet(author: "dingwei.ma@hand-china.com", id: "2019-01-15-EXP_DOCUMENT_HISTORY") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_DOCUMENT_HISTORY_S', startValue:"10001")
        }

        createTable(tableName: "EXP_DOCUMENT_HISTORY", remarks: "单据历史跟踪表") {

            column(name: "HISTORY_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外间使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_DOCUMENT_HISTORY_PK")} 
            column(name: "DOCUMENT_TYPE", type: "VARCHAR(30)", remarks: "单据类型（EXP_REPORT费用报销单,EXP_REQUISITION费用申请单,PAYMENT_REQUISITION借款申请单,ACP_REQUISITION付款申请单,BUDGET_JOURNAL预算日记账,CSH_REPAYMENT_REGISTER还款申请单）")  {constraints(nullable:"false")}
            column(name: "DOCUMENT_ID", type: "BIGINT", remarks: "单据ID")  {constraints(nullable:"false")}  
            column(name: "OPERATION_CODE", type: "VARCHAR(30)", remarks: "操作动作代码")  {constraints(nullable:"false")}  
            column(name: "USER_ID", type: "BIGINT", remarks: "用户ID")  {constraints(nullable:"false")}  
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "员工ID")   
            column(name: "OPERATION_TIME", type: "DATETIME", remarks: "操作时间")  {constraints(nullable:"false")}  
            column(name: "OPERATION_TIME_TZ", type: "TIMESTAMP", defaultValueComputed : "CURRENT_TIMESTAMP", remarks: "操作时间_业务时区")  {constraints(nullable:"false")}
            column(name: "OPERATION_TIME_LTZ", type: "TIMESTAMP", defaultValueComputed : "CURRENT_TIMESTAMP", remarks: "操作时间_查询时区")
            column(name: "DESCRIPTION", type: "VARCHAR(2000)", remarks: "描述说明")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "EXP_DOCUMENT_HISTORY", indexName: "EXP_DOCUMENT_HISTORIES_N1") {
                    column(name: "DOCUMENT_TYPE")
                    column(name: "DOCUMENT_ID")
                }
        
    }
	
	
	changeSet(author: "dingwei.ma@hand-china.com", id: "2019-01-15-CSH_MO_TRX_CLS_REF_FLOW_IT") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_MO_TRX_CLS_REF_FLOW_IT_S', startValue:"10001")
        }

        createTable(tableName: "CSH_MO_TRX_CLS_REF_FLOW_IT", remarks: "管理组织级现金事物关联现金流量项") {

            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "REF_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_MO_TRX_CLS_REF_FLOW_IT_PK")} 
            column(name: "MO_CSH_TRX_CLASS_ID", type: "BIGINT", remarks: "管理组织级现金事物分类ID")  {constraints(nullable:"false")}  
            column(name: "CASH_FLOW_ITEM_ID", type: "BIGINT", remarks: "现金流量项ID")  {constraints(nullable:"false")}  
            column(name: "DEFAULT_FLAG", type: "VARCHAR(1)", remarks: "默认现金流量项标志")  {constraints(nullable:"false")}  
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"CASH_FLOW_ITEM_ID,MO_CSH_TRX_CLASS_ID",tableName:"CSH_MO_TRX_CLS_REF_FLOW_IT",constraintName: "CSH_MO_TRX_CLS_REF_FLOW_ITS_U1")
    }
	
	changeSet(author: "dingwei.ma@hand-china.com", id: "2019-01-15-CSH_MO_TRANSACTION_CLASS") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_MO_TRANSACTION_CLASS_S', startValue:"10001")
        }

        createTable(tableName: "CSH_MO_TRANSACTION_CLASS", remarks: "管理组织级现金事物分类定义") {

            column(name: "MO_CSH_TRX_CLASS_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "现金事物分类ID,PK")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_MO_TRANSACTION_CLASS_PK")} 
            column(name: "MAG_ORG_ID", type: "BIGINT", remarks: "管理组织ID")  {constraints(nullable:"false")}  
            column(name: "MO_CSH_TRX_CLASS_CODE", type: "VARCHAR(30)", remarks: "现金事物分类代码")  {constraints(nullable:"false")}  
            column(name: "CSH_TRANSACTION_TYPE_CODE", type: "VARCHAR(30)", remarks: "现金事物类型代码（PAYMENT，PREPAYMENT）")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述ID")  {constraints(nullable:"false")}  
            column(name: "ENABLED_WRITE_OFF_FLAG", type: "VARCHAR(1)", remarks: "是否启用核销")  {constraints(nullable:"false")}  
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
        
        addUniqueConstraint(columnNames:"MAG_ORG_ID,MO_CSH_TRX_CLASS_CODE",tableName:"CSH_MO_TRANSACTION_CLASS",constraintName: "CSH_MO_TRANSACTION_CLASSES_U1")
    }

    changeSet(author:"dingwei.ma@hand-china.com", id: "2019-01-15-CSH_MO_TRANSACTION_CLASS_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_MO_TRANSACTION_CLASS_TL_S', startValue: "10001")
        }

        createTable(tableName: "CSH_MO_TRANSACTION_CLASS_TL", remarks: "管理组织级现金事物分类定义多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "MO_CSH_TRX_CLASS_ID", type: "BIGINT",  remarks: "现金事物分类ID,PK")  {constraints(nullable: "false", primaryKey: "true")} 
            column(name: "DESCRIPTION", type: "VARCHAR(500)",  remarks: "描述ID")   
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
	
	changeSet(author: "dingwei.ma@hand-china.com", id: "2019-01-15-CON_DOCUMENT_FLOW") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CON_DOCUMENT_FLOW_S', startValue:"10001")
        }

        createTable(tableName: "CON_DOCUMENT_FLOW", remarks: "合同与单据关系表") {

            column(name: "DOCUMENT_TYPE", type: "VARCHAR(30)", remarks: "单据类型")  {constraints(nullable:"false")}  
            column(name: "DOCUMENT_ID", type: "BIGINT", remarks: "单据头ID")  {constraints(nullable:"false")}  
            column(name: "DOCUMENT_LINE_ID", type: "BIGINT", remarks: "单据行ID")   
            column(name: "SOURCE_DOCUMENT_TYPE", type: "VARCHAR(30)", remarks: "源单据类型")  {constraints(nullable:"false")}  
            column(name: "SOURCE_DOCUMENT_ID", type: "BIGINT", remarks: "源单据头ID")  {constraints(nullable:"false")}  
            column(name: "SOURCE_DOCUMENT_LINE_ID", type: "BIGINT", remarks: "源单据行ID")   
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "CON_DOCUMENT_FLOW", indexName: "CON_DOCUMENT_FLOWS_N1") {
                    column(name: "DOCUMENT_TYPE")
                    column(name: "DOCUMENT_ID")
                }
                createIndex(tableName: "CON_DOCUMENT_FLOW", indexName: "CON_DOCUMENT_FLOWS_N2") {
                    column(name: "SOURCE_DOCUMENT_ID")
                    column(name: "SOURCE_DOCUMENT_TYPE")
                }
        
        addUniqueConstraint(columnNames:"SOURCE_DOCUMENT_LINE_ID,SOURCE_DOCUMENT_ID,SOURCE_DOCUMENT_TYPE,DOCUMENT_ID,DOCUMENT_TYPE,DOCUMENT_LINE_ID",tableName:"CON_DOCUMENT_FLOW",constraintName: "CON_DOCUMENT_FLOWS_U1")
    }

}