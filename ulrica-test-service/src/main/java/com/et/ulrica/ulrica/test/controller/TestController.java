package com.et.ulrica.ulrica.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:ulrica
 * @date: 2022/7/26 上午9:08
 * @description:
 * @version:1.0
 **/
@RestController
public class TestController {

  @GetMapping("/test")
  public String getTestString() {
    return "hello test";
  }

  @GetMapping("/test1")
  public String getTestString1(@RequestParam("id") String id) {
    System.out.println(id);
    return "hello " + id;
  }
}