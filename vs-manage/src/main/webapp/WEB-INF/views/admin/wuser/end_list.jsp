<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN" />
<!-- X-UA-Compatible 设置ie8/ie9 的兼容模式 -->
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>欠费待终结方案</title>
<%@include file="../../common/import-easyui-js.jspf"%>
<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>
<script type="text/javascript" src="${ctx}/static/script/wuser/end_list.js"></script>
<script type="text/javascript" src="${ctx}/static/script/userTrade/monitorList.js"></script>
<script type="text/javascript" src=${ctx}/static/script/common/dateUtils.js></script>
<script type="text/javascript">
	function changeToStr(value,row,index){
		return getFormatDateByLong(value,"yyyy-MM-dd");
	}
	
	function changeToStrs(value,row,index){
		return getFormatDateByLong(value,"yyyy-MM-dd hh:mm:ss");
	}
</script>



<style type="text/css">
    .frontImg {
        width: 200px;
    }
</style>

<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">

</head>
<body>
<shiro:hasPermission name="sys:riskmanager:arrearsEnd:view">
<div id="arrearsEndTab" class="easyui-tabs" data-options="tools:'#tab-tools',border:false,fit:true" style="margin-top: 5px;">
	
	<div id="y_jin" title="股票合买与随心配欠费方案" data-options="tools:'#p-tools'" style="padding:20px;">
		<div id="y_jin_toolbar">
	        <form id="y_jin_query_form" method="post">
		     <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
	            <tr>
	                <td class="label right">手机号:</td>
	                <td><input name="mobile" type="text" /></td>
	                <td class="label right">交易账号:</td>
	                <td><input name="account" type="text"  /></td>
	            </tr>
	            <tr>
	             	<td class="label right">
						<span>方案类型：</span>
					</td>
	                <!-- <td class="label right">买入状态：</td> -->
	                <td>
	                	<input class="easyui-combobox" id="activityType" name="search_EQ_activityType" data-options="valueField: 'id', textField:'text',
	                     url:'${ctx}/admin/component/dictCombobox?typeKey=activityType'">
						<%-- <input class="easyui-combobox" name="buyStatus" data-options="
										url:'${ctx}/admin/dataDic/getDicDatas?typeKey=limitStatus',
										valueField:'valueField',
										panelHeight:50,
										textField:'textField'"> --%>
	                </td>
	                <td class="label right">
						<!-- <span>方案类型：</span> -->
	                	<span></span>
					</td>
					<td>
						<a id="y_jin_btn" href="#" onclick="$.easyui.datagridQuery('y_jin_dg003','y_jin_query_form')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
					<%-- <input class="easyui-combobox" id="activityType" name="search_EQ_activityType" data-options="valueField: 'id', textField:'text',
	                     url:'${ctx}/admin/component/dictCombobox?typeKey=activityType'"> --%>
					</td>
	            </tr>
	           <!--  <tr>
	                <td class="label">&nbsp;</td>
	                <td class="label" colspan="3">
	                	<a id="y_jin_btn" href="#" onclick="$.easyui.datagridQuery('y_jin_dg003','y_jin_query_form')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
	                </td>
	            </tr> -->
	          </table>
	        </form>
			<shiro:hasPermission name="sys:riskmanager:arrearsEnd:end"> 
		     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="endSolation('y_jin_dg003', 'endSolationReview')">终结方案</a>
	   		</shiro:hasPermission>
	   		
	   		 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openMoneyDetail('y_jin_dg003')">查询欠费明细</a>
	    </div>
	    <table id="y_jin_dg003" class="easyui-datagrid" width="100%" 
	        toolbar="#y_jin_toolbar" url="${ctx}/admin/arrearsEnd/endList?type=2" pagination="true"
	            rownumbers="true" fitColumns="true" singleSelect="true"> 
	        <thead>
	            <tr>
	                <th field="groupId" data-options="checkbox:true"></th>
	                <th field="tname" width="150" sortable="true">用户姓名 </th>
					<th field="mobile" width="150" sortable="true">手机号 </th>
					<th field="account" width="150" sortable="true">交易账号</th>
					<th field="accountName" width="150" sortable="true">交易账户名</th>
					<th field="programNo" width="150" sortable="true">方案号 </th>
					<th field="avlBal" width="150" sortable="true">平台余额</th>
					<th field="money" width="150" sortable="true">欠费金额</th>
					<th field="addtimeStr" width="150" sortable="true">欠费日期 </th>
					<!-- <th field="buyStatusStr" width="150" sortable="true">买入状态</th>
					<th field="sellStatusStr" width="150" sortable="true">卖出状态</th> -->
					<th field="activityTypeStr" width="150" sortable="true">方案类型</th>
	            </tr>
	       </thead>
	    </table>
	</div>
	
  	<div id="q_jiang" title="月月配即将到期方案" data-options="tools:'#p-tools'" style="padding:20px;">
	    <div id="q_jiang_toolbar">
	        <form id="queryForm" action="${ctx}/admin/arrearsEnd/monthNearEndValue" method="post">
		     <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
	            <tr>
	                <td class="label right">手机号：</td>
	                <td><input name="mobile" type="text" /></td>
	                <td class="label right">交易账号：</td>
	                <td><input name="account" type="text"  /></td>
	            </tr>
	            <tr>
	                <td class="label right">剩余天数：</td>
	                 <td >
						<input name="surplusDays" type="text"  />
	                 </td>
	                <!--  <td class="label right">结束时间：</td>
	                 <td >
						 <table class="many">
	                        <tr>
	                            <td><input class="easyui-datebox" name="estimateEndtime_start"></td>
	                            <td>至</td>
	                            <td><input class="easyui-datebox" name="estimateEndtime_end"></td>
	                        </tr>
	                    </table> -->
	                 </td>
	                  <td class="label">&nbsp;</td>
	                <td class="label" colspan="3"><a id="btn" href="#" onclick="$.easyui.datagridQuery('dg003','queryForm')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a></td>
	            </tr>
	           
	          </table>
	        </form>
	       
			<shiro:hasPermission name="sys:riskmanager:arrearsEnd:end"> 
		     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="endSolation('dg003', 'endSolationReview')">终结方案</a>
	   		</shiro:hasPermission>
	   		<shiro:hasPermission name="sys:riskmanager:arrearsEnd:cancelLimit">
	   		 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="changetype('dg003')">限买已解除</a>
	   		</shiro:hasPermission>
	    </div>
	    <table id="dg003" class="easyui-datagrid" width="100%" 
	        toolbar="#q_jiang_toolbar" url="${ctx}/admin/arrearsEnd/monthNearEndValue" pagination="true"
	            rownumbers="true" fitColumns="true"  fit="true" singleSelect="true"> 
	        <thead>
	            <tr>
	                <th field="tradeId" data-options="checkbox:true"></th>
	                <th field="isManualDelay" hidden="true"></th>
	                <th field="userName" width="150" sortable="true">用户姓名 </th>
					<th field="mobile" width="150" sortable="true">手机号 </th>
					<th field="account" width="150" sortable="true">交易账号</th>
					<th field="accountName" width="150" sortable="true">交易帐户名</th>
					<th field="groupId" width="150" sortable="true">方案号 </th>
					<th field="balance" width="150" sortable="true">平台余额</th>
					<th field="startTime" width="150" sortable="true" formatter="changeToStr" >开始时间</th>
					<th field="tradeMonth" width="150" sortable="true">方案时长 </th>
					<th field="prolongMonth" width="150" sortable="true">方案延长时间</th>
					<th field="surplusDays" width="150" sortable="true">剩余天数</th>
					<th field="estimateEndtime" width="150" sortable="true" formatter="changeToStr" >方案结束日期</th>
					
	            </tr>
	       </thead>
	    </table>
	</div> 
	
	 <div id="ths_jin" title="月月配已到期方案" data-options="tools:'#p-tools'" style="padding:20px;">
		<div id="ths_jin_toolbar">
	        <form id="ths_jin_query_form" method="post">
		     <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
	            <tr>
	                <td class="label right">手机号:</td>
	                <td><input name="mobile" type="text" /></td>
	                <td class="label right">交易账号：:</td>
	                <td><input name="account" type="text"  /></td>
	            </tr>
	            <tr>
	            
	            <td class="label right">结束时间：</td>
	            	 <td><input class="easyui-datebox" name="estimateEndtime_end"></td>
	                <td class="label">&nbsp;</td>
	                <td class="label" colspan="3"><a id="y_jin_btn" href="#" onclick="$.easyui.datagridQuery('ths_jin_dg003','ths_jin_query_form')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a></td>
	            	
	            </tr>
	          </table>
	        </form>
			<shiro:hasPermission name="sys:riskmanager:arrearsEnd:end"> 
		     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="endSolation('ths_jin_dg003', 'endSolationReview')">终结方案</a>
	   		</shiro:hasPermission>
	   		<shiro:hasPermission name="sys:riskmanager:arrearsEnd:cancelLimit">
	   		 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="changetype('ths_jin_dg003')">限买已解除</a>
	   		 </shiro:hasPermission>
	    </div>
	    <table id="ths_jin_dg003" class="easyui-datagrid" width="100%" 
	        toolbar="#ths_jin_toolbar" url="${ctx}/admin/arrearsEnd/monthEndValue" pagination="true"
	            rownumbers="true" fitColumns="true" singleSelect="true" fit="true" > 
	        <thead>
	            <tr>
	               <th field="tradeId" data-options="checkbox:true"></th>
	                <th field="isManualDelay" hidden="true"></th>
	                <th field="userName" width="150" sortable="true">用户姓名 </th>
					<th field="mobile" width="150" sortable="true">手机号 </th>
					<th field="account" width="150" sortable="true">交易账号</th>
					<th field="accountName" width="150" sortable="true">交易帐户名</th>
					<th field="groupId" width="150" sortable="true">方案号 </th>
					<th field="balance" width="150" sortable="true">平台余额</th>
					<th field="startTime" width="150" sortable="true" formatter="changeToStr">开始时间</th>
					<th field="tradeMonth" width="150" sortable="true">方案时长 </th>
					<th field="prolongMonth" width="150" sortable="true">方案延长时间</th>
					<th field="surplusDays" width="150" sortable="true">剩余天数</th>
					<th field="estimateEndtime" width="150" sortable="true" formatter="changeToStr">方案结束日期</th>
	            </tr>
	       </thead>
	    </table>
	</div> 
	
</div>
</shiro:hasPermission>
	<shiro:lacksPermission name="sys:riskmanager:arrearsEnd:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
	
	<div id="moneyDetail" class="easyui-window" title="欠费明细" data-options="iconCls:'icon-save',modal:true,closed:true" style="width:800px;height:400px;">
           <form id="queryFormDetail" method="post">
             <input type="hidden" name="groupId" id="groupId" />
             <a id="detailBtn" style="display:none;" href="#" onclick="$.easyui.datagridQuery('dgdetail','queryFormDetail')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
            </form>
	   <table id="dgdetail" class="easyui-datagrid" width="100%" style="height:auto;"
             url="${ctx}/admin/arrearsEnd/detail" pagination="false"
            rownumbers="true" fitColumns="true" singleSelect="true">
	        <thead>
	            <tr>
					<th field="time" width="150" sortable="true">欠费时间 </th>
					<th field="money" width="150">欠费金额</th>
	            </tr>
	        </thead>
       </table>       
        
    </div>
    <%--
	<div id="settlement_scheme" class="easyui-window" title="结算方案" data-options="iconCls:'icon-edit',width:400,height:250,modal:true,closed:true,minimizable:false,maximizable:false,collapsible:false,loadingMessage:'正在加载,请等待......'" style="padding:-5px;top: 20px;">
		<div  style="padding:20px 20px">
		    <form id="y_jin_query_form_detail" method="post">
				<table style="margin: auto;">
		    		<input type="hidden" name="groupId" id="groupId2"/>
		    		<tr>
		    			<td>方案结算金额:</td>
		    			<td><input class="easyui-numberbox" type="text" name="money" id="money"  data-options="required:true,precision:2,min:1,groupSeparator:',',decimalSeparator:','"/></td>
		    		</tr>
			   	</table>
		    </form>
		</div>
	    <div style="text-align:center;padding:5px">
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeSettlementScheme()">提交</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#settlement_scheme').window('close')">取消</a>
	    </div>
    </div>
     --%>
</body>
</html>