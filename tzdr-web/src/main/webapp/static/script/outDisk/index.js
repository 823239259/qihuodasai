//保证金【人民币】
var traderBond;
$(document).ready(function(){
	$('.navlist li a').removeClass('on');
	$("#internationalFutures").addClass("on");

	
	/**
	 * 绑定保证金点击事件
	 */
	$('.cplx_mianlist li').live("click",function() { 
		$('.cplx_mianlist li.on').removeClass('on');
		$(this).addClass('on');
		traderBond = Number($(this).attr("data"));
		$("#traderBondAttr").val(traderBond);
		getData();
	});
	
	
	
});
function getData(){
	$.post(basepath + "outDisk/data.json", {
		traderBond : traderBond
	}, function(result) {
		if (result.success) {
			// 设置总操盘资金、平仓线、手数显示
			$("#traderBond").html($.formatMoney(result.obj.traderBond));
			$("#traderTotal").html($.formatMoney(result.obj.traderTotal));
			$("#lineLoss").html($.formatMoney(result.obj.lineLoss));
			$("#ATranActualLever").html($.formatMoney(result.obj.ATranActualLever));
			$("#HTranActualLever").html($.formatMoney(result.obj.HTranActualLever));
			$("#YTranActualLever").html(result.obj.YTranActualLever);
			
			$("#mntranActualLever").html(result.obj.mntranActualLever);
			$("#mbtranActualLever").html(result.obj.mbtranActualLever);
			$("#daxtranActualLever").html(result.obj.daxtranActualLever);
			$("#mdtranActualLever").html(result.obj.mdtranActualLever);
			$("#nikkeiTranActualLever").html(result.obj.nikkeiTranActualLever);
			$("#hstranActualLever").html(result.obj.hstranActualLever);
			$("#agtranActualLever").html(result.obj.agtranActualLever);
			$("#hIndexActualLever").html(result.obj.hIndexActualLever);
			$("#xhIndexActualLever").html(result.obj.xhIndexActualLever);
			$("#aCopperActualLever").html(result.obj.aCopperActualLever);
			$("#aSilverActualLever").html(result.obj.aSilverActualLever);
			$("#smaActualLever").html(result.obj.smaActualLever);
		} else {
			showMsgDialog("提示",result.message);
		}
	}, "json");
}


function submitSetting() {
	if ($("#checkbox_agree").attr("checked")) {
		if(!isLoginSSO){
			window.location.href=basepath+"/toOutDiskIndexSSO"; 
		}else{ 
    		$.easyui.submitForm("settingForm");
		} 
	}
	else {
		showMsgDialog("提示","请勾选\"我已阅读并同意《国际期货综合操盘合作协议》\"");
		return false;
	}
};

function lookContract(){
	var htmlHeight = 800;  //网页高度
	var htmlWidth = 1221;  //网页宽度
	var iTop = (window.screen.height-30-htmlHeight)/2; //获得窗口的垂直位置;  
	var iLeft = (window.screen.width-10-htmlWidth)/2;  //获得窗口的水平位置;  
	window.open(basepath+'static/outDisk/outDisk.html','国际期货综合操盘合作协议','height='+htmlHeight+',innerHeight='+htmlHeight+',width='+htmlWidth+',innerWidth='+htmlWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizeable=no,location=no,status=no');  
}