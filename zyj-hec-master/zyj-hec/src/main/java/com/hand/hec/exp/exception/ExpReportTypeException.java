package com.hand.hec.exp.exception;

import com.hand.hap.core.exception.BaseRuntimeExcepiton;

/**
 * 报销单类型相关异常信息类
 *
 * @author yang.duan 2019/03/29 16:54
 */
public class ExpReportTypeException extends BaseRuntimeExcepiton {

    public static final String CODE_EXCEPTION = "EXP4060";

    //费用报销单类型重复,请重新定义!
    public static String EXP_REPORT_TYPE_DUP_ERROR = "exp_report_type_dup_error";

    //存在相同的页面元素记录，请检查!
    public static String EXP_REP_TYPE_ELE_DUP_ERROR = "exp_rep_type_ele_dup_error";

    //不能为该费用报销单指定重复的员工组!
    public static String EXP_REP_TYPE_EMP_GP_DUP_ERROR = "exp_rep_type_emp_gp_dup_error";

    //维度已分配至页面元素中，请确认！
    public static String EXP_REP_TYPE_DIM_EXIST_ERROR = "exp_rep_type_dim_exist_error";

    //不能为该费用报销单类型指定重复的维度!
    public static String EXP_REP_TYPE_DIM_DUP_ERROR = "exp_rep_type_dim_dup_error";

    //指定的维度布局顺序重复,请重新选择!
    public static String EXP_REP_TYPE_ELE_DIM_LAY_DUP_ERROR = "exp_rep_type_ele_dim_lay_dup_error";

    public static String EXP_REP_TYPE_HEADER_DIM_EXIST_ERROR = "exp_rep_type_header_dim_exist_error";

    //该费用对象类型已分配到对应的行费用对象中,且处于启用状态,请确认!
    public static String EXP_REP_TYPE_LINE_OBJECT_EXIST_ERROR = "exp_rep_type_line_object_exist_error";

    //不能为该费用报销单类型指定重复的费用对象!
    public static String EXP_REP_TYPE_OBJECT_DUP_ERROR = "exp_rep_type_object_dup_error";

    //指定的费用对象布局顺序重复,请重新选择!
    public static String EXP_REP_TYPE_OBJECT_LAY_ERROR = "exp_rep_type_object_lay_error";
    //该报销单类型必须存在一个启用的默认付款用途!
    public static String EXP_REP_TYPE_PAY_USEDE_DEFAULT_ERROR = "exp_rep_type_pay_usede_default_error";

    //该公司已分配,请检查!
    public static String EXP_REP_TYPE_COMPANY_EXIST_ERROR = "exp_rep_type_company_exist_error";

    //该费用对象类型已分配到对应的头费用对象中,且处于启用状态,请确认!
    public static String EXP_REP_TYPE_HEADER_OBJECT_EXIST_ERROR = "exp_rep_type_header_object_exist_error";

    //费用报销单单据类型-页面元素只允许包含一个表单头！
    public static String EXP_MO_REP_TYPE_REF_HEADER_DUPLICATE_ERROR = "exp_mo_rep_type_ref_header_duplicate_error";



    public ExpReportTypeException(String descriptionKey, Object[] parameters) {
        super(CODE_EXCEPTION, descriptionKey, parameters);
    }
}
