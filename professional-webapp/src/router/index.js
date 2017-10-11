import Vue from 'vue'
import Router from 'vue-router'
import test from '../components/test.vue'
import index from '../pages/index.vue'
import trade from '../pages/trade.vue'
import account from '../pages/account.vue'
import calendar from '../pages/calendar.vue'
import download from '../pages/download.vue'
import liveStream from '../pages/liveStream.vue'
import openAccount from '../pages/openAccount.vue'

Vue.use(Router)

export default new Router({
	mode: 'history',
	routes: [
		{
			path: '*',
			component: index
		},
		{
			path: '/',
			component: index
		},
		{
			path: '/trade',
			component: trade
		},
		{
			path: '/account',
			component: account
		},
		{
			path: '/calendar',
			component: calendar
		},
		{
			path: '/download',
			component: download
		},
		{
			path: '/liveStream',
			component: liveStream
		},
		{
			path: '/openAccount',
			component: openAccount
		},
		{
			path: '/test',
			component: test
		}
	]
})