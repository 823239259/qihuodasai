<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@page import="java.util.*" %>
   <%@page import="java.text.*" %>
 <%
 	Calendar calendar = Calendar.getInstance(); 
 	Calendar maxcalendar = Calendar.getInstance();  
 	calendar.add(Calendar.YEAR, -90);  
 	maxcalendar.add(maxcalendar.YEAR, -15);  
    Date mindate = calendar.getTime(); 
    Date maxdate = maxcalendar.getTime(); 
    String min = (new SimpleDateFormat("yyyy")).format(mindate);  
    min+="-12-31";
    String max = (new SimpleDateFormat("yyyy")).format(maxdate); 
    max+="-12-31";
 %>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-fileupload-js.jspf"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../common/commonkeyword.jsp"%>
<link rel="stylesheet" href="${ctx}/static/activies/css/sp.css"/>
<link rel="stylesheet" href="${ctx}/static/css/tzdr.css"/>
 <script src="${ctx}/static/script/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
 <script src="${ctx}/static/script/questionnaire/questionnaire.js" type="text/javascript"></script>
 <script src="${ctx}/static/script/tzdr.js" type="text/javascript"></script>
<script src="${ctx}/static/script/common/tzdr.user.js?version=20150720" type="text/javascript"></script>		
<script src="${ctx}/static/script/jquery.validate.min.js" type="text/javascript"></script>		
		
	
</head>
<body>
<form  id="questionFrom" name="questionFrom" method="post" action="${ctx}/telphone">
<input hidden="id" id="id" name="id">
<div class="question">
    <div class="qbanner1"></div>
    <div class="qbanner2"></div>
   
    <div class="qstep">
        <img src="${ctx }/static/activies/images/qtitle_02.PNG">
    </div>
    <div class="qs_bg">
        <div class="qs_main">
            <div class="qs_m_bg1"></div>
            <div class="qs_m_bg2"></div>
            <h2>填写调查问卷，抢VIP账号，免费领<i>8800</i>炒股红包!<br>数量有限，先到先得！</h2>
            <h3>个人信息填写<i></i></h3>

            <ul class="qs_info">
                <li>
                <label>姓&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp名：</label>
                <input type="text"  name="name" id="name" class="qs_i_ip"/>
                <label>性&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp别：</label>
                <input type="radio" name="sex" value="1" ><i>男</i>
            	<input type="radio" name="sex" value="2" ><i>女</i>
                </li>
                <li>
                    <label>生&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp日：</label>
                     <input type="text" name="birthday" id="birthday" onchange="showage();" onclick="WdatePicker({minDate:'<%=min %>',maxDate:'<%=max %>'});" class="qs_i_ip">
                    <span>年龄：<span id="agenum"></span></span>
                    <input type="hidden" id="age" name="age">
                </li>
                <li><label>居&nbsp住&nbsp&nbsp地：</label>
                <select class="qs_i_sel" id="place" name="place" onchange="getcitydata();">
                   <option value="">请选择</option>
                    <c:forEach items="${place}"  var="province" >
                    	<option value="${province.id}">${province.title }</option>
		            </c:forEach>
		          </select>
                <select class="qs_i_sel" id="city" name="city">
                	
                </select>
                <input type="text" class="qs_i_ip" />
                </li>
                <li><label>职&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp业：</label>
                    <select class="qs_i_sel2" name="occupation" id="occupation">
                        <option value="1">企业主</option>
            			<option value="2">在校学生</option>
            			<option value="3">固定工作者</option>
            			<option value="4">自由职业</option>
            			<option value="6">其他</option>
                    </select>
                </li>
                <li>
                    <label>年&nbsp收&nbsp&nbsp入：</label>
                        <select class="qs_i_sel2" name="income" id="income">
                            <option value="0">5W以下</option>
                            <option value="1">5W-10W</option>
                            <option value="2">10W-20W</option>
                            <option value="3">20W-50W</option>
                            <option value="4">50W-100W</option>
                            <option value="5">100W以上</option>
                        </select>
                    <label style="margin-left:20px;">股&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp龄：</label>
                    <select class="qs_i_sel2" name="stockyear" id="stockyear">
                        <option value="0">1年以下</option>
                        <option  value="1">1年-3年</option>
                        <option  value="2">3年-5年</option>
                        <option  value="3">5年-10年</option>
                        <option  value="4">10年以上</option>
                    </select>
                </li>

                <li>
                    <label>联系电话：</label>
                    <input type="text" name="mobile" id="mobile" maxlength="11" onKeyUp="javascript:clearNoNumber(event,this)"  class="qs_i_ip">
                    <em class="qs_telp_right" style="display:none;"></em>
                    <em class="qs_telp_error" style="display:none">您的账号已存在，请立即<a href="${ctx}/login">登录</a></em>

                </li>
                <li><span><i>*</i>同一个号码仅能申请一个账号</span></li>
            </ul>
            <h3>问题<i></i></h3>
            <div class="qs_info">
                <div class="qs_i_qstitle">
                    <img src="${ctx }/static/activies/images/1.PNG">
                    <em>01</em>
                    <span>您从什么渠道了解到我们的活动？</span>
                    <i></i>
                </div>
                <div class="qs_i_qssel">
                    <label>答案选择：</label>
                    <input type="radio" name="question1" value="1"><i>亲友介绍</i>
                <input type="radio" name="question1" value="2"><i>网络新闻</i>
                <input type="radio" name="question1" value="3"><i>网络广告</i>
                <input type="radio" name="question1" value="4"><i>QQ群</i>
                <input type="radio" name="question1" value="5"><i>论坛</i>
                <input type="radio" name="question1" value="6"><i>微信微博</i>
                <input type="radio" name="question1" value="7"><i>搜索引擎</i>
                </div>
                <div class="qs_i_qstitle">
                    <img src="${ctx }/static/activies/images/2.PNG">
                    <em></em>
                    <span>您从什么渠道了解到股票操盘产品？</span>
                    <i></i>
                </div>
                <div class="qs_i_qssel">
                    <label>答案选择：</label>
                    <input type="radio" name="question2" value="1"><i>亲友介绍</i>
	                <input type="radio" name="question2" value="2"><i>网络新闻</i>
	                <input type="radio" name="question2" value="3"><i>网络广告</i>
	                <input type="radio" name="question2" value="4"><i>QQ群</i>
	                <input type="radio" name="question2" value="5"><i>论坛</i>
	                <input type="radio" name="question2" value="6"><i>微信微博</i>
	                <input type="radio" name="question2" value="7"><i>搜索引擎</i>
                </div>
                <div class="qs_i_qstitle">
                    <img src="${ctx }/static/activies/images/3.PNG">
                    <em></em>
                    <span>您所使用的网络理财产品有哪些？（多选）</span>
                    <i></i>
                </div>
                <div class="qs_i_qssel">
                    <label>答案选择：</label>
                    <input type="hidden" name="question3" id="question3">
                <input type="checkbox" name="question03"  value="1"><i>无</i>
                <input type="checkbox" name="question03"  value="2"><i>余额宝</i>
                <input type="checkbox" name="question03"  value="3"><i>P2P</i>
                <input type="checkbox" name="question03"  value="4"><i>银行理财</i>
                <input type="checkbox" name="question03"  value="5"><i>信托</i>
                <input type="checkbox" name="question03"  value="6"><i>基金</i>
                </div>
                <div class="qs_i_qstitle">
                    <img src="${ctx }/static/activies/images/4.PNG">
                    <em></em>
                    <span>对于股票操盘网站的选择您最看重的是？</span>
                    <i></i>
                </div>
                <div class="qs_i_qssel">
                    <label>答案选择：</label>
                      <input type="radio" name="question4" value="1"><i>资金安全</i>
	                <input type="radio" name="question4" value="2"><i>风险控制</i>
	                <input type="radio" name="question4" value="3"><i>客服服务</i>
	                <input type="radio" name="question4" value="4"><i>网站资金实力</i>
	                <input type="radio" name="question4" value="5"><i>高收益</i>
                </div>
                <div class="qs_i_qstitle">
                    <img src="${ctx }/static/activies/images/5.PNG">
                    <em></em>
                    <span>您期望申请的最大操盘金额为多少？</span>
                    <i></i>
                </div>
                <div class="qs_i_qssel">
                    <label>答案选择：</label>
                   <input type="radio" name="question5" value="1"><i>1万一下</i>
                <input type="radio" name="question5" value="2"><i>1万-10万</i>
                <input type="radio" name="question5" value="3"><i>10万-50万</i>
                <input type="radio" name="question5" value="4"><i>50万-100万</i>
                </div>
                <div class="qs_i_qstitle">
                    <img src="${ctx }/static/activies/images/6.PNG">
                    <em></em>
                    <span>您使用股票操盘产品的情况？</span>
                    <i></i>
                </div>
                <div class="qs_i_qssel">
                    <label>答案选择：</label>
                       <input type="radio"  name="question6" value="1"><i>从未使用</i>
                <input type="radio"  name="question6" value="2"><i>首次使用</i>
                <input type="radio"  name="question6" value="3"><i>偶尔使用</i>
                <input type="radio"  name="question6" value="4"><i>长期使用</i>
                </div>
                <div class="qs_i_qstitle">
                    <img src="${ctx }/static/activies/images/7.PNG">
                    <em></em>
                    <span>您是否会主动向您的亲友推荐维胜股票操盘产品？</span>
                    <i></i>
                </div>
                <div class="qs_i_qssel">
                    <label>答案选择：</label>
                 <input type="radio"  name="question7" value="1"><i>是</i>
                <input type="radio" name="question7" value="2"><i>否</i>
                <input type="radio" name="question7" value="3"><i>不一定</i>
                </div>
            </div>
           
        </div>


    </div>
</div>
</form>

<!-- 公司简介 -->
<!-- DINGBU -->
<%@ include file="../common/dsp.jsp"%>
</body>
</html>
