package org.example.lazzy.service.impl;

import org.apache.ibatis.annotations.Select;
import org.example.lazzy.dto.SetmealDto;
import org.example.lazzy.mapper.SetmealDishMapper;
import org.example.lazzy.mapper.SetmealMapper;
import org.example.lazzy.pojo.Dish;
import org.example.lazzy.pojo.Setmeal;
import org.example.lazzy.pojo.PageBean;
import org.example.lazzy.pojo.SetmealDish;
import org.example.lazzy.service.CategoryService;
import org.example.lazzy.service.SetmealDishService;
import org.example.lazzy.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SetmealDishService setmealDishService;
    /**
     *  query user by username
     * @return the employee if exists, otherwise, return null
     */
    @Override
    public List<Setmeal> selectAll(){
        return setmealMapper.selectAll();
    }

    @Override
    public List<Setmeal> selectByCategoryIdAndStatus(Long categoryId, Integer status) {
       return setmealMapper.selectByCategoryIdAndStatus(categoryId, status);
    }


    /**
     *  add the category info into employee table
     * @param setmeal
     */
    @Override
    public void addSetmeal(Setmeal setmeal) {
        setmealMapper.addSetmeal(setmeal);
    }

    /**
     * paging query
     * @param page
     * @param pageSize
     */
    @Override
    public PageBean<Setmeal> selectByPage(int page, int pageSize) {
        // calculate the index
        int index= (page - 1) * pageSize;


        // query records and store the data
        List<Setmeal> records = setmealMapper.selectByPage(index, pageSize);
        // query total
        int total = setmealMapper.total();
        // encapsualte the data into PageBean object
        PageBean<Setmeal> pageBean = new PageBean<>();
        pageBean.setRecords(records);
        pageBean.setTotal(total);
        return pageBean;
    }

    @Override
    public PageBean<Setmeal> selectByPageAndConditions(int page, int pageSize, String name) {
        // determine the start index
        int index = (page - 1) * pageSize;

        // deal the name
        if(name != null) {
            name = "%" + name + "%";
        }
        // query setmeals from database
        List<Setmeal> setmeals = setmealMapper.selectByPageAndConditions(index, pageSize, name);
        // create a pageBean object to encapsulate the data
        PageBean<Setmeal> pageBean = new PageBean<>();
       pageBean.setRecords(setmeals);
       pageBean.setTotal(setmealMapper.totalByConditions(name));
        return pageBean;
    }

    /**
     *  update the category info
     * @param setmeal
     */
    @Override
    public void update(Setmeal setmeal) {
        setmealMapper.updateInfo(setmeal);
    }

    @Override
    public void deleteById(Long id) {
        setmealMapper.deleteById(id);
    }

    @Override
    public int selectByCategoryId(Long categoryId) {
        return setmealMapper.selectByCategoryId(categoryId);
    }

    /**
     *  batch deletion
     * @param ids
     */
    @Override
    public int deleteByIds(List<Long> ids) {
        return setmealMapper.deleteByIds(ids);
    }

    @Override
    public void updateStatuses(Setmeal setmeal, List<Long> ids, int status) {
        setmealMapper.updateStatuses(ids, status);
    }

    /**
     *  query setmeal info by setmeal Id
     * @param setmealId
     * @return
     */
    @Override
    public SetmealDto selectById(Long setmealId) {
        Setmeal setmeal = setmealMapper.selectById(setmealId);
        // create SetmealDto object
        SetmealDto setmealDto = new SetmealDto(setmeal);
        // query dishes by setmeal id
        List<SetmealDish> setmealDishes = setmealDishService.selectBySetmealId(setmealId);
        setmealDto.setSetmealDishes(setmealDishes);
        Long categoryId = setmealDto.getCategoryId();
        if(categoryId != null) {
            setmealDto.setCategoryName(categoryService.selectById(categoryId).getName());
        }
        return setmealDto;

    }


}
