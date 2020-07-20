package com.jackdyt.poem.service;

import com.jackdyt.poem.dto.poemDTO;

import java.util.List;

public interface poemService {
    List<poemDTO> findByPage(Integer page, Integer size);
    Long findTotal();
    void saveToEs();
    void deleteFromEs();
    List<poemDTO> findByKeyword(String keyword, String type, String author);

}
