package com.lzy.service.impl;

import com.lzy.entity.Blogs;
import com.lzy.mapper.BlogsMapper;
import com.lzy.service.BlogsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liuzhiyu
 * @since 2022-03-31
 */
@Service
public class BlogsServiceImpl extends ServiceImpl<BlogsMapper, Blogs> implements BlogsService {

}
