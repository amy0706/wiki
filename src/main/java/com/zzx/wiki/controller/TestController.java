package com.zzx.wiki.controller;

import com.zzx.wiki.domain.Test;
import com.zzx.wiki.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping("/test/list")
    public List<Test> list() {
        return testService.list();
    }

}
