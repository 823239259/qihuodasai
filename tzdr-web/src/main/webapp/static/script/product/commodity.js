//保证金【人民币】
var traderBond;
$(document).ready(function(){
	
	$('.navlist li a').removeClass('on');
	$("#productli").addClass("on");
	/**
	 * 绑定保证金点击事件
	 */
	$('.cplx_mianlist li').live("click",function() { 
		$('.cplx_mianlist li.on').removeClass('on');
		$(this).addClass('on');
		traderBond = Number($(this).attr("data"));
		//alert(traderBond);
		$("#traderBondAttr").val(traderBond);
		getData();
	});
	
	
	
});
function getData(){
	$.post(basepath + "commodity/data.json", {
		traderBond : traderBond
	}, function(result) {
		if (result.success) {
			// 设置总操盘资金、平仓线、手数显示
			/*$("#traderBond").html($.formatMoney(result.obj.traderBond));
			$("#traderTotal").html($.formatMoney(result.obj.traderTotal));
			$("#lineLoss").html($.formatMoney(result.obj.lineLoss));*/
			$("#traderBond").html(result.obj.traderBond);
			$("#traderTotal").html(result.obj.traderTotal);
			$("#lineLoss").html(result.obj.lineLoss);
			
			$("#silverLever").html(result.obj.silverLever);
			$("#aluminumLever").html(result.obj.aluminumLever);
			$("#goldLever").html(result.obj.goldLever);
			$("#asphaltLever").html(result.obj.asphaltLever);
			$("#copperLever").html(result.obj.copperLever);
			$("#coilLever").html(result.obj.coilLever);
			$("#nickelLever").html(result.obj.nickelLever);
			$("#rebarLever").html(result.obj.rebarLever);
			$("#zincLever").html(result.obj.zincLever);
			$("#rubberLever").html(result.obj.rubberLever);
			
			$("#beanLever").html(result.obj.beanLever);
			$("#cornLever").html(result.obj.cornLever);
			$("#cornStarchLever").html(result.obj.cornStarchLever);
			$("#ironOreLever").html(result.obj.ironOreLever);
			$("#cokeLever").html(result.obj.cokeLever);
			$("#eggLever").html(result.obj.eggLever);
			$("#cokingCoalLever").html(result.obj.cokingCoalLever);
			$("#plasticLever").html(result.obj.plasticLever);
			$("#soybeanMealLever").html(result.obj.soybeanMealLever);
			$("#palmOilLever").html(result.obj.palmOilLever);
			$("#polypropyleneLever").html(result.obj.polypropyleneLever);
			$("#soybeanOilLever").html(result.obj.soybeanOilLever);
			
			$("#cottonLever").html(result.obj.cottonLever);
			$("#glassLever").html(result.obj.glassLever);
			$("#methanolLever").html(result.obj.methanolLever);
			$("#rapeOilLever").html(result.obj.rapeOilLever);
			$("#rapeseedMealLever").html(result.obj.rapeseedMealLever);
			$("#sugarLever").html(result.obj.sugarLever);
			$("#pTALever").html(result.obj.pTALever);
			$("#powerCoalLever").html(result.obj.powerCoalLever);
			
			$("#fiveNationalDebtLever").html(result.obj.fiveNationalDebtLever);
			$("#tenNationalDebtLever").html(result.obj.tenNationalDebtLever);
			
			//alert(result.obj.aluminumLever);
			/*$("#ATranActualLever").html($.formatMoney(result.obj.ATranActualLever));
			$("#HTranActualLever").html($.formatMoney(result.obj.HTranActualLever));
			$("#YTranActualLever").html(result.obj.YTranActualLever);
			
			$("#mntranActualLever").html(result.obj.mntranActualLever);
			$("#mbtranActualLever").html(result.obj.mbtranActualLever);
			$("#daxtranActualLever").html(result.obj.daxtranActualLever);
			$("#mdtranActualLever").html(result.obj.mdtranActualLever);
			$("#nikkeiTranActualLever").html(result.obj.nikkeiTranActualLever);*/
			
			
			
		} else {
			showMsgDialog("提示",result.message);
		}
	}, "json");
}



function submitSetting() {
	if ($("#checkbox_agree").attr("checked")) {
		if(!isLoginSSO){
			window.location.href=basepath+"/toCommodityAllIndexSSO"; 
		}else{ 
    		$.easyui.submitForm("settingForm");
			//$("#settingForm").submit();
		} 
	}
	else {
		showMsgDialog("提示","请勾选\"我已阅读并同意《商品期货综合操盘合作协议》\"");
		return false;
	}
};

function lookContract(){
	var htmlHeight = 800;  //网页高度
	var htmlWidth = 1221;  //网页宽度
	var iTop = (window.screen.height-30-htmlHeight)/2; //获得窗口的垂直位置;  
	var iLeft = (window.screen.width-10-htmlWidth)/2;  //获得窗口的水平位置;  
	window.open(basepath+'static/product/commodity.html','商品期货综合操盘合作协议','height='+htmlHeight+',innerHeight='+htmlHeight+',width='+htmlWidth+',innerWidth='+htmlWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizeable=no,location=no,status=no');  
}