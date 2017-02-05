<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>股票合买方案${trade.groupId} 总操盘资金<fmt:formatNumber value="${trade.totalOperateMoney}" type="number" maxFractionDigits="2" />元 - 维胜</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/public.css?v=${v}">
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/buy.css?v=${v}"">
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/uc.css?v=${v}">
<script language="javascript" src="${ctx}/static/script/tzdr.js"></script>
<script type="text/javascript" src="${ctx}/static/script/common/dateUtils.js"></script> 
<script src="${ctx}/static/script/togetherTrade/publicDetail.js?v=${v}" type="text/javascript"></script>
</head>
</head>

<body>
<!--顶部 -->
	<!-- DINGBU -->
<div class="bd_info">
    <h2><em>股票合买方案：${trade.groupId}</em><em>总操盘资金：<fmt:formatNumber value="${trade.totalOperateMoney}"  type="number" maxFractionDigits="2"  ></fmt:formatNumber>元</em><a href="${ctx}/together/list"><< 返回列表</a></h2>
    <div class="bd_i_ctn">
        <div class="bd_i_list">
            <ul class="bd_il_one">
                <li>
                    <h4>操盘者出资</h4>
                    <span><i><fmt:formatNumber value="${trade.totalLeverMoney}"  type="number" maxFractionDigits="2"  ></fmt:formatNumber></i>元</span>
                </li>
                <li>
                    <h4>合买者出资</h4>
                    <span><i><fmt:formatNumber value="${trade.totalLending}"  type="number" maxFractionDigits="2"  ></fmt:formatNumber></i>元</span>
                </li>
                <li>
                    <h4>操盘周期</h4>
                    <span>T+<i>${trade.naturalDays}</i></span>
                </li>
                <li>
                    <h4>合买收益(年化)</h4>
                    <span><i><fmt:formatNumber value="${trade.feeMonth*36500/trade.totalLending}"  type="number" pattern="#.00"  maxFractionDigits="2"  ></fmt:formatNumber>%</i></span>
                </li>
                <li>
                    <h4>合买分利</h4>
                    <span><i><fmt:formatNumber value="${togetherTrade.profit_ratio*100}"  type="number"  pattern="#.00"  maxFractionDigits="2"  ></fmt:formatNumber>%</i></span>
                </li>
            </ul>
            <div class="bd_i_promt">
                <label>保障范围：</label>
                <a href="javascript:void(0);" class="color1">保底机制</a>
                
                <span>结算方式：<em>终结支付</em></span>
            </div>
        </div>
        <c:if test="${trade.status eq 0}">
        <!-- 合买进行中 -->
        <ul class="bd_i_list2">
            <li><label>合买人数：</label><span>0人</span></li>
            <li><label>已投金额：</label><span>0元</span></li>
            <li><label>合买进度：</label><span>合买中</span></li>
            <li><label>开始时间：</label><span>${trade.exportAddtime}</span></li>
        </ul>
    </div>
    <div class="bd_i_status">
        <div class="bd_is_trade" >
            <ul class="bd_il_one">
                <li>
                    <h4>可投金额</h4>
                    <span><i><fmt:formatNumber value="${trade.totalLending}"  type="number" maxFractionDigits="2"  ></fmt:formatNumber></i>元</span>
                </li>
                <li>
                    <h4>起投金额</h4>
                    <span><i>10</i>元</span>
                </li>
            </ul>
            <div class="bd_is_ip"><input type="text"><em>元</em></div>
            <div class="bd_is_btn"><a href="javascript:void(0);">立即合买</a></div>
            <div class="bd_is_agree"><input type="checkbox"><span>我已阅读并同意</span><a href="javascript:lookContract();">《股票合买操盘合作协议》</a></div>
        </div>
          </div>
			</div>
			
			<div class="bd_box">
			    <h3><i></i><em>方案信息</em></h3>
			    <div class="bd_b_ctn">
			        <ul class="bd_infolist">
			            <li class="li1"><label>持仓规则：</label><p>${operatorsInfo }</p></li>
			            <li class="li2"><label>亏损补仓线：</label><p><fmt:formatNumber value="${trade.warning}"  type="number" maxFractionDigits="2"  ></fmt:formatNumber>元 ，当操盘资金低于补仓线时，系统提醒操盘手使用自有资金进行补仓，防止被平仓</p></li>
			            <li class="li3"><label>总操盘资金：</label><p><fmt:formatNumber value="${trade.totalOperateMoney}"  type="number" maxFractionDigits="2"  ></fmt:formatNumber>元 ，操盘者和合买者共同出的资金，入金到维胜分发的操盘账户中</p></li>
			            <li class="li4"><label>亏损平仓线：</label><p><fmt:formatNumber value="${trade.open}"  type="number" maxFractionDigits="2"  ></fmt:formatNumber>元 ，当操盘资金低于平仓线时，系统将强行平仓，保护合买者的利益</p></li>
			            <li class="li5"><label>申请操盘天数：</label><p>${trade.naturalDays}天</p></li>
			            <li class="li6"><label>开始操盘日期：</label><p></p></li>
			        </ul>
			    </div>
			</div>
        </c:if>
        <c:if test="${trade.status eq 2}">
		<c:choose>
			<c:when test="${trade.auditStatus eq 2}">
				<!-- 合买失败 -->
				
       			 <ul class="bd_i_list2">
					<li><label>合买人数：</label><span>0人</span></li>
		            <li><label>已投金额：</label><span>0元</span></li>
		            <li><label>合买进度：</label><span>合买失败</span></li>
		            <li><label>开始时间：</label><span>${trade.exportAddtime}</span></li>
		         </ul>
			 </div>
			 
    	    <div class="bd_i_status">
				 <div class="bd_is_fail"  >
	          		  <a href="javascript:void(0);" class="failA">合买失败</a>
	        	</div>
        	</div>
        	  </div>
			</div>
			
			<div class="bd_box">
			    <h3><i></i><em>方案信息</em></h3>
			    <div class="bd_b_ctn">
			        <ul class="bd_infolist">
			            <li class="li1"><label>持仓规则：</label><p>${operatorsInfo }</p></li>
			            <li class="li2"><label>亏损补仓线：</label><p><fmt:formatNumber value="${trade.warning}"  type="number" maxFractionDigits="2"  ></fmt:formatNumber>元 ，当操盘资金低于补仓线时，系统提醒操盘手使用自有资金进行补仓，防止被平仓</p></li>
			            <li class="li3"><label>总操盘资金：</label><p><fmt:formatNumber value="${trade.totalOperateMoney}"  type="number" maxFractionDigits="2"  ></fmt:formatNumber>元 ，操盘者和合买者共同出的资金，入金到维胜分发的操盘账户中</p></li>
			            <li class="li4"><label>亏损平仓线：</label><p><fmt:formatNumber value="${trade.open}"  type="number" maxFractionDigits="2"  ></fmt:formatNumber>元 ，当操盘资金低于平仓线时，系统将强行平仓，保护合买者的利益</p></li>
			            <li class="li5"><label>申请操盘天数：</label><p>${trade.naturalDays}天</p></li>
			            <li class="li6"><label>开始操盘日期：</label><p></p></li>
			        </ul>
			    </div>
			</div>
		</c:when>
		<c:otherwise>	
		<!-- 方案已终结 -->
		<ul class="bd_i_list2">
			<li><label>合买人数：</label><span>${number}人</span></li>
            <li><label>已投金额：</label><span><fmt:formatNumber value="${trade.totalLending}"  type="number" maxFractionDigits="2"  ></fmt:formatNumber>元</span></li>
            <li><label>合买进度：</label><span>已终结</span></li>
            <li><label>开始时间：</label><span>${trade.exportAddtime}</span></li>
        </ul>
        </div>
        			
   				 <div class="bd_i_status">
		             <div class="bd_is_done">
			            <c:if test="${trade.totalAccrual ge 0}">
			            	<h3>收益率: <i style="color:#f00;">
							+<fmt:formatNumber value="${trade.totalAccrual/(trade.totalLeverMoney+trade.totalAppendLeverMoney)*100}"  type="number" maxFractionDigits="2"  ></fmt:formatNumber>%
						</c:if>	
						<c:if test="${trade.totalAccrual lt 0}">
							<h3>收益率: <i>
							<fmt:formatNumber value="${trade.totalAccrual/(trade.totalLeverMoney+trade.totalAppendLeverMoney)*100}"  type="number" maxFractionDigits="2"  ></fmt:formatNumber>%
						</c:if>
			            </i></h3>
			            <a href="javascript:void(0);">已终结</a>
			            <p>合买满标时间：${trade.auditPassTimeStr}</p>
       				 </div>
       			  </div>
       			    </div>
			</div>
			
			<div class="bd_box">
			    <h3><i></i><em>方案信息</em></h3>
			    <div class="bd_b_ctn">
			        <ul class="bd_infolist">
			            <li class="li1"><label>持仓规则：</label><p>${operatorsInfo }</p></li>
			            <li class="li2"><label>亏损补仓线：</label><p><fmt:formatNumber value="${trade.warning}"  type="number" maxFractionDigits="2"  ></fmt:formatNumber>元 ，当操盘资金低于补仓线时，系统提醒操盘手使用自有资金进行补仓，防止被平仓</p></li>
			            <li class="li3"><label>总操盘资金：</label><p><fmt:formatNumber value="${trade.totalOperateMoney}"  type="number" maxFractionDigits="2"  ></fmt:formatNumber>元 ，操盘者和合买者共同出的资金，入金到维胜分发的操盘账户中</p></li>
			            <li class="li4"><label>亏损平仓线：</label><p><fmt:formatNumber value="${trade.open}"  type="number" maxFractionDigits="2"  ></fmt:formatNumber>元 ，当操盘资金低于平仓线时，系统将强行平仓，保护合买者的利益</p></li>
			            <li class="li5"><label>申请操盘天数：</label><p>${trade.naturalDays}天</p></li>
			            <li class="li6"><label>开始操盘日期：</label><p>${trade.starttimeString}</p></li>
			        </ul>
			    </div>
			</div>
       		</c:otherwise>
			</c:choose>
		</c:if>
     	 <c:if test="${trade.status eq 1}">
      		  <!-- 操盘中 -->
      		  <ul class="bd_i_list2">
	     		<li><label>合买人数：</label><span>${number}人</span></li>
	            <li><label>已投金额：</label><span><fmt:formatNumber value="${trade.totalLending}"  type="number" maxFractionDigits="2"  ></fmt:formatNumber>元</span></li>
	            <li><label>合买进度：</label><span>操盘中</span></li>
	            <li><label>开始时间：</label><span>${trade.exportAddtime}</span></li>
             </ul>
       	 </div>
       	 <div class="bd_i_status">
             <div class="bd_is_over">
	            <h3>已操盘${trade.tradingDays}个交易日</h3>
	            <a href="javascript:void(0);">操盘中</a>
	            <p>合买满标时间：${trade.auditPassTimeStr}</p>
        	</div> 
        </div>
        </div> 
          </div>
		</div>
		
		<div class="bd_box">
		    <h3><i></i><em>方案信息</em></h3>
		    <div class="bd_b_ctn">
		        <ul class="bd_infolist">
		            <li class="li1"><label>持仓规则：</label><p>${operatorsInfo }</p></li>
		            <li class="li2"><label>亏损补仓线：</label><p><fmt:formatNumber value="${trade.warning}"  type="number" maxFractionDigits="2"  ></fmt:formatNumber>元 ，当操盘资金低于补仓线时，系统提醒操盘手使用自有资金进行补仓，防止被平仓</p></li>
		            <li class="li3"><label>总操盘资金：</label><p><fmt:formatNumber value="${trade.totalOperateMoney}"  type="number" maxFractionDigits="2"  ></fmt:formatNumber>元 ，操盘者和合买者共同出的资金，入金到维胜分发的操盘账户中</p></li>
		            <li class="li4"><label>亏损平仓线：</label><p><fmt:formatNumber value="${trade.open}"  type="number" maxFractionDigits="2"  ></fmt:formatNumber>元 ，当操盘资金低于平仓线时，系统将强行平仓，保护合买者的利益</p></li>
		            <li class="li5"><label>申请操盘天数：</label><p>${trade.naturalDays}天</p></li>
		            <li class="li6"><label>开始操盘日期：</label><p>${trade.starttimeString}</p></li>
		        </ul>
		    </div>
		</div>
      </c:if>
  
<div class="hd_box" style="display:none">
    <h3><i></i><em>合买者利益</em></h3>
</div>
<div class="hd_box">
    <h3><i></i><em>合买记录</em></h3>
    <div class="hd_b_ctn">
        <ul class="hd_r_list">
            <ol class="title">
                <li>合买者</li>
                <li>合买金额</li>
                <li>合买时间</li>
                <li>保底收益</li>
                <li>盈利分成</li>
                <li class="lastLi">结算时间</li>
            </ol>
            <c:if test="${number gt 0}">
            	<c:forEach  var="userInfo" items="${userList}" varStatus="user">
            		<c:choose>
               			<c:when test="${user.index % 2 == 0}">
               				<ol class="olDan">                
				                <li>${userInfo.uphone}</li>
				                <li><fmt:formatNumber value="${userInfo.money}"  type="number" maxFractionDigits="2"  ></fmt:formatNumber>元</li>
				                <li>${userInfo.buyTimeStr}</li>
				                <li class="no">仅合买者可见</li>
				                <li class="no">仅合买者可见</li>
				               <li class="lastLi">${userInfo.endTimeStr}</li>
				            </ol>
               			</c:when>
               			<c:otherwise>
               				<ol >                
				                <li>${userInfo.uphone}</li>
				                <li><fmt:formatNumber value="${userInfo.money}"  type="number" maxFractionDigits="2"  ></fmt:formatNumber>元</li>
				                <li>${userInfo.buyTimeStr}</li>
				                <li class="no">仅合买者可见</li>
				                <li class="no">仅合买者可见</li>
				                <li class="lastLi">${userInfo.endTimeStr}</li>
				            </ol>
               			</c:otherwise>
               		</c:choose>
            	</c:forEach>
            </c:if>
        </ul>
    </div>
</div>
<!-- DINGBU -->
</body>
<script type="text/javascript">
function lookContract(){
	var htmlHeight = 800;  //网页高度
	var htmlWidth = 1221;  //网页宽度
	var iTop = (window.screen.height-30-htmlHeight)/2; //获得窗口的垂直位置;  
	var iLeft = (window.screen.width-10-htmlWidth)/2;  //获得窗口的水平位置;  
	window.open(basepath+'static/together/together.html','实盘申请合作操盘协议','height='+htmlHeight+',innerHeight='+htmlHeight+',width='+htmlWidth+',innerWidth='+htmlWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizeable=no,location=no,status=no');  
}
</script>
</html>
    