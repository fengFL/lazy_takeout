package org.example.lazzy.mapper;

import org.apache.ibatis.annotations.*;
import org.example.lazzy.pojo.Category;
import org.example.lazzy.pojo.Dish;
import org.example.lazzy.pojo.Setmeal;

import java.util.List;

@Mapper
public interface SetmealMapper {
    @Select("select * from setmeal;")
    List<Setmeal> selectAll();

    /**
     * query setmeal by id
     * @param id
     */
    @Select("select * from setmeal where id = #{id};")
    Setmeal selectById(Long id);

    /**
     * query the count of setmeal by category id
     * @param categoryId
     */
    @Select("select count(*) from setmeal where category_id = #{categoryId};")
    int selectByCategoryId(Long categoryId);

    /**
     * query the info of setmeal by category id and status
     * @param categoryId
     */
    @Select("select * from setmeal where category_id = #{categoryId} and status = #{status};")
    List<Setmeal> selectByCategoryIdAndStatus(Long categoryId, Integer status);

    @Insert("insert into setmeal(id, category_id, name, price, status, code, description, image, create_time, update_time, create_user, update_user) values(#{id}, #{categoryId}, #{name}, #{price}, #{status}, #{code}, #{description}, #{image}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser});")
    void addSetmeal(Setmeal setmeal);

    /**
     *  limit selection
     * @param startIndex
     * @param pageSize
     * @return
     */
    @Select("select * from setmeal ORDER BY sort ASC limit #{startIndex}, #{pageSize};")
    List<Setmeal> selectByPage(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

    /**
     * get the total of records of category
     * @return
     */
    @Select("select count(*) from setmeal;")
    int total();


    /**
     *  paging query with conditions
     *  using dynamic sql in xml.
     */
    List<Setmeal> selectByPageAndConditions(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize, @Param("name") String setmealName);




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
    void updateInfo(Setmeal setmeal);


    /**
     *  remove the setmeal with the specified id
     * @param id
     */
    @Delete("delete from setmeal where id = #{id};")
    void deleteById(Long id);

    /**
     *  remove the setmeal(s) with id(s) using in (x, x, x) dynamic sql in xml
     *
     */
    int deleteByIds(@Param("ids") List<Long> ids);

    /**
     *  update the status by ids
     *  using dynamic sql
     * @param ids
     */
    void updateStatuses(@Param("ids") List<Long> ids, @Param("status") int status);


}
