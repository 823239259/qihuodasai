package com.tzdr.cpp;

import java.lang.reflect.Method;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * 系统服务组件Aspect切面Bean
 * @author Shenghany
 * @date 2013-5-28
 */
@Component
public class DataSourceAspect {
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
                       System.out.println(source.value()+"类注解");
                       DynamicDataSourceHolder.setDataSource(source.value());
                   }
                   // 方法注解可以覆盖类型注解
                   Method m = clazz.getMethod(method.getName(), types);
                   if (m != null && m.isAnnotationPresent(DataSource.class)) {
                       DataSource source = m.getAnnotation(DataSource.class);
                       System.out.println(source.value()+"方法");
                       DynamicDataSourceHolder.setDataSource(source.value());
                   }
              } catch (Exception e) {
                  System.out.println(clazz + ":" + e.getMessage());
               }
           }
}
