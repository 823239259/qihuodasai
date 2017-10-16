<template>
	<div id="account_certification">
		<p><span>提示：</span>认证年龄需满<span>18</span>周岁，不建议<span>60</span>岁以上的用户交易</p>
		<div>
			<p>姓名：<input type="text" /></p>
			<p>身份证号码：<input type="text" /></p>
			<button class="btn yellow">确认</button>
		</div>
		<p>实名认证相关</p>
		<ul>
			<li>实名认证遇到问题怎么办？</li>
			<li>答：实名认证因涉及到账户提现安全，最好认证本人身份信息，认证信息错误三次以上需要后台人工审核才可继续认证。</li>
		</ul>
		<p>投资有风险，入市需谨慎</p>
	</div>
</template>

<script>
	export default {
		name : "account_certification"
	}
</script>

<style lang="scss" scoped type="text/css">
	@import "../../assets/css/common.scss";
	
	
</style>