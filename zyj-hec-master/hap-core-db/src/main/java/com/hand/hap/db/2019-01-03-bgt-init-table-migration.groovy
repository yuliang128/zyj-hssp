package com.hand.hap.db

import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = mhi.dbType()
databaseChangeLog(logicalFilePath: '2019-01-03-bgt-init-table-migration.groovy') {


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_BUDGET_CURRENCY_TMP") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_BUDGET_CURRENCY_TMP_S', startValue: "10001")
        }

        createTable(tableName: "BGT_BUDGET_CURRENCY_TMP", remarks: "预算_关联币种临时表") {

            column(name: "FROM_CURRENCY_CODE", type: "VARCHAR(30)", remarks: "从币种") { constraints(nullable: "false") }
            column(name: "TO_CURRENCY_CODE", type: "VARCHAR(30)", remarks: "到币种") { constraints(nullable: "false") }
            column(name: "EXCHANGE_RATE_TYPE", type: "VARCHAR(30)", remarks: "汇率类型")
            column(name: "EXCHANGE_RATE", type: "NUMBER", remarks: "汇率") { constraints(nullable: "false") }
            column(name: "EXCHANGE_DATE", type: "DATETIME", remarks: "汇兑日期") { constraints(nullable: "false") }
            column(name: "EXCHANGE_PERIOD_NAME", type: "VARCHAR(30)", remarks: "汇兑期间") {
                constraints(nullable: "false")
            }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_BUDGET_ITEM") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_BUDGET_ITEM_S', startValue: "10001")
        }

        createTable(tableName: "BGT_BUDGET_ITEM", remarks: "预算项目表") {

            column(name: "BUDGET_ITEM_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_BUDGET_ITEM_PK")
            }
            column(name: "BGT_ORG_ID", type: "BIGINT", remarks: "预算组织ID") { constraints(nullable: "false") }
            column(name: "BUDGET_ITEM_CODE", type: "VARCHAR(30)", remarks: "预算项目代码") { constraints(nullable: "false") }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "预算项目描述ID") { constraints(nullable: "false") }
            column(name: "BUDGET_ITEM_TYPE_ID", type: "BIGINT", remarks: "预算项目类型ID") { constraints(nullable: "false") }
            column(name: "VARIATION_ATTRIBUTE", type: "VARCHAR(30)", remarks: "变动属性")
            column(name: "NOTES", type: "VARCHAR(2000)", remarks: "备注")
            column(name: "SUMMARY_FLAG", type: "VARCHAR(1)", remarks: "汇总标志") { constraints(nullable: "false") }
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BGT_BUDGET_ITEM", indexName: "BGT_BUDGET_ITEMS_N1") {
            column(name: "BGT_ORG_ID")
            column(name: "BUDGET_ITEM_TYPE_ID")
        }
        createIndex(tableName: "BGT_BUDGET_ITEM", indexName: "BGT_BUDGET_ITEMS_N2") {
            column(name: "BUDGET_ITEM_TYPE_ID")
        }

        addUniqueConstraint(columnNames: "BUDGET_ITEM_CODE,BGT_ORG_ID", tableName: "BGT_BUDGET_ITEM", constraintName: "BGT_BUDGET_ITEMS_U1")
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_BUDGET_ITEM_TL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_BUDGET_ITEM_TL_S', startValue: "10001")
        }

        createTable(tableName: "BGT_BUDGET_ITEM_TL", remarks: "预算项目表多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "BUDGET_ITEM_ID", type: "BIGINT", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "预算项目描述ID")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_BUDGET_ITEM_INTERFACE") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_BUDGET_ITEM_INTERFACE_S', startValue: "10001")
        }

        createTable(tableName: "BGT_BUDGET_ITEM_INTERFACE", remarks: "预算项目导入接口表") {

            column(name: "BATCH_ID", type: "BIGINT", remarks: "批次ID")
            column(name: "BATCH_LINE_ID", type: "BIGINT", remarks: "批次行ID")
            column(name: "BUDGET_ORG_ID", type: "BIGINT", remarks: "预算组织ID")
            column(name: "BUDGET_ORG_CODE", type: "VARCHAR(30)", remarks: "预算组织代码") { constraints(nullable: "false") }
            column(name: "BUDGET_ITEM_TYPE_ID", type: "BIGINT", remarks: "预算项目类型ID")
            column(name: "BUDGET_ITEM_TYPE_CODE", type: "VARCHAR(30)", remarks: "预算项目类型代码") {
                constraints(nullable: "false")
            }
            column(name: "BUDGET_ITEM_CODE", type: "VARCHAR(30)", remarks: "预算项目代码") { constraints(nullable: "false") }
            column(name: "DESCRIPTION", type: "VARCHAR(2000)", remarks: "预算项目描述")
            column(name: "NOTES", type: "VARCHAR(2000)", remarks: "备注")
            column(name: "SUMMARY_FLAG", type: "VARCHAR(1)", remarks: "汇总标志") { constraints(nullable: "false") }
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "ACCOUNT_ID", type: "BIGINT", remarks: "科目ID")
            column(name: "ACCOUNT_CODE", type: "VARCHAR(100)", remarks: "科目代码")
            column(name: "INTERFACE_ENABLED_FLAG", type: "VARCHAR(1)", remarks: "接口启用标志")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID") { constraints(nullable: "false") }
            column(name: "VARIATION_ATTRIBUTE", type: "VARCHAR(30)", remarks: "变动属性")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BGT_BUDGET_ITEM_INTERFACE", indexName: "BGT_BUDGET_ITEMS_INTERFACE_N1") {
            column(name: "BATCH_ID")
            column(name: "BUDGET_ORG_CODE")
            column(name: "BUDGET_ITEM_TYPE_CODE")
            column(name: "BUDGET_ITEM_CODE")
        }

        addUniqueConstraint(columnNames: "BATCH_ID,BATCH_LINE_ID", tableName: "BGT_BUDGET_ITEM_INTERFACE", constraintName: "BGT_BUDGET_ITEMS_INTERFACE_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_BUDGET_ITEM_INT_ERR_LOG") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_BUDGET_ITEM_INT_ERR_LOG_S', startValue: "10001")
        }

        createTable(tableName: "BGT_BUDGET_ITEM_INT_ERR_LOG", remarks: "预算项目导入错误日志表") {

            column(name: "BATCH_ID", type: "BIGINT", remarks: "批次ID")
            column(name: "BATCH_LINE_ID", type: "BIGINT", remarks: "批次行ID")
            column(name: "BUDGET_ORG_CODE", type: "VARCHAR(30)", remarks: "预算组织编码")
            column(name: "BUDGET_ITEM_TYPE_CODE", type: "VARCHAR(30)", remarks: "预算项目类型编码")
            column(name: "BUDGET_ITEM_CODE", type: "VARCHAR(30)", remarks: "预算项目编码")
            column(name: "ACCOUNT_CODE", type: "VARCHAR(100)", remarks: "会计科目编码")
            column(name: "MESSAGE", type: "VARCHAR(1000)", remarks: "日志信息")
            column(name: "MESSAGE_DATE", type: "DATETIME", remarks: "日志日期")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BGT_BUDGET_ITEM_INT_ERR_LOG", indexName: "BGT_BUDGET_ITM_INT_ERR_LOGS_N1") {
            column(name: "MESSAGE_DATE")
        }
        createIndex(tableName: "BGT_BUDGET_ITEM_INT_ERR_LOG", indexName: "BGT_BUDGET_ITM_INT_ERR_LOGS_N2") {
            column(name: "BATCH_ID")
            column(name: "BATCH_LINE_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_BUDGET_ITEM_TEMP") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_BUDGET_ITEM_TEMP_S', startValue: "10001")
        }

        createTable(tableName: "BGT_BUDGET_ITEM_TEMP", remarks: "") {

            column(name: "BUDGET_ORG_ID", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "BUDGET_ITEM_TYPE_ID", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "BUDGET_ITEM_ID", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "BUDGET_ITEM_CODE", type: "VARCHAR(30)", remarks: "") { constraints(nullable: "false") }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "") { constraints(nullable: "false") }
            column(name: "VARIATION_ATTRIBUTE", type: "VARCHAR(30)", remarks: "")
            column(name: "NOTES", type: "VARCHAR(2000)", remarks: "")
            column(name: "SUMMARY_FLAG", type: "VARCHAR(1)", remarks: "") { constraints(nullable: "false") }
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_BUDGET_ITEM_TEMP_TL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_BUDGET_ITEM_TEMP_TL_S', startValue: "10001")
        }

        createTable(tableName: "BGT_BUDGET_ITEM_TEMP_TL", remarks: "多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_BUDGET_ITEM_ACCOUNT") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_BUDGET_ITEM_ACCOUNT_S', startValue: "10001")
        }

        createTable(tableName: "BGT_BUDGET_ITEM_ACCOUNT", remarks: "预算项目明细科目关联表") {

            column(name: "BUDGET_ITEM_ACCOUNTS_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "预算项目明细科目表ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_BUDGET_ITEM_ACCOUNT_PK")
            }
            column(name: "BUDGET_ORG_ID", type: "BIGINT", remarks: "预算组织ID") { constraints(nullable: "false") }
            column(name: "BUDGET_ITEM_ID", type: "BIGINT", remarks: "预算项目ID") { constraints(nullable: "false") }
            column(name: "ACCOUNT_ID", type: "BIGINT", remarks: "科目ID") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BGT_BUDGET_ITEM_ACCOUNT", indexName: "BGT_BUDGET_ITEM_ACCOUNTS_N1") {
            column(name: "BUDGET_ITEM_ID")
            column(name: "BUDGET_ORG_ID")
            column(name: "ACCOUNT_ID")
        }

        addUniqueConstraint(columnNames: "ACCOUNT_ID,BUDGET_ITEM_ID", tableName: "BGT_BUDGET_ITEM_ACCOUNT", constraintName: "BGT_BUDGET_ITEM_ACCOUNTS_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_BUDGET_ITEM_ASGN_COM") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_BUDGET_ITEM_ASGN_COM_S', startValue: "10001")
        }

        createTable(tableName: "BGT_BUDGET_ITEM_ASGN_COM", remarks: "预算项目分配公司") {

            column(name: "ASSIGN_MO_ID", type: "BIGINT", remarks: "预算项目分配管理组织ID") { constraints(nullable: "false") }
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "管理公司ID") { constraints(nullable: "false") }
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "ASSIGN_COM_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_BUDGET_ITEM_ASGN_COM_PK")
            }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "COMPANY_ID,ASSIGN_MO_ID", tableName: "BGT_BUDGET_ITEM_ASGN_COM", constraintName: "BGT_BUDGET_ITEM_ASGN_COM_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_BUDGET_ITEM_ASGN_MO") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_BUDGET_ITEM_ASGN_MO_S', startValue: "10001")
        }

        createTable(tableName: "BGT_BUDGET_ITEM_ASGN_MO", remarks: "预算项目分配管理组织") {

            column(name: "ASSIGN_MO_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_BUDGET_ITEM_ASGN_MO_PK")
            }
            column(name: "BUDGET_ITEM_ID", type: "BIGINT", remarks: "预算项目ID") { constraints(nullable: "false") }
            column(name: "MAG_ORG_ID", type: "BIGINT", remarks: "管理组织ID") { constraints(nullable: "false") }
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "MAG_ORG_ID,BUDGET_ITEM_ID", tableName: "BGT_BUDGET_ITEM_ASGN_MO", constraintName: "BGT_BUDGET_ITEM_ASGN_MO_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_BUDGET_ITEM_HIERARCHY") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_BUDGET_ITEM_HIERARCHY_S', startValue: "10001")
        }

        createTable(tableName: "BGT_BUDGET_ITEM_HIERARCHY", remarks: "预算项目层次表") {

            column(name: "HIERARCHY_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_BUDGET_ITEM_HIERARCHY_PK")
            }
            column(name: "PARENT_BUDGET_ITEM_ID", type: "BIGINT", remarks: "父预算项目ID") { constraints(nullable: "false") }
            column(name: "BUDGET_ITEM_ID", type: "BIGINT", remarks: "子预算项目ID") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "BUDGET_ITEM_ID,PARENT_BUDGET_ITEM_ID", tableName: "BGT_BUDGET_ITEM_HIERARCHY", constraintName: "BGT_BUDGET_ITEM_HIERARCHY_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_BUDGET_ITEM_MAPPING") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_BUDGET_ITEM_MAPPING_S', startValue: "10001")
        }

        createTable(tableName: "BGT_BUDGET_ITEM_MAPPING", remarks: "预算项决定规则表") {

            column(name: "MAPPING_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_BUDGET_ITEM_MAPPING_PK")
            }
            column(name: "BGT_ORG_ID", type: "BIGINT", remarks: "预算组织ID") { constraints(nullable: "false") }
            column(name: "BUSINESS_CATEGORY", type: "VARCHAR(30)", remarks: "业务类别（SYSCODE：BGT_RELATED_BUSINESS_TYPE）") {
                constraints(nullable: "false")
            }
            column(name: "PRIORITY", type: "NUMBER", remarks: "优先级") { constraints(nullable: "false") }
            column(name: "BUDGET_ITEM_ID", type: "BIGINT", remarks: "预算项目ID") { constraints(nullable: "false") }
            column(name: "MAG_ORG_ID", type: "BIGINT", remarks: "管理组织ID")
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "管理公司ID")
            column(name: "DOCUMENT_TYPE_ID", type: "BIGINT", remarks: "单据类型ID")
            column(name: "BUSINESS_TYPE_ID", type: "BIGINT", remarks: "业务类型（业务类别为费用申请/报销则为报销类型ID；业务类别为采购申请/订单则为采购类型ID）")
            column(name: "PARAM_VALUE1_ID", type: "BIGINT", remarks: "参数1（业务类别为费用申请/报销则为报销类型下费用项目；业务类别为采购申请/订单则为采购类型下采购对象类型）")
            column(name: "PARAM_VALUE2_ID", type: "BIGINT", remarks: "参数2（业务类别为费用申请/报销则为空；业务类别为采购申请/订单则为参数1下的明细值如：物料、资产子分类、费用项目）")
            column(name: "UNIT_ID", type: "BIGINT", remarks: "部门ID")
            column(name: "POSITION_ID", type: "BIGINT", remarks: "岗位ID")
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "员工ID")
            column(name: "ACC_ENTITY_ID", type: "BIGINT", remarks: "核算主体ID")
            column(name: "RESP_CENTER_ID", type: "BIGINT", remarks: "成本中心ID")
            column(name: "PAYEE_CATEGORY", type: "VARCHAR(30)", remarks: "收款对象类型")
            column(name: "PAYEE_ID", type: "BIGINT", remarks: "收款方")
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
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BGT_BUDGET_ITEM_MAPPING", indexName: "BGT_BUDGET_ITEM_MAPPING_N1") {
            column(name: "BGT_ORG_ID")
            column(name: "PRIORITY")
            column(name: "BUSINESS_CATEGORY")
            column(name: "MAG_ORG_ID")
            column(name: "COMPANY_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_BUDGET_ITEM_TYPE") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_BUDGET_ITEM_TYPE_S', startValue: "10001")
        }

        createTable(tableName: "BGT_BUDGET_ITEM_TYPE", remarks: "预算项目类型表") {

            column(name: "BUDGET_ITEM_TYPE_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_BUDGET_ITEM_TYPE_PK")
            }
            column(name: "BGT_ORG_ID", type: "BIGINT", remarks: "预算组织ID") { constraints(nullable: "false") }
            column(name: "BUDGET_ITEM_TYPE_CODE", type: "VARCHAR(30)", remarks: "预算项目类型代码") {
                constraints(nullable: "false")
            }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "预算项目类型描述ID") { constraints(nullable: "false") }
            column(name: "NOTES", type: "VARCHAR(2000)", remarks: "备注")
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "BGT_ORG_ID,BUDGET_ITEM_TYPE_CODE", tableName: "BGT_BUDGET_ITEM_TYPE", constraintName: "BGT_BUDGET_ITEM_TYPES_U1")
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_BUDGET_ITEM_TYPE_TL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_BUDGET_ITEM_TYPE_TL_S', startValue: "10001")
        }

        createTable(tableName: "BGT_BUDGET_ITEM_TYPE_TL", remarks: "预算项目类型表多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "BUDGET_ITEM_TYPE_ID", type: "BIGINT", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "预算项目类型描述ID")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_BUDGET_RESERVE") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_BUDGET_RESERVE_S', startValue: "10001")
        }

        createTable(tableName: "BGT_BUDGET_RESERVE", remarks: "预算保留表") {

            column(name: "BUDGET_RESERVE_LINE_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "预算保留表行ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_BUDGET_RESERVE_PK")
            }
            column(name: "RESERVE_COMPANY_ID", type: "BIGINT", remarks: "预算公司ID")
            column(name: "RESERVE_OPERATION_UNIT_ID", type: "BIGINT", remarks: "废弃_预算经营单位")
            column(name: "BGT_ORG_ID", type: "BIGINT", remarks: "预算组织ID") { constraints(nullable: "false") }
            column(name: "BGT_ENTITY_ID", type: "BIGINT", remarks: "预算实体ID") { constraints(nullable: "false") }
            column(name: "BGT_CENTER_ID", type: "BIGINT", remarks: "预算中心ID") { constraints(nullable: "false") }
            column(name: "PERIOD_NAME", type: "VARCHAR(30)", remarks: "期间") { constraints(nullable: "false") }
            column(name: "RELEASE_ID", type: "BIGINT", remarks: "预算释放ID")
            column(name: "BUSINESS_TYPE", type: "VARCHAR(30)", remarks: "EXP_REQUISITION:费用申请单,EXP_REPORT:费用报销单") {
                constraints(nullable: "false")
            }
            column(name: "RESERVE_FLAG", type: "VARCHAR(1)", remarks: "R:申请单冻结,U:报销单占用") {
                constraints(nullable: "false")
            }
            column(name: "STATUS", type: "VARCHAR(30)", remarks: "状态") { constraints(nullable: "false") }
            column(name: "MANUAL_FLAG", type: "VARCHAR(1)", remarks: "手工标志") { constraints(nullable: "false") }
            column(name: "DOCUMENT_ID", type: "BIGINT", remarks: "费用申请单/报销单头ID")
            column(name: "DOCUMENT_LINE_ID", type: "BIGINT", remarks: "费用申请单/报销单分配行ID")
            column(name: "CURRENCY", type: "VARCHAR(30)", remarks: "币种")
            column(name: "BUDGET_ITEM_ID", type: "BIGINT", remarks: "预算项目ID") { constraints(nullable: "false") }
            column(name: "RESPONSIBILITY_CENTER_ID", type: "BIGINT", remarks: "成本中心ID")
            column(name: "EXCHANGE_RATE_TYPE", type: "VARCHAR(30)", remarks: "汇率类型")
            column(name: "EXCHANGE_RATE", type: "NUMBER", remarks: "汇率")
            column(name: "AMOUNT", type: "NUMBER", remarks: "金额")
            column(name: "FUNCTIONAL_AMOUNT", type: "NUMBER", remarks: "本位币金额")
            column(name: "QUANTITY", type: "NUMBER", remarks: "数量")
            column(name: "UOM", type: "VARCHAR(30)", remarks: "单位")
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "单据公司ID")
            column(name: "OPERATION_UNIT_ID", type: "BIGINT", remarks: "废弃_单据经营单位")
            column(name: "UNIT_ID", type: "BIGINT", remarks: "部门ID")
            column(name: "POSITION_ID", type: "BIGINT", remarks: "岗位ID")
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "员工ID")
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
            column(name: "SOURCE_TYPE", type: "VARCHAR(30)", remarks: "预算释放类型")
            column(name: "PERIOD_YEAR", type: "BIGINT", remarks: "预算年度")
            column(name: "PERIOD_QUARTER", type: "BIGINT", remarks: "预算季度")
            column(name: "BGT_PERIOD_NUM", type: "BIGINT", remarks: "预算期间数")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BGT_BUDGET_RESERVE", indexName: "BGT_BUDGET_RESERVES_N1") {
            column(name: "BUDGET_ITEM_ID")
        }
        createIndex(tableName: "BGT_BUDGET_RESERVE", indexName: "BGT_BUDGET_RESERVES_N2") {
            column(name: "DOCUMENT_LINE_ID")
            column(name: "BUSINESS_TYPE")
            column(name: "DOCUMENT_ID")
        }
        createIndex(tableName: "BGT_BUDGET_RESERVE", indexName: "BGT_BUDGET_RESERVES_N3") {
            column(name: "RELEASE_ID")
        }
        createIndex(tableName: "BGT_BUDGET_RESERVE", indexName: "BGT_BUDGET_RESERVES_N4") {
            column(name: "BGT_ORG_ID")
            column(name: "BGT_CENTER_ID")
            column(name: "BGT_ENTITY_ID")
        }
        createIndex(tableName: "BGT_BUDGET_RESERVE", indexName: "BGT_BUDGET_RESERVES_N5") {
            column(name: "BUSINESS_TYPE")
            column(name: "DOCUMENT_LINE_ID")
            column(name: "STATUS")
        }
        createIndex(tableName: "BGT_BUDGET_RESERVE", indexName: "BGT_BUDGET_RESERVES_N6") {
            column(name: "PERIOD_YEAR")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_BUDGET_RESERVE_SUM_TEMP") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_BUDGET_RESERVE_SUM_TEMP_S', startValue: "10001")
        }

        createTable(tableName: "BGT_BUDGET_RESERVE_SUM_TEMP", remarks: "预算使用汇总金额临时表") {

            column(name: "BGT_ORG_ID", type: "BIGINT", remarks: "预算组织id") { constraints(nullable: "false") }
            column(name: "BUDGET_STRUCTURE_ID", type: "BIGINT", remarks: "预算表id")
            column(name: "SCENARIO_ID", type: "BIGINT", remarks: "预算场景id") { constraints(nullable: "false") }
            column(name: "VERSION_ID", type: "BIGINT", remarks: "版本ID") { constraints(nullable: "false") }
            column(name: "RESPONSIBILITY_CENTER_ID", type: "BIGINT", remarks: "责任中心id")
            column(name: "BUDGET_ITEM_ID", type: "BIGINT", remarks: "预算项目id")
            column(name: "PERIOD_STRATEGY", type: "VARCHAR(30)", remarks: "编制期段")
            column(name: "PERIOD_YEAR", type: "BIGINT", remarks: "预算年度")
            column(name: "PERIOD_QUARTER", type: "BIGINT", remarks: "季度")
            column(name: "BGT_PERIOD_NUM", type: "BIGINT", remarks: "预算期间")
            column(name: "FUNCTIONAL_AMOUNT", type: "NUMBER", remarks: "本位币金额")
            column(name: "QUANTITY", type: "NUMBER", remarks: "数量")
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司ID")
            column(name: "OPERATION_UNIT_ID", type: "BIGINT", remarks: "经营单位id")
            column(name: "UNIT_ID", type: "BIGINT", remarks: "部门id")
            column(name: "UNIT_GROUP_ID", type: "BIGINT", remarks: "部门组ID")
            column(name: "POSITION_ID", type: "BIGINT", remarks: "岗位id")
            column(name: "POSITION_GROUP_ID", type: "BIGINT", remarks: "岗位组ID")
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "员工id")
            column(name: "EXPENSE_USER_GROUP_ID", type: "BIGINT", remarks: "")
            column(name: "DIMENSION1_ID", type: "BIGINT", remarks: "维值1id")
            column(name: "DIMENSION2_ID", type: "BIGINT", remarks: "维值2id")
            column(name: "DIMENSION3_ID", type: "BIGINT", remarks: "维值3id")
            column(name: "DIMENSION4_ID", type: "BIGINT", remarks: "维值4id")
            column(name: "DIMENSION5_ID", type: "BIGINT", remarks: "维值5id")
            column(name: "DIMENSION6_ID", type: "BIGINT", remarks: "维值6id")
            column(name: "DIMENSION7_ID", type: "BIGINT", remarks: "维值7id")
            column(name: "DIMENSION8_ID", type: "BIGINT", remarks: "维值8id")
            column(name: "DIMENSION9_ID", type: "BIGINT", remarks: "维值9id")
            column(name: "DIMENSION10_ID", type: "BIGINT", remarks: "维值10id")
            column(name: "CACHE_FIELD_NAMES", type: "VARCHAR(1000)", remarks: "预算查询描述")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_BUDGET_RESERVE_SUM_TMP") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_BUDGET_RESERVE_SUM_TMP_S', startValue: "10001")
        }

        createTable(tableName: "BGT_BUDGET_RESERVE_SUM_TMP", remarks: "预算使用金额临时表") {

            column(name: "BGT_ORG_ID", type: "BIGINT", remarks: "预算组织id") { constraints(nullable: "false") }
            column(name: "BUDGET_STRUCTURE_ID", type: "BIGINT", remarks: "预算表id")
            column(name: "SCENARIO_ID", type: "BIGINT", remarks: "预算场景id") { constraints(nullable: "false") }
            column(name: "VERSION_ID", type: "BIGINT", remarks: "版本ID") { constraints(nullable: "false") }
            column(name: "RESPONSIBILITY_CENTER_ID", type: "BIGINT", remarks: "责任中心id")
            column(name: "BUDGET_ITEM_ID", type: "BIGINT", remarks: "预算项目id")
            column(name: "PERIOD_STRATEGY", type: "VARCHAR(30)", remarks: "编制期段")
            column(name: "PERIOD_YEAR", type: "BIGINT", remarks: "预算年度")
            column(name: "PERIOD_QUARTER", type: "BIGINT", remarks: "季度")
            column(name: "BGT_PERIOD_NUM", type: "BIGINT", remarks: "预算期间")
            column(name: "FUNCTIONAL_AMOUNT", type: "NUMBER", remarks: "本位币金额")
            column(name: "QUANTITY", type: "NUMBER", remarks: "数量")
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司ID")
            column(name: "OPERATION_UNIT_ID", type: "BIGINT", remarks: "经营单位id")
            column(name: "UNIT_ID", type: "BIGINT", remarks: "部门id")
            column(name: "UNIT_GROUP_ID", type: "BIGINT", remarks: "部门组ID")
            column(name: "POSITION_ID", type: "BIGINT", remarks: "岗位id")
            column(name: "POSITION_GROUP_ID", type: "BIGINT", remarks: "岗位组ID")
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "员工id")
            column(name: "EXPENSE_USER_GROUP_ID", type: "BIGINT", remarks: "")
            column(name: "DIMENSION1_ID", type: "BIGINT", remarks: "维值1id")
            column(name: "DIMENSION2_ID", type: "BIGINT", remarks: "维值2id")
            column(name: "DIMENSION3_ID", type: "BIGINT", remarks: "维值3id")
            column(name: "DIMENSION4_ID", type: "BIGINT", remarks: "维值4id")
            column(name: "DIMENSION5_ID", type: "BIGINT", remarks: "维值5id")
            column(name: "DIMENSION6_ID", type: "BIGINT", remarks: "维值6id")
            column(name: "DIMENSION7_ID", type: "BIGINT", remarks: "维值7id")
            column(name: "DIMENSION8_ID", type: "BIGINT", remarks: "维值8id")
            column(name: "DIMENSION9_ID", type: "BIGINT", remarks: "维值9id")
            column(name: "DIMENSION10_ID", type: "BIGINT", remarks: "维值10id")
            column(name: "CACHE_FIELD_NAMES", type: "VARCHAR(1000)", remarks: "预算查询描述")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_BUDGET_RESERVE_TEMP") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_BUDGET_RESERVE_TEMP_S', startValue: "10001")
        }

        createTable(tableName: "BGT_BUDGET_RESERVE_TEMP", remarks: "") {

            column(name: "DIMENSION7_ID", type: "BIGINT", remarks: "")
            column(name: "DIMENSION8_ID", type: "BIGINT", remarks: "")
            column(name: "DIMENSION9_ID", type: "BIGINT", remarks: "")
            column(name: "DIMENSION10_ID", type: "BIGINT", remarks: "")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "")
            column(name: "SOURCE_TYPE", type: "VARCHAR(30)", remarks: "")
            column(name: "LINE_ID", type: "BIGINT", remarks: "")
            column(name: "BUDGET_RESERVE_LINE_ID", type: "BIGINT", remarks: "")
            column(name: "RESERVE_COMPANY_ID", type: "BIGINT", remarks: "")
            column(name: "RESERVE_OPERATION_UNIT_ID", type: "BIGINT", remarks: "")
            column(name: "BGT_ORG_ID", type: "BIGINT", remarks: "")
            column(name: "PERIOD_YEAR", type: "BIGINT", remarks: "")
            column(name: "PERIOD_QUARTER", type: "BIGINT", remarks: "")
            column(name: "PERIOD_NAME", type: "VARCHAR(30)", remarks: "")
            column(name: "BGT_PERIOD_NUM", type: "BIGINT", remarks: "")
            column(name: "RELEASE_ID", type: "BIGINT", remarks: "")
            column(name: "BUSINESS_TYPE", type: "VARCHAR(30)", remarks: "")
            column(name: "RESERVE_FLAG", type: "VARCHAR(1)", remarks: "")
            column(name: "STATUS", type: "VARCHAR(30)", remarks: "")
            column(name: "MANUAL_FLAG", type: "VARCHAR(1)", remarks: "")
            column(name: "DOCUMENT_ID", type: "BIGINT", remarks: "")
            column(name: "DOCUMENT_LINE_ID", type: "BIGINT", remarks: "")
            column(name: "DOCUMENT_LINE_IDS", type: "BIGINT", remarks: "")
            column(name: "CURRENCY", type: "VARCHAR(30)", remarks: "")
            column(name: "BUDGET_ITEM_ID", type: "BIGINT", remarks: "")
            column(name: "RESPONSIBILITY_CENTER_ID", type: "BIGINT", remarks: "")
            column(name: "EXCHANGE_RATE_TYPE", type: "VARCHAR(30)", remarks: "")
            column(name: "EXCHANGE_RATE_QUOTATION", type: "VARCHAR(30)", remarks: "")
            column(name: "EXCHANGE_RATE", type: "NUMBER", remarks: "")
            column(name: "AMOUNT", type: "NUMBER", remarks: "")
            column(name: "FUNCTIONAL_AMOUNT", type: "NUMBER", remarks: "")
            column(name: "QUANTITY", type: "NUMBER", remarks: "")
            column(name: "UOM", type: "VARCHAR(30)", remarks: "")
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "")
            column(name: "OPERATION_UNIT_ID", type: "BIGINT", remarks: "")
            column(name: "UNIT_ID", type: "BIGINT", remarks: "")
            column(name: "POSITION_ID", type: "BIGINT", remarks: "")
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "")
            column(name: "DIMENSION1_ID", type: "BIGINT", remarks: "")
            column(name: "DIMENSION2_ID", type: "BIGINT", remarks: "")
            column(name: "DIMENSION3_ID", type: "BIGINT", remarks: "")
            column(name: "DIMENSION4_ID", type: "BIGINT", remarks: "")
            column(name: "DIMENSION5_ID", type: "BIGINT", remarks: "")
            column(name: "DIMENSION6_ID", type: "BIGINT", remarks: "")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BGT_BUDGET_RESERVE_TEMP", indexName: "BGT_BUDGET_RESERVES_TEMP_N1") {
            column(name: "RESPONSIBILITY_CENTER_ID")
            column(name: "BUDGET_ITEM_ID")
            column(name: "RESERVE_COMPANY_ID")
        }
        createIndex(tableName: "BGT_BUDGET_RESERVE_TEMP", indexName: "BGT_BUDGET_RESERVES_TEMP_N2") {
            column(name: "DOCUMENT_ID")
            column(name: "DOCUMENT_LINE_ID")
            column(name: "BUSINESS_TYPE")
        }
        createIndex(tableName: "BGT_BUDGET_RESERVE_TEMP", indexName: "BGT_BUDGET_RESERVES_TEMP_N3") {
            column(name: "RELEASE_ID")
        }
        createIndex(tableName: "BGT_BUDGET_RESERVE_TEMP", indexName: "BGT_BUDGET_RESERVES_TEMP_N5") {
            column(name: "COMPANY_ID")
            column(name: "RESPONSIBILITY_CENTER_ID")
            column(name: "BGT_ORG_ID")
            column(name: "BUDGET_ITEM_ID")
            column(name: "PERIOD_NAME")
            column(name: "STATUS")
        }
        createIndex(tableName: "BGT_BUDGET_RESERVE_TEMP", indexName: "BGT_BUDGET_RESERVES_TEMP_N6") {
            column(name: "STATUS")
            column(name: "DOCUMENT_LINE_ID")
            column(name: "BUSINESS_TYPE")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_BUDGET_RESERVE_TMP") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_BUDGET_RESERVE_TMP_S', startValue: "10001")
        }

        createTable(tableName: "BGT_BUDGET_RESERVE_TMP", remarks: "预算保留临时表") {

            column(name: "BUDGET_RESERVE_LINE_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "预算保留表ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_BUDGET_RESERVE_TMP_PK")
            }
            column(name: "BGT_ORG_ID", type: "BIGINT", remarks: "预算组织ID")
            column(name: "BGT_ENTITY_ID", type: "BIGINT", remarks: "预算实体ID")
            column(name: "BGT_CENTER_ID", type: "BIGINT", remarks: "预算中心ID")
            column(name: "PERIOD_YEAR", type: "BIGINT", remarks: "预算年度")
            column(name: "PERIOD_QUARTER", type: "BIGINT", remarks: "季度")
            column(name: "PERIOD_NAME", type: "VARCHAR(30)", remarks: "期间")
            column(name: "BGT_PERIOD_NUM", type: "BIGINT", remarks: "预算期间数")
            column(name: "RELEASE_ID", type: "BIGINT", remarks: "预算释放ID")
            column(name: "BUSINESS_TYPE", type: "VARCHAR(30)", remarks: "业务类型")
            column(name: "RESERVE_FLAG", type: "VARCHAR(1)", remarks: "释放标志")
            column(name: "STATUS", type: "VARCHAR(30)", remarks: "状态")
            column(name: "MANUAL_FLAG", type: "VARCHAR(1)", remarks: "手工标志")
            column(name: "DOCUMENT_ID", type: "BIGINT", remarks: "费用申请单/报销单头ID")
            column(name: "DOCUMENT_LINE_ID", type: "BIGINT", remarks: "费用申请单/报销单分配行ID")
            column(name: "CURRENCY", type: "VARCHAR(30)", remarks: "币种")
            column(name: "BUDGET_ITEM_ID", type: "BIGINT", remarks: "预算项目ID")
            column(name: "EXCHANGE_RATE_TYPE", type: "VARCHAR(30)", remarks: "汇率类型")
            column(name: "EXCHANGE_RATE", type: "NUMBER", remarks: "汇率")
            column(name: "AMOUNT", type: "NUMBER", remarks: "金额")
            column(name: "FUNCTIONAL_AMOUNT", type: "NUMBER", remarks: "本位币金额")
            column(name: "QUANTITY", type: "NUMBER", remarks: "数量")
            column(name: "UOM", type: "VARCHAR(30)", remarks: "单位")
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司ID")
            column(name: "OPERATION_UNIT_ID", type: "BIGINT", remarks: "废弃_单据经营单位")
            column(name: "UNIT_ID", type: "BIGINT", remarks: "部门ID")
            column(name: "POSITION_ID", type: "BIGINT", remarks: "岗位ID")
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "员工ID")
            column(name: "SOURCE_TYPE", type: "VARCHAR(30)", remarks: "预算释放类型")
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
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BGT_BUDGET_RESERVE_TMP", indexName: "BGT_BUDGET_RESERVES_TMP_N1") {
            column(name: "DOCUMENT_ID")
            column(name: "DOCUMENT_LINE_ID")
            column(name: "BUSINESS_TYPE")
        }
        createIndex(tableName: "BGT_BUDGET_RESERVE_TMP", indexName: "BGT_BUDGET_RESERVES_TMP_N2") {
            column(name: "RELEASE_ID")
        }
        createIndex(tableName: "BGT_BUDGET_RESERVE_TMP", indexName: "BGT_BUDGET_RESERVES_TMP_N3") {
            column(name: "STATUS")
            column(name: "PERIOD_NAME")
            column(name: "BUDGET_ITEM_ID")
            column(name: "COMPANY_ID")
            column(name: "BGT_ORG_ID")
        }
        createIndex(tableName: "BGT_BUDGET_RESERVE_TMP", indexName: "BGT_BUDGET_RESERVES_TMP_N4") {
            column(name: "BGT_CENTER_ID")
            column(name: "BGT_ENTITY_ID")
            column(name: "BGT_ORG_ID")
        }
        createIndex(tableName: "BGT_BUDGET_RESERVE_TMP", indexName: "BGT_BUDGET_RESERVES_TMP_N5") {
            column(name: "STATUS")
            column(name: "DOCUMENT_LINE_ID")
            column(name: "BUSINESS_TYPE")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_BUDGET_RESERVE_EXTEND") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_BUDGET_RESERVE_EXTEND_S', startValue: "10001")
        }

        createTable(tableName: "BGT_BUDGET_RESERVE_EXTEND", remarks: "预算占用扩展表") {

            column(name: "BUDGET_RESERVE_LINE_ID", type: "BIGINT", remarks: "预算占用ID") { constraints(nullable: "false") }
            column(name: "AMOUNT", type: "NUMBER", remarks: "金额") { constraints(nullable: "false") }
            column(name: "CURRENCY_CODE", type: "VARCHAR(30)", remarks: "币种") { constraints(nullable: "false") }
            column(name: "EXCHANGE_RATE_TYPE", type: "VARCHAR(30)", remarks: "汇率类型")
            column(name: "EXCHANGE_RATE", type: "NUMBER", remarks: "汇率") { constraints(nullable: "false") }
            column(name: "EXCHANGE_DATE", type: "DATETIME", remarks: "兑换日期") { constraints(nullable: "false") }
            column(name: "EXCHANGE_PERIOD_NAME", type: "VARCHAR(30)", remarks: "兑换期间") {
                constraints(nullable: "false")
            }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addPrimaryKey(columnNames: "CURRENCY_CODE,BUDGET_RESERVE_LINE_ID", tableName: "BGT_BUDGET_RESERVE_EXTEND", constraintName: "BGT_BUDGET_RESERVE_EXTEND_PK")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_BUDGET_RESERVE_EXTEND_BAK") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_BUDGET_RESERVE_EXTEND_BAK_S', startValue: "10001")
        }

        createTable(tableName: "BGT_BUDGET_RESERVE_EXTEND_BAK", remarks: "预算占用扩展表_备份表") {

            column(name: "BUDGET_RESERVE_LINE_ID", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "AMOUNT", type: "NUMBER", remarks: "") { constraints(nullable: "false") }
            column(name: "CURRENCY_CODE", type: "VARCHAR(30)", remarks: "") { constraints(nullable: "false") }
            column(name: "EXCHANGE_RATE_TYPE", type: "VARCHAR(30)", remarks: "")
            column(name: "EXCHANGE_RATE", type: "NUMBER", remarks: "") { constraints(nullable: "false") }
            column(name: "EXCHANGE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "EXCHANGE_PERIOD_NAME", type: "VARCHAR(30)", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_BUDGET_STRC_AUTHORITY") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_BUDGET_STRC_AUTHORITY_S', startValue: "10001")
        }

        createTable(tableName: "BGT_BUDGET_STRC_AUTHORITY", remarks: "预算表授权") {

            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司ID") { constraints(nullable: "false") }
            column(name: "BUDGET_STRC_HEADER_ID", type: "BIGINT", remarks: "预算表头ID") { constraints(nullable: "false") }
            column(name: "USER_ID", type: "BIGINT", remarks: "用户ID") { constraints(nullable: "false") }
            column(name: "STRC_ENTERABLE", type: "VARCHAR(1)", remarks: "预算表维护")
            column(name: "BUDGET_ENTERABLE", type: "VARCHAR(1)", remarks: "预算编制")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addPrimaryKey(columnNames: "USER_ID,BUDGET_STRC_HEADER_ID", tableName: "BGT_BUDGET_STRC_AUTHORITY", constraintName: "BGT_BUDGET_STRC_AUTHORITY_PK")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_BUDGET_STRUCT_ASGN_CM_TMP") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_BUDGET_STRUCT_ASGN_CM_TMP_S', startValue: "10001")
        }

        createTable(tableName: "BGT_BUDGET_STRUCT_ASGN_CM_TMP", remarks: "预算表临时表") {

            column(name: "SESSION_ID", type: "BIGINT", remarks: "系统SESSION ID")
            column(name: "APPLICATION_CODE", type: "VARCHAR(30)", remarks: "应用编码")
            column(name: "BUDGET_STRUCTURE_ID", type: "BIGINT", remarks: "预算表id")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BGT_BUDGET_STRUCT_ASGN_CM_TMP", indexName: "BGT_BUDGET_STRUCT_ASGN_CM_N1") {
            column(name: "SESSION_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_CENTER") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_CENTER_S', startValue: "10001")
        }

        createTable(tableName: "BGT_CENTER", remarks: "") {

            column(name: "CENTER_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_CENTER_PK")
            }
            column(name: "BGT_ORG_ID", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "CENTER_CODE", type: "VARCHAR(30)", remarks: "") { constraints(nullable: "false") }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "")
            column(name: "CURRENCY_CODE", type: "VARCHAR(30)", remarks: "")
            column(name: "CENTER_TYPE", type: "VARCHAR(30)", remarks: "") { constraints(nullable: "false") }
            column(name: "SOURCE_TYPE_CODE", type: "VARCHAR(30)", remarks: "") { constraints(nullable: "false") }
            column(name: "SOURCE_ID", type: "BIGINT", remarks: "")
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BGT_CENTER", indexName: "BGT_CENTERS_N1") {
            column(name: "DESCRIPTION")
        }

        addUniqueConstraint(columnNames: "SOURCE_TYPE_CODE,BGT_ORG_ID,CENTER_CODE", tableName: "BGT_CENTER", constraintName: "BGT_CENTERS_U1")
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_CENTER_TL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_CENTER_TL_S', startValue: "10001")
        }

        createTable(tableName: "BGT_CENTER_TL", remarks: "多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "CENTER_ID", type: "BIGINT", remarks: "") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_CENTER_HIERARCHY") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_CENTER_HIERARCHY_S', startValue: "10001")
        }

        createTable(tableName: "BGT_CENTER_HIERARCHY", remarks: "预算中心层次表") {

            column(name: "HIERARCHY_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_CENTER_HIERARCHY_PK")
            }
            column(name: "PARENT_CENTER_ID", type: "BIGINT", remarks: "父预算中心ID") { constraints(nullable: "false") }
            column(name: "CENTER_ID", type: "BIGINT", remarks: "子预算中心ID") { constraints(nullable: "false") }
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "CENTER_ID,PARENT_CENTER_ID", tableName: "BGT_CENTER_HIERARCHY", constraintName: "BGT_CENTER_HIERARCHY_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_CENTER_REF_BGT_ENTITY") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_CENTER_REF_BGT_ENTITY_S', startValue: "10001")
        }

        createTable(tableName: "BGT_CENTER_REF_BGT_ENTITY", remarks: "预算中心关联预算主体") {

            column(name: "REF_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "pk") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_CENTER_REF_BGT_ENTITY_PK")
            }
            column(name: "CENTER_ID", type: "BIGINT", remarks: "预算中心ID") { constraints(nullable: "false") }
            column(name: "ENTITY_ID", type: "BIGINT", remarks: "预算主体ID") { constraints(nullable: "false") }
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "ENTITY_ID,CENTER_ID", tableName: "BGT_CENTER_REF_BGT_ENTITY", constraintName: "BGT_CENTER_REF_BGT_ENTITY_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_CHANGE_REQ_TYPE") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_CHANGE_REQ_TYPE_S', startValue: "10001")
        }

        createTable(tableName: "BGT_CHANGE_REQ_TYPE", remarks: "预算改变申请单类型表") {

            column(name: "BGT_CHANGE_REQ_TYPE_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "预算改变申请单类型id") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_CHANGE_REQ_TYPE_PK")
            }
            column(name: "BGT_CHANGE_REQ_TYPE_CODE", type: "VARCHAR(30)", remarks: "预算改变申请单类型编码") {
                constraints(nullable: "false")
            }
            column(name: "DESCRIPTION", type: "VARCHAR(1000)", remarks: "描述") { constraints(nullable: "false") }
            column(name: "CROSS_ORG_FLAG", type: "VARCHAR(1)", remarks: "组织标识")
            column(name: "PERCENTAGE_CHANGE_FLAG", type: "VARCHAR(1)", remarks: "改变标志")
            column(name: "AUTO_APPROVE_FLAG", type: "VARCHAR(1)", remarks: "自审批") { constraints(nullable: "false") }
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用") { constraints(nullable: "false") }
            column(name: "CHANNEL_FLAG", type: "VARCHAR(1)", remarks: "渠道标识") { constraints(nullable: "false") }
            column(name: "PRODUCT_FLAG", type: "VARCHAR(1)", remarks: "产品标志") { constraints(nullable: "false") }
            column(name: "PROJECT_FLAG", type: "VARCHAR(1)", remarks: "更改标识") { constraints(nullable: "false") }
            column(name: "BGT_MAN_FLAG", type: "VARCHAR(1)", remarks: "主键标识") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期") { constraints(nullable: "false") }
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司id") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "COMPANY_ID,BGT_CHANGE_REQ_TYPE_CODE", tableName: "BGT_CHANGE_REQ_TYPE", constraintName: "BGT_CHANGE_REQ_TYPES_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_CHECK_LOG") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_CHECK_LOG_S', startValue: "10001")
        }

        createTable(tableName: "BGT_CHECK_LOG", remarks: "预算检查日志表") {

            column(name: "BGT_ORG_ID", type: "BIGINT", remarks: "预算组织") { constraints(nullable: "false") }
            column(name: "BGT_ENTITY_ID", type: "BIGINT", remarks: "预算实体")
            column(name: "BGT_CENTER_ID", type: "BIGINT", remarks: "预算中心")
            column(name: "DOCUMENT_COMPANY_ID", type: "BIGINT", remarks: "单据公司") { constraints(nullable: "false") }
            column(name: "DOCUMENT_OPERATION_UNIT_ID", type: "BIGINT", remarks: "单据经营单位")
            column(name: "DOCUMENT_TYPE", type: "VARCHAR(30)", remarks: "单据类别") { constraints(nullable: "false") }
            column(name: "DOCUMENT_ID", type: "BIGINT", remarks: "单据头ID") { constraints(nullable: "false") }
            column(name: "DOCUMENT_LINE_ID", type: "BIGINT", remarks: "单据行ID")
            column(name: "DOCUMENT_DIST_ID", type: "BIGINT", remarks: "单据分配行ID")
            column(name: "BUDGET_RESERVE_LINE_ID", type: "BIGINT", remarks: "预算占用行ID") {
                constraints(nullable: "false")
            }
            column(name: "PERIOD_NAME", type: "VARCHAR(30)", remarks: "期间")
            column(name: "CONTROL_STRATEGY_ID", type: "BIGINT", remarks: "预算控制策略ID") { constraints(nullable: "false") }
            column(name: "BUDGET_CONTROL_RULE_ID", type: "BIGINT", remarks: "预算控制规则ID") {
                constraints(nullable: "false")
            }
            column(name: "STRUCTURE_ID", type: "BIGINT", remarks: "预算表") { constraints(nullable: "false") }
            column(name: "SCENARIO_ID", type: "BIGINT", remarks: "预算场景") { constraints(nullable: "false") }
            column(name: "VERSION_ID", type: "BIGINT", remarks: "预算版本") { constraints(nullable: "false") }
            column(name: "BUDGET_ITEM_ID", type: "BIGINT", remarks: "预算项目")
            column(name: "RESPONSIBILITY_CENTER_ID", type: "BIGINT", remarks: "责任中心")
            column(name: "CURRENCY_CODE", type: "VARCHAR(30)", remarks: "币种")
            column(name: "AMOUNT", type: "NUMBER", remarks: "金额")
            column(name: "BGT_BUDGET_AMOUNT", type: "NUMBER", remarks: "预算余额金额")
            column(name: "BGT_REVERSE_AMOUNT", type: "NUMBER", remarks: "预算占用金额")
            column(name: "BGT_REVERSE_AMOUNT_R", type: "NUMBER", remarks: "预算占用金额_申请")
            column(name: "BGT_REVERSE_AMOUNT_U", type: "NUMBER", remarks: "预算占用金额_占用")
            column(name: "BGT_BUDGET_QUANTITY", type: "NUMBER", remarks: "预算余额数量")
            column(name: "BGT_RESERVE_QUANTITY", type: "NUMBER", remarks: "预算占用数量")
            column(name: "AVAILABLE_AMOUNT", type: "NUMBER", remarks: "可用金额")
            column(name: "AVAILABLE_QUANTITY", type: "NUMBER", remarks: "可用数量")
            column(name: "UOM", type: "VARCHAR(30)", remarks: "计量单位")
            column(name: "BUDGET_FORMULA", type: "VARCHAR(2000)", remarks: "预算计算公式")
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司")
            column(name: "OPERATION_UNIT_ID", type: "BIGINT", remarks: "经营单位")
            column(name: "UNIT_ID", type: "BIGINT", remarks: "部门")
            column(name: "UNIT_GROUP_ID", type: "BIGINT", remarks: "部门组")
            column(name: "POSITION_ID", type: "BIGINT", remarks: "岗位")
            column(name: "POSITION_GROUP_ID", type: "BIGINT", remarks: "岗位组")
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "员工")
            column(name: "EMPLOYEE_GROUP_ID", type: "BIGINT", remarks: "员工组")
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
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BGT_CHECK_LOG", indexName: "BGT_CHECK_LOGS_N1") {
            column(name: "DOCUMENT_LINE_ID")
            column(name: "DOCUMENT_TYPE")
            column(name: "DOCUMENT_ID")
            column(name: "DOCUMENT_DIST_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_CHECK_SCHEDULE_LOG") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_CHECK_SCHEDULE_LOG_S', startValue: "10001")
        }

        createTable(tableName: "BGT_CHECK_SCHEDULE_LOG", remarks: "预算执行率日志表") {

            column(name: "BGT_ORG_ID", type: "BIGINT", remarks: "预算组织") { constraints(nullable: "false") }
            column(name: "BGT_ENTITY_ID", type: "BIGINT", remarks: "预算实体")
            column(name: "BGT_CENTER_ID", type: "BIGINT", remarks: "预算中心")
            column(name: "DOCUMENT_COMPANY_ID", type: "BIGINT", remarks: "单据公司") { constraints(nullable: "false") }
            column(name: "DOCUMENT_OPERATION_UNIT_ID", type: "BIGINT", remarks: "单据经营单位")
            column(name: "DOCUMENT_TYPE", type: "VARCHAR(30)", remarks: "单据类别") { constraints(nullable: "false") }
            column(name: "DOCUMENT_ID", type: "BIGINT", remarks: "单据头ID") { constraints(nullable: "false") }
            column(name: "DOCUMENT_LINE_ID", type: "BIGINT", remarks: "单据行ID")
            column(name: "DOCUMENT_DIST_ID", type: "BIGINT", remarks: "单据分配行ID")
            column(name: "BUDGET_RESERVE_LINE_ID", type: "BIGINT", remarks: "预算占用行ID") {
                constraints(nullable: "false")
            }
            column(name: "PERIOD_NAME", type: "VARCHAR(30)", remarks: "期间")
            column(name: "CONTROL_STRATEGY_ID", type: "BIGINT", remarks: "预算控制策略ID") { constraints(nullable: "false") }
            column(name: "BUDGET_CONTROL_RULE_ID", type: "BIGINT", remarks: "预算控制规则ID") {
                constraints(nullable: "false")
            }
            column(name: "STRUCTURE_ID", type: "BIGINT", remarks: "预算表") { constraints(nullable: "false") }
            column(name: "SCENARIO_ID", type: "BIGINT", remarks: "预算场景") { constraints(nullable: "false") }
            column(name: "VERSION_ID", type: "BIGINT", remarks: "预算版本") { constraints(nullable: "false") }
            column(name: "BUDGET_ITEM_ID", type: "BIGINT", remarks: "预算项目")
            column(name: "RESPONSIBILITY_CENTER_ID", type: "BIGINT", remarks: "责任中心")
            column(name: "CURRENCY_CODE", type: "VARCHAR(30)", remarks: "币种")
            column(name: "AMOUNT", type: "NUMBER", remarks: "金额")
            column(name: "BGT_BUDGET_AMOUNT", type: "NUMBER", remarks: "预算余额金额")
            column(name: "BGT_REVERSE_AMOUNT", type: "NUMBER", remarks: "预算占用金额")
            column(name: "BGT_REVERSE_AMOUNT_R", type: "NUMBER", remarks: "预算占用金额_申请")
            column(name: "BGT_REVERSE_AMOUNT_U", type: "NUMBER", remarks: "预算占用金额_占用")
            column(name: "BGT_BUDGET_QUANTITY", type: "NUMBER", remarks: "预算余额数量")
            column(name: "BGT_RESERVE_QUANTITY", type: "NUMBER", remarks: "预算占用数量")
            column(name: "AVAILABLE_AMOUNT", type: "NUMBER", remarks: "可用金额")
            column(name: "AVAILABLE_QUANTITY", type: "NUMBER", remarks: "可用数量")
            column(name: "UOM", type: "VARCHAR(30)", remarks: "计量单位")
            column(name: "BUDGET_FORMULA", type: "VARCHAR(2000)", remarks: "预算计算公式")
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司")
            column(name: "OPERATION_UNIT_ID", type: "BIGINT", remarks: "经营单位")
            column(name: "UNIT_ID", type: "BIGINT", remarks: "部门")
            column(name: "UNIT_GROUP_ID", type: "BIGINT", remarks: "部门组")
            column(name: "POSITION_ID", type: "BIGINT", remarks: "岗位")
            column(name: "POSITION_GROUP_ID", type: "BIGINT", remarks: "岗位组")
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "员工")
            column(name: "EMPLOYEE_GROUP_ID", type: "BIGINT", remarks: "员工组")
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
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BGT_CHECK_SCHEDULE_LOG", indexName: "BGT_CHECK_SCHEDULE_LOGS_N1") {
            column(name: "DOCUMENT_TYPE")
            column(name: "DOCUMENT_DIST_ID")
            column(name: "DOCUMENT_LINE_ID")
            column(name: "DOCUMENT_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_COMPANY_PERIOD_SET") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_COMPANY_PERIOD_SET_S', startValue: "10001")
        }

        createTable(tableName: "BGT_COMPANY_PERIOD_SET", remarks: "预算期间公司分配表") {

            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司ID") { constraints(nullable: "false") }
            column(name: "BGT_PERIOD_SET_CODE", type: "VARCHAR(30)", remarks: "预算期间") { constraints(nullable: "false") }
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "BGT_PERIOD_SET_CODE,COMPANY_ID", tableName: "BGT_COMPANY_PERIOD_SET", constraintName: "BGT_COMPANY_PERIOD_SETS_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_CONTROL_RULE") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_CONTROL_RULE_S', startValue: "10001")
        }

        createTable(tableName: "BGT_CONTROL_RULE", remarks: "预算控制规则定义表") {

            column(name: "BGT_ORG_ID", type: "BIGINT", remarks: "预算组织ID") { constraints(nullable: "false") }
            column(name: "CONTROL_RULE_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "规则ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_CONTROL_RULE_PK")
            }
            column(name: "CONTROL_RULE_CODE", type: "VARCHAR(30)", remarks: "规则代码") { constraints(nullable: "false") }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述ID") { constraints(nullable: "false") }
            column(name: "PRIORITY", type: "NUMBER", remarks: "优先级") { constraints(nullable: "false") }
            column(name: "BUDGET_STRATEGY_GROUP_ID", type: "BIGINT", remarks: "预算控制策略表") {
                constraints(nullable: "false")
            }
            column(name: "START_DATE", type: "DATETIME", remarks: "开始日期")
            column(name: "END_DATE", type: "DATETIME", remarks: "结束日期")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "CONTROL_RULE_CODE,BGT_ORG_ID", tableName: "BGT_CONTROL_RULE", constraintName: "BGT_CONTROL_RULES_U1")
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_CONTROL_RULE_TL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_CONTROL_RULE_TL_S', startValue: "10001")
        }

        createTable(tableName: "BGT_CONTROL_RULE_TL", remarks: "预算控制规则定义表多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "CONTROL_RULE_ID", type: "BIGINT", remarks: "规则ID") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述ID")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_CONTROL_RULE_DETAIL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_CONTROL_RULE_DETAIL_S', startValue: "10001")
        }

        createTable(tableName: "BGT_CONTROL_RULE_DETAIL", remarks: "预算控制规则明细表") {

            column(name: "CONTROL_RULE_ID", type: "BIGINT", remarks: "规则ID") { constraints(nullable: "false") }
            column(name: "CONTROL_RULE_DETAIL_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "规则明细ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_CONTROL_RULE_DETAIL_PK")
            }
            column(name: "RULE_PARAMETER_TYPE", type: "VARCHAR(30)", remarks: "规则参数类型") {
                constraints(nullable: "false")
            }
            column(name: "RULE_PARAMETER", type: "VARCHAR(30)", remarks: "规则参数") { constraints(nullable: "false") }
            column(name: "FILTRATE_METHOD", type: "VARCHAR(30)", remarks: "取值方式") { constraints(nullable: "false") }
            column(name: "SUMMARY_OR_DETAIL", type: "VARCHAR(30)", remarks: "取值范围") { constraints(nullable: "false") }
            column(name: "PARAMETER_LOWER_LIMIT", type: "VARCHAR(100)", remarks: "下限值")
            column(name: "PARAMETER_UPPER_LIMIT", type: "VARCHAR(100)", remarks: "上限制")
            column(name: "USER_EXIT_PROCEDURE", type: "VARCHAR(100)", remarks: "用户出口程序")
            column(name: "INVALID_DATE", type: "DATETIME", remarks: "失效日期")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BGT_CONTROL_RULE_DETAIL", indexName: "BGT_CONTROL_RULE_DETAILS_N1") {
            column(name: "CONTROL_RULE_DETAIL_ID")
            column(name: "CONTROL_RULE_ID")
        }
        createIndex(tableName: "BGT_CONTROL_RULE_DETAIL", indexName: "BGT_CONTROL_RULE_DETAILS_N2") {
            column(name: "RULE_PARAMETER")
            column(name: "CONTROL_RULE_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_CONTROL_STRATEGY") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_CONTROL_STRATEGY_S', startValue: "10001")
        }

        createTable(tableName: "BGT_CONTROL_STRATEGY", remarks: "预算控制策略明细表") {

            column(name: "CONTROL_STRATEGY_GROUP_ID", type: "BIGINT", remarks: "控制策略ID") {
                constraints(nullable: "false")
            }
            column(name: "CONTROL_STRATEGY_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "策略明细ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_CONTROL_STRATEGY_PK")
            }
            column(name: "CONTROL_STRATEGY_SEQUENCE", type: "BIGINT", remarks: "序号")
            column(name: "CONTROL_STRATEGY_CODE", type: "VARCHAR(30)", remarks: "策略代码") {
                constraints(nullable: "false")
            }
            column(name: "CONTROL_STRATEGY_DESC", type: "VARCHAR(2000)", remarks: "策略描述")
            column(name: "EXP_WF_EVENT", type: "VARCHAR(50)", remarks: "事件")
            column(name: "CONTROL_METHOD", type: "VARCHAR(30)", remarks: "控制方法")
            column(name: "MESSAGE_CODE", type: "VARCHAR(200)", remarks: "消息代码")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "CONTROL_STRATEGY_CODE,CONTROL_STRATEGY_GROUP_ID", tableName: "BGT_CONTROL_STRATEGY", constraintName: "BGT_CONTROL_STRATEGIES_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_CONTROL_STRATEGY_GROUP") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_CONTROL_STRATEGY_GROUP_S', startValue: "10001")
        }

        createTable(tableName: "BGT_CONTROL_STRATEGY_GROUP", remarks: "预算控制策略表") {

            column(name: "CONTROL_STRATEGY_GROUP_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_CONTROL_STRATEGY_GROUP_PK")
            }
            column(name: "BGT_ORG_ID", type: "BIGINT", remarks: "预算组织ID") { constraints(nullable: "false") }
            column(name: "CONTROL_STRATEGY_GROUP_CODE", type: "VARCHAR(30)", remarks: "预算策略组代码") {
                constraints(nullable: "false")
            }
            column(name: "CONTROL_STRATEGY_GROUP_DESC", type: "VARCHAR(2000)", remarks: "预算策略组描述")
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "BGT_ORG_ID,CONTROL_STRATEGY_GROUP_CODE", tableName: "BGT_CONTROL_STRATEGY_GROUP", constraintName: "BGT_CONTROL_STRATEGY_GROUPS_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_CTRL_STRATEGY_MP_COND") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_CTRL_STRATEGY_MP_COND_S', startValue: "10001")
        }

        createTable(tableName: "BGT_CTRL_STRATEGY_MP_COND", remarks: "预算控制策略决定机制表") {

            column(name: "CTRL_STRATEGY_MP_COND_ID", type: "BIGINT", remarks: "决定机制ID") {
                constraints(nullable: "false")
            }
            column(name: "CONTROL_STRATEGY_ID", type: "BIGINT",autoIncrement: true, startWith: "10001", remarks: "策略明细ID") { constraints(nullable: "false") }
            column(name: "TYPE", type: "VARCHAR(30)", remarks: "10:参数,20:函数") { constraints(nullable: "false") }
            column(name: "RANGE", type: "VARCHAR(30)", remarks: "范围")
            column(name: "OBJECT", type: "VARCHAR(30)", remarks: "对象")
            column(name: "PERIOD_STRATEGY", type: "VARCHAR(30)", remarks: "控制期段")
            column(name: "MANNER", type: "VARCHAR(30)", remarks: "方式")
            column(name: "OPERATOR", type: "VARCHAR(30)", remarks: "运算符")
            column(name: "VALUE", type: "NUMBER", remarks: "值")
            column(name: "AND_OR", type: "VARCHAR(30)", remarks: "And Or")
            column(name: "USER_EXIT_PROCEDURE", type: "VARCHAR(100)", remarks: "用户出口程序")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BGT_CTRL_STRATEGY_MP_COND", indexName: "BGT_CTRL_STRATEGY_MP_CONDS_N1") {
            column(name: "CONTROL_STRATEGY_ID")
            column(name: "CTRL_STRATEGY_MP_COND_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_ENTITY") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_ENTITY_S', startValue: "10001")
        }

        createTable(tableName: "BGT_ENTITY", remarks: "预算实体表") {

            column(name: "ENTITY_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_ENTITY_PK")
            }
            column(name: "BGT_ORG_ID", type: "BIGINT", remarks: "预算组织ID") { constraints(nullable: "false") }
            column(name: "ENTITY_CODE", type: "VARCHAR(30)", remarks: "预算实体代码") { constraints(nullable: "false") }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "预算实体描述ID")
            column(name: "CURRENCY_CODE", type: "VARCHAR(30)", remarks: "预算币种")
            column(name: "ENTITY_TYPE", type: "VARCHAR(30)", remarks: "实体类型（SYSCODE：BGT_ENTITY_TYPE）") {
                constraints(nullable: "false")
            }
            column(name: "SOURCE_TYPE_CODE", type: "VARCHAR(30)", remarks: "来源类型（SYSCODE：BGT_ORG_SOURCE_TYPE）") {
                constraints(nullable: "false")
            }
            column(name: "SOURCE_ID", type: "BIGINT", remarks: "来源明细")
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BGT_ENTITY", indexName: "BGT_ENTITIES_N1") {
            column(name: "DESCRIPTION")
        }

        addUniqueConstraint(columnNames: "SOURCE_TYPE_CODE,BGT_ORG_ID,ENTITY_CODE", tableName: "BGT_ENTITY", constraintName: "BGT_ENTITIES_U1")
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_ENTITY_TL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_ENTITY_TL_S', startValue: "10001")
        }

        createTable(tableName: "BGT_ENTITY_TL", remarks: "预算实体表多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "ENTITY_ID", type: "BIGINT", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "预算实体描述ID")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_ENTITY_HIERARCHY") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_ENTITY_HIERARCHY_S', startValue: "10001")
        }

        createTable(tableName: "BGT_ENTITY_HIERARCHY", remarks: "预算实体层次表") {

            column(name: "HIERARCHY_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_ENTITY_HIERARCHY_PK")
            }
            column(name: "PARENT_ENTITY_ID", type: "BIGINT", remarks: "预算实体ID") { constraints(nullable: "false") }
            column(name: "ENTITY_ID", type: "BIGINT", remarks: "子预算实体ID") { constraints(nullable: "false") }
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "PARENT_ENTITY_ID,ENTITY_ID", tableName: "BGT_ENTITY_HIERARCHY", constraintName: "BGT_ENTITY_HIERARCHY_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_EXECUTION_RATE_TMP") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_EXECUTION_RATE_TMP_S', startValue: "10001")
        }

        createTable(tableName: "BGT_EXECUTION_RATE_TMP", remarks: "") {

            column(name: "SESSION_ID", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "BGT_ORG_ID", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "RESPONSIBILITY_CENTER_ID", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "BUDGET_ITEM_ID", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "PERIOD_YEAR", type: "BIGINT", remarks: "")
            column(name: "PERIOD_NAME", type: "VARCHAR(30)", remarks: "")
            column(name: "BGT_PERIOD_FUNCTIONAL_AMOUNT", type: "NUMBER", remarks: "")
            column(name: "RSV_PERIOD_FUNCTIONAL_AMOUNT", type: "NUMBER", remarks: "")
            column(name: "USD_PERIOD_FUNCTIONAL_AMOUNT", type: "NUMBER", remarks: "")
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "OPERATION_UNIT_ID", type: "BIGINT", remarks: "")
            column(name: "UNIT_ID", type: "BIGINT", remarks: "")
            column(name: "POSITION_ID", type: "BIGINT", remarks: "")
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "")
            column(name: "DIMENSION1_ID", type: "BIGINT", remarks: "")
            column(name: "DIMENSION2_ID", type: "BIGINT", remarks: "")
            column(name: "DIMENSION3_ID", type: "BIGINT", remarks: "")
            column(name: "DIMENSION4_ID", type: "BIGINT", remarks: "")
            column(name: "DIMENSION5_ID", type: "BIGINT", remarks: "")
            column(name: "DIMENSION6_ID", type: "BIGINT", remarks: "")
            column(name: "DIMENSION7_ID", type: "BIGINT", remarks: "")
            column(name: "DIMENSION8_ID", type: "BIGINT", remarks: "")
            column(name: "DIMENSION9_ID", type: "BIGINT", remarks: "")
            column(name: "DIMENSION10_ID", type: "BIGINT", remarks: "")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_IMPORT_TYPE") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_IMPORT_TYPE_S', startValue: "10001")
        }

        createTable(tableName: "BGT_IMPORT_TYPE", remarks: "预算导入单据类型表") {

            column(name: "BGT_IMPORT_TYPE_ID", type: "BIGINT", remarks: "预算导入单据类型id") { constraints(nullable: "false") }
            column(name: "BGT_IMPORT_TYPE_CODE", type: "VARCHAR(30)", remarks: "预算导入单据类型编码") {
                constraints(nullable: "false")
            }
            column(name: "DESCRIPTION", type: "VARCHAR(2000)", remarks: "单据类型描述") { constraints(nullable: "false") }
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "所属公司") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期") { constraints(nullable: "false") }
            column(name: "ENABLE_FLAG", type: "VARCHAR(1)", remarks: "标志") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "BGT_IMPORT_TYPE_ID", tableName: "BGT_IMPORT_TYPE", constraintName: "BGT_IMPORT_TYPE_U1")
        addUniqueConstraint(columnNames: "COMPANY_ID,BGT_IMPORT_TYPE_CODE", tableName: "BGT_IMPORT_TYPE", constraintName: "BGT_IMPORT_TYPE_U2")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_ITEM_ASSIGNED_COMPANY_TMP") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_ITEM_ASSIGNED_COMPANY_TMP_S', startValue: "10001")
        }

        createTable(tableName: "BGT_ITEM_ASSIGNED_COMPANY_TMP", remarks: "预算项目分配公司表") {

            column(name: "SESSION_ID", type: "BIGINT", remarks: "系统SESSION ID")
            column(name: "APPLICATION_CODE", type: "VARCHAR(30)", remarks: "可用code")
            column(name: "BUDGET_ITEM_ID", type: "BIGINT", remarks: "预算项目id")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BGT_ITEM_ASSIGNED_COMPANY_TMP", indexName: "BGT_ITEM_ASSIGNED_COM_TMP_N1") {
            column(name: "SESSION_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_JE_DIMENSION_LAYOUT") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_JE_DIMENSION_LAYOUT_S', startValue: "10001")
        }

        createTable(tableName: "BGT_JE_DIMENSION_LAYOUT", remarks: "预算日记账维度分布表") {

            column(name: "BUDGET_JOURNAL_HEADER_ID", type: "BIGINT", remarks: "预算日记账头ID") {
                constraints(nullable: "false")
            }
            column(name: "LAYOUT_POSITION", type: "VARCHAR(30)", remarks: "布局位置") { constraints(nullable: "false") }
            column(name: "LAYOUT_PRIORITY", type: "NUMBER", remarks: "布局顺序") { constraints(nullable: "false") }
            column(name: "DIMENSION_ID", type: "BIGINT", remarks: "维度ID") { constraints(nullable: "false") }
            column(name: "DEFAULT_DIM_VALUE_ID", type: "BIGINT", remarks: "默认维值ID")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addPrimaryKey(columnNames: "DIMENSION_ID,BUDGET_JOURNAL_HEADER_ID", tableName: "BGT_JE_DIMENSION_LAYOUT", constraintName: "BGT_JE_DIMENSION_LAYOUTS_PK")
        addUniqueConstraint(columnNames: "LAYOUT_POSITION,LAYOUT_PRIORITY,BUDGET_JOURNAL_HEADER_ID", tableName: "BGT_JE_DIMENSION_LAYOUT", constraintName: "BGT_JE_DIMENSION_LAYOUTS_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_JOURNAL_BALANCE") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_JOURNAL_BALANCE_S', startValue: "10001")
        }

        createTable(tableName: "BGT_JOURNAL_BALANCE", remarks: "预算余额表") {

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
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "BALANCE_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_JOURNAL_BALANCE_PK")
            }
            column(name: "BGT_ORG_ID", type: "BIGINT", remarks: "预算组织ID") { constraints(nullable: "false") }
            column(name: "BGT_ENTITY_ID", type: "BIGINT", remarks: "预算实体ID")
            column(name: "BGT_CENTER_ID", type: "BIGINT", remarks: "预算中心ID")
            column(name: "STRUCTURE_ID", type: "BIGINT", remarks: "预算表ID") { constraints(nullable: "false") }
            column(name: "SCENARIO_ID", type: "BIGINT", remarks: "预算场景ID") { constraints(nullable: "false") }
            column(name: "VERSION_ID", type: "BIGINT", remarks: "预算版本ID") { constraints(nullable: "false") }
            column(name: "BUDGET_ITEM_ID", type: "BIGINT", remarks: "预算项目ID") { constraints(nullable: "false") }
            column(name: "PERIOD_YEAR", type: "NUMBER", remarks: "期间年份")
            column(name: "PERIOD_QUARTER", type: "NUMBER", remarks: "期间季度")
            column(name: "PERIOD_NAME", type: "VARCHAR(30)", remarks: "期间名称")
            column(name: "BGT_PERIOD_NUM", type: "BIGINT", remarks: "期间数")
            column(name: "CURRENCY_CODE", type: "VARCHAR(30)", remarks: "币种") { constraints(nullable: "false") }
            column(name: "PERIOD_AMOUNT", type: "NUMBER", remarks: "期间金额")
            column(name: "PERIOD_FUNCTIONAL_AMOUNT", type: "NUMBER", remarks: "期间本币金额")
            column(name: "PERIOD_QUANTITY", type: "NUMBER", remarks: "期间数量")
            column(name: "QUARTER_AMOUNT", type: "NUMBER", remarks: "季度金额")
            column(name: "QUARTER_FUNCTIONAL_AMOUNT", type: "NUMBER", remarks: "季度本币金额")
            column(name: "QUARTER_QUANTITY", type: "NUMBER", remarks: "季度数量")
            column(name: "YEAR_AMOUNT", type: "NUMBER", remarks: "年度金额")
            column(name: "YEAR_FUNCTIONAL_AMOUNT", type: "NUMBER", remarks: "年度本币金额")
            column(name: "YEAR_QUANTITY", type: "NUMBER", remarks: "年度数量")
            column(name: "UOM", type: "VARCHAR(30)", remarks: "单位")
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司ID")
            column(name: "OPERATION_UNIT_ID", type: "BIGINT", remarks: "经营单位ID")
            column(name: "UNIT_ID", type: "BIGINT", remarks: "部门ID")
            column(name: "UNIT_GROUP_ID", type: "BIGINT", remarks: "部门组ID")
            column(name: "POSITION_ID", type: "BIGINT", remarks: "岗位ID")
            column(name: "POSITION_GROUP_ID", type: "BIGINT", remarks: "岗位组ID")
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "员工ID")
            column(name: "EMPLOYEE_GROUP_ID", type: "BIGINT", remarks: "员工组ID")
            column(name: "DIMENSION1_ID", type: "BIGINT", remarks: "维度1")
            column(name: "DIMENSION2_ID", type: "BIGINT", remarks: "维度2")
            column(name: "DIMENSION3_ID", type: "BIGINT", remarks: "维度3")
            column(name: "DIMENSION4_ID", type: "BIGINT", remarks: "维度4")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BGT_JOURNAL_BALANCE", indexName: "BGT_JOURNAL_BALANCES_N1") {
            column(name: "BGT_ENTITY_ID")
            column(name: "BGT_CENTER_ID")
            column(name: "BGT_ORG_ID")
        }
        createIndex(tableName: "BGT_JOURNAL_BALANCE", indexName: "BGT_JOURNAL_BALANCES_N2") {
            column(name: "STRUCTURE_ID")
            column(name: "COMPANY_ID")
            column(name: "BUDGET_ITEM_ID")
        }
        createIndex(tableName: "BGT_JOURNAL_BALANCE", indexName: "BGT_JOURNAL_BALANCES_N3") {
            column(name: "COMPANY_ID")
            column(name: "STRUCTURE_ID")
            column(name: "VERSION_ID")
            column(name: "SCENARIO_ID")
        }
        createIndex(tableName: "BGT_JOURNAL_BALANCE", indexName: "BGT_JOURNAL_BALANCES_N4") {
            column(name: "STRUCTURE_ID")
            column(name: "BGT_ORG_ID")
            column(name: "VERSION_ID")
            column(name: "COMPANY_ID")
            column(name: "BUDGET_ITEM_ID")
            column(name: "PERIOD_YEAR")
            column(name: "PERIOD_QUARTER")
            column(name: "PERIOD_NAME")
            column(name: "BGT_PERIOD_NUM")
            column(name: "CURRENCY_CODE")
            column(name: "SCENARIO_ID")
        }
        createIndex(tableName: "BGT_JOURNAL_BALANCE", indexName: "BGT_JOURNAL_BALANCES_N5") {
            column(name: "BGT_CENTER_ID")
            column(name: "BUDGET_ITEM_ID")
            column(name: "PERIOD_YEAR")
            column(name: "BGT_ENTITY_ID")
        }
        createIndex(tableName: "BGT_JOURNAL_BALANCE", indexName: "BGT_JOURNAL_BALANCES_N6") {
            column(name: "PERIOD_YEAR")
            column(name: "EMPLOYEE_ID")
            column(name: "VERSION_ID")
            column(name: "STRUCTURE_ID")
        }
        createIndex(tableName: "BGT_JOURNAL_BALANCE", indexName: "BGT_JOURNAL_BALANCES_N7") {
            column(name: "BGT_CENTER_ID")
            column(name: "BGT_ENTITY_ID")
            column(name: "STRUCTURE_ID")
            column(name: "BUDGET_ITEM_ID")
        }
        createIndex(tableName: "BGT_JOURNAL_BALANCE", indexName: "BGT_JOURNAL_BALANCES_N8") {
            column(name: "BUDGET_ITEM_ID")
            column(name: "VERSION_ID")
            column(name: "BGT_ENTITY_ID")
            column(name: "BGT_CENTER_ID")
            column(name: "SCENARIO_ID")
        }
        createIndex(tableName: "BGT_JOURNAL_BALANCE", indexName: "BGT_JOURNAL_BALANCES_N9") {
            column(name: "BGT_CENTER_ID")
            column(name: "BGT_ENTITY_ID")
            column(name: "STRUCTURE_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_JOURNAL_BALANCE_SUM_TEMP") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_JOURNAL_BALANCE_SUM_TEMP_S', startValue: "10001")
        }

        createTable(tableName: "BGT_JOURNAL_BALANCE_SUM_TEMP", remarks: "预算余额总额临时表") {

            column(name: "BGT_ORG_ID", type: "BIGINT", remarks: "预算组织id") { constraints(nullable: "false") }
            column(name: "BUDGET_STRUCTURE_ID", type: "BIGINT", remarks: "预算表id")
            column(name: "SCENARIO_ID", type: "BIGINT", remarks: "预算场景id") { constraints(nullable: "false") }
            column(name: "VERSION_ID", type: "BIGINT", remarks: "版本ID") { constraints(nullable: "false") }
            column(name: "RESPONSIBILITY_CENTER_ID", type: "BIGINT", remarks: "责任中心id")
            column(name: "BUDGET_ITEM_ID", type: "BIGINT", remarks: "预算项目id")
            column(name: "PERIOD_STRATEGY", type: "VARCHAR(30)", remarks: "编制期段")
            column(name: "PERIOD_YEAR", type: "BIGINT", remarks: "预算年度")
            column(name: "PERIOD_QUARTER", type: "BIGINT", remarks: "季度")
            column(name: "BGT_PERIOD_NUM", type: "BIGINT", remarks: "预算期间")
            column(name: "PERIOD_AMOUNT", type: "NUMBER", remarks: "期间总额")
            column(name: "PERIOD_FUNCTIONAL_AMOUNT", type: "NUMBER", remarks: "本位币期间金额")
            column(name: "PERIOD_QUANTITY", type: "NUMBER", remarks: "期间数量")
            column(name: "QUARTER_AMOUNT", type: "NUMBER", remarks: "季度总额")
            column(name: "QUARTER_FUNCTIONAL_AMOUNT", type: "NUMBER", remarks: "本位币季度金额")
            column(name: "QUARTER_QUANTITY", type: "NUMBER", remarks: "季度数量")
            column(name: "YEAR_AMOUNT", type: "NUMBER", remarks: "年度金额")
            column(name: "YEAR_FUNCTIONAL_AMOUNT", type: "NUMBER", remarks: "本位币年度金额")
            column(name: "YEAR_QUANTITY", type: "NUMBER", remarks: "年度数量")
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司ID")
            column(name: "OPERATION_UNIT_ID", type: "BIGINT", remarks: "经营单位id")
            column(name: "UNIT_ID", type: "BIGINT", remarks: "部门id")
            column(name: "UNIT_GROUP_ID", type: "BIGINT", remarks: "部门组ID")
            column(name: "POSITION_ID", type: "BIGINT", remarks: "岗位id")
            column(name: "POSITION_GROUP_ID", type: "BIGINT", remarks: "岗位组ID")
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "员工id")
            column(name: "EXPENSE_USER_GROUP_ID", type: "BIGINT", remarks: "")
            column(name: "DIMENSION1_ID", type: "BIGINT", remarks: "维值1id")
            column(name: "DIMENSION2_ID", type: "BIGINT", remarks: "维值2id")
            column(name: "DIMENSION3_ID", type: "BIGINT", remarks: "维值3id")
            column(name: "DIMENSION4_ID", type: "BIGINT", remarks: "维值4id")
            column(name: "DIMENSION5_ID", type: "BIGINT", remarks: "维值5id")
            column(name: "DIMENSION6_ID", type: "BIGINT", remarks: "维值6id")
            column(name: "DIMENSION7_ID", type: "BIGINT", remarks: "维值7id")
            column(name: "DIMENSION8_ID", type: "BIGINT", remarks: "维值8id")
            column(name: "DIMENSION9_ID", type: "BIGINT", remarks: "维值9id")
            column(name: "DIMENSION10_ID", type: "BIGINT", remarks: "维值10id")
            column(name: "CACHE_FIELD_NAMES", type: "VARCHAR(1000)", remarks: "预算查询描述")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_JOURNAL_BALANCE_SUM_TMP") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_JOURNAL_BALANCE_SUM_TMP_S', startValue: "10001")
        }

        createTable(tableName: "BGT_JOURNAL_BALANCE_SUM_TMP", remarks: "预算余额临时表") {

            column(name: "BGT_ORG_ID", type: "BIGINT", remarks: "预算组织id") { constraints(nullable: "false") }
            column(name: "BUDGET_STRUCTURE_ID", type: "BIGINT", remarks: "预算表id")
            column(name: "SCENARIO_ID", type: "BIGINT", remarks: "预算场景id") { constraints(nullable: "false") }
            column(name: "VERSION_ID", type: "BIGINT", remarks: "版本ID") { constraints(nullable: "false") }
            column(name: "RESPONSIBILITY_CENTER_ID", type: "BIGINT", remarks: "责任中心id")
            column(name: "BUDGET_ITEM_ID", type: "BIGINT", remarks: "预算项目id")
            column(name: "PERIOD_STRATEGY", type: "VARCHAR(30)", remarks: "编制期段")
            column(name: "PERIOD_YEAR", type: "BIGINT", remarks: "预算年度")
            column(name: "PERIOD_QUARTER", type: "BIGINT", remarks: "季度")
            column(name: "BGT_PERIOD_NUM", type: "BIGINT", remarks: "预算期间")
            column(name: "PERIOD_AMOUNT", type: "NUMBER", remarks: "期间金额")
            column(name: "PERIOD_FUNCTIONAL_AMOUNT", type: "NUMBER", remarks: "本位币期间金额")
            column(name: "PERIOD_QUANTITY", type: "NUMBER", remarks: "期间数量")
            column(name: "QUARTER_AMOUNT", type: "NUMBER", remarks: "期间金额")
            column(name: "QUARTER_FUNCTIONAL_AMOUNT", type: "NUMBER", remarks: "本位币季度金额")
            column(name: "QUARTER_QUANTITY", type: "NUMBER", remarks: "季度数量")
            column(name: "YEAR_AMOUNT", type: "NUMBER", remarks: "年度总额")
            column(name: "YEAR_FUNCTIONAL_AMOUNT", type: "NUMBER", remarks: "本位币年度金额")
            column(name: "YEAR_QUANTITY", type: "NUMBER", remarks: "年度数量")
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司ID")
            column(name: "OPERATION_UNIT_ID", type: "BIGINT", remarks: "经营单位id")
            column(name: "UNIT_ID", type: "BIGINT", remarks: "部门id")
            column(name: "UNIT_GROUP_ID", type: "BIGINT", remarks: "部门组ID")
            column(name: "POSITION_ID", type: "BIGINT", remarks: "岗位id")
            column(name: "POSITION_GROUP_ID", type: "BIGINT", remarks: "岗位组ID")
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "员工id")
            column(name: "EXPENSE_USER_GROUP_ID", type: "BIGINT", remarks: "")
            column(name: "DIMENSION1_ID", type: "BIGINT", remarks: "维值1id")
            column(name: "DIMENSION2_ID", type: "BIGINT", remarks: "维值2id")
            column(name: "DIMENSION3_ID", type: "BIGINT", remarks: "维值3id")
            column(name: "DIMENSION4_ID", type: "BIGINT", remarks: "维值4id")
            column(name: "DIMENSION5_ID", type: "BIGINT", remarks: "维值5id")
            column(name: "DIMENSION6_ID", type: "BIGINT", remarks: "维值6id")
            column(name: "DIMENSION7_ID", type: "BIGINT", remarks: "维值7id")
            column(name: "DIMENSION8_ID", type: "BIGINT", remarks: "维值8id")
            column(name: "DIMENSION9_ID", type: "BIGINT", remarks: "维值9id")
            column(name: "DIMENSION10_ID", type: "BIGINT", remarks: "维值10id")
            column(name: "CACHE_FIELD_NAMES", type: "VARCHAR(1000)", remarks: "预算查询描述")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_JOURNAL_BALANCE_TMP") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_JOURNAL_BALANCE_TMP_S', startValue: "10001")
        }

        createTable(tableName: "BGT_JOURNAL_BALANCE_TMP", remarks: "预算余额临时表") {

            column(name: "PERIOD_AMOUNT", type: "NUMBER", remarks: "期间总额")
            column(name: "PERIOD_FUNCTIONAL_AMOUNT", type: "NUMBER", remarks: "本位币期间金额")
            column(name: "PERIOD_QUANTITY", type: "NUMBER", remarks: "期间数量")
            column(name: "QUARTER_AMOUNT", type: "NUMBER", remarks: "季度总额")
            column(name: "QUARTER_FUNCTIONAL_AMOUNT", type: "NUMBER", remarks: "本位币季度金额")
            column(name: "QUARTER_QUANTITY", type: "NUMBER", remarks: "季度数量")
            column(name: "UOM", type: "VARCHAR(30)", remarks: "单位")
            column(name: "YEAR_AMOUNT", type: "NUMBER", remarks: "年度总额")
            column(name: "YEAR_FUNCTIONAL_AMOUNT", type: "NUMBER", remarks: "本位币年度金额")
            column(name: "YEAR_QUANTITY", type: "NUMBER", remarks: "年度数量")
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司ID")
            column(name: "OPERATION_UNIT_ID", type: "BIGINT", remarks: "废弃_经营单位ID")
            column(name: "UNIT_ID", type: "BIGINT", remarks: "部门ID")
            column(name: "UNIT_GROUP_ID", type: "BIGINT", remarks: "部门组ID")
            column(name: "POSITION_ID", type: "BIGINT", remarks: "岗位ID")
            column(name: "POSITION_GROUP_ID", type: "BIGINT", remarks: "岗位组ID")
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "员工ID")
            column(name: "EMPLOYEE_GROUP_ID", type: "BIGINT", remarks: "员工组ID")
            column(name: "DIMENSION1_ID", type: "BIGINT", remarks: "维值1 ")
            column(name: "DIMENSION2_ID", type: "BIGINT", remarks: "维值2 ")
            column(name: "DIMENSION3_ID", type: "BIGINT", remarks: "维值3 ")
            column(name: "DIMENSION4_ID", type: "BIGINT", remarks: "维值4 ")
            column(name: "DIMENSION5_ID", type: "BIGINT", remarks: "维值5 ")
            column(name: "DIMENSION6_ID", type: "BIGINT", remarks: "维值6 ")
            column(name: "DIMENSION7_ID", type: "BIGINT", remarks: "维值7 ")
            column(name: "DIMENSION8_ID", type: "BIGINT", remarks: "维值8 ")
            column(name: "DIMENSION9_ID", type: "BIGINT", remarks: "维值9 ")
            column(name: "DIMENSION10_ID", type: "BIGINT", remarks: "维值10 ")
            column(name: "DIMENSION11_ID", type: "BIGINT", remarks: "维值11 ")
            column(name: "DIMENSION12_ID", type: "BIGINT", remarks: "维值12 ")
            column(name: "DIMENSION13_ID", type: "BIGINT", remarks: "维值13 ")
            column(name: "DIMENSION14_ID", type: "BIGINT", remarks: "维值14 ")
            column(name: "DIMENSION15_ID", type: "BIGINT", remarks: "维值15 ")
            column(name: "DIMENSION16_ID", type: "BIGINT", remarks: "维值16 ")
            column(name: "DIMENSION17_ID", type: "BIGINT", remarks: "维值17 ")
            column(name: "DIMENSION18_ID", type: "BIGINT", remarks: "维值18 ")
            column(name: "DIMENSION19_ID", type: "BIGINT", remarks: "维值19 ")
            column(name: "DIMENSION20_ID", type: "BIGINT", remarks: "维值20 ")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "BALANCE_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "预算临时表ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_JOURNAL_BALANCE_TMP_PK")
            }
            column(name: "BGT_ORG_ID", type: "BIGINT", remarks: "预算组织ID")
            column(name: "BGT_ENTITY_ID", type: "BIGINT", remarks: "预算实体ID")
            column(name: "BGT_CENTER_ID", type: "BIGINT", remarks: "预算中心ID")
            column(name: "STRUCTURE_ID", type: "BIGINT", remarks: "预算表ID")
            column(name: "SCENARIO_ID", type: "BIGINT", remarks: "预算场景ID")
            column(name: "VERSION_ID", type: "BIGINT", remarks: "预算版本ID")
            column(name: "BUDGET_ITEM_ID", type: "BIGINT", remarks: "预算项目ID")
            column(name: "PERIOD_YEAR", type: "BIGINT", remarks: "预算年度")
            column(name: "PERIOD_QUARTER", type: "BIGINT", remarks: "季度")
            column(name: "PERIOD_NAME", type: "VARCHAR(30)", remarks: "期间")
            column(name: "BGT_PERIOD_NUM", type: "BIGINT", remarks: "预算期间数")
            column(name: "CURRENCY", type: "VARCHAR(30)", remarks: "币种")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_JOURNAL_BALANCE_EXTEND") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_JOURNAL_BALANCE_EXTEND_S', startValue: "10001")
        }

        createTable(tableName: "BGT_JOURNAL_BALANCE_EXTEND", remarks: "预算余额扩展表") {

            column(name: "BALANCE_EXTEND_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "预算余额扩展表ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_JOURNAL_BALANCE_EXTEND_PK")
            }
            column(name: "EXCHANGE_RATE", type: "NUMBER", remarks: "汇率") { constraints(nullable: "false") }
            column(name: "EXCHANGE_DATE", type: "DATETIME", remarks: "汇兑日期") { constraints(nullable: "false") }
            column(name: "EXCHANGE_PERIOD_NAME", type: "VARCHAR(30)", remarks: "汇兑期间") {
                constraints(nullable: "false")
            }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "BALANCE_ID", type: "BIGINT", remarks: "预算余额ID") { constraints(nullable: "false") }
            column(name: "PERIOD_AMOUNT", type: "NUMBER", remarks: "期间金额") { constraints(nullable: "false") }
            column(name: "QUARTER_AMOUNT", type: "NUMBER", remarks: "季度金额") { constraints(nullable: "false") }
            column(name: "YEAR_AMOUNT", type: "NUMBER", remarks: "年度金额") { constraints(nullable: "false") }
            column(name: "CURRENCY_CODE", type: "VARCHAR(30)", remarks: "币种") { constraints(nullable: "false") }
            column(name: "EXCHANGE_RATE_TYPE", type: "VARCHAR(30)", remarks: "汇率类型")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_JOURNAL_BALANCE_EXTEND_BAK") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_JOURNAL_BALANCE_EXTEND_BAK_S', startValue: "10001")
        }

        createTable(tableName: "BGT_JOURNAL_BALANCE_EXTEND_BAK", remarks: "预算余额扩展表_备份表") {

            column(name: "BALANCE_EXTEND_BAK_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "预算余额扩展表ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_JOURNAL_BALANCE_EXTEND_BAK_PK")
            }
            column(name: "BALANCE_ID", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "PERIOD_AMOUNT", type: "NUMBER", remarks: "") { constraints(nullable: "false") }
            column(name: "QUARTER_AMOUNT", type: "NUMBER", remarks: "") { constraints(nullable: "false") }
            column(name: "YEAR_AMOUNT", type: "NUMBER", remarks: "") { constraints(nullable: "false") }
            column(name: "CURRENCY_CODE", type: "VARCHAR(30)", remarks: "") { constraints(nullable: "false") }
            column(name: "EXCHANGE_RATE_TYPE", type: "VARCHAR(30)", remarks: "")
            column(name: "EXCHANGE_RATE", type: "NUMBER", remarks: "") { constraints(nullable: "false") }
            column(name: "EXCHANGE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "EXCHANGE_PERIOD_NAME", type: "VARCHAR(30)", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_JOURNAL_HEADER") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_JOURNAL_HEADER_S', startValue: "10001")
        }

        createTable(tableName: "BGT_JOURNAL_HEADER", remarks: "预算日记账头表") {

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
            column(name: "JOURNAL_HEADER_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_JOURNAL_HEADER_PK")
            }
            column(name: "BGT_ORG_ID", type: "BIGINT", remarks: "预算组织ID") { constraints(nullable: "false") }
            column(name: "BGT_ENTITY_ID", type: "BIGINT", remarks: "预算实体ID")
            column(name: "BGT_CENTER_ID", type: "BIGINT", remarks: "预算中心ID")
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "管理公司ID") { constraints(nullable: "false") }
            column(name: "BGT_JOURNAL_TYPE_ID", type: "BIGINT", remarks: "预算日记账类型ID") { constraints(nullable: "false") }
            column(name: "STRUCTURE_ID", type: "BIGINT", remarks: "预算表ID") { constraints(nullable: "false") }
            column(name: "BUDGET_JOURNAL_NUMBER", type: "VARCHAR(30)", remarks: "预算日记账编号") {
                constraints(nullable: "false")
            }
            column(name: "PERIOD_YEAR", type: "BIGINT", remarks: "年度")
            column(name: "PERIOD_QUARTER", type: "BIGINT", remarks: "季度")
            column(name: "PERIOD_NAME", type: "VARCHAR(30)", remarks: "期间")
            column(name: "BGT_PERIOD_NUM", type: "BIGINT", remarks: "预算期间数")
            column(name: "SCENARIO_ID", type: "BIGINT", remarks: "预算场景ID") { constraints(nullable: "false") }
            column(name: "VERSION_ID", type: "BIGINT", remarks: "预算版本ID") { constraints(nullable: "false") }
            column(name: "STATUS", type: "VARCHAR(30)", remarks: "状态") { constraints(nullable: "false") }
            column(name: "REVERSED_FLAG", type: "VARCHAR(1)", remarks: "反冲标志")
            column(name: "SOURCE_TYPE", type: "VARCHAR(30)", remarks: "来源类型")
            column(name: "SOURCE_BUDGET_HEADER_ID", type: "BIGINT", remarks: "来源日记账头ID")
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "员工ID")
            column(name: "POSITION_ID", type: "BIGINT", remarks: "岗位ID")
            column(name: "UNIT_ID", type: "BIGINT", remarks: "部门ID")
            column(name: "APPROVED_DATE", type: "DATETIME", remarks: "审批日期")
            column(name: "APPROVED_BY", type: "BIGINT", remarks: "审批人")
            column(name: "POSTED_DATE", type: "DATETIME", remarks: "过账日期")
            column(name: "POSTED_BY", type: "BIGINT", remarks: "过账人")
            column(name: "JOURNAL_HEADER_NOTES", type: "VARCHAR(2000)", remarks: "头备注")
            column(name: "OPERATION_UNIT_ID", type: "BIGINT", remarks: "经营单位ID（废弃）")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "DIMENSION1_ID", type: "BIGINT", remarks: "维度1")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BGT_JOURNAL_HEADER", indexName: "BGT_JOURNAL_HEADERS_N1") {
            column(name: "BGT_CENTER_ID")
            column(name: "BGT_ENTITY_ID")
            column(name: "BGT_ORG_ID")
        }
        createIndex(tableName: "BGT_JOURNAL_HEADER", indexName: "BGT_JOURNAL_HEADERS_N2") {
            column(name: "REVERSED_FLAG")
            column(name: "SOURCE_BUDGET_HEADER_ID")
        }

        addUniqueConstraint(columnNames: "COMPANY_ID,BUDGET_JOURNAL_NUMBER", tableName: "BGT_JOURNAL_HEADER", constraintName: "BGT_JOURNAL_HEADERS_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_JOURNAL_INTERFACE") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_JOURNAL_INTERFACE_S', startValue: "10001")
        }

        createTable(tableName: "BGT_JOURNAL_INTERFACE", remarks: "预算日记账接口表") {

            column(name: "DIMENSION17_CODE", type: "VARCHAR(30)", remarks: "维度17代码")
            column(name: "DIMENSION18_ID", type: "BIGINT", remarks: "维度18ID")
            column(name: "DIMENSION18_CODE", type: "VARCHAR(30)", remarks: "维度18代码")
            column(name: "DIMENSION19_ID", type: "BIGINT", remarks: "维度19ID")
            column(name: "DIMENSION19_CODE", type: "VARCHAR(30)", remarks: "维度19代码")
            column(name: "DIMENSION20_ID", type: "BIGINT", remarks: "维度20ID")
            column(name: "DIMENSION20_CODE", type: "VARCHAR(30)", remarks: "维度20代码")
            column(name: "INTERFACE_ENABLED_FLAG", type: "VARCHAR(1)", remarks: "预算接口表启用标志")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID") { constraints(nullable: "false") }
            column(name: "BATCH_ID", type: "BIGINT", remarks: "批次ID") { constraints(nullable: "false") }
            column(name: "BATCH_LINE_ID", type: "BIGINT", remarks: "批次行ID") { constraints(nullable: "false") }
            column(name: "BGT_ORG_ID", type: "BIGINT", remarks: "预算组织ID")
            column(name: "BGT_ORG_CODE", type: "VARCHAR(30)", remarks: "预算组织")
            column(name: "STRUCTURE_ID", type: "BIGINT", remarks: "预算表ID")
            column(name: "STRUCTURE_CODE", type: "VARCHAR(30)", remarks: "预算表代码") { constraints(nullable: "false") }
            column(name: "PERIOD_YEAR", type: "BIGINT", remarks: "年度")
            column(name: "PERIOD_QUARTER", type: "BIGINT", remarks: "季度")
            column(name: "PERIOD_NAME", type: "VARCHAR(30)", remarks: "期间")
            column(name: "PERIOD_NUM", type: "BIGINT", remarks: "预算期间数")
            column(name: "JOURNAL_HEADER_NOTES", type: "VARCHAR(2000)", remarks: "头备注")
            column(name: "SCENARIO_ID", type: "BIGINT", remarks: "场景ID")
            column(name: "SCENARIO_CODE", type: "VARCHAR(30)", remarks: "预算场景代码") { constraints(nullable: "false") }
            column(name: "VERSION_ID", type: "BIGINT", remarks: "版本ID")
            column(name: "VERSION_CODE", type: "VARCHAR(30)", remarks: "版本代码") { constraints(nullable: "false") }
            column(name: "BGT_JOURNAL_TYPE_ID", type: "BIGINT", remarks: "预算日记账类型ID")
            column(name: "BGT_JOURNAL_TYPE_CODE", type: "VARCHAR(30)", remarks: "预算日记账类型CODE") {
                constraints(nullable: "false")
            }
            column(name: "JOURNAL_COMPANY_ID", type: "BIGINT", remarks: "公司ID")
            column(name: "JOURNAL_COMPANY_CODE", type: "VARCHAR(30)", remarks: "公司代码") {
                constraints(nullable: "false")
            }
            column(name: "JOURNAL_POSITION_ID", type: "BIGINT", remarks: "预算编制岗位ID")
            column(name: "JOURNAL_POSITION_CODE", type: "VARCHAR(30)", remarks: "预算编制岗位CODE")
            column(name: "JOURNAL_UNIT_ID", type: "BIGINT", remarks: "预算编制部门ID")
            column(name: "JOURNAL_UNIT_CODE", type: "VARCHAR(30)", remarks: "预算编制部门CODE")
            column(name: "JOURNAL_EMPLOYEE_ID", type: "BIGINT", remarks: "预算编制人ID")
            column(name: "JOURNAL_EMPLOYEE_CODE", type: "VARCHAR(30)", remarks: "预算编制人CODE")
            column(name: "BGT_ENTITY_ID", type: "BIGINT", remarks: "预算实体ID")
            column(name: "BGT_ENTITY_CODE", type: "VARCHAR(30)", remarks: "预算实体")
            column(name: "BGT_CENTER_ID", type: "BIGINT", remarks: "预算中心ID")
            column(name: "BGT_CENTER_CODE", type: "VARCHAR(30)", remarks: "预算中心")
            column(name: "BUDGET_ITEM_ID", type: "BIGINT", remarks: "预算项目ID")
            column(name: "BUDGET_ITEM_CODE", type: "VARCHAR(30)", remarks: "预算项代码") { constraints(nullable: "false") }
            column(name: "CURRENCY_CODE", type: "VARCHAR(10)", remarks: "币种")
            column(name: "EXCHANGE_RATE_TYPE", type: "VARCHAR(30)", remarks: "汇率类型")
            column(name: "EXCHANGE_RATE", type: "NUMBER", remarks: "汇率")
            column(name: "AMOUNT", type: "NUMBER", remarks: "金额")
            column(name: "FUNCTIONAL_AMOUNT", type: "NUMBER", remarks: "本币金额")
            column(name: "QUANTITY", type: "NUMBER", remarks: "数量")
            column(name: "UOM", type: "VARCHAR(30)", remarks: "单位")
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司ID")
            column(name: "COMPANY_CODE", type: "VARCHAR(30)", remarks: "公司代码")
            column(name: "UNIT_ID", type: "BIGINT", remarks: "部门ID")
            column(name: "UNIT_CODE", type: "VARCHAR(30)", remarks: "部门代码")
            column(name: "POSITION_ID", type: "BIGINT", remarks: "岗位ID")
            column(name: "POSITION_CODE", type: "VARCHAR(30)", remarks: "岗位")
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "员工ID")
            column(name: "EMPLOYEE_CODE", type: "VARCHAR(30)", remarks: "员工代码")
            column(name: "JOURNAL_LINES_NOTES", type: "VARCHAR(2000)", remarks: "行备注")
            column(name: "DIMENSION1_ID", type: "BIGINT", remarks: "维度1ID")
            column(name: "DIMENSION1_CODE", type: "VARCHAR(30)", remarks: "维度1代码")
            column(name: "DIMENSION2_ID", type: "BIGINT", remarks: "维度2ID")
            column(name: "DIMENSION2_CODE", type: "VARCHAR(30)", remarks: "维度2代码")
            column(name: "DIMENSION3_ID", type: "BIGINT", remarks: "维度3ID")
            column(name: "DIMENSION3_CODE", type: "VARCHAR(30)", remarks: "维度3代码")
            column(name: "DIMENSION4_ID", type: "BIGINT", remarks: "维度4ID")
            column(name: "DIMENSION4_CODE", type: "VARCHAR(30)", remarks: "维度4代码")
            column(name: "DIMENSION5_ID", type: "BIGINT", remarks: "维度5ID")
            column(name: "DIMENSION5_CODE", type: "VARCHAR(30)", remarks: "维度5代码")
            column(name: "DIMENSION6_ID", type: "BIGINT", remarks: "维度6ID")
            column(name: "DIMENSION6_CODE", type: "VARCHAR(30)", remarks: "维度6代码")
            column(name: "DIMENSION7_ID", type: "BIGINT", remarks: "维度7ID")
            column(name: "DIMENSION7_CODE", type: "VARCHAR(30)", remarks: "维度7代码")
            column(name: "DIMENSION8_ID", type: "BIGINT", remarks: "维度8ID")
            column(name: "DIMENSION8_CODE", type: "VARCHAR(30)", remarks: "维度8代码")
            column(name: "DIMENSION9_ID", type: "BIGINT", remarks: "维度9ID")
            column(name: "DIMENSION9_CODE", type: "VARCHAR(30)", remarks: "维度9代码")
            column(name: "DIMENSION10_ID", type: "BIGINT", remarks: "维度10ID")
            column(name: "DIMENSION10_CODE", type: "VARCHAR(30)", remarks: "维度10代码")
            column(name: "DIMENSION11_ID", type: "BIGINT", remarks: "维度11ID")
            column(name: "DIMENSION11_CODE", type: "VARCHAR(30)", remarks: "维度11代码")
            column(name: "DIMENSION12_ID", type: "BIGINT", remarks: "维度12ID")
            column(name: "DIMENSION12_CODE", type: "VARCHAR(30)", remarks: "维度12代码")
            column(name: "DIMENSION13_ID", type: "BIGINT", remarks: "维度13ID")
            column(name: "DIMENSION13_CODE", type: "VARCHAR(30)", remarks: "维度13代码")
            column(name: "DIMENSION14_ID", type: "BIGINT", remarks: "维度14ID")
            column(name: "DIMENSION14_CODE", type: "VARCHAR(30)", remarks: "维度14代码")
            column(name: "DIMENSION15_ID", type: "BIGINT", remarks: "维度15ID")
            column(name: "DIMENSION15_CODE", type: "VARCHAR(30)", remarks: "维度15代码")
            column(name: "DIMENSION16_ID", type: "BIGINT", remarks: "维度16ID")
            column(name: "DIMENSION16_CODE", type: "VARCHAR(30)", remarks: "维度16代码")
            column(name: "DIMENSION17_ID", type: "BIGINT", remarks: "维度17ID")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BGT_JOURNAL_INTERFACE", indexName: "BGT_JOURNAL_INTERFACE_N1") {
            column(name: "JOURNAL_COMPANY_CODE")
            column(name: "SCENARIO_CODE")
            column(name: "BATCH_ID")
            column(name: "VERSION_CODE")
            column(name: "BGT_ORG_CODE")
            column(name: "STRUCTURE_CODE")
        }

        addUniqueConstraint(columnNames: "BATCH_LINE_ID,BATCH_ID", tableName: "BGT_JOURNAL_INTERFACE", constraintName: "BGT_JOURNAL_INTERFACE_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_JOURNAL_INTERFACE_ERROR") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_JOURNAL_INTERFACE_ERROR_S', startValue: "10001")
        }

        createTable(tableName: "BGT_JOURNAL_INTERFACE_ERROR", remarks: "预算日记账导入记录表") {

            column(name: "BGT_PERIOD_NUM", type: "BIGINT", remarks: "预算期间")
            column(name: "JOURNAL_HEADER_NOTES", type: "VARCHAR(2000)", remarks: "头备注")
            column(name: "SCENARIO_ID", type: "BIGINT", remarks: "预算场景id")
            column(name: "SCENARIO_CODE", type: "VARCHAR(30)", remarks: "预算场景代码")
            column(name: "VERSION_ID", type: "BIGINT", remarks: "版本ID")
            column(name: "VERSION_CODE", type: "VARCHAR(30)", remarks: "版本代码")
            column(name: "BGT_JOURNAL_TYPE_ID", type: "BIGINT", remarks: "")
            column(name: "BGT_JOURNAL_TYPE_CODE", type: "VARCHAR(30)", remarks: "")
            column(name: "JOURNAL_EMPLOYEE_ID", type: "BIGINT", remarks: "")
            column(name: "JOURNAL_EMPLOYEE_CODE", type: "VARCHAR(30)", remarks: "")
            column(name: "JOURNAL_POSITION_ID", type: "BIGINT", remarks: "")
            column(name: "JOURNAL_POSITION_CODE", type: "VARCHAR(30)", remarks: "")
            column(name: "RESPONSIBILITY_CENTER_ID", type: "BIGINT", remarks: "责任中心id")
            column(name: "RESPONSIBILITY_CENTER_CODE", type: "VARCHAR(30)", remarks: "成本中心ID")
            column(name: "BUDGET_ITEM_ID", type: "BIGINT", remarks: "预算项目id")
            column(name: "BUDGET_ITEM_CODE", type: "VARCHAR(30)", remarks: "预算项目CODE")
            column(name: "CURRENCY_CODE", type: "VARCHAR(10)", remarks: "币种")
            column(name: "EXCHANGE_RATE_TYPE", type: "VARCHAR(30)", remarks: "汇率类型")
            column(name: "EXCHANGE_RATE_QUOTATION", type: "VARCHAR(30)", remarks: "标价方法")
            column(name: "EXCHANGE_RATE", type: "NUMBER", remarks: "汇率")
            column(name: "AMOUNT", type: "NUMBER", remarks: "金额")
            column(name: "FUNCTIONAL_AMOUNT", type: "NUMBER", remarks: "本位币金额")
            column(name: "QUANTITY", type: "NUMBER", remarks: "数量")
            column(name: "UOM", type: "VARCHAR(30)", remarks: "经营单位")
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司ID")
            column(name: "COMPANY_CODE", type: "VARCHAR(30)", remarks: "公司编码")
            column(name: "OPERATION_UNIT_ID", type: "BIGINT", remarks: "经营单位id")
            column(name: "OPERATION_UNIT_CODE", type: "VARCHAR(30)", remarks: "经营单位编码")
            column(name: "UNIT_ID", type: "BIGINT", remarks: "部门id")
            column(name: "UNIT_CODE", type: "VARCHAR(30)", remarks: "部门编码")
            column(name: "POSITION_ID", type: "BIGINT", remarks: "岗位id")
            column(name: "POSITION_CODE", type: "VARCHAR(30)", remarks: "岗位编码")
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "员工id")
            column(name: "EMPLOYEE_CODE", type: "VARCHAR(30)", remarks: "员工CODE")
            column(name: "JOURNAL_LINES_NOTES", type: "VARCHAR(2000)", remarks: "行备注")
            column(name: "DIMENSION1_ID", type: "BIGINT", remarks: "维值1id")
            column(name: "DIMENSION1_CODE", type: "VARCHAR(30)", remarks: "维值1编码")
            column(name: "DIMENSION2_ID", type: "BIGINT", remarks: "维值2id")
            column(name: "DIMENSION2_CODE", type: "VARCHAR(30)", remarks: "维值2编码")
            column(name: "DIMENSION3_ID", type: "BIGINT", remarks: "维值3id")
            column(name: "DIMENSION3_CODE", type: "VARCHAR(30)", remarks: "维值3编码")
            column(name: "DIMENSION4_ID", type: "BIGINT", remarks: "维值4id")
            column(name: "DIMENSION4_CODE", type: "VARCHAR(30)", remarks: "维值4编码")
            column(name: "DIMENSION5_ID", type: "BIGINT", remarks: "维值5id")
            column(name: "DIMENSION5_CODE", type: "VARCHAR(30)", remarks: "维值5编码")
            column(name: "DIMENSION6_ID", type: "BIGINT", remarks: "维值6id")
            column(name: "DIMENSION6_CODE", type: "VARCHAR(30)", remarks: "维值6编码")
            column(name: "DIMENSION7_ID", type: "BIGINT", remarks: "维值7id")
            column(name: "DIMENSION7_CODE", type: "VARCHAR(30)", remarks: "维值7编码")
            column(name: "DIMENSION8_ID", type: "BIGINT", remarks: "维值8id")
            column(name: "DIMENSION8_CODE", type: "VARCHAR(30)", remarks: "维值8编码")
            column(name: "DIMENSION9_ID", type: "BIGINT", remarks: "维值9id")
            column(name: "DIMENSION9_CODE", type: "VARCHAR(30)", remarks: "维值9编码")
            column(name: "DIMENSION10_ID", type: "BIGINT", remarks: "维值10id")
            column(name: "DIMENSION10_CODE", type: "VARCHAR(30)", remarks: "维值10编码")
            column(name: "INTERFACE_ENABLED_FLAG", type: "VARCHAR(1)", remarks: "预算接口表启用标志")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID")
            column(name: "BATCH_ID", type: "BIGINT", remarks: "批次ID")
            column(name: "BATCH_LINE_ID", type: "BIGINT", remarks: "预算日记账导入行id")
            column(name: "JOURNAL_COMPANY_ID", type: "BIGINT", remarks: "预算日记账公司id")
            column(name: "JOURNAL_COMPANY_CODE", type: "VARCHAR(30)", remarks: "公司编码")
            column(name: "JOURNAL_OPERATION_UNIT_ID", type: "BIGINT", remarks: "经营单位id")
            column(name: "JOURNAL_OPERATION_UNIT_CODE", type: "VARCHAR(30)", remarks: "经营单位编码")
            column(name: "BGT_ORG_ID", type: "BIGINT", remarks: "预算组织id")
            column(name: "BGT_ORG_CODE", type: "VARCHAR(30)", remarks: "预算组织编码")
            column(name: "BUDGET_STRUCTURE_ID", type: "BIGINT", remarks: "预算表id")
            column(name: "BUDGET_STRUCTURE_CODE", type: "VARCHAR(30)", remarks: "预算表编码")
            column(name: "PERIOD_YEAR", type: "BIGINT", remarks: "预算年度")
            column(name: "PERIOD_QUARTER", type: "BIGINT", remarks: "季度")
            column(name: "PERIOD_NAME", type: "VARCHAR(30)", remarks: "期间")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_JOURNAL_INT_ERR_LOG") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_JOURNAL_INT_ERR_LOG_S', startValue: "10001")
        }

        createTable(tableName: "BGT_JOURNAL_INT_ERR_LOG", remarks: "预算日记账导入日志表") {

            column(name: "BATCH_ID", type: "BIGINT", remarks: "批次ID")
            column(name: "BATCH_LINE_ID", type: "BIGINT", remarks: "日志表行id")
            column(name: "JOURNAL_COMPANY_CODE", type: "VARCHAR(30)", remarks: "公司编码")
            column(name: "BGT_ORG_CODE", type: "VARCHAR(30)", remarks: "预算组织编码")
            column(name: "BUDGET_STRUCTURE_CODE", type: "VARCHAR(30)", remarks: "预算表编码")
            column(name: "SCENARIO_CODE", type: "VARCHAR(30)", remarks: "预算场景代码")
            column(name: "VERSION_CODE", type: "VARCHAR(30)", remarks: "版本代码")
            column(name: "RESPONSIBILITY_CENTER_CODE", type: "VARCHAR(30)", remarks: "责任中心编码")
            column(name: "BUDGET_ITEM_CODE", type: "VARCHAR(30)", remarks: "预算项目CODE")
            column(name: "MESSAGE", type: "VARCHAR(1000)", remarks: "日志信息")
            column(name: "MESSAGE_DATE", type: "DATETIME", remarks: "日志日期")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BGT_JOURNAL_INT_ERR_LOG", indexName: "BGT_JOURNAL_INT_ERR_LOGS_N1") {
            column(name: "MESSAGE_DATE")
        }
        createIndex(tableName: "BGT_JOURNAL_INT_ERR_LOG", indexName: "BGT_JOURNAL_INT_ERR_LOGS_N2") {
            column(name: "BATCH_LINE_ID")
            column(name: "BATCH_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_JOURNAL_LINE") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_JOURNAL_LINE_S', startValue: "10001")
        }

        createTable(tableName: "BGT_JOURNAL_LINE", remarks: "预算日记账行表") {

            column(name: "JOURNAL_LINE_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_JOURNAL_LINE_PK")
            }
            column(name: "JOURNAL_HEADER_ID", type: "BIGINT", remarks: "预算日记账头ID") { constraints(nullable: "false") }
            column(name: "BGT_ENTITY_ID", type: "BIGINT", remarks: "预算实体ID")
            column(name: "BGT_CENTER_ID", type: "BIGINT", remarks: "预算中心ID")
            column(name: "BUDGET_ITEM_ID", type: "BIGINT", remarks: "预算项目ID") { constraints(nullable: "false") }
            column(name: "LINE_PERIOD_NAME", type: "VARCHAR(30)", remarks: "期间")
            column(name: "LINE_PERIOD_QUARTER", type: "BIGINT", remarks: "季度")
            column(name: "LINE_PERIOD_YEAR", type: "BIGINT", remarks: "年度")
            column(name: "LINE_BGT_PERIOD_NUM", type: "BIGINT", remarks: "预算期间数")
            column(name: "CURRENCY", type: "VARCHAR(30)", remarks: "币种")
            column(name: "EXCHANGE_RATE", type: "NUMBER", remarks: "汇率")
            column(name: "EXCHANGE_RATE_TYPE", type: "VARCHAR(30)", remarks: "汇率类型")
            column(name: "AMOUNT", type: "decimal(10,2)", remarks: "金额")
            column(name: "FUNCTIONAL_AMOUNT", type: "NUMBER", remarks: "本币金额")
            column(name: "QUANTITY", type: "NUMBER", remarks: "数量")
            column(name: "UOM", type: "VARCHAR(30)", remarks: "单位")
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "管理公司ID")
            column(name: "UNIT_ID", type: "BIGINT", remarks: "部门ID")
            column(name: "POSITION_ID", type: "BIGINT", remarks: "岗位ID")
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "员工ID")
            column(name: "JOURNAL_LINES_NOTES", type: "VARCHAR(2000)", remarks: "行备注")
            column(name: "OPERATION_UNIT_ID", type: "BIGINT", remarks: "经营单位ID（废弃）")
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
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BGT_JOURNAL_LINE", indexName: "BGT_JOURNAL_LINES_N1") {
            column(name: "JOURNAL_HEADER_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_JOURNAL_OBJECT") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_JOURNAL_OBJECT_S', startValue: "10001")
        }

        createTable(tableName: "BGT_JOURNAL_OBJECT", remarks: "费用申请单费用对象信息") {

            column(name: "JOURNAL_OBJECT_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_JOURNAL_OBJECT_PK")
            }
            column(name: "JOURNAL_HEADER_ID", type: "BIGINT", remarks: "费用申请单头ID") { constraints(nullable: "false") }
            column(name: "JOURNAL_LINE_ID", type: "BIGINT", remarks: "费用申请单行ID")
            column(name: "EXPENSE_OBJECT_TYPE_ID", type: "BIGINT", remarks: "费用对象类型ID") {
                constraints(nullable: "false")
            }
            column(name: "EXPENSE_OBJECT_ID", type: "BIGINT", remarks: "费用对象ID")
            column(name: "EXPENSE_OBJECT_DESC", type: "VARCHAR(2000)", remarks: "费用对象描述")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BGT_JOURNAL_OBJECT", indexName: "BGT_JOURNAL_OBJECTS_N1") {
            column(name: "EXPENSE_OBJECT_TYPE_ID")
            column(name: "EXPENSE_OBJECT_ID")
        }
        createIndex(tableName: "BGT_JOURNAL_OBJECT", indexName: "BGT_JOURNAL_OBJECTS_N2") {
            column(name: "JOURNAL_HEADER_ID")
        }

        addUniqueConstraint(columnNames: "JOURNAL_HEADER_ID,JOURNAL_LINE_ID,EXPENSE_OBJECT_TYPE_ID", tableName: "BGT_JOURNAL_OBJECT", constraintName: "BGT_JOURNAL_OBJECTS_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_JOURNAL_TP_RANGE_CEN") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_JOURNAL_TP_RANGE_CEN_S', startValue: "10001")
        }

        createTable(tableName: "BGT_JOURNAL_TP_RANGE_CEN", remarks: "预算日记账类型指定范围预算中心") {

            column(name: "RANGE_CEN_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_JOURNAL_TP_RANGE_CEN_PK")
            }
            column(name: "RANGE_ETS_ID", type: "BIGINT", remarks: "预算日记账类型-指定范围预算实体ID") {
                constraints(nullable: "false")
            }
            column(name: "CENTER_ID", type: "BIGINT", remarks: "预算中心ID") { constraints(nullable: "false") }
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "CENTER_ID,RANGE_ETS_ID", tableName: "BGT_JOURNAL_TP_RANGE_CEN", constraintName: "BGT_JOURNAL_TP_RANGE_CEN_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_JOURNAL_TP_RANGE_ET") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_JOURNAL_TP_RANGE_ET_S', startValue: "10001")
        }

        createTable(tableName: "BGT_JOURNAL_TP_RANGE_ET", remarks: "预算日记账类型指定范围预算实体") {

            column(name: "RANGE_ETS_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_JOURNAL_TP_RANGE_ET_PK")
            }
            column(name: "RANGE_ID", type: "BIGINT", remarks: "预算日记账类型-指定范围ID") { constraints(nullable: "false") }
            column(name: "ENTITY_ID", type: "BIGINT", remarks: "预算实体ID") { constraints(nullable: "false") }
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "RANGE_ID,ENTITY_ID", tableName: "BGT_JOURNAL_TP_RANGE_ET", constraintName: "BGT_JOURNAL_TP_RANGE_ETS_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_JOURNAL_TYPE") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_JOURNAL_TYPE_S', startValue: "10001")
        }

        createTable(tableName: "BGT_JOURNAL_TYPE", remarks: "预算日记账类型") {

            column(name: "BGT_JOURNAL_TYPE_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "预算日记账类型ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_JOURNAL_TYPE_PK")
            }
            column(name: "BGT_ORG_ID", type: "BIGINT", remarks: "预算组织ID") { constraints(nullable: "false") }
            column(name: "BGT_JOURNAL_TYPE_CODE", type: "VARCHAR(30)", remarks: "预算日记账类型CODE") {
                constraints(nullable: "false")
            }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述ID") { constraints(nullable: "false") }
            column(name: "BGT_BUSINESS_TYPE", type: "VARCHAR(30)", remarks: "预算业务类型syscode") {
                constraints(nullable: "false")
            }
            column(name: "AUTHORITY_TYPE", type: "VARCHAR(30)", remarks: "权限类型") { constraints(nullable: "false") }
            column(name: "AUTO_APPROVE_FLAG", type: "VARCHAR(1)", remarks: "自审批") { constraints(nullable: "false") }
            column(name: "IMPORT_FLAG", type: "VARCHAR(1)", remarks: "只可导入") { constraints(nullable: "false") }
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "DOCUMENT_PAGE_TYPE", type: "VARCHAR(30)", remarks: "单据页面类型")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "BGT_JOURNAL_TYPE_CODE,BGT_ORG_ID", tableName: "BGT_JOURNAL_TYPE", constraintName: "BGT_JOURNAL_TYPES_U1")
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_JOURNAL_TYPE_TL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_JOURNAL_TYPE_TL_S', startValue: "10001")
        }

        createTable(tableName: "BGT_JOURNAL_TYPE_TL", remarks: "预算日记账类型多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "BGT_JOURNAL_TYPE_ID", type: "BIGINT", remarks: "预算日记账类型ID") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述ID")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_JOURNAL_TYPE_ASGN_COM") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_JOURNAL_TYPE_ASGN_COM_S', startValue: "10001")
        }

        createTable(tableName: "BGT_JOURNAL_TYPE_ASGN_COM", remarks: "预算日记账类型分配公司") {

            column(name: "ASSIGN_COM_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_JOURNAL_TYPE_ASGN_COM_PK")
            }
            column(name: "ASSIGN_MO_ID", type: "BIGINT", remarks: "预算日记账类型分配管理组织ID") { constraints(nullable: "false") }
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "管理公司ID") { constraints(nullable: "false") }
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "ASSIGN_MO_ID,COMPANY_ID", tableName: "BGT_JOURNAL_TYPE_ASGN_COM", constraintName: "BGT_JOURNAL_TYPE_ASGN_COM_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_JOURNAL_TYPE_ASGN_MO") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_JOURNAL_TYPE_ASGN_MO_S', startValue: "10001")
        }

        createTable(tableName: "BGT_JOURNAL_TYPE_ASGN_MO", remarks: "预算日记账类型分配管理组织") {

            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "ASSIGN_MO_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_JOURNAL_TYPE_ASGN_MO_PK")
            }
            column(name: "BGT_JOURNAL_TYPE_ID", type: "BIGINT", remarks: "预算日记账类型ID") { constraints(nullable: "false") }
            column(name: "MAG_ORG_ID", type: "BIGINT", remarks: "管理组织ID") { constraints(nullable: "false") }
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "BGT_JOURNAL_TYPE_ID,MAG_ORG_ID", tableName: "BGT_JOURNAL_TYPE_ASGN_MO", constraintName: "BGT_JOURNAL_TYPE_ASGN_MO_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_JOURNAL_TYPE_REF_GP") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_JOURNAL_TYPE_REF_GP_S', startValue: "10001")
        }

        createTable(tableName: "BGT_JOURNAL_TYPE_REF_GP", remarks: "预算日记账类型关联员工组") {

            column(name: "REF_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_JOURNAL_TYPE_REF_GP_PK")
            }
            column(name: "BGT_JOURNAL_TYPE_ID", type: "BIGINT", remarks: "预算日记账类型ID") { constraints(nullable: "false") }
            column(name: "MO_EMP_GROUP_ID", type: "BIGINT", remarks: "员工组ID") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "BGT_JOURNAL_TYPE_ID,MO_EMP_GROUP_ID", tableName: "BGT_JOURNAL_TYPE_REF_GP", constraintName: "BGT_JOURNAL_TYPE_REF_GPS_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_JOURNAL_TYPE_REF_IT") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_JOURNAL_TYPE_REF_IT_S', startValue: "10001")
        }

        createTable(tableName: "BGT_JOURNAL_TYPE_REF_IT", remarks: "预算日记账类型关联预算项目") {

            column(name: "REF_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_JOURNAL_TYPE_REF_IT_PK")
            }
            column(name: "BGT_JOURNAL_TYPE_ID", type: "BIGINT", remarks: "预算日记账类型ID") { constraints(nullable: "false") }
            column(name: "BUDGET_ITEM_ID", type: "BIGINT", remarks: "预算项目ID") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "BUDGET_ITEM_ID,BGT_JOURNAL_TYPE_ID", tableName: "BGT_JOURNAL_TYPE_REF_IT", constraintName: "BGT_JOURNAL_TYPE_REF_ITS_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_JOURNAL_TYPE_REF_OBJ") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_JOURNAL_TYPE_REF_OBJ_S', startValue: "10001")
        }

        createTable(tableName: "BGT_JOURNAL_TYPE_REF_OBJ", remarks: "预算日记账类型关联对象") {

            column(name: "REF_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_JOURNAL_TYPE_REF_OBJ_PK")
            }
            column(name: "BGT_JOURNAL_TYPE_ID", type: "BIGINT", remarks: "预算日记账类型ID") { constraints(nullable: "false") }
            column(name: "EXPENSE_OBJECT_TYPE_ID", type: "BIGINT", remarks: "费用对象类型ID") {
                constraints(nullable: "false")
            }
            column(name: "LAYOUT_POSITION", type: "VARCHAR(30)", remarks: "布局位置") { constraints(nullable: "false") }
            column(name: "LAYOUT_PRIORITY", type: "NUMBER", remarks: "布局顺序") { constraints(nullable: "false") }
            column(name: "DEFAULT_OBJECT_ID", type: "BIGINT", remarks: "默认对象")
            column(name: "REQUIRED_FLAG", type: "VARCHAR(1)", remarks: "是否必输") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "EXPENSE_OBJECT_TYPE_ID,BGT_JOURNAL_TYPE_ID", tableName: "BGT_JOURNAL_TYPE_REF_OBJ", constraintName: "BGT_JOURNAL_TYPE_REF_OBJS_U1")
        addUniqueConstraint(columnNames: "LAYOUT_PRIORITY,BGT_JOURNAL_TYPE_ID,LAYOUT_POSITION", tableName: "BGT_JOURNAL_TYPE_REF_OBJ", constraintName: "BGT_JOURNAL_TYPE_REF_OBJS_U2")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_JOURNAL_TYPE_REF_RAG") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_JOURNAL_TYPE_REF_RAG_S', startValue: "10001")
        }

        createTable(tableName: "BGT_JOURNAL_TYPE_REF_RAG", remarks: "预算日记账类型指定范围") {

            column(name: "RANGE_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_JOURNAL_TYPE_REF_RAG_PK")
            }
            column(name: "BGT_JOURNAL_TYPE_ID", type: "BIGINT", remarks: "预算日记账类型ID") { constraints(nullable: "false") }
            column(name: "EMPLOYEE_ID", type: "BIGINT", remarks: "员工ID") { constraints(nullable: "false") }
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "EMPLOYEE_ID,BGT_JOURNAL_TYPE_ID", tableName: "BGT_JOURNAL_TYPE_REF_RAG", constraintName: "BGT_JOURNAL_TYPE_REF_RAGS_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_JOURNAL_TYPE_REF_SCNR") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_JOURNAL_TYPE_REF_SCNR_S', startValue: "10001")
        }

        createTable(tableName: "BGT_JOURNAL_TYPE_REF_SCNR", remarks: "预算日记账类型关联预算场景") {

            column(name: "REF_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_JOURNAL_TYPE_REF_SCNR_PK")
            }
            column(name: "BGT_JOURNAL_TYPE_ID", type: "BIGINT", remarks: "预算日记账类型ID") { constraints(nullable: "false") }
            column(name: "SCENARIO_ID", type: "BIGINT", remarks: "预算场景ID") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "BGT_JOURNAL_TYPE_ID,SCENARIO_ID", tableName: "BGT_JOURNAL_TYPE_REF_SCNR", constraintName: "BGT_JOURNAL_TYPE_REF_SCNR_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_JOURNAL_TYPE_REF_STRC") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_JOURNAL_TYPE_REF_STRC_S', startValue: "10001")
        }

        createTable(tableName: "BGT_JOURNAL_TYPE_REF_STRC", remarks: "预算日记账类型关联预算表") {

            column(name: "REF_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_JOURNAL_TYPE_REF_STRC_PK")
            }
            column(name: "BGT_JOURNAL_TYPE_ID", type: "BIGINT", remarks: "预算日记账类型ID") { constraints(nullable: "false") }
            column(name: "STRUCTURE_ID", type: "BIGINT", remarks: "预算表ID") { constraints(nullable: "false") }
            column(name: "DEFAULT_FLAG", type: "VARCHAR(1)", remarks: "默认标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "STRUCTURE_ID,BGT_JOURNAL_TYPE_ID", tableName: "BGT_JOURNAL_TYPE_REF_STRC", constraintName: "BGT_JOURNAL_TYPE_REF_STRC_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_JOURNAL_TYPE_REF_VER") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_JOURNAL_TYPE_REF_VER_S', startValue: "10001")
        }

        createTable(tableName: "BGT_JOURNAL_TYPE_REF_VER", remarks: "预算日记账类型关联预算版本") {

            column(name: "REF_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_JOURNAL_TYPE_REF_VER_PK")
            }
            column(name: "BGT_JOURNAL_TYPE_ID", type: "BIGINT", remarks: "预算日记账类型ID") { constraints(nullable: "false") }
            column(name: "VERSION_ID", type: "BIGINT", remarks: "预算版本ID") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "VERSION_ID,BGT_JOURNAL_TYPE_ID", tableName: "BGT_JOURNAL_TYPE_REF_VER", constraintName: "BGT_JOURNAL_TYPE_REF_VER_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_JOUR_OBJECT_LAYOUT") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_JOUR_OBJECT_LAYOUT_S', startValue: "10001")
        }

        createTable(tableName: "BGT_JOUR_OBJECT_LAYOUT", remarks: "预算日记账费用对象布局") {

            column(name: "JOURNAL_HEADER_ID", type: "BIGINT", remarks: "预算日记账头ID") { constraints(nullable: "false") }
            column(name: "LAYOUT_POSITION", type: "VARCHAR(30)", remarks: "布局位置") { constraints(nullable: "false") }
            column(name: "LAYOUT_PRIORITY", type: "NUMBER", remarks: "布局顺序") { constraints(nullable: "false") }
            column(name: "EXPENSE_OBJECT_TYPE_ID", type: "BIGINT", remarks: "费用对象类型ID") {
                constraints(nullable: "false")
            }
            column(name: "DEFAULT_OBJECT_ID", type: "BIGINT", remarks: "缺省费用对象ID")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addPrimaryKey(columnNames: "EXPENSE_OBJECT_TYPE_ID,JOURNAL_HEADER_ID", tableName: "BGT_JOUR_OBJECT_LAYOUT", constraintName: "BGT_JOUR_OBJECT_LAYOUTS_PK")
        addUniqueConstraint(columnNames: "LAYOUT_POSITION,LAYOUT_PRIORITY,JOURNAL_HEADER_ID", tableName: "BGT_JOUR_OBJECT_LAYOUT", constraintName: "BGT_JOUR_OBJECT_LAYOUTS_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_ORGANIZATION") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_ORGANIZATION_S', startValue: "10001")
        }

        createTable(tableName: "BGT_ORGANIZATION", remarks: "预算组织表") {

            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "BGT_ORG_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_ORGANIZATION_PK")
            }
            column(name: "BGT_ORG_CODE", type: "VARCHAR(30)", remarks: "预算组织代码") { constraints(nullable: "false") }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "预算组织描述ID") { constraints(nullable: "false") }
            column(name: "CURRENCY_CODE", type: "VARCHAR(30)", remarks: "预算币种") { constraints(nullable: "false") }
            column(name: "SOURCE_TYPE_CODE", type: "VARCHAR(30)", remarks: "来源类型（SYSCODE：BGT_ORG_SOURCE_TYPE）") {
                constraints(nullable: "false")
            }
            column(name: "EXCHANGE_RATE_TYPE_ID", type: "BIGINT", remarks: "汇率类型ID")
            column(name: "PERIOD_SET_ID", type: "BIGINT", remarks: "预算期ID")
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "BGT_ORG_CODE", tableName: "BGT_ORGANIZATION", constraintName: "BGT_ORGANIZATIONS_U1")
        addUniqueConstraint(columnNames: "DESCRIPTION", tableName: "BGT_ORGANIZATION", constraintName: "BGT_ORGANIZATIONS_U2")
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_ORGANIZATION_TL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_ORGANIZATION_TL_S', startValue: "10001")
        }

        createTable(tableName: "BGT_ORGANIZATION_TL", remarks: "预算组织表多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "BGT_ORG_ID", type: "BIGINT", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "预算组织描述ID")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_ORG_REF_DETAIL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_ORG_REF_DETAIL_S', startValue: "10001")
        }

        createTable(tableName: "BGT_ORG_REF_DETAIL", remarks: "预算组织来源明细表") {

            column(name: "DETAIL_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_ORG_REF_DETAIL_PK")
            }
            column(name: "BGT_ORG_ID", type: "BIGINT", remarks: "预算组织ID") { constraints(nullable: "false") }
            column(name: "SOURCE_ID", type: "BIGINT", remarks: "来源ID") { constraints(nullable: "false") }
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "BGT_ORG_ID,SOURCE_ID", tableName: "BGT_ORG_REF_DETAIL", constraintName: "BGT_ORG_REF_DETAILS_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_PERIOD") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_PERIOD_S', startValue: "10001")
        }

        createTable(tableName: "BGT_PERIOD", remarks: "预算期间表") {

            column(name: "PERIOD_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_PERIOD_PK")
            }
            column(name: "PERIOD_SET_ID", type: "BIGINT", remarks: "预算期ID") { constraints(nullable: "false") }
            column(name: "PERIOD_YEAR", type: "BIGINT", remarks: "年") { constraints(nullable: "false") }
            column(name: "PERIOD_NUM", type: "BIGINT", remarks: "月份") { constraints(nullable: "false") }
            column(name: "PERIOD_NAME", type: "VARCHAR(30)", remarks: "期间") { constraints(nullable: "false") }
            column(name: "INTERNAL_PERIOD_NUM", type: "BIGINT", remarks: "期间序号") { constraints(nullable: "false") }
            column(name: "START_DATE", type: "DATETIME", remarks: "日期从") { constraints(nullable: "false") }
            column(name: "END_DATE", type: "DATETIME", remarks: "日期到") { constraints(nullable: "false") }
            column(name: "QUARTER_NUM", type: "BIGINT", remarks: "季度") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "PERIOD_SET_ID,PERIOD_NAME", tableName: "BGT_PERIOD", constraintName: "BGT_PERIODS_U1")
        addUniqueConstraint(columnNames: "INTERNAL_PERIOD_NUM,PERIOD_SET_ID", tableName: "BGT_PERIOD", constraintName: "BGT_PERIODS_U2")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_PERIOD_ASGN_CM_TMP") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_PERIOD_ASGN_CM_TMP_S', startValue: "10001")
        }

        createTable(tableName: "BGT_PERIOD_ASGN_CM_TMP", remarks: "预算期间控制记录表") {

            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID") { constraints(nullable: "false") }
            column(name: "SESSION_ID", type: "BIGINT", remarks: "系统SESSION ID")
            column(name: "APPLICATION_CODE", type: "VARCHAR(30)", remarks: "预算期间控制功能编码")
            column(name: "PERIOD_SET_CODE", type: "VARCHAR(30)", remarks: "预算期间编码")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BGT_PERIOD_ASGN_CM_TMP", indexName: "BGT_PERIOD_CM_N1") {
            column(name: "SESSION_ID")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_PERIOD_RULE") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_PERIOD_RULE_S', startValue: "10001")
        }

        createTable(tableName: "BGT_PERIOD_RULE", remarks: "预算期间规则") {

            column(name: "DATE_TO", type: "BIGINT", remarks: "日期到") { constraints(nullable: "false") }
            column(name: "QUARTER_NUM", type: "BIGINT", remarks: "季度") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "PERIOD_RULE_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_PERIOD_RULE_PK")
            }
            column(name: "PERIOD_SET_ID", type: "BIGINT", remarks: "预算期ID") { constraints(nullable: "false") }
            column(name: "PERIOD_ADDITIONAL_NAME", type: "VARCHAR(30)", remarks: "期间名附加")
            column(name: "PERIOD_NUM", type: "BIGINT", remarks: "月份") { constraints(nullable: "false") }
            column(name: "MONTH_FROM", type: "BIGINT", remarks: "月份从") { constraints(nullable: "false") }
            column(name: "MONTH_TO", type: "BIGINT", remarks: "月份到") { constraints(nullable: "false") }
            column(name: "DATE_FROM", type: "BIGINT", remarks: "日期从") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "PERIOD_SET_ID,PERIOD_NUM", tableName: "BGT_PERIOD_RULE", constraintName: "BGT_PERIOD_RULES_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_PERIOD_SET") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_PERIOD_SET_S', startValue: "10001")
        }

        createTable(tableName: "BGT_PERIOD_SET", remarks: "预算期表") {

            column(name: "PERIOD_SET_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_PERIOD_SET_PK")
            }
            column(name: "PERIOD_SET_CODE", type: "VARCHAR(30)", remarks: "预算期代码") { constraints(nullable: "false") }
            column(name: "PERIOD_SET_NAME", type: "VARCHAR(500)", remarks: "预算期描述ID") { constraints(nullable: "false") }
            column(name: "TOTAL_PERIOD_NUM", type: "BIGINT", remarks: "期间总数") { constraints(nullable: "false") }
            column(name: "PERIOD_ADDITIONAL_FLAG", type: "VARCHAR(1)", remarks: "名称附加（SYSCODE：PERIOD_ADDITIONAL_FLAG）") {
                constraints(nullable: "false")
            }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BGT_PERIOD_SET", indexName: "BGT_PERIOD_SETS_N1") {
            column(name: "PERIOD_SET_NAME")
        }

        addUniqueConstraint(columnNames: "PERIOD_SET_CODE", tableName: "BGT_PERIOD_SET", constraintName: "BGT_PERIOD_SETS_U1")
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_PERIOD_SET_TL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_PERIOD_SET_TL_S', startValue: "10001")
        }

        createTable(tableName: "BGT_PERIOD_SET_TL", remarks: "预算期表多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "PERIOD_SET_ID", type: "BIGINT", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "PERIOD_SET_NAME", type: "VARCHAR(500)", remarks: "预算期描述ID")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_PERIOD_SET_ASGN_COM") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_PERIOD_SET_ASGN_COM_S', startValue: "10001")
        }

        createTable(tableName: "BGT_PERIOD_SET_ASGN_COM", remarks: "预算期分配公司表") {

            column(name: "ASSIGN_COM_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_PERIOD_SET_ASGN_COM_PK")
            }
            column(name: "ASSIGN_MO_ID", type: "BIGINT", remarks: "预算期分配管理组织ID") { constraints(nullable: "false") }
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "管理公司ID") { constraints(nullable: "false") }
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "COMPANY_ID,ASSIGN_MO_ID", tableName: "BGT_PERIOD_SET_ASGN_COM", constraintName: "BGT_PERIOD_SET_ASGN_COM_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_PERIOD_SET_ASGN_MO") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_PERIOD_SET_ASGN_MO_S', startValue: "10001")
        }

        createTable(tableName: "BGT_PERIOD_SET_ASGN_MO", remarks: "预算期分配管理组织表") {

            column(name: "ASSIGN_MO_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_PERIOD_SET_ASGN_MO_PK")
            }
            column(name: "PERIOD_SET_ID", type: "BIGINT", remarks: "预算期ID") { constraints(nullable: "false") }
            column(name: "MAG_ORG_ID", type: "BIGINT", remarks: "管理组织ID") { constraints(nullable: "false") }
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "MAG_ORG_ID,PERIOD_SET_ID", tableName: "BGT_PERIOD_SET_ASGN_MO", constraintName: "BGT_PERIOD_SET_ASGN_MO_U1")
    }


    // 修改成两张表，guiyuting 2019-03014
/*    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_PERIOD_STATUS") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_PERIOD_STATUS_S', startValue: "10001")
        }

        createTable(tableName: "BGT_PERIOD_STATUS", remarks: "预算期间状态表") {

            column(name: "BGT_ORG_ID", type: "BIGINT", remarks: "预算组织ID") { constraints(nullable: "false") }
            column(name: "BGT_PERIOD_SET_CODE", type: "VARCHAR(30)", remarks: "预算期间") { constraints(nullable: "false") }
            column(name: "BGT_PERIOD_YEAR", type: "BIGINT", remarks: "年度") { constraints(nullable: "false") }
            column(name: "BGT_PERIOD_NUM", type: "BIGINT", remarks: "预算期间数") { constraints(nullable: "false") }
            column(name: "PERIOD_NAME", type: "VARCHAR(30)", remarks: "期间")
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "公司ID") { constraints(nullable: "false") }
            column(name: "BGT_PERIOD_STATUS_CODE", type: "VARCHAR(20)", remarks: "预算期间状态")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BGT_PERIOD_STATUS", indexName: "BGT_PERIOD_STATUS_N1") {
            column(name: "BGT_PERIOD_NUM")
            column(name: "BGT_ORG_ID")
            column(name: "BGT_PERIOD_STATUS_CODE")
        }

        addPrimaryKey(columnNames: "COMPANY_ID,BGT_ORG_ID,BGT_PERIOD_NUM", tableName: "BGT_PERIOD_STATUS", constraintName: "BGT_PERIOD_STATUS_PK")
    }*/


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_RESERVE_SUM") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_RESERVE_SUM_S', startValue: "10001")
        }

        createTable(tableName: "BGT_RESERVE_SUM", remarks: "预算使用金额汇总表") {

            column(name: "DIM_VALUES_KEY", type: "VARCHAR(1000)", remarks: "预算使用总金额") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_RESERVE_SUM_PK")
            }
            column(name: "RESERVES_VALUE", type: "NUMBER", remarks: "预算使用组合")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_RESERVE_SUM_TEMP") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_RESERVE_SUM_TEMP_S', startValue: "10001")
        }

        createTable(tableName: "BGT_RESERVE_SUM_TEMP", remarks: "预算使用总金额临时表") {

            column(name: "DIM_VALUES_KEY", type: "VARCHAR(1000)", remarks: "预算使用总金额") { constraints(nullable: "false") }
            column(name: "RESERVES_VALUE", type: "NUMBER", remarks: "预算使用组合")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期") { constraints(nullable: "false") }
            column(name: "BUDGET_RESERVE_LINE_ID", type: "BIGINT", remarks: "")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_RESERVE_SUM_TMP") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_RESERVE_SUM_TMP_S', startValue: "10001")
        }

        createTable(tableName: "BGT_RESERVE_SUM_TMP", remarks: "预算使用总金额临时表") {

            column(name: "DIM_VALUES_KEY", type: "VARCHAR(1000)", remarks: "预算使用组合") { constraints(nullable: "false") }
            column(name: "RESERVES_VALUE", type: "NUMBER", remarks: "预算使用总金额")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BGT_RESERVE_SUM_TMP", indexName: "BGT_RESERVE_SUM_TMP_N1") {
            column(name: "DIM_VALUES_KEY")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_SCENARIO") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_SCENARIO_S', startValue: "10001")
        }

        createTable(tableName: "BGT_SCENARIO", remarks: "预算场景表") {

            column(name: "SCENARIO_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_SCENARIO_PK")
            }
            column(name: "BGT_ORG_ID", type: "BIGINT", remarks: "预算组织ID") { constraints(nullable: "false") }
            column(name: "SCENARIO_CODE", type: "VARCHAR(30)", remarks: "预算场景代码") { constraints(nullable: "false") }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "预算场景描述ID") { constraints(nullable: "false") }
            column(name: "NOTES", type: "VARCHAR(2000)", remarks: "备注")
            column(name: "DEFAULT_FLAG", type: "VARCHAR(1)", remarks: "默认场景") { constraints(nullable: "false") }
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BGT_SCENARIO", indexName: "BGT_SCENARIOS_N1") {
            column(name: "DESCRIPTION")
        }

        addUniqueConstraint(columnNames: "BGT_ORG_ID,SCENARIO_CODE", tableName: "BGT_SCENARIO", constraintName: "BGT_SCENARIOS_U1")
    }

    changeSet(author: "hongliu.ye@hand-china.com", id: "2019-02-15-BGT_SCENARIO") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_SCENARIO_S', startValue: "10001")
        }

        createTable(tableName: "BGT_SCENARIO", remarks: "预算场景表") {

            column(name: "SCENARIO_ID", type: "BIGINT", autoIncrement: "true", startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_SCENARIO_PK")
            }
            column(name: "BGT_ORG_ID", type: "BIGINT", remarks: "预算组织ID") { constraints(nullable: "false") }
            column(name: "SCENARIO_CODE", type: "VARCHAR(30)", remarks: "预算场景代码") { constraints(nullable: "false") }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "预算场景描述") { constraints(nullable: "false") }
            column(name: "NOTES", type: "VARCHAR(2000)", remarks: "备注")
            column(name: "DEFAULT_FLAG", type: "VARCHAR(1)", remarks: "默认场景") { constraints(nullable: "false") }
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BGT_SCENARIO", indexName: "BGT_SCENARIO_N1") {
            column(name: "DESCRIPTION")
        }

        addUniqueConstraint(columnNames: "BGT_ORG_ID,SCENARIO_CODE", tableName: "BGT_SCENARIO", constraintName: "BGT_SCENARIO_U1")
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_SCENARIO_TL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_SCENARIO_TL_S', startValue: "10001")
        }

        createTable(tableName: "BGT_SCENARIO_TL", remarks: "预算场景表多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "SCENARIO_ID", type: "BIGINT", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "预算场景描述ID")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_SOLUTION_ASSIGN") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_SOLUTION_ASSIGN_S', startValue: "10001")
        }

        createTable(tableName: "BGT_SOLUTION_ASSIGN", remarks: "预算查询方案分配表") {

            column(name: "BGT_SOLUTION_ASSIGN_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "分配表id") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_SOLUTION_ASSIGN_PK")
            }
            column(name: "BGT_SOLUTION_HEADER_ID", type: "BIGINT", remarks: "预算查询方案头id") {
                constraints(nullable: "false")
            }
            column(name: "USER_ID", type: "BIGINT", remarks: "用户id") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BGT_SOLUTION_ASSIGN", indexName: "BGT_SOLUTION_ASSIGNS_N1") {
            column(name: "BGT_SOLUTION_HEADER_ID")
        }

        addUniqueConstraint(columnNames: "USER_ID,BGT_SOLUTION_HEADER_ID", tableName: "BGT_SOLUTION_ASSIGN", constraintName: "BGT_SOLUTION_ASSIGNS_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_SOLUTION_HEADER") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_SOLUTION_HEADER_S', startValue: "10001")
        }

        createTable(tableName: "BGT_SOLUTION_HEADER", remarks: "预算查询方案头表") {

            column(name: "BGT_ORG_ID", type: "BIGINT", remarks: "预算组织id") { constraints(nullable: "false") }
            column(name: "BGT_SOLUTION_HEADER_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "预算查询方案头id") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_SOLUTION_HEADER_PK")
            }
            column(name: "BGT_SOLUTION_CODE", type: "VARCHAR(30)", remarks: "预算查询方案编码") {
                constraints(nullable: "false")
            }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述ID") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "BGT_SOLUTION_CODE,BGT_ORG_ID", tableName: "BGT_SOLUTION_HEADER", constraintName: "BGT_SOLUTION_HEADERS_U1")
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_SOLUTION_HEADER_TL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_SOLUTION_HEADER_TL_S', startValue: "10001")
        }

        createTable(tableName: "BGT_SOLUTION_HEADER_TL", remarks: "预算查询方案头表多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "BGT_SOLUTION_HEADER_ID", type: "BIGINT", remarks: "预算查询方案头id") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "描述ID")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_SOLUTION_LINE") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_SOLUTION_LINE_S', startValue: "10001")
        }

        createTable(tableName: "BGT_SOLUTION_LINE", remarks: "预算查询方案行表") {

            column(name: "BGT_SOLUTION_HEADER_ID", type: "BIGINT", remarks: "预算查询方案头id") {
                constraints(nullable: "false")
            }
            column(name: "BGT_SOLUTION_LINE_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "预算查询方案行id") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_SOLUTION_LINE_PK")
            }
            column(name: "PARAMETER_TYPE", type: "VARCHAR(100)", remarks: "参数类型") { constraints(nullable: "false") }
            column(name: "PARAMETER_ID", type: "BIGINT", remarks: "参数id")
            column(name: "PARAMETER_CODE", type: "VARCHAR(100)", remarks: "参数编码")
            column(name: "PARAMETER_VALUE_ID", type: "BIGINT", remarks: "参数值id")
            column(name: "PARAMETER_VALUE_CODE", type: "VARCHAR(100)", remarks: "参数值id")
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID") { constraints(nullable: "false") }
            column(name: "CREATED_DATE", type: "DATETIME", remarks: "创建日期") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BGT_SOLUTION_LINE", indexName: "BGT_SOLUTION_LINES_N1") {
            column(name: "BGT_SOLUTION_HEADER_ID")
            column(name: "BGT_SOLUTION_LINE_ID")
        }
        createIndex(tableName: "BGT_SOLUTION_LINE", indexName: "BGT_SOLUTION_LINES_N2") {
            column(name: "BGT_SOLUTION_HEADER_ID")
            column(name: "PARAMETER_CODE")
            column(name: "PARAMETER_VALUE_CODE")
            column(name: "PARAMETER_VALUE_ID")
            column(name: "PARAMETER_TYPE")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_SOLUTION_TMP") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_SOLUTION_TMP_S', startValue: "10001")
        }

        createTable(tableName: "BGT_SOLUTION_TMP", remarks: "预算查询明细表") {

            column(name: "SESSION_ID", type: "BIGINT", remarks: "系统SESSION ID")
            column(name: "BGT_SOLUTION_HEADER_ID", type: "BIGINT", remarks: "查询方案头表")
            column(name: "PARAMETER_TYPE", type: "VARCHAR(100)", remarks: "参数类型")
            column(name: "PARAMETER_ID", type: "BIGINT", remarks: "参数id")
            column(name: "PARAMETER_CODE", type: "VARCHAR(100)", remarks: "参数编码")
            column(name: "PARAMETER_VALUE_ID", type: "BIGINT", remarks: "参数值id")
            column(name: "PARAMETER_VALUE_CODE", type: "VARCHAR(100)", remarks: "参数值编码")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BGT_SOLUTION_TMP", indexName: "BGT_SOLUTION_TMP_N1") {
            column(name: "SESSION_ID")
        }
        createIndex(tableName: "BGT_SOLUTION_TMP", indexName: "BGT_SOLUTION_TMP_N2") {
            column(name: "PARAMETER_TYPE")
            column(name: "PARAMETER_VALUE_ID")
            column(name: "PARAMETER_VALUE_CODE")
            column(name: "BGT_SOLUTION_HEADER_ID")
            column(name: "PARAMETER_CODE")
        }

    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_STRUCTURE") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_STRUCTURE_S', startValue: "10001")
        }

        createTable(tableName: "BGT_STRUCTURE", remarks: "预算表") {

            column(name: "STRUCTURE_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_STRUCTURE_PK")
            }
            column(name: "BGT_ORG_ID", type: "BIGINT", remarks: "预算组织ID") { constraints(nullable: "false") }
            column(name: "STRUCTURE_CODE", type: "VARCHAR(30)", remarks: "预算表代码") { constraints(nullable: "false") }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "预算表描述ID")
            column(name: "PERIOD_SET_ID", type: "BIGINT", remarks: "预算期") { constraints(nullable: "false") }
            column(name: "PERIOD_STRATEGY", type: "VARCHAR(30)", remarks: "编制期段（SYSCODE：BUDGET_PERIOD）") {
                constraints(nullable: "false")
            }
            column(name: "NOTE", type: "VARCHAR(2000)", remarks: "备注")
            column(name: "ENTITY_FLAG", type: "VARCHAR(1)", remarks: "预算实体标志") { constraints(nullable: "false") }
            column(name: "CENTER_FLAG", type: "VARCHAR(1)", remarks: "预算中心标志") { constraints(nullable: "false") }
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "BGT_ORG_ID,STRUCTURE_CODE", tableName: "BGT_STRUCTURE", constraintName: "BGT_STRUCTURES_U1")
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_STRUCTURE_TL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_STRUCTURE_TL_S', startValue: "10001")
        }

        createTable(tableName: "BGT_STRUCTURE_TL", remarks: "预算表多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "STRUCTURE_ID", type: "BIGINT", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "预算表描述ID")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_STRUCTURE_ASGN_COM") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_STRUCTURE_ASGN_COM_S', startValue: "10001")
        }

        createTable(tableName: "BGT_STRUCTURE_ASGN_COM", remarks: "预算表分配公司") {

            column(name: "ASSIGN_COM_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_STRUCTURE_ASGN_COM_PK")
            }
            column(name: "ASSIGN_MO_ID", type: "BIGINT", remarks: "预算表分配管理组织ID") { constraints(nullable: "false") }
            column(name: "COMPANY_ID", type: "BIGINT", remarks: "管理公司ID") { constraints(nullable: "false") }
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "ASSIGN_MO_ID,COMPANY_ID", tableName: "BGT_STRUCTURE_ASGN_COM", constraintName: "BGT_STRUCTURE_ASGN_COM_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_STRUCTURE_ASGN_MO") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_STRUCTURE_ASGN_MO_S', startValue: "10001")
        }

        createTable(tableName: "BGT_STRUCTURE_ASGN_MO", remarks: "预算表分配管理组织表") {

            column(name: "ASSIGN_MO_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_STRUCTURE_ASGN_MO_PK")
            }
            column(name: "STRUCTURE_ID", type: "BIGINT", remarks: "预算表ID") { constraints(nullable: "false") }
            column(name: "MAG_ORG_ID", type: "BIGINT", remarks: "管理组织ID") { constraints(nullable: "false") }
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "MAG_ORG_ID,STRUCTURE_ID", tableName: "BGT_STRUCTURE_ASGN_MO", constraintName: "BGT_STRUCTURE_ASGN_MO_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_STRUCTURE_LAYOUT") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_STRUCTURE_LAYOUT_S', startValue: "10001")
        }

        createTable(tableName: "BGT_STRUCTURE_LAYOUT", remarks: "预算表维度") {

            column(name: "STRUCTURE_LAYOUT_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_STRUCTURE_LAYOUT_PK")
            }
            column(name: "STRUCTURE_ID", type: "BIGINT", remarks: "预算表ID") { constraints(nullable: "false") }
            column(name: "DIMENSION_ID", type: "BIGINT", remarks: "维度ID") { constraints(nullable: "false") }
            column(name: "LAYOUT_POSITION", type: "VARCHAR(30)", remarks: "布局位置") { constraints(nullable: "false") }
            column(name: "LAYOUT_PRIORITY", type: "NUMBER", remarks: "布局顺序") { constraints(nullable: "false") }
            column(name: "DEFAULT_DIM_VALUE_ID", type: "BIGINT", remarks: "默认维值")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

        addUniqueConstraint(columnNames: "DIMENSION_ID,STRUCTURE_ID", tableName: "BGT_STRUCTURE_LAYOUT", constraintName: "BGT_STRUCTURE_LAYOUTS_U1")
        addUniqueConstraint(columnNames: "LAYOUT_POSITION,STRUCTURE_ID,LAYOUT_PRIORITY", tableName: "BGT_STRUCTURE_LAYOUT", constraintName: "BGT_STRUCTURE_LAYOUTS_U2")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_VARIABLE_ITEM_REF") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_VARIABLE_ITEM_REF_S', startValue: "10001")
        }

        createTable(tableName: "BGT_VARIABLE_ITEM_REF", remarks: "固定变动预算项目关系表") {

            column(name: "BUDGET_ORG_ID", type: "BIGINT", remarks: "预算组织ID") { constraints(nullable: "false") }
            column(name: "VARIABLE_REF_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "固定变动预算项目关系表ID") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_VARIABLE_ITEM_REF_PK")
            }
            column(name: "VARIABLE_BGT_ITEM_ID", type: "BIGINT", remarks: "变动预算项目ID") { constraints(nullable: "false") }
            column(name: "FIXED_BGT_ITEM_ID", type: "BIGINT", remarks: "固定预算项目ID") { constraints(nullable: "false") }
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
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "最后更新日期") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "最后更新用户ID") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BGT_VARIABLE_ITEM_REF", indexName: "BGT_VARIABLE_ITEM_REFS_N1") {
            column(name: "VARIABLE_BGT_ITEM_ID")
        }

        addUniqueConstraint(columnNames: "DIMENSION2_ID,FIXED_BGT_ITEM_ID", tableName: "BGT_VARIABLE_ITEM_REF", constraintName: "BGT_VARIABLE_ITEM_REFS_U1")
    }


    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_VERSION") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_VERSION_S', startValue: "10001")
        }

        createTable(tableName: "BGT_VERSION", remarks: "预算版本表") {

            column(name: "VERSION_ID", type: "BIGINT", autoIncrement: true, startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_VERSION_PK")
            }
            column(name: "BGT_ORG_ID", type: "BIGINT", remarks: "预算组织ID") { constraints(nullable: "false") }
            column(name: "VERSION_CODE", type: "VARCHAR(30)", remarks: "预算版本代码") { constraints(nullable: "false") }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "预算版本描述ID") { constraints(nullable: "false") }
            column(name: "VERSION_DATE", type: "DATETIME", remarks: "版本日期") { constraints(nullable: "false") }
            column(name: "STATUS", type: "VARCHAR(30)", remarks: "状态（SYSCODE：BGT_VERSION_STATUS）") {
                constraints(nullable: "false")
            }
            column(name: "NOTES", type: "VARCHAR(2000)", remarks: "备注")
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BGT_VERSION", indexName: "BGT_VERSIONS_N1") {
            column(name: "DESCRIPTION")
        }

        addUniqueConstraint(columnNames: "BGT_ORG_ID,VERSION_CODE", tableName: "BGT_VERSION", constraintName: "BGT_VERSIONS_U1")
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-02-16-BGT_VERSION") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_VERSION_S', startValue: "10001")
        }

        createTable(tableName: "BGT_VERSION", remarks: "预算版本表") {

            column(name: "VERSION_ID", type: "BIGINT", autoIncrement: "true", startWith: "10001", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "BGT_VERSION_PK")
            }
            column(name: "BGT_ORG_ID", type: "BIGINT", remarks: "预算组织ID") { constraints(nullable: "false") }
            column(name: "VERSION_CODE", type: "VARCHAR(30)", remarks: "预算版本代码") { constraints(nullable: "false") }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "预算版本描述") { constraints(nullable: "false") }
            column(name: "VERSION_DATE", type: "DATETIME", remarks: "版本日期") { constraints(nullable: "false") }
            column(name: "STATUS", type: "VARCHAR(30)", remarks: "状态（SYSCODE：BGT_VERSION_STATUS）") {
                constraints(nullable: "false")
            }
            column(name: "NOTES", type: "VARCHAR(2000)", remarks: "备注")
            column(name: "ENABLED_FLAG", type: "VARCHAR(1)", remarks: "启用标志") { constraints(nullable: "false") }
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATED_BY", type: "BIGINT", remarks: "") { constraints(nullable: "false") }
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", remarks: "") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
        createIndex(tableName: "BGT_VERSION", indexName: "BGT_VERSION_N1") {
            column(name: "DESCRIPTION")
        }

        addUniqueConstraint(columnNames: "BGT_ORG_ID,VERSION_CODE", tableName: "BGT_VERSION", constraintName: "BGT_VERSION_U1")
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_VERSION_TL") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_VERSION_TL_S', startValue: "10001")
        }

        createTable(tableName: "BGT_VERSION_TL", remarks: "预算版本表多语言表") {

            column(name: "LANG", type: "VARCHAR(10)", remarks: "语言") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "VERSION_ID", type: "BIGINT", remarks: "主键，供其他表外键使用") {
                constraints(nullable: "false", primaryKey: "true")
            }
            column(name: "DESCRIPTION", type: "VARCHAR(500)", remarks: "预算版本描述ID")
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "CREATION_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATED_BY", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_DATE", type: "DATETIME", defaultValueComputed: "CURRENT_TIMESTAMP")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }
    }

    changeSet(author: "hao.zhou@hand-china.com", id: "2019-01-08-BGT_VERSION_ASGN_CM_TMP") {

        if (mhi.getDbType().isSupportSequence()) {
            createSequence(sequenceName: 'BGT_VERSION_ASGN_CM_TMP_S', startValue: "10001")
        }

        createTable(tableName: "BGT_VERSION_ASGN_CM_TMP", remarks: "预算版本分配表") {

            column(name: "SESSION_ID", type: "BIGINT", remarks: "系统SESSION ID")
            column(name: "APPLICATION_CODE", type: "VARCHAR(30)", remarks: "可分配编码")
            column(name: "VERSION_ID", type: "BIGINT", remarks: "版本ID")
            column(name: "CREATION_DATE", type: "DATETIME", remarks: "创建日期") { constraints(nullable: "false") }
            column(name: "CREATED_BY", type: "BIGINT", remarks: "创建用户ID") { constraints(nullable: "false") }
            column(name: "OBJECT_VERSION_NUMBER", type: "BIGINT", defaultValue: "1")
            column(name: "REQUEST_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "PROGRAM_ID", type: "BIGINT", defaultValue: "-1")
            column(name: "LAST_UPDATE_LOGIN", type: "BIGINT", defaultValue: "-1")

        }

    }


}