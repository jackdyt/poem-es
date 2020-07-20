package com.jackdyt.poem.service;

import com.jackdyt.poem.dto.poemDTO;
import com.jackdyt.poem.mapper.poemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class poemServiceImpl implements poemService {
    @Autowired
    private poemMapper poemMapper;


    @Override
    public List<poemDTO> findByPage(Integer page, Integer size) {
        List<poemDTO> poems = poemMapper.findByPage(page, size);
        return poems;
    }

    @Override
    public Long findTotal() {
        Long count = poemMapper.findTotal();
        return count;
    }

    @Override
    public void saveToEs() {

    }

    @Override
    public void deleteFromEs() {

    }

    @Override
    public List<poemDTO> findByKeyword(String keyword, String type, String author) {
        return null;
    }
}
