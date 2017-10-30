<template>
	<div id="account_bindBankCard">
		<div class="account_bindBankCard_top">
			<ul>
				<li>
					<img src="../../../assets/images/icon_accountsafe.png" alt="logo_safe" />
				</li>
				<li>
					<p>您已绑定银行卡，可提现到银行卡中。</p>
					<p>以下为您绑定的银行卡</p>
				</li>
			</ul>
		</div>
		<div class="account_bindBankCard_center">
			<ul>
				<li>
					<input type="radio" name="radiobutton" value="radiobutton" checked> 
				</li>
				<li>
					<input type="radio" name="radiobutton" value="radiobutton" checked>  
				</li>
				<li>
					<input type="radio" name="radiobutton" value="radiobutton" checked>  
				</li>
				<li>
					<button class="btn yellow" v-on:click="toAddBankCard">添加银行卡</button>
				</li>
			</ul>
			
		</div>
		<div class="account_bindBankCard_btm">
			<p>投资有分析，入市需谨慎</p>
		</div>
	</div>
</template>

<script>
	import pro from "../../../assets/js/common.js"
	export default {
		name : "safe_bindBankCard",
		data(){
			return{
				bankList : ''
			}
		},
		methods:{
			//添加银行卡
			toAddBankCard:function(){
				this.$router.push({path:'/safe_addBankCard'})
			},
			//删除银行卡
//			delBankCard:function(){
//				pro.fetch("post",'/user/withdraw/del_bank',data,headers).then((res)=>{
//					if(res.success == true){
//						if(res.code == 1){
//							layer.msg('删除成功',{time:1000});
//						}else if(res.code == 5){
//							layer.msg("该银行卡正在提现处理中，不能删除",{time:1000})
//						}
//					}
//				}).catch((err)=>{
//					layer.msg("网络不给力，请稍后再试",{time:1000})
//				})
//			},
			//设置默认
//			setDeaultBank:function(){
//				pro.fetch("post",'/user/withdraw/set_default_bank',data,headers).then((res)=>{
//					if(res.success == true){
//						if(res.code == 1){
//							layer.msg('设置默认成功',{time:1000})
//						}else if(res.code == 3){
//							layer.msg('银行卡不存在',{time:1000})
//						}
//					}
//				}).catch((err)=>{
//					layer.msg('网络不给力，请稍后再试',{time:1000})
//				})
//			}
		},
		mounted : function(){
			//获取绑定银行卡
			pro.fetch('post','/user/withdraw/ bank_list','',{
				token : JSON.parse(localStorage.user).token,
				secret : JSON.parse(localStorage.user).secret
			}).then((res)=>{
				if(res.success == true){
					if(res.code == 1){
						this.bankList = res.data;
					}
				}
			}).catch((err)=>{
				layer.msg('网络不给力，请稍后再试',{time:1000})
			})
		}
	}
</script>

<style lang="scss" scoped type="text/css">
	@import "../../../assets/css/common.scss";
	#account_bindBankCard {
		width: 100%;
		.account_bindBankCard_top {
			height : 150px;
			background-color : $blue;
			border-bottom : 1px solid $bottom_color;
			li {
				float: left;
				&:nth-child(1){
					margin-left: 30px;
					margin-top: 30px;
				}
				&:nth-child(2) {
					margin-top: 60px;
					margin-left: 20px;
					p {
						&:nth-child(1){
							color : $white;
							margin-bottom: 5px;
						}
					}
				}
			}
		}
		.account_bindBankCard_center {
			width: 100%;
			height: 160px;
			background-color: $blue;
			text-align: center;
			input {
				width: 400px;
				height: 30px;
				border: 1px solid $bottom_color;
				border-radius: 5px;
			}
			.btn {
				width: 160px;
				height: 30px;
			}
		}
		.account_bindBankCard_btm {
			p {
				width: 100%;
				height: 40px;
				text-align: center;
				background-color: $blue;
				margin-top: 10px;
				line-height: 40px;
			}
		}
	}
</style>