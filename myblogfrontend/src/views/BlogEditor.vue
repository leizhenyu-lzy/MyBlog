<template>
  <div>
    <!--头部组件-->
    <Header></Header>

    <div class="m-content">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="Title" prop="title">
          <el-input v-model="ruleForm.title"></el-input>
        </el-form-item>

        <el-form-item label="Description" prop="description">
          <el-input type="textarea" v-model="ruleForm.description"></el-input>
        </el-form-item>

        <el-form-item label="BlogContent" prop="blogContent">
          <!--mavon-editor绑定一个model-->
          <mavon-editor v-model="ruleForm.blogContent"></mavon-editor>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">Create Blog</el-button>
          <el-button @click="resetForm('ruleForm')">Reset All</el-button>
        </el-form-item>
      </el-form>

    </div>

  </div>
</template>

<script>
  import Header from "../components/Header";  // 引入头部
  export default {
    name: "BlogEditor.vue",
    components: {Header},
    data() {
      return {
        // 可以设置默认值
        ruleForm: {
          blogId: '',
          title: '',
          description: '',
          blogContent: ''
        },
        rules: {
          title: [
            { required: true, message: 'Please enter the blog title.', trigger: 'blur' },
            { min: 3, max: 25, message: 'The length of blog title should between 3 and 25.', trigger: 'blur' }
          ],
          description: [
            { required: true, message: 'Please enter the blog description.', trigger: 'blur' }
          ],
          content: [
            { required: true, message: 'Please enter the blog content.', trigger: 'blur' }
          ]
        }
      };
    },
    methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            const _this = this
            // post请求
            this.$axios.post('/blog/editor', this.ruleForm, {
              // 添加头部信息
              headers: {
                "Authorization": localStorage.getItem("token")
              }
            }).then(res => {
              console.log(res)
              // 消息弹窗，参考element官网
              _this.$alert('Successful Operation', 'Editor Prompt', {
                confirmButtonText: 'Confirm',
                callback: action => {
                  _this.$router.push("/blogs")  // 页面跳转
                }
              });
            })
          } else {
            console.log('Error Submit.');
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      }
    },
    // 内容回显
    created() {
      // blogId已经在路由中定义了
      const blogId_ = this.$route.params.blogId
      console.log(blogId_)
      const _this = this
      // 发起get请求
      if(blogId_) {
        this.$axios.get('/blog/' + blogId_).then(res => {
          const blog = res.data.data
          _this.ruleForm.blogId = blog.blogId
          _this.ruleForm.title = blog.title
          _this.ruleForm.description = blog.description
          _this.ruleForm.blogContent = blog.blogContent
        })
      }
    }
  }
</script>

<style scoped>
  /*设置居中*/
  .m-content {
    text-align: center;
  }
</style>