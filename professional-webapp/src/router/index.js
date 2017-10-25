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
import login from "../pages/account/login.vue"
import register from "../pages/account/register.vue"
import forgetPassword from "../pages/account/forgetPassword.vue"
import account_openDetail from "../pages/account/account_openDetail/account_openDetail.vue"
import account_survey from "../pages/account/account_survey/account_survey.vue"
import account_safe from "../pages/account/account_safe/account_safe.vue"
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
			component: account,
			children:[{
				path : '/',
				component : account_survey
			},
			{
				path : '/account_survey',
				component : account_survey
			},
			{
				path : '/account_openDetail',
				component : account_openDetail
			},
			{
				path : '/account_safe',
				component : account_safe
			}]
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
		},
		{
			path: '/login',
			component: login
		},
		{
			path : '/register',
			component: register
		},
		{
			path : '/forgetPassword',
			component : forgetPassword
		}
	]
})