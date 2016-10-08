<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-fileupload-js.jspf"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="维胜，国际期货，期货，投资达人，金勺子，高盛，都城，南华期货，配资，期货配资，期货开户，外盘，富时A50，国际原油，恒指期货，期货公司，期货平台，炒期货，模拟盘，赚钱，头寸，持仓，成都盈透科技有限公司"/>
<meta name="description" content="维胜（www.vs.com）-致力于成为中国领先的国际期货及衍生品互联网交易平台，提供恒指期货、国际原油、富时A50等主流国际期货产品，开户操盘快捷方便，交易费用全网最低。"/>
<title>交易行情 - 维胜金融-中国领先的国际期货及衍生品互联网交易平台</title>
<link href="${ctx }/static/css/trade.css?v=${v}" rel="stylesheet" type="text/css" />
<link href="${ctx }/static/css/quotation.css?v=${v}" rel="stylesheet" type="text/css" />
<script type='text/javascript' src="${ctx}/static/script/qutrade/quotation.js?v=${v}"></script>
<script type='text/javascript' src="${ctx}/static/script/qutrade/quote.trade.js?v=${v}"></script>
</head>
<body>
<!-- top -->
<div class="quotation_title">
	<a href="http://www.vs.com"><img src="${ctx}/static/images/common-new/new_logo.png" title="维胜金融" alt="维胜金融"></a>
	<div class="quotation_anniu">
		<input type="text" name="quotation_account" id="quotation_account" placeholder="输入交易账号"/>
		<input type="text" name="quotation_password" id="quotation_password" placeholder="输入交易密码"/>
		<button><a href="#">登录</a></button>
		<button><a href="#">开户</a></button>
		<a href="#" class="backPassword">找回密码</a>
	</div>
</div>
<!-- center -->
<div class="quotation_center">
    <div id="left">
    	<p class="center_futures"><input type="text" name="quotation_futures" id="quotation_futures" placeholder="期货搜索"/></p>
		<div class="futuresList">
			<ul class="on">
		        <li class="futures_name">
		        	<span class="futures_mz">恒指期货</span>
		        	<span class="futures_bm">CL1611</span>
		        </li>
		        <li class="qlast" style="color: #30bf30;">-0.002</li>
		        <li class="futures_number">1213</li>
		        <li class="scal" style="color: #30bf30;">-0.01%</li>
    		</ul>
    		<ul>
		        <li class="futures_name">
		        	<span class="futures_mz">恒指期货</span>
		        	<span class="futures_bm">CL1611</span>
		        </li>
		        <li class="qlast" style="color: #ff4040;">+0.002</li>
		        <li class="futures_number">1213</li>
		        <li class="scal" style="color: #ff4040;">+0.01%</li>
    		</ul>
    		<ul>
		        <li class="futures_name">
		        	<span class="futures_mz">恒指期货</span>
		        	<span class="futures_bm">CL1611</span>
		        </li>
		        <li class="qlast" style="color: #30bf30;">-0.002</li>
		        <li class="futures_number">1213</li>
		        <li class="scal" style="color: #30bf30;">-0.01%</li>
    		</ul>
    		<ul>
		        <li class="futures_name">
		        	<span class="futures_mz">恒指期货</span>
		        	<span class="futures_bm">CL1611</span>
		        </li>
		        <li class="qlast" style="color: #30bf30;">-0.002</li>
		        <li class="futures_number">1213</li>
		        <li class="scal" style="color: #30bf30;">-0.01%</li>
    		</ul>
		</div>
    </div>
    <div id="content">
    	<div class="quotation_echarts">
    		<div id="right">
    			<p class="right_title"><p>
    			<p class="right_lis">富时A50  CL1611</p>
    			<div class="right_tab">
    				<ul>
	    				<li class="right_tab_zxj">最新价</li>
	    				<li class="right_tab_zxjq">106253.5</li>
	    				<li class="right_tab_zj">昨结</li>
	    				<li class="right_tab_zjl">106253.5</li>
    				</ul>
	    			<ul>
	    				<li class="right_tab_zxj">涨跌</li>
	    				<li class="right_tab_zxjq" style="color: #ff4040;">120</li>
	    				<li class="right_tab_zj">今开</li>
	    				<li class="right_tab_zjl">4</li>
    				</ul>
	    			<ul>
	    				<li class="right_tab_zxj">幅度</li>
	    				<li class="right_tab_zxjq"  style="color: #ff4040;">+00.8</li>
	    				<li class="right_tab_zj">最高</li>
	    				<li class="right_tab_zjl">4</li>
    				</ul>
	    			<ul>
	    				<li class="right_tab_zxj">总量</li>
	    				<li class="right_tab_zxjq">2</li>
	    				<li class="right_tab_zj">最低</li>
	    				<li class="right_tab_zjl">4</li>
    				</ul>
    			</div>
    			<div class="right_tab tab1" style="border-bottom: 5px solid #404040; border-top: 5px solid #404040;">
    				<ul>
	    				<li class="right_tab1_left">卖五</li>
	    				<li class="right_tab1_center">50,652</li>
	    				<li class="right_tab1_right">2</li>
    				</ul>
    				<ul>
	    				<li class="right_tab1_left">卖四</li>
	    				<li class="right_tab1_center">50,652</li>
	    				<li class="right_tab1_right">2</li>
    				</ul>
    				<ul>
	    				<li class="right_tab1_left">卖三</li>
	    				<li class="right_tab1_center">50,652</li>
	    				<li class="right_tab1_right">2</li>
    				</ul>
    				<ul>
	    				<li class="right_tab1_left">卖二</li>
	    				<li class="right_tab1_center">50,652</li>
	    				<li class="right_tab1_right">2</li>
    				</ul>
    				<ul>
	    				<li class="right_tab1_left">卖一</li>
	    				<li class="right_tab1_center">50,652</li>
	    				<li class="right_tab1_right">2</li>
    				</ul>
    			</div>
    			<div class="right_tab tab2">
    				<ul>
	    				<li class="right_tab1_left">买一</li>
	    				<li class="right_tab1_center">50,652</li>
	    				<li class="right_tab1_right">2</li>
    				</ul>
    				<ul>
	    				<li class="right_tab1_left">买二</li>
	    				<li class="right_tab1_center">50,652</li>
	    				<li class="right_tab1_right">2</li>
    				</ul>
    				<ul>
	    				<li class="right_tab1_left">卖三</li>
	    				<li class="right_tab1_center">50,652</li>
	    				<li class="right_tab1_right">2</li>
    				</ul>
    				<ul>
	    				<li class="right_tab1_left">买四</li>
	    				<li class="right_tab1_center">50,652</li>
	    				<li class="right_tab1_right">2</li>
    				</ul>
    				<ul>
	    				<li class="right_tab1_left">买五</li>
	    				<li class="right_tab1_center">50,652</li>
	    				<li class="right_tab1_right">2</li>
    				</ul>
    			</div>
    		</div>
    		<div id="content2">插件</div>
    	</div>
    	<div class="quotation_transaction">
    		<div class="quotation_type">
    			<div class="quotation_p" style="margin-top: 20px;">
    				<label>合约代码</label>
    				<div class="quotation_type_fr">
    					<select>
					    	<option>富士A50</option>
					    	<option>富士A50</option>
					    	<option>富士A50</option>
						</select>
    				</div>
				</div>
				<div class="quotation_p">
					<label>下单方式</label>
					<div class="quotation_type_fr">
						<label class="xdfs_sj"><input type="radio" name="mode_order" id="marketPrice" checked="checked"/>市价</label>
						<label class="xdfs_xj"><input type="radio" name="mode_order" id="fixedPrice" style="margin-left:50px;"/>限价</label>
					</div>
				</div>
				<div class="quotation_p">
					<label>委托价格</label>
					<div class="quotation_type_fr">
						<p class="money_sj">市价</p>
						<p class="money_js" style="display: none;">
							<span class="del">-</span>
							<span class="add">+</span>
							<input type="text" name="" id="money_number" style="" value="102.5">
						</p>
					</div>
				</div>
				<div class="quotation_p">
					<label>委托数量</label>
					<div class="quotation_type_fr">
						<p class="money_sjsl">
							<span class="del">-</span>
							<span class="add">+</span>
							<input type="text" name="" id="entrust_number" style="margin-top:5px;" value="1"/>
						</p>
					</div>
				</div>
				<div class="quotation_p" style="margin-top: 10px;">
					<label style="width: 56px;">&nbsp;</label>
					<div class="quotation_type_fr">
						<ul class="mairu">
							<li style="border-bottom: 1px solid #ff8c8c;">买入</li>
							<li>1025.2</li>
						</ul>
						<ul class="maichu" style="margin-left: 8px;">
							<li style="border-bottom: 1px solid #83d983">卖出</li>
							<li>1025.2</li>
						</ul>
					</div>
				</div>
    		</div>
    		<div class="quotation_detailed">
			    <div class="quotation_detailed_lis">
			    	<a href="javascript:void(0);" class="on">持仓</a>|
				    <a href="javascript:void(0);">委托</a>|
				    <a href="javascript:void(0);">挂单</a>|
				    <a href="javascript:void(0);">成交记录</a>|
				    <a href="javascript:void(0);">资金明细</a>
			    </div>
			    <div class="quotation_detailed_conent" style="position: relative;">
			        <div class="quotation_detailed_title">
			        	<ul class="tab_lis">
			        		<li class="ml" style="width: 100px;">合约代码</li>
			        		<li>持仓数量</li>
			        		<li>买卖</li>
			        		<li>持仓均价</li>
			        		<li>浮动盈利</li>
			        		<li>交易所</li>
			        		<li>币种</li>
			        	</ul>
			        	<ul class="tab_content">
			        		<li class="ml" style="width: 100px;">富时A501610</li>
			        		<li>24234</li>
			        		<li>32</li>
			        		<li>1313.32</li>
			        		<li>322323</li>
			        		<li>纽约交易所</li>
			        		<li>USA</li>
			        	</ul>
			        	<ul class="tab_content">
			        		<li class="ml" style="width: 100px;">富时A501610</li>
			        		<li>24234</li>
			        		<li>32</li>
			        		<li>1313.32</li>
			        		<li>322323</li>
			        		<li>纽约交易所</li>
			        		<li>USA</li>
			        	</ul>
			        	<ul class="tab_content">
			        		<li class="ml" style="width: 100px;">富时A501610</li>
			        		<li>24234</li>
			        		<li>32</li>
			        		<li>1313.32</li>
			        		<li>322323</li>
			        		<li>纽约交易所</li>
			        		<li>USA</li>
			        	</ul>
			        	<ul class="tab_content">
			        		<li class="ml" style="width: 100px;">富时A501610</li>
			        		<li>24234</li>
			        		<li>32</li>
			        		<li>1313.32</li>
			        		<li>322323</li>
			        		<li>纽约交易所</li>
			        		<li>USA</li>
			        	</ul>
			        	<ul class="tab_content">
			        		<li class="ml" style="width: 100px;">富时A501610</li>
			        		<li>24234</li>
			        		<li>32</li>
			        		<li>1313.32</li>
			        		<li>322323</li>
			        		<li>纽约交易所</li>
			        		<li>USA</li>
			        	</ul>
			        	<ul class="tab_content">
			        		<li class="ml" style="width: 100px;">富时A501610</li>
			        		<li>24234</li>
			        		<li>32</li>
			        		<li>1313.32</li>
			        		<li>322323</li>
			        		<li>纽约交易所</li>
			        		<li>USA</li>
			        	</ul>
			        	<ul class="tab_content">
			        		<li class="ml" style="width: 100px;">富时A501610</li>
			        		<li>24234</li>
			        		<li>32</li>
			        		<li>1313.32</li>
			        		<li>322323</li>
			        		<li>纽约交易所</li>
			        		<li>USA</li>
			        	</ul>
			        	<ul class="tab_content">
			        		<li class="ml" style="width: 100px;">富时A501610</li>
			        		<li>24234</li>
			        		<li>32</li>
			        		<li>1313.32</li>
			        		<li>322323</li>
			        		<li>纽约交易所</li>
			        		<li>USA</li>
			        	</ul>
			        	<ul class="caozuo">
			        		<li><a href="javascript:void(0);">全部平仓</a></li>
			        		<li><a href="javascript:void(0);">平仓</a></li>
			        		<li><a href="javascript:void(0);">反手</a></li>
			        	</ul>
			        </div>
			        <div class="quotation_detailed_title" style="display: none;">
			        	<ul class="tab_lis">
			        		<li class="ml">合约代码</li>
			        		<li>买卖</li>
			        		<li>委托价</li>
			        		<li>委托量</li>
			        		<li>触发价</li>
			        		<li>委托状态</li>
			        		<li>委托状态</li>
			        		<li>成交均价</li>
			        		<li>成交量</li>
			        		<li>撤单时间</li>
			        		<li>订单号</li>
			        	</ul>
			        	<ul class="tab_content">
			        		<li class="ml">富时A50</li>
			        		<li>2</li>
			        		<li>982424.23</li>
			        		<li>23</li>
			        		<li>982424.23</li>
			        		<li>已完成</li>
			        		<li>09:23:43</li>
			        		<li>982424.23</li>
			        		<li>3</li>
			        		<li>09:23:43</li>
			        		<li>3232131231</li>
			        	</ul>
			        	<ul class="caozuo">
			        		<li><a href="javascript:void(0);">撤单</a></li>
			        		<li><a href="javascript:void(0);">全撤</a></li>
			        	</ul>
			        </div>
			        <div class="quotation_detailed_title" style="display: none;">
			        	<ul class="tab_lis">
			        		<li class="ml">合约代码</li>
			        		<li>合约名称</li>
			        		<li>买卖</li>
			        		<li>委托价</li>
			        		<li>委托量</li>
			        		<li>挂单量</li>
			        	</ul>
			        	<ul class="tab_content">
			        		<li class="ml">合约代码</li>
			        		<li>合约名称</li>
			        		<li>买卖</li>
			        		<li>委托价</li>
			        		<li>委托量</li>
			        		<li>挂单量</li>
			        	</ul>
			        	<ul class="caozuo">
			        		<li><a href="javascript:void(0);">撤单</a></li>
			        	</ul>
			        </div>
			        <div class="quotation_detailed_title" style="display: none;">
			        	<ul class="tab_lis">
			        		<li class="ml">合约代码</li>
			        		<li>买卖</li>
			        		<li>成交均价</li>
			        		<li>成交量</li>
			        		<li>币种</li>
			        		<li>成交编号</li>
			        		<li>订单号</li>
			        		<li>成交时间</li>
			        		<li>交易所</li>
			        	</ul>
			        	<ul class="tab_content">
			        		<li class="ml">富时A50</li>
			        		<li>2</li>
			        		<li>982424.23</li>
			        		<li>23</li>
			        		<li></li>
			        		<li></li>
			        		<li></li>
			        		<li>09:23:43</li>
			        		<li></li>
			        	</ul>
			        </div>
			        <div class="quotation_detailed_title" style="display: none;">
			        	<ul class="tab_lis">
			        		<li class="ml">币种</li>
			        		<li>今权益</li>
			        		<li>今可用</li>
			        		<li>保证金</li>
			        		<li>今日浮盈</li>
			        		<li>维持保证金</li>
			        		<li>做收益</li>
			        		<li>昨结存</li>
			        		<li>今结存</li>
			        		<li>冻结资金</li>
			        		<li>盈利率</li>
			        	</ul>
			        	<ul class="tab_content">
			        		<li class="ml">USD</li>
			        		<li>20554.54</li>
			        		<li>20554.54</li>
			        		<li>20554.54</li>
			        		<li>10</li>
			        		<li>22333</li>
			        		<li>33333</li>
			        		<li>33333</li>
			        		<li>33333</li>
			        		<li>0.0</li>
			        		<li>5%</li>
			        	</ul>
			        </div>
			    </div>
				<div class="quotation_detailed_qx">
					<label>账户资产：<span>00.0</span></label>
					<label>交易保障金：<span>00.0</span></label>
					<label>账户余额：<span>00.0</span></label>
					<label>持仓盈亏：<span>00.0</span></label>
					<label>交易盈亏：<span>00.0</span></label>
					<a href="javascript:void(0);">追加保证金</a>
				</div>
			</div>
    	</div>	
    </div>
</div>
</body>
</html>