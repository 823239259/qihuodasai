package com.tzdr.cms.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.cms.service.user.PasswordService;
import com.tzdr.business.cms.service.user.UserExtendService;
import com.tzdr.business.cms.service.user.UserService;
import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.business.service.pay.UserFundService;
import com.tzdr.business.service.userTrade.UserTradeService;
import com.tzdr.business.service.wuser.UserVerifiedService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.cms.utils.DataGridVo;
import com.tzdr.cms.utils.WebUtil;
import com.tzdr.common.api.ihuyi.SMSSender;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.RandomCodeUtil;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.vo.ActivityUserVo;
import com.tzdr.domain.vo.ActivityWuserVo;
import com.tzdr.domain.web.entity.Account;
import com.tzdr.domain.web.entity.UserFund;
import com.tzdr.domain.web.entity.WUser;


/**
 * 
 * <p>用户近制器</p>
 * @author LiuQing
 * @see
 * @version 2.0
 * 2015年1月8日下午4:26:47
 */
@Controller
@RequestMapping("/admin/activityWuser")
public class ActivityWuserController extends BaseCmsController<WUser> {
	
	private static Logger log = LoggerFactory.getLogger(ActivityWuserController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private UserTradeService userTradeService;
	
	@Autowired
	private PasswordService passwordService;
	
	@Autowired
	private UserFundService userFundService;
	
	@Autowired
	private UserExtendService userExtendService;
	
	@Autowired
	private DataMapService dataMapService;
	
	
	@RequestMapping(value = "/listActivity")
	public String listActivity(HttpServletRequest request,HttpServletResponse resp) throws Exception {
		return ViewConstants.WuserViewJsp.LIST_ACTIVITY_VIEW;
	}
	
	public ActivityWuserController() {
		setResourceIdentity("sys:customerService:activityWuser");
	}
	
	private static Object lock = new Object();
	
	private static String messageText;

	@Autowired
	private WUserService wuserService;
	
	@Autowired
	private UserVerifiedService userVerifiedService;
	
	/**
	 * 
	 * @param request HttpServletRequest
	 * @param resp HttpServletResponse
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadFile",method=RequestMethod.POST)
	public void uploadFile(@RequestParam(value = "uploadFile", required = false) MultipartFile file,
			HttpServletRequest request,HttpServletResponse resp) throws Exception {
		try {
			synchronized (lock) {
				int activityCount = this.wuserService.activityWuserCount();
				if (activityCount >= TypeConvert.EVENT_ACTIVITY_WUSER_MAX_NUM) {
					messageText = "活动用户已经到最大上限[" 
								+ TypeConvert.EVENT_ACTIVITY_WUSER_MAX_NUM  + "]";
					return;
				}
				
				List<ActivityUserVo> voes = ActivityUserVo.excelToObjs(file.getInputStream());
				if ( (activityCount + voes.size() - 1) > TypeConvert.EVENT_ACTIVITY_WUSER_MAX_NUM) {
					messageText = "导入活动用户不能大于最大上限[" 
								+ TypeConvert.EVENT_ACTIVITY_WUSER_MAX_NUM  + "]";
					return;
				}
				List<WUser> wusers = new ArrayList<WUser>();
				if (voes != null && voes.size() > 0) {
					ActivityUserVo vo = voes.get(0);
					if ("序号".equals(vo.getSn()) || "手机号".equals(vo.getMobile())) {
						//messageText = "";
						for (int i = 1 ; i < voes.size(); i++) {
							ActivityUserVo activityUserVo = voes.get(i);
							WUser wuser = new WUser();
							wuser.setMobile(activityUserVo.getMobile());
							wusers.add(wuser);
						}
					}
					else {
						for (ActivityUserVo activityUserVo:voes) {
							WUser wuser = new WUser();
							wuser.setMobile(activityUserVo.getMobile());
							wusers.add(wuser);
						}
					}
				}
				if (activityCount + wusers.size() > TypeConvert.EVENT_ACTIVITY_WUSER_MAX_NUM) {
					messageText = "活动用户已经到最大上限[" 
							+ TypeConvert.EVENT_ACTIVITY_WUSER_MAX_NUM  + "]";
					return;
				}
				for (WUser validations:wusers) {
					if (validations.getMobile() != null 
							&& !"".equals(validations.getMobile())) {
						if (validations.getMobile().length() != 11) {
						    messageText = "数据格式不对[" + validations.getMobile() + "]!";
						    return;
						}
					}
					else {
						 messageText = "手机号不能为空!";
					}
				}
				boolean haveError = false;
				StringBuffer errorMsg = new StringBuffer("<div style=\"overflow:auto;\">手机号已存在：");
				//更新对应数据
				List<WUser> cmsWusers = this.wuserService.queryByUserType("-3");
				
				for (WUser wuser:wusers) {
					if (wuser.getMobile() == null || "".equals(wuser.getMobile().replaceAll("\\s*", ""))) {
						continue;
					}
					int count = this.wuserService.queryWuserCountByMobile(wuser.getMobile());
					if (count == 0) {
						if (cmsWusers != null && cmsWusers.size() > 0) {
							wuser.setParentNode(cmsWusers.get(0));
							
						}
						this.wuserService.saveActivityWUser(wuser,false);
						
					}
					else {
						haveError = true;
						errorMsg.append(wuser.getMobile() + ",");
					}
				}
				errorMsg.append("</div>");
				if (haveError) {
					messageText = errorMsg.toString().substring(0,errorMsg.length() - 1);
				}
				else {
					messageText = "success";
				}
				
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
			messageText = "导入数据文件格式不对!";
		}
		finally {
			
		}
	}
	
	@RequestMapping("/queryImportExcel")
	public void queryImportExcelActivityWuser(HttpServletResponse resp) {
		synchronized(lock) {
			WebUtil.printText(messageText, resp);
		}
	}
	
	@RequestMapping(value = "/updateWuserToPain")
	public void updateWuserToPain(HttpServletRequest request,HttpServletResponse resp) {
		String uid = request.getParameter("uid");
		if (uid != null && !"".equals(uid)) {
			WUser wuser = this.wuserService.get(uid);
			String userType = wuser.getUserType();
			if (!TypeConvert.USER_TYPE_ASSET.equals(userType)) {
				UserFund userFund = new UserFund();
				userFund.setUid(wuser.getId());
				userFund.setType(TypeConvert.USER_FUND_C_TYPE_EVENT_AVL_BACK);
				if(WUser.ActivityType.ACTIVITY_6600 == wuser.getActivityType()) {					
					userExtendService.remove(uid, WUser.ActivityType.ACTIVITY_6600);
					userFund.setMoney(TypeConvert.EVENT_ACTIVITY_6600_AVL_MONEY);
					userFund.setRemark(TypeConvert.payRemark("支付",TypeConvert.EVENT_ACTIVITY_6600_AVL_MONEY));
				} else {
					userFund.setMoney(TypeConvert.EVENT_ACTIVITY_DEFAULT_AVL_MONEY);
					userFund.setRemark(TypeConvert.payRemark("支付",TypeConvert.EVENT_ACTIVITY_DEFAULT_AVL_MONEY));
				}
				userFundService.rechargeOperation(userFund, TypeConvert.TAKE_DEPOSIT_TYPE_OUTSTORE);
				wuser.setActivityType(0);
				this.wuserService.update(wuser);
				WebUtil.printText("success", resp);
			}
			else {
				WebUtil.printText("已经配资不能变更为普通账户!", resp);
			}
		}
		else {
			WebUtil.printText("用户不存在!", resp);
		}
	}
	
	
	@RequestMapping(value = "/resetPassword")
	public void resetPassword(HttpServletRequest request,HttpServletResponse resp) {
		String uids = request.getParameter("uids");
		String idsSeparate   = TypeConvert.cleanSpaceCharacter(uids);
		if (idsSeparate != null) {
			String ids[] = idsSeparate.split(",");
			boolean isSendSuccessful = true;
			for (String uid:ids) {
				WUser wuser = this.wuserService.get(uid);
				if (wuser != null) {
					String randomPassword = "t" + RandomCodeUtil.randStr(6);
					wuser.randomSalt();
					wuser.setPassword(passwordService.encryptPassword(randomPassword, wuser.getLoginSalt()));
					this.wuserService.update(wuser);
					try {
						Thread.sleep(TypeConvert.EVENT_ACTIVITY_MESSAGE_SLEEP_TIME_MILLISECOND);
					} 
					catch (InterruptedException e) {
						e.printStackTrace();
					}
					isSendSuccessful = 
							SMSSender.getInstance().sendByTemplateClasspath(dataMapService.getSmsContentOthers(),
									"activity002", wuser.getMobile(), wuser.getMobile(),randomPassword);
				}
			}
			
			if(isSendSuccessful){ //判断短信发送是否成功
				WebUtil.printText("success", resp);
				return;
			}
			else {
				WebUtil.printText("短信发送失败", resp);
				return;
			}
		}
		else {
			WebUtil.printText(JSON.toJSONString("服务器忙!"), resp);
		}
		
	}
	
	@RequestMapping("/sendActivitySms")
	public void sendActivitySms(@RequestParam String type, HttpServletRequest request,HttpServletResponse resp) {
		List<ActivityWuserVo> activityData = this.wuserService.queryActivityData();
		this.sendSms(activityData,type,null);
		WebUtil.printText("success", resp);
	}
	
	/**
	 * 发送短信
	 * @param datas List<ActivityWuserVo>
	 * @param type String
	 * @param params String[]
	 */
	private void sendSms(List<ActivityWuserVo> datas,String type,String...params) {
		synchronized (smsLock) {
			for (ActivityWuserVo vo:datas) {
				if (vo.getMobile() == null || vo.getMobile().length() == 0) {
					continue;
				}
				try {
					Thread.sleep(TypeConvert.SEND_SMS_LONG);
					String content = null;
					if (TypeConvert.ACTIVITY_BEFORE_BEGIN.equals(type)) {
						content = TypeConvert.getActivityFileContent("activity001");
					}
					else if (TypeConvert.ACTIVITY_REPASSWORD.equals(type)) {
						content = TypeConvert.getActivityFileContent("activity002",params);
						
					}
					else if (TypeConvert.ACTIVITY_BEFORE_END.equals(type)) {
						content = TypeConvert.getActivityFileContent("activity003");
						
					}
					else if (TypeConvert.ACTIVITY_END.equals(type)) {
						content = TypeConvert.getActivityFileContent("activity004");
					}
					if (content != null) {
						SMSSender sms = SMSSender.getInstance();
						sms.send(dataMapService.getSmsContentOthers(), vo.getMobile(), content);
					}
				} 
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 短信静态锁
	 */
	public static Object smsLock = new Object();
	
	@RequestMapping(value = "/activityListData")
	public void activityListData(@ModelAttribute ActivityWuserVo activityWuserVo,
			HttpServletRequest request,HttpServletResponse resp) throws Exception {
		try {
			
			DataGridVo<ActivityWuserVo> grid = new DataGridVo<ActivityWuserVo>();
			PageInfo<ActivityWuserVo> dataPage = new PageInfo<ActivityWuserVo>(request);
			
			PageInfo<ActivityWuserVo> wuseres = this.wuserService.queryActivityDataPage(dataPage, activityWuserVo);
			grid.add(wuseres.getPageResults());
			grid.setTotal(wuseres.getTotalCount());
			WebUtil.printText(JSON.toJSONString(grid), resp);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public BaseService<WUser> getBaseService() {
		return wuserService;
	}
	
	
}
