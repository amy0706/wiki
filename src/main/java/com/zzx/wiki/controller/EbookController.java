package com.zzx.wiki.controller;

import com.zzx.wiki.domain.Ebook;
import com.zzx.wiki.service.EbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EbookController {

    @Autowired
    private EbookService ebookService;

    @RequestMapping("/ebook/list")
    public List<Ebook> list() {
        return ebookService.list();
    }
}
