/*获取今天明天*/
function GetDateStr(AddDayCount) {
    var dd = new Date();
    dd.setDate(dd.getDate()+AddDayCount);
    var y = dd.getFullYear();
    var m = dd.getMonth()+1;
    var d = dd.getDate();
    return y+"-"+m+"-"+d;
}
/*获取点击时间的明天*/
function GetDateStrDate(date) {
    var dd = new Date(date);
    dd.setDate(dd.getDate()+1);
    var y = dd.getFullYear();
    var m = dd.getMonth()+1;
    var d = dd.getDate();
    return y+"-"+m+"-"+d;
}
var todayTime = GetDateStr(0);
var TomorrowTime = GetDateStr(1);
/*日期 周一到周日*/
time();
function time() {
    var startStop = new Array();//起止日期数组
    var currentDate = new Date();//获取当前时间
    var week = currentDate.getDay(); //返回date是一周中的某一天
    var millisecond = 1000 * 60 * 60 * 24;//一天的毫秒数
    var minusDay = week != 0 ? week - 1 : 6; //减去的天数
    var monday = new Date(currentDate.getTime() - (minusDay * millisecond));//本周一
    var tuesday = new Date(monday.getTime() +millisecond);
    var wednesday = new Date(monday.getTime() + (2 * millisecond));
    var thursday = new Date(monday.getTime() + (3 * millisecond));
    var friday = new Date(monday.getTime() + (4 * millisecond));
    var saturday = new Date(monday.getTime() + (5 * millisecond));
    var sunday = new Date(monday.getTime() + (6 * millisecond));//本周日
    var i ="",j="";
    if(monday.getMonth()<9){
        i="0";
    }
    if(monday.getDate()<10){
        j="0";
    }
    $(".monday").html(j + monday.getDate()).attr("data-time",currentDate.getFullYear()+'-'+(monday.getMonth()+1)+'-'+monday.getDate());
    $(".tuesday").html(j + tuesday.getDate()).attr("data-time",currentDate.getFullYear()+'-'+(tuesday.getMonth()+1)+'-'+tuesday.getDate());;
    $(".wednesday").html(j + wednesday.getDate()).attr("data-time",currentDate.getFullYear()+'-'+(wednesday.getMonth()+1)+'-'+wednesday.getDate());;
    $(".thursday").html(j + thursday.getDate()).attr("data-time",currentDate.getFullYear()+'-'+(thursday.getMonth()+1)+'-'+thursday.getDate());;
    $(".friday").html(j + friday.getDate()).attr("data-time",currentDate.getFullYear()+'-'+(friday.getMonth()+1)+'-'+friday.getDate());;
    $(".saturday").html(j + saturday.getDate()).attr("data-time",currentDate.getFullYear()+'-'+(saturday.getMonth()+1)+'-'+saturday.getDate());;
    $(".sunday").html(j + sunday.getDate()).attr("data-time",currentDate.getFullYear()+'-'+(sunday.getMonth()+1)+'-'+sunday.getDate());;
	$("#result").html(currentDate.getFullYear()+"年"+ i+(monday.getMonth() + 1)+"月");
    if(week==1){
        $(".monday").parent().addClass("mui-active");
    }else if(week==2){
        $(".tuesday").parent().addClass("mui-active");
    }else if(week==3){
        $(".wednesday").parent().addClass("mui-active");
    }else if(week==4){
        $(".thursday").parent().addClass("mui-active");
    }else if(week==5){
        $(".friday").parent().addClass("mui-active");
    }else if(week==6){
        $(".saturday").parent().addClass("mui-active");
    }else if(week==0){
        $(".sunday").parent().addClass("mui-active");
    }
}
/*年月日-日历*/
(function($) {
	$.init();
	var result = $('#result')[0];
	var btns = $('.btn');
	btns.each(function(i, btn) {
		btn.addEventListener('tap', function() {
			var optionsJson = this.getAttribute('data-options') || '{}';
			var options = JSON.parse(optionsJson);
			var id = this.getAttribute('id');
			/*
			 * 首次显示时实例化组件
			 * 示例为了简洁，将 options 放在了按钮的 dom 上
			 * 也可以直接通过代码声明 optinos 用于实例化 DtPicker
			 */
			var picker = new $.DtPicker(options);
			picker.show(function(rs) {
				/*
				 * rs.value 拼合后的 value
				 * rs.text 拼合后的 text
				 * rs.y 年，可以通过 rs.y.vaue 和 rs.y.text 获取值和文本
				 * rs.m 月，用法同年
				 * rs.d 日，用法同年
				 * rs.h 时，用法同年
				 * rs.i 分（minutes 的第二个字母），用法同年
				 */
				result.innerText = rs.y.text+'年'+ rs.m.text + '月';
				var calendar = rs.y.text+'-'+ rs.m.text + '-'+ rs.d.text;
				document.getElementById("finance").innerHTML="";
				var clickTomorrow  =  GetDateStrDate(calendar);
			    var paramsThere={
			        pageIndex:0,
			        startTime:calendar,
			        endTime:clickTomorrow
			    };
			    list_numThere = 0;
			    queryData(urlTwo,paramsThere,finance,"","post");
				/* 
				 * 返回 false 可以阻止选择框的关闭
				 * return false;
				 */
				/*
				 * 释放组件资源，释放后将将不能再操作组件
				 * 通常情况下，不需要示放组件，new DtPicker(options) 后，可以一直使用。
				 * 当前示例，因为内容较多，如不进行资原释放，在某些设备上会较慢。
				 * 所以每次用完便立即调用 dispose 进行释放，下次用时再创建新实例。
				 */
				picker.dispose();
			});
		}, false);
	});
})(mui);
/*下拉刷新 上拉加载*/
	var url= "http://www.vs.com/crawler/getCrawlerByChannelLiveContent";
	var urlTwo= "http://www.vs.com/crawler/getCrawlerCalendarByTime";
	var dataAll;
	var list = null;
	var listtwo = null;
	var listThere=null;  
	var list_numThere=0;
	var params={
	    pageIndex:0,
	    size: 10,
	    channelset:1
	};
	/*时间选择*/
	$("#weekLis a").on("tap",function() {
		$("#chartContainerContentOne .mui-table-view").empty();
		var index = $(this);
		var clickToday = index.find("span").attr("data-time");
		var clickTomorrow  =  GetDateStrDate(clickToday);
	    var paramsThere={
	        pageIndex:0,
	        startTime:clickToday,
	        endTime:clickTomorrow
	    };
	    list_numThere = 0;
	    queryData(urlTwo,paramsThere,finance,"","post");
	    //console.log(clickTomorrow);
	});
	/*国家条件筛选*/
	var  b = {
		"pageResults": [{
	        "country": "中国"
	    },{
	        "country": "中国"
	    },{
	        "country": "中国"
	    },{
	        "country": "中国"
	    },{
	        "country": "中国"
	    },{
	        "country": "美国"
	    },{
	        "country": "美国"
	    },{
	        "country": "美国"
	    },{
	        "country": "美国"
	    },{
	        "country": "英国"
	    },{
	        "country": "英国"
	    },{
	        "country": "英国"
	    },{
	        "country": "法国"
	    },{
	        "country": "法国"
	    },{
	        "country": "德国"
	    }]
	}
	var valList="";
	var dataUp=[];
	$("#chioceProfit .buttonListLi ul li span").on("tap",function() {
		var _this = $(this);
		var check = _this.attr("class");
		if(check == "checked") {
			_this.removeClass("checked");
		} else if(check == "") {
			_this.addClass("checked");
		}
        valList="";
        var s='';
        var listFour=[];
        var obj = $("#chioceProfit .buttonListLi ul li span");
        //取到对象数组后，我们来循环检测它是不是被选中
        for(var i=0; i<obj.length; i++){
            if(obj.eq(i).attr("class") == "checked"){
                s+=obj.eq(i).attr("data-city")+',';
                valList+=obj.eq(i).text()+",";
            }
        }
	    if(valList.length>0){
	        if(valList.indexOf("全部")>=0){
	            console.log(b.pageResults);
	        }else{
	        	var a = b.pageResults;
	        	for(var i=0;i<a.length;i++){
				    if(valList.indexOf(a[i].country)>=0){
				    	listFour.push(a[i]);
				    }
				}
	        	console.log(listFour);
			    /*$.ajax({
					type: "GET",
					url:"http://127.0.0.1:8020/APP1/erweima2.json",
					dataType: 'json',
					success: function(data){
						var a = data.pageResults;
						for(var i=0;i<a.length;i++){
						    if(valList.indexOf(a[i].country)>=0){
						    	listFour.push(a[i]);
						    }
						}
					}
				});*/
	        }
	    }else{
	    	console.log(b.pageResults);
	    }
	    //updateData(valList);
    });
    $("#confirmBottonProfit").on("tap",function() {
    	console.log(b.pageResults);
    	console.log(listFour);
	});
	(function($) {
		//阻尼系数
		var deceleration = mui.os.ios?0.003:0.0009;
		$('.mui-scroll-wrapper').scroll({
			bounce: false,
			indicators: true, //是否显示滚动条
			deceleration:deceleration
		});
		$.ready(function() {
			/*7*24小时*/
			$.each(document.querySelectorAll('#directSeedNews .mui-scroll'), function(index, pullRefreshEl) {
				var div = this;
 				contentrefresh: '正在加载...',
 				queryData(url,params,forList,"","post");/*第一次加载*/
				$(pullRefreshEl).pullToRefresh({
					down: {
						contentrefresh: '正在刷新...',
						callback: function() {
							var self = this;
							setTimeout(function() {
								queryData(url,params,"","post");
								self.endPullDownToRefresh();
							}, 1500);
						}
					},
					up: {
						contentrefresh: '正在加载更多的数据...',
						callback: function() {
							var self = this;
							setTimeout(function() {
								fundmore();
								if(list.length == 10){
									self.endPullUpToRefresh(false);
									num++;
								}else{
									self.endPullUpToRefresh(true);
								}
							}, 1500);
						}
					}
				});
			});
			/*财经日历*/
			$.each(document.querySelectorAll('#directSeedCalendar .mui-scroll'), function(index, pullRefreshEl) {
				var div = this;
				var paramsTwo={
		            pageIndex:0,
		            startTime:todayTime,
		            endTime:TomorrowTime
        		};
 				contentrefresh: '正在加载...',
 				queryData(urlTwo,paramsTwo,finance,"","post");/*第一次加载*/
				$(pullRefreshEl).pullToRefresh({
					/*down: {
						contentrefresh: '正在刷新...',
						callback: function() {
							var self = this;
							setTimeout(function() {
								//queryData(url,params,"","post");
								self.endPullDownToRefresh();
							}, 1500);
						}
					},*/
					up: {
						contentrefresh: '正在加载更多的数据...',
						callback: function() {
							var self = this;
							setTimeout(function() {
								financemore(self);
							}, 1500);
						}
					}
				});
			});
		});
	})(mui);
	function queryData(url,params,success,error,method){
	    var success = arguments[2]?arguments[2]:function(){};
	    var error = arguments[3]?arguments[3]:function(){};
		if(method == undefined){
			method = "get";
		}
	    $.ajax({
	        url:url,
	        type:method,
	        dataType:"json",
	        data:params,
	        success: function (res) {
	        	if(res.success==true){
                	success(res.data);
            	}
	        },
	        error: function (res) {
	            error(res);
	        }
	    })
	}
	var num = 1; 
	function fundmore(){
		var self = this;
		var params1={
	        pageIndex:num,
	        size: 10,
	        channelset:1
		};
  		queryData(url,params1,forList,"","post");
	}
	function forList(res){
		if(!mui.isnull(res)){
  			list=res.data;
			if(!mui.isnull(list)){
				mui.each(list,function(i,item){
					fundList(item);
				});
			}
		}
	}
	function fundList(fund){
	  	var $fundList=document.body.querySelector("#directSeedNews .mui-table-view"); 
	  	var time = vs.dateUtil.getFormatDataByLong(fund.liveCreatetime,"hh:mm");
	  	var li=document.createElement("li");
	  	li.innerHTML = "<div class='content-left'>"+time+"</div>"+
	  					"<div class='content-circular'></div>"+
	  					"<div class='content-right'><p>"+fund.liveContent+"</p></div>";
	  	$fundList.appendChild(li);
	}
	function finance(res){
		if(!mui.isnull(res)){
			listThere = res.data;
			if(listThere.length>0) {
				mui.each(listThere,function(i,item){
					if(i<10){
						financeList(item);
						list_numThere++;
					}
				}); 
			}else {
				alert("没有数据啊");
			}
		}
	}
	function financemore(self){
	  	if(!mui.isnull(list)&&listThere.length>10){
	  		var isnum=(listThere.length-(list_numThere+10)) >=0 ? true : false;
	  		if(isnum){
		  		for(var i=0;i<10;i++){
		  			financeList(listThere[list_numThere]);  
		  			list_numThere++;
		  		}
		  		self.endPullUpToRefresh(false);
	  		}else{
	  			var obj_num = listThere.length-list_numThere;
		  		for(var i=0;i<obj_num;i++){
		  			financeList(listThere[list_numThere]);
		  			list_numThere++;
		  		}
		  		self.endPullUpToRefresh(true);
	  		}
	  	}else{
	  		self.endPullUpToRefresh(true);
	  	}
  	}
	
	function financeList(fund) {
		var $fundList=document.body.querySelector("#directSeedCalendar .mui-table-view");
		var time = vs.dateUtil.getFormatDataByLong(fund.timestamp,"hh:mm");
	  	var li= document.createElement("li");
	  	li.className = 'finance-lis';
	  	var star,importance;
        if(fund.importance==1){
            star="<span>★</span>"
            importance = "commonly";
        }else if(fund.importance==2){
            star="<span>★</span><span>★</span>";
            importance = "commonly";
        }else if(fund.importance==3){
            star="<span>★</span><span>★</span><span>★</span>";
            importance = "key";
        }
        var now = Date.parse(new Date());
		var timestamp = fund.timestamp+"000";
        if(timestamp<now){
        	importance = "overdue";
        }
        if(fund.actual==null){
        	fund.actual ="- -";
        }
        if(fund.forecast==null){
        	fund.forecast ="- -";
        }
        if(fund.previous==null){
        	fund.previous ="- -";
        }
	  	li.innerHTML =  "<div class='mui-col-xs-3 mui-col-sm-3 finance-left "+importance+" '>"+
						"<p class='finance-stars'>"+star+"</p>"+
						"<p class='finance-hour'>"+time+"</p>"+
						"<img src='../../img/directSeed-clock.png'/></div>"+
						"<div class='mui-col-xs-9 mui-col-sm-9 finance-right'>"+
						"<p class='finance-right-country'><img src='../../img/China.png'/><span>"+fund.country+"</span></p>"+
						"<p class='finance-right-title'>"+fund.title+"</p>"+
						"<ul><li class='mui-col-xs-4 mui-col-sm-4 on'>今值 "+fund.actual+"</li>"+
						"<li class='mui-col-xs-4 mui-col-sm-4'>预期 "+fund.forecast+"</li>"+
						"<li class='mui-col-xs-4 mui-col-sm-4'>前值 "+fund.previous+"</li></ul></div>";
	  	$fundList.appendChild(li);
	}
	/*国家条件筛选*/
	function updateData(val){
	}
	
















