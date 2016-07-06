<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-fileupload-js.jspf"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"  />
<title>新华油 - 维胜</title>
<meta content="新华油,黑色金子,低门槛,高回报" name="description">
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/index.css">
<style type="text/css">
/* CSS Document */
* { margin: 0; padding: 0;}
body { font-size: 100%; background: #fff;}
ul,li,ol,em,i { list-style: none; font-style: normal;}
.sp_banner { width: 100%; height: 400px; margin: 0 auto; position: relative; overflow: hidden;}
.sp_banner img { width: 1920px; height: 400px; position: absolute; left: 50%; margin-left: -960px;} 
.sp_login { height: 300px; background: url(${ctx }/static/images/spot/lg_bg.png) repeat; position: absolute; left: 50%; top: 50px; margin-left:150px; width: 272px; padding: 0 15px; border-radius: 3px;   }
.sp_login h3 {height: 51px; background: url(${ctx }/static/images/spot/lg_title.png) no-repeat; left: -20px; position: relative; top: 15px; width: 240px; padding:5px 35px; line-height: 24px; font-size: 18px; color: #fff; text-align: center; font-weight: normal; font-family: "微软雅黑"; margin-bottom: 30px; z-index: 5; }
.sp_lg_sign { width: 100%; height: 44px; font-size: 0; font-family: "微软雅黑"; margin-top: 25px; }
.sp_lg_sign label { display: inline-block; width: 85px; height: 44px; line-height: 44px; text-align: right; font-size: 16px; color: #575757; vertical-align: middle;  }
.sp_lg_ip { width: 160px; height: 42px; padding: 0 10px; font-size: 16px; color:#575757; outline: none; vertical-align: middle; border:none; font-family: "微软雅黑"; background: #fff; border:1px solid  #dcdcdc; border-radius: 3px;	 }
.sp_lg_btn { width: 270px; height: 46px; margin: 25px auto 0; }
.sp_lg_btn a { display: block; width: 270px; height: 46px; background: #ff872f; border-radius: 3px; font-family: "微软雅黑"; color:#fff; font-size:22px; text-align: center; line-height: 46px; text-decoration: none;  }
.sp_lg_btn a:hover { background: #f36700;}
.sp_lg_link { width: 100%; height: 32px; line-height: 32px; font-size: 18px; color: #5a2e0e; text-align: center; font-family: "微软雅黑";  }
.sp_lg_link a { color: #f00; text-decoration: none;}
.sp_lg_space { margin-top: 30px;}
.sp_login h3 em { display: block; width: 100%; font-size: 23px; font-style: normal; height: 30px;line-height: 30px; }
.sp_login h3 span { display: block; width: 100%; font-size: 14px; }
.sp_lg_promt { width: 100%; height: 48px; line-height: 24px; font-size: 16px; color: #575757; font-family: "微软雅黑"; text-align: left; padding-left: 10px;} 
.sp_login h4 { width: 100%; height: 40px; line-height: 40px; text-align: center; font-family: "微软雅黑"; color: #ff872f; font-size: 28px; font-weight: normal; padding: 10px 0 5px;}
.sp_login h5 { width: 100%; height: 45px; line-height: 45px; text-align: left; font-size: 16px; font-family: "微软雅黑"; color: #575757; font-weight: normal; }
.sp_lg_mask { width: 100%; height: 300px;filter:progid:DXImageTransform.Microsoft.gradient(enabled='true',startColorstr='#99000000', endColorstr='#99000000');background:rgba(0,0,0,0.6); position: absolute; top: 0; left: 0; z-index: 5;}
.sp_lg_mask span { display: block; width: 100%; height: 72px; line-height: 36px; font-size: 20px; color: #fffefe; text-align: center; font-family: "微软雅黑"; padding-top: 130px;   }
.sp_lg_mask span a { color: #ff872f; text-decoration: none; padding: 0 5px; }
.sp_lg_btn2 { margin-top: 0;}
.sp_main { width: 1000px; height: auto; margin: 0 auto; border-top: 1px solid #ccc; padding-bottom: 30px; overflow: hidden;}
.sp_main h2 { width: 100%; height: 90px; line-height: 90px; text-align: left; font-size: 28px; color: #444; font-family: "微软雅黑"; font-weight: normal; }
.sp_mainb { border-top: none;}
.sp_step { width: 1000%; height: 160px; overflow: hidden; padding-top: 15px; }
.sp_step li { display: block; width: 235px; height: 160px; float: left; position: relative; margin: 0 15px 0; }
.sp_step li p { width: 231px; height: 76px; border:2px solid #ff872f; font-size: 24px; font-family: "微软雅黑"; line-height: 34px; border-radius: 3px; color: #ff872f; text-align: center; padding: 5px 0;  }
.sp_step li span { display: block; width: 100%; height:55px; line-height: 55px; text-align: center; font-size: 18px; color: #444; font-family: "微软雅黑"; }
.sp_step li em { display: block; width: 30px; height: 30px; text-align: center; line-height: 30px; font-size: 20px; font-family: "微软雅黑";  color: #fff; background: url(../images/spot/icon_01.gif) no-repeat; position: absolute; left: -15px; top: -15px; }
.sp_step li.next  { width: 41px; height: 32px; margin: 26px 20px 0;   }
.sp_adv { width: 100%; height: auto; overflow: hidden;  font-family: "微软雅黑";  padding-top: 20px;}
.sp_adv li { display: block; width: 165px; height: auto; float: left; margin: 0 40px 40px;  }
.sp_adv li img { display: block; width: 100px; height: 100px; margin: 0 auto 18px;}
.sp_adv li h3 { width: 100%; height: 32px; line-height: 32px; text-align: center; font-size: 18px; color: #444; font-weight: normal;  }
.sp_adv li p { width: 100%; height: 32px; line-height: 32px; font-size: 14px; color: #444; text-align: center;  }
.sp_safetitle { width: 100%; height: 70px; line-height: 70px; text-align: center; font-size: 24px; color: #444; font-family: "微软雅黑"; font-weight: normal;  }
.sp_safefont { width: 935px; height: 60px; border:1px dashed #e83c3c; background: #fff9ee; font-size:16px; color: #555; font-family: "微软雅黑"; line-height: 30px; text-align: center; border-radius: 4px; padding: 9px 0; margin: 0 auto;   }
.sp_safepromt { width: 100%;  height: 85px; line-height: 85px; text-align: center; font-size: 24px; color: #ff2323; font-family: "微软雅黑"; }
.sp_safe { width: 100%; height: auto; overflow: hidden;}
.sp_safe li { display: block; width: 450px; height: 100px; float: left; margin: 0 0 40px 45px;  }
.sp_safe li img { display: block; float: left; width: 100px; height: 100px;}
.sp_safe li p { width: 260px; height: 100px; float: left; margin-left: 20px; font-family: "微软雅黑"; }
.sp_safe li p em  {display: block; width: 100%; height: 40px; line-height: 40px; font-size: 18px; color: #444; text-align: left; font-weight: bold;  }
.sp_safe li p span { display: block; width: 100%; height: 60px; line-height: 30px; font-size: 14px; color: #444;  }
.sp_rule { width: 890px; height: auto; border:1px solid #e1e1e1; border-bottom: none; border-right: none; margin: 0 auto;}
.sp_rule tr td { border-right: 1px solid #e1e1e1;  border-bottom: 1px solid #e1e1e1; height: 40px; line-height: 40px; padding-left: 25px; text-align: left; font-size: 14px; color: #555; font-family: "微软雅黑";  }
.sp_rule tr:nth-child(2n+1) { background: #fffcf6;} 
</style>
</head>
<body>
<%@ include file="../common/personheader.jsp"%>
<div class="sp_banner">
	<img src="${ctx }/static/images/spot/banner.jpg" alt="低门槛,高回报,原油,黑色金子">
	<c:choose>
		<c:when test="${isLogin }">
			<c:choose>
				<c:when test="${isBooking }">
					<!-- 预约成功 -->
					<div class="sp_login" style="display:block;">
						<h4>现货开户预约成功！</h4>
						<p class="sp_lg_promt">我们的投资顾问会第一时间联系您<br>投资顾问${salesmanName }：${salesmanMobile }</p>
						<h5>现在您可以</h5>
						<div class="sp_lg_btn sp_lg_btn2"><a target="_blank" href="http://user.hbot.cn/moni/index.jsp?memNo=125">先申请模拟账号</a></div>
						<h5>模拟操盘20次之后</h5>
						<div class="sp_lg_btn sp_lg_btn2"><a target="_blank" href="http://user.hbot.cn/real/index.jsp?memNo=125">再申请实盘账号</a></div>
					</div>
				</c:when>
				<c:otherwise>
					<!-- 开户登录 -->
					<div class="sp_login" style="display:block;">
						<h3>让我们全程协助您现货开户<br>实现财富增值</h3>
						<form action="${ctx }/spot/booking" method="post" id="booking-frm">
							<div class="sp_lg_sign sp_lg_space">
								<label>真实姓名：</label>
								<input type="text" name="autonym" id="autonym" value="${autonym }" class="sp_lg_ip"/>
							</div>
							<div class="sp_lg_sign">
								<label>手机号码：</label>
								<input type="text" name="mobile" id="mobile" value="${mobile }" class="sp_lg_ip"/>
							</div>
							<div class="sp_lg_btn"><a href="javascript:;" id="booking-sbm">立即预约开户</a></div>
						</form>
					</div>
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			<!-- 开户未登录 -->
			<div class="sp_login" style="display:block;">			
				<div class="sp_lg_mask"><span>请先<a href="${ctx }/spot/booking">登录</a>后预约开户<br>没有账号？那先<a href="${ctx}/signin" target="_blank">注册</a>吧！</span></div>
				<h3>让我们全程协助您现货开户<br>实现财富增值</h3>
				<div class="sp_lg_sign">
					<label>真实姓名：</label>
					<input type="text" disabled="disabled" class="sp_lg_ip"/>
				</div>
				<div class="sp_lg_sign">
					<label>手机号码：</label>
					<input type="text" disabled="disabled" class="sp_lg_ip"/>
				</div>
				<div class="sp_lg_btn sp_nolg_btn"><a href="javascript:;">立即预约开户</a></div>				
			</div>
		</c:otherwise>
	</c:choose>
</div>

<div class="sp_main sp_mainb">
	<h2>现货操盘流程：</h2>
	<ul class="sp_step">
		<li>
			<em>1</em>
			<p>先预约开户，获得<br>模拟盘和实盘账号</p>
			<span>我们全程协助您开户</span>
		</li>
		<li class="next"><img src="${ctx }/static/images/spot/icon_02.gif"></li>		
		<li>
			<em>2</em>
			<p>下载新华大宗官方<br>的行情和交易软件</p>
			<span>交易所软件公开公正透明</span>
		</li>
		<li class="next"><img src="${ctx }/static/images/spot/icon_02.gif"></li>
		<li>
			<em>3</em>
			<p>关联银行卡号、入<br>金、开始操盘赚钱</p>
			<span>投资顾问专项操盘指导</span>
		</li>
	</ul>
</div>
<div class="sp_main">	
	<h2>现货交易细则：</h2>
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="sp_rule">
	  	<tr>
		    <td><b>交易品种</b></td>
		    <td>新华油100桶</td>
		    <td>新华油500桶</td>
		    <td>新华油1000桶</td>
	    </tr>
	  	<tr>
		    <td><b>交易代码</b></td>
		    <td>CL100</td>
		    <td>CL500</td>
		    <td>CL1000</td>
	    </tr>
		<tr>
			<td><b>报价单位</b></td>
			<td colspan="3">人民币(元/桶)</td>
		</tr>
	  	<tr>
		    <td><b>交易单位</b></td>
		    <td>100桶/手</td>
		    <td>500桶/手</td>
		    <td>1000桶/手</td>
	    </tr>
		<tr>
			<td><b>最小变动单位</b></td>
			<td colspan="3">0.01元/桶</td>
		</tr>
	  	<tr>
		    <td><b>单笔最大下单量</b></td>
		    <td>100手</td>
		    <td>40手</td>
		    <td>20手</td>
	    </tr>
	  	<tr>
		    <td><b>最大持仓量</b></td>
		    <td>1000手</td>
		    <td>400手</td>
		    <td>200手</td>
	    </tr>
	  	<tr>
		    <td><b>保证金比例</b></td>
		    <td>不低于合约价值的5%</td>
		    <td></td>
		    <td>不低于合约价值的3%</td>
	    </tr>
	  	<tr>
		    <td><b>手续费</b></td>
		    <td colspan="3">成交金额的0.08%(开仓、平仓双向收取)</td>
	    </tr>
	  	<tr>
		    <td><b>差点</b></td>
		    <td colspan="3">0.6元/桶</td>
	    </tr>
	  	<tr>
		    <td><b>延期费</b></td>
		    <td colspan="3">成交额的0.01%(每天收取)</td>
	    </tr>
	  	<tr>
		    <td><b>强制平仓比例</b></td>
		    <td colspan="3">风险率小于或等于50%</td>
	    </tr>
	  	<tr>
		    <td><b>交易时间</b></td>
		    <td colspan="3">北京时间周一08:00至周六04:00连续交易（夏令时）（每天结算休市时间04:00-06:00）</td>
	    </tr>
	</table>
</div>
<div class="sp_main">
	<h2>新华现货8大优势：</h2>
	<ul class="sp_adv">
		<li>
			<img src="${ctx }/static/images/spot/adv_01.gif">
			<h3>低门槛</h3>
			<p>小金额即可交易一手</p>
		</li>
		<li>
			<img src="${ctx }/static/images/spot/adv_02.gif">
			<h3>全天交易</h3>
			<p>5X22小时交易服务</p>
		</li>
		<li>
			<img src="${ctx }/static/images/spot/adv_03.gif">
			<h3>5%低保证金</h3>
			<p>资金利用率高</p>
		</li>
		<li>
			<img src="${ctx }/static/images/spot/adv_04.gif">
			<h3>20倍杠杆</h3>
			<p>低投入高回报</p>
		</li>
		<li>
			<img src="${ctx }/static/images/spot/adv_05.gif">
			<h3>灵活交易</h3>
			<p>T+0交易，自由灵活</p>
		</li>
		<li>
			<img src="${ctx }/static/images/spot/adv_06.gif">
			<h3>双向交易</h3>
			<p>涨跌皆赚，双向盈利</p>
		</li>
		<li>
			<img src="${ctx }/static/images/spot/adv_07.gif">
			<h3>交易透明</h3>
			<p>无庄家操控，国际价格</p>
		</li>
		<li>
			<img src="${ctx }/static/images/spot/adv_08.gif">
			<h3>第三方资管</h3>
			<p>资金安全，存取方便</p>
		</li>
	</ul>
</div>
<div class="sp_main">
	<h2>投资权益4大保障：</h2>
	<h3 class="sp_safetitle">国有交易平台：新华浙江大宗商品交易中心</h3>
	<p class="sp_safefont">新华浙江大宗商品交易中心由新华社直属企业——中经社控股有限公司全资子公司新华社金融信息交易所与杭州兴利投<br>
资有限公司共同组建。作为新华社隆重推出的大宗商品交易机构，新华大宗商品交易中心是新华社商品定价权战略布局重要的一环。</p>
	<p class="sp_safepromt">投资达人是新华大宗交易所会员单位下的顶级代理，为现货用户提供全程指导服务！</p>
	<ul class="sp_safe">
		<li>
			<img src="${ctx }/static/images/spot/safe_01.gif">
			<p><em>监管严谨</em><span>交易中心是新华社旗下合法现货交易服务供应商，国有单位</span></p>
		</li>
		<li>
			<img src="${ctx }/static/images/spot/safe_02.gif">
			<p><em>操作规范</em><span>服务标准化、流程化、从业人员六大禁令保证客户 利益</span></p>
		</li>
		<li>
			<img src="${ctx }/static/images/spot/safe_03.gif">
			<p><em>资金安全</em><span>资金由银行存管，更加安全，出入金更便捷</span></p>
		</li>
		<li>
			<img src="${ctx }/static/images/spot/safe_04.gif">
			<p><em>实力雄厚</em><span>注册资本2000万元，专业从事大宗商品及金融衍生品现货交易与咨询服务</span></p>
		</li>
	</ul>
</div>
<%@ include file="../common/personfooter.jsp"%>
<span style="display:none"><script src='http://w.cnzz.com/q_stat.php?id=1256807294&l=3' language='JavaScript'></script></span>
<script type="text/javascript" src="${ctx }/static/script/spot/spot-booking.js"></script>
<script type="text/javascript">
	$(function() {
		$('.navlist li a').removeClass('on');
		$('#xin_hua_you').addClass('on');
	})
</script>
</body> 
</html>