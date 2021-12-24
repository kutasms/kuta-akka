package com.kuta.base.util;

import java.util.Random;

import com.kuta.base.exception.KutaIllegalArgumentException;

public class KutaRandomUtil {
	/**
	 * generate 'n' bits random digits.
	 * 'n' is between 4 - 20
	 * */
	
	public static String genNBitsDigit(int n) {
		if(n<4 || n> 20) {
			throw new KutaIllegalArgumentException("'n' must between 4 and 20.");
		}
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for(int i=0;i<n;i++) {
			sb.append(random.nextInt(9));
		}
		return sb.toString();
	}
}
