package org.example.lazzy.mapper;

import org.apache.ibatis.annotations.*;
import org.example.lazzy.pojo.Category;
import org.example.lazzy.pojo.Dish;

import java.util.List;

@Mapper
public interface DishMapper {
    @Select("select * from dish;")
    List<Dish> selectAll();

    /**
     * query dish by id
     * @param id
     */
    @Select("select * from dish where id = #{id};")
    Dish selectById(Long id);

    /**
     * query the number of dishes by category id
     * @param categoryId
     */
    @Select("select count(*) from dish where category_id = #{categoryId};")
    int selectByCategoryId(Long categoryId);

    @Insert("insert into dish(id, name, category_id, price, code, image, description, create_time, update_time, create_user, update_user) values(#{id}, #{name}, #{categoryId}, #{price}, #{code}, #{image}, #{description}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser});")
    void addDish(Dish dish);

    /**
     *  limit selection with conditions
     * @param startIndex
     * @param pageSize
     * @param name
     * @return
     */

    List<Dish> selectByPageAndConditions(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize, @Param("name") String name);

    /**
     * get the total of records of category
     * @return
     */
    @Select("select count(*) from dish;")
    int total();

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
    void updateInfo(Dish dish);


    /**
     *  remove the dish with the specified id
     * @param id
     */
    @Delete("delete from dish where id = #{id};")
    void deleteById(Long id);

    /**
     * delete the dishes in batches
     * sql is written in xml
     */
    void deleteByIds(@Param("ids") Long[] ids);


    /**
     *  single operation
     *  update the status by dish id
     */
    @Update("update dish set status = #{status} where id = #{id}")
    void updateStatusById(@Param("status") int status, @Param("id") Long id);

    /**
     *  batch operation
     *  update the status by dish ids
     *  using dynamic sql in xml
     */
    void updateStatusByIds(@Param("status") int status, @Param("ids") Long[] ids);


    /**
     *  get dish list by category id and status = 1
     */
    @Select("select * from dish where category_id = #{categoryId} and status = 1;")
    List<Dish> getListByCategoryId(Long categoryId);




}
