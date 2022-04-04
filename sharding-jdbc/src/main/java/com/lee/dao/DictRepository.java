package com.lee.dao;


import com.lee.dto.Dict;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DictRepository {

    @Insert("insert into t_dict(dict_id, name) values(#{dictId},#{name})")
    Long insert(Dict user);



}
