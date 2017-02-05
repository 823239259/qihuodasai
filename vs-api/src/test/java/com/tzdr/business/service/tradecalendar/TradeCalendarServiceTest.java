package com.tzdr.business.service.tradecalendar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;

import com.hundsun.t2sdk.interfaces.T2SDKException;
import com.tzdr.business.cms.service.auth.UserAuthService;
import com.tzdr.business.cms.service.permission.PermissionService;
import com.tzdr.business.cms.service.permission.RoleResourcePermissionService;
import com.tzdr.business.cms.service.resource.ResourceService;
import com.tzdr.business.cms.service.user.UserService;
import com.tzdr.business.service.activity.ActivityProfitRecordService;
import com.tzdr.business.service.combine.CombineInfoService;
import com.tzdr.business.service.pay.UserFundService;
import com.tzdr.business.service.realdeal.RealDealService;
import com.tzdr.domain.cms.entity.permission.Role;
import com.tzdr.domain.cms.entity.permission.RoleResourcePermission;
import com.tzdr.domain.cms.entity.resource.Resource;
import com.tzdr.domain.cms.entity.user.User;

/**
 * @author zhouchen
 * @version 创建时间：2015年1月4日 下午3:55:46
 * 类说明
 */
@ContextConfiguration(locations = { "/applicationContext-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TradeCalendarServiceTest {

	@Autowired
	private TradeDayService  tradeCalendarService;
	
	@Autowired
	private UserFundService  userFundService;
	
	@Autowired
	private RealDealService  realDealService;
	
	@Autowired
	private ActivityProfitRecordService  activityProfitRecordService;
	
	@Autowired
	private UserAuthService  userauthService;
	
	@Autowired
	private RoleResourcePermissionService  roleResourcePermissionService;
	
	@Autowired
	private PermissionService  permissionService;
	
	@Autowired
	private ResourceService  resourceService;
	
	@Autowired
	private CombineInfoService  combineInfoService;
	
	
	@Autowired
	private UserService  userService;
	@Test
	public void testpermis()
	{
		
		 // 创建Excel的工作书册 Workbook,对应到一个excel文档  
	    HSSFWorkbook wb = new HSSFWorkbook();  
	  
	    // 创建Excel的工作sheet,对应到一个excel文档的tab  
	    HSSFSheet sheet = wb.createSheet("sheet1");  
	  
	    // 设置excel每列宽度  
	    sheet.setColumnWidth(0, 4000);  
	    sheet.setColumnWidth(1, 3500);  
	  
	    // 创建字体样式  
	    HSSFFont font = wb.createFont();  
	    font.setFontName("Verdana");  
	    font.setBoldweight((short) 100);  
	    font.setFontHeight((short) 300);  
	    font.setColor(HSSFColor.BLUE.index);  
	  
	    // 创建单元格样式  
	    HSSFCellStyle style = wb.createCellStyle();  
	    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
	    style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
	    style.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);  
	    style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
	  
	    // 设置边框  
	    style.setBottomBorderColor(HSSFColor.RED.index);  
	    style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
	    style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
	    style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
	    style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
	  
	    style.setFont(font);// 设置字体 
	    
	 // 创建Excel的sheet的一行  
	    HSSFRow row = sheet.createRow(0);  
	    row.setHeight((short) 500);// 设定行的高度  
	    // 创建一个Excel的单元格  
	    row.createCell(0).setCellValue("姓名");
	    row.createCell(1).setCellValue("角色");  
	    row.createCell(2).setCellValue("管理模块");  
	    row.createCell(3).setCellValue("权限");  
	    
		List<User>  users = userService.findByUsersDeleteFalse();
		int i=1;
		for (User user : users){
			Set<Role> roles = userauthService.findRoles(user);
			if (CollectionUtils.isEmpty(roles)){
				System.out.println(user.getRealname()+" ");
				sheet.createRow(i).createCell(0).setCellValue(user.getRealname());
				i++;
				continue;
			}
			
			List<RoleResourcePermission> rrpList=roleResourcePermissionService.findByRoleIn(roles);
			for (RoleResourcePermission rrp : rrpList) {
					Resource resource = resourceService.findById(rrp.getResourceId());
					Set<Long> permissionIds =  rrp.getPermissionIds();
					StringBuffer perminsinDetails = new StringBuffer();
					for (Long perid : permissionIds){
						perminsinDetails.append(permissionService.findById(perid).getName()+";");
					}
					row = sheet.createRow(i);
					row.createCell(0).setCellValue(user.getRealname());
					row.createCell(1).setCellValue(rrp.getRole().getName());
					row.createCell(2).setCellValue(resource.getName());
					row.createCell(3).setCellValue(perminsinDetails.toString());
					i++;
					System.out.println(user.getRealname()+" "+rrp.getRole().getName()+" "+resource.getName() +"  "+ perminsinDetails);		
		 }
			 try {
				File  file  =  new File("D:\\系统权限数据.xls");
				if (file.exists()){
					file.delete();
				}
				FileOutputStream os = new FileOutputStream(file);  
				
				  wb.write(os);  
				  os.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}
	}
	
	
	
	//@Test
	public void test() throws T2SDKException {
		
		activityProfitRecordService.saveRecord();
	//	combineInfoService.refreshCombineInfos(null);
		//activityProfitRecordService.saveRecord();
		/*HundsunJres hundsunJres = HundsunJres.getInstance();
		String userToken = hundsunJres.Login("13769001", "1376");
		System.out.print(userToken);
		// String query=hundsunJres.query(userToken, "16280001");
		// String query=hundsunJres.query(userToken,"16280001","10930");
		List<Realdeal> realdeals = 	 hundsunJres.funcAmRealdealHistoryQry(userToken,20150115,0,0);
		for (Realdeal realdeal : realdeals) {
			System.out.println("成交序号:" + realdeal.getBusinessNo()+"，账户编号："+realdeal.getFundAccount()
					+"，证券代码"+realdeal.getStockCode()
					+ "，成交时间:" + realdeal.getBusinessTime()
					+",成交数量："+realdeal.getBusinessAmount()
					+",成交价格："+realdeal.getBusinessPrice()
					+",成交金额："+realdeal.getBusinessBalance());
		}
		hundsunJres.stop();*/
		
		// realDealService.deleteByInitDate(20150115);
		//System.out.println(lsi.size());
	//	tradeCalendarService.createCalendar(2015);
	//System.out.println(tradeCalendarService.getTradeDays("20150103", " 20150119"));
	//System.out.println("=================="+tradeCalendarService.getNaturalDays("20150106", "20150121")+"******************************");
	}

	
}
