package com.tzdr.cms.controller.ComprehensiveCommodity;

import com.tzdr.business.service.comprehensiveCommodity.ComprehensiveCommodityParameterService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.common.utils.ValidatorUtil;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.web.entity.ComprehensiveCommodityParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * @description
 * @Author huangkai
 * @Date 2016-03-28
 */
@Controller
@RequestMapping("/admin/ComprehensiveCommodity")
public class ComprehensiveCommodityParameterController extends BaseCmsController<ComprehensiveCommodityParameter> {

    @Autowired
    private ComprehensiveCommodityParameterService comprehensiveCommodityService;

    @RequestMapping(value = "/setting_parameter",method = {RequestMethod.POST,RequestMethod.GET})
    public String settingParameter(){
        return ViewConstants.ComperhensiveCommodity.SETTING_PARAMS;
    }

    @Override
    public BaseService<ComprehensiveCommodityParameter> getBaseService() {
        return comprehensiveCommodityService;
    }

    @Override
    public void setResourceIdentity(String resourceIdentity) {
        super.setResourceIdentity("sys:settingParams:comprehensiveCommodity");
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult update(@Valid @ModelAttribute("ComprehensiveCommodityParameter") ComprehensiveCommodityParameter m, BindingResult result) throws Exception {
        //修改权限
        if (permissionList != null) {
            this.permissionList.assertHasUpdatePermission();
        }
        if (result.hasErrors())
        {
            return new JsonResult(false, ValidatorUtil.getErrorMessage(result));
        }
       comprehensiveCommodityService.edit(m);

        return new JsonResult(MessageUtils.message("update.success"));
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult create(@Valid @ModelAttribute("ComprehensiveCommodityParameter") ComprehensiveCommodityParameter m,BindingResult result) throws Exception {
        //增加权限
        if (permissionList != null) {
            this.permissionList.assertHasCreatePermission();
        }

        if (result.hasErrors())
        {
            return new JsonResult(false, ValidatorUtil.getErrorMessage(result));
        }



        comprehensiveCommodityService.create(m);
        return new JsonResult(MessageUtils.message("create.success"));
    }
}
