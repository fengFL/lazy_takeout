package org.example.lazzy.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.example.lazzy.common.Result;
import org.example.lazzy.dto.DishDto;
import org.example.lazzy.pojo.Dish;
import org.example.lazzy.pojo.DishFlavor;
import org.example.lazzy.pojo.PageBean;
import org.example.lazzy.service.CategoryService;
import org.example.lazzy.service.DishFlavorService;
import org.example.lazzy.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 *  dish management
 */
@Slf4j
@RestController
@RequestMapping("/dish")
@Api(tags = "Apis for dish")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     *  Add dish and dish flavors
     *  Since we do not have a pojo can match the json from the client,
     *  we create a new pojo to match the data
     *  We dus Data transfer object to complete the task in this case.
     *
     */
    @PostMapping
    @ApiOperation("add new dish")
    public Result<String> addDish(@RequestBody DishDto dishDto){
        dishService.addDish(dishDto);

        // clear all the cache for dishes
//        Set keys = redisTemplate.keys("dish_*"); // get the keys start with "dish_"
//        redisTemplate.delete(keys); // delete the keys (clear cache)

        // precisely clear cache
        // get the key
        String key = "dish_"+dishDto.getCategoryId()+"_1";
        redisTemplate.delete(key); // delete the cache for the key

        return Result.success("add dish successful");
    }

    /**
     *  paging query with name
     */
    @GetMapping("/page")
    @ApiOperation("paging query")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "page", value = "current page", required = true),
                    @ApiImplicitParam(name = "pageSize", value = "the number of item displayed", required = true),
                    @ApiImplicitParam(name = "name", value = "setmeal name", required = false)
            }
    )
    public Result<PageBean<DishDto>> getPages(int page, int pageSize, String name){

        if ( name != null && name.trim().equals("")){
            name = null;
        }
        PageBean<DishDto> dishDtoPageBean = dishService.selectByPageAndConditions(page, pageSize, name);

        if(dishDtoPageBean == null){
            return Result.error("load error");
        }
        return Result.success(dishDtoPageBean);

    }

    /**
     *  redisplay the dish info
     */
    @GetMapping("/{id}")
    public Result<DishDto> redisplay(@PathVariable Long id){
        log.info("dish id is ---> {}", id);
        // encapsulate the id into Dish object
        Dish dish = new Dish();
        dish.setId(id);

        DishDto dishDto = dishService.redisplay(dish);
        if(dishDto == null) {
            return Result.error("id does not exist");
        }
        return  Result.success(dishDto);
    }


    /**
     *  update the info
     */
    @PutMapping
    public Result<String> update(@RequestBody DishDto dishDto){
        dishService.update(dishDto);

        // clear all the cache for dishes
//        Set keys = redisTemplate.keys("dish_*"); // get the keys start with "dish_"
//        redisTemplate.delete(keys); // delete the keys (clear cache)

        // precisely clear cache
        // get the key
        String key = "dish_"+dishDto.getCategoryId()+"_1";
        redisTemplate.delete(key); // delele the cache for the key
        return Result.success("succeed");
    }

    /**
     *  stop selling the dish in bulk
     */
    @PostMapping("/status/{status}")
    public Result<String> updateStatus(@PathVariable int status, Long[] ids){
        log.info("stop selling status is {}, and id ---> {}", status, Arrays.toString(ids) );
        // get the dish object whose status will be updated
        if(ids == null || ids.length == 0) {
            return Result.error("update error");
        }
        if(ids.length == 1) {
            // update single dish
            Long id = ids[0];
            dishService.updateStatusById(new Dish(),status, id);
        } else {
            dishService.updateStatusByIds(new Dish(), status, ids);
        }

        return Result.success("success");
    }

    /**
     * Delete current dish and the associated flavors
     */
    @DeleteMapping()
    public Result<String> deleteDishAndFlavors(Long[] ids){
        if(ids == null || ids.length == 0) {
            return Result.error("id does not exits");
        }
        // the parameter is dish ids
        if( ids.length == 1){
            // delete one dish
            dishService.deleteDishAndFlavors(ids[0]);
        } else {
            // delete multiple dishes
            dishService.deleteDishAndFlavorsInBatch(ids);
        }

        return Result.success("success");
    }

    /**
     *  mapping for /list
     *  get dish list
     */
    @GetMapping("/list")

    public Result<List<DishDto>> list(Long categoryId, Integer status){

        // initialize dishDtoList, which is used to return
        List<DishDto> dishDtoList = null;
        if(categoryId == null) {
            return  Result.error("category id error");
        }
        // get data from redis server
        // set the key
        String key = "dish_"+categoryId + "_" + status;

        dishDtoList = (List<DishDto>) redisTemplate.opsForValue().get(key);

        // if data exists, return the data
        if(dishDtoList != null) {
            return Result.success(dishDtoList);
        }

        // if data does not exist, query data from database

        // get the list with category id and status = 1 only.
        List<Dish> dishList = dishService.getListByCategoryId(categoryId);
        dishDtoList = dishList.stream().map(dish -> {
            DishDto dishDto = new DishDto(dish);
            // get the flavors for current dish
            List<DishFlavor> dishFlavors = dishFlavorService.selectByDishId(dishDto.getId());
            dishDto.setFlavors(dishFlavors);
            return dishDto;
        }).collect(Collectors.toList());
        // store the data into redis
        redisTemplate.opsForValue().set(key, dishDtoList, 60, TimeUnit.MINUTES);
        return Result.success(dishDtoList);

    }

//    public Result<List<Dish>> list(Long categoryId){
//        if(categoryId == null) {
//            return  Result.error("category id error");
//        }
//        // get the list with category id and status = 1 only.
//        List<Dish> dishList = dishService.getListByCategoryId(categoryId);
//        return Result.success(dishList);
//
//    }
}
