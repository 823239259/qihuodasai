package com.tzdr.cms.controller.ComprehensiveCommodity;

import com.tzdr.business.service.comprehensiveCommodity.ComprehensiveCommodityPriceService;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.common.utils.ValidatorUtil;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.web.entity.ComprehensiveCommodityPrice;
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
 * @Date 2016-03-29
 */
@Controller
@RequestMapping("/admin/Comprehensive_Commodity")
public class ComprehensiveCommodityPriceController extends BaseCmsController<ComprehensiveCommodityPrice> {

    @Autowired
    private ComprehensiveCommodityPriceService comprehensiveCommodityPriceService;

    @Override
    public BaseService<ComprehensiveCommodityPrice> getBaseService() {
        return comprehensiveCommodityPriceService;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult update(@Valid @ModelAttribute("ComprehensiveCommodityPrice") ComprehensiveCommodityPrice m, BindingResult result) throws Exception {
        //修改权限
        if (permissionList != null) {
            this.permissionList.assertHasUpdatePermission();
        }
        if (result.hasErrors())
        {
            return new JsonResult(false, ValidatorUtil.getErrorMessage(result));
        }
        comprehensiveCommodityPriceService.edit(m);

        return new JsonResult(MessageUtils.message("update.success"));
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult create(@Valid @ModelAttribute("ComprehensiveCommodityPrice") ComprehensiveCommodityPrice m,BindingResult result) throws Exception {
        //增加权限
        if (permissionList != null) {
            this.permissionList.assertHasCreatePermission();
        }

        if (result.hasErrors())
        {
            return new JsonResult(false, ValidatorUtil.getErrorMessage(result));
        }



        comprehensiveCommodityPriceService.create(m);
        return new JsonResult(MessageUtils.message("create.success"));
    }
}
