$.fn.extend({
	//判断对象是否可见
	visible:function(){return this.is(':visible');},
	//替换CSS
	cssR:function(c1,c2){this.removeClass(c1);this.addClass(c2);},
	//在对象内部存入数据
	htm:function(s){if(this.length==0)return false;if($.no(s))return this[0].innerHTML;for(var i=0;i<this.length;i++)this[i].innerHTML=s;return true;},
	//焦点延迟处理
	f:function(){var t=this,f=function(){t.focus()};setTimeout(f,100);}
});

$.format = function (source, params) {
    if (arguments.length == 1)
        return function () {
            var args = $.makeArray(arguments);
            args.unshift(source);
            return $.format.apply(this, args);
        };
    if (arguments.length > 2 && params.constructor != Array) {
        params = $.makeArray(arguments).slice(1);
    }
    if (params.constructor != Array) {
        params = [params];
    }
    $.each(params, function (i, n) {
        source = source.replace(new RegExp("\\{" + i + "\\}", "g"), n);
    });
    return source;
};

$.extend({
	_:function(s){if($.no(s))s=$.t();document.title=s;},
	//获取对象(不带缓存)
	$:function(o){return $.isS(o)?$('#'+o):$(o);},
	//获取对象(带缓存)
	o:function(o){if(!$.isS(o))return $(o);var obj=$.os[o];if(obj)return obj;obj=$('#'+o);if(obj.length>0)$.os[o]=obj;return obj;},os:{},
	//获取对象值
	v:function(o,v){if(v)$.$(o).val(v);else{return $.trim($.$(o).val());}},
	//返回当前时间
	t:function(){return $.d().getTime();},
	//返回当前时间对象
	d:function(t){if(t)return new Date(t);return new Date()},
	//转换成整数
	n:function(s){return parseInt(s);},
	//转换成浮点数
	f:function(s){return parseFloat(s);},
	//判断对象是否存在
	no:function(){var as=arguments;for(var i=0;i<as.length;i++)if(as[i]==null || as[i]==undefined)return true;return false;},
	//判断对象类型
	isS:function(o){return typeof o=="string"},
	isN:function(o){return typeof o=="number"},
	isB:function(o){return typeof o=="boolean"},
	isO:function(o){return typeof o=="object"},
	isInt:function(o){return $.isN(o)&&Math.round(o)==o},
	//是否包含指定内容
	cc:function(cs,c,n){var e=!$.no(n);for(var i=0;i<cs.length;i++)if((e && cs[i][n]==c) || (!e && cs[i]==c))return i;return -1;},
	//返回RegExp
	re:function(s,c){var r=new RegExp(s);if(c)return r.test(c);return r;},
	//是否IE浏览器
	ie:function(v){if(!$.browser.msie)return false;if(v)return $.browser.version==v || $.browser.version.indexOf(v+'.')==0;return true;},
	//创建DOC对象
	ceok:false,
	ce:function(n){return $(document.createElement(n));},
	winPos:function(timestamp){return window.location.href+(timestamp?+"?t="+new Date().valueOf():"")},
	//从字符串中获取第一个数值
	nv:function(s,sv){var si=-1,ei=-1,i=0;if(sv){i=s.indexOf(sv);if(i<0)i=0;}for(;i<s.length;i++)if(si==-1){if(s.charAt(i)>='0' && s.charAt(i)<='9')si=i;}else{if(s.charAt(i)<'0' || s.charAt(i)>'9'){ei=i;break;}}return $.n(si==-1 && ei==-1 ? -1 : (ei==-1 ? s.substr(si) : s.substring(si,ei)));},
	//数值四舍五入
	round:function(n,mantissa){if(!mantissa)mantissa=0;if(mantissa<=0)return Math.round(n);var v=1;for(var i=0;i<mantissa;i++)v*=10;return Math.round(n*v)/v;},
	//金额格式化
	formatMoney : function(num,n) {
	    num = String(Number(num).toFixed(n?n:2));
	    var re = /(-?\d+)(\d{3})/;
	    while(re.test(num)) num = num.replace(re,"$1,$2")
	    return n?num:num.replace(/^([0-9,]+\.[1-9])0$/,"$1").replace(/^([0-9,]+)\.00$/,"$1");;
	},
	//字符串替换
	replace:function(s,s1,s2){return s.replace(new RegExp(s1,'g'),s2);},
	//字符串长度(中文算2个)
	strlen:function(s){return s.replace(/[^\x00-\xff]/g,"**").length},
	//字符串是否包含中文
	strch:function(s){return /[^\x00-\xff]+/.test(s)},
	//清除字符串中的'"字符和头尾空格
	clear:function(){var as=arguments,s;if(as.length<1)return '';s=as[0];if(as.length<2)as=[s,"'",'"'];for(var i=1;i<as.length;i++)s=$.replace(s,as[i],'');return $.trim(s);},
	//cookie操作
	getCookie:function(name,dv){var d=document.cookie;var il1=d.indexOf(name+'=');if(il1==-1)return $.no(dv) ? null : dv;il1+=name.length+1;var il2=d.indexOf(';',il1);if(il2==-1)il2=d.length;return decodeURI(d.substring(il1,il2));},
	setCookie:function(name,value,expires,path,domain,secure){var s=new Text()._(name)._('=')._(encodeURI(value));if(!expires || (expires && expires!='temp')){var day=60*60*24*1000;if(expires=='day')expires=$.d($.t()+day);else if(expires=='week')expires=$.d($.t()+day*7);else if(expires=='month')expires=$.d($.t()+day*30);else if(expires=='year')expires=$.d($.t()+day*365);else{expires=$.d($.t()+day*365*100);}s._(';expires=')._(expires.toGMTString());}if(path)s._(';path=')._(path);if(domain)s._(';domain=')._(domain);if(secure)s._(';secure=')._(secure);document.cookie=s;},
	delCookie:function(name,path,domain){var s=new Text()._(name)._('=null;expires=')._($.d($.t()-100000000).toGMTString());if(path)s._(';path=')._(path);if(domain!=null)s._(';domain=')._(domain);document.cookie=s;},
	clrCookie:function(path,domain){var ds=document.cookie.split(';');for(var i=0;i<ds.length;i++)$.delCookie($.trim(ds[i].split('=')[0]),path,domain);},
	//获取Flash对象
	getFlash:function(name){if($.ie())return window[name];else if($.browser.mozilla)return document[name+'-1'];else{var fl=window[name+'-1'];if(!fl)fl=window[name];if(!fl)fl=document[name+'-1'];return fl;}},
	//初始化对象
	init:function(o,dv){if(!o)return dv;for(i in dv)if($.no(o[i]))o[i]=dv[i];return o;},
	stringify  : function stringify(obj) {
        var t = typeof (obj);
        if (t != "object" || obj === null) {
            // simple data type
            if (t == "string") obj = '"' + obj + '"';
            return String(obj);
        } else {
            // recurse array or object
            var n, v, json = [], arr = (obj && obj.constructor == Array);
 
            for (n in obj) {
                v = obj[n];
                t = typeof(v);
                if (obj.hasOwnProperty(n)) {
                    if (t == "string") v = '"' + v + '"'; else if (t == "object" && v !== null) v = jQuery.stringify(v);
                    json.push((arr ? "" : '"' + n + '":') + String(v));
                }
            }
            return (arr ? "[" : "{") + String(json) + (arr ? "]" : "}");
        }
    }
});
T={
		numKeyPress:function(e){
			var k = e.keyCode || e.which;
			if(k>=48&&k<=57||k==8){
				return true;
			}
			return false;
		}
}

//错误提示
$(function (){
	$.alertTip = function(o,t,speed,f){
	  	 $('div.alertTip').remove();
	  	 var className;
	  	 if(!arguments[2]) speed=3000;
	  	 if(!arguments[3]) f=false;
	  	 if(!f) className="alertTip";else className="alertTip alertTip_right";
	     $('body').append('<div class="'+className+'"><div>'+t+'</div><span></span><a href="javaScript:void(0);" class="close" title="点击关闭提示"></a></div>');
	  	 var obj=$('div.alertTip');
	  	 obj.css("left",o.offset().left+"px").css("top",o.offset().top-36+"px");
	  	 obj.delay(speed).animate({top: o.offset().top-50,opacity:0}, 1000,function(){obj.remove();});
	  	 obj.find('a.close').click(function() {
	  	 obj.remove();
	  	 return false;
	  	 });
	};
});