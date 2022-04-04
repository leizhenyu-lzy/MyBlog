package com.lzy.service.impl;

import com.lzy.entity.Users;
import com.lzy.mapper.UsersMapper;
import com.lzy.service.UsersService;
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
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

}
