<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-fileupload-js.jspf"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<%
	String tab = request.getParameter("tab");
    String mobile = userSessionBean.getMobile();
%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="baidu-site-verification" content="19aaf89cfc08519ae9fee304f1fcca71"/>
	<title>我的邀请码 - 维胜金融-中国领先的国际期货及衍生品互联网交易平台</title>
	<link rel="stylesheet" href="${ctx}/static/css/uc.css?version=20170113" type="text/css">
	<link href="${ctx}/static/css/public.css" rel="stylesheet" type="text/css">
	<link href="${ctx}/static/css/pagination.css?version=20170113" rel="stylesheet" type="text/css" />
	<script src="${ctx}/static/script/common/jquery.pagination.js" type="text/javascript"></script>
	<!-- 
<link href="common/tablesorter.css" rel="stylesheet" type="text/css" />
	 -->
	<script src="${ctx}/static/script/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
	<script type='text/javascript' src="${ctx}/static/script/fund/invitationCode.js?version=20170113"></script>
	<%-- <script type='text/javascript' src="${ctx}/static/script/common/jquery.zclip.min.js?version=20150721"></script> --%>
	<script type='text/javascript' src="${ctx}/static/script/fund/share.js?version=20150721"></script>
	<script type='text/javascript' src="${ctx}/static/script/common/dateUtils.js"></script>
	<script type='text/javascript' src="${ctx}/static/script/common/ZeroClipboard.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/script/tzdr.js"></script>
	<script type='text/javascript' src="${ctx}/static/swf/jquery.zclip.min.js"></script>
	<title>维胜</title>
	<script type="text/javascript">
		var tab =<%=tab%>;
		var isOut = false;
		$(document).ready(function() {
			$('#paytype').click(function(event) {
				event.stopPropagation();
				$("#paytypediv").show();
			});
			$(document).click(function() {
				if (isOut == false) {
					$("#alltypediv").hide();
					$("#paytypediv").hide();
				}
			});
			$('#alltype').click(function(event) {
				event.stopPropagation();
				$("#alltypediv").show();
			});
			$("#alltypediv a").each(function() {
				$(this).click(function() {
					$('#fundtype').attr("data-id", $(this).attr("data-id"));
					var value = $(this).html();
					$('#fundtype').val(value);
					$('#alltypediv').hide();
				});
			});
			$("#Pagination").html("");
			var iphone=$("#iphone").val();
			var starttime=$("#allstarttime").val();
			var endtime=$("#allendtime").val();
			var type=$("#fundtype").attr("data-id");
			if(starttime!="" && endtime!=""){
				if(starttime>endtime){
					showMsgDialog("提示","开始时间不能大于结束时间");
					return;
				}
			}
			/* getTitleData(starttime,endtime,type,"incount","insummoney"); */
			var index="${index}";
			if(index){
				$("#fundtab ul.uc_paynav a").eq(index).trigger("click");
			}
			/* $('.fzwz span.anniucopy').on('click',function() {
	            var $this = $(this);
	            var $target = $this.prev(),
	                    $copy = $this.closest('.wangzhi'),
	                    $str = $('<div class="pop">按 Ctrl+C 复制地址</div>');
	            var a =$target.select();
	            $copy.find('.pop').stop(true).remove();
	            $str.appendTo($copy).fadeIn();
	            $(".pop").css({
	                position: "absolute",
	                top: "30px",
	                color: "#fc3"
	            });
	            setTimeout(function() {
	                $copy.find('.pop').fadeOut();
	            }, 3000);	
	        }) */
		});
	</script>
<style>
	#nav_my {color: #ffcc33; border-bottom:2px solid #ffcc33; padding-bottom: 26px;}
	.uc_fsmoney {width: 100%; height: 30px; line-height: 30px;}
	.uc_fssearch {width: 100%;}
	.uc_fs_type {left: 239px; top: 80px; width: 115px;}
</style>
</head>
<body>
	<!--顶部 -->
	<!--个人中心导航 -->
	<%@include file="../common/header.jsp"%>
	<div class="uc">
		<!--个人中心导航 -->
		<%@ include file="../common/leftnav.jsp"%>
		<!--网银支付-->
		<div class="uc_mianbar">
			<div class="uc_pay" id="fundtab">
				<ul class="uc_paynav">
					<li><a href="javascript:void(0);" class="on">我的邀请码</a></li>
					<li><a href="javascript:void(0);">邀请记录</a></li>
				</ul>
				<div class="tabcon">
					<div class="subtab">
						<div class="my_invitationCode">
							<p class="my_jieshao" style="font-weight: bolder;">分享以下邀请链接，好友通过您的链接注册成功并完成实名认证，并成功申请方案，您可获得免手续费的奖励，可累计。</p>
							<p class="fzwz">
								<span class="wangzhi" id="test">http://www.vs.com/signin</span>
            					<a href="javascript:" id = "anniucopy" class="anniucopy cancelCopy">复制链接</a>
            					<span id="xians" style="float: right; color: red; margin-right: 200px;"></span>
            				</p>
            				<input type="hidden" name="" id="mobile_vs" value="<%=mobile %>"/>
							<!-- <textarea maxlength="3000"></textarea> -->
							<%-- <p><span class="fenxiang">分享到: </span>
                    			<a href="#" onclick="shareToSinaWB(event)" title="分享到新浪微博"><img alt="" src="${ctx}/static/images/uc/fenxiang_sina.jpg"></a>
                    			<a href="#" onclick="shareToQQwb(event)" title="分享到腾讯微博"><img alt="" src="${ctx}/static/images/uc/fenxiang_tengxun.jpg"></a>
                    			<a href="#"  title="分享到微信"><img alt="" src="${ctx}/static/images/uc/fenxiang_weixin.jpg"></a>
                    			<a href="#" onclick="shareQQ(event)"  title="分享到QQ好友"><img alt="" src="${ctx}/static/images/uc/fenxiang_qq.jpg"></a>
                    			<a href="#" onclick="shareToQzone(event)" title="分享到QQ空间"><img alt="" src="${ctx}/static/images/uc/fenxiang_zone.jpg"></a>
                    		</p> --%>
                    		<div class="bdsharebuttonbox" data-tag="share_1" style="height: 30px; line-height: 26px; margin-top: 10px; border-bottom: 1px solid #e6e6e6; padding-bottom: 10px;">
								<span style="float: left;">分享：</span><a class="bds_mshare" data-cmd="mshare"></a>
								<a class="bds_qzone" data-cmd="qzone" href="#"></a>
								<a class="bds_tsina" data-cmd="tsina"></a>
								<a class="bds_baidu" data-cmd="baidu"></a>
								<a class="bds_renren" data-cmd="renren"></a>
								<a class="bds_tqq" data-cmd="tqq"></a>
								<a class="bds_more" data-cmd="more">更多</a>
								<a class="bds_count" data-cmd="count"></a>
							</div>
							<div class="erweima">
            					<p style="font-weight: bolder; font-size: 14px;">您的专属二维码</p>
            					<div id="ewm" style="width: 160px; height: 160px; margin: 0 auto;"></div>
            					<p style="font-size: 14px;">将二维码发送给好友，好友扫描二维码后打开邀请页注册</p>
            				</div>
							<div class="invitation">
								<ul class="i_register">
									<img src="${ctx}/static/images/image/yao_2.png" alt=""/>
									<li class="lis"><span class="ftradeNum">0</span>人</li>
									<li>注册人数</li>
								</ul>
								<ul class="i_apply">
									<img src="${ctx}/static/images/image/yao_3.png" alt=""/>
									<li class="lis"><span class="registNum">0</span>人</li>
									<li>申请方案人数</li>
								</ul>
								<ul class="i_reward">
									<img src="${ctx}/static/images/image/yao_4.png" alt=""/>
									<li class="lis"><span class="awardSum">0</span>次</li>
									<li>已获奖励次数</li>
								</ul>
							</div>
							<div class="xj">
								<p class="p2">邀请好友，免交易手续费！<a href="${ctx}/topic/oldBelt/index.html" target="_blank">活动规则</a></p>
								<p class="p1">多拉新朋友，即可多获减免奖励。比如小明叫了10个朋友来维胜金融操盘，8个进行了实名认证，其中6个人操盘交易均达到双边5手。那么小明就可以获得（累计）：2个1手交易费减免资格，4个2手交易费减免资格，总共 1 + 1 + 2 + 2 + 2 = 2 = 10 手双边免费交易资格。以此类推，多拉新用户多得减免金。</p>
							</div>
<script>
	window._bd_share_config = {
		common : {
			bdText : '自定义分享内容',	
			bdDesc : '自定义分享摘要',	
			bdUrl : 'http://www.vs.com', 	
			bdPic : 'http://www.vs.com/static/images/common-new/new_logo.png'
		},
		share : [{
			"bdSize" : 16
		}]
		/* slide : [{	   
			bdImg : 0,
			bdPos : "right",
			bdTop : 100
		}], */
		/* image : [{
			viewType : 'list',
			viewPos : 'top',
			viewColor : 'black',
			viewSize : '16',
			viewList : ['qzone','tsina','huaban','tqq','renren']
		}] ,
		selectShare : [{
			"bdselectMiniList" : ['qzone','tqq','kaixin001','bdxc','tqf',"weixin"]
		}] */
	};
	with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?cdnversion='+~(-new Date()/36e5)];
</script>
						</div>
					</div>
					<div class="subtab">
						<!-- 全部明细 -->
						<div class="uc_trade">
							<!-- 浮动层 -->
							<div class="uc_floatlayer">
								<!-- 类型 -->
								<div class="uc_bc_option uc_fs_type" id="alltypediv"
									style="display: none;">
									<a href="javascript:void(0)" data-id="">佣金收入</a>
									<a href="javascript:void(0)" data-id="3">佣金收入</a>
									<c:forEach items="${data}" var="type" varStatus="status">
										<a href="javascript:void(0)" data-id="${type['valueKey']}">${type['valueName']}</a>
									</c:forEach>
								</div>
							</div>
							<div class="uc_fsdetails">
								<%-- <div class="uc_fsmoney">
									<p>
										收入<i class="color1" id="incount"><c:choose><c:when test="${!empty infund.count}">${infund.count}</c:when><c:otherwise>0</c:otherwise></c:choose></i>笔，共<i class="color1" id="insummoney"><c:choose><c:when test="${!empty infund.summoney}"><fmt:formatNumber value="${infund.summoney}" pattern="##.##" minFractionDigits="2"></fmt:formatNumber></c:when><c:otherwise>0.00</c:otherwise></c:choose></i>元
									</p>
									<p>
										支出<i class="color5" id="outcount"><c:choose><c:when test="${!empty outfund.count}">${outfund.count}</c:when><c:otherwise>0</c:otherwise></c:choose></i>笔，共<i class="color5" id="outsummoney"><c:choose><c:when test="${!empty outfund.summoney}"><fmt:formatNumber value="${outfund.summoney}" pattern="##.##" minFractionDigits="2"></fmt:formatNumber></c:when><c:otherwise>0.00</c:otherwise></c:choose></i>元
									</p>
								</div> --%>
								<div class="uc_fssearch">
									<em>手机后4位：</em> 
									<input type="text" class="" id="iphone" name="iphone" value="" maxlength="4" style="padding: 0; padding-left: 5px;">
									<!-- <em style="margin-left: 5px;">收支类型：</em> 
									<input type="text" class="uc_fs_ip" id="fundtype" name="fundtype" value="佣金收入">
									<a href="javascript:void(0)" id="alltype" class="uc_fssdown uc_fsiptype"></a>  -->
									<em>时间：</em> <input style="width: 100px;" type="text" name="allstarttime" readonly="readonly" onclick="WdatePicker();" id="allstarttime" class="uc_fsiptime">
									<i>—</i> <input type="text" style="width: 100px;" name="allendtime" id="allendtime" readonly="readonly" onclick="WdatePicker();" class="uc_fsiptime"> 
									<span><a href="javascript:void(0)" onclick="findAllData('Searchresult','Pagination');">查询</a></span>
								</div>
							</div>
							<ul class="uc_fslist">
								<ol class="uc_fsl_title">
									<li class="uc_fsl165" style="width: 150px;">手机号码</li>
									<li class="uc_fsl200" style="width: 50px;">姓名</li>
									<li class="uc_fsl100" style="width: 200px;">注册时间</li>
									<li class="uc_fsl100" style="width: 200px;">交易时间</li>
									<li class="uc_fsl100" style="width: 100px;">交易手数</li>
								</ol>
								<div>
									<div id="Searchresult"></div>
								</div>
							</ul>
							<div class="uc_tpage uc_fspage">
								<div id="Pagination"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../common/footer.jsp"%>
	<%@ include file="../common/dsp.jsp"%>
</body>
<script>

</script>
</html>