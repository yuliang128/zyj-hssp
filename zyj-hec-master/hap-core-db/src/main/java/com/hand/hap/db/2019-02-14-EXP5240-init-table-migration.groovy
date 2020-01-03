package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-02-14-EXP5240-init-table-migration.groovy') {


    changeSet(author: "hui.zhao01@hand-china.com", id: "2019-02-14-VAT_INVOICE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'VAT_INVOICE_S', startValue:"10001")
        }

        createTable(tableName: "VAT_INVOICE", remarks: "增值税发票") {

            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "INVOICE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "VAT_INVOICE_PK")} 
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "员工")  {constraints(nullable:"false")}  
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体")   
            column(name: "INVOICE_CODE", type: "VARCHAR(200)", remarks: "发票代码")   
            column(name: "INVOICE_NUMBER", type: "VARCHAR(200)", remarks: "发票号")   
            column(name: "INVOICE_DATE", type: "DATETIME", remarks: "开票日期")  {constraints(nullable:"false")}  
            column(name: "INVOICE_DISTRECT", type: "VARCHAR(200)", remarks: "发票地区")   
            column(name: "MACHINE_NUMBER", type: "VARCHAR(200)", remarks: "机器号")   
            column(name: "CHECK_CODE", type: "VARCHAR(200)", remarks: "校验码")   
            column(name: "CHECK_CODE_LAST_CHARS", type: "VARCHAR(30)", remarks: "校验码后六位")   
            column(name: "PURCHASER_NAME", type: "VARCHAR(2000)", remarks: "购买方名称")   
            column(name: "PURCHASER_TAX_NUMBER", type: "VARCHAR(200)", remarks: "购买方税号")   
            column(name: "PURCHASER_ADDRESS_PHONE", type: "VARCHAR(2000)", remarks: "购买方地址和电话")   
            column(name: "PURCHASER_BANK_ACCOUNT", type: "VARCHAR(200)", remarks: "购买方银行账号")   
            column(name: "AMOUNT", type: "BIGINT", remarks: "金额")  {constraints(nullable:"false")}  
            column(name: "AMOUNT_ZHS", type: "VARCHAR(200)", remarks: "金额大写")  {constraints(nullable:"false")}  
            column(name: "TAX_AMOUNT", type: "BIGINT", remarks: "税金")   
            column(name: "WITHOUT_TAX_AMOUNT", type: "BIGINT", remarks: "不含税金额")   
            column(name: "SELLER_NAME", type: "VARCHAR(2000)", remarks: "销售方名称")   
            column(name: "SELLER_TAX_NUMBER", type: "VARCHAR(200)", remarks: "销售方税号")   
            column(name: "SELLER_ADDRESS_PHONE", type: "VARCHAR(2000)", remarks: "销售方地址和电话")   
            column(name: "SELLER_BANK_ACCOUNT", type: "VARCHAR(200)", remarks: "销售方银行账号")   
            column(name: "INVOICE_TYPE", type: "VARCHAR(30)", remarks: "发票类型，SYSCODE：VAT_INVOICE_TYPE")   
            column(name: "INVOICE_SOURCE", type: "VARCHAR(30)", remarks: "发票来源，SYSCODE：VAT_INVOICE_SOURCE")  {constraints(nullable:"false")}  
            column(name: "AUTHENTICATION_STATUS", type: "VARCHAR(30)", remarks: "验证状态，SYSCODE：VAT_INVOICE_AUTH_STATUS")   
            column(name: "AUTHENTICATION_DATE", type: "DATETIME", remarks: "发票认证日期")   
            column(name: "INVOICE_CATEGORY_ID", type: "BIGINT", remarks: "发票种类")   
            column(name: "CONFIRM_FLAG", type: "VARCHAR(1)", remarks: "发票确认标识")  {constraints(nullable:"false")}  
            column(name: "CONFIRM_DATE", type: "DATETIME", remarks: "发票确认日期")   
            column(name: "INVOICE_STATUS", type: "VARCHAR(30)", remarks: "发票状态，SYSCODE：VAT_INVOICE_STATUS")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "VARCHAR(2000)", remarks: "发票描述")   
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "VAT_INVOICE", indexName: "VAT_INVOICE_N1") {
                    column(name: "INVOICE_DATE")
                }
                createIndex(tableName: "VAT_INVOICE", indexName: "VAT_INVOICE_N2") {
                    column(name: "EMPLOYEE_ID")
                }
                createIndex(tableName: "VAT_INVOICE", indexName: "VAT_INVOICE_N3") {
                    column(name: "SELLER_TAX_NUMBER")
                }
                createIndex(tableName: "VAT_INVOICE", indexName: "VAT_INVOICE_N4") {
                    column(name: "PURCHASER_TAX_NUMBER")
                }
            
        addUniqueConstraint(columnNames:"INVOICE_CODE,INVOICE_NUMBER",tableName:"VAT_INVOICE",constraintName: "VAT_INVOICE_U1")
    }


    changeSet(author: "hui.zhao01@hand-china.com", id: "2019-02-14-VAT_INVOICE_CATEGORY") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'VAT_INVOICE_CATEGORY_S', startValue:"10001")
        }

        createTable(tableName: "VAT_INVOICE_CATEGORY", remarks: "发票种类") {

            column(name: "INVOICE_CATEGORY_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键，供其他表外键使用")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "VAT_INVOICE_CATEGORY_PK")} 
            column(name: "MAG_ORG_ID", type: "BIGINT", remarks: "管理组织")  {constraints(nullable:"false")}  
            column(name: "INVOICE_CATEGORY_CODE", type: "VARCHAR(30)", remarks: "发票种类代码")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述")  {constraints(nullable:"false")}  
            column(name: "INVOICE_ATTRIBUTES", type: "VARCHAR(30)", remarks: "发票属性（SYSCODE：VAT_INVOICE_ATTRIBUTES）")  {constraints(nullable:"false")}  
            column(name: "AUTHENTICATING_FLAG", type: "VARCHAR(1)", remarks: "认证标志")  {constraints(nullable:"false")}  
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
        
        addUniqueConstraint(columnNames:"MAG_ORG_ID,INVOICE_CATEGORY_CODE",tableName:"VAT_INVOICE_CATEGORY",constraintName: "VAT_INVOICE_CATEGORY_U1")
    }

    changeSet(author:"hui.zhao01@hand-china.com", id: "2019-02-14-VAT_INVOICE_CATEGORY_TL") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'VAT_INVOICE_CATEGORY_TL_S', startValue: "10001")
        }

        createTable(tableName: "VAT_INVOICE_CATEGORY_TL", remarks: "发票种类多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {constraints(nullable: "false", primaryKey: "true")}
            column(name: "INVOICE_CATEGORY_ID", type: "BIGINT",  remarks: "主键，供其他表外键使用")  {constraints(nullable: "false", primaryKey: "true")} 
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

    changeSet(author: "hui.zhao01@hand-china.com", id: "2019-02-14-VAT_INVOICE_REL_TAX_LN") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'VAT_INVOICE_REL_TAX_LN_S', startValue:"10001")
        }

        createTable(tableName: "VAT_INVOICE_REL_TAX_LN", remarks: "发票关联税金分配") {

            column(name: "RELATION_TAX_LINE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "VAT_INVOICE_REL_TAX_LN_PK")} 
            column(name: "RELATION_ID", type: "BIGINT", remarks: "发票关联")  {constraints(nullable:"false")}  
            column(name: "TAX_LINE_ID", type: "BIGINT", remarks: "发票税金行")   
            column(name: "TAX_TYPE_ID", type: "BIGINT", remarks: "税率类型")   
            column(name: "SPLITTED_TAX_AMOUNT", type: "BIGINT", remarks: "拆分税金")  {constraints(nullable:"false")}  
            column(name: "PAYMENT_SP_TAX_AMOUNT", type: "BIGINT", remarks: "支付币种拆分税金（拆分税金 * 业务币种->支付币种汇率）")  {constraints(nullable:"false")}  
            column(name: "FUNCTIONAL_SP_TAX_AMOUNT", type: "BIGINT", remarks: "本位币拆分税金（支付币种拆分税金 * 支付币种->本位币汇率）")  {constraints(nullable:"false")}  
            column(name: "REPORT_TAX_AMOUNT", type: "BIGINT", remarks: "实际报销税金")  {constraints(nullable:"false")}  
            column(name: "PAYMENT_RP_TAX_AMOUNT", type: "BIGINT", remarks: "支付币种实际报销税金（实际报销税金 * 业务币种->支付币种汇率）")  {constraints(nullable:"false")}  
            column(name: "FUNCTIONAL_RP_TAX_AMOUNT", type: "BIGINT", remarks: "本位币实际报销税金（支付币种实际报销税金 * 支付币种->本位币汇率）")  {constraints(nullable:"false")}  
            column(name: "INVOICE_ITEM_ID", type: "BIGINT", remarks: "发票项目")   
            column(name: "INVOICE_USEDE_ID", type: "BIGINT", remarks: "发票用途")   
            column(name: "MANUAL_SPLITTED_FLAG", type: "VARCHAR(1)", remarks: "手动拆分标志")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "VAT_INVOICE_REL_TAX_LN", indexName: "VAT_INVOICE_REL_TAX_LN_N1") {
                    column(name: "TAX_LINE_ID")
                }
                createIndex(tableName: "VAT_INVOICE_REL_TAX_LN", indexName: "VAT_INVOICE_REL_TAX_LN_N2") {
                    column(name: "TAX_TYPE_ID")
                }
                createIndex(tableName: "VAT_INVOICE_REL_TAX_LN", indexName: "VAT_INVOICE_REL_TAX_LN_N3") {
                    column(name: "INVOICE_ITEM_ID")
                }
                createIndex(tableName: "VAT_INVOICE_REL_TAX_LN", indexName: "VAT_INVOICE_REL_TAX_LN_N4") {
                    column(name: "INVOICE_USEDE_ID")
                }
            
        addUniqueConstraint(columnNames:"RELATION_ID,TAX_TYPE_ID",tableName:"VAT_INVOICE_REL_TAX_LN",constraintName: "VAT_INVOICE_REL_TAX_LN_U1")
    }


    changeSet(author: "hui.zhao01@hand-china.com", id: "2019-02-14-VAT_INVOICE_TAX_LINE") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'VAT_INVOICE_TAX_LINE_S', startValue:"10001")
        }

        createTable(tableName: "VAT_INVOICE_TAX_LINE", remarks: "发票税金行") {

            column(name: "TAX_LINE_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "VAT_INVOICE_TAX_LINE_PK")} 
            column(name: "INVOICE_LINE_ID", type: "BIGINT", remarks: "发票行")  {constraints(nullable:"false")}  
            column(name: "TAX_TYPE_ID", type: "BIGINT", remarks: "税率类型")   
            column(name: "TAX_RATE", type: "BIGINT", remarks: "税率")   
            column(name: "TAX_AMOUNT", type: "BIGINT", remarks: "税金")  {constraints(nullable:"false")}  
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}  
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
            createIndex(tableName: "VAT_INVOICE_TAX_LINE", indexName: "VAT_INVOICE_TAX_LINE_N1") {
                    column(name: "INVOICE_LINE_ID")
                }
        
    }

    changeSet(author: "hui.zhao01@hand-china.com", id: "2019-02-14-VAT_INVOICE_RELATION") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'VAT_INVOICE_RELATION_S', startValue:"10001")
        }

        createTable(tableName: "VAT_INVOICE_RELATION", remarks: "增值税发票关联") {

            column(name: "RELATION_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "VAT_INVOICE_RELATION_PK")}
            column(name: "DOCUMENT_CATEGORY", type: "VARCHAR(30)", remarks: "单据类别")  {constraints(nullable:"false")}
            column(name: "DOCUMENT_ID", type: "BIGINT", remarks: "单据")  {constraints(nullable:"false")}
            column(name: "DOCUMENT_LINE_ID", type: "BIGINT", remarks: "单据行")  {constraints(nullable:"false")}
            column(name: "DOCUMENT_DIST_ID", type: "BIGINT", remarks: "单据分配行")
            column(name: "INVOICE_ID", type: "BIGINT", remarks: "发票")
            column(name: "INVOICE_LINE_ID", type: "BIGINT", remarks: "发票行")
            column(name: "BUSINESS_AMOUNT", type: "BIGINT", remarks: "实际报销业务金额")
            column(name: "BIZ2PAY_EXCHANGE_RATE", type: "BIGINT", remarks: "业务币种->支付币种汇率")
            column(name: "PAYMENT_AMOUNT", type: "BIGINT", remarks: "实际报销支付币种金额")
            column(name: "PAY2FUN_EXCHANGE_RATE", type: "BIGINT", remarks: "支付币种->本位币汇率")
            column(name: "FUNCTIONAL_AMOUNT", type: "BIGINT", remarks: "实际报销本位币金额")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")  {constraints(nullable:"false")}
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }
        createIndex(tableName: "VAT_INVOICE_RELATION", indexName: "VAT_INVOICE_RELATION_N1") {
            column(name: "DOCUMENT_CATEGORY")
            column(name: "DOCUMENT_ID")
            column(name: "DOCUMENT_LINE_ID")
            column(name: "DOCUMENT_DIST_ID")
        }
        createIndex(tableName: "VAT_INVOICE_RELATION", indexName: "VAT_INVOICE_RELATION_N2") {
            column(name: "INVOICE_ID")
            column(name: "INVOICE_LINE_ID")
        }

    }

    changeSet(author: "hui.zhao01@hand-china.com", id: "2019-02-22-EXP_AMORTIZATION_ACCOUNT") {

        if(mhi.getDbType().isSupportSequence()){
            createSequence(sequenceName: 'EXP_AMORTIZATION_ACCOUNT_S', startValue:"10001")
        }

        createTable(tableName: "EXP_AMORTIZATION_ACCOUNT", remarks: "待摊科目表") {

            column(name: "AMR_ACCOUNT_ID", type: "BIGINT",autoIncrement: true, startWith: "10001",  remarks: "表主键")  {constraints(nullable:"false", primaryKey: "true", primaryKeyName: "EXP_AMORTIZATION_ACCOUNT_PK")}
            column(name: "SET_OF_BOOKS_ID", type: "BIGINT", remarks: "账套")  {constraints(nullable:"false")}
            column(name: "ACCOUNT_ID", type: "BIGINT", remarks: "科目")  {constraints(nullable:"false")}
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")  {constraints(nullable:"false")}
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")  {constraints(nullable:"false")}
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")  {constraints(nullable:"false")}
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户")  {constraints(nullable:"false")}
            column(name: "OBJECT_VERSION_NUMBER",type:"BIGINT",defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue : "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue : "-1")

        }

        addUniqueConstraint(columnNames:"SET_OF_BOOKS_ID,ACCOUNT_ID",tableName:"EXP_AMORTIZATION_ACCOUNT",constraintName: "EXP_AMORTIZATION_ACCOUNT_U1")
    }

}