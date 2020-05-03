package com;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @author: yangyingyang
 * @date: 2020/4/26.
 */
@Api(description = "接口")
@RestController
@RequestMapping(value = "/message")
public class ApiController {

    @Value("${server.port}")
    private String port;

    @ApiOperation(value = "get")
    @GetMapping("/get")
    public String message(){
        System.out.println("message");
        return port;
    }
}
