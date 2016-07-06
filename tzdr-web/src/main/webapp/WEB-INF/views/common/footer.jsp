<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 公司简介 -->
<div class="copyright">
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
                <p>扫二维码关注<br>投资达人公众号</p>
            </div>
            <a href="javascript: void(0);"></a>
            <p>微信公众号</p>
        </div>
        <div class="ft_tel">
            <h3 class="ft_title">客服热线</h3>
            <h4>400-020-0158</h4>
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
            <p>Copyright ©  2016 上海信闳投资管理有限公司 版权所有 沪ICP备14048395号-1<i>投资有风险   入市需谨慎</i></p>
            <p>友情链接：<a href="http://www.csrc.gov.cn/pub/newsite/" target="_blank">证监会</a><a href="http://www.cbrc.gov.cn/index.html"  target="_blank">银监会</a><a href="http://www.circ.gov.cn/web/site0/"  target="_blank">保监会</a><a href="http://www.szse.cn/"  target="_blank">深交所</a><a href="http://www.sse.com.cn/"  target="_blank">上交所</a><a href="http://www.sgx.com/"  target="_blank">新加坡交易所</a><a href="http://www.hkex.com.hk/"  target="_blank">香港交易所</a><a href="https://www.nyse.com/index"  target="_blank">纽约证券交易所</a><a href="http://www.nasdaq.com/"  target="_blank">纳斯达克</a></p>
        </div>
        <div class="cp_cppic">
            <a href="https://credit.szfw.org/CX20160302013752300118.html" target="_blank"><img src="${ctx}/static/images/common-new/cppic_01.gif"></a>
            <a href="http://webscan.360.cn/index/checkwebsite/url/www.tzdr.com" target="_blank"><img src="${ctx}/static/images/common-new/cppic_02.gif"></a>
            <a href="http://www.anquan.org/authenticate/cert/?site=www.tzdr.com&at=realname" target="_blank"><img src="${ctx}/static/images/common-new/cppic_03.gif"></a>
        </div>
    </div>
</div>
<!-- 网站公告 -->
<div class="site-notice notice-fixed" style="display: none;">
	<div class="notice-style">
		<h3 class="notice-title">尊敬的用户：</h3>
		<div class="notice-content"></div>
		<a href="javascript: closeNotice();" class="notice-close"></a>
	</div>
</div>
<input id="noticeid" type="hidden" value="123456" />
<div class="site-notice fl_notic" style="position: relative;display: none;">
    <div class="fl_ntctn">
        <h3>尊敬的用户：</h3>
        <div class="notice-content"></div>
        <a href="javascript: closeNotice();" class="fl_n_close"></a>
    </div>
</div>
<script type="text/javascript">
	$(document).ready(function() {
	    $('.ft_wx a').hover(function() {
	        $('.ft_wxtk ').show();
	    }, function() {
	        $('.ft_wxtk ').hide();
	    });
	    
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
	    			$('.notice-content').html(content);
	    			$('#noticeid').val($(this).attr("version"));
	    		    // 检查公告
	    		    checkNotice();
	    		    showNotice = true;
	    		})
	    	},dataType:'json'
	    })
	    
	    $(window).scroll(function () {
	    	if(showNotice) {
			    var scrollTop = $(this).scrollTop();//滚动条位置
			    var scrollHeight = $(document).height();//高度
			    var windowHeight = $(this).height();//整体高度
			    if (scrollTop + windowHeight >= scrollHeight-80) {
			    	$(".notice-fixed").fadeOut("fast");
			    	$(".fl_notic").fadeIn("fast");
				} else {
					$(".fl_notic").fadeOut("fast");
					$(".notice-fixed").fadeIn("fast");
				}
	    	}
		});
	});
	// 检测公告
	function checkNotice() {
		var noticeid = getCookie("noticeid");
		var loaclNoticeid = $("#noticeid").val();
		if(noticeid === loaclNoticeid) {
			$(".site-notice").remove();
		} else {
			$(".notice-fixed").fadeIn("slow");
		}
	}
	// 关闭公告
	function closeNotice() {
		$(".site-notice").remove();
		// cookie记录公告已删除
		addCookie("noticeid", $("#noticeid").val());
	}
	//添加cookie 
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
