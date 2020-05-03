package com;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description
 * @author: yangyingyang
 * @date: 2020/4/26.
 */
@Api(description = "接口")
@RestController
@RequestMapping(value = "/message")
public class ManagerController {

    @ApiOperation(value = "get")
    @GetMapping("/get")
    public void message(){
        System.out.println("message");
    }
}
