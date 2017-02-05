package com.tzdr.cms.controller;

import com.tzdr.business.service.generalize.GeneralizeChannelService;
import com.tzdr.business.service.generalize.GeneralizeService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.cms.utils.WebUtil;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.EasyuiUtil;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.common.utils.ValidatorUtil;
import com.tzdr.common.web.support.EasyUiPageData;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.web.entity.GeneralizeChannel;

import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/13.
 */
@Controller
@RequestMapping(value = "/admin/channelPromotion")
public class ChannelPromotionController extends BaseCmsController<GeneralizeChannel> {

    public static final Logger log = LoggerFactory.getLogger(ChannelPromotionController.class);

    @Autowired
    private GeneralizeChannelService generalizeService;

    @Override
    public BaseService<GeneralizeChannel> getBaseService() {
        return generalizeService;
    }

    public void setResourceIdentity() {
        super.setResourceIdentity("sys:settingParams:channelPromotion");
    }

    @RequestMapping(value = "/list",method = {RequestMethod.GET,RequestMethod.POST})
    public String list(Model model){
        return ViewConstants.channelPromotion.LIST_VIEW;
    }
    
    @RequestMapping(value = "/getData",method = RequestMethod.POST)
    @ResponseBody
    public Object getData(EasyUiPageInfo easyUiPage, Model model, ServletRequest request){

        //查看权限
        if (permissionList != null) {
            this.permissionList.assertHasViewPermission();
        }

        //获取模糊搜索参数
        Map<String, Object> searchParams = EasyuiUtil.getParametersStartingWith(request, EasyuiUtil.SEARCH_PREFIX);
        //排序设置
        EasyuiUtil.getMultilistSortMap(easyUiPage);

        PageInfo<Object> pageInfo =generalizeService .queryAllTrades(easyUiPage, searchParams);

        return new EasyUiPageData(pageInfo);
    }

    /**
     * 判断代码是否为空
     * @param parms
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/isRepeat")
    public Boolean isRepeat(String parms){
        Boolean isRepeat = generalizeService.isRepeat(parms);
        return isRepeat;
    }


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult create(@Valid @ModelAttribute("GeneralizeChannel") GeneralizeChannel m,BindingResult result) throws Exception {
        //增加权限
        if (permissionList != null) {
            this.permissionList.assertHasCreatePermission();
        }
        if (result.hasErrors())
        {
            return new JsonResult(false, ValidatorUtil.getErrorMessage(result));
        }
        if(generalizeService.isRepeat(m.getParam())){
        	return new JsonResult(false, "代码重复");
        }else{
        	 generalizeService.create(m);
             return new JsonResult(MessageUtils.message("create.success"));
        }
       
    }

    @ResponseBody
    @RequestMapping(value = "/typeList")
    public JsonResult typeList(String name,Integer type){
        JsonResult jsonResult = new JsonResult(false);
        List<String> list = generalizeService.getType(name,type);
        jsonResult.setObj(list);
        return jsonResult;
    }

    @ResponseBody
    @RequestMapping(value = "/getMap")
    public JsonResult getMap(){
        JsonResult jsonResult = new JsonResult();
        Map<String,List<String>> map = generalizeService.getList();
        jsonResult.setObj(map);
        return jsonResult;
    }

   
    @RequestMapping(value = "/deleteChannel", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult deleteChannel(HttpServletResponse response,HttpServletRequest request,GeneralizeChannel gc){
    	try {
    		if(StringUtil.isBlank(gc.getId())){
    			return new JsonResult(false,"请先选择你要删除的数据！");
    		}
    		return generalizeService.delete(gc);
    	} catch (Exception e) {
    		e.printStackTrace();
    		log.error("删除一条推广渠道信息：",e);
    		return new JsonResult(false,"服务器忙！");
    	}
    }
    
}
