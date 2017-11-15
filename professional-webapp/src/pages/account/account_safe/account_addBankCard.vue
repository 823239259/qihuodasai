<template>
	<div id="account_addBankCard">
		<div class="account_addBankCard_top">
			<p>姓名：<input type="text" v-model="username" /></p>
			<p>开户银行：
				<select id="bank" v-model="bank">
					<option value="">请选择银行类型</option>
					<template v-for="key in bankList">
						<option :value="key.abbreviation">{{key.bankName}}</option>
					</template>
				</select>
			</p>
			<p>开户省份：
				<select id="province" v-model="province">
					<option value="">请选择开户省份</option>
					<template v-for="key in provinceList">
						<option :value="key.text">{{key.text}}</option>
					</template>
				</select>
			</p>
			<p>开户城市：
				<select id="city" v-model="city">
					<option value="">请选择开户城市</option>
					<template v-for="key in cityList">
						<option :value="key.text">{{key.text}}</option>
					</template>
				</select>
			</p>
			<p>开户支行：<input type="text" v-model="address"/></p>
			<p>银行卡卡号：<input type="text" v-model="bankId"/><span>(不支持存折)</span></p>
			<p>确认卡号：<input type="text" v-model="sure_bankId"/></p>
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
	import cityData from '../../../assets/js/city.data.js'
	export default {
		name : "safe_addBankCard",
		data(){
			return{
				bankId : '',
				sure_bankId : '',
				bank : '',
				province : '',
				city : '',
				address: '',
				username : '',
				bankList:[],
				provinceList: [],
				province:'',
				cityList: [],
				bankReg: /^(\d{16}|\d{19})$/,
				setFirstDeault:1,
				defaultBankId:''
			}
		},
		watch: {
			province: function(n, o){
				var provinceData = this.provinceList;
				provinceData.forEach(function(o, i){
					if(o.text == n) this.cityList = o.children;
 				}.bind(this));
			}
		},
		methods : {
			addBankCard :function(){
				if(this.bank == ''){
					layer.msg('请填写开户银行',{time:1000})
				}else if(this.province == ''){
					layer.msg('请填写开户省份',{time:1000})
				}else if(this.city == ''){
					layer.msg('请填写开户城市',{time:1000})
				}else if(this.address == ''){
					layer.msg('请填写开户地址',{time:1000})
				}else if(this.bankId == ''){
					layer.msg('请输入储蓄卡卡号',{time:1000})
				}else if(this.sure_bankId == ''){
					layer.msg('请输入确认卡号',{time:1000})
				}else if(this.bankReg.test(this.bankId) == false || this.bankReg.test(this.sure_bankId) == false){
					layer.msg('请输入正确的银行卡号',{time:1000})
				}else if(this.bankId!=this.sure_bankId){
					layer.msg('确认卡号与储蓄卡号不一致',{time:1000})
				}else {
					var data = {
						bank:this.bank,
						card:this.bankId,
						prov:this.province,
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
								this.bank ='';
								this.bankId='';
								this.province='';
								this.city='';
								this.address='';
								//重接拉取已绑定银行卡数据
								this.getBindBankList();
								if(this.setFirstDeault == 1){
									this.setDefaultBank();
									this.setFirstDeault =2
								}
								this.$router.push({path:'/safe_bindBankCard'})
							}
						}
					}).catch((err)=>{
						if(err.data.success == false){
							switch (err.data.code){
								case '-1':
									this.bank ='';
									this.bankId='';
									this.province='';
									this.city='';
									this.address='';
									layer.msg("认证失败",{time:2000})
									break;
								case '2':
									this.bank ='';
									this.bankId='';
									this.province='';
									this.city='';
									this.address='';
									layer.msg("设置失败",{time:2000})
									break;
								case '3':
									this.bank ='';
									this.bankId='';
									this.province='';
									this.city='';
									this.address='';
									layer.msg("用户信息不存在",{time:2000})
									break;
								case '4':
									this.bank ='';
									this.bankId='';
									this.province='';
									this.city='';
									this.address='';
									layer.msg("银行卡号已经存在",{time:2000})
									break;
								case '5':
									this.bank ='';
									this.bankId='';
									this.province='';
									this.city='';
									this.address='';
									layer.msg("请先实名，方可添加银行卡",{time:2000})
									break;
							}
						}else{
							this.bank ='';
							this.bankId='';
							this.province='';
							this.city='';
							this.address='';
							layer.msg("网络不给力，请稍后再试",{time:2000})
						}
					});
				}
			},
			//获取支持的银行卡
			getBankList: function(){
				var headers = {
					token : JSON.parse(localStorage.user).token,
					secret : JSON.parse(localStorage.user).secret
				}
				pro.fetch("post",'/user/withdraw/support_banks','',headers).then((res)=>{
					var data = res.data
					if(res.success == true){
						if(res.code == 1){
							this.bankList = data
						}
					}
				}).catch((err)=>{
					if(err.data.success == false){
						if(err.data.code == 3){
							layer.msg("用户信息不存在",{time:2000});
						}
					}else{
						layer.msg("网络不给力，请稍后再试",{time:2000});
					}
				})
			},
			//第一次绑定默认银行卡
			setDefaultBank:function(){
				var headers ={
					token : JSON.parse(localStorage.user).token,
					secret : JSON.parse(localStorage.user).secret
				}
				pro.fetch("post",'user/withdraw/set_default_bank',{bankId:this.defaultBankId},headers).then((res)=>{
					if(res.success == true){
						if(res.code == 1){
							layer.msg("已为您设置成默认银行卡",{time:2000});
						}
					}
				}).catch((err)=>{
					layer.msg("网络不给力，请稍后再试",{time:2000})
				})
			},
			//第一次绑定成功后重新获取绑定银行卡
			getBindBankList: function(){
				var headers ={
					token : JSON.parse(localStorage.user).token,
					secret : JSON.parse(localStorage.user).secret
				}
				pro.fetch("post","/user/withdraw/bank_list",'',headers).then((res)=>{
					if(res.success == true){
						if(res.code == 1){
							if(res.data.bankId!=''){
								this.defaultBankId == res.data.bankId
							}
						}
					}
				}).catch((err)=>{
					if(err.data.success == false){
	
					}else{
						layer.msg("网络不给力，请稍后再试",{time:2000})
					}
				})
			}
		},
		activated:function(){
		},
		mounted:function(){
			//获取支持提现的银行卡
			this.getBankList();
			//获取支持省市
			this.provinceList = cityData;
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
				background-color: #596080;
				border-radius:5px; 
				&:hover{
					border: 1px solid $yellow;
				}
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