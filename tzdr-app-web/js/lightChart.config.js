var lightChartTime={
	"time":[],
	"price":[]
}
function lightChartData(json){
    	var dosizeL=$("#doSize").val();
    	var CommodityNo=$("#CommodityNo").text();
    	var CommodityNo1=json.Parameters.CommodityNo+json.Parameters.ContractNo;
    	if(CommodityNo==CommodityNo1){
	        var TimeLength=lightChartTime.time.length;
	        lightChartTime.price.push(json.Parameters.LastPrice);
	           lightChartTime.time.push((json.Parameters.DateTimeStamp).split(" ")[1]);
			lightChartTime.time=lightChartTime.time.slice(-100);
			lightChartTime.price=lightChartTime.price.slice(-100);
	        if(lightChart != null){
	        	var option = lightChartDealData();
	            lightChart.setOption(option);
	            lightChart.resize();
	        }
    	}
  }
    function lightChartDealData(){
       var  option = {
       	backgroundColor: 'rgba(43, 43, 43, 0)',
           tooltip : {
               show: false,
           },
           toolbox: {
               show: false,
           },
           animation: false,
			 xAxis:[{
					type: 'category',
					show:true,
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
               x2:46,
               y2:20
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
               data:lightChartTime.price
           }
       }
        return option
    }
