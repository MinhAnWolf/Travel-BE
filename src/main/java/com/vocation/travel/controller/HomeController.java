package com.vocation.travel.controller;

import com.vocation.travel.common.constant.ApiConstant;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        return ResponseEntity.ok("O k");
    }
}
