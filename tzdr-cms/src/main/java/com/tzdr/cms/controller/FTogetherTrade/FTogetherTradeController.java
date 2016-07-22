package com.tzdr.cms.controller.FTogetherTrade;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.ObjectUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.service.togetherFuture.FTogetherConjunctureService;
import com.tzdr.business.service.togetherFuture.FTogetherRecordService;
import com.tzdr.business.service.togetherFuture.FTogetherTradeService;
import com.tzdr.business.service.togetherFuture.TogetherFutureService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.cms.utils.WebUtil;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.EasyuiUtil;
import com.tzdr.common.web.support.EasyUiPageData;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.app.vo.FTogetherTradeWebVo;
import com.tzdr.domain.constants.Constant;
import com.tzdr.domain.vo.FConjunctureVo;
import com.tzdr.domain.vo.TogetherConjunctureVo;
import com.tzdr.domain.web.entity.FTogetherConjuncture;
import com.tzdr.domain.web.entity.FTogetherRecord;
import com.tzdr.domain.web.entity.FTogetherTrade;
import com.tzdr.domain.web.entity.TogetherFuture;

/**
 * Created by huangkai on 2016/5/20.
 */
@Controller
@RequestMapping("/admin/fTogetherTrade")
public class FTogetherTradeController extends BaseCmsController<FTogetherTrade> {

    @Autowired
    private FTogetherTradeService fTogetherTradeService;

    @Autowired
    private TogetherFutureService togetherFutureService;

    @Autowired
    private FTogetherConjunctureService fTogetherConjunctureService;

    @Autowired
    private FTogetherRecordService fTogetherRecordService;
    
    
    @Override
    public BaseService<FTogetherTrade> getBaseService() {
        return this.fTogetherTradeService;
    }

    @Override
    public void setResourceIdentity(String resourceIdentity) {
        super.setResourceIdentity("sys:riskmanager:fTogetherTrade");
    }

    /**
     * 立即退款
     * @param request
     * @param id
     * @return
     */
	@RequestMapping(value="/instantRefund")
	@ResponseBody
	public JsonResult instantRefund(HttpServletRequest request,@RequestParam("id") String  id){
		FTogetherTrade  fTogetherTrade = fTogetherTradeService.get(id);
		if (ObjectUtil.equals(null, fTogetherTrade)){
			return  new JsonResult(false,"未找到对应的方案，无法退款！");
		}
		if (fTogetherTrade.getStatus()==Constant.FtogetherGame.END_STATUS){
			return  new JsonResult(false,"方案已结算，不能再进行退款操作！");
		}
		if (Dates.getCurrentLongDate()+Constant.FtogetherGame.FIVE_MINITE_SEC < fTogetherTrade.getOpenTime()){
			return  new JsonResult(false,"还未到操盘准备时间，请勿乱来哟！请在开盘前5分钟进行退款操作！");
		}
		fTogetherTradeService.refundNotFullCopies(fTogetherTrade);
		
		return  new JsonResult("退款成功！");
	}
    
	  /**
     * 立即结算
     * @param request
     * @param id
     * @return
     */
	@RequestMapping(value="/instantSettle")
	@ResponseBody
	public JsonResult instantSettle (HttpServletRequest request,@RequestParam("id") String  id){
		FTogetherTrade  fTogetherTrade = fTogetherTradeService.get(id);
		if (fTogetherTrade.getStatus()==Constant.FtogetherGame.END_STATUS){
			return  new JsonResult(false,"方案已结算，不能重复操作！");
		}
	/*	
		if (ObjectUtil.equals(null, fTogetherTrade.getCallOpenPoint()) 
				|| ObjectUtil.equals(null, fTogetherTrade.getCallClosePoint()) 
				|| ObjectUtil.equals(null, fTogetherTrade.getPutOpenPoint()) 
				|| ObjectUtil.equals(null, fTogetherTrade.getPutClosePoint()) 
		){
			return  new JsonResult(false,"行情点数未录入完整，无法完成结算操作！");
		}*/
		
		fTogetherTradeService.instantSettle(fTogetherTrade);
		return  new JsonResult("结算成功！");
	}

    /**
     *  含参数的搜索  search_EQ_name
     * @param easyUiPage
     * @param model
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/getDatas", method = RequestMethod.POST)
    @ResponseBody
    public Object easyuiPage(EasyUiPageInfo easyUiPage, Model model,
                             HttpServletRequest request,HttpServletResponse response) throws Exception{

        //查看权限
        if (permissionList != null) {
            this.permissionList.assertHasViewPermission();
        }
		Map<String, Boolean> sortMap = EasyuiUtil.getSortMap(easyUiPage);
        //获取模糊搜索参数
        Map<String, Object> searchParams = EasyuiUtil.getParametersStartingWith(request,EasyuiUtil.SEARCH_PREFIX);
        PageInfo<Object> pageInfo = fTogetherTradeService.queryTogetherTradeList(easyUiPage, searchParams);

        return new EasyUiPageData(pageInfo);
    }


    @RequestMapping(value = "/list")
    public String list(Model model,HttpServletRequest request){
    	List<TogetherFuture> configs = togetherFutureService.getAll();
    	request.setAttribute("configs",configs);
        return ViewConstants.togetherFuture.TRADE_LIST;
    }


    @ResponseBody
    @RequestMapping("/add")
    public JsonResult add(String configID,String name,String openTime,Integer operateTime,HttpServletResponse resp ){
    	JsonResult jsonResult = new JsonResult();
        TogetherFuture together = togetherFutureService.get(configID);
        
        if(null == together || null == openTime || null == name || null == operateTime ){

          jsonResult.setMessage("找不到参数");
          return jsonResult;
        }
        long openTimes = 0;
       
        Date dates = Dates.parse(openTime,"yyyy-MM-dd HH:mm");
        openTimes = dates.getTime()/1000;
    
        String success=fTogetherTradeService.add(together, name, openTimes, operateTime);
        jsonResult.setMessage(success);
        return jsonResult;
    }

    @RequestMapping(value="/edit",method={RequestMethod.GET,RequestMethod.POST})
    public void edit(String name,String id,String openTime,Integer operateTime,HttpServletResponse resp ){
    	if(id != null && name != null && openTime != null && operateTime != null ){
    		FTogetherTradeWebVo fTogetherTradeweb = fTogetherTradeService.findTradeById(id);
    		if (Constant.FtogetherGame.JOINT_PURCHASE_STATUS != fTogetherTradeweb.getStatus()){
    			WebUtil.printText("不好意思，只有合买中的方案才能进行修改！",resp);
    			return ;
    		}
    		FTogetherTrade  fTogetherTrade = fTogetherTradeService.get(id);
    		 
	        	long openTimes = 0;
	            Date dates = Dates.parse(openTime,"yyyy-MM-dd HH:mm");
	            openTimes = dates.getTime()/1000;
    	        Long now = new Date().getTime()/1000;
    	        if(openTimes<now){
    	        	WebUtil.printText("时间不能小于当前时间", resp);
    	        	return;
    	        }
    	        fTogetherTrade.setName(name);
    	        fTogetherTrade.setOpenTime(openTimes);
    	        if(operateTime != fTogetherTrade.getOperateTime()){
    	        	fTogetherTrade.setOperateTime(operateTime);
	        		fTogetherTradeService.updateByTime(fTogetherTrade);
    	        }else {
					fTogetherTradeService.update(fTogetherTrade);
				}

    			WebUtil.printText("success",resp);
    	}else {
    		WebUtil.printText("找不到该方案参数",resp);
		}
    }

    @ResponseBody
    @RequestMapping(value="/getValue",method={RequestMethod.GET,RequestMethod.POST})
    public Map<String, Object> getValue(String id){
    	FTogetherTrade fTogetherTrade = fTogetherTradeService.get(id);
    	Map<String, Object> map = new HashMap<String, Object>();
    	if(fTogetherTrade == null){
    		map.put("status", "false");
    		return map;
    	}
    	map = fTogetherTradeService.getTradeValue(fTogetherTrade);
    	map.put("status", "true");
    	return map;

    }


    @RequestMapping(value = "/deleted", method = RequestMethod.POST)
    public void deleted(@RequestParam("id") String id,HttpServletResponse response) throws Exception  {
        //刪除权限
        if (permissionList != null) {
            this.permissionList.assertHasDeletePermission();
        }

       List<FTogetherRecord>  records =  fTogetherRecordService.findByTradeId(id);
       if (!CollectionUtils.isEmpty(records)){
           WebUtil.printText("已有用户合买，无法删除！",response);
           return;
       }
        getBaseService().removeById(id);
        WebUtil.printText("success",response);
    }



    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/ProfitValue", method=RequestMethod.POST)
    @ResponseBody
    public Object easyuiPages(EasyUiPageInfo easyUiPage,
                             HttpServletRequest request,HttpServletResponse response) throws Exception{

        //查看权限
        if (permissionList != null) {
            this.permissionList.assertHasViewPermission();
        }
        //获取模糊搜索参数
        Map<String, Object> searchParams = EasyuiUtil.getParametersStartingWith(request,EasyuiUtil.SEARCH_PREFIX);
        PageInfo<Object> pageInfo = fTogetherTradeService.getProfitValue(easyUiPage, searchParams);

        return new EasyUiPageData(pageInfo);
    }
    /**
     * 根据方案id得到行情
     * @param tradeId
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/getMarket",method=RequestMethod.POST)
    public JsonResult getMarket(String tradeId){
    	JsonResult jsonResult = new JsonResult(Boolean.FALSE);
    	if(tradeId == null){
    		jsonResult.setMessage("找不到该方案");
    		return jsonResult;
    	}
    	FTogetherTrade fTogetherTrade = fTogetherTradeService.get(tradeId);
    	TogetherConjunctureVo vo =fTogetherTradeService.getMarke(fTogetherTrade);
    	jsonResult.setObj(vo);
    	jsonResult.setSuccess(true);
    	return jsonResult;
    }
    @ResponseBody
    @RequestMapping(value = "/saveMarket",method = {RequestMethod.GET,RequestMethod.POST})
    public JsonResult saveMarket(@RequestBody TogetherConjunctureVo markets,
                           HttpServletResponse resp) {
        JsonResult jsonResult = new JsonResult(Boolean.FALSE);
        if(null == markets.getTradeId()){
            WebUtil.printText("找不到该方案", resp);
        }
        FTogetherTrade fTogetherTrade = fTogetherTradeService.get(markets.getTradeId());
        if (fTogetherTrade != null) {


        	
            
                fTogetherTrade.setCallClosePoint(markets.getCallClosePoint());
            
            
                fTogetherTrade.setCallOpenPoint(markets.getCallOpenPoint());
            
            
                fTogetherTrade.setPutClosePoint(markets.getPutClosePoint());
           
            
                fTogetherTrade.setPutOpenPoint(markets.getPutOpenPoint());
            
            for (FConjunctureVo conjunctureVo : markets.getConjuns()) {
                FTogetherConjuncture ftc = fTogetherConjunctureService.get(conjunctureVo.getId());
                if (ftc.getMinutes().equals(conjunctureVo.getMinutes())) {
                    ftc.setPoint(conjunctureVo.getPoint());
                }
                fTogetherConjunctureService.update(ftc);
            }
            fTogetherTradeService.update(fTogetherTrade);
            jsonResult.setSuccess(true);
            return jsonResult;
        }else {
           jsonResult.setMessage("找不到行情参数");
            return jsonResult;
        }
    }
}
