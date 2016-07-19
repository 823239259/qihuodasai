<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.umpay.api.log.SysOutLogger"%>
<%@page import="com.tzdr.common.utils.ConfUtil"%>
<!-- 公司简介 -->
<%-- <div class="copyright">
    <div class="footer_ctn">
        <div class="ft_link">            
            <a href="${ctx}/news/newsdata?colname=8a2868ab4be94f15014be9a25cff03e0" target="_blank">达人动态</a>
            <a href="${ctx}/about" target="_blank">公司简介</a>            
            <a href="${ctx}/news/newsdata?colname=8a2868ab4be94f15014be9a17d1303df" target="_blank">市场热点</a>
            <a href="${ctx}/company" target="_blank">公司资质</a>
            <a href="${ctx}/help?tab=newbie&leftMenu=1" target="_blank">帮助中心</a>
            <a href="${ctx}/contact" target="_blank">联系我们</a>
        </div>
        <div class="ft_sina">
            <a href="http://www.weibo.com/shxhtzdr" target="_blank"></a>
            <p>新浪微博</p>
        </div>
        <div class="ft_wx">
            <div class="ft_wxtk" style="display: none;">
                <img src="${ctx}/static/images/common-new/code.jpg">
                <p>扫二维码关注<br>投资大人公众号</p>
            </div>
            <a href="javascript: void(0);"></a>
            <p>微信公众号</p>
        </div>
        <div class="ft_tel">
            <h3 class="ft_title">客服热线</h3>
            <h4>400-852-8008</h4>
            <p>工作日08:30-24:00   周末09:00-17:00</p>
        </div>
        <div class="ft_pic">
            <a href="${ctx}/company" class="ft_picweb" target="_blank"></a>
            <a href="${ctx}/help?tab=safety&leftMenu=7" class="ft_piceb" target="_blank"></a>
        </div>
        <div class="ft_qq">
            <h3 class="ft_title">交流互动</h3>
            <p>期货操盘QQ群：<a target="_blank" href="http://shang.qq.com/wpa/qunwpa?idkey=802aa738876d3098418e2bab4cd59451ddfc7ca7b9d7de3f20aa09777ddb8ed8">498543006</a>（国际+商品期货）</p>
            <p>股票操盘QQ群：<a target="_blank" href="http://shang.qq.com/wpa/qunwpa?idkey=eb656f50aa2927966fd7fc2f89d10f2bf943ad3f9cadf3bebfad9d6019bfd04a">196113230</a>（A股+港股操盘）</p>
        </div>
    </div>
</div>
<div class="cpbox">
    <div class="cp_copyright">
        <div class="cp_link">
            <p>Copyright ©  2016 成都盈透科技有限公司 版权所有 蜀ICP备16018768号-1<i>投资有风险   入市需谨慎</i></p>
            <p>友情链接：<a href="http://www.csrc.gov.cn/pub/newsite/" target="_blank">证监会</a><a href="http://www.cbrc.gov.cn/index.html"  target="_blank">银监会</a><a href="http://www.circ.gov.cn/web/site0/"  target="_blank">保监会</a><a href="http://www.szse.cn/"  target="_blank">深交所</a><a href="http://www.sse.com.cn/"  target="_blank">上交所</a><a href="http://www.sgx.com/"  target="_blank">新加坡交易所</a><a href="http://www.hkex.com.hk/"  target="_blank">香港交易所</a><a href="https://www.nyse.com/index"  target="_blank">纽约证券交易所</a><a href="http://www.nasdaq.com/"  target="_blank">纳斯达克</a></p>
        </div>
        <div class="cp_cppic">
            <a href="https://credit.szfw.org/CX20160302013752300118.html" target="_blank"><img src="${ctx}/static/images/common-new/cppic_01.gif"></a>
            <a href="http://webscan.360.cn/index/checkwebsite/url/www.tzdr.com" target="_blank"><img src="${ctx}/static/images/common-new/cppic_02.gif"></a>
            <a href="http://www.anquan.org/authenticate/cert/?site=www.tzdr.com&at=realname" target="_blank"><img src="${ctx}/static/images/common-new/cppic_03.gif"></a>
        </div>
    </div>
</div> --%>
<div style="background: #333;height: 327px;" id="ungundontiao">
<div class="copyright">
    <div class="copyright_content">
        <div class="copyright_link">
            <ul class="link">
                <li><a href="${ctx }/news/newsdata?colname=ff8080814e956d02014e95788e300001" target="_blank">维胜动态</a></li>
                <li><a href="${ctx }/about" target="_blank">公司简介</a></li>
                <li><a href="${ctx }/news/newsdata?colname=ff8080814f73cadc014f73cf0f6b0001" target="_blank">市场热点</a></li>
                <%-- <li><a href="${ctx }/company" target="_blank">公司资质</a></li> --%>
                <li><a href="${ctx }/help?tab=newbie&leftMenu=1" target="_blank">帮助中心</a></li>
                <li><a href="${ctx }/contact" target="_blank">联系我们</a></li>
            </ul>
           <%--  <ul class="follow">
                <li>关注我们：</li>
                <li><a href="http://www.weibo.com/shxhtzdr" target="_blank" class="weibo"></a></li>
                <li class="erweima" style="position: relative;">
                	<a href="javascript: void(0);" target="_blank" class="weixin"></a>
		            <div class="erweima-wxtk" style="display: none;">
		                <img src="${ctx}/static/images/common-new/erweima.png">
		            </div>
                </li>
            </ul> --%>
        </div>
        <div class="copyright_contact">
            <h3>客服热线</h3>
            <p>400-852-8008</p>
            <p>工作日：8:30 - 24:00</p>
            <p>周末：09:00 - 17:00</p>
        </div>
        <!-- <div class="copyright_interaction">
            <h3>交流互动</h3>
            <p>期货操盘QQ群：</p>
            <p><a target="_blank" href="http://shang.qq.com/wpa/qunwpa?idkey=802aa738876d3098418e2bab4cd59451ddfc7ca7b9d7de3f20aa09777ddb8ed8">498543006</a></p>
            <p>国际期货</p>
        </div> -->
    </div>
</div>
<div class="copybox">
    <div class="copybox_content">
        <div class="cp_link">
            <ul>
                <li>友情链接：</li>
                <li><a href="http://www.csrc.gov.cn/pub/newsite/" target="_blank">证监会</a></li>
                <li><a href="http://www.cbrc.gov.cn/index.html" target="_blank">银监会</a></li>
                <li><a href="http://www.circ.gov.cn/web/site0/" target="_blank">保监会</a></li>
                <li><a href="http://www.szse.cn/" target="_blank">深交所</a></li>
                <li><a href="http://www.sse.com.cn/" target="_blank">上交所</a></li>
                <li><a href="http://www.sgx.com/" target="_blank">新加坡交易所</a></li>
                <li><a href="http://www.hkex.com.hk/" target="_blank">香港交易所</a></li>
                <li><a href="https://www.nyse.com/index" target="_blank">纽约证券交易所</a></li>
                <li><a href="http://www.nasdaq.com/" target="_blank">纳斯达克</a></li>
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
<script type="text/javascript">
 $(function () {
	 // 加载最新公告
    var showNotice = false;
    var content="";
    $.ajax({
    	url:basepath+"findnewData",
    	data:{},
    	type:'POST',
    	success:function(nitives){
    		var reg1=new RegExp("&lt;","g"); 
    		var reg2=new RegExp("&gt;","g"); 
    		$(nitives).each(function(){
    			content = $(this).attr("content");
    			content=content.replace(reg1,"<");
    			content=content.replace(reg2,">");
    			$('.notice-content').html();
   				$('#noticeid').val($(this).attr("version"));
   			 	// 检查公告
   	   		    checkNotice();
   	   		    showNotice = true;
    		});
    	},dataType:'json'
    })
    
    $(window).scroll(function () {
    	if(showNotice) {
		    var scrollTop = $(this).scrollTop(); //滚动条位置
		    var scrollHeight = $(document).height();//高度
		    var windowHeight = $(this).height();//整体高度
		    if (scrollTop + windowHeight >= scrollHeight-80) {
		    	$(".notice-fixed").fadeOut();
		    	$(".notice-relative").fadeIn();
			} else {
				$(".notice-fixed").fadeIn();
				$(".notice-relative").fadeOut();
			}
    	}
	});
    /*二维码*/
     $('.follow .erweima').hover(function() {
        $('.erweima-wxtk').show();
    }, function() {
        $('.erweima-wxtk').hide();
    }); 
    
}); 
	
//检测公告
function checkNotice() {
	var noticeid = getCookie("noticeid");
	if(noticeid != null && noticeid.length > 0) {
		$(".site-notice").remove();
	} else {
		$(".notice-fixed").fadeIn("slow");
	}
}
// 关闭公告
function closeNotice() {
	$(".site-notice").remove();
	addCookie("noticeid", $("#noticeid").val());
}

function addCookie(objName, objValue){
	if(objValue==""){
		var Num="";
		for(var i=0;i<6;i++){ 
			Num+=Math.floor(Math.random()*10); 
		} 
		objValue=Num;
	}
	var days = 365; 
    var exp = new Date(); 
    exp.setTime(exp.getTime() + days*24*60*60*1000); 
    document.cookie = objName+"="+ escape (objValue)+";path=/;expires="+exp.toGMTString(); 
}
//获取指定名称的cookie的值 
function getCookie(c_name) {
	if (document.cookie.length > 0) {
		var c_start = document.cookie.indexOf(c_name + "=");
		if(c_start != -1) {
			c_start = c_start + c_name.length + 1; 
			c_end = document.cookie.indexOf(";", c_start)
			if(c_end == -1) {
				c_end = document.cookie.length;
			}
		    return unescape(document.cookie.substring(c_start, c_end))
		}
	}
	return ""
} 
</script><!-- <script src='http://w.cnzz.com/q_stat.php?id=1256807294&l=3' language='JavaScript'></script> -->
<span style="display: none"><script src="https://s95.cnzz.com/z_stat.php?id=1259839078&web_id=1259839078" language="JavaScript"></script></span>
