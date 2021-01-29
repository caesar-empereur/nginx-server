package com;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description
 * @author: yangyingyang
 * @date: 2020/4/26.
 */
@Api(description = "接口")
@RestController
@RequestMapping(value = "/message")
@Slf4j
public class ApiController {

    @Resource
    private OrderRepository orderRepository;

    @ApiOperation(value = "get")
    @GetMapping("/get")
    public List<Order> message(){
        log.info("接收到请求");

        return orderRepository.findAll();
    }

}
