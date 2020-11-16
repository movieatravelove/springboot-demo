package com.zhang.demo.modules.api.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: zhang
 * @Date: 2020/11/9 18:02
 * @Description: 用户实体类
 * @Version: 1.0
 */
@Data
public class User {

    @ApiModelProperty(value = "姓名，文档描述")
    private String name;
    @ApiModelProperty(value = "年龄")
    private int age;
    @ApiModelProperty(value = "手机")
    private String phone;

}
