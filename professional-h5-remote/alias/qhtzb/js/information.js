/*下拉刷新 上拉加载*/
	
	var url= tzdr.constants.api_domain + "crawler/getCrawler";
	var dataAll;
	var list = null;
	var listtwo = null;
	var listThere=null;  
	var list_numThere=0;
	var params={
	    pageIndex:0,
	    size: 20
	};
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
			
			newData(url,params,forList,"","post");
			contentrefresh: '正在加载...',
			$(pullRefreshEl).pullToRefresh({
				up: {
					contentrefresh: '正在加载更多的数据...',
					callback: function() {
						var self = this;
						setTimeout(function() {
							fundmore();
							if(list.length == 20){
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
	})(mui);
	function finance(res){
		if(!mui.isnull(res)){
			listThere = res.data;
			if(listThere.length>0) {
				mui.each(listThere,function(i,item){
					if(i<100){
//						financeList(item);
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
//				li.innerHTML = '<div id="noSearchResultContainer" >' +
//	            				'暂无活动喔，你可关注维胜公众号留意近期活动~</div>';
				ul.append(li);
	        }
	    })
	}
	var num = 1; 
	function fundmore(){
		var self = this;
		var params1={
	        pageIndex:num,
	        size: 20
		};
  		newData(url,params1,forList,"","post");
	}
	// 7*24小时开始
	function forList(res){ 
//		console.log(JSON.stringify(res.data[0].liveTitle));
//		console.log(JSON.stringify(res.data[0].createdAt));
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
	  	var time = tzdr.dateUtil.getFormatDataByLong(list[i].createdAt,"hh:mm");
	  	var li=document.createElement("li");
	  	li.innerHTML = "<div class='content-left'>"+time+"</div>"+
	  					"<div class='content-circular'></div>"+
	  					"<div class='content-right'>"+"&nbsp;&nbsp;"+list[i].liveTitle+"<div class='xians' style='background: #fff;z-index: 999; width: 100%; position: absolute; top: 80px;'><span class='caoz'>展开</div></div>";
	  	$fundList.appendChild(li);
	}
	

