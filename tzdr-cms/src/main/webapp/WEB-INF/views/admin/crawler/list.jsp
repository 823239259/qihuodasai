<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN" />
<!-- X-UA-Compatible 设置ie8/ie9 的兼容模式 -->
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<%@include file="../../common/import-easyui-js.jspf"%>
<title>爬虫网络参数设置</title>
</head>
<body>
		<table id="dg"  class="easyui-datagrid"  width="100%" style="height:auto;" 
			url="${ctx }/admin/crawler/url/listData"
			toolbar="#toolbar"  pagination="true"
			rownumbers="true" fitColumns="true" singleSelect="true">
			<thead>
				<tr>
					<th field="urlTitle" width="50" >网站标题</th>
					<th field="urlUrl" width="50" >网站路劲</th>
					<th field="status" width="50">执行状态</th>
					<th field="execRule" width="50" >执行规则</th>
					<th field="urlMethod" width="50">请求方式</th>
					<!-- <th field="urlCreatetime" width="50" >创建时间</th> -->
					<th field="urlRemarks" width="50" >备注</th>
				</tr>
			</thead>
</table>
</body>
</html>