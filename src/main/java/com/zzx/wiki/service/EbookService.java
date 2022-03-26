package com.zzx.wiki.service;

import com.mysql.cj.util.StringUtils;
import com.zzx.wiki.domain.Ebook;
import com.zzx.wiki.domain.EbookExample;
import com.zzx.wiki.mapper.EbookMapper;
import com.zzx.wiki.req.EbookReq;
import com.zzx.wiki.resp.EbookResp;
import com.zzx.wiki.util.CopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class EbookService {

    @Autowired
    private EbookMapper ebookMapper;

    public List<EbookResp> list(EbookReq req) {
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getName())) {
            criteria.andNameLike("%" + req.getName() + "%");

        }
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);

//        List<EbookResp> respList = new ArrayList<>();
//        for (Ebook ebook : ebookList) {
////            EbookResp ebookResp = new EbookResp();
////            BeanUtils.copyProperties(ebook, ebookResp);
        //对象复制
//            EbookResp ebookResp = CopyUtil.copy(ebook, EbookResp.class);
//            respList.add(ebookResp);
//        }

        //列表复制
        List<EbookResp> respList = CopyUtil.copyList(ebookList, EbookResp.class);
        return respList;
    }
}
