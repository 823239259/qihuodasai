 var DateTimeStampSubscript;
var LastPriceSubscript;
var OpenPriceSubscript;
var LowPriceSubscript;
var HighPriceSubscript;
var VolumeSubscript;
var	CandlestickChart = null;
var timeChart=null;
var volumeChart=null;
var CandlestickVolumeChart=null;
var lightChart=null;
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
function loadEchart() {
	CandlestickChart = echarts.init(document.getElementById('candlestickChartDiv'));
	timeChart=echarts.init(document.getElementById("timeChart"));
	volumeChart=echarts.init(document.getElementById("volumeChart"));
	lightChart=echarts.init(document.getElementById("lightChart"));
	CandlestickVolumeChart=echarts.init(document.getElementById("candlestickVolumeChart"));
	echarts.connect("group1");
	echarts.connect("group2");
}
loadEchart()