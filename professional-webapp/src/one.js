import Vue from 'vue'
import router from './router/one.js'
import one from './one.vue'

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#one',
  router,
  template: '<one/>',
  components: { one }
})
