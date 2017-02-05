package com.tzdr.olog.aop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;

import com.tzdr.business.cms.service.resource.ResourceService;
import com.tzdr.domain.cms.entity.resource.Resource;





/**
 * 安全框架管理的模块名字映射实现
 * 
 * 加载并缓存所有的模块对象，通过传入的url查询对应的模块名称
 * 
 * @author Lin Feng
 * 
 */
public class ModuleNameMappingResourceFactory implements
		ModuleNameMappingFactory {

	/** 使用Spring容器管理，所以没有使用静态变量 */
	private volatile Map<String, String> moduleNameMapping = null;

	@Autowired
	ResourceService resourceService;

	AntPathMatcher antPathMatcher = new AntPathMatcher();

	@Override
	public String getModuleName(String module) {
		if (moduleNameMapping == null) {
			synchronized (ModuleNameMappingResourceFactory.class) {
				if (moduleNameMapping == null) {
					moduleNameMapping = new HashMap<String, String>();
					List<Resource> resources = resourceService.getAll();
					if (resources != null) {
						for (Resource resource : resources) {
							//resource.getResString()这儿换成了getUrl
							moduleNameMapping.put(resource.getUrl(),
									resource.getName());
						}
					}
				}
			}
		}
		return getMatchName(module);
	}

	private String getMatchName(String module) {
		String moduleName = moduleNameMapping.get(module);
		if (StringUtils.isBlank(moduleName)) {
			String modulePatten = StringUtils.substringBeforeLast(module, "/")
					+ "/*";
			for (Map.Entry<String, String> entry : moduleNameMapping.entrySet()) {
				if (antPathMatcher.match(modulePatten, entry.getKey())) {
					moduleName = entry.getValue();
					break;
				}
			}
		}
		if (StringUtils.isBlank(moduleName)) {
			moduleName = module;
		}
		return moduleName;
	}
}
