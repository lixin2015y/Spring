package com.lee.dao;



import com.lee.dto.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

@Mapper
public interface UserRepository {

    @Insert("insert into user(name) values(#{name})")
    Long insert(User user);

    void delete(Long userId);

    List<User> selectAll();

    public List<User> selectBigThan(final Long userId);
}
