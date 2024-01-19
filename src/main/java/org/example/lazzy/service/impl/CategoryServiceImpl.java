package org.example.lazzy.service.impl;

import org.example.lazzy.mapper.CategoryMapper;
import org.example.lazzy.mapper.EmployeeMapper;
import org.example.lazzy.pojo.Category;
import org.example.lazzy.pojo.Employee;
import org.example.lazzy.pojo.PageBean;
import org.example.lazzy.service.CategoryService;
import org.example.lazzy.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    /**
     *  query user by username
     * @return the employee if exists, otherwise, return null
     */
    @Override
    public List<Category> selectAll(){
        return categoryMapper.selectAll();
    }

    @Override
    public List<Category> selectByType(Integer type) {
        return categoryMapper.selectByType(type);
    }

    /**
     *  select category by id
     * @param id
     * @return
     */
    @Override
    public Category selectById(Long id) {
        return categoryMapper.selectById(id);
    }

    /**
     *  add the category info into employee table
     * @param category
     */
    @Override
    public void addCategory(Category category) {
        categoryMapper.addCategory(category);
    }

    /**
     * paging query
     * @param page
     * @param pageSize
     */
    @Override
    public PageBean<Category> selectByPage(int page, int pageSize) {
        // calculate the index
        int index= (page - 1) * pageSize;


        // query records and store the data
        List<Category> records = categoryMapper.selectByPage(index, pageSize);
        // query total
        int total = categoryMapper.total();
        // encapsualte the data into PageBean object
        PageBean<Category> pageBean = new PageBean<>();
        pageBean.setRecords(records);
        pageBean.setTotal(total);
        return pageBean;
    }

    /**
     *  update the category info
     * @param category
     */
    @Override
    public void update(Category category) {
        categoryMapper.updateInfo(category);
    }

    @Override
    public void deleteById(Long id) {
        categoryMapper.deleteById(id);
    }
}
