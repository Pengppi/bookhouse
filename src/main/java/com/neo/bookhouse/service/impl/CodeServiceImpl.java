package com.neo.bookhouse.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neo.bookhouse.entity.Code;
import com.neo.bookhouse.mapper.CodeMapper;
import com.neo.bookhouse.service.CodeService;

@Service
public class CodeServiceImpl extends ServiceImpl<CodeMapper, Code> implements  CodeService{

}
