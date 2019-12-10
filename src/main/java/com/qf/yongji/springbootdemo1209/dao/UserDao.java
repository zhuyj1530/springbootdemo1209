package com.qf.yongji.springbootdemo1209.dao;

import com.qf.yongji.springbootdemo1209.pojo.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    User findByUserName(String username);
    User findById(int id);
}
