package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-01-24-EXP4010-init-table-migration.groovy') {


    changeSet(author: "dingwei.ma@hand-china.com", id: "2019-01-24-EXP_REQUISITION_HEADER") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_REQUISITION_HEADER_S', startValue:"10001")
        }

        createTable(tableName: "EXP_REQUISITION_HEADER", remarks: "申请单头") {

            column(name: "PAYEE_CATEGORY", type: "VARCHAR(30)", remarks: "收款方类别")   
            column(name: "PAYEE_ID", type: "BIGINT", remarks: "收款方ID")   
            column(name: "MO_EXP_REQ_TYPE_ID", type: "BIGINT", remarks: "管理组织级费用申请单类型ID")  {constraints(nullable:"false")}  
            column(name: "MO_EMP_GROUP_ID", type: "BIGINT", remarks: "员工组ID")   
            column(name: "PAYMENT_CURRENCY_CODE", type: "VARCHAR(30)", remarks: "支付币种")  {constraints(nullable:"false")}  
            column(name: "PAY2FUN_EXCHANGE_TYPE", type: "VARCHAR(30)", remarks: "支付币种->本位币汇率类型")   
            column(name: "PAY2FUN_EXCHANGE_RATE", type: "DECIMAL(10,2)", remarks: "支付币种->本位币汇率")  {constraints(nullable:"false")}
            column(name: "REQUISITION_DATE", type: "DATETIME", remarks: "申请日期")  {constraints(nullable:"false")}  
            column(name: "REQUISITION_DATE_TZ", type: "TIMESTAMP", defaultValueComputed : "CURRENT_TIMESTAMP", remarks: "申请日期_业务时区")  {constraints(nullable:"false")}
            column(name: "REQUISITION_DATE_LTZ", type: "TIMESTAMP", defaultValueComputed : "CURRENT_TIMESTAMP", remarks: "申请日期_查询时区")  {constraints(nullable:"false")}
            column(name: "PERIOD_NAME", type: "VARCHAR(30)", remarks: "期间")  {constraints(nullable:"false")}  
            column(name: "REQUISITION_STATUS", type: "VARCHAR(30)", remarks: "申请单状态")  {constraints(nullable:"false")}  
            column(name: "JE_CREATION_STATUS", type: "VARCHAR(30)", remarks: "凭证创建状态")  {constraints(nullable:"false")}  
            column(name: "AUDIT_FLAG", type: "VARCHAR(1)", remarks: "审核状态")  {constraints(nullable:"false")}  
            column(name: "GLD_INTERFACE_FLAG", type: "VARCHAR(1)", remarks: "总账接口标识")  {constraints(nullable:"false")}
            column(name: "ATTACHMENT_NUM", type: "BIGINT", remarks: "附件数")   
            column(name: "DESCRIPTION", type: "VARCHAR(4000)", remarks: "描述")   
            column(name: "RELEASED_STATUS", type: "VARCHAR(30)", remarks: "下达状态")  {constraints(nullable:"false")}  
            column(name: "REVERSED_FLAG", type: "VARCHAR(1)", remarks: "反冲标志")  {constraints(nullable:"false")}  
            column(name: "SOURCE_EXP_REQ_HEADER_ID", type: "BIGINT", remarks: "来源申请单头ID")   
            column(name: "SOURCE_TYPE", type: "VARCHAR(30)", remarks: "来源类型(MANUAL)")  {constraints(nullable:"false")}  
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
            column(name: "PAYMENT_FLAG", type: "VARCHAR(1)", remarks: "借款标志")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "DEPARTURE_DATE", type: "DATETIME", remarks: "预计出发日期")   
            column(name: "ARRIVAL_DATE", type: "DATETIME", remarks: "预计返抵日期")   
            column(name: "EXP_REQUISITION_HEADER_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "申请单头ID，PK")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_REQUISITION_HEADER_PK")} 
            column(name: "EXP_REQUISITION_NUMBER", type: "VARCHAR(30)", remarks: "申请单编号")  {constraints(nullable:"false")}  
            column(name: "EXP_REQUISITION_BARCODE", type: "VARCHAR(30)", remarks: "废弃_申请单编码")   
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司ID")  {constraints(nullable:"false")}  
            column(name: "OPERATION_UNIT_ID", type: "BIGINT", remarks: "废弃_经营单位ID")   
            column(name: "UNIT_ID", type: "BIGINT", remarks: "部门ID")  {constraints(nullable:"false")}  
            column(name: "POSITION_ID", type: "BIGINT", remarks: "岗位ID")  {constraints(nullable:"false")}  
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "员工ID")  {constraints(nullable:"false")}  
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体ID")  {constraints(nullable:"false")}  
            column(name: "RESP_CENTER_ID", type: "BIGINT", remarks: "责任中心ID")  {constraints(nullable:"false")}  
            column(name: "BGT_ENTITY_ID", type: "BIGINT", remarks: "预算主体ID")  {constraints(nullable:"false")}  
            column(name: "BGT_CENTER_ID", type: "BIGINT", remarks: "预算中心ID")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "EXP_REQUISITION_HEADER", indexName: "EXP_REQUISITION_HEADER_N1") {
                    column(name: "COMPANY_ID")
                    column(name: "POSITION_ID")
                    column(name: "UNIT_ID")
                }
                createIndex(tableName: "EXP_REQUISITION_HEADER", indexName: "EXP_REQUISITION_HEADER_N2") {
                    column(name: "RESP_CENTER_ID")
                    column(name: "ACC_ENTITY_ID")
                }
                createIndex(tableName: "EXP_REQUISITION_HEADER", indexName: "EXP_REQUISITION_HEADER_N3") {
                    column(name: "BGT_ENTITY_ID")
                    column(name: "BGT_CENTER_ID")
                }
                createIndex(tableName: "EXP_REQUISITION_HEADER", indexName: "EXP_REQUISITION_HEADER_N4") {
                    column(name: "EMPLOYEE_ID")
                }
                createIndex(tableName: "EXP_REQUISITION_HEADER", indexName: "EXP_REQUISITION_HEADER_N5") {
                    column(name: "MO_EXP_REQ_TYPE_ID")
                }
                createIndex(tableName: "EXP_REQUISITION_HEADER", indexName: "EXP_REQUISITION_HEADER_N6") {
                    column(name: "SOURCE_EXP_REQ_HEADER_ID")
                    column(name: "REVERSED_FLAG")
                }
            
        addUniqueConstraint(columnNames:"EXP_REQUISITION_NUMBER",tableName:"EXP_REQUISITION_HEADER",constraintName: "EXP_REQUISITION_HEADER_U1")
    }


    changeSet(author: "dingwei.ma@hand-china.com", id: "2019-01-24-EXP_REQUISITION_LINE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_REQUISITION_LINE_S', startValue:"10001")
        }

        createTable(tableName: "EXP_REQUISITION_LINE", remarks: "申请单行") {

            column(name: "PLACE_TYPE_ID", type: "BIGINT", remarks: "地点类型ID")   
            column(name: "PLACE_ID", type: "BIGINT", remarks: "地点ID")   
            column(name: "CITY", type: "VARCHAR(200)", remarks: "城市，手动输入")   
            column(name: "DESCRIPTION", type: "VARCHAR(4000)", remarks: "描述")   
            column(name: "DATE_FROM", type: "DATETIME", remarks: "费用申请日期从")   
            column(name: "DATE_TO", type: "DATETIME", remarks: "费用申请日期到")   
            column(name: "PERIOD_NAME", type: "VARCHAR(30)", remarks: "期间")  {constraints(nullable:"false")}  
            column(name: "BUSINESS_CURRENCY_CODE", type: "VARCHAR(30)", remarks: "业务币种")  {constraints(nullable:"false")}  
            column(name: "BIZ2PAY_EXCHANGE_TYPE", type: "VARCHAR(30)", remarks: "业务币种->支付币种汇率类型")   
            column(name: "BIZ2PAY_EXCHANGE_RATE", type: "BIGINT", remarks: "业务币种->支付币种汇率")  {constraints(nullable:"false")}
            column(name: "PAYMENT_CURRENCY_CODE", type: "VARCHAR(30)", remarks: "支付币种")  {constraints(nullable:"false")}  
            column(name: "PAY2FUN_EXCHANGE_TYPE", type: "VARCHAR(30)", remarks: "支付币种->本位币汇率类型")   
            column(name: "PAY2FUN_EXCHANGE_RATE", type: "BIGINT", remarks: "支付币种->本位币汇率")  {constraints(nullable:"false")}
            column(name: "MANAGEMENT_CURRENCY_CODE", type: "VARCHAR(30)", remarks: "管理币种")  {constraints(nullable:"false")}  
            column(name: "PAY2MAG_EXCHANGE_TYPE", type: "VARCHAR(30)", remarks: "业务币种->管理币种汇率类型")   
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
            column(name: "PAYMENT_FLAG", type: "VARCHAR(1)", remarks: "废弃_付款标志")   
            column(name: "REQUISITION_STATUS", type: "VARCHAR(30)", remarks: "废弃_申请状态")   
            column(name: "AUDIT_FLAG", type: "VARCHAR(1)", remarks: "废弃_审核标志")   
            column(name: "ATTACHMENT_NUM", type: "BIGINT", remarks: "附件数量")   
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
            column(name: "ENTERTAIN_OBJECT", type: "VARCHAR(30)", remarks: "招待对象")   
            column(name: "ENTERTAIN_NUMBER", type: "BIGINT", remarks: "招待人数")   
            column(name: "EXP_REQUISITION_LINE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "申请单行ID，pk")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_REQUISITION_LINE_PK")} 
            column(name: "EXP_REQUISITION_HEADER_ID", type: "BIGINT", remarks: "申请单头ID")  {constraints(nullable:"false")}  
            column(name: "LINE_NUMBER", type: "BIGINT", remarks: "行号")  {constraints(nullable:"false")}  
            column(name: "REQ_PAGE_ELEMENT_CODE", type: "VARCHAR(30)", remarks: "申请单页面元素代码")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "EXP_REQUISITION_LINE", indexName: "EXP_REQUISITION_LINE_N1") {
                    column(name: "POSITION_ID")
                    column(name: "UNIT_ID")
                    column(name: "COMPANY_ID")
                }
                createIndex(tableName: "EXP_REQUISITION_LINE", indexName: "EXP_REQUISITION_LINE_N2") {
                    column(name: "ACC_ENTITY_ID")
                    column(name: "RESP_CENTER_ID")
                }
                createIndex(tableName: "EXP_REQUISITION_LINE", indexName: "EXP_REQUISITION_LINE_N3") {
                    column(name: "BGT_CENTER_ID")
                    column(name: "BGT_ENTITY_ID")
                }
                createIndex(tableName: "EXP_REQUISITION_LINE", indexName: "EXP_REQUISITION_LINE_N4") {
                    column(name: "EMPLOYEE_ID")
                }
            
        addUniqueConstraint(columnNames:"REQ_PAGE_ELEMENT_CODE,LINE_NUMBER,EXP_REQUISITION_HEADER_ID",tableName:"EXP_REQUISITION_LINE",constraintName: "EXP_REQUISITION_LINE_U1")
    }


}