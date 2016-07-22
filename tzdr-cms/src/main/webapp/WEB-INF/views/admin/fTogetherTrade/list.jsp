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
<title>期货合买方案管理</title>
<%@include file="../../common/import-easyui-js.jspf"%>
<%@include file="../../common/import-fileupload-js.jspf"%>
<script type="text/javascript"
	src="${ctx}/static/script/wuser/public.js"></script>
<script type="text/javascript"
	src="${ctx}/static/script/common/dateUtils.js"></script>
<script type="text/javascript"
	src="${ctx}/static/plugins/my97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/css/dataStyle.css">
<script type="text/javascript"
	src="${ctx}/static/script/fTogetherTrade/list.js"></script>
</head>
<body>
	<shiro:hasPermission name="sys:riskmanager:fTogetherTrade:view">
		<div id="proxy" style="padding: 1px; height: auto;">
		<table id="dg003" class="easyui-datagrid" toolbar="#dg003Toolbar"
				url="${ctx}/admin/fTogetherTrade/getDatas" pagination="true"
				rownumbers="true" fitColumns="true" singleSelect="true" remoteSort="true" >
				<thead>
					<tr>
						<th field="id" data-options="checkbox:true"></th>
						<th field="typeName" hidden="true">typeName</th>
						<th field="configId" hidden="true">ee</th>
						<th field="species" hidden="true"></th>
						<th field="openTimeValue" hidden="true"></th>
						
						<th field="tradeNo" width="150">方案号</th>
						
						<th field="name" width="150">合约方案</th>
						
						<th field="contract" width="150">操盘合约</th>
						<th field="openTime" width="150" sortable="true" formatter="changeToStrs">开仓时间</th>
						<th field="operateTime" width="150">操盘时间</th>
						<th field="callNo" width="150">看涨份数</th>
						<th field="callFullNo" width="150" formatter="changeToColor">看涨满单</th>
						<th field="putNo" width="150">看跌份数</th>
						<th field="putFullNo" width="150" formatter="changeToColor">看跌满单</th>
						<th field="callOpenPoint" width="150">看涨开仓点数</th>
						<th field="putOpenPoint" width="150">看跌开仓点数</th>
						<th field="callClosePoint" width="150">看涨平仓点数</th>
						<th field="putClosePoint" width="150">看跌平仓点数</th>
						<th field="addTime" width="150" sortable="true"
							formatter="changeToStr">发布时间</th>
						<th field="status" width="150" formatter="changeToType">合买状态</th>
					</tr>
				</thead>
			</table>
			<div id="dg003Toolbar">
				<shiro:hasPermission name="sys:riskmanager:fTogetherTrade:delete">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						 onclick="deleteTrade()">删除</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="sys:riskmanager:fTogetherTrade:create">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						 onclick="addTradeOpen()">新增</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="sys:riskmanager:fTogetherTrade:update">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						 onclick="updateTradeOpen()">修改</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="sys:riskmanager:fTogetherTrade:view">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						 onclick="showValue()">查看方案详情</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="sys:riskmanager:fTogetherTrade:upRecord">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						 onclick="arrearsEndOptions.openDetail(1)">看涨合买记录</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="sys:riskmanager:fTogetherTrade:downRecord">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						 onclick="arrearsEndOptions.openDetail(2)">看跌合买记录</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="sys:riskmanager:fTogetherTrade:writePoint">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						 onclick="markeOpen()">录入行情点位</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="sys:riskmanager:fTogetherTrade:end">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						 onclick="instantRefund()">立即退款</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="sys:riskmanager:fTogetherTrade:end">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						 onclick="instantSettle()">立即结算</a>
				</shiro:hasPermission>
			</div>
		</div>

		<div id="addTrade" class="easyui-window" title="新增方案"
			style="width: 350px; height: 390px; display: none; border: none; overflow: hidden;"
			data-options="iconCls:'icon-save',modal:true,closed:true">
			<form id="voForm">
				<table border="0" style="font-size: 12px;" class="conn" width="100%"
					cellpadding="0" cellspacing="0">
					<tr>
						<td class="label right">合买品种:</td>
						<td>
							<select  id="togetherTradeType"  name="configID"  style="width:170px;">
								<c:forEach var="config" items="${configs}" varStatus="status">
									<option value="${config.id}">${config.typeName} </option>
								</c:forEach>
							</select>
						</td>
						<td><span></span></td>
					</tr>
					<tr>
						<td class="label right">合买方案:</td>
						<td><input name="name" class="easyui-validatebox"
							id="addName" data-options="required:true" type="text" /></td>
						<td><span></span></td>
					</tr>
					<tr>
						<td class="label right">开仓时间:</td>
						<td><input class="easyui-datetimebox"
							data-options="required:true,showSeconds:false" id="addOpenTime" name="openTime"
							type="text" /></td>
						<td></td>
					</tr>
					<tr>
						<td class="label right">操盘时间:</td>
						<td><input name="operateTime" class="easyui-validatebox"
							id="addOperateTime" data-options="required:true" type="text" >备注：分钟</input>
						</td>
						<td><span></span></td>
					</tr>
					<tr>
						<td align="center" colspan="3"><a id="btn" href="#"
							onclick="addTradeSave()" class="easyui-linkbutton">提交</a> <a
							id="btn" href="#" onclick="addTradeClose()"
							class="easyui-linkbutton">取消</a></td>
					</tr>
				</table>
			</form>
		</div>

		<div id="updateTrade" class="easyui-window" title="修改方案"
			style="width: 350px; height: 390px; display: none; border: none; overflow: hidden;"
			data-options="iconCls:'icon-save',modal:true,closed:true">
			<form id="voUpdateForm">
				<table border="0" style="font-size: 12px;" class="conn" width="100%"
					cellpadding="0" cellspacing="0">
					<tr>
						<td class="label right">合买品种:</td>
						<td>
							<input type="hidden" name="id" id="updateId">
							<span id="typeName"></span>
						</td>
						<td><span></span></td>
					</tr>
					<tr>
						<td class="label right">合买方案:</td>
						<td><input name="name" class="easyui-validatebox"
							id="updateName" data-options="required:true" type="text" /></td>
						<td><span></span></td>
					</tr>
					<tr>
						<td class="label right">开仓时间:</td>
						<td><input class="easyui-datetimebox"
							data-options="required:true" id="updateOpenTime" name="openTime"
							type="text" /></td>
						<td></td>
					</tr>
					<tr>
						<td class="label right">操盘时间:</td>
						<td><input name="operateTime" class="easyui-validatebox"
							id="updateOperateTime" data-options="required:true" type="text" >备注：分钟</input>
						</td>
						<td><span></span></td>
					</tr>
					<tr>
						<td align="center" colspan="3"><a id="btn" href="#"
							onclick="updateTradeSave()" class="easyui-linkbutton">提交</a> <a
							id="btn" href="#" onclick="updateTradeClose()"
							class="easyui-linkbutton">取消</a></td>
					</tr>
				</table>
			</form>
		</div>



      <div id="tradeValue" class="easyui-window" title="方案详情"
			style="width: 350px; height: 390px; display: none; border: none; overflow: hidden;"
			data-options="iconCls:'icon-save',modal:true,closed:true">
				<table border="0" style="font-size: 12px;" class="conn" width="100%"
					cellpadding="0" cellspacing="0">
					<tr>
						<td class="label right">合买品种:</td>
						<td>
						<input class="easyui-validatebox"
							id="t_scope" data-options="required:true" type="text" readOnly="true" />
							
						</td>
						<td><span></span></td>
					</tr>
					<tr>
						<td class="label right">操盘合约:</td>
						<td><input  class="easyui-validatebox"
							id="t_contract" data-options="required:true" type="text" readOnly="true"  /></td>
						<td><span></span></td>
					</tr>
					<tr>
						<td class="label right">开仓时间:</td>
						<td><input class="easyui-validatebox"
							data-options="required:true" id="t_openTime"
							type="text"  readOnly="true"  /></td>
						<td></td>
					</tr>
					<tr>
						<td class="label right">平仓时间:</td>
						<td><input  class="easyui-validatebox"
							id="t_endTime" data-options="required:true" type="text" readOnly="true"   />
						</td>
						<td><span></span></td>
					</tr>
					<tr>
						<td class="label right">合买价格:</td>
						<td><input  class="easyui-validatebox"
							id="t_price" data-options="required:true" type="text" readOnly="true"  />
						</td>
						<td><span></span></td>
					</tr>
					<tr>
						<td class="label right">合买规则:</td>
						<td><input  class="easyui-validatebox"
							id="t_fullNum" data-options="required:true" type="text" readOnly="true"  />
						</td>
						<td><span></span></td>
					</tr>
					<tr>
						<td class="label right">止盈点数:</td>
						<td><input  class="easyui-validatebox"
							id="t_stopProfitPoint" data-options="required:true" type="text"  readOnly="true" />
						</td>
						<td><span></span></td>
					</tr>
					<tr>
						<td class="label right">止亏点数:</td>
						<td><input  class="easyui-validatebox"
							id="t_stopLossPoint" data-options="required:true" type="text" readOnly="true"  />
						</td>
						<td><span></span></td>
					</tr>
					<tr>
						<td class="label right">盈利结算:</td>
						<td><input  class="easyui-validatebox"
							id="t_profitFee" style="width: 250px"  type="text" readOnly="true"  />
						</td>
						<td><span></span></td>
					</tr>
					<tr>
						<td class="label right">亏损结算:</td>
						<td><input  class="easyui-validatebox"
							id="t_lossFee" style="width: 250px" type="text" readOnly="true"  />
						</td>
						<td><span></span></td>
					</tr>
				</table>
		</div>
		
	
	
	<div id="Detail" class="easyui-window" title="合买记录"
		data-options="iconCls:'icon-save',modal:true,closed:true"
		style="width: 500px; height: 400px;">
		<form id="queryFormDetail" method="post">
			<input type="hidden" name="search_EQ_tradeId"
				id="search_EQ_Id" />
			<input type="hidden" name="search_EQ_direction"
				id="search_EQ_direction" />	
				 <a id="detailBtn"
				style="display: none;" href="javascript:void(0)"
				onclick="datagridUtils.datagridQuery('dgdetail','queryFormDetail')"
				class="easyui-linkbutton"
				data-options="iconCls:'icon-search'">查询</a>
		</form>
		<table id="dgdetail" width="100%"></table>
	</div>
	
	<div id="tradeMarket" class="easyui-window" title="录入行情点位"
		 style="width: 820px; height: 390px; display: none; border: none; overflow: hidden;"
		 data-options="iconCls:'icon-save',modal:true,closed:true">
		<form id="detailValueForm">
			<table border="0" style="font-size: 12px;" class="conn" width="100%"
				   cellpadding="0" cellspacing="0">
				<tr id="detail_append_1">
					<td class="label right">看涨开仓点位:</td>
					<td>
						<input name="callOpenPoint" class="easyui-validatebox"
							   id="callOpenPoint"  type="text" />
					</td>
					<td><span></span></td>
					<td class="label right">看跌开仓点位:</td>
					<td><input name="putOpenPoint" class="easyui-validatebox"
							   id="putOpenPoint"  type="text" /></td>
					<td><span></span></td>
				</tr>
				
				<tr>
				<td class="label right">看涨平仓点位:</td>
					<td><input class="easyui-validatebox"
							   id="callClosePoint" name="callClosePoint"
							   type="text" /></td>
					<td></td>
					<td class="label right">看跌平仓点位:</td>
					<td><input name="putClosePoint" class="easyui-validatebox"
							   id="putClosePoint"  type="text" />
					</td>
					<td><span></span></td>
				</tr>
				<tr>
					<td align="center" colspan="6  "><a id="btn" href="#"
													  onclick="marketSave()" class="easyui-linkbutton">提交</a> <a
							id="btn" href="#" onclick="marketClose()"
							class="easyui-linkbutton">取消</a></td>
				</tr>
			</table>
		</form>
	</div>
	
	</shiro:hasPermission>
	<shiro:lacksPermission name="sys:riskmanager:fTogetherTrade:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>