// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
//import guide from './pages/Guide.vue'
import navbar from './components/NavBar.vue'
import router from './router'
import store from './store'

Vue.config.productionTip = false

new Vue({
	el: '#app',
	router,
	store,
	template: `
  		<div>
     		<transition name="fade" mode="out-in">
     		 <router-view></router-view>
     		</transition>
     		<navbar v-show="navbarshow"></navbar>
     	</div>	
  `,
	components: {
		navbar
	},
	computed: {
		navbarshow() {
			return this.$store.state.isshow.navBarShow
		}
	}
})