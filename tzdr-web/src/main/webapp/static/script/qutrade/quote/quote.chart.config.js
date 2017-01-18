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
var Candlestick=null;
loadK();
var DateTimeStampSubscript;
var LastPriceSubscript;
var OpenPriceSubscript;
var LowPriceSubscript;
var HighPriceSubscript;
var VolumeSubscript;
function loadK(){
    require(
            [
                'echarts',
                'echarts/chart/pie', // 使用柱状图就加载bar模块，按需加载
            ],
            function (ec) {
                // 基于准备好的dom，初始化echarts图表
                timeChart = ec.init(document.getElementById('timeChart'));
                console.log(timeChart);
                volumeChart=ec.init(document.getElementById("volumeChart"));
               	 ec.connect("group1");
            }
    );
};
