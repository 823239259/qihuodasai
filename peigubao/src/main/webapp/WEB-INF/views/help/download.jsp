<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>交易软件下载 - 配股宝</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/qa.css?v=${v}" />
</head>

<body>
<!--顶部 -->
<%@ include file="../common/personheader.jsp"%>

<div class="content">
	<div class="nav">
		<ul>			
			<li><a href="${ctx}/help?status=5">关于我们</a></li>
			<li><a href="${ctx}/help?status=1">A股操盘须知</a></li>
			<li><a href="${ctx}/help?status=2">港股操盘须知</a></li>
			<li id="nowLi"><a href="javascript:void(0)" id="nowA">交易软件下载</a><img src="${ctx}/static/images/qa/sanjiao.png" /></li>
			<li><a href="${ctx}/help?status=4">常见问答</a></li>
		</ul>
	</div>	
	
	<div class="contentDownload">
		<div class="contentDownload1"><img src="${ctx}/static/images/qa/wxp.png" /></div>
		<div class="contentDownload2">
			<h3>win XP用户安装时需注意</h3>
			<p>1,先下载并安装"<a href="http://update.tzdr.com/Future/download/.netFramework3.5.exe">.NET Framework 3.5</a>";</p>
			<p>2,再下载并安装"<a href="http://update.tzdr.com/Future/download/%E9%92%B1%E9%9A%86TTS.exe">钱隆TTS股票交易系统</a>",然后启动程序。</p>
			<p>若程序仍无法运行,请致电客服400-633-9527。</p>
		</div>
	</div>
	<div class="contentDownload">
		<div class="contentDownload1"><img src="${ctx}/static/images/qa/w7.png" /></div>
		<div class="contentDownload2">
			<h3>win 7用户安装时需注意</h3>
			<p>1,直接下载安装"<a href="http://update.tzdr.com/Future/download/%E9%92%B1%E9%9A%86TTS.exe">钱隆TTS股票交易系统</a>",然后启动程序。</p>
			<p>2,若程序无法运行，请补充下载并安装"<a href="http://update.tzdr.com/Future/download/.netFramework3.5.exe">.NET Framework 3.5</a>"再运行TTS系统。</p>
			<p>若程序仍无法运行,请致电客服400-633-9527。</p>
		</div>
	</div>
	<div class="contentDownload">
		<div class="contentDownload1"><img src="${ctx}/static/images/qa/w8.png" id="w8Img" /></div>
		<div class="contentDownload2" id="w82">
			<h3>win 8用户安装时需注意</h3>
			<p>1,直接下载并安装"<a href="http://update.tzdr.com/Future/download/vcredist_x86.zip">VC2005运行库</a>";</p>
			<p>2,再下载并安装"<a href="http://update.tzdr.com/Future/download/%E9%92%B1%E9%9A%86TTS.exe">钱隆TTS股票交易系统</a>"然后启动程序。</p>
			<p>若程序仍无法运行,请致电客服400-633-9527。</p>
		</div>
	</div>
	<div class="contentDownload" id="lastContentDownload">
		<div class="contentDownload1"><img src="${ctx}/static/images/qa/aip.png" id="lastImg" /></div>
		<div class="contentDownload2" id="lastContentDownload2">
			<h3>手机用户掌上交易,扫描二维码进入：</h3>
			<p><img src="${ctx}/static/images/qa/erweima.png" width="120" height="120" /></p>
		</div>
	</div>
	<div class="contentIntorduce">
		<div class="contentIntorduce1" id="firstContentIntorduce1">
			<img src="${ctx}/static/images/qa/tts.png" />
			<p>钱隆TTS资管之家客户端，是资管专用的投资交易委托下单系统，支持股票交易相关买入、卖出、信息查询等功能，具有交易灵活可靠、交易速度快、系统稳定等特点。</p>
		</div>
		<div class="contentIntorduce1">
			<img src="${ctx}/static/images/qa/netframework.png" />
			<p>.Net Framework3.5是支持和运行应用程序内部Windows的组件。运行钱隆TTS交易软件,电脑操作系统必须已装安装.Net Framework3.5。</p>
		</div>
		<div class="contentIntorduce1">
			<img src="${ctx}/static/images/qa/vc.png" />
			<p>运行库是一个经过封装的程序模块，如果不适用运行库,操作系统在运行程序时找不到对应的运行库程序就无法运行。运行钱隆TTS交易软件,电脑系统必须已经安装VC运行库。</p>
		</div>
	</div>
	
</div>
<!--底部 -->
<%@ include file="../common/personfooter.jsp"%>
</body>
</html>
