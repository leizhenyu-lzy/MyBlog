<template>
  <div class="m-content">
    <h3>Welcome To MyBlog</h3>
    <div class="block">
      <el-avatar :size="50" :src="user.avatar"></el-avatar>  <!--用户头像-->
      <div>{{ user.username }}</div>  <!--居中显示-->
    </div>

    <div class="m-action">
      <!--el-link中的type可以选则：primary、success、warning、danger、info-->

      <span v-show="hasLogin"><el-link type="warning" href="/blogs">HomePage</el-link></span>

      <span v-show="hasLogin"><el-divider direction="vertical"></el-divider></span><!--分割线-->

      <span v-show="hasLogin"><el-link type="success" href="/blog/add">Write a Blog</el-link></span>
      <!--<span v-show="!hasLogin"><el-link type="success" href="/login">Write Blogs After Login</el-link></span>-->

      <span v-show="hasLogin"><el-divider direction="vertical"></el-divider></span><!--分割线-->

      <!--可能是退出也可能是登录，定义参数-->
      <span v-show="!hasLogin"><el-link type="primary" href="/login">Login</el-link></span>
      <span v-show="hasLogin"><el-link type="danger" @click="logout">Logout</el-link></span> <!--退出需要清除用户信息-->

    </div>
  </div>
</template>

<script>
  export default {
    name: "Header",
    data() {
      return {
        user: {  // 用户信息
          username: 'Please login first.',
          avatar: 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'
        },
        hasLogin: false
      }
    },
    methods: {
      // 退出需要先登录，所以需要将token信息传递
      logout() {
        const _this = this
        _this.$axios.get("/logout", {
          headers: {  // 添加token信息
            "Authorization": localStorage.getItem("token")  // 通过localstorage获取token
          }
        }).then(res => {
          _this.$store.commit("REMOVE_INFO")  // 调用REMOVE_INFO方法清除信息
          _this.$router.push("/login")  // 退出后切换到登录页面
        })
      }
    },
    created() {
      // 信息回显
      if(this.$store.getters.getUser.username) {
        this.user.username = this.$store.getters.getUser.username
        this.user.avatar = this.$store.getters.getUser.avatar
        this.hasLogin = true  // 表名登录成功
      }
    }
  }
</script>

<style scoped>

  .m-content {
    max-width: 960px;
    margin: 0 auto;
    text-align: center;
  }
  .m-action {
    margin: 10px 0;
  }

</style>