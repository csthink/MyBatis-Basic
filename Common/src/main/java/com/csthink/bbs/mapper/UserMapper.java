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
    @Select("SELECT * FROM user ORDER BY create_time DESC LIMIT #{skip}, #{size}")
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
     *
     * @return 用户数量
     */
    @Select("SELECT count(*) FROM `user`")
    int countUsers();

    /**
     * 根据手机号查询用户
     *
     * @param phone 手机号
     * @return 用户集合
     */
    @Select("SELECT id FROM user WHERE phone = #{phone}")
    List<User> findUserByPhone(@Param("phone") String phone);

    /**
     * 新增用户
     *
     * @param user 用户对象
     * @return 受影响行数
     */
    @Insert("insert into user(username, password, phone) value (#{user.username}, #{user.password}, #{user.phone})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int addUser(@Param("user") User user);

    /**
     * 更新用户
     *
     * @param user 用户信息
     * @return 受影响行数
     */
    @Update("<script>UPDATE user" +
            "<set>" +
            "<if test=\"username != null\">username = #{user.username},</if>" +
            "<if test=\"password != null\">password = #{user.password},</if>" +
            "<if test=\"realName != null\">real_name = #{user.realName},</if>" +
            "<if test=\"sex != null\">sex = #{user.sex},</if>" +
            "<if test=\"birthday != null\">birthday = #{user.birthday},</if>" +
            "<if test=\"address != null\">address = #{user.address},</if>" +
            "<if test=\"photo != null\">photo = #{user.photo},</if>" +
            "<if test=\"interest != null\">interest = #{user.interest},</if>" +
            "</set>" +
            "</script>"
    )
    int updateUser(@Param("user") User user);

    /**
     * 根据ID删除指定用户
     *
     * @param id 用户ID
     * @return 受影响行数
     */
    @Delete("DELETE FROM user WHERE id = #{id}")
    int deleteUser(@Param("id") Integer id);
}
