package com.tzdr.domain.vo;

import java.io.Serializable;
import java.math.BigInteger;

import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;

/**
 * 
 * <p>设置预警值</p>
 * @author LiuQing
 * @see 
 * @version 2.0
 * 2015年1月12日下午8:47:24
 */
public class QuartzVo implements Serializable {
	
	private static final long serialVersionUID = -6469817179539585307L;
	
	/**
	 * 触发器名称
	 */
	private String triggerName;
	
	/**
	 * 任务名称
	 */
	private String  jobName;

	public String getTriggerName() {
		return triggerName;
	}

	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	
}
