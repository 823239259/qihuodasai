<template>
	<div id="trade_details">
		<table>
			<thead>
				<tr>
					<td>合约名称</td>
					<td>状态</td>
					<td>类型</td>
					<td>条件</td>
					<td>下单</td>
					<td>有效日期</td>
					<td>下单时间</td>
				</tr>
			</thead>
			<tbody>
				<template></template>
				<tr>
					<td>合约名称</td>
					<td>状态</td>
					<td>类型</td>
					<td>条件</td>
					<td>下单</td>
					<td>有效日期</td>
					<td>下单时间</td>
				</tr>
			</tbody>
		</table>
		<div class="tools">
			<button class="btn blue">暂停</button>
			<button class="btn blue" @click="editConditionOrder">修改</button>
			<button class="btn blue">删除</button>
		</div>
		<div id="edit_price_order" v-show="showDialog">
			<div class="edit_order cont">
				<div class="row">
					<div class="fl">
						<label>合约:</label>
						<span>HSI,恒指期货</span>
					</div>
					<div class="fl">
						<label>最新:</label>
						<span>56.12</span>
					</div>
				</div>
				<div class="row">
						<label>条件:</label>
						<span>价格</span>
						<div class="slt-box row_price_box">
							<input type="text" class="slt" disabled="disabled" selectVal="0" value=">="/>
							<span class="tal-box"><span class="tal"></span></span>
							<div class="slt-list">
								<ul>
									<li selectVal="0">></li>
									<li selectVal="1">>=</li>
									<li selectVal="2"><</li>
									<li selectVal="3"><=</li>
								</ul>
							</div>
						</div>
						<input type="text" class="ipt" />
						<label>附加:</label>
						<div class="slt-box row_price_box">
							<input type="text" class="slt" disabled="disabled" selectVal="0" value=">="/>
							<span class="tal-box"><span class="tal"></span></span>
							<div class="slt-list">
								<ul>
									<li selectVal="0">></li>
									<li selectVal="1">>=</li>
									<li selectVal="2"><</li>
									<li selectVal="3"><=</li>
								</ul>
							</div>
						</div>
						<input type="text" class="ipt" />
				</div>
				<div class="row">
					<div class="fl">
						<label>操作:</label>
						<div class="slt-box row_money_box">
							<input type="text" class="slt" disabled="disabled" selectVal="0" value="买入"/>
							<span class="tal-box"><span class="tal"></span></span>
							<div class="slt-list">
								<ul>
									<li selectVal="0">买入</li>
									<li selectVal="1">卖出</li>
								</ul>
							</div>
						</div>
						<div class="slt-box row_money_box">
							<input type="text" class="slt" disabled="disabled" selectVal="0" value="市价"/>
							<span class="tal-box"><span class="tal"></span></span>
							<div class="slt-list">
								<ul>
									<li selectVal="0">市价</li>
									<li selectVal="1">限价</li>
								</ul>
							</div>
						</div>
					</div>
					<div class="fl">
						<label>手数:</label>
						<input type="text" class="ipt" />
					</div>
				</div>
			</div>
			<h6>当日有效</h6>
			<p>1.止损单在云端执行，软件关闭后扔然有效，云端自动确认结算单。</p>
			<p>2.止损单在行情不活跃或快速发送变化下，不保证成交价为指定价。</p>
			<p>3.止损单存在风险，云端系统、网络故障情况下失效等。</p>
		</div>
		<div id="edit_time_order" v-show="showDialog">
			<div class="edit_order cont">
				<div class="row">
					<div class="fl">
						<label>合约:</label>
						<span>HSI,恒指期货</span>
					</div>
					<div class="fl">
						<label>最新:</label>
						<span>56.12</span>
					</div>
				</div>
				<div class="row">
						<label>条件:</label>
						<span>时间</span>
						<input type="text" class="ipt time" />
						<label>附加:</label>
						<div class="slt-box row_price_box">
							<input type="text" class="slt" disabled="disabled" selectVal="0" value=">="/>
							<span class="tal-box"><span class="tal"></span></span>
							<div class="slt-list">
								<ul>
									<li selectVal="0">></li>
									<li selectVal="1">>=</li>
									<li selectVal="2"><</li>
									<li selectVal="3"><=</li>
								</ul>
							</div>
						</div>
						<input type="text" class="ipt" />
				</div>
				<div class="row">
					<div class="fl">
						<label>操作:</label>
						<div class="slt-box row_money_box">
							<input type="text" class="slt" disabled="disabled" selectVal="0" value="买入"/>
							<span class="tal-box"><span class="tal"></span></span>
							<div class="slt-list">
								<ul>
									<li selectVal="0">买入</li>
									<li selectVal="1">卖出</li>
								</ul>
							</div>
						</div>
						<div class="slt-box row_money_box">
							<input type="text" class="slt" disabled="disabled" selectVal="0" value="市价"/>
							<span class="tal-box"><span class="tal"></span></span>
							<div class="slt-list">
								<ul>
									<li selectVal="0">市价</li>
									<li selectVal="1">限价</li>
								</ul>
							</div>
						</div>
					</div>
					<div class="fl">
						<label>手数:</label>
						<input type="text" class="ipt" />
					</div>
				</div>
			</div>
			<h6>当日有效</h6>
			<p>1.止损单在云端执行，软件关闭后扔然有效，云端自动确认结算单。</p>
			<p>2.止损单在行情不活跃或快速发送变化下，不保证成交价为指定价。</p>
			<p>3.止损单存在风险，云端系统、网络故障情况下失效等。</p>
		</div>
	</div>
</template>

<script>
	export default{
		name: 'trade_details',
		data(){
			return{
				showDialog: false,
			}
		},
		computed: {
			
		},
		methods: {
			editConditionOrder: function(){
				this.showDialog = true;
				layer.open({
					type: 1,
					title: '修改价格条件单',
					area: ['400px', 'auto'],
					content: $("#edit_time_order"),
					btn: ['确定','取消'],
					btn1: function(index){
						
						layer.close(index);
						this.showDialog = false;
					}.bind(this),
					btn2: function(){
						this.showDialog = false;
					}.bind(this),
					cancel: function(){
						this.showDialog = false;
					}.bind(this)
				});
			}
		}
	}
</script>

<style lang="scss" scoped>
	@import "../../assets/css/common.scss";
	#trade_details{
		height: 151px;
		overflow: auto;
	}
	table{
		thead tr{
			height: 30px;
			background: $bottom_color;
		}
		td{
			padding: 0 10px;
		}
		tbody tr{
			height: 40px;
			border-bottom: 1px solid $bottom_color;
		}
	}
	.tools{
		position: absolute;
		bottom: 10px;
		left: 10px;
		z-index: 2;
		.btn{
			width: 90px;
			height: 30px;
			line-height: 30px;
		}
	}
	.cont{
		width: 400px;
		overflow: hidden;
		padding: 20px 10px 0 10px;
		.row{
			height: 30px;
			margin-bottom: 18px;
			div:first-child{
				width: 268px;
			}
			label{
				float: left;
				display: inline-block;
				width: 40px;
				line-height: 30px;
			}
			span{
				float: left;
				color: $white;
				line-height: 30px;
			}
			.ipt{
				float: left;
				width: 58px;
				height: 26px;
				line-height: 26px;
				border: 1px solid #474c66;
				border-radius: 4px;
				color: $white;
				text-align: center;
				&.time{
					width: 80px;
					margin: 0 20px 0 5px;
				}
			}
			.row_money_box, .row_price_box{
				width: 80px;
				margin-left: 5px;
				.slt{
					width: 78px;
					padding-left: 0;
				}
				span{
					line-height: 54px;
				}
			}
			.row_price_box{
				width: 60px;
				margin-right: 5px;
				.slt{
					width: 58px;
					padding-left: 0;
				}
			}
		}
	}
	h6{
		height: 30px;
		line-height: 30px;
		overflow: hidden;
		text-align: center;
		background: $deepblue;
		margin-bottom: 15px;
	}
	p{
		font-size: $fs12;
		line-height: 22px;
		padding: 0 10px;
	}
	@media only screen and (min-width: 1280px) and (max-width: 1366px) {
		#trade_details{
			width: 635px;
			td{
				font-size: $fs12;
			}
		}
	}
</style>