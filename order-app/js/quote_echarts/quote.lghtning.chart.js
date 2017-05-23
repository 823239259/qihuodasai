



function lightChartData(json) {
		if(json.Parameters.CommodityNo==SuperCommodityNo){
	        var TimeLength=lightChartTime.time.length;
	        var LastPrice=fixedPriceByContract(json.Parameters.LastPrice, $("#commodityNo").val());
	        lightChartTime.price.push(LastPrice);
	        lightChartTime.time.push((json.Parameters.DateTimeStamp).split(" ")[1]);
			lightChartTime.time=lightChartTime.time.slice(-50);
			lightChartTime.price=lightChartTime.price.slice(-50);
	        if(lightChart != null){
		        var option = lightChartDealData();
		        lightChart.setOption(option);
		        lightChart.resize();
	        }
	    }    
	       
  }
    function lightChartDealData() {
     var  option = {
		    backgroundColor: "#fff",
		    "tooltip": {
		        "show":false,
		    },
            animation: false,
	           grid: {
	               x: 50,
	               y:80,
	               x2:46,
	               y2:20
	           },
				 xAxis:[{
						type: 'category',
						show:true,
				        data: lightChartTime.time,
				        axisLine: { lineStyle: { color: '#777777' } },
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
	                    axisLine: { lineStyle: { color: '#777777' } },
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
	                        color: "#CCCCCC"
	                    }
	                }
	               }
	           ],
		    "series": [{
		            "name": "总数",
		            "type": "line",
		            "stack": "总量",
		            symbolSize: 10,
		            symbol: 'circle',
		            "itemStyle": {
		                "normal": {
		                    "color": "#3A4F5D",
		                    "barBorderRadius": 0,
		                    "label": {
		                        "show": true,
		                        "position": "top",
		                        formatter: function(p) {
		                            return p.value > 0 ? (p.value) : '';
		                        }
		                    }
		                }
		            },
		            "data":lightChartTime.price
		        }
		    ]
		}
        return option
    }
