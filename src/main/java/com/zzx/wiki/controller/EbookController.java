package com.zzx.wiki.controller;

import com.zzx.wiki.req.EbookQueryReq;
import com.zzx.wiki.req.EbookSaveReq;
import com.zzx.wiki.resp.CommonResp;
import com.zzx.wiki.resp.EbookQueryResp;
import com.zzx.wiki.resp.PageResp;
import com.zzx.wiki.service.EbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/ebook")
public class EbookController {

    @Autowired
    private EbookService ebookService;

    @GetMapping("/list")
    public CommonResp list(EbookQueryReq req) {
        CommonResp<PageResp<EbookQueryResp>> resp = new CommonResp<>();
        PageResp<EbookQueryResp> list = ebookService.list(req);
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@RequestBody EbookSaveReq req) {
        CommonResp resp = new CommonResp<>();
        ebookService.save(req);
        return resp;
    }
}
