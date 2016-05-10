package com.tzdr.common.web.support;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * 动态代理对象的JsonMapper 解决在Json解析Hibernate生成的延迟加载动态代理对象中生成的部分属性不可以用反射访问的问题。
 * 
 * @author Lin Feng
 * 
 */
public class DynamicProxyObjectMapper extends ObjectMapper {

	private static final long serialVersionUID = 6931339368333683269L;

	private boolean prettyOutput = false;
	// WRITE_NULL_MAP_VALUES
	private boolean nullOutput = true;

	public DynamicProxyObjectMapper() {
		super();
		disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
	}

	public boolean isPrettyOutput() {
		return prettyOutput;
	}

	public void setPrettyOutput(boolean isPrettyOutput) {
		this.prettyOutput = isPrettyOutput;
		if (isPrettyOutput) {
			enable(SerializationFeature.INDENT_OUTPUT);
		} else {
			disable(SerializationFeature.INDENT_OUTPUT);
		}
	}

	public boolean isNullOutput() {
		return nullOutput;
	}

	public void setNullOutput(boolean nullOutput) {
		this.nullOutput = nullOutput;
		if (nullOutput) {
			setSerializationInclusion(Include.NON_DEFAULT);
		} else {
			setSerializationInclusion(Include.NON_EMPTY);
		}
	}

}
