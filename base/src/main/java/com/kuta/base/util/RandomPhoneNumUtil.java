package com.kuta.base.util;

/**
 * 随机电话号码生成工具
 * */
public class RandomPhoneNumUtil {

	/**
	 * 获取一个随机的电话号码
	 * @return 随机电话号码
	 * */
	public static String getPhoneNum() {
		// 给予真实的初始号段，号段是在百度上面查找的真实号段
		String[] start = { "133", "149", "153", "173", "177", "180", "181", "189", "199", "130", "131", "132", "145",
				"155", "156", "166", "171", "175", "176", "185", "186", "166", "134", "135", "136", "137", "138", "139",
				"147", "150", "151", "152", "157", "158", "159", "172", "178", "182", "183", "184", "187", "188", "198",
				"170", "171" };

		// 随机出真实号段 使用数组的length属性，获得数组长度，
		// 通过Math.random（）*数组长度获得数组下标，从而随机出前三位的号段
		String phoneFirstNum = start[(int) (Math.random() * start.length)];
		// 随机出剩下的8位数
		String phoneLastNum = "";
		// 定义尾号，尾号是8位
		final int LENPHONE = 8;
		// 循环剩下的位数
		for (int i = 0; i < LENPHONE; i++) {
			// 每次循环都从0~9挑选一个随机数
			phoneLastNum += (int) (Math.random() * 10);
		}
		// 最终将号段和尾数连接起来
		String phoneNum = phoneFirstNum + phoneLastNum;
		return phoneNum;
	}
}
