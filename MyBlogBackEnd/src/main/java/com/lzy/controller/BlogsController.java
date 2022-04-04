package com.lzy.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzy.common.lang.Result;
import com.lzy.entity.Blogs;
import com.lzy.service.BlogsService;
import com.lzy.util.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liuzhiyu
 * @since 2022-03-31
 */
@RestController
// @RequestMapping("/blogs")
public class BlogsController
{
    @Autowired
    BlogsService blogsService;

    // @GetMapping("/{blog_id}")
    // public Object index(@PathVariable("blog_id") Long blog_id)
    // {
    //     return blogsService.getById(blog_id);
    // }


    // 列表Controller
    @GetMapping("/blogs")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage) // 默认第一页
    {
        Page page = new Page(currentPage, 5);  // 一页默认的记录数
        IPage pageData = blogsService.page(page, new QueryWrapper<Blogs>().orderByDesc("created"));

        return Result.success(pageData);
    }

    // 详情Controller
    @GetMapping("/blog/{blogId}")
    public Result detail(@PathVariable(name = "blogId") Long blogId)
    {
        Blogs blogs = blogsService.getById(blogId);
        // 使用断言
        Assert.notNull(blogs, "Detail Prompt : This blog does not exist.");

        return Result.success(blogs);
    }

    // 编辑器Controller
    @RequiresAuthentication  // 需要登录才能编辑
    @PostMapping("/blog/editor")
    public Result editor(@Validated @RequestBody Blogs blogs)  // 校验传入的参数是否满足要求
    {
        // Assert.isTrue(false, "公开版不能任意编辑！");
        Blogs temp = null;
        if(blogs.getUserId() != null)  // 原来的数据库中有该博客，修改博客
        {
            temp = blogsService.getById(blogs.getUserId());
            // 只能编辑自己的文章
            System.out.println(ShiroUtils.getProfile().getUserId());
            Assert.isTrue(temp.getUserId().equals(ShiroUtils.getProfile().getUserId())
                    , "Editor Prompt : You don't have the authority to edit this blog.");
        }
        else  // 原来的数据库中没有该博客，新建博客
        {
            temp = new Blogs();
            temp.setUserId(ShiroUtils.getProfile().getUserId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }

        BeanUtil.copyProperties(blogs, temp, "id", "userId", "created", "status");  // 将旧博客的信息拷贝到新博客
        blogsService.saveOrUpdate(temp);

        return Result.success(200,"The operation is saved",null);
    }

}
