package com.zzx.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzx.wiki.domain.Category;
import com.zzx.wiki.domain.CategoryExample;
import com.zzx.wiki.mapper.CategoryMapper;
import com.zzx.wiki.req.CategoryQueryReq;
import com.zzx.wiki.req.CategorySaveReq;
import com.zzx.wiki.resp.CategoryQueryResp;
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
public class CategoryService {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private SnowFlake snowFlake;

    /**
     * 查询
     */
    public List<CategoryQueryResp> all() {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort asc");
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);

        PageInfo<Category> pageInfo = new PageInfo<>(categoryList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        //列表复制
        List<CategoryQueryResp> respList = CopyUtil.copyList(categoryList, CategoryQueryResp.class);
        return respList;
    }

    /**
     * 保存
     */
    public void save(CategorySaveReq req) {
        Category category = CopyUtil.copy(req, Category.class);
        if (ObjectUtils.isEmpty(req.getId())) {
            //新增
            category.setId(snowFlake.nextId());
            categoryMapper.insert(category);
        } else {
            //编辑
            categoryMapper.updateByPrimaryKey(category);
        }
    }

    /**
     * 删除
     */
    public void delete(Long id) {
        categoryMapper.deleteByPrimaryKey(id);
    }

    public PageResp<CategoryQueryResp> list(CategoryQueryReq req) {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort asc");
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
        PageHelper.startPage(req.getPage(), req.getSize());
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);

        PageInfo<Category> pageInfo = new PageInfo<>(categoryList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());


//        List<CategoryResp> respList = new ArrayList<>();
//        for (Category category : categoryList) {
////            CategoryResp categoryResp = new CategoryResp();
////            BeanUtils.copyProperties(category, categoryResp);
        //对象复制
//            CategoryResp categoryResp = CopyUtil.copy(category, CategoryResp.class);
//            respList.add(categoryResp);
//        }

        //列表复制
        List<CategoryQueryResp> respList = CopyUtil.copyList(categoryList, CategoryQueryResp.class);
        PageResp<CategoryQueryResp> pageResp = new PageResp();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);

        return pageResp;
    }
}
