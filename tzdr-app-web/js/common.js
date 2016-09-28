(function($) {
	// 校验网络 
	var network = true;
	mui.checkNetwork = function(){
		if(mui.os.plus){
			mui.plusReady(function () {
				if(plus.networkinfo.getCurrentType()==plus.networkinfo.CONNECTION_NONE){
					network = false;
				}else
				{
					network = true;
				}
				//监听网络状态变化事件
				document.addEventListener("netchange",onNetChange,false);
				//状态栏设置
				setstatus();
			}); 
		}
		return network;
	}
	
	function onNetChange() {
		var nt = plus.networkinfo.getCurrentType();
		if(nt==plus.networkinfo.CONNECTION_NONE){
			network = false;
		}else{
			network = true;
		}
	}
	
	
	function setstatus(){
		if(plus.navigator.isFullscreen()){
			plus.navigator.setFullscreen(false);
		}
		
	};
	
	
	
	// 需要认证用户身份的请求调用接口
	mui.app_request= function (func_url,params,onSuccess,onError){
		if(mui.checkNetwork()==false){ 
			mui.toast("当前网络不给力，请稍后再试"); 
			return;
		}
	    //plus.nativeUI.showWaiting('正在努力加载中...'); 
		var onSuccess = arguments[2]?arguments[2]:function(){};
		var onError = arguments[3]?arguments[3]:function(){};
		var func_url = tzdr.constants.api_domain + func_url;
		//http://api.dktai.com/+
		
		mui.ajax(func_url,{  
			headers:{
				'token':mui.cacheUser.get(tzdr.constants.user_token),
				'secret':mui.cacheUser.get(tzdr.constants.user_secret)   
			},
			data:params,
			timeout:60000,
			dataType:'json',//服务器返回json格式数据
			type:'post',//HTTP请求类型
			success:function(data){ 
				// plus.nativeUI.closeWaiting();
			    //获得服务器响应
			    
			    if (data.success){
			    	onSuccess(data);
			    }
			    else
			    {
			    	if (data.code==-1){
						mui.cacheUser.clearCachePages(true); 
						mui.cacheUser.clear();
			    		mui.toast("认证失败，请重新登录！");    
			    		mui.openWindow(mui.app_filePath("tzdr/login/login.html"),"login");
						return;
			    	}
			    	onError(data);
			    }
			},
			error: function(XMLHttpRequest, textStatus, errorThrown){ 
				 //plus.nativeUI.closeWaiting();
				if(network==false){
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
		clear:function(){
			plus.storage.removeItem(tzdr.constants.user_token);
      		plus.storage.removeItem(tzdr.constants.user_secret);   
      		plus.storage.removeItem(tzdr.constants.user_mobile);
		},
		save:function(token,secret,mobile){
			plus.storage.setItem(tzdr.constants.user_token,token);
      		plus.storage.setItem(tzdr.constants.user_secret,secret); 
      		plus.storage.setItem(tzdr.constants.user_mobile,mobile);
		},
		get:function(key){
			return plus.storage.getItem(key);
		},
		isLogin:function(){
			var user_token=plus.storage.getItem(tzdr.constants.user_token);
			var user_secret=plus.storage.getItem(tzdr.constants.user_secret);  
			if(mui.isnull(user_token)||mui.isnull(user_secret)){  
				return false;
			}
			return true;
		},
		setCach:function(key,value){
			plus.storage.setItem(key,value);
		},
		/**
		 * 清除当前页面以外的其他页面
		 */
		clearCachePages:function(holdPage){
			// 清除所有缓存页面
      		var cachepages = plus.webview.all();
      		var curr = plus.webview.currentWebview();
      		if (holdPage){
      			mui.each(cachepages,function(index,item){
	      			//alert(item.getURL());
	      			// 不是当前页和首页的都可以删除
	      			if (item.getURL().indexOf('main.html')<0){
	      				plus.webview.close(item);
	      			}
      			});
      			return;
      		}
      		mui.each(cachepages,function(index,item){
      			//alert(item.getURL());
      			// 不是当前页和首页的都可以删除
      			if (item.getURL() != curr.getURL() && item.getURL().indexOf('main.html')<0){
      				plus.webview.close(item);
      			}
      		});
		}
	}
	
	//校验非空,true为空；false不空
	mui.isnull=function(data){
		//alert(data);  
		return data==null || data == "" || typeof(data) == "undefined" || data.length == 0 ? true : false;
	}
	
	
	//呼叫弹出框
    mui.callService=function(){
			plus.nativeUI.confirm("工作日 8:30-24:00  周末 9:00-17:00",function(e){
				if(e.index==1){
					plus.device.dial("4008528008",false);
				}
			},
			"400-852-8008",["取消","呼叫"]);
    }
    

    /**
     * 打开新页面 
     * @param {Object} page_url页面路径
     * @param {Object} pageid 页面id
     */
	mui.openNewPage=function(page_url,pageid){
		
		//判断token和密钥是否存在，不存在则转到登录页面。
		if(!mui.cacheUser.isLogin()){  
			mui.openWindow(mui.app_filePath("tzdr/login/login.html"));
			return;
		}
		mui.openWindow(
				page_url,
				pageid,
				{waiting:{ 
      				autoShow:true,//自动显示等待框，默认为true
      				title:'正在加载...',//等待对话框上显示的提示内容
				}});
	}
	
	/**
	 * 放回指定页面，并且刷新指定页面
	 * @param {Object} pageId 指定页面ID
	 * @param {Object} isRefresh  是否刷新  如：true=刷新；false=不刷新
	 */
	mui.app_back=function(pageId,isRefresh){
		mui.init({
			beforeback:function(){
				if(!mui.isnull(pageId)){
					var _page = plus.webview.getWebviewById(pageId);
					if(_page){
						_page.reload(isRefresh);	
					}
				}
				return true;
			}
    	});
    	mui.back(); 
	} 
	/**
	 * 放回指定页面，并且刷新指定页面
	 * @param {Object} pageId 指定页面ID
	 */
	mui.app_refresh=function(pageId){
		if(!mui.isnull(pageId)){
			var _page = plus.webview.getWebviewById(pageId); 
			if(_page){
				_page.reload(true);	
			}
		}	 
	}
	
	/**
	 * 获取文件所在环境具体位置 
	 * @param {Object} file_url 文件及文件所在位置    如：tzdr/login/login.html
	 */
	mui.app_filePath=function(file_url){
		
		var path=plus.io.convertLocalFileSystemURL('_www/'+file_url);
		
		var filePath = plus.io.convertAbsoluteFileSystem(path);
		
		return filePath;
	}
	
})(mui);

	//扩展Date的format方法
	Date.prototype.format = function (format) {
		var o = {
			"M+": this.getMonth() + 1,
			"d+": this.getDate(),
			"h+": this.getHours(),
			"m+": this.getMinutes(),
			"s+": this.getSeconds(),
			"q+": Math.floor((this.getMonth() + 3) / 3),
			"S": this.getMilliseconds()
		}
		if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
		}
		for (var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
		format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
		}
		}
		return format;
	}


/**
 * 自定义工具类
 */
var tzdr = {
	// 系统常量
	constants:{
		//接口域名地址
		api_domain:"http://192.168.2.174:8080/tzdr-app/",
//		api_domain:"http://test.api.vs.com/",
//		api_domain:"http://api.vs.com/",
		//图片地址
		base_images_url:'http://manage.vs.com/',
		//token
		user_token:'user_token',
		//密钥
		user_secret:'user_secret',
		//用户手机号
		user_mobile:'user_mobile',
		//区分appstore，如:false=否，true=是,默认值为false
		is_appstore:false,
		//企业ipa下载地址
		ipa_download_url:'https://itunes.apple.com/cn/app/wei-sheng-qi-huo/id1140076487?mt=8'
	},
	cacheNews:{
		// 新闻加载日期
		load_news_date:'load_news_date',
		//新闻数据
		news_data:'news_data',
		//已经阅读过的新闻id集合
		readed_nids:'readed_nids',
		getData:function(key){
			return plus.storage.getItem(key);
		},
		setData:function(key,data){
			plus.storage.setItem(key,data);  
		},
		clearData:function(key){
			plus.storage.removeItem(key);
		},
		/**
		 * 判断是否已读
		 * @param {Object} nid
		 * return false 未读  true已读
		 */
		isread:function(nid){
			var  readed_nids = tzdr.cacheNews.getData(tzdr.cacheNews.readed_nids);
			if(mui.isnull(readed_nids)){
				return false;
			}
			var readed_nids_array =  readed_nids.split(",");
			if (readed_nids_array.indexOf(nid)<0){
				return false;
			}
			return true;
		}
	},
	/**
	 * 验证手机号是否符合格式要求 
	 * @param {Object} mobile
	 */
	validate_mobile:function (mobile){
		//var mobilePattern=/^(((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))+\d{8})$/;//手机校验规则
		var mobilePattern={mobile: /^(((13[0-9])|(14[5-7])|(15[0-9])|(17[0-9])|(18[0-9]))+\d{8})$/ };
		if(!mobilePattern.mobile.test(mobile)){
    			return false;
    	}
		return true;
	},
	
	/**
	 * 验证密码是否符合格式要求
	 * @param {Object} password
	 */
	validate_password:function(password){
		var passwordPattern=/^(?![0-9]+$)(?![a-zA-Z]+$)[a-zA-Z\d]{6,16}$/;//登录密码校验规则
		if(!passwordPattern.test(password)){
			return false;
		}
		return true;
	},
	dateUtil:{
		/**
		*转换日期对象为日期字符串
		* @param l long值
		* @param pattern 格式字符串,例如：yyyy-MM-dd hh:mm:ss
		* @return 符合要求的日期字符串
		*/
		getFormatDate:function(curDate, pattern) {
			if (mui.isnull(curDate)) {
				curDate = new Date();
			}
			if (mui.isnull(pattern)) {
				pattern = "yyyy-MM-dd";
			}
			return curDate.format(pattern);
		},
		/**
		 * 将long 值转换为对应的日期格式值
		 * @param {Object} longdate
		 * @param {Object} pattern
		 */
		getFormatDataByLong:function(longdate, pattern){
			if (mui.isnull(longdate)){
				return;
			}
			if (mui.isnull(pattern)) {
				pattern = "yyyy-MM-dd";
			}
			return tzdr.dateUtil.getFormatDate(new Date(longdate*1000),pattern);
		}
	},
	init:{
		initHearListener:function(){
			if (document.getElementById("telBtn")){
				document.getElementById("telBtn").addEventListener("tap",function(){
	    			mui.callService();
	    		});
    		}
    		if (document.getElementById("back")){
    			document.getElementById("back").addEventListener("tap",function(){
    				mui.back();
    			});
    		}
		},
		initButtomListener:function(home){
			
				document.getElementById("account").addEventListener("tap",function(){
						if (mui.cacheUser.isLogin()){
							var a=plus.webview.getWebviewById("account");
							if(a){
								mui.app_refresh('account');
							}
							mui.openWindow({url:mui.app_filePath("tzdr/account/account.html"),id:'account'}); 
							return;
						}
						mui.openWindow(mui.app_filePath("tzdr/account/accountno.html"),"accountno");
				});
				document.getElementById("scheme").addEventListener("tap",function(){
					if (mui.cacheUser.isLogin()){
						var s=plus.webview.getWebviewById("scheme");
					    if(s){
							mui.app_refresh('scheme');
						}
						mui.openWindow({url:mui.app_filePath("tzdr/scheme.html"),id:"scheme"}); 
						return;
					}
					mui.openWindow({url:mui.app_filePath("tzdr/future/listrg.html"),id:"noscheme"});
					
				});
				document.getElementById("product").addEventListener("tap",function(){
					var p=plus.webview.getWebviewById("home");
					if(p){
						mui.app_refresh('home');  
					}
					mui.openWindow({url:mui.app_filePath("home.html"),id:"home"});
				});
			
				document.getElementById("quotationMain").addEventListener("tap",function(){
					var p=plus.webview.getWebviewById("quotationMain");
					if(p){
						mui.app_refresh('quotationMain');  
					}
					mui.openWindow({url:mui.app_filePath("tzdr/quotation/quotationMain.html"),id:"quotationMain"});
				});
			
		}
	},
	moneyUtils:{
		//匹配是否小数点后两位数
		testDecimal:function(num){
			var reg=/^-?\d+\.?\d{0,2}$/;
			if(!mui.isnull(num)&&reg.test(num)){
    			return true;
    		}
    		return false;
		},
		//num表示要四舍五入的数,v表示要保留的小数位数。
    	decimal:function(num,v){
    		var vv = Math.pow(10,v);  
		    return Math.round(num*vv)/vv; 
    	},
    	/** 
		 * 将数值四舍五入(保留n位小数)后格式化成金额形式 
		 * 
		 * @param s 数值(Number或者String) 
		 * @param n 保留小数位(Number或者String) 不传默认2位  整数可不传
		 * @return 金额格式的字符串,如'1,234,567.45' 
		 * @type String 
		 */  
		formatCurrency:function(s,n) { 
			var num=s;
			s = Math.abs(s);
			n = n>0 && n<=20 ? n : 2;
			s = parseFloat((s+"").replace(/[^\d\.-]/g,"")).toFixed(n)+""; 
			var l = s.split(".")[0].split("").reverse(), 
			r = s.split(".")[1]; 
			t = ""; 
			for(i = 0;i<l.length;i++){ 
			t+=l[i]+((i+1)%3==0 && (i+1) != l.length ? "," : ""); 
			} 
			
			var result = num;
			if(n==0||num.toString().indexOf(".")==-1){
				
				   result = t.split("").reverse().join("");
			}else 
			{
					result = t.split("").reverse().join("")+"."+r; 
			}
		
			if (num<0){
				result = "-"+result;
			}
			return result;
		},
		/**
		 * 金额数值去掉','号
		 * @param s 数值(String) 
		 * @return float 
		 */
		reverseMoney:function(s){
			return parseFloat(s.replace(/,/g,''));
		}
	}
}



/**
 * 短信倒计时 60s
 * @param {Object} o  点击获取验证码对象
 */
var time=60;
tzdr.smsTime=function(o){
    if (time == 0) {  
        o.removeAttribute("disabled");            
        o.innerHTML="获取验证码";  
        time = 60;  
        //o.style.background="#d3413d";
       o.style.background="#33333";
      
         o.style.color="#FFCC33";
    } else {  
        o.setAttribute("disabled", true);  
        o.innerHTML="倒计时(" + time + ")";  
        time--;  
        //o.style.background="rgb(211, 211, 211)";
       o.style.background="#333333";

         o.style.color="#FFCC33";
        setTimeout(function() {  
            tzdr.smsTime(o);  
        },  
        1000)  
    } 
}



/**
 * IOS或Android下载
 * @param {Object} obj  按钮对象
 */
tzdr.kuaiqiangshou=function(obj){
			var has_ios=false;
			var has_android=false;
			//判断手机为安卓或ios
			var hasDown_ios=false;
    		var hasDown_android=false;
    		
			switch (plus.os.name){
				case "Android":
					//判断是否已经安装Android apk true:启动app false:下载app
					has_android=true;
					
					/*if(tzdr.judge_android()){
						hasDown_android=true;
						obj.innerHTML="打开交易软件";
						document.getElementById("android_info").style.display="block";
					}else{
						hasDown_android=false;
						obj.innerHTML="下载交易软件";
						document.getElementById("android_info").style.display="none";
					}*/
					break;
				case "iOS":
					//判断是否已经安装iOS ipa  true:启动app false:下载app
					/*has_ios=true;
					hasDown_ios=true; 
					obj.innerHTML="打开交易软件";
					document.getElementById("ios_info").style.display="block";
					document.getElementById("ios_down").style.display="none";*/
					break;
				default:
					break;
			}
			obj.addEventListener("tap",function(){
				if(has_ios){
					if(hasDown_ios){   
						//启动app
						plus.runtime.launchApplication({action:"kuaiqiangshou://"}, function (e) {
							mui.toast("您未安装快抢手软件，请下载安装！");
							hasDown_ios=false;
						    obj.innerHTML="下载交易软件";
						    document.getElementById("ios_down").style.display="block";
						    document.getElementById("ios_info").style.display="none";
						});  
						return;
					}
					if (!hasDown_ios && obj.innerHTML=="下载交易软件"){
						plus.webview.currentWebview().reload("true");
					}
					//打开app stroe 下载 ipa
					document.getElementById("ios_info").style.display="block";
					document.getElementById("ios_down").style.display="none";
					plus.runtime.openURL("itms-services://?action=download-manifest&amp;url=https://update.vs.com/Future/download/ios_vs_app/kqs.plist");  
					return;
				}  
				else if(has_android){
					if(hasDown_android){
						//启动app
						plus.runtime.launchApplication({pname:"com.tts.mtrader.start"
							,extra:{}}, function ( e ) {
								alert( "Open system default browser failed: " + e.message );
						});
						return ;
					}
//					if (!hasDown_android && obj.innerHTML=="下载交易软件"){
//						plus.webview.currentWebview().reload("true");
//					}
					//下载 apk  
					plus.runtime.openURL("http://update.vs.com/Future/download/android_vs_app/kuaiqiangshou.apk");
					return;
				
			}
		});
}

