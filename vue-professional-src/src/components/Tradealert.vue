<template>
	<div id="alert" v-show='isshow'>
		<div>
			<ul>
				<li class="fontgray">
					{{title}}<i @tap="close"></i>
				</li>
				<li>
					<p class="fontwhite">{{line1}}</p>
					<p class="fontwhite">{{line2}}</p>
				</li>
				<li>
					<div class="fontgray fl" @tap='confirm(addr,objstr)'>确认</div>
					<div class="fontgray fl" @tap="close">取消</div>
				</li>
			</ul>
		</div>

	</div>
</template>

<script>
	export default {
		name: 'alert',
		props: ['title', 'line1', 'line2', 'addr', 'objstr','type'],
		data() {
			return {
				isshow: false
			}
		},
		computed:{
			tradeSocket:function(){
				return this.$store.state.tradeSocket;
			}
		},
		methods: {
			close: function() {
				this.isshow = false;
			},
			confirm: function(a, b) {
				if(this.type == '1'){
					var cnm = JSON.parse(this.objstr);
					for(var i=0;i<cnm.length;i++){
						this.tradeSocket.send(JSON.stringify(cnm[i]));
					}
				}else{
					console.log('1112222');
					
					this.tradeSocket.send(this.objstr);
				}
				this.isshow = false;
			}
		}
	}
</script>

<style scoped lang="less">
	@import url("../assets/css/main.less");
	@width: 330px;
	@height: 200px;
	#alert {
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		z-index: 1010;
		background-color: rgba(0, 0, 0, .8);
	}
	
	#alert>div {
		width: @width;
		height: @height;
		background-color: #1a1924;
		position: fixed;
		top: 270px;
		left: 40px;
	}
	
	#alert>div>ul>li {
		text-align: center;
		box-shadow: 0px 0px 1px #000;
	}
	
	#alert>div>ul>li:first-child {
		background-color: #242633;
		height: 42px;
		line-height: 42px;
		font-size: 17px;
		/*border: 1px solid #1b1b26;*/
	}
	
	#alert>div>ul>li:nth-child(2) {
		background-color: #242633;
		height: 104px;
		padding-top: 30px;
	}
	
	#alert>div>ul>li:nth-child(2)>p {
		font-size: 17px;
	}
	
	#alert>div>ul>li:nth-child(2)>p:first-child {
		margin-bottom: 12px;
		margin-top: -9px
	}
	
	#alert>div>ul>li:nth-child(3) {
		background-color: #242633;
		font-size: 17px;
		height: 48px;
		position: absolute;
		width: 100%;
		bottom: 0;
	}
	
	#alert>div>ul>li:nth-child(3)>div {
		height: 48px;
		width: 50%;
		line-height: 44px;
	}
	
	i {
		position: absolute;
		right: 0;
		top: 0;
		background-image: url('../assets/img/x.png');
		background-size: 100% 100%;
	}
	/*ip5*/
	
	@media(max-width:370px) {
		i {
			width: 33px*@ip5;
			height: 33px*@ip5;
		}
		#alert {
			position: fixed;
			top: 0;
			left: 0;
			right: 0;
			bottom: 0;
			z-index: 1010;
			background-color: rgba(0, 0, 0, .8);
		}
		#alert>div {
			width: @width*@ip5;
			height: @height*@ip5;
			background-color: #1a1924;
			position: fixed;
			top: 270px*@ip5;
			left: 42px*@ip5;
		}
		#alert>div>ul>li {
			text-align: center;
			box-shadow: 0px 0px 1px #000;
		}
		#alert>div>ul>li:first-child {
			background-color: #242633;
			height: 42px*@ip5;
			line-height: 42px*@ip5;
			font-size: 17px*@ip5;
			/*border: 1px solid #1b1b26;*/
		}
		#alert>div>ul>li:nth-child(2) {
			background-color: #242633;
			height: 104px*@ip5;
			padding-top: 30px*@ip5;
		}
		#alert>div>ul>li:nth-child(2)>p {
			font-size: 17px*@ip5;
		}
		#alert>div>ul>li:nth-child(2)>p:first-child {
			margin-bottom: 12px*@ip5;
			margin-top: -9px*@ip5
		}
		#alert>div>ul>li:nth-child(3) {
			background-color: #242633;
			font-size: 17px*@ip5;
			height: 48px*@ip5;
			position: absolute;
			width: 100%;
			bottom: 0;
		}
		#alert>div>ul>li:nth-child(3)>div {
			height: 48px*@ip5;
			width: 50%;
			line-height: 44px*@ip5;
		}
	}
	/*ip6*/
	
	@media (min-width:371px) and (max-width:410px) {
		i {
			width: 33px*@ip6;
			height: 33px*@ip6;
		}
		#alert {
			position: fixed;
			top: 0;
			left: 0;
			right: 0;
			bottom: 0;
			z-index: 1010;
			background-color: rgba(0, 0, 0, .8);
		}
		#alert>div {
			width: @width*@ip6;
			height: @height*@ip6;
			background-color: #1a1924;
			position: fixed;
			top: 270px*@ip6;
			left: 42px*@ip6;
		}
		#alert>div>ul>li {
			text-align: center;
			box-shadow: 0px 0px 1px #000;
		}
		#alert>div>ul>li:first-child {
			background-color: #242633;
			height: 42px*@ip6;
			line-height: 42px*@ip6;
			font-size: 17px*@ip6;
			/*border: 1px solid #1b1b26;*/
		}
		#alert>div>ul>li:nth-child(2) {
			background-color: #242633;
			height: 104px*@ip6;
			padding-top: 30px*@ip6;
		}
		#alert>div>ul>li:nth-child(2)>p {
			font-size: 17px*@ip6;
		}
		#alert>div>ul>li:nth-child(2)>p:first-child {
			margin-bottom: 12px*@ip6;
			margin-top: -9px*@ip6
		}
		#alert>div>ul>li:nth-child(3) {
			background-color: #242633;
			font-size: 17px*@ip6;
			height: 48px*@ip6;
			position: absolute;
			width: 100%;
			bottom: 0;
		}
		#alert>div>ul>li:nth-child(3)>div {
			height: 48px*@ip6;
			width: 50%;
			line-height: 44px*@ip6;
		}
	}
	/*ip6p及以上*/
	
	@media (min-width:411px) {
		i {
			width: 33px;
			height: 33px;
		}
		#alert {
			position: fixed;
			top: 0;
			left: 0;
			right: 0;
			bottom: 0;
			z-index: 1010;
			background-color: rgba(0, 0, 0, .8);
		}
		#alert>div {
			width: @width;
			height: @height;
			background-color: #1a1924;
			position: fixed;
			top: 270px;
			left: 42px;
		}
		#alert>div>ul>li {
			text-align: center;
			box-shadow: 0px 0px 1px #000;
		}
		#alert>div>ul>li:first-child {
			background-color: #242633;
			height: 42px;
			line-height: 42px;
			font-size: 17px;
			/*border: 1px solid #1b1b26;*/
		}
		#alert>div>ul>li:nth-child(2) {
			background-color: #242633;
			height: 104px;
			padding-top: 30px;
		}
		#alert>div>ul>li:nth-child(2)>p {
			font-size: 17px;
		}
		#alert>div>ul>li:nth-child(2)>p:first-child {
			margin-bottom: 12px;
			margin-top: -9px
		}
		#alert>div>ul>li:nth-child(3) {
			background-color: #242633;
			font-size: 17px;
			height: 48px;
			position: absolute;
			width: 100%;
			bottom: 0;
		}
		#alert>div>ul>li:nth-child(3)>div {
			height: 48px;
			width: 50%;
			line-height: 44px;
		}
	}
</style>