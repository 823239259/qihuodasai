<!--<template>
	<div id="account_survey" v-if="isshow_accountSurey">
				<p class="p_left">账户资金</p>
				<div class="account_info">
					<div class="info_left">
						<ul>
							<li>
								<img src="../assets/images/icon_smileFace.png" alt="笑脸" />
								<span>1555****888</span>
								<img src="../assets/images/icon_acc1.png" alt="账户" />
								<img src="../assets/images/icon_password1.png" alt="提现密码" />
							</li>
							<li>
								<button class="btn yellow" >充值</button>
								<button class="btn blue" v-on:click="toWithDraw">提现</button>
							</li>
						</ul>
					</div> 
					<div class="info_right">
						<ul>
							<li>
								<img src="../assets/images/icon_moneyuse.png" alt="可用资金" />
								<div>
									<p>可用资金<i class="ifont question">&#xe66d;</i></p>
									<span>￥1000.00</span>
								</div>
							</li>
							<li>
								<img src="../assets/images/icon_money.png" alt="冻结资金" />
								<div>
									<p>冻结资金<i class="ifont question">&#xe66d;</i></p>
									<span>￥10.00</span>
								</div>
							</li>
							<li>
								<p id="color_blue">注：账户总资产=可用资金+冻结资金</p>
							</li>
						</ul>
					</div>
				</div>
				<p class="p_left">资金明细</p>
				<div class="account_money"></div>
				<div class="survey_functionChoose">
					<div class="survey_functionChoose_top">
						<p id="color_dea">收入<span class="white">10</span>笔，共<i class="color_yellow">1000000.00</i>元 支出<span class="white">5</span>笔，共<i  class="color_yellow">2750</i>元  </p>
					</div>
					<div class="survey_functionChoose_center">
						<ul>
							<li>今天</li>
							<li>7天</li>	
							<li>15天</li>
							<li>30天</li>
							<li>起始时间</li>
							<li><select name="">
								<option value="">2017-10-15</option>
							</select></li>
							<li><i class="ifont toright">&#xe604;</i></li>
							<li><select name="">
								<option value="">2017-10-15</option>
							</select></li>
						</ul>
					</div>
					<div class="survey_functionChoose_btm">
						<ul>
							<li>全部</li>
							<li>收入</li>
							<li>支出</li>
						</ul>
					</div>
				</div>
				
				
				<div class="moneyDetail_list">
					<table>
						<thead>
							<tr>
								<td>时间</td>
								<td>类型</td>
								<td>金额</td>
								<td>账户余额</td>
								<td>详情</td>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>2017-07-06 16:29:25</td>
								<td>收入</td>
								<td>3500元</td>
								<td>99999元</td>
								<td>申请国际综合开户，扣款3500元</td>
							</tr>
							<tr>
								<td>2017-07-06 16:29:25</td>
								<td>收入</td>
								<td>3500元</td>
								<td>99999元</td>
								<td>申请国际综合开户，扣款3500元</td>
							</tr>
							<tr>
								<td>2017-07-06 16:29:25</td>
								<td>支出</td>
								<td>3500元</td>
								<td>99999元</td>
								<td>申请国际综合开户，扣款3500元</td>
							</tr>
							<tr>
								<td>2017-07-06 16:29:25</td>
								<td>支出</td>
								<td>3500元</td>
								<td>99999元</td>
								<td>申请国际综合开户，扣款3500元</td>
							</tr>
							<tr>
								<td>2017-07-06 16:29:25</td>
								<td>支出</td>
								<td>3500元</td>
								<td>99999元</td>
								<td>申请国际综合开户，扣款3500元</td>
							</tr>
						</tbody>
					</table>
					<div class="page_next">
						<ul>
							<li>下一页</li>
							<li>5</li>
							<li>4</li>
							<li>3</li>
							<li>2</li>
							<li>1</li>
							<li>上一页</li>
						</ul>
					</div>
					<p class="p_center">投资有风险，入市需谨慎</p>
				</div>
			</div>
</template>

<script>
</script>

<style lang="scss" scoped type="text/css">
@import "../../assets/css/common.scss";
	
	#account_survey {
		/*display: none;*/
		background-color: $blue;
		width: 100%;
		float: left;
		
	}
		li {
				width: 60px;
			}
		.p_left {
			height: 40px;
			line-height: 40px;
			color: $white;
			width: 100%;
			background-color: $bottom_color;
			text-indent: 5px;
		}
		.p_center {
			text-align: center;
			height: 40px;
			line-height: 40px;
		}
		.survey_functionChoose_top {
			height: 60px;
			border-bottom: 1px solid $bottom_color;
			line-height: 60px;
		}
		.survey_functionChoose_center {
			height: 70px;
			padding-top: 30px;
			border-bottom: 1px solid $bottom_color;
			li {
				float: left;
				width: 65px;
			}
			select {
				background-color: $blue;
				border: 1px solid $bottom_color;
				color: $white;
				padding: 3px 20px;
			}
		}
		.toright {
			font-size: 20px;
			background-color: $bottom_color;
			margin-left: 45px;
		}
		.survey_functionChoose_btm {
			font-size: $fs12;
			height: 45px;
			line-height: 45px;
			border-bottom: 1px solid $bottom_color;
			li {
				float: left;
			}
		}
		.account_info {
			width: 100%;
			height: 240px;			
		}
		.info_left {
			width: 50%;
			float: left;
			li {
				margin-left: 20%;
				width: 100%;
				height: 90px;
				padding-top: 30px;
			}
			img {
				&:nth-child(1) {
					width: 90px;
					height: 90px;
					position: relative;
				}
				&:nth-child(3) {
					width: 20px;
					height: 20px;
					position: relative;
					bottom: 35px;	
				}
				&:nth-child(4) {
					width: 20px;
					height: 20px;
					position: relative;
					bottom : 35px;
				}
			}
			.btn {
				width: 120px;
				height: 30px;
				margin-top: 30px;
				color: $black;	
			}
			span {
				width: 80px;
				display: inline-block;
				position: relative;
				bottom: 40px;
			}
		}
		.info_right {
			width: 50%;
			float: left;
			li {
				display: flex;
				justify-content:center;
				width: 100%;
				margin-top: 30px;
				height: 40px;
			}
			p {
				margin-left: 20px;
				background-color: $blue;
			}
			img {
				position: relative;
				top: 6px;
			}
			span {
				font-size: 20px;
				font-weight: 700;
				color: $white;
			}
		}
		.question {
			font-size: 16px;
			margin-left: 5px;
			color: $yellow;
		}
		.page_next {
			height: 60px;
			line-height: 60px;
			li {
				float: right;
			}
		}
		.account_money {
			width: 100%;
		}
		
		
		#color_blue {
					color: $lightblue;
					background-color: $blue;
				}
		#color_dea {
			/*background-color: $blue;*/
			/*color: $lightblue;*/
		}
		.color_yellow  {
			color: $yellow;
			margin: 0 5px;
			font-weight: 700;
		}
		.white {
			color: $white;
			margin: 0 5px;
		}
		table {
			text-indent: 5px;
			background-color: none;
			tr {
				height: 40px;
				border-bottom: 1px solid $bottom_color;
				background-color: $blue;
				&:nth-child(1)
					{
						height: 30px;
					}	
			}
		}
</style>-->