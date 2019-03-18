package com.csthink.bbs.service;

import com.csthink.bbs.entity.User;
import com.csthink.bbs.mapper.UserMapper;
import com.csthink.bbs.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    /**
     * 分页查询用户
     *
     * @param page 要查询的页数
     * @param size 要查询的数量
     * @return 用户集合
     */
    public List<User> findUsers(Integer page, Integer size) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        List<User> users = null;

        try {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            users = mapper.findUsers((page - 1) * size, size);
        } catch (Exception e) {
            logger.error("分页查询用户失败: page=" + page + ",size=" + size + "\n" + e.toString());
        } finally {
            sqlSession.close();
        }

        return users;
    }

    /**
     * 根据手机号查询用户
     *
     * @param phone 手机号
     * @return 用户集合
     */
    public List<User> findUserByPhone(String phone) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        List<User> users = null;

        try {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            users = mapper.findUserByPhone(phone);
        } catch (Exception e) {
            logger.error("手机号查询用户失败:" + "\n" + e.toString());
        } finally {
            sqlSession.close();
        }

        return users;
    }


    /**
     * 统计用户数量
     *
     * @return total 用户数量
     */
    public int countUsers() {
        SqlSession sqlSession = MyBatisUtils.openSession();
        int total = 0;

        try {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            total = mapper.countUsers();
        } catch (Exception e) {
            logger.error("统计用户数量失败:" + "\n" + e.toString());
        } finally {
            sqlSession.close();
        }

        return total;
    }

    /**
     * 新增用户
     *
     * @param user 用户对象
     * @return true 成功, false 失败
     */
    public boolean addUser(User user) {
        Date now = new Date();
        user.setCreateTime(now);
        user.setUpdateTime(now);
        SqlSession sqlSession = MyBatisUtils.openSession();
        int row = 0;
        boolean flag = false;

        try {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            row = mapper.addUser(user); // 返回受影响行数
            sqlSession.commit();
        } catch (Exception e) {
            logger.error("新增用户失败:" + user + "\n" + e.toString());
        } finally {
            sqlSession.close();
            if (row > 0) {
                flag = true;
            }
        }

        return flag;
    }

    /**
     * 更新用户
     *
     * @param user 用户信息
     * @return true 成功, false 失败
     */
    public boolean updateUser(User user) {
        Date now = new Date();
        user.setUpdateTime(now);
        SqlSession sqlSession = MyBatisUtils.openSession();
        int row = 0;
        boolean flag = false;

        try {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            row = mapper.updateUser(user);
            sqlSession.commit();
        } catch (Exception e) {
            logger.error("更新用户失败:" + user + "\n" + e.toString());
        } finally {
            sqlSession.close();
            if (row > 0) {
                flag = true;
            }
        }

        return flag;
    }

    /**
     * 根据ID删除指定用户
     *
     * @param id 用户ID
     * @return true 成功, false 失败
     */
    public boolean deleteUser(Integer id) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        int row = 0;
        boolean flag = false;

        try {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            row = mapper.deleteUser(id);
            sqlSession.commit();
        } catch (Exception e) {
            logger.error("删除用户失败:" + id + "\n" + e.toString());
        } finally {
            sqlSession.close();
            if (row > 0) {
                flag = true;
            }
        }

        return flag;
    }

}
