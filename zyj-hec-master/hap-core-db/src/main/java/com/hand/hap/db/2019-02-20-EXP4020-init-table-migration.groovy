package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-02-20-EXP4020-init-table-migration.groovy') {


    changeSet(author: "jiangxz", id: "2019-02-20-EXP_MO_REQ_TYPE_REF_ELE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_MO_REQ_TYPE_REF_ELE_S', startValue:"10001")
        }

        createTable(tableName: "EXP_MO_REQ_TYPE_REF_ELE", remarks: "管理组织级费用申请单类型关联页面元素") {

            column(name: "REQ_PAGE_ELE_REF_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "管理组织级费用申请单类型下页面元素关联ID,PK")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_MO_REQ_TYPE_REF_ELE_PK")}
            column(name: "MO_EXP_REQ_TYPE_ID", type: "BIGINT", remarks: "管理组织级费用申请单类型ID")  {constraints(nullable:"false")}  
            column(name: "REQ_PAGE_ELEMENT_ID", type: "BIGINT", remarks: "费用申请单页面元素ID")  {constraints(nullable:"false")}  
            column(name: "SEQUENCE", type: "BIGINT", remarks: "页面排序顺序")   
            column(name: "DOC_TYPE_CODE", type: "VARCHAR(30)", remarks: "表单类型（SYSCODE：EXP_DOC_PAGE_TYPE）")  {constraints(nullable:"false")}  
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
        
        addUniqueConstraint(columnNames:"REQ_PAGE_ELEMENT_ID,MO_EXP_REQ_TYPE_ID",tableName:"EXP_MO_REQ_TYPE_REF_ELE",constraintName: "EXP_MO_REQ_TYPE_REF_ELE_U1")
    }


}