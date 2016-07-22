<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Language" content="zh-CN" />
<!-- X-UA-Compatible 设置ie8/ie9 的兼容模式 -->
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>优惠券管理</title>
<%@include file="../../common/import-easyui-js.jspf"%>
<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>
<script type="text/javascript" src="${ctx}/static/script/common/dateUtils.js"></script>
<script type="text/javascript" src="${ctx}/static/script/Future/list.js?v=20160302"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
<script type="text/javascript" src="${ctx}/static/plugins/my97DatePicker/WdatePicker.js"></script>
</head>
<body>
	<shiro:hasPermission name="sys:operationalConfig:simplecoupon:view">
		<div id="spifTab" class="easyui-tabs" data-options="tools:'#tab-tools',border:false,fit:true" style="margin-top: 5px;">
			<div title="优惠券管理"  data-options="tools:'#p-tools'" style="padding:20px;">
				<div id="ManageToolbar"  style="padding: 5px; height: auto">
					<form id="queryForm" method="post">
						  <table border="0"  style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
								<tr>
								<td class="label right">名称：</td>
								<td><input type="text" name="search_EQ_name" id="search_EQ_name"></td>
		                        <td class="label right">使用平台：</td>
		                        <td>
		                            <select id="search_LIKE_platform" class="easyui-combobox" name="search_LIKE_platform" style="width:200px;">
		                                <option value="">请选择</option>
		                                <option value="投资达人">投资达人</option>
		                                <option value="配股宝">配股宝</option>
		                            </select>
		                        </td>
								</tr>
								<tr>
								<td class="label right">类型：</td>
								<td>
								<select id="type" class="easyui-combobox" name="search_EQ_type" style="width:200px;">
								    <option value="">请选择</option>
								    <option value="1">现金红包</option>
								    <option value="2">代金券</option>
								    <option value="3">折扣券</option>
								    <!-- <option value="4">实物</option>
								    <option value="5">优惠券</option> -->
								    <option value="6">抵扣券</option>
								</select>
								</td>
								<td class="label right">创建日期：</td>
								<td>
									<input id="startTime1" name="search_date_GTE_createTime" class="Wdate" type="text" onFocus="var endTime1=$dp.$('endTime1');WdatePicker({onpicked:function(){endTime1.focus();},maxDate:'#F{$dp.$D(\'endTime1\')}'})"/>
									至  <input id="endTime1" name="search_date_LTE_createTime" class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime1\')}'})"/>							      
								</td>
								<tr>
									<td colspan="8">
										<div style="margin: 10px 10px 10px 10px">
											<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="datagridUtils.datagridQuery('edatagrid','queryForm')">查询</a>
											<shiro:hasPermission name="sys:operationalConfig:simplecoupon:create">	
							  					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="CouponManage.add()">增加</a>
											</shiro:hasPermission>
											<shiro:hasPermission name="sys:operationalConfig:simplecoupon:update">
							 					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="CouponManage.edit()">修改</a>
											</shiro:hasPermission>
										</div>
									</td>
								</tr>
						  </table>
					</form>
				</div>
		
					        
				<table id="edatagrid" class="easyui-datagrid"  pagination="true" 
		      		toolbar="#ManageToolbar" url="${ctx}/admin/couponManage/getDatas"
		            rownumbers="true" fitColumns="true" singleSelect="true" 
		            data-options="checkOnSelect:true,toolbar:'#ManageToolbar',
		            sortName:'createTime',sortOrder:'desc',
					frozenColumns:[[
			            {field:'ck',checkbox:true}
					]]">
					<thead>
						<tr>
			            	<th field="id" hidden="true"></th>
							<th field="name" width="150">名称</th>
							<th field="type" width="150"  formatter="typeToStr">类型</th>
							<th field="scope" width="150"  formatter="numToStr">使用范围</th>
							<th field="platform" width="150" >使用平台</th>
							<th field="money" width="150" formatter="addUnit">面值</th>
							<th field="numToHave" width="150">个数</th>
							<th field="numToLost" width="150">已发放个数</th>
							<th field="cycle" width="150"  formatter="cycleaddStr">使用周期</th>
							<th field="deadline" width="150"  formatter="changeToStr">截止日期</th>
							<th field="createUser" width="150">创建人</th>
							<th field="createTime" width="150" formatter="changeToStr">创建时间</th> 
		            	</tr>
					</thead>
				</table>
			</div>
		
			<div title="优惠券使用详情"  data-options="tools:'#p-tools'" style="padding:20px;">
				<div id="UseToolbar"  style="padding: 5px; height: auto">
					<form id="queryForm2" method="post">
						<table border="0"  style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td class="label right">名称：</td>
								<td><input type="text" name="search_EQ_name" id="name"></td>
								<td class="label right">电话：</td>
								<td><input type="text" name="search_EQ_userPhone" id="userPhone"></td>
								<td class="label right">发放时间：</td>
								<td>
									<input id="startTime2" name="search_date_GTE_grantTime" class="Wdate" type="text" onFocus="var endTime2=$dp.$('endTime2');WdatePicker({onpicked:function(){endTime2.focus();},maxDate:'#F{$dp.$D(\'endTime2\')}'})"/>
					      			至  <input id="endTime2" name="search_date_LTE_grantTime" class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime2\')}'})"/>							      
								</td>
							</tr>
							<tr>
								<td class="label right">类型：</td>
								<td>
									<select id="type" class="easyui-combobox" name="search_EQ_type" style="width:200px;">
		    							<option value="">请选择</option>
		    							<option value="1">现金红包</option>
										<option value="2">代金券</option>
										<option value="3">折扣券</option>
										<!-- <option value="4">实物</option>
										<option value="5">优惠券</option> -->
										<option value="6">抵扣券</option>
									</select>
								</td>
								<td class="label right">使用平台：</td>
								<td>
		                            <select id="platform" class="easyui-combobox" name="search_LIKE_platform" style="width:200px;">
		                                <option value="">请选择</option>
		                                <option value="投资达人">投资达人</option>
		                                <option value="配股宝">配股宝</option>
		                            </select>
		                        </td>
								<td class="label right">使用时间：</td>
								<td>
									<input id="startTime3" name="search_date_GTE_useTime" class="Wdate" type="text" onFocus="var endTime3=$dp.$('endTime3');WdatePicker({onpicked:function(){endTime3.focus();},maxDate:'#F{$dp.$D(\'endTime3\')}'})"/>
					      			至  <input id="endTime3" name="search_date_LTE_useTime" class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime3\')}'})"/>							      
								</td>
							</tr>
							<tr>
								<td class="label right">状态：</td>
								<td>
									<select id="status" class="easyui-combobox" name="search_EQ_status" style="width:200px;">
		    							<option value="">请选择</option>
		  								<!--   <option value="1">未发放</option> -->
		    							<option value="2">已发放</option>
		    							<option value="3">已使用</option>
		    							<option value="4">已过期</option>
									</select>
								</td>
		                    </tr>
		                    <tr>
								<td class="label right"></td>
								<td colspan="5">
									<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="datagridUtils.datagridQuery('edatagrid2','queryForm2')">查询</a>
									<shiro:hasPermission name="sys:operationalConfig:simplecoupon:provide">
										<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" onclick="CouponManage.give()">发放</a>
									</shiro:hasPermission>
									<shiro:hasPermission name="sys:operationalConfig:simplecoupon:used">
										<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" onclick="CouponManage.use()">使用</a>
									</shiro:hasPermission>
								</td>
							</tr>
						</table>
					</form>
				</div>
				<table id="edatagrid2" class="easyui-datagrid"  pagination="true" 
					toolbar="#UseToolbar" url="${ctx}/admin/couponUse/getDatas"
					rownumbers="true" fitColumns="true" singleSelect="true" 
					data-options="checkOnSelect:true,toolbar:'#UseToolbar',frozenColumns:[[{field:'ck',checkbox:true}]]">
					<thead>
						<tr>
							<th field="id" hidden="true"></th>
							<th field="userId" hidden="true"></th>
							<th field="name" width="150">名称</th>
							<th field="type" width="150" formatter="typeToStr">类型</th>
							<th field="scope" width="150"  formatter="numToStr">使用范围</th>
							<th field="platform" width="150" >使用平台</th>
							<th field="createTime" width="150" formatter="changeToStr">创建日期</th>
							<th field="money" width="150" formatter="addUnit">面值</th>
							<th field="status" width="150"  formatter="statusToStr">状态</th>
							<th field="grantTime" width="150" formatter="changeToStr">发放时间</th>
							<th field="useTime" width="150" formatter="changeToStr">使用时间</th>
							<th field="userName" width="150">使用人</th>
							<th field="userPhone" width="150">电话号码</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
		
		<!-- window 新增优惠券弹框-->
		<div id="addWin" class="easyui-window" title="新增优惠券" 
			style="width:350px;height:auto;border:none; overflow: hidden;"
			data-options="iconCls:'icon-save',modal:true,closed:true">
			<form id="addForm" method="post">
				<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td class="label right">名称:</td>
						<td>
							<input id="name2" name="name" class="easyui-validatebox"  data-options="required:true"/>
						</td>
						<td><span ></span></td>
					</tr>
					<tr>
						<td class="label right">使用范围:</td>
						<td id="scopeCheckBox">
							<input name="scope" class="easyui-validatebox" data-options="required:true" type="checkbox"
								value="0"/>富时A50
							<input name="scope" class="easyui-validatebox" data-options="required:true" type="checkbox"
								value="7"/>恒指期货<br/>
							<input name="scope" class="easyui-validatebox" data-options="required:true" type="checkbox"
								value="6"/>国际原油
							<input name="scope" class="easyui-validatebox" data-options="required:true" type="checkbox"
								value="8"/>国际综合<br/>
							<input name="scope" class="easyui-validatebox" data-options="required:true" type="checkbox"
								value="5"/>商品综合
							<input name="scope" class="easyui-validatebox" data-options="required:true" type="checkbox"
								value="11"/>A股操盘<br/>
							<input name="scope" class="easyui-validatebox" data-options="required:true" type="checkbox"
								value="10"/>港股操盘
							<input name="scope" class="easyui-validatebox" data-options="required:true" type="checkbox"
								value="12"/>股票合买
							<input name="scope" class="easyui-validatebox" data-options="required:true" type="checkbox"
								value="13"/>小恒指
						</td>
						<td><span></span></td>
					</tr>
		
					<tr>
						<td class="label right">使用平台:</td>
						<td>
							<label id="platform2"></label>
							<input type="hidden" id="platform2Hid" name="platform" class="easyui-validatebox"/>
						</td>
						<td><span></span></td>
					</tr>
		
					<tr>
						<td class="label right">类型:</td>
						<td>
							<select id="type2" class="easyui-combobox" name="type" style="width:174px;">
								<option value="-1">请选择</option>
								<option value="1">现金红包</option>
								<option value="2">代金券</option>
								<option value="3">折扣券</option>
								<!-- <option value="4">实物</option>
								<option value="5">优惠券</option> -->
								<option value="6">抵扣券</option>
							</select>
						</td>
						<td><span ></span></td>
					</tr>
		
					<tr>
						<td class="label right">面值:</td>
						<td>
							<input id="money2" name="money" class="easyui-numberbox" precision="1" data-options="required:true"/>
							<span id="unit"></span>
						</td>
						<td><span></span></td>
					</tr>
					<tr>
						<td class="label right">个数:</td>
						<td>
							<input id="numToHave2" name="numToHave" class="easyui-validatebox"  data-options="required:true"/>
						</td>
						<td><span ></span></td>
					</tr>
					<tr>
						<td class="label right">有效日期:</td>
						<td>
							<input  name="date" value="0" class="dateclass"  type="radio" checked="checked"/>截止日期
							<input  name="date" value="1" class="dateclass"  type="radio"/>使用周期
						</td>
						<td><span ></span></td>
					</tr>
					<tr id="deadline">
						<td class="label right"></td>
						<td>
							<input id="deadline2"  name="deadline" class="easyui-datetimebox" type="text" data-options="required:true"/>
							
							
							<!-- <input id="deadline2" name="deadline" class="Wdate" type="text" onFocuWdates="WdatePicker({lang:'zh-cn',minDate:new Date()})"/> -->
						</td>
						<td><span ></span></td>
					</tr>
					<tr id="cycle" style="display: none">
						<td class="label right"></td>
						<td>
							<input id="cycle2" name="cycle" class="easyui-validatebox"  data-options="required:true" value="0"/>天
						</td>
						<td><span ></span></td>
					</tr>
		
					<tr>
						<td align="center" colspan="3">
							<a id="btn" href="javascript:void(0);" onclick="passCreate()" class="easyui-linkbutton">提交</a>
							<a id="btn" href="javascript:void(0);" onclick="CouponManage.close('addWin')" class="easyui-linkbutton">取消</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		
		
		<!-- window 修改优惠券弹框-->
		<div id="editWin" class="easyui-window" title="修改优惠券" 
			style="width:350px;height:auto;border:none; overflow: hidden;"
			data-options="iconCls:'icon-save',modal:true,closed:true">
			<form id="editForm" method="post">
				<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td class="label right">名称:</td>
						<td>
							<span id="name3"></span>
						</td>
						<td><span ></span></td>
					</tr>
					<tr>
						<td class="label right">使用范围:</td>
						<td>
							<span id="scope3"></span>
						</td>
						<td><span ></span></td>
					</tr>
					<tr>
						<td class="label right">使用平台:</td>
						<td>
							<span id="platform3"></span>
						</td>
						<td><span></span></td>
					</tr>
					<tr>
						<td class="label right">类型:</td>
						<td>
							<span id="type3"></span>
						</td>
						<td><span ></span></td>
					</tr> 
					<tr>
						<td class="label right">面值:</td>
						<td>
							<span id="money3"></span>
						</td>
						<td><span></span></td>
					</tr>
		            <tr>
						<td class="label right">新增个数:</td>
						<td>
							<input id="numToHave3" name="numToHave" class="easyui-validatebox"  data-options="required:true"/>
						</td>
						<td><span ></span></td>
					</tr>
					<tr>
						<td class="label right">有效日期:</td>
						<td>
							<input  name="date" value="0" class="dateclass2"  type="radio" checked="checked"/>截止日期
							<input  name="date" value="1" class="dateclass2"  type="radio"/>使用周期
						</td>
						<td><span ></span></td>
					</tr>
					<tr id="deadline_e">
						<td class="label right"></td>
						<td>
							<input id="deadline3"  name="deadline" class="easyui-datetimebox"  data-options="required:true"/>
						</td>
						<td><span ></span></td>
					</tr>
					<tr id="cycle_e" style="display: none">
						<td class="label right"></td>
						<td>
							<input id="cycle3" name="cycle" class="easyui-validatebox"  data-options="required:true" value="0"/>天
						</td>
						<td><span ></span></td>
					</tr>
		
					<tr>
						<td align="center" colspan="3">
							<a id="btn" href="javascript:void(0);" onclick="passUpdate()" class="easyui-linkbutton">提交</a>
							<a id="btn" href="javascript:void(0);" onclick="CouponManage.close('editWin')" class="easyui-linkbutton">取消</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		
		<!-- window 发放优惠券弹框-->
		<div id="giveWin" class="easyui-window" title="发放优惠券" 
			style="width:350px;height:auto;border:none; overflow: hidden;"
			data-options="iconCls:'icon-save',modal:true,closed:true">
			<form id="giveForm">
				<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td class="label right">优惠券:</td>
						<td>
							<input id="name4" class="easyui-combobox" name="name4" data-options="valueField:'id',textField:'text',url:'${ctx}/admin/couponUse/findName',onSelect: function(rec){findById(rec);}">
						</td>
						<td><span></span></td>
					</tr>
					<tr>
						<td class="label right">类型:</td>
						<td>
						<span id="type4"></span>
						</td>
						<td><span ></span></td>
					</tr>
					<tr>
						<td class="label right">面值:</td>
						<td>
							<span id="money4"></span>
						</td>
						<td><span ></span></td>
					</tr>
					<tr>
						<td class="label right">电话:</td>
						<td>
							<input id="userPhone4" name="userPhone4" class="easyui-validatebox" data-options="required:true" />
						</td>
						<td><span ></span></td>
					</tr>
					<tr>
						<td class="label right">姓名:</td>
						<td>
							<span id="userName4"></span>
						</td>
						<td><span ></span></td>
					</tr>
					<tr>
						<td align="center" colspan="3">
							<a id="btn" href="javascript:void(0);" onclick="passGive()" class="easyui-linkbutton">发放</a>
							<a id="btn" href="javascript:void(0);" onclick="CouponManage.close('giveWin')" class="easyui-linkbutton">取消</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</shiro:hasPermission>

	<shiro:lacksPermission name="sys:operationalConfig:simplecoupon:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>

</body>
</html>