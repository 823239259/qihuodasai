<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.umpay.api.log.SysOutLogger"%>
<%@page import="com.tzdr.common.utils.ConfUtil"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<meta name="viewport" content="width=1010">
	<!-- <meta name="viewport" content="user-scalable=no" /> -->
	<title>维胜 - 中国领先的国际期货及衍生品互联网交易平台</title>
	<meta name="description" content="维胜投身普惠金融互联网服务，以网络平台为A股、港股、美股、富时A50、恒指期货、国际原油等金融产品的操盘提供便利条件。" />
	<%
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		String casServerLoginUrl=ConfUtil.getContext("SSO.casServer.loginUrl");
		String imgPreURL = ConfUtil.getContext("banner.url");
	%>
	<c:set var="ctx" value="<%=basePath%>"></c:set>
	<c:set var="v" value="20151127"></c:set>
	<c:set var="imgPreURL" value="<%=imgPreURL %>"></c:set>
	<%-- <link rel="stylesheet" href="${ctx}/static/css/common.css?v=${v}">
	<!-- custom css -->
	<link rel="stylesheet" href="${ctx}/static/css/home.css?v=${v}"> --%>
	<link rel="stylesheet" href="${ctx }/static/css/new_index.css?v=${v}">
	<link href="${ctx }/static/css/trade.css?v=${v}" rel="stylesheet" type="text/css" />
    <script src="${ctx }/static/script/homepage/gundongtiao.js?v=${v}"></script>
	<!-- common css -->
	<link rel="shortcut icon" href="${ctx}/static/ico/icon.png">
	<!-- common js -->
	<script src="${ctx}/static/script/common/jquery-1.8.0.min.js?v=${v}"></script>
	<script type="text/javascript">
		var basepath = "<%=basePath%>" + "/";
		var casServerLoginUrl = "<%=casServerLoginUrl%>";
	</script>
	<script src="${ctx }/static/script/esl.js?v=${v}"></script>
    <script src="${ctx }/static/script/slide-box.js?v=${v}"></script>
	<style type="text/css">
	.ft_wx a:hover { background: url(../static/images/common-new/wxon.png) no-repeat; }
	.ft_wx a { display: block; width: 50px; height: 50px; background: url(../static/images/common-new/wx.png) no-repeat;}
	.navlist li a.on{color: #ffcc33; border-bottom:2px solid #ffcc33; padding-bottom: 26px;}
	.left_hidden{float: left; width: 195px; }
	#left_xiangqing{background: #333; float: left;height: 508px;  width: 195px; position: relative;overflow: hidden;}
    #left_xiangqing .w_content .left_hidden{float: left; width: 195px;}
    #left_xiangqing .scroll_y{background: #fc3;position: absolute; right: 0;width: 5px;}
    #left_xiangqing .scroll_ymove{background: #292929; height: 508px;position: absolute; right: 0;width: 5px;z-index: 2;}
	</style>
</head>
<body>
<!-- header -->
<%@include file="../common/header.jsp"%>
<!-- 广告切换 -->
<div class="bannerlist">
    <div class="ad_slider" id="ad-slider">
        <c:forEach var="b" items="${banners }" varStatus="status">
        	<a  title="${status.count }" <c:if test="${status.index }==0">class="on"</c:if>><span></span></a>
        </c:forEach>
    </div>
    <div class="slide_box" id="slide-box">
       	<div class="slide_banner">
           <c:forEach var="b" items="${banners }" varStatus="status">
	           	<c:if test="${not empty b.linkUrl }">
			    	<a href="${b.linkUrl }" target="_blank" style="display: none;"><img src="${imgPreURL }${b.imgPath }" title="banner" alt="banner"></a>
	           	</c:if>
	           	<c:if test="${empty b.linkUrl }">
			    	<a href="javascript:void(0);"  style="display: none;"><img src="${imgPreURL }${b.imgPath }" title="banner" alt="banner"></a>
	           	</c:if>
	       </c:forEach>
    	</div>
    </div>
    <div class="login">
	        <div class="loginbox">
	        <%
	       		if(request.getSession().getAttribute("userName")!=null){
	       	%>
			<div class="lgctn" id="logondiv">
				<h3 class="lg_user">您好，<i>${mobile}</i><a href="${ctx}/logout">【安全退出】</a></h3>
				<div class="lg_info">
					<p class="yuei">账户余额：${usermap.user_avlbal }元</p>
					<div>
	            		<a href="${ctx}/user/account" class="lg_chaopan">操盘账户</a>
	            		<%-- <a href="<%=ConfUtil.getContext("p2p.user.account") %>" style="display:none;">投资账户</a> --%>
	            	</div>
	            	<p class="lg_time" style="display:block;">上次登录时间：<br><i>${lastLoginTime}</i></p>
				</div>
	        	<p style="border-top: 1px solid #4d4d4d"></p>
	        </div>
	       	<%
	       		}else{
	       	%>
	        <div class="lgctn" id="logindiv">
	            <h3>登录</h3>
	             <form id="loginForm" name="loginForm" action="<%=casServerLoginUrl%>" onsubmit="return loginValidate();" method="post" target="ssoLoginFrame">
	                <input type="hidden" name="isajax" value="true">
	                <input type="hidden" name="isframe" value="true">
	                <input type="hidden" name="lt" value="" id="LoginTicket">
	                <input type="hidden" name="execution" value="e3s1" id="J_FlowExecutionKey">
	                <input type="hidden" name="_eventId" value="submit">
	                <div class="lg_ip">
	                    <div class="lg_ipctn">
	                        <i class="user"></i>
	                        <input type="tel" id="username" name="username" value="" placeholder="请输入手机号码">
	                        <!-- 请输入手机号码 -->
	                    </div>
	                    <div class="lg_ipctn">
	                        <i class="password"></i>
	                        <input type="password" id="password" name="password" value="" placeholder="请输入登录密码">
	                        <!-- 请输入登录密码 -->
	                    </div>
	                    <div class="lg_btn"><button id="login" type="button">立即登录</button></div>
	                    <div class="lg_link">
	                        <a href="${ctx}/forgetpw" class="left">忘记密码?</a>
	               			 <a href="${ctx}/signin" class="right">免费注册</a>
	                    </div>
	                </div>
	            </form>
	            </div>
	            <% } %>
	            <div class="lg_bottom"></div>
   		</div>
    </div>
</div>
<!-- 最新公告 -->
<div style="background: #333; height: 46px;">
<div class="notice  h_notic">
    <div class="notice_scroll">
        <h2><i></i>最新动态：</h2>
        <ul class="h_noticlist" id="h_scroll">
        </ul>
         <a href="${ctx}/news/newsdata" class="h_n_more" target="_blank">更多动态</a>
    </div>
</div>
</div>
<!--content-->
<div class="w_content_app">
<div class="w_content">
    <div class="w_center">
        <div class="w_center_border"></div>
        <div class="w_center_top">
            <div class="left-shangzheng">
                <p>实时行情</p>
            </div>
            <div class="right-gengxin">
            	<a href = "#" id = "mainSqcp" target="_blank">申请操盘</a>
                <p><span class = "jk"></span><span class = "fd"></span></p>
                <p><span class = "zs"></span><span class = "gxsj"></span></p>
            </div>
        </div>
        <div class="w_center_xiangqing">
            <div class="left_xiangqing" id="left_xiangqing">
                <div class="left_hidden"></div>
                <div class="scroll_ymove">
        			<div class="scroll_y" unorbind="unbind"></div>
    			</div>
		    <!--<div class="scroll_xmove">
		        	<div class="scroll_x" unorbind="unbind"></div>
		    	</div>-->
    			<input type="hidden" id="whichscro">
            </div>
            <div style="width: 5px; height: 510px; background: #292929; float: left; position: relative; right: 5px;"></div>
            <div class="right_xiangqing">
                <div id="main" style="height:500px; width: 800px;"></div>
            </div>
        </div>
    </div>
    <div class="w-qihuo">
        <div class="w_center_border"></div>
        <div class="w-qihuo-title">
            <h3>国际期货</h3>
            <a href="${ctx}/outDisk/index" target="_blank">查看更多</a>
        </div> 
        <div class="w-qihuo-content">
            <div class="w-qihuo-caopan">
                <div class="w-qihuo-img"><img src="static/images/image/qidai-1.png" alt=""/></div>
                <p><i class="gou"></i>交人民币保证金操盘美元账户</p>
                <p><i class="gou"></i>保证金交易 以小博大</p>
                <p><i class="gou"></i>白天晚上都可以交易</p>
                <p><i class="gou"></i>极速开户 T+0结算到账</p>
                <p style="padding-left: 0;"><a href="${ctx}/help?tab=rule&leftMenu=5" target="_blank">操盘细则</a><a href="${ctx}/help?tab=software&leftMenu=1" target="_blank">实盘系统下载</a></p>
            </div>
            <div class="w-guopjiqihuo">
                <h3>【恒指期货】</h3>
                <h4><i>${showmap.hsi[0].valueData }</i>元/双边</h4>
                <ul>
                    <li>• 港股指数 金融市场更加成熟</li>
                    <li>• 交易灵活 技术分析更有效</li>
                    <li>• 一天12个小时可以交易 盈利时间长</li>
                    <li>• T+0交易 随时锁定利润</li>
                </ul>
                <div class="money">
                    <p>总共操盘: ${showmap.hsi[1].valueData }人</p>
                    <p>总共交易: ${showmap.hsi[2].valueData }手</p>
                </div>
                <p class="chaopan"><a href="${ctx}/hsi/index" target="_blank">申请操盘</a></p>
            </div>
            <div class="w-guopjiqihuo">
                <h3>【国际原油】</h3>
                <h4><i>${showmap.fco[0].valueData }</i>元/双边</h4>
                <ul>
                    <li>• 全球玩家用户量最大 涨跌迅猛</li>
                    <li>• 高透明度便于基本面分析</li>
                    <li>• 全球交易市场无人操控</li>
                    <li>• 来自纽约商业交易所 行业标准</li>
                </ul>
                <div class="money">
                    <p>总共操盘: ${showmap.fco[1].valueData }人</p>
                    <p>总共交易: ${showmap.fco[2].valueData }手</p>
                </div>
                <p class="chaopan"><a href="${ctx}/crudeoil/index" target="_blank">申请操盘</a></p>
            </div>
            <div class="w-guopjiqihuo" style="border-right: 10px solid #292929;">
                <h3>【富时A50】</h3>
                <h4><i>${showmap.ffa50[0].valueData }</i>元/双边</h4>
                <ul>
                    <li>• A股精准风向标 免庄家操控</li>
                    <li>• 门槛低 超短线 交易灵活</li>
                    <li>• 国际版“IF指数” 操盘人数多</li>
                    <li>• 来自新加坡交易所 透明公正</li>
                </ul>
                <div class="money">
                    <p>总共操盘: ${showmap.ffa50[1].valueData }人</p>
                    <p>总共交易: ${showmap.ffa50[2].valueData }手</p>
                </div>
                <p class="chaopan"><a href="${ctx}/ftse/index" target="_blank">申请操盘</a></p>
            </div>
        </div>
    </div>
</div>
</div>
<div style="background: #333; height: 580px;">
<div class="safe">
    <div class="safelist">
        <ul>
            <li>
                <p class="safelist-img"><img src="static/images/image/chuangxin.png"></p>
                <h2>模式创新</h2>
                <p class="safelist-lis">光速开户，降低交易门槛</p>
            </li>
            <li>
                <p class="safelist-img"><img src="static/images/image/zijin.png"></p>
                <h2>资金安全</h2>
                <p class="safelist-lis">专款专用，资金封闭管理</p>
            </li>
            <li>
                <p class="safelist-img"><img src="static/images/image/jiaoyi.png"></p>
                <h2>交易安全</h2>
                <p class="safelist-lis">杜绝对赌，保障您的交易安全</p>
            </li>
            <li>
                <p class="safelist-img"><img src="static/images/image/zhuanye.png"></p>
                <h2>专业指导</h2>
                <p class="safelist-lis">投资管家，提供指导策略</p>
            </li>
        </ul>
    </div>
</div>
<div class="xuanzhe">
    <div class="xuanzhelist">
        <p class="xuanzhe-title">炒期货为什么选择维胜</p>
        <p style="width: 850px;margin: 0 auto;">
            <img src="static/images/image/zjichao.png" alt=""/>
            <img src="static/images/image/vs.png" alt="" class="vs-img"/>
            <img src="static/images/image/weishengchao.png" alt=""/>
        </p>
    </div>
</div>
</div>
<div class="h_partner_app">
<div class="h_partner">
    <div class="h_partner_content">
        <p class="h_chosetitle"><span>合作伙伴与媒体报道</span></p>
        <p class="h_img">
            <a href="javascript:void(0)"><img src="static/images/image/tengxun-bank.png" style="margin-left: 0px" alt=""/></a>
            <a href="javascript:void(0)"><img src="static/images/image/xinlang-bank.png" alt=""/></a>
            <a href="javascript:void(0)"><img src="static/images/image/huaxing-bank.png" alt=""/></a>
            <a href="javascript:void(0)"><img src="static/images/image/huanan-bank.png" alt=""/></a>
            <a href="javascript:void(0)"><img src="static/images/image/anjin-bank.png" alt=""/></a>
            <a href="javascript:void(0)"><img src="static/images/image/xiangcai-bank.png" alt=""/></a>
            <a href="javascript:void(0)"><img src="static/images/image/zhida-bank.png" alt=""/></a>
            <a href="javascript:void(0)"><img src="static/images/image/zhongdian-bank.png" style="margin-right: 0px" alt=""/></a>
        </p>
    </div>
</div>
</div>
<input type= "hidden" id = "dqCommodNo"/>
<!-- footer -->
<%@include file="../common/footer.jsp"%>
<!-- custom js -->
<script src="static/script/homepage/homepage.js?version=20151127"></script>
<%@ include file="/WEB-INF/views/common/count.jsp"%>
</body>
<script type="text/javascript">
	   var href = window.location.href;
	 	// 路径配置
	    require.config({
	        paths:{//${ctx }/static/script
	            'echarts' :href + '/static/script/echarts',
	            'echarts/chart/pie' :href + '/static/script/echarts'
	        }
	    });
	 	
	    var rawData = [];
	    var loadCount = 0;
	    var loadKcount = 0 ;
	    var	 myChart = null;
	    //加载K线图数据模型
	    function loadKData(commodity,contract){
	    	$(function(){
	    		var rawDataLength = rawData.length - 1;
	            var url = "Quotation/doGetQk?commodity="+commodity+"&contract="+contract;
	            if(rawDataLength > 0){
	            	var beginTime = rawData[rawDataLength][0];
	            	url +="&beginTime="+beginTime;
	            }
	            $.ajax({
		           	 url : url,
		   			 type:"get",
		   			 dateType:"json",
		   			 success:function(data){
		   				 var resultData = data.data;
		   				 /* var addKData = []; */
		   					$.each(resultData,function (i,item){
		   						var j = rawDataLength + i;
		   						var openPrice = item.OpenPrice;
		   						var closePrice = item.LastPrice;
		   						var chaPrice = closePrice - openPrice;
		   						var sgData = [item.DateTime,openPrice,closePrice,chaPrice,"",item.LowPrice,item.HighPrice,"","","-"];
		   						rawData[j] = sgData;
		   					});
		   					//追加到容器中
		   					var option = setOption(rawData);
		   					if(myChart != null){
			   					myChart.setOption(option);
		   					}
		   				 }
	            });
	    	});
	    }
	    //设置数据参数（为画图做准备）
	    function setOption(rawData){
	    	var dates = rawData.map(function (item) {
	            return item[0];
	        });

	        var data = rawData.map(function (item) {
	            return [+item[1], +item[2], +item[5], +item[6]];
	        });
	    	var option = {
	                title: {
	                    text: '1分钟K线',
	                    textStyle: {
	                    	color : "#ffcc33"
	                    }
	                },
	                backgroundColor: '#333',
	                tooltip: {
	                    trigger: 'axis',
	                    axisPointer: {
	                        animation: false,
	                        lineStyle: {
	                            color: '#376df4',
	                            width: 2,
	                            opacity: 1
	                        }
	                    }
	                },
	                xAxis: {
	                    type: 'category',
	                    data: dates,
	                    axisLine: { lineStyle: { color: '#8392A5' } }
	                },
	                yAxis: {
	                    scale: true,
	                    axisLine: { lineStyle: { color: '#8392A5' } },
	                    splitLine: { show: false }
	                },
	                dataZoom: [{
	                    textStyle: {
	                        color: '#8392A5'
	                    },
	                    handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
	                    handleSize: '80%',
	                    dataBackground: {
	                        areaStyle: {
	                            color: '#8392A5'
	                        },
	                        lineStyle: {
	                            opacity: 0.8,
	                            color: '#8392A5'
	                        }
	                    },
	                    handleStyle: {
	                        color: '#fff',
	                        shadowBlur: 3,
	                        shadowColor: 'rgba(0, 0, 0, 0.6)',
	                        shadowOffsetX: 2,
	                        shadowOffsetY: 2
	                    }
	                }, {
	                    type: 'inside'
	                }],
	                animation: false,
	                series: [
	                    {
	                        type: 'candlestick',
	                        name:"1分钟K线",
	                        data: data,
	                        itemStyle: {
	                            normal: {
	                                color: '#FD1050',
	                                color0: '#0CF49B',
	                                borderColor: '#FD1050',
	                                borderColor0: '#0CF49B'
	                            }
	                        }
	                    }
	                ]
	            };
	    	return option;
	    }
	    function calculateMA(dayCount, data) {
	        var result = [];
	        for (var i = 0, len = data.length; i < len; i++) {
	            if (i < dayCount) {
	                result.push('-');
	                continue;
	            }
	            var sum = 0;
	            for (var j = 0; j < dayCount; j++) {
	                sum += data[i - j][1];
	            }
	            result.push(sum / dayCount);
	        }
	        return result;
	    } 
	    
	    //生成一个K线图容器
	    function loadK(){
	    	 // 使用
	        require(
	                [
	                    'echarts',
	                    'echarts/chart/pie' // 使用柱状图就加载bar模块，按需加载
	                ],
	                function (ec) {
	                	/* document.getElementById('main').innerHTML = ""; */
	                    // 基于准备好的dom，初始化echarts图表
	                    myChart = ec.init(document.getElementById('main'));
	                	var option = setOption(rawData);
	    			    // 为echarts对象加载数据
		    			myChart.setOption(option);
	                }
	        );
	    }
	    var interValData = null;
	    function bindLoadKDataInterValData(CommodityNo,contract){
	    	interValData = window.setInterval(function(){loadKData(CommodityNo,contract)}, 3000);
	    }
	    function clearLoadKDataInterVal(){
	    	if(interValData != null){
		    	window.clearInterval(interValData);
	    	}
	    }
	    function exctionLoadK(CommodityNo,contract){
		 	clearLoadKDataInterVal();
		 	bindLoadKDataInterValData(CommodityNo,contract);
	    }
	    function propHrefCP(commod){
	    	$(function(){
		    	if(commod == "HSI"){
			 		 $("#mainSqcp").prop("href",href + "hsi/index");
			 	 }else if(commod == "CL"){
			 		 $("#mainSqcp").prop("href",href + "crudeoil/index");
			 	 }else if(commod == "CN"){
			 		 $("#mainSqcp").prop("href",href + "ftse/index");
			 	 }else{
			 		 $("#mainSqcp").prop("href",href + "outDisk/index");
			 	 }
	    	});
	    }
	    var contractData = [];
	    function sendMessage(method,parameters){
	    	socket.send('{"Method":"'+method+'","Parameters":'+parameters+'}');
	    }
	    
	    var url = "ws://socket.dktai.com:9002";
	    var socket = new WebSocket(url);
	    socket.onopen = function(evt){
			sendMessage('Login','{"serName":"13677622344","PassWord":"a123456"}');
	     };
	    socket.onclose = function(evt){
	    };
	    socket.onmessage = function(evt){
	    	$(function(){
		    	var data = evt.data;
		    	var jsonData = JSON.parse(data);
		    	var method = jsonData.Method;
		    	//用户登录
		    	if(method == "OnRspLogin"){
		    		//发送消息获取品种
		    		sendMessage('QryCommodity','""')
		    	}else if(method == "OnRspQryCommodity"){
		    		var commoditys = jsonData.Parameters;
		    		console.log(jsonData);
		    		var size = commoditys.length;
		    		//生成实时行情html
		    		console.log(commoditys);
		    		for(var i = 0 ; i < size ; i++){
		    			var _data = commoditys[i];
		    			var html ="";
		    			var doSize = _data.DotSize;
		    			html += '<input type="hidden" class="doSize'+i+'" value = "'+doSize+'" /><div   class="left_xiangmu left_x'+i+'';
						 if(i  == 0){
						 	 html += ' on';
						 }
						 html +=  '"> <p><em  class = "right"></em>'
						 	  + '<span class = "qlast'+i+' left"></span>'
						 	  + '<span class = "qchange'+i+' right"></span>'
						 	  + '<span class = "scal'+i+' left"></span>';
						 $(".w_center_xiangqing .left_xiangqing .left_hidden").append(html);
						 contractData[i] = {'cname':_data.CommodityName,'Cmno':_data.CommodityNo,'doSize':doSize};
						 $(".left_x"+i+"").bind("click",function(){
							 rawData=[];
							 loadK();
							 var obj = $(this);
							 var da = obj.attr("data");
							 if(da != null){
							 	  var daArray = da.split("&");
							 	  var commod = daArray[0];
							 	  var contract = daArray[1];
							 	  loadKData(commod,contract);
							 	  exctionLoadK(commod,contract);
							 	  $("#dqCommodNo").val(commod);
							 	  propHrefCP(commod);
							 }
							 var topData = obj.attr("topData");
							 if(topData != null){
								 var topDataArray = topData.split("&");	
								 $(".zs").text("昨收:"+topDataArray[0]);	
								 $(".fd").text("每日幅度:"+topDataArray[1]+' - '+topDataArray[2]);
								 $(".jk").text("今开:"+topDataArray[3]);
								 $(".gxsj").text("更新时间:"+_data.TimeStamp);
							 }
							 var left_xiangmu   = $(".w_content .w_center_xiangqing .left_xiangmu");
							 left_xiangmu.each(function(){
								 left_xiangmu.removeClass('on');
							 });
							 obj.addClass('on');
						 });
						 var left_xiangmu   = $(".w_content .w_center_xiangqing .left_xiangmu");
						 left_xiangmu.each(function(){
						    left_xiangmu.on('touchstart',function(){
						    	rawData=[];
								 loadK();
								 var obj = $(this);
								 var da = obj.attr("data");
								 if(da != null){
								 	  var daArray = da.split("&");
								 	  var commod = daArray[0];
								 	  var contract = daArray[1];
								 	  loadKData(commod,contract);
								 	  exctionLoadK(commod,contract);
								 	  $("#dqCommodNo").val(commod);
								 	  propHrefCP(commod);
								 }
								 var topData = obj.attr("topData");
								 if(topData != null){
									 var topDataArray = topData.split("&");	
									 $(".zs").text("昨收:"+topDataArray[0]);	
									 $(".fd").text("每日幅度:"+topDataArray[1]+' - '+topDataArray[2]);
									 $(".jk").text("今开:"+topDataArray[3]);
									 $(".gxsj").text("更新时间:"+_data.TimeStamp);
								 }
						    	left_xiangmu.removeClass('on');
						        $(this).addClass('on');
						    });
						 })
		    		}
		    		for(var i = 0 ; i < size ; i++){
		    			var comm = commoditys[i];
		    			sendMessage('Subscribe','{"CommodityNo":"'+comm.CommodityNo+'","ContractNo":"'+comm.MainContract+'"}');
		    		}
		    	}else if(method == "OnRspSubscribe"){
		    	}else if(method == "OnRtnQuote"){
		    		var size = contractData.length;
		    		var newCommNo = jsonData.Parameters.CommodityNo;
		    		var flag = false;
		    		for(var i = 0 ;i < size ; i ++){
		    			var con = contractData[i].Cmno;
		    			if(newCommNo == con){
		    				contractData[i].Parameters = jsonData;
				    		break;
		    			}
		    		}
		    		var contractLength = contractData.length;
		    		var loadInitCommod = null;
		    		if(contractLength > 0){
		    			loadInitCommod = contractData[0].Cmno;
		    		}
		    	   for(var i = 0 ; i < contractLength; i++){
						 var _data_ = contractData[i];
						 if(_data_.hasOwnProperty("Parameters")){
							 var _data = _data_.Parameters.Parameters;
							 var size = $(".doSize"+i+"").val();
							 var qlastPrice = (parseFloat(_data.QLastPrice)).toFixed(size);
							 var qpreCloseingPrice = (parseFloat(_data.QPreClosingPrice)).toFixed(size);
							 var qLowPrice = (parseFloat(_data.QLowPrice)).toFixed(size);
							 var qHighPrice = (parseFloat(_data.QHighPrice)).toFixed(size);
							 var qOpenPrice = (parseFloat(_data.QOpenPrice)).toFixed(size);
							 var scal = (parseFloat(_data.QChangeRate)).toFixed(size);
							 var qChangeValue = (parseFloat(_data.QChangeValue)).toFixed(size);
							 var qBidPrice1 = (parseFloat(_data.QBidPrice1)).toFixed(size);
							 var qAskPrice1 =  (parseFloat(_data.QAskPrice1)).toFixed(size);
							 var bs = "↑";
							 var jj = "+";
							 var color = " #ff5500";
							 if(_data.QChangeRate < 0){
								 bs = "↓";
								 jj = "";
								 color = "#0bffa4";
							 }
							 $(".left_x"+i+"").attr("data",""+_data.CommodityNo+"&"+_data.ContractNo+"");
							 $(".left_x"+i+" .right").text(_data_.cname);
							 $(".qlast"+i+"").text(qlastPrice + " " + bs);
							 $(".qchange"+i+"").text(jj + qChangeValue);
							 $(".scal"+i+"").text(jj + scal + "%");
							 $(".qlast"+i+"").css("color",color);
							 $(".qchange"+i+"").css("color",color);
							 $(".scal"+i+"").css("color",color);
							 var CNo = $("#dqCommodNo").val();
							 if(CNo == _data.CommodityNo){
								 $(".zs").text("最新买价:"+qBidPrice1);	
								 $(".fd").text("最新价:"+qlastPrice);
								 $(".jk").text("最新卖价:"+qAskPrice1);
								 $(".gxsj").text("更新时间:"+_data.TimeStamp);
							 }
							 if(loadInitCommod != null && loadInitCommod == _data.CommodityNo && loadCount == 0){
								  loadK();
								  rawData=[];
							 	  var cd = _data.CommodityNo;
							 	  var ct = _data.ContractNo;
							 	  loadKData(cd,ct);
							 	  exctionLoadK(cd,ct);
							 	  $("#dqCommodNo").val(cd);
							 	  propHrefCP(cd);
								 loadCount++;
							 }
					 }
		    	   }
		    	}
	    	});
	    };
	    socket.onerror = function(evt){
		   
	    }
	    function gundongtiao(){
	    	$("#whichscro").val($.trim($(this).parent().attr("id")))
	        if ((navigator.userAgent.match(/(iPhone|Android|iPad)/i))){
	            var scrollfathter1=document.getElementById($.trim($(this).parent().attr("id")));
	            scrollfathter1.addEventListener("touchstart", touchStart, false);
	            scrollfathter1.addEventListener("touchmove", touchMove, false);
	            scrollfathter1.addEventListener("touchend", touchEnd, false);
	        }		
	    }
	    $(".left_hidden").mouseover(function(){
	        $("#whichscro").val($.trim($(this).parent().attr("id")))
	        if ((navigator.userAgent.match(/(iPhone|Android|iPad)/i))){
	            var scrollfathter1=document.getElementById($.trim($(this).parent().attr("id")));
	            scrollfathter1.addEventListener("touchstart", touchStart, false);
	            scrollfathter1.addEventListener("touchmove", touchMove, false);
	            scrollfathter1.addEventListener("touchend", touchEnd, false);
	        }
	    });
	    scroll_y("left_xiangqing","left_hidden","scroll_y","scroll_ymove","scroll_x","scroll_xmove","","wheely","");	
	    
</script>
</html>