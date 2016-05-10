package com.tzdr.common.api.contact;

import org.mapu.themis.ThemisClient;
import org.mapu.themis.api.common.PersonalIdentifer;
import org.mapu.themis.api.common.PreservationType;
import org.mapu.themis.api.request.cer.CertificateLinkGetRequest;
import org.mapu.themis.api.request.contract.ContractFilePreservationCreateRequest;
import org.mapu.themis.api.response.cer.CertificateLinkGetResponse;
import org.mapu.themis.api.response.preservation.PreservationCreateResponse;

import com.tzdr.common.utils.ConfUtil;


/**
 * 第三方合同保存
 * @author 张军
 *
 */
public class ContactSaveDoc {
	private static ContactSaveDoc instance;
	private static String SERVICES_URL;
	private static String APP_KEY;
	private static String APP_SECRET;
	private static ThemisClient themisClient;
	private ContactSaveDoc() {
		SERVICES_URL = ConfUtil.getContext("ebaoquan.url");
		APP_KEY = ConfUtil.getContext("ebaoquan.key");
		APP_SECRET = ConfUtil.getContext("ebaoquan.secret");
		themisClient= new ThemisClient(SERVICES_URL, APP_KEY, APP_SECRET);
	}
	
	public static synchronized ContactSaveDoc getInstance() {
		if (instance == null) {
			instance = new ContactSaveDoc();
		}
		return instance;
	}
	
	/**
	 * 发送合同到第三方平台
	 * @param filepath 合同文件
	 * @param iden 主体身份信息
	 * @param bean 合同信息
	 * @return
	 */
	public PreservationCreateResponse sendContact(String filepath,PersonalIdentifer iden,ContactBean bean){
		//此段主要是初始化HttpClient 相关的配置，包括各种超时配置，请根据业务类型及场景合理配置
		 
		 ContractFilePreservationCreateRequest.Builder builder
		 = new ContractFilePreservationCreateRequest.Builder();
		 builder.withFile(filepath); //将保全文件在本地进行计算HASH
		 builder.withPreservationTitle(bean.getTitle()); //保全标题
		 builder.withPreservationType(PreservationType.DIGITAL_CONTRACT); //保全分类
		 builder.withIdentifer(iden); //保全主体身份信息
		 builder.withSourceRegistryId(bean.getSouceRegId()); //来源企业用户ID(和保全主体对应)
		 builder.withContractAmount(bean.getAmout()); //合同金额
		 builder.withContractNumber(bean.getContactNumber()); //合同编号
		 builder.withUserEmail(bean.getEmail()); //用户邮箱
		 builder.withMobilePhone(bean.getPhone()); //用户电话
		 PreservationCreateResponse response =themisClient.createPreservation(builder.build());
		 return response;
	}
	
	/**
	 * 根据保全的id获取数据
	 * @param response
	 * @return
	 */
	public static String getLinkUrl(PreservationCreateResponse response){
		Long preservationId=response.getPreservationId();
		CertificateLinkGetRequest request = new CertificateLinkGetRequest();
		request.setPreservationId(preservationId);
		CertificateLinkGetResponse getresponse = themisClient.getCertificateLink(request);
		return getresponse.getLink();
	}
	
	/**
	 * 根据id查链接地址
	 * @param preservationId
	 * @return
	 * @date 2015年5月9日
	 * @author zhangjun
	 */
	public static String getLinkUrlById(Long preservationId){
		CertificateLinkGetRequest request = new CertificateLinkGetRequest();
		request.setPreservationId(preservationId);
		CertificateLinkGetResponse getresponse = themisClient.getCertificateLink(request);
		return getresponse.getLink();
	}
}
