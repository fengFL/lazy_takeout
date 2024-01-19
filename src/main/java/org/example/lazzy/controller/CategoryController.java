package org.example.lazzy.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.lazzy.common.CustomException;
import org.example.lazzy.common.Result;
import org.example.lazzy.pojo.Category;
import org.example.lazzy.pojo.Employee;
import org.example.lazzy.pojo.PageBean;
import org.example.lazzy.service.CategoryService;
import org.example.lazzy.service.DishService;
import org.example.lazzy.service.EmployeeService;
import org.example.lazzy.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;

    /**
     * Add category
     * @param category
     * @return
     */
    @PostMapping
    public Result<String> addCategory(@RequestBody Category category){
        log.info("current Thread id in addXxx() controller ---> {}", Thread.currentThread().getId());
        log.info("add category. category info: {}", category.toString());
        // create id
        Long id = System.currentTimeMillis();
        category.setId(id);
        if(category.getName() == null || category.getName() == "" || category.getType() == null || category.getSort() == null){
            return Result.error("add category failed");
        }

        // create time, update time, create user, and update user are filled in Advice class.

        // not null check

        /**
         *
         *  do not query from the database, which can give more burden to server.
         *  Through the exception directly, then processed by the global exception handler
          */

        // If name does not exist. Add the info into database
        // If name exists, the exception will be caught in GlobalExceptionHandler.
        categoryService.addCategory(category);
        return Result.success("add category succeed");
    }

    @GetMapping("/page")
    public Result<PageBean<Category>> selectByPage(int page, int pageSize){
        // get the result
        PageBean<Category> pageBean = categoryService.selectByPage(page, pageSize);
        if(pageBean == null) {
            return Result.error("no message");
        }
        return Result.success(pageBean);
    }


    /**
     *  update category info by id
     */
    @GetMapping("/{id}")
    public Result<Category> updateById( @PathVariable Long id){
        if(id == null) {
            return Result.error("update failed");
        }
        // query info with the specified id
        Category category = categoryService.selectById(id);
        if(category == null) {
            // employee does not exist
            return Result.error("update failed");
        }
        // employee exists
        // return the employee
        return Result.success(category);
    }


    @DeleteMapping
    public Result<String> deleteById(Long ids){
        if( ids == null) {
            return Result.error("remove failed");
        }

        // check if the current category is related to a dish
        //      if relevant, throws a customException and passes the error message
        int count = dishService.selectByCategoryId(ids);
        if( count > 0 ){
            throw new CustomException("Current category related to the dish");
        }
        // check if the current category is related to a setmeal
        //      if relevant, throws a customException and passes the error message
        count = setmealService.selectByCategoryId(ids);
        if( count > 0 ){
            throw new CustomException("Current category related to the setmeal");
        }
        // remove the category
        categoryService.deleteById(ids);
        return Result.success("delete succeed");
    }

    @PutMapping
    public Result<String> updateCategory(@RequestBody Category category){
        categoryService.update(category);
        return Result.success("Updated succeed");
    }


    /**
     *  get the category list by type
     *  we encapsulate the type into a category object
     *
     */
    @GetMapping("/list")
    public Result<List<Category>> getList(Category category){
        // check the type
        Integer type = category.getType();

        // query the database
        List<Category> categories = categoryService.selectByType(type);
        return Result.success(categories);
    }


}
