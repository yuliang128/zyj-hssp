package com.hand.hec.exp.dto;

/**
 * description 费用对象值Dto
 *
 * @author jiangxz 2019/02/28 10:21
 */
public class ExpMoObjectValue {
    public static final String FIELD_ID = "id";
    public static final String FIELD_CODE = "code";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_SQL_QUERY = "sqlQuery";
    public static final String FIELD_MO_EXPENSE_OBJECT_ID = "moExpenseObjectId";
    private Long id;

    private String code;

    private String name;

    private String sqlQuery;

    private Long moExpenseObjectId;

    public Long getMoExpenseObjectId() {
        return moExpenseObjectId;
    }

    public void setMoExpenseObjectId(Long moExpenseObjectId) {
        this.moExpenseObjectId = moExpenseObjectId;
    }

    public String getSqlQuery() {
        return sqlQuery;
    }

    public void setSqlQuery(String sqlQuery) {
        this.sqlQuery = sqlQuery;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
