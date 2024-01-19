package org.example.lazzy.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.lazzy.common.CustomException;
import org.example.lazzy.common.Result;
import org.example.lazzy.dto.SetmealDto;
import org.example.lazzy.pojo.Dish;
import org.example.lazzy.pojo.PageBean;
import org.example.lazzy.pojo.Setmeal;
import org.example.lazzy.pojo.SetmealDish;
import org.example.lazzy.service.SetmealDishService;
import org.example.lazzy.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private SetmealDishService setmealDishService;

    /**
     *  add setmeal, of course, we need add the dishes for the setmeal at the same time.
     *  add data into two tables
     */
    @PostMapping
    @Transactional
    public Result<String> add(@RequestBody SetmealDto setmealDto){
        // prepare the data for setmeal
        Setmeal setmeal = new Setmeal(setmealDto);
        setmeal.setId(System.currentTimeMillis());
        setmealService.addSetmeal(setmeal);
        SetmealDish setmealDish = new SetmealDish();
        setmealDto.getSetmealDishes().stream().peek(item -> {
            item.setSetmealId(setmeal.getId());
            item.setId(System.currentTimeMillis());
            item.setSort(0);
        }).forEach(item ->{
            // store SetmealDish
            setmealDishService.addSetmealDish(item);
        });


        return  Result.success("add set meal successful");
    }

    /**
     *   get pages
     */
    @GetMapping("/page")
    public Result<PageBean<Setmeal>> getPages(Integer page, Integer pageSize, String name){
        if(page == null || pageSize == null) {
            return Result.error("error");
        }
        if (name != null && name.equals("")){
            name = null;
        }

        // get list of setmeals
        PageBean<Setmeal> setmealPageBean = setmealService.selectByPageAndConditions(page, pageSize, name);
        return Result.success(setmealPageBean);
    }

    /**
     *  get setmeal dish info
     */
    @GetMapping("/{id}")
    public Result<SetmealDto> getSetmealInfo(@PathVariable Long id){
        if(id == null) {
            return Result.error("error");

        }
        SetmealDto setmealDto = setmealService.selectById(id);
        return Result.success(setmealDto);
    }

    /**
     * update setmeal info
     * put method
     * mapping
     */
    @PutMapping
    public Result<String> update(@RequestBody SetmealDto setmealDto){
        // 1. update setmeal info
        // 1.1 prepare the data for setmeal
        Setmeal setmeal = new Setmeal(setmealDto);
        // 1.2 update the info
        setmealService.update(setmeal);

        // 2. remove original setmeal dish info by setmeal id, then save the new setmeal dish info
        // 2.1 remove the originall setmeal dish info by setmeal id
        setmealDishService.deleteBySetmealId(setmeal.getId());
        // 2.2 prepare the data for saving
        setmealDto.getSetmealDishes().stream().forEach(setmealDish -> {
            setmealDish.setId(System.currentTimeMillis());
            // set setmeal dish info
            setmealDish.setSetmealId(setmeal.getId());
            setmealDish.setSort(0);

            // add setmeal dish into database
            setmealDishService.addSetmealDish(setmealDish);
        });

        return  Result.success(" update successful");
    }


    /**
     *  delete setmeal(s) by id(s)
     */
    @Transactional
    @DeleteMapping
    // We need use requestParam annotation when using List<T> to receive data
    public Result<String> delete(@RequestParam List<Long> ids){

        log.info("setmeal ids is ---> : {}", ids);
        if(ids == null || ids.size() == 0){
            return Result.error("delete error");
        }

        // 1. remove setmeals that are not for sale
        int affectedRows = setmealService.deleteByIds(ids);
        // 2. remove all the setmeal dishes in the setmeal_dish table by setmeal id
        if (affectedRows == 0) {
            throw new CustomException("The setmeal is currently on sale");
        }
        ids.forEach(id ->{
            setmealDishService.deleteBySetmealId(id);
        });

        return Result.success("delete succeed");
    }

    /**
     *  stop selling setmeal(s)
     */
    @PostMapping("/status/{status}")
    public Result<String> updateStatuses(@PathVariable int status, @RequestParam List<Long> ids){
        if(status != 0 && status != 1){
            return Result.error("update failed");
        }
        // update the status of setmeals by ids
        if(ids == null || ids.size() == 0) {
            return Result.error("update failed");
        }
        setmealService.updateStatuses(new Setmeal(),ids, status);
        return Result.success("update status successful");
    }

    /**
     *  query setmeal info for a specific categoryId;
     */
    @GetMapping("/list")
    public Result<List<Setmeal>> list(Setmeal setmeal){
        // the category ID and status is encapsulated into setmeal
        // get category ID
        Long categoryId = setmeal.getCategoryId();

        if(categoryId == null){
            throw new CustomException("no such category ID");
        }
        // category is not null
        // get status
        Integer status = setmeal.getStatus();
        if(status == null) {
            throw new CustomException("no such category");
        }
        // get the setmeal info
        List<Setmeal> setmeals = setmealService.selectByCategoryIdAndStatus(categoryId, status);
        if (setmeals == null){
            throw new CustomException("setmeal error");
        }
        return Result.success(setmeals);
    }

    /**
     *  setmeal dishes info
     */
    @GetMapping("/dish/{setmealId}")
    public Result<List<SetmealDish>> dishes(@PathVariable Long setmealId){
        // check setmeal id
        if(setmealId == null) {
            throw  new CustomException("no such setmeal id");
        }
        // get its dishes from setmeal_dish by setmeal id
        List<SetmealDish> setmealDishes = setmealDishService.selectBySetmealId(setmealId);
        return Result.success(setmealDishes);
    }

}
