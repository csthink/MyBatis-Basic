package com.csthink.bbs.mapper;

import com.csthink.bbs.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {

    /**
     * 分页查询用户
     *
     * @param skip 跳过的记录数，也就是从哪条开始查询
     * @param size 要查询的数量
     * @return 用户集合
     */
    @Select("SELECT * FROM `user` ORDER BY `create_time` DESC  LIMIT #{skip}, #{size}")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "username", property = "username"),
            @Result(column = "password", property = "password"),
            @Result(column = "real_name", property = "realName"),
            @Result(column = "birthday", property = "birthday"),
            @Result(column = "phone", property = "phone"),
            @Result(column = "address", property = "address")
    })
    List<User> findUsers(@Param("skip") Integer skip, @Param("size") Integer size);

    /**
     * 统计用户数量
     * @return 用户数量
     */
    @Select("SELECT count(*) FROM `user`")
    int countUsers();


    /**
     * 新增用户
     * @param user 用户信息
     */
    @Insert("INSERT `user`(`username`, `password`, `real_name`, `birthday`, `phone`, `address`)" +
            "VALUES(#{user.username}, #{user.password}, #{user.realName} #{user.birthday} #{user.phone), #{user.address}")
    void addUser(User user);

    /**
     * 更新用户
     * @param user 用户信息
     */
    @Update("<script>UPDATE `user`" +
            "<set>" +
            "<if test=\"username != null\">username = #{user.username},</if>" +
            "<if test=\"password != null\">password = #{user.password},</if>" +
            "<if test=\"realName != null\">real_name = #{user.realName},</if>" +
            "<if test=\"birthday != null\">birthday = #{user.birthday},</if>" +
            "<if test=\"phone != null\">phone = #{user.phone},</if>" +
            "<if test=\"address != null\">address = #{user.address},</if>" +
            "</set>" +
            "</script>"
    )
    void updateUser(User user);

    /**
     * 根据ID删除指定用户
     * @param id 用户ID
     */
    @Delete("DELETE FROM `user` WHERE id = #{id}")
    void deleteUser(Integer id);
}
