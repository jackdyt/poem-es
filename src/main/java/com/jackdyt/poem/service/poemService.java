package com.jackdyt.poem.service;

import com.jackdyt.poem.dto.poemDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface poemService {
    List<poemDTO> findByPage(@Param("start")Integer start, @Param("size")Integer size);
    Long findTotal();
    void saveToEs();
    void deleteFromEs();
    List<poemDTO> findByKeyword(String keyword, String type);

}
