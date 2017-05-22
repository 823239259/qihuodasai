(function($) {
	//网络状态的判断
	var network = true;
	/*mui.checkNetworkState=function (){
		var types = {}; 
		types[plus.networkinfo.CONNECTION_UNKNOW] = "Unknown connection"; 
		types[plus.networkinfo.CONNECTION_NONE] = "None connection"; 
		types[plus.networkinfo.CONNECTION_ETHERNET] = "Ethernet connection"; 
		types[plus.networkinfo.CONNECTION_WIFI] = "WiFi connection"; 
		types[plus.networkinfo.CONNECTION_CELL2G] = "Cellular 2G connection"; 
		types[plus.networkinfo.CONNECTION_CELL3G] = "Cellular 3G connection"; 
		types[plus.networkinfo.CONNECTION_CELL4G] = "Cellular 4G connection"; 
			return types;
	}*/
	//检查网络
	mui.checkNetwork = function() {
		if(mui.os.plus) {
			mui.plusReady(function() {
				if(plus.networkinfo.getCurrentType() == plus.networkinfo.CONNECTION_NONE) {
					network = false;
				} else {
					network = true;
				}
				//监听网络状态变化事件
				document.addEventListener("netchange", onNetChange, false);
				//状态栏设置
			});
		}
		return network;
	}
	//检查网络
	function onNetChange() {
		var nt = plus.networkinfo.getCurrentType();
		if(nt == plus.networkinfo.CONNECTION_NONE) {
			network = false;
		} else {
			network = true;
		}
	}
	// 需要认证用户身份的请求调用接口
	mui.app_request = function(func_url, params, onSuccess, onError) {
		if(mui.checkNetwork() == false) {
			mui.toast("当前网络不给力，请稍后再试");
			return;
		}
		var onSuccess = arguments[2] ? arguments[2] : function() {};
		var onError = arguments[3] ? arguments[3] : function() {};
		var func_url = vs.constants.api_domain + func_url;
		mui.ajax(func_url, {
			headers: {
				'token': mui.cacheUser.get(vs.constants.user_token),
				'secret': mui.cacheUser.get(vs.constants.user_secret)
			},
			data: params,
			timeout: 100000,
			dataType: 'JSON', //服务器返回json格式数据
			type: 'post', //HTTP请求类型
			success: function(data) {
				//获得服务器响应
				var data = JSON.parse(data);
				if(data.success) {
					onSuccess(data);
				} else {
					onError(data);
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				if(network == false) {
					mui.toast("当前网络不给力，请稍后再试");
					return;
				}
				mui.toast("当前网络不给力，请稍后再试");
			}
		});
	}

	//用户信息管理
	mui.cacheUser = {
		/**
		 * 清除用户缓存信息
		 */
		clear: function() {
			localStorage.removeItem(vs.constants.user_token);
			localStorage.removeItem(vs.constants.user_secret);
			localStorage.removeItem(vs.constants.user_mobile);
		},
		save: function(token, secret, mobile) {
			localStorage.setItem(vs.constants.user_token, token);
			localStorage.setItem(vs.constants.user_secret, secret);
			localStorage.setItem(vs.constants.user_mobile, mobile);
		},
		get: function(key) {
			return localStorage.getItem(key);
		},
		isLogin: function() {
			var user_token = localStorage.getItem(vs.constants.user_token);
			var user_secret = localStorage.getItem(vs.constants.user_secret);
			if(mui.isnull(user_token) || mui.isnull(user_secret)) {
				return false;
			}
			return true;
		},
		setCach: function(key, value) {
			localStorage.setItem(key, value);
		},
		/**
		 * 清除当前页面以外的其他页面
		 */
		clearCachePages: function(holdPage) {
			// 清除所有缓存页面
			var cachepages = plus.webview.all();
			var curr = plus.webview.currentWebview();
			if(holdPage) {
				mui.each(cachepages, function(index, item) {
					//alert(item.getURL());
					// 不是当前页和首页的都可以删除
					if(item.getURL().indexOf('startPage.html') < 0) {
						plus.webview.close(item);
					}
				});
				return;
			}
			mui.each(cachepages, function(index, item) {
				//alert(item.getURL());
				// 不是当前页和首页的都可以删除
				if(item.getURL() != curr.getURL() && item.getURL().indexOf('start.html') < 0) {
					plus.webview.close(item);
				}
			});
		}
	}

	//校验非空,true为空；false不空
	mui.isnull = function(data) {
		//alert(data);  
		return data == null || data == "" || typeof(data) == "undefined" || data.length == 0 ? true : false;
	}
	/**
	 * 呼叫弹出框
	 */
	mui.callService=function(){
		plus.nativeUI.confirm("工作日 8:30-24:00  周末 9:00-17:00",function(e){
			if(e.index==1){
				plus.device.dial("4008528008",false);
			}
		},
		"400-852-8008",["取消","呼叫"]);
    }
	/**
	 * 获取文件所在环境具体位置 
	 * @param {Object} file_url 文件及文件所在位置    如：vs/login/login.html
	 */
	mui.app_filePath = function(file_url) {
		var path = plus.io.convertLocalFileSystemURL('_www/' + file_url);
		var filePath = plus.io.convertAbsoluteFileSystem(path);
		return filePath;
	}
	/**
	 * 放回指定页面，并且刷新指定页面
	 * @param {Object} pageId 指定页面ID
	 */
	mui.app_refresh = function(pageId) {
		if(!mui.isnull(pageId)) {
			if(vs.browser.versions.Html5Plus){
				var _page = plus.webview.getWebviewById(pageId);
				if(_page) {
					_page.reload(true);
				}
			}else{
				location.reload(true)
			}
		}
	}
	/**
	 * 返回指定页面，并且刷新指定页面
	 * @param {Object} pageId 指定页面ID
	 * @param {Object} isRefresh  是否刷新  如：true=刷新；false=不刷新
	 */
	mui.app_back = function(pageId, isRefresh,url) {
		if(vs.browser.versions.Html5Plus) {
			mui.init({
				beforeback: function() {
					if(!mui.isnull(pageId)) {
						var _page = plus.webview.getWebviewById(pageId);
						if(_page) {
							_page.reload(isRefresh);
						}
					}
					return true;
				}
			});
			mui.back();
		}else{
			//服务器地址+绝对地址
			window.location.href=vs.constants.server_url+url;
		}
	}
	/**
	 * 打开需要传值的页面
	 * @param {Object} pageUrl 指定页面地址
	 * @param {Object} pageId 指定页面ID
	 * @param {Object} pageData 传递的data
	 */
	mui.open_window_data = function(pageUrl, pageId, pageData) {
		if(vs.browser.versions.Html5Plus) {
			mui.openWindow({
				"url": pageUrl,
				"id": pageId,
				"extras": {
					pageData: pageData
				}
			});
		} else {
			pageData = JSON.stringify(pageData)
			sessionStorage.setItem(pageId, pageData);
			mui.openWindow({
				"url": pageUrl+"?"+pageId,
				"id": pageId,
			});
		}
	}
	/**
	 * 获取传递的值
	 * @param {Object} pageId 指定页面ID
	 */
	mui.get_window_data = function(pageId) {
		var data = null;
		if(vs.browser.versions.Html5Plus) {
			var currentWebview = plus.webview.currentWebview();
			data = currentWebview.pageData;
		} else {
			var pageId=window.location.search.split("?")[1];
			data = sessionStorage.getItem(pageId);
			data = JSON.parse(data);
		}
		return data;
	}
	/*弹出层*/
	mui.clickLayer=function(_this){
		var id = _this.attr("data-id");
		document.getElementById("chioce").style.display="block";
		document.getElementById(id).style.display="block";
		var height = document.getElementById(id).offsetHeight;
		document.getElementById(id).style.marginTop= "-"+height/2+"px";
    }
})(mui);
//扩展Date的format方法  format格式字符串,例如：yyyy-MM-dd hh:mm:ss
Date.prototype.format = function(format) {
	var o = {
		"M+": this.getMonth() + 1,
		"d+": this.getDate(),
		"h+": this.getHours(),
		"m+": this.getMinutes(),
		"s+": this.getSeconds(),
		"q+": Math.floor((this.getMonth() + 3) / 3),
		"S": this.getMilliseconds()
	}
	if(/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	}
	for(var k in o) {
		if(new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
}
/**
 * 自定义工具类
 */
var vs = {
	// 系统常量
	constants: {
		api_domain: "http://test.api.dktai.cn/",
		//图片地址
		base_images_url: 'http://manage.vs.com/',
		//token
		user_token: 'user_token',
		//密钥 
		user_secret: 'user_secret',
		//用户手机号
		user_mobile: 'user_mobile',
		//服务器地址
		server_url:"http://www.vs.com/",
		//区分appstore，如:false=否，true=是,默认值为false
		is_appstore: false,
		//企业ipa下载地址
		ipa_download_url: 'https://itunes.apple.com/cn/app/wei-sheng-qi-huo/id1140076487?mt=8',
		// 财经日历、7*24小时
		api_calendar: 'http://api.vs.com/'
	},
	/**
	 * 验证手机号是否符合格式要求 
	 * @param {Object} mobile
	 */
	validate_mobile: function(mobile) {
		var mobilePattern = { mobile: /^(((13[0-9])|(14[5-7])|(15[0-9])|(17[0-9])|(18[0-9]))+\d{8})$/ };
		if(!mobilePattern.mobile.test(mobile)) {
			return false;
		}
		return true;
	},

	/**
	 * 验证密码是否符合格式要求
	 * @param {Object} password
	 */
	validate_password: function(password) {
		var passwordPattern = /^(?![0-9]+$)(?![a-zA-Z]+$)[a-zA-Z\d]{6,16}$/; //登录密码校验规则
		if(!passwordPattern.test(password)) {
			return false;
		}
		return true;
	},
	dateUtil: {
		/**
		 *转换日期对象为日期字符串
		 * @param l long值
		 * @param pattern 格式字符串,例如：yyyy-MM-dd hh:mm:ss
		 * @return 符合要求的日期字符串
		 */
		getFormatDate: function(curDate, pattern) {
			if(mui.isnull(curDate)) {
				curDate = new Date();
			}
			if(mui.isnull(pattern)) {
				pattern = "yyyy-MM-dd";
			}
			return curDate.format(pattern);
		},
		/**
		 * 将long 值转换为对应的日期格式值
		 * @param {Object} longdate
		 * @param {Object} pattern
		 */
		getFormatDataByLong: function(longdate, pattern) {
			if(mui.isnull(longdate)) {
				return;
			}
			if(mui.isnull(pattern)) {
				pattern = "yyyy-MM-dd";
			}
			return vs.dateUtil.getFormatDate(new Date(longdate * 1000), pattern);
		}
	},
	moneyUtils: {
		//匹配是否小数点后两位数
		testDecimal: function(num) {
			var reg = /^-?\d+\.?\d{0,2}$/;
			if(!mui.isnull(num) && reg.test(num)) {
				return true;
			}
			return false;
		},
		//num表示要四舍五入的数,v表示要保留的小数位数。
		decimal: function(num, v) {
			var vv = Math.pow(10, v);
			return Math.round(num * vv) / vv;
		},
		/** 
		 * 将数值四舍五入(保留n位小数)后格式化成金额形式 
		 * 
		 * @param s 数值(Number或者String) 
		 * @param n 保留小数位(Number或者String) 不传默认2位  整数可不传
		 * @return 金额格式的字符串,如'1,234,567.45' 
		 * @type String 
		 */
		formatCurrency: function(s, n) {
			var num = s;
			s = Math.abs(s);
			n = n > 0 && n <= 20 ? n : 2;
			s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
			var l = s.split(".")[0].split("").reverse(),
				r = s.split(".")[1];
			t = "";
			for(i = 0; i < l.length; i++) {
				t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
			}

			var result = num;
			if(n == 0 || num.toString().indexOf(".") == -1) {

				result = t.split("").reverse().join("");
			} else {
				result = t.split("").reverse().join("") + "." + r;
			}

			if(num < 0) {
				result = "-" + result;
			}
			return result;
		},
		/**
		 * 金额数值去掉','号
		 * @param s 数值(String) 
		 * @return float 
		 */
		reverseMoney: function(s) {
			return parseFloat(s.replace(/,/g, ''));
		},
		removeY: function(s) {
			return s.substring(1);
		}
	},
	browser: {
		versions: function() {
			var u = navigator.userAgent,
				app = navigator.appVersion;
			return { //移动终端浏览器版本信息   
				trident: u.indexOf('Trident') > -1, //IE内核  
				presto: u.indexOf('Presto') > -1, //opera内核  
				webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核  
				gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核  
				mobile: !!u.match(/AppleWebKit.*Mobile.*/), //是否为移动终端  
				ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端  
				android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器  
				iPhone: u.indexOf('iPhone') > -1, //是否为iPhone或者QQHD浏览器  
				iPad: u.indexOf('iPad') > -1, //是否iPad    
				webApp: u.indexOf('Safari') == -1, //是否web应该程序，没有头部与底部  ，
				Html5Plus: u.indexOf("Html5Plus") > -1 //是否为html5环境
			};
		}(),
		language: (navigator.browserLanguage || navigator.language).toLowerCase()
	},
	//绑定客服热线的点击事件
	callService : function(){
		document.getElementById("telBtn").addEventListener("tap",function(){
		mui.callService();
	})
	},
	//绑定在线客服的点击事件
	online:function(){
		document.getElementById("online").addEventListener("tap",function(){
			if(vs.browser.versions.Html5Plus){
				var path=mui.app_filePath("online.html");
				mui.openWindow(path,"online"); 
			}else{
				
			}
			
		});
	},
	clickLayer : function(){
		document.getElementById("click-alert").addEventListener("tap",function(){
			var _this = $(this);
			var id = _this.attr("data-id");
			document.getElementById("chioce").style.display="block";
			document.getElementById(id).style.display="block";
			var height = document.getElementById(id).offsetHeight;
			document.getElementById(id).style.marginTop= "-"+height/2+"px";
		});
		document.getElementById("popupButton").addEventListener("tap",function(){
			var _this = $(this);
			var id = _this.attr("data-id");
			document.getElementById("chioce").style.display="none";
			document.getElementById(id).style.display="none";
		});
		
	}
}

function initBottom(url1, url2, url3, url4) {
	document.getElementById("account").addEventListener("tap", function() {
		if(mui.cacheUser.isLogin()) {
			mui.openWindow({ url: url1, id: 'account' });
			return;
		}
		mui.openWindow(url1, "account");
	});
	document.getElementById("find").addEventListener("tap", function() {
		if(mui.cacheUser.isLogin()) {
			mui.openWindow({ url: url2, id: "find" });
			return;
		}
		mui.openWindow({ url: url2, id: "find" });

	});
	document.getElementById("quoteTrade").addEventListener("tap", function() {
		mui.openWindow({ url: url3, id: "quoteTrade" });
	});

	document.getElementById("directSeed").addEventListener("tap", function() {
		mui.openWindow({ url: url4, id: "directSeed" });
	});

}

/**
 * 短信倒计时 60s
 * @param {Object} o  点击获取验证码对象
 */
var time=60;
vs.smsTime=function(o){
    if (time == 0) {  
        o.removeAttribute("disabled");            
        o.innerHTML="获取验证码";  
        time = 60;  
       o.style.background="#fff";
         o.style.color="#ffb319";
    } else {  
        o.setAttribute("disabled", true);  
        o.innerHTML="倒计时(" + time + ")";  
        time--;  
       o.style.background="#33333";
         o.style.color="#ffb319";
        setTimeout(function() {  
            vs.smsTime(o);  
        },  
        1000)  
    } 
}

