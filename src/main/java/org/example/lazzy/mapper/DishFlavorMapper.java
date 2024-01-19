package org.example.lazzy.mapper;

import org.apache.ibatis.annotations.*;
import org.example.lazzy.pojo.Dish;
import org.example.lazzy.pojo.DishFlavor;

import java.util.List;

@Mapper
public interface DishFlavorMapper {
    @Select("select * from dish_flavor;")
    List<DishFlavor> selectAll();

    /**
     * query DishFlavor by id
     * @param id
     */
    @Select("select * from dish_flavor where id = #{id};")
    DishFlavor selectById(Long id);

    /**
     * query the number of dish flavors by category id
     * @param categoryId
     */
    @Select("select count(*) from dish_flavor where category_id = #{categoryId};")
    int selectByCategoryId(Long categoryId);

    /**
     * query the list of dish flavors by dish id
     * @param dishId
     */
    @Select("select * from dish_flavor where dish_id = #{dishId};")
    List<DishFlavor> selectByDishId(Long dishId);

    @Insert("insert into dish_flavor(id, dish_id, name, value, create_time, update_time, create_user, update_user) values(#{id}, #{dishId}, #{name}, #{value}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser});")
    void addDishFlavor(DishFlavor dishFlavor);

    /**
     *  limit selection
     * @param startIndex
     * @param pageSize
     * @return
     */
    @Select("select * from dish_flavor ORDER BY sort ASC limit #{startIndex}, #{pageSize};")
    List<DishFlavor> selectByPage(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

    /**
     * get the total of records of category
     * @return
     */
    @Select("select count(*) from dish_flavor;")
    int total();






    /**
     * get the total of records by conditions
     * use xml to query
     * @return
     */
    int totalByConditions(String name);

    /**
     *  update the info of a specified flavor
     *  This is a common method used to update a dish_flavor information
     *
     *
     * @param
     */
    DishFlavor updateInfo(DishFlavor dishFlavor);


    /**
     *  remove the flavor with the specified id
     * @param id
     */
    @Delete("delete from dish_flavor where id = #{id};")
    void deleteById(Long id);


    /**
     *  remove the flavor with the specified dish id
     * @param dishId
     */
    @Delete("delete from dish_flavor where dish_id = #{dishId};")
    void deleteByDishId(Long dishId);

    /**
     *  remove the flavors with the specified dish ids
     * @param dishIds
     */
    void deleteByDishIds(@Param("dishIds") Long[] dishIds);



}
