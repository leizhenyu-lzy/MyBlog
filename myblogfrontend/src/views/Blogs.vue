<template>
  <div class="m-contaner">
    <Header></Header>

    <div class="block">
      <!--时间线-->
      <el-timeline>
        <!--这里有一个warning，需要显示定义一个key-->
        <el-timeline-item :timestamp="blog.created" placement="top" v-for="blog in blogs">
        <!--<el-timeline-item :timestamp="blog.created" placement="top">-->
        <el-card>
            <h4>
              <!--添加一个路由，用于点击响应，跳转到博客详情页-->
              <router-link :to="{name: 'BlogDetail', params: {blogId: blog.blogId}}">
                {{blog.title}}
              </router-link>
            </h4>
            <p>{{blog.description}}</p>
          </el-card>
        </el-timeline-item>

      </el-timeline>

      <!--分页-->
      <el-pagination class="m-page"
                     background
                     layout="prev, pager, next"
                     :current-page="currentPage"
                     :page-size="pageSize"
                     :total="total"
                     @current-change=page> <!-- 增加页面跳转 -->
      </el-pagination>

    </div>

  </div>
</template>

<script>
  import Header from "../components/Header";  // 引入Header组件

  export default {
    name: "Blogs.vue",
    components: {Header},  // 备注要使用Header组件
    data() {
      return {
        blogs: {},
        currentPage: 1,  // 当前页，默认为第一页
        total: 0,  // 总共的页数
        pageSize: 5  // 一页的博客数，默认5条
      }
    },

    methods: { // 数据展示需要进行请求
      page(currentPage) {
        const _this = this
        _this.$axios.get("/blogs?currentPage=" + currentPage).then(res => {  // 添加当前页码
          console.log(res)

          _this.blogs = res.data.data.records  // 注意：data中的records才是博客信息
          _this.currentPage = res.data.data.current
          _this.total = res.data.data.total
          _this.pageSize = res.data.data.size
        })
      }
    },
    // 在页面渲染时进行调用
    created() {
      this.page(1)
    }
  }
</script>

<style scoped>

  .m-page {
    margin: 0 auto;
    text-align: center;
  }

</style>