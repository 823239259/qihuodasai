<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-fileupload-js.jspf"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../common/commonkeyword.jsp"%>
<link rel="stylesheet" href="${ctx}/static/activies/css/sp.css">
<c:forEach items="${stocklist}" var="stock" varStatus="status">
	<script type='text/javascript' src="http://hq.sinajs.cn/list=sh${stock.stock_code}"></script>
	<script type='text/javascript' src="http://hq.sinajs.cn/list=sz${stock.stock_code}"></script>
</c:forEach>
<script type="text/javascript">
function showDetail(){
 showPageDialog("${ctx}/activitiesDetail","活动细则","1000px","700px");
}

</script>
<script type="text/javascript">


$(document).ready(function() {
	<c:forEach items="${stocklist}" var="stock" varStatus="status">
		var code='${stock.stock_code}';
		var hq_str${stock.stock_code}=eval("hq_str_sh"+code); 
		if(hq_str${stock.stock_code}==""){
			hq_str${stock.stock_code}=eval("hq_str_sz"+code);
		}
		var elements${stock.stock_code}=hq_str${stock.stock_code}.split(",");
		$("#"+code).html(elements${stock.stock_code}[0]);
	</c:forEach>
});

function toQuestion(){
	var isshow='${isshow}';
	 if(isshow=="show"){
		 window.location.href="${ctx}/toQuestionnaire";
	 }else{
		 //showMsgDialog("提示","3月16日09:00准时开抢，敬请期待！"); 
		 clickCloseRefresh("提示","3月16日09:00准时开抢，敬请期待！");
	 }
	 
		
}



</script>
</head>
<body>
 
<div class="main">
    <div class="banner1"></div>
    <div class="banner2"></div>
    <div class="banner3">
	    
	        <div class="ctn">
	            <a href="javascript:void(0)" onclick="javascript:toQuestion();" title="立即抢VIP账号" class="join_btn"></a>
	        </div>
	  
    </div>
    <div class="gift">
        <div class="ctn">
             <p>收益排名公布：4月17日<br>收益排名发奖：4月18日—4月26日<br>活动结束后，我们将对所有VIP账号收益情况进行排名，并送出丰厚大奖。</p>

        </div>
    </div>
    <div class="banner4"></div>
    <div class="banner5">
        <div class="ctn">
            <div class="step">
                 <p>活动期间，登陆维胜网(www.mzkqh.com)，进入活动页面点击“立即抢”，填写简单的调查问卷，即可参与“达人红包8800”活动（限1000个名额，先到先得，送完即止）。按照网站指引，全免费体验维胜“实盘申请”股票操盘产品，领取8800元实盘资金供您操盘。活动结束后平仓结算，收益全归您，亏损我们承担。</p>
                <p>*活动期间您可以体验维胜“实盘申请”独有的“提取收益”功能，将当天盈利提现。如果您不提现，则可将当天的盈利作为投资本金，继续进行股票投资，放大收益，并且在活动结束时有机会通过收益排名获得丰厚大奖<em>（中途提取的利润不计入收益排名）</em>。</p>

            </div>
            <div class="step_btn">
            		<a target="_blank" href="http://shang.qq.com/wpa/qunwpa?idkey=bc5462cf61042208f091d0b347e85ce61ba6c31f86e4c5587be6101c0d9e1e81"><i>快速加入</i></a>
            </div>
        </div>
    </div>
    <div class="banner6">

        <div class="ctn">
            <a href="javascript:showDetail();" class="btn_active"><i>活动详细</i></a>

        </div>

    </div>
    <div class="banner7">
        <div class="rank">            
            <div class="rank_listbox fl">
                <ul class="rank_title">
                    <li class="rank_l_rank">排名</li>
                    <li class="rank_l_user">用户</li>
                    <li class="rank_l_income">收益</li>
                    <li class="rank_l_rate">收益率</li>
                </ul>
                 <ul class="rank_list">
	                   	  	<ol class="rank_top">
		                        <li class="rank_l_rank">
		                        <img src="${ctx}/static/images/rank_01.gif">
		                        </li>
		                        <li class="rank_l_user">130****8178</li>
		                        <li class="rank_l_income">3099.38</li>
		                        <li class="rank_l_rate">35.2%</li>
	                        </ol>
	                   	 
	                   	  	<ol class="rank_top">
		                        <li class="rank_l_rank">
		                        <img src="${ctx}/static/images/rank_02.gif">
		                        </li>
		                        <li class="rank_l_user">136****1063</li>
		                        <li class="rank_l_income">2962.45</li>
		                        <li class="rank_l_rate">33.7%</li>
	                        </ol>
	                   	 
	                   	  	<ol class="rank_top">
		                        <li class="rank_l_rank">
		                        <img src="${ctx}/static/images/rank_03.gif">
		                        </li>
		                        <li class="rank_l_user">139****8918</li>
		                        <li class="rank_l_income">2610.27</li>
		                        <li class="rank_l_rate">29.7%</li>
	                        </ol>
	                   	 
		                    <ol>
		                        <li class="rank_l_rank">4</li>
		                        <li class="rank_l_user">136****3662</li>
		                        <li class="rank_l_income">2437.14</li>
		                        <li class="rank_l_rate">27.7%</li>
		                    </ol>
	                  		   <ol>
		                        <li class="rank_l_rank">5</li>
		                        <li class="rank_l_user">137****9584</li>
		                        <li class="rank_l_income">2279.38</li>
		                        <li class="rank_l_rate">25.9%</li>
		                    </ol> 
		                      <ol>
		                        <li class="rank_l_rank">6</li>
		                        <li class="rank_l_user">135****8747</li>
		                        <li class="rank_l_income">2254</li>
		                        <li class="rank_l_rate">25.6%</li>
		                    </ol>
		                       <ol>
		                        <li class="rank_l_rank">7</li>
		                        <li class="rank_l_user">136****6555</li>
		                        <li class="rank_l_income">2188.23</li>
		                        <li class="rank_l_rate">24.9%</li>
		                    </ol> 
		                  
		                    <ol>
		                        <li class="rank_l_rank">8</li>
		                        <li class="rank_l_user">134****7737</li>
		                        <li class="rank_l_income">2002.26</li>
		                        <li class="rank_l_rate">22.8%</li>
		                    </ol>  
		                    <ol>
		                        <li class="rank_l_rank">9</li>
		                        <li class="rank_l_user">134****8435</li>
		                        <li class="rank_l_income">1832.6</li>
		                        <li class="rank_l_rate">20.8%</li>
		                    </ol> 
		                     <ol>
		                        <li class="rank_l_rank">10</li>
		                        <li class="rank_l_user">139****1115</li>
		                        <li class="rank_l_income">1802.25</li>
		                        <li class="rank_l_rate">20.5%</li>
		                    </ol> 
                </ul>
				<p class="rank_l_notic">注：榜单数据更新时间为每日16:00</p>
            </div>         
            <div class="rank_listbox fr">
                <ul class="rank_title">
                    <li class="rank_l_rank">排名</li>
                    <li class="rank_l_user">用户</li>
                    <li class="rank_l_income">收益率</li>
                    <li class="rank_l_rate">排名增幅</li>
                </ul>
                <ul class="rank_list">
                <ol class="rank_top">
                      <li class="rank_l_rank"><img src="${ctx }/static/images/rank_01.gif"></li>
                      <li class="rank_l_user">153****9244</li>
                      <li class="rank_l_income">5.9%</li>
                      <li class="rank_l_rate">698</li>
                  </ol>
                	  
                	  	 <ol class="rank_top">
                      <li class="rank_l_rank"><img src="${ctx }/static/images/rank_02.gif"></li>
                      <li class="rank_l_user">138****0104</li>
                      <li class="rank_l_income">8.5%</li>
                      <li class="rank_l_rate">684</li>
                  </ol>
                	   
                 <ol class="rank_top">
                      <li class="rank_l_rank"><img src="${ctx }/static/images/rank_03.gif"></li>
                      <li class="rank_l_user">184****2042</li>
                      <li class="rank_l_income">9.3%</li>
                      <li class="rank_l_rate">651</li>
                  </ol>
                	  
                   <ol>
                       <li class="rank_l_rank">4</li>
                       <li class="rank_l_user">139****2876</li>
                       <li class="rank_l_income">5.2%</li>
                       <li class="rank_l_rate">609</li>
                   </ol>
                  
                   <ol>
                       <li class="rank_l_rank">5</li>
                       <li class="rank_l_user">185****7434</li>
                       <li class="rank_l_income">2.9%</li>
                       <li class="rank_l_rate">577</li>
                   </ol>
                  
                   <ol>
                       <li class="rank_l_rank">6</li>
                       <li class="rank_l_user">135****6953</li>
                       <li class="rank_l_income">7.8%</li>
                       <li class="rank_l_rate">515</li>
                   </ol>
                  
                	    
                   <ol>
                       <li class="rank_l_rank">7</li>
                       <li class="rank_l_user">189****7510</li>
                       <li class="rank_l_income">6.6%</li>
                       <li class="rank_l_rate">514</li>
                   </ol>
                  
                   <ol>
                       <li class="rank_l_rank">8</li>
                       <li class="rank_l_user">138****6618</li>
                       <li class="rank_l_income">1.8%</li>
                       <li class="rank_l_rate">508</li>
                   </ol>
                	    
                   <ol>
                       <li class="rank_l_rank">9</li>
                       <li class="rank_l_user">150****4867</li>
                       <li class="rank_l_income">5.2%</li>
                       <li class="rank_l_rate">500</li>
                   </ol>
                  
                   <ol>
                       <li class="rank_l_rank">10</li>
                       <li class="rank_l_user">156****9492</li>
                       <li class="rank_l_income">4.5%</li>
                       <li class="rank_l_rate">472</li>
                   </ol>
                </ul>


            </div>

        </div>

    </div>
    <div class="banner8">
        <div class="share">
            <ul class="sharelist">
                 <c:forEach items="${stocklist}" var="stock" varStatus="status">
		            <li>
		                <h4>买入人数 ${stock.countnum }</h4>
		                <p ><span id="${stock.stock_code }"></span><br>${stock.stock_code }</p>
		            </li>
            </c:forEach>
            </ul>
            <div class="sharebtn">
                <a target="_blank" href="http://shang.qq.com/wpa/qunwpa?idkey=09395da7e2240d9931bab7840e0d6b87aa7681b68feaf77c88ee564564c402d1"><i>快速加入</i></a>
            </div>

        </div>
    </div>
    <div class="sharebtn"></div>
    <div class="detabox">
        <div class="deta">
            <ul class="detalist">
                           <ol class="fl">
                    <li>
                        <h4>1.     登陆VIP账号</h4>
                        <p class="date_p1">使用由维胜提供的VIP账号登陆，方可参与活动。未获得VIP账户的用户，点击活动页面“立即抢VIP账号”，可参与VIP账号申领，数量有限，先到先得，送完即止。</p>
                    </li>
                    <li>
                        <h4>2.  申请操盘</h4>
                        <p class="date_p2">登陆VIP账号，即可在个人账号余额中查看800元达人红包。选择“我要操盘”，按照网站指引进行融资操作，申请8800元实盘资金，并无需支付任何管理费用，全免费体验。</p>
                    </li>
                    <li>
                        <h4>3.  操作股票</h4>
                        <p class="">申请成功后，将获得交易账户的账号密码，请按照提示下载交易软件，进入软件平台进行自由股票操作。</p>
                    </li>
                </ol>
                <ol class="fr">
                    <li>
                        <h4>4.  收益结算</h4>
                        <p class="date_p1">4月16日所有活动方案将会平仓结算，建议您于当天11:30之前平仓，未在11:30之前自行终止的活动方案，则维胜有权在当天收市前帮助活动用户平仓、终止方案，<i>具体收益排名的核算以终止方案时的收益为准。</i></p>
                    </li>
                    <li>
                        <h4>5.  分享有奖</h4>
                        <p class="date_p2">4月16日活动结束后，您可以在个人中心查看您的收益排行情况，并可以通过分享按钮将您的成绩分享到朋友圈。将您分享的截图发送至维胜官方微信，更有惊喜礼品等您领取。</p>
                    </li>
                    <li>
                        <h4>6.  赢取大奖</h4>
                        <p class="date_p3">4月17日公布收益排名，收益排名以“达人红包”指定的免费体验股票操盘产品平仓结算后的收益排名为准，数据来自交易软件平台。我们会在7个工作日内联系获奖者，确认信息发放奖品。</p>
                    </li>
                </ol>


            </ul>
            <div class="data_btn">
                <a href="javascript:showDetail();"><i>活动详细</i></a>
            </div>
            
		        <div class="joinbtn">
		            <a href="javascript:void(0)" onclick="javascript:toQuestion();" ><i>立即抢</i></a>
		        </div>
	       
        </div>
        
    </div>
</div>


<!-- 公司简介 -->
<!-- DINGBU -->
<%@ include file="../common/dsp.jsp"%>
</body>
</html>
