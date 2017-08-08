<template>
	<button id="chartBtn" :class="className" @touchstart="lontap" @touchend='levtap'>{{name}}</button>
</template>

<script>
	export default{
		name:'chartBtn',
		props: ['type'],
		computed: {
			className: function(){
				if(this.type == 'buy'){
					return 'redbtn';
				}else{
					return 'greenbtn';
				}
			},
			name: function(){
				if(this.type == 'buy'){
					return '买入';
				}else{
					return '卖出';
				}
			}
		},
		methods: {
			lontap:function(e){
				$(e.target).addClass("current");
			},
			levtap:function(e){
				$(e.target).removeClass("current");
			}
		}
	}
</script>

<style scoped lang="less">
@import url("../assets/css/main.less");
@import url("../assets/css/base.less");
/*ip6p及以上*/
@media (min-width:411px) {
    #chartBtn{
    	display: inline-block;
    	width: 165px;
    	height: 54px;
    	overflow: hidden;
    	border-radius: 27px;
    	color: @white;
    	font-size: @fs18;
    	text-align: center;
    	&.greenbtn{
			background: #3dcc85;
			border-color: #5ce6a1;
			&.current{
				background: #5ce6a1;
			}
		}
		&.redbtn{
			background: #FF4C4C;
			border-color: #ff6666;
			&.current{
				background: #ff6666;
			}
		}
    }
}
/*ip6*/
@media (min-width:371px) and (max-width:410px) {
	#chartBtn{
    	display: inline-block;
    	width: 165px*@ip6;
    	height: 54px*@ip6;
    	overflow: hidden;
    	border-radius: 27px*@ip6;
    	color: @white;
    	font-size: @fs18*@ip6;
    	text-align: center;
    	&.greenbtn{
			background:#3dcc85;
			border-color: #5ce6a1;
			&.current{
				background: #5ce6a1;
			}
		}
		&.redbtn{
			background: #FF4C4C;
			border-color: #ff6666;
			&.current{
				background: #ff6666;
			}
		}
    }
}
/*ip5*/
@media(max-width:370px) {
	#chartBtn{
    	display: inline-block;
    	width: 165px*@ip5;
    	height: 54px*@ip5;
    	overflow: hidden;
    	border: 2px solid @white;
    	border-radius: 27px*@ip5;
    	color: @white;
    	font-size: @fs18*@ip5;
    	text-align: center;
    	&.greenbtn{
			background:#3dcc85;
			border-color: #5ce6a1;
			&.current{
				background: #5ce6a1;
			}
		}
		&.redbtn{
			background: #FF4C4C;
			border-color: #ff6666;
			&.current{
				background: #ff6666;
			}
		}
    }
}
</style>