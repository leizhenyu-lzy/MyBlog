import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Element from 'element-ui'
import mavonEditor from 'mavon-editor'  // markdown编辑器
import axios from 'axios'

import "./permission"
import "./axios"  // 导入axios.js
import "element-ui/lib/theme-chalk/index.css"
import 'mavon-editor/dist/css/index.css'

Vue.use(Element)
Vue.use(mavonEditor)

Vue.config.productionTip = false
Vue.prototype.$axios = axios

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')

// pic : ../../FrontendPics
