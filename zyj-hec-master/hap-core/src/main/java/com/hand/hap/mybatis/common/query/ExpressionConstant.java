package com.hand.hap.mybatis.common.query;

/**
 * {@link Where}注解的 Expression 表达式所需常量
 *
 * @author shira 2019/03/20 18:41
 */
public class ExpressionConstant {

	/**
	 * 本表别名占位符的正则表达式，默认本表别名为a
	 */
	public static final String TABLE_ALIAS_REG="(\\ba\\.)|(\\bA\\.)|(\\bA0\\.)|(\\ba0\\.)";

	/**
	 * 变量的正则表达式 ： 匹配 #{companyId} 不匹配#{dto.companyId}
	 */
	public static final String VARIABLE_REG="(#\\{)(?!dto\\.)(?=[^\\}]*\\})";

	/**
	 * 本表别名占位符，默认本表别名为a
	 */
	public static final String PLACEHOLDER_DTO="#{dto.";

	/**
	 * Expression 表达式以and 开头（不区分大小写）
	 */
	public static final String AND="and ";

	/**
	 * Expression 表达式以or 开头（不区分大小写）
	 */
	public static final String OR="or ";





}
