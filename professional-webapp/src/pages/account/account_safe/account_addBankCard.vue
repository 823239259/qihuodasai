<template>
	<div id="account_addBankCard">
		<div class="account_addBankCard_top">
			<p>姓名：<input type="text" v-model="username" /></p>
			<p>开户银行：<input type="text" v-model="bank"/></p>
			<p>开户省份：
				<select name="下拉框">
					<option value="">四川省</option>
				</select>
			</p>
			<p>开户城市：<input type="text" v-model="city"/></p>
			<p>开户支行：<input type="text" v-model="address"/></p>
			<p>银行卡卡号：<input type="text" v-model="bankId"/></p>
			<p>确认卡号：<input type="text" v-model="sure_bankId"/><span>(不支持存折)</span></p>
			<button class="btn yellow" v-on:click="addBankCard">确认</button>
		</div>
		<div class="account_addBankCard_btm">
			<p>新增/修改银行卡遇到问题</p>
			<ul>
				<li>绑定的银行卡不是自己的怎么办？</li>
				<li>答：建议绑定自己的银行卡，如有疑问请拨打客服热线。</li>
			</ul>
		</div>
	</div>
</template>

<script>
	import pro from "../../../assets/js/common.js"
	export default {
		name : "safe_addBankCard",
		data(){
			return{
				bankId : '',
				sure_bankId : '',
				bank : '',
				prov : '',
				city : '',
				address: '',
				username : ''
			}
		},
		methods : {
			addBankCard :function(){
				if(this.bank == ''){
					layer.msg('请填写开户银行',{time:1000})
				}else if(this.prov == ''){
					layer.msg('请填写开户省份',{time:1000})
				}else if(this.city == ''){
					layer.msg('请填写开户城市',{time:1000})
				}else if(this.address == ''){
					layer.msg('请填写开户地址',{time:1000})
				}else if(this.bankId == ''){
					layer.msg('请输入储蓄卡卡号',{time:1000})
				}else if(this.sure_bankId == ''){
					layer.msg('请输入确认卡号',{time:1000})
				}else if(this.bankId!=this.sure_bankId){
					layer.msg('确认卡号与储蓄卡号不一致ng',{time:1000})
				}else {
					var data = {
							bankId:'',
							bank:this.bank,
							card:this.bankId,
							prov:this.prov,
							city:this.city,
							address: this.address
					};
					var headers = {
						token : JSON.parse(localStorage.user).token,
						secret : JSON.parse(localStorage.user).secret
					};
					pro.fetch('post','/user/withdraw/add_bank',data,headers).then((res)=>{
						if(res.success == ''){
							if(res.caode == 1){
								layer.msg("绑定成功",{time:1000});
								this.$router.push({path:'/safe_bindBankCard'})
							}else if(res.code == 5){
								lay.msg('请先实名认证再绑定银行卡',{time:1000});
								this.$router.push({path:'/safe_certification'});
							}else if(res.code == 4){
								this.bank ='';
								this.bankId='';
								this.prov='';
								this.city='';
								this.address='';
								lay.msg('银行卡卡号已经存在',{time:1000});
							}else if(res.code == 3){
								lay.msg('用户信息不存在，请仔细核对',{time:1000});
							}
						}
					}).catch((err)=>{
						layer.msg('网络不给力，请稍后重试',{time:1000})
					});
				}
			}
		}
	}
</script>

<style lang="scss" scoped type="text/css">
	@import "../../../assets/css/common.scss";
	#account_addBankCard {
		width: 100%;
		.account_addBankCard_top {
			width : 100%;
			height : 420px;
			background-color : $blue;
			p {
				margin-left: 40%;
				padding-top: 20px;
				span {
					font-size : $fs12;
				}
			}
			input {
				width: 160px;
				height: 30px;
				border: 1px solid $bottom_color;
				border-radius: 5px;
				color: $white;
				&:hover{
					border: 1px solid $yellow;
				}
			}
			.btn {
				width: 160px;
				height: 30px;
				margin-left: 50%;
				margin-top: 10px;
			}
			select{
				background-color: $bottom_color;
				color: $white;
				width: 160px;
				height: 30px;
			}
		}
		.account_addBankCard_btm {
			width: 100%;
			height: 120px;
			p {
				width: 100%;
				height: 40px;
				background-color: $bottom_color;
				line-height: 40px;
				margin-top: 10px;
			}
			ul {
				height: 80px;
				width: 100%;
				background-color: $blue;
			}
			li {
				padding-top:20px; 
				&:nth-child(2) {
					color: $white;
				}
			}
		}
	}
	
</style>