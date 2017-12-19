import Vue from 'vue'
import navbar from './components/NavBar.vue'
import router from './router'
import store from './store'
import VueResource from 'vue-resource'
import tipsDialog from './components/tipsDialog.vue'
Vue.use(VueResource);

Vue.config.productionTip = false

new Vue({
	el: '#app',
	router,
	store,
	template: `
  		<div>
     		 <keep-alive>
    				<router-view v-if="!$route.meta.notKeepAlive"></router-view>
			</keep-alive>
				<router-view v-if="$route.meta.notKeepAlive"></router-view>
     		<navbar v-show="navbarshow"></navbar>
     		<tipsDialog :msg="msgTips" ref="dialog"></tipsDialog>
     	</div>
  `,
  	data(){
  		return{
  			msg: '',
  		}
  	},
	components: {navbar, tipsDialog},
	computed: {
		navbarshow() {
			return this.$store.state.isshow.navBarShow
		},
		islogin() {
			return this.$store.state.account.islogin
		},
		msgTips: function(){
			return this.msg;
		},
		errorMsg(){
			return this.$store.state.market.errorMsg;
		}
	},
	watch: {
		errorMsg: function(n, o){
//			console.log(n);
			setTimeout(function(){
				this.$refs.dialog.isShow = true;
				this.msg = n.slice(0,-1);
			}.bind(this), 1000);
		}
	},
	mounted: function() {
		if(localStorage.helpshow == '关闭'){
			this.$store.state.isshow.helpshow = false;
		}
		if(this.$store.state.isshow.helpshow == true){
			localStorage.helpshow = '关闭';
		}
//		var lg = localStorage.guideshow;
//		if(lg == undefined) {
//			this.$store.state.isshow.guideshow = true;
//		} else {
//			this.$store.state.isshow.guideshow = false;
//		}

		//取URL地址
		var _url = window.location.href;
		var param = _url.split('?')[1];
		if(param != '' && param != undefined){
			param = param.split('=');
			if(param[0] == 'channel'){
				this.$store.state.account.packChannel = param[1].slice(0, -2);
			}
		}
	}
})