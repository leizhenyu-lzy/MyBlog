<template>
  <div>
    <div class="front">
      <el-container>
        <el-header>
          <img class="m-logo" src="../../FrontendPics/github.png" alt="">
        </el-header>
        <el-main>
          <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
            <el-form-item label="Username" prop="username">
              <el-input v-model="ruleForm.username"></el-input>
            </el-form-item>
            <el-form-item label="Password" prop="password">
              <!--password输入框，不显示具体密码-->
              <el-input type="password" v-model="ruleForm.password"></el-input>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" @click="submitForm('ruleForm')">Login</el-button>
              <el-button @click="resetForm('ruleForm')">Reset</el-button>
            </el-form-item>
          </el-form>

        </el-main>
      </el-container>

      <el-calendar v-model="value">
      </el-calendar>

    </div>

    <div class="background">
      <img :src="imgSrc" class="imgSrc"/>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      //背景

      // 日历
      value: new Date(),
      // 双向数据绑定
      ruleForm: {
        // 可以在这里给用户名和密码设置默认值
        username: '',
        password: ''
        // username: 'root',
        // password: 'root'
      },
      rules: {
        username: [
          { required: true, message: 'Please enter your username.', trigger: 'blur' },
          { min: 3, max: 16, message: 'The username\'s length should between 3 and 16.', trigger: 'blur' }
        ],
        password: [
          { required: true, message: 'Please enter your password.', trigger: 'change' },
          { min: 3, max: 16, message: 'The password\'s length should between 3 and 16.', trigger: 'blur' }
        ]
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const _this = this  // 预存
          // alert('Your username and password are submitted !');
          // post请求
          //this.$axios.post('http://localhost:8081/login',this.ruleForm)
          this.$axios.post('/login',this.ruleForm)  // "http://localhost:8081"为前缀信息，放在axios.js中处理(axios.defaults.baseURL)
              .then(res=>
              {
                console.log(res.headers)//输出请求头数据（jwt在请求头中）
                console.log(res)//输出全部数据
                // 有些时候需要让多个组件共享数据，需要在store/index.js中进行相应配置
                const jwt = res.headers['authorization']
                const userInfo = res.data.data

                // 把数据共享
                _this.$store.commit("SET_TOKEN", jwt)
                _this.$store.commit("SET_USERINFO", userInfo)

                // 获取
                console.log(_this.$store.getters.getUser)

                // 使用router跳转到列表页面
                _this.$router.push("/blogs")
              }
              )// vue进行双向数据绑定将ruleForm数据拿来即可
        } else {
          console.log('error submit !');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
}
</script>

<style scoped>
.el-header, .el-footer {
  background-color: #B3C0D1;
  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-aside {
  background-color: #D3DCE6;
  color: #333;
  text-align: center;
  line-height: 200px;
}

.el-main {
  /*background-color: #E9EEF3;*/
  color: #333;
  text-align: center;
  line-height: 160px;
}

body > .el-container {
  margin-bottom: 40px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}

.m-logo {
  height: 90%;
  margin-top: 2px;
}

.demo-ruleForm {
  max-width: 500px;
  margin: 0 auto;
}

.el-calendar{
  width: 50%;
  height: 20%;
  margin: 0 auto;
}

</style>