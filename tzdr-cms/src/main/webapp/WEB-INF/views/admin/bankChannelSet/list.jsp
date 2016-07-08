<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN" />
<!-- X-UA-Compatible 设置ie8/ie9 的兼容模式 -->
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>网银通道设置</title>
<%@include file="../../common/import-easyui-js.jspf"%>
<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>
<script type="text/javascript" src="${ctx}/static/script/common/dateUtils.js"></script>
<script type="text/javascript" src="${ctx}/static/plugins/my97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
<script type="text/javascript">
$(document).ready(function(){
	var str="";
	$.ajax({
		url:"${ctx}/admin/bankChannel/querybank",
		data:{},
		dataType:'json',
		type:'POST',
		success:function(list){
			$(list).each(function(i){
				if(i%2==0){
					str+="<tr>";
				}
				str=str+"<td><input type='hidden' name='id"+i+"' value='"+$(this).attr('id')+"'/>"+$(this).attr("bankName")+"</td>";
				if("兴业银行"!=($(this).attr("bankName"))){
					if($(this).attr("supportBbpay")==1){
						str=str+"<td><input type='checkbox' checked='checked' name='supportBbpay"+i+"'/></td>";
					}else if($(this).attr("supportBbpay")==0){
						str=str+"<td><input type='checkbox' name='supportBbpay"+i+"'/></td>";
					}
				}else{
					str=str+"<td></td>";
				}
				if($(this).attr("supportUmpay")==1){
						str=str+"<td><input type='checkbox' checked='checked' name='supportUmpay"+i+"'/></td>";
				}else if($(this).attr("supportUmpay")==0){
						str=str+"<td><input type='checkbox' name='supportUmpay"+i+"'/></td>";
				}
				if(i%2!=0){
					str+="</tr>";
				}
			})
			$("#bankinfo").find("tbody").html(str);
		}
	});
	var tt=$("#spifTab").tabs({
		border:false,
	    onSelect:function(title){
	    	if("配股宝设置"==title){
	    		peigubaoinfo();
	    	}
	    }
	});
	
})


function peigubaoinfo(){
	var str="";
	$.ajax({
		url:"${ctx}/admin/pgbbankChannel/pgbquerybank",
		data:{},
		dataType:'json',
		type:'POST',
		success:function(list){
			$(list).each(function(i){
				if(i%2==0){
					str+="<tr>";
				}
				str=str+"<td><input type='hidden' name='pgbid"+i+"' value='"+$(this).attr('id')+"'/>"+$(this).attr("bankName")+"</td>";
				if("兴业银行"!=($(this).attr("bankName"))){
					if($(this).attr("supportBbpay")==1){
						str=str+"<td><input type='checkbox' checked='checked' name='pgbsupportBbpay"+i+"'/></td>";
					}else if($(this).attr("supportBbpay")==0){
						str=str+"<td><input type='checkbox' name='pgbsupportBbpay"+i+"'/></td>";
					}
				}else{
					str=str+"<td></td>";
				}
				if($(this).attr("supportUmpay")==1){
						str=str+"<td><input type='checkbox' checked='checked' name='pgbsupportUmpay"+i+"'/></td>";
				}else if($(this).attr("supportUmpay")==0){
						str=str+"<td><input type='checkbox' name='pgbsupportUmpay"+i+"'/></td>";
				}
				if(i%2!=0){
					str+="</tr>";
				}
			})
			$("#peigubaobankinfo").find("tbody").html(str);
		}
	});
}


function bankupdatesub(){
	 $.messager.confirm("修改提示", "您确定要执行修改吗？", function (data) {  
         if (data) {   
        	$(":checkbox").each(function(){
        		if($(this).attr("checked")=="checked"){
        			$(this).val("1");
        		}
        	});
        	$("#bankupdate").submit();
         }  
          
     });  
	
	
}


function pgbbankupdatesub(){
	 $.messager.confirm("修改提示", "您确定要执行修改吗？", function (data) {  
        if (data) {   
       	$(":checkbox").each(function(){
       		if($(this).attr("checked")=="checked"){
       			$(this).val("1");
       		}
       	});
       	
    	$.post($.easyui.path() + "/admin/pgbbankChannel/pgbbankupdate"
				,$("#pgbbankupdate").serialize(),function(data){
				if ("success"==data){
					eyWindow.walert("成功提示","设置成功！", 'info');
				}
				else
				{
				eyWindow.walert("错误提示","设置失败", 'error');
				}
		},"text");
        }
    });  
	
	
}
</script>
<style type="text/css">
#bankinfo{text-align: center;border:1px solid #d0d0d0;width: 700px;}
#bankinfo th{border: 1px solid #d0d0d0;background-color: #eeeeee;height: 50px;}
#bankinfo td{border: 1px solid #d0d0d0;height: 30px;}

#peigubaobankinfo{text-align: center;border:1px solid #d0d0d0;width: 700px;}
#peigubaobankinfo th{border: 1px solid #d0d0d0;background-color: #eeeeee;height: 50px;}
#peigubaobankinfo td{border: 1px solid #d0d0d0;height: 30px;}

#withdrawtb{text-align: center;border:1px solid #d0d0d0;width: 700px;} 
#withdrawtb th{border: 1px solid #d0d0d0;background-color: #eeeeee;height: 50px;}
#withdrawtb td{border: 1px solid #d0d0d0;height: 30px;}
.tbname{margin: 10px;font-weight:bold; }
</style>
</head>
<body>
	<shiro:hasPermission name="sys:finance:bankChannel:view">
	<div id="spifTab" class="easyui-tabs" data-options="tools:'#tab-tools',border:false,fit:true" style="margin-top: 5px;">
			<div title="维胜设置" data-options="tools:'#p-tools'" style="padding:20px;">
			<div id="audittb" style="padding: 5px; height: auto">
					<div style="margin-bottom: 5px">
						<%-- <shiro:hasPermission name="sys:finance:bankChannel:create">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="pass(1)">新增</a>
						</shiro:hasPermission> --%>
						<shiro:hasPermission name="sys:finance:bankChannel:update">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"  onclick="bankupdatesub()">提交修改</a>
						</shiro:hasPermission>
						<%-- <shiro:hasPermission name="sys:finance:bankChannel:delete">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="removeRecord()">删除</a>
						</shiro:hasPermission> --%>
					</div>
				</div> 


				
				<form  action="${ctx}/admin/bankChannel/bankupdate" id="bankupdate" method="post">
				
				<div class="tbname">提现设置</div>
				<table id="withdrawtb" cellspacing="0" cellpadding="0">
					<thead>
							<th>银行名称</th>
							<th>币币支付</th>
							<th>联动优势</th>
					</thead>
					<tbody>
					<tr>
						<c:choose>
								<c:when test="${withdrawSetting==2}">
									<td>所有银行</td>
									<td>
										<input type="radio" name="withdrawSetting" value="2" checked="checked"/>
									</td>
									<td>
										<input type="radio" name="withdrawSetting" value="1"/>
									</td>
								</c:when>
								<c:otherwise>
									<td>所有银行</td>
									<td>
										<input type="radio" name="withdrawSetting" value="2"/>
									</td>
									<td>
										<input type="radio" name="withdrawSetting" value="1"  checked="checked"/>
									</td>
								</c:otherwise>
						</c:choose>
					</tr>
					</tbody>
				</table>
				<div class="tbname">充值设置</div>
				<table id="bankinfo" cellspacing="0" cellpadding="0">
				<thead>
				<th>银行名称</th>
				<th>币币支付</th>
				<th>联动优势</th>
				<th>银行名称</th>
				<th>币币支付</th>
				<th>联动优势</th>
				</thead>
				<tbody></tbody>
				</table>
				</form>
			</div>
			
			
			<!-- 配股宝 -->
			<div title="配股宝设置" data-options="tools:'#p-tools'" style="padding:20px;">
			<div id="audittb" style="padding: 5px; height: auto">
					<div style="margin-bottom: 5px">
						<%-- <shiro:hasPermission name="sys:finance:bankChannel:create">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="pass(1)">新增</a>
						</shiro:hasPermission> --%>
						<shiro:hasPermission name="sys:finance:bankChannel:update">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"  onclick="pgbbankupdatesub()">提交修改</a>
						</shiro:hasPermission>
						<%-- <shiro:hasPermission name="sys:finance:bankChannel:delete">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="removeRecord()">删除</a>
						</shiro:hasPermission> --%>
					</div>
				</div> 


				
				<form  id="pgbbankupdate">
				
				<div class="tbname">提现设置</div>
				<table id="withdrawtb" cellspacing="0" cellpadding="0">
					<thead>
							<th>银行名称</th>
							<th>币币支付</th>
							<th>联动优势</th>
					</thead>
					<tbody>
					<tr>
						<c:choose>
								<c:when test="${pgbWithdrawSetting==2}">
									<td>所有银行</td>
									<td>
										<input type="radio" name="pgbWithdrawSetting" value="2" checked="checked"/>
									</td>
									<td>
										<input type="radio" name="pgbWithdrawSetting" value="1"/>
									</td>
								</c:when>
								<c:otherwise>
									<td>所有银行</td>
									<td>
										<input type="radio" name="pgbWithdrawSetting" value="2"/>
									</td>
									<td>
										<input type="radio" name="pgbWithdrawSetting" value="1"  checked="checked"/>
									</td>
								</c:otherwise>
						</c:choose>
					</tr>
					</tbody>
				</table>
				<div class="tbname">充值设置</div>
				<table id="peigubaobankinfo" cellspacing="0" cellpadding="0">
				<thead>
				<th>银行名称</th>
				<th>币币支付</th>
				<th>联动优势</th>
				<th>银行名称</th>
				<th>币币支付</th>
				<th>联动优势</th>
				</thead>
				<tbody></tbody>
				</table>
				</form>
			</div>
			
	</div>	
	<!-- window 申请 分配帐号弹框-->
	<div id="passWin" class="easyui-window" title="编辑" 
		style="width:450px;height:350px;display:none;border:none; overflow: hidden;"
        data-options="iconCls:'icon-save',modal:true,closed:true">
        <form id="passForm">
        <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
             <tr>
                <td class="label right">银行名称:</td>
                <td>
                   <input id="bankName" name="bankName" class="easyui-validatebox"  data-options="required:true"/>
                </td>
                <td><span ></span></td>
            </tr>
            <tr>
                <td class="label right">银行简称:</td>
                <td>
                   <input id="abbreviation" name="abbreviation" class="easyui-validatebox" data-options="required:true" />
                </td>
                <td><span ></span></td>
            </tr>  
            <tr>  
                <td class="label right">币币支付银行代码:</td>
                <td>
                   <input id="bbpayCode" name="bbpayCode" class="easyui-validatebox"  data-options="required:true"/>
                </td>
                <td><span ></span></td>
            </tr>
            <tr>
                <td class="label right">币币支付提现联行号:</td>
                <td>
                   <input id="bbpayContactNumber" name="bbpayContactNumber" class="easyui-validatebox"  data-options="required:true"/>
                </td>
                <td><span ></span></td>
            </tr>
            <tr>
                <td class="label right">排序:</td>
                <td>
                   <input id="weight" name="weight" class="easyui-validatebox"  data-options="required:true"/>
                </td>
                <td><span ></span></td>
            </tr>
           <tr>
                <td class="label right">图标地址:</td>
                <td>
                   <input id="iconPath" name="iconPath" class="easyui-validatebox"  data-options="required:true"/>
                </td>
                <td><span ></span></td>
            </tr>
             <!-- 
            <tr>
                <td class="label right">入金总金额($):</td>
                <td>
                   <input id="goldenMoney" name="goldenMoney" class="easyui-validatebox"  data-options="required:true"/>
                </td>
                <td><span ></span></td>
            </tr> -->
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
	<shiro:lacksPermission name="sys:finance:bankChannel:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>