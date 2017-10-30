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
import sure_withDraw from "../pages/account/account_survey/sure_withDraw.vue"
import withDraw_bankcard from "../pages/account/account_survey/withDraw_bankcard.vue"
import safe_addBankCard from "../pages/account/account_safe/account_addBankCard.vue"
import safe_bindBankCard from "../pages/account/account_safe/account_bindBankCard.vue"
import safe_certification from "../pages/account/account_safe/account_certification.vue"
import safe_resetCellPhone from "../pages/account/account_safe/account_resetCellPhone.vue"
import safe_resetLoginPassword from "../pages/account/account_safe/account_resetLoginPassword.vue"
import safe_withdrawalPassword from "../pages/account/account_safe/account_withdrawalPassword.vue"
import openDetail_additionlMargin from "../pages/account/account_openDetail/account_additionlMargin.vue"
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
			},
			{
				path:'/sure_withDraw',
				component : sure_withDraw
			},
			{
				path:'/withDraw_bankcard',
				component : withDraw_bankcard
			},{
				path:'/safe_addBankCard',
				component : safe_addBankCard
			},{
				path : '/safe_bindBankCard',
				component :safe_bindBankCard
			},{
				path : '/safe_certification',
				component : safe_certification
			},{
				path : '/safe_resetCellPhone',
				component :safe_resetCellPhone
			},{
				path :'/safe_resetLoginPassword',
				component : safe_resetLoginPassword
			},{
				path : '/safe_withdrawalPassword',
				component:safe_withdrawalPassword
			},{
				path: '/openDetail_additionlMargin',
				component : openDetail_additionlMargin
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