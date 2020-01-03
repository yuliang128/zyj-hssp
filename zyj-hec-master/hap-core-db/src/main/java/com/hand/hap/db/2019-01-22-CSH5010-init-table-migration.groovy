package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-01-22-CSH5010-init-table-migration.groovy') {


    changeSet(author: "dingwei.ma@hand-china.com", id: "2019-01-22-CSH_DOC_PAY_ACC_ENTITY") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_DOC_PAY_ACC_ENTITY_S', startValue:"10001")
        }

        createTable(tableName: "CSH_DOC_PAY_ACC_ENTITY", remarks: "单据付款实体表，取代csh_doc_payment_company") {

            column(name: "ENTITY_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_DOC_PAY_ACC_ENTITY_PK")} 
            column(name: "DOC_CATEGORY", type: "VARCHAR(30)", remarks: "单据类别")  {constraints(nullable:"false")}  
            column(name: "DOC_COMPANY_ID", type: "BIGINT", remarks: "单据公司ID")  {constraints(nullable:"false")}  
            column(name: "DOC_TYPE_ID", type: "BIGINT", remarks: "单据类型ID")  {constraints(nullable:"false")}  
            column(name: "DOC_ID", type: "BIGINT", remarks: "单据ID")  {constraints(nullable:"false")}  
            column(name: "DOC_LINE_ID", type: "BIGINT", remarks: "单据行ID")  {constraints(nullable:"false")}  
            column(name: "PAYMENT_METHOD_ID", type: "BIGINT", remarks: "付款方式")   
            column(name: "PAYEE_CATEGORY", type: "VARCHAR(30)", remarks: "收款方类别")   
            column(name: "PAYMENT_ACC_ENTITY_ID", type: "BIGINT", remarks: "付款核算主体ID")  {constraints(nullable:"false")}  
            column(name: "PAYMENT_STATUS", type: "VARCHAR(30)", remarks: "支付状态（NEVER无需支付，FROZEN冻结，PENDING待支付，ALLOWED已确认，PAID已支付）")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "CSH_DOC_PAY_ACC_ENTITY", indexName: "CSH_DOC_PAY_ACC_ENTITY_N1") {
                    column(name: "PAYMENT_ACC_ENTITY_ID")
                }
            
        addUniqueConstraint(columnNames:"DOC_CATEGORY,DOC_ID,DOC_LINE_ID",tableName:"CSH_DOC_PAY_ACC_ENTITY",constraintName: "CSH_DOC_PAY_ACC_ENTITY_U1")
    }


}