package org.example.lazzy.service;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.example.lazzy.pojo.Dish;
import org.example.lazzy.pojo.PageBean;
import org.example.lazzy.pojo.Setmeal;
import org.example.lazzy.pojo.SetmealDish;

import java.util.List;

public interface SetmealDishService {
    /**
     *  query all set meals

     * @return
     */
    List<Setmeal> selectAll();

    /**
     * query setmeal info by id
     */
    Setmeal selectById(Long id);
    /**
     *  add category into database
     * @param setmealDish
     */
    void addSetmealDish(SetmealDish setmealDish);

    /**
     * query by page and page size
     * @param page
     * @param pageSize
     */
    PageBean<Setmeal> selectByPage(int page, int pageSize);

    /**
     *  update the category info
     */
    void update(Setmeal setmeal);

    /**
     *  remove the setmeal dishes with the specified setmeal id
     * @param setmealId
     */
    void deleteBySetmealId(Long setmealId);

    /**
     * query the number of dishes by category id
     * @param categoryId
     */
    int selectByCategoryId(Long categoryId);


    /**
     *  query dishes from setmeal_dish table
     * @param setmealId
     */

    List<SetmealDish> selectBySetmealId(Long setmealId);



}
