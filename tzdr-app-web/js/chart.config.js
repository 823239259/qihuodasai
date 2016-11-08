	var aa=require.config({
			    paths:{
			        'echarts' :'../../js/echarts',
			        'echarts/chart/pie' :'../../js/echarts',
			    }
	});
//	var muiSpinner=document.getElementsByClassName("mui-spinner");
    var	 myChart = null;
    var timeChart=null;
    var volumeChart=null;
    var CandlestickVolumeChart=null;
    var echarts;
    loadK();
    var DateTimeStampSubscript;
	var LastPriceSubscript;
	var OpenPriceSubscript;
	var LowPriceSubscript;
	var HighPriceSubscript;
	var VolumeSubscript;
	var setIntvalTimeAll=null;
	
    //生成一个K线图容器
    function loadK(){
        // 使用
        require(
                [
                    'echarts',
                    'echarts/chart/pie', // 使用柱状图就加载bar模块，按需加载
//                    'echarts/chart/line',
                ],
                function (ec) {
                    /* document.getElementById('main').innerHTML = ""; */
                    // 基于准备好的dom，初始化echarts图表
                    myChart = ec.init(document.getElementById('CandlestickChartDiv'));
                    echarts=ec;
                    timeChart=ec.init(document.getElementById("timeChart"));
                    volumeChart=ec.init(document.getElementById("volumeChart"));
                    CandlestickVolumeChart=ec.init(document.getElementById("CandlestickVolumeChart"));
                   	 ec.connect("group1");
                     ec.connect("group2");
//                   ec.connect("group3");
//                   dayCandlestickChartDiv=ec.init(document.getElementById("dayCandlestickChartDiv"));
//                   dayCandlestickVolumeChart=ec.init(document.getElementById("dayCandlestickVolumeChart"));
//                   fiveCandlestickChartDiv=ec.init(document.getElementById("fiveCandlestickChartDiv"))
//                   fiveCandlestickVolumeChart=ec.init(document.getElementById("fiveCandlestickVolumeChart"));
//                   ec.connect("group4");
//                   TenCandlestickChartDiv=ec.init(document.getElementById("TenCandlestickChartDiv"));
//                   TenCandlestickVolumeChart=ec.init(document.getElementById("TenCandlestickVolumeChart"));
//                   ec.connect("group5");
//                   threeCandlestickChartDiv=ec.init(document.getElementById("threeCandlestickChartDiv"));
//                   threeCandlestickVolumeChart=ec.init(document.getElementById("threeCandlestickVolumeChart"));
//                     ec.connect("group6");
                }
        );
		
    };
    function sendHistoryMessageProtype(num){
    		console.log(num);
    		 var exchangeNo = $("#exchangeNo").val();
		    var commodityNo = $("#commodeityNo").val();
		    var contractNo = $("#contractNo").val();
		    masendMessage('QryHistory','{"ExchangeNo":"'+exchangeNo+'","CommodityNo":"'+commodityNo+'","ContractNo":"'+contractNo+'"}');
		    masendMessage('QryHistory','{"ExchangeNo":"'+exchangeNo+'","CommodityNo":"'+commodityNo+'","ContractNo":"'+contractNo+'","HisQuoteType":'+num+'}');
		  masendMessage('Subscribe','{"ExchangeNo":"'+exchangeNo+'","CommodityNo":"'+commodityNo+'","ContractNo":"'+contractNo+'"}');
	        setIntvalTimeAll = setInterval(function(){
	            masendMessage('QryHistory','{"ExchangeNo":"'+exchangeNo+'","CommodityNo":"'+commodityNo+'","ContractNo":"'+contractNo+'","Count":1,"HisQuoteType":'+num+'}');
	        },3000);
    }