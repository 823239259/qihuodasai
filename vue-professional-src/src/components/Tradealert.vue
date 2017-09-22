<template>
	<div id="alert" v-show='isshow'>
		<div>
			<ul>
				<li class="fontgray">
					{{title}}<i @tap="close"></i>
				</li>
				<li>
					<p class="fontwhite">{{line1}}</p>
					<!--<p class="fontwhite">{{line2}}</p>-->
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
		props: ['title', 'line1', 'addr', 'objstr','type', 'jump', 'status'],
		data() {
			return {
				isshow: false,
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
//				}else if(this.type == '2'){
//					if(this.objstr) this.tradeSocket.send(this.objstr);
//					this.$parent.hasNostopLossList.splice(i,1);
//					this.$parent.stopLossList.splice(i,1);
				}else{
					if(this.objstr){
						this.tradeSocket.send(this.objstr);
					}
				}
				if(this.jump && this.jump == 'true'){
					this.$router.push({path: '/tradeLogin'});
				}
				this.isshow = false;
				this.$parent.isshow = false;
				if(this.status == 1){
					this.$parent.status = 1;
				}
			}
		}
	}
</script>

<style scoped lang="less">
@import url("../assets/css/main.less");
/*@width: 330px;
@height: 200px;*/
/*ip6p及以上*/
@media (min-width:411px) {
	#alert{
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		z-index: 1010;
		background-color: rgba(0, 0, 0, .8);
	}
	#alert>div{
		width: 330px;
		height: 200px;
		background-color: #1a1924;
		position: absolute;
		top: 50%;
		left: 50%;
		margin: -100px 0 0 -165px;
	}
	#alert>div>ul>li {
		text-align: center;
		box-shadow: 0px 0px 1px #000;
		background-color: #242633;
	}
	#alert>div>ul>li:first-child {
		height: 42px;
		line-height: 42px;
		font-size: 16px;
	}
	#alert>div>ul>li:nth-child(2) {
		height: 100px;
		padding: 0 10px;
		display:flex;
		justify-content: center;
		align-items: center;
	}
	#alert>div>ul>li:nth-child(2)>p {
		font-size: 14px;
		
	}
	#alert>div>ul>li:nth-child(3) {
		height: 48px;
		line-height: 48px;
		font-size: 14px;
		position: absolute;
		width: 100%;
		bottom: 0;
		left: 0;
	}
	#alert>div>ul>li:nth-child(3)>div {
		width: 50%;
	}
	i{
		position: absolute;
		right: 0;
		top: 0;
		width: 32px;
		height: 32px;
		background: url('../assets/img/x.png') no-repeat center center;
		background-size: 100% 100%;
	}
}
/*ip6*/
@media (min-width:371px) and (max-width:410px) {
	#alert{
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		z-index: 1010;
		background-color: rgba(0, 0, 0, .8);
	}
	#alert>div{
		width: 330px*@ip6;
		height: 200px*@ip6;
		background-color: #1a1924;
		position: absolute;
		top: 50%;
		left: 50%;
		margin: -100px*@ip6 0 0 -165px*@ip6;
	}
	#alert>div>ul>li {
		text-align: center;
		box-shadow: 0px 0px 1px #000;
		background-color: #242633;
	}
	#alert>div>ul>li:first-child {
		height: 42px*@ip6;
		line-height: 42px*@ip6;
		font-size: 16px*@ip6;
	}
	#alert>div>ul>li:nth-child(2) {
		height: 100px*@ip6;
		padding: 0 10px*@ip6;
		display:flex;
		justify-content: center;
		align-items: center;
	}
	#alert>div>ul>li:nth-child(2)>p {
		font-size: 14px*@ip6;
	}
	#alert>div>ul>li:nth-child(3) {
		height: 48px*@ip6;
		line-height: 48px*@ip6;
		font-size: 14px*@ip6;
		position: absolute;
		width: 100%;
		bottom: 0;
		left: 0;
	}
	#alert>div>ul>li:nth-child(3)>div {
		width: 50%;
	}
	i{
		position: absolute;
		right: 0;
		top: 0;
		width: 32px*@ip6;
		height: 32px*@ip6;
		background: url('../assets/img/x.png') no-repeat center center;
		background-size: 100% 100%;
	}
}
/*ip5*/
@media(max-width:370px) {
	#alert{
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		z-index: 1010;
		background-color: rgba(0, 0, 0, .8);
	}
	#alert>div{
		width: 330px*@ip5;
		height: 200px*@ip5;
		background-color: #1a1924;
		position: absolute;
		top: 50%;
		left: 50%;
		margin: -100px*@ip5 0 0 -165px*@ip5;
	}
	#alert>div>ul>li {
		text-align: center;
		box-shadow: 0px 0px 1px #000;
		background-color: #242633;
	}
	#alert>div>ul>li:first-child {
		height: 42px*@ip5;
		line-height: 42px*@ip5;
		font-size: 16px*@ip5;
	}
	#alert>div>ul>li:nth-child(2) {
		height: 100px*@ip5;
		padding: 0 10px*@ip5;
		display:flex;
		justify-content: center;
		align-items: center;
	}
	#alert>div>ul>li:nth-child(2)>p {
		font-size: 14px*@ip5;
	}
	#alert>div>ul>li:nth-child(3) {
		height: 48px*@ip5;
		line-height: 48px*@ip5;
		font-size: 14px*@ip5;
		position: absolute;
		width: 100%;
		bottom: 0;
		left: 0;
	}
	#alert>div>ul>li:nth-child(3)>div {
		width: 50%;
	}
	i{
		position: absolute;
		right: 0;
		top: 0;
		width: 32px*@ip5;
		height: 32px*@ip5;
		background: url('../assets/img/x.png') no-repeat center center;
		background-size: 100% 100%;
	}
}
</style>