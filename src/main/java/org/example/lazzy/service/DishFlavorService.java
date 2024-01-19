package org.example.lazzy.service;

import org.example.lazzy.dto.DishDto;
import org.example.lazzy.pojo.Dish;
import org.example.lazzy.pojo.DishFlavor;
import org.example.lazzy.pojo.PageBean;

import java.util.List;

public interface DishFlavorService {
    /**
     *  query all DishFlavores

     * @return
     */
    List<DishFlavor> selectAll();

    /**
     * query DishFlavor info by id
     */
    DishFlavor selectById(Long id);
    /**
     *  add DishFlavor into database
     * @param dishFlavor
     */
    void addDishFlavor(DishFlavor dishFlavor);

    /**
     * query by page and page size
     * @param page
     * @param pageSize
     */
    PageBean<DishFlavor> selectByPage(int page, int pageSize);

    /**
     *  update the DishFlavor info
     */
    DishDto update(DishFlavor dishFlavor);

    /**
     *  delete the DishFlavor with the specified id
     */
    void deleteById(Long id);

    /**
     *  delete the DishFlavor with the specified dish id
     */
    void deleteByDishId(Long dishId);

    /**
     *  delete the DishFlavors with the specified dish ids
     */
    void deleteByDishIds(Long[] dishIds);


    /**
     * query the number of DishFlavores by category id
     * @param dishId
     */
    List<DishFlavor> selectByDishId(Long dishId);

}
