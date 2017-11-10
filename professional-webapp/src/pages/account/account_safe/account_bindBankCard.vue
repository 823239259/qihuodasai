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
				<li  v-for="(k,index) in bindBankList" class="bankList" v-on:click="choosBank" :class="{curr:current1 == index}">
					<i class="ifont">&#xe698;</i>
					<span>{{k.bankName}}</span>
					<span>尾号{{k.card.substr(-4,4)}}</span>
					<label  v-if="k.default !=true"></label>
					<label  v-else="k.default==true">默认</label>
					<select id="manage" @change="chooseChandle">
						<option value="1">管理</option>
						<option value="2" v-if="k.default !=true">设为默认</option>
						<option value="2" v-else="k.default==true"></option>
						<option value="3">编辑</option>
						<option value="4">删除</option>
					</select>
				</li>
			</ul>
			<button class="btn yellow" v-on:click="toAddBankCard">添加银行卡</button>
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
				bindBankList : [],
				bankId:'',
				current1:0,
			}
		},
		methods:{
			choosBank:function(e){
				var index = $(e.currentTarget).index();
//				$(e.currentTarget).addClass("btn1").siblings().removeClass("btn1");
				return this.bankId = this.bindBankList[index].bankId;
			},
			//select事件
			chooseChandle:function(e){
				var index = $("#manage option:selected").val();
				switch (index){
					case "2":
						this.setDeaultBank(this.bankId);
						break;
					case "3":
						this.$router.push({path:'/safe_addBankCard'});
						break;
					case "4":
						this.delBankCard(this.bankId);
						break;
					default:
						break;
				}
			},
			//添加银行卡
			toAddBankCard:function(){
				this.$router.push({path:'/safe_addBankCard'})
			},
			//删除银行卡
			delBankCard:function(){
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
						pro.fetch("post",'/user/withdraw/del_bank',{bankId:delbankid},headers).then((res)=>{
							if(res.success == true){
								if(res.code == 1){
									layer.msg('删除成功',{time:1000});
									//重新拉取已绑定银行卡
									this.bindBankList = [];
									this.getBindBankList();
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
							}
							layer.msg("网络不给力，请稍后再试",{time:1000})
						})
					},
					btn2:function(){
						this.$router.push({path:"/safe_bindBankCard"});
					}.bind(this)
				});
			},
			//设置默认
			setDeaultBank:function(setDefaultBank){
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
				    btn1:function(setDefaultBank){
				    	pro.fetch("post",'/user/withdraw/set_default_bank',{bankId:setDefaultBank},headers).then((res)=>{
							if(res.success == true){
								if(res.code == 1){
									layer.msg('设置默认成功',{time:1000})
									//重新拉取选项卡
									this.bindBankList = [];
									this.getBindBankList();
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
			//获取已绑定银行卡
			getBindBankList:function(){
				pro.fetch('post','/user/withdraw/ bank_list','',
				{
					token : JSON.parse(localStorage.user).token,
					secret : JSON.parse(localStorage.user).secret
				}).then((res)=>{
					var data = res.data 
					if(res.success == true){
						if(res.code == 1){
							data.forEach(function(o,i){
								this.bindBankList.push(o);
							}.bind(this));
						}
					}
				}).catch((err)=>{
					if(err.data.success == false){
						layer.msg("获取用户信息失败，请重试",{time:2000});
					}else{
						layer.msg('网络不给力，请稍后再试',{time:1000});
					}
				})
			}
		},
		mounted : function(){
			//获取已绑定的银行卡
			this.bindBankList = [];
			this.getBindBankList();
		},
		activated: function(){
			
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
			li{
				width: 400px;
				height: 40px;
				display: inline-block;
				margin: auto;
				border-radius: 5px;
				line-height: 40px;
				margin-top: 30px;
				span{
					float: left;
					margin-left: 10px;
					&:nth-child(2){
						color: white;
						font-size: $fs16;
					}
				}
				label{
					float: left;
					margin-left: 10px;
					color: $white;
				}
			}
			.btn {
				width: 160px;
				height: 30px;
				margin-top: 20px;
				color: black;
			}
			input{
				width: 20px;
				height: 20px;
				border: 1px solid $yellow;
				border-radius: 10px;
			}
			select {
				background-color: $blue;
				color: $lightblue;
				float: right;
				border: none;
				margin-right: 20px;
				line-height: 38px;
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
	.ifont {
		color: $yellow;
		font-size: $fs16;
		float: left;
		margin-left: 20px;
	}
	.curr{
		border: 1px solid $yellow;
	}
</style>