package com.tzdr.business.service.api.yjfinance;

import java.net.MalformedURLException;
import java.util.List;

import org.apache.axis2.AxisFault;
import org.datacontract.schemas._2004._07.finance_epm.ArrayOfIdentifierData;
import org.datacontract.schemas._2004._07.finance_epm.Credential;
import org.datacontract.schemas._2004._07.finance_epm.IdentifierData;
import org.datacontract.schemas._2004._07.finance_epm.QueryHistoryRequest;
import org.datacontract.schemas._2004._07.finance_epm.QueryHistoryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.nciic.www.QueryBalance;
import cn.com.nciic.www.QueryBalanceResponse;
import cn.com.nciic.www.QuerySimpleHistoryData;
import cn.com.nciic.www.QuerySimpleHistoryDataResponse;
import cn.com.nciic.www.SimpleCheckByJson;
import cn.com.nciic.www.SimpleCheckByJsonResponse;
import cn.com.nciic.www.service.IdentifierServiceStub;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.tzdr.common.exception.EmailExceptionHandler;
import com.tzdr.common.utils.ConfUtil;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.EmailUtils;
import com.tzdr.common.utils.SpringUtils;
import com.tzdr.domain.dao.HundsunTokenDao;
import com.tzdr.domain.dao.identity.IdentityCardHistoryDao;
import com.tzdr.domain.entity.HundsunToken;
import com.tzdr.domain.web.entity.IdentityCardHistory;


/**
 * 
 * 身份验证工具类
 * 爰金金融 http://www.yjfinance.com/
 * 接口URL: http://service.sfxxrz.com/IdentifierService.svc?wsdl
 * Lin Feng
 * 
*/
public class IdentityCard {
	
	
    public static final Logger log = LoggerFactory.getLogger(IdentityCard.class);
	
	private static IdentityCard instance;
	
	private static String userName;
	
	private static String password;
	
	private static int minBalance;
	
	private static IdentifierServiceStub client;
	
	private static String cred;
	
	 /**
	  * 调用接口成功返码
	  */
    public static final int PASS_CODE = 100;
    /**
	  * 调用接口成功，认证通过
	  */
    public static final String SUCCESS_RESULT = "一致";
    
	
	private static String notityEmail;
	
	
	private IdentityCard() throws AxisFault {
		notityEmail= ConfUtil.getContext("mail.to.identity");
		userName= ConfUtil.getContext("identity.user.name");
		password = ConfUtil.getContext("identity.user.password");
		minBalance =Integer.parseInt(ConfUtil.getContext("identity.min.balance"));
		client= new IdentifierServiceStub();
		cred = String.format("{\"UserName\":\"%s\",\"Password\":\"%s\"}", userName, password);
	}
	
	public static synchronized IdentityCard getInstance() throws AxisFault {
		if (instance == null) {
			instance = new IdentityCard();
		}
		return instance;
	}

	/**
	 * 查询剩余次数大于0用
	 * @param idNumber
	 * @param  name
	 * @return
	 */
	public  boolean idSimpleCheckByJson(String idNumber,String name) throws MalformedURLException {
		try {
			String req = String.format("{\"IDNumber\":\"%s\",\"Name\":\"%s\"}", idNumber, name);
						
			SimpleCheckByJson scbj = new SimpleCheckByJson();
			scbj.setCred(cred);
			scbj.setRequest(req);			
			SimpleCheckByJsonResponse scbr = client.simpleCheckByJson(scbj);			
			String result = scbr.getSimpleCheckByJsonResult();	
			
			log.info("实名认证返回结果：{}",result);
			queryBalanceWarning();
			
			JSONObject resultJson=JSONObject.parseObject(result);
			int responseCode=resultJson.getInteger("ResponseCode");   			
			if(PASS_CODE==responseCode){
				JSONObject identifierJson=resultJson.getJSONObject("Identifier");
				String isPass = identifierJson.getString("Result");
				String birthday = identifierJson.getString("Birthday");
				String iDNumber = identifierJson.getString("IDNumber");
				String realName = identifierJson.getString("Name");
				String sex = identifierJson.getString("Sex");
				
				IdentityCardHistory identityCardHistory =new IdentityCardHistory();
				identityCardHistory.setIdCard(iDNumber);
				identityCardHistory.setBirthday(birthday);
				identityCardHistory.setResult(isPass);
				identityCardHistory.setName(realName);
				identityCardHistory.setSex(sex);
				identityCardHistory.setCreateTime(Dates.getCurrentLongDate());
				SpringUtils.getBean(IdentityCardHistoryDao.class).save(identityCardHistory);

				if(SUCCESS_RESULT.equals(isPass)) {
					return true;
				}
				else {
					return false;
				}
			}else{
				return false;
			}
		} catch (Exception e) {
			String details=idNumber+","+name;		
			EmailExceptionHandler.getInstance().HandleExceptionWithData(e, "实名验证接口报错", this.getClass().getName()+"idSimpleCheckByJson",details);
		}
		return false;
	}
	
	/**
	 * 查询剩余次数
	 * @param id5
	 * @return
	 */
	public  void queryBalanceWarning(){
		try {
			String req = "";
						
			QueryBalance qb = new QueryBalance();
			qb.setCred(cred);
			qb.setRequest(req);			
			QueryBalanceResponse qbr = client.queryBalance(qb);	
			String result =qbr.getQueryBalanceResult();		
			JSONObject resultJson=JSONObject.parseObject(result);
			int responseCode=resultJson.getInteger("ResponseCode");  
			if(PASS_CODE==responseCode){
				int balance=resultJson.getInteger("SimpleBalance");
				log.info("身份验证剩余条数："+balance);
				if(balance<=minBalance) {
					//邮件通知 冲值
					List<String> pramas = Lists.newArrayList();
					pramas.add(String.valueOf(balance));
					EmailUtils.getInstance().sendMailTemp(notityEmail, "identitycardemail",pramas);
				}
			}						
		} catch (Exception e) {
			EmailExceptionHandler.getInstance().HandleException(e, "实名验证接口报错", this.getClass().getName()+"queryBalanceWarning");
		}
	}
	
	public  IdentifierData[] queryHistory(String dateStart,String dateEnd){
		IdentifierData[] list=null;
		try {					
			QuerySimpleHistoryData qr = new QuerySimpleHistoryData();
			Credential credential= new Credential();
			credential.setUserName(userName);
			credential.setPassword(password);
			QueryHistoryRequest queryHistoryRequest = new QueryHistoryRequest();
			queryHistoryRequest.setDateStart(dateStart);
			queryHistoryRequest.setDateEnd(dateEnd);
			qr.setCred(credential);
			qr.setRequest(queryHistoryRequest);
			QuerySimpleHistoryDataResponse qbr = client.querySimpleHistoryData(qr);
			QueryHistoryResponse result =qbr.getQuerySimpleHistoryDataResult();
			ArrayOfIdentifierData arrayOfIdentifierData =result.getHistories();
			list = arrayOfIdentifierData.getIdentifierData();		
		} catch (Exception e) {
			EmailExceptionHandler.getInstance().HandleException(e, "实名验证接口报错", this.getClass().getName()+"queryBalanceWarning");
		}
		return list;
	}			
}
