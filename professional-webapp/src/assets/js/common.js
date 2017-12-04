/*  
 * @description: 公用JS 
 * @author: name  
 * @update: 2017-10-13
 */

var pro = {};
/***************************************************************/
/**
 * 下拉框
 * @param {} 
 * 
 */
function close(obj){
	obj.slideUp(100);
	$(obj).parents(".slt-box").removeClass("current");
};
function open(obj){
	obj.slideDown(100);
	$(obj).parents(".slt-box").addClass("current");
};
function select(obj,ipt,fun){
	$(obj).find(".slt-list > ul > li").off().on('click', function(event){
		event.stopPropagation();
		var that = $(this);
		var val = that.attr("selectVal");
		var texts = that.text();
//			if(that.hasClass("charts-auto")) return false;
		ipt.val(texts);
		ipt.attr("selectVal",val);
		that.addClass("selectedLi").siblings().removeClass("selectedLi");
		close(that.parent().parent());
		ipt.focus().blur();
		if(typeof(fun) == 'undefined'){
			return true;
		}else if(ipt.attr("selectVal") == ipt.attr("prev_value")){
			return true;
		} 
		ipt.attr("prev_value",ipt.attr("selectVal"));
		fun(val, ipt); //回调函数
	})
};
pro.selectEvent = function(e,fun){
	if(typeof(e) == 'undefined'){
		e = 1;
	}
	var obj = $(e).children(".slt");   //获得新生成下拉列表的输入框
	select(e,obj,fun); //调用选择
	if(e == 1){
		$(e).click(function(event){
			event.stopPropagation();
			close($(".slt-box").children("div"));
			open(obj.siblings(".slt-list"));
		});
	}else{
		$(e).click(function(event){
			event.stopPropagation();
			close($(".slt-box").children("div"));
			open(obj.siblings(".slt-list"));
		});
		obj.keyup(function(){
			var VAL = $(this).val();
			close($(".slt-box").children("div"));
			$(this).attr("selectVal",VAL)
		});
	}
	$("body,html").click(function(){
		close(obj.siblings(".slt-list"));
	});	
}

/**
 * axios
 * 
 */
import axios from 'axios'
import qs from 'qs'
import store from '../../store'
// axios 配置
axios.defaults.timeout = 5000;
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8';
axios.defaults.baseURL = store.getters.PATH;
//POST传参序列化
axios.interceptors.request.use((config) => {
    if(config.method  === 'post'){
        config.data = qs.stringify(config.data);
    }
    return config;
},(error) =>{
    return Promise.reject(error);
});
//返回状态判断
axios.interceptors.response.use((res) =>{
    if(!res.data.success){
        return Promise.reject(res);
    }
    return res;
}, (error) => {
    return Promise.reject(error);
});
pro.fetch = function(type, url, params, header){
	return new Promise((resolve, reject) => {
        axios({
        	method: type,
			url: url,
			headers: {
				token:  header.token ? header.token : '',
				secret: header.secret ? header.secret : '',
				version: header.version ? header.version : ''
			},
			data: params
        }).then(response => {
            resolve(response.data);
        }, err => {
            reject(err);
        }).catch((error) => {
           reject(error)
        });
    });
}

/**
 * 时间搓转化成2017-07-07 02:05:00
 * 
 */
pro.getDate=function(d,time){
	var date=new Date(time);
    var year=date.getYear()+1900;
    var month=date.getMonth()+1;
    var day=date.getDate();
    var hour=date.getHours();
    var minu=date.getMinutes();
    var sec=date.getSeconds();
    if(day < 10){
    	day = "0" + day;
    }
    if(month < 10){
    	month = "0" + month;
    }
    if(hour < 10){
    	hour = "0" + hour;
    }
    if(minu < 10){
    	minu = "0" + minu;
    }
    if(sec < 10){ 
    	sec = "0" + sec;
    }
    var d1 = year + "-"+ month + "-" + day + " " + hour + ":" + minu + ":" + sec;
    var d2 = year + "-"+ month + "-" + day;
    var d3 = hour+":"+minu+":"+sec;
    var d4 = hour+":"+minu;
    var d5 = year+"年"+month+"月"+day+"日";
    if(d == "y-m-d"){
        return d2;
    }else if(d == "y-m-d h:i:s"){
        return d1;
    }else if(d == "h:i:s"){
    	return d3;
    }else if(d == "h:m"){
    	return d4;
    }else if(d == "yy-mm-dd"){
    	return d5;
    }
}
export default pro