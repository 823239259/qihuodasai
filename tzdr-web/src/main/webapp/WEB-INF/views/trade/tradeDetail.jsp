<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.tzdr.common.utils.ConfUtil"%> 
<%@ include file="../common/common.jsp"%>
   <%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../common/commonkeyword.jsp"%>
 <link rel="stylesheet" href="${ctx}/static/css/uc.css?version=20150710"  type="text/css">
 <link rel="stylesheet" type="text/css" href="${ctx}/static/css/public.css">
 <link href="${ctx}/static/css/public.css" rel="stylesheet" type="text/css">
 <link href="${ctx}/static/css/pagination.css" rel="stylesheet" type="text/css" /> 	
<script type="text/javascript" src="${ctx}/static/script/tzdr.js"></script>
 <script src="${ctx}/static/script/common/jquery.pagination.js" type="text/javascript"></script>
 <script type="text/javascript" src="${ctx}/static/script/common/dateUtils.js"></script> 
 <script type="text/javascript" src="${ctx}/static/script/trade/tradeDetail.js?v=${v}"></script>
 <script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/buttonLite.js#style=-1&amp;uuid=fc88a5c2-48c9-4d9d-9c01-ec40b668977f&amp;pophcol=2&amp;lang=zh"></script>
<script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/bshareC2P.js"></script>

<script type="text/javascript">
	
</script>

</head>
<body>

<!--顶部 -->
	<%@ include file="../common/personheader.jsp"%>
	
<!-- 浮动框 -->
<div class="floatlayer">
	<!-- 追加方案 -->
	<div class="uc_tffl_trade" style="display:none;">	
		<p class="uc_tdfl_font">你可以在<em>一个交易号中</em>操作<em>多个方案！</em>但<em>放大倍数</em>与<em>借款结束时间</em>必须与当前方案相同!</p>
		<i class="uc_tdfl_icon"></i>
	</div>
	<c:choose>
		<c:when test="${trade.feeType eq 0 || trade.feeType eq 1}">
			<!-- 追加保证金 -->
			<div class="uc_tdfl_bail" style="display:none;">
				<h2 class="uc_tdfl_title">友情提示：</h2>
				<p class="uc_tdfl_font">建议您追加保证金金额+当前市值不要高于您的期初资产，多余的金额没有放大。如果您想增加收益，请选择追加方案</p>
				<i class="uc_tdfl_icon"></i>
			</div>
			<!-- 终止操盘 -->
			<div class="uc_tdfl_stop" style="display:none;">
				<p class="uc_tdfl_font">请将您的全部股票清仓后，进行终结方案！</p>
				<i class="uc_tdfl_icon"></i>
			</div>
		</c:when>
		<c:when test="${trade.feeType eq 2 || trade.feeType eq 3}">
			<!-- 追加保证金 -->
			<div class="uc_tdfl_bail uc_tdfl_bail2 " style="display:none;">
				<h2 class="uc_tdfl_title">友情提示：</h2>
				<p class="uc_tdfl_font">建议您追加保证金金额+当前市值不要高于您的期初资产，多余的金额没有放大。如果您想增加收益，请选择追加方案！每日最多追加保证金5次！</p>
				<i class="uc_tdfl_icon"></i>
			</div>
		 	<!-- 终止操盘 -->
			<div class="uc_tdfl_stop uc_tdfl_stop2" style="display:none;">
				<p class="uc_tdfl_font">请将您的全部股票清仓后，进行终结方案！</p>
				<i class="uc_tdfl_icon"></i>
			</div>
		</c:when>
		<c:otherwise>
			<!-- 追加保证金 -->
			<div class="uc_tdfl_bail uc_tdfl_bail2 " style="display:none;">
				<h2 class="uc_tdfl_title">友情提示：</h2>
				<p class="uc_tdfl_font">建议您追加保证金金额+当前市值不要高于您的期初资产，多余的金额没有放大。如果您想增加收益，请选择追加方案！每日最多追加保证金5次！</p>
				<i class="uc_tdfl_icon"></i>
			</div>
		 	<!-- 终止操盘 -->
			<div class="uc_tdfl_stop uc_tdfl_stop2" style="display:none;">
				<p class="uc_tdfl_font">请将您的全部股票清仓后，进行终结方案！</p>
				<i class="uc_tdfl_icon"></i>
			</div>
		</c:otherwise>
	</c:choose>
	<div class="uc_tdfl_pro" style="display:none;">
		<p class="uc_tdfl_font">当天收盘后，才能提取利润</p>
		<i class="uc_tdfl_icon"></i>	
	</div>
	<!-- 终止操盘  提示框-->
	<div class="fl_box fl_uc_stops" style="display:none;" id="endOfProgramAlert">		
		<div class="fl_navtitle">
			<h3>终止操盘</h3>
			<a href="javascript:void(0)" class="close"></a>
		</div>
		<div class="fl_uc_main">
			<p class="fl_uc_stopfont">你确认要终结方案么？</p>
		</div>
		<div class="fl_uc_btn">
			<a href="javascript:void(0)" class="fl_uc_cancelbtn" style="margin:auto;float:none;">确认</a>
		</div>
	</div>	
	<!-- 终止操盘 确认框 -->
	<div class="fl_box fl_uc_stops" style="display:none;" id="endOfProgramConfirm">		
		<div class="fl_navtitle">
			<h3>终止操盘</h3>
			<a href="javascript:void(0)" class="close"></a>
		</div>
		<div class="fl_uc_main">
			<p class="fl_uc_stopfont">你确认要终结方案么？</p>
		</div>
		<div class="fl_uc_btn">
			<a id="endOfProgram" href="javascript:void(0)" class="fl_uc_surebtn">确定</a>
			<a href="javascript:void(0)" class="fl_uc_cancelbtn">取消</a>
		</div>
	</div>	
	<!-- 遮罩层 -->
	<div class="fl_mask" style="display:none;"></div>
	<!-- 追加保证金弹出框 -->
	<div class="fl_box fl_uc_trade" style="display:none;">
		<div class="fl_navtitle">
			<h3>追加保证金</h3>
			<a href="javascript:void(0);" class="close"></a>
		</div>
		<div class="fl_uc_main">
			<ul class="fl_uc_list">
				<li>
					<label>账户余额：</label>
					<input type="hidden" id="balance" value="${balance}" />
					<!-- 从下面操盘规则出获取
					<input type="hidden" id="group_id" value="${gid}" />
					 -->
					<span>
						<i><fmt:formatNumber value="${balance}"  type="number" minFractionDigits="2"  ></fmt:formatNumber></i>元
						<a href="${ctx}/pay/payinfo" target="_blank">&nbsp;&nbsp;去充值&gt;&gt;</a>
					</span>
				</li>
				<!-- 
				<li>
					<label>总操盘资金：</label>
					<span><i><fmt:formatNumber value="${trade.totalOperateMoney}" type="number" minFractionDigits="2"></fmt:formatNumber></i>元</span>
				</li>
				 -->
				<li>
					<label>追加金额：</label>
					<input id="additionalMargin" type="hidden" value="${trade.activityType}" />
					<input id="minAddMoney" type="hidden" value="${minAddMoney}" />
					<input id="maxAddMoney" type="hidden" value="${maxAddMoney}" />
					<input id="addMoney" type="text" maxlength="9" value="${minAddMoney}" />
					<span>元</span>
				</li>
			</ul>
		</div>
		<div class="fl_uc_btn">
			<a href="javascript:void(0);" class="fl_uc_cancelbtn">取消</a>
			<a id="addBondNext" href="javascript:void(0);" class="fl_uc_surebtn">下一步</a>
		</div>
	</div>
	<!-- 追加保证金弹出确认框 -->
	<div class="fl_box fl_uc_trade2" style="display: none;">
		<div class="fl_navtitle">
			<h3>追加保证金</h3>
			<a href="javascript:void(0);" class="close"></a>
		</div>
		<div class="fl_uc_main">
			<p style="padding: 8px 16px;font-size:14px;text-align: left;font-family: '宋体'; line-height: 24px;">一，系统会先扣账户余额，再追加到A股交易账户中，追加成功后会以短信提示您；</p>
			<p style="padding: 8px 16px;font-size:14px;text-align: left;font-family: '宋体'; line-height: 24px;">二，交易时间，10分钟内完成追加；非交易时间，下个交易日开盘前完成追加。</p>
			<p style="padding: 16px;font-size:14px;text-align: center;font-family: '宋体'; line-height: 24px;">请确认是否追加？</p>
		</div>
		<div class="fl_uc_btn">
			<a href="javascript:void(0);" class="fl_uc_cancelbtn">取消</a>
			<a id="addBond" href="javascript:void(0);" class="fl_uc_surebtn">确认</a>
		</div>
	</div>
	<!-- 提取利润弹出框 -->
	<div class="fl_box fl_uc_pro"  style="display:none">
		<div class="fl_navtitle">
			<h3>提取利润</h3>
			<a href="javascript:void(0)" class="close"></a>
		</div>
		<div class="fl_uc_main">
			<ul class="fl_uc_list">
				<li>
					<label>可提取金额：</label>
					<input type="hidden"  id="isExtractableProfit" value="${isExtractableProfit}"/>
					<input type="hidden"  id="extractableProfit" value="${trade.extractableProfit}"/>
					<span><i>${trade.extractableProfit}</i>元</span>
				</li>
				<li>
					<label>提现金额：</label>
					<input type="text" id="extractMoney"value="${trade.extractableProfit}">
					<span>元</span>
				</li>
			</ul>
		</div>
		<div class="fl_uc_btn">
			<a id="extractableProfitBtn" href="javascript:void(0);" class="fl_uc_surebtn">确定</a>
			<a href="javascript:void(0);" class="fl_uc_cancelbtn">取消</a>
		</div>
	</div>

</div>
<div class="uc">

	<!--个人中心导航 -->
	<%@ include file="../common/leftnav.jsp"%>
	<div class="uc_mianbar">
		<div class="uc_td_main">
		<c:if test="${trade.status eq 1}">
			<c:choose>
				<c:when test="${trade.feeType eq 0 || trade.feeType eq 1}">
					<!-- 正在操盘中 -->
					<div class="uc_td_trade" style="display:block;">
						<h3>正在操盘中</h3>
						<ul id="uc_qj_trading_info">
							<%--
							<li>
								<label>总盈亏：</label>
								<span>
								<c:choose>
									<c:when test="${trade.totalAccrual ge 0}">
										<i class="uc_red"><fmt:formatNumber value="${trade.totalAccrual}"  type="number" minFractionDigits="2"  ></fmt:formatNumber></i>元
									</c:when>
									<c:otherwise>
										<i class="uc_green"><fmt:formatNumber value="${trade.totalAccrual}"  type="number" minFractionDigits="2"  ></fmt:formatNumber></i>元
									</c:otherwise>
									</c:choose>
								
								</span>
							</li>
							<li>
								<label>持仓现金余额：</label>
								<span><fmt:formatNumber value="${trade.cashBalance}"  type="number" minFractionDigits="2"  ></fmt:formatNumber>元</span>
							</li>
							<li>
							
								<label>可提取盈利：</label>
								<span><fmt:formatNumber value="${trade.extractableProfit}"  type="number" minFractionDigits="2"  ></fmt:formatNumber>元</span>
							</li>
							<li>
								<label>净资产：</label>
								<span><fmt:formatNumber value="${trade.assetTotalValue}"  type="number" minFractionDigits="2"  ></fmt:formatNumber>元</span>
							</li>
							<li>
								<label>股票资产：</label>
								<span><fmt:formatNumber value="${trade.stockAssets}"  type="number" minFractionDigits="2"  ></fmt:formatNumber>元</span>
							</li>
							 --%>
							 <li>
								<label>总盈亏：</label>
								<span style="display:none">
								<%-- <c:choose>
									<c:when test="${trade.totalAccrual ge 0}">
										<i class="uc_red">1元</i>
									</c:when>
									<c:otherwise>
										<i class="uc_green">0元</i>
									</c:otherwise>
									</c:choose> --%>
								</span>
								<a href="javascript:;">点击获取</a>
								<img src="${ctx }/static/images/uc/loading.gif" style="display: none;"/>
							</li>
							<li>
								<label>持仓现金余额：</label>
								<span style="display:none;"></span>
								<a href="javascript:;">点击获取</a>
								<img src="${ctx }/static/images/uc/loading.gif" style="display: none;"/>
							</li>
							<li>
								<label>可提取盈利：</label>
								<span style="display:none;"></span>
								<a href="javascript:;">点击获取</a>
								<img src="${ctx }/static/images/uc/loading.gif" style="display: none;"/>
							</li>
							<li>
								<label>净资产：</label>
								<span style="display:none;"></span>
								<a href="javascript:;">点击获取</a>
								<img src="${ctx }/static/images/uc/loading.gif" style="display: none;"/>
							</li>
							<li>
								<label>股票资产：</label>
								<span style="display:none;"></span>
								<a href="javascript:;">点击获取</a>
								<img src="${ctx }/static/images/uc/loading.gif" style="display: none;"/>
							</li>
						</ul>
						<div class="uc_td_tdbtn">
							<input type="hidden"  id="isAddProgram" value="${isAddProgram}"/> <!--  -->
							<a href="${ctx}/trade/more/${trade.groupId}" data-feeType="${trade.feeType}" class="ul_td_tradebtn">追加操盘方案</a>
							<!-- <a href="javascript:void(0)" class="ul_td_tradebtn">追加操盘方案</a> -->
							<a href="javascript:void(0)" class="ul_td_bailbtn" data-feeType="${trade.feeType}">追加保证金</a>
							<a href="javascript:void(0)" class="ul_td_stopbtn" data-feeType="${trade.feeType}">终止操盘</a>
							<a href="javascript:void(0)" class="ul_td_probtn" data-totalLending="${trade.totalLending}" data-totalLeverMoney="${trade.totalLeverMoney}">提取利润</a>
						</div>
					</div>
				</c:when>
				<c:otherwise>  
					<!-- 涌金版正在操盘中 -->
					<div class="uc_td_trade" style="display:block;">
						<h3>正在操盘中</h3>
						<ul>
							<li>
								<label>总操盘金：</label>
								<span><i><fmt:formatNumber value="${trade.totalOperateMoney}"  type="number" minFractionDigits="2"  ></fmt:formatNumber></i>元</span>
							</li>
							<li>
								<label>操盘配额：</label>
								<span><fmt:formatNumber value="${trade.totalLending}"  type="number" minFractionDigits="2"  ></fmt:formatNumber>元</span>
							</li>
							<li>
								<label>操盘保证金：</label>
								<span><fmt:formatNumber value="${trade.totalLeverMoney}"  type="number" minFractionDigits="2"  ></fmt:formatNumber>元</span>
							</li>
							<li>
								<label>追加保证金：</label>
								<span><fmt:formatNumber value="${trade.totalAppendLeverMoney}"  type="number" minFractionDigits="2"  ></fmt:formatNumber>元</span>
							</li>
							<li>
								<label>类型：</label>
								<span>
									<c:if test="${trade.feeType eq 2}">涌金版</c:if>
									<c:if test="${trade.feeType eq 3}">同花顺</c:if>
								</span>
							</li>
						</ul>
						<div class="uc_td_tdbtn">
						    
							<a href="javascript:void(0)" data-feeType="${trade.feeType}" <c:if test="${activityType6600 == 2}"> style="display:none;" </c:if>  class="ul_td_bailbtn">追加保证金</a>
							
							<a href="javascript:void(0)" data-feeType="${trade.feeType}" class="ul_td_stopbtn" data-feeType="${trade.feeType}">终止操盘</a>
						</div>
					</div>
				</c:otherwise>
			</c:choose>
			</c:if>
			<c:if test="${trade.status eq 2}">
			<div class="uc_td_tradestop">
				<c:if test="${activityEnd}">
				<p class="uc_td_ttpromt">8800活动你打败<i>${rankint}%</i>的用户</p>
				</c:if>
				<div class="uc_tds_title">
					<h3>方案已完结</h3>
					<span><i>结算金额</i></span>
					<p><em><fmt:formatNumber value="${trade.finishedMoney}" type="number" minFractionDigits="2"  ></fmt:formatNumber></em>元</p>
				</div>
				<c:if test="${trade.totalAccrual ge 0}">
				<div class="uc_tds_money">
					<h3>投资盈利</h3>
					<p><i>+<fmt:formatNumber value="${trade.totalAccrual}"  type="number" minFractionDigits="2"  ></fmt:formatNumber></i>元</p>
				</div>
				</c:if>
					<c:if test="${trade.totalAccrual lt 0}">
				<div class="uc_tds_money_kui">
					<h3>投资亏损</h3>
					<p><i><fmt:formatNumber value="${trade.totalAccrual}"  type="number" minFractionDigits="2"  ></fmt:formatNumber></i>元</p>
				</div>
				</c:if>
				<c:if test="${activityEnd}">
				<div class="uc_spl_sharelist">
				<!-- <span>分享到：</span> -->
					<div class="bshare-custom icon-medium" style="margin-left: 11px;margin-top: inherit;" bshareUrl="<%=ConfUtil.getContext("web.address") %>/share/${trade.groupId}" bshareTitle="股票操盘，放大收益，维胜8800活动" bshareSummary="你打败 ${rankint}%的活动用户。" bsharePic="<%=ConfUtil.getContext("web.address") %>/static/images/common/logo.jpg">
						<div class="bsPromo bsPromo2"></div>
						<div class="bsPromo bsPromo2"></div>           	 
					</div>
				</div>
			</c:if>
			</div>
			
			</c:if>
			<!-- 操盘规则 -->
			<div class="uc_td_rule">
				<h3><em>操盘规则</em><i id="group_id">${trade.groupId}</i></h3>
				<ul class="ul_td_rulenum">
					<li class="ul_td_ruletotal">
						<p><i><fmt:formatNumber value="${trade.totalOperateMoney}" type="number" minFractionDigits="0"></fmt:formatNumber></i>元</p>
						<span>总操盘资金</span>
					</li>
					<li class="uc_td_ruleicon">=</li>
					<li class="uc_td_rulemoeny">
						<p><i><fmt:formatNumber value="${trade.totalLending}" type="number" minFractionDigits="0"></fmt:formatNumber></i>元</p>
						<span>操盘配额</span>	
					</li>
					<li class="uc_td_ruleicon">+</li>
					<li class="uc_td_rulemoeny">
						<p><i><fmt:formatNumber value="${trade.totalLeverMoney}" type="number" minFractionDigits="0"></fmt:formatNumber></i>元</p>
						<span>操盘保证金</span>	
					</li>
					<li class="uc_td_ruleicon">+</li>
					<li class="uc_td_rulemoeny">
						<p><i><fmt:formatNumber value="${trade.totalAppendLeverMoney}" type="number" minFractionDigits="0"></fmt:formatNumber></i>元</p>
						<span>追加保证金</span>	
					</li>
				</ul>	
				<ul class="ul_td_rulelist">
					<ol>
						<li>
							<label>亏损补仓线</label>
							<span><i><fmt:formatNumber value="${trade.warning}" type="number" minFractionDigits="0"></fmt:formatNumber></i>元</span>
						</li>
						<li class="ul_tdrl_space">
							<label>亏损平仓线</label>
							<span><i><fmt:formatNumber value="${trade.open}" type="number" minFractionDigits="0"></fmt:formatNumber></i>元</span>
						</li>
					</ol>
					<ol>
						<li>
							<label>账户管理费</label>
							<span><em><fmt:formatNumber value="${trade.feeDay}" type="number" minFractionDigits="2"></fmt:formatNumber></em>元/天	<c:if test="${trade.activityType eq 1}"><img src="${ctx}/static/images/free.gif" title="达人红包8800活动省"></c:if></span>
						</li>
						<li class="ul_tdrl_space">
							<label>总操盘利息</label>
							<span>
							<em><fmt:formatNumber value="${trade.apr}" type="number" minFractionDigits="2"></fmt:formatNumber></em>元
							<c:if test="${isshowticket==true}">
							<img src="${ctx}/static/images/common/ticket.png" class="ul_tdrl_ticket">
							</c:if>
							<c:if test="${trade.activityType eq 1}">
							<img src="${ctx}/static/images/free.gif" title="达人红包8800活动省">
							</c:if>
							</span>
						</li>
					</ol>
					<ol>
						<li>
							<label>借款期限</label>
							<span><em>${trade.naturalDays} </em>天</span>
						</li>
						<li class="ul_tdrl_space">
							<label>已交易天数</label>
							<span><em>${trade.tradingDays} </em>天</span>
						</li>
					</ol>
					<ol>
						<li>
							<label>借款开始时间</label>
							<span>${trade.starttimeString}</span>
						</li>
						<li class="ul_tdrl_space">
							<label>借款结束时间</label>
							<span>${trade.estimateEndtimeString}</span>
						</li>
					</ol>
					<ol class="ul_td_rulefont">
						<li>
							<label>操盘须知</label>
							<span>${operatorsInfo}</span>
						</li>
					</ol>
					<c:if test="${!empty parentEndDate}">
					<ol>
						<li>
							<label>最长可延期</label>
							<span>${parentEndDate }</span>
						</li>
					</ol>
					</c:if>
				</ul>	
			</div>
		</div>
		<div class="uc_td_live">
			<ul class="uc_td_livelist">
				
				<ol></ol>
			</ul>
		</div>
		<div class="uc_td_list">
			<ul class="uc_paynav uc_paynav2">
				<li><a href="javascript:void(0);"  class="on" data="1" id="program">方案列表</a></li>
				
				<c:if test="${trade.status eq 1}">
				<li><a href="javascript:void(0);"  data="2">当前持仓明细</a></li>
				</c:if>	
				<c:if test="${trade.status eq 2}">
				<li><a href="javascript:void(0);"  data="7">历史持仓明细</a></li>
				</c:if>	
				<li><a href="javascript:void(0);"  data="3">账户管理费</a></li>
				<li><a href="javascript:void(0);"  data="4">股票交易账户</a></li>
				<li><a href="javascript:void(0);"  data="5">利润提取记录</a></li>
				<li><a href="javascript:void(0);"  data="6">追加保证金</a></li>
			</ul>
			<!-- 方案列表 -->
			<div class="uc_td_program box box1" id="program_list">
				<ul class="uc_td_pgtitle">
					<li>方案ID</li>
					<li>总操盘资金</li>
					<li>操盘配额</li>
					<li>操盘保证金</li>
					<!--<li>亏损补仓钱</li> -->
					<!--<li>亏损平仓钱</li> -->
					<li>账户管理费</li>
					<li>总操盘利息</li>
					<li>操盘时间</li>
					<li></li>
				</ul>
				<table width="790" border="0" cellspacing="0" cellpadding="0" id="program_table">
				 
				</table>
			</div>
			<c:if test="${trade.status eq 1}">
			<!-- 当前持仓明细 -->
			<div class="uc_td_details box box2" style="display:none;">
				<ul class="uc_td_dttitle">
					<li>证券代码</li>
					<li>成本价</li>
					<li>持仓数量</li>
					<li>持仓市值</li>
					<li>浮动盈亏</li>
					<li>浮盈率（%）</li>
					<li>日期</li>
				</ul>				
				<table width="790" border="0" cellspacing="0" cellpadding="0" id="positionDetails_table">
				</table>
			</div>
			</c:if>
			<c:if test="${trade.status eq 2}">
			<!-- 历史持仓明细 -->
			<div class="uc_td_details box box7" style="display:none;">
				<ul class="uc_td_dttitle">
					<li>证券代码</li>
					<li>交易市场</li>
					<li>委托方向</li>
					<li>成交价格</li>
					<li>成交数量</li>
					<li>成交金额</li>
					<li>成交时间</li>
				</ul>				
				<table width="790" border="0" cellspacing="0" cellpadding="0" id="historyDetails_table">
				</table>
			</div>
			</c:if>
			<!-- 账户管理费/利息 -->
			<c:if test="${trade.activityType eq 0 ||trade.activityType eq 3}">
			<div class="uc_td_interest box box3" style="display:none;">				
				<ul class="uc_tdit_list" id="fee">
					
				
				</ul>
				<ul class="ud_tdit_info">
					<li>
						<p>已支付管理费</p>
						<span><i id="sumManage"></i>元</span>
					</li>
					<li>
						<p>已支付利息</p>
						<span><i id="sumInterest"></i>元
						
						</span>
					</li>
					<li class="last">						
						<p>已支付天数</p>
						<span><i>${trade.tradingDays} </i>天</span>
					</li>
				</ul>
			</div>
			</c:if>
			<c:if test="${trade.activityType eq 1}">
			<div class="uc_td_interest box box3" style="display:none;">				
				<ul class="ud_tdit_freeinfo">
					<li>
						<p>已支付管理费</p>
						<span><i>${totalManageFee}</i>元<img src="${ctx}/static/images/free.gif" title="达人红包8800活动省"></span>
					</li>
					<li>
						<p>已支付利息</p>
						<span><i>${trade.apr}</i>元<img src="${ctx}/static/images/free.gif" title="达人红包8800活动省"></span>
					</li>
					<li class="last">						
						<p>已支付天数</p>
						<span><i>${trade.tradingDays}</i>天</span>
					</li>
				</ul>
			</div>
			</c:if>
			<!-- 股票交易账户-->
			<div class="uc_td_interest box box4">
			<c:if test="${trade.status ne 0}">			
				<div class="uc_tlpromt_account">					
					<p>重点提醒：交易前请线阅读 <a href="${ctx}/help?tab=configuration&leftMenu=1" target="_blank">《股票操盘规则说明》</a></p>
					<p>资金风控：亏损警戒线<i><fmt:formatNumber value="${trade.warning}" type="number" minFractionDigits="0"></fmt:formatNumber></i> 元，亏损平仓线<i><fmt:formatNumber value="${trade.open} " type="number" minFractionDigits="0"></fmt:formatNumber></i>元</p>
					<p>开户券商：<span>${trade.hsBelongBroker}</span></p>
					<p>交易账户：<span>${trade.account}</span></p>
					<p>交易密码：<span>${trade.password}</span>(为了您的资金安全，请妥善保管好密码)</p>
					<c:if test="${!empty insuranceNo}">
					<p>保险单号：<a href="#">${insuranceNo }</a></p>
					</c:if>
					<%-- <c:choose>
						<c:when test="${trade.feeType eq 2}">
							<p> 交易软件：<a href="${ctx}/help?tab=software&leftMenu=5" target="_blank">涌金版软件下载</a></p>
						</c:when>
						<c:when test="${trade.feeType eq 3}">
							<p> 交易软件：<a href="${ctx}/help?tab=software&leftMenu=6" target="_blank">同花顺软件下载</a></p>
						</c:when>
						<c:otherwise>
							<p> 交易软件：<a href="${ctx}/help?tab=software&leftMenu=2" target="_blank">独立委托版</a></p>
						</c:otherwise>
					</c:choose> --%>
					<p> 交易软件：1.若您的交易帐号以“<i style='color: red;'>6236</i>”开头，需使用钱江版交易软件，请“<a href='http://www.ihoms.com/file/xunlei/downloadxunlei/HOMS_QiJian/ZhengShi/DuLi/homs_duli.exe?fileCode=10&sourceType=4' target='_blank'>立即下载</a>”;<br><em class="uc_tlpromtsp2">2.若您的交易帐号以“<i style='color: red;'>8709</i>”开头，需使用涌金版交易软件，请“<a href='http://www.tzdr.com/static/apply/HOMS_YONGJINBAN_jiaoyiduan.exe' target='_blank'>立即下载</a>”；</em><br><em class="uc_tlpromtsp2">3.若您的交易帐号以“<i style="color: red;">15703</i>”开头，需使用钱隆TTS股票交易系统，请“<a href='${ctx}/help?tab=software&leftMenu=1' target="_blank">立即下载</a>”。</em></p>
					<%--
					<p>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp提示：1.停牌股处理规则，<a href="${ctx}/help?tab=configuration&leftMenu=6" target="_blank">点击查看</a><br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp2.方案最长可延期至：${parentEndDate }<br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp3.平仓后方案会继续保留并产生费用，停止产生费用请终结方案。</p>
					 --%>
					<p>
					<em class="uc_tlpromtsp">提示：1.停牌股处理规则，<a href="${ctx}/help?tab=configuration&leftMenu=6" target="_blank">点击查看</a><br></em>
					<em class="uc_tlpromtsp2">2.平仓后方案继续保留并产生费用，停止产品费用请终结方案</em>
					</p>
				</div>
			</c:if>	
			</div>
			<!-- 利润提取记录 -->
			<div class="uc_td_profit box box5"  style="display:none;">
				<p class="ud_td_pftitle">累计提取<i id="profit_count">0</i>笔，总计<i id="profit_money">0</i>元</p>
				<ul class="uc_tdpf_list" id="profits">
					
				</ul>
			</div>
			<!-- 添加保证金记录 -->
			<div class="uc_td_bail box box6" style="display:none;">
				<p class="ud_td_pftitle">累计添加<i id="bail_count">0</i>笔，总计<i id="bail_money">0</i>元</p>
				<ul class="uc_tdpf_list" id="add">
					
				</ul>
			</div>
		</div>
	</div>
   <form id="showContract" name="showContract" action="" method="post" target="_blank"/>
</div>


	<%@ include file="../common/personfooter.jsp"%>
<%@ include file="../common/dsp.jsp"%>
</body>
</html>

