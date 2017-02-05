package com.tzdr.common.utils.interceptor;

import java.util.concurrent.ConcurrentHashMap;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * spring 性能监控
 * @ClassName PerfInterceptor
 * @author L.Y
 * @email liuyuan@peigubao.com
 * @date 2015年5月28日
 */
public class PerfInterceptor implements MethodInterceptor {
 
    Logger logger = LoggerFactory.getLogger(PerfInterceptor.class.getName());
    private static ConcurrentHashMap<String, MethodStats> methodStats = new ConcurrentHashMap<String, MethodStats>();
    //每个方法调用 做一次记录
    private static long statLogFrequency = 1;
    //方法访问时间警告阀值
    private static long methodWarningThreshold = 1000;
    
    @Override
    public Object invoke(MethodInvocation method) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            return method.proceed();
        } finally {
            updateStats(method.getMethod().getDeclaringClass().getSimpleName() + ":"+ method.getMethod().getName(),
            		(System.currentTimeMillis() - start));
        }
    }
 
    /**
     * 统计
     * @MethodName updateStats
     * @author L.Y
     * @date 2015年5月28日
     * @param methodName 类名 + 方法名
     * @param elapsedTime 执行时间
     */
    private void updateStats(String methodName, long elapsedTime) {
        try {
			MethodStats stats = methodStats.get(methodName);
			if (null == stats) {
				stats = new MethodStats(methodName);
				methodStats.put(methodName, stats);
			}
			stats.count++;
			stats.totalTime += elapsedTime;
			if (elapsedTime > stats.maxTime) {
				stats.maxTime = elapsedTime;
			}
			//方法响应时间超过1000ms的时候给出警告
			if (elapsedTime > methodWarningThreshold) {
				logger.warn("method warning: " + methodName + "(), cnt = "
						+ stats.count + ", lastTime = " + elapsedTime
						+ ", maxTime = " + stats.maxTime);
			}
			if (stats.count % statLogFrequency == 0) {
				long avgTime = stats.totalTime / stats.count;
				long runningAvg = (stats.totalTime - stats.lastTotalTime)
						/ statLogFrequency;
				logger.info("method: " + methodName + "(), cnt = "
						+ stats.count + ", lastTime = " + elapsedTime
						+ ", avgTime = " + avgTime + ", runningAvg = "
						+ runningAvg + ", maxTime = " + stats.maxTime);

				//reset the last total time
				stats.lastTotalTime = stats.totalTime;
			}
		} catch (Exception e) {
			logger.warn("spring 性能监控故障：" + e.getMessage());
		}
    }
    
    class MethodStats {
    	/**
    	 * 方法名
    	 */
        public String methodName;
        /**
         * 执行次数
         */
        public long count;
        /**
         * 总时间
         */
        public long totalTime;
        /**
         * 上一次执行结束总时间
         */
        public long lastTotalTime;
        /**
         * 执行最大时间
         */
        public long maxTime;
        
        public MethodStats(String methodName) {
            this.methodName = methodName;
        }
    } 
}