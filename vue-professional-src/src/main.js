import Vue from 'vue'
import navbar from './components/NavBar.vue'
import router from './router'
import store from './store'
import VueResource from 'vue-resource';
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
     	</div>	
  `,
	components: {navbar},
	computed: {
		navbarshow() {
			return this.$store.state.isshow.navBarShow
		},
		islogin() {
			return this.$store.state.account.islogin
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