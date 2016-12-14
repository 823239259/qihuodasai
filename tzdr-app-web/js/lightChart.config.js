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
		var option = {
			backgroundColor: 'rgba(43, 43, 43, 0)',
		    tooltip: {
		    	 show: false,
		        trigger: 'axis'
		    },
		    toolbox: {
		        show: false,
		    },
		    xAxis:  {
		        type: 'category',
		        boundaryGap: true,
		         axisLine: { lineStyle: { color: '#8392A5' } },
		     	data:lightChartTime.time
		    },
		    yAxis: {
		        type: 'value',
		        scale: true,
		         axisLine: { lineStyle: { color: '#8392A5' } },
		    },
           grid: {
	               x: 55,
	               y:20,
	               x2:20,
	               y2:20
	           },
		    series: [
		        {
		            name:'',
		            type:'line',
	               lineStyle: {
	                   normal: {
	                       width: 1,
	                       color: "#ffffff"
	                   }
	               },
		            label: {
	                   normal: {
	                       show: false,
	                       position: 'inside'
	                   },
	               },
	            itemStyle:{
	               	 normal: {
	               	 	  show: false,
	                       color: "#ffffff"
	                   }
	               },
		            data:lightChartTime.price,
		        }
		    ]
		};


        return option
    }
