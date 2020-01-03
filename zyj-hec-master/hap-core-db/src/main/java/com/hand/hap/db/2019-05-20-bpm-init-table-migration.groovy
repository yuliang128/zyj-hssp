package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: 'asd-init-table-migration.groovy') {


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-20-BPM_DOC_HEADER") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_DOC_HEADER_S', startValue: "10001")
        }

        createTable(tableName: "BPM_DOC_HEADER", remarks: "") {

            column(name: "DOC_HEADER_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_DOC_HEADER_PK")
            }
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "")
            column(name: "UNIT_ID", type: "BIGINT", remarks: "")
            column(name: "POSITION_ID", type: "BIGINT", remarks: "")
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "")
            column(name: "DOCUMENT_NUMBER", type: "VARCHAR(200)", remarks: "")
            column(name: "OA_FLOW_CATEGORY", type: "VARCHAR(200)", remarks: "")
            column(name: "OA_FLOW_SUBCATEGORY", type: "VARCHAR(200)", remarks: "")
            column(name: "DOCUMENT_DATE", type: "DATETIME", remarks: "")
            column(name: "DOCUMENT_STATUS", type: "VARCHAR(30)", remarks: "")
            column(name: "NOTE", type: "VARCHAR(2000)", remarks: "")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")

            column(name: "TEMPLATE_ID", type: "BIGINT", remarks: "")
            column(name: "PAGE_GROUP_ID", type: "BIGINT", remarks: "")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-25-BPM_DYNAMIC_DATA_LINE") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_DYNAMIC_DATA_LINE_S', startValue: "10001")
        }

        createTable(tableName: "BPM_DYNAMIC_DATA_LINE", remarks: "") {

            column(name: "DATA_LINE_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_DYNAMIC_DATA_LINE_PK")
            }
            column(name: "BUSINESS_CATEGORY", type: "VARCHAR(30)", remarks: "") { constraints(nullable: "false") }
            column(name: "DOC_ID", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "PARENT_BUSINESS_CATEGORY", type: "VARCHAR(30)", remarks: "")
            column(name: "PARENT_DOC_ID", type: "BIGINT", remarks: "")
            column(name: "DESCRIPTION", type: "TEXT", remarks: "描述")
            column(name: "TRANSACTION_DATE", type: "DATETIME", remarks: "交易发生日期")
            column(name: "PERIOD_NAME", type: "TEXT", remarks: "交易发生日期")
            column(name: "CURRENCY_CODE", type: "TEXT", remarks: "币种")
            column(name: "CURRENCY_NAME", type: "TEXT", remarks: "币种名称")
            column(name: "EXCHANGE_RATE_TYPE", type: "TEXT", remarks: "汇率类型")
            column(name: "EXCHANGE_RATE_TYPE_NAME", type: "TEXT", remarks: "汇率类型名称")
            column(name: "EXCHANGE_RATE", type: "NUMBER(12,5)", remarks: "汇率")
            column(name: "PRICE", type: "NUMBER(12,5)", remarks: "单价")
            column(name: "QUANTITY", type: "NUMBER(12,5)", remarks: "数量")
            column(name: "AMOUNT", type: "NUMBER(12,5)", remarks: "金额")
            column(name: "FUNCTIONAL_AMOUNT", type: "NUMBER(12,5)", remarks: "本币金额")
            column(name: "COMPANY_ID", type: "INT", remarks: "公司ID")
            column(name: "COMPANY_NAME", type: "TEXT", remarks: "公司名称")
            column(name: "UNIT_ID", type: "INT", remarks: "部门ID")
            column(name: "UNIT_NAME", type: "TEXT", remarks: "部门名称")
            column(name: "POSITION_ID", type: "INT", remarks: "岗位ID")
            column(name: "POSITION_NAME", type: "TEXT", remarks: "岗位名称")
            column(name: "EMPLOYEE_ID", type: "INT", remarks: "员工ID")
            column(name: "EMPLOYEE_NAME", type: "TEXT", remarks: "员工姓名")
            column(name: "SET_OF_BOOKS_ID", type: "INT", remarks: "账套ID")
            column(name: "SET_OF_BOOKS_NAME", type: "TEXT", remarks: "账套名称")
            column(name: "ACCOUNTING_ENTITY_ID", type: "INT", remarks: "核算主体ID")
            column(name: "ACCOUNTING_ENTITY_NAME", type: "TEXT", remarks: "核算主体名称")
            column(name: "RESP_CENTER_ID", type: "INT", remarks: "责任中心ID")
            column(name: "RESP_CENTER_NAME", type: "TEXT", remarks: "责任中心名称")
            column(name: "ACCOUNT_ID", type: "INT", remarks: "科目ID")
            column(name: "ACCOUNT_NAME", type: "INT", remarks: "科目名称")
            column(name: "PAYEE_CATEGORY", type: "TEXT", remarks: "收款方类别")
            column(name: "PAYEE_CATEGORY_NAME", type: "TEXT", remarks: "收款方类别名称")
            column(name: "PAYEE_ID", type: "INT", remarks: "收款方ID")
            column(name: "PAYEE_NAME", type: "INT", remarks: "收款方名称")
            column(name: "BANK_ACCOUNT_ID", type: "INT", remarks: "银行账户ID")
            column(name: "BANK_ACCOUNT_NUMBER", type: "TEXT", remarks: "银行账户")
            column(name: "BANK_ACCOUNT_NAME", type: "TEXT", remarks: "银行户名")
            column(name: "N001", type: "NUMBER(12,5)", remarks: "")
            column(name: "N002", type: "NUMBER(12,5)", remarks: "")
            column(name: "N003", type: "NUMBER(12,5)", remarks: "")
            column(name: "N004", type: "NUMBER(12,5)", remarks: "")
            column(name: "N005", type: "NUMBER(12,5)", remarks: "")
            column(name: "N006", type: "NUMBER(12,5)", remarks: "")
            column(name: "N007", type: "NUMBER(12,5)", remarks: "")
            column(name: "N008", type: "NUMBER(12,5)", remarks: "")
            column(name: "N009", type: "NUMBER(12,5)", remarks: "")
            column(name: "N010", type: "NUMBER(12,5)", remarks: "")
            column(name: "N011", type: "NUMBER(12,5)", remarks: "")
            column(name: "N012", type: "NUMBER(12,5)", remarks: "")
            column(name: "N013", type: "NUMBER(12,5)", remarks: "")
            column(name: "N014", type: "NUMBER(12,5)", remarks: "")
            column(name: "N015", type: "NUMBER(12,5)", remarks: "")
            column(name: "N016", type: "NUMBER(12,5)", remarks: "")
            column(name: "N017", type: "NUMBER(12,5)", remarks: "")
            column(name: "N018", type: "NUMBER(12,5)", remarks: "")
            column(name: "N019", type: "NUMBER(12,5)", remarks: "")
            column(name: "N020", type: "NUMBER(12,5)", remarks: "")
            column(name: "N021", type: "NUMBER(12,5)", remarks: "")
            column(name: "N022", type: "NUMBER(12,5)", remarks: "")
            column(name: "N023", type: "NUMBER(12,5)", remarks: "")
            column(name: "N024", type: "NUMBER(12,5)", remarks: "")
            column(name: "N025", type: "NUMBER(12,5)", remarks: "")
            column(name: "N026", type: "NUMBER(12,5)", remarks: "")
            column(name: "N027", type: "NUMBER(12,5)", remarks: "")
            column(name: "N028", type: "NUMBER(12,5)", remarks: "")
            column(name: "N029", type: "NUMBER(12,5)", remarks: "")
            column(name: "N030", type: "NUMBER(12,5)", remarks: "")
            column(name: "N031", type: "NUMBER(12,5)", remarks: "")
            column(name: "N032", type: "NUMBER(12,5)", remarks: "")
            column(name: "N033", type: "NUMBER(12,5)", remarks: "")
            column(name: "N034", type: "NUMBER(12,5)", remarks: "")
            column(name: "N035", type: "NUMBER(12,5)", remarks: "")
            column(name: "N036", type: "NUMBER(12,5)", remarks: "")
            column(name: "N037", type: "NUMBER(12,5)", remarks: "")
            column(name: "N038", type: "NUMBER(12,5)", remarks: "")
            column(name: "N039", type: "NUMBER(12,5)", remarks: "")
            column(name: "N040", type: "NUMBER(12,5)", remarks: "")
            column(name: "N041", type: "NUMBER(12,5)", remarks: "")
            column(name: "N042", type: "NUMBER(12,5)", remarks: "")
            column(name: "N043", type: "NUMBER(12,5)", remarks: "")
            column(name: "N044", type: "NUMBER(12,5)", remarks: "")
            column(name: "N045", type: "NUMBER(12,5)", remarks: "")
            column(name: "N046", type: "NUMBER(12,5)", remarks: "")
            column(name: "N047", type: "NUMBER(12,5)", remarks: "")
            column(name: "N048", type: "NUMBER(12,5)", remarks: "")
            column(name: "N049", type: "NUMBER(12,5)", remarks: "")
            column(name: "N050", type: "NUMBER(12,5)", remarks: "")
            column(name: "N051", type: "NUMBER(12,5)", remarks: "")
            column(name: "N052", type: "NUMBER(12,5)", remarks: "")
            column(name: "N053", type: "NUMBER(12,5)", remarks: "")
            column(name: "N054", type: "NUMBER(12,5)", remarks: "")
            column(name: "N055", type: "NUMBER(12,5)", remarks: "")
            column(name: "N056", type: "NUMBER(12,5)", remarks: "")
            column(name: "N057", type: "NUMBER(12,5)", remarks: "")
            column(name: "N058", type: "NUMBER(12,5)", remarks: "")
            column(name: "N059", type: "NUMBER(12,5)", remarks: "")
            column(name: "N060", type: "NUMBER(12,5)", remarks: "")
            column(name: "N061", type: "NUMBER(12,5)", remarks: "")
            column(name: "N062", type: "NUMBER(12,5)", remarks: "")
            column(name: "N063", type: "NUMBER(12,5)", remarks: "")
            column(name: "N064", type: "NUMBER(12,5)", remarks: "")
            column(name: "N065", type: "NUMBER(12,5)", remarks: "")
            column(name: "N066", type: "NUMBER(12,5)", remarks: "")
            column(name: "N067", type: "NUMBER(12,5)", remarks: "")
            column(name: "N068", type: "NUMBER(12,5)", remarks: "")
            column(name: "N069", type: "NUMBER(12,5)", remarks: "")
            column(name: "N070", type: "NUMBER(12,5)", remarks: "")
            column(name: "N071", type: "NUMBER(12,5)", remarks: "")
            column(name: "N072", type: "NUMBER(12,5)", remarks: "")
            column(name: "N073", type: "NUMBER(12,5)", remarks: "")
            column(name: "N074", type: "NUMBER(12,5)", remarks: "")
            column(name: "N075", type: "NUMBER(12,5)", remarks: "")
            column(name: "N076", type: "NUMBER(12,5)", remarks: "")
            column(name: "N077", type: "NUMBER(12,5)", remarks: "")
            column(name: "N078", type: "NUMBER(12,5)", remarks: "")
            column(name: "N079", type: "NUMBER(12,5)", remarks: "")
            column(name: "N080", type: "NUMBER(12,5)", remarks: "")
            column(name: "C001", type: "TEXT", remarks: "")
            column(name: "C002", type: "TEXT", remarks: "")
            column(name: "C003", type: "TEXT", remarks: "")
            column(name: "C004", type: "TEXT", remarks: "")
            column(name: "C005", type: "TEXT", remarks: "")
            column(name: "C006", type: "TEXT", remarks: "")
            column(name: "C007", type: "TEXT", remarks: "")
            column(name: "C008", type: "TEXT", remarks: "")
            column(name: "C009", type: "TEXT", remarks: "")
            column(name: "C010", type: "TEXT", remarks: "")
            column(name: "C011", type: "TEXT", remarks: "")
            column(name: "C012", type: "TEXT", remarks: "")
            column(name: "C013", type: "TEXT", remarks: "")
            column(name: "C014", type: "TEXT", remarks: "")
            column(name: "C015", type: "TEXT", remarks: "")
            column(name: "C016", type: "TEXT", remarks: "")
            column(name: "C017", type: "TEXT", remarks: "")
            column(name: "C018", type: "TEXT", remarks: "")
            column(name: "C019", type: "TEXT", remarks: "")
            column(name: "C020", type: "TEXT", remarks: "")
            column(name: "C021", type: "TEXT", remarks: "")
            column(name: "C022", type: "TEXT", remarks: "")
            column(name: "C023", type: "TEXT", remarks: "")
            column(name: "C024", type: "TEXT", remarks: "")
            column(name: "C025", type: "TEXT", remarks: "")
            column(name: "C026", type: "TEXT", remarks: "")
            column(name: "C027", type: "TEXT", remarks: "")
            column(name: "C028", type: "TEXT", remarks: "")
            column(name: "C029", type: "TEXT", remarks: "")
            column(name: "C030", type: "TEXT", remarks: "")
            column(name: "C031", type: "TEXT", remarks: "")
            column(name: "C032", type: "TEXT", remarks: "")
            column(name: "C033", type: "TEXT", remarks: "")
            column(name: "C034", type: "TEXT", remarks: "")
            column(name: "C035", type: "TEXT", remarks: "")
            column(name: "C036", type: "TEXT", remarks: "")
            column(name: "C037", type: "TEXT", remarks: "")
            column(name: "C038", type: "TEXT", remarks: "")
            column(name: "C039", type: "TEXT", remarks: "")
            column(name: "C040", type: "TEXT", remarks: "")
            column(name: "C041", type: "TEXT", remarks: "")
            column(name: "C042", type: "TEXT", remarks: "")
            column(name: "C043", type: "TEXT", remarks: "")
            column(name: "C044", type: "TEXT", remarks: "")
            column(name: "C045", type: "TEXT", remarks: "")
            column(name: "C046", type: "TEXT", remarks: "")
            column(name: "C047", type: "TEXT", remarks: "")
            column(name: "C048", type: "TEXT", remarks: "")
            column(name: "C049", type: "TEXT", remarks: "")
            column(name: "C050", type: "TEXT", remarks: "")
            column(name: "C051", type: "TEXT", remarks: "")
            column(name: "C052", type: "TEXT", remarks: "")
            column(name: "C053", type: "TEXT", remarks: "")
            column(name: "C054", type: "TEXT", remarks: "")
            column(name: "C055", type: "TEXT", remarks: "")
            column(name: "C056", type: "TEXT", remarks: "")
            column(name: "C057", type: "TEXT", remarks: "")
            column(name: "C058", type: "TEXT", remarks: "")
            column(name: "C059", type: "TEXT", remarks: "")
            column(name: "C060", type: "TEXT", remarks: "")
            column(name: "C061", type: "TEXT", remarks: "")
            column(name: "C062", type: "TEXT", remarks: "")
            column(name: "C063", type: "TEXT", remarks: "")
            column(name: "C064", type: "TEXT", remarks: "")
            column(name: "C065", type: "TEXT", remarks: "")
            column(name: "C066", type: "TEXT", remarks: "")
            column(name: "C067", type: "TEXT", remarks: "")
            column(name: "C068", type: "TEXT", remarks: "")
            column(name: "C069", type: "TEXT", remarks: "")
            column(name: "C070", type: "TEXT", remarks: "")
            column(name: "C071", type: "TEXT", remarks: "")
            column(name: "C072", type: "TEXT", remarks: "")
            column(name: "C073", type: "TEXT", remarks: "")
            column(name: "C074", type: "TEXT", remarks: "")
            column(name: "C075", type: "TEXT", remarks: "")
            column(name: "C076", type: "TEXT", remarks: "")
            column(name: "C077", type: "TEXT", remarks: "")
            column(name: "C078", type: "TEXT", remarks: "")
            column(name: "C079", type: "TEXT", remarks: "")
            column(name: "C080", type: "TEXT", remarks: "")
            column(name: "C081", type: "TEXT", remarks: "")
            column(name: "C082", type: "TEXT", remarks: "")
            column(name: "C083", type: "TEXT", remarks: "")
            column(name: "C084", type: "TEXT", remarks: "")
            column(name: "C085", type: "TEXT", remarks: "")
            column(name: "C086", type: "TEXT", remarks: "")
            column(name: "C087", type: "TEXT", remarks: "")
            column(name: "C088", type: "TEXT", remarks: "")
            column(name: "C089", type: "TEXT", remarks: "")
            column(name: "C090", type: "TEXT", remarks: "")
            column(name: "C091", type: "TEXT", remarks: "")
            column(name: "C092", type: "TEXT", remarks: "")
            column(name: "C093", type: "TEXT", remarks: "")
            column(name: "C094", type: "TEXT", remarks: "")
            column(name: "C095", type: "TEXT", remarks: "")
            column(name: "C096", type: "TEXT", remarks: "")
            column(name: "C097", type: "TEXT", remarks: "")
            column(name: "C098", type: "TEXT", remarks: "")
            column(name: "C099", type: "TEXT", remarks: "")
            column(name: "C100", type: "TEXT", remarks: "")
            column(name: "D001", type: "DATETIME", remarks: "")
            column(name: "D002", type: "DATETIME", remarks: "")
            column(name: "D003", type: "DATETIME", remarks: "")
            column(name: "D004", type: "DATETIME", remarks: "")
            column(name: "D005", type: "DATETIME", remarks: "")
            column(name: "D006", type: "DATETIME", remarks: "")
            column(name: "D007", type: "DATETIME", remarks: "")
            column(name: "D008", type: "DATETIME", remarks: "")
            column(name: "D009", type: "DATETIME", remarks: "")
            column(name: "D010", type: "DATETIME", remarks: "")
            column(name: "D011", type: "DATETIME", remarks: "")
            column(name: "D012", type: "DATETIME", remarks: "")
            column(name: "D013", type: "DATETIME", remarks: "")
            column(name: "D014", type: "DATETIME", remarks: "")
            column(name: "D015", type: "DATETIME", remarks: "")
            column(name: "D016", type: "DATETIME", remarks: "")
            column(name: "D017", type: "DATETIME", remarks: "")
            column(name: "D018", type: "DATETIME", remarks: "")
            column(name: "D019", type: "DATETIME", remarks: "")
            column(name: "D020", type: "DATETIME", remarks: "")
            column(name: "D021", type: "DATETIME", remarks: "")
            column(name: "D022", type: "DATETIME", remarks: "")
            column(name: "D023", type: "DATETIME", remarks: "")
            column(name: "D024", type: "DATETIME", remarks: "")
            column(name: "D025", type: "DATETIME", remarks: "")
            column(name: "D026", type: "DATETIME", remarks: "")
            column(name: "D027", type: "DATETIME", remarks: "")
            column(name: "D028", type: "DATETIME", remarks: "")
            column(name: "D029", type: "DATETIME", remarks: "")
            column(name: "D030", type: "DATETIME", remarks: "")
            column(name: "D031", type: "DATETIME", remarks: "")
            column(name: "D032", type: "DATETIME", remarks: "")
            column(name: "D033", type: "DATETIME", remarks: "")
            column(name: "D034", type: "DATETIME", remarks: "")
            column(name: "D035", type: "DATETIME", remarks: "")
            column(name: "D036", type: "DATETIME", remarks: "")
            column(name: "D037", type: "DATETIME", remarks: "")
            column(name: "D038", type: "DATETIME", remarks: "")
            column(name: "D039", type: "DATETIME", remarks: "")
            column(name: "D040", type: "DATETIME", remarks: "")
            column(name: "D041", type: "DATETIME", remarks: "")
            column(name: "D042", type: "DATETIME", remarks: "")
            column(name: "D043", type: "DATETIME", remarks: "")
            column(name: "D044", type: "DATETIME", remarks: "")
            column(name: "D045", type: "DATETIME", remarks: "")
            column(name: "D046", type: "DATETIME", remarks: "")
            column(name: "D047", type: "DATETIME", remarks: "")
            column(name: "D048", type: "DATETIME", remarks: "")
            column(name: "D049", type: "DATETIME", remarks: "")
            column(name: "D050", type: "DATETIME", remarks: "")
            column(name: "CLOB001", type: "CLOB", remarks: "")
            column(name: "CLOB002", type: "CLOB", remarks: "")
            column(name: "CLOB003", type: "CLOB", remarks: "")
            column(name: "CLOB004", type: "CLOB", remarks: "")
            column(name: "CLOB005", type: "CLOB", remarks: "")
            column(name: "CLOB006", type: "CLOB", remarks: "")
            column(name: "CLOB007", type: "CLOB", remarks: "")
            column(name: "CLOB008", type: "CLOB", remarks: "")
            column(name: "CLOB009", type: "CLOB", remarks: "")
            column(name: "CLOB010", type: "CLOB", remarks: "")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")

            column(name: "DOC_HEADER_ID", type: "BIGINT", remarks: "")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-20-BPM_DYNAMIC_TABLE_COLUMN") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_DYNAMIC_TABLE_COLUMN_S', startValue: "10001")
        }

        createTable(tableName: "BPM_DYNAMIC_TABLE_COLUMN", remarks: "") {

            column(name: "COLUMN_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_DYNAMIC_TABLE_COLUMN_PK")
            }
            column(name: "TABLE_NAME", type: "VARCHAR(30)", remarks: "") { constraints(nullable: "false") }
            column(name: "COLUMN_NAME", type: "VARCHAR(30)", remarks: "") { constraints(nullable: "false") }
            column(name: "DATA_TYPE", type: "VARCHAR(240)", remarks: "")
            column(name: "DATA_LENGTH", type: "BIGINT", remarks: "")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-20-BPM_JS_TEMPLATE") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_JS_TEMPLATE_S', startValue: "10001")
        }

        createTable(tableName: "BPM_JS_TEMPLATE", remarks: "") {

            column(name: "JS_TEMPLATE_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_JS_TEMPLATE_PK")
            }
            column(name: "JS_TEMPLATE_CODE", type: "VARCHAR(30)", remarks: "") { constraints(nullable: "false") }
            column(name: "JS_TEMPLATE_DESC", type: "VARCHAR(240)", remarks: "") { constraints(nullable: "false") }
            column(name: "JS_CONTENT", type: "CLOB", remarks: "")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "JS_TEMPLATE_CODE", tableName: "BPM_JS_TEMPLATE", constraintName: "BPM_JS_TEMPLATE_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-20-BPM_PAGE") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_PAGE_S', startValue: "10001")
        }

        createTable(tableName: "BPM_PAGE", remarks: "") {

            column(name: "PAGE_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_PAGE_PK")
            }
            column(name: "PAGE_TYPE", type: "VARCHAR(30)", remarks: "") { constraints(nullable: "false") }
            column(name: "PAGE_CODE", type: "VARCHAR(240)", remarks: "") { constraints(nullable: "false") }
            column(name: "PAGE_DESC", type: "VARCHAR(240)", remarks: "")
            column(name: "GROUP_ID", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "PAGE_CODE", tableName: "BPM_PAGE", constraintName: "BPM_PAGE_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-20-BPM_PAGE_BUTTON") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_PAGE_BUTTON_S', startValue: "10001")
        }

        createTable(tableName: "BPM_PAGE_BUTTON", remarks: "") {

            column(name: "BUTTON_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_PAGE_BUTTON_PK")
            }
            column(name: "PAGE_ID", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "BUTTON_SEQUENCE", type: "BIGINT", remarks: "")
            column(name: "BUTTON_CODE", type: "VARCHAR(30)", remarks: "") { constraints(nullable: "false") }
            column(name: "BUTTON_DESC", type: "VARCHAR(240)", remarks: "")
            column(name: "TEMPLATE_FLAG", type: "VARCHAR(1)", remarks: "")
            column(name: "ID", type: "VARCHAR(240)", remarks: "")
            column(name: "text", type: "VARCHAR(240)", remarks: "")
            column(name: "CLICK", type: "CLOB", remarks: "")
            column(name: "BUTTON_TYPE", type: "CLOB", remarks: "")
            column(name: "WIDTH", type: "BIGINT", remarks: "")
            column(name: "HEIGHT", type: "BIGINT", remarks: "")
            column(name: "ICON", type: "BIGINT", remarks: "")
            column(name: "DISABLED", type: "VARCHAR(30)", remarks: "")
            column(name: "HIDDEN", type: "VARCHAR(30)", remarks: "")
            column(name: "style", type: "CLOB", remarks: "")
            column(name: "BTNSTYLE", type: "CLOB", remarks: "")
            column(name: "OPERATION_TYPE", type: "VARCHAR(30)", remarks: "")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "PAGE_ID,BUTTON_CODE", tableName: "BPM_PAGE_BUTTON", constraintName: "BPM_PAGE_BUTTON_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-20-BPM_PAGE_DATASET") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_PAGE_DATASET_S', startValue: "10001")
        }

        createTable(tableName: "BPM_PAGE_DATASET", remarks: "") {

            column(name: "DATASET_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_PAGE_DATASET_PK")
            }
            column(name: "PAGE_ID", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "DATASET_USAGE", type: "VARCHAR(240)", remarks: "")
            column(name: "DATASOURCE_TYPE", type: "VARCHAR(30)", remarks: "")
            column(name: "MODEL", type: "VARCHAR(240)", remarks: "")
            column(name: "BASE_URL", type: "VARCHAR(240)", remarks: "")
            column(name: "AUTO_QUERY", type: "VARCHAR(30)", remarks: "")
            column(name: "QUERY_URL", type: "VARCHAR(240)", remarks: "")
            column(name: "AUTO_CREATE", type: "VARCHAR(30)", remarks: "")
            column(name: "SUBMIT_URL", type: "VARCHAR(240)", remarks: "")
            column(name: "BIND_NAME", type: "VARCHAR(30)", remarks: "")
            column(name: "BIND_TARGET", type: "VARCHAR(240)", remarks: "")
            column(name: "SELECTABLE", type: "VARCHAR(30)", remarks: "")
            column(name: "SELECTION_MODEL", type: "VARCHAR(30)", remarks: "")
            column(name: "LOAD_DATA", type: "VARCHAR(30)", remarks: "")
            column(name: "LOOKUP_CODE", type: "VARCHAR(30)", remarks: "")
            column(name: "QUERY_DATASET", type: "VARCHAR(240)", remarks: "")
            column(name: "FETCHALL", type: "VARCHAR(30)", remarks: "")
            column(name: "PAGE_SIZE", type: "BIGINT", remarks: "")
            column(name: "AUTO_COUNT", type: "VARCHAR(30)", remarks: "")
            column(name: "AUTO_PAGESIZE", type: "VARCHAR(30)", remarks: "")
            column(name: "MAX_PAGESIZE", type: "BIGINT", remarks: "")
            column(name: "MODIFIED_CHECK", type: "VARCHAR(30)", remarks: "")
            column(name: "NOTIFICATION", type: "VARCHAR(240)", remarks: "")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BPM_PAGE_DATASET", indexName: "BPM_PAGE_DATASET_N1") {
            column(name: "PAGE_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-20-BPM_PAGE_GROUP") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_PAGE_GROUP_S', startValue: "10001")
        }

        createTable(tableName: "BPM_PAGE_GROUP", remarks: "") {

            column(name: "GROUP_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_PAGE_GROUP_PK")
            }
            column(name: "GROUP_TYPE", type: "VARCHAR(30)", remarks: "") { constraints(nullable: "false") }
            column(name: "GROUP_CODE", type: "VARCHAR(30)", remarks: "") { constraints(nullable: "false") }
            column(name: "TEMPLATE_ID", type: "BIGINT", remarks: "")
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")

            column(name: "OA_FLOW_CATEGORY", type: "VARCHAR(30)", remarks: "")
            column(name: "OA_FLOW_SUB_CATEGORY", type: "VARCHAR(30)", remarks: "")
            column(name: "INTERFACE_FLAG", type: "VARCHAR(1)", remarks: "")
            column(name: "AUTO_APPROVE_FLAG", type: "VARCHAR(1)", remarks: "")
            column(name: "GROUP_DESC", type: "VARCHAR(240)", remarks: "")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "GROUP_CODE", tableName: "BPM_PAGE_GROUP", constraintName: "BPM_PAGE_GROUP_U1")
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-20-BPM_PAGE_GROUP_ENTITY") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_PAGE_GROUP_ENTITY_S', startValue: "10001")
        }

        createTable(tableName: "BPM_PAGE_GROUP_ENTITY", remarks: "") {

            column(name: "ENTITY_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "实体ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_PAGE_GROUP_FIELD_PK")
            }
            column(name: "GROUP_ID", type: "BIGINT", remarks: "页面分组ID") { constraints(nullable: "false") }
            column(name: "ORDER_NUM", type: "BIGINT", remarks: "排序") { constraints(nullable: "false") }
            column(name: "PARENT_ENTITY_ID", type: "BIGINT", remarks: "父级实体ID")
            column(name: "ENTITY_LEVEL", type: "INT", remarks: "实体层级")
            column(name: "ENTITY_CODE", type: "VARCHAR(30)", remarks: "实体代码") { constraints(nullable: "false") }
            column(name: "ENTITY_NAME", type: "VARCHAR(30)", remarks: "实体名称") { constraints(nullable: "false") }
            column(name: "ENTITY_LEVEL", type: "BIGINT", remarks: "实体级别")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "GROUP_ID,ENTITY_CODE", tableName: "BPM_PAGE_GROUP_ENTITY", constraintName: "BPM_PAGE_GROUP_ENTITY_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-20-BPM_PAGE_GROUP_FIELD") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_PAGE_GROUP_FIELD_S', startValue: "10001")
        }

        createTable(tableName: "BPM_PAGE_GROUP_FIELD", remarks: "") {

            column(name: "FIELD_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_PAGE_GROUP_FIELD_PK")
            }
            column(name: "ENTITY_ID", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "ORDER_NUM", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "BUSINESS_CATEGORY", type: "VARCHAR(30)", remarks: "")
            column(name: "TABLE_NAME", type: "VARCHAR(30)", remarks: "") { constraints(nullable: "false") }
            column(name: "FIELD_NAME", type: "VARCHAR(30)", remarks: "") { constraints(nullable: "false") }
            column(name: "DATA_TYPE", type: "VARCHAR(30)", remarks: "") { constraints(nullable: "false") }
            column(name: "FIELD_DESC", type: "VARCHAR(30)", remarks: "") { constraints(nullable: "false") }
            column(name: "LOGIC_TYPE", type: "VARCHAR(30)", remarks: "")
            column(name: "LOGIC_BELONG_FIELD_ID", type: "BIGINT", remarks: "")
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")

            column(name: "QUERY_FLAG", type: "VARCHAR(1)", remarks: "")
            column(name: "QUERY_TYPE", type: "VARCHAR(30)", remarks: "")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "TABLE_NAME,FIELD_NAME,ENTITY_ID", tableName: "BPM_PAGE_GROUP_FIELD", constraintName: "BPM_PAGE_GROUP_FIELD_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-20-BPM_PAGE_GROUP_TABLE") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_PAGE_GROUP_TABLE_S', startValue: "10001")
        }

        createTable(tableName: "BPM_PAGE_GROUP_TABLE", remarks: "") {

            column(name: "TABLE_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_PAGE_GROUP_TABLE_PK")
            }
            column(name: "GROUP_ID", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "TABLE_NAME", type: "VARCHAR(240)", remarks: "") { constraints(nullable: "false") }
            column(name: "TABLE_DESC", type: "VARCHAR(240)", remarks: "")
            column(name: "BUSINESS_CATEGORY", type: "VARCHAR(30)", remarks: "")
            column(name: "REF_TABLE_ID", type: "BIGINT", remarks: "")
            column(name: "REF_TYPE", type: "VARCHAR(30)", remarks: "")
            column(name: "SELF_REF_CONDITION1", type: "VARCHAR(240)", remarks: "")
            column(name: "TARGET_REF_CONDITION1", type: "VARCHAR(240)", remarks: "")
            column(name: "SELF_REF_CONDITION2", type: "VARCHAR(240)", remarks: "")
            column(name: "TARGET_REF_CONDITION2", type: "VARCHAR(240)", remarks: "")
            column(name: "SELF_REF_CONDITION3", type: "VARCHAR(240)", remarks: "")
            column(name: "TARGET_REF_CONDITION3", type: "VARCHAR(240)", remarks: "")
            column(name: "SELF_REF_CONDITION4", type: "VARCHAR(240)", remarks: "")
            column(name: "TARGET_REF_CONDITION4", type: "VARCHAR(240)", remarks: "")
            column(name: "SELF_REF_CONDITION5", type: "VARCHAR(240)", remarks: "")
            column(name: "TARGET_REF_CONDITION5", type: "VARCHAR(240)", remarks: "")
            column(name: "SELF_REF_CONDITION6", type: "VARCHAR(240)", remarks: "")
            column(name: "TARGET_REF_CONDITION6", type: "VARCHAR(240)", remarks: "")
            column(name: "SELF_REF_CONDITION7", type: "VARCHAR(240)", remarks: "")
            column(name: "TARGET_REF_CONDITION7", type: "VARCHAR(240)", remarks: "")
            column(name: "SELF_REF_CONDITION8", type: "VARCHAR(240)", remarks: "")
            column(name: "TARGET_REF_CONDITION8", type: "VARCHAR(240)", remarks: "")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "GROUP_ID,TABLE_NAME", tableName: "BPM_PAGE_GROUP_TABLE", constraintName: "BPM_PAGE_GROUP_TABLE_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-20-BPM_PAGE_LAYOUT_BASIC") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_PAGE_LAYOUT_BASIC_S', startValue: "10001")
        }

        createTable(tableName: "BPM_PAGE_LAYOUT_BASIC", remarks: "") {

            column(name: "LAYOUT_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_PAGE_LAYOUT_BASIC_PK")
            }
            column(name: "PAGE_ID", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAYOUT_SEQUENCE", type: "BIGINT", remarks: "")
            column(name: "LAYOUT_CODE", type: "VARCHAR(30)", remarks: "")
            column(name: "LAYOUT_DESC", type: "VARCHAR(240)", remarks: "")
            column(name: "LAYOUT_TYPE", type: "VARCHAR(30)", remarks: "")
            column(name: "ENTITY_ID", type: "BIGINT", remarks: "实体ID")
            column(name: "PARENT_LAYOUT_ID", type: "BIGINT", remarks: "")
            column(name: "LAYOUT_LEVEL", type: "BIGINT", remarks: "")
            column(name: "TEMPLATE_FLAG", type: "VARCHAR(1)", remarks: "")
            column(name: "ID", type: "VARCHAR(240)", remarks: "")
            column(name: "DATASET", type: "VARCHAR(240)", remarks: "")
            column(name: "TAB_GROUP_NUMBER", type: "BIGINT", remarks: "")
            column(name: "WIDTH", type: "BIGINT", remarks: "")
            column(name: "HEIGHT", type: "BIGINT", remarks: "")
            column(name: "MARGINWIDTH", type: "BIGINT", remarks: "")
            column(name: "MARGINHEIGHT", type: "BIGINT", remarks: "")
            column(name: "style", type: "CLOB", remarks: "")
            column(name: "HIDDEN", type: "VARCHAR(30)", remarks: "")
            column(name: "BUSINESS_CATEGORY", type: "VARCHAR(30)", remarks: "")
            column(name: "PARENT_BUSINESS_CATEGORY", type: "VARCHAR(30)", remarks: "")
            column(name: "REF_TABLE", type: "VARCHAR(240)", remarks: "")
            column(name: "REF_FIELD", type: "VARCHAR(240)", remarks: "")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")

            column(name: "WRITE_BACK_PROGRAM", type: "VARCHAR(240)", remarks: "")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "LAYOUT_CODE,PAGE_ID", tableName: "BPM_PAGE_LAYOUT_BASIC", constraintName: "BPM_PAGE_LAYOUT_BASIC_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-20-BPM_PAGE_LAYOUT_BUTTON") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_PAGE_LAYOUT_BUTTON_S', startValue: "10001")
        }

        createTable(tableName: "BPM_PAGE_LAYOUT_BUTTON", remarks: "") {

            column(name: "BUTTON_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_PAGE_LAYOUT_BUTTON_PK")
            }
            column(name: "LAYOUT_ID", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "BUTTON_SEQUENCE", type: "BIGINT", remarks: "")
            column(name: "BUTTON_CODE", type: "VARCHAR(30)", remarks: "") { constraints(nullable: "false") }
            column(name: "BUTTON_DESC", type: "VARCHAR(240)", remarks: "")
            column(name: "TEMPLATE_FLAG", type: "VARCHAR(1)", remarks: "")
            column(name: "ID", type: "VARCHAR(240)", remarks: "")
            column(name: "text", type: "VARCHAR(240)", remarks: "")
            column(name: "CLICK", type: "CLOB", remarks: "")
            column(name: "BUTTON_TYPE", type: "CLOB", remarks: "")
            column(name: "WIDTH", type: "BIGINT", remarks: "")
            column(name: "HEIGHT", type: "BIGINT", remarks: "")
            column(name: "ICON", type: "BIGINT", remarks: "")
            column(name: "DISABLED", type: "VARCHAR(30)", remarks: "")
            column(name: "HIDDEN", type: "VARCHAR(30)", remarks: "")
            column(name: "style", type: "CLOB", remarks: "")
            column(name: "BTNSTYLE", type: "CLOB", remarks: "")
            column(name: "OPERATION_TYPE", type: "VARCHAR(30)", remarks: "")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "BUTTON_CODE,LAYOUT_ID", tableName: "BPM_PAGE_LAYOUT_BUTTON", constraintName: "BPM_PAGE_LAYOUT_BUTTON_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-20-BPM_PAGE_LAYOUT_EVENT") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_PAGE_LAYOUT_EVENT_S', startValue: "10001")
        }

        createTable(tableName: "BPM_PAGE_LAYOUT_EVENT", remarks: "") {

            column(name: "EVENT_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_PAGE_LAYOUT_EVENT_PK")
            }
            column(name: "LAYOUT_ID", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "EVENT_TARGET", type: "VARCHAR(30)", remarks: "")
            column(name: "EVENT_TYPE", type: "VARCHAR(30)", remarks: "")
            column(name: "EVENT_HANDLER", type: "CLOB", remarks: "")
            column(name: "GUIDE_ID", type: "BIGINT", remarks: "")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BPM_PAGE_LAYOUT_EVENT", indexName: "BPM_PAGE_LAYOUT_EVENT_N1") {
            column(name: "LAYOUT_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-20-BPM_PAGE_LAYOUT_FORM") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_PAGE_LAYOUT_FORM_S', startValue: "10001")
        }

        createTable(tableName: "BPM_PAGE_LAYOUT_FORM", remarks: "") {

            column(name: "LAYOUT_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_PAGE_LAYOUT_FORM_PK")
            }
            column(name: "PROMPT", type: "VARCHAR(240)", remarks: "")
            column(name: "TITLE", type: "VARCHAR(240)", remarks: "")
            column(name: "COLUMN_NUM", type: "BIGINT", remarks: "")
            column(name: "ROW_NUM", type: "BIGINT", remarks: "")
            column(name: "LABELWIDTH", type: "BIGINT", remarks: "")
            column(name: "LABELSEPARATOR", type: "VARCHAR(30)", remarks: "")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-20-BPM_PAGE_LAYOUT_GRID") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_PAGE_LAYOUT_GRID_S', startValue: "10001")
        }

        createTable(tableName: "BPM_PAGE_LAYOUT_GRID", remarks: "") {

            column(name: "LAYOUT_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_PAGE_LAYOUT_GRID_PK")
            }
            column(name: "NAVBAR", type: "VARCHAR(30)", remarks: "")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-20-BPM_PAGE_LAYOUT_TAB") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_PAGE_LAYOUT_TAB_S', startValue: "10001")
        }

        createTable(tableName: "BPM_PAGE_LAYOUT_TAB", remarks: "") {

            column(name: "LAYOUT_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_PAGE_LAYOUT_TAB_PK")
            }
            column(name: "SELECTED", type: "VARCHAR(30)", remarks: "")
            column(name: "CLOSEABLE", type: "VARCHAR(30)", remarks: "")
            column(name: "DISABLED", type: "VARCHAR(30)", remarks: "")
            column(name: "ref", type: "VARCHAR(240)", remarks: "")
            column(name: "TABSTYLE", type: "CLOB", remarks: "")
            column(name: "BODYSTYLE", type: "CLOB", remarks: "")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-20-BPM_PAGE_TAG_BASIC") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_PAGE_TAG_BASIC_S', startValue: "10001")
        }

        createTable(tableName: "BPM_PAGE_TAG_BASIC", remarks: "") {

            column(name: "TAG_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_PAGE_TAG_BASIC_PK")
            }
            column(name: "LAYOUT_ID", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "TAG_SEQUENCE", type: "BIGINT", remarks: "")
            column(name: "TAG_CODE", type: "VARCHAR(30)", remarks: "") { constraints(nullable: "false") }
            column(name: "TAG_DESC", type: "VARCHAR(240)", remarks: "")
            column(name: "TAG_TYPE", type: "VARCHAR(30)", remarks: "")
            column(name: "TEMPLATE_FLAG", type: "VARCHAR(1)", remarks: "")
            column(name: "FIELD_ID", type: "BIGINT", remarks: "")
            column(name: "ID", type: "VARCHAR(240)", remarks: "")
            column(name: "NAME", type: "VARCHAR(240)", remarks: "")
            column(name: "DEFAULTVALUE", type: "VARCHAR(240)", remarks: "")
            column(name: "READONLY", type: "VARCHAR(30)", remarks: "")
            column(name: "REQUIRED", type: "VARCHAR(30)", remarks: "")
            column(name: "PROMPT", type: "VARCHAR(240)", remarks: "")
            column(name: "validator", type: "CLOB", remarks: "")
            column(name: "REQUIREDMESSAGE", type: "CLOB", remarks: "")
            column(name: "BINDTARGET", type: "VARCHAR(240)", remarks: "")
            column(name: "WIDTH", type: "BIGINT", remarks: "")
            column(name: "HEIGHT", type: "BIGINT", remarks: "")
            column(name: "style", type: "CLOB", remarks: "")
            column(name: "COLSPAN", type: "BIGINT", remarks: "")
            column(name: "ROWSPAN", type: "BIGINT", remarks: "")
            column(name: "HIDDEN", type: "VARCHAR(30)", remarks: "")
            column(name: "PARENT_TAG_ID", type: "BIGINT", remarks: "")
            column(name: "ALIGN", type: "VARCHAR(30)", remarks: "")
            column(name: "EDITOR", type: "VARCHAR(240)", remarks: "")
            column(name: "EDITORFUNCTION", type: "CLOB", remarks: "")
            column(name: "FOOTERRENDERER", type: "CLOB", remarks: "")
            column(name: "LOCK_FLAG", type: "VARCHAR(30)", remarks: "")
            column(name: "RENDERER", type: "CLOB", remarks: "")
            column(name: "RESIZABLE", type: "VARCHAR(30)", remarks: "")
            column(name: "SORTABLE", type: "VARCHAR(30)", remarks: "")
            column(name: "FOOTERRENDERER_JS", type: "CLOB", remarks: "")
            column(name: "PRINTABLE", type: "VARCHAR(30)", remarks: "")
            column(name: "FIELD_SOURCE", type: "VARCHAR(30)", remarks: "")
            column(name: "APP_HIDE_FLAG", type: "VARCHAR(1)", remarks: "")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "TAG_CODE,LAYOUT_ID", tableName: "BPM_PAGE_TAG_BASIC", constraintName: "BPM_PAGE_TAG_BASIC_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-20-BPM_PAGE_TAG_CHECKBOX") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_PAGE_TAG_CHECKBOX_S', startValue: "10001")
        }

        createTable(tableName: "BPM_PAGE_TAG_CHECKBOX", remarks: "") {

            column(name: "TAG_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_PAGE_TAG_CHECKBOX_PK")
            }
            column(name: "CHECKEDVALUE", type: "VARCHAR(30)", remarks: "")
            column(name: "UNCHECKEDVALUE", type: "VARCHAR(30)", remarks: "")
            column(name: "label", type: "VARCHAR(240)", remarks: "")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-20-BPM_PAGE_TAG_COMBOBOX") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_PAGE_TAG_COMBOBOX_S', startValue: "10001")
        }

        createTable(tableName: "BPM_PAGE_TAG_COMBOBOX", remarks: "") {

            column(name: "TAG_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_PAGE_TAG_COMBOBOX_PK")
            }
            column(name: "options", type: "VARCHAR(240)", remarks: "")
            column(name: "VALUEFIELD", type: "VARCHAR(240)", remarks: "")
            column(name: "RETURNFIELD", type: "VARCHAR(240)", remarks: "")
            column(name: "DISPLAYFIELD", type: "VARCHAR(240)", remarks: "")
            column(name: "SYSCODE", type: "VARCHAR(240)", remarks: "")
            column(name: "SQLTEXT", type: "CLOB", remarks: "")
            column(name: "DATASOURCE", type: "VARCHAR(240)", remarks: "")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")

            column(name: "LOVCODE", type: "VARCHAR(240)", remarks: "")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-20-BPM_PAGE_TAG_COMBOBOX_FIELD") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_PAGE_TAG_COMBOBOX_FIELD_S', startValue: "10001")
        }

        createTable(tableName: "BPM_PAGE_TAG_COMBOBOX_FIELD", remarks: "") {

            column(name: "COMBOBOX_FIELD_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_PAGE_TAG_COMBOBOX_FIELD_PK")
            }
            column(name: "TAG_ID", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "COMBOBOX_FIELD", type: "VARCHAR(240)", remarks: "")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BPM_PAGE_TAG_COMBOBOX_FIELD", indexName: "BPM_PAGE_TAG_COMBOBOX_FIELD_N1") {
            column(name: "TAG_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-20-BPM_PAGE_TAG_COMBOBOX_MAP") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_PAGE_TAG_COMBOBOX_MAP_S', startValue: "10001")
        }

        createTable(tableName: "BPM_PAGE_TAG_COMBOBOX_MAP", remarks: "") {

            column(name: "MAP_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_PAGE_TAG_COMBOBOX_MAP_PK")
            }
            column(name: "TAG_ID", type: "BIGINT", remarks: "")
            column(name: "MAP_SEQUENCE", type: "BIGINT", remarks: "")
            column(name: "FROM_FIELD", type: "VARCHAR(240)", remarks: "")
            column(name: "TO_FIELD", type: "VARCHAR(240)", remarks: "")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BPM_PAGE_TAG_COMBOBOX_MAP", indexName: "BPM_PAGE_TAG_COMBOBOX_MAP_N1") {
            column(name: "TAG_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-20-BPM_PAGE_TAG_COMBOBOX_OPT") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_PAGE_TAG_COMBOBOX_OPT_S', startValue: "10001")
        }

        createTable(tableName: "BPM_PAGE_TAG_COMBOBOX_OPT", remarks: "") {

            column(name: "OPTION_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_PAGE_TAG_COMBOBOX_OPT_PK")
            }
            column(name: "TAG_ID", type: "BIGINT", remarks: "")
            column(name: "OPTION_SEQUENCE", type: "BIGINT", remarks: "")
            column(name: "label", type: "VARCHAR(240)", remarks: "")
            column(name: "LABEL_US", type: "VARCHAR(240)", remarks: "") { constraints(nullable: "false") }
            column(name: "VALUE", type: "VARCHAR(240)", remarks: "")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BPM_PAGE_TAG_COMBOBOX_OPT", indexName: "BPM_PAGE_TAG_COMBOBOX_OPT_N1") {
            column(name: "TAG_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-20-BPM_PAGE_TAG_DATA_GUIDE") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_PAGE_TAG_DATA_GUIDE_S', startValue: "10001")
        }

        createTable(tableName: "BPM_PAGE_TAG_DATA_GUIDE", remarks: "") {

            column(name: "GUIDE_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_PAGE_TAG_DATA_GUIDE_PK")
            }
            column(name: "TAG_ID", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "UPDATE_EVENT_ID", type: "BIGINT", remarks: "")
            column(name: "TARGET_TAG_ID", type: "BIGINT", remarks: "")
            column(name: "TARGET_LAYOUT_ID", type: "BIGINT", remarks: "")
            column(name: "GUIDE_SEQUENCE", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "CURRENT_LOGIC_TYPE", type: "VARCHAR(30)", remarks: "")
            column(name: "TRIGGER_CONDITION", type: "VARCHAR(30)", remarks: "")
            column(name: "CONDITION_VALUE", type: "VARCHAR(240)", remarks: "")
            column(name: "READONLY_FLAG", type: "VARCHAR(30)", remarks: "")
            column(name: "REQUIRED_FLAG", type: "VARCHAR(30)", remarks: "")
            column(name: "HIDDEN_FLAG", type: "VARCHAR(30)", remarks: "")
            column(name: "CLEAR_FLAG", type: "VARCHAR(30)", remarks: "")
            column(name: "TARGET_VALUE", type: "CLOB", remarks: "")
            column(name: "FILTER_NAME", type: "VARCHAR(240)", remarks: "")
            column(name: "TRIGGER_TYPE", type: "VARCHAR(30)", remarks: "")
            column(name: "LOAD_EVENT_ID", type: "BIGINT", remarks: "")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")

            column(name: "FORMULA", type: "CLOB", remarks: "")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BPM_PAGE_TAG_DATA_GUIDE", indexName: "BPM_PAGE_TAG_DATA_GUIDE_N1") {
            column(name: "TAG_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-20-BPM_PAGE_TAG_DATEPICKER") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_PAGE_TAG_DATEPICKER_S', startValue: "10001")
        }

        createTable(tableName: "BPM_PAGE_TAG_DATEPICKER", remarks: "") {

            column(name: "TAG_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_PAGE_TAG_DATEPICKER_PK")
            }
            column(name: "DAYRENDERER", type: "CLOB", remarks: "")
            column(name: "ENABLEBESIDEDAYS", type: "VARCHAR(30)", remarks: "")
            column(name: "ENABLEMONTHBTN", type: "VARCHAR(30)", remarks: "")
            column(name: "VIEWSIZE", type: "BIGINT", remarks: "")
            column(name: "RENDERER", type: "CLOB", remarks: "")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-20-BPM_PAGE_TAG_DATETIMEPICKER") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_PAGE_TAG_DATETIMEPICKER_S', startValue: "10001")
        }

        createTable(tableName: "BPM_PAGE_TAG_DATETIMEPICKER", remarks: "") {

            column(name: "TAG_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_PAGE_TAG_DATETIMEPICKER_PK")
            }
            column(name: "DAYRENDERER", type: "CLOB", remarks: "")
            column(name: "ENABLEBESIDEDAYS", type: "VARCHAR(30)", remarks: "")
            column(name: "ENABLEMONTHBTN", type: "VARCHAR(30)", remarks: "")
            column(name: "VIEWSIZE", type: "BIGINT", remarks: "")
            column(name: "RENDERER", type: "CLOB", remarks: "")
            column(name: "hour", type: "BIGINT", remarks: "")
            column(name: "minute", type: "BIGINT", remarks: "")
            column(name: "second", type: "BIGINT", remarks: "")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-20-BPM_PAGE_TAG_EVENT") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_PAGE_TAG_EVENT_S', startValue: "10001")
        }

        createTable(tableName: "BPM_PAGE_TAG_EVENT", remarks: "") {

            column(name: "EVENT_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_PAGE_TAG_EVENT_PK")
            }
            column(name: "TAG_ID", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "EVENT_TARGET", type: "VARCHAR(240)", remarks: "") { constraints(nullable: "false") }
            column(name: "EVENT_TYPE", type: "VARCHAR(30)", remarks: "") { constraints(nullable: "false") }
            column(name: "EVENT_HANDLER", type: "CLOB", remarks: "")
            column(name: "GUIDE_ID", type: "BIGINT", remarks: "")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BPM_PAGE_TAG_EVENT", indexName: "BPM_PAGE_TAG_EVENT_N1") {
            column(name: "TAG_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-20-BPM_PAGE_TAG_LABEL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_PAGE_TAG_LABEL_S', startValue: "10001")
        }

        createTable(tableName: "BPM_PAGE_TAG_LABEL", remarks: "") {

            column(name: "TAG_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_PAGE_TAG_LABEL_PK")
            }
            column(name: "RENDERER", type: "CLOB", remarks: "")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-20-BPM_PAGE_TAG_LOV") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_PAGE_TAG_LOV_S', startValue: "10001")
        }

        createTable(tableName: "BPM_PAGE_TAG_LOV", remarks: "") {

            column(name: "TAG_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_PAGE_TAG_LOV_PK")
            }
            column(name: "LOVCODE", type: "VARCHAR(30)", remarks: "")
            column(name: "LOVURL", type: "CLOB", remarks: "")
            column(name: "LOVAUTOQUERY", type: "VARCHAR(30)", remarks: "")
            column(name: "LOVGRIDHEIGHT", type: "BIGINT", remarks: "")
            column(name: "LOVHEIGHT", type: "BIGINT", remarks: "")
            column(name: "LOVWIDTH", type: "BIGINT", remarks: "")
            column(name: "LOVLABELWIDTH", type: "BIGINT", remarks: "")
            column(name: "AUTOCOMPLETE", type: "VARCHAR(30)", remarks: "")
            column(name: "AUTOCOMPLETEFIELD", type: "VARCHAR(240)", remarks: "")
            column(name: "TITLE", type: "VARCHAR(240)", remarks: "")
            column(name: "SQLTEXT", type: "CLOB", remarks: "")
            column(name: "SYSCODE", type: "VARCHAR(240)", remarks: "")
            column(name: "DATASOURCE", type: "VARCHAR(30)", remarks: "")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-20-BPM_PAGE_TAG_LOV_FIELD") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_PAGE_TAG_LOV_FIELD_S', startValue: "10001")
        }

        createTable(tableName: "BPM_PAGE_TAG_LOV_FIELD", remarks: "") {

            column(name: "LOV_FIELD_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_PAGE_TAG_LOV_FIELD_PK")
            }
            column(name: "TAG_ID", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "ORDER_NUM", type: "BIGINT", remarks: "")
            column(name: "LOV_FIELD", type: "VARCHAR(240)", remarks: "")
            column(name: "LOV_FIELD_PROMPT", type: "VARCHAR(240)", remarks: "")
            column(name: "DISPLAY_FLAG", type: "VARCHAR(1)", remarks: "")
            column(name: "QUERY_FLAG", type: "VARCHAR(1)", remarks: "")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BPM_PAGE_TAG_LOV_FIELD", indexName: "BPM_PAGE_TAG_LOV_FIELD_N1") {
            column(name: "TAG_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-20-BPM_PAGE_TAG_LOV_MAP") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_PAGE_TAG_LOV_MAP_S', startValue: "10001")
        }

        createTable(tableName: "BPM_PAGE_TAG_LOV_MAP", remarks: "") {

            column(name: "MAP_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_PAGE_TAG_LOV_MAP_PK")
            }
            column(name: "TAG_ID", type: "BIGINT", remarks: "")
            column(name: "MAP_SEQUENCE", type: "BIGINT", remarks: "")
            column(name: "FROM_FIELD", type: "VARCHAR(240)", remarks: "")
            column(name: "TO_FIELD", type: "VARCHAR(240)", remarks: "")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BPM_PAGE_TAG_LOV_MAP", indexName: "BPM_PAGE_TAG_LOV_MAP_N1") {
            column(name: "TAG_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-20-BPM_PAGE_TAG_NUMBERFIELD") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_PAGE_TAG_NUMBERFIELD_S', startValue: "10001")
        }

        createTable(tableName: "BPM_PAGE_TAG_NUMBERFIELD", remarks: "") {

            column(name: "TAG_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_PAGE_TAG_NUMBERFIELD_PK")
            }
            column(name: "ALLOWDECIMALS", type: "VARCHAR(30)", remarks: "")
            column(name: "DECIMALPRECISION", type: "BIGINT", remarks: "")
            column(name: "ALLOWNEGATIVE", type: "VARCHAR(30)", remarks: "")
            column(name: "ALLOWFORMAT", type: "VARCHAR(30)", remarks: "")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-20-BPM_PAGE_TAG_RADIO") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_PAGE_TAG_RADIO_S', startValue: "10001")
        }

        createTable(tableName: "BPM_PAGE_TAG_RADIO", remarks: "") {

            column(name: "TAG_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_PAGE_TAG_RADIO_PK")
            }
            column(name: "options", type: "VARCHAR(240)", remarks: "")
            column(name: "VALUEFIELD", type: "VARCHAR(240)", remarks: "")
            column(name: "LABELEXPRESSION", type: "VARCHAR(240)", remarks: "")
            column(name: "LABELFIELD", type: "VARCHAR(240)", remarks: "")
            column(name: "LAYOUT", type: "VARCHAR(30)", remarks: "")
            column(name: "SYSCODE", type: "VARCHAR(200)", remarks: "")
            column(name: "SQLTEXT", type: "CLOB", remarks: "")
            column(name: "DATASOURCE", type: "VARCHAR(240)", remarks: "")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-20-BPM_PAGE_TAG_RADIO_OPT") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_PAGE_TAG_RADIO_OPT_S', startValue: "10001")
        }

        createTable(tableName: "BPM_PAGE_TAG_RADIO_OPT", remarks: "") {

            column(name: "OPTION_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_PAGE_TAG_RADIO_OPT_PK")
            }
            column(name: "TAG_ID", type: "BIGINT", remarks: "")
            column(name: "OPTION_SEQUENCE", type: "BIGINT", remarks: "")
            column(name: "label", type: "VARCHAR(240)", remarks: "")
            column(name: "LABEL_US", type: "VARCHAR(240)", remarks: "") { constraints(nullable: "false") }
            column(name: "VALUE", type: "VARCHAR(240)", remarks: "")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BPM_PAGE_TAG_RADIO_OPT", indexName: "BPM_PAGE_TAG_RADIO_OPT_N1") {
            column(name: "TAG_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-20-BPM_PAGE_TAG_TEXTFIELD") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_PAGE_TAG_TEXTFIELD_S', startValue: "10001")
        }

        createTable(tableName: "BPM_PAGE_TAG_TEXTFIELD", remarks: "") {

            column(name: "TAG_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_PAGE_TAG_TEXTFIELD_PK")
            }
            column(name: "TYPECASE", type: "VARCHAR(30)", remarks: "")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-20-BPM_PAGE_TAG_TREE") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_PAGE_TAG_TREE_S', startValue: "10001")
        }

        createTable(tableName: "BPM_PAGE_TAG_TREE", remarks: "") {

            column(name: "TAG_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_PAGE_TAG_TREE_PK")
            }
            column(name: "SHOWCHECKBOX", type: "VARCHAR(30)", remarks: "")
            column(name: "DISPLAYFIELD", type: "VARCHAR(30)", remarks: "")
            column(name: "IDFIELD", type: "VARCHAR(30)", remarks: "")
            column(name: "PARENTFIELD", type: "VARCHAR(30)", remarks: "")
            column(name: "SEQUENCEFIELD", type: "VARCHAR(30)", remarks: "")
            column(name: "RENDERER", type: "CLOB", remarks: "")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-20-BPM_PAGE_TAG_TREEGRID") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_PAGE_TAG_TREEGRID_S', startValue: "10001")
        }

        createTable(tableName: "BPM_PAGE_TAG_TREEGRID", remarks: "") {

            column(name: "TAG_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_PAGE_TAG_TREEGRID_PK")
            }
            column(name: "SHOWCHECKBOX", type: "VARCHAR(30)", remarks: "")
            column(name: "DISPLAYFIELD", type: "VARCHAR(30)", remarks: "")
            column(name: "IDFIELD", type: "VARCHAR(30)", remarks: "")
            column(name: "PARENTFIELD", type: "VARCHAR(30)", remarks: "")
            column(name: "SEQUENCEFIELD", type: "VARCHAR(30)", remarks: "")
            column(name: "RENDERER", type: "CLOB", remarks: "")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-20-BPM_TEMPLATE") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_TEMPLATE_S', startValue: "10001")
        }

        createTable(tableName: "BPM_TEMPLATE", remarks: "") {

            column(name: "TEMPLATE_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_TEMPLATE_PK")
            }
            column(name: "TEMPLATE_CODE", type: "VARCHAR(30)", remarks: "") { constraints(nullable: "false") }
            column(name: "SCREEN", type: "VARCHAR(200)", remarks: "") { constraints(nullable: "false") }
            column(name: "TEMPLATE_DESC", type: "VARCHAR(240)", remarks: "")
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BPM_TEMPLATE", indexName: "BPM_TEMPLATE_N1") {
            column(name: "TEMPLATE_CODE")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-20-BPM_TEMPLATE_REF_TABLE") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_TEMPLATE_REF_TABLE_S', startValue: "10001")
        }

        createTable(tableName: "BPM_TEMPLATE_REF_TABLE", remarks: "") {

            column(name: "REF_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_TEMPLATE_REF_TABLE_PK")
            }
            column(name: "TEMPLATE_ID", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "TABLE_NAME", type: "VARCHAR(30)", remarks: "") { constraints(nullable: "false") }
            column(name: "TABLE_DESCRIPTION", type: "VARCHAR(30)", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "TEMPLATE_ID,TABLE_NAME", tableName: "BPM_TEMPLATE_REF_TABLE", constraintName: "BPM_TEMPLATE_REF_TABLE_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-20-BPM_TPLT001_HEADER") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_TPLT001_HEADER_S', startValue: "10001")
        }

        createTable(tableName: "BPM_TPLT001_HEADER", remarks: "") {

            column(name: "HEADER_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_TPLT001_HEADER_PK")
            }
            column(name: "DOC_HEADER_ID", type: "BIGINT", remarks: "")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-20-BPM_TPLT001_LINE") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_TPLT001_LINE_S', startValue: "10001")
        }

        createTable(tableName: "BPM_TPLT001_LINE", remarks: "") {

            column(name: "LINE_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_TPLT001_LINE_PK")
            }
            column(name: "HEADER_ID", type: "BIGINT", remarks: "")
            column(name: "LINE_NUM", type: "BIGINT", remarks: "")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-20-BPM_TPLT_BUTTON") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_TPLT_BUTTON_S', startValue: "10001")
        }

        createTable(tableName: "BPM_TPLT_BUTTON", remarks: "") {

            column(name: "BUTTON_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_TPLT_BUTTON_PK")
            }
            column(name: "TEMPLATE_ID", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "BUTTON_SEQUENCE", type: "BIGINT", remarks: "")
            column(name: "BUTTON_CODE", type: "VARCHAR(30)", remarks: "") { constraints(nullable: "false") }
            column(name: "BUTTON_DESC", type: "VARCHAR(240)", remarks: "")
            column(name: "ID", type: "VARCHAR(240)", remarks: "")
            column(name: "text", type: "VARCHAR(240)", remarks: "")
            column(name: "CLICK", type: "CLOB", remarks: "")
            column(name: "BUTTON_TYPE", type: "CLOB", remarks: "")
            column(name: "WIDTH", type: "BIGINT", remarks: "")
            column(name: "HEIGHT", type: "BIGINT", remarks: "")
            column(name: "ICON", type: "BIGINT", remarks: "")
            column(name: "DISABLED", type: "VARCHAR(30)", remarks: "")
            column(name: "HIDDEN", type: "VARCHAR(30)", remarks: "")
            column(name: "style", type: "CLOB", remarks: "")
            column(name: "BTNSTYLE", type: "CLOB", remarks: "")
            column(name: "OPERATION_TYPE", type: "VARCHAR(30)", remarks: "")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "TEMPLATE_ID,BUTTON_CODE", tableName: "BPM_TPLT_BUTTON", constraintName: "BPM_TPLT_BUTTON_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-20-BPM_TPLT_DATASET") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_TPLT_DATASET_S', startValue: "10001")
        }

        createTable(tableName: "BPM_TPLT_DATASET", remarks: "") {

            column(name: "DATASET_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_TPLT_DATASET_PK")
            }
            column(name: "TEMPLATE_ID", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "DATASET_USAGE", type: "VARCHAR(240)", remarks: "")
            column(name: "DATASOURCE_TYPE", type: "VARCHAR(30)", remarks: "")
            column(name: "MODEL", type: "VARCHAR(240)", remarks: "")
            column(name: "BASE_URL", type: "VARCHAR(240)", remarks: "")
            column(name: "AUTO_QUERY", type: "VARCHAR(30)", remarks: "")
            column(name: "QUERY_URL", type: "VARCHAR(240)", remarks: "")
            column(name: "AUTO_CREATE", type: "VARCHAR(30)", remarks: "")
            column(name: "SUBMIT_URL", type: "VARCHAR(240)", remarks: "")
            column(name: "BIND_NAME", type: "VARCHAR(30)", remarks: "")
            column(name: "BIND_TARGET", type: "VARCHAR(240)", remarks: "")
            column(name: "SELECTABLE", type: "VARCHAR(30)", remarks: "")
            column(name: "SELECTION_MODEL", type: "VARCHAR(30)", remarks: "")
            column(name: "LOAD_DATA", type: "VARCHAR(30)", remarks: "")
            column(name: "LOOKUP_CODE", type: "VARCHAR(30)", remarks: "")
            column(name: "QUERY_DATASET", type: "VARCHAR(240)", remarks: "")
            column(name: "FETCHALL", type: "VARCHAR(30)", remarks: "")
            column(name: "PAGE_SIZE", type: "BIGINT", remarks: "")
            column(name: "AUTO_COUNT", type: "VARCHAR(30)", remarks: "")
            column(name: "AUTO_PAGESIZE", type: "VARCHAR(30)", remarks: "")
            column(name: "MAX_PAGESIZE", type: "BIGINT", remarks: "")
            column(name: "MODIFIED_CHECK", type: "VARCHAR(30)", remarks: "")
            column(name: "NOTIFICATION", type: "VARCHAR(240)", remarks: "")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BPM_TPLT_DATASET", indexName: "BPM_TPLT_DATASET_N1") {
            column(name: "TEMPLATE_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-20-BPM_TPLT_LAYOUT_BASIC") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_TPLT_LAYOUT_BASIC_S', startValue: "10001")
        }

        createTable(tableName: "BPM_TPLT_LAYOUT_BASIC", remarks: "") {

            column(name: "LAYOUT_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_TPLT_LAYOUT_BASIC_PK")
            }
            column(name: "TEMPLATE_ID", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "ID", type: "VARCHAR(240)", remarks: "")
            column(name: "LAYOUT_SEQUENCE", type: "BIGINT", remarks: "")
            column(name: "LAYOUT_CODE", type: "VARCHAR(30)", remarks: "")
            column(name: "LAYOUT_DESC", type: "VARCHAR(240)", remarks: "")
            column(name: "LAYOUT_TYPE", type: "VARCHAR(30)", remarks: "")
            column(name: "PARENT_LAYOUT_ID", type: "BIGINT", remarks: "")
            column(name: "LAYOUT_LEVEL", type: "BIGINT", remarks: "")
            column(name: "DATASET", type: "VARCHAR(240)", remarks: "")
            column(name: "TAB_GROUP_NUMBER", type: "BIGINT", remarks: "")
            column(name: "WIDTH", type: "BIGINT", remarks: "")
            column(name: "HEIGHT", type: "BIGINT", remarks: "")
            column(name: "MARGINWIDTH", type: "BIGINT", remarks: "")
            column(name: "MARGINHEIGHT", type: "BIGINT", remarks: "")
            column(name: "style", type: "CLOB", remarks: "")
            column(name: "HIDDEN", type: "VARCHAR(30)", remarks: "")
            column(name: "BUSINESS_CATEGORY", type: "VARCHAR(30)", remarks: "")
            column(name: "PARENT_BUSINESS_CATEGORY", type: "VARCHAR(30)", remarks: "")
            column(name: "REF_TABLE", type: "VARCHAR(240)", remarks: "")
            column(name: "REF_FIELD", type: "VARCHAR(240)", remarks: "")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "TEMPLATE_ID,LAYOUT_CODE", tableName: "BPM_TPLT_LAYOUT_BASIC", constraintName: "BPM_TPLT_LAYOUT_BASIC_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-20-BPM_TPLT_LAYOUT_BUTTON") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_TPLT_LAYOUT_BUTTON_S', startValue: "10001")
        }

        createTable(tableName: "BPM_TPLT_LAYOUT_BUTTON", remarks: "") {

            column(name: "BUTTON_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_TPLT_LAYOUT_BUTTON_PK")
            }
            column(name: "LAYOUT_ID", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "BUTTON_SEQUENCE", type: "BIGINT", remarks: "")
            column(name: "BUTTON_CODE", type: "VARCHAR(30)", remarks: "") { constraints(nullable: "false") }
            column(name: "BUTTON_DESC", type: "VARCHAR(240)", remarks: "")
            column(name: "ID", type: "VARCHAR(240)", remarks: "")
            column(name: "text", type: "VARCHAR(240)", remarks: "")
            column(name: "CLICK", type: "CLOB", remarks: "")
            column(name: "BUTTON_TYPE", type: "CLOB", remarks: "")
            column(name: "WIDTH", type: "BIGINT", remarks: "")
            column(name: "HEIGHT", type: "BIGINT", remarks: "")
            column(name: "ICON", type: "BIGINT", remarks: "")
            column(name: "DISABLED", type: "VARCHAR(30)", remarks: "")
            column(name: "HIDDEN", type: "VARCHAR(30)", remarks: "")
            column(name: "style", type: "CLOB", remarks: "")
            column(name: "BTNSTYLE", type: "CLOB", remarks: "")
            column(name: "OPERATION_TYPE", type: "VARCHAR(30)", remarks: "")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "BUTTON_CODE,LAYOUT_ID", tableName: "BPM_TPLT_LAYOUT_BUTTON", constraintName: "BPM_TPLT_LAYOUT_BUTTON_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-20-BPM_TPLT_LAYOUT_FORM") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_TPLT_LAYOUT_FORM_S', startValue: "10001")
        }

        createTable(tableName: "BPM_TPLT_LAYOUT_FORM", remarks: "") {

            column(name: "LAYOUT_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_TPLT_LAYOUT_FORM_PK")
            }
            column(name: "PROMPT", type: "VARCHAR(240)", remarks: "")
            column(name: "TITLE", type: "VARCHAR(240)", remarks: "")
            column(name: "COLUMN_NUM", type: "BIGINT", remarks: "")
            column(name: "ROW_NUM", type: "BIGINT", remarks: "")
            column(name: "LABELWIDTH", type: "BIGINT", remarks: "")
            column(name: "LABELSEPARATOR", type: "VARCHAR(30)", remarks: "")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-20-BPM_TPLT_LAYOUT_GRID") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_TPLT_LAYOUT_GRID_S', startValue: "10001")
        }

        createTable(tableName: "BPM_TPLT_LAYOUT_GRID", remarks: "") {

            column(name: "LAYOUT_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_TPLT_LAYOUT_GRID_PK")
            }
            column(name: "NAVBAR", type: "VARCHAR(30)", remarks: "")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-20-BPM_TPLT_LAYOUT_TAB") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_TPLT_LAYOUT_TAB_S', startValue: "10001")
        }

        createTable(tableName: "BPM_TPLT_LAYOUT_TAB", remarks: "") {

            column(name: "LAYOUT_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_TPLT_LAYOUT_TAB_PK")
            }
            column(name: "SELECTED", type: "VARCHAR(30)", remarks: "")
            column(name: "CLOSEABLE", type: "VARCHAR(30)", remarks: "")
            column(name: "DISABLED", type: "VARCHAR(30)", remarks: "")
            column(name: "ref", type: "VARCHAR(240)", remarks: "")
            column(name: "TABSTYLE", type: "CLOB", remarks: "")
            column(name: "BODYSTYLE", type: "CLOB", remarks: "")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-05-20-BPM_TPLT_TAG_BASIC") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BPM_TPLT_TAG_BASIC_S', startValue: "10001")
        }

        createTable(tableName: "BPM_TPLT_TAG_BASIC", remarks: "") {

            column(name: "TAG_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BPM_TPLT_TAG_BASIC_PK")
            }
            column(name: "LAYOUT_ID", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "TAG_SEQUENCE", type: "BIGINT", remarks: "")
            column(name: "TAG_CODE", type: "VARCHAR(30)", remarks: "") { constraints(nullable: "false") }
            column(name: "TAG_DESC", type: "VARCHAR(240)", remarks: "")
            column(name: "ID", type: "VARCHAR(240)", remarks: "")
            column(name: "TAG_TYPE", type: "VARCHAR(30)", remarks: "")
            column(name: "COLSPAN", type: "BIGINT", remarks: "")
            column(name: "ROWSPAN", type: "BIGINT", remarks: "")
            column(name: "HIDDEN", type: "VARCHAR(30)", remarks: "")
            column(name: "PARENT_TAG_ID", type: "BIGINT", remarks: "")
            column(name: "NAME", type: "VARCHAR(240)", remarks: "")
            column(name: "DEFAULTVALUE", type: "VARCHAR(240)", remarks: "")
            column(name: "READONLY", type: "VARCHAR(30)", remarks: "")
            column(name: "REQUIRED", type: "VARCHAR(30)", remarks: "")
            column(name: "PROMPT", type: "VARCHAR(240)", remarks: "")
            column(name: "validator", type: "CLOB", remarks: "")
            column(name: "REQUIREDMESSAGE", type: "CLOB", remarks: "")
            column(name: "FIELD_SOURCE", type: "VARCHAR(240)", remarks: "")
            column(name: "BINDTARGET", type: "VARCHAR(240)", remarks: "")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")

            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

    }


}