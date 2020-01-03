package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-04-23-FND1040-init-table-migration.groovy') {


    changeSet(author: "zhongyu.wang@hand-china", id: "2019-04-23-FND_UOMS") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'FND_UOMS_S', startValue:"10001")
        }

        createTable(tableName: "FND_UOM", remarks: "管理组织级计量单位定义") {

            column(name: "UOM_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "FND_UOMS_PK")} 
            column(name: "UOM_TYPE_ID", type: "BIGINT", remarks: "计量单位类型ID")  {constraints(nullable:"false")}  
            column(name: "UOM_CODE", type: "VARCHAR(30)", remarks: "计量单位代码")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "计量单位描述ID")   
            column(name: "PRECISION", type: "BIGINT", remarks: "精度")   
            column(name: "META_CONVERSION_RATE", type: "BIGINT", remarks: "元单位转换率")   
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
        
        addUniqueConstraint(columnNames:"UOM_CODE",tableName:"FND_UOM",constraintName: "FND_UOMS_U1")
    }

    changeSet(author:"zhongyu.wang@hand-china", id: "2019-04-23-FND_UOMS_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'FND_UOMS_TL_S', startValue: "10001")
        }

        createTable(tableName: "FND_UOM_TL", remarks: "管理组织级计量单位定义多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "UOM_ID", type: "BIGINT",  remarks: "主键，供其他表外键使用")  {constraints(nullable: "false", primaryKey: "true")} 
            column(name: "DESCRIPTION", type: "VARCHAR(500)",  remarks: "计量单位描述")
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



    changeSet(author: "zhognyu.wang", id: "2019-04-24-FND_UOM_ASGN_MO") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'FND_UOM_ASGN_MO_S', startValue:"10001")
        }

        createTable(tableName: "FND_UOM_ASGN_MO", remarks: "计量单位分配管理组织") {

            column(name: "ASSIGN_MO_ID", type: "BIGINT",autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "FND_UOM_ASGN_MO_PK")}
            column(name: "UOM_ID", type: "BIGINT", remarks: "管理组织级计量单位ID")  {constraints(nullable:"false")}
            column(name: "MAG_ORG_ID", type: "BIGINT", remarks: "管理组织ID")  {constraints(nullable:"false")}
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

        addUniqueConstraint(columnNames:"MAG_ORG_ID,UOM_ID",tableName:"FND_UOM_ASGN_MO",constraintName: "FND_UOM_ASGN_MO_U1")
    }

    changeSet(author: "zhongyu.wang@hand-china.com", id: "2019-04-25-FND_UOM_ASGN_COM") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'FND_UOM_ASGN_COM_S', startValue:"10001")
        }

        createTable(tableName: "FND_UOM_ASGN_COM", remarks: "计量单位分配公司") {

            column(name: "ASSIGN_COM_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "FND_UOM_ASGN_COM_PK")}
            column(name: "ASSIGN_MO_ID", type: "BIGINT", remarks: "计量单位分配管理组织ID")  {constraints(nullable:"false")}
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "管理公司ID")  {constraints(nullable:"false")}
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

        addUniqueConstraint(columnNames:"ASSIGN_MO_ID,COMPANY_ID",tableName:"FND_UOM_ASGN_COM",constraintName: "FND_UOM_ASGN_COM_U1")
    }
}