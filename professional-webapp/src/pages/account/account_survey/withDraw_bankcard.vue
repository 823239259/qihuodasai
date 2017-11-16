<template>
	<div id="withDraw_bankcard">
		<div class="withDraw_unboundBankCard" v-if="showUnboundCard">
			<button class="btn yellow">绑定银行卡</button>
			<p>（您还为绑定银行卡，暂不能进行提现操作）</p>	
		</div>
		<div class="account_withDraw_top" v-if="showBoundCard">
			<ul class="title">
				<li>
					提取余额到银行卡
				</li>
				<li>
					余额：
				</li>
				<li class="yel">
					{{accountMoney}}元
				</li>
				<li>
					（累计免费提现金额：<i class="yel">{{operateMoney}}</i>）元
				</li>
				<li>
					提现记录
				</li>
				<li>
					添加银行卡
				</li>
			</ul>
			<ul class="banklist">
				<li  v-for="(k,index) in bandCardList" class="chooseNo" v-on:click="chooseBankCard(k.bankId,index)" :class="{chooseIn:current1 == index}">
					<i class="ifont" v-if="k.default ==true">&#xe698;</i>
					<i class="ifont" v-else="k.default!=true">&#xe626;</i>
					<span>{{k.bankName}}</span>
					<span>尾号{{k.card.substr(-4,4)}}</span>
					<label  v-if="k.default !=true"></label>
					<label  v-else="k.default==true">默认</label>
					<select id="manage" @change="chooseChandle(k.bankId)">
						<option value="1">管理</option>
						<option value="2" v-if="k.default !=true">设为默认</option>
						<option value="2" v-else="k.default==true"></option>
						<option value="3">删除</option>
						<option value="4">编辑</option>
					</select>
				</li>
			</ul>
			<p class="writeIn">提现金额：<input type="text" v-model="withDrawMoney" v-on:input="changeRate"/>元<span>（收取 <i>{{rate}}</i>元提现手续费，实际到账<i>{{withDrawMoney-rate}}</i>元）</span></p>
			<button class="btn blue" v-on:click="toWith_draw">下一步</button>
		</div>
		<div class="account_withDraw_btm">
			<ul>
				<li>相关提示</li>
				<li>大额充值资金无法一次性提现出去怎么办？<p>答：建议使用充值退回功能。</p></li>
				<li>最快：<span>10</span>分钟到账
					<p>1、到账时间：工作日09:00--16:30申请，24小时到账，最快5分钟到账。其余时间申请，将在下一个工作日到账。</p>
					<p>2、提现手续费： 1%（适用于充值后，未实际操盘用户） 0元（适用于操盘提现用户提现）。</p>
					<p>3、提现处理时间：每个工作日固定时间进行提现处理。具体时间为：10:00，14:00，17:30</p>
				</li>
				<li>支持银行多达 <span>15</span>家
				<p>推荐您使用工商银行、建设银行、农业银行提现，到账最快</p>
				</li>
				<li><span>温馨提示：</span>禁止洗钱、信用卡套现、虚假交易等行为，一经发现并确认，将终止该用户使用</li>
			</ul>
		</div>
		<div class="btm">
			<p>投资有风险，入市需谨慎</p>
		</div>
	</div>
</template>

<script>
	import pro from "../../../assets/js/common.js"
	export default{
		name:"withDraw_bankcard",
		data(){
			return{
				showUnboundCard:false,
				showBoundCard:false,
				bandCardList:'',
				withDrawMoney:'',
				accountMoney:'',
				rate:'',
				operateMoney:'',
				current1:0,
				bankid:''
			}
		},
		methods:{
			//select事件
			chooseChandle:function(e){
				var index = $("#manage option:selected").val();
				switch (index){
					case "2":
						this.setDeaultBank(e);
						break;
					case "4":
						this.$router.push({path:'/account_editBankCard'});
						break;
					case "3":
						this.delBankCard(e);
						break;
					default:
						break;
				}
			},
			//删除银行卡
			delBankCard:function(e){
				var headers = {
					token : JSON.parse(localStorage.user).token,
					secret : JSON.parse(localStorage.user).secret
				}
				layer.open({
					title:"删除银行卡",
				    type: 1,
				    skin: 'layui-layer-demo', 
				    anim: 2,
				    shadeClose: true, 
				    content: '删除银行卡将无法提现到该银行卡中，确认删除？',
				    btn:['确认','取消'],
				    btn1:function(){
						pro.fetch("post",'/user/withdraw/del_bank',{bankId:this.bankId},headers).then((res)=>{
							if(res.success == true){
								if(res.code == 1){
									layer.msg('删除成功',{time:1000});
									//重新拉取已绑定银行卡
									this.bandCardList = [];
									this.getBandCard();
								}
							}
						}).catch((err)=>{
							if(err.data.success == false){
								switch (err.data.code){
									case '-1':
										layer.msg("认证失败，参数错误或为空",{time:2000});
										break;
									case '2':
										layer.msg("删除失败",{time:2000});
										break;
									case '3':
										layer.msg("用户信息不存在",{time:2000});
										break;
									case '4':
										layer.msg("银行卡信息不存在",{time:2000});
										break;
									case '5':
										layer.msg("该银行卡正在提现中，不能删除",{time:2000});
										break;
									default:
										break;
								}
							}else{
								layer.msg("网络不给力，请稍后再试",{time:1000})
							}
						})
					},
					btn2:function(){
						this.$router.push({path:"/withDraw_bankcard"});
					}.bind(this)
				});
			},
			//设置默认
			setDeaultBank:function(e){
				var headers = {
					token : JSON.parse(localStorage.user).token,
					secret : JSON.parse(localStorage.user).secret
				}
				layer.open({
					title:"设为默认",
				    type: 1,
				    skin: 'layui-layer-demo', 
				    anim: 2,
				    shadeClose: true, 
				    content: '确认将该银行卡设为默认提现银行卡？',
				    btn:['确认','取消'],
				    btn1:function(e){
				    	pro.fetch("post",'/user/withdraw/set_default_bank',{bankId:e},headers).then((res)=>{
							if(res.success == true){
								if(res.code == 1){
									layer.msg('设置默认成功',{time:1000})
									//重新拉取选项卡
									this.bandCardList = [];
									this.getBandCard();
								}
							}
						}).catch((err)=>{
							if(err.data.success == false){
								switch (err.data.code){
									case -1:
									layer.msg("认证失败，参数错误或为空",{time:2000});
										break;
									case 3:
									layer.msg("银行卡不存在",{time:2000});
										break
									default:
										break;
								}
							}
							layer.msg('网络不给力，请稍后再试',{time:1000})
						})
				    },
				    btn2:function(){
				    	this.$router.push({path:"/safe_bindBankCard"});
				    }.bind(this)
				})
			},
			//去确认
			toWith_draw:function(){
				console.log(this.bankid);
				if(this.withDrawMoney<0){
					layer.msg("提现金额不能为负数");
				}else if(this.withDrawMoney == 0){
					layer.msg("请输入提现金额");
				}else if(this.withDrawMoney > this.accountMoney){
					layer.msg("提现大于账户余额，请重新输入");
				}else{
					this.$router.push({path:'/sure_withDraw',query:{"bankid":this.bankid,"withFee":this.rate,"withMoney":this.withDrawMoney}})
				}
			},
			//获取绑定银行卡列表
			getBandCard:function(){
				var headers = {
					token : JSON.parse(localStorage.user).token,
					secret : JSON.parse(localStorage.user).secret
				}
				pro.fetch("post","/user/withdraw/bank_list",'',headers).then((res)=>{
					if(res.success == true){
						if(res.code == 1){
							if(res.data == ''){
								this.showUnboundCard = true;
								this.showBoundCard = false;
							}else{
								this.showUnboundCard = false;
								this.showBoundCard = true;
								this.bandCardList = res.data;
							}
						}
					}
				}).catch((err)=>{
					if(err.data.success == false){
						switch (err.data.code){
							case '3':
							layer.msg("用户信息不存在",{time:2000})
								break;
							default:
								break;
						}
					}else{
						layer.msg('网络不给力，请稍后再试', {time: 2000});
					}
				})
			},
			//获取余额
			getAccountMonet:function(){
				var headers = {
					token : JSON.parse(localStorage.user).token,
					secret : JSON.parse(localStorage.user).secret
				}
				var data ={
					businessType:4,
					couponBusinessType:''
				}
				pro.fetch("post",'/user/getbalancerate',data,headers).then((res)=>{
					if(res.success == true){
						if(res.code == 1){
							this.accountMoney = res.data.balance;
							this.operateMoney = res.data.operateMoney;
						}
					}
				}).catch((err)=>{
					if(err.data.success == false){
						if(err.data.code == "3"){
							layer.msg('用户信息不存在', {time: 2000});
						}
					}else{
						layer.msg('网络不给力，请稍后再试', {time: 2000});
					}
				})
			},
			//计算提现手续费
			withDrawFree:function(){
				var headers = {
					token : JSON.parse(localStorage.user).token,
					secret : JSON.parse(localStorage.user).secret
				}
				var data ={
					money:this.withDrawMoney
				}
				pro.fetch("post","/user/withdraw/drawFee",data,headers).then((res)=>{
					if(res.success == true){
						if(res.code == 1){
							this.rate = res.data.fee;
						}
					}
				}).catch((err)=>{
					if(err.data.success == false){
						
					}else{
						layer.msg('网络不给力，请稍后再试', {time: 2000});
					}
				})
			},
			changeRate:function(){
				this.withDrawFree();
			},
			chooseBankCard:function(a,c){
				this.current1 = c
				this.bankid = a;
				return;
			}
		},
		mounted:function(){
			//互获取绑定卡列表
			this.getBandCard();
			this.getAccountMonet();
		}
	}
</script>

<style lang="scss" scoped type="text/css">
@import "../../../assets/css/common.scss";
	#withDraw_bankcard {
		width: 100%;
	}
	.account_withDraw_top {
		background-color: $blue;
		text-align: center;
		.title{
			margin-bottom: 20px;
			background-color: $bottom_color;
			height: 40px;
			width:100%;
			li{
				line-height: 40px;
				&:nth-child(1){
					float: left;
				}
				&:nth-child(2){
					float: left;
				}
				&:nth-child(3){
					float: left;
				}
				&:nth-child(4){
					float: left;
				}
				&:nth-child(5){
					
					float: right;
				}
				&:nth-child(6){
					margin-right: 20px;
					float: right;
				}
			}
		}
		.banklist{
			.chooseIn {
				border-color:$yellow;
			}
		}
		.chooseNo{
			margin-top: 10px;
			line-height: 40px;
			border: 1px solid #7a7f99;
			width: 400px;
			height: 40px;
			margin: auto;
			margin-bottom: 10px;
			border-radius: 5px;
			span{
				float: left;
				margin-left: 10px;
				&:nth-child(2){
					color: white;
					font-size: $fs16;
				}
			}
		}
		.ifont {
			color: $yellow;
			font-size: $fs16;
			float: left;
			margin-left: 20px;
		}
		
		label{
			float: left;
			margin-left: 10px;
			color: $white;
		}
		select {
			background-color: $blue;
			color: $lightblue;
			float: right;
			border: none;
			margin-right: 20px;
			line-height: 38px;
		}
		input{
			width: 120px;
			height: 30px;
			border-radius: 5px;
			border: 1px solid #7a7f99;
			margin-right: 10px;
			color: $white;
		}
		.writeIn{
			position: relative;
			left: 2px;
			span{
				color: $yellow;
				i{
					display: inline-block;
					width: 50px;
					font-size: fs12;
				}
			}
		}
		.btn{
			position: relative;
			top: 10px;
			left: -140px;
			width: 120px;
			height: 30px;
		}
	}
	.account_withDraw_btm {
		width: 100%;
		background-color: $blue;
		li {
			font-size: $fs14;
			color: $lightblue;
			text-indent:10px; 
			&:nth-child(1) {
				color: $white;
				height: 40px;
				line-height: 40px;
			}
			&:nth-child(2) {
				padding-top: 20px;
				height: 80px;
				color: $lightblue;
				p {
					color: $white;
					font-size: $fs12;
					padding-top: 10px;
				}
			}
			&:nth-child(3) {
				padding-top: 20px;
				height: 140px;
				border-bottom: 1px solid $bottom_color;
				border-top: 1px solid $bottom_color;
				span {
					font-size: $fs18;
					color: $white;
					font-weight: 800;
					margin: 0 6px;
				}
				p {
					padding-top: 10px;
				}
			}
			&:nth-child(4) {
				height: 80px;
				padding-top: 20px;
				border-bottom: 1px solid $bottom_color;
				span {
					font-size: $fs18;
					color: $white;
					font-weight: 800;
					margin: 0 5px;
				}
				p {
					font-size: $fs12;
					padding-top: 10px;
				}
			}
			&:nth-child(5) {
				height: 60px;
				line-height: 60px;
				span {
					color: $yellow;	
				}
			}
		}
	}
	.btm {
		line-height: 40px;
		text-align: center;
		height: 40px;
		margin-top: 10px;
		width: 100%;
		background-color: $blue;
		font-size: $fs12;
	}
	/*未绑定银行卡*/
	.withDraw_unboundBankCard {
		width : 100%;
		height : 200px;
		text-align: center;
		background-color : $blue;	
		.btn {
			width: 120px;
			height: 30px;
			margin-top: 20px;
		}
		p {
			margin-top: 10px;
			font-size: $fs12;
			text-align: center;
		}
	}
	.yel{
		color: $yellow;
	}
</style>