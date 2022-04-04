import Vue from 'vue'
import VueRouter from 'vue-router'
// 将.vue文件添加进来
import Login from '../views/Login.vue'
import Blogs from '../views/Blogs.vue'
import BlogEditor from '../views/BlogEditor.vue'
import BlogDetail from '../views/BlogDetail.vue'

Vue.use(VueRouter)

const routes = [
  // 登录博客页面
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/',
    name: 'Index',
    redirect: {name: "Blogs"}  // 首页重定向，name标签的内容需要加双引号
  },
  {
    path: '/blogs',
    name: 'Blogs',
    component: Blogs
  },
    // 添加博客页面
  {
    path: '/blog/add',
    name: 'BlogAdd',
    component: BlogEditor,
    meta: {
      requireAuth: true
    }
  },
  {
    path: '/blog/:blogId',
    name: 'BlogDetail',
    component: BlogDetail
  },
    // 修改博客页面
  {
    path: '/blog/:blogId/editor',
    name: 'BlogEditor',
    component: BlogEditor,
    meta: {
      requireAuth: true
    }
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
