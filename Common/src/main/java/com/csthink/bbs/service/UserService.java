package com.csthink.bbs.service;

import com.csthink.bbs.entity.User;
import com.csthink.bbs.mapper.UserMapper;
import com.csthink.bbs.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.Date;
import java.util.List;

public class UserService {

    /**
     * 分页查询用户
     *
     * @param page 要查询的页数
     * @param size 要查询的数量
     * @return 用户集合
     */
    public List<User> findUsers(Integer page, Integer size) {
        SqlSession sqlSession = MyBatisUtils.openSession();

        try {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            return mapper.findUsers((page - 1) * size, size);
        } finally {
            sqlSession.close();
        }
    }

    public List<User> findUserByPhone(String phone) {
        SqlSession sqlSession = MyBatisUtils.openSession();

        try {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            return mapper.findUserByPhone(phone);
        } finally {
            sqlSession.close();
        }
    }


    /**
     * 统计用户数量
     * @return
     */
    public int countUsers() {
        SqlSession sqlSession = MyBatisUtils.openSession();

        try {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            return mapper.countUsers();
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 新增用户
     * @param user 用户信息
     */
    public void addUser(User user) {
        Date now = new Date();
        user.setCreateTime(now);
        user.setUpdateTime(now);
        SqlSession sqlSession = MyBatisUtils.openSession();

        try {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            mapper.addUser(user);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 更新用户
     * @param user 用户信息
     * @return
     */
    public void updateUser(User user) {
        Date now = new Date();
        user.setUpdateTime(now);
        SqlSession sqlSession = MyBatisUtils.openSession();

        try {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            mapper.updateUser(user);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 根据ID删除指定用户
     * @param id 用户ID
     */
    public void deleteUser(Integer id) {
        SqlSession sqlSession = MyBatisUtils.openSession();

        try {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            mapper.deleteUser(id);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

}
