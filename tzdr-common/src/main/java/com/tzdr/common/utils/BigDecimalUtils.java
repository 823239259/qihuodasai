package com.tzdr.common.utils;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhouchen
 * @version 创建时间：2015年1月4日 下午2:15:50 有关 资金计算的 工具类
 */
public class BigDecimalUtils {

	private static Logger log = LoggerFactory.getLogger(BigDecimalUtils.class);
	/**
	 * 由于Java的简单类型不能够精确的对浮点数进行运算，这个工具类提供精 确的浮点数运算，包括加减乘除和四舍五入。
	 */
	// 默认除法运算精度
	private static final int DEF_DIV_SCALE = 10;
	
	// 默认精度
	private static final int SCALE = 2;

	// 这个类不能实例化
	private BigDecimalUtils() {
	}

	/**
	 * 提供精确的加法运算。
	 * 
	 * @param v1
	 *            被加数
	 * @param v2
	 *            加数
	 * @return 两个参数的和
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}
	
	/**
	 * 提供精确的加法运算。
	 * 
	 * @param v1
	 *            被加数
	 * @param v2
	 *            加数
	 * @return 两个参数的和
	 */
	public static double add2(Double v1, Double v2){
		v1 = v1 == null ? 0.00 : v1;
		v2 = v2 == null ? 0.00 : v2;
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}
	
	public static double addRound(Double v1, Double v2){
		v1 = v1 == null ? 0.00 : v1;
		v2 = v2 == null ? 0.00 : v2;
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return round(b1.add(b2).doubleValue(),SCALE);
	}
	
	public static double addRound(Double v1, Double v2,int scale){
		v1 = v1 == null ? 0.00 : v1;
		v2 = v2 == null ? 0.00 : v2;
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return round(b1.add(b2).doubleValue(),scale);
	}



	/**
	 * 提供精确的减法运算。
	 * 
	 * @param v1
	 *            被减数
	 * @param v2
	 *            减数
	 * @return 两个参数的差
	 */
	public static double sub(Double v1, Double v2) {
		v1 = v1 == null ? 0.00 : v1;
		v2 = v2 == null ? 0.00 : v2;
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}
	
	/**
	 * @param v1
	 * @param v2
	 * @return	保留两位小数
	 */
	public static double subRound(Double v1, Double v2) {
		v1 = v1 == null ? 0.00 : v1;
		v2 = v2 == null ? 0.00 : v2;
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return round(b1.subtract(b2).doubleValue(),SCALE);
	}

	public static double subRound(Double v1, Double v2,int scale) {
		v1 = v1 == null ? 0.00 : v1;
		v2 = v2 == null ? 0.00 : v2;
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return round(b1.subtract(b2).doubleValue(),scale);
	}
	/**
	 * 提供精确的乘法运算。
	 * 
	 * @param v1
	 *            被乘数
	 * @param v2
	 *            乘数
	 * @return 两个参数的积
	 */
	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}
	
	public static double mulRound(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return round(b1.multiply(b2).doubleValue(),SCALE);
	}
	
	public static double mulRound(double v1, double v2,int scale) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return round(b1.multiply(b2).doubleValue(),scale);
	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}
	
	public static double divRound(double v1, double v2) {
		return div(v1, v2, SCALE);
	}
	
	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @return 两个参数的商
	 */
	public static double div2(Double v1, Double v2) {
		v1 = v1 == null ? 0.00 : v1; 
		v2 = v2 == null ? 0.00 : v2; 
		return div(v1, v2, DEF_DIV_SCALE);
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static double div(Double v1, Double v2, int scale) {
		if (scale < 0) {
			log.error("The scale must be a positive integer or zero");
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		
		v1 = v1 == null ? 0.00 : v1;
		v2 = v2 == null ? 0.00 : v2;
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double round1(double v, int scale) {
		if (scale < 0) {
			log.error("The scale must be a positive integer or zero");
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double round2(Double v, int scale) {
		v = v == null ? 0.00 : v;
		return round1(v,scale);
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位  必须大于0
	 * @return 四舍五入后的结果
	 */
	public static double round(double v, int scale) {
		if (scale < 0) {
			log.error("The scale must be a positive integer or zero");
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		
		return b.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 提供数字的小数位直接舍去处理。
	 * @param v
	 *            需要小数位后直接舍去的数字
	 * @param scale
	 *            小数点后保留几位  必须大于0
	 * @return 后直接舍去后的结果
	 */
	public static double truncation(Double v, int scale) {
		if (scale < 0) {
			log.error("The scale must be a positive integer or zero");
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		
		return b.setScale(scale, BigDecimal.ROUND_DOWN).doubleValue();
	}
	
	/**
	 * 提供精确的类型转换(Float)
	 * 
	 * @param v
	 *            需要被转换的数字
	 * @return 返回转换结果
	 */
	public static float convertsToFloat(double v) {
		BigDecimal b = new BigDecimal(Double.toString(v));
		return b.floatValue();
	}

	/**
	 * 提供精确的类型转换(Int)不进行四舍五入
	 * 
	 * @param v
	 *            需要被转换的数字
	 * @return 返回转换结果
	 */
	public static int convertsToInt(double v) {
		BigDecimal b = new BigDecimal(Double.toString(v));
		return b.intValue();
	}

	/**
	 * 提供精确的类型转换(Int)进行四舍五入
	 * 
	 * @param v
	 *            需要被转换的数字
	 * @return 返回转换结果
	 */
	public static int convertsToInt2(Double v){
		if(v == null) return 0;
		return new BigDecimal(Double.toString(v)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
	}
	
	/**
	 * 提供精确的类型转换(Long)
	 * 
	 * @param v
	 *            需要被转换的数字
	 * @return 返回转换结果
	 */
	public static long convertsToLong(double v) {
		BigDecimal b = new BigDecimal(Double.toString(v));
		return b.longValue();
	}

	/**
	 * 返回两个数中大的一个值
	 * 
	 * @param v1
	 *            需要被对比的第一个数
	 * @param v2
	 *            需要被对比的第二个数
	 * @return 返回两个数中大的一个值
	 */
	public static double returnMax(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.max(b2).doubleValue();
	}

	/**
	 * 返回两个数中小的一个值
	 * 
	 * @param v1
	 *            需要被对比的第一个数
	 * @param v2
	 *            需要被对比的第二个数
	 * @return 返回两个数中小的一个值
	 */
	public static double returnMin(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.min(b2).doubleValue();
	}

	/**
	 * 精确对比两个数字
	 * 
	 * @param v1
	 *            需要被对比的第一个数
	 * @param v2
	 *            需要被对比的第二个数
	 * @return 如果两个数一样则返回0，如果第一个数比第二个数大则返回1，反之返回-1
	 */
	public static int compareTo(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.compareTo(b2);
	}
	
	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位  必须大于0
	 * @return 四舍五入后的结果
	 */
	public static BigDecimal roundTwo(BigDecimal value) {
		return value.setScale(SCALE, BigDecimal.ROUND_HALF_UP);
	}
}
