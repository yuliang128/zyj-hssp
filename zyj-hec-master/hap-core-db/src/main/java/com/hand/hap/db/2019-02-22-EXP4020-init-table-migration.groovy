package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-02-22-EXP4020-init-table-migration.groovy') {


    changeSet(author: "jiangxz", id: "2019-02-22-EXP_MO_REQ_TYPE_REF_HD_DIM") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_MO_REQ_TYPE_REF_HD_DIM_S', startValue:"10001")
        }

        createTable(tableName: "EXP_MO_REQ_TYPE_REF_HD_DIM", remarks: "管理组织级申请单类型关联头维度") {

            column(name: "REF_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "pk")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_MO_REQ_TYPE_REF_HD_DIM_PK")} 
            column(name: "MO_EXP_REQ_TYPE_ID", type: "BIGINT", remarks: "管理组织级报销单类型ID")  {constraints(nullable:"false")}  
            column(name: "DIMENSION_ID", type: "BIGINT", remarks: "维度ID")  {constraints(nullable:"false")}  
            column(name: "LAYOUT_PRIORITY", type: "BIGINT", remarks: "布局顺序")  {constraints(nullable:"false")}  
            column(name: "DEFAULT_DIM_VALUE_ID", type: "BIGINT", remarks: "默认维值ID")   
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
        
        addUniqueConstraint(columnNames:"MO_EXP_REQ_TYPE_ID,DIMENSION_ID",tableName:"EXP_MO_REQ_TYPE_REF_HD_DIM",constraintName: "EXP_MO_REQ_TYPE_REF_HD_DIM_U1")
    }


    changeSet(author: "jiangxz", id: "2019-02-22-EXP_MO_REQ_TYPE_REF_HD_OBJ") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_MO_REQ_TYPE_REF_HD_OBJ_S', startValue:"10001")
        }

        createTable(tableName: "EXP_MO_REQ_TYPE_REF_HD_OBJ", remarks: "管理组织级申请单类型关联头费用对象") {

            column(name: "REF_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "pk")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_MO_REQ_TYPE_REF_HD_OBJ_PK")} 
            column(name: "MO_EXP_REQ_TYPE_ID", type: "BIGINT", remarks: "管理组织级申请单类型ID")  {constraints(nullable:"false")}  
            column(name: "MO_EXP_OBJ_TYPE_ID", type: "BIGINT", remarks: "管理组织级费用对象类型ID")  {constraints(nullable:"false")}  
            column(name: "REQUIRED_FLAG", type: "VARCHAR(1)", remarks: "是否必输")  {constraints(nullable:"false")}  
            column(name: "LAYOUT_PRIORITY", type: "BIGINT", remarks: "布局顺序")  {constraints(nullable:"false")}  
            column(name: "DEFAULT_MO_OBJECT_ID", type: "BIGINT", remarks: "默认费用对象ID")   
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
        
        addUniqueConstraint(columnNames:"MO_EXP_OBJ_TYPE_ID,MO_EXP_REQ_TYPE_ID",tableName:"EXP_MO_REQ_TYPE_REF_HD_OBJ",constraintName: "EXP_MO_REQ_TYPE_REF_HD_OBJ_U1")
    }


}