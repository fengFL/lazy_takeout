package org.example.lazzy.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户信息
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    // customer name
    private String name;

    // phone number
    private String phone;

    // gender 0 female 1 male
    private String sex;

    // identification number
    private String idNumber;

    // avatar
    private String avatar;

    // status 0:disabled，1:enabled
    private Integer status;
}
