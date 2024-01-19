package org.example.lazzy.service;

import org.apache.ibatis.annotations.Select;
import org.example.lazzy.dto.SetmealDto;
import org.example.lazzy.pojo.Category;
import org.example.lazzy.pojo.PageBean;
import org.example.lazzy.pojo.Setmeal;

import java.util.List;

public interface SetmealService {
    /**
     *  query all set meals

     * @return
     */
    List<Setmeal> selectAll();

    /**
     *  query setmeal by category Id and status
     */
    List<Setmeal> selectByCategoryIdAndStatus(Long categoryId, Integer status);

    /**
     * query setmeal info by id
     */
    SetmealDto selectById(Long id);
    /**
     *  add category into database
     * @param setmeal
     */
    void addSetmeal(Setmeal setmeal);

    /**
     * query by page and page size
     * @param page
     * @param pageSize
     */
    PageBean<Setmeal> selectByPage(int page, int pageSize);

    /**
     * query by page, page size and name
     * @param page
     * @param pageSize
     */
    PageBean<Setmeal> selectByPageAndConditions(int page, int pageSize, String name);

    /**
     *  update the category info
     */
    void update(Setmeal setmeal);

    /**
     *  delete the category with the specified id
     */
    void deleteById(Long id);


    /**
     * query the count of setmeal by category id
     * @param categoryId
     */
    int selectByCategoryId(Long categoryId);

    /**
     *  delete setmeals by ids
     * @param ids
     */
    int deleteByIds(List<Long> ids);


    /**
     *  update the status for batch
     */
    void updateStatuses(Setmeal setmeal, List<Long> ids, int status);

}
