package com.zzx.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzx.wiki.domain.Ebook;
import com.zzx.wiki.domain.EbookExample;
import com.zzx.wiki.mapper.EbookMapper;
import com.zzx.wiki.req.EbookQueryReq;
import com.zzx.wiki.req.EbookSaveReq;
import com.zzx.wiki.resp.EbookQueryResp;
import com.zzx.wiki.resp.PageResp;
import com.zzx.wiki.util.CopyUtil;
import com.zzx.wiki.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class EbookService {

    private static final Logger LOG = LoggerFactory.getLogger(EbookService.class);

    @Autowired
    private EbookMapper ebookMapper;

    @Autowired
    private SnowFlake snowFlake;

    /**
     * 查询
     */
    public PageResp<EbookQueryResp> list(EbookQueryReq req) {
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getName())) {
            criteria.andNameLike("%" + req.getName() + "%");

        }
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);

        PageInfo<Ebook> pageInfo = new PageInfo<>(ebookList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());


//        List<EbookResp> respList = new ArrayList<>();
//        for (Ebook ebook : ebookList) {
////            EbookResp ebookResp = new EbookResp();
////            BeanUtils.copyProperties(ebook, ebookResp);
        //对象复制
//            EbookResp ebookResp = CopyUtil.copy(ebook, EbookResp.class);
//            respList.add(ebookResp);
//        }

        //列表复制
        List<EbookQueryResp> respList = CopyUtil.copyList(ebookList, EbookQueryResp.class);
        PageResp<EbookQueryResp> pageResp = new PageResp();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);

        return pageResp;
    }

    /**
     * 保存
     */
    public void save(EbookSaveReq req) {
        Ebook ebook = CopyUtil.copy(req, Ebook.class);
        if (ObjectUtils.isEmpty(req.getId())) {
            //新增
            ebook.setId(snowFlake.nextId());
            ebookMapper.insert(ebook);
        } else {
            //编辑
            ebookMapper.updateByPrimaryKey(ebook);
        }
    }
}
