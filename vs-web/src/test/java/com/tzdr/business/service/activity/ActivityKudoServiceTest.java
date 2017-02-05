/**
 * 
 */
package com.tzdr.business.service.activity;

import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tzdr.domain.vo.activity.ActivityKudoWebVo;

/**
 * <B>说明: </B>活动奖品service测试
 * 
 * @chen.ding 2016年1月26日
 */
@ContextConfiguration(locations = { "/applicationContext-test.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ActivityKudoServiceTest {

	@Autowired
	private ActivityKudoService activityKudoService;

	@Ignore
	@Test
	public void generateNewYearGifts() {
		int activityType = 3;
		String userId = "40288a275114a87f015114aab70c0001", mobile = "13350359651";

		try {
			this.activityKudoService.generateNewYearGifts(activityType, userId, mobile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	@Test
	public void recivieMyActivityKudo() {
		int activityType = 3;
		String userId = "40288a275114a87f015114aab70c0001", mobile = "13350359651";

		int result = 0;
		try {
			result = this.activityKudoService.recivieMyActivityKudo(activityType, userId, mobile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("recivieMyActivityKudo result: " + result);
		Assert.assertEquals(result, 1);
	}

	@Ignore
	@Test
	public void isRecivieActivityKudo() {
		int activityType = 3;
		String userId = "40288a275114a87f015114aab70c0001";

		boolean result = this.activityKudoService.isRecivieActivityKudo(activityType, userId);

		Assert.assertTrue(result);
	}

	@Ignore
	@Test
	public void getMyActivityKudo() {
		int activityType = 3;
		String userId = "40288a275114a87f015114aab70c0001", mobile = "13350359651";

		List<ActivityKudoWebVo> result = this.activityKudoService.getMyActivityKudo(activityType, userId, mobile);

		Assert.assertNotNull(result);
	}

	@Ignore
	@Test
	public void useMyActivityKudo() {
		String id = "4028b881527c7bcc01527c7bfd0e0000";

		try {
			this.activityKudoService.useMyActivityKudo(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
