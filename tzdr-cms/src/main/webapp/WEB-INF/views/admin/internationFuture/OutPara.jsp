<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN" />
<!-- X-UA-Compatible 设置ie8/ie9 的兼容模式 -->
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>支付银行管理</title>
<%@include file="../../common/import-easyui-js.jspf"%>
<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>
<script type="text/javascript" src="${ctx}/static/script/common/dateUtils.js"></script>
<script type="text/javascript" src="${ctx}/static/plugins/my97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
<script type="text/javascript">
var setParameterType = 0;
var tabsType="";
function pass(type,tabs) {
	setParameterType = type;
	tabsType=tabs;
	if(tabsType=='one'){
		if(type==1){
			$("#traderBond").val("");
			$("#traderTotal").val("");
			$("#lineLoss").val("");
			$("#goldenMoney").val("");
			$("#atranActualLever").val("");
			$("#htranActualLever").val("");
			$("#ytranActualLever").val("");
			
			$("#mntranActualLever").val("");
			$("#mbtranActualLever").val("");
			$("#daxtranActualLever").val("");
			$("#mdtranActualLever").val("");
			$("#nikkeiTranActualLever").val("");
			$("#hstranActualLever").val("");
			$("#agtranActualLever").val("");
			$("#passWin").window({title:'添加'});
		} else if(type==2){
			var rows = $("#edatagrid").datagrid('getSelections');
			if (Check.validateSelectItems($("#edatagrid"),1)) {
				$("#traderBond").val(rows[0].traderBond);
				$("#traderTotal").val(rows[0].traderTotal);
				$("#lineLoss").val(rows[0].lineLoss);
				$("#goldenMoney").val(rows[0].goldenMoney);
				$("#atranActualLever").val(rows[0].atranActualLever);
				$("#htranActualLever").val(rows[0].htranActualLever);
				$("#ytranActualLever").val(rows[0].ytranActualLever);
				
				$("#mntranActualLever").val(rows[0].mntranActualLever);
				$("#mbtranActualLever").val(rows[0].mbtranActualLever);
				$("#daxtranActualLever").val(rows[0].daxtranActualLever);
				$("#mdtranActualLever").val(rows[0].mdtranActualLever);
				$("#nikkeiTranActualLever").val(rows[0].nikkeiTranActualLever);
				$("#mdtranActualLever").val(rows[0].mdtranActualLever);
				$("#nikkeiTranActualLever").val(rows[0].nikkeiTranActualLever);
				$("#hstranActualLever").val(rows[0].hstranActualLever);
				$("#agtranActualLever").val(rows[0].agtranActualLever);
				
				$("#passWin").window({title:'修改'});
			}else{
				return;
			}
		}
		
		$("#passWin").show();
		$("#passWin").window('open');
	}else if(tabsType=='two'){
		if(type==1){
			$("#tradeType_S").combobox('setValue','');
			$("#mainContract").val("");
			$("#price").val("");
			$("#passWinP").window({title:'添加'});
		} else if(type==2){
			var rows = $("#edatagridP").datagrid('getSelections');
			if (Check.validateSelectItems($("#edatagridP"),1)) {
				
				$("#tradeType_S").combobox('select',rows[0].tradeType);
				$("#mainContract").val(rows[0].mainContract);
				$("#price").val(rows[0].price);
				$("#passWinP").window({title:'修改'});
				
			}else{
				return;
			}
		}
		
		$("#passWinP").show();
		$("#passWinP").window('open');
	}
	
	
};
function passSave() {
	var rows = $("#edatagrid").datagrid('getSelections');
	
	if(tabsType=='one'){
		if ($("#passWin").form("validate")) {
			var traderBond = $("#traderBond").val();
			var traderTotal = $("#traderTotal").val();
			var lineLoss = $("#lineLoss").val();
			var goldenMoney = $("#goldenMoney").val();
			var ATranActualLever = $("#atranActualLever").val();
			var HTranActualLever=$("#htranActualLever").val();
			var YTranActualLever=$("#ytranActualLever").val();
			
			var mntranActualLever = $("#mntranActualLever").val();
			var mbtranActualLever = $("#mbtranActualLever").val();
			var daxtranActualLever = $("#daxtranActualLever").val();
			var mdtranActualLever = $("#mdtranActualLever").val();
			var nikkeiTranActualLever = $("#nikkeiTranActualLever").val();
			var hstranActualLever = $("#hstranActualLever").val();
			var agtranActualLever = $("#agtranActualLever").val();
			
			var parameters = '{}';
			if(setParameterType==1){
				parameters = {"traderBond":traderBond,"traderTotal":traderTotal,"lineLoss":lineLoss,"goldenMoney":goldenMoney,"atranActualLever":ATranActualLever,
						"htranActualLever":HTranActualLever,"ytranActualLever":YTranActualLever,"mntranActualLever":mntranActualLever,"mbtranActualLever":mbtranActualLever,
						"daxtranActualLever":daxtranActualLever,"mdtranActualLever":mdtranActualLever,"nikkeiTranActualLever":nikkeiTranActualLever,
						"hstranActualLever":hstranActualLever,"agtranActualLever":agtranActualLever};
				$.post(Check.rootPath() + "/admin/OutDiskParameters/create",
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
			} else if(setParameterType==2){
				parameters = {"id":rows[0].id,"traderBond":traderBond,"traderTotal":traderTotal,"lineLoss":lineLoss,"goldenMoney":goldenMoney,"atranActualLever":ATranActualLever,
						"htranActualLever":HTranActualLever,"ytranActualLever":YTranActualLever,
						"mntranActualLever":mntranActualLever,"mbtranActualLever":mbtranActualLever,
						"daxtranActualLever":daxtranActualLever,"mdtranActualLever":mdtranActualLever,"nikkeiTranActualLever":nikkeiTranActualLever,
						"hstranActualLever":hstranActualLever,"agtranActualLever":agtranActualLever};
				$.post(Check.rootPath() + "/admin/OutDiskParameters/update",
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
			
		}
	}else if(tabsType=='two'){
		var $rows = $("#edatagridP").datagrid('getSelections');
		var tr=$("#tradeType_S").combobox('getValue');
		if(tr==null||tr==""){
			Check.messageBox("提示","交易品种不能为空！！");
			return;
		}
		if ($("#passWinP").form("validate")) {
			var tradeType=tr;
			var mainContract = $("#mainContract").val();
			var price = $("#price").val();
			var parameters = '{}';
			if(setParameterType==1){
				parameters = {"tradeType":tradeType,"mainContract":mainContract,"price":price};
				$.post(Check.rootPath() + "/admin/OutDiskPrice/create",
						parameters,
						function(data){
							if (data.success) {
								Check.messageBox("提示",data.message);
								$("#edatagridP").datagrid('reload');
								passClose() ;
							} else {
								Check.messageBox("提示",data.message,"error");
							}
				});
			} else if(setParameterType==2){
				parameters = {"id":$rows[0].id,"tradeType":tradeType,"mainContract":mainContract,"price":price};
				$.post(Check.rootPath() + "/admin/OutDiskPrice/update",
						parameters,
						function(data){
							if (data.success) {
								Check.messageBox("提示",data.message);
								$("#edatagridP").datagrid('reload');
								passClose() ;
							} else {
								Check.messageBox("提示",data.message,"error");
							}
				});
			}
			
		}
	}
	
};

function passClose() {
	if(tabsType=='one'){
		$("#passWin").show();
		$("#passWin").window('close');
	}else if(tabsType=='two'){
		$("#passWinP").show();
		$("#passWinP").window('close');
	}
	
};

function tradeToS(value,row,index){
	if(value==0){
		return '富时A50';
	}else if(value==6){ 
		return '国际原油';
	}else if(value==7){ 
		return '恒指期货';
	}else if (value==9){ 
		return '迷你道指';
	}else if (value==10){  
		return '迷你纳指';
	}else if (value==11){  
		return '迷你标普';
	}else if (value==12){
		return '德国DAX';
	}else if (value==13){
		return '日经225';
	}else if (value==14){
		return '小恒指';
	}else if (value==15){
		return '美黄金';
	}
	
	// 9.迷你道指、10.迷你纳指、11.迷你标普、12.德国DAX、13.日经225、14.小恒指、15.美黄金
}

function priceToS(value,row,index){
	if(value!=null&&value>0){
		return value+'元/手';
	}
}

function DateToS(value,row,index){
	if(value!=null){
	return getFormatDateByLong(value,"yyyy-MM-dd hh:mm:ss");
	}
}

$(document).ready(function(){
	$('#spifTab').tabs({
		 onSelect:function(title){
				if(title=='国际综合参数'){
					$("#edatagrid").datagrid('reload');
					
				}else if(title=='国际综合价格'){
					$("#edatagridP").datagrid('reload');
				}
		    }
	})
})

</script>
</head>
<body>
	<shiro:hasPermission name="sys:settingParams:OutDiskParameters:view">
	<div id="spifTab" class="easyui-tabs" data-options="tools:'#tab-tools',border:false,fit:true" style="margin-top: 5px;">
			<div title="国际综合参数" data-options="tools:'#p-tools'" style="padding:20px;">
				<div id="audittb" style="padding: 5px; height: auto">
					<div style="margin-bottom: 5px">
						<shiro:hasPermission name="sys:settingParams:OutDiskParameters:create">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add"   plain="true" onclick="pass(1,'one')">添加</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:settingParams:OutDiskParameters:update">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="pass(2,'one')">修改</a>
						</shiro:hasPermission>
					</div>
				</div> 
				<table id="edatagrid" class="easyui-datagrid"  pagination="true" 
		            toolbar="#audittb" url="${ctx}/admin/OutDiskParameters/easyuiPage"
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
			            	
							<th field="traderBond" width="150" sortable="true">操盘保证金（人民币）</th>
							<th field="traderTotal" width="150">总操盘资金（美元）</th>
							<th field="lineLoss" width="150">亏损平仓线（美元）</th>
							<th field="goldenMoney" width="150">入金金额（美元）</th>
							
							<th field="atranActualLever" width="150" hidden="true">A50交易手数</th>
							<th field="htranActualLever" width="150" hidden="true">恒指交易手数</th>
							<th field="ytranActualLever" width="150" hidden="true">原油交易手数</th>
							<th field="mdtranActualLever" width="150" hidden="true">迷你道指交易手数</th>
							<th field="mntranActualLever" width="150" hidden="true">迷你纳指交易手数</th>
							<th field="mbtranActualLever" width="150" hidden="true">迷你标普交易手数</th>
							<th field="daxtranActualLever" width="150" hidden="true">德国DAX交易手数</th>
							<th field="nikkeiTranActualLever" width="150" hidden="true">日经225交易手数</th>
							<th field="hstranActualLever" width="150" hidden="true">小恒指交易手数</th>
							<th field="agtranActualLever" width="150" hidden="true">美黄金交易手数</th>
							
							<th field="updateTime" width="150" formatter="DateToS">更新时间</th>
							<th field="updateUser" width="150">操作人</th>
			            </tr>
			        </thead>
   				</table>
			</div>
			<div title="国际综合价格" data-options="tools:'#p-tools'" style="padding:20px;">
				<div id="audittbP" style="padding: 5px; height: auto">
					<div style="margin-bottom: 5px">
						<shiro:hasPermission name="sys:settingParams:OutDiskParameters:create">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add"   plain="true" onclick="pass(1,'two')">添加</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:settingParams:OutDiskParameters:update">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="pass(2,'two')">修改</a>
						</shiro:hasPermission>
					</div>
				</div> 
				<table id="edatagridP" class="easyui-datagrid"  pagination="true" 
		            toolbar="#audittbP" url="${ctx}/admin/OutDiskPrice/easyuiPage"
		             rownumbers="true" fitColumns="true" singleSelect="true" 
		             data-options="checkOnSelect:true,toolbar:'#audittbP',
						frozenColumns:[[
				            {field:'ck',checkbox:true}
						]],
				        onLoadSuccess:function(data){
							datagridUtils.loadSuccess(data,'edatagrid');
						}">
			        <thead>
			            <tr>
			            	<th field="id" hidden="true"></th>
							<th field="tradeType" width="150" sortable="true" formatter="tradeToS">交易品种</th>
							<th field="mainContract" width="150">主力合约</th>
							<th field="price" width="150"  formatter="priceToS">价格</th>
							<th field="updateTime" width="150" formatter="DateToS">更新时间</th>
							<th field="updateUser" width="150">操作人</th>
			            </tr>
			        </thead>
   				</table>
			</div>
	</div>	
	
	
	<!-- 国际综合参数操作弹框-->
	<div id="passWin" class="easyui-window" title="编辑" 
		style="width:600px;height:350px;display:none;border:none; overflow: hidden;"
        data-options="iconCls:'icon-save',modal:true,closed:true">
        <form id="passForm">
        <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
             <tr>
                <td class="label right">操盘保证金(￥):</td>
                <td>
                   <input id="traderBond" name="traderBond" class="easyui-validatebox"  data-options="required:true"/>
                </td>
                <td class="label right">总操盘资金($):</td>
                <td>
                   <input id="traderTotal" name="traderTotal" class="easyui-validatebox" data-options="required:true" />
                </td>
                <td><span ></span></td>
            </tr>
            <tr>  
                <td class="label right">亏损平仓线($):</td>
                <td>
                   <input id="lineLoss" name="lineLoss" class="easyui-validatebox"  data-options="required:true"/>
                </td>
                 <td class="label right">入金金额($):</td>
                <td>
                   <input id="goldenMoney" name="goldenMoney" class="easyui-validatebox"  data-options="required:true"/>
                </td>
                <td><span ></span></td>
            </tr>
         
            <tr>
                <td class="label right">A50交易手数:</td>
                <td>
                   <input id="atranActualLever" name="atranActualLever" class="easyui-validatebox"  data-options="required:true"/>
                </td>
                 <td class="label right">恒指交易手数:</td>
                <td>
                   <input id="htranActualLever" name="htranActualLever" class="easyui-validatebox"  data-options="required:true"/>
                </td>
                <td><span ></span></td>
            </tr>
            <tr>
                <td class="label right">原油交易手数:</td>
                <td>
                   <input id="ytranActualLever" name="ytranActualLever" class="easyui-validatebox"  data-options="required:true"/>
                </td>
                <td class="label right">迷你道指交易手数:</td>
                <td>
                   <input id="mdtranActualLever" name="mdtranActualLever" class="easyui-validatebox"  data-options="required:true"/>
                </td>
                <td><span ></span></td>
            </tr>
             <tr>
                <td class="label right">迷你纳指交易手数:</td>
                <td>
                   <input id="mntranActualLever" name="mntranActualLever" class="easyui-validatebox"  data-options="required:true"/>
                </td>
                <td class="label right">迷你标普交易手数:</td>
                <td>
                   <input id="mbtranActualLever" name="mbtranActualLever" class="easyui-validatebox"  data-options="required:true"/>
                </td>
                <td><span ></span></td>
            </tr>
             <tr>
                <td class="label right">德国DAX交易手数:</td>
                <td>
                   <input id="daxtranActualLever" name="daxtranActualLever" class="easyui-validatebox"  data-options="required:true"/>
                </td>
                <td class="label right">日经225交易手数:</td>
                <td>
                   <input id="nikkeiTranActualLever" name="nikkeiTranActualLever" class="easyui-validatebox"  data-options="required:true"/>
                </td>
                <td><span ></span></td>
            </tr>
            <tr>
                <td class="label right">小恒指交易手数:</td>
                <td>
                   <input id="hstranActualLever" name="hstranActualLever" class="easyui-validatebox"  data-options="required:true"/>
                </td>
                <td class="label right">美黄金交易手数:</td>
                <td>
                   <input id="agtranActualLever" name="agtranActualLever" class="easyui-validatebox"  data-options="required:true"/>
                </td>
                <td><span ></span></td>
            </tr>
            <tr>
                <td align="center" colspan="5">
	               <a id="btn" href="javascript:void(0);" onclick="passSave()" class="easyui-linkbutton">提交</a>
	               <a id="btn" href="javascript:void(0);" onclick="passClose()" class="easyui-linkbutton">取消</a>
               </td>
            </tr>
        </table>
        </form>
	</div>
	
	<!-- 国际综合价格操作弹框-->
	<div id="passWinP" class="easyui-window" title="编辑" 
		style="width:450px;height:200px;display:none;border:none; overflow: hidden;"
        data-options="iconCls:'icon-save',modal:true,closed:true">
        <form id="passForm">
        <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
             <tr>
                <td class="label right">交易品种:</td>
                <td>
                <select id="tradeType_S" class="easyui-combobox" name="tradeType_S" style="width:174px;">
				    <option value="-1" selected="selected"></option>
				    <option value="0">富时A50</option>
				    <option value="7">恒指期货</option>
				    <option value="6">国际原油</option>
				    <option value="9">迷你道指</option>
				    <option value="10">迷你纳指</option>
				    <option value="11">迷你标普</option>
				    <option value="12">德国DAX</option>
				    <option value="13">日经225</option>
				    <option value="14">小恒指</option>
				    <option value="15">美黄金</option>
				</select>
                </td>
                <td><span ></span></td>
            </tr>
            <tr>
                <td class="label right">主力合约:</td>
                <td>
                   <input id="mainContract" name="mainContract" class="easyui-validatebox" data-options="required:true" />
                </td>
                <td><span ></span></td>
            </tr>  
            <tr>  
                <td class="label right">价格:</td>
                <td>
                   <input id="price" name="price" class="easyui-validatebox"  data-options="required:true"/>元/手
                </td>
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
	
	
	</shiro:hasPermission>
	<shiro:lacksPermission name="sys:settingParams:OutDiskParameters:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>