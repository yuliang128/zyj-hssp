package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-03-01-ACP1135-init-table-migration.groovy') {


    changeSet(author: "hui.lu@hand-china.com", id: "2019-03-01-ACP_MO_PAY_REQ_TYPE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'ACP_MO_PAY_REQ_TYPE_S', startValue:"10001")
        }

        createTable(tableName: "ACP_MO_PAY_REQ_TYPE", remarks: "管理组织级付款申请单类型") {

            column(name: "MO_PAY_REQ_TYPE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "ACP_MO_PAY_REQ_TYPE_PK")} 
            column(name: "MAG_ORG_ID", type: "BIGINT", remarks: "管理组织ID")  {constraints(nullable:"false")}  
            column(name: "MO_PAY_REQ_TYPE_CODE", type: "VARCHAR(30)", remarks: "付款申请单类型代码")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "付款申请单名称")
            column(name: "CURRENCY_CODE", type: "VARCHAR(30)", remarks: "币种")   
            column(name: "AUTO_APPROVE_FLAG", type: "VARCHAR(1)", remarks: "自审批标志")  {constraints(nullable:"false")}
            column(name: "AUTO_AUDIT_FLAG", type: "VARCHAR(1)", remarks: "自审核标志")  {constraints(nullable:"false")}
            column(name: "BUSINESS_FLAG", type: "VARCHAR(1)", remarks: "经营类标志")  {constraints(nullable:"false")}  
            column(name: "PAYMENT_TYPE_CODE", type: "VARCHAR(30)", remarks: "付款类型（SYSCODE：ACP_PAYMENT_TYPE_CODE）")  {constraints(nullable:"false")}  
            column(name: "REPORT_NAME", type: "VARCHAR(2000)", remarks: "报表名称")   
            column(name: "ICON", type: "VARCHAR(255)", remarks: "图标（路径/CSS类）")   
            column(name: "CAPTION_HDS_ID", type: "BIGINT", remarks: "填写说明ID")
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
        
        addUniqueConstraint(columnNames:"MO_PAY_REQ_TYPE_CODE",tableName:"ACP_MO_PAY_REQ_TYPE",constraintName: "ACP_MO_PAY_REQ_TYPES_U1")
    }

    changeSet(author:"hui.lu@hand-china.com", id: "2019-03-01-ACP_MO_PAY_REQ_TYPE_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'ACP_MO_PAY_REQ_TYPE_TL_S', startValue: "10001")
        }

        createTable(tableName: "ACP_MO_PAY_REQ_TYPE_TL", remarks: "管理组织级付款申请单类型多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "MO_PAY_REQ_TYPE_ID", type: "BIGINT",  remarks: "主键，供其他表外键使用")  {constraints(nullable: "false", primaryKey: "true")} 
            column(name: "DESCRIPTION", type: "VARCHAR(500)",  remarks: "付款申请单名称")
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

}