package com.tzdr.cpp;

import java.lang.reflect.Method;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 系统服务组件Aspect切面Bean
 * @author Shenghany
 * @date 2013-5-28
 */
@Component
public class DataSourceAspect {
	   private static final Logger logger = LoggerFactory.getLogger(DataSourceAspect.class);
			/**
	       * 拦截目标方法，获取由@DataSource指定的数据源标识，设置到线程存储中以便切换数据源
	       * 
	      * @param point
	       * @throws Exception
	       */
	      public void intercept(JoinPoint point) throws Exception {
	    	  System.out.println("拦截访问，设置数据源");
	         Class<?> target = point.getTarget().getClass();
	         MethodSignature signature = (MethodSignature) point.getSignature();
	         // 默认使用目标类型的注解，如果没有则使用其实现接口的注解
	         for (Class<?> clazz : target.getInterfaces()) {
	             resolveDataSource(clazz, signature.getMethod());
	         }
	         resolveDataSource(target, signature.getMethod());
	     }
      		 /**
            * 提取目标对象方法注解和类型注解中的数据源标识
            * 
            * @param clazz
            * @param method
            */
           private void resolveDataSource(Class<?> clazz, Method method) {
               try {
                   Class<?>[] types = method.getParameterTypes();
                   // 默认使用类型注解
                   if (clazz.isAnnotationPresent(DataSource.class)) {
                       DataSource source = clazz.getAnnotation(DataSource.class);
                       DynamicDataSourceHolder.setDataSource(source.value());
                   }
                   // 方法注解可以覆盖类型注解
                   Method m = clazz.getMethod(method.getName(), types);
                   if (m != null && m.isAnnotationPresent(DataSource.class)) {
                       DataSource source = m.getAnnotation(DataSource.class);
                       DynamicDataSourceHolder.setDataSource(source.value());
                   }
              } catch (Exception e) {
            	  logger.info(clazz + ":" + e.getMessage());
               }
           }
           /**
            * 当执行CPP下CRUD完成时，设置为默认数据源（如果不指定，虽然在jpa中配置了默认数据源，在数据源切换时会出现切换失效）
            */
           public void resolveDefaultDataSource(){
        	   try {
				DynamicDataSourceHolder.setDataSource("dataSource");
			} catch (Exception e) {
				logger.info("设置默认数据源异常：" + e);
			}
           }
}
