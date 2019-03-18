package com.csthink.bbs.mapper;

import com.csthink.bbs.entity.Message;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface MessageMapper {

    /**
     * 分页查询消息
     *
     * @param skip 跳过的记录数，也就是从哪条开始查询
     * @param size 要查询的数量
     * @return 消息集合
     */
    @Select("SELECT * FROM message ORDER BY create_time DESC LIMIT #{skip}, #{size}")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "uid", property = "uid"),
            @Result(column = "username", property = "username"),
            @Result(column = "title", property = "title"),
            @Result(column = "content", property = "content"),
            @Result(column = "create_time", property = "createTime")
    })
    List<Message> getMessages(@Param("skip") Integer skip, @Param("size") Integer size);

    /**
     * 根据用户uid分页查询消息
     *
     * @param uid  用户uid
     * @param skip 跳过的记录数，也就是从哪条开始查询
     * @param size 要查询的数量
     * @return 消息对象
     */
    @Select("SELECT id, uid, username, title, content, create_time createTime FROM message " +
            "WHERE uid = #{uid} ORDER BY create_time DESC LIMIT #{skip}, #{size}\"")
    List<Message> getMessagesByUid(@Param("uid") Integer uid, @Param("skip") Integer skip, @Param("size") Integer size);

    /**
     * 统计消息记录总数
     *
     * @return 消息记录总数
     */
    @Select("SELECT count(*) FROM message")
    int countMessages();

    /**
     * 统计指定用户的消息记录总数
     *
     * @param uid 用户编号 uid
     * @return 用户的消息记录总数
     */
    @Select("SELECT count(*) FROM message WHERE uid = #{uid}")
    int countMessagesByUid(Integer uid);

    /**
     * 新增消息
     *
     * @param message 消息对象
     * @return 受影响行数
     */
    @Insert("INSERT message(uid, username, title, content, create_time) " +
            "VALUES(#{message.uid}, #{message.username}, #{message.title}, #{message.content}, #{message.createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int addMessage(@Param("message") Message message);

    /**
     * 根据用户uid 删除消息
     *
     * @param uid 用户编号 uid
     * @return 受影响行数
     */
    @Delete("DELETE FROM message WHERE uid = #{uid}")
    int deleteMessageByUid(@Param("uid") Integer uid);
}
