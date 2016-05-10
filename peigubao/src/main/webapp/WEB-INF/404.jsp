<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="views/common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>404</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/public.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/404.css">
</head>

<body>

<!--顶部 -->
<%@ include file="views/common/personheader.jsp"%>
<!-- 404 -->
<div class="error">
    <div class="errorbox">
        <div class="errorpic"><img src="${ctx}/static/images/404/404.jpg"></div>
        <div class="errorfontbox">
            <div class="errorpromtpic"><img src="${ctx}/static/images/404/font.gif"></div>
            <div class="errorfont">
                <div class="errorbtn">
                    <a href="${ctx}/" class="errorbtn1">返回首页</a>
                    <a href="javascript:void(0);" onclick="history.back();" class="errorbtn2 back">返回上一页</a>
                </div>
                <div class="errorpromt">
                    <p>没有发现你要找的页面，经过砖家仔细研究可能原因如下:</p>
                    <ul>
                        <li>您输入地址时可能存在键入错误</li>
                        <li>页面被程序猿无情的删除了</li>
                        <li>电信网通那头接口生锈</li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<!--底部 -->
<%@ include file="views/common/personfooter.jsp"%>
</body>
</html>