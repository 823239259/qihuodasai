package com.tzdr.business.service.contractsave.impl;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import jodd.datetime.JDateTime;
import jodd.util.StringUtil;

import org.dozer.DozerBeanMapper;
import org.mapu.themis.api.common.PersonalIdentifer;
import org.mapu.themis.api.response.preservation.PreservationCreateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.tzdr.business.hkstock.service.HkParentAccountService;
import com.tzdr.business.service.account.ParentAccountService;
import com.tzdr.business.service.contractsave.ContractsaveService;
import com.tzdr.business.service.investor.InvestorService;
import com.tzdr.business.service.userTrade.UserTradeService;
import com.tzdr.business.util.WebUtil;
import com.tzdr.common.api.contact.ContactBean;
import com.tzdr.common.api.contact.ContactSaveDoc;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.exception.EmailExceptionHandler;
import com.tzdr.common.utils.ConfUtil;
import com.tzdr.common.utils.ConvertMoneyUtil;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.HtmlToDocUtils;
import com.tzdr.common.utils.StringCodeUtils;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.dao.contractsave.ContractsaveDao;
import com.tzdr.domain.hkstock.entity.HkUserTrade;
import com.tzdr.domain.hkstock.vo.ContractHkTradeSafeVo;
import com.tzdr.domain.vo.ContractTradeSafeVo;
import com.tzdr.domain.vo.ContractsaveVo;
import com.tzdr.domain.web.entity.Contractsave;
import com.tzdr.domain.web.entity.Investor;
import com.tzdr.domain.web.entity.ParentAccount;
import com.tzdr.domain.web.entity.UserTrade;
import com.tzdr.domain.web.entity.UserVerified;

/**
 * 第三方合同保存
 * @author 张军
 *
 */
@Service("contractsaveService")
@Transactional
public class ContractsaveServiceImpl extends BaseServiceImpl<Contractsave, ContractsaveDao> implements ContractsaveService {
	private static Logger log = LoggerFactory
			.getLogger(ContractsaveServiceImpl.class);
	@Autowired
	private UserTradeService userTradeService;
	@Autowired
	private InvestorService investorService;
	@Autowired
	private ParentAccountService parentAccountService;
	
	@Autowired
	private HkParentAccountService hkParentAccountService;
	
	@Override
	public PageInfo<ContractsaveVo> queryData(PageInfo<ContractsaveVo> dataPage,
			ContractsaveVo vo) {
		List<Object> params = Lists.newArrayList();
		String sql=" select d.program_no as programNo,d.savedate,d.saveid,"
				+ " date_format(from_unixtime(ts.addtime),'%Y-%m-%d %H:%i:%s') tradetime,d.link_url linkUrl,ve.tname,w.mobile from w_contract_list d,"
				+ "w_user_trade  ts ,w_user w,w_user_verified ve ";
			sql+="  where d.uid=ts.uid and ts.program_no=d.program_no ";
			sql+="	and ts.uid=ve.uid and d.uid=ve.uid";
			sql+=" and w.id=ts.uid and w.id=ve.uid ";
			
			if(StringUtil.isNotBlank(vo.getTradestarttime())){
				Date starttime=Dates.parse(vo.getTradestarttime(), "yyyy-MM-dd");
				sql+=" and ts.addtime>=?";
				params.add(starttime.getTime()/1000);
			}
			if(StringUtil.isNotBlank(vo.getTradeendtime())){
				String endtimestr=vo.getTradeendtime()+" 23:59:59";
				Date endtime=Dates.parse(endtimestr, "yyyy-MM-dd HH:mm:ss");
				sql+=" and ts.addtime<=?";
				params.add(endtime.getTime()/1000);
			}
			if(StringUtil.isNotBlank(vo.getSavestarttime())){
				Date starttime=Dates.parse(vo.getSavestarttime(), "yyyy-MM-dd");
				sql+=" and d.savedate>=?";
				params.add(starttime.getTime());
			}
			if(StringUtil.isNotBlank(vo.getSaveendtime())){
				String endtimestr=vo.getSaveendtime()+" 23:59:59";
				Date endtime=Dates.parse(endtimestr, "yyyy-MM-dd HH:mm:ss");
				sql+=" and d.savedate<=?";
				params.add(endtime.getTime());
			}
			
			if(StringUtil.isNotBlank(vo.getMobile())){
				sql+=" and w.mobile=?";
				params.add(vo.getMobile());
			}
			if(StringUtil.isNotBlank(vo.getProgramNo())){
				sql+=" and d.program_no=?";
				params.add(vo.getProgramNo());
			}
			if(StringUtil.isNotBlank(vo.getTname())){
				sql+=" and ve.tname=?";
				params.add(vo.getTname());
			}
			if(vo.getSaveid()!=null){
				sql+=" and d.saveid=?";
				params.add(vo.getSaveid());
			}
			
			sql+=" order by d.savedate desc";
			PageInfo<ContractsaveVo> page= getEntityDao().queryPageBySql(dataPage, sql,
					ContractsaveVo.class,null, params.toArray());
				return page;	
	}
	
 	private  ContractTradeSafeVo convertVoByTrade(UserTrade entity) {
		 if (entity == null) return null;
		 entity=(UserTrade) TypeConvert.createBaseTypeClone(entity);
		 ContractTradeSafeVo vo = new DozerBeanMapper().map(entity, ContractTradeSafeVo.class);
		 return vo;
 	}

	@Override
	public List<ContractTradeSafeVo> createTradeVo(List<UserTrade> userTradeList,String basePath) {
		List<ContractTradeSafeVo> list=new ArrayList<ContractTradeSafeVo>();
		for(UserTrade trade:userTradeList){
			ContractTradeSafeVo vo=this.convertVoByTrade(trade);
			String programNo=vo.getProgramNo();
			String uid=trade.getWuser().getId();
			Contractsave entity=getContractsave(programNo,uid);
			if(entity==null){
				entity=createSafeData(trade, basePath);
			}else{
				//保存了数据表但是没有保存到第三方数据保全中心则重新生成
				if(StringUtil.isBlank(entity.getSeckey()))
					entity=updateContract(programNo, uid, trade, basePath);
				
			}
			if(entity!=null){
				vo.setSeckey(entity.getSeckey());
				vo.setSaveid(entity.getSaveid());
				vo.setLinkUrl(entity.getLinkUrl());
			}
			Long stime=entity.getSavedate();
			if(stime!=null){
				Date date = new Date(stime);
	    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
	    		String savedate=sdf.format(date);
				vo.setSavedate(savedate);
			}
    	
			list.add(vo);
		}
		return list;
	}
	
	/**
	 * 根据方案号和用户id查询数据保全的数据
	 * @param programNo
	 * @param uid
	 * @return
	 * @date 2015年5月6日
	 * @author zhangjun
	 */
	private Contractsave getContractsave(String programNo,String uid){
		List<Contractsave> list= this.getEntityDao().findSafeData(programNo, uid);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		
		return null;
	}
	
	@Override
	public Contractsave createSafeData(UserTrade trade,String basePath) {
		 if(StringUtil.isBlank(basePath)){
			 basePath = ConfUtil.getContext("contractUrl");
		 }
		 String programNo=trade.getProgramNo();
		 String userId=trade.getWuser().getId();
		 Contractsave contractsave=getContractsave(programNo,userId);
		 if(contractsave!=null){
			 return null;
		 }
			//生成保全合同
			String uid=trade.getWuser().getId();
			String parentAccoutNo=trade.getParentAccountNo();
			UserVerified userVerified=trade.getWuser().getUserVerified();
			String loanTrueName=userVerified.getTname();
			String cardNo=userVerified.getIdcard();
			String nickName=trade.getWuser().getMobile();
			double capitalAmount=trade.getMoney();
			long loanAmounts=(long)capitalAmount;
			double loanInterest=trade.getFeeMonth();
			double cMargin=trade.getLeverMoney();
			long margin=(long)cMargin;
			double managementFee=trade.getFeeDay();
			long tradedays=trade.getNaturalDays();
			int days=(int)tradedays;
			String startTime=Dates.format(Dates.parseLong2Date(trade.getStarttime()), Dates.CHINESE_DATE_FORMAT_LINE);
			String endTime=Dates.format(Dates.parseLong2Date(trade.getEstimateEndtime()), Dates.CHINESE_DATE_FORMAT_LINE);
			double tradewarning=trade.getWarning();
			long warning=(long)tradewarning;
			double tardeopen=trade.getOpen();
			long open=(long)tardeopen;
			double totalmoney=trade.getTotalLeverMoney();
			String email=trade.getWuser().getUserVerified().getValidateemail();
			Contractsave entity = new Contractsave();
			try {
				ParentAccount parentAccount=parentAccountService.findByAccountNo(parentAccoutNo);
				if(parentAccount!=null){
					String parentEndDate=TypeConvert.long1000ToDateStr((parentAccount.getAllocationDate()-5*Dates.DAY_LONG));
					entity = createSafeData(programNo, loanTrueName, cardNo, nickName, loanAmounts, loanInterest, margin, managementFee, days, startTime, endTime, warning, open, basePath,totalmoney,uid,email,parentEndDate);
				}
			} catch (Exception e) {
				 log.error("查询母账号信息错误--"+parentAccoutNo,e.getMessage());	
			}
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
	 * @throws Exception 
	 */
	private Contractsave createSafeData(String programNo,String loanTrueName,String cardNo,
			String nickName,long loanAmounts,double loanInterest,long margin,
			double managementFee,int days,String startTime,String endTime,
			long warning,long open,String basePath,double totalmoney,String uid,
			String email,String parentEndDate) throws Exception {
		Contractsave contract=new Contractsave();
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
		List<String> paramsList = this.conductCreateContractParams(contributiveTName,contributiveIdCard,programNo,loanTrueName, cardNo,nickName, loanAmounts,loanInterest,margin,managementFee,days,startTime,endTime,warning,open,parentEndDate);  
		paramsList.add(basePath);
		try {
			String htmlpath=WebUtil.createContractFile(programNo, ".html", paramsList, basePath);
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
				Contractsave contact=new Contractsave();
				contact.setContactNo(programNo);
				contact.setProgramNo(programNo);
				contact.setUid(uid);
				this.save(contact);
				Contractsave contactentity=this.getContractsave(programNo, uid);
				PreservationCreateResponse response=ContactSaveDoc.getInstance().sendContact(contractdocpath+programNo+".doc", iden, bean);
				//将数据插入数据库
				log.info("数据保全返回结果---------->"+response.getDocHash()+","+response.getPreservationId()+""+response.getPreservationTime());
			    if(contactentity!=null){
			    	contactentity.setSavedate(response.getPreservationTime());
			    	contactentity.setSeckey(response.getDocHash());
			    	contactentity.setSaveid(response.getPreservationId());
			    }
				if(StringUtil.isNotBlank(contactentity.getSeckey())){
					this.update(contactentity);
				}else{
					throw new Exception("数据保没有返回结果");
				}
				return contactentity;
			}
			
		} catch (Exception e) {
			 log.error("创建协议错误",e.getMessage());
			 EmailExceptionHandler.getInstance().HandleException(e, "生成配资协议文件错误", "createSafeData"+paramsList.toString());
			 throw new Exception("数据保全出现异常");
		}
		return contract;
	}

	private List<String> conductCreateContractParams(String contributiveTName,String contributiveIdCard,String contractNo,String loanTrueName,String cardNo,String nickName,long loanAmounts,double loanInterest,long margin,double managementFee,int days,String startTime,String endTime,long warning,long open,String parentEndDate){
		//将借款金额数字转换成大写人民币
		String loanAmountsStr = ConvertMoneyUtil.convertMoney(new BigDecimal(loanAmounts));
		//将借款利息数字转换成大写人民币
		String loanInterestStr = ConvertMoneyUtil.convertMoney(new BigDecimal(loanInterest));
		//将保证金金额数字转换成大写人民币
		String marginStr = ConvertMoneyUtil.convertMoney(new BigDecimal(margin));
		//将账户管理费数字转换成大写人民币
		String managementFeeStr = ConvertMoneyUtil.convertMoney(new BigDecimal(managementFee));
		//将警戒线数字转换成大写人民币
		String warningStr = ConvertMoneyUtil.convertMoney(new BigDecimal(warning));
		//将平仓线数字转换成大写人民币
		String openStr = ConvertMoneyUtil.convertMoney(new BigDecimal(open));
		List<String> paramsList = new ArrayList<String>();  //动态参数
		paramsList.add(contractNo);
		
		JDateTime jdtContributiveStartTime = new JDateTime("2015-07-01"); 
//		JDateTime jdtContributiveStartTime = new JDateTime(new Date());   
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
		paramsList.add(parentEndDate);
		paramsList.add(warning+"");
		paramsList.add(warningStr);
		paramsList.add(open+"");
		paramsList.add(openStr);
		return paramsList;
	}

	/**
	 * 保存数据后有数据保全失败的情况重新生成数据保全
	 * @param programNo 方案号
	 * @param uid
	 */
	private Contractsave updateContract(String programNo, String uid,UserTrade trade,String basePath){
		Contractsave contactentity=this.getContractsave(programNo, uid);
		//生成doc文件
		String contractdocpath=basePath + "upload"+File.separator+"tradeContract"+File.separator+"doc"+File.separator;
		String name=trade.getWuser().getUserVerified().getTname();
		String cardNo=trade.getWuser().getUserVerified().getIdcard();
		double totalmoney=trade.getTotalLeverMoney();
		PersonalIdentifer iden=new PersonalIdentifer(cardNo,name.trim());
		ContactBean bean=new ContactBean();
		bean.setTitle("实盘申请合作操盘协议"+programNo);
		bean.setContactNumber(programNo);
		bean.setPhone(trade.getWuser().getMobile());
		bean.setEmail(trade.getWuser().getEmail());
		bean.setSouceRegId(StringCodeUtils.getRandomStr(10));
		bean.setAmout(totalmoney);
		PreservationCreateResponse response=ContactSaveDoc.getInstance().sendContact(contractdocpath+programNo+".doc", iden, bean);
		//将数据插入数据库
		log.info("数据保全返回结果---------->"+response.getDocHash()+","+response.getPreservationId()+""+response.getPreservationTime());
	    if(contactentity!=null){
	    	contactentity.setSavedate(response.getPreservationTime());
	    	contactentity.setSeckey(response.getDocHash());
	    	contactentity.setSaveid(response.getPreservationId());
	    }
		if(StringUtil.isNotBlank(contactentity.getSeckey())){
			this.update(contactentity);
			return contactentity;
		}
		return null;
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

	@Override
	public String getUrlById(Long id) {
		String url=ContactSaveDoc.getInstance().getLinkUrlById(id);
		return url;
	}

	
	private  ContractHkTradeSafeVo convertVoByHkTrade(HkUserTrade entity) {
		 if (entity == null) return null;
		 entity=(HkUserTrade) TypeConvert.createBaseTypeClone(entity);
		 ContractHkTradeSafeVo vo = new DozerBeanMapper().map(entity, ContractHkTradeSafeVo.class);
		 return vo;
	}
	
	@Override
	public Contractsave createSafeData(HkUserTrade hktrade,String basePath) {
		 if(StringUtil.isBlank(basePath)){
			 basePath = ConfUtil.getContext("contractUrl");
		 }
		 String programNo=hktrade.getProgramNo();
		 String userId=hktrade.getWuser().getId();
		 Contractsave contractsave=getContractsave(programNo,userId);
		 if(contractsave!=null){
			 return null;
		 }
			//生成保全合同
			String uid=hktrade.getWuser().getId();
			//String parentAccountId=hktrade.getParentAccountId();
			UserVerified userVerified=hktrade.getWuser().getUserVerified();
			String loanTrueName=userVerified.getTname();
			String cardNo=userVerified.getIdcard();
			String nickName=hktrade.getWuser().getMobile();
			double capitalAmount=hktrade.getMoney();
			long loanAmounts=(long)capitalAmount;
			double loanInterest=hktrade.getFeeMonth();
			double cMargin=hktrade.getLeverMoney();
			long margin=(long)cMargin;
			double managementFee=hktrade.getFeeDay();
			long tradedays=hktrade.getNaturalDays();
			int days=(int)tradedays;
			String startTime=Dates.format(Dates.parseLong2Date(hktrade.getStarttime()), Dates.CHINESE_DATE_FORMAT_LINE);
			String endTime=Dates.format(Dates.parseLong2Date(hktrade.getEstimateEndtime()), Dates.CHINESE_DATE_FORMAT_LINE);
			double tradewarning=hktrade.getWarning();
			long warning=(long)tradewarning;
			double tardeopen=hktrade.getOpen();
			long open=(long)tardeopen;
			double totalmoney=hktrade.getTotalLeverMoney();
			String email=hktrade.getWuser().getUserVerified().getValidateemail();
			Contractsave entity = new Contractsave();
			try {
				entity = createSafeData(programNo, loanTrueName, cardNo, nickName, loanAmounts, loanInterest, margin, managementFee, days, startTime, endTime, warning, open, basePath,totalmoney,uid,email);
			} catch (Exception e) {
				// log.error("查询母账号信息错误--"+parentAccoutNo,e.getMessage());	
				log.error(e.getMessage());
			}
			return entity;
		}
	
	@Override
	public List<ContractHkTradeSafeVo> createHkTradeVo(
			List<HkUserTrade> userTradeList, String basePath) {
		List<ContractHkTradeSafeVo> list=new ArrayList<ContractHkTradeSafeVo>();
		for(HkUserTrade hkTrade:userTradeList){
			ContractHkTradeSafeVo vo=this.convertVoByHkTrade(hkTrade);
			String programNo=vo.getProgramNo();
			String uid=hkTrade.getWuser().getId();
			Contractsave entity=getContractsave(programNo,uid);
			if(entity==null){
				entity=createSafeData(hkTrade, basePath);
			}else{
				//保存了数据表但是没有保存到第三方数据保全中心则重新生成
				if(StringUtil.isBlank(entity.getSeckey()))
					entity=updateContract(programNo, uid, hkTrade, basePath);
				
			}
			if(entity!=null){
				vo.setSeckey(entity.getSeckey());
				vo.setSaveid(entity.getSaveid());
				vo.setLinkUrl(entity.getLinkUrl());
			}
			Long stime=entity.getSavedate();
			if(stime!=null){
				Date date = new Date(stime);
	    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
	    		String savedate=sdf.format(date);
				vo.setSavedate(savedate);
			}
    	
			list.add(vo);
		}
		return list;
	}

	/**
	* @Description:港股处理配资合同动态参数
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
	 * @throws Exception 
	 */
	private Contractsave createSafeData(String programNo,String loanTrueName,String cardNo,
			String nickName,long loanAmounts,double loanInterest,long margin,
			double managementFee,int days,String startTime,String endTime,
			long warning,long open,String basePath,double totalmoney,String uid,
			String email) throws Exception {
		Contractsave contract=new Contractsave();
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
		paramsList.add(basePath);
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
				Contractsave contact=new Contractsave();
				contact.setContactNo(programNo);
				contact.setProgramNo(programNo);
				contact.setUid(uid);
				this.save(contact);
				Contractsave contactentity=this.getContractsave(programNo, uid);
				PreservationCreateResponse response=ContactSaveDoc.getInstance().sendContact(contractdocpath+programNo+".doc", iden, bean);
				//将数据插入数据库
				log.info("数据保全返回结果---------->"+response.getDocHash()+","+response.getPreservationId()+""+response.getPreservationTime());
			    if(contactentity!=null){
			    	contactentity.setSavedate(response.getPreservationTime());
			    	contactentity.setSeckey(response.getDocHash());
			    	contactentity.setSaveid(response.getPreservationId());
			    }
				if(StringUtil.isNotBlank(contactentity.getSeckey())){
					this.update(contactentity);
				}else{
					throw new Exception("数据保没有返回结果");
				}
				return contactentity;
			}
			
		} catch (Exception e) {
			 log.error("创建协议错误",e.getMessage());
			 EmailExceptionHandler.getInstance().HandleException(e, "生成配资协议文件错误", "createSafeData"+paramsList.toString());
			 throw new Exception("数据保全出现异常");
		}
		return contract;
	}
	
	/**
	 * 港股
	 * @param contributiveTName
	 * @param contributiveIdCard
	 * @param contractNo
	 * @param loanTrueName
	 * @param cardNo
	 * @param nickName
	 * @param loanAmounts
	 * @param loanInterest
	 * @param margin
	 * @param managementFee
	 * @param days
	 * @param startTime
	 * @param endTime
	 * @param warning
	 * @param open
	 * @return
	 */
	private List<String> conductCreateContractParams(String contributiveTName,String contributiveIdCard,String contractNo,String loanTrueName,String cardNo,String nickName,long loanAmounts,double loanInterest,long margin,double managementFee,int days,String startTime,String endTime,long warning,long open){
		//将借款金额数字转换成大写人民币
		String loanAmountsStr = ConvertMoneyUtil.convertMoney(new BigDecimal(loanAmounts));
		//将借款利息数字转换成大写人民币
		String loanInterestStr = ConvertMoneyUtil.convertMoney(new BigDecimal(loanInterest));
		//将保证金金额数字转换成大写人民币
		String marginStr = ConvertMoneyUtil.convertMoney(new BigDecimal(margin));
		//将账户管理费数字转换成大写人民币
		String managementFeeStr = ConvertMoneyUtil.convertMoney(new BigDecimal(managementFee));
		//将警戒线数字转换成大写人民币
		String warningStr = ConvertMoneyUtil.convertMoney(new BigDecimal(warning));
		//将平仓线数字转换成大写人民币
		String openStr = ConvertMoneyUtil.convertMoney(new BigDecimal(open));
		List<String> paramsList = new ArrayList<String>();  //动态参数
		paramsList.add(contractNo);
		
		JDateTime jdtContributiveStartTime = new JDateTime("2015-07-01"); 
//		JDateTime jdtContributiveStartTime = new JDateTime(new Date());   
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
		// paramsList.add(parentEndDate);
		paramsList.add(warning+"");
		paramsList.add(warningStr);
		paramsList.add(open+"");
		paramsList.add(openStr);
		return paramsList;
	}

	/**
	 * 保存数据后有数据保全失败的情况重新生成数据保全
	 * @param programNo 方案号
	 * @param uid
	 */
	private Contractsave updateContract(String programNo, String uid,HkUserTrade hkTrade,String basePath){
		Contractsave contactentity=this.getContractsave(programNo, uid);
		//生成doc文件
		String contractdocpath=basePath + "upload"+File.separator+"tradeContract"+File.separator+"doc"+File.separator;
		String name=hkTrade.getWuser().getUserVerified().getTname();
		String cardNo=hkTrade.getWuser().getUserVerified().getIdcard();
		double totalmoney=hkTrade.getTotalLeverMoney();
		PersonalIdentifer iden=new PersonalIdentifer(cardNo,name.trim());
		ContactBean bean=new ContactBean();
		bean.setTitle("实盘申请合作操盘协议"+programNo);
		bean.setContactNumber(programNo);
		bean.setPhone(hkTrade.getWuser().getMobile());
		bean.setEmail(hkTrade.getWuser().getEmail());
		bean.setSouceRegId(StringCodeUtils.getRandomStr(10));
		bean.setAmout(totalmoney);
		PreservationCreateResponse response=ContactSaveDoc.getInstance().sendContact(contractdocpath+programNo+".doc", iden, bean);
		//将数据插入数据库
		log.info("数据保全返回结果---------->"+response.getDocHash()+","+response.getPreservationId()+""+response.getPreservationTime());
	    if(contactentity!=null){
	    	contactentity.setSavedate(response.getPreservationTime());
	    	contactentity.setSeckey(response.getDocHash());
	    	contactentity.setSaveid(response.getPreservationId());
	    }
		if(StringUtil.isNotBlank(contactentity.getSeckey())){
			this.update(contactentity);
			return contactentity;
		}
		return null;
	}
	
}
