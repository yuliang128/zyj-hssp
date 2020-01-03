package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-2-25-CSH1075-init-table-migration.groovy') {


    changeSet(author: "yang.cai@hand-china.com", id: "2019-02-25-CSH_MO_PAY_USD_REF_FLOW_IT") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_MO_PAY_USD_REF_FLOW_IT_S', startValue:"10001")
        }

        createTable(tableName: "CSH_MO_PAY_USD_REF_FLOW_IT", remarks: "管理组织级付款用途关联现金流量项") {

            column(name: "REF_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "pk")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_MO_PAY_USD_REF_FLOW_IT_PK")} 
            column(name: "PAYMENT_USEDE_ID", type: "BIGINT", remarks: "付款用途ID")  {constraints(nullable:"false")}  
            column(name: "CASH_FLOW_ITEM_ID", type: "BIGINT", remarks: "现金流量项ID")  {constraints(nullable:"false")}  
            column(name: "DEFAULT_FLAG", type: "VARCHAR(1)", remarks: "默认标志")  {constraints(nullable:"false")}  
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
        
        addUniqueConstraint(columnNames:"PAYMENT_USEDE_ID,CASH_FLOW_ITEM_ID",tableName:"CSH_MO_PAY_USD_REF_FLOW_IT",constraintName: "CSH_MO_PAY_USD_REF_FLOW_ITS_U1")
    }
	
	changeSet(author: "yang.cai@hand-china.com", id: "2019-02-25-CSH_MO_PAYMENT_USED") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_MO_PAYMENT_USED_S', startValue:"10001")
        }

        createTable(tableName: "CSH_MO_PAYMENT_USED", remarks: "管理组织级付款用途") {

            column(name: "PAYMENT_USEDE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "付款用途ID，pk")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_MO_PAYMENT_USED_PK")} 
            column(name: "MAG_ORG_ID", type: "BIGINT", remarks: "管理组织ID")  {constraints(nullable:"false")}  
            column(name: "PAYMENT_USEDE_CODE", type: "VARCHAR(30)", remarks: "付款用途代码")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述")  {constraints(nullable:"false")}  
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
        
        addUniqueConstraint(columnNames:"PAYMENT_USEDE_CODE,MAG_ORG_ID",tableName:"CSH_MO_PAYMENT_USED",constraintName: "CSH_MO_PAYMENT_USEDES_U1")
    }

    changeSet(author:"yang.cai@hand-china.com", id: "2019-02-25-CSH_MO_PAYMENT_USED_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_MO_PAYMENT_USED_TL_S', startValue: "10001")
        }

        createTable(tableName: "CSH_MO_PAYMENT_USED_TL", remarks: "管理组织级付款用途多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "PAYMENT_USEDE_ID", type: "BIGINT",  remarks: "付款用途ID，pk")  {constraints(nullable: "false", primaryKey: "true")} 
            column(name: "DESCRIPTION", type: "VARCHAR(500)",  remarks: "描述")   
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
	
	changeSet(author: "yang.cai@hand-china.com", id: "2019-02-25-CSH_MO_PAY_USD_ASGN_COM") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_MO_PAY_USD_ASGN_COM_S', startValue:"10001")
        }

        createTable(tableName: "CSH_MO_PAY_USD_ASGN_COM", remarks: "管理组织级付款用途分配公司") {

            column(name: "ASSIGN_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "pk")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_MO_PAY_USD_ASGN_COM_PK")} 
            column(name: "PAYMENT_USEDE_ID", type: "BIGINT", remarks: "付款用途ID")  {constraints(nullable:"false")}  
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司ID")  {constraints(nullable:"false")}  
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
        
        addUniqueConstraint(columnNames:"COMPANY_ID,PAYMENT_USEDE_ID",tableName:"CSH_MO_PAY_USD_ASGN_COM",constraintName: "CSH_MO_PAY_USD_ASGN_COM_U1")
    }
	
	/*changeSet(author: "yang.cai@hand-china.com", id: "2019-02-25-CSH_DEFAULT_CASH_FLOW_ITEM") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_DEFAULT_CASH_FLOW_ITEM_S', startValue:"10001")
        }

        createTable(tableName: "CSH_DEFAULT_CASH_FLOW_ITEM", remarks: "默认现金流量项表") {

            column(name: "ASSIGN_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "分配ID")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_DEFAULT_CASH_FLOW_ITEM_PK")}
            column(name: "CASH_FLOW_ITEM_ID", type: "BIGINT", remarks: "现金流量项ID")  {constraints(nullable:"false")}
            column(name: "SET_OF_BOOKS_ID", type: "BIGINT", remarks: "帐套ID")  {constraints(nullable:"false")}
            column(name: "ACCOUNT_ID", type: "BIGINT", remarks: "科目ID")  {constraints(nullable:"false")}
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID")  {constraints(nullable:"false")}
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }

        addUniqueConstraint(columnNames:"ACCOUNT_ID,CASH_FLOW_ITEM_ID",tableName:"CSH_DEFAULT_CASH_FLOW_ITEM",constraintName: "CSH_DEFAULT_CASH_FLOW_ITEM_U1")
    }*/


}