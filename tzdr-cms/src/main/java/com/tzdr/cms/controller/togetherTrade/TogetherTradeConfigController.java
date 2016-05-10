package com.tzdr.cms.controller.togetherTrade;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.service.togetherTrade.TogetherConfigService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.web.entity.TogetherConfig;

/**
 * <B>说明: </B>股票合买参数设置
 * @chen.ding
 * 2016年1月20日
 */
@Controller
@RequestMapping("/admin/config/togetherTrade")
public class TogetherTradeConfigController extends BaseCmsController<TogetherConfig> {

	private static Logger logger = LoggerFactory.getLogger(TogetherTradeConfigController.class);

	@Autowired
	private AuthService authService;

	@Autowired
	private TogetherConfigService togetherConfigService;

	@Override
	public BaseService<TogetherConfig> getBaseService() {
		return togetherConfigService;
	}

	public TogetherTradeConfigController() {
		setResourceIdentity("sys:settingParams:togetherTradeConfig");
	}

	/**
	 * 跳转到参数页
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 查看权限
		if (permissionList != null) {
			this.permissionList.assertHasViewPermission();
		}
		TogetherConfig togetherConfig = new TogetherConfig();
		List<TogetherConfig> list = togetherConfigService.getAll();
		if (list != null && list.size() > 0) {
			togetherConfig = list.get(0);
		}
		request.setAttribute("togetherConfig", togetherConfig);
		return ViewConstants.OpertinalConfigViewJsp.TOGETHER_TRADE_CONFIG_VIEW;
	}

	/**
	 * 修改股票合买参数
	 * 
	 * @param togetherConfig
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateParams", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult updateParams(TogetherConfig togetherConfig) throws Exception {
		// 修改权限
		if (permissionList != null) {
			this.permissionList.assertHasUpdatePermission();
		}
		JsonResult result = new JsonResult(false);
		try {
			if (checkData(togetherConfig)) {
				User loginUser = authService.getCurrentUser();
				togetherConfig.setUpdateUserId(loginUser.getId());
				togetherConfig.setUpdateUser(loginUser.getRealname());
				togetherConfig.setUpdateTime(Dates.getCurrentLongDate());
				togetherConfigService.update(togetherConfig);
				result.setSuccess(true);
				result.setMessage("操作成功");
			} else {
				result.setMessage("传入的数据不正确");
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
			result.setMessage("操作失败");
		}
		return result;
	}

	/**
	 * 检测提交的数据
	 * 
	 * @param togetherConfig
	 * @return
	 */
	private boolean checkData(TogetherConfig togetherConfig) {
		if (togetherConfig == null) {
			return false;
		}
		// 合买利息系数
		if (togetherConfig.getFoenusRatio() < 0) {
			return false;
		}
		// 合买管理费系数
		if (togetherConfig.getManageRatio() < 0) {
			return false;
		}
		// 操盘者最低出资
		if (togetherConfig.getMinMoney() < 0) {
			return false;
		}
		// 操盘者最高出资
		if (togetherConfig.getMaxMoney() < 0 && togetherConfig.getMaxMoney() > togetherConfig.getMinMoney()) {
			return false;
		}
		// 操盘者出资推荐
		if (StringUtil.isBlank(togetherConfig.getRecommendMoney())) {
			return false;
		}
		// 合买者出资倍数
		if (StringUtil.isBlank(togetherConfig.getMoneyRatio())) {
			return false;
		}
		// 推荐操盘天数
		if (StringUtil.isBlank(togetherConfig.getRecommendDay())) {
			return false;
		}
		// 合买者分成系数
		if (togetherConfig.getProfitRatio() < 0) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		// 形如：1;2;3;4;5;6; 的正则，并去掉最后一个分号
		String exp = "(^[1-9]\\d*\\;){3,}";
		System.out.println(exp);
		Pattern pattern = Pattern.compile(exp);
		Matcher matcher = pattern.matcher("1;2;3;4" + ";");
		System.out.println(matcher.matches());
	}

}
