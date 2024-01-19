package org.example.lazzy.mapper;

import org.apache.ibatis.annotations.*;
import org.example.lazzy.pojo.Employee;
import org.example.lazzy.pojo.PageBean;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    @Select("select * from employee where username = #{username};")
    Employee selectByUsername(String username);

    /**
     * query employee by id
     * @param id
     */
    @Select("select * from employee where id = #{id};")
    Employee selectById(Long id);

    @Insert("insert into employee(id, name, username, password, sex, id_number, phone, create_time, update_time, create_user, update_user) values(#{id}, #{name}, #{username}, #{password}, #{sex}, #{idNumber}, #{phone}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser});")
    void addEmployee(Employee employee);

    /**
     *  limit selection
     * @param startIndex
     * @param pageSize
     * @return
     */
    @Select("select * from employee limit #{startIndex}, #{pageSize};")
    List<Employee> selectByPage(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

    /**
     * get the total of records
     * @return
     */
    @Select("select count(*) from employee;")
    int total();


    /**
     *  limit selection with conditions
     *  use the sql in xml
     * @param startIndex
     * @param pageSize
     * @return
     */

    // used when multiple conditions are required
//    List<Employee> selectByPageAndConditions(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize, @Param("employee") Employee employee);

    List<Employee> selectByPageAndConditions(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize, @Param("name") String name);


    /**
     * get the total of records by conditions
     * use xml to query
     * @return
     */
//    int totalByConditions(Employee employee); // used when multiple conditions
    int totalByConditions(String name);

    /**
     *  update the info of a specified employee
     *  This is a common method used to update an employee information
     *
     *
     * @param
     */
    void updateInfo(Employee employee);




}
