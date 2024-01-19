package org.example.lazzy.service.impl;

import org.example.lazzy.dto.DishDto;
import org.example.lazzy.mapper.DishFlavorMapper;
import org.example.lazzy.pojo.Dish;
import org.example.lazzy.pojo.DishFlavor;
import org.example.lazzy.pojo.PageBean;
import org.example.lazzy.service.DishFlavorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishFlavorServiceImpl implements DishFlavorService {
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    /**
     *  query user by username
     * @return the employee if exists, otherwise, return null
     */
    @Override
    public List<DishFlavor> selectAll(){
        return dishFlavorMapper.selectAll();
    }

    /**
     *  select DishFlavor by id
     * @param id
     * @return
     */
    @Override
    public DishFlavor selectById(Long id) {
        return dishFlavorMapper.selectById(id);
    }

    /**
     *  add the category info into employee table
     * @param dishFlavor
     */
    @Override
    public void addDishFlavor(DishFlavor dishFlavor) {
        dishFlavorMapper.addDishFlavor(dishFlavor);
    }

    /**
     * paging query
     * @param page
     * @param pageSize
     */
    @Override
    public PageBean<DishFlavor> selectByPage(int page, int pageSize) {
        // calculate the index
        int index= (page - 1) * pageSize;


        // query records and store the data
        List<DishFlavor> records = dishFlavorMapper.selectByPage(index, pageSize);
        // query total
        int total = dishFlavorMapper.total();
        // encapsualte the data into PageBean object
        PageBean<DishFlavor> pageBean = new PageBean<>();
        pageBean.setRecords(records);
        pageBean.setTotal(total);
        return pageBean;
    }

    /**
     *  update the category info
     * @param dishFlavor
     */
    @Override
    public DishDto update(DishFlavor dishFlavor) {
       return null;
    }

    @Override
    public void deleteById(Long id) {
        dishFlavorMapper.deleteById(id);
    }


    /**
     *  delete the DishFlavor with the specified dish id
     */
    @Override
    public void deleteByDishId(Long dishId) {
        dishFlavorMapper.deleteByDishId(dishId);
    }


    /**
     *  delete the DishFlavors with the specified dish ids
     */
    @Override
    public void deleteByDishIds(Long[] dishIds) {
        dishFlavorMapper.deleteByDishIds(dishIds);

    }


    @Override
    public List<DishFlavor> selectByDishId(Long dishId) {
        return dishFlavorMapper.selectByDishId(dishId);
    }
}
