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
<title>母账户编辑</title>
</head>
<body>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>
		<form method="post" id="addForm">
		<input type="hidden" id="accountID" name="id" value="${parentAccount.id}">
		<!-- missingMessage:'不能为空' 可以修改为空时的提示语 -->
					<table class="conn" border="0" width="100%" cellpadding="0" cellspacing="0"  class="conn" >
						<tr>
							<td  class="label right">账户名称:</td>
							<td>
								<input class="easyui-validatebox" value="${parentAccount.accountName}" id="accountName" value=""   name="accountName" data-options="required:true,validType:['length[2,8]']">
							</td>
						</tr>
						<tr>
							<td  class="label right">母账号交易通道:</td>
							<td>
								<input class="easyui-combobox" id="editAccountType" value="${parentAccount.accountGenre}" name="accountGenre" data-options="
										url:'${ctx}/admin/dataDic/getDicDatas?typeKey=accountGenre',
										valueField:'valueField',
										panelHeight:50,
										required:true,
										textField:'textField'">
							</td>
						</tr>
						<tr>
							<td  class="label right">账户编号:</td>
							<td>
								<input class="easyui-validatebox" value="${parentAccount.accountNo}" id="accountNo" value=""   name="accountNo" data-options="required:true,validType:['length[2,8]']">
							</td>
						</tr>
						
						<%-- <c:if test="${fromType=='add'}">
							<tr>
								<td>账户密码:</td>
								<td>
									<input class="easyui-validatebox" id="password" type="password" value=""   name="password" data-options="required:true,validType:'safepass'">
								</td>
							</tr>
						</c:if> --%>
						<tr>
							<td  class="label right">管理单元序号:</td>
							<td><input class="easyui-validatebox" value="${parentAccount.unitNumber}" id="unitNumber"  name="unitNumber" data-options="required:true"></td>
						</tr>
						
						<tr>
							<td  class="label right">配资倍数范围:</td>
							<td>
							<table class="many">
			                        <tr>
										<td><input  type="text" name="multipleStart" value="${parentAccount.multipleStart}" class="easyui-numberbox" precision="0" min="1" data-options="required:true" /></td>
										<td>至</td>
										<td><input  type="text" name="multipleEnd" value="${parentAccount.multipleEnd}" class="easyui-numberbox" precision="0" min="1" data-options="required:true" /></td>
		                            </tr>
	                            </table>
							
							</td>
						</tr>
						
						<tr>
							<td  class="label right">账户金额范围:</td>
							<td>
							<table class="many">
			                        <tr>
										<td><input  type="text" name="amountStart" value="${parentAccount.amountStart}" class="easyui-numberbox" precision="2" data-options="required:true" /></td>
										<td>至</td>
										<td><input  type="text" name="amountEnd" value="${parentAccount.amountEnd}" class="easyui-numberbox" precision="2" data-options="required:true" /></td>
		                            </tr>
	                            </table>
							
							</td>
						</tr>
						<tr>
							<td  class="label right">配资截止日期:</td>
							<td>
							   <input class="easyui-datebox" name="allocationDateStr" value="${allocationStr}" data-options="required:true">
							</td>
						</tr>
						<tr>
							<td  class="label right">新老用户抓取:</td>
							<td>
								<table class="many">
			                        <tr>
										<td>新用户</td>
										<td><input type="checkbox" value="1" <c:if test='${newAnd001 == 1}'> checked="checked" </c:if> name="newAndOldStateStr"></td>
										<td>老用户</td>
										<td><input type="checkbox" value="2" <c:if test='${newAnd002 == 1}'> checked="checked" </c:if> name="newAndOldStateStr"></td>
		                            </tr>
	                            </table>
							</td>
						</tr>
						<tr>
							<td  class="label right">账户优先级编号:</td>
							<td><input class="easyui-validatebox" value="${parentAccount.priorityNo}" id="priorityNo"  name="priorityNo" data-options="required:true"></td>
						</tr>
						<tr>
							<td  class="label right">账户类型:</td>
							<td>
								<input class="easyui-combobox" id="editAccountType" value="${parentAccount.accountType}" name="accountType" data-options="
										url:'${ctx}/admin/dataDic/getDicDatas?typeKey=parentAccountType',
										valueField:'valueField',
										panelHeight:100,
										required:true,
										textField:'textField'
										">
							</td>
						</tr>
						
						<tr>
							<td  class="label right">所属劵商:</td>
							<td>
								<input class="easyui-combobox" id="securitiesBusiness" value="${parentAccount.securitiesBusiness}" name="securitiesBusiness" data-options="
										url:'${ctx}/admin/dataDic/getDicDatas?typeKey=securitiesBusiness',
										valueField:'valueField',
										panelHeight:100,
										required:true,
										textField:'textField'">
							</td>
						</tr>
						
						<tr>
							<td class="label right">资金总额:</td>
							<td><input class="easyui-validatebox" value="<fmt:formatNumber value="${parentAccount.totalFunds}" pattern="##.##"/>" id="totalFunds"  name="totalFunds" data-options="required:true,validType:'money'"></td>
						</tr>
						<tr>
							<td class="label right">暂停抓取金额:</td>
							<td><input class="easyui-validatebox" value="<fmt:formatNumber value="${parentAccount.subFunds}" pattern="##.##"/>" id="subFunds"  name="subFunds" data-options="required:true,validType:'money'"></td>
						</tr>
						
						<%--
						<c:choose>
						
						<c:when test="${parentAccount.accountType=='A'}">
							<tr id="pstatus">
								<td class="label right">账户状态:</td>
								<td>
								<c:choose>
									<c:when test="${parentAccount.status==true}">
											<input type="radio" name="status" value="true" checked="checked" ><span>开启</span></input>
											<input type="radio" name="status" value="false"><span>关闭</span></input>
									</c:when>
									<c:otherwise>
										<input type="radio" name="status" value="true"><span>开启</span></input>
										<input type="radio" name="status" value="false"  checked="checked"><span>关闭</span></input>
									</c:otherwise>
								</c:choose>
								</td>	
							</tr>
						</c:when>
						
						<c:otherwise>
							<tr id="pstatus" style="display: none;">
								<td class="label right">账户状态:</td>
								<td>
									<input type="radio" name="status" value="true"  checked="checked"><span>开启</span></input>
									<input type="radio" name="status" value="false"><span>关闭</span></input>
								</td>	
							</tr>
						</c:otherwise>
						
						</c:choose>
						
						--%>
						
						<%--
						<c:choose>
						<c:when test="${parentAccount.accountType=='R'}">
							<tr id="pmultipleLimit">
								<td class="label right">配资倍数限制</td>
								<td>
									<select id="multipleLimit"  class="easyui-combobox" name="multipleLimit" style="width:176px;">
										   <c:forEach begin="1" end="15" step="1" var="mutiple"> 
										    	<c:choose>
										    		<c:when test="${mutiple==parentAccount.multipleLimit}">
										    			<option  value="${mutiple}" selected="selected">${mutiple}倍</option>
										    		</c:when>
										    		<c:otherwise>
										    		   	<option  value="${mutiple}">${mutiple}倍</option>
										    		</c:otherwise>
										    	</c:choose>
										   </c:forEach>
									</select>
								</td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr id="pmultipleLimit" style="display: none;">
									<td class="label right">配资倍数限制</td>
									<td>
										<select id="multipleLimit"  class="easyui-combobox" name="multipleLimit" style="width:160px;" value="${parentAccount.multipleLimit}">
											   <c:forEach begin="1" end="15" step="1" var="mutiple"> 
											    	<option  value="${mutiple}">${mutiple}倍</option>
											   </c:forEach>
										</select>
									</td>
								</tr>
						</c:otherwise>
						</c:choose>
						
						 --%>
						
						
						
						<tr>
								<td colspan="2" align="center">
								<c:choose>
									<c:when test="${fromType=='add'}">
										<a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-save'"  onclick="parentAccount.saveOrUpdate('admin/parentAccount/createSave')">保存</a>
									</c:when>
									<c:otherwise>
										<a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-edit'"  onclick="parentAccount.saveOrUpdate('admin/parentAccount/updateSave')">修改</a>
									</c:otherwise>
								</c:choose>
								</td>
						</tr>
					</table>
		</form>
</body>
</html>