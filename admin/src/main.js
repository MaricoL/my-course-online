import Vue from 'vue'
import App from './app.vue'
import router from './router'
import axios from 'axios'

import Filter from '@/filter/Filter'

Vue.config.productionTip = false
// 定义一个Vue全局变量，可以在任意组件中使用 this.$ajax 访问
Vue.prototype.$ajax = axios

// 定义全局过滤器
Object.keys(Filter).forEach(key=>{
  Vue.filter(key, Filter[key]);
})

/**
 * axios拦截器
 */

axios.interceptors.request.use(request => {
  Loading.show();
  console.log(`请求：`,request);
  return request;
}, error => {
});

axios.interceptors.response.use(response => {
  console.log(`返回响应：`,response);
  Loading.hide();
  return response;
}, error => {
});

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')

console.log("环境：", process.env.NODE_ENV);
