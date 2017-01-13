    function lightChartDealData(){
     var  option = {
		    backgroundColor: "#1f1f1f",
		    "tooltip": {
		        "show":false,
		    },
            animation: false,
	           grid: {
	               x: 50,
	               y:40,
	               x2:46,
	               y2:20
	           },
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
		    "series": [{
		            "name": "总数",
		            "type": "line",
		            "stack": "总量",
		            symbolSize: 10,
		            symbol: 'circle',
		            "itemStyle": {
		                "normal": {
		                    "color": "#8392A5",
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
