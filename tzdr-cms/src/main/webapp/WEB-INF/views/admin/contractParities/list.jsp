<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN" />
<!-- X-UA-Compatible 设置ie8/ie9 的兼容模式 -->
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>主力合约参数设置</title>
<%@include file="../../common/import-easyui-js.jspf"%>
<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>
<script type="text/javascript" src="${ctx}/static/script/common/dateUtils.js"></script>
<script type="text/javascript" src="${ctx}/static/plugins/my97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
<script type="text/javascript">
var setParameterType = 0;
function pass(type) {
	setParameterType = type;
	if(type==1){
		
	} else if(type==2){
		var rows = $("#edatagrid").datagrid('getSelections');
		if (Check.validateSelectItems($("#edatagrid"),1)) {
			$("#typeName").val(rows[0].typeName);
			$("#contract").val(rows[0].contract);
			$("#passWin").show();
			$("#passWin").window('open');
		}
	}

};
function passSave() {
	var rows = $("#edatagrid").datagrid('getSelections');
	if ($("#passWin").form("validate")) {
		var contract = $("#contract").val();
		
		var parameters = '{}';
		if(setParameterType==1){
			parameters = {"contract":contract};
		} else if(setParameterType==2){
			parameters = {"id":rows[0].id,"contract":contract};
		}
		$.post(Check.rootPath() + "/admin/contractParities/saveOrUpdateConfig",
				parameters,
				function(data){
					if (data.success) {
						Check.messageBox("提示",data.message);
						$("#edatagrid").datagrid('reload');
						passClose() ;
					} else {
						Check.messageBox("提示",data.message,"error");
					}
		});
	}
};

function passClose() {
	$("#passWin").show();
	$("#passWin").window('close');
};

function openVariety(type){
	
	
	if(type==1){//添加
		 var userIndex=$("#userIndex").val("");
		var commodityNo=$("#commodityNo").val("");
		var commodityName=$("#commodityName").val("");
		var exchangeNo=$("#exchangeNo").val("");
		var exchangeName=$("#exchangeName").val("");
		//var timeBucket=$("input[name='timeBucket']").val("");
		//var timeBucket1=$("input[name='timeBucket']").val("");
		var contractSize=$("#contractSize").val("");
		var miniTikeSize=$("#miniTikeSize").val("");
		var dotSize=$("#dotSize").val(""); 
		$("#passVariety").show;
		$("#passVariety").window('open');
	}else{//修改
		 var rows = $("#ed").datagrid('getSelections');
		if (Check.validateSelectItems($("#ed"),1)) { 
			$("#passVariety").show;
			$("#passVariety").window('open');
	}
	
}
}
function removeTime(d){
	var a=$(d).parent();
	a.remove();
};
function addTime(){
	$("#addTime").append("<div><input style='width:70px'  name='timeBucket' class='easyui-timespinner'  data-options='required:true'/>- <input style='width:70px' name='timeBucket1' class='easyui-timespinner'  data-options='required:true'/><a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-remove' plain='true' onclick='removeTime(this)'>删除</a></div>");
};
function varietySubmit(type){
	var userIndex=$("#userIndex").val();
	var commodityNo=$("#commodityNo").val();
	var commodityName=$("#commodityName").val();
	var exchangeNo=$("#exchangeNo").val();
	var exchangeName=$("#exchangeName").val();
	var timeBucket=$("input[name='timeBucket']");
	var timeBucket1=$("input[name='timeBucket']");
	var contractSize=$("#contractSize").val();
	var miniTikeSize=$("#miniTikeSize").val();
	var dotSize=$("#dotSize").val();
	
	if(type==1){
		//添加
		timeBucket
	}else{
		//修改
	}
};
</script>
</head>
<body>
	<shiro:hasPermission name="sys:settingParams:contractParities:view">
	<div id="spifTab" class="easyui-tabs" data-options="tools:'#tab-tools',border:false,fit:true" style="margin-top: 5px;">
			<div title="主力合约参数设置" data-options="tools:'#p-tools'" style="padding:20px;">
				<div id="audittb" style="padding: 5px; height: auto">
					<div style="margin-bottom: 5px">
						<%-- <shiro:hasPermission name="sys:settingParams:contractParities:create">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="pass(1)">新增</a>
						</shiro:hasPermission> --%>
						<shiro:hasPermission name="sys:settingParams:contractParities:update">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="pass(2)">修改</a>
						</shiro:hasPermission>
						<%-- <shiro:hasPermission name="sys:settingParams:contractParities:delete">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="removeRecord()">删除</a>
						</shiro:hasPermission> --%>
					</div>
				</div> 
				<table id="edatagrid" class="easyui-datagrid"  pagination="true" 
		            toolbar="#audittb" url="${ctx}/admin/contractParities/getDatas"
		             rownumbers="true" fitColumns="true" singleSelect="true" 
		             data-options="checkOnSelect:true,toolbar:'#audittb',
						frozenColumns:[[
				            {field:'ck',checkbox:true}
						]],
				        onLoadSuccess:function(data){
							datagridUtils.loadSuccess(data,'edatagrid');
						}">
			        <thead>
			            <tr>
			            	<th field="id" hidden="true"></th>
							<th field="typeName" width="100" sortable="true">交易品种</th>
							<th field="contract" width="500">主力合约</th>
							<th field="createTime" width="120">添加时间</th>
							<th field="updateTime" width="120">修改时间</th>
							<th field="updateUser" width="120">操作人</th>
			            </tr>
			        </thead>
   				</table>
			</div>
			<div title="行情主力合约维护" data-options="tools:'#p-tools'" style="padding:20px;">
			    <div id="auditMains" style="padding: 5px; height: auto">
					<div style="margin-bottom: 5px">
					 <shiro:hasPermission name="sys:settingParams:contractParities:create">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="openVariety(1)">新增</a>
						</shiro:hasPermission>
						 <shiro:hasPermission name="sys:settingParams:contractParities:update">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="openVariety(2)">修改</a>
						</shiro:hasPermission>
						
						<%-- <shiro:hasPermission name="sys:settingParams:contractParities:delete">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="removeRecord()">删除</a>
						</shiro:hasPermission> --%>
					</div> 
				</div>
				<table id="ed" class="easyui-datagrid"  pagination="true" 
		            toolbar="#auditMain" url="${ctx}/admin/contractParities/getDatas"
		             rownumbers="true" fitColumns="true" singleSelect="true" 
		             data-options="checkOnSelect:true,toolbar:'#auditMain',
						frozenColumns:[[
				            {field:'ck',checkbox:true}
						]],
				        onLoadSuccess:function(data){
							datagridUtils.loadSuccess(data,'ed');
						}">
			        <thead>
			            <tr>
			            	<th field="id" hidden="true"></th>
							<th field="commodityNo" width="100" sortable="true">品种代码</th>
							<th field="commodityName" width="100">品种名称</th>
							<th field="exchangeName" width="120">交易所名称</th>
							<th field="timeBucket" width="120">交易时间段</th>
							<th field="contractSize" width="120">合约乘数</th>
							<th field="miniTikeSize" width="120">最小变动单位</th>
							<th field="dotSize" width="120">小数位数</th>
							<th field="currencyNo" width="120">币种</th>
							<th field="mainContract" width="120" >主力合约</th>
			            </tr>
			        </thead>
   				</table>
			</div>
	</div>	
	<div id="passWin" class="easyui-window" title="编辑" 
		style="width:450px;height:250px;display:none;border:none; overflow: hidden;"
        data-options="iconCls:'icon-save',modal:true,closed:true">
        <form id="passForm">
        <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
             <tr>
                <td class="label right">交易品种:</td>
                <td>
                   <input id="typeName" name="typeName" class="easyui-validatebox"  disabled="disabled" data-options="required:true"/>
                </td>
                <td><span ></span></td>
            </tr>
            <tr>
                <td class="label right">操盘须知:</td>
                <td>
                   <textarea id="contract" name="contract"  rows="6" cols="30"></textarea>
                <td><span ></span></td>
            </tr>  
           
           
            <tr>
                <td align="center" colspan="3">
	               <a id="btn" href="javascript:void(0);" onclick="passSave()" class="easyui-linkbutton">提交</a>
	               <a id="btn" href="javascript:void(0);" onclick="passClose()" class="easyui-linkbutton">取消</a>
               </td>
            </tr>
        </table>
        </form>
	</div>
	<div id="passVariety" class="easyui-window" title="编辑"
        data-options="iconCls:'icon-save',closed:true">
          <form id="varietyForm">
        <table border="0" style="font-size:12px;" class="conn"  width="100%" height="320px" cellpadding="0" cellspacing="0">
             <tr>
                <td class="label right">序号:</td>
                <td>
                   <input id="userIndex" name="userIndex" class="easyui-validatebox"  data-options="required:true"/>
                </td>
                <td><span ></span></td>
            </tr>
            <tr>
                <td class="label right">合约代码:</td>
                <td>
                   <input id="commodityNo" name="commodityNo" class="easyui-validatebox"  data-options="required:true"/>
                <td><span ></span></td>
            </tr>  
               <tr>
                <td class="label right">合约名称:</td>
                <td>
                   <input id="commodityName" name="commodityName" class="easyui-validatebox"  data-options="required:true"/>
                <td><span ></span></td>
            </tr>  
      
               <tr>
                <td class="label right">交易所代码:</td>
                <td>
                   <input id="exchangeNo" name="exchangeNo" class="easyui-validatebox"   data-options="required:true"/>
                <td><span ></span></td>
            </tr>  
               <tr>
                <td class="label right">交易所名称:</td>
                <td>
                   <input id="exchangeName" name="exchangeName" class="easyui-validatebox"   data-options="required:true"/>
                <td><span ></span></td>
            </tr>  
               <tr>
                <td class="label right">交易时间段:</td>
                <td id="addTime">
                   <input style="width:70px"  name="timeBucket" class="easyui-timespinner"  data-options="required:true"/>- <input style="width:70px" name="timeBucket1" class="easyui-timespinner"  data-options="required:true"/>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addTime()"/>
                <td><span ></span></td>
            </tr>  
               <tr>
                <td class="label right">合约乘数:</td>
                <td>
                   <input id="contractSize" name="contractSize" class="easyui-validatebox"   data-options="required:true"/>
                <td><span ></span></td>
            </tr>  
               <tr>
                <td class="label right">最小变动单位:</td>
                <td>
                   <input id="miniTikeSize" name="miniTikeSize" class="easyui-validatebox"   data-options="required:true"/>
                <td><span ></span></td>
            </tr>  
               <tr>
                <td class="label right">小数位数:</td>
                <td>
                   <input id="dotSize" name="dotSize" class="easyui-validatebox"   data-options="required:true"/>
                <td><span ></span></td>
            </tr>  
            <tr>
                <td align="center" colspan="3">
	               <a id="btn" href="javascript:void(0);" onclick="" class="easyui-linkbutton">提交</a>
	               <a id="btn" href="javascript:void(0);" onclick="" class="easyui-linkbutton">取消</a>
               </td>
            </tr>
        </table>
        </form>
        
        
        </div>
	</shiro:hasPermission>
	<shiro:lacksPermission name="sys:settingParams:contractParities:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>