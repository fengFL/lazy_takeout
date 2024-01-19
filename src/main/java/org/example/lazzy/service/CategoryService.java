package org.example.lazzy.service;

import org.apache.ibatis.annotations.Select;
import org.example.lazzy.pojo.Category;
import org.example.lazzy.pojo.Employee;
import org.example.lazzy.pojo.PageBean;

import java.util.List;

public interface CategoryService {
    /**
     *  query all category

     * @return
     */
    List<Category> selectAll();

    /**
     * get categories by type
     * @param type
     */

    List<Category> selectByType(Integer type);


    /**
     * query category info by id
     */
    Category selectById(Long id);
    /**
     *  add category into database
     * @param category
     */
    void addCategory(Category category);

    /**
     * query by page and page size
     * @param page
     * @param pageSize
     */
    PageBean<Category> selectByPage(int page, int pageSize);

    /**
     *  update the category info
     */
    void update(Category category);

    /**
     *  delete the category with the specified id
     */
    void deleteById(Long id);

}
