package com.kuta.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间处理工具
 * */
public class KutaTimeUtil {
	/**
	 * 计算单位
	 * */
	public enum Unit{
		y,M,d,H,m,s,S,n
	}
	
	/**
	 * 带3位毫秒的时间格式化器
	 * */
	private static final SimpleDateFormat formatterWithMill = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	/**
	 * 不带毫秒的时间格式化器
	 * */
	private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 格式化时间
	 * @param date 时间
	 * @return String类型的时间
	 * */
	public static String format(Date date) {
		if(KutaUtil.isValueNull(date)) {
			return null;
		}
		return formatter.format(date);
	}
	/**
	 * 将string类型的时间转换为Date类型
	 * @param s string类型的时间
	 * @return Date类型的时间
	 * @throws ParseException 转换异常时抛出
	 * */
	public static Date parse(String s) throws ParseException {
		if(KutaUtil.isValueNull(s)) {
			return null;
		}
		return formatter.parse(s);
	}
	/**
	 * 将string类型带毫秒的的时间转换为Date类型
	 * @param s string类型的时间
	 * @return Date类型的时间
	 * @throws ParseException 转换异常时抛出
	 * */
	public static Date parseWithMill(String s) throws ParseException {
		if(KutaUtil.isValueNull(s)) {
			return null;
		}
		try {
		return formatterWithMill.parse(s);
		}catch (ParseException e) {
			return formatter.parse(s);
		}
	}
	/**
	 * 格式化带毫秒的时间
	 * @param date 时间
	 * @return String类型的时间
	 * */
	public static String formatWithMill(Date date) {
		if(KutaUtil.isValueNull(date)) {
			return null;
		}
		return formatterWithMill.format(date);
	}
	
	/**
	 * 以start为起点增加指定数量和单位的时间
	 * @param start 起点
	 * @param quantity 增加数量
	 * @param unitStr 单位
	 * @return 计算后的时间
	 * */
	public static Date toDate(Date start,Integer quantity, String unitStr) {
		if(KutaUtil.isEmptyString(unitStr)) {
			return null;
		}
		try {
		Unit unit = Unit.valueOf(unitStr);
		return toDate(start, quantity, unit);
		} catch (IllegalArgumentException e) {
			// TODO: handle exception
			return null;
		}
	}
	/**
	 * 以start为起点增加指定数量和单位的时间
	 * @param start 起点
	 * @param quantity 增加数量
	 * @param unitStr 单位
	 * @return 计算后的时间
	 * */
	public static Date toDate(Date start,Integer quantity, Unit unit) {
		Date now = KutaUtil.isValueNull(start) ? new Date() : start;
		Calendar cd = Calendar.getInstance();
		cd.setTime(now);
		switch (unit) {
		case y:
			cd.add(Calendar.YEAR, quantity);
			break;
		case M:
			cd.add(Calendar.MONTH, quantity);
			break;
		case d:
			cd.add(Calendar.DATE, quantity);
			break;
		case H:
			cd.add(Calendar.HOUR, quantity);
			break;
		case m:
			cd.add(Calendar.MINUTE, quantity);
			break;
		case s:
			cd.add(Calendar.SECOND, quantity);
			break;
		case S:
			cd.add(Calendar.MILLISECOND, quantity);
			break;
		case n:
			break;
		default:
			break;
		}
		return cd.getTime();
	}
	
	/**
	 * 获取两个时间段的交集之间的时间长度
	 * @param start1 起始时间1
	 * @param start2 起始时间2
	 * @param end1 结束时间1
	 * @param end2 结束时间2
	 * @return 毫秒数
	 * */
	public static Long mixed(Date start1,Date start2,Date end1, Date end2) {
		Date start = start1.getTime() > start2.getTime() ? start1 : start2;
		Date end = end1.getTime() > end2.getTime() ? end2 : end1;
		return end.getTime() - start.getTime();
	}
	
	/**
	 * 获取两个时间段的交集之间的时间长度
	 * @param start1 起始时间1
	 * @param start2 起始时间2
	 * @param end1 结束时间1
	 * @param end2 结束时间2
	 * @return 毫秒数
	 * */
	public static Long mixed(long start1,long start2,long end1, long end2) {
		long start = start1 > start2 ? start1 : start2;
		long end  = end1 > end2 ? end2 : end1;
		return end - start;
	}
}
