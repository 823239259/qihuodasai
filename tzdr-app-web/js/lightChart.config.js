var lightChartTime={
	"time":[],
	"price":[]
}
function lightChartData(json){
    	var dosizeL=$("#doSize").val();
        var TimeLength=lightChartTime.time.length;
        lightChartTime.price.push(json.Parameters.LastPrice);
           lightChartTime.time.push(json.Parameters.DateTimeStamp);
//		for(var i=0;i<lightChartTime.time.length-1;i++){
//			if(lightChartTime.time[i]==lightChartTime.time[i+1]){
//				lightChartTime.time.splice(i,1);
//				lightChartTime.price.splice(i,1);
//			}
//		}
		lightChartTime.time=lightChartTime.time.slice(-100);
		lightChartTime.price=lightChartTime.price.slice(-100);
//		console.log(JSON.stringify(lightChartTime));
        if(lightChart != null){
        	var option = lightChartDealData();
            lightChart.setOption(option);
            lightChart.resize();
        }

  }
    function lightChartDealData(){
     var  option = {
       	backgroundColor: 'rgba(43, 43, 43, 0)',
           tooltip : {
               show: true,
               transitionDuration:0,
               trigger: 'axis',
               axisPointer : {
                   type : 'line',
                   animation: false,
		            lineStyle: {
		                color: '#ffffff',
		                width: 1,
		                opacity: 1
		            }
               },
//             formatter: function(params) {
//             	console.log(JSON.stringify(params));
//             	  var time  = params[0].name;
//                 var val   = params[0].value;
//                 var html  = '时间:'+time + '<br/>' +
//                         '价格: ' + val + '<br/>';
//                 return html;
//             },
           },
           toolbox: {
               show: false,
           },
           animation: false,
			 xAxis:[{
					type: 'category',
					show:false,
			        data: lightChartTime.time,
			        axisLine: { lineStyle: { color: '#8392A5' } },
			        boundaryGap: true
			}],	
           yAxis:  [
               {
                   type: 'value',
                   scale: true,
                   position:"left",
                   axisTick:{
                   	show:false,
                   },
                    axisLine: { lineStyle: { color: '#8392A5' } },
                   splitArea: {
                       show: false
                   },
                    axisLabel: {
                        inside: false,
                        margin: 4,
                    },
                  splitLine: {
                    show: true,
                    lineStyle: {
                        color: "#8392A5"
                    }
                }
               }
           ],
           grid: {
               x: 40,
               y:20,
               x2:20,
               y2:5
           },
           series: {
           	type: 'line',
               label: {
                   normal: {
                       show: false,
                       position: 'inside'
                   },
               },
               lineStyle: {
                   normal: {
                       width: 1,
                       color: "#ffffff"
                   }
               },
               itemStyle:{
               	 normal: {
                       color: "#ffffff"
                   }
               },
               symbolSize: 2,
               data:lightChartTime.price,
           }
       }

        return option
    }
