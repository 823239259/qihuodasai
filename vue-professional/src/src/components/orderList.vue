<template>
	<div id="orderList" class="list" :val="getData">
		<ul>
			<li class="list_head">
				<span>合约名称</span>
				<span>多空</span>
				<span>手数</span>
				<span>平仓均价</span>
				<span>浮动盈利</span>
			</li>
		</ul>
		<ul class="list_cont_box">
			<template v-for="k in datas">
				<li>
					<div :class="[list_cont,{current:k.showbar}]" @tap="listTap(datas)">
						<span>{{k.name}}</span>
						<span :class="{red: k.total_color == 'red', green: k.total_color == 'green'}">{{k.type}}</span>
						<span>{{k.num}}</span>
						<span>{{k.price}}</span>
						<span :class="{red: k.total_color == 'red', green: k.total_color == 'green'}">{{k.total}}</span>
					</div>
					<!--<transition name="fade" mode="out-in">-->
						<div class="list_tools" v-show="k.showbar">
							<cbtn name="暂停"></cbtn>
							<cbtn name="修改"></cbtn>
							<cbtn name="删除"></cbtn>
						</div>
					<!--</transition>-->
				</li>
			</template>
		</ul>
	</div>
</template>

<script>
	import cbtn from '../components/conditionBtn.vue'
	export default{
		name: 'orderList',
		components: {cbtn},
		props: ['val'],
		data(){
			return {
				datas: '',
			}
		},
		computed: {
			list_cont: function(){
				return 'list_cont'
			},
			getData: function(){
				if(this.val){
					this.datas = JSON.parse(this.val);
				}
			}
		},
		methods: {
			listTap: function(obj){
				var index  = $(event.currentTarget).parents("li").index();
				console.log(index);
				if(obj[index].showbar == false){
					for(var i in obj){
						obj[i].showbar = false;
					}
					obj[index].showbar = true;
				}else{
					obj[index].showbar = false;
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
		.list{
			.list_cont_box{
				overflow-y: scroll;
			}
			li{
				width: 100%;
				background: @deepblue;
				border-top: 1px solid @black;
				&.list_head{
					height: 44px;
					padding: 0 3.62%;
					background: #36394d;
				}
				.list_cont{
					height: 44px;
					padding: 0 3.62%;
					&.current{
						background: #2d3040;
					}
				}
				span{
					display: inline-block;
					height: 44px;
					line-height: 44px;
					overflow: hidden;
					color: @lightblue;
					font-size: @fs14;
					margin: 0 0.4%;
					&:nth-child(1){
						width: 22.61%;
					}
					&:nth-child(2){
						width: 14.5%;
					}
					&:nth-child(3){
						width: 14.5%;
					}
					&:nth-child(4){
						width: 21.5%;
					}
					&:nth-child(5){
						width: 16.5%;
					}
					&.red{
						color: @red;
					}
					&.green{
						color: @green;
					}
				}
				.list_tools{
					height: 44px;
					overflow: hidden;
					border-top: 1px solid @black;
					background: #2d3040;
					padding: 0 3.62%;
					#conditionBtn{
						float: right;
						margin: 6px 0 0 5px;
					}
				}
			}
		}
	}
	
	/*ip6*/
	@media (min-width:371px) and (max-width:410px) {
		.list{
			.list_cont_box{
				overflow-y: scroll;
			}
			li{
				width: 100%;
				background: @deepblue;
				border-top: 1px solid @black;
				&.list_head{
					height: 44px*@ip6;
					padding: 0 3.62%;
					background: #36394d;
				}
				.list_cont{
					height: 44px*@ip6;
					padding: 0 3.62%;
					&.current{
						background: #2d3040;
					}
				}
				span{
					display: inline-block;
					height: 44px*@ip6;
					line-height: 44px*@ip6;
					overflow: hidden;
					color: @lightblue;
					font-size: @fs14;
					margin: 0 0.4%;
					&:nth-child(1){
						width: 21.11%;
					}
					&:nth-child(2){
						width: 14.5%;
					}
					&:nth-child(3){
						width: 14.5%;
					}
					&:nth-child(4){
						width: 21.5%;
					}
					&:nth-child(5){
						width: 18.5%;
					}
					&.red{
						color: @red;
					}
					&.green{
						color: @green;
					}
				}
				.list_tools{
					height: 44px*@ip6;
					overflow: hidden;
					border-top: 1px solid @black;
					background: #2d3040;
					padding: 0 3.62%;
					#conditionBtn{
						float: right;
						margin: 6px*@ip6 0 0 5px*@ip6;
					}
				}
			}
		}
	}
	
	/*ip5*/
	@media(max-width:370px) {
		.list{
			.list_cont_box{
				overflow-y: scroll;
			}
			li{
				width: 100%;
				background: @deepblue;
				border-top: 1px solid @black;
				&.list_head{
					height: 44px*@ip5;
					padding: 0 3.62%;
					background: #36394d;
				}
				.list_cont{
					height: 44px*@ip5;
					padding: 0 3.62%;
					&.current{
						background: #2d3040;
					}
				}
				span{
					display: inline-block;
					height: 44px*@ip5;
					line-height: 44px*@ip5;
					overflow: hidden;
					color: @lightblue;
					font-size: @fs14;
					margin: 0 0.4%;
					&:nth-child(1){
						width: 21.11%;
					}
					&:nth-child(2){
						width: 14%;
					}
					&:nth-child(3){
						width: 14%;
					}
					&:nth-child(4){
						width: 21%;
					}
					&:nth-child(5){
						width: 19%;
					}
					&.red{
						color: @red;
					}
					&.green{
						color: @green;
					}
				}
				.list_tools{
					height: 44px*@ip5;
					overflow: hidden;
					border-top: 1px solid @black;
					background: #2d3040;
					padding: 0 3.62%;
					#conditionBtn{
						float: right;
						margin: 6px*@ip5 0 0 5px*@ip5;
					}
				}
			}
		}
	}
</style>