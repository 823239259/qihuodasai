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
<%@include file="../../common/import-easyui-js.jspf"%>
<script type="text/javascript" src="${ctx}/static/script/recharge/editRechargeAdditional.js"></script>
<title>补录充值表单</title>
</head>
<body>
<input type="hidden" id="tempsource" value="${additionalVo.source}">
		<form method="post" id="addForm" style="padding-left: 22%;">
		<input type="hidden" name="id" value="${additionalVo.id}">
		<input type="hidden" name="rechargeId" value="${additionalVo.rechargeId}">
		<!-- missingMessage:'不能为空' 可以修改为空时的提示语 -->
					<table class="formTable">
					<tr>
								<td>表单类型</td>
								<td>
									<select id="retype"  class="easyui-combobox" name="type" style="width:176px;"
									 data-options="onChange:function (n,o){
									 	editRecharge.changeType(n);
									 }">
								    	<c:choose>
								    		<c:when test="${additionalVo.type==3}">
								    			<option  value="3" selected="selected">支付宝充值</option>
								    			<option  value="4">银行转账充值</option>
								    		</c:when>
								    		<c:otherwise>
								    		   	<option  value="3">支付宝充值</option>
								    			<option  value="4"  selected="selected">银行转账充值</option>
								    		</c:otherwise>
								    	</c:choose>
									</select>
								</td>
					</tr>
							
						<tr id="trmobile">
							<td>手机号:</td>
							<td>
								<input class="easyui-validatebox" value="${additionalVo.mobile}" id="mobile"    name="mobile" data-options="required:true,validType:['mobile']">
							</td>
						</tr>
						
						<tr id="tralipayNo">
							<td>支付宝帐号:</td>
							<td>
								<input class="easyui-validatebox" value="${additionalVo.alipayNo}" id="alipayNo" name="alipayNo" data-options="required:true">
							</td>
						</tr>
						<tr id="trtradeAccount">
							<td>收款银行:</td>
							<td>
								<input class="easyui-combobox" id="tradeAccount" value="${additionalVo.tradeAccount}" name="tradeAccount" data-options="
										url:'${ctx}/admin/recharge/dataMapCombobox?key=bankname&includes=ccb,abc,boc,cmb,icbc',
										valueField:'id',
										panelHeight:100,
										required:true,
										textField:'text'">
							</td>
						</tr>
						<tr id="trbankCard">
							<td>银行卡号:</td>
							<td><input class="easyui-validatebox" value="${additionalVo.bankCard}" id="bankCard"  name="bankCard" data-options="required:true"></td>
						</tr>
						
						<tr>
							<td>充值金额:</td>
							<td><input class="easyui-validatebox" value="<fmt:formatNumber value="${additionalVo.money}" pattern="##.##"/>" id="money"  name="money" data-options="required:true,validType:'money'"></td>
						</tr>
						<tr>
							<td>交易号:</td>
							<td><input class="easyui-validatebox" value="${additionalVo.tradeNo}"  id="tradeNo"  name="tradeNo"></td>
						</tr>
						<tr>
							<td>来源网站：</td>
							<td>
								<select class="easyui-combobox" name="source" id="source"  data-options="panelHeight:50">
			                        <option value="1">维胜</option>
			                        <option value="2">配股宝</option>
			                    </select>
							</td>
						</tr>
						<tr>
								<td colspan="2" align="center">
								<c:choose>
									<c:when test="${fromType=='add'}">
										<a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-save'"  onclick="baseUtils.saveOrUpdate('admin/rechargeAdditional/saveInfo', 'iframe')">保存</a>
									</c:when>
									<c:otherwise>
										<a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-edit'"  onclick="baseUtils.saveOrUpdate('admin/rechargeAdditional/updateInfo', 'iframe')">修改</a>
									</c:otherwise>
								</c:choose>
								</td>
						</tr>
					</table>
		</form>
</body>
</html>