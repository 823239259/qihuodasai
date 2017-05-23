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
    var p="", h="";
    if(m<9) {
    	p=0;
    }
    if(d<9) {
    	h=0;
    }
    return y+"-"+p+m+"-"+h+d;
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
    var i ="",j="",j1="",j2="",j3="",j4="",j5="",j6="",j7="";
    if(monday.getMonth()<9){
        i="0";
    }
    if(currentDate.getDate() <10) {
    	j="0";
    }
    if(monday.getDate()<10){
        j1="0";
    }
    if(tuesday.getDate()<10){
        j2="0";
    }
    if(wednesday.getDate()<10){
        j3="0";
    }
    if(thursday.getDate()<10){
        j4="0";
    }
    if(friday.getDate()<10){
        j5="0";
    }
    if(saturday.getDate()<10){
        j6="0";
    }
    if(sunday.getDate()<10){
        j7="0";
    }
    $(".monday").html(j1+monday.getDate()).attr("data-time",currentDate.getFullYear()+'-'+i+(monday.getMonth()+1)+'-'+j1+monday.getDate());
    $(".tuesday").html(j2 + tuesday.getDate()).attr("data-time",currentDate.getFullYear()+'-'+i+(tuesday.getMonth()+1)+'-'+j2+tuesday.getDate());;
    $(".wednesday").html(j3 + wednesday.getDate()).attr("data-time",currentDate.getFullYear()+'-'+i+(wednesday.getMonth()+1)+'-'+j3+wednesday.getDate());;
    $(".thursday").html(j4 + thursday.getDate()).attr("data-time",currentDate.getFullYear()+'-'+i+(thursday.getMonth()+1)+'-'+j4+thursday.getDate());;
    $(".friday").html(j5 + friday.getDate()).attr("data-time",currentDate.getFullYear()+'-'+i+(friday.getMonth()+1)+'-'+j5+friday.getDate());;
    $(".saturday").html(j6 + saturday.getDate()).attr("data-time",currentDate.getFullYear()+'-'+i+(saturday.getMonth()+1)+'-'+j6+saturday.getDate());;
    $(".sunday").html(j7 + sunday.getDate()).attr("data-time",currentDate.getFullYear()+'-'+i+(sunday.getMonth()+1)+'-'+j7+sunday.getDate());;
	$("#result").html(currentDate.getFullYear()+"-"+ i+(currentDate.getMonth() + 1)+"-"+ j+(currentDate.getDate()));
	$("#result1").html(currentDate.getFullYear()+"-"+ i+(currentDate.getMonth() + 1)+"-"+ j+(currentDate.getDate()));
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
			var picker = new $.DtPicker(options);
			picker.show(function(rs) {
				result.innerText = rs.y.text+'-'+ rs.m.text + '-'+ rs.d.text;
				var calendar = rs.y.text+'-'+ rs.m.text + '-'+ rs.d.text;
				document.getElementById("finance").innerHTML="";
				var clickTomorrow  =  GetDateStrDate(calendar);
			    var paramsThere={
			        pageIndex:0,
			        startTime:calendar,
			        endTime:clickTomorrow
			    };
			    list_numThere = 0;
			    calendarData(urlTwo,paramsThere,finance,"","post");
			    var div1 = document.getElementById('mui-scroll-one');
          		div1.style.transform="translate3d(0px, 0px, 0px)";
//			    $("#chartContainerContentOne .mui-scroll").setAttribute('style', 'translate3d(0px, 0px, 0px)');
//			    $("#chartContainerContentOne .mui-scroll").css("transform","translate3d(0px, 0px, 0px)");
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
	    size: 30,
	    channelset:1
	};
	/*国家条件筛选*/
	var valList="";
	var dataUp=[];
	var valListFive;
	var valListOne="";
	$("#chioceProfit .buttonListLi ul.important li span").on("tap",function() {
		valListOne="";
		valListFive=[];
		var _this = $(this);
		$("#chioceProfit .buttonListLi ul.important li span").removeClass("span-selected");
		_this.addClass("span-selected");
		/*var check = _this.attr("class");
		if(check == "span-selected") {
			_this.removeClass("span-selected");
		} else if(check == "") {
			_this.addClass("span-selected");
		}*/
		var obj = $("#chioceProfit .buttonListLi ul.important li span");
		for(var i=0; i<obj.length; i++){
            if(obj.eq(i).attr("class") == "span-selected"){
                valListOne+=obj.eq(i).text()+",";
                valListFive.push(obj.eq(i).text());
            }
        }
//		console.log(valListOne.length);
	});
	var valListtwo;
	$("#chioceProfit .buttonListLi ul.country li span").on("tap",function() {
		valListtwo =[];
		var _this = $(this);
		var check = _this.attr("class");
		if(check == "span-selected") {
			_this.removeClass("span-selected");
		} else if(check == "") {
			_this.addClass("span-selected");
		}
		
		valList="";
		var obj = $("#chioceProfit .buttonListLi ul.country li span");
        //取到对象数组后，我们来循环检测它是不是被选中
        var s='';
        
        for(var i=0; i<obj.length; i++){
            if(obj.eq(i).attr("class") == "span-selected"){
                valList+=obj.eq(i).text()+",";
                valListtwo.push(obj.eq(i).text());
            }
        }
//      console.log(valList.length);
    });
	$("#confirmBottonProfit").on("tap",function() {
		var listFour=[];
		if(valList.length>0 && valListOne.length >0) {
//			console.log("都点击的时候")
			if(valList.indexOf("全部")>=0 && valListOne.indexOf("重要")>=0){
//				console.log("全部国家，全部重要性");//只判断重要性
				for(var i=0;i<listThere.length;i++){
	            	if(listThere[i].importance ==3){
	            		listFour.push(listThere[i]);
	            	}
				}
				$("#chartContainerContentOne .nodata").remove();
				$("#chartContainerContentOne .mui-table-view").empty();
        		finance12(listFour);
        		var _this = $(this);
				var id = _this.attr("data-id");
				$("#"+id).css("display","none");
				$("#chioce").css("display","none");
			}else if(valList.indexOf("全部")>=0 && valListOne.indexOf("重要") ==-1) {
				console.log("全部国家，没有重要性");//不需要循环只需要放进去
				$("#chartContainerContentOne .nodata").remove();
				$("#chartContainerContentOne .mui-table-view").empty();
				finance12(listThere);
				var _this = $(this);
				var id = _this.attr("data-id");
				$("#"+id).css("display","none");
				$("#chioce").css("display","none");
			}else if(valList.indexOf("全部")==-1 && valListOne.indexOf("重要")==-1) {
//				console.log("一些国家，没有重要性");
				for(var i=0;i<listThere.length;i++){
	            	for(var g = 0;g<valListtwo.length;g++) {
			    		if(valListtwo[g] == listThere[i].country){
					    	listFour.push(listThere[i]);
					    }
	    			}
				}
				$("#chartContainerContentOne .nodata").remove();
				$("#chartContainerContentOne .mui-table-view").empty();
        		finance12(listFour);
        		var _this = $(this);
				var id = _this.attr("data-id");
				$("#"+id).css("display","none");
				$("#chioce").css("display","none");
			}else if (valList.indexOf("全部")==-1 && valListOne.indexOf("重要")>=0) {
//				console.log("一些国家，有重要性");
				for(var i=0;i<listThere.length;i++){
	            	for(var g = 0;g<valListtwo.length;g++) {
			    		if(valListtwo[g] == listThere[i].country && listThere[i].importance ==3){
					    	listFour.push(listThere[i]);
					    }
	    			}
				}
				$("#chartContainerContentOne .nodata").remove();
				$("#chartContainerContentOne .mui-table-view").empty();
        		finance12(listFour);
        		var _this = $(this);
				var id = _this.attr("data-id");
				$("#"+id).css("display","none");
				$("#chioce").css("display","none");
			}
		}else if(valList.length == 0 && valListOne.length >0) {
//			console.log("只点击的重要时候")
			if(valList.indexOf("全部")==-1 && valListOne.indexOf("重要")>=0) {
				for(var i=0;i<listThere.length;i++){
		    		if(listThere[i].importance ==3){
				    	listFour.push(listThere[i]);
				    }
				}
				$("#chartContainerContentOne .nodata").remove();
				$("#chartContainerContentOne .mui-table-view").empty();
	    		finance12(listFour);
	    		var _this = $(this);
				var id = _this.attr("data-id");
				$("#"+id).css("display","none");
				$("#chioce").css("display","none");
			}else {
				$("#chartContainerContentOne .nodata").remove();
				$("#chartContainerContentOne .mui-table-view").empty();
	    		finance12(listThere);
	    		var _this = $(this);
				var id = _this.attr("data-id");
				$("#"+id).css("display","none");
				$("#chioce").css("display","none");
			}
		}else if(valList.length > 0 && valListOne.length ==0) {
//			console.log("只点击的国家时候");
			if(valList.indexOf("全部")==-1 && valListOne.indexOf("重要")==-1) {
				for(var i=0;i<listThere.length;i++){
	            	for(var g = 0;g<valListtwo.length;g++) {
			    		if(valListtwo[g] == listThere[i].country){
					    	listFour.push(listThere[i]);
					    }
	    			}
				}
				$("#chartContainerContentOne .nodata").remove();
				$("#chartContainerContentOne .mui-table-view").empty();
	    		finance12(listFour);
	    		var _this = $(this);
				var id = _this.attr("data-id");
				$("#"+id).css("display","none");
				$("#chioce").css("display","none");
			}else {
				$("#chartContainerContentOne .nodata").remove();
				$("#chartContainerContentOne .mui-table-view").empty();
	    		finance12(listThere);
	    		var _this = $(this);
				var id = _this.attr("data-id");
				$("#"+id).css("display","none");
				$("#chioce").css("display","none");
			}
		}else{
//			console.log("第一次进来什么都没有点的");
			$("#chartContainerContentOne .nodata").remove();
			$("#chartContainerContentOne .mui-table-view").empty();
    		finance12(listThere);
    		var _this = $(this);
			var id = _this.attr("data-id");
			$("#"+id).css("display","none");
			$("#chioce").css("display","none");
		}
	});
	function finance12(data){
		if(!mui.isnull(data)){
			if(data.length>0) {
				mui.each(data,function(i,item){
					//if(i<10){
						financeList(item);
						list_numThere++;
					//}
				}); 
			}else {
				var ul = $('#chartContainerContentOne .mui-table-view');
				var li;
				li = document.createElement('li');
				li.innerHTML = '<div style="width: 100%;height: 115px; position: absolute; z-index: 99999; background: #efeff4;"><div id="noSearchResultContainer" >' +
	            				'没有搜索到相关财经日历！</div></div>';
				ul.append(li);
			}
		}else{
			var ul = $('#chartContainerContentOne .mui-table-view');
			var li;
			li = document.createElement('li');
			li.innerHTML = '<div style="width: 100%;height: 115px; position: absolute; z-index: 99999; background: #efeff4;"><div id="noSearchResultContainer" >' +
	        				'没有搜索到相关财经日历！</div></div>';
			ul.append(li);
		}
	}
	(function($) {
		//阻尼系数
		var deceleration = mui.os.ios?0.003:0.0009;
		$('.mui-scroll-wrapper').scroll({
			bounce: false,
			indicators: true, //是否显示滚动条
			deceleration:deceleration
		});
		// 7*24
		$.each(document.querySelectorAll('#directSeedNews .mui-scroll'), function(index, pullRefreshEl) {
			var div = this;
			contentrefresh: '正在加载...',
			newData(url,params,forList,"","post");
			
			$(pullRefreshEl).pullToRefresh({
				up: {
					contentrefresh: '正在加载更多的数据...',
					callback: function() {
						var self = this;
						setTimeout(function() {
							fundmore();
							if(list.length == 30){
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
			var paramsTwo={
	            pageIndex:0,
	            startTime:todayTime,
	            endTime:TomorrowTime
    		};
			contentrefresh: '正在加载...',
			calendarData(urlTwo,paramsTwo,finance,"","post");
			
			$(pullRefreshEl).pullToRefresh({
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
	})(mui);
	// 财经日历开始
	/*财经日历时间选择*/
	$("#weekLis a").on("tap",function() {
		$("#chartContainerContentOne .nodata").remove();
		$("#chartContainerContentOne .mui-table-view").empty();
		$("#chartContainerContentOne .mui-scroll").css("transform","translate3d(0px, 0px, 0px)");
		var index = $(this);
		var clickToday = index.find("span").attr("data-time");
		var clickTomorrow  =  GetDateStrDate(clickToday);
		console.log(clickToday);
		console.log(clickTomorrow);
	    var paramsThere={
	        pageIndex:0,
	        startTime:clickToday,
	        endTime:clickTomorrow
	    };
	    list_numThere = 0;
	    calendarData(urlTwo,paramsThere,finance,"","post");
	});
	// 财经日历 ajax
	function calendarData(url,params,success,error,method){
		$("#chioceProfit .buttonListLi ul.country li span").removeClass("span-selected");
		$("#chioceProfit .buttonListLi ul.important li span").removeClass("span-selected");
		$("#chioceProfit .buttonListLi ul.important li span").eq(0).addClass("span-selected");
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
	            //alert("没有数据");
	            //var ul = $('#chartContainerContentOne .mui-table-view');
	            //var ts = $('#chartContainerContentOne .mui-pull-bottom-tips');
	            //ts.css("display","none");
				//var li;
				//li = document.createElement('li');
				//li.innerHTML = '<div id="noSearchResultContainer" >' +
	            //				'暂时没有数据</div>';
				//ul.append(li);
	        }
	    })
	}
	function finance(res){
		if(!mui.isnull(res)){
			listThere = res.data;
			console.log(listThere);
			if(listThere.length>0) {
				mui.each(listThere,function(i,item){
					if(i<100){
						financeList(item);
						list_numThere++;
					}
				}); 
			}else {
				var ul = $('#chartContainerContentOne .mui-table-view');
				var li;
				li = document.createElement('li');
				li.innerHTML = '<div style="width: 100%;height: 115px; position: absolute; z-index: 99999; background: #efeff4;"><div id="noSearchResultContainer" >' +
	            				'暂时无数据！</div></div>';
				ul.append(li);
			}
		}
	}
	function finance1(res){
		if(!mui.isnull(res)){
			if(listThere.length>0) {
				mui.each(listThere,function(i,item){
					//if(i<10){
						financeList(item);
						list_numThere++;
					//}
				}); 
			}else {
				var ul = $('#chartContainerContentOne .mui-table-view');
				var li;
				li = document.createElement('li');
				li.innerHTML = '<div style="width: 100%;height: 115px; position: absolute; z-index: 99999; background: #efeff4;"><div id="noSearchResultContainer" >' +
	            				'暂时没有数据</div></div>';
				ul.append(li);
			}
		}
	}
	function financemore(self){
		$("#chartContainerContentOne .nodata").remove();
	  	if(!mui.isnull(list)&&listThere.length>100){
	  		var isnum=(listThere.length-(list_numThere+100)) >=0 ? true : false;
	  		if(isnum){
		  		for(var i=0;i<100;i++){
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
		  		var insertHtml='<div class="nodata">没有数据了...</div>'
    			$('#chartContainerContentOne .mui-table-view').after(insertHtml);
		  		self.endPullUpToRefresh(false);
	  		}
	  	}else{
	  		var insertHtml='<div class="nodata">没有数据了...</div>'
    		$('#chartContainerContentOne .mui-table-view').after(insertHtml);
	  		self.endPullUpToRefresh(false);
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
            star="<span style='color: #fcc900;'>★</span><span style='color: #fcc900;'>★</span><span style='color: #fcc900;'>★</span>";
            importance = "key";
        }else{
        	star="<span></span>"
        	importance = "commonly";
        }
        var country;
        if(fund.country == "中国"){
        	country ="<img src='../../img/China.png'/>";
        }else if(fund.country == "英国") {
        	country ="<img src='../../img/Britain.png'/>";
        }else if(fund.country == "意大利") {
        	country ="<img src='../../img/Italy.png'/>";
        }else if(fund.country == "新西兰") {
        	country ="<img src='../../img/Zealand.png'/>";
        }else if(fund.country == "西班牙") {
        	country ="<img src='../../img/Spain.png'/>";
        }else if(fund.country == "瑞士") {
        	country ="<img src='../../img/Switzerland.png'/>";
        }else if(fund.country == "瑞典") {
        	country ="<img src='../../img/Sweden.png'/>";
        }else if(fund.country == "日本") {
        	country ="<img src='../../img/Japan.png'/>";
        }else if(fund.country == "欧元区") {
        	country ="<img src='../../img/Eurozone.png'/>";
        }else if(fund.country == "美国") {
        	country ="<img src='../../img/USA.png'/>";
        }else if(fund.country == "加拿大") {
        	country ="<img src='../../img/Canada.png'/>";
        }else if(fund.country == "韩国") {
        	country ="<img src='../../img/Seoul.png'/>";
        }else if(fund.country == "法国") {
        	country ="<img src='../../img/France.png'/>";
        }else if(fund.country == "俄罗斯") {
        	country ="<img src='../../img/Russia.png'/>";
        }else if(fund.country == "德国") {
        	country ="<img src='../../img/Germany.png'/>";
        }else if(fund.country == "朝鲜") {
        	country ="<img src='../../img/NorthKorea.png'/>";
        }else if(fund.country == "比利时") {
        	country ="<img src='../../img/Belgium.png'/>";
        }else if(fund.country == "澳大利亚") {
        	country ="<img src='../../img/Australia.png'/>";
        }else if(fund.country == "奥地利") {
        	country ="<img src='../../img/Austria.png'/>";
        }else {
        	country ="<img src='../../img/allCity.png'/>";
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
						"<p class='finance-right-country'>"+country+"<span>"+fund.country+"</span></p>"+
						"<p class='finance-right-title'>"+fund.title+"</p>"+
						"<ul class='finance-uls'><li class='mui-col-xs-4 mui-col-sm-4 on'>今值 "+fund.actual+"</li>"+
						"<li class='mui-col-xs-4 mui-col-sm-4'>预期 "+fund.forecast+"</li>"+
						"<li class='mui-col-xs-4 mui-col-sm-4'>前值 "+fund.previous+"</li></ul></div>";
	  	$fundList.appendChild(li);
	  	
	}
	// 财经日历结束
	//7*24 ajax
	function newData(url,params,success,error,method){
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
	            var ul = $('#chartContainerContent1 .mui-table-view');
	            var ts = $('#chartContainerContent1 .mui-pull-bottom-tips');
	            ts.css("display","none");
				var li;
				li = document.createElement('li');
				li.innerHTML = '<div id="noSearchResultContainer" >' +
	            				'暂无活动喔，你可关注维胜公众号留意近期活动~</div>';
				ul.append(li);
	        }
	    })
	}
	var num = 1; 
	function fundmore(){
		var self = this;
		var params1={
	        pageIndex:num,
	        size: 30,
	        channelset:1
		};
  		newData(url,params1,forList,"","post");
	}
	// 7*24小时开始
	function forList(res){ 
		console.log(JSON.stringify(res));
		if(!mui.isnull(res)){
  			list=res.data;
			if(!mui.isnull(list)){
				for(var i= 0;i<list.length; i++) {
					fundList(list,i);
				}
				for(var j= 0;j<$("#chartContainerContent1 ul li .content-right").length; j++){
					if($("#chartContainerContent1 ul li .content-right").eq(j).height() > 85){
						$("#chartContainerContent1 ul li .content-right").eq(j).css("height","105px");
					}else{
						$("#chartContainerContent1 ul li .content-right").eq(j).find(".caoz").css("display","none");
					}
				}
				$("#chartContainerContent1 .caoz").on("tap",function() {
					var _this = $(this);
					if(_this.html()  == "展开") {
						_this.parent().removeAttr("style");
						_this.parent().parent().css("height","inherit");
						_this.html("收起");
					}else if(_this.html()  == "收起") {
						_this.parent().css({
							"background": "#ffffff",
							"z-index": "999",
							"width": "100%",
							"position": "absolute",
							"top": "80px",
							"height": "21px"
						});
						_this.parent().parent().css("height","105px");
						_this.html("展开");
					}
				});
			}
		}
	}
	function fundList(list,i){
	  	var $fundList=document.body.querySelector("#directSeedNews .mui-table-view"); 
	  	var time = vs.dateUtil.getFormatDataByLong(list[i].liveCreatetime/1000,"hh:mm");
	  	//console.log(vs.dateUtil.getFormatDataByLong(list[i].liveCreatetime/1000,"yyyy-MM-dd"));
	  	$("#result1").html(vs.dateUtil.getFormatDataByLong(list[i].liveCreatetime/1000,"yyyy-MM-dd"));
	  	var li=document.createElement("li");
	  	li.innerHTML = "<div class='content-left'>"+time+"</div>"+
	  					"<div class='content-circular'></div>"+
	  					"<div class='content-right'>"+list[i].liveContent+"<div class='xians' style='background: #fff;z-index: 999; width: 100%; position: absolute; top: 80px;'><span class='caoz'>展开</div></div>";
	  	$fundList.appendChild(li);
	}