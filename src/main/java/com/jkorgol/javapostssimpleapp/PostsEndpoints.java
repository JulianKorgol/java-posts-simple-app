package com.jkorgol.javapostssimpleapp;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PostsEndpoints {
    @GetMapping("/health-check")
    public Map<String, String> healthCheck() {
        Map<String, String> res = new HashMap<>();
        res.put("status", "ok");

        return res;
    }
}
