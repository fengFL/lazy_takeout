package org.example.lazzy.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 *  Result class, which is used to as the result of every response.
 * @param <T>
 */
@Data
@ApiModel("return value")
public class Result<T> implements Serializable {

    @ApiModelProperty("1 for succeed, 0 for failed")
    private Integer code; //code：1 succeed，0 and others failed

    @ApiModelProperty("message is used when error occur")
    private String msg; // error message

    @ApiModelProperty("used to store data")
    private T data; // data

    @ApiModelProperty("Dynamic data")
    private Map map = new HashMap(); // dynamic data

    public static <T> Result<T> success(T object) {
        Result<T> r = new Result<T>();
        r.data = object;
        r.code = 1;
        return r;
    }

    public static <T> Result<T> error(String msg) {
        Result r = new Result();
        r.msg = msg;
        r.code = 0;
        return r;
    }

    public Result<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

}
