<template>
	<div id="stopmoneyalert" v-if='isshow'>
		<div class="bg">
			<div>
				<div :class="[fl,{current:isstopm}]" @click="sel">
					止损
				</div>
				<div :class="[fl,{current:!isstopm}]" @click="sel">
					止盈
				</div>
			</div>
			<template v-if="isstopm">
				<ul class="cl">
					<li>
						<ol class="cl">
							<li class="fl fontgray">合约</li>
							<li class="fl fontwhite">{{commodityObj.CommodityName}}</li>
							<li class="fl fontgray">{{condition.Drection==0?'多':'空'}}</li>
							<li class="fl fontgray">
								最新：<span class="fontwhite">{{templateListObj.LastPrice}}</span>
							</li>
						</ol>
					</li>
					<li>
						<ol class="cl">
							<li class="fl fontgray">方式</li>
							<li class="fl">
								<select class="fontwhite sellong">
									<option>止损价</option>
									<option>动态价</option>
								</select>
								<input type="text" value='89.64' class="inp" />
								<span class="fontgray">0.00%</span>
							</li>
						</ol>
					</li>
					<li>
						<ol class="cl">
							<li class="fl fontgray">手数</li>
							<li class="fl"><input class='inp' type="text" value="10" /></li>
							<li class="fl  fontgray">
								止损委托价：
								<select name="" class='fontwhite selshort'>
									<option value="">市价</option>
									<option value="">限价</option>
								</select>
							</li>
						</ol>
					</li>
				</ul>
			</template>
			<template v-else="isstopm">
				<ul class="cl">
					<li>
						<ol class="cl">
							<li class="fl fontgray">合约</li>
							<li class="fl fontwhite">{{commodityObj.CommodityName}}</li>
							<li class="fl fontgray">多头</li>
							<li class="fl fontgray">
								最新：<span class="fontwhite">69.65</span>
							</li>
						</ol>
					</li>
					<li>
						<ol class="cl">
							<li class="fl fontgray">止盈价</li>
							<li class="fl">
								<input type="text" value='89.64' class="inp" />
								<span class="fontgray">0.00%</span>
							</li>
						</ol>
					</li>
					<li>
						<ol class="cl">
							<li class="fl fontgray">手数</li>
							<li class="fl"><input class='inp' type="text" value="10" /></li>
							<li class="fl  fontgray">
								止损委托价：
								<select name="" class='fontwhite selshort'>
									<option value="">市价</option>
									<option value="">限价</option>
								</select>
							</li>
						</ol>
					</li>
				</ul>
			</template>
			<div class="cl">
				<div class="fl fontgray" @tap='close'>关闭</div>
				<div class="fl fontgray" @tap='confirm'>添加</div>
			</div>

		</div>
	</div>
</template>

<script>
	export default {
		name: 'stopmoneyalert',
		data() {
			return {
				isstopm: true,
				isshow:false
			}
		},
		props: ['val'],
		computed: {
			fl() {
				return 'fl'
			},
			orderTemplist(){
				return	this.$store.state.market.orderTemplist;
			},
			condition(){
//				console.log(JSON.parse(this.val));
				return JSON.parse(this.val);
			},
			commodityObj(){
				
				return this.orderTemplist[this.condition.CommodityNo];
			},
			templateListObj(){
//				this.$store.state.market.Parameters.forEach(function(a, r) {
//				if(a.CommodityNo == this.condition.CommodityNo) {
//					context.state.market.Parameters.splice(r, 1, e);
//					}
//				});
//				console.log(this.$store.state.market.templateList[this.condition.CommodityNo]);
				return this.$store.state.market.templateList[this.condition.CommodityNo];
			},
		},
		watch:{
			templateListObj:function(n,o){
//				console.log(1331);
				return this.$store.state.market.templateList[this.condition.CommodityNo];
			}
		},
		methods: {
			sel: function(e) {
				var txt = e.target.innerText;
				switch(txt) {
					case '止损':
						this.isstopm = true;
						break;
					case '止盈':
						this.isstopm = false;
						break;
				}
			},
			close: function() {
				this.isshow = false;
			},
			confirm: function() {
				/*
				 * 确认并提交数据到后台
				 * @param {String} a '提交到后台的地址';{String} b '提交到后台的对象字符串'
				 */
				this.isshow = false;
				console.log(this.val);
				console.log(this.orderTemplist[this.condition.CommodityNo]);
			}
		},
		mounted: function(){
			console.log(this.$parent.$parent.$parent.$parent);
		}
	}
</script>

<style scoped lang="less">
	@import url("../assets/css/main.less");
	@width: 330px;
	@height: 226px;
	#stopmoneyalert {
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		background-color: rgba(0, 0, 0, .8);
		font-size: 15px;
		z-index: 1100;
	}
	
	.bg {
		width: @width;
		height: @height;
		background-color: #1b1b26;
		position: fixed;
		top: 212px;
		left: 40px;
		position: relative;
		box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.3), -1px -1px 3px rgba(0, 0, 0, 0.3);
		border-top-left-radius: 5px;
		border-top-right-radius: 5px;
		border-bottom-left-radius: 5px;
		border-bottom-right-radius: 5px;
	}
	
	.bg>div {
		width: 100%;
		height: 44px;
		background-color: #242633;
		border-top-left-radius: 5px;
		border-top-right-radius: 5px;
	}
	
	.bg>ul {
		background-color: #242633;
		width: 100%;
		height: 132px;
	}
	
	.bg>div:after {
		content: '';
		display: div;
		clear: both;
	}
	
	.bg>div:first-child>div {
		color: #949bbb;
		width: 50%;
		height: 44px;
		text-align: center;
		line-height: 44px;
	}
	
	.bg>div:last-child>div {
		color: #949bbb;
		width: 50%;
		height: 44px;
		text-align: center;
		line-height: 44px;
	}
	
	.bg>div:last-child {
		border-bottom-left-radius: 5px;
		border-bottom-right-radius: 5px;
	}
	
	ol {
		height: 44px;
		width: 100%;
	}
	
	ol>li {
		text-align: center;
	}
	
	ol>li:first-child {
		width: 56px;
		border-right: 1px solid #1c1c27;
	}
	
	ul>li:first-child>ol>li:nth-child(2) {
		width: 111px;
		border-right: 1px solid #1c1c27;
	}
	
	ul>li:first-child>ol>li:nth-child(3) {
		width: 56px;
		border-right: 1px solid #1c1c27;
	}
	
	ul>li:first-child>ol>li:nth-child(4) {
		width: 100px;
	}
	
	ul>li:nth-child(2)>ol>li:nth-child(2) {
		padding-left: 5px;
	}
	
	ul>li:nth-child(3)>ol>li:nth-child(2) {
		width: 110px;
		border-right: 1px solid #1c1c27;
	}
	
	ul>li:nth-child(3)>ol>li:nth-child(3) {
		padding-left: 5px;
	}
	
	.inp {
		width: 99px;
		height: 33px;
		border-radius: 3px;
		border: 1px solid #14151d;
		color: white;
		outline: none;
		background-color: #1b1b26;
		text-align: center;
		margin: 0;
	}
	
	.sellong {
		padding-left: 2em;
		width: 99px;
		height: 33px;
		border-radius: 3px;
		border: 1px solid #14151d;
		color: white;
		outline: none;
		background-color: #1b1b26;
	}
	
	.selshort {
		padding-left: 1em;
		width: 55px;
		height: 33px;
		border-radius: 3px;
		border: 1px solid #14151d;
		color: white;
		outline: none;
		background-color: #1b1b26;
	}
	
	.bg>ul>li {
		width: 100%;
		height: 44px;
		border-top: 1px solid #1c1c27;
	}
	
	.bg>ul>li li {
		line-height: 44px;
	}
	
	.bg>div:last-child {
		position: absolute;
		bottom: 0;
	}
	
	.bg>div>div.current {
		color: #fcc900;
	}
</style>