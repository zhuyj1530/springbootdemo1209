package com.qf.yongji.springbootdemo1209.service.impl;

import com.qf.yongji.springbootdemo1209.dao.UserDao;
import com.qf.yongji.springbootdemo1209.pojo.User;
import com.qf.yongji.springbootdemo1209.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceimpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public User findByUserName(String username) {
        return userDao.findByUserName(username);
    }

    @Override
    public User findById(int id) {
        return userDao.findById(id);
    }
}
