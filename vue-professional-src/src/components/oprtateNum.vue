<template>
	<div id="operatenum" class="market_cont">
		<span class="operation" @tap="add">+</span>
		<span>手数：</span>
		<input type="number" :class="widthEvent" v-model="defaultNum" />
		<span class="white">(市价)</span>
		<span class="operation" @tap="reduce">-</span>
	</div>
</template>

<script>
	export default{
		name: 'operatenum',
		data(){
			return {
				defaultNum: 1
			}
		},
		props: ['w'],
		computed: {
			widthEvent: function(){
				if(this.w == 'sm'){
					return 'ipt';
				}else{
					return '';
				}
			}
		},
		components: {},
		methods: {
			add: function(){
				return this.defaultNum++;
			},
			reduce: function(){
				return this.defaultNum--;
			}
		},
		watch: {
			defaultNum: function(n,o){
				this.defaultNum = parseInt(n);
				if(n < 1 || n == ''){
					this.defaultNum = 0;
				}
			}
		}
	}
</script>

<style scoped lang="less">
	@import url("../assets/css/main.less");
	@import url("../assets/css/base.less");
	/*ip6p及以上*/
	@media (min-width:411px) {
	    .market_cont{
			height: 45px;
			background: @black;
			border: 1px solid #12121a;
			border-radius: 5px;
			span{
				float: left;
				display: block;
				height: 45px;
				line-height: 45px;
				color: @blue;
				font-size: @fs14;
				&:nth-child(2){
					margin-left: 15px;
				}
				&.operation{
					width: 54px;
					text-align: center;
					font-size: 24px;
					&:last-child{
						line-height: 42px;
					}
				}
				&:first-child{
					border-right: 1px solid #12121a; 
				}
				&:last-child{
					float: right;
					border-left: 1px solid #12121a;
				}
				&.white{
					color: @white;
				}
			}
			input{
				float: left;
				width: 160px;
				height: 45px;
				font-size: @fs14;
				background: none;
				border: none;
				margin: 0;
				padding: 5px 10px;
				color: @white;
				text-align: center;
				&.ipt{
					width: 85px;
				}
			}
		}
	}
	/*ip6*/
	@media (min-width:371px) and (max-width:410px) {
	    .market_cont{
			height: 45px*@ip6;
			background: @black;
			border: 1px solid #12121a;
			border-radius: 5px*@ip6;
			span{
				float: left;
				display: block;
				height: 45px*@ip6;
				line-height: 45px*@ip6;
				color: @blue;
				font-size: @fs14*@ip6;
				&:nth-child(2){
					margin-left: 15px*@ip6;
				}
				&.operation{
					width: 54px*@ip6;
					text-align: center;
					font-size: 24px*@ip6;
					&:last-child{
						line-height: 42px*@ip6;
					}
				}
				&:first-child{
					border-right: 1px solid #12121a; 
				}
				&:last-child{
					float: right;
					border-left: 1px solid #12121a;
				}
				&.white{
					color: @white;
				}
			}
			input{
				float: left;
				width: 160px*@ip6;
				height: 45px*@ip6;
				font-size: @fs14*@ip6;
				background: none;
				border: none;
				margin: 0;
				padding: 5px*@ip6 10px*@ip6;
				color: @white;
				text-align: center;
				&.ipt{
					width: 85px*@ip6;
				}
			}
		}
	}
	/*ip5*/
	@media(max-width:370px) {
		.market_cont{
			height: 45px*@ip5;
			background: @black;
			border: 1px solid #12121a;
			border-radius: 5px*@ip5;
			span{
				float: left;
				display: block;
				height: 45px*@ip5;
				line-height: 45px*@ip5;
				color: @blue;
				font-size: @fs14*@ip5;
				&:nth-child(2){
					margin-left: 15px*@ip5;
				}
				&.operation{
					width: 54px*@ip5;
					text-align: center;
					font-size: 24px*@ip5;
					&:last-child{
						line-height: 42px*@ip5;
					}
				}
				&:first-child{
					border-right: 1px solid #12121a; 
				}
				&:last-child{
					float: right;
					border-left: 1px solid #12121a;
				}
				&.white{
					color: @white;
				}
			}
			input{
				float: left;
				width: 160px*@ip5;
				height: 45px*@ip5;
				font-size: @fs14*@ip5;
				background: none;
				border: none;
				margin: 0;
				padding: 5px*@ip5 10px*@ip5;
				color: @white;
				text-align: center;
				&.ipt{
					width: 85px*@ip5;
				}
			}
		}
	}
		
</style>