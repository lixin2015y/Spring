package com.lee.dao;



import com.lee.dto.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


import java.util.List;

@Mapper
public interface UserRepository {

    @Insert("insert into user(user_id, name) values(#{userId},#{name})")
    Long insert(User user);

    @Select("select * from user where user_id > #{userIdBegin} and user_id < #{userIdEnd}")
    List<User> selectUser(@Param("userIdBegin") Long userIdBegin, @Param("userIdEnd") Long userIdEnd);

    @Select("select * from user where user_id > #{userIdBegin} and user_id < #{userIdEnd} and name = #{name}")
    List<User> selectUserByIdAndName(@Param("userIdBegin") Long userIdBegin, @Param("userIdEnd") Long userIdEnd, @Param("name") String name);


}
