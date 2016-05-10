
$(document).ready(function() {
	
	$('#navlist a').removeClass('cur');
	$("#stockli").addClass("cur");
	 
	/**
    * 计算利息和管理费
    * @param obj
    * @param minmoney 最小融资金额
    * @param maxmoney 最大融资金额
    * @param id 
    * @param minMultiple 最小融资倍数
    * @param maxMultiple 最大融资倍数
    */
	$(".inputno").keypress(T.numKeyPress).keyup(function() {
		var minmoney=$(this).attr("minmoney");
		var maxmoney=$(this).attr("maxmoney");
		var id=$(this).attr("idstr");
		var minMultiple=$(this).attr("minMultiple");
		var maxMultiple=$(this).attr("maxMultiple");
		var minDuration=$(this).attr("minDuration");
		var maxDuration=$(this).attr("maxDuration");
		var interest=$(this).attr("interest");
		var expenese=$(this).attr("expenese");
		var value=$(this).val();
	 	  if(value!="" ){
	 		 value=getMoney(value);
	 		if(Number(value)>Number(maxmoney)){
	 			value=maxmoney;
	 		}
	 		if(Number(value)>=Number(minmoney) && minMultiple==maxMultiple){
	 			$("#zcpj"+id).html($.formatMoney(Number(value)*minMultiple+Number(value))+"元");
	 		}
	 		
	 		$(this).val($.formatMoney(value));
	 		var leverdata=getLever(id);
	 		if(maxMultiple==minMultiple){
	 			leverdata=maxMultiple;
	 		}
	 		var useday=getuseday(id);
	 		if(minDuration==maxDuration){
	 			useday=minDuration;
	 		}
	 		value=getMoney(value);
	 		if(Number(value)>=Number(minmoney) &&!isNaN(leverdata) && !isNaN(useday)){
	 			getmaxlever(value,leverdata,id);
	 			$("#zcpj"+id).html($.formatMoney(Number(value)*leverdata+Number(value))+"元");
	 			getTradeData(id,useday,leverdata,Number(value),"0",interest,expenese);
	 		}else{
	 			if(!(Number(value)>=Number(minmoney) &&!isNaN(leverdata))){
	 				initLinkageData(id,0);
	 			}else{
	 				$("#zcpj"+id).html($.formatMoney(Number(value)*leverdata+Number(value))+"元");
	 			}
	 		}
	 	 }
	});
	
	$('.pzmultiple_select').live('change',function(){ 
		var $this = $(this);
		var id = $this.attr('iddata');
		var minmoney = $this.attr('minBond');
		var maxmoney = $this.attr('maxBond');
		var minMultiple = $this.attr('minMultiple');
		var maxMultiple = $this.attr('maxMultiple');
		var minDuration = $this.attr('minDuration');
		var maxDuration = $this.attr('maxDuration');
		var interest = $this.attr('interest');
		var expenese = $this.attr('expenese');
		var type = $this.attr('type');
		if(!isNaN($this.val())){
			setChangeMoney($this,minmoney,maxmoney,id,minMultiple,maxMultiple,minDuration,maxDuration,
					  interest,expenese,type);
		}else{
			initLinkageData(id,1);
		}
	});
	
	$('.daymultiple_select').live('change',function(){ 
		var $this = $(this);
		var id = $this.attr('iddata');
		var minmoney = $this.attr('minBond');
		var maxmoney = $this.attr('maxBond');
		var minMultiple = $this.attr('minMultiple');
		var maxMultiple = $this.attr('maxMultiple');
		var minDuration = $this.attr('minDuration');
		var maxDuration = $this.attr('maxDuration');
		var interest = $this.attr('interest');
		var expenese = $this.attr('expenese');
		var type = $this.attr('type');
		if(!isNaN($this.val())){
			setChangeMoney($this,minmoney,maxmoney,id,minMultiple,maxMultiple,minDuration,maxDuration,
					  interest,expenese,type);
		}else{
			initLinkageData(id,2);
		}
	});
});

function moneyValueFocus(obj,id,minMoney,MaxMoney){
	    var money=obj.value;
	    var value=minMoney+"—"+MaxMoney;
	    $(obj).removeClass("color");
		if(money==value||money.indexOf("万")>0){
			obj.value="";
		}
  }

//返回金额提示设置
function resetmoeny(obj,minmoney,maxmoney,id,minmoneystr,maxmoneystr){
	  var money=obj.value;
	  $(obj).removeClass("color");
	  if(minmoneystr!="" && money==""){
		  $(obj).addClass("color");
		  $("#money"+id).val(minmoneystr+"—"+maxmoneystr);
	  }else{
		  if(money==""){
			  $(obj).addClass("color");
			  $("#money"+id).val(minmoney+"—"+maxmoney);
		  } 
	  }
}

function getMoney(money){
	return $.trim(money).replace(/\,/g,"");
}

//获取倍数
function getLever(id){
	  var levernum=$("#pzmultiple"+id).val();
	  return levernum;
}

//获取天数
function getuseday(id){
	  var levernum=$("#daymultiple"+id).val();
	  return levernum;
}

//初始化联动数据
function initLinkageData(id,type){
	if(type == 0 || type == 1){
		$("#zcpj"+id).html("元");
	}
	var lx = $("#lx"+id);
	if(lx){
		lx.html("--元");
	}
	var pzglf = $("#pzglf"+id);
	if(pzglf){
		var pzglfyj = $("#pzglfyj"+id);
		var text = "--元/交易日";
		if(pzglfyj){
			text += "<em id='pzglfyj'"+id+">(原 -- 元/交易日)</em>";
		};
		pzglf.html(text);
	}
}

function getmaxlever(capitalMargin,lever,id){
	
	  var minPzjeMoney = Number(1500);
	  $.post(basepath + "maxLever.json", {		
			capitalMargin : capitalMargin
		}, function(result) {
			if (result.success) {	
				var pzbzj = $.isNumeric(capitalMargin)?capitalMargin:0;
				var minpzgg = Math.ceil(minPzjeMoney / pzbzj);
				var maxpzgg = Number(result.obj);
				if (maxpzgg >= 15) {
					maxpzgg = 15;
				}
				if(minpzgg==maxpzgg){
					$('#min_lever'+id).val(minpzgg);
				}else{
					$('#min_lever'+id).val(minpzgg);
					$('#max_lever'+id).val(maxpzgg);	
				}
			} else {
				showMsgDialog("提示",result.message);
			}
		}, "json");
}

/**
 * 
 * @param borrowPeriod 融资天数
 * @param lever 融资杆杆
 * @param capitalMargin 保证金
 * @param tradeStart 交易日标识
 */
function getTradeData(id,borrowPeriod,lever,capitalMargin,tradeStart,interest,expenese) {

  $.post(basepath + "data.json", {
				borrowPeriod : borrowPeriod,
				lever : lever,
				capitalMargin : capitalMargin,
				tradeStart:tradeStart,
				interest:interest,
				expenese:expenese
			}, function(result) {
				if (result.success) {
					if(result.data.interestFee!="0"){
						$("#lx"+id).html(($.formatMoney(Number(result.data.interestFee*borrowPeriod).toFixed(2)))+"元");
					}
					if(result.data.manageFee!="0"){
						var text = $.formatMoney(Number(result.data.manageFee))+" 元/交易日";
						var pzglfyj = $("#pzglfyj"+id);
						if(pzglfyj && expenese){
							text +="<em id='pzglfyj'"+id+">(原 "+$.formatMoney(Number(result.data.manageFee*100/expenese))+" 元/交易日)</em>";
						}
						$("#pzglf"+id).html(text);
					}
				} else {
					showMsgDialog("提示",result.message);
				}
			}, "json");
 
}

/**
 * 获取金额
 * @param obj
 * @param minmoney
 * @param maxmoney
 * @param id
 * @param minMultiple
 * @param maxMultiple
 */
function setChangeMoney(obj,minmoney,maxmoney,id,minMultiple,maxMultiple,minDuration,maxDuration,
		  interest,expenese,type){
	  var useday="";
		var leverdata="";
		var money=$("#money"+id).val();
		if(type=="2"){
			useday=obj.val();
			leverdata=getLever(id);
		}else if(type=="1"){
			leverdata=obj.val();
			useday=getuseday(id);
		}
		if(minMultiple==maxMultiple){
			leverdata=minMultiple;
		}
		if(minDuration==maxDuration){
			useday=minDuration;
		}
		money=getMoney(money);
		if(Number(money)>=Number(minmoney) &&!isNaN(leverdata) ){
			 getmaxlever(money,leverdata,id);
			$("#zcpj"+id).html($.formatMoney(Number(money)*leverdata+Number(money))+"元");
		}
		
		if(Number(money)>=Number(minmoney) &&!isNaN(leverdata) && !isNaN(useday)){
			getTradeData(id,useday,leverdata,Number(money),"0",interest,expenese);
		}
}

/**
 * 到融资页面
 * @param id
 * @param minBond 最低保证金
 * @param maxBond 最大保证金
 * @param minMultiple 最小倍数
 * @param maxMultiple 最大倍数
 * @param minDuration 最小天数
 * @param maxDuration 最大天数
 */
function  toBespokeTrade(id,minBond,maxBond,minMultiple,maxMultiple,minDuration,maxDuration){
	 
	 var money=$("#money"+id).val();
	 money=getMoney(money);
	 var lever=getLever(id);
	 var useday=getuseday(id);
	 var expenese=$("#expenese"+id).val();
	 var interest=$("#interest"+id).val();
	 if(minMultiple==maxMultiple){
		 lever=minMultiple;
	 }
	 if(minDuration==maxDuration){
		 useday=minDuration;
	 }
	 if(isNaN(money)){
		 showMsgDialog("提示","请输入保证金");
		 return;
	 }
	 if(Number(money)<Number(minBond)||Number(money)>Number(maxBond)){
		 showMsgDialog("提示","输入的保证金在"+minBond+"-"+maxBond+"元");	
		 return;
	 }
	 if(isNaN(lever)){
		 showMsgDialog("提示","请选择放大倍数");
		 return;
	 }
	 if(isNaN(useday)){
		 showMsgDialog("提示","请选择操盘天数");	
		 return;
	 }
	 
	 if(!$("#cpxy"+id).attr('checked')){
		 showMsgDialog("提示","请勾选协议！");
		 return;
	 }
	 
	 $("#tradeConfigId").val(id);
	 $("#tradeStart").val("0");
	 $("#lever").val(lever);
	 $("#capitalMargin").val(money);
	 $("#borrowPeriod").val(useday);
	 $("#interest").val(interest);
	 $("#expenese").val(expenese);
	 
	 //判断操盘金额
	 var zcpj=Number(money)*lever+Number(money);
	 var pzje=Number(money)*lever;
	 if(Number(lever)>0){
			$.post(basepath + "maxAmount.json", {		
				lever : lever
			}, function(result) {
				if (result.success) {
					var maxAmount=Number(result.obj);
					var minPzgg = Number($('#min_lever'+id).val());
					var maxPzgg = $('#max_lever'+id).val()==''?Number($('#min_lever'+id).val()): Number($('#max_lever'+id).val());
					var inputgg=lever;
					if(pzje>maxAmount){
						showMsgDialog("提示",lever+"倍最大操盘金为"+maxAmount+"元,请重新输入保证金");	
						return false;	
					}else{
						if(inputgg>=minPzgg&&inputgg<=maxPzgg){
							 document.forms["bespokeTradeForm"].submit();
						}else{
							showMsgDialog("提示","保证金"+money+"元,放大倍数最小"+minPzgg+"倍"+",最大"+maxPzgg+"倍");	
						}
					}
				} else {
					showMsgDialog("提示",result.message);
				}
			}, "json");
	 }else if(Number(lever)==0){
		 document.forms["bespokeTradeForm"].submit();
	 }
}

function tradeContract(){
	var htmlHeight = 800;  //网页高度
	var htmlWidth = 1221;  //网页宽度
	var iTop = (window.screen.height-30-htmlHeight)/2; //获得窗口的垂直位置;  
	var iLeft = (window.screen.width-10-htmlWidth)/2;  //获得窗口的水平位置;  
	window.open(basepath+'tradeContract','实盘申请合作操盘协议','height='+htmlHeight+',innerHeight='+htmlHeight+',width='+htmlWidth+',innerWidth='+htmlWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizeable=no,location=no,status=no');  
}