package com.tzdr.business.service.togetherTrade;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.Dates;
import com.tzdr.domain.dao.togetherTrade.TogetherUserListDao;
import com.tzdr.domain.vo.cms.TogetherUserListVo;
import com.tzdr.domain.web.entity.TogetherUserList;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class TogetherUserListService extends BaseServiceImpl<TogetherUserList, TogetherUserListDao> {

	private static Logger logger = LoggerFactory.getLogger(TogetherUserListService.class);
	@Autowired
	private TogetherUserListDao togetherUserListDao;
	/**
	 * 合买记录由程序在开户审核通过时，自动生成，生成规则如下： 1，先内置一批马甲账号，程序随机抽取3-10个马甲。
	 * 2，合买金额在N-1个马甲之间随机生成金额，第N个马甲的金额等于：合买者出资总金额 - 前面N-1个马甲的合买金额之和
	 * 3，合买时间：在用户方案申请成功的时间 到 CMS后台开户审核通过的时间，这时间段之间随机生成，然后按时间顺序排列。
	 * 
	 * @param groupId
	 */
	public void generateTogetherUsers(String groupId) {
		// 生成一个3-10的随机数，从w_user表中随机抽取随机数记录
		StringBuffer sql = new StringBuffer(" select wut.id tid, ? gid, w1.id uid, w1.mobile uphone, ");
		sql.append(" substr(wut.money, 1, length(wut.money)-2)+0 money, substr(wut.money, -2)+0 minusMoney, ");
		sql.append(" concat('', wut.addtime+floor(rand()*(?-wut.addtime))) buyTime, " + "'仅合买者可见' interestIncome, '仅合买者可见' profitShare ");
		sql.append(" from (select id, mobile from w_user where user_type >= 0 limit 1000) w1 ");
		sql.append(" left join (select id, money, addtime from w_user_trade where group_id = ? order by addtime asc limit 1) wut on 1 = 1 ");
		sql.append(" where 1 = 1 order by rand() limit ? ");
		
		List<Object> params = new ArrayList<Object>();
		int number = (int) (3 + Math.floor(Math.random() * 7));
		params.add(groupId);
		params.add(Dates.getCurrentLongDate());
		params.add(groupId);
		params.add(number);
		@SuppressWarnings("unchecked")
		List<TogetherUserListVo> togetherUserList = (List<TogetherUserListVo>) nativeQuery(sql.toString(), params, TogetherUserListVo.class);

		// 分配合买金额并保存
		double togetherMoney = togetherUserList.get(0).getMoney();
		List<Double> money = randomFee(togetherMoney, number, 0.1);
		Iterator<TogetherUserListVo> iterator = togetherUserList.iterator();
		for (int i = 0; i < togetherUserList.size(); i++) {
			TogetherUserList togetherUser = new TogetherUserList();
			TogetherUserListVo vo = iterator.next();
			togetherUser.setTid(vo.getTid());
			togetherUser.setGid(vo.getGid());
			togetherUser.setUid(vo.getUid());
			String uphone = vo.getUphone();
			int size = uphone.length();
			logger.info("----------------------------------------------------------------");
			logger.info("TogetherUserListService mobile :" + uphone);
			logger.info("----------------------------------------------------------------");
			String newUphone = uphone.substring(0,3)+"*****"+uphone.substring(size-3,size);
			togetherUser.setUphone(newUphone);
			if (togetherUserList.size() - 1 == i) {
				togetherUser.setMoney(BigDecimalUtils.add2(BigDecimalUtils.mul(money.get(i), 100), vo.getMinusMoney()));
			} else {
				togetherUser.setMoney(BigDecimalUtils.mul(money.get(i), 100));
			}
			togetherUser.setBuyTime(Long.parseLong(vo.getBuyTime()));
			this.save(togetherUser);
		}
	}

	/**
	 * 根据groupId批量更新合买者结算时间
	 * 
	 * @param groupId
	 */
	public void updateByGroupId(String groupId) {
		if (StringUtil.isBlank(groupId)) {
			logger.info("groupId is null");
			return;
		}
		String sql = "update w_together_user_list wtul set wtul.end_time = ? where wtul.gid = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(Dates.getCurrentLongDate());
		params.add(groupId);

		int affectedRow = nativeUpdate(sql, params);
		if (affectedRow > 0) {
			logger.info("batch update w_together_user_list by groupid success!");
		} else {
			logger.info("batch update w_together_user_list by groupid failed!");
		}
	}

	/**
	 * 固定金额随机分配算法
	 * 
	 * @param total
	 *            总金额
	 * @param number
	 *            分配数量
	 * @param min
	 *            平均最小值
	 */
	public static List<Double> randomFee(double total, int number, double min) {
		double safeTotal = 0, money = 0, num = number, temp = 100;
		List<Double> result = new ArrayList<Double>();
		for (int i = 1; i < num; i++) {
			// safeTotal = (total-(num-i)*min)/(num-i);
			safeTotal = BigDecimalUtils.div2(BigDecimalUtils.sub(total, BigDecimalUtils.mul((num - i), min)), (num - i));
			// money = (min*100+Math.random()*((safeTotal*100)-(min*100)))/100;
			money = BigDecimalUtils
					.div2(BigDecimalUtils.add2(BigDecimalUtils.mul(min, temp),
							BigDecimalUtils.mul(Math.random(), BigDecimalUtils.sub(BigDecimalUtils.mul(safeTotal, temp), BigDecimalUtils.mul(min, temp)))), temp);
			total = BigDecimalUtils.round2(BigDecimalUtils.sub(total, money), 1);
			money = BigDecimalUtils.round2(money, 1);
			result.add(money);
		}
		result.add(total);
		return result;
	}
	
	/**
	 * 根据groupId获取虚拟列表
	 * 
	 * @param groupId
	 */
	public List<TogetherUserList> findByGid(String groupId) {
		List<TogetherUserList> list = togetherUserListDao.findByGidOrderByBuyTimeDesc(groupId);
		return list;
	}
	
	public static void main(String[] args) {
		System.out.println("算法开始时间：" + System.currentTimeMillis());
		List<Double> result = randomFee(2, 10, 0.1);
		System.out.println("算法结束时间：" + System.currentTimeMillis());
		double sum = 0;
		for (int i = 0; i < result.size(); i++) {
			System.out.println((i + 1) + ";    " + "取：" + result.get(i));
			sum = BigDecimalUtils.add2(sum, result.get(i));
		}
		System.out.println("合计：" + sum);

		System.out.println(Dates.getCurrentLongDate());
	}

}
