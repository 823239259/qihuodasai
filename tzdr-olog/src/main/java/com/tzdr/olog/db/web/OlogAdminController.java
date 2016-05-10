package com.tzdr.olog.db.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.web.BaseController;

@Controller
@RequestMapping(value = "/admin/system/olog")
public class OlogAdminController extends BaseController {

	@Override
	public BaseService getBaseService() {
		return null;
	}
	//空着为后面写log web
}
