package com.zzx.wiki.mapper;

import com.zzx.wiki.domain.Test;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestMapper {
     List<Test> list();
}
