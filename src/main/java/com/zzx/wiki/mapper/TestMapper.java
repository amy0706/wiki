package com.zzx.wiki.mapper;

import com.zzx.wiki.domain.Test;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface TestMapper {
     List<Test> list();
}
