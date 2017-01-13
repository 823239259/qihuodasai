/**
 *引入echart配置 **/
var requireEchart=require.config({
    paths:{
        'echarts' :'./../../static/script/qutrade/echarts.min',
        'echarts/chart/pie' :'../../static/script/qutrade/echarts.min',
    }
});
var timeChart=null;
var volumeChart=null;
function loadK(){
    require(
            [
                'echarts',
                'echarts/chart/pie', // 使用柱状图就加载bar模块，按需加载
            ],
            function (ec) {
                // 基于准备好的dom，初始化echarts图表
                timeChart = ec.init(document.getElementById('timeChart'));
                echarts=ec;
                volumeChart=ec.init(document.getElementById("volumeChart"));
               	 ec.connect("group1");
            }
    );
};