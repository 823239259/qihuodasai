<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.umpay.api.log.SysOutLogger"%>
<%@page import="com.tzdr.common.utils.ConfUtil"%>
<style>
#footer {
    clear: both;
    background-color: #333333;
    overflow: hidden;
    padding: 40px 0px 40px;
    width: 1280px;
    margin: 0 auto;
    height: 160px;
    margin-top: 20px;
}
#footer div {
    float: left;
}
#footer .footerList ul {
    float: left;
    width: 104px;
    margin-right: 80px;
}
#footer .footerList ul li {
    font-size: 14px;
    line-height: 24px;
    color: #999;
}
#footer .footerList ul li a {
    color: #999999;
    display: inline-block;
    height: 24px;
    line-height: 24px;
}
#footer .footerList ul li.footHover a:hover {
    color: #ffb319;
}
#footer .footerList ul li a {
    color: #666;
}
#footer .footerList ul li a:hover {
    color: #ffb319;
}
#footer .footerList ul li.footerTitle {
    margin-bottom: 25px;
    color: #999;
    font-size: 16px;
}
#iOS {
    margin-left: 40px;
    margin-right: 180px;
}
#iOS img {
    width: 160px;
}
#iOS p{
    font-size: 14px;
    color: #fff;
    text-align: center;
    margin-top: 5px;
}
#footer #QRcode #iOS p,
#footer #QRcode #Android p {
    margin-top: 8px;
    color: #ffffff;
    font-size: 14px;
}
#footer #customerService {
    float: right;
}
#footer #customerService ul {
    width: 145px;
    margin-right: 0;
}
#footer #customerService li {
    color: #666;
}
#footer #customerService li.footerTitle {
    color: #999;
}
#Copyright {
    width: 1280px;
    margin: 0 auto;
    background-color: #f2f2f2;
}
#Copyright div {
    padding: 10px 0px;
    margin: auto;
    font-size: 12px;
    text-align: center;
    line-height: 20px;
    color: #999999;
}
#Copyright div a {
    line-height: 20px;
    margin-left: 5px;
    color: #999999;
}
#Copyright div a:hover {
    color: #ffb319;
}
#MEIQIA-BTN #MEIQIA-BTN-TEXT {
	display: none;
}
#MEIQIA-BTN-HOLDER {
	right: 10px;
    bottom: 239px;
    width: 40px;
    height: 40px;
}
#MEIQIA-BTN {
	background-color: #1a1a1a;
	border-radius: 4px;
}
</style>
<!-- <div style="background: #333;height: 338px;" id="ungundontiao"> -->
<div>
<%-- <div class="copyright">
    <div class="copyright_content">
        <div class="copyright_link">
            <ul class="link">
                <li><a href="${ctx }/news/newsdata?colname=ff8080814e956d02014e95788e300001" target="_blank">维胜动态</a></li>
                <li><a href="${ctx }/about" target="_blank">公司简介</a></li>
                <li><a href="${ctx }/news/newsdata?colname=ff8080814f73cadc014f73cf0f6b0001" target="_blank">市场热点</a></li>
                <li><a href="${ctx }/contact" target="_blank">联系我们</a></li>
                <li><a href="${ctx }/help?tab=newbie&leftMenu=1" target="_blank">帮助中心</a></li>
            </ul>
            <ul>
	           	<li>公司品牌：</li>
	           	<li><a href=" http://www.vs.com/" target="_blank">维胜</a> </li>
	           	<li><a href=" http://www.vs.com/" target="_blank">国际期货公司</a></li>
	           	<li><a href=" http://www.vs.com/" target="_blank">外盘期货公司</a></li>
	           	<li><a href=" http://www.vs.com/" target="_blank">成都期货</a></li>
         	</ul>
            <ul>
            	<li>公司产品：</li>
            	<li><a href=" http://www.vs.com/" target="_blank">期货</a></li>
            	<li><a href=" http://www.vs.com/" target="_blank">外盘期货</a></li>
            	<li><a href=" http://www.vs.com/" target="_blank">恒指期货</a></li>
            	<li><a href=" http://www.vs.com/" target="_blank">国际原油</a></li>
            	<li><a href=" http://www.vs.com/" target="_blank">富时A50</a></li>
            </ul>
	        <ul>
		       	<li>公司业务：</li>
		       	<li><a href=" http://www.vs.com/" target="_blank">期货开户</a></li>
		       	<li><a href=" http://www.vs.com/" target="_blank">国际期货开户</a></li>
		       	<li><a href=" http://www.vs.com/" target="_blank">期货交易</a></li>
		       	<li><a href=" http://www.vs.com/" target="_blank">期货行情</a></li>
		       	<li><a href=" http://www.vs.com/" target="_blank">外盘期货行情</a></li>
	        </ul>
			<ul style="line-height: 25px;">
				<li style="width: 70px; text-align: right; height: 50px;">维胜资讯：</li>
				<li><a href="http://www.vs.com/a/news/commodity/" target="_blank">商品市场</a></li>
				<li><a href="http://www.vs.com/a/view/stock/" target="_blank">证券市场</a></li>
				<li><a href="http://www.vs.com/a/news/exchange/" target="_blank">外汇市场</a></li>
				<li><a href="http://www.vs.com/a/news/whole/" target="_blank">宏观经济</a></li>
				<li><a href="http://www.vs.com/a/view/cnstock/" target="_blank">中国股市</a></li>
				<li><a href="http://www.vs.com/a/view/usstock/" target="_blank">欧美股市</a></li>
				<li><a href="http://www.vs.com/a/view/international/" target="_blank">国际商品</a></li>
			</ul>
        </div>
        <div class="copyright_contact">
            <h3>咨询电话</h3>
            <p style="font-size: 30px; color: #999; margin-top: 20px;"><span>400</span>-<span>852</span>-<span>8008</span></p>
            <p>工作日：8:30 - 24:00</p>
            <p>周末：09:00 - 17:00</p>
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
    </div>
</div> --%>
<%-- <div class="copybox">
    <div class="copybox_content">
        <div class="cp_link" style="padding-top: 30px;">
        	<ul class="copy_yqlj">
        		<li style="font-size: 12px;">友情链接：</li>
        		<li><a target="_blank" href="http://www.58che.com/2600/">沃尔沃S80</a></li>
        		<li><a target="_blank" href="http://www.shenmingbm.com/">上海注册公司</a></li>
        		<li><a target="_blank" href="http://www.starswealth.com/">第三方理财机构</a></li>
        		<li><a target="_blank" href="http://www.zhongdaihui.com/">众贷汇理财</a></li>
        		<li><a target="_blank" href="http://www.xj917.com/">新疆综合网</a></li>
        		<li><a target="_blank" href="http://90au.haouc.com/">劲舞团</a></li>
        	</ul>
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
            <a target="_blank" href="http://webscan.360.cn/index/checkwebsite?url=www.vs.com"><img src="${ctx}/static/images/image/anquan.png"></a>
            <a target="_blank" href="http://www.shuidixy.com/company/professionalInfo?s=94424583567954757334740627845150"><img style="opacity: 0.6;" src="${ctx}/static/images/image/small.png"></a>
        </div>
    </div>
</div> --%>

<div id="footer">
    <div id="iOS">
        <img src="http://cms.vs.com/templets/style/html/images/image/iosCode.png">
        <p>维胜金融APP下载</p>
    </div>
    <div class="footerList" >
            <ul>
                <li class="footerTitle">关于我们</li>
                <li><a href="http://www.vs.com/about">公司简介</a></li>
                <li><a href="http://www.vs.com/contact">联系我们</a></li>
                <li><a href="/vsnews/baodao/">媒体报道</a></li>
                <li><a href="/vsnews/help/">帮助中心</a></li>
            </ul>
            <ul>
                <li class="footerTitle">公司品牌</li>
                <li><a href=" http://www.vs.com/">维胜</a></li>
                <li><a href=" http://www.vs.com/">国际期货公司</a></li>
                <li><a href=" http://www.vs.com/">外盘期货公司</a></li>
                <li><a href=" http://www.vs.com/">成都期货</a></li>
            </ul>
            <ul>
                <li class="footerTitle">公司产品</li>
                <li><a href=" http://www.vs.com/">期货</a></li>
                <li><a href=" http://www.vs.com/">外盘期货</a></li>
                <li><a href=" http://www.vs.com/">恒指期货</a></li>
                <li><a href=" http://www.vs.com/">国际原油</a></li>
                <li><a href=" http://www.vs.com/">富时A50</a></li>
            </ul>
            <ul>
                <li class="footerTitle">公司业务</li>
                <li><a href=" http://www.vs.com/">期货开户</a></li>
                <li><a href=" http://www.vs.com/">国际期货开户</a></li>
                <li><a href=" http://www.vs.com/">期货交易</a></li>
                <li><a href=" http://www.vs.com/">期货行情</a></li>
                <li><a href=" http://www.vs.com/">外盘期货行情</a></li>
            </ul>
        </div>
    <div id="customerService" class="footerList">
        <ul>
            <li  class="footerTitle">客服热线</li>
            <li>400-852-8008</li>
            <li>工作日 08:30-24:00</li>
            <li>周末 09:00-17:00</li>
        </ul>
    </div>
</div>
<div id="Copyright">
    <div>
        <p>
			<span style="font-size: 12px;">友情链接：</span>
			<a target="_blank" href="http://www.58che.com/2600/">沃尔沃S80</a>
			<a target="_blank" href="http://www.shenmingbm.com/">上海注册公司</a>
			<a target="_blank" href="http://www.starswealth.com/">第三方理财机构</a>
			<a target="_blank" href="http://www.zhongdaihui.com/">众贷汇理财</a>
			<a target="_blank" href="http://www.xj917.com/">新疆综合网</a>
			<a target="_blank" href="http://90au.haouc.com/">劲舞团</a>
			<a target="_blank" href="http://www.xj917.com/">新抚州网</a>
			<a target="_blank" href="http://shpain.cn/">点子网</a>
	    </p>
    	相关网站：<a href="javascript:void(0);">证监会</a><a href="javascript:void(0);">银监会</a><a href="javascript:void(0);">保监会</a><a href="javascript:void(0);">深交所</a><a href="javascript:void(0);">上交所</a><a href="javascript:void(0);">新加坡交易所</a><a href="javascript:void(0);">新加坡交易所</a><a href="javascript:void(0);">香港交易所</a><a href="javascript:void(0);">纽约证券交易所</a><a href="#">纳斯达克</a>
    	<p>Copyright  2016  成都盈透科技有限公司  版权所有  蜀ICP备16018768号-1  投资有风险，入市需谨慎</p>
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
<script>
var phone = $("#mq_userName").val(); 
var userName = $("#mq_realName").val();
function yourFunction(servability) {
    // 你可以根据自己的需求编写相应的代码
    console.log('美洽网站插件初始化完毕。');
    if (servability) {
        console.log('有客服在线');
    } else {
        console.log('无客服在线');
    }
}
(function(m, ei, q, i, a, j, s) {
    m[i] = m[i] || function() {
        (m[i].a = m[i].a || []).push(arguments)
    };
    j = ei.createElement(q),
            s = ei.getElementsByTagName(q)[0];
    j.async = true;
    j.charset = 'UTF-8';
    j.src = '//static.meiqia.com/dist/meiqia.js';
    s.parentNode.insertBefore(j, s);
})(window, document, 'script', '_MEIQIA');
_MEIQIA('entId', '48917');
_MEIQIA('allSet', yourFunction);
//_MEIQIA('showPanel');
if(userName == null && phone != null){
	_MEIQIA('metadata', {
		tel: phone
    });
}else if(userName != null && phone == null) {
	_MEIQIA('metadata', {
        name: userName
    });
}else if(userName != null && phone !== null) {
	_MEIQIA('metadata', {
        name: userName,
        tel: phone
    });
}
</script>