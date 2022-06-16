package com.flowchart.controller;

import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @className: LoginController
 * @Description: 模拟用户登录，可以删掉
 * @author: ct
 * @date: 2022/2/28 10:51
 */
@RestController
@RequestMapping("/api/oauth")
public class LoginController {

    @GetMapping("/sso/{accessToken}")
    public Map<String, Object> toSso(@PathVariable("accessToken") String accessToken) {
        // "id":"531a562c69ae4a80ac997c42bbaa7f11",
        // "name":"局长",
        // "alias":"局长",
        //{"organId":"2258e0d5f5894ce78ec05d7a6e957d0a",
        // "zoneId":"ed1768754e6844bfb0fd0a6a567763bf",
        // "organName":"X部/A局/A局领导",
        // "departPosition":"A局领导参谋"}
        // "isOnline":false,
        // "sequnece":901,
        // "organPath":"2f7f10319bb8419287748d656886cd6b,ed1768754e6844bfb0fd0a6a567763bf,2258e0d5f5894ce78ec05d7a6e957d0a",

        Integer randomNumber = (int)(1+Math.random()*(10-1+1));
        Map<String, Object> result = new HashMap<>();
        result.put("id", randomNumber);
        result.put("name", "编号:" + randomNumber);
        result.put("alias", "张局长");
        result.put("enAccount", "Mr.zhang");
        result.put("organId", "2258e0d5f5894ce78ec05d7a6e957d0a");
        result.put("zoneId", "ed1768754e6844bfb0fd0a6a567763bf");
        result.put("organName", "X部/A局/A局领导");
        result.put("departPosition", "A局领导参谋");
        return result;
    }

}
