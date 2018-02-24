<template>
	<div id="account_survey">
		<p class="p_left">账户资金</p>
		<div class="account_info">
			<div class="info_left">
				<ul>
					<li>
						<img src="../../../assets/images/icon_smileFace.png" alt="笑脸" />
						<span v-if="realName == null">{{username}}</span>
						<span v-else = "realName!=null">{{realName}}&nbsp;{{username}}</span>
						<img src="../../../assets/images/acc_01.png" alt="账户" v-if="realName == null" v-on:click="toCertification" />
						<img src="../../../assets/images/acc_02.png" alt="账户" v-else="realName != null" />
						<img src="../../../assets/images/acc_03.png" alt="提现密码" v-if="isBoundBankCard == false" v-on:click="towWithDrawlPassword"/>
						<img src="../../../assets/images/acc_04.png" alt="提现密码" v-else="isBoundBankCard != false" v-on:click="towWithDrawlPassword"/>
					</li>
					<li>
						<button class="btn yellow" v-on:click="toRecharge">充值</button>
						<button class="btn blue" v-on:click="toWithDraw">提现</button>
					</li>
				</ul>
			</div> 
			<div class="info_right">
				<div class="info_right_top">
					<div class="right_left">
						<ul>
							<li><img src="../../../assets/images/icon_moneyuse.png" alt="可用资金" /></li>
							<li><img src="../../../assets/images/icon_money.png" alt="冻结资金" /></li>
						</ul>
					</div>
					<div class="right_right">
						<ul>
							<li>
								<span>可用资金</span><i class="ifont question" v-on:mouseenter="showHide" v-on:mouseleave="hide">&#xe66d;</i>
								<p class="surveyMoney"><i class="ifont icon_money">&#xe613;</i>{{balance}}</p>
							</li>
							<span class="showMoney" v-if="showmoney" >您可提现和开户的金额</span>
							<li>
								<span>冻结资金</span><i class="ifont question"  v-on:mouseenter="showHide1" v-on:mouseleave="hide1">&#xe66d;</i>
								<p class="surveyMoney"><i class="ifont icon_money">&#xe613;</i>{{frzBal}}</p>
							</li>
							<span class="showmoney1" v-if="showMoneyNo">您提现时被冻结的金额</span>
						</ul>
					</div>
				</div>
				<div class="info_right_btm">
					<p id="color_blue">注：账户总资产=可用资金+冻结资金</p>
				</div>
			</div>
		</div>
		<p class="p_left">资金明细</p>
		<div class="survey_functionChoose">
			<div class="survey_functionChoose_top">
				<p id="color_dea">收入<span class="white">{{incomeNum}}</span>笔，共<i class="color_yellow">{{incomeMoney | fixNumTwo}}</i>元 支出<span class="white">{{outlayNum}}</span>笔，共<i  class="color_yellow">{{outlayMoney | fixNumTwo}}</i>元  </p>
			</div>
			<div class="survey_functionChoose_center">
				<ul>
					<li class="active" v-on:click="timeChoose">今天</li>
					<li v-on:click="timeChoose">7天</li>	
					<li v-on:click="timeChoose">15天</li>
					<li v-on:click="timeChoose">30天</li>
					<li>起始时间</li>
					<div class="time">
						<input type="text" readonly="readonly" class="fl startTime" :value="startTime" />
						<i class="ifont fl">&#xe690;</i>
						<input type="text" readonly="readonly" class="fr endTime" :value="endTime" />
					</div>
					<li>
						<button class="btn blue" @click="serchEvent">搜索</button>
					</li>
					<!--<li><select name="">
						<option value="">2017-10-15</option>
					</select></li>
					<li><i class="ifont toright">&#xe604;</i></li>
					<li><select name="">
						<option value="">2017-10-15</option>
					</select></li>-->
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
					<tr v-for="item in showList">
						<td>{{item.subTime | getTime}}</br><i>{{item.subTime | getTimeTwo}}</i></td>
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
		</div>
		<!--<div class="pager">
		    <button class="btn_span"  @click="prePage">上一页</button>
		    <span  @click="toIndexPage" v-for="(n,index) in pageCount" :class="{active:current1 == index}">{{n}}</span>
		    <button class="btn_span"  @click="nextPage">下一页</button>
		</div>-->
		<div class="pager">
		    <button class="btn_span"   v-show="currentPage != 1" @click="currentPage-- && goIndex(currentPage--,'last')">上一页</button>
		    <span  v-for="index in pages" @click="goIndex(index)" :class="{active:currentPage == index}" :key="index">{{index}}</span>
		    <button class="btn_span"  v-show="pageCount != currentPage && pageCount != 0 " @click="currentPage++ && goIndex(currentPage++,'!last')">下一页</button>
		</div>
		
		
		<p class="p_center">投资有风险，入市需谨慎</p>
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
				realName:'',
				isBoundBankCard : '',
				startTime: '',
				endTime: '',
				accountMoney:'',
				day:'',
				query:'',
				showList:[],//显示数据
				pageCount:'',//总页数
				eachPage:10,//每页条数
				showPage:true,//是否显示分页
				currentPage:1,//当前页
				totalList:[]//总数据,
			}
		},
		filters: {
			getTimeTwo:function(e){
				return pro.getDate('h:i:s',e*1000);
			},
			getTime: function(e){
				return pro.getDate('y-m-d',e*1000);
			},
			fixNumTwo: function(num){
				if(typeof num == 'number'){
					return num.toFixed(2);
				}else{
					return num;
				}
				
			},
		},
		methods:{
			goIndex:function(index,el){
	            if(index == this.currentPage) return;
	            this.currentPage = index;
	            var curtotal=(this.currentPage-1)*this.eachPage;//上一页显示的最后一条
	            var tiaoshu=this.currentPage*this.eachPage;//当前页显示的最后一条
	            this.showList=this.totalList.slice(curtotal,tiaoshu); //当前页应显示的数据
	        },
			//实名
			toCertification:function(){
				this.$router.push({path:'/safe_certification'});
			},
			//设置提现密码
			towWithDrawlPassword:function(){
				this.$router.push({path:'/safe_withdrawalPassword'});
			},
			//日历
			serchEvent: function(){
				this.selectedNum = -1;
				this.startTime = $(".startTime").val();
				this.endTime = $(".endTime").val();
				var beginTime = $(".startTime").val() + ' 00:00:00';
				var t =  Date.parse(new Date(this.endTime)) + 86400000;
				var endTime = pro.getDate("y-m-d", t) + ' 00:00:00';
				this.GetList('',beginTime,endTime,'')
			},
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
					this.showNotice = true;
					this.query = 2;
					switch (this.day){
						case 0:
							this.GetList('',this.getNowDate(),this.getNowFormatDate(),2)
							break;
						case 1:
							this.GetList('',this.getWeekDate(),this.getNowFormatDate(),2)
							break;
						case 2:
							this.GetList('',this.getHalfMonthDate(),this.getNowFormatDate(),2)
							break;
						case 3:
							this.GetList('',this.getMonthDate(),this.getNowFormatDate(),2)
							break;
						default:
							break;
					}
						break;
					case 1:
						this.showNotice = true;
						this.query = 1;
						switch (this.day){
							case 0:
							this.GetList('',this.getNowDate(),this.getNowFormatDate(),1)
							break;
						case 1:
							this.GetList('',this.getWeekDate(),this.getNowFormatDate(),1)
							break;
						case 2:
							this.GetList('',this.getHalfMonthDate(),this.getNowFormatDate(),1)
							break;
						case 3:
							this.GetList('',this.getMonthDate(),this.getNowFormatDate(),1)
							break;
							default:
								break;
						}
						break;
					case 2:
						this.showNotice = false;
						this.query = 0;
						switch (this.day){
							case 0:
							this.GetList('',this.getNowDate(),this.getNowFormatDate(),0)
							break;
						case 1:
							this.GetList('',this.getWeekDate(),this.getNowFormatDate(),0)
							break;
						case 2:
							this.GetList('',this.getHalfMonthDate(),this.getNowFormatDate(),0)
							break;
						case 3:
							this.GetList('',this.getMonthDate(),this.getNowFormatDate(),0)
							break;
							default:
								break;
						}
						break;
				}
			},
			//时间选择
			timeChoose:function(e){
				var index = $(e.currentTarget).index();
				$(e.currentTarget).addClass("active").siblings().removeClass("active");
				switch (index){
					case 0:
						this.GetList('',this.getNowDate(),this.getNowFormatDate(),this.query);
						this.day = 0;
						break;
					case 1:
						this.GetList('',this.getWeekDate(),this.getNowFormatDate(),this.query)
						this.day = 1;
						break;
					case 2:
					this.GetList('',this.getHalfMonthDate(),this.getNowFormatDate(),this.query)
					this.day = 2;
						break;
					case 3:
					this.GetList('',this.getMonthDate(),this.getNowFormatDate(),this.query)
					this.day = 3;
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
			toWithDraw : function (){
				this.$router.push({path:'/withDraw_bankcard'})
			},
			toRecharge:function(){
				this.accountMoney = this.balance;
				this.$router.push({path:"/recharge",query:{"accountMoney":this.accountMoney,"backhere":2}});
			},
			//获取数据
			GetList:function(page,startT,endT,chooseType){
				var token = JSON.parse(localStorage.user).token;
				var secret = JSON.parse(localStorage.user).secret;
				var headers = {
					token : token,
					secret :secret
				}
				var info = {
					pageIndex:page,
					size:100,
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
							//资金收入支出详情列表fundList
							this.totalList = res.data.fundList;
							this.pageCount = Math.ceil(this.totalList.length/this.eachPage);
							var curtotal=(this.currentPage-1)*this.eachPage;//上一页显示的最后一条
				            var tiaoshu=this.currentPage*this.eachPage;//当前页显示的最后一条
				            this.showList=this.totalList.slice(curtotal,tiaoshu); //当前页应显示的数据
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
			    " "+"23:59:59";
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
			    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + (strDate-1)+
			    " "+"00:00:00";
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
			    	strDate = (30 + parseInt(strDate) - 7)
			    }else{
			    	strDate = strDate - 7
			    }
			    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate+
			    " "+"23:59:59";
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
			    	strDate = (30 + parseInt(strDate) - 15)
			    }else{
			    	strDate = strDate - 15
			    }
			    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate+
			    " "+"23:59:59";
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
			    " "+"23:59:59";
			    return currentdate
			},
			//获取实名信息
			getSafeInfo:function(){
				var phoneNumber= JSON.parse(localStorage.user).username;
				this.username = phoneNumber.substr(0, 3) + '****' + phoneNumber.substr(7); 
				var token = JSON.parse(localStorage.user).token;
				var secret = JSON.parse(localStorage.user).secret;
				var headers = {
					token : token,
					secret :secret
				}
				pro.fetch("post","/user/security",'',headers).then((res)=>{
					if(res.success == true){
						if(res.code == 1){
							var realname = res.data.realName
							if(realname!=null){
								this.realName = '*'+realname.substr(1,5);
							}else {
								this.realName = null
							}
							this.isBoundBankCard = res.data.isBoundBankCard;
						}
					}
				}).catch((err)=>{
					if(err.data.success == false){
						layer.msg(err.data.message,{time:2000})
					}else {
						layer.msg("网络不给力，请稍后再试",{time:2000})
					}
				})
			}
		},
		mounted:function(){
			//初始化高度
			var h = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
			var _h = h - 80 - 47;
			var contH = $("#account_survey").height();
			if(contH > _h){
				$("#account_survey").height(_h);
			}
			$(window).resize(function(){
				var h = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
				var _h = h - 80 - 47;
				if(contH > _h){
					$("#account_survey").height(_h);
				}
			});
			//获取默认
			this.GetList('',this.getNowDate(),this.getNowFormatDate(),2);
			this.day = 0;
			this.query = 2;
			//获取实名和提现密码
			this.getSafeInfo();
			//调用日历插件
			dateEvent('.startTime');
			dateEvent('.endTime');
			var date = new Date();
			var time = pro.getDate("y-m-d", date.getTime()).split(' ')[0];
			this.startTime = time;
			this.endTime = time;
		},
		computed:{
			pages:function(){
                var pag = [];
                    if( this.currentPage < this.eachPage ){
                       var i = Math.min(this.eachPage,this.pageCount);
                       while(i){
                           pag.unshift(i--);
                       }
                    }else{ 
                       var middle = this.currentPage - Math.floor(this.eachPage / 2 ),
                           i = this.eachPage;
                       if( middle >  (this.pageCount - this.eachPage)  ){
                           middle = (this.pageCount - this.eachPage) + 1
                       }
                       while(i--){
                           pag.push( middle++ );
                       }
                   }
                 return pag
                }
		}
	}
</script>

<style lang="scss" scoped type="text/css">
@import "../../../assets/css/common.scss";
	#account_survey {
		/*display: none;*/
		width: 100%;
		overflow-y: auto;
		float: left;
		height: 1050px;
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
			text-indent: 10px;
		}
		.p_center {
			margin-top: 5px;
			width: 100%;
			float: left;
			text-align: center;
			height: 40px;
			line-height: 40px;
			background-color: $bottom_color;
		}
		.survey_functionChoose_top {
			padding-left: 10px;
			background-color: $blue;
			height: 60px;
			border-bottom: 1px solid $bottom_color;
			line-height: 60px;
		}
		.survey_functionChoose_center {
			padding-left: 10px;
			background-color: $blue;
			height: 70px;
			padding-top: 30px;
			border-bottom: 1px solid $bottom_color;
			li {
				float: left;
				cursor: pointer;
				width: 65px;
				&:nth-child(1){
					&:hover{
						color: $yellow;
					}
				}
				&:nth-child(2){
					&:hover{
						color: $yellow;
					}
				}
				&:nth-child(3){
					&:hover{
						color: $yellow;
					}
				}
				&:nth-child(4){
					&:hover{
						color: $yellow;
					}
				}
			}
			select {
				background-color: $blue;
				border: 1px solid $bottom_color;
				color: $white;
				padding: 3px 20px;
			}
			.btn{
				padding: 0 10px;
				height: 30px;
				border-radius: 5px;
				margin-left: 5px;
				margin-top: -5px;
			}
		}
		.toright {
			font-size: 20px;
			background-color: $bottom_color;
			margin-left: 45px;
		}
		.survey_functionChoose_btm {
			padding-left: 10px;
			background-color: $blue;
			font-size: $fs12;
			height: 45px;
			line-height: 45px;
			border-bottom: 1px solid $bottom_color;
			li {
				cursor: pointer;
				float: left;
				&:hover{
					color: $yellow;
				}
			}
		}
		.moneyDetail_list{
			/*height: 400px;*/
			table{
				tr{
					text-indent: 10px;
					i{
						margin-left: 10px;
					}
				}
			}
		}
		.account_info {
			background-color: $blue;
			width: 100%;
			height: 240px;		
		}
		.info_left {
			height: 240px;
			border-right: 1px solid $bottom_color;
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
				text-align: center;
				display: inline-block;
				position: relative;
				bottom: 40px;
			}
		}
		.info_right {
			height: 240px;
			width: 50%;
			float: left;
			.info_right_top{
				width: 100%;
				.right_left{
					padding-right: 10px;
					width: 40%;
					float: left;
					li{
						padding-top: 20px;
						height: 80px;
						width: 100%;
						text-align: right;
						img{
							display: inline;
						}
					}
				}
				.right_right{
					position: relative;
					width: 60%;
					float: left;
					li{
						padding-top: 20px;
						display: block;
						width: 100%;
						height: 80px;
						.question{
							font-size: 16px;
							color: $yellow;
						}
						.color_yellow  {
							color: $yellow;
							margin: 0 5px;
							font-weight: 700;
						}
						.surveyMoney{
							color: white;
							font-weight: 700;
							font-size: 20px;
							margin-top: 5px;
							.icon_money{
								color: $white;
								font-size: $fs16;
								font-weight: 500;
								display: inline-block;
							}
						}
					}
					.showMoney{
						font-size: $fs14;
						color: $yellow;
						position: absolute;
						top: 10px;
						left: 70px;
						height: 30px;
						line-height: 30px;
						background-color: #596080;
						text-align: center;
						border-radius: 5px;
						color: $white;
						padding: 5px;
						opacity: 0.8;
					}
					.showmoney1{
						font-size: $fs14;
						color: $yellow;
						position: absolute;
						top: 95px;
						left: 70px;
						line-height: 30px;
						height: 30px;
						background-color: #596080;
						text-align: center;
						color: $white;
						border-radius: 5px;
						padding: 5px;
						opacity: 0.8;
					}
				}
			}
			.info_right_btm{
				padding-top: 10px;
				width: 100%;
				height: 80px;
				float: left;
					p{
						padding-left:34%;
					}
			}
		}
		#color_blue {
					color: $lightblue;
					background-color: $blue;
				}
		.white {
			color: $white;
			margin: 0 5px;
		}
		table {
			background-color: none;
			tr {
				height: 50px;
				border-bottom: 1px solid $bottom_color;
				background-color: $blue;
				&:nth-child(1)
					{
						height: 50px;
					}	
			}
		}
		.color_yellow{
			color: $yellow;
			font-weight: 500;
		}
		/*分页*/
		.pager {
			background-color: $blue;
			width: 100%;
			float: left;
			height: 40px;
			line-height: 40px;
			text-align: right;
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
		.time{
			float: left;
			width: 270px;
			height: 30px;
			overflow: hidden;
			border: 1px solid $lightblue;
			margin-top: -5px;
			border-radius: 4px;
			input{
				width: 109px;
				height: 28px;
				line-height: 28px;
				padding: 0 5px;
				color: $white;
				cursor: pointer;
			}
			.ifont{
				width: 30px;
				height: 28px;
				line-height: 28px;
				text-align: center;
				background: $lightblue;
				font-size: $fs18;
				color: $blue;
			}
			
		}
</style>