import Vue from 'vue'
import Router from 'vue-router'
import test from '../pages/download.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      component: test
    },
  ]
})
