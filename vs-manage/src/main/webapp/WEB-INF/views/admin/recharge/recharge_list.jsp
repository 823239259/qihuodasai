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
<title>用户列表</title>
<%@include file="../../common/import-easyui-js.jspf"%>
<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>
<script type="text/javascript" src="${ctx}/static/script/recharge/recharge_list.js"></script>
<script type="text/javascript">

  
  setInterval("reloadData()",1000 * 3000);
  function reloadData(){
	  var rows001 = $("#dg001").datagrid('getSelections');
	  if (rows001 && rows001.length > 0) {
	      $("#dg001").datagrid("selectRecord",rows001[0].id);
	  }
	  $("#dg001").datagrid('reload');
	  
	  var rows002 = $("#dg002").datagrid('getSelections');
	  if (rows002 && rows002.length > 0) {
		  $("#dg002").datagrid("selectRecord",rows002[0].id);
	  }
	  $("#dg002").datagrid('reload');
	  
	  var rows003 = $("#dg003").datagrid('getSelections');
	  if (rows003 && rows003.length > 0) {
		  $("#dg003").datagrid("selectRecord",rows003[0].id);
	  }
	  $("#dg003").datagrid('reload');
	  
	  var rows004 = $("#dg004").datagrid('getSelections');
	  if (rows004 && rows004.length > 0) {
		  $("#dg004").datagrid("selectRecord",rows004[0].id);
	  }
	  $("#dg004").datagrid('reload');
  };

</script>
<style type="text/css">
    .frontImg {
        width: 200px;
    }
</style>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
</head>
<body>
<shiro:hasPermission name="sys:finance:rechargeReview:view">
<div id="tabWindow" class="easyui-tabs" style="height:auto;">
    <div title="支付宝充值审核" style="padding:1px;">
        <table id="dg001" class="easyui-datagrid" toolbar="#dg001Toolbar"
             width="100%"
             url="${ctx}/admin/rechargeReview/listData" idField="id" pagination="true"
             data-options="checkOnSelect:true"
            rownumbers="true" fitColumns="true" singleSelect="true">
	        <thead>
	            <tr>
	                <th field="id" data-options="checkbox:true"></th>
					<th field="mobileNo" width="150">手机号 </th>
					<th field="tname" width="150">用户姓名</th>
					<th field="account" width="150">支付宝账号</th>
					<th field="tradeNo" width="150">交易号</th>
					<th field="money" width="150">充值表单金额</th>
					<th field="addtimeStr" width="150">提交时间</th>
					<th field="actualMoney" width="150">实际到账金额 </th>
					<th field="statusStr" width="150">充值状态 </th>
					<th field="uptimeStr" width="150">充值时间</th>
					<th field="source" width="150">来源网站</th>
	            </tr>
	        </thead>
        </table>
        <div id="dg001Toolbar">
         <shiro:hasPermission name="sys:finance:rechargeReview:recharge">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="alibaPayOpen()">确认充值</a>
           </shiro:hasPermission>
      <shiro:hasPermission name="sys:finance:rechargeReview:rechargeFail">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="failAlibaPay()">充值失败</a>
       	</shiro:hasPermission>
        </div>
        
    </div>
    <div title="银行转账充值审核" style="padding:1px;">
        <table id="dg002" class="easyui-datagrid" width="100%" idField="id" toolbar="#dg002Toolbar"
             url="${ctx}/admin/rechargeReview/listDataBank" pagination="true"
            rownumbers="true" fitColumns="true" singleSelect="true">
        <thead>
            <tr>
                <th field="id" data-options="checkbox:true"></th>
				<th field="mobileNo" width="150">手机号 </th>
				<th field="tname" width="150">用户姓名</th>
				<th field="tradeNo" width="150">流水号</th>
				<th field="tradeAccountBank" width="150">收款银行</th>
				<th field="money" width="150">充值表单金额</th>
				<th field="addtimeStr" width="150">提交时间</th>
				<th field="actualMoney" width="150">实际到账金额 </th>
				<th field="statusStr" width="150">充值状态 </th>
				<th field="uptimeStr" width="150">充值时间</th>
				<th field="source" width="150">来源网站</th>
            </tr>
        </thead>
    </table>
    <div id="dg002Toolbar">
      <shiro:hasPermission name="sys:finance:rechargeReview:recharge">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="bankPayOpen()">确认充值</a>
     </shiro:hasPermission>
      <shiro:hasPermission name="sys:finance:rechargeReview:rechargeFail">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="failBankPay()">充值失败</a>
    	</shiro:hasPermission>
    </div>
    
    </div>
    <div title="网银充值审核" style="padding:1px;">
        <table id="dg003" class="easyui-datagrid" width="100%" idField="id" toolbar="#dg003Toolbar"
             url="${ctx}/admin/rechargeReview/listDataNetBank" pagination="true"
            rownumbers="true" fitColumns="true" singleSelect="true">
        <thead>
            <tr>
                <th field="id" data-options="checkbox:true"></th>
				<th field="mobileNo" width="150">手机号 </th>
				<th field="tname" width="150">用户姓名</th>
				<th field="no" width="150">商户号</th>
				<th field="tradeNo" width="150">流水号</th>
				<th field="tradeAccountBank" width="150">收款银行</th>
				<th field="money" width="150">充值表单金额</th>
				<th field="addtimeStr" width="150">提交时间</th>
				<th field="actualMoney" width="150">实际到账金额 </th>
				<th field="statusStr" width="150">充值状态 </th>
				<th field="uptimeStr" width="150">充值时间</th>
				<th field="source" width="150">来源网站</th>
            </tr>
        </thead>
    </table>
    <div id="dg003Toolbar">
       <shiro:hasPermission name="sys:finance:rechargeReview:recharge">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="netBankPayOpen()">确认充值</a>
     </shiro:hasPermission>
      <shiro:hasPermission name="sys:finance:rechargeReview:rechargeFail">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="failNetBankPay()">充值失败</a>
    	</shiro:hasPermission>
    </div>
    
    </div>
    <div title="微信充值审核" style="padding:1px;">
        <table id="dg004" class="easyui-datagrid" width="100%" idField="id" toolbar="#dg004Toolbar"
             url="${ctx}/admin/rechargeReview/listDataWechat" pagination="true"
            rownumbers="true" fitColumns="true" singleSelect="true">
        <thead>
            <tr>
                <th field="id" data-options="checkbox:true"></th>
				<th field="mobileNo" width="150">手机号 </th>
				<th field="tname" width="150">用户姓名</th>
				<th field="account" width="150">微信号</th>
				<th field="tradeNo" width="150">流水号</th>
				<th field="tradeAccountBank" width="150">收款银行</th>
				<th field="money" width="150">充值表单金额</th>
				<th field="actualMoney" width="150">实际到账金额 </th>
				<th field="addtimeStr" width="150">提交时间</th>
				<th field="statusStr" width="150">充值状态 </th>
				<th field="uptimeStr" width="150">充值时间</th>
				<th field="source" width="150">来源网站</th>
            </tr>
        </thead>
    </table>
    <div id="dg004Toolbar">
       <shiro:hasPermission name="sys:finance:rechargeReview:recharge">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="wechatPayOpen()">确认充值</a>
     </shiro:hasPermission>
      <shiro:hasPermission name="sys:finance:rechargeReview:rechargeFail">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="failWechatPay()">充值失败</a>
    	</shiro:hasPermission>
    </div>
    
    </div>
  <%--  <div title="成功充值记录查询" style="padding:1px;">
        <table id="dg003" class="easyui-datagrid" width="100%" toolbar="#dg003Toolbar" url="${ctx}/admin/recharge/listRecharge" pagination="true"
            rownumbers="true" fitColumns="true" singleSelect="true">
	        <thead>
	            <tr>
	                <!-- <th field="id" data-options="checkbox:true"></th> -->
					<th field="mobileNo" width="150">手机号 </th>
					<th field="tradeNo" width="150">流水号</th>
					<th field="tradeAccountBank" width="150">交易银行</th>
					<th field="money" width="150">充值表单金额</th>
					<th field="type" width="150">支付类型</th>
					<th field="addtime" width="150">提交时间</th>
					<th field="actualMoney" width="150">实际到账金额 </th>
					<!-- <th field="status" width="150">充值状态 </th> -->
					<th field="oktime" width="150">充值时间</th>
	            </tr>
	        </thead>
    	</table>
    	<div id="dg003Toolbar">
	        <form id="queryForm" method="post">
		     <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
	            <tr>
	                <td class="label right">支付类型:</td>
	                <td>
	                <input class="easyui-combobox" id="recharge_type" type="text" name="type" 
                       data-options="valueField:'id',required:true,textField:'text',url:'/tzdr-cms/admin/recharge/dataMapCombobox?key=paytype'" />
	                </td>
	                <td colspan="2">&nbsp;</td>
	            </tr>
	            <tr>
	                <td class="label">&nbsp;</td>
	                <td class="label" colspan="3"><a id="btn" href="#" onclick="$.easyui.datagridQuery('dg003','queryForm')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a></td>
	            </tr>
	          </table>
	        </form>
	    </div>
    </div> --%>
     
</div>



<div id="alibaPay" class="easyui-window" title="新增充值" style="padding:2px;width:300px;height:150px;display:none; overflow: hidden;"
        data-options="iconCls:'icon-save',modal:true,closed:true">
        <table border="0" style="margin: 2px;font-size:12px;" cellpadding="5" cellspacing="0">
        	<%--
            <tr>
                <td>充值金额</td>
                <td><input  type="text" id="rechargeAmountId" class="easyui-numberbox" precision="2" data-options="required:true" /></td>
            </tr>
            <tr>
                <td>交易号</td>
                <td><input  class="easyui-textbox" id="tradeNoId" type="text" name="name" data-options="required:true" /></td>
            </tr>
        	 --%>
        	<tr>
                <td>手机号</td>
                <td><input  class="easyui-textbox" id="mobile" type="text" name="mobile" data-options="required:true" /></td>
            </tr>
            <tr>
                <td align="center" colspan="2">
                <a id="btn" href="#" onclick="confirmAlibaPay()" class="easyui-linkbutton">提交</a>
               <a id="btn" href="#" onclick="alibaPayClose()" class="easyui-linkbutton">取消</a>
               </td>
            </tr>
        </table>
        
</div>


<div id="bankPay" class="easyui-window" title="新增充值" style="padding:2px;width:300px;height:200px;display:none; overflow: hidden;"
        data-options="iconCls:'icon-save',modal:true,closed:true">
        <form id="bankPayForm">
        <table border="0" style="margin: 2px;font-size:12px;" cellpadding="5" cellspacing="0">
            <tr>
                <td>充值金额</td>
                <td><input  type="text" id="bankAmountId" class="easyui-numberbox" precision="2" data-options="required:true" /></td>
            </tr>
            <tr>
                <td>流水号</td>
                <td><input class="easyui-validatebox" id="bankTradeNoId" type="text" name="name" data-options="required:true" /></td>
            </tr>
            <tr>
                <td>收款银行</td>
                <td>
                <input class="easyui-combobox" 
                id="bankId" type="text" name="bankname" 
                data-options="valueField:'id',required:true,textField:'text',url:'${ctx}/admin/recharge/dataMapCombobox?key=bankname&includes=ccb,icbc,abc,boc,cmb'" 
                />
                </td>
            </tr>
            <tr>
                <td align="center" colspan="2">
                <a id="btn" href="#" onclick="confirmBankPay()" class="easyui-linkbutton">提交</a>
               <a id="btn" href="#" onclick="bankPayClose()" class="easyui-linkbutton">取消</a>
               </td>
            </tr>
        </table>
        </form>
        
</div>


<div id="netBankPay" class="easyui-window" title="新增充值" style="padding:2px;width:300px;height:200px;display:none; overflow: hidden;"
        data-options="iconCls:'icon-save',modal:true,closed:true">
        <form id="netBankPayForm">
        <table border="0" style="margin: 2px;font-size:12px;" cellpadding="5" cellspacing="0">
            <tr>
                <td>充值金额</td>
                <td><input  type="text" id="netBankAmountId" class="easyui-numberbox" precision="2" data-options="required:true" /></td>
            </tr>
            <tr>
                <td>流水号</td>
                <td><input class="easyui-validatebox" id="netBankTradeNoId" type="text" name="name" data-options="required:true" /></td>
            </tr>
            <%-- <tr>
                <td>收款银行</td>
                <td>
                <input class="easyui-combobox" 
                id="netBankId" type="text" name="bankname" 
                data-options="valueField:'id',required:true,textField:'text',url:'${ctx}/admin/recharge/dataMapCombobox?key=bankname&includes=ccb,icbc,abc,boc,cmb'" 
                />
                </td>
            </tr> --%>
            <tr>
                <td align="center" colspan="2">
                <a id="btn" href="#" onclick="confirmNetBankPay()" class="easyui-linkbutton">提交</a>
               <a id="btn" href="#" onclick="netBankPayClose()" class="easyui-linkbutton">取消</a>
               </td>
            </tr>
        </table>
        </form>
        
</div>

<div id="wechatPay" class="easyui-window" title="新增充值" style="padding:2px;width:300px;height:200px;display:none; overflow: hidden;"
        data-options="iconCls:'icon-save',modal:true,closed:true">
        <form id="wechatPayForm">
        <table border="0" style="margin: 2px;font-size:12px;" cellpadding="5" cellspacing="0">
            <tr>
                <td>充值金额</td>
                <td><input  type="text" id="wechatAmountId" class="easyui-numberbox" precision="2" data-options="required:true" /></td>
            </tr>
            <tr>
                <td>流水号</td>
                <td><input class="easyui-validatebox" id="wechatTradeNoId" type="text" name="name" data-options="required:true" /></td>
            </tr>
            <%-- <tr>
                <td>收款银行</td>
                <td>
                <input class="easyui-combobox" 
                id="netBankId" type="text" name="bankname" 
                data-options="valueField:'id',required:true,textField:'text',url:'${ctx}/admin/recharge/dataMapCombobox?key=bankname&includes=ccb,icbc,abc,boc,cmb'" 
                />
                </td>
            </tr> --%>
            <tr>
                <td align="center" colspan="2">
                <a id="btn" href="#" onclick="confirmWechatPay()" class="easyui-linkbutton">提交</a>
               <a id="btn" href="#" onclick="wechatPayClose()" class="easyui-linkbutton">取消</a>
               </td>
            </tr>
        </table>
        </form>
        
</div>



<div id="applyReasonWin" class="easyui-window" title="审核不通过原因" style="padding:2px;width:400px;height:300px;display:none; overflow: hidden;font-size: 12px;"
        data-options="iconCls:'icon-save',modal:true,closed:true">
        <table border="0" width="100%" style="margin: 2px;font-size: 12px;" height="100%" cellpadding="0" cellspacing="0">
            <tr>
                <td align="right">原因:</td>
                <td><textarea style="height:200px;width: 100%" id="applyNotByReasonId"></textarea></td>
            </tr>
            
            <tr>
                <td style="text-align: center;" colspan="2">
                <a id="btn" href="#" onclick="sidNotPass()" class="easyui-linkbutton">提交</a>
               </td>
            </tr>
        </table>
        
</div>


<div id="threeApplyWin" class="easyui-window" title="身份证照片审核" style="padding:2px;width:600px;height:400px;display:none; overflow: hidden;"
        data-options="iconCls:'icon-save',modal:true,closed:true">
        <table border="0" width="100%" style="margin: 2px;" height="100%" cellpadding="0" cellspacing="0">
            <tr>
                <td><img border="0" id="threeImgId001" class="frontImg" src=""></td>
                <td><img border="0" id="threeImgId002" class="frontImg" src=""></td>
            </tr>
            <tr>
                <td><img border="0" id="threeImgId003" class="frontImg" src=""></td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td style="text-align: center;" colspan="2">
                <a id="btn" href="#" onclick="threePass()" class="easyui-linkbutton">通过</a>
               <a id="btn" href="#" onclick="threeApplayReasonShow()" class="easyui-linkbutton">不通过</a>
               </td>
            </tr>
        </table>
</div>

<div id="threeApplyReasonWin" class="easyui-window" title="审核不通过原因" style="padding:2px;width:400px;height:300px;display:none; overflow: hidden;font-size: 12px;"
        data-options="iconCls:'icon-save',modal:true,closed:true">
        <table border="0" width="100%" style="margin: 2px;font-size: 12px;" height="100%" cellpadding="0" cellspacing="0">
            <tr>
                <td align="right">原因:</td>
                <td><textarea style="height:200px;width: 100%" id="threeApplyNotByReasonId"></textarea></td>
            </tr>
            
            <tr>
                <td style="text-align: center;" colspan="2">
                <a id="btn" href="#" onclick="threeNotPass()" class="easyui-linkbutton">提交</a>
               </td>
            </tr>
        </table>
        
</div>
 </shiro:hasPermission>
   <shiro:lacksPermission name="sys:finance:rechargeReview:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>