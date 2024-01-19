package org.example.lazzy.pojo;

import lombok.Data;
import org.example.lazzy.dto.SetmealDto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 套餐
 */
@Data
public class Setmeal implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;


    //分类id
    private Long categoryId;


    //套餐名称
    private String name;


    //套餐价格
    private BigDecimal price;


    //状态 0:停用 1:启用
    private Integer status;


    //编码
    private String code;


    //描述信息
    private String description;


    //图片
    private String image;



    private LocalDateTime createTime;



    private LocalDateTime updateTime;


    private Long createUser;


    private Long updateUser;


    //是否删除
    private Integer isDeleted;

    public Setmeal() {
    }

    public Setmeal(SetmealDto setmealDto) {
        this.setCode(setmealDto.getCode());
        this.setId(setmealDto.getId());
        this.setCategoryId(setmealDto.getCategoryId());
        this.setCreateTime(setmealDto.getCreateTime());
        this.setUpdateTime(setmealDto.getUpdateTime());
        this.setCreateUser(setmealDto.getCreateUser());
        this.setUpdateUser(setmealDto.getUpdateUser());
        this.setDescription(setmealDto.getDescription());
        this.setImage(setmealDto.getImage());
        this.setIsDeleted(setmealDto.getIsDeleted());
        this.setName(setmealDto.getName());
        this.setPrice(setmealDto.getPrice());
        this.setStatus(setmealDto.getStatus());
    }
}
