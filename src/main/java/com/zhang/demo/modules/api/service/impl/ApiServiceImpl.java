package com.zhang.demo.modules.api.service.impl;

import com.zhang.demo.modules.api.service.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

/**
  * @Author: zhang
  * @Date: 2020-11-06 14:58:47
  * @Description:
  * @Version: 1.0
  */
@Slf4j
@Service
public class ApiServiceImpl implements ApiService {


    @Value("${api.xf.url}")
    private String xfApi;

    @Value("classpath:static/data/result.json")
    private Resource MENU_JSON;


    public static void main(String[] args) {

    }


}
