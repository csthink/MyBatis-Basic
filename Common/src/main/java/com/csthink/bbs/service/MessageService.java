package com.csthink.bbs.service;

import com.csthink.bbs.entity.Message;
import com.csthink.bbs.mapper.MessageMapper;
import com.csthink.bbs.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

public class MessageService {

    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

    /**
     * 分页查询消息
     *
     * @param page 要查询的页数
     * @param size 要查询的数量
     * @return 消息集合
     */
    public List<Message> getMessages(Integer page, Integer size) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        List<Message> messages = null;

        try {
            MessageMapper mapper = sqlSession.getMapper(MessageMapper.class);
            messages = mapper.getMessages((page - 1) * size, size);
        } catch (Exception e) {
            logger.error("分页查询用户失败: page=" + page + ",size=" + size + "\n" + e.toString());
        } finally {
            sqlSession.close();
        }

        return messages;
    }

    /**
     * 根据用户uid分页查询消息
     *
     * @param uid  用户uid
     * @param page 要查询的页数
     * @param size 要查询的数量
     * @return 消息集合
     */
    public List<Message> getMessagesByUid(Integer uid, Integer page, Integer size) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        List<Message> messages = null;

        try {
            MessageMapper mapper = sqlSession.getMapper(MessageMapper.class);
            messages = mapper.getMessagesByUid(uid, (page - 1) * size, size);
        } catch (Exception e) {
            logger.error("根据用户uid分页查询消息失败: uid=" + uid + ",page=" + page + ",size=" + size + "\n" + e.toString());
        } finally {
            sqlSession.close();
        }

        return messages;
    }

    /**
     * 统计消息记录总数
     *
     * @return 消息记录总数
     */
    public int countMessages() {
        SqlSession sqlSession = MyBatisUtils.openSession();
        int total = 0;

        try {
            MessageMapper mapper = sqlSession.getMapper(MessageMapper.class);
            total = mapper.countMessages();
        } catch (Exception e) {
            logger.error("统计消息记录总数失败:" + "\n" + e.toString());
        } finally {
            sqlSession.close();
        }

        return total;
    }

    /**
     * 统计指定用户的消息记录总数
     *
     * @param uid 用户编号 uid
     * @return 用户的消息记录总数
     */
    public int countMessagesByUid(Integer uid) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        int total = 0;

        try {
            MessageMapper mapper = sqlSession.getMapper(MessageMapper.class);
            total = mapper.countMessagesByUid(uid);
        } catch (Exception e) {
            logger.error("统计指定用户的消息记录总数失败: uid=" + uid + "\n" + e.toString());
        } finally {
            sqlSession.close();
        }

        return total;
    }

    /**
     * 新增消息
     *
     * @param message 消息对象
     * @return true 成功, false 失败
     */
    public boolean addMessage(Message message) {
        Date now = new Date();
        message.setCreateTime(now);
        SqlSession sqlSession = MyBatisUtils.openSession();
        boolean flag = false;
        int row = 0;

        try {
            MessageMapper mapper = sqlSession.getMapper(MessageMapper.class);
            row = mapper.addMessage(message);
            sqlSession.commit();
        } catch (Exception e) {
            logger.error("新增消息失败: " + message + "\n" + e.toString());
        } finally {
            sqlSession.close();
            if (row > 0) {
                flag = true;
            }
        }

        return flag;
    }

    /**
     * 根据用户uid 删除消息
     *
     * @param uid 用户编号 uid
     * @return true 成功, false 失败
     */
    public boolean deleteMessageByUid(Integer uid) {
        SqlSession sqlSession = MyBatisUtils.openSession();
        int row = 0;
        boolean flag = false;

        try {
            MessageMapper mapper = sqlSession.getMapper(MessageMapper.class);
            row = mapper.deleteMessageByUid(uid);
            sqlSession.commit();
        } catch (Exception e) {
            logger.error("根据用户uid 删除消息失败: " + uid + "\n" + e.toString());
        } finally {
            sqlSession.close();
            if (row > 0) {
                flag = true;
            }
        }

        return flag;
    }


}
