<template>
	<div id="bindBankCard">
		<tipsDialog :msg="msgTips"></tipsDialog>
		<topbar title="绑定银行卡"></topbar>
		<back></back>
		<cs title="客服"></cs>
		<div class="page_cont">
			<div class="bank_box">
				<ul>
					<template v-for="key in userInfo.bankList">
						<li :class="{current: key.default == true}">
							<div class="fl">
								<p class="white">{{key.bankName}}</p>
								<p>{{key.card | cardEvent}}</p>
							</div>
							<i class="icon_del fr" :id="key.bankId" @tap="deleteBank"></i>
							<i class="icon_choose fr" @tap="setDefault"><em></em></i>
						</li>
					</template>
				</ul>
			</div>
			<div class="fm_box mt10">
				<div class="ipt_row">
					<label for="name">开户姓名</label>
					<input type="text" id="name" placeholder="请输入开户姓名" v-model.trim="username" />
				</div>
				<div class="ipt_row">
					<label>开户银行</label>
					<select id="bank" v-model="bank">
						<option value="">请选择银行类型</option>
						<template v-for="key in bankList">
							<option :value="key.abbreviation">{{key.bankName}}</option>
						</template>
					</select>
					<i class="icon_select"></i>
				</div>
				<div class="ipt_row">
					<label>开户所在省</label>
					<select id="province" v-model="province">
						<option value="">请选择开户所在省</option>
						<template v-for="key in provinceList">
							<option :value="key.text">{{key.text}}</option>
						</template>
					</select>
					<i class="icon_select"></i>
				</div>
				<div class="ipt_row">
					<label>开户所在市</label>
					<select id="city" v-model="city">
						<option value="">请选择开户所在市</option>
						<template v-for="key in cityList">
							<option :value="key.text">{{key.text}}</option>
						</template>
					</select>
					<i class="icon_select"></i>
				</div>
				<div class="ipt_row">
					<label for="address">详细地址</label>
					<input type="text" id="address" placeholder="请输入详细地址" v-model="address" />
				</div>
				<div class="ipt_row">
					<label for="card">银行卡号</label>
					<input type="number" id="card" placeholder="请输入银行卡号" v-model="card" />
				</div>
				<btn name="添加银行卡" @tap.native="addBankCard"></btn>
			</div>
		</div>
	</div>
</template>

<script>
	import topbar from '../../components/Topbar.vue'
	import back from '../../components/back.vue'
	import cs from '../../components/customerService.vue'
	import btn from '../../components/bigBtn.vue'
	import tipsDialog from '../../components/tipsDialog.vue'
	import cityData from '../../assets/city.data.js'
	export default{
		name:'bindBankCard',
		components: {topbar, back, cs, btn, tipsDialog},
		data(){
			return {
				msg: '',
				bindBankList: [],
				bankList: [],
				provinceList: [],
				cityList: [],
				username: '',
				bank: '',
				province: '',
				city: '',
				address: '',
				card: '',
				bankReg: /^(\d{16}|\d{19})$/,
				nameReg: /^([\u4e00-\u9fa5]){2,7}$/
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
		filters: {
			cardEvent: function(e){
				if(!e) return;
				return e.substr(0,4) + '***********' + e.substr(-4,4);
			}
		},
		computed: {
			msgTips: function(){
				return this.msg;
			},
			PATH: function(){
				return this.$store.getters.PATH;
			},
			userInfo: function(){
				return this.$store.state.account;
			}
		},
		methods: {
			setDefault: function(e){
				if($(e.currentTarget).parents("li").hasClass("current")) return false;
				$(e.currentTarget).parents("li").addClass("current").siblings().removeClass("current");
				var bankId = $(e.currentTarget).siblings(".icon_del").attr("id");
				this.$http.post(this.PATH + '/user/withdraw/set_default_bank', {emulateJSON: true},{
					headers: {
						'token':  this.userInfo.token,
						'secret': this.userInfo.secret
					},
					params: {
						bankId: bankId
					},
					timeout: 5000
				}).then(function(e){
					var data = e.body;
					if(data.success == true){
						if(data.code == 1){
							this.$children[0].isShow = true;
							this.msg = '设置成功';
							this.userInfo.bankList.forEach(function(o, i){
								if(o.bankId == bankId){
									o.default = true;
								}else{
									o.default = false
								}
							});
						}
					}else{
						switch (data.code){
							case '-1':
								this.$children[0].isShow = true;
								this.msg = '认证失败，参数错误或为空';
								break;
							case '3':
								this.$children[0].isShow = true;
								this.msg = '银行卡不存在';
								break;
							default:
								break;
						}
					}
				}.bind(this), function(){
					this.$children[0].isShow = true;
					this.msg = '服务器连接失败';
				});
			},
			deleteBank: function(e){
//				if(!$(e.currentTarget).parents("li").hasClass("current")){
					var bankId = $(e.currentTarget).attr("id");
					if(bankId){
						this.$http.post(this.PATH + '/user/withdraw/del_bank', {emulateJSON: true},{
							headers: {
								'token':  this.userInfo.token,
								'secret': this.userInfo.secret
							},
							params: {
								bankId: bankId
							},
							timeout: 5000
						}).then(function(e){
							var data = e.body;
							if(data.success == true){
								if(data.code == 1){
									this.$children[0].isShow = true;
									this.msg = '删除成功';
									//重接拉取已绑定银行卡数据
									this.getBindBankList();
								}
							}else{
								switch (data.code){
									case '-1':
										this.$children[0].isShow = true;
										this.msg = '认证失败，参数错误或为空';
										break;
									case '2':
										this.$children[0].isShow = true;
										this.msg = '删除失败';
										break;
									case '3':
										this.$children[0].isShow = true;
										this.msg = '用户信息不存在';
										break;
									case '4':
										this.$children[0].isShow = true;
										this.msg = '银行卡信息不存在';
										break;
									case '5':
										this.$children[0].isShow = true;
										this.msg = '该银行卡正在提现中，不能删除';
										break;
									default:
										break;
								}
							}
						}.bind(this), function(){
							this.$children[0].isShow = true;
							this.msg = '服务器连接失败';
						});
					}
//				}else{
//					this.$children[0].isShow = true;
//					this.msg = '1246';
//				}
				
			},
			addBankCard: function(){
				if(this.username == ''){
					this.$children[0].isShow = true;
					this.msg = '请输入开户姓名';
				}else if(this.nameReg.test(this.username) == false){
					this.$children[0].isShow = true;
					this.msg = '请输入真实姓名';
				}else if(this.bank == ''){
					this.$children[0].isShow = true;
					this.msg = '请选择开户银行';
				}else if(this.province == ''){
					this.$children[0].isShow = true;
					this.msg = '请选择开户所在省';
				}else if(this.city == ''){
					this.$children[0].isShow = true;
					this.msg = '请选择开户所在市';
				}else if(this.address == ''){
					this.$children[0].isShow = true;
					this.msg = '请输入详细地址';
				}else if(this.card == ''){
					this.$children[0].isShow = true;
					this.msg = '请输入银行卡号';
				}else if(this.bankReg.test(this.card) == false){
					this.$children[0].isShow = true;
					this.msg = '银行卡号格式错误';
				}else{
					this.$http.post(this.PATH + '/user/withdraw/add_bank', {emulateJSON: true},{
						headers: {
							'token':  this.userInfo.token,
							'secret': this.userInfo.secret
						},
						params: {
							bank: this.bank,
							card: this.card,
							prov: this.province,
							city: this.city,
							address: this.address
						},
						timeout: 5000
					}).then(function(e){
						var data = e.body;
						if(data.success == true){
							if(data.code == 1){
								this.$children[0].isShow = true;
								this.msg = '添加成功';
								//重接拉取已绑定银行卡数据
								this.getBindBankList();
								this.username = '';
								this.bank = '';
								this.province = '';
								this.city = '';
								this.address = '';
								this.card = '';
							}
						}else{
							switch (data.code){
								case '-1':
									this.$children[0].isShow = true;
									this.msg = '认证失败';
									break;
								case '2':
									this.$children[0].isShow = true;
									this.msg = '设置失败';
									break;
								case '3':
									this.$children[0].isShow = true;
									this.msg = '用户信息不存在';
									break;
								case '4':
									this.$children[0].isShow = true;
									this.msg = '银行卡卡号已经存在';
									break;
								case '5':
									this.$children[0].isShow = true;
									this.msg = '请先实名认证方可添加银行卡';
									break;
								default:
									break;
							}
						}
					}.bind(this), function(){
						this.$children[0].isShow = true;
						this.msg = '服务器连接失败';
					});
				}
			},
			getBindBankList: function(){
				this.$http.post(this.PATH + '/user/withdraw/bank_list', {emulateJSON: true},{
					headers: {
						'token':  this.userInfo.token,
						'secret': this.userInfo.secret
					},
					params: {},
					timeout: 5000
				}).then(function(e){
					var data = e.body;
					if(data.success == true){
						if(data.code == 1){
							var len = 0;
							data.data.forEach(function(o, i){
								if(o.default == true) len += 1;
							});
							if(len == 0){
								data.data[0].default = true;
							}
							this.$store.state.account.bankList = data.data;
						}
					}else{
						switch (data.code){
							case '3':
								this.$children[0].isShow = true;
								this.msg = '用户信息不存在';
								break;
							default:
								break;
						}
					}
				}.bind(this), function(){
					this.$children[0].isShow = true;
					this.msg = '服务器连接失败';
				});
			},
			getBankList: function(){
				this.$http.post(this.PATH + '/user/withdraw/support_banks', {emulateJSON: true},{
					headers: {
						'token':  this.userInfo.token,
						'secret': this.userInfo.secret
					},
					params: {},
					timeout: 5000
				}).then(function(e){
					var data = e.body;
					if(data.success == true){
						if(data.code == 1) this.bankList = data.data;
					}else{
						switch (data.code){
							case '3':
								this.$children[0].isShow = true;
								this.msg = '用户信息不存在';
								break;
							default:
								break;
						}
					}
				}.bind(this), function(){
					this.$children[0].isShow = true;
					this.msg = '服务器连接失败';
				});
			}
		},
		mounted: function(){},
		activated: function(){
			//获取支持提现的银行卡
			this.getBankList();
			//获取省市数据
			this.provinceList = cityData;
			//获取已绑定的银行卡
			this.getBindBankList();
		}
		
	}
</script>

<style scoped lang="less">
@import url("../../assets/css/base.less");
/*ip6p及以上*/
@media (min-width:411px) {
    #bindBankCard{
    	width: 100%;
    	overflow: hidden;
    	padding-top: 50px;
    	.page_cont{
    		.bank_box{
    			width: 100%;
    			overflow: hidden;
    			li{
    				height: 60px;
    				padding: 0 15px;
    				background: @deepblue;
    				border-bottom: 1px solid @black;
    				&.current{
    					p, p:first-child{
    						color: @yellow;
    					}
    					.icon_choose{ 
    						border-color: @yellow;
    						em{
    							display: inline-block;
    						}
    					}
    				}
    				p{
    					color: @blue;
    					font-size: @fs14;
    					&:first-child{
    						line-height: 21px;
    						color: @white;
    						font-size: @fs16;
    						margin-top: 10px; 
    					}
    				}
    				.icon_choose{
						position: relative;
						float: right;
						width: 22px;
						height: 22px;
						border: 2px solid @blue;
						border-radius: 100%;
						margin: 20px 30px 0 0;
						em{
							display: none;
							position: absolute;
							top: 3px;
							left: 3px;
							width: 12px;
							height: 12px;
							background: @yellow;
							border-radius: 100%;
						}
					}
    				.icon_del{
    					width: 22px;
    					height: 20px;
    					overflow: hidden;
    					background: url(../../assets/img/del.png) no-repeat center center;
    					background-size: 100% 100%;
    					margin-top: 20px;
    				}
    			}
    		}
    		.fm_box{
    			width: 100%;
    			padding: 5px 0 15px 0;
    			overflow: hidden;
    			background: @deepblue;
    			.ipt_row{
    				label{
	    				width: 120px;
	    			}
	    			input{
	    				padding-left: 120px;
	    				padding-right: 10px;
	    			}
	    			select{
	    				position: absolute;
					    top: 0;
					    left: 0;
					    z-index: 2;
					    display: inline-block;
					    width: 100%;
					    height: 54px;
					    border: 1px solid #12121a;
					    border-radius: 5px;
					    padding: 10px 0 10px 120px;
					    color: #fff;
					    font-size: 16px;
					    background: #1b1b26;
	    			}
	    			.icon_select{
	    				width: 7px;
	    				height: 7px;
	    				overflow: hidden;
	    				background: url(../../assets/img/sanjiao.png) no-repeat center center;
	    				background-size: 100% 100%;
	    				position: absolute;
	    				bottom: 10px;
	    				right: 10px;
	    				z-index: 5;
	    			}
    			}
    		}
    	}
    }
}
/*ip6*/
@media (min-width:371px) and (max-width:410px) {
	#bindBankCard{
    	width: 100%;
    	overflow: hidden;
    	padding-top: 50px*@ip6;
    	.page_cont{
    		.bank_box{
    			width: 100%;
    			overflow: hidden;
    			li{
    				height: 60px*@ip6;
    				padding: 0 15px*@ip6;
    				background: @deepblue;
    				border-bottom: 1px solid @black;
    				&.current{
    					p, p:first-child{
    						color: @yellow;
    					}
    					.icon_choose{ 
    						border-color: @yellow;
    						em{
    							display: inline-block;
    						}
    					}
    				}
    				p{
    					color: @blue;
    					font-size: @fs14*@ip6;
    					&:first-child{
    						line-height: 21px*@ip6;
    						color: @white;
    						font-size: @fs16*@ip6;
    						margin-top: 10px*@ip6; 
    					}
    				}
    				.icon_choose{
						position: relative;
						float: right;
						width: 22px*@ip6;
						height: 22px*@ip6;
						border: 2px*@ip6 solid @blue;
						border-radius: 100%;
						margin: 20px*@ip6 30px*@ip6 0 0;
						em{
							display: none;
							position: absolute;
							top: 3px*@ip6;
							left: 3px*@ip6;
							width: 12px*@ip6;
							height: 12px*@ip6;
							background: @yellow;
							border-radius: 100%;
						}
					}
    				.icon_del{
    					width: 22px*@ip6;
    					height: 20px*@ip6;
    					overflow: hidden;
    					background: url(../../assets/img/del.png) no-repeat center center;
    					background-size: 100% 100%;
    					margin-top: 20px*@ip6;
    				}
    			}
    		}
    		.fm_box{
    			width: 100%;
    			padding: 5px*@ip6 0 15px*@ip6 0;
    			overflow: hidden;
    			background: @deepblue;
    			.ipt_row{
    				label{
	    				width: 120px*@ip6;
	    			}
	    			input{
	    				padding-left: 120px*@ip6;
	    				padding-right: 10px*@ip6;
	    			}
	    			select{
	    				position: absolute;
					    top: 0;
					    left: 0;
					    z-index: 2;
					    display: inline-block;
					    width: 100%;
					    height: 54px*@ip6;
					    border: 1px solid #12121a;
					    border-radius: 5px*@ip6;
					    padding: 10px*@ip6 0 10px*@ip6 120px*@ip6;
					    color: #fff;
					    font-size: @fs16*@ip6;
					    background: #1b1b26;
	    			}
	    			.icon_select{
	    				width: 7px*@ip6;
	    				height: 7px*@ip6;
	    				overflow: hidden;
	    				background: url(../../assets/img/sanjiao.png) no-repeat center center;
	    				background-size: 100% 100%;
	    				position: absolute;
	    				bottom: 10px*@ip6;
	    				right: 10px*@ip6;
	    				z-index: 5;
	    			}
    			}
    		}
    	}
    }
}
/*ip5*/
@media(max-width:370px) {
	#bindBankCard{
    	width: 100%;
    	overflow: hidden;
    	padding-top: 50px*@ip5;
    	.page_cont{
    		.bank_box{
    			width: 100%;
    			overflow: hidden;
    			li{
    				height: 60px*@ip5;
    				padding: 0 15px*@ip5;
    				background: @deepblue;
    				border-bottom: 1px solid @black;
    				&.current{
    					p, p:first-child{
    						color: @yellow;
    					}
    					.icon_choose{ 
    						border-color: @yellow;
    						em{
    							display: inline-block;
    						}
    					}
    				}
    				p{
    					color: @blue;
    					font-size: @fs14*@ip5;
    					&:first-child{
    						line-height: 21px*@ip5;
    						color: @white;
    						font-size: @fs16*@ip5;
    						margin-top: 10px*@ip5; 
    					}
    				}
    				.icon_choose{
						position: relative;
						float: right;
						width: 22px*@ip5;
						height: 22px*@ip5;
						border: 2px*@ip5 solid @blue;
						border-radius: 100%;
						margin: 20px*@ip5 30px*@ip5 0 0;
						em{
							display: none;
							position: absolute;
							top: 3px*@ip5;
							left: 3px*@ip5;
							width: 12px*@ip5;
							height: 12px*@ip5;
							background: @yellow;
							border-radius: 100%;
						}
					}
    				.icon_del{
    					width: 22px*@ip5;
    					height: 20px*@ip5;
    					overflow: hidden;
    					background: url(../../assets/img/del.png) no-repeat center center;
    					background-size: 100% 100%;
    					margin-top: 20px*@ip5;
    				}
    			}
    		}
    		.fm_box{
    			width: 100%;
    			padding: 5px*@ip5 0 15px*@ip5 0;
    			overflow: hidden;
    			background: @deepblue;
    			.ipt_row{
    				label{
	    				width: 120px*@ip5;
	    			}
	    			input{
	    				padding-left: 120px*@ip5;
	    				padding-right: 10px*@ip5;
	    			}
	    			select{
	    				position: absolute;
					    top: 0;
					    left: 0;
					    z-index: 2;
					    display: inline-block;
					    width: 100%;
					    height: 54px*@ip5;
					    border: 1px solid #12121a;
					    border-radius: 5px*@ip5;
					    padding: 10px*@ip5 0 10px*@ip5 120px*@ip5;
					    color: #fff;
					    font-size: @fs16*@ip5;
					    background: #1b1b26;
	    			}
	    			.icon_select{
	    				width: 7px*@ip5;
	    				height: 7px*@ip5;
	    				overflow: hidden;
	    				background: url(../../assets/img/sanjiao.png) no-repeat center center;
	    				background-size: 100% 100%;
	    				position: absolute;
	    				bottom: 10px*@ip5;
	    				right: 10px*@ip5;
	    				z-index: 5;
	    			}
    			}
    		}
    	}
    }
}
</style>