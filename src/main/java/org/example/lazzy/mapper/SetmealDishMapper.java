package org.example.lazzy.mapper;

import org.apache.ibatis.annotations.*;
import org.example.lazzy.pojo.Dish;
import org.example.lazzy.pojo.Setmeal;
import org.example.lazzy.pojo.SetmealDish;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
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

    @Insert("insert into setmeal_dish(id, setmeal_id, dish_id, name, price, copies, sort, create_time, update_time, create_user, update_user) values(#{id}, #{setmealId}, #{dishId}, #{name}, #{price}, #{copies}, #{sort}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser});")
    void addSetmealDish(SetmealDish setmealDish);

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
     *  remove the setmeal dishes with the specified setmeal id
     * @param setmealId
     */
    @Delete("delete from setmeal_dish where setmeal_id = #{setmealId};")
    void deleteBySetmealId(Long setmealId);


    /**
     *  query dishes from setmeal_dish table
     * @param setmealId
     */
    @Select("select * from setmeal_dish where setmeal_id = #{setmealId};")
    List<SetmealDish> selectBySetmealId(Long setmealId);


}
