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
<title>登录</title>
<script type="text/javascript" src="${ctx}/static/script/common/dateUtils.js"></script>
<%@include file="../../common/import-easyui-js.jspf"%>
<%@include file="../../common/import-kindeditor-js.jspf"%>
<script type="text/javascript" src="${ctx}/static/script/operationalConfig/list.js?version=20160114"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">

</head>
<body>
	<input type="hidden" value="${type}" id="configType">
	<c:if test="${type==1}">
		<shiro:hasPermission name="sys:operationalConfig:friendlyLink:view">
		<div id="tb" style="padding: 5px; height: auto">
			<div>
				<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td class="label right">
							<span>名称：</span>
						</td>
						<td>
						<input class="easyui-validatebox" id="name">
						&nbsp;&nbsp;
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="opConfig.doSearch(1)">查询</a>
						</td>
					</tr>
				</table>				
			</div>
			<div style="margin-bottom: 5px">
				<shiro:hasPermission name="sys:operationalConfig:friendlyLink:create">  
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="baseUtils.openAddwin(500,250,'新增友情连接','config/friendlyLink')">新增</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="sys:operationalConfig:friendlyLink:delete"> 
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="baseUtils.deleteData('config/friendlyLink')">删除</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="sys:operationalConfig:friendlyLink:update"> 
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="baseUtils.openEditwin(500,250,'修改友情链接','config/friendlyLink')">修改</a>
				</shiro:hasPermission>
			</div>
		</div>
		<table id="edatagrid"></table>
	
		<!-- ADD window -->
		<div id="addWin" style="padding:10px;top: 20px;"></div>
		<!-- window -->
		
		</shiro:hasPermission>
		<shiro:lacksPermission name="sys:operationalConfig:friendlyLink:view">
			<%@ include file="../../common/noPermission.jsp"%>
		</shiro:lacksPermission>
	</c:if>
	<c:if test="${type==2}">
	<shiro:hasPermission name="sys:operationalConfig:newsColumn:view">
		<div id="tb" style="padding: 5px; height: auto">
			<div>
				<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td class="label right">
							<span>名称：</span>
						</td>
						<td>
						<input class="easyui-validatebox" id="name">
						&nbsp;&nbsp;
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="opConfig.doSearch(2)">查询</a>
						</td>
					</tr>
				</table>						
			</div>
			<div style="margin-bottom: 5px">
				<shiro:hasPermission name="sys:operationalConfig:newsColumn:create">  
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="baseUtils.openAddwin(500,200,'新增新闻栏目','config/newsColumn')">新增</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="sys:operationalConfig:newsColumn:delete"> 
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="baseUtils.deleteData('config/newsColumn')">删除</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="sys:operationalConfig:newsColumn:update"> 
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="baseUtils.openEditwin(500,200,'修改新闻栏目','config/newsColumn')">修改</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="sys:operationalConfig:newsColumn:update"> 
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="opConfig.setParams('true','admin/config/newsColumn/setEnable')">启用</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="sys:operationalConfig:newsColumn:update"> 
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="opConfig.setParams('false','admin/config/newsColumn/setEnable')">禁用</a>
				</shiro:hasPermission>
			</div>
		</div>
		<table id="edatagrid"></table>
	
		<!-- ADD window -->
		<div id="addWin" style="padding:10px;top: 20px;"></div>
		<!-- window -->
		
		</shiro:hasPermission>
		<shiro:lacksPermission name="sys:operationalConfig:newsColumn:view">
			<%@ include file="../../common/noPermission.jsp"%>
		</shiro:lacksPermission>
	</c:if>
	<c:if test="${type==3}">
	<shiro:hasPermission name="sys:operationalConfig:news:view">
		<div id="tb" style="padding: 5px; height: auto">
			<div>
				<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td class="label right">
							<span>名称：</span>
						</td>
						<td>
						<input class="easyui-validatebox" id="name">
						&nbsp;&nbsp;
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="opConfig.doSearch(3)">查询</a>
						</td>
					</tr>
				</table>				
			</div>
			<div style="margin-bottom: 5px">
				<shiro:hasPermission name="sys:operationalConfig:news:create">  
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="baseUtils.openAddIframeWin(700,420,'新增新闻','config/news')">新增</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="sys:operationalConfig:news:delete"> 
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="baseUtils.deleteData('config/news')">删除</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="sys:operationalConfig:news:update"> 
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="baseUtils.openEditIframeWin(700,420,'修改新闻','config/news')">修改</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="sys:operationalConfig:news:update"> 
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="opConfig.setParams('true','admin/config/news/setRelease')">发布</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="sys:operationalConfig:news:update"> 
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="opConfig.setParams('true','admin/config/news/setTop')">置顶</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="sys:operationalConfig:news:update"> 
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="opConfig.setParams('false','admin/config/news/setTop')">取消置顶</a>
				</shiro:hasPermission>
			</div>
		</div>
		<table id="edatagrid"></table>
	
		<!-- ADD window -->
		<div id="addWin" style="padding:10px;top: 30px;"></div>
		<!-- window -->
		
		</shiro:hasPermission>
		<shiro:lacksPermission name="sys:operationalConfig:news:view">
			<%@ include file="../../common/noPermission.jsp"%>
		</shiro:lacksPermission>
	</c:if>
	<c:if test="${type==4}">
	<shiro:hasPermission name="sys:operationalConfig:banner:view">
	<div id="bannerTab" class="easyui-tabs" data-options="tools:'#tab-tools',border:false,fit:true" style="margin-top: 5px;">
		<div title="维胜banner" data-options="tools:'#p-tools'" style="padding:20px;">
		<div id="tb" style="padding: 5px; height: auto">
			<div>
				<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td class="label right">
							<span>名称：</span>
						</td>
						<td>
						<input class="easyui-validatebox" id="name">
						&nbsp;&nbsp;
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="opConfig.doSearch(4,'bannerTab')">查询</a>
						</td>
					</tr>
				</table>							
			</div>
			<div style="margin-bottom: 5px">
				<shiro:hasPermission name="sys:operationalConfig:banner:create">  
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="bannertest.openAddIframeWin(600,340,'新增Banner','config/banner','bannerTab')">新增</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="sys:operationalConfig:banner:delete"> 
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="bannertest.deleteData('config/banner','bannerTab')">删除</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="sys:operationalConfig:banner:update"> 
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="bannertest.openEditIframeWin(600,340,'修改Banner','config/banner','bannerTab')">修改</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="sys:operationalConfig:banner:refresh"> 
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="refreshWebHomePageBanner()">刷新缓存</a>
				</shiro:hasPermission>
			</div>
		</div>
		<table id="edatagrid"></table>
		</div>
		
		<!-- 配股宝banner -->
		<div title="配股宝banner" data-options="tools:'#p-tools'" style="padding:20px;">
		<div id="tb_pgb" style="padding: 5px; height: auto">
			<div>
				<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td class="label right">
							<span>名称：</span>
						</td>
						<td>
						<input class="easyui-validatebox" id="name_pgb">
						&nbsp;&nbsp;
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="opConfig.doSearch(8,'bannerTab')">查询</a>
						</td>
					</tr>
				</table>							
			</div>
			<div style="margin-bottom: 5px">
				<shiro:hasPermission name="sys:operationalConfig:banner:create">  
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="bannertest.openAddIframeWin(600,340,'新增Banner','config/banner','bannerTab')">新增</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="sys:operationalConfig:banner:delete"> 
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="bannertest.deleteData('config/banner','bannerTab')">删除</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="sys:operationalConfig:banner:update"> 
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="bannertest.openEditIframeWin(600,340,'修改Banner','config/banner','bannerTab')">修改</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="sys:operationalConfig:banner:refresh"> 
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="refreshPgbHomePageBanner()">刷新缓存</a>
				</shiro:hasPermission>
			</div>
		</div>
		<table id="edatagrid_pgb"></table>
		</div>

		<!-- 维胜app-banner -->
		<div title="维胜APP-banner" data-options="tools:'#p-tools'" style="padding:20px;">
			<div id="tb_tzdrApp" style="padding: 5px; height: auto">
				<div>
					<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td class="label right">
								<span>名称：</span>
							</td>
							<td>
								<input class="easyui-validatebox" id="name_tzdrApp">
								&nbsp;&nbsp;
								<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="opConfig.doSearch(9,'bannerTab')">查询</a>
							</td>
						</tr>
					</table>
				</div>
				<div style="margin-bottom: 5px">
					<shiro:hasPermission name="sys:operationalConfig:banner:create">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="bannertest.openAddIframeWin(600,340,'新增Banner','config/banner','bannerTab')">新增</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="sys:operationalConfig:banner:delete">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="bannertest.deleteData('config/banner','bannerTab')">删除</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="sys:operationalConfig:banner:update">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="bannertest.openEditIframeWin(600,340,'修改Banner','config/banner','bannerTab')">修改</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="sys:operationalConfig:banner:refresh">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="refreshPgbHomePageBanner()">刷新缓存</a>
					</shiro:hasPermission>
				</div>
			</div>
			<table id="edatagrid_tzdrApp"></table>
		</div>
	</div>

		<!-- ADD window -->
		<div id="addWin" style="padding:10px;top: 20px;"></div>
		<!-- window -->

		</shiro:hasPermission>
		<shiro:lacksPermission name="sys:operationalConfig:banner:view">
			<%@ include file="../../common/noPermission.jsp"%>
		</shiro:lacksPermission>
	</c:if>
	<c:if test="${type==5}">
	<shiro:hasPermission name="sys:operationalConfig:partners:view">
		<div id="tb" style="padding: 5px; height: auto">
			<div>
				<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td class="label right">
							<span>名称：</span>
						</td>
						<td>
						<input class="easyui-validatebox" id="name">
						&nbsp;&nbsp;
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="opConfig.doSearch(5)">查询</a>
						</td>
					</tr>
				</table>					
			</div>
			<div style="margin-bottom: 5px">
				<shiro:hasPermission name="sys:operationalConfig:partners:create">  
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="baseUtils.openAddIframeWin(600,300,'新增合作伙伴','config/partners')">新增</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="sys:operationalConfig:partners:delete"> 
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="baseUtils.deleteData('config/partners')">删除</a>
				</shiro:hasPermission>
				<shiro:hasPermission name="sys:operationalConfig:partners:update"> 
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="baseUtils.openEditIframeWin(600,300,'修改合作伙伴','config/partners')">修改</a>
				</shiro:hasPermission>
			</div>
		</div>
		<table id="edatagrid"></table>
	
		<!-- ADD window -->
		<div id="addWin" style="padding:10px;top: 20px;"></div>
		<!-- window -->
		
		</shiro:hasPermission>
		<shiro:lacksPermission name="sys:operationalConfig:partners:view">
			<%@ include file="../../common/noPermission.jsp"%>
		</shiro:lacksPermission>
	</c:if>
	
</body>
<script type="text/javascript">

var bannertest={
/**
 * 通用 弹出iframe 新增框
 * @param width  弹出框宽度
 * @param height 高度
 * @param title 名称
 * @param modelName  模块名称  如：user、org、role。。。。
 * @param TabsId easyui-tabs的id
 */
openAddIframeWin:function(width,height,title,modelName,TabsId){
	var maximizableValue=false;
	if ("config/news"==modelName){
		 maximizableValue=true;
	}
	var html = '<iframe scrolling="yes" frameborder="0"  src="'+basepath+'admin/'+modelName+'/edit?fromType=add&type=4" style="width:100%;height:100%;"></iframe>';
	var $title=$("#"+TabsId).tabs('getSelected').panel('options').title;
	if($title.indexOf("维胜")>-1){
		html = '<iframe scrolling="yes" frameborder="0"  src="'+basepath+'admin/'+modelName+'/edit?fromType=add&type=8" style="width:100%;height:100%;"></iframe>';
	}
	if($title.indexOf("维胜APP")>-1){
		html = '<iframe scrolling="yes" frameborder="0"  src="'+basepath+'admin/'+modelName+'/edit?fromType=add&type=9" style="width:100%;height:100%;"></iframe>';
	}
	$('#addWin').window({collapsible:false,minimizable:false,maximizable:maximizableValue,width:width,height:height,title:title,loadingMessage:'正在加载,请等待......',iconCls:'icon-add',closed:true,modal:true,content:html});
	$('#addWin').window('open');
},

/**
 * 通用弹出iframe修改框
 * @param width  弹出框宽度
 * @param height 高度
 * @param title 名称
 * @param modelName  模块名称  如：user、org、role。。。。
 * @param TabsId easyui-tabs的id
 */
openEditIframeWin:function(width,height,title,modelName,TabsId){
	
	var rowData=$('#edatagrid').datagrid('getSelected');
	var $title=$("#"+TabsId).tabs('getSelected').panel('options').title;

			if($title.indexOf("维胜")>-1){
				rowData=$('#edatagrid_pgb').datagrid('getSelected');
			}
			if($title.indexOf("维胜APP")>-1){
				rowData=$('#edatagrid_tzdrApp').datagrid('getSelected');
			}
	if (null == rowData){
		eyWindow.walert("修改提示",'请选择要修改的行', 'info');
		return;
	}
	
	var maximizableValue=false;
	if ("config/news"==modelName){ 
		 maximizableValue=true;
	}
	
	var html = '<iframe scrolling="yes" frameborder="0"  src="'+basepath+'admin/'+modelName+'/edit?fromType=edit&type=4&id='+rowData.id+'" style="width:100%;height:100%;"></iframe>';
	if($title.indexOf("维胜")>-1){
		html = '<iframe scrolling="yes" frameborder="0"  src="'+basepath+'admin/'+modelName+'/edit?fromType=edit&type=8&id='+rowData.id+'" style="width:100%;height:100%;"></iframe>';
	}
	if($title.indexOf("维胜APP")>-1){
		html = '<iframe scrolling="yes" frameborder="0"  src="'+basepath+'admin/'+modelName+'/edit?fromType=edit&type=9&id='+rowData.id+'" style="width:100%;height:100%;"></iframe>';
	}
	$('#addWin').window({collapsible:false,minimizable:false,maximizable:maximizableValue,width:width,height:height,title:title,loadingMessage:'正在加载,请等待......',iconCls:'icon-edit',closed:true,modal:true,content:html});
	$('#addWin').window('open');
	
},
/**
 * 通用删除方法
 * @param modelName
 * @param TabsId easyui-tabs的id
 */
deleteData:function(modelName,TabsId){
	var eda="edatagrid";
	var $title=$("#"+TabsId).tabs('getSelected').panel('options').title;
	if($title.indexOf("维胜")>-1){
		eda="edatagrid_pgb";
	}
	if($title.indexOf("维胜APP")>-1){
		eda="edatagrid_tzdrApp"
	}
	
	var rows = $("#"+eda).datagrid('getChecked');
	if (!rows || rows.length==0){
		eyWindow.walert("删除提示","请选择要删除的行", 'info');
		return;
	}
	// 删除数据
	$.messager.confirm("确认提示","确认是否删除选中的数据？", function(result){
		if (result){
			datagridUtils.deleteData(rows,"admin/"+modelName+"/batchDelete",eda);
		}
	});
}
}
</script>
</html>