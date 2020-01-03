package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: 'CSH7020-2019-02-27-init-table-migration.groovy') {


    changeSet(author: "dingwei.ma@hand-china.com", id: "2019-02-27-CSH_REPAYMENT_REGISTER_DIST") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_REPAYMENT_REGISTER_DIST_S', startValue:"10001")
        }

        createTable(tableName: "CSH_REPAYMENT_REGISTER_DIST", remarks: "还款登记单分配行表") {

            column(name: "REGISTER_DIST_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_REPAYMENT_REGISTER_DIST_PK")} 
            column(name: "REGISTER_LNS_ID", type: "BIGINT", remarks: "还款登记单行ID")  {constraints(nullable:"false")}  
            column(name: "PAY_TRX_LINE_ID", type: "BIGINT", remarks: "关联付款现金事物行ID")  {constraints(nullable:"false")}  
            column(name: "REPAY_TRX_LINE_ID", type: "BIGINT", remarks: "关联付款预付款现金事物行ID")  {constraints(nullable:"false")}  
            column(name: "AMOUNT", type: "NUMBER", remarks: "金额")  {constraints(nullable:"false")}  
            column(name: "REPAYMENT_FLAG", type: "VARCHAR(1)", remarks: "还款标志")  {constraints(nullable:"false")}  
            column(name: "REPAYMENT_PAY_TRX_LINE_ID", type: "BIGINT", remarks: "付款现金事物ID")   
            column(name: "REPAYMENT_REPAY_TRX_LINE_ID", type: "BIGINT", remarks: "预付款现金事物ID")   
            column(name: "REVERSE_FLAG", type: "VARCHAR(1)", remarks: "反冲标志")  {constraints(nullable:"false")}  
            column(name: "REVERSE_PAY_TRX_LINE_ID", type: "BIGINT", remarks: "反冲现金事物行ID")   
            column(name: "REVERSE_REPAY_TRX_LINE_ID", type: "BIGINT", remarks: "反冲预付款现金事物行ID")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "CSH_REPAYMENT_REGISTER_DIST", indexName: "CSH_REPAYMENT_REGISTER_DIST_N1") {
                    column(name: "REGISTER_LNS_ID")
                }
                createIndex(tableName: "CSH_REPAYMENT_REGISTER_DIST", indexName: "CSH_REPAYMENT_REGISTER_DIST_N2") {
                    column(name: "REPAY_TRX_LINE_ID")
                    column(name: "PAY_TRX_LINE_ID")
                    column(name: "REPAYMENT_PAY_TRX_LINE_ID")
                    column(name: "REPAYMENT_REPAY_TRX_LINE_ID")
                    column(name: "REVERSE_PAY_TRX_LINE_ID")
                    column(name: "REVERSE_REPAY_TRX_LINE_ID")
                }
        
    }


    changeSet(author: "dingwei.ma@hand-china.com", id: "2019-02-27-CSH_REPAYMENT_REGISTER_HD") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_REPAYMENT_REGISTER_HD_S', startValue:"10001")
        }

        createTable(tableName: "CSH_REPAYMENT_REGISTER_HD", remarks: "还款登记单头表") {

            column(name: "REGISTER_HDS_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，共其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_REPAYMENT_REGISTER_HD_PK")} 
            column(name: "REGISTER_NUMBER", type: "VARCHAR(200)", remarks: "还款登记单号")  {constraints(nullable:"false")}  
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "管理公司ID")  {constraints(nullable:"false")}  
            column(name: "UNIT_ID", type: "BIGINT", remarks: "部门ID")  {constraints(nullable:"false")}  
            column(name: "POSITION_ID", type: "BIGINT", remarks: "岗位ID")  {constraints(nullable:"false")}  
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "员工ID")  {constraints(nullable:"false")}  
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体ID")  {constraints(nullable:"false")}  
            column(name: "MO_REPAYMENT_REG_TYPE_ID", type: "BIGINT", remarks: "还款登记单类型ID")  {constraints(nullable:"false")}  
            column(name: "BANK_ACCOUNT_ID", type: "BIGINT", remarks: "银行账户ID")  {constraints(nullable:"false")}  
            column(name: "PAYMENT_METHOD_ID", type: "BIGINT", remarks: "支付方式ID")  {constraints(nullable:"false")}  
            column(name: "REPAYMENT_DATE", type: "DATETIME", remarks: "还款日期")  {constraints(nullable:"false")}  
            column(name: "REPAYMENT_DATE_TZ", type: "TIMESTAMP", defaultValueComputed : "CURRENT_TIMESTAMP", remarks: "还款日期_业务时区")  {constraints(nullable:"false")}  
            column(name: "REPAYMENT_DATE_LTZ", type: "TIMESTAMP", defaultValueComputed : "CURRENT_TIMESTAMP", remarks: "还款日期_查询时区")  {constraints(nullable:"false")}  
            column(name: "AMOUNT", type: "NUMBER", remarks: "还款金额")  {constraints(nullable:"false")}  
            column(name: "REPAYMENT_BANK_NOTE", type: "VARCHAR(200)", remarks: "银行备注")   
            column(name: "DESCRIPTION", type: "VARCHAR(2000)", remarks: "说明")   
            column(name: "REPAYMENT_STATUS", type: "VARCHAR(30)", remarks: "状态（SYSCODE：CSH_REPAYMENT_REGISTER_STATUS）")  {constraints(nullable:"false")}  
            column(name: "CASHIER_USER_ID", type: "BIGINT", remarks: "出纳确认用户ID")   
            column(name: "CASHIER_CONFIRM_DATE", type: "DATETIME", remarks: "出纳确认日期")   
            column(name: "ACCOUNTING_USER_ID", type: "BIGINT", remarks: "财务确认用户ID")   
            column(name: "ACCOUNTING_CONFIRM_DATE", type: "DATETIME", remarks: "财务确认日期")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "CSH_REPAYMENT_REGISTER_HD", indexName: "CSH_REPAYMENT_REGISTER_HD_N1") {
                    column(name: "COMPANY_ID")
                    column(name: "UNIT_ID")
                    column(name: "POSITION_ID")
                    column(name: "EMPLOYEE_ID")
                }
                createIndex(tableName: "CSH_REPAYMENT_REGISTER_HD", indexName: "CSH_REPAYMENT_REGISTER_HD_N2") {
                    column(name: "ACC_ENTITY_ID")
                }
                createIndex(tableName: "CSH_REPAYMENT_REGISTER_HD", indexName: "CSH_REPAYMENT_REGISTER_HD_N3") {
                    column(name: "MO_REPAYMENT_REG_TYPE_ID")
                }
                createIndex(tableName: "CSH_REPAYMENT_REGISTER_HD", indexName: "CSH_REPAYMENT_REGISTER_HD_N4") {
                    column(name: "REPAYMENT_DATE")
                }
            
        addUniqueConstraint(columnNames:"REGISTER_NUMBER",tableName:"CSH_REPAYMENT_REGISTER_HD",constraintName: "CSH_REPAYMENT_REGISTER_HD_U1")
    }


    changeSet(author: "dingwei.ma@hand-china.com", id: "2019-02-27-CSH_REPAYMENT_REGISTER_LN") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_REPAYMENT_REGISTER_LN_S', startValue:"10001")
        }

        createTable(tableName: "CSH_REPAYMENT_REGISTER_LN", remarks: "还款登记单行表") {

            column(name: "REGISTER_LNS_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其它表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_REPAYMENT_REGISTER_LN_PK")} 
            column(name: "REGISTER_HDS_ID", type: "BIGINT", remarks: "还款登记单头ID")  {constraints(nullable:"false")}  
            column(name: "LINE_NUMBER", type: "BIGINT", remarks: "行号")  {constraints(nullable:"false")}  
            column(name: "PAYMENT_REQUISITION_LINE_ID", type: "BIGINT", remarks: "借款申请单行ID")  {constraints(nullable:"false")}  
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "管理公司ID")  {constraints(nullable:"false")}  
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体ID")  {constraints(nullable:"false")}  
            column(name: "ACCOUNT_NAME", type: "VARCHAR(200)", remarks: "还款人户名")   
            column(name: "ACCOUNT_NUMBER", type: "VARCHAR(200)", remarks: "还款人账号")   
            column(name: "AMOUNT", type: "NUMBER", remarks: "金额")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "VARCHAR(2000)", remarks: "说明")   
            column(name: "PAYEE_CATEGORY", type: "VARCHAR(30)", remarks: "还款人类别")   
            column(name: "PAYEE_ID", type: "BIGINT", remarks: "还款人ID")   
            column(name: "CASH_FLOW_ITEM_ID", type: "BIGINT", remarks: "现金流量项ID")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "CSH_REPAYMENT_REGISTER_LN", indexName: "CSH_REPAYMENT_REGISTER_LN_N1") {
                    column(name: "PAYMENT_REQUISITION_LINE_ID")
                }
                createIndex(tableName: "CSH_REPAYMENT_REGISTER_LN", indexName: "CSH_REPAYMENT_REGISTER_LN_N2") {
                    column(name: "COMPANY_ID")
                }
                createIndex(tableName: "CSH_REPAYMENT_REGISTER_LN", indexName: "CSH_REPAYMENT_REGISTER_LN_N3") {
                    column(name: "ACC_ENTITY_ID")
                }
            
        addUniqueConstraint(columnNames:"LINE_NUMBER,REGISTER_HDS_ID",tableName:"CSH_REPAYMENT_REGISTER_LN",constraintName: "CSH_REPAYMENT_REGISTER_LN_U1")
    }

    changeSet(author: "dingwei.ma@hand-china.com", id: "2019-02-27-CSH_MO_REPAYMENT_REG_TYPE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_MO_REPAYMENT_REG_TYPE_S', startValue:"10001")
        }

        createTable(tableName: "CSH_MO_REPAYMENT_REG_TYPE", remarks: "管理组织级还款申请单类型") {

            column(name: "MO_REPAYMENT_REG_TYPE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "CSH_MO_REPAYMENT_REG_TYPE_PK")}
            column(name: "MAG_ORG_ID", type: "BIGINT", remarks: "管理组织ID")  {constraints(nullable:"false")}
            column(name: "MO_REPAYMENT_REG_TYPE_CODE", type: "VARCHAR(30)", remarks: "还款申请单类型代码")  {constraints(nullable:"false")}
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "还款申请单描述ID")
            column(name: "CROSS_ENTITY_FLAG", type: "VARCHAR(1)", remarks: "跨实体还款标志")  {constraints(nullable:"false")}
            column(name: "AUTO_AUDIT_FLAG", type: "VARCHAR(1)", remarks: "自审核标志")  {constraints(nullable:"false")}
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

        addUniqueConstraint(columnNames:"MAG_ORG_ID,MO_REPAYMENT_REG_TYPE_CODE",tableName:"CSH_MO_REPAYMENT_REG_TYPE",constraintName: "CSH_MO_REPAYMENT_REG_TYPE_U1")
    }

    changeSet(author:"dingwei.ma@hand-china.com", id: "2019-02-27-CSH_MO_REPAYMENT_REG_TYPE_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'CSH_MO_REPAYMENT_REG_TYPE_TL_S', startValue: "10001")
        }

        createTable(tableName: "CSH_MO_REPAYMENT_REG_TYPE_TL", remarks: "管理组织级还款申请单类型多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "MO_REPAYMENT_REG_TYPE_ID", type: "BIGINT",  remarks: "主键，供其他表外键使用")  {constraints(nullable: "false", primaryKey: "true")}
            column(name: "DESCRIPTION", type: "VARCHAR(500)",  remarks: "还款申请单描述ID")
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

    changeSet(author: "dingwei.ma@hand-china.com", id: "2019-03-01-SSC_TASK_POOL_LOCAL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'SSC_TASK_POOL_LOCAL_S', startValue:"10001")
        }

        createTable(tableName: "SSC_TASK_POOL_LOCAL", remarks: "本地任务池") {

            column(name: "TASK_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "SSC_TASK_POOL_LOCAL_PK")}
            column(name: "DOC_CATEGORY", type: "VARCHAR(30)", remarks: "单据类别")  {constraints(nullable:"false")}
            column(name: "DOC_TYPE_ID", type: "BIGINT", remarks: "单据类型ID")
            column(name: "DOC_ID", type: "BIGINT", remarks: "单据ID")  {constraints(nullable:"false")}
            column(name: "DOC_NUMBER", type: "VARCHAR(200)", remarks: "单据编号")
            column(name: "MAG_ORG_ID", type: "BIGINT", remarks: "管理组织ID")
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司ID")
            column(name: "UNIT_ID", type: "BIGINT", remarks: "部门ID")
            column(name: "POSITION_ID", type: "BIGINT", remarks: "岗位ID")
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "员工ID")
            column(name: "SET_OF_BOOKS_ID", type: "BIGINT", remarks: "账套ID")
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体ID")
            column(name: "ENTER_TIME", type: "DATETIME", remarks: "进入本池时间")  {constraints(nullable:"false")}
            column(name: "EXIT_TIME", type: "DATETIME", remarks: "退出本池时间")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        createIndex(tableName: "SSC_TASK_POOL_LOCAL", indexName: "SSC_TASK_POOL_LOCAL_N1") {
            column(name: "MAG_ORG_ID")
            column(name: "COMPANY_ID")
        }
        createIndex(tableName: "SSC_TASK_POOL_LOCAL", indexName: "SSC_TASK_POOL_LOCAL_N2") {
            column(name: "ACC_ENTITY_ID")
            column(name: "SET_OF_BOOKS_ID")
        }

        addUniqueConstraint(columnNames:"DOC_ID,DOC_CATEGORY",tableName:"SSC_TASK_POOL_LOCAL",constraintName: "SSC_TASK_POOL_LOCAL_U1")
    }


}