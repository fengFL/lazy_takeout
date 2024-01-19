package org.example.lazzy.mapper;

import org.apache.ibatis.annotations.*;
import org.example.lazzy.pojo.Category;
import org.example.lazzy.pojo.Employee;

import java.util.List;

@Mapper
public interface CategoryMapper {
    @Select("select * from category;")
    List<Category> selectAll();

    /**
     * query category by id
     * @param id
     */
    @Select("select * from category where id = #{id};")
    Category selectById(Long id);

    /**
     * get categories by type
     * using dynamic sql
     * @param type
     */
    List<Category> selectByType(Integer type);



    @Insert("insert into category(id, type, name, sort, create_time, update_time, create_user, update_user) values(#{id}, #{type}, #{name}, #{sort}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser});")
    void addCategory(Category category);

    /**
     *  limit selection
     * @param startIndex
     * @param pageSize
     * @return
     */
    @Select("select * from category ORDER BY sort ASC limit #{startIndex}, #{pageSize};")
    List<Category> selectByPage(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

    /**
     * get the total of records of category
     * @return
     */
    @Select("select count(*) from category;")
    int total();


    /**
     *  limit selection with conditions
     *  use the sql in xml
     * @param startIndex
     * @param pageSize
     * @return
     */



    /**
     * get the total of records by conditions
     * use xml to query
     * @return
     */
    int totalByConditions(String name);

    /**
     *  update the info of a specified category
     *  This is a common method used to update an employee information
     *
     *
     * @param
     */
    void updateInfo(Category category);


    /**
     *  remove the category with the specified id
     * @param id
     */
    @Delete("delete from category where id = #{id};")
    void deleteById(Long id);



}
