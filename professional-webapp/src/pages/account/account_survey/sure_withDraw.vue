<template>
	<div id="withDraw_sure">
		<div class="withDraw_sure_top">
			<div class="sure_top_left">
				<ul>
					<li>银行卡信息：</li>
					<li>提现金额：</li>
					<li>提现手续费：</li>
					<li>实际到账金额：</li>
				</ul>
			</div>
			<div class="sure_top_right">
				<ul>
					<li><span>{{username}}</span><i>{{bankChnName}}（{{bankCrad}}）</i></li>
					<li><span class="color_yellow">{{withMoney}}</span>元</li>
					<li><span>{{withFee}}</span>元<i>（按揭提现金额的0.5%收取）</i></li>
					<li><span class="color_yellow">{{withMoney-withFee}}</span>元<i>（提现金额-提现手续费）</i></li>
				</ul>
			</div>
		</div>
		<div class="withDraw_sure_center">
			<div class="sure_center_left">
				<ul>
					<li>提现密码：</li>
				</ul>
			</div>
			<div class="sure_center_right">
				<ul>
					<li><input type="password" v-model="withdrawPwd"/><span v-on:click="toResetPassword">忘记密码</span></li>
					<li><button class="btn yellow" v-on:click="withDraw_money">确认提现</button><span v-on:click="back">返回修改</span></li>
				</ul>
			</div>
		</div>
		<div class="withDraw_sure_btm">
			<p >提现相关</p>
			<ul>
				<li>忘记密码怎么办？</li>
				<li>答：可点击【忘记密码】重新设置提款密码</li>
			</ul>
		</div>
		<p class="p_center">投资有风险，入市需谨慎</p>
	</div>
</template>

<script>
	import pro from "../../../assets/js/common.js"
	export default {
		name:"sure_withDraw",
		data(){
			return{
				withdrawPwd:'',
				withFee:'',
				bankid:'',
				withMoney:'',
				bankName:'',
				username:'',
				bankCrad:'',
				bankCardShow:'',
				bankChnName:''
			}
		},
		methods:{
			//忘记密码
			toResetPassword:function(){
				this.$router.push({path:'/safe_withdrawalPassword'})
			},
			back:function(){
				this.$router.push({path:'/withDraw_bankcard'})
			},
			//提现
			withDraw_money:function(){
				var pwdReg = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,18}$/;
				var token = JSON.parse(localStorage.user).token;
				var secret = JSON.parse(localStorage.user).secret;
				var headers = {
					token : token,
					secret :secret
				}
				var data = {
					bank:this.bankName,
				    card:this.bankCardShow,
				    money:this.withMoney,
				    withdrawPwd:this.withdrawPwd
				}
				console.log(data)
				if(this.withdrawPwd==''){
					layer.msg("请输入提现密码");
				}else if(pwdReg.test(this.withdrawPwd)==false){
					this.withdrawPwd = '';
					layer.msg("密码格式输入有误，请重试",{time:2000});
				}else {
					pro.fetch("post","/user/withdraw/handle",data,headers).then((res)=>{
						if(res.success == true){
							if(res.code == 1){
								layer.msg("提现申请已提交，等待要银行处理。若24小时未到账请拨打：400-852-8008",{time:2000});
								this.$router.push({path:"/account_survey"});
							}
						}
					}).catch((err)=>{
						var data = err.data ;
						if(data == undefined || data == ""){
							layer.msg("网络不给力，请稍后再试",{time:2000})
						}else{
							layer.msg(data.message,{time:2000})
						}
					})
				}
			},
			//查询银行卡信息
			queryBank:function(){
				var data = {
					bankId:this.bankid
				}
				var token = JSON.parse(localStorage.user).token;
				var secret = JSON.parse(localStorage.user).secret;
				var headers = {
					token : token,
					secret :secret
				}
				pro.fetch("post","/user/withdraw/queryBank",data,headers).then((res)=>{
					if(res.success == true){
						if(res.code == 1){
							var phoneNumber = res.data.tname;
							this.username = "";
							this.bankName = res.data.abbreviation;
							this.bankChnName = res.data.bankName;
							var card = res.data.card;
							this.bankCrad = "**** **** **** "+card.substring(15,20);
							this.bankCardShow = res.data.card;
						}
					}
				}).catch((err)=>{
					console.log(err)
					var data = err.data;
					if(data == undefined || data == ""){
						layer.msg("网络不给力，请稍后再试",{time:2000})
					}else{
						layer.msg(data.message,{time:2000})
					}
					
				})
			}
		},
		mounted:function(){
			this.bankid=this.$route.query.bankid;
			this.withFee=this.$route.query.withFee;
			this.withMoney=this.$route.query.withMoney;
			console.log(this.bankid);
			console.log(this.withFee);
			console.log(this.withMoney)
			this.queryBank();
		},
		activated:function(){
			this.bankid=this.$route.query.bankid;
			this.withFee=this.$route.query.withFee;
			this.withMoney=this.$route.query.withMoney;
		}
		
	}
</script>

<style lang="scss" scoped type="text/css">
@import "../../../assets/css/common.scss";
	#withDraw_sure {
		width: 100%;
		.withDraw_sure_top {
			background-color: $blue;
			padding-top: 20px;
			height: 210px;
			width: 100%;
			border-bottom: 1px solid $bottom_color;
			/*text-align: center;*/
			i {
				font-size: $fs12;
				margin-left: 5px;
				text-transform: uppercase;
			}
			span {
				color: $white;
			}
			.color_yellow {
				color: $yellow;
				font-weight: 800;
			}
			li {
				margin-top:25px;
			}
			.sure_top_left{
				width: 50%;
				float: left;
				text-align: right;
			}
			.sure_top_right{
				width: 50%;
				float: left;
			}
		}
		.withDraw_sure_center {
			background-color: $blue;
			height: 150px;
			width: 100%;
			li{
				height: 75px;
				line-height: 75px;
			}
			span {
			 	font-size: $fs12;
			 	margin-left: 10px;
			}
			.sure_center_left{
				width: 50%;
				float: left;
				text-align: right;
			}
			.sure_center_right{
				width: 50%;
				float: left;
				input {
					width: 120px;
					height: 30px;
					border: 1px solid $bottom_color;
					border-radius: 5px;
					color: $white;
					&:hover{
						border-color: $yellow;
					}
				}
				.btn {
					width: 120px;
					height: 30px;
				}
				span{
					&:hover{
						color: $yellow;
						cursor: pointer;
					}
				}
			}
		}
		.withDraw_sure_btm {
			margin-top: 5px;
			text-indent: 10px;
			background-color: $blue;
			height: 120px;
			text-align: center;
			li{
				text-align: left;
				height: 40px;
				line-height: 40px;
			}
			p {
				text-align: left;
				background-color: $bottom_color;
				height: 40px;
				color: $white;
				line-height: 40px;
			}
		}
		.p_center{
			background-color: $bottom_color;
			text-align: center;
			width: 100%;
			height: 40px;
			line-height: 40px;
			margin-top: 5px;
			font-size: $fs12;
		}
	}
</style>