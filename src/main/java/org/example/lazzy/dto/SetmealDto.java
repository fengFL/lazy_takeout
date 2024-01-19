package org.example.lazzy.dto;

import org.example.lazzy.pojo.Setmeal;
import org.example.lazzy.pojo.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;


    public SetmealDto() {
    }

    public SetmealDto(Setmeal setmeal) {
        this.setCode(setmeal.getCode());
        this.setId(setmeal.getId());
        this.setCategoryId(setmeal.getCategoryId());
        this.setCreateTime(setmeal.getCreateTime());
        this.setUpdateTime(setmeal.getUpdateTime());
        this.setCreateUser(setmeal.getCreateUser());
        this.setUpdateUser(setmeal.getUpdateUser());
        this.setDescription(setmeal.getDescription());
        this.setImage(setmeal.getImage());
        this.setIsDeleted(setmeal.getIsDeleted());
        this.setName(setmeal.getName());
        this.setPrice(setmeal.getPrice());
        this.setStatus(setmeal.getStatus());
    }
}
