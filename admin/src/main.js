import Vue from 'vue'
import App from './app.vue'
import router from './router'
import axios from 'axios'

Vue.config.productionTip = false
// 定义一个Vue全局变量，可以在任意组件中使用 this.$ajax 访问
Vue.prototype.$ajax = axios

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
