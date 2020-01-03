package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-04-10-ACP3100-init-table-migration.groovy') {


    changeSet(author: "jingjing.tang@hand-china.com", id: "2019-04-10-ACP_REQUISITION_HD") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'ACP_REQUISITION_HD_S', startValue:"10001")
        }

        createTable(tableName: "ACP_REQUISITION_HD", remarks: "付款申请单头表") {

            column(name: "REQUISITION_HDS_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "ACP_REQUISITION_HD_PK")} 
            column(name: "REQUISITION_NUMBER", type: "VARCHAR(30)", remarks: "申请编号")  {constraints(nullable:"false")}  
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "管理公司ID")  {constraints(nullable:"false")}  
            column(name: "UNIT_ID", type: "BIGINT", remarks: "部门ID")  {constraints(nullable:"false")}  
            column(name: "POSITION_ID", type: "BIGINT", remarks: "岗位ID")  {constraints(nullable:"false")}  
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "员工ID")  {constraints(nullable:"false")}  
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体ID")  {constraints(nullable:"false")}  
            column(name: "REQUISITION_DATE", type: "DATETIME", remarks: "申请日期")  {constraints(nullable:"false")}  
            column(name: "REQUISITION_DATE_TZ", type: "TIMESTAMP", remarks: "申请日期_业务时区")  {constraints(nullable:"false")}  
            column(name: "REQUISITION_DATE_LTZ", type: "TIMESTAMP", remarks: "申请日期_查询时区")  {constraints(nullable:"false")}  
            column(name: "MO_PAY_REQ_TYPE_ID", type: "BIGINT", remarks: "付款申请单类型ID")  {constraints(nullable:"false")}  
            column(name: "TRANSACTION_CATEGORY", type: "VARCHAR(30)", remarks: "事务类型（BUSINESS经营类，MISCELLANEOUS杂项类）")  {constraints(nullable:"false")}  
            column(name: "PAYMENT_METHOD_ID", type: "BIGINT", remarks: "付款方式ID")   
            column(name: "PAYMENT_USEDE_ID", type: "BIGINT", remarks: "付款用途ID")   
            column(name: "PAYEE_CATEGORY", type: "VARCHAR(30)", remarks: "对象类型（EMPLOYEE员工，VENDER供应商，CUSTOMER客户）")  {constraints(nullable:"false")}  
            column(name: "PAYEE_ID", type: "BIGINT", remarks: "收款对象ID")  {constraints(nullable:"false")}  
            column(name: "AMOUNT", type: "decimal(10,2)", remarks: "金额")  {constraints(nullable:"false")}
            column(name: "CURRENCY_CODE", type: "VARCHAR(30)", remarks: "币种")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "VARCHAR(2000)", remarks: "描述")   
            column(name: "STATUS", type: "VARCHAR(30)", remarks: "状态（APPROVED审批通过，CLOSED关闭，GENERATE新建，REJECTED拒绝，SUBMITTED提交，TAKEN_BACK收回）")  {constraints(nullable:"false")}  
            column(name: "APPROVAL_DATE", type: "DATETIME", remarks: "审批")   
            column(name: "APPROVAL_DATE_TZ", type: "TIMESTAMP", remarks: "审批_业务时区")   
            column(name: "APPROVAL_DATE_LTZ", type: "TIMESTAMP", remarks: "审批_查询时区")   
            column(name: "APPROVED_BY", type: "BIGINT", remarks: "审批人")   
            column(name: "CLOSED_DATE", type: "DATETIME", remarks: "关闭日期")   
            column(name: "CLOSED_DATE_TZ", type: "TIMESTAMP", remarks: "关闭日期_业务时区")   
            column(name: "CLOSED_DATE_LTZ", type: "TIMESTAMP", remarks: "关闭日期_查询时区")   
            column(name: "JE_CREATION_STATUS", type: "VARCHAR(30)", remarks: "凭证创建状态")   
            column(name: "AUDIT_FLAG", type: "VARCHAR(1)", remarks: "审核标志")  {constraints(nullable:"false")}  
            column(name: "AUDIT_DATE", type: "DATETIME", remarks: "审核日期")   
            column(name: "AUDIT_DATE_TZ", type: "TIMESTAMP", remarks: "审核日期_业务时区")   
            column(name: "AUDIT_DATE_LTZ", type: "TIMESTAMP", remarks: "审核日期_查询时区")   
            column(name: "REVERSED_FLAG", type: "VARCHAR(1)", remarks: "反冲标志")  {constraints(nullable:"false")}  
            column(name: "SOURCE_TYPE", type: "VARCHAR(30)", remarks: "来源类型")   
            column(name: "SOURCE_HEADER_ID", type: "BIGINT", remarks: "来源单据ID")   
            column(name: "DOC_STATUS", type: "VARCHAR(30)", remarks: "退回状态（SYSCODE：CSH_DOC_BACK）")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "ACP_REQUISITION_HD", indexName: "ACP_REQUISITION_HD_N1") {
                    column(name: "COMPANY_ID")
                }
                createIndex(tableName: "ACP_REQUISITION_HD", indexName: "ACP_REQUISITION_HD_N2") {
                    column(name: "ACC_ENTITY_ID")
                }
            
        addUniqueConstraint(columnNames:"REQUISITION_NUMBER",tableName:"ACP_REQUISITION_HD",constraintName: "ACP_REQUISITION_HD_U1")
    }


    changeSet(author: "jingjing.tang@hand-china.com", id: "2019-04-10-ACP_REQUISITION_LN") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'ACP_REQUISITION_LN_S', startValue:"10001")
        }

        createTable(tableName: "ACP_REQUISITION_LN", remarks: "付款申请单行表") {

            column(name: "REQUISITION_LNS_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "ACP_REQUISITION_LN_PK")} 
            column(name: "REQUISITION_HDS_ID", type: "BIGINT", remarks: "付款申请单头ID")  {constraints(nullable:"false")}  
            column(name: "LINE_NUMBER", type: "BIGINT", remarks: "行号")  {constraints(nullable:"false")}  
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "管理公司ID")  {constraints(nullable:"false")}  
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体ID")  {constraints(nullable:"false")}  
            column(name: "MO_CSH_TRX_CLASS_ID", type: "BIGINT", remarks: "现金事务分类ID")   
            column(name: "PAYEE_CATEGORY", type: "VARCHAR(30)", remarks: "收款方类型")  {constraints(nullable:"false")}  
            column(name: "PAYEE_ID", type: "BIGINT", remarks: "收款方ID")  {constraints(nullable:"false")}  
            column(name: "AMOUNT", type: "decimal(10,2)", remarks: "金额")  {constraints(nullable:"false")}
            column(name: "DESCRIPTION", type: "VARCHAR(2000)", remarks: "描述")   
            column(name: "PAYMENT_STATUS", type: "VARCHAR(30)", remarks: "付款状态（COMPLETELY全部付款，NEVER从未付款，PARTIALLY部分付款）")   
            column(name: "PAYMENT_COMPLETED_DATE", type: "DATETIME", remarks: "付款完成日期")   
            column(name: "PAYMENT_METHOD_ID", type: "BIGINT", remarks: "付款方式ID")   
            column(name: "PAYMENT_USEDE_ID", type: "BIGINT", remarks: "付款用途ID")   
            column(name: "CASH_FLOW_ITEM_ID", type: "BIGINT", remarks: "现金流量项ID（现金事务分类定义中设置的现金流量项）")   
            column(name: "REQUISITION_PAYMENT_DATE", type: "DATETIME", remarks: "计划付款日")   
            column(name: "ACCOUNT_NAME", type: "VARCHAR(200)", remarks: "银行户名")   
            column(name: "ACCOUNT_NUMBER", type: "VARCHAR(200)", remarks: "银行帐号")   
            column(name: "BANK_CODE", type: "VARCHAR(30)", remarks: "银行代码")   
            column(name: "BANK_NAME", type: "VARCHAR(200)", remarks: "银行名称")   
            column(name: "BANK_LOCATION_CODE", type: "VARCHAR(30)", remarks: "分行代码")   
            column(name: "BANK_LOCATION_NAME", type: "VARCHAR(200)", remarks: "分行名称")   
            column(name: "PROVINCE_CODE", type: "VARCHAR(30)", remarks: "分行所在省")   
            column(name: "PROVINCE_NAME", type: "VARCHAR(200)", remarks: "省名称")   
            column(name: "CITY_CODE", type: "VARCHAR(30)", remarks: "分行所在城市")   
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
            createIndex(tableName: "ACP_REQUISITION_LN", indexName: "ACP_REQUISITION_LN_N1") {
                    column(name: "COMPANY_ID")
                    column(name: "ACC_ENTITY_ID")
                }
                createIndex(tableName: "ACP_REQUISITION_LN", indexName: "ACP_REQUISITION_LN_N2") {
                    column(name: "MO_CSH_TRX_CLASS_ID")
                }
            
        addUniqueConstraint(columnNames:"REQUISITION_HDS_ID,LINE_NUMBER",tableName:"ACP_REQUISITION_LN",constraintName: "ACP_REQUISITION_LN_U1")
    }


    changeSet(author: "jingjing.tang@hand-china.com", id: "2019-04-10-ACP_REQUISITION_DTL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'ACP_REQUISITION_DTL_S', startValue:"10001")
        }

        createTable(tableName: "ACP_REQUISITION_DTL", remarks: "付款申请单明细表") {

            column(name: "AMOUNT", type: "decimal(10,2)", remarks: "金额")  {constraints(nullable:"false")}
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "REQUISITION_DTL_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "ACP_REQUISITION_DTL_PK")} 
            column(name: "REQUISITION_LNS_ID", type: "BIGINT", remarks: "付款申请单行表ID")  {constraints(nullable:"false")}  
            column(name: "LINE_NUMBER", type: "BIGINT", remarks: "行号")  {constraints(nullable:"false")}  
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "管理公司ID")  {constraints(nullable:"false")}  
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体ID")  {constraints(nullable:"false")}  
            column(name: "REF_DOCUMENT_TYPE", type: "VARCHAR(30)", remarks: "单据类型（REPORT/报销单、CONTRACT/合同）")  {constraints(nullable:"false")}  
            column(name: "REF_DOCUMENT_ID", type: "BIGINT", remarks: "单据头ID（报销单头ID、合同头ID）")  {constraints(nullable:"false")}  
            column(name: "REF_DOCUMENT_LINE_ID", type: "BIGINT", remarks: "单据付款行ID（报销单结算行ID、合同结算行ID）")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "ACP_REQUISITION_DTL", indexName: "ACP_REQUISITION_DTL_N1") {
                    column(name: "REF_DOCUMENT_TYPE")
                    column(name: "REF_DOCUMENT_ID")
                    column(name: "REF_DOCUMENT_LINE_ID")
                }
            
        addUniqueConstraint(columnNames:"REQUISITION_LNS_ID,LINE_NUMBER",tableName:"ACP_REQUISITION_DTL",constraintName: "ACP_REQUISITION_DTL_U1")
    }


    changeSet(author: "jingjing.tang@hand-china.com", id: "2019-04-10-ACP_REQUISITION_REF") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'ACP_REQUISITION_REF_S', startValue:"10001")
        }

        createTable(tableName: "ACP_REQUISITION_REF", remarks: "付款申请关联单据表") {

            column(name: "REQUISITION_REF_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "ACP_REQUISITION_REF_PK")} 
            column(name: "REQUISITION_LNS_ID", type: "BIGINT", remarks: "付款申请单行ID")  {constraints(nullable:"false")}  
            column(name: "CSH_TRANSACTION_LINE_ID", type: "BIGINT", remarks: "现金事务行ID")  {constraints(nullable:"false")}  
            column(name: "WRITE_OFF_FLAG", type: "VARCHAR(1)", remarks: "核销标志")   
            column(name: "WRITE_OFF_ID", type: "BIGINT", remarks: "核销表ID")   
            column(name: "AMOUNT", type: "decimal(10,2)", remarks: "金额")  {constraints(nullable:"false")}
            column(name: "DESCRIPTION", type: "VARCHAR(2000)", remarks: "描述")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "ACP_REQUISITION_REF", indexName: "ACP_REQUISITION_REF_N1") {
                    column(name: "REQUISITION_LNS_ID")
                }
                createIndex(tableName: "ACP_REQUISITION_REF", indexName: "ACP_REQUISITION_REF_N2") {
                    column(name: "CSH_TRANSACTION_LINE_ID")
                }
                createIndex(tableName: "ACP_REQUISITION_REF", indexName: "ACP_REQUISITION_REF_N3") {
                    column(name: "WRITE_OFF_ID")
                }
        
    }


    changeSet(author: "jingjing.tang@hand-china.com", id: "2019-04-10-ACP_REQUISITION_ACCOUNT") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'ACP_REQUISITION_ACCOUNT_S', startValue:"10001")
        }

        createTable(tableName: "ACP_REQUISITION_ACCOUNT", remarks: "付款申请凭证表") {

            column(name: "ACCOUNT_SEGMENT1", type: "VARCHAR(200)", remarks: "核算段1")   
            column(name: "ACCOUNT_SEGMENT2", type: "VARCHAR(200)", remarks: "核算段2")   
            column(name: "ACCOUNT_SEGMENT3", type: "VARCHAR(200)", remarks: "核算段3")   
            column(name: "ACCOUNT_SEGMENT4", type: "VARCHAR(200)", remarks: "核算段4")   
            column(name: "ACCOUNT_SEGMENT5", type: "VARCHAR(200)", remarks: "核算段5")   
            column(name: "ACCOUNT_SEGMENT6", type: "VARCHAR(200)", remarks: "核算段6")   
            column(name: "ACCOUNT_SEGMENT7", type: "VARCHAR(200)", remarks: "核算段7")   
            column(name: "ACCOUNT_SEGMENT8", type: "VARCHAR(200)", remarks: "核算段8")   
            column(name: "ACCOUNT_SEGMENT9", type: "VARCHAR(200)", remarks: "核算段9")   
            column(name: "ACCOUNT_SEGMENT10", type: "VARCHAR(200)", remarks: "核算段10")   
            column(name: "ACCOUNT_SEGMENT11", type: "VARCHAR(200)", remarks: "核算段11")   
            column(name: "ACCOUNT_SEGMENT12", type: "VARCHAR(200)", remarks: "核算段12")   
            column(name: "ACCOUNT_SEGMENT13", type: "VARCHAR(200)", remarks: "核算段13")   
            column(name: "ACCOUNT_SEGMENT14", type: "VARCHAR(200)", remarks: "核算段14")   
            column(name: "ACCOUNT_SEGMENT15", type: "VARCHAR(200)", remarks: "核算段15")   
            column(name: "ACCOUNT_SEGMENT16", type: "VARCHAR(200)", remarks: "核算段16")   
            column(name: "ACCOUNT_SEGMENT17", type: "VARCHAR(200)", remarks: "核算段17")   
            column(name: "ACCOUNT_SEGMENT18", type: "VARCHAR(200)", remarks: "核算段18")   
            column(name: "ACCOUNT_SEGMENT19", type: "VARCHAR(200)", remarks: "核算段19")   
            column(name: "ACCOUNT_SEGMENT20", type: "VARCHAR(200)", remarks: "核算段20")   
            column(name: "ACCOUNT_SEGMENT21", type: "VARCHAR(200)", remarks: "核算段21")   
            column(name: "ACCOUNT_SEGMENT22", type: "VARCHAR(200)", remarks: "核算段22")   
            column(name: "ACCOUNT_SEGMENT23", type: "VARCHAR(200)", remarks: "核算段23")   
            column(name: "ACCOUNT_SEGMENT24", type: "VARCHAR(200)", remarks: "核算段24")   
            column(name: "ACCOUNT_SEGMENT25", type: "VARCHAR(200)", remarks: "核算段25")   
            column(name: "ACCOUNT_SEGMENT26", type: "VARCHAR(200)", remarks: "核算段26")   
            column(name: "ACCOUNT_SEGMENT27", type: "VARCHAR(200)", remarks: "核算段27")   
            column(name: "ACCOUNT_SEGMENT28", type: "VARCHAR(200)", remarks: "核算段28")   
            column(name: "ACCOUNT_SEGMENT29", type: "VARCHAR(200)", remarks: "核算段29")   
            column(name: "ACCOUNT_SEGMENT30", type: "VARCHAR(200)", remarks: "核算段30")   
            column(name: "ACCOUNT_SEGMENT31", type: "VARCHAR(200)", remarks: "核算段31")   
            column(name: "ACCOUNT_SEGMENT32", type: "VARCHAR(200)", remarks: "核算段32")   
            column(name: "ACCOUNT_SEGMENT33", type: "VARCHAR(200)", remarks: "核算段33")   
            column(name: "ACCOUNT_SEGMENT34", type: "VARCHAR(200)", remarks: "核算段34")   
            column(name: "ACCOUNT_SEGMENT35", type: "VARCHAR(200)", remarks: "核算段35")   
            column(name: "ACCOUNT_SEGMENT36", type: "VARCHAR(200)", remarks: "核算段36")   
            column(name: "ACCOUNT_SEGMENT37", type: "VARCHAR(200)", remarks: "核算段37")   
            column(name: "ACCOUNT_SEGMENT38", type: "VARCHAR(200)", remarks: "核算段38")   
            column(name: "ACCOUNT_SEGMENT39", type: "VARCHAR(200)", remarks: "核算段39")   
            column(name: "ACCOUNT_SEGMENT40", type: "VARCHAR(200)", remarks: "核算段40")   
            column(name: "ACCOUNT_SEGMENT41", type: "VARCHAR(200)", remarks: "核算段41")   
            column(name: "ACCOUNT_SEGMENT42", type: "VARCHAR(200)", remarks: "核算段42")   
            column(name: "ACCOUNT_SEGMENT43", type: "VARCHAR(200)", remarks: "核算段43")   
            column(name: "ACCOUNT_SEGMENT44", type: "VARCHAR(200)", remarks: "核算段44")   
            column(name: "ACCOUNT_SEGMENT45", type: "VARCHAR(200)", remarks: "核算段45")   
            column(name: "ACCOUNT_SEGMENT46", type: "VARCHAR(200)", remarks: "核算段46")   
            column(name: "ACCOUNT_SEGMENT47", type: "VARCHAR(200)", remarks: "核算段47")   
            column(name: "ACCOUNT_SEGMENT48", type: "VARCHAR(200)", remarks: "核算段48")   
            column(name: "ACCOUNT_SEGMENT49", type: "VARCHAR(200)", remarks: "核算段49")   
            column(name: "ACCOUNT_SEGMENT50", type: "VARCHAR(200)", remarks: "核算段50")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "ACP_REQ_JE_LINE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "ACP_REQUISITION_ACCOUNT_PK")} 
            column(name: "REQUISITION_LNS_ID", type: "BIGINT", remarks: "付款申请单行ID")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "VARCHAR(2000)", remarks: "描述")   
            column(name: "JOURNAL_DATE", type: "DATETIME", remarks: "凭证日期")  {constraints(nullable:"false")}  
            column(name: "PERIOD_NAME", type: "VARCHAR(30)", remarks: "期间")  {constraints(nullable:"false")}  
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体ID")  {constraints(nullable:"false")}  
            column(name: "RESP_CENTER_ID", type: "BIGINT", remarks: "责任中心ID")  {constraints(nullable:"false")}  
            column(name: "ACCOUNT_ID", type: "BIGINT", remarks: "科目ID")  {constraints(nullable:"false")}  
            column(name: "CURRENCY_CODE", type: "VARCHAR(10)", remarks: "币种")  {constraints(nullable:"false")}  
            column(name: "EXCHANGE_RATE_TYPE", type: "VARCHAR(30)", remarks: "汇率类型")   
            column(name: "EXCHANGE_RATE", type: "decimal(10,2)", remarks: "汇率")  {constraints(nullable:"false")}
            column(name: "ENTERED_AMOUNT_DR", type: "decimal(10,2)", remarks: "原币借方金额")
            column(name: "ENTERED_AMOUNT_CR", type: "decimal(10,2)", remarks: "原币贷方金额")
            column(name: "FUNCTIONAL_AMOUNT_DR", type: "decimal(10,2)", remarks: "本币借方金额")
            column(name: "FUNCTIONAL_AMOUNT_CR", type: "decimal(10,2)", remarks: "本币贷方金额")
            column(name: "GLD_INTERFACE_FLAG", type: "VARCHAR(1)", remarks: "总账标志")  {constraints(nullable:"false")}  
            column(name: "USAGE_CODE", type: "VARCHAR(30)", remarks: "用途代码")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "ACP_REQUISITION_ACCOUNT", indexName: "ACP_REQUISITION_ACCOUNT_N1") {
                    column(name: "REQUISITION_LNS_ID")
                }
        
    }


}