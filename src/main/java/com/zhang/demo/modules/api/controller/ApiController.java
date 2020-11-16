package com.zhang.demo.modules.api.controller;

import com.zhang.demo.entity.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/** 
  * @Author: zhang 
  * @Date: 2020-11-06 15:04:13
  * @Description:
  * @Version: 1.0
  */  
@Slf4j
@Api(tags = "接口")
@RestController
@RequestMapping("/api")
public class ApiController {


    @GetMapping(value = "create")
    @ResponseBody
    @ApiOperation("测试")
    public ResultObject getByUrl(@ApiParam(value = "参数描述", required = true) @RequestParam(value = "url") String url) {
        return new ResultObject().success("success");
    }

}
