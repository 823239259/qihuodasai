package com.tzdr.common.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * @Description: TODO(字符串处理类)
 * @ClassName: SysStringUtils
 * @author wangpinqun
 * @date 2015年1月4日 下午4:30:57
 */
public class SysStringUtils {

	/**
	 * @Description: TODO(时间段问候)
	 * @Title: sayHello
	 * @return
	 * @return int    返回类型  0：晚上 1：早上 2：上午 3：中午 4：下午
	 */
	public static int sayHelloTime(){
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		if(hour >= 6 && hour < 8){          // 6:00~7:59  早上
			return 1;
		}else if(hour >= 8 && hour < 11){   // 6:00~7:59  上午
			return 2;
		}else if(hour >= 11 && hour < 13){  // 6:00~7:59  中午
			return 3;
		}else if(hour >= 13 && hour < 18){  // 6:00~7:59  下午
			return 4;
		}else{   // 6:00~7:59  晚上
			return 0;
		}
	}
	
}
