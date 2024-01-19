package org.example.lazzy.service.impl;

import org.example.lazzy.mapper.SetmealDishMapper;
import org.example.lazzy.mapper.SetmealMapper;
import org.example.lazzy.pojo.Dish;
import org.example.lazzy.pojo.PageBean;
import org.example.lazzy.pojo.Setmeal;
import org.example.lazzy.pojo.SetmealDish;
import org.example.lazzy.service.SetmealDishService;
import org.example.lazzy.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetmealDishServiceImpl implements SetmealDishService {
    @Autowired
    private SetmealDishMapper setmealDishMapper;
    /**
     *  query user by username
     * @return the employee if exists, otherwise, return null
     */
    @Override
    public List<Setmeal> selectAll(){
        return setmealDishMapper.selectAll();
    }

    /**
     *  select Setmeal by id
     * @param id
     * @return
     */
    @Override
    public Setmeal selectById(Long id) {
        return setmealDishMapper.selectById(id);
    }

    /**
     *  add the category info into employee table
     * @param setmealDish
     */
    @Override
    public void addSetmealDish(SetmealDish setmealDish) {
        setmealDishMapper.addSetmealDish(setmealDish);
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
        List<Setmeal> records = setmealDishMapper.selectByPage(index, pageSize);
        // query total
        int total = setmealDishMapper.total();
        // encapsualte the data into PageBean object
        PageBean<Setmeal> pageBean = new PageBean<>();
        pageBean.setRecords(records);
        pageBean.setTotal(total);
        return pageBean;
    }

    /**
     *  update the category info
     * @param setmeal
     */
    @Override
    public void update(Setmeal setmeal) {
        setmealDishMapper.updateInfo(setmeal);
    }

    @Override
    public void deleteBySetmealId(Long setmealId) {
        setmealDishMapper.deleteBySetmealId(setmealId);
    }


    @Override
    public int selectByCategoryId(Long categoryId) {
        return setmealDishMapper.selectByCategoryId(categoryId);
    }

    @Override
    public List<SetmealDish> selectBySetmealId(Long setmealId) {
         return setmealDishMapper.selectBySetmealId(setmealId);
    }
}
