package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-02-28-CON1050-init-table-migration.groovy') {


    changeSet(author: "junkai.lu@hand-china.com", id: "2019-02-28-CON_CONTRACT_HEADER") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CON_CONTRACT_HEADER_S', startValue:"10001")
        }

        createTable(tableName: "CON_CONTRACT_HEADER", remarks: "合同申请头表") {

            column(name: "CONTRACT_HEADER_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键ID")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CON_CONTRACT_HEADER_PK")} 
            column(name: "CONTRACT_TYPE_ID", type: "BIGINT", remarks: "合同类型ID")  {constraints(nullable:"false")}  
            column(name: "CONTRACT_NUMBER", type: "VARCHAR(30)", remarks: "合同编号")  {constraints(nullable:"false")}  
            column(name: "MAG_ORG_ID", type: "BIGINT", remarks: "管理组织")   
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司")  {constraints(nullable:"false")}  
            column(name: "UNIT_ID", type: "BIGINT", remarks: "部门")  {constraints(nullable:"false")}  
            column(name: "POSITION_ID", type: "BIGINT", remarks: "岗位")  {constraints(nullable:"false")}  
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "员工")  {constraints(nullable:"false")}  
            column(name: "REQUISITION_DATE", type: "DATETIME", remarks: "申请日期")   
            column(name: "PERIOD_NAME", type: "VARCHAR(30)", remarks: "期间")   
            column(name: "PROJECT_ID", type: "BIGINT", remarks: "")   
            column(name: "BGT_ENTITY_ID", type: "BIGINT", remarks: "预算实体")   
            column(name: "BGT_CENTER_ID", type: "BIGINT", remarks: "预算中心")   
            column(name: "PARTNER_CATEGORY", type: "VARCHAR(30)", remarks: "对象类型")   
            column(name: "PARTNER_ID", type: "BIGINT", remarks: "合同方")   
            column(name: "DOCUMENT_NUMBER", type: "VARCHAR(30)", remarks: "合同号")   
            column(name: "DOCUMENT_DESC", type: "VARCHAR(100)", remarks: "合同名称")   
            column(name: "START_DATE", type: "DATETIME", remarks: "开始时间")   
            column(name: "END_DATE", type: "DATETIME", remarks: "结束时间")   
            column(name: "SIGN_DATE", type: "DATETIME", remarks: "签订日期")   
            column(name: "SIGN_PLACE", type: "VARCHAR(30)", remarks: "签订地点")   
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算实体")  {constraints(nullable:"false")}  
            column(name: "RESP_CENTER_ID", type: "BIGINT", remarks: "责任中心")  {constraints(nullable:"false")}  
            column(name: "RESP_EMPLOYEE_ID", type: "BIGINT", remarks: "责任人")   
            column(name: "RESP_UNIT_ID", type: "BIGINT", remarks: "责任部门")   
            column(name: "APPLY_UNIT_ID", type: "BIGINT", remarks: "使用部门")   
            column(name: "CURRENCY_CODE", type: "VARCHAR(10)", remarks: "币种")  {constraints(nullable:"false")}  
            column(name: "EXCHANGE_TYPE", type: "VARCHAR(30)", remarks: "汇率类型")   
            column(name: "EXCHANGE_RATE", type: "NUMBER", remarks: "汇率")  {constraints(nullable:"false")}  
            column(name: "AMOUNT", type: "NUMBER", remarks: "金额")   
            column(name: "FUNCTION_AMOUNT", type: "NUMBER", remarks: "本币金额")   
            column(name: "STATUS", type: "VARCHAR(30)", remarks: "状态")  {constraints(nullable:"false")}  
            column(name: "SOURCE_TYPE", type: "VARCHAR(30)", remarks: "来源类型")   
            column(name: "SOURCE_CONTRACT_HEADER_ID", type: "BIGINT", remarks: "来源合同ID")   
            column(name: "PRICE_AGREEMENT_FLAG", type: "VARCHAR(1)", remarks: "是否有价格协议")   
            column(name: "MORE_PAYMENT_ENTITY_FLAG", type: "VARCHAR(1)", remarks: "多付款主体")   
            column(name: "CONTRACT_FORM", type: "VARCHAR(100)", remarks: "合同形式")   
            column(name: "FORMAT_FLAG", type: "VARCHAR(1)", remarks: "是否是格式合同")   
            column(name: "FORMAT_TYPE", type: "VARCHAR(30)", remarks: "格式合同类型")   
            column(name: "TENDER_METHOD", type: "VARCHAR(30)", remarks: "招标类型")   
            column(name: "FREE_TENDER_NUMBER", type: "VARCHAR(100)", remarks: "招标单号")   
            column(name: "ARCHIVE_STATUS", type: "VARCHAR(30)", remarks: "归档状态")   
            column(name: "DESCRIPTION", type: "VARCHAR(4000)", remarks: "描述")   
            column(name: "PUR_ORG_ID", type: "BIGINT", remarks: "采购组织")   
            column(name: "MO_PUR_GROUP_ID", type: "BIGINT", remarks: "采购组")   
            column(name: "BUYER_ID", type: "BIGINT", remarks: "采购员")   
            column(name: "VERSION_NUMBER", type: "VARCHAR(30)", remarks: "版本号")   
            column(name: "DIMENSION1_ID", type: "BIGINT", remarks: "")   
            column(name: "DIMENSION2_ID", type: "BIGINT", remarks: "")   
            column(name: "DIMENSION3_ID", type: "BIGINT", remarks: "")   
            column(name: "DIMENSION4_ID", type: "BIGINT", remarks: "")   
            column(name: "DIMENSION5_ID", type: "BIGINT", remarks: "")   
            column(name: "DIMENSION6_ID", type: "BIGINT", remarks: "")   
            column(name: "DIMENSION7_ID", type: "BIGINT", remarks: "")   
            column(name: "DIMENSION8_ID", type: "BIGINT", remarks: "")   
            column(name: "DIMENSION9_ID", type: "BIGINT", remarks: "")   
            column(name: "DIMENSION10_ID", type: "BIGINT", remarks: "")   
            column(name: "DIMENSION11_ID", type: "BIGINT", remarks: "")   
            column(name: "DIMENSION12_ID", type: "BIGINT", remarks: "")   
            column(name: "DIMENSION13_ID", type: "BIGINT", remarks: "")   
            column(name: "DIMENSION14_ID", type: "BIGINT", remarks: "")   
            column(name: "DIMENSION15_ID", type: "BIGINT", remarks: "")   
            column(name: "DIMENSION16_ID", type: "BIGINT", remarks: "")   
            column(name: "DIMENSION17_ID", type: "BIGINT", remarks: "")   
            column(name: "DIMENSION18_ID", type: "BIGINT", remarks: "")   
            column(name: "DIMENSION19_ID", type: "BIGINT", remarks: "")   
            column(name: "DIMENSION20_ID", type: "BIGINT", remarks: "")   
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        
        addUniqueConstraint(columnNames:"CONTRACT_NUMBER",tableName:"CON_CONTRACT_HEADER",constraintName: "CON_CONTRACT_HEADERS_U1")
    }


    changeSet(author: "junkai.lu@hand-china.com", id: "2019-02-28-CON_PAYMENT_SCHEDULE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CON_PAYMENT_SCHEDULE_S', startValue:"10001")
        }

        createTable(tableName: "CON_PAYMENT_SCHEDULE", remarks: "合同资金计划行表") {

            column(name: "CONTRACT_HEADER_ID", type: "BIGINT", remarks: "合同头ID")  {constraints(nullable:"false")}
            column(name: "PAYMENT_SCHEDULE_LINE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "资金计划行ID")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CON_PAYMENT_SCHEDULE_PK")}
            column(name: "LINE_NUMBER", type: "BIGINT", remarks: "行号")  {constraints(nullable:"false")}
            column(name: "EXPENSE_TYPE_ID", type: "BIGINT", remarks: "报销类型ID")
            column(name: "REQ_ITEM_ID", type: "BIGINT", remarks: "申请项目ID")
            column(name: "AMOUNT", type: "NUMBER", remarks: "金额")
            column(name: "PAYMENT_AMOUNT", type: "NUMBER", remarks: "支付金额")
            column(name: "PAYMENT_METHOD", type: "VARCHAR(30)", remarks: "付款方法")
            column(name: "PAYMENT_TERM_ID", type: "BIGINT", remarks: "付款条款ID")
            column(name: "PARTNER_CATEGORY", type: "VARCHAR(30)", remarks: "对象类型")
            column(name: "PARTNER_ID", type: "BIGINT", remarks: "对象ID")
            column(name: "START_DATE", type: "DATETIME", remarks: "开始日期")
            column(name: "DUE_DATE", type: "DATETIME", remarks: "计划付款日期(预计完成时间)")
            column(name: "ACTUAL_DATE", type: "DATETIME", remarks: "实际付款日期")
            column(name: "MEMO", type: "VARCHAR(2000)", remarks: "备注")
            column(name: "CURRENCY_CODE", type: "VARCHAR(10)", remarks: "币种")  {constraints(nullable:"false")}
            column(name: "PAYMENT_CURRENCY_CODE", type: "VARCHAR(10)", remarks: "里程碑行付款币种")
            column(name: "REQ2PAY_EXCHANGE_TYPE", type: "VARCHAR(30)", remarks: "头币种到里程碑币种汇率类型")
            column(name: "REQ2PAY_EXCHANGE_RATE", type: "NUMBER", remarks: "头币种到里程碑币种汇率")
            column(name: "LANDMARK_PHASE", type: "VARCHAR(100)", remarks: "合同里程碑阶段")
            column(name: "ACCEPTED_FLAG", type: "VARCHAR(1)", remarks: "是否需要验收")
            column(name: "PAYMENT_NODE_FLAG", type: "VARCHAR(1)", remarks: "是否付款节点")
            column(name: "PAYMENT_TERM", type: "VARCHAR(4000)", remarks: "付款条件")
            column(name: "PAYMENT_NODE_TYPE", type: "VARCHAR(30)", remarks: "付款节点属性")
            column(name: "PAYMENT_RATIO", type: "VARCHAR(30)", remarks: "付款比例")
            column(name: "STATUS", type: "VARCHAR(30)", remarks: "验收状态")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID")  {constraints(nullable:"false")}
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")  {constraints(nullable:"false")}
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        createIndex(tableName: "CON_PAYMENT_SCHEDULE", indexName: "CON_PAYMENT_SCHEDULES_N1") {
            column(name: "CONTRACT_HEADER_ID")
            column(name: "PAYMENT_SCHEDULE_LINE_ID")
        }

        addUniqueConstraint(columnNames:"LINE_NUMBER,CONTRACT_HEADER_ID",tableName:"CON_PAYMENT_SCHEDULE",constraintName: "CON_PAYMENT_SCHEDULES_U1")
    }

}