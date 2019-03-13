package com.csthink.bbs.service;

import com.csthink.bbs.entity.Message;
import com.csthink.bbs.mapper.MessageMapper;
import com.csthink.bbs.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.Date;
import java.util.List;

public class MessageService {

    /**
     * 分页查询消息
     *
     * @param page 要查询的页数
     * @param size 要查询的数量
     * @return 消息集合
     */
    public List<Message> getMessages(Integer page, Integer size) {
        SqlSession sqlSession = MyBatisUtils.openSession();

        try {
            MessageMapper mapper = sqlSession.getMapper(MessageMapper.class);
            return mapper.getMessages((page - 1) * size, size);
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 根据用户uid分页查询消息
     * @param uid 用户uid
     * @param page 要查询的页数
     * @param size 要查询的数量
     * @return 消息集合
     */
    public List<Message> getMessagesByUid(Integer uid, Integer page, Integer size) {
        SqlSession sqlSession = MyBatisUtils.openSession();

        try {
            MessageMapper mapper = sqlSession.getMapper(MessageMapper.class);
            return mapper.getMessagesByUid(uid,(page - 1) * size, size);
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 统计消息记录总数
     * @return 消息记录总数
     */
    public int countMessages() {
        SqlSession sqlSession = MyBatisUtils.openSession();

        try {
            MessageMapper mapper = sqlSession.getMapper(MessageMapper.class);
            return mapper.countMessages();
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 统计指定用户的消息记录总数
     * @param uid 用户编号 uid
     * @return 用户的消息记录总数
     */
    public int countMessagesByUid(Integer uid) {
        SqlSession sqlSession = MyBatisUtils.openSession();

        try {
            MessageMapper mapper = sqlSession.getMapper(MessageMapper.class);
            return mapper.countMessagesByUid(uid);
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 新增消息
     * @param message 消息信息
     */
    public void addMessage(Message message) {
        Date now = new Date();
        message.setCreateTime(now);
        SqlSession sqlSession = MyBatisUtils.openSession();

        try {
            MessageMapper mapper = sqlSession.getMapper(MessageMapper.class);
            mapper.addMessage(message);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 根据用户uid 删除消息
     * @param uid 用户编号 uid
     */
    public void deleteMessageByUid(Integer uid) {
        SqlSession sqlSession = MyBatisUtils.openSession();

        try {
            MessageMapper mapper = sqlSession.getMapper(MessageMapper.class);
            mapper.deleteMessageByUid(uid);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }



}
