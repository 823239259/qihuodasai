package com.tzdr.cms.controller.togetherFuture;

import com.tzdr.business.service.togetherFuture.TogetherFutureService;
import com.tzdr.business.service.togetherTrade.TogetherConfigService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.common.utils.ValidatorUtil;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.web.entity.ComprehensiveCommodityParameter;
import com.tzdr.domain.web.entity.TogetherFuture;
import jodd.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Created by Administrator on 2016/5/17.
 */
@Controller
@RequestMapping(value = "/admin/togetherFuture")
public class TogetherFutureController extends BaseCmsController<TogetherFuture> {

    public static final Logger log = LoggerFactory.getLogger(TogetherFutureController.class);

    @Autowired
    private TogetherFutureService togetherFutureService;

    @Override
    public BaseService<TogetherFuture> getBaseService() {
        return this.togetherFutureService;
    }

    @Override
    public void setResourceIdentity(String resourceIdentity) {
        super.setResourceIdentity("sys:settingParams:togetherFuture");
    }

    @RequestMapping("/list")
    public String list(){
        return ViewConstants.togetherFuture.LIST_VIEW;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult create(@Valid @ModelAttribute("TogetherFuture") TogetherFuture m,BindingResult result) throws Exception {
        //增加权限
        if (permissionList != null) {
            this.permissionList.assertHasCreatePermission();
        }

        if (result.hasErrors())
        {
            return new JsonResult(false, ValidatorUtil.getErrorMessage(result));
        }



        togetherFutureService.create(m);
        return new JsonResult(MessageUtils.message("create.success"));
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult update(@Valid @ModelAttribute("TogetherFuture") TogetherFuture m, BindingResult result) throws Exception {
        //修改权限
        if (permissionList != null) {
            this.permissionList.assertHasUpdatePermission();
        }
        if (result.hasErrors())
        {
            return new JsonResult(false, ValidatorUtil.getErrorMessage(result));
        }
        togetherFutureService.update(m);

        return new JsonResult(MessageUtils.message("update.success"));
    }

    @RequestMapping(value = "/deleted", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult deleted(@RequestParam("id") String id) throws Exception  {
        //刪除权限
        if (permissionList != null) {
            this.permissionList.assertHasDeletePermission();
        }
        TogetherFuture togetherFuture = togetherFutureService.get(id);
        if(null == togetherFuture){
            return new JsonResult(false,"请先选择你要删除的数据！");
        }
        togetherFutureService.delete(togetherFuture);
        return new JsonResult(MessageUtils.message("delete.success"));
    }
}
