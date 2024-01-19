package org.example.lazzy.service;

import org.apache.ibatis.annotations.Select;
import org.example.lazzy.dto.DishDto;
import org.example.lazzy.pojo.Category;
import org.example.lazzy.pojo.Dish;
import org.example.lazzy.pojo.PageBean;

import java.util.List;

public interface DishService {
    /**
     *  query all dishes

     * @return
     */
    List<Dish> selectAll();

    /**
     * query dish info by id
     */
    Dish selectById(Long id);
    /**
     *  add dish into database
     * @param dishDto
     */
    void addDish(DishDto dishDto);

    /**
     * query by page, page size, and name
     * @param page
     * @param pageSize
     * @param name
     */
    PageBean<DishDto> selectByPageAndConditions(int page, int pageSize, String name);

    /**
     *  redisplay the dish info
     */
    DishDto redisplay(Dish dish);

    /**
     * update the dish and dish flavor info
     */
    void update(DishDto dishDto);

    /**
     * update the dish info
     */
    void update(Dish dish);

    /**
     *  delete the dish with the specified id
     */
    void deleteById(Long id);

    /**
     * query the number of dishes by category id
     * @param categoryId
     */
    int selectByCategoryId(Long categoryId);

    /**
     * query the list of dishes by category id
     * @param categoryId
     */
    List<Dish> getListByCategoryId(Long categoryId);

    /**
     * delete the dish and its associated flavors
     * @param dishId is dish id
     */
    void deleteDishAndFlavors(Long dishId);

    /**
     * batch deletion
     * @param dishIds
     */
    void deleteDishAndFlavorsInBatch(Long[] dishIds);

    /**
     *  update single dish's status
     * @param status, id --> dish id
     */
    void updateStatusById(Dish dish, int status, Long id);

    /**
     *  update batch dish's status
     * @param ids -- dish ids
     */
    void updateStatusByIds(Dish dish, int status, Long[] ids);


}
