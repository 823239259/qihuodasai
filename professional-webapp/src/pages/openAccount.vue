<template>
	<div id="openAccount">
		<div class="openAccount_top">
			<img src="../assets/images/icon_openaccount_01.png" v-if="showpage==true"/>
			<img src="../assets/images/icon_openaccount_02.png" v-else="showpage == false"/>
		</div>
		<div class="openAccount_center" v-if="isshow_openAccount_1">
			<div class="title">
				<span>开户入金</span>
				<span>操盘保证金越多，可持仓手数越多</span>
				<span>国际操盘规则</span>
			</div>
			<div class="openAccount_center_left">
				<ul>
					<li >
						<button v-for="item in item" class="btn" @click="chose">￥{{item.traderBond}}</button>
					</li>
					<li>
						<button class="btn yellow" v-on:click="to_openAccount_2">下一步</button>
					</li>
					<li>
						<p>提交申请即代表你已阅读并同意<span>《国际期货综合操盘合作协议》</span></p>
					</li>
				</ul>
			</div>
			<div class="openAccount_center_right">
				<ul>
					<li>您的投资本金：<label>{{show_price}}元</label><i>(固定汇率{{rate}}，1美元={{rate}}元人民币)</i></li>
					<li>总操盘资金<i>（盈利全归你）</i></li>
					<li>{{traderTotal}}美元={{(show_price/rate).toFixed(0)}}美元<i>（本金）</i>+{{traderTotal-(show_price/rate).toFixed(0)}}美元<i>（获得资金）</i></li>
					<li>亏损平仓线：<span>{{lineLoss*rate}}元（{{lineLoss}}美元）</span><i>（平仓线=总操盘资金-风险保证金x0.6）</i></li>
					<li>管理费：<span>免费</span></li>
					<li>交易时间：<span>请参照交易规则</span></li>
				</ul>
			</div>
		</div>
		<div class="openAccount_center_step2" v-if="isshow_openAccount_2">
			<div class="title">
				<span>确认方案信息</span>
			</div>
			<div>
				<table>
					<thead>
						<tr>
							<td>总操盘资金</td>
							<td>亏损平仓线</td>
							<td>投资本金</td>
							<td>账户管理费</td>
							<td></td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>{{traderTotal}}美元</td>
							<td>{{lineLoss}}美元</td>
							<td>{{show_price}}元</td>
							<td>免费</td>
							<td></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="to_openAccount">
				<span>支付金额：<i>{{show_price}}</i>元</span>
				<button class="btn yellow" v-on:click="to_comfirmPayment">立即开户</button>
				<label v-on:click="back">返回修改</label>
			</div>
		</div>
		<div class="openAccount_btm">
			<div class="openAccount_btm_top">
				<span>交易规则</span>
				<span>（一个账号可同时交易多种期货产品）</span>
			</div>
			<div class="openAccount_btm_center">
				<div class="product_list">
					<table>
						<thead>
							<tr class="color_deepblue" >
								<td>期货产品</td>
								<td>交易时间段</td>
								<td>初始持仓手数</td>
								<td>单边手续费</td>
							</tr>
							<tr class="color_deepblue1" >
								<td>期货产品</td>
								<td>交易时间段</td>
								<td>初始持仓手数</td>
								<td>单边手续费</td>
							</tr>
						</thead>
						<tbody class="show_list">
							<tr v-for="k in temp.contractList" class="show_list_td">
								<td>{{k.tradeType | cnname}}</br><i>{{k.mainContract}}</i></td>
								<td>{{k.tradTime}}</td>
								<td>{{k.shoushu | filtershoushu(chooseType)}}</td>
								<td>{{k.price}}元/手</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="btm_btm">
					<span v-on:click="show_listAll">展开</span>
				</div>
			</div>
			<div class="openAccount_btm_btm">
				<span>投资有风险，入市需谨慎</span>
			</div>
		</div>
	</div>
</template>
<script>
	import pro from "../assets/js/common.js"
	export default{
		name:'openAccount',
		data(){
			return {
				isshow_openAccount_1 : true,
				isshow_openAccount_2 : false,
				show_price : 3000,
				item: '',
				lineLoss:'',
				listLeft : '',
				listRight :'',
				rate:'',
				traderTotal:'',
				temp:{},
				chooseType: 3000,
				show_list:true,
				showpage:true
			}
		},
		methods : {
			//返回修改
			back:function(){
				this.isshow_openAccount_2 = false,
				this.isshow_openAccount_1 = true,
				this.showpage = true
			},
			//展开
			show_listAll:function(){
				if(this.show_list == true){
					$(".product_list").css("overflow-y","scroll");
					$(".btm_btm>span").html("关闭")
					return this.show_list = false
				}else if(this.show_list == false){
					$(".product_list").css("overflow-y","visible");
					$(".btm_btm>span").html("展开");
					return this.show_list = true;
				}
			},
			to_comfirmPayment : function(){
				this.payMoney = this.chooseType
				console.log(this.payMoney);
				this.$router.push({path:"/confirmPayment",query:{"payMoney":this.payMoney}});
			},
			to_openAccount_2 :function(){
				this.isshow_openAccount_2 = true,
				this.isshow_openAccount_1 = false,
				this.showpage=false
			},
			//选择不同价格
			chose:function(e){
				var index = $(e.currentTarget).index();
				//截取价格并显示
				var show_price=$(e.currentTarget).html().substring(1);
				this.show_price = show_price;
				this.chooseType = parseInt(show_price);
				$(e.currentTarget).addClass("btn1").siblings().removeClass("btn1");
				switch (index){
					case 0:
						this.lineLoss = this.item[0].lineLoss;
						this.traderTotal = this.item[0].traderTotal;
						break;
					case 1:
						this.lineLoss = this.item[1].lineLoss;
						this.traderTotal = this.item[1].traderTotal;
						break;
					case 2:
						this.lineLoss = this.item[2].lineLoss;
						this.traderTotal = this.item[2].traderTotal;
						break;
					case 3:
						this.lineLoss = this.item[3].lineLoss;
						this.traderTotal = this.item[3].traderTotal;
						break;
					case 4:
						this.lineLoss = this.item[4].lineLoss;
						this.traderTotal = this.item[4].traderTotal;
						break;
					case 5:
						this.lineLoss = this.item[5].lineLoss;
						this.traderTotal = this.item[5].traderTotal;
						break;
					case 6:
						this.lineLoss = this.item[6].lineLoss;
						this.traderTotal = this.item[6].traderTotal;
						break;
					case 7:
						this.lineLoss = this.item[7].lineLoss;
						this.traderTotal = this.item[7].traderTotal;
						break;
				}
			}
		},
		beforeCreate:function(){
			//获取汇率
			var headers ={
				token:JSON.parse(localStorage.user).token,
				secret:JSON.parse(localStorage.user).secret
			}
			pro.fetch("post","/user/getbalancerate",{businessType:1},headers).then((res)=>{
				if(res.success == true){
					if(res.code == 1){
						this.rate = res.data.rate;
					}
				}
			}).catch((err)=>{
				if(err.success ==false ){
					
				}else{
					
				}
			})
			//获取数据列表
			pro.fetch("post",'/ftrade/params',{businessType:8},'').then((res)=>{
				var data = res.data;
				if(res.success == true){
					if(res.code == 1){
						this.traderTotal = data.paramList[0].traderTotal;
						this.lineLoss = data.paramList[0].lineLoss;
						this.temp = data;
						this.item = data.paramList;
						this.$store.state.tempTradeapply = this.temp;
						this.temp.contractList.forEach(function(o, i) {
							switch(o.tradeType) {
								case 0:   //return '富时A50'
									var arr = [];
									this.temp.paramList.forEach(function(a) {
										arr.push(a.tranLever);
										o.shoushu = arr;
									}.bind(this));
									break;
								case 6:   //return '国际原油'
									var arr = [];
									this.temp.paramList.forEach(function(a) {
										arr.push(a.crudeTranLever);
										o.shoushu = arr;
									}.bind(this));
									break;
								case 7:   //return '恒指期货'
									var arr = [];
									this.temp.paramList.forEach(function(a) {
										arr.push(a.hsiTranLever);
										o.shoushu = arr;
									}.bind(this));
									break;
								case 9:   //return '迷你道指'
									var arr = [];
									this.temp.paramList.forEach(function(a) {
										arr.push(a.mdtranLever);
										o.shoushu = arr;
									}.bind(this));
									break;
								case 10:   //return '迷你纳指'
									var arr = [];
									this.temp.paramList.forEach(function(a) {
										arr.push(a.mntranLever);
										o.shoushu = arr;
									}.bind(this));
									break;
								case 11:   //return '迷你标普'
									var arr = [];
									this.temp.paramList.forEach(function(a) {
										arr.push(a.mbtranLever);
										o.shoushu = arr;
									}.bind(this));
									break;
								case 12:   //return '德国DAX'
									var arr = [];
									this.temp.paramList.forEach(function(a) {
										arr.push(a.daxtranLever);
										o.shoushu = arr;
									}.bind(this));
									break;
								case 13:   //return '日经225'
									var arr = [];
									this.temp.paramList.forEach(function(a) {
										arr.push(a.nikkeiTranLever);
										o.shoushu = arr;
									}.bind(this));
									break;
								case 14:   //return '小恒指'
									var arr = [];
									this.temp.paramList.forEach(function(a) {
										arr.push(a.lhsiTranActualLever);
										o.shoushu = arr;
									}.bind(this));
									break;
								case 15:   //return '美黄金'
									var arr = [];
									this.temp.paramList.forEach(function(a) {
										arr.push(a.agTranActualLever);
										o.shoushu = arr;
									}.bind(this));
									break;
								case 16:   //return 'H股指数'
									var arr = [];
									this.temp.paramList.forEach(function(a) {
										arr.push(a.hIndexActualLever);
										o.shoushu = arr;
									}.bind(this));
									break;
								case 17:   //return '小H股指数'
									var arr = [];
									this.temp.paramList.forEach(function(a) {
										arr.push(a.xhIndexActualLever);
										o.shoushu = arr;
									}.bind(this));
									break;
								case 18:   //return '美铜'
									var arr = [];
									this.temp.paramList.forEach(function(a) {
										arr.push(a.aCopperActualLever);
										o.shoushu = arr;
									}.bind(this));
									break;
								case 19:   //return '美白银'
									var arr = [];
									this.temp.paramList.forEach(function(a) {
										arr.push(a.aSilverActualLever);
										o.shoushu = arr;
									}.bind(this));
									break;
								case 20:   //return '小原油'
									var arr = [];
									this.temp.paramList.forEach(function(a) {
										arr.push(a.smaActualLever);
										o.shoushu = arr;
									}.bind(this));
									break;
								case 21:   //迷你德国DAX指数
									var arr = [];
									this.temp.paramList.forEach(function(a) {
										arr.push(a.daxtranMinActualLever);
										o.shoushu = arr;
									}.bind(this));
									break;
								case 22:   //return '天然气'
									var arr = [];
									this.temp.paramList.forEach(function(a) {
										if(a.naturalGasActualLever==null){
											arr.push(0);
										}else{
											arr.push(a.naturalGasActualLever);
										}
										o.shoushu = arr;
									}.bind(this));
									break;
							}
						}.bind(this));
					}
				}
			}).catch((err)=>{
				if(err.success == false){
					
				}else{
					
				}
			})
		},
		filters:{
			filtershoushu: function(arr,chooseType){
				switch(chooseType){
					case 3000:
						return arr[0];
						break;
					case 6000:
						return arr[1];
						break;
					case 10000:
						return arr[2];
						break;
					case 12000:
						return arr[3];
						break;
					case 15000:
						return arr[4];
						break;
					case 50000:
						return arr[5];
						break;
					case 100000:
						return arr[6];
						break;
					case 200000:
						return arr[7];
						break;
				}
			},
			moneytype: function(num) {
				if(num) return num.toLocaleString();
			},
			cnname: function(a) {
				switch(a) {
					case 0:
						return '富时A50'
						break;
					case 6:
						return '国际原油'
						break;
					case 7:
						return '恒指期货'
						break;
					case 9:
						return '迷你道指'
						break;
					case 10:
						return '迷你纳指'
						break;
					case 11:
						return '迷你标普'
						break;
					case 12:
						return '德国DAX'
						break;
					case 13:
						return '日经225'
						break;
					case 14:
						return '小恒指'
						break;
					case 15:
						return '美黄金'
						break;
					case 16:
						return 'H股指数'
						break;
					case 17:
						return '小H股指数'
						break;
					case 18:
						return '美铜'
						break;
					case 19:
						return '美白银'
						break;
					case 20:
						return '小原油'
						break;
					case 21:
						return '迷你德国DAX指数'  //迷你德国DAX指数
						break;
					case 22:
						return '天然气'
						break;
				}
			},
			varieties: function(e){    //交易品种
				switch(e) {
					case 8:
						return "国际综合";
						break;
					case 7:
						return "恒指期货";
						break;
					case 6:
						return "国际原油";
						break;
					case 0:
						return "富时A50";
						break;
				}
			}
		},
	}
</script>

<style scoped lang="scss">
@import "../assets/css/common.scss";
	#openAccount {
		width: 80%;
		margin: auto;
	}
	.openAccount_top {
		text-align: center;
		height: 140px;
		background-color: $blue;
		img {
			width: 723px;
			height: 63px;
			margin-top: 40px;
		}
	}
	.openAccount_center {
		width: 100%;
		margin-top: 10px;
		height: 280px;
	}
	.openAccount_center_left {
		width: 50%;
		background-color: $blue;
		height: 240px;
		float: left;
		text-align: center;
		span {
			color: $white;
		}
		li {
			&:nth-child(1){
				padding-top: 15px;
				width: 50%;
				margin: auto;
			}
			&:nth-child(2){
				margin-top: 20px;
			}
			&:nth-child(3){
				margin-top: 25px;
			}
			&:nth-child(4){
				margin-top: 20px;
			}
		}
	}
	.title {
		height: 40px;
		width: 100%;
		line-height: 40px;
		background-color: $bottom_color;
		span {
			&:nth-child(1){
				color : $white;
			}
			&:nth-child(2){
				font-size: $fs12;
			}
			&:nth-child(3){
				font-size: $fs14;
				float: right;
			}
		}
	}
	.btn{
		margin:0px 5px;
		margin-top: 15px;
		width: 80px;
		height: 40px;
		color: $white;
		border: 5px;
		background-image: url(../assets/images/icon_choseMoneyNo.png);
	}
	.btn1 {
		width: 80px;
		height: 40px;
		color: $white;	
		border-radius: 5px;
		background-image: url(../assets/images/icon_choseMoney.png);
	}
	.yellow {
		width: 120px;
		height: 30px;
	}
	.openAccount_center_right {
		border-left: 1px solid $bottom_color;
		width: 50%;
		background-color: $blue;
		height: 240px;
		float: left;
		li {
			text-indent: 5px;
			height:40px; 
			line-height: 40px;
			&:nth-child(4){
				border-bottom: 1px solid $bottom_color;
				border-top: 1px solid $bottom_color;
			}
			&:nth-child(6){
				border-bottom: 1px solid $bottom_color;
				border-top: 1px solid $bottom_color;
			}
			span {
				color : $white;
			}
			i {
				font-size: $fs12;
			}
			label {
				color: $yellow;
			}
		}
	}
	.openAccount_btm {
		width: 100%;
		margin-top: 10px;
		height: 300px;
		background-color: $bottom_color;
		/*overflow: scroll;*/
	}
	.openAccount_btm_top {
		height: 40px;
		width: 100%;
		line-height: 40px;
		background-color: $bottom_color;
		span {
			&:first-child {
				font-size: $fs16;
				color: $white;
			}
			&:last-child {
				font-size: $fs12;
			}
		}
	}
	.openAccount_btm_center {
		background-color: $bottom_color;
		width: 100%;
	}
	.btm_btm {
		width: 100%;
		height: 60px;
		float: left;
		line-height: 60px;
		text-align: center;
		background-color: $blue;
		border-top: 1px solid $bottom_color;
	}
	.product_list {
		width: 100%;
		height: 220px;
	}
	.show_list{
		display: block;
		width: 100%;
		td{
			width: 25%;
			float: left;
		}
	}
	.show_list_td{
		width:49.5%; 
		td{
			padding-top: 10px;
		}
		&:nth-child(odd){
			float: left;
			background-color: $blue;
		}
		&:nth-child(even){
			float: right;
			background-color: $blue;
			&:hover{
				background-color:$blue;
			}
		}
	}
	table{
	 	tr{
	 		/*background-color: $blue;*/
	 		height: 50px;
	 		border-bottom: 1px solid $bottom_color;
	 	}
	 }
	 .openAccount_btm_btm {
	 	/*margin-top: 10x;*/
	 	float: left;
	 	width: 100%;
	 	height: 40px;
	 	text-align: center;
	 	line-height: 40px;
	 	background-color: $bottom_color;
	 }
	 .color_deepblue {
	 	&:hover{
	 		background-color: $deepblue;
	 	}
	 	float: left;
	 	background-color: $deepblue;
	 	height: 30px;
	 	width: 49.5%;
	 	display: block;
	 	line-height: 30px;
	 	td{
	 		width: 25%;
	 		float: left;
	 	}
	 }
	  .color_deepblue1 {
	  	&:hover{
	 		background-color: $deepblue;
	 	}
	  	float: right;
	 	background-color: $deepblue;
	 	height: 30px;
	 	width: 49.5%;
	 	display: block;
	 	line-height: 30px;
	 	td{
	 		width: 25%;
	 		float: left;
	 	}
	 }
	 .openAccount_center_step2 {
	 	margin-top: 10px;
	 	width: 100%;
	 	height: 210px;
	 	background-color: $blue;
	 }
	 .to_openAccount {
	 	height: 90px;
	 	line-height: 90px;
	 	text-align: center;
	 	span{
	 			color: $white;
	 			font-size: $fs14;
	 	}
	 	label{
	 		font-size: $fs12;
	 	}
	 	.btn {
	 		width: 120px;
	 		height: 30px;
	 	}
	 	i{
	 		color: $yellow;
	 		font-size: $fs16;
	 		font-weight: 500;
	 	}
	 	.yellow{
	 		margin-bottom: 20px;
	 		color: #242633;
	 	}
	 }
</style>