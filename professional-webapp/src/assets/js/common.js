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
	obj.slideUp(400);
	$(obj).parents(".slt-box").removeClass("current");
};
function open(obj){
	obj.slideDown(400);
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
// axios 配置
axios.defaults.timeout = 5000;
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8';
axios.defaults.baseURL = 'http://test.api.duokongtai.cn/';
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
pro.fetch = function(url, params, head){
	return new Promise((resolve, reject) => {
        axios({
        	method: 'post',
			url: url,
			headers: {
				'token':  head ? head.token : '',
				'secret': head ? head.secret : ''
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


export default pro