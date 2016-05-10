package com.tzdr.common;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

/**
 *
 * @author Lin Feng
 * @date 2014年11月30日
 */
public class UUIDTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BigInteger count= new BigInteger("10");
		BigInteger countAll= new BigInteger("20");
		BigDecimal countb= new BigDecimal(String.valueOf(count));
		BigDecimal countAllb= new BigDecimal(String.valueOf(countAll));
		
		BigDecimal i=countb.divide(countAllb);
		System.out.print(i);
		

	}

}
