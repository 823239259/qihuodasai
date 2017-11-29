<template>
	<div id="endScheme">
		<div class="bg"></div>
		<div class="endScheme">
			<p>终结方案<i class="ifont ifont_x" v-on:click="close">&#xe624;</i></p>
			<p>是否终结该方案？</p>
			<button class="btn yellow" v-on:click="confirm">确认</button>
			<button class="btn green" v-on:click="cancel">取消</button>
		</div>
	</div>
</template>
<script>
	import pro from "../../../assets/js/common.js"
	export default {
		name: "account_endScheme",
		data(){
			return{
			}
		},
		methods:{
			confirm:function(){
				var headers = {
					token : JSON.parse(localStorage.user).token,
					secret : JSON.parse(localStorage.user).secret
				}
				var data = {
					id:this.id,
					cId:'',
					businessType:8
				}
				pro.fetch("post","/user/ftrade/endtrade",data,headers).then((res)=>{
					if(res.success == true){
						if(res.code == 1){
							layer.msg("操作成功",{time:2000});
							this.$router.push({path:'/openDetail_billingDetails',query:{"id":this.id}})
						}
					}
				}).catch((err)=>{
					console.log(err);
					if(err.data.success == false){
						switch (err.data.code){
							case '-1':
									layer.msg("认证失败",{time:2000});
									break;
								case '2':
									layer.msg("系统异常，结算失败",{time:2000});
									break;
								case '3':
									layer.msg("已申请终结",{time:2000});
									break;
								case '4':
									layer.msg("非操盘中",{time:2000});
									break;
								case '5':
									layer.msg("未找到该优惠劵",{time:2000});
									break;
								case '6':
									layer.msg("折扣劵无法使用",{time:2000});
									break;
								case '7':
									layer.msg("方案编号不能为空",{time:2000});
									break;
								case '8':
									layer.msg("参数错误",{time:2000});
									break;
								case '9':
									layer.msg("参数错误",{time:2000});
									break;
								case '10':
									layer.msg("有持仓，不能结算",{time:2000});
									break;
								case '11':
									layer.msg(err.data.message,{time:2000});
									break;
								case '20':
									layer.msg("连接服务不成功",{time:2000});
									break;
								case '22':
									layer.msg("交易账户不存在",{time:2000});
									break;
								default:
									break;
						}
					}else{
						layer.msg("网络不给力请稍后再试",{time:2000})
					}
				})
			},
			close:function(){
				this.$router.push({path:'/account_openDetail'});
			},
			cancel:function(){
				this.$router.push({path:'/account_openDetail'});
			}
		},
		mounted:function(){
			this.id = this.$route.query.id;
		},
		actived:function(){
			this.id = this.$route.query.id;
		}
	}
</script>

<style lang="scss" scoped type="text/css">
@import "../../../assets/css/common.scss";
	#endScheme{
		height: 800px;
	}
	.endScheme{
		position: absolute;
		top: 50%;
		left: 50%;
		width: 360px;
		height: 150px;
		z-index: 120;
		margin: -100px 0 0 -130px;
		background-color: $blue;
		border-radius: 10px;
		p{
			text-align: center;
			&:nth-child(1){
				background-color: $bottom_color;
				height: 40px;
				border-radius: 10px;
				text-align: center;
				line-height: 40px;
			}
			&:nth-child(2){
				margin-top: 20px;
			}
		}
	}
	.btn{
		width: 120px;
		height: 30px;
		border-radius: 5px;
		margin-top: 20px;
	}
	.yellow{
		float: left;
		margin-left: 40px;
	}
	.green{
		float: right;
		margin-right: 40px;
	}
	.ifont_x {
			float: right;
			margin-right: 10px;
			color: $lightblue;
		}

</style>