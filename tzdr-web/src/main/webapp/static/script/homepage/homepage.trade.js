 var href = window.location.href;
	 	// 路径配置
	    require.config({
	        paths:{//${ctx }/static/script
	            'echarts' :href + '/static/script/echarts',
	            'echarts/chart/pie' :href + '/static/script/echarts'
	        }
	    });
	 	
	    var rawData = [];
	    var loadCount = 0;
	    var loadKcount = 0 ;
	    var	 myChart = null;
	    //加载K线图数据模型
	    function loadKData(exchangeNo,commodity,contract,hisQuoteType,beginTime,endTime,count){
	    	var rawDataLength = rawData.length - 1;
            if(rawDataLength > 0){
            	beginTime = rawData[rawDataLength][0];
            }
	    	sendMessage('QryHistory','{"ExchangeNo":"'+currenExchangeNo+'","CommodityNo":"'+currenCommodityNo+'","ContractNo":"'+currenContractNo+'","HisQuoteType":'+hisQuoteType+',"BeginTime":"'+beginTime+'","EndTime":"'+endTime+'","Count":'+count+'}');
	    }
	    //设置数据参数（为画图做准备）
	    function setOption(rawData){
	    	var dates = rawData.map(function (item) {
	            return item[0];
	        });

	        var data = rawData.map(function (item) {
	            return [+item[1], +item[2], +item[5], +item[6]];
	        });
	    	var option = {
	                title: {
	                    text: '1分钟K线',
	                    textStyle: {
	                    	color : "#ffcc33"
	                    }
	                },
	                backgroundColor: '#333',
	                tooltip: {
	                    trigger: 'axis',
	                    axisPointer: {
	                        animation: false,
	                        lineStyle: {
	                            color: '#376df4',
	                            width: 2,
	                            opacity: 1
	                        }
	                    }
	                },
	                xAxis: {
	                    type: 'category',
	                    data: dates,
	                    axisLine: { lineStyle: { color: '#8392A5' } }
	                },
	                yAxis: {
	                    scale: true,
	                    axisLine: { lineStyle: { color: '#8392A5' } },
	                    splitLine: { show: false }
	                },
	                dataZoom: [{
	                    textStyle: {
	                        color: '#8392A5'
	                    },
	                    handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
	                    handleSize: '80%',
	                    dataBackground: {
	                        areaStyle: {
	                            color: '#8392A5'
	                        },
	                        lineStyle: {
	                            opacity: 0.8,
	                            color: '#8392A5'
	                        }
	                    },
	                    handleStyle: {
	                        color: '#fff',
	                        shadowBlur: 3,
	                        shadowColor: 'rgba(0, 0, 0, 0.6)',
	                        shadowOffsetX: 2,
	                        shadowOffsetY: 2
	                    }
	                }, {
	                    type: 'inside'
	                }],
	                animation: false,
	                series: [
	                    {
	                        type: 'candlestick',
	                        name:"1分钟K线",
	                        data: data,
	                        itemStyle: {
	                            normal: {
	                                color: '#FD1050',
	                                color0: '#0CF49B',
	                                borderColor: '#FD1050',
	                                borderColor0: '#0CF49B'
	                            }
	                        }
	                    }
	                ]
	            };
	    	return option;
	    }
	    function calculateMA(dayCount, data) {
	        var result = [];
	        for (var i = 0, len = data.length; i < len; i++) {
	            if (i < dayCount) {
	                result.push('-');
	                continue;
	            }
	            var sum = 0;
	            for (var j = 0; j < dayCount; j++) {
	                sum += data[i - j][1];
	            }
	            result.push(sum / dayCount);
	        }
	        return result;
	    } 
	    
	    //生成一个K线图容器
	    function loadK(){
	    	 // 使用
	        require(
	                [
	                    'echarts',
	                    'echarts/chart/pie' // 使用柱状图就加载bar模块，按需加载
	                ],
	                function (ec) {
	                	/* document.getElementById('main').innerHTML = ""; */
	                    // 基于准备好的dom，初始化echarts图表
	                    myChart = ec.init(document.getElementById('main'));
	                	var option = setOption(rawData);
	    			    // 为echarts对象加载数据
		    			myChart.setOption(option);
	                }
	        );
	    }
	    var interValData = null;
	    function bindLoadKDataInterValData(exchangeNo,commodityNo,contract,hisQuoteType,beginTime,endTime,count){
	    	interValData = window.setInterval(function(){loadKData(exchangeNo,commodityNo,contract,hisQuoteType,beginTime,endTime,count)}, 30000);
	    }
	    function clearLoadKDataInterVal(){
	    	if(interValData != null){
		    	window.clearInterval(interValData);
	    	}
	    }								  
	    function exctionLoadK(exchangeNo,commodityNo,contract,hisQuoteType,beginTime,endTime,count){
		 	clearLoadKDataInterVal();
		 	bindLoadKDataInterValData(exchangeNo,commodityNo,contract,hisQuoteType,beginTime,endTime,count);
	    }
	    function propHrefCP(commod){
	    	$(function(){
		    	if(commod == "HSI"){
			 		 $("#mainSqcp").prop("href",href + "hsi/index");
			 	 }else if(commod == "CL"){
			 		 $("#mainSqcp").prop("href",href + "crudeoil/index");
			 	 }else if(commod == "CN"){
			 		 $("#mainSqcp").prop("href",href + "ftse/index");
			 	 }else{
			 		 $("#mainSqcp").prop("href",href + "outDisk/index");
			 	 }
	    	});
	    }
	    var contractData = [];
	    function sendMessage(method,parameters){
	    	socket.send('{"Method":"'+method+'","Parameters":'+parameters+'}');
	    }
	    var url = "ws://192.168.0.213:9006";
	    var socket = new WebSocket(url);
	    var currenExchangeNo = "";
	    var currenCommodityNo = "";
	    var currenContractNo = "";
	    socket.onopen = function(evt){
			sendMessage('Login','{"serName":"13677622344","PassWord":"a123456"}');
	     };
	    socket.onclose = function(evt){
	    };
	    socket.onmessage = function(evt){
	    	$(function(){
		    	var data = evt.data;
		    	var jsonData = JSON.parse(data);
		    	var method = jsonData.Method;
		    	//用户登录
		    	if(method == "OnRspLogin"){
		    		//发送消息获取品种
		    		sendMessage('QryCommodity',null);
		    	}else if(method == "OnRspQryCommodity"){
		    		var commoditys = jsonData.Parameters;
		    		var size = commoditys.length;
		    		//生成实时行情html
		    		for(var i = 0 ; i < size ; i++){
		    			var _data = commoditys[i];
		    			var html ="";
		    			var doSize = _data.DotSize;
		    			html += '<input type="hidden" class="doSize'+i+'" value = "'+doSize+'" /><div   class="left_xiangmu left_x'+i+'';
						 if(i  == 0){
						 	 html += ' on';
						 }
						 html +=  '"> <p><em  class = "right"></em>'
						 	  + '<span class = "qlast'+i+' left"></span>'
						 	  + '<span class = "qchange'+i+' right"></span>'
						 	  + '<span class = "scal'+i+' left"></span>';
						 $(".w_center_xiangqing .left_xiangqing .left_hidden").append(html);
						 contractData[i] = {"exchangeNo":_data.ExchangeNo,'cname':_data.CommodityName,'Cmno':_data.CommodityNo,'doSize':doSize};
						 $(".left_x"+i+"").bind("click",function(){
							 rawData=[];
							 loadK();
							 var obj = $(this);
							 var da = obj.attr("data");
							 if(da != null){
							 	  var daArray = da.split("&");
							 	  currenCommodityNo = daArray[0];
							 	  currenContractNo = daArray[1];
							 	  currenExchangeNo = daArray[2];
							 	  loadKData(currenExchangeNo,currenCommodityNo,currenContractNo,0,"","",0);
							 	  exctionLoadK(currenExchangeNo,currenCommodityNo,currenContractNo,1,"","",0);
							 	  $("#dqCommodNo").val(currenCommodityNo);
							 	  propHrefCP(currenCommodityNo);
							 }
							 var topData = obj.attr("topData");
							 if(topData != null){
								 var topDataArray = topData.split("&");	
								 $(".zs").text("昨收:"+topDataArray[0]);	
								 $(".fd").text("每日幅度:"+topDataArray[1]+' - '+topDataArray[2]);
								 $(".jk").text("今开:"+topDataArray[3]);
								 $(".gxsj").text("更新时间:"+_data.TimeStamp);
							 }
							 var left_xiangmu   = $(".w_content .w_center_xiangqing .left_xiangmu");
							 left_xiangmu.each(function(){
								 left_xiangmu.removeClass('on');
							 });
							 obj.addClass('on');
						 });
						 var left_xiangmu   = $(".w_content .w_center_xiangqing .left_xiangmu");
						 left_xiangmu.each(function(){
						    left_xiangmu.on('touchstart',function(){
						    	rawData=[];
								 loadK();
								 var obj = $(this);
								 var da = obj.attr("data");
								 if(da != null){
								 	  var daArray = da.split("&");
								 	  currenCommodityNo = daArray[0];
								 	  currenContractNo = daArray[1];
								 	  currenExchangeNo = daArray[2];
								 	  loadKData(currenExchangeNo,currenCommodityNo,currenContractNo,0,"","",0);
								 	  exctionLoadK(currenExchangeNo,currenCommodityNo,currenContractNo,1,"","",0);
								 	  $("#dqCommodNo").val(currenCommodityNo);
								 	  propHrefCP(currenCommodityNo);
								 }
								 var topData = obj.attr("topData");
								 if(topData != null){
									 var topDataArray = topData.split("&");	
									 $(".zs").text("昨收:"+topDataArray[0]);	
									 $(".fd").text("每日幅度:"+topDataArray[1]+' - '+topDataArray[2]);
									 $(".jk").text("今开:"+topDataArray[3]);
									 $(".gxsj").text("更新时间:"+_data.TimeStamp);
								 }
						    	left_xiangmu.removeClass('on');
						        $(this).addClass('on');
						    });
						 })
		    		}
		    		for(var i = 0 ; i < size ; i++){
		    			var comm = commoditys[i];
		    			sendMessage('Subscribe','{"ExchangeNo":"'+comm.ExchangeNo+'","CommodityNo":"'+comm.CommodityNo+'","ContractNo":"'+comm.MainContract+'"}');
		    		}
		    	}else if(method == "OnRspSubscribe"){
		    	}else if(method == "OnRtnQuote"){
		    		var size = contractData.length;
		    		var newCommNo = jsonData.Parameters.CommodityNo;
		    		var flag = false;
		    		for(var i = 0 ;i < size ; i ++){
		    			var con = contractData[i].Cmno;
		    			if(newCommNo == con){
		    				contractData[i].Parameters = jsonData;
				    		break;
		    			}
		    		}
		    		var contractLength = contractData.length;
		    		var loadInitCommod = null;
		    		if(contractLength > 0){
		    			loadInitCommod = contractData[0].Cmno;
		    		}
		    	   for(var i = 0 ; i < contractLength; i++){
						 var _data_ = contractData[i];
						 if(_data_.hasOwnProperty("Parameters")){
							 var _data = _data_.Parameters.Parameters;
							 var size = $(".doSize"+i+"").val();
							 var qlastPrice = (parseFloat(_data.LastPrice)).toFixed(size);
							 var qpreCloseingPrice = (parseFloat(_data.PreClosingPrice)).toFixed(size);
							 var qLowPrice = (parseFloat(_data.LowPrice)).toFixed(size);
							 var qHighPrice = (parseFloat(_data.HighPrice)).toFixed(size);
							 var qOpenPrice = (parseFloat(_data.OpenPrice)).toFixed(size);
							 var scal = (parseFloat(_data.ChangeRate)).toFixed(2);
							 var qChangeValue = (parseFloat(_data.ChangeValue)).toFixed(size);
							 var qBidPrice1 = (parseFloat(_data.BidPrice1)).toFixed(size);
							 var qAskPrice1 =  (parseFloat(_data.AskPrice1)).toFixed(size);
							 var bs = "↑";
							 var jj = "+";
							 var color = " #ff5500";
							 if(_data.ChangeRate < 0){
								 bs = "↓";
								 jj = "";
								 color = "#0bffa4";
							 }
							 $(".left_x"+i+"").attr("data",""+_data.CommodityNo+"&"+_data.ContractNo+"&"+_data.ExchangeNo);
							 $(".left_x"+i+" .right").text(_data_.cname);
							 $(".qlast"+i+"").text(qlastPrice + " " + bs);
							 $(".qchange"+i+"").text(jj + qChangeValue);
							 $(".scal"+i+"").text(jj + scal + "%");
							 $(".qlast"+i+"").css("color",color);
							 $(".qchange"+i+"").css("color",color);
							 $(".scal"+i+"").css("color",color);
							 var CNo = $("#dqCommodNo").val();
							 if(CNo == _data.CommodityNo){
								 $(".zs").text("最新买价:"+qBidPrice1);	
								 $(".fd").text("最新价:"+qlastPrice);
								 $(".jk").text("最新卖价:"+qAskPrice1);
								 $(".gxsj").text("更新时间:"+_data.DateTimeStamp);
							 }
							 if(loadInitCommod != null && loadInitCommod == _data.CommodityNo && loadCount == 0){
								  loadK();
								  rawData=[];
							 	  currenCommodityNo = _data.CommodityNo;
							 	  currenContractNo = _data.ContractNo;;
							 	  currenExchangeNo = _data.ExchangeNo;
							 	  loadKData(currenExchangeNo,currenCommodityNo,currenContractNo,0,"","",0);
							 	  exctionLoadK(currenExchangeNo,currenCommodityNo,currenContractNo,1,"","",0);
							 	  $("#dqCommodNo").val(currenCommodityNo);
							 	  propHrefCP(currenCommodityNo);
								 loadCount++;
							 }
					 }
		    	   }
		    	}else if(method = "OnRspQryHistory"){
	    		    var historys = jsonData.Parameters;
	    		    if(historys == null)return;
	    		    var rawDataLength = rawData.length - 1;
	    		    for(var i = 0 ; i < historys.length;i++){
	    		    	var item =  historys[i];
	    		    	var dataTime =item.DateTimeStamp;
	    		    	var j  = 0;
	    		    	j = rawDataLength + i;
	 					var openPrice = item.OpenPrice;
	 					var closePrice = item.LastPrice;
	 					var chaPrice = closePrice - openPrice;
	 					var sgData = [dataTime,openPrice,closePrice,chaPrice,"",item.LowPrice,item.HighPrice,"","","-"];
	 					rawData[j] = sgData;
	    		    }
	    		  //追加到容器中
   					var option = setOption(rawData);
   					if(myChart != null){
	   					myChart.setOption(option);
   					}
		    	}
	    	});
	    };
	    socket.onerror = function(evt){
		   
	    }
	    
	    $(".left_hidden").mouseover(function(){
	        $("#whichscro").val($.trim($(this).parent().attr("id")))
	        if ((navigator.userAgent.match(/(iPhone|Android|iPad)/i))){
	            var scrollfathter1=document.getElementById($.trim($(this).parent().attr("id")));
	            scrollfathter1.addEventListener("touchstart", touchStart, false);
	            scrollfathter1.addEventListener("touchmove", touchMove, false);
	            scrollfathter1.addEventListener("touchend", touchEnd, false);
	        }
	    });
	    scroll_y("left_xiangqing","left_hidden","scroll_y","scroll_ymove","scroll_x","scroll_xmove","","wheely","");	