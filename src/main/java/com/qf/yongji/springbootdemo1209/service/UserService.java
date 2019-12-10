package com.qf.yongji.springbootdemo1209.service;

import com.qf.yongji.springbootdemo1209.pojo.User;



public interface UserService {
    User findByUserName(String username);
    User findById(int id);
}
