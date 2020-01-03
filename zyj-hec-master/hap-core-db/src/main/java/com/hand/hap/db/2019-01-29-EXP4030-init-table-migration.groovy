package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-01-29-EXP4030-init-table-migration.groovy') {


    changeSet(author: "dingwei.ma@hand-china.com", id: "2019-03-05-EXP_REQUISITION_DIST") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_REQUISITION_DIST_S', startValue:"10001")
        }

        createTable(tableName: "EXP_REQUISITION_DIST", remarks: "申请单分配行") {

            column(name: "PAY2MAG_EXCHANGE_RATE", type: "BIGINT", remarks: "业务币种->管理币种汇率")  {constraints(nullable:"false")}
            column(name: "MO_EXPENSE_TYPE_ID", type: "BIGINT", remarks: "管理组织级报销类型ID")  {constraints(nullable:"false")}
            column(name: "MO_REQ_ITEM_ID", type: "BIGINT", remarks: "管理组织级费用申请项目ID")  {constraints(nullable:"false")}
            column(name: "BUDGET_ITEM_ID", type: "BIGINT", remarks: "预算项目ID")
            column(name: "BUSINESS_PRICE", type: "BIGINT", remarks: "业务币种单价")  {constraints(nullable:"false")}
            column(name: "PAYMENT_PRICE", type: "BIGINT", remarks: "付款币种单价")  {constraints(nullable:"false")}
            column(name: "MANAGEMENT_PRICE", type: "BIGINT", remarks: "管理币种单价")  {constraints(nullable:"false")}
            column(name: "PRIMARY_QUANTITY", type: "BIGINT", remarks: "数量")  {constraints(nullable:"false")}
            column(name: "PRIMARY_UOM", type: "VARCHAR(30)", remarks: "数量单位")
            column(name: "SECONDARY_QUANTITY", type: "BIGINT", remarks: "废弃_次要数量")
            column(name: "SECONDARY_UOM", type: "VARCHAR(30)", remarks: "废弃_次要数量单位")
            column(name: "BUSINESS_AMOUNT", type: "BIGINT", remarks: "业务币种金额")  {constraints(nullable:"false")}
            column(name: "PAYMENT_AMOUNT", type: "BIGINT", remarks: "付款币种金额")  {constraints(nullable:"false")}
            column(name: "MANAGEMENT_AMOUNT", type: "BIGINT", remarks: "管理币种金额")  {constraints(nullable:"false")}
            column(name: "REQUISITION_FUNCTIONAL_AMOUNT", type: "BIGINT", remarks: "本位币金额")  {constraints(nullable:"false")}
            column(name: "DISTRIBUTION_SET_ID", type: "BIGINT", remarks: "废弃_分配集")
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司ID")  {constraints(nullable:"false")}
            column(name: "OPERATION_UNIT_ID", type: "BIGINT", remarks: "废弃_经营单位ID")
            column(name: "UNIT_ID", type: "BIGINT", remarks: "部门ID")  {constraints(nullable:"false")}
            column(name: "POSITION_ID", type: "BIGINT", remarks: "岗位ID")  {constraints(nullable:"false")}
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "员工ID")  {constraints(nullable:"false")}
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体ID")  {constraints(nullable:"false")}
            column(name: "RESP_CENTER_ID", type: "BIGINT", remarks: "责任中心ID")  {constraints(nullable:"false")}
            column(name: "BGT_ENTITY_ID", type: "BIGINT", remarks: "预算主体ID")  {constraints(nullable:"false")}
            column(name: "BGT_CENTER_ID", type: "BIGINT", remarks: "预算中心ID")  {constraints(nullable:"false")}
            column(name: "PAYEE_CATEGORY", type: "VARCHAR(30)", remarks: "收款方类别")
            column(name: "PAYEE_ID", type: "BIGINT", remarks: "收款方ID")
            column(name: "PAYMENT_FLAG", type: "VARCHAR(1)", remarks: "废弃_付款标志")  {constraints(nullable:"false")}
            column(name: "CLOSE_FLAG", type: "VARCHAR(1)", remarks: "关闭标志")  {constraints(nullable:"false")}
            column(name: "CLOSE_DATE", type: "DATETIME", remarks: "关闭日期")
            column(name: "REQUISITION_STATUS", type: "VARCHAR(30)", remarks: "废弃_申请状态")  {constraints(nullable:"false")}
            column(name: "AUDIT_FLAG", type: "VARCHAR(1)", remarks: "废弃_审核标志")
            column(name: "ATTACHMENT_NUM", type: "BIGINT", remarks: "附件数量")
            column(name: "EXCEED_BUDGET_STRATEGY", type: "VARCHAR(30)", remarks: "控制策略")
            column(name: "RELEASED_AMOUNT", type: "BIGINT", remarks: "下达金额")
            column(name: "RELEASED_QUANTITY", type: "BIGINT", remarks: "下达数量")
            column(name: "RELEASED_STATUS", type: "VARCHAR(30)", remarks: "下达状态")
            column(name: "DIMENSION1_ID", type: "BIGINT", remarks: "维度1")
            column(name: "DIMENSION2_ID", type: "BIGINT", remarks: "维度2")
            column(name: "DIMENSION3_ID", type: "BIGINT", remarks: "维度3")
            column(name: "DIMENSION4_ID", type: "BIGINT", remarks: "维度4")
            column(name: "DIMENSION5_ID", type: "BIGINT", remarks: "维度5")
            column(name: "DIMENSION6_ID", type: "BIGINT", remarks: "维度6")
            column(name: "DIMENSION7_ID", type: "BIGINT", remarks: "维度7")
            column(name: "DIMENSION8_ID", type: "BIGINT", remarks: "维度8")
            column(name: "DIMENSION9_ID", type: "BIGINT", remarks: "维度9")
            column(name: "DIMENSION10_ID", type: "BIGINT", remarks: "维度10")
            column(name: "DIMENSION11_ID", type: "BIGINT", remarks: "维度11")
            column(name: "DIMENSION12_ID", type: "BIGINT", remarks: "维度12")
            column(name: "DIMENSION13_ID", type: "BIGINT", remarks: "维度13")
            column(name: "DIMENSION14_ID", type: "BIGINT", remarks: "维度14")
            column(name: "DIMENSION15_ID", type: "BIGINT", remarks: "维度15")
            column(name: "DIMENSION16_ID", type: "BIGINT", remarks: "维度16")
            column(name: "DIMENSION17_ID", type: "BIGINT", remarks: "维度17")
            column(name: "DIMENSION18_ID", type: "BIGINT", remarks: "维度18")
            column(name: "DIMENSION19_ID", type: "BIGINT", remarks: "维度19")
            column(name: "DIMENSION20_ID", type: "BIGINT", remarks: "维度20")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "EXP_REQUISITION_DIST_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "申请单行ID，pk")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_REQUISITION_DIST_PK")}
            column(name: "EXP_REQUISITION_LINE_ID", type: "BIGINT", remarks: "申请单头ID")  {constraints(nullable:"false")}
            column(name: "DESCRIPTION", type: "VARCHAR(4000)", remarks: "描述")
            column(name: "DATE_FROM", type: "DATETIME", remarks: "日期从")
            column(name: "DATE_TO", type: "DATETIME", remarks: "日期到")
            column(name: "PERIOD_NAME", type: "VARCHAR(30)", remarks: "期间")  {constraints(nullable:"false")}
            column(name: "BUSINESS_CURRENCY_CODE", type: "VARCHAR(30)", remarks: "业务币种")  {constraints(nullable:"false")}
            column(name: "BIZ2PAY_EXCHANGE_TYPE", type: "VARCHAR(30)", remarks: "业务币种->支付币种汇率类型")
            column(name: "BIZ2PAY_EXCHANGE_RATE", type: "BIGINT", remarks: "业务币种->支付币种汇率")  {constraints(nullable:"false")}
            column(name: "PAYMENT_CURRENCY_CODE", type: "VARCHAR(30)", remarks: "支付币种")  {constraints(nullable:"false")}
            column(name: "PAY2FUN_EXCHANGE_TYPE", type: "VARCHAR(30)", remarks: "支付币种->本位币汇率类型")
            column(name: "PAY2FUN_EXCHANGE_RATE", type: "BIGINT", remarks: "支付币种->本位币汇率")  {constraints(nullable:"false")}
            column(name: "MANAGEMENT_CURRENCY_CODE", type: "VARCHAR(30)", remarks: "管理币种")  {constraints(nullable:"false")}
            column(name: "PAY2MAG_EXCHANGE_TYPE", type: "VARCHAR(30)", remarks: "业务币种->管理币种汇率类型")
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        createIndex(tableName: "EXP_REQUISITION_DIST", indexName: "EXP_REQUISITION_DIST_N1") {
            column(name: "COMPANY_ID")
            column(name: "UNIT_ID")
            column(name: "POSITION_ID")
        }
        createIndex(tableName: "EXP_REQUISITION_DIST", indexName: "EXP_REQUISITION_DIST_N2") {
            column(name: "RESP_CENTER_ID")
            column(name: "ACC_ENTITY_ID")
        }
        createIndex(tableName: "EXP_REQUISITION_DIST", indexName: "EXP_REQUISITION_DIST_N3") {
            column(name: "BGT_CENTER_ID")
            column(name: "BGT_ENTITY_ID")
        }
        createIndex(tableName: "EXP_REQUISITION_DIST", indexName: "EXP_REQUISITION_DIST_N4") {
            column(name: "EMPLOYEE_ID")
        }

        addUniqueConstraint(columnNames:"EXP_REQUISITION_LINE_ID",tableName:"EXP_REQUISITION_DIST",constraintName: "EXP_REQUISITION_DIST_U1")
    }

    changeSet(author: "jiangxz", id: "2019-01-29-EXP_REQUISITION_ACCOUNT") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'EXP_REQUISITION_ACCOUNT_S', startValue: "10001")
        }

        createTable(tableName: "EXP_REQUISITION_ACCOUNT", remarks: "费用申请单核算凭证") {

            column(name: "EXP_REQUISITION_DIST_ID", type: "BIGINT", remarks: "费用申请单分配行ID") {
                constraints(nullable: "false")
            }
            column(name: "EXP_REQUISITION_JE_LINE_ID", type: "BIGINT", remarks: "费用申请单凭证行ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "EXP_REQUISITION_ACCOUNT_PK")
            }
            column(name: "DESCRIPTION", type: "VARCHAR(2000)", remarks: "描述")
            column(name: "JOURNAL_DATE", type: "DATETIME", remarks: "记帐日期") { constraints(nullable: "false") }
            column(name: "PERIOD_NAME", type: "VARCHAR(30)", remarks: "期间") { constraints(nullable: "false") }
            column(name: "SOURCE_CODE", type: "VARCHAR(30)", remarks: "来源")
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司ID") { constraints(nullable: "false") }
            column(name: "COMPANY_SEGMENT", type: "VARCHAR(30)", remarks: "公司段")
            column(name: "RESPONSIBILITY_CENTER_ID", type: "BIGINT", remarks: "责任中心ID") {
                constraints(nullable: "false")
            }
            column(name: "ACCOUNT_ID", type: "BIGINT", remarks: "科目ID") { constraints(nullable: "false") }
            column(name: "ACCOUNT_SEGMENT1", type: "VARCHAR(200)", remarks: "段1")
            column(name: "ACCOUNT_SEGMENT2", type: "VARCHAR(200)", remarks: "段2")
            column(name: "ACCOUNT_SEGMENT3", type: "VARCHAR(200)", remarks: "段3")
            column(name: "ACCOUNT_SEGMENT4", type: "VARCHAR(200)", remarks: "段4")
            column(name: "ACCOUNT_SEGMENT5", type: "VARCHAR(200)", remarks: "段5")
            column(name: "ACCOUNT_SEGMENT6", type: "VARCHAR(200)", remarks: "段6")
            column(name: "ACCOUNT_SEGMENT7", type: "VARCHAR(200)", remarks: "段7")
            column(name: "ACCOUNT_SEGMENT8", type: "VARCHAR(200)", remarks: "段8")
            column(name: "ACCOUNT_SEGMENT9", type: "VARCHAR(200)", remarks: "段9")
            column(name: "ACCOUNT_SEGMENT10", type: "VARCHAR(200)", remarks: "段10")
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
            column(name: "DIMENSION1_ID", type: "BIGINT", remarks: "维度1")
            column(name: "DIMENSION2_ID", type: "BIGINT", remarks: "维度2")
            column(name: "DIMENSION3_ID", type: "BIGINT", remarks: "维度3")
            column(name: "DIMENSION4_ID", type: "BIGINT", remarks: "维度4")
            column(name: "DIMENSION5_ID", type: "BIGINT", remarks: "维度5")
            column(name: "DIMENSION6_ID", type: "BIGINT", remarks: "维度6")
            column(name: "DIMENSION7_ID", type: "BIGINT", remarks: "维度7")
            column(name: "DIMENSION8_ID", type: "BIGINT", remarks: "维度8")
            column(name: "DIMENSION9_ID", type: "BIGINT", remarks: "维度9")
            column(name: "DIMENSION10_ID", type: "BIGINT", remarks: "维度10")
            column(name: "CURRENCY_CODE", type: "VARCHAR(10)", remarks: "币种") { constraints(nullable: "false") }
            column(name: "EXCHANGE_RATE_TYPE", type: "VARCHAR(30)", remarks: "汇率类型")
            column(name: "EXCHANGE_RATE_QUOTATION", type: "VARCHAR(30)", remarks: "汇率标价方法")
            column(name: "EXCHANGE_RATE", type: "BIGINT", remarks: "汇率") { constraints(nullable: "false") }
            column(name: "ENTERED_AMOUNT_DR", type: "BIGINT", remarks: "原币贷方金额")
            column(name: "ENTERED_AMOUNT_CR", type: "BIGINT", remarks: "原币借方金额")
            column(name: "FUNCTIONAL_AMOUNT_DR", type: "BIGINT", remarks: "本币贷方金额")
            column(name: "FUNCTIONAL_AMOUNT_CR", type: "BIGINT", remarks: "本币借方金额")
            column(name: "GLD_INTERFACE_FLAG", type: "VARCHAR(1)", remarks: "过帐")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期") { constraints(nullable: "false") }
            column(name: "JE_CATEGORY_CODE", type: "VARCHAR(150)", remarks: "凭证类别代码")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "EXP_REQUISITION_ACCOUNT", indexName: "EXP_REQUISITION_ACCOUNT_N1") {
            column(name: "EXP_REQUISITION_DIST_ID")
        }

    }
    changeSet(author: "jiangxz", id: "2019-01-29-EXP_REQUISITION_OBJECT") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'EXP_REQUISITION_OBJECT_S', startValue: "10001")
        }

        createTable(tableName: "EXP_REQUISITION_OBJECT", remarks: "费用申请单费用对象信息") {

            column(name: "EXP_REQUISITION_HEADER_ID", type: "BIGINT", remarks: "费用申请单头ID") {
                constraints(nullable: "false")
            }
            column(name: "EXP_REQUISITION_LINE_ID", type: "BIGINT", remarks: "费用申请单行ID")
            column(name: "MO_EXP_OBJ_TYPE_ID", type: "BIGINT", remarks: "费用对象类型ID") { constraints(nullable: "false") }
            column(name: "MO_EXPENSE_OBJECT_ID", type: "BIGINT", remarks: "费用对象ID")
            column(name: "MO_EXPENSE_OBJECT_NAME", type: "VARCHAR(2000)", remarks: "费用对象描述")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "EXP_REQUISITION_OBJECT", indexName: "EXP_REQUISITION_OBJECT_N1") {
            column(name: "MO_EXP_OBJ_TYPE_ID")
            column(name: "MO_EXPENSE_OBJECT_ID")
        }
        createIndex(tableName: "EXP_REQUISITION_OBJECT", indexName: "EXP_REQUISITION_OBJECT_N2") {
            column(name: "EXP_REQUISITION_HEADER_ID")
        }

        addUniqueConstraint(columnNames: "EXP_REQUISITION_LINE_ID,EXP_REQUISITION_HEADER_ID,MO_EXP_OBJ_TYPE_ID", tableName: "EXP_REQUISITION_OBJECT", constraintName: "EXP_REQUISITION_OBJECT_U1")
    }
    changeSet(author: "jiangxz", id: "2019-01-29-EXP_REQUISITION_RELEASE") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'EXP_REQUISITION_RELEASE_S', startValue: "10001")
        }

        createTable(tableName: "EXP_REQUISITION_RELEASE", remarks: "报销单核销费用申请关联表") {

            column(name: "RELEASE_ID", type: "BIGINT", remarks: "核销ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "EXP_REQUISITION_RELEASE_PK")
            }
            column(name: "EXP_REQUISITION_HEADER_ID", type: "BIGINT", remarks: "费用申请单头ID")
            column(name: "EXP_REQUISITION_LINE_ID", type: "BIGINT", remarks: "费用申请单行ID")
            column(name: "EXP_REQUISITION_DIST_ID", type: "BIGINT", remarks: "费用申请单分配行ID")
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司ID") { constraints(nullable: "false") }
            column(name: "OPERATION_UNIT_ID", type: "BIGINT", remarks: "经营单位ID")
            column(name: "DOCUMENT_TYPE", type: "VARCHAR(30)", remarks: "单据类型") { constraints(nullable: "false") }
            column(name: "DOCUMENT_ID", type: "BIGINT", remarks: "单据头ID")
            column(name: "DOCUMENT_LINE_ID", type: "BIGINT", remarks: "单据行ID")
            column(name: "DOCUMENT_DIST_ID", type: "BIGINT", remarks: "单据分配行ID")
            column(name: "REQ_RELEASE_AMOUNT", type: "BIGINT", remarks: "申请单核销金额")
            column(name: "DOC_RELEASE_AMOUNT", type: "BIGINT", remarks: "单据核销金额")
            column(name: "REQ_RELEASE_QUANTITY", type: "BIGINT", remarks: "申请单核销数量")
            column(name: "REQ_RELEASE_UOM", type: "VARCHAR(30)", remarks: "申请单核销单位")
            column(name: "DOC_RELEASE_QUANTITY", type: "BIGINT", remarks: "单据核销数量")
            column(name: "DOC_RELEASE_UOM", type: "VARCHAR(30)", remarks: "单据核销单位")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "EXP_REQUISITION_RELEASE", indexName: "EXP_REQUISITION_RELEASE_N1") {
            column(name: "EXP_REQUISITION_LINE_ID")
            column(name: "EXP_REQUISITION_DIST_ID")
            column(name: "EXP_REQUISITION_HEADER_ID")
        }
        createIndex(tableName: "EXP_REQUISITION_RELEASE", indexName: "EXP_REQUISITION_RELEASE_N2") {
            column(name: "DOCUMENT_LINE_ID")
            column(name: "DOCUMENT_DIST_ID")
            column(name: "DOCUMENT_TYPE")
            column(name: "DOCUMENT_ID")
        }
        createIndex(tableName: "EXP_REQUISITION_RELEASE", indexName: "EXP_REQUISITION_RELEASE_N3") {
            column(name: "EXP_REQUISITION_DIST_ID")
        }
        createIndex(tableName: "EXP_REQUISITION_RELEASE", indexName: "EXP_REQUISITION_RELEASE_N4") {
            column(name: "DOCUMENT_TYPE")
            column(name: "DOCUMENT_DIST_ID")
        }

    }
    changeSet(author: "jiangxz", id: "2019-01-29-EXP_REQUISITION_TRAVEL_LINE") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'EXP_REQUISITION_TRAVEL_LINE_S', startValue: "10001")
        }

        createTable(tableName: "EXP_REQUISITION_TRAVEL_LINE", remarks: "费用申请行-差旅关联行") {

            column(name: "TRAVEL_LINE_ID", type: "BIGINT", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "EXP_REQUISITION_TRAVEL_LINE_PK")
            }
            column(name: "EXP_REQUISITION_LINE_ID", type: "BIGINT", remarks: "费用申请单行ID") {
                constraints(nullable: "false")
            }
            column(name: "TRAVEL_LINE_CATEGORY", type: "VARCHAR(30)", remarks: "差旅行类别") {
                constraints(nullable: "false")
            }
            column(name: "TRANSPORTATION", type: "VARCHAR(30)", remarks: "行程_交通工具")
            column(name: "SEAT_CLASS", type: "VARCHAR(30)", remarks: "行程_舱位/座位等级")
            column(name: "DEPARTURE_PLACE", type: "VARCHAR(255)", remarks: "行程_出发地")
            column(name: "DEPARTURE_PLACE_ID", type: "BIGINT", remarks: "行程_出发地ID")
            column(name: "DEPARTURE_DATE", type: "DATETIME", remarks: "行程_出发日期")
            column(name: "ARRIVAL_PLACE", type: "VARCHAR(255)", remarks: "行程_到达地")
            column(name: "ARRIVAL_PLACE_ID", type: "BIGINT", remarks: "行程_到达地ID")
            column(name: "ARRIVAL_DATE", type: "DATETIME", remarks: "行程_到达日期")
            column(name: "ACCOMMODATION_DATE_FROM", type: "DATETIME", remarks: "住宿_入住日期")
            column(name: "ACCOMMODATION_DATE_TO", type: "DATETIME", remarks: "住宿_离店日期")
            column(name: "ACCOMMODATION_DAYS", type: "BIGINT", remarks: "住宿_住宿天数")
            column(name: "PEER_PEOPLE", type: "VARCHAR(255)", remarks: "同行人")
            column(name: "MONOPOLIZE_FLAG", type: "VARCHAR(1)", remarks: "统购标志") { constraints(nullable: "false") }
            column(name: "MONOPOLIZE_PLATFORM", type: "VARCHAR(30)", remarks: "统购平台")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "EXP_REQUISITION_LINE_ID,TRAVEL_LINE_CATEGORY", tableName: "EXP_REQUISITION_TRAVEL_LINE", constraintName: "EXP_REQ_TRAVEL_LINE_U1")
    }
    changeSet(author: "jiangxz", id: "2019-01-29-EXP_REQ_DIMENSION_LAYOUT") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'EXP_REQ_DIMENSION_LAYOUT_S', startValue: "10001")
        }

        createTable(tableName: "EXP_REQ_DIMENSION_LAYOUT", remarks: "费用申请单维度布局") {

            column(name: "EXPENSE_REQUISITION_HEADER_ID", type: "BIGINT", remarks: "费用申请单头ID") {
                constraints(nullable: "false")
            }
            column(name: "LAYOUT_POSITION", type: "VARCHAR(30)", remarks: "布局位置") { constraints(nullable: "false") }
            column(name: "LAYOUT_PRIORITY", type: "BIGINT", remarks: "布局顺序") { constraints(nullable: "false") }
            column(name: "DIMENSION_ID", type: "BIGINT", remarks: "维度ID") { constraints(nullable: "false") }
            column(name: "DEFAULT_DIM_VALUE_ID", type: "BIGINT", remarks: "缺省维值ID")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addPrimaryKey(columnNames: "DIMENSION_ID,EXPENSE_REQUISITION_HEADER_ID", tableName: "EXP_REQ_DIMENSION_LAYOUT", constraintName: "EXP_REQ_DIMENSION_LAYOUT_PK")
    }
    changeSet(author: "jiangxz", id: "2019-01-29-EXP_REQ_OBJECTS_LAYOUT") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'EXP_REQ_OBJECTS_LAYOUT_S', startValue: "10001")
        }

        createTable(tableName: "EXP_REQ_OBJECTS_LAYOUT", remarks: "费用申请单费用对象布局") {

            column(name: "EXPENSE_REQUISITION_HEADER_ID", type: "BIGINT", remarks: "费用申请单头ID") {
                constraints(nullable: "false")
            }
            column(name: "LAYOUT_POSITION", type: "VARCHAR(30)", remarks: "布局位置") { constraints(nullable: "false") }
            column(name: "LAYOUT_PRIORITY", type: "BIGINT", remarks: "布局顺序") { constraints(nullable: "false") }
            column(name: "EXPENSE_OBJECT_TYPE_ID", type: "BIGINT", remarks: "费用对象类型ID") {
                constraints(nullable: "false")
            }
            column(name: "DEFAULT_OBJECT_ID", type: "BIGINT", remarks: "缺省费用对象ID")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addPrimaryKey(columnNames: "EXPENSE_OBJECT_TYPE_ID,EXPENSE_REQUISITION_HEADER_ID", tableName: "EXP_REQ_OBJECTS_LAYOUT", constraintName: "EXP_REQ_OBJECTS_LAYOUT_PK")
    }

}