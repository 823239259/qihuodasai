<template>
	<div id="tradebutton" :class="className" @touchstart="lontap" @touchend='levtap'>
		<div>
			<ul>
				<li class="fontwhite">{{marketprice}}</li>
				<li class="fontwhite">{{trade}}</li>
			</ul>
		</div>
	</div>
</template>

<script>
	export default{
		name:'tradebutton',
		props:['marketprice','transaction'], //市价、buy或sell、
		computed:{
			trade:function(){
				if(this.transaction=='buy'){
					return '买入';
				}else if(this.transaction=='sell'){
					return '卖出'
				}
			},
			className:function(){
				if(this.transaction=='buy'){
					return 'greenbtn';
				}else if(this.transaction=='sell'){
					return 'redbtn'
				}
			}
		},
		methods:{
			lontap:function(){
				var clname=this.$el.className;
				$(this.$el).removeClass().addClass(clname+'cl');
			},
			levtap:function(){
				var e=this.$el.className.substr(-2,2);
				if(e=='cl'){
					var clname=this.$el.className.slice(0,-2);
					$(this.$el).removeClass().addClass(clname);
				}
			}
		}
	}
</script>

<style scoped lang="less">
@import url("../assets/css/main.less");
#tradebutton div,#tradebutton div ul{
	height: 100%;
}
#tradebutton div ul>li{
	width: 100%;
	text-align: center;
	height: 50%;
}
#tradebutton div ul>li:first-child{
	border-bottom: 1px solid #fff;
}
.greenbtn{
	background-color:#3dcc85;
	border: 2px solid #5ce6a1;
}
.greenbtncl{
	background-color:#5ce6a1;
	border: 2px solid #5ce6a1;
}
.redbtn{
	border: 2px solid #ff6666;
	background-color:#FF4C4C;
}
.redbtncl{
	border: 2px solid #ff6666;
	background-color:#ff6666;
}
/*ip5*/
@media(max-width:370px) {
	#tradebutton{
		width: 165px*@ip5;
		height: 54px*@ip5;
		border-radius:28px*@ip5;
	}
	#tradebutton div ul{
		padding-left: 27px*@ip5;
		padding-right: 27px*@ip5;
	}
	#tradebutton div ul>li{
		line-height: 27px*@ip5;
		font-size: 14px*@ip5;
	}
}
/*ip6*/
@media (min-width:371px) and (max-width:410px) {
	#tradebutton{
		width: 165px*@ip6;
		height: 54px*@ip6;
		border-radius:28px*@ip6;
	}
	#tradebutton div ul{
		padding-left: 27px*@ip6;
		padding-right: 27px*@ip6;
	}
	#tradebutton div ul>li{
		line-height: 27px*@ip6;
		font-size: 14px*@ip6;
	}
}
/*ip6p及以上*/
@media (min-width:411px) {
	#tradebutton{
		width: 165px;
		height: 54px;
		border-radius:28px;
	}
	#tradebutton div ul{
		padding-left: 27px;
		padding-right: 27px;
	}
	#tradebutton div ul>li{
		line-height: 27px;
		font-size: 14px;
	}
}
</style>
