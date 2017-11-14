<template>
	<div id="account_survey">
		<p class="p_left">账户资金</p>
		<div class="account_info">
			<div class="info_left">
				<ul>
					<li>
						<img src="../../../assets/images/icon_smileFace.png" alt="笑脸" />
						<span>{{username}}</span>
						<img src="../../../assets/images/icon_acc1.png" alt="账户" />
						<img src="../../../assets/images/icon_password1.png" alt="提现密码" />
					</li>
					<li>
						<button class="btn yellow" v-on:click="toRecharge">充值</button>
						<button class="btn blue" v-on:click="toWithDraw">提现</button>
					</li>
				</ul>
			</div> 
			<div class="info_right">
				<ul>
					<li>
						<img src="../../../assets/images/icon_moneyuse.png" alt="可用资金" />
						<div>
							<p>可用资金<i class="ifont question" v-on:mouseenter="showHide" v-on:mouseleave="hide">&#xe66d;</i></p>
							<span class="surveyMoney">￥{{balance}}</span>
						</div>
					</li>
					<span id="showMoney" v-if="showmoney" >您可提现和开户的金额</span>
					<li>
						<img src="../../../assets/images/icon_money.png" alt="冻结资金" />
						<div>
							<p>冻结资金<i class="ifont question"  v-on:mouseenter="showHide1" v-on:mouseleave="hide1">&#xe66d;</i></p>
							<span class="surveyMoney">￥{{frzBal}}</span>
						</div>
					</li>
					<span id="showmoney1" v-if="showMoneyNo">冻结资金（您提现时被冻结的金额）</span>
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
				<p id="color_dea">收入<span class="white">{{incomeNum}}</span>笔，共<i class="color_yellow">{{incomeMoney}}</i>元 支出<span class="white">{{outlayNum}}</span>笔，共<i  class="color_yellow">{{outlayMoney}}</i>元  </p>
			</div>
			<div class="survey_functionChoose_center">
				<ul>
					<li class="active" v-on:click="timeChoose">今天</li>
					<li v-on:click="timeChoose">7天</li>	
					<li v-on:click="timeChoose">15天</li>
					<li v-on:click="timeChoose">30天</li>
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
					<li class="active" v-on:click="chooseQuery">全部</li>
					<li v-on:click="chooseQuery">收入</li>
					<li v-on:click="chooseQuery">支出</li>
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
						<!--<td>账户余额</td>-->
						<td>详情</td>
					</tr>
				</thead>
				<tbody>
					<tr v-for="item in item">
						<td>{{item.subTime}}</td>
						<td v-if="item.money >0" class="color_yellow">收入</td>
						<td v-else="item.money <0">支出</td>	
						<td>{{item.money}}元</td>
						<td>{{item.remark}}</td>
					</tr>
					<!--<tr>
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
					</tr>-->
				</tbody>
			</table>
			 <div class="pager">
			    <button class="btn_span"  @click="prePage">上一页</button>
			    <span  @click="toIndexPage" v-for="(n,index) in pageCount" :class="{active:current1 == index}">{{n}}</span>
			    <button class="btn_span"  @click="nextPage">下一页</button>
			  </div>
			<p class="p_center">投资有风险，入市需谨慎</p>
		</div>
	</div>
</template>

<script>
	import pro from "../../../assets/js/common.js"
	export default {
		name:"account_survey",
		data(){
			return{
				show_accountWithDraw:false,
				username:'',
				balance:'',
				frzBal:'',
				incomeNum:'',
				incomeMoney:'',
				outlayNum:'',
				outlayMoney:'',
				item:[],
				pageCount:'',
				current1:0,
				current:1,
				showmoney:false,
				showMoneyNo:false,
			}
		},
		methods:{
			hide1:function(){
				this.showMoneyNo = false
			},
			showHide1:function(){
				this.showMoneyNo = true
			},
			hide:function(){
				this.showmoney = false
			},
			showHide:function(){
				this.showmoney =true
			},
			//条件查询
			chooseQuery:function(e){
				$(e.currentTarget).addClass("active").siblings().removeClass("active");
				var index = $(e.currentTarget).index();
				switch (index){
					case 0:
					this.GetList('','','',2)
						break;
					case 1:
					this.GetList('','','',1)
						break;
					case 2:
					this.GetList('','','',0)
						break;
					default:
						break;
				}
			},
			//时间选择
			timeChoose:function(e){
				var index = $(e.currentTarget).index();
				$(e.currentTarget).addClass("active").siblings().removeClass("active");
				switch (index){
					case 0:
						this.GetList('',this.getNowDate(),this.getNowFormatDate(),'')
						break;
					case 1:
						this.GetList('',this.getWeekDate(),this.getNowFormatDate(),'')
						break;
					case 2:
					this.GetList('',this.getHalfMonthDate(),this.getNowFormatDate(),'')
						break;
					case 3:
					this.GetList('',this.getMonthDate(),this.getNowFormatDate(),'')
						break;
					default:
						break;
				}
			},
			//去目标页
			toIndexPage:function(e) {
				var index =parseInt($(e.currentTarget).index());
				$(e.currentTarget).addClass("active").siblings().removeClass("active");
				this.GetList(index,'','','');
				this.current1 = index
				return;
		   	},
			// 下一页
			 nextPage:function() {
			 	if((this.current)+1 >this.pageCount){
			 		return;
			 	}else{
			 		console.log(this.current)
			 		this.GetList((this.current+1),'','','');
			 		this.current++;
			 		return 
			 	}
		    },
			// 上一页
			 prePage:function(){
			 	if((this.current-1) <=0){
			 		console.log(this.current);
			 		return;
			 	}else{
			 		console.log(this.current);
			 		this.GetList((this.current - 1),'','','');
			 		this.current--;
			 		return;
			 	}
			},
			toWithDraw : function (){
				this.$router.push({path:'/withDraw_bankcard'})
			},
			toRecharge:function(){
				this.$router.push({path:"/recharge"})
			},
			//获取数据
			GetList:function(page,startT,endT,chooseType){
				this.username = JSON.parse(localStorage.user).username;
				var token = JSON.parse(localStorage.user).token;
				var secret = JSON.parse(localStorage.user).secret;
				var headers = {
					token : token,
					secret :secret
				}
				var info = {
					pageIndex:page,
					size:10,
					startTime:startT,
					endTime:endT,
					operaType:chooseType
				};
				pro.fetch("post",'/user/fund/list',info,headers).then((res)=>{
					var data = res.data
					if(res.success == true){	
						if(res.code == 1){
							this.incomeNum=data.incomeNum;
							this.incomeMoney=data.incomeMoney;
							this.outlayNum=data.outlayNum;
							this.outlayMoney=data.outlayMoney;
							this.frzBal=data.frzBal;
							this.balance=data.balance;
							this.pageCount = data.totalPage;
							//console.log(this.pageCount)
							//资金收入支出详情列表fundList
							this.item=data.fundList;
							var time = pro.getDate("y-m-d h:i:s",this.item[0].subTime*1000);
						}
					}
				}).catch((err)=>{
					layer.msg('网络不给力，请稍后再试', {time: 1000});
				})
			},
			//获取结束时间
			 getNowFormatDate:function() {
			    var date = new Date();
			    var seperator1 = "-";
			    var seperator2 = ":";
			    var month = date.getMonth() + 1;
			    var strDate = date.getDate();
			    if (month >= 1 && month <= 9) {
			        month = "0" + month;
			    }
			    if (strDate >= 0 && strDate <= 9) {
			        strDate = "0" + strDate;
			    }
			    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate+
			    " "+"23：59：59";
			    return currentdate;
			},
			//获取一天
			 getNowDate:function() {
			    var date = new Date();
			    var seperator1 = "-";
			    var seperator2 = ":";
			    var month = date.getMonth() + 1;
			    var strDate = date.getDate();
			    if (month >= 1 && month <= 9) {
			        month = "0" + month;
			    }
			    if (strDate >= 0 && strDate <= 9) {
			        strDate = "0" + strDate;
			    }
			    if(strDate=1){
			    	month--;
			    	strDate = 30-1
			    }
			    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate+
			    " "+"00：00：00";
			    return currentdate;
			},
			//获取7天时间
			getWeekDate:function(){
				var date = new Date();
			    var seperator1 = "-";
			    var seperator2 = ":";
			    var month = date.getMonth() + 1;
			    var strDate = date.getDate();
			    if (month >= 1 && month <= 9) {
			        month = "0" + month;
			    }
			    if (strDate >= 0 && strDate <= 9) {
			        strDate = "0" + strDate;
			    }
			    if(strDate<7){
			    	month--;
			    	strDate = (30 + strDate - 7)
			    }else{
			    	strDate = strDate - 7
			    }
			    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate+
			    " "+"23：59：59";
			    return currentdate
			},
			//获取15天数据
			getHalfMonthDate:function(){
				var date = new Date();
			    var seperator1 = "-";
			    var seperator2 = ":";
			    var month = date.getMonth() + 1;
			    var strDate = date.getDate();
			    if (month >= 1 && month <= 9) {
			        month = "0" + month;
			    }
			    if (strDate >= 0 && strDate <= 9) {
			        strDate = "0" + strDate;
			    }
			    if(strDate<15){
			    	month--;
			    	strDate = (30 + strDate - 15)
			    }else{
			    	strDate = strDate - 15
			    }
			    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate+
			    " "+"23：59：59";
			    return currentdate
			},
			//获取一个月数据
			getMonthDate:function(){
				var date = new Date();
			    var seperator1 = "-";
			    var seperator2 = ":";
			    var month = date.getMonth() + 1;
			    var strDate = date.getDate();
			    var year = date.getFullYear();
			    if (month >= 1 && month <= 9) {
			        month = "0" + month;
			    }
			    if (strDate >= 0 && strDate <= 9) {
			        strDate = "0" + strDate;
			    }
			    if(month = 1){
			    	year--;
			    	month = 12;
			    }else{
			    	month--;
			    }
			    var currentdate = year + seperator1 + month + seperator1 + strDate+
			    " "+"23：59：59";
			    return currentdate
			}
		},
		mounted:function(){
			//获取默认
			this.GetList('','','','');
		}
	}
</script>

<style lang="scss" scoped type="text/css">
@import "../../../assets/css/common.scss";
	
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
				font-size: 18px;
				font-weight: 500;
				color: $white;
			}
		}
		.question {
			font-size: 16px;
			margin-left: 5px;
			color: $yellow;
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
		.color_yellow{
			color: $yellow;
			font-weight: 500;
		}
		.surveyMoney {
			padding-left: 20px;
		}
		.moneyDetail_list {
			height: 300px;
			overflow-y: scroll;
		}
		/*分页*/
		.pager {
			float: right;
			span{
				margin: 0 10px;
			}
		}
		.btn_span{
			color: $white;
			background-color: $blue;
			border: none;
			margin: 0 10px;
		}
		.active {
		  color: $yellow;
		}
		#showMoney{
			font-size: $fs14;
			color: $yellow;
			position: relative;
			top: -36px;
			left: 450px;
		}
		#showmoney1{
			font-size: $fs14;
			color: $yellow;
			position: relative;
			top: -36px;
			left: 450px;
		}
</style>