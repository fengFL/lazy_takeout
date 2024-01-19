package org.example.lazzy.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.lazzy.pojo.User;

@Mapper
public interface UserMapper {
    /**
     *  select user by phone number
     */
    @Select("select * from user where phone = #{phone};")
    User selectByPhone(String phone);

    /**
     *  add user into the user database
     */
    @Insert("insert into user (id,name, phone, sex, id_number,avatar) values(#{id}, #{name}, #{phone}, #{sex}, #{idNumber}, #{avatar})")
    void add(User user);
}
