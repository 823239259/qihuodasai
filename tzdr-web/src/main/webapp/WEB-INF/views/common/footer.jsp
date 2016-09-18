<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.umpay.api.log.SysOutLogger"%>
<%@page import="com.tzdr.common.utils.ConfUtil"%>
<div style="background: #333;height: 327px;" id="ungundontiao">
<div class="copyright">
    <div class="copyright_content">
        <div class="copyright_link">
            <ul class="link">
                <li><a href="${ctx }/news/newsdata?colname=ff8080814e956d02014e95788e300001" target="_blank">维胜动态</a></li>
                <li><a href="${ctx }/about" target="_blank">公司简介</a></li>
                <li><a href="${ctx }/news/newsdata?colname=ff8080814f73cadc014f73cf0f6b0001" target="_blank">市场热点</a></li>
                <li><a href="${ctx }/contact" target="_blank">联系我们</a></li>
                <li><a href="${ctx }/help?tab=newbie&leftMenu=1" target="_blank">帮助中心</a></li>
            </ul>
            <ul class="follow">
                <li>关注我们：</li>
                <li><a href="http://weibo.com/weishengjinrong" target="_blank" class="weibo"></a></li>
                <li class="erweima" style="position: relative; margin-right: 20px;">
                	<a href="javascript: void(0);" target="_blank" class="weixin">
                		<div class="erweima-wxtk" style="display: none;">
		                	<img src="${ctx}/static/images/common-new/erweima.png">
		            	</div>
                	</a>
                </li>
            </ul>
        </div>
        <div class="copyright_contact">
            <h3>客服热线</h3>
            <p><span>400</span>-<span>852</span>-<span>8008</span></p>
            <p>工作日：8:30 - 24:00</p>
            <p>周末：09:00 - 17:00</p>
        </div>
        <div class="copyright_interaction">
            <h3>交流互动</h3>
            <p>期货操盘QQ群：</p>
            <p><a target="_blank" href="http://crm2.qq.com/page/portalpage/wpa.php?uin=4008528008&aty=0&a=0&curl=&ty=1">4008528008</a></p>
            <p>国际期货</p>
        </div>
    </div>
</div>
<div class="copybox">
    <div class="copybox_content">
        <div class="cp_link">
            <ul>
                <li>相关网站：</li>
                <li>证监会</li>
                <li>银监会</li>
                <li>保监会</li>
                <li>深交所</li>
                <li>上交所</li>
                <li>新加坡交易所</li>
                <li>香港交易所</li>
                <li>纽约证券交易所</li>
                <li>纳斯达克</li>
            </ul>
            <p>Copyright &copy; 2016 成都盈透科技有限公司 版权所有 蜀ICP备16018768号-1  投资有风险，入市需谨慎</p>
        </div>
        <div class="cp_cppic">
            <img src="${ctx}/static/images/image/shiming.png">
            <img src="${ctx}/static/images/image/anquan.png">
            <img src="${ctx}/static/images/image/chengxing.png">
        </div>
    </div>
</div>
</div>
<!-- 网站公告 -->
<div class="site-notice notice-fixed">
	<p class="site-touming"></p>
	<div class="gonggao-title-bj">
		<div class="notice-title-bj">
			<h3 class="notice-title">维胜公告栏</h3>
			<a href="javascript: closeNotice();" class="notice-close"></a>
		</div>	
	</div>	
	<div class="nr">
		<div class="notice-style">
			<div class="notice-content">
				<p></p>
			</div>
		</div>	
	</div>	
</div>
<input id="noticeid" type="hidden" value="144" />
<div class="site-notice notice-relative" style="position: relative; display: none;">
	<p class="site-touming" style="margin-top: -5px;"></p>
	<div class="gonggao-title-bj">
		<div class="notice-title-bj">
			<h3 class="notice-title">维胜公告栏</h3>
			<a href="javascript: closeNotice();" class="notice-close"></a>
		</div>	
	</div>
    <div class="nr">
		<div class="notice-style">
			<div class="notice-content">
				<p></p>
			</div>
		</div>	
	</div>	
</div>
<script type='text/javascript' src="${ctx}/static/script/common/footer.js?v=${v}"></script>