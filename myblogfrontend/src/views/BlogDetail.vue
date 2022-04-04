<template>
  <div class="front">
    <Header></Header>

    <div class="my-blog">
      <h2> {{ blog.title }}</h2>

      <!--添加一个编辑按钮，只有博客作者用户能够看到-->
      <el-link icon="el-icon-edit" v-if="ownBlog">
        <!--如果检查到是自己创建的博客，则显示一个可以编辑的链接-->
        <router-link :to="{name: 'BlogEditor', params: {blogId: blog.blogId}}" >
          Edit this Blog
        </router-link>
      </el-link>

      <el-divider></el-divider>  <!--分割线-->
      <!--添加一个markdown-body-->
      <div class="markdown-body" v-html="blog.blogContent"></div>
    </div>

  </div>
</template>

<script>
  import 'github-markdown-css/github-markdown-light.css'  // 导入css样式
  import Header from "../components/Header";  // 导入Header组件

  export default {
    name: "BlogDetail.vue",
    components: {Header},
    data() {
      return {
        imgsrc: require('../../FrontendPics/BackGround.png'),
        blog: {
          blogId: "",
          title: "",
          description: "",
          blogContent: ""
        },
        ownBlog: false  // 默认隐藏
      }
    },
    created() {
      const blogId = this.$route.params.blogId
      console.log(blogId)
      const _this = this

      this.$axios.get('/blog/' + blogId).then(res => {
        const blog = res.data.data
        _this.blog.blogId = blog.blogId
        _this.blog.title = blog.title
        _this.blog.description = blog.description

        // 将内容进行渲染
        var MardownIt = require("markdown-it")
        var md = new MardownIt()
        var result = md.render(blog.blogContent)

        _this.blog.blogContent = result  // 将渲染结果返回
        _this.ownBlog = (blog.userId === _this.$store.getters.getUser.userId)

      })
    }
  }
</script>

<style scoped>
  .my-blog {
    /*背景阴影*/
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    width: 100%;
    min-height: 700px;
    padding: 20px 15px;
  }
</style>


