/**
 *引入echart配置 **/
var requireEchart=require.config({
    paths:{
        'echarts' :'../../static/script/qutrade/echarts',
        'echarts/chart/pie' :'../../static/script/qutrade/echarts',
    }
});
var timeChart=null;
var volumeChart=null;
var CandlestickChart=null;
var CandlestickVolumeChart=null;
var lightChart=null;
loadK();
var DateTimeStampSubscript;
var LastPriceSubscript;
var OpenPriceSubscript;
var LowPriceSubscript;
var HighPriceSubscript;
var VolumeSubscript;
var timeData={
	"timeLabel":[],
	"prices":[],
	"time":[]
}
var volumeChartData={
	"time":[],
	"volume":[]
}
var rawData=[];
var CandlestickVolumeData={
	"time":[],
	"volume":[]
}
var CandlestickData={
	"categoryData":[],
	"values":[]
};
var lightChartTime={
	"time":[],
	"price":[]
}
function loadK(){
    require(
            [
                'echarts',
                'echarts/chart/pie', // 使用柱状图就加载bar模块，按需加载
            ],
            function (ec) {
                // 基于准备好的dom，初始化echarts图表
                	timeChart = ec.init(document.getElementById('timeChart'));
                	volumeChart=ec.init(document.getElementById("volumeChart"));
                 	CandlestickChart=ec.init(document.getElementById("CandlestickChart"));
                 	CandlestickVolumeChart=ec.init(document.getElementById("CandlestickVolumeChart"));
                 	lightChart=ec.init(document.getElementById("container1"));
               	 	ec.connect("group1");
               	 	ec.connect("group2");
            }
    );
};
