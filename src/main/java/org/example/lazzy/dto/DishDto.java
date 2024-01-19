package org.example.lazzy.dto;

import org.example.lazzy.pojo.Dish;
import org.example.lazzy.pojo.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;


/**
 * This class is used to encapsulate the data from the client.
 */
@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;

    public DishDto() {
    }
    public DishDto(Dish dish) {

        this.setCode(dish.getCode());
        this.setId(dish.getId());
        this.setImage(dish.getImage());
        this.setName(dish.getName());
        this.setPrice(dish.getPrice());
        this.setStatus(dish.getStatus());
        this.setCategoryId(dish.getCategoryId());
        this.setUpdateUser(dish.getUpdateUser());
        this.setCreateTime(dish.getCreateTime());
        this.setUpdateTime(dish.getUpdateTime());
        this.setCreateUser(dish.getCreateUser());
        this.setIsDeleted(dish.getIsDeleted());
        this.setDescription(dish.getDescription());
    }

}
