package com.zzx.wiki.service;

import com.zzx.wiki.domain.Test;
import com.zzx.wiki.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {

    @Autowired
    private TestMapper testMapper;

    public List<Test> list() {
        return testMapper.list();
    }
}
