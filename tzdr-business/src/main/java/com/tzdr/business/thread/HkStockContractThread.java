package com.tzdr.business.thread;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jodd.datetime.JDateTime;
import jodd.util.StringUtil;

import org.mapu.themis.api.common.PersonalIdentifer;
import org.mapu.themis.api.response.preservation.PreservationCreateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.tzdr.business.exception.UserTradeException;
import com.tzdr.business.service.contractsave.ContractsaveService;
import com.tzdr.business.service.investor.InvestorService;
import com.tzdr.business.util.WebUtil;
import com.tzdr.common.api.contact.ContactBean;
import com.tzdr.common.api.contact.ContactSaveDoc;
import com.tzdr.common.utils.ConfUtil;
import com.tzdr.common.utils.ConvertHkMoneyUtil;
import com.tzdr.common.utils.ConvertMoneyUtil;
import com.tzdr.common.utils.EmailUtils;
import com.tzdr.common.utils.HtmlToDocUtils;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.common.utils.SpringUtils;
import com.tzdr.common.utils.StringCodeUtils;
import com.tzdr.domain.web.entity.Contractsave;
import com.tzdr.domain.web.entity.Investor;


public class HkStockContractThread extends Thread{

	private static Logger log = LoggerFactory
			.getLogger(HkStockContractThread.class);
	
	private String programNo;
	private String loanTrueName;
	private String cardNo;
	private String nickName;
	private Double loanAmounts;
	private double loanInterest;
	private Double margin;
	private double managementFee;
	private int days;
	private String startTime;
	private String endTime;
	private Double warning;
	private Double open;
	private String basePath;
	private double totalmoney;
	private String uid;
	private String email;
	public HkStockContractThread(String programNo,String loanTrueName,String cardNo,
			String nickName,Double loanAmounts,double loanInterest,Double margin,
			double managementFee,int days,String startTime,String endTime,
			Double warning,Double open,String basePath,double totalmoney,String uid,
			String email) {
		this.programNo = programNo;
		this.loanTrueName = loanTrueName;
		this.cardNo = cardNo;
		this.nickName = nickName;
		this.loanAmounts = loanAmounts;
		this.loanInterest = loanInterest;
		this.margin = margin;
		this.managementFee=managementFee;
		this.days = days;
		this.startTime = startTime;
		this.endTime = endTime;
		this.warning = warning;
		this.open = open;
		this.basePath = basePath;
		this.totalmoney=totalmoney;
		this.uid=uid;
		this.email=email;
	}

	

	public void run() {
		InvestorService investorService=SpringUtils.getBean(InvestorService.class);
		ContractsaveService contactsaveService=SpringUtils.getBean("contractsaveService");
		List<Investor> investorList = investorService.findAll();
		String contributiveTName = null;
		String contributiveIdCard = null;
		if(investorList != null && investorList.size() > 0){
			int index = new Random().nextInt(investorList.size());
			Investor investor = investorList.get(index);
			contributiveTName =  investor.getRealName();
			contributiveIdCard = investor.getIdCard();
		}
		contributiveTName = StringUtil.isBlank(contributiveTName) ? "" : contributiveTName;
		contributiveIdCard = StringUtil.isBlank(contributiveIdCard) ? "" : contributiveIdCard;
		List<String> paramsList = this.conductCreateContractParams(contributiveTName,contributiveIdCard,programNo,loanTrueName, cardNo,nickName, loanAmounts,loanInterest,margin,managementFee,days,startTime,endTime,warning,open);  
		try {
			String htmlpath=WebUtil.createHkstockContractFile(programNo, ".html", paramsList, basePath);
			//生成doc文件
			String contractdocpath=basePath + "upload"+File.separator+"tradeContract"+File.separator+"doc"+File.separator;
			boolean flag=HtmlToDocUtils.writeWordFile(htmlpath,contractdocpath+programNo+".doc");
			//开始将doc文件发送到第三方保存
			if(flag){
				PersonalIdentifer iden=new PersonalIdentifer(cardNo,loanTrueName.trim());
				ContactBean bean=new ContactBean();
				bean.setTitle("实盘申请合作操盘协议"+programNo);
				bean.setContactNumber(programNo);
				bean.setPhone(nickName);
				bean.setEmail(email);
				bean.setSouceRegId(StringCodeUtils.getRandomStr(10));
				bean.setAmout(totalmoney);
				log.info("开始调用数据保全---------->"+cardNo+","+loanTrueName+","+bean.getTitle()+","+bean.getPhone()+","+bean.getEmail()+","+bean.getSouceRegId()+","+bean.getAmout());
				PreservationCreateResponse response=ContactSaveDoc.getInstance().sendContact(contractdocpath+programNo+".doc", iden, bean);
				log.info("数据保全返回结果---------->"+response.getDocHash()+","+response.getPreservationId()+""+response.getPreservationTime());
				//将数据插入数据库
				Contractsave contact=getEntityByResponse(response);
				//String url=ContactSaveDoc.getInstance().getLinkUrl(response);
				//log.info("数据保全返回url---->"+url);
				contact.setContactNo(programNo);
				contact.setProgramNo(programNo);
				//contact.setLinkUrl(url);
				contact.setUid(uid);
				if(StringUtil.isNotBlank(contact.getSeckey())){
					contactsaveService.save(contact);
				}
			}
			
		} catch (Exception e) {
			log.error("创建协议错误",
					e.getMessage());
			List<String> pramas = Lists.newArrayList();
			String methodName = this.getClass().getName() + "."
					+ "TransferMoney";
			String exception = e.getMessage();
			pramas.add(methodName);
			pramas.add("参数 programNo:" + programNo+"-paramsList:"+paramsList.toString() + exception);
			try {
				EmailUtils.getInstance().sendMailTemp(
						ConfUtil.getContext("mail.to.dev"), "exceptionemail",
						pramas);
				throw new UserTradeException(
						MessageUtils.message("transfer.money.error"), null);
			} catch (Exception e1) {
				log.error(this.getClass().getName()+e.getMessage());
			}
			
		}
	}
	
	/**
	 * 根据保全的返回值构建保全实体
	 * @param response
	 * @return
	 */
	private Contractsave getEntityByResponse(PreservationCreateResponse response){
		Contractsave entity=new Contractsave();
		//保存时间到毫秒所以除以1000
		entity.setSavedate(response.getPreservationTime());
		entity.setSeckey(response.getDocHash());
		entity.setSaveid(response.getPreservationId());
		return entity;
	}
	/**
	* @Description: TODO(处理配资合同动态参数)
	* @Title: createContract
	* @param contributiveTName    出资人真实姓名
	* @param contributiveIdCard   出资人身份证号
	* @param contractNo    协议编号
	* @param loanTrueName  真实姓名
	* @param cardNo        身份证号
	* @param nickName      投资用户名
	* @param loanAmounts   借款金额
	* @param loanInterest  借款利息
	* @param margin        保证金金额
	* @param managementFee 账户管理费
	* @param days          借款期限（天数）自然日
	* @param startTime     借款期限（开始时间  格式如：2015-01-21）
	* @param endTime       借款期限（结束时间  格式如：2015-01-25）
	* @param warning       警戒线
	* @param open          平仓线
	* @return List<String>    返回类型
	 */
	private List<String> conductCreateContractParams(String contributiveTName,String contributiveIdCard,String contractNo,String loanTrueName,String cardNo,String nickName,double loanAmounts,double loanInterest,double margin,double managementFee,int days,String startTime,String endTime,double warning,double open){
		//将借款金额数字转换成大写人民币
		String loanAmountsStr = ConvertHkMoneyUtil.convertMoney(new BigDecimal(loanAmounts));
		//将借款利息数字转换成大写人民币
		String loanInterestStr = ConvertMoneyUtil.convertMoney(new BigDecimal(loanInterest));
		//将保证金金额数字转换成大写人民币
		String marginStr = ConvertMoneyUtil.convertMoney(new BigDecimal(margin));
		//将账户管理费数字转换成大写人民币
		String managementFeeStr = ConvertMoneyUtil.convertMoney(new BigDecimal(managementFee));
		//将警戒线数字转换成大写人民币
		String warningStr = ConvertHkMoneyUtil.convertMoney(new BigDecimal(warning));
		//将平仓线数字转换成大写人民币
		String openStr = ConvertHkMoneyUtil.convertMoney(new BigDecimal(open));
		List<String> paramsList = new ArrayList<String>();  //动态参数
		paramsList.add(contractNo);
		
		/**
		 * 暂时均改为20150701 签署
		 * @date 2015/07/14
		 */
//		JDateTime jdtContributiveStartTime = new JDateTime(new Date());
		JDateTime jdtContributiveStartTime = new JDateTime(); 
		paramsList.add(jdtContributiveStartTime.toString("YYYY"));
		paramsList.add(jdtContributiveStartTime.toString("MM"));
		paramsList.add(jdtContributiveStartTime.toString("DD"));
		paramsList.add(contributiveTName);
//		contributiveIdCard = StringUtil.isBlank(contributiveIdCard) ? null : StringCodeUtils.buildIdCard(contributiveIdCard);
//		paramsList.add(contributiveIdCard);
		
		paramsList.add(loanTrueName);
		paramsList.add(cardNo);
		paramsList.add(nickName);
		paramsList.add(loanAmounts+"");
		paramsList.add(loanAmountsStr);
		paramsList.add(loanInterest+"");
		paramsList.add(loanInterestStr);
		paramsList.add(margin+"");
		paramsList.add(marginStr);
		paramsList.add(managementFee+"");
		paramsList.add(managementFeeStr);
		paramsList.add(days+"");
		JDateTime jdtStartTime = new JDateTime(startTime);   
		paramsList.add(jdtStartTime.toString("YYYY"));
		paramsList.add(jdtStartTime.toString("MM"));
		paramsList.add(jdtStartTime.toString("DD"));
		JDateTime jdtEndTime = new JDateTime(endTime);   
		paramsList.add(jdtEndTime.toString("YYYY"));
		paramsList.add(jdtEndTime.toString("MM"));
		paramsList.add(jdtEndTime.toString("DD"));
		paramsList.add(warning+"");
		paramsList.add(warningStr);
		paramsList.add(open+"");
		paramsList.add(openStr);
		paramsList.add(warning+"");
		paramsList.add(warningStr);
		paramsList.add(open+"");
		paramsList.add(openStr);
		return paramsList;
	}

}
