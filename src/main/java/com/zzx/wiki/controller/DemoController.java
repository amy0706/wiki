package com.zzx.wiki.controller;

import com.zzx.wiki.domain.Demo;
import com.zzx.wiki.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DemoController {

    @Autowired
    private DemoService demoService;

    @RequestMapping("/demo/list")
    public List<Demo> list() {
        return demoService.list();
    }

}
