<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>A股操盘须知 - 配股宝</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/agu.css?v=${v}" />
</head>

<body>

<!--顶部 -->
<%@ include file="../common/personheader.jsp"%>
<div class="content">
	<div class="nav">
		<ul>			
			<li><a href="${ctx}/help?status=5">关于我们</a></li>
			<li id="nowLi"><a href="javascript:void(0)" id="nowA">A股操盘须知</a><img src="${ctx }/static/images/agu/sanjiao.png" /></li>
			<!-- <li><a href="#">港股操盘须知</a></li> -->
			<li><a href="${ctx}/help?status=3">交易软件下载</a></li>
			<li><a href="${ctx}/help?status=4">常见问答</a></li>
		</ul>
	</div>	
	<div class="content1">
		<h3>什么是A股操盘</h3>
		<p>A股操盘是一种创新的资金放大工具，通过少量的自有资金当本金，向平台借入本金一定倍数的资金。从而让投资者更好的抓住盈利机会，更高效的利用自有资金，进而实现更快捷、更丰富的投资收益。</p>
		<p>举个例子：假如您有1万本金，到配股宝放大至5万，共6万资金供您操盘，放大后一个涨停板就可以赚50%，收益全归您，配股宝只收取超低利息和管理费。</p>
	</div>
	<div class="content1">
		<h3>操盘规则</h3>
		<p>一，操盘方案：操盘方案在交易日的14点45分之前生成可以当日交易，之后生成只能是次日交易，当日无法登陆且不会扣除当日的相关费用。</p>
		<p>二，交易账户：在工作时间内提交的操盘申请，通常会在30分钟内开通股票交易账户，其他时间下个交易日09:15前开通。交易账户开户成功后我们将短信通知您。您也可以登录配股宝 → 我的账户 → A股操盘 → 对应操盘方案 → 操盘详情页，查询到账号和密码。</p>
		<p>三，持仓限制：股票交易使用T+1的规则，当日购买的股票只能在次日卖出。可以购买沪深A股及创业板，不同的借款金额，不同的仓位限制，盈利100%归您所有。为了保证您的权益，部分限制股无法购买。</p>
		<p>四，风控警戒线：当账户净资产小于补仓线，系统会限制客户买入；当客户的账户净资产触及到平仓线，系统会进行股票平仓。（当日购买的股票或者持仓股票跌停可能无法平仓，只能次日平仓）。</p>
		<p>五，操盘天数：当您的方案到期，系统会对您的方案自动延期，最长延期时间可以在配股宝的操盘详细页里查询。方案还未到期就终止操盘，已缴纳的利息不退，管理费从终止操盘后的次日不再扣除。</p>
		<p>六，追加保证金：当您的净资产触及到补仓线或者平仓线，需要通过配股宝追加保证金到交易账户，追加的金额最低1000元。</p>
	</div>
	<div class="content1">
		<h3>收费介绍</h3>
		<p>一，账户管理费：即交易账户的使用费，会在国家规定的股票交易日每天从平台余额自动扣除固定的费用。如果您在配股宝的平台余额不够扣除管理费，那么配股宝会按照A股操盘用户协议对交易账户强制平仓并终止方案。</p>
		<p>二，操盘总利息：利息就是指公司配给客户的那部分资金所要收取的利息费用。利息会在方案生成的时候按照客户选择的天数一次性扣除，而当客户方案在自动延期后，利息的收取规则是按天（自然日）扣除，具体数额 =总利息/天数。</p>
		<p>三，交易手续费：手续费包含印花税、过户费和交易佣金，印花税和过户费按财政部和交易所规定收取，交易佣金0.08%由券商收取；交易手续费直接由交易系统扣取，配股宝平台不会再收取这部分费用。</p>
	</div>
	<div class="content1">
		<h3>停牌股处理</h3>
		<p>若客户所购股票因包括但不限于异常波动、上市公司涉嫌违规需要进行调查以及突发性事件或不可抗力等原因导致该股票停牌的，客户必须继续持有该股票，操盘期限自动延期，客户应继续按协议约定支付应付的通道使用费和账户管理费等相应款项，否则视为客户已自欠付款项之日起放弃方案中股票的持有权利。</p>
		<p>同时因违反欠费约定，客户应及时将账户内其他股票平仓，否则配股宝有权依据协议约定将交易账户的全部股票变现为货币资金并办理客户操盘账户内资产的清算手续。配股宝亦有权按照协议约定顺序清偿相关债务，直至客户所欠的债务全部清偿完毕，并将剩余部分返还至客户指定账户内。</p>
		<p>客户也可选择全额购回该停牌股票，购回次日起无需支付通道使用费和账户管理费。股票复牌之日起五个交易日内客户需卖出该股票并通知配股宝进行清算，按复牌实际交易日以原方案扣除账户管理费后的剩余部分返还至客户指定账户内。</p>
	</div>
	<div class="content1">
		<h3>穿仓处理</h3>
		<p>一，客户持有停牌股欲转让给配股宝，需同时满足以下两个条件，可以联系配股宝客服：</p>
		<p style="text-indent: 4em;">A  停牌4个交易日及以上；</p>
		<p style="text-indent: 4em;">B  持有停牌股的方案已到期。</p>
		<p>二，系统发送短信给客户确认转让细节。</p>
		<p>三，配股宝立即冻结相应股票，并于次日开盘前完成证券及资金划转。</p>
	</div>	
	<div class="content1" id="lastContent">
		<h3>操盘限制</h3>
		<p>一，不得购买或持有S、ST、*ST、S*ST、SST以及被交易所特别处理的股票；</p>
		<p>二，不得购买或持有权证类可以T+0交易的证券；</p>
		<p>三，不得购买或持有首日上市新股（或复牌首日股票）等当日不设涨跌停板限制的股票；</p>
		<p>四，融资金额10万以上至50万，单只股票不得超过账户总资产的70%（10万或以下不受限制）；</p>
		<p>五，融资金额50万以上单只股票不得超过账户总资产的50%（50万或以下不受此限制）；</p>
		<p>六，融资金额50万以上创业板股票不得超过账户总资产的33%（50万或以下不受限制）；</p>
		<p>七，单只股票持仓总市值不得超过该股前5个交易日日均成交额的30%；</p>
		<p>八，不得进行坐庄、对敲、接盘、大宗交易、内幕信息等违反股票交易法律法规及证券公司规定的交易。</p>
		<a name="list"></a>
		<p>九，高风险股票已经加入到操盘软件黑名单，不能下单。高风险股票每天更新一次，名单如下：</p>
		<table id="listTa" border="0" cellspacing="0" cellpadding="0">
		
		</table>
	</div>
	
</div>

<%-- ${stocks[0].name } --%>

<!--底部 -->
<%@ include file="../common/personfooter.jsp"%>

<script type="text/javascript">
$(document).ready(function() {
	/**
	*限制股表格
	**/
	$.ajax({
			url:basepath+"/helpPage/getData",
			data:{status : 1},
			success:function(data) {
		 		var res = data.data.st;
		 		var len = data.data.n;
		 		 for(var i = 1;i<=len;i++){
		 			if(4*i-1<res.length){
		 				$("#listTa").append("<tr>"+		 					
			 					"<td>"+res[4*i-4].name+"</td><td>"+res[4*i-4].code+"</td>"+
			 					"<td>"+res[4*i-3].name+"</td><td>"+res[4*i-3].code+"</td>"+
			 					"<td>"+res[4*i-2].name+"</td><td>"+res[4*i-2].code+"</td>"+
			 					"<td>"+res[4*i-1].name+"</td><td>"+res[4*i-1].code+"</td>"+
			 					"</tr>");
		 			}else if(4*i-2<res.length){
		 				$("#listTa").append("<tr>"+		 					
		 						"<td>"+res[4*i-4].name+"</td><td>"+res[4*i-4].code+"</td>"+
			 					"<td>"+res[4*i-3].name+"</td><td>"+res[4*i-3].code+"</td>"+
			 					"<td>"+res[4*i-2].name+"</td><td>"+res[4*i-2].code+"</td>"+
			 					"<td></td><td></td>"+
			 					"</tr>");
		 			}else if(4*i-3<res.length){
		 				$("#listTa").append("<tr>"+		 					
		 						"<td>"+res[4*i-4].name+"</td><td>"+res[4*i-4].code+"</td>"+
			 					"<td>"+res[4*i-3].name+"</td><td>"+res[4*i-3].code+"</td>"+
			 					"<td></td><td></td>"+
			 					"<td></td><td></td>"+
			 					"</tr>");
		 			}else if(4*i-4<res.length){
		 				$("#listTa").append("<tr>"+		 					
		 						"<td>"+res[4*i-4].name+"</td><td>"+res[4*i-4].code+"</td>"+
		 						"<td></td><td></td>"+
		 						"<td></td><td></td>"+
		 						"<td></td><td></td>"+
			 					"</tr>");
		 			}	
		 		}	
			}
	}); 
});
</script>
</body>
</html>
