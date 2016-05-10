/**
 * 所有的ajax提交都通过此方法，返回json对象，统一错误处理
 * @param url 提交url，必填
 * @param dataType 数据类型，非必填
 * @param data 提交数据，非必填
 * @param async 同步标志，非必填
 * @param error ajax错误处理，非必填
 * @param success ajax回调函数，必填
 * @param beforeSend ajax开始发送前回调函数，参数function (XMLHttpRequest) ,非必填
 * @param complete ajax请求结束回调函数，无论成功与否都进入，参数function (XMLHttpRequest, textStatus),非必填
 * @param loading 加载遮罩参数，默认{tip:'加载数据中...', mask:true, ajaxLoadingImg:true}, 非必填
 * @param dateCols 指定需要转换格式的时间列数组集合, 如['birthday','endDate'], 非必填
 * @param isTip	是否需要遮罩效果,默认为[true]需要。
 */
var IS_AJAX="1";

function ajaxPost(config){
	config.isTip = (config.isTip) ? true : false;
	var data = config.data ? config.data : {};
	data["ajax"] = IS_AJAX;
	$.ajax({
		url: config.url,
		type: 'post',
		cache:config.cache === true?true:false,
		dataType: config.dataType?config.dataType:'json',
		data: data,
		async: config.async === false?false:true,
		error: function(XMLHttpRequest, textStatus, errorThrown){
//			$.fn.Dialogue.close();
			// 全局错误机制
			if(config.error){
				alert(textStatus+"|"+errorThrown);
				config.error(XMLHttpRequest, textStatus, errorThrown);
			}else{
				//TODO 测试前咔掉alert
//				alert(config.url+"\n"+XMLHttpRequest+"\n"+textStatus+"\n"+errorThrown);
				//add by bhu 2011-09-24	增加全局error错误处理机制，提示联系管理员
				//MsgBox.fail({title:"失败", content:"系统出现未知错误,请重新尝试该次操作。\n如多次操作失败,请联系系统管理员!"});
			}
		},
		beforeSend: function(XMLHttpRequest){
			if(config.isTip){
				if(config.loading){
//					$.fn.Dialogue(config.loading);
				}
				else{
//					$.fn.Dialogue({tip:'加载数据中...', mask:true, ajaxLoadingImg:true});
				}
			}
			if(config.beforeSend){
				config.beforeSend(XMLHttpRequest);
			}
		},
		complete: config.complete? config.complete : null,
		success: function(responseData){
			//超时的处理 返回登录页面
			if(responseData && responseData.statusCode && (responseData.statusCode == -100000000)){
				
				alert("xxxxxxx");
				//document.location.href = basePath+"login.jsp";
			}
			config.success(responseData);
		}
	});
}



function clearNoNum(event,obj){ 
    //响应鼠标事件，允许左右方向键移动 
    event = window.event||event; 
    if(event.keyCode == 37 || event.keyCode == 39){ 
        return; 
    } 
    //先把非数字的都替换掉，除了数字和. 
    obj.value = obj.value.replace(/[^\d.]/g,""); 
    //必须保证第一个为数字而不是. 
    obj.value = obj.value.replace(/^\./g,""); 
    //保证只有出现一个.而没有多个. 
    obj.value = obj.value.replace(/\.{2,}/g,"."); 
    //保证.只出现一次，而不能出现两次以上 
    obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$","."); 
} 

function clearNotNumber(event,obj){ 
	   //响应鼠标事件，允许左右方向键移动 
    event = window.event||event; 
    if(event.keyCode == 37 || event.keyCode == 39){ 
        return; 
    } 
    //先把非数字的都替换掉，除了数字和. 
    obj.value = obj.value.replace(/[^\d]/g,""); 
    if(obj.value.length==1){
    	obj.value=obj.value.replace(/[^1-9]/g,'')
    }else{
    	obj.value=obj.value.replace(/\D/g,'')
    }
    
  }
	

function clearNoNumber(event,obj){ 
    //响应鼠标事件，允许左右方向键移动 
    event = window.event||event; 
    if(event.keyCode == 37 || event.keyCode == 39){ 
        return; 
    } 
    //先把非数字的都替换掉，除了数字和. 
    obj.value = obj.value.replace(/[^\d]/g,""); 
    if(obj.value.length==1){
    	obj.value=obj.value.replace(/[^1-9]/g,'')
    }else{
    	obj.value=obj.value.replace(/\D/g,'')
    }
  }

function clearNoDouble(event,obj){ 
    //响应鼠标事件，允许左右方向键移动 
    event = window.event||event; 
    if(event.keyCode == 37 || event.keyCode == 39){ 
        return; 
    } 
    
    obj.value = obj.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符   
    obj.value = obj.value.replace(/^\./g,"");  //验证第一个字符是数字而不是.   
    obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的   
    obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");   
    obj.value=obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');//只能输入两个小数    
  }


function clearCodeNum(event,obj){ 
    //响应鼠标事件，允许左右方向键移动 
    event = window.event||event; 
    if(event.keyCode == 37 || event.keyCode == 39){ 
        return; 
    } 
    //先把非数字的都替换掉，除了数字和. 
    obj.value = obj.value.replace(/[^\d]/g,""); 
    if(obj.value.length==1){
    	obj.value=obj.value.replace(/[^0-9]/g,'')
    }else{
    	obj.value=obj.value.replace(/\D/g,'')
    }
  }

//判断内核
function getVendorPrefix() { 
	  // 使用body是为了避免在还需要传入元素 
	  var body = document.body || document.documentElement, 
	    style = body.style, 
	    vendor = ['webkit', 'khtml', 'moz', 'ms', 'o'], 
	    i = 0; 
	  
	  while (i < vendor.length) { 
	    // 此处进行判断是否有对应的内核前缀 
	    if (typeof style[vendor[i] + 'Transition'] === 'string') { 
	      return vendor[i]; 
	    } 
	    i++; 
	  } 
	}

//是否是银行卡
isBankCard=function(cardNumber){
	var reg = new RegExp("^[0-9]*$");   
	if(cardNumber.length<10){
		return false;
	}else if(!reg.test(cardNumber)){
	      return false;
	  }
	
	return true;
	//return cardNumber&&/^(\d{16}|\d{19})$/.test(cardNumber);
}

//是否是金额
isMoney=function(data,isPositive){
	return isPositive?/^\d+(\.\d{1,2})?$/.test(data)&&parseFloat(data)>0:/^(-)?\d+(\.\d{1,2})?$/.test(data);
}

function formatMoney(s, type) { 
	if (/[^0-9\.]/.test(s)) 
	 return "0"; 
	if (s == null || s == "") 
	  return "0"; 
	s = s.toString().replace(/^(\d*)$/, "$1."); 
	s = (s + "00").replace(/(\d*\.\d\d)\d*/, "$1"); 
	s = s.replace(".", ","); 
	var re = /(\d)(\d{3},)/; 
	while (re.test(s)) 
		s = s.replace(re, "$1,$2"); 
		s = s.replace(/,(\d\d)$/,".$1"); 
	if (type == 0) {// 不带小数位(默认是有小数位) 
		var a = s.split("."); 
		if (a[1] == "00") { 
			s = a[0]; 
		} 
	} 
	return s; 
	}
var browser={     
		versions:function(){             
			var u = navigator.userAgent, 
			 app = navigator.appVersion;             
			return {                 
				trident: u.indexOf('Trident') > -1, //IE内核                 
				presto: u.indexOf('Presto') > -1, //opera内核                 
				webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核                 
				gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核                 
				mobile: !!u.match(/AppleWebKit.*Mobile.*/)||!!u.match(/AppleWebKit/), //是否为移动终端                 
				ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端                 
				android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器                 
				iPhone: u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1, //是否为iPhone或者QQHD浏览器                 
				iPad: u.indexOf('iPad') > -1, //是否iPad                 
				webApp: u.indexOf('Safari') == -1 //是否web应该程序，没有头部与底部             
			}; 
		}() 
	}  

function getUrlParameter(){
	var url=location.search;
	if(url){
		var params="";
		var str=url.substr(url.indexOf("?") + 1);
		var strs = str.split("&");
		 for(var i = 0; i < strs.length; i ++)
	    {
			 if(strs[i].split("=")[1]){
				 params+=strs[i].split("=")[0]+"="+decodeURIComponent(strs[i].split("=")[1])+"&";
			 }
			 
	    }
		return params;
	}else{
		return "";
	}
}
