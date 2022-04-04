import axios from 'axios'
import Element from 'element-ui'  // 导入element弹窗需要
import router from './router'  // 后续会进行页面跳转
import store from './store'


axios.defaults.baseURL = "http://localhost:8081"
// 会作为前缀添加到url之前

// 前置拦截器，可以添加请求头信息
axios.interceptors.request.use(config => {
  return config
})

// 后置拦截器，出现异常后，弹窗拦截
axios.interceptors.response.use(response => {
    let res = response.data;

    console.log("Response Interceptors")
    console.log(res)  // 输入信息

    // 对内容进行解析，分支处理
    if (res.code === 200) {
      return response
    } else { // res.code 不等于200 说明存在异常
        // 弹出弹窗，给予用户提示
      Element.Message.error('Wrong password.', {duration: 3 * 1000}) // 添加超时时间3秒
      return Promise.reject(response.data.msg)  // 阻止返回
    }
  },
  error => {
    console.log(error)
    if(error.response.data) {
      error.message = error.response.data.msg
    }

    if(error.response.status === 401) {  // 出现ShiroException，跳转到登录页面
      store.commit("REMOVE_INFO")  // 清空全局参数
      router.push("/login")
    }

    Element.Message.error(error.message, {duration: 3 * 1000})
    return Promise.reject(error)  // 阻止返回
  }
)