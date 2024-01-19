package org.example.lazzy.service.impl;

import org.example.lazzy.common.Result;
import org.example.lazzy.dto.DishDto;
import org.example.lazzy.mapper.CategoryMapper;
import org.example.lazzy.mapper.DishMapper;
import org.example.lazzy.pojo.Category;
import org.example.lazzy.pojo.Dish;
import org.example.lazzy.pojo.DishFlavor;
import org.example.lazzy.pojo.PageBean;
import org.example.lazzy.service.CategoryService;
import org.example.lazzy.service.DishFlavorService;
import org.example.lazzy.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;
    /**
     *  query user by username
     * @return the employee if exists, otherwise, return null
     */
    @Override
    public List<Dish> selectAll(){
        return dishMapper.selectAll();
    }

    /**
     *  select dish by id
     * @param id
     * @return
     */
    @Override
    public Dish selectById(Long id) {
        return dishMapper.selectById(id);
    }

    /**
     *  add the dish and dish flavor info into employee table
     * @param dishDto
     */
    @Transactional
    @Override
    public void addDish(DishDto dishDto) {
        // the data is encapsulated in dishDto.
        // 1. prepare the data for dish
        Dish dish = new Dish(dishDto);
        long id = System.currentTimeMillis();
        dish.setId(id);
        // 1.1 add the dish
        dishMapper.addDish(dish);

        // 2. prepare the data for dish flavor
        // the flavor are stored in dishDto.flavors.
        // It is a list, we need use loop to add all the data into dish flavor
        List<DishFlavor> flavors = dishDto.getFlavors();
        for (DishFlavor flavor : flavors) {
            long id1 = System.currentTimeMillis();
            flavor.setId(id1);
            flavor.setDishId(dish.getId());
            dishFlavorService.addDishFlavor(flavor);
        }

    }

    /**
     * paging query
     * @param page
     * @param pageSize
     */
    @Override
    public PageBean<DishDto> selectByPageAndConditions(int page, int pageSize, String name) {
        // calculate the index
        int index= (page - 1) * pageSize;

        if(name != null) {
            name = "%" + name + "%";
        }
        // query records and store the data
        List<Dish> records = dishMapper.selectByPageAndConditions(index, pageSize, name);
        // process name if it is not null


        // query total
        int total = dishMapper.totalByConditions(name);
        // encapsualte the data into PageBean object

        PageBean<DishDto> dishDtoPageBean = new PageBean<>();

        List<DishDto> collect = records.stream().map(item -> {
            DishDto dishDto = new DishDto(item);
            // get categoryId
            Long categoryId = item.getCategoryId();
            // get categoryName and set it to dishDto
            if(categoryId != null) {
                Category category = categoryService.selectById(categoryId);
                if(category != null) {
                    dishDto.setCategoryName(category.getName());
                }
            }

            // set all other info
            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPageBean.setRecords(collect);

        dishDtoPageBean.setTotal(total);
        return dishDtoPageBean;
    }

    /**
     *  redisplay the category info
     * @param dish
     */
    @Override
    public DishDto redisplay(Dish dish) {
        if(dish == null) {
            return null;
        }
        // get the dish id

        Long id = dish.getId();
        if(id == null) {
            return null;
        }
        // get Dish object by id
        Dish dishInfo = dishMapper.selectById(id);

        // get Dish flavors by dish Id
        List<DishFlavor> dishFlavors = dishFlavorService.selectByDishId(id);

        // encapsulate the info into Dto
        DishDto dishDto = new DishDto(dishInfo);
        // set flavors
        dishDto.setFlavors(dishFlavors);
        // set category name
        Long categoryId = dish.getCategoryId();
        if(categoryId != null) {
            Category category = categoryService.selectById(categoryId);
            if(category != null) {
                dishDto.setCategoryName(category.getName());
            }
        }
        return dishDto;
    }

    /**
     *  update the dish and dish flavors info
     * @param dishDto
     */

    @Override
    @Transactional
    public void update(DishDto dishDto) {
        // chech the dishId
        if(dishDto == null || dishDto.getId() == null){
            return;
        }
        Long dishId = dishDto.getId();
        // set the dish info
        Dish dish = new Dish(dishDto);
        dishMapper.updateInfo(dish);

        // remove the dish flavors, then stores the new flavors
        dishFlavorService.deleteByDishId(dishId);

        // add the new flavors info
        dishDto.getFlavors().stream().map(item -> {
            item.setId(System.currentTimeMillis());
            item.setDishId(dishId);
            return item;
        }).forEach((item -> {
            dishFlavorService.addDishFlavor(item);
        }));

    }

    /**
     *  update dish info only
     * @param dish
     */
    @Override
    @Transactional
    public void update(Dish dish) {
        // chech the dishId
        if(dish == null || dish.getId() == null){
            return;
        }
        dishMapper.updateInfo(dish);

    }


    @Override
    public void deleteById(Long id) {
        dishMapper.deleteById(id);
    }

    @Override
    public int selectByCategoryId(Long categoryId) {
        return dishMapper.selectByCategoryId(categoryId);
    }

    @Override
    public List<Dish> getListByCategoryId(Long categoryId) {
        return  dishMapper.getListByCategoryId(categoryId);
    }

    /**
     *  delete dish and its flavors
     * @param dishId is dish id
     */
    @Override
    @Transactional
    public void deleteDishAndFlavors(Long dishId) {
        // delete the dish from dish table
        dishMapper.deleteById(dishId);

        // delete flavors form dish_flavor table
        dishFlavorService.deleteByDishId(dishId);
    }


    /**
     * delete dish and its associated flavors in batches
     * @param dishIds the dish id array
     */
    @Override
    @Transactional
    public void deleteDishAndFlavorsInBatch(Long[] dishIds) {
        dishMapper.deleteByIds(dishIds);
        dishFlavorService.deleteByDishIds(dishIds);
    }

    @Override
    public void updateStatusById(Dish dish, int status, Long id) {

        dishMapper.updateStatusById( status, id);
    }

    @Override
    public void updateStatusByIds(Dish dish, int status, Long[] ids) {
        dishMapper.updateStatusByIds(status, ids);

    }
}
